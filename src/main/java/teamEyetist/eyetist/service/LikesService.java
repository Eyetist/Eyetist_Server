package teamEyetist.eyetist.service;

import teamEyetist.eyetist.domain.AzureDTO;
import teamEyetist.eyetist.domain.Likes;

import java.util.List;

public interface LikesService {
    public String receiveHeart(String blobName, String member, String heart);
    public List<Likes> getLikesList(String member);
}
