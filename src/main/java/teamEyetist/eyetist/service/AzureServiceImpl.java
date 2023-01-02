package teamEyetist.eyetist.service;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.*;
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
    public String storeImage(MultipartFile file) throws IOException {

        // 컨테이너 생성
        String name = "tesd";
        // 컨테이너 생성
        blobServiceClient.createBlobContainer(name);

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=eyetiststorage;AccountKey=SkhZFLZGXwefVTMjkwqAyQgdNZpz3eA1nCCHfshtn4/xdAYwQbRNKFVRlMxINVWdtWKOzYrD6PJH+AStbSGvYQ==;EndpointSuffix=core.windows.net")
                .containerName(name)
                .buildClient();

        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = container.getBlobClient("test.png");

        // Upload the blob
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        System.out.print("파일 업로드 성공");
        return null;
    }

    @Override
    public String readImage(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
    }

    @Override
    public String deleteImage(String containerName) {
        //Azure에 로그인할 디폴트 크레덴셜
        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        //Azure에 로그인
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://eyetiststorage.blob.core.windows.net/")
                .credential(defaultCredential)
                .buildClient();

        // Delete the container using the service client
        blobServiceClient.deleteBlobContainer(containerName);
        return null;
    }

    @Override
    public String modifyImage() {
        return null;
    }
}
