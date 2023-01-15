package teamEyetist.eyetist.repository;
import com.nimbusds.jose.shaded.json.JSONObject;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.User;

import java.util.List;

public interface AzureRepository {

    public String storeImage(Azure azure);
    public Azure readImage(String blobName);
    public List<Azure> readImageList(String id);
    public List<Azure> readPublicImageList(String visibility, int page);
    public Long imageCount();
    public String findImage(String imageName, String id);
    public String deleteUserStore();
    public String deleteImage(String blobName);
}
