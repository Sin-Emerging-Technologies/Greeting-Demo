package com.sinemergingtechnologies.database;

import com.sinemergingtechnologies.database.utils.StartupUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		context.getBean(StartupUtils.class).updateDbWithRoleEnums();
	}


//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				String reactAppUrl = "http://localhost:3000";
//
//				registry.addMapping("/test").allowedOrigins(reactAppUrl);
//				registry.addMapping("/users/").allowedOrigins(reactAppUrl);
//				registry.addMapping("/primaryprovidermap/").allowedOrigins(reactAppUrl);
//			}
//		};
//	}
}
