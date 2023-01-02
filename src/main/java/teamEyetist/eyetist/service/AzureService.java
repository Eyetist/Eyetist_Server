package teamEyetist.eyetist.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AzureService {
    public String storeImage(MultipartFile file) throws IOException;
    public String readImage(Resource resource) throws IOException;
    public String deleteImage(String containerName);
    public String modifyImage();
}
