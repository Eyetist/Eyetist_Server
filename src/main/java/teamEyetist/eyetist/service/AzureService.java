package teamEyetist.eyetist.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AzureService {
    public String storeImage(MultipartFile file, String publicCheck, String ContainerName, String blobName) throws IOException;
    public JSONObject readImage(String containerName, String blobName);
    public String deleteContainer(String containerName);
    public String deleteBlob(String containerName, String blobName);
    public JSONObject readImageList(String containerName);
    public String findByBlobName(String containerName, String blobName);
    public JSONObject test(String userId);
}
