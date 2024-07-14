package org.nehlsen.venueshot.pictureservice;

import org.nehlsen.venueshot.pictureservice.storage.StorageProperties;
import org.nehlsen.venueshot.pictureservice.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PictureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
