package teamEyetist.eyetist;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamEyetist.eyetist.service.AzureService;
import teamEyetist.eyetist.service.AzureServiceImpl;

@Configuration
public class AzureConfiguration {
    //Azure에 로그인할 디폴트 크레덴셜
    DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

    //Azure에 로그인
    BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .endpoint("https://eyetiststorage.blob.core.windows.net/")
            .credential(defaultCredential)
            .buildClient();

    @Bean
    public AzureService azureService(){
        return new AzureServiceImpl(defaultCredential, blobServiceClient);
    }
}
