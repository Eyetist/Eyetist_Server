package teamEyetist.eyetist.service;

import com.azure.storage.blob.BlobContainerClient;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AzureService {
    public String storeImage(MultipartFile file, String ContainerName, String blobName) throws IOException;
    public String readImage(Resource resource) throws IOException;
    public String deleteContainer(String containerName);
    public String deleteBlob(String containerName, String blobName);
    public String modifyImage();

    public String findByBlobName(BlobContainerClient blobContainerClient, String blobName);

    public String test(MultipartFile file, String containerName, String imageTitle) throws IOException;
}
