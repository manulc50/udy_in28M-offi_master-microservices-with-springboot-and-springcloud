package com.mlorenzo.microservices.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@EnableEurekaClient // Opcional. Con tener la dependencia "spring-cloud-starter-netflix-eureka-client", es suficiente para registrarse en el servidor de descubrimiento Eureka
@EnableFeignClients
@SpringBootApplication
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}
	
	// Cliente RestTemplate con balanceador de carga
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate( ) {
		return new RestTemplate();
	}

}
