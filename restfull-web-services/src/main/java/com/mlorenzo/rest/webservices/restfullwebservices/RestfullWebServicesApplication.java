package com.mlorenzo.rest.webservices.restfullwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class RestfullWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfullWebServicesApplication.class, args);
	}
	
	// Bean de Spring con nuestra configuración personalizada de Spring Data Rest
	@Bean
	public RepositoryRestConfigurer springDataRestConfig() {
		
		return new RepositoryRestConfigurer() {
			
			// Desactivamos la exposición de los endpoints por defecto de Spring Data Rest(Se incluye en la dependencia de Hal Explorer "spring-data-rest-hal-explorer")
			// ya que queremos manejar únicamente nuestros endpoints de forma manual
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
				config.disableDefaultExposure();
			}
		};
	}

}
