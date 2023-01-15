package teamEyetist.eyetist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamEyetist.eyetist.domain.Likes;
import teamEyetist.eyetist.repository.LikesRepository;

import java.util.List;

@Service
@Transactional
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;

    public LikesServiceImpl(LikesRepository likesRepository){
        this.likesRepository = likesRepository;
    }

    public void receiveHeart(String blobName, String member, String heart){
        likesRepository.receiveHeart(blobName, member, heart);
    };

    public List<Likes> getLikesList(String member){
        return likesRepository.getLikesList(member);
    }
}
