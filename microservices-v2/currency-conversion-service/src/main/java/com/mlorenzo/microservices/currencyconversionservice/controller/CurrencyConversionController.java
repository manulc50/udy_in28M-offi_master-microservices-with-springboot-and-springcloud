package com.mlorenzo.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mlorenzo.microservices.currencyconversionservice.model.CurrencyConversion;
import com.mlorenzo.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// Usando RestTemplate
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable(name = "quantity") BigDecimal qty) {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		// Realiza una petición http de tipo Get a la ruta "/currency-exchange/from/{from}/to/{to}" del host "localhost:8000"(una instancia determinada del microservicio "currency-exchange")
		// Podemos indicar que el tipo esperado es "CurrencyConversion" porque las propiedades de la clase "CurrencyExchange" del servicio "currency-exchange" coinciden con las propiedades de la clase "CurrencyConversion"
		/*ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversion.class,
				uriVariables);*/
		
		// Realiza una petición http de tipo Get a la ruta "/currency-exchange/from/{from}/to/{to}" del microservicio "currency-exchange"(si hay varias instancias del microservicio, hace balanceo de carga)
		// Podemos indicar que el tipo esperado es "CurrencyConversion" porque las propiedades de la clase "CurrencyExchange" del servicio "currency-exchange" coinciden con las propiedades de la clase "CurrencyConversion"
		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://currency-exchange/currency-exchange/from/{from}/to/{to}",
				CurrencyConversion.class,
				uriVariables);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		currencyConversion.setQuantity(qty);
		currencyConversion.setTotalCalculatedAmount(qty.multiply(currencyConversion.getConversionMultiple()));
		currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " rest templete");
		
		return currencyConversion;
	}
	
	// Usando un cliente o proxy Feign
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable(name = "quantity") BigDecimal qty) {
		
		CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
		
		currencyConversion.setQuantity(qty);
		currencyConversion.setTotalCalculatedAmount(qty.multiply(currencyConversion.getConversionMultiple()));
		currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " feign");
		
		return currencyConversion;
	
	}

}
