package com.mlorenzo.microservices.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Clase de configuración de Spring para personalizar rutas del Api Gateway
// Otra opción es usar el archivo de configuraciones "application.properties" para personalizar las rutas

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				// Ruta de prueba. Esta ruta no tiene nada que ver con nuestros microservicios
				// La ruta "/get" a este Api Gateway se enruta a la url "http://httpbin.org:80", pero antes de realizar ese enrutamiento, se añade la cabecera "MyHeader" y el parámetro "MyParam" mediante un filtro
				.route(predicate -> predicate.path("/get")
						.filters(filter -> filter
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("MyParam", "MyValue"))
						.uri("http://httpbin.org:80"))
				// Cualquier ruta a este Api Gateway que coincida con la expresión "/currency-exchange/**", se enruta al microservicio con nombre "currency-exchange" usando el balanceador de carga
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange")) // Con "lb" habilitamos el uso del balanceador de carga en caso de que exista más de una instancia del microservicio "currency-exchange" registrada en el servidor de descubrimiento Eureka
				// Cualquier ruta a este Api Gateway que coincida con la expresión "/currency-conversion/**", se enruta al microservicio con nombre "currency-conversion" usando el balanceador de carga
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion")) // Con "lb" habilitamos el uso del balanceador de carga en caso de que exista más de una instancia del microservicio "currency-conversion" registrada en el servidor de descubrimiento Eureka
				// Cualquier ruta a este Api Gateway que coincida con la expresión "/currency-conversion-feign/**", se enruta al microservicio con nombre "currency-conversion" usando el balanceador de carga
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion")) // Con "lb" habilitamos el uso del balanceador de carga en caso de que exista más de una instancia del microservicio "currency-conversion" registrada en el servidor de descubrimiento Eureka
				// Cualquier ruta a este Api Gateway que coincida con la expresión "/currency-conversion-new/", se enruta al microservicio con nombre "currency-conversion" usando el balanceador de carga
				.route(p -> p.path("/currency-conversion-new/**")
						// Usamos este filtro para rescribir las rutas, que coinciden con la expresión "/currency-conversion-new/**" y no existen en el microservicio "currency-conversion", a rutas que coinciden con la expresión "/currency-conversion-feign/**" y que sí existen en ese microservicio
						// Con "(?<segment>.*)", hacemos referencia a todas las subrutas que haya a continuación de "/currency-conversion-new/"
						// Con "${segment}", usamos las subrutas obtenidas por "(?<segment>.*)"
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion")) // Con "lb" habilitamos el uso del balanceador de carga en caso de que exista más de una instancia del microservicio "currency-conversion" registrada en el servidor de descubrimiento Eureka
				.build();
	}
}
