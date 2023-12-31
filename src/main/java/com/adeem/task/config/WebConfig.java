package com.adeem.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("").setViewName("home");
		registry.addViewController("/congratulations").setViewName("congratulations");
		registry.addViewController("/login");
		registry.addViewController("/logout-form").setViewName("logout-form");

	}

}
