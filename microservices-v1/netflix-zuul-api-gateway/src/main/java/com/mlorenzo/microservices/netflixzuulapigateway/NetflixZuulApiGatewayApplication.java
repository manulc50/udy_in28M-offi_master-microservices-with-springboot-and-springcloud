package com.mlorenzo.microservices.netflixzuulapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;


@EnableZuulProxy
@EnableEurekaClient // Opcional. Con tener la dependencia "spring-cloud-starter-netflix-eureka-client", es suficiente para registrarse en el servidor de descubrimiento Eureka
@SpringBootApplication
public class NetflixZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixZuulApiGatewayApplication.class, args);
	}
	
	// Bean de Spring que configura Propiedad la cantidad de trazas que Sleuth debe enviar al servidor Zipkin
	// Otra alternativa equivalente es usar la propiedad "spring.sleuth.sampler.probability" con el valor "1.0" en el archivo de propiedades
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
