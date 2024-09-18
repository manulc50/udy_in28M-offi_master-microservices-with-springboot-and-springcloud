package com.mlorenzo.microservices.netflixzuulapigateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

// Filtro global que añade al log la ruta de cada petición http que recibe este Api Gateway

@Component
public class ZuulLoggingFilter extends ZuulFilter {
	
	private final static Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);

	// Este método es para indicar si el filtro debe ejecutarse(el método devuelve true) o no(el método devuelve false)
	@Override
	public boolean shouldFilter() {
		return true;
	}

	// Este método es para implementar la lógica del filtro
	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		logger.info("request -> {}, request uri -> {}", request, request.getRequestURI());
		return null;
	}

	// Este método es para indicar el tipo de filtro:
	// Tipo "pre": El filtro se ejecuta antes de relizarse la petición http
	// Tipo "post": El filtro se ejecuta después de relizarse la petición http
	// Tipo "error": El filtro se ejecuta cuando ocurre un error producido por una excepción lanzada durante la ejecución de la petición http
	@Override
	public String filterType() {
		return "pre";
	}

	// Si tenemos muchos filtros, con este método podemos establecer el orden de prioridad de ejecutarse entre ellos
	@Override
	public int filterOrder() {
		return 1;
	}

}
