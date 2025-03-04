package kr.co.ict2.ngg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class NggApplication {

	public static void main(String[] args) {
		SpringApplication.run(NggApplication.class, args);
		// String non_slice_birth = "1989-05-26T21355135:3253";
    
		// String sliced_birth = non_slice_birth.substring(0, 10);
		// System.out.println(sliced_birth);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Test ================");
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedHeaders("*")
						.allowedMethods("*").maxAge(3600);
			}
		};
	}
}
