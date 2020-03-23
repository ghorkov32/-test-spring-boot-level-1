package coop.tecso.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("coop.tecso.examen.model")
public class MainApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
	
}
