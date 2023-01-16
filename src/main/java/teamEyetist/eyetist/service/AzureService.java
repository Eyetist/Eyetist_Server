package teamEyetist.eyetist.service;

import net.minidev.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.AzureDTO;

import java.io.IOException;
import java.util.List;

public interface AzureService {
    public String storeImage(String file, String member, String blobName, Long like, String set) throws IOException;
    public Azure readImage(String blobName);
    public List<AzureDTO> readImageList(String containerName);
    public List<AzureDTO> readPublicImageList(String visibility, int page, String member);
    public Long imageCount();
    public void increaseLikes(String blobName);
    public void decreaseLikes(String blobName);
    public String deleteContainer(String containerName);
    public String deleteBlob(String userId, String blobName);
    public String findByBlobName(String containerName, String blobName);
    public String test(MultipartFile file, String member, String blobName, Long likes, String visibility) throws IOException;
}
