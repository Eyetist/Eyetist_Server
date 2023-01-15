package teamEyetist.eyetist.repository;
import com.nimbusds.jose.shaded.json.JSONObject;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.User;

import java.util.List;

public interface AzureRepository {

    public String storeImage(Azure azure);
    public Azure readImage(String blobName);
    public List<Azure> readImageList(String id);
    public List<Azure> readPublicImageList(String visibility, int page, String member);
    public Long imageCount();
    public void increaseLikes(String blobName);
    public void decreaseLikes(String blobName);
    public String deleteUserStore();
    public String deleteImage(String blobName);
}
