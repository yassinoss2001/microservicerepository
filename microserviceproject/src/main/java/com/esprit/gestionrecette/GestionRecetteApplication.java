package com.esprit.gestionrecette;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GestionRecetteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionRecetteApplication.class, args);
    }

}
