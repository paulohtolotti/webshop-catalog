package com.phtdev.webshopcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebshopcatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopcatalogApplication.class, args);
	}

}
