package com.mlorenzo.microservices.limitservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


// Nota: La anotación "@RefreshScope" sólo es necesaria si se usa la anotación "@Value" para inyectar valores de propiedades de archivos de configuraciones. Si se usa una clase de configuración anotada con "@ConfigurationProperties" o se usa el bean que implementa la interfaz "Environment" para ese proposito, no es necesario usar la anotación "@RefreshScope"


@Component
@ConfigurationProperties("limit-service")
public class Configuration {
	private Integer minimum;
	private Integer maximum;
	
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public Integer getMaximum() {
		return maximum;
	}
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	
}
