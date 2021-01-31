package org.springframework.samples.petclinic;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.classic.Logger;

@SpringBootApplication()
public class PetclinicApplication {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PetclinicApplication.class);
	
	public static void main(String[] args) {
		log.info("Lanzando aplicación");
		SpringApplication.run(PetclinicApplication.class, args);
		log.info("Aplicación lanzada/recargada satisfactoriamente");
	}

}
