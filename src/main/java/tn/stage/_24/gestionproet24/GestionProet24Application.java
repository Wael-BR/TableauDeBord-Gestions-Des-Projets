package tn.stage._24.gestionproet24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "tn.stage._24.gestionproet24.repository")
public class GestionProet24Application {

	public static void main(String[] args) {
		SpringApplication.run(GestionProet24Application.class, args);
	}

}
