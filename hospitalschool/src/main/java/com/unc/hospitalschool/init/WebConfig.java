package com.unc.hospitalschool.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private static Logger logger = LoggerFactory.getLogger("LOGGER");

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		logger.info("IN webconfig");
		registry.addMapping("/login").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
				.allowCredentials(false);
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
		registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
	}
}
