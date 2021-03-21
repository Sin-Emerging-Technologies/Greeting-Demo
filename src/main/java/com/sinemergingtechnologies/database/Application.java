package com.sinemergingtechnologies.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				String reactAppUrl = "http://localhost:3000";
//
//				registry.addMapping("/test").allowedOrigins(reactAppUrl);
//				registry.addMapping("/clients/").allowedOrigins(reactAppUrl);
//				registry.addMapping("/providers/").allowedOrigins(reactAppUrl);
//				registry.addMapping("/primaryprovidermap/").allowedOrigins(reactAppUrl);
//			}
//		};
//	}
}
