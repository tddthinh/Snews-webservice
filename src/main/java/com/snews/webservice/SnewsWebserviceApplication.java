package com.snews.webservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.snews.webservice.files.StorageProperties;
import com.snews.webservice.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SnewsWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnewsWebserviceApplication.class, args);
	}
	
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            //storageService.deleteAll();
            //storageService.init();
        };
    }
}
