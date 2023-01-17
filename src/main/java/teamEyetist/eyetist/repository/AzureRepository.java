package teamEyetist.eyetist.repository;
import net.minidev.json.JSONObject;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.AzureDTO;

import java.util.List;

public interface AzureRepository {

    public String storeImage(Azure azure);
    public Azure readImage(String blobName);
    public List<AzureDTO> readImageList(String id);
    public List<AzureDTO> readPublicImageList(String visibility, int page, String member);
    public Long imageCount();
    public void increaseLikes(String blobName);
    public void decreaseLikes(String blobName);
    public String deleteImage(String blobName);
    public List<AzureDTO> weeklyHeart(String visibility, int page, String member);
    public List<AzureDTO> topHeart(String visibility, int page, String member);
}
