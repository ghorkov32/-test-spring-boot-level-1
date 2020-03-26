package coop.tecso.examen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class MainApplication {

	@Bean
	@Primary
	public ObjectMapper scmsObjectMapper() {
		com.fasterxml.jackson.databind.ObjectMapper responseMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		responseMapper.registerModule(new JodaModule());
		return responseMapper;
	}

	public static void main(final String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
