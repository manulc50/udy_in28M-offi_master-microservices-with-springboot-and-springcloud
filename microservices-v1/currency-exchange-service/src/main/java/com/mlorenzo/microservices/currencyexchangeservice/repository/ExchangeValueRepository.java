package com.mlorenzo.microservices.currencyexchangeservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mlorenzo.microservices.currencyexchangeservice.entity.ExchangeValue;


public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	ExchangeValue findByFromAndTo(String from, String to);

}
