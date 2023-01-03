package teamEyetist.eyetist.service;

import com.azure.identity.DefaultAzureCredential;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.Charset;

public class AzureServiceImpl implements AzureService{

    private final DefaultAzureCredential defaultCredential;
    private final BlobServiceClient blobServiceClient;
    public AzureServiceImpl(DefaultAzureCredential defaultCredential, BlobServiceClient blobServiceClient) {
        this.defaultCredential = defaultCredential;
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public String storeImage(MultipartFile file, String containerName, String blobName) throws IOException {

        // 컨테이너 존재하지 않으면 생성
        blobServiceClient.createBlobContainerIfNotExists(containerName);

        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);

        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

        // Upload the blob
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        System.out.print("파일 업로드 성공");

        // blob Url
        return blobClient.getBlobUrl();
    }

    @Override
    public String readImage(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
    }

    @Override
    public String deleteContainer(String containerName) {
        // 컨테이너 삭제
        blobServiceClient.deleteBlobContainerIfExists(containerName);
        return null;
    }

    @Override
    public String deleteBlob(String containerName, String blobName) {
        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);
        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        //blob 삭제
        blobClient.undelete();
        return null;
    }

    @Override
    public String modifyImage() {
        return null;
    }

    @Override
    public String findByBlobName(String containerName, String blobName) {

        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);

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
    public JSONObject test(String containerName){

        //blobContainer 생성
        blobServiceClient.createBlobContainerIfNotExists(containerName);

        // blobContainerClient 생성
        BlobContainerClient blobContainerClient = makeBlobContainerClient(containerName);

        JSONObject jsonObject = new JSONObject();


        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
              System.out.println("blobItem.URL = " + blobContainerClient.getBlobClient(blobItem.getName()).getBlobUrl());
            System.out.println("blobItem.getName() = " + blobItem.getName());
                jsonObject.put(blobItem.getName(),blobContainerClient.getBlobClient(blobItem.getName()).getBlobUrl());
          }

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