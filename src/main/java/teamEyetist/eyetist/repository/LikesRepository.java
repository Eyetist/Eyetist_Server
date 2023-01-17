package teamEyetist.eyetist.repository;

import teamEyetist.eyetist.domain.AzureDTO;
import teamEyetist.eyetist.domain.Likes;

import java.util.List;

public interface LikesRepository {
    public String receiveHeart(String blobName, String member, String heart);
    public List<Likes> getLikesList(String member);
    public void storeHeart(String blobName, String member);
    public void deleteHeart(String likesBlobName, String member);
}
