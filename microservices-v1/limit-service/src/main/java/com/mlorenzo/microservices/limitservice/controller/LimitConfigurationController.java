package com.mlorenzo.microservices.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.microservices.limitservice.configuration.Configuration;
import com.mlorenzo.microservices.limitservice.entity.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


// Nota: La anotación "@RefreshScope" sólo es necesaria si se usa la anotación "@Value" para inyectar valores de propiedades de archivos de configuraciones. Si se usa una clase de configuración anotada con "@ConfigurationProperties" o se usa el bean que implementa la interfaz "Environment" para ese proposito, no es necesario usar la anotación "@RefreshScope"


@RefreshScope
@RestController
public class LimitConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsConfigurations() {
		//return new LimitConfiguration(1, 1000);
		return new LimitConfiguration(configuration.getMinimum(), configuration.getMaximum());
	}
	
	// Con la anotación "@HystrixCommand", si ocurre algún error en este método, se ejecuta la alternativa implementada en el método "fallbackRetrieveConfiguration"
	@HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
	@GetMapping("/fault-tolerance-example")
	public LimitConfiguration retrieveConfiguration() {
		// Lanzamos esta excepción para simular un error
		throw new RuntimeException("Not available");
	}
	
	// Método(puede ser público o privado) para dar una respuesta alternativa en caso de ocurrir algún error en el método anterior "retrieveConfiguration" 
	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(9, 999);
	}
	
}
