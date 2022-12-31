package teamEyetist.eyetist.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
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
    @Value("https://eyetiststorage.blob.core.windows.net/images/hi")
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
        // Create a unique name for the container
        String containerName = "quickstartblobs" + java.util.UUID.randomUUID();

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=eyetiststorage;AccountKey=SkhZFLZGXwefVTMjkwqAyQgdNZpz3eA1nCCHfshtn4/xdAYwQbRNKFVRlMxINVWdtWKOzYrD6PJH+AStbSGvYQ==;EndpointSuffix=core.windows.net")
                .containerName("images")
                .buildClient();

        BlobClient blobClient = container.getBlobClient("test");
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        System.out.print("파일 업로드 성공");
        return containerName;
    }
}
