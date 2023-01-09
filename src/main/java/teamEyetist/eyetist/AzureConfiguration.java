package teamEyetist.eyetist;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamEyetist.eyetist.repository.AzureRepository;
import teamEyetist.eyetist.repository.AzureRepositoryImpl;
import teamEyetist.eyetist.service.AzureService;
import teamEyetist.eyetist.service.AzureServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AzureConfiguration {

    @PersistenceContext    // EntityManagerFactory가 DI 할 수 있도록 어노테이션 설정
    private EntityManager em;

    @Bean
    public AzureRepository azureRepository(){
        return new AzureRepositoryImpl(em);
    }
    @Bean
    public AzureService azureService(){
        return new AzureServiceImpl(new AzureRepositoryImpl(em));
    }
}
