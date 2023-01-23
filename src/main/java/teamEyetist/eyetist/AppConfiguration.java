package teamEyetist.eyetist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import teamEyetist.eyetist.repository.UserRepository;
import teamEyetist.eyetist.repository.UserRepositoryImpl;
import teamEyetist.eyetist.service.UserService;
import teamEyetist.eyetist.service.UserServiceImpl;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AppConfiguration implements WebMvcConfigurer{

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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
