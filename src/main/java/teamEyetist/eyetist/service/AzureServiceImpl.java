package teamEyetist.eyetist.service;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.repository.AzureRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public String storeImage(MultipartFile file, String userId, String title, Long likes, String set) throws IOException {

        // 컨테이너 존재하지 않으면 생성
        blobServiceClient.createBlobContainerIfNotExists(userId);
        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(userId);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        String blobName = UUID.randomUUID().toString();
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        // Upload the blob
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        //객체 생성
        Azure azure = new Azure(userId, blobName, title, blobClient.getBlobUrl(), likes, set);

        //db에 저장
        azureRepository.storeImage(azure);

        // blob Url
        return blobClient.getBlobUrl();
    }

    /**
     * 회원의 저장된 그림 이미지 한 개 가져오는 코드
     */
    @Override
    public Azure readImage(String blobname){
        return azureRepository.readImage(blobname);
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

    @Override
    public String deleteContainer(String userId) {
        // 컨테이너 삭제
        blobServiceClient.deleteBlobContainerIfExists(userId);
        return null;
    }

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
    public JSONObject test(String containerName, String blobName){
    // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

        Map<String, String> tags = new HashMap<String, String>();
        tags.put("title", blobName);
        tags.put("Content", "image");
        tags.put("Date", String.valueOf(LocalDate.now()));
        blobClient.setTags(tags);

        String query = "&where=title='iopp3423'";

        blobContainerClient.findBlobsByTags(query)
                .forEach(blob -> System.out.printf("Name: %s%n", blob.getName()));

        JSONObject jsonObject = new JSONObject();
        // blob Url
        return jsonObject;
    }

    public BlobContainerClient makeBlobContainerClient(String containerName){
        return new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=eyetiststorage;AccountKey=SkhZFLZGXwefVTMjkwqAyQgdNZpz3eA1nCCHfshtn4/xdAYwQbRNKFVRlMxINVWdtWKOzYrD6PJH+AStbSGvYQ==;EndpointSuffix=core.windows.net")
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