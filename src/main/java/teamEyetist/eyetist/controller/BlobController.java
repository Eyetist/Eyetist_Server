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
import teamEyetist.eyetist.service.AzureService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@RestController
@RequestMapping("blob")
public class BlobController{
    @Value("https://eyetiststorage.blob.core.windows.net/test")
    private Resource resource;
    private AzureService azureService;

    public BlobController(AzureService azureService) {
        this.azureService = azureService;
    }

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
        azureService.storeImage(file);
        System.out.print("파일 업로드 성공");
        return "200";
    }

    @GetMapping ("delete")
    public void deleteContainer() {

        //Azure에 로그인할 디폴트 크레덴셜
        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        //Azure에 로그인
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://eyetiststorage.blob.core.windows.net/")
                .credential(defaultCredential)
                .buildClient();

        // Delete the container using the service client
        blobServiceClient.deleteBlobContainer("test");
    }
}
