package ar.edu.unq.reviewitbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ReviewItBackendApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ReviewItBackendApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReviewItBackendApplication.class);
	}

}
