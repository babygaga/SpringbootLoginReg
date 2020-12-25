package com.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements  WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//super.addViewControllers(registry); -rossz elavult kód
		WebMvcConfigurer.super.addViewControllers(registry);
		registry.addViewController("/login").setViewName("auth/login");//authmappában van a login oldal
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);	
	}
}
