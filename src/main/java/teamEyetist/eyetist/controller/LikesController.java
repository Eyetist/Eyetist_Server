package teamEyetist.eyetist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamEyetist.eyetist.domain.Likes;
import teamEyetist.eyetist.service.LikesService;
import teamEyetist.eyetist.service.LikesServiceImpl;

import java.util.List;

@RestController
@RequestMapping("like")
public class LikesController {

    private final LikesService likesService;

    public LikesController(LikesService likesService){
        this.likesService = likesService;
    }

    @PostMapping("/store")
    public void storeOrRemoveHeart(@RequestParam String blobName, @RequestParam String member, @RequestParam String heart){
        likesService.receiveHeart(blobName, member, heart);
    }

    @GetMapping("/getList")
    public List<Likes> getLikesList(String member){
        return likesService.getLikesList(member);
    }
}
