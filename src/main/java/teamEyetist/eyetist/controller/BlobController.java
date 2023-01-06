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

    /**
     * 한 회원이 선택한 이미지 가져오는 코드
     */
    @PostMapping("/getImage")
    public JSONObject readBlobFile(@RequestParam String userId, @RequestParam String imageTitle){
        return azureService.readImage(userId, imageTitle);
    }

    /**
     * 한 회원의 이미지 리스트 가져오는 코드
     */
    @PostMapping("/getImageList")
    JSONObject getImageList(@RequestParam String userId) throws IOException {
        return azureService.readImageList(userId);
    }
    /**
     * 이미지 저장하는 코드
     */
    @PostMapping("/storeImage")
    public String storeImageFile(@RequestParam MultipartFile file, @RequestParam String publicCheck, @RequestParam String containerName, @RequestParam String imageTitle) throws IOException{
        return azureService.storeImage(file, publicCheck, containerName, imageTitle); // 이미지 url 리턴
    }
    /**
     * 컨테이너 지우는거 -> 회원삭제할 때 같이 지워야함
     * @param userId
     */
    @PostMapping ("/deleteStorage")
    public void deleteUserStorage(String userId) {
        azureService.deleteContainer(userId);
    }

    /**
     * blob지우는 거 -> 회원에 저장된 사진 중 한 개 지울 때 사용
     */
    @PostMapping("/deleteImage")
    public void deleteUserImage(@RequestParam String userId, @RequestParam String imageTitle) {
        azureService.deleteBlob(userId, imageTitle);
    }

    /**
     * 이미지 있는지 체크(지워도 될듯?)
     */
    @PostMapping("/findImage")
    public String findImage(@RequestParam String userId, @RequestParam String imageTitle){
         return azureService.findByBlobName(userId, imageTitle);
    }
    @PostMapping("/test")
    public JSONObject test(@RequestParam String userId) throws IOException{
        return azureService.test(userId);
    }
}
