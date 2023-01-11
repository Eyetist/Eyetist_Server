package teamEyetist.eyetist.controller;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.service.AzureService;

import java.io.File;
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
    public Azure readImage(@RequestParam String blobName){
        return azureService.readImage(blobName);
    }
    /**
     * 한 회원의 이미지 리스트 가져오는 코드(완료)
     */
    @PostMapping("/getImageList")
    List<Azure> getImageList(@RequestParam String member){
        return azureService.readImageList(member);
    }
    /**
     * 이미지 저장하는 코드(완료)
     */
    @PostMapping("/store")
    public String storeImageFile(@RequestParam String file, @RequestParam String member, @RequestParam String title, @RequestParam Long likes, @RequestParam String visibility) throws IOException{
        return azureService.storeImage(file, member, title, likes, visibility); // 이미지 url 리턴
    }
    /**
     * 퍼블릭 이미지 가져오는 코드(완료)
     */
    @GetMapping("/publicImage")
    public List<Azure> readPublicImageList(String visibility){
        return azureService.readPublicImageList(visibility);
    }
    /**
     * 컨테이너 지우는거 -> 회원삭제할 때 같이 지워야함
     * @param member
     */
    @PostMapping ("/deleteStorage")
    public void deleteUserStorage(String member) {
        azureService.deleteContainer(member);
    }

    /**
     * blob지우는 거 -> 회원에 저장된 사진 중 한 개 지울 때 사용(완료)
     */
    @PostMapping("/deleteImage")
    public void deleteUserImage(@RequestParam String member, @RequestParam String blobName) {
        azureService.deleteBlob(member, blobName);
    }

    /**
     * 이미지 있는지 체크(지워도 될듯?)
     */
    @PostMapping("/findImage")
    public String findImage(@RequestParam String member, @RequestParam String imageTitle){
         return azureService.findByBlobName(member, imageTitle);
    }

    @PostMapping("/test")
    public String test(@RequestParam MultipartFile file, @RequestParam String member, @RequestParam String title, @RequestParam Long likes, @RequestParam String visibility) throws IOException{
        return azureService.test(file, member, title, likes, visibility); // 이미지 url 리턴
    }
}
