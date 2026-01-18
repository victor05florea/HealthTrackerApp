package com.victor.healthtracker;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clasa principala a aplicatiei Spring Boot
 * Porneste serverul Spring Boot, configureaza bean-urile necesare (ex: RestTemplate pentru HTTP) si este punctul de intrare al aplicatiei
 * 
 * Metode:
 * -main(args): Punctul de intrare al aplicatiei (args = argumentele liniei de comanda)
 * -restTemplate(): Configureaza si returneaza o instanta de RestTemplate pentru comunicarea HTTP (returneaza instanta de RestTemplate configurata)
 */
@SpringBootApplication
public class HealthtrackerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HealthtrackerApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
