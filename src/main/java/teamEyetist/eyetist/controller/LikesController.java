package teamEyetist.eyetist.controller;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamEyetist.eyetist.domain.AzureDTO;
import teamEyetist.eyetist.domain.Likes;
import teamEyetist.eyetist.service.AzureService;
import teamEyetist.eyetist.service.LikesService;
import teamEyetist.eyetist.service.LikesServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("like")
public class LikesController {

    private final LikesService likesService;
    private final AzureService azureService;

    public LikesController(LikesService likesService, AzureService azureService) {
        this.azureService = azureService;
        this.likesService = likesService;
    }

    @PostMapping("/store")
    public void storeOrRemoveHeart(@RequestParam String likesBlobName, @RequestParam String member, @RequestParam String heart){
        String likes = likesService.receiveHeart(likesBlobName, member, heart);
        System.out.println(likes);
        if(likes.equals("add")){
            azureService.increaseLikes(likesBlobName);
        }
        else{
            azureService.decreaseLikes(likesBlobName);
        }
    }

    @GetMapping("/getList")
    public List<Likes> getLikesList(String member){
        List<Likes> likes = likesService.getLikesList(member);
        return likesService.getLikesList(member);
    }
}
