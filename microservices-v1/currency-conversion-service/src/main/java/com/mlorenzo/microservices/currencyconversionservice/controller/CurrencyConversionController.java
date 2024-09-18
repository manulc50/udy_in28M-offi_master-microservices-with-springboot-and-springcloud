package com.mlorenzo.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mlorenzo.microservices.currencyconversionservice.model.CurrencyConversion;
import com.mlorenzo.microservices.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	private final static Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// Usando RestTemplate
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable(name = "quantity") BigDecimal qty) {
		
		// Para ver en el log el id de la traza y el id del span generados por Sleuth
		logger.info("Sleuth");
		
		//CurrencyConversion currencyConversion = new CurrencyConversion(1L, from, to, qty, BigDecimal.ONE, qty, 0);
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		// Realiza una petición http de tipo Get a la ruta "/currency-exchange/from/{from}/to/{to}" del host "localhost:8000"(una instancia determinada del microservicio "currency-exchange-service")
		// Podemos indicar que el tipo esperado es "CurrencyConversion" porque las propiedades de la clase "ExchangeValue" del servicio "currency-exchange-service" coinciden con las propiedades de la clase "CurrencyConversion"
		/*ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversion.class,
				uriVariables);*/
		
		// Realiza una petición http de tipo Get a la ruta "/currency-exchange/from/{from}/to/{to}" del microservicio "currency-exchange-service"(si hay varias instancias del microservicio, hace balanceo de carga)
		// Podemos indicar que el tipo esperado es "CurrencyConversion" porque las propiedades de la clase "ExchangeValue" del servicio "currency-exchange-service" coinciden con las propiedades de la clase "CurrencyConversion"
		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://currency-exchange-service/currency-exchange/from/{from}/to/{to}",
				CurrencyConversion.class,
				uriVariables);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		currencyConversion.setQuantity(qty);
		currencyConversion.setTotalCalculatedAmount(qty.multiply(currencyConversion.getConversionMultiple()));
		currencyConversion.setPort(currencyConversion.getPort());
		
		return currencyConversion;
	}
	
	// Usando un cliente o proxy Feign
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable(name = "quantity") BigDecimal qty) {
		
		// Para ver en el log el id de la traza y el id del span generados por Sleuth
		logger.info("Sleuth");
		
		CurrencyConversion currencyConversion = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		
		currencyConversion.setQuantity(qty);
		currencyConversion.setTotalCalculatedAmount(qty.multiply(currencyConversion.getConversionMultiple()));
		currencyConversion.setPort(currencyConversion.getPort());
		
		return currencyConversion;
	
	}

}
