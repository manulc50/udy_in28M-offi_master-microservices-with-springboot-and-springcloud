package com.mlorenzo.microservices.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

// Filtro global que añade al log la ruta de cada petición http que recibe este Api Gateway

@Component
public class LoggingFilter implements GlobalFilter {
	
	private final static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Path of the request received -> {}", exchange.getRequest().getPath());

		return chain.filter(exchange); // Ejecuta el siguiente filtro de la cadena de filtros
	}

}
