package dev.homework.restclientapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestClientAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestClientAppApplication.class, args);
    }

}

