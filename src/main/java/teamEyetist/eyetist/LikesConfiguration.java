package teamEyetist.eyetist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamEyetist.eyetist.repository.LikesRepository;
import teamEyetist.eyetist.repository.LikesRepositoryImpl;
import teamEyetist.eyetist.repository.UserRepositoryImpl;
import teamEyetist.eyetist.service.LikesService;
import teamEyetist.eyetist.service.LikesServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Configuration
public class LikesConfiguration {

    @PersistenceContext    // EntityManagerFactory가 DI 할 수 있도록 어노테이션 설정
    private EntityManager em;

    @Bean
    public LikesRepository likesRepository(){
        return new LikesRepositoryImpl(em);
    }

    @Bean
    public LikesService likesService(){
        return new LikesServiceImpl(new LikesRepositoryImpl(em));
    }
}
