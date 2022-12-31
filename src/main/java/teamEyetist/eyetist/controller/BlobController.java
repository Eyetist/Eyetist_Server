package teamEyetist.eyetist.controller;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("blob")
public class BlobController{
    @Value("https://eyetiststorage.blob.core.windows.net/test/testtest.png")
    private Resource resource;

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException {
        return StreamUtils.copyToString(this.resource.getInputStream(), Charset.defaultCharset());
    }


    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
            os.write(multipartFile.getBytes());
        }
        return "file was updated";
    }
    @PostMapping("/test")
    public String writeTest(@RequestPart(value = "file") MultipartFile file) throws IOException{

        //Azure에 로그인할 디폴트 크레덴셜
        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        //Azure에 로그인
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://eyetiststorage.blob.core.windows.net/")
                .credential(defaultCredential)
                .buildClient();

        // 컨테이너 생성
        String name = "test";
        // 컨테이너 생성
        blobServiceClient.createBlobContainer(name);

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=eyetiststorage;AccountKey=SkhZFLZGXwefVTMjkwqAyQgdNZpz3eA1nCCHfshtn4/xdAYwQbRNKFVRlMxINVWdtWKOzYrD6PJH+AStbSGvYQ==;EndpointSuffix=core.windows.net")
                .containerName(name)
                .buildClient();

        // 파일 객체의 파일을 Blob 컨테이너에 할당
        BlobClient blobClient = container.getBlobClient("testtest.png");

        // Upload the blob
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        System.out.print("파일 업로드 성공");
        return name;
    }
}
