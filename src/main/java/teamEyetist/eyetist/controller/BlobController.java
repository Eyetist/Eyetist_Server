package teamEyetist.eyetist.controller;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.service.AzureService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("blob")
public class BlobController{

    private final AzureService azureService;

    public BlobController(AzureService azureService) {
        this.azureService = azureService;
    }
    /**
     * 한 회원이 선택한 이미지 가져오는 코드(완료)
     */
    @PostMapping("/getImage")
    public Azure readImage(@RequestParam String blobname){
        return azureService.readImage(blobname);
    }
    /**
     * 한 회원의 이미지 리스트 가져오는 코드(완료)
     */
    @PostMapping("/getImageList")
    List<Azure> getImageList(@RequestParam String userId){
        return azureService.readImageList(userId);
    }
    /**
     * 이미지 저장하는 코드(완료)
     */
    @PostMapping("/storeImage")
    public String storeImageFile(@RequestParam String file, @RequestParam String containerName, @RequestParam String imageTitle, @RequestParam Long likes, @RequestParam String check) throws IOException{
        return azureService.storeImage(file, containerName, imageTitle, likes, check); // 이미지 url 리턴
    }
    /**
     * 퍼블릭 이미지 가져오는 코드(완료)
     */
    @GetMapping("/publicImage")
    public List<Azure> readPublicImageList(String setting){
        return azureService.readPublicImageList(setting);
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
     * blob지우는 거 -> 회원에 저장된 사진 중 한 개 지울 때 사용(완료)
     */
    @PostMapping("/deleteImage")
    public void deleteUserImage(@RequestParam String userId, @RequestParam String blobname) {
        azureService.deleteBlob(userId, blobname);
    }

    /**
     * 이미지 있는지 체크(지워도 될듯?)
     */
    @PostMapping("/findImage")
    public String findImage(@RequestParam String userId, @RequestParam String imageTitle){
         return azureService.findByBlobName(userId, imageTitle);
    }

    @PostMapping("/test")
    public JSONObject test(@RequestParam String userId, @RequestParam String imageTitle) throws IOException{
        return azureService.test(userId, imageTitle);
    }
}
