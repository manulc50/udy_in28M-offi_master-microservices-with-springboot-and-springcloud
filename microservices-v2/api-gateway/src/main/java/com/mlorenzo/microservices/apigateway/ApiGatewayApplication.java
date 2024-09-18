package com.mlorenzo.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;


@EnableEurekaClient // Opcional. Con tener la dependencia "spring-cloud-starter-netflix-eureka-client", es suficiente para registrarse en el servidor de descubrimiento Eureka
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	// Bean de Spring que configura Propiedad la cantidad de trazas que Sleuth debe enviar al servidor Zipkin
	// Otra alternativa equivalente es usar la propiedad "spring.sleuth.sampler.probability" con el valor "1.0" en el archivo de propiedades
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
