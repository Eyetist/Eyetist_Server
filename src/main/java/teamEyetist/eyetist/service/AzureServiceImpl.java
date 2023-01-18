package teamEyetist.eyetist.service;

import com.azure.identity.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.models.BlobItem;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.AzureDTO;
import teamEyetist.eyetist.repository.AzureRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static teamEyetist.eyetist.Constant.CONNECT_STRING;


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
                .connectionString(CONNECT_STRING)
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
        String azureBlobName = UUID.randomUUID().toString();

        BlobClient blobClient = blobContainerClient.getBlobClient(azureBlobName);

        String data = file.split(",")[1];

        byte[] binaryData = Base64.getDecoder().decode(data);

        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(binaryData)) {

            //이미지 Azure에 업로드
            blobClient.upload(dataStream);

            //blob 이미지 content-type -> image/png로 변경
            headerChange(blobClient);

            //Azure 컨테이너 퍼블릭 읽기권한으로 변경
            readPermissionChange(blobContainerClient);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String timeStamp = date.format(new Date());

        //객체 생성
        Azure azure = new Azure(member, azureBlobName, title, blobClient.getBlobUrl(), likes, visibility, Long.parseLong(timeStamp), 0L);

        //db에 저장
        azureRepository.storeImage(azure);

        // blob Url
        return blobClient.getBlobUrl();
    }

    /**
     * 회원의 저장된 그림 이미지 한 개 가져오는 코드
     */
    @Override
    public Azure readImage(String azureBlobName){
        return azureRepository.readImage(azureBlobName);
    }

    /**
     * 한 회원의 이미지 리스트 가져오는 코드
     */
    @Override
    public List<AzureDTO> readImageList(String userId) {
        return azureRepository.readImageList(userId);
    }

    /**
     * 공개된 사진 리스트 가져오는 코드
     */
    @Override
    public List<AzureDTO> readPublicImageList(String visibility, int page, String member) {
        return azureRepository.readPublicImageList(visibility, page, member);
    }

    /**
     * 퍼블릭 이미지 수 반환
     * @return
     */
    @Override
    public Long imageCount() {
        return azureRepository.imageCount();
    }

    /**
     * 좋아요 1 증가
     */
    @Override
    public void increaseLikes(String azureBlobName) {
        azureRepository.increaseLikes(azureBlobName);
    }
    /**
     * 좋아요 1 감소
     */
    @Override
    public void decreaseLikes(String azureBlobName) {
        azureRepository.decreaseLikes(azureBlobName);
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
    public String deleteBlob(String id, String azureBlobName) {
        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(id);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = blobContainerClient.getBlobClient(azureBlobName);

        //blob 삭제
        blobClient.deleteIfExists();
        azureRepository.deleteImage(azureBlobName);
        return null;
    }

    /**
     * 주간 인기게시물 리턴
     */
    @Override
    public List<AzureDTO> weeklyHeart(String visibility, int page, String member) {
        return azureRepository.weeklyHeart(visibility, page, member);
    }

    /**
     * 누적 인기 게시물
     */
    @Override
    public List<AzureDTO> topHeart(String visibility, int page, String member) {
        return azureRepository.topHeart(visibility, page, member);
    }

    @Override
    public String findByBlobName(String id, String azureBlobName) {

        BlobContainerClient blobContainerClient = makeBlobContainerClient(id);

        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            if (azureBlobName.equals(blobItem.getName())) {
                // 현재 중복되는 사진 이름이 있음
                return "200";
            }
        }
        // 현재 중복되는 사진 이름이 없음
        return "400";
    }

    @Override
    public String test(MultipartFile file, String member, String title, Long likes, String set) throws IOException {
        // blob Url
        return "200";
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
                        .setPermissions("racwdl"));

        //에저에 읽기권한 추가
        blobContainerClient.setAccessPolicy(PublicAccessType.CONTAINER, Collections.singletonList(identifier));
    }
    private BlobContainerClient makeBlobContainerClient(String containerName){
        return new BlobContainerClientBuilder()
                .connectionString(CONNECT_STRING)
                .containerName(containerName)
                .buildClient();
    }
}
