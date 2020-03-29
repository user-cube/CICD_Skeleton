package com.ies.smartroom.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class APIAppplication {
    public static void main(String[] args) {
        SpringApplication.run(APIAppplication.class, args);

    }
}
