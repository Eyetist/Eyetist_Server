package teamEyetist.eyetist.controller;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.*;
import com.nimbusds.jose.shaded.json.JSONObject;
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
import java.util.Map;

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
    @PostMapping("/storeImage")
    public String storeImageFile(@RequestParam MultipartFile file, @RequestParam String containerName, @RequestParam String imageTitle) throws IOException{
        return azureService.storeImage(file, containerName, imageTitle); // 이미지 url 리턴
    }
    @PostMapping ("/deleteStorage")
    public void deleteUserStorage(String userId) {
        azureService.deleteContainer(userId);
    }
    @PostMapping("/deleteImage")
    public void deleteUserImage(@RequestParam String userId, @RequestParam String imageTitle) {
        azureService.deleteBlob(userId, imageTitle);
    }
    @PostMapping("/readImages")
    public void readImages(@RequestParam String userId, @RequestParam String imageTitle){
        azureService.findByBlobName(userId, imageTitle);
    }

    @GetMapping("/test")
    public JSONObject test(@RequestParam String userId) throws IOException{
        return azureService.test(userId);
    }
}
