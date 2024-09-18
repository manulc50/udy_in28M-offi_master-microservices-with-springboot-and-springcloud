package com.mlorenzo.microservices.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.microservices.limitservice.configuration.Configuration;
import com.mlorenzo.microservices.limitservice.entity.Limit;

// Nota: La anotación "@RefreshScope" sólo es necesaria si se usa la anotación "@Value" para inyectar valores de propiedades de archivos de configuraciones. Si se usa una clase de configuración anotada con "@ConfigurationProperties" o se usa el bean que implementa la interfaz "Environment" para ese proposito, no es necesario usar la anotación "@RefreshScope"


@RestController
@RequestMapping("limits") // Ruta base del controlador: "/limits"
public class LimitController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping
	public Limit retrieveLimits() {
		//return new Limits(1, 1000);
		return new Limit(configuration.getMinimum(), configuration.getMaximum());
	}
}
