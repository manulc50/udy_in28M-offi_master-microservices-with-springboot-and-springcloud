package com.mlorenzo.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private final static Logger log = LoggerFactory.getLogger(CircuitBreakerController.class);

	
	// Indicamos que usamos la configuración por defecto de reintentos(Realiza 3 reintentos automáticamente de peticiones http de tipo Get a esta ruta "/sample-api") 
	@Retry(name = "default", fallbackMethod = "hardcodedResponse") // Si después de los reintentos sigue fallando, se ejecuta el método "hardcodedResponse" para dar una respuesta alternativa
	@GetMapping("/sample-api/retry/default")
	public String sampleApiRetryDefault() {
		log.info("Sample Api call received");
		// Esta petición http es para simular un error de comunicación. La url "http://localhost:8080/some-dumy-url" es inventada y no existe
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dumy-url", String.class);
		return responseEntity.getBody();
	}
	
	// Indicamos que usamos nuestra configuración personalizada de reintentos llamada "sample-api"(Realiza 5 reintentos automáticamente de peticiones http de tipo Get a esta ruta "/sample-api") 
	@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse") // Si después de los reintentos sigue fallando, se ejecuta el método "hardcodedResponse" para dar una respuesta alternativa
	@GetMapping("/sample-api/retry/custom")
	public String sampleApiRetryCustom() {
		log.info("Sample Api call received");
		// Esta petición http es para simular un error de comunicación. La url "http://localhost:8080/some-dumy-url" es inventada y no existe
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dumy-url", String.class);
		return responseEntity.getBody();
	}
	
	// Aplica el patrón "Circuit Breaker" usando la configuración por defecto
	// Cuando el circuito esté abierto, se ejecutará la alternativa del método "hardcodedResponse"
	@CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodedResponse")
	@GetMapping("/sample-api/circuit-breaker")
	public String sampleApiCircuitBreaker() {
		log.info("Sample Api call received");
		// Esta petición http es para simular un error de comunicación. La url "http://localhost:8080/some-dumy-url" es inventada y no existe
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dumy-url", String.class);
		return responseEntity.getBody();
	}
	
	// Con esta anotación podemos establecer un número máximo de llamadas para un determinado periodo de tiempo(Por ej: 10000 llamadas en 10 seg)
	// Si se pasa del límite establecido para ese determinado periodo de tiempo, se ejecutará la alternativa del método "hardcodedResponse"
	@RateLimiter(name = "default", fallbackMethod = "hardcodedResponse") // Usamos la configuración por defecto
	@GetMapping("/sample-api/rate-limiter")
	public String sampleApiRateLimiter() {
		log.info("Sample Api call received");
		return "Sample Api";
	}
	
	// Esta anotación es para limitar el número máximo de llamadas concurrentes(hilos)
	@Bulkhead(name = "default") // Usamos la configuración por defecto
	public String sampleApiBulkhead() {
		log.info("Sample Api call received");
		return "Sample Api";
	}
	
	// Podemos tener varios métodos(pueden ser públicos o privados) para dar alternativas diferentes en función del tipo de excepción
	// En este caso, como le estamos pasando un argumento de entrada de tipo "Exception", que es el tipo más genérico de una excepción, sólo tenemos un método para dar una respuesta alternativa
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
