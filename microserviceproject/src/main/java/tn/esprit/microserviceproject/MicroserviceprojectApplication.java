package tn.esprit.microserviceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceprojectApplication.class, args);
	}

}
