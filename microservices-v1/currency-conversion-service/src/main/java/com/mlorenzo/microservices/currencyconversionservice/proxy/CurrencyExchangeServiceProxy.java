package com.mlorenzo.microservices.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mlorenzo.microservices.currencyconversionservice.model.CurrencyConversion;

// Si el microservicio con el queremos comunicarnos está registrado en un servidor de decubrimiento como Eureka y queremos usar balanceo de carga porque hay varias instancias de ese microservicio, basta con indicar en esta anotación el valor de la propiedad "spring.application.name" de ese microservicio
// Si sólo queremos comunicarnos con una instancia determinada de un microservicio sin usar balanceo de carga, entonces en esta anotación también tenemos que indica la url donde se encuentra esa instancia de ese microservicio
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
	
	// Realiza una petición http de tipo Get a la ruta "/currency-exchange/from/{from}/to/{to}" del host "localhost:8000"(microservicio "currency-exchange-service")
	// Podemos indicar que el tipo esperado es "CurrencyConversion" porque las propiedades de la clase "ExchangeValue" del servicio "currency-exchange-service" coinciden con las propiedades de la clase "CurrencyConversion"
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}
