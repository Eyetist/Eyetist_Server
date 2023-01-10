package teamEyetist.eyetist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EyetistApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EyetistApplication.class, args);
	}
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(EyetistApplication.class);
	}
}