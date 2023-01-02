package teamEyetist.eyetist;


import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamEyetist.eyetist.repository.UserRepository;
import teamEyetist.eyetist.repository.UserRepositoryImpl;
import teamEyetist.eyetist.service.AzureService;
import teamEyetist.eyetist.service.AzureServiceImpl;
import teamEyetist.eyetist.service.UserService;
import teamEyetist.eyetist.service.UserServiceImpl;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AppConfiguration {

    @PersistenceContext    // EntityManagerFactory가 DI 할 수 있도록 어노테이션 설정
    private EntityManager em;

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl(em);
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl(new UserRepositoryImpl(em));
    }

}
