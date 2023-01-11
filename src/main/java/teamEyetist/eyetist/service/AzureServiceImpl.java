package teamEyetist.eyetist.service;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.models.BlobItem;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.repository.AzureRepository;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
public class AzureServiceImpl implements AzureService{

    private final DefaultAzureCredential defaultCredential;
    private final BlobServiceClient blobServiceClient;
    private final AzureRepository azureRepository;

    @Autowired
    public AzureServiceImpl(AzureRepository azureRepository) {
        //Azure에 로그인할 디폴트 크레덴셜
        defaultCredential = new DefaultAzureCredentialBuilder().build();

        //Azure에 로그인
        blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://eyetiststorage.blob.core.windows.net/")
                .credential(defaultCredential)
                .buildClient();

        this.azureRepository = azureRepository;
    }

    /**
     * 이미지 저장하는 코드
     */
    @Override
    public String storeImage(String file, String member, String title, Long likes, String visibility) throws IOException {

        // 컨테이너 존재하지 않으면 생성
        blobServiceClient.createBlobContainerIfNotExists(member);
        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(member);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        String blobName = UUID.randomUUID().toString();
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);


        String data = file.split(",")[1];
        byte[] binaryData = Base64.getDecoder().decode(data);

        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(binaryData.toString().getBytes())) {
            //이미지 Azure에 업로드
            blobClient.upload(dataStream);

            //blob 이미지 content-type -> image/png로 변경
            headerChange(blobClient);

            //Azure 컨테이너 퍼블릭 읽기권한으로 변경
            readPermissionChange(blobContainerClient);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //객체 생성
        Azure azure = new Azure(member, blobName, title, blobClient.getBlobUrl(), likes, visibility);

        //db에 저장
        azureRepository.storeImage(azure);

        // blob Url
        return blobClient.getBlobUrl();
    }

    /**
     * 회원의 저장된 그림 이미지 한 개 가져오는 코드
     */
    @Override
    public Azure readImage(String blobName){
        return azureRepository.readImage(blobName);
        /**
         *
        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);

        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageName", blobClient.getBlobName());
        jsonObject.put("imageUrl", blobContainerClient.getBlobClient(blobClient.getBlobName()).getBlobUrl());
         return jsonObject;
         */
    }

    /**
     * 한 회원의 이미지 리스트 가져오는 코드
     */
    @Override
    public List<Azure> readImageList(String userId) {
        return azureRepository.readImageList(userId);
        /**
         *
        //blobContainer 생성
        blobServiceClient.createBlobContainerIfNotExists(containerName);

        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("imageName", blobItem.getName());
            tempObject.put("imageUrl", blobContainerClient.getBlobClient(blobItem.getName()).getBlobUrl());
            jsonArray.add(tempObject);
        }
        jsonObject.put("images", jsonArray);
        // blob Url
        return jsonObject;
         */
    }

    /**
     * 공개된 사진 리스트 가져오는 코드
     */
    @Override
    public List<Azure> readPublicImageList(String set) {
        return azureRepository.readPublicImageList(set);
    }

    /**
     * 회원삭제 할 때 컨테이너 지우는 코드
     */
    @Override
    public String deleteContainer(String userId) {
        // 컨테이너 삭제
        blobServiceClient.deleteBlobContainerIfExists(userId);
        return null;
    }

    /**
     * 이미지 지우는 코드
     */
    @Override
    public String deleteBlob(String userId, String blobname) {
        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(userId);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = blobContainerClient.getBlobClient(blobname);

        //blob 삭제
        blobClient.deleteIfExists();
        azureRepository.deleteImage(blobname);
        return null;
    }

    @Override
    public String findByBlobName(String userId, String blobName) {

        BlobContainerClient blobContainerClient = makeBlobContainerClient(userId);

        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            if (blobName.equals(blobItem.getName())) {
                // 현재 중복되는 사진 이름이 있음
                return "200";
            }
        }
        // 현재 중복되는 사진 이름이 없음
        return "400";
    }

    @Override
    public String test(MultipartFile file, String member, String title, Long likes, String set) throws IOException {
        // 컨테이너 존재하지 않으면 생성
        blobServiceClient.createBlobContainerIfNotExists(member);
        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(member);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        String blobName = UUID.randomUUID().toString();

        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

        blobClient.upload(file.getInputStream());
        //blob 이미지 content-type -> image/png로 변경
        headerChange(blobClient);

        //Azure 컨테이너 퍼블릭 읽기권한으로 변경
        readPermissionChange(blobContainerClient);

        //객체 생성
        Azure azure = new Azure(member, blobName, title, blobClient.getBlobUrl(), likes, set);

        //db에 저장
        azureRepository.storeImage(azure);

        // blob Url
        return blobClient.getBlobUrl();
    }

    private void headerChange(BlobClient blobClient){
        BlobHttpHeaders blobHttpHeader = new BlobHttpHeaders();
        blobHttpHeader.setContentType("image/png");
        blobClient.setHttpHeaders(blobHttpHeader);
    }
    private void readPermissionChange(BlobContainerClient blobContainerClient){
        //에저에 읽기권한 추가
        BlobSignedIdentifier identifier = new BlobSignedIdentifier()
                .setId("publicreadaccess")
                .setAccessPolicy(new BlobAccessPolicy()
                        .setPermissions("r"));
        //에저에 읽기권한 추가
        blobContainerClient.setAccessPolicy(PublicAccessType.BLOB, Collections.singletonList(identifier));
    }
    private BlobContainerClient makeBlobContainerClient(String containerName){
        return new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=eyetiststorage;AccountKey=GS/r8XlAh96ulBFtlicC+FYL+IQXOKksbLcvphHfN2ti1pVZn/4tJCpRoAGDWWXD+9/WwOVaIc9Z+AStQiCrZw==;EndpointSuffix=core.windows.net")
                .containerName(containerName)
                .buildClient();
    }
}
/**
 Map<String, String> tags = new HashMap<String, String>();
 tags.put("title", imageTitle);
 tags.put("Content", "image");
 tags.put("Date", String.valueOf(LocalDate.now()));
 blobClient.setTags(tags);

 String query = "&where=title='iopp3423'";

 blobContainerClient.findBlobsByTags(query)
 .forEach(blob -> System.out.printf("Name: %s%n", blob.getName()));
 *
 */