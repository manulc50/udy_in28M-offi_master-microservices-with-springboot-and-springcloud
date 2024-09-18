package com.mlorenzo.microservices.currencyexchangeservice.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.microservices.currencyexchangeservice.entity.ExchangeValue;
import com.mlorenzo.microservices.currencyexchangeservice.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	private final static Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		// Para ver en el log el id de la traza y el id del span generados por Sleuth
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		
		/*ExchangeValue exchangeValue = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65));
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port"))); */
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		if(exchangeValue == null)
			throw new RuntimeException(String.format("Unable to find data for \"%s\" to \"%s\"", from, to));
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		
		return exchangeValue;
	}

}
