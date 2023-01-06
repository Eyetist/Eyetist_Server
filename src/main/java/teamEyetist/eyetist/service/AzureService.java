package teamEyetist.eyetist.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;

import java.io.IOException;
import java.util.List;

public interface AzureService {
    public String storeImage(MultipartFile file, String ContainerName, String blobName, Long like, String check) throws IOException;
    public Azure readImage(String containerName, String blobName);
    public List<Azure> readImageList(String containerName);
    public List<Azure> readPublicImageList(String check);
    public String deleteContainer(String containerName);
    public String deleteBlob(String containerName, String blobName);
    public String findByBlobName(String containerName, String blobName);
    public JSONObject test(String containerName, String blobName);
}
