package com.mlorenzo.rest.webservices.restfullwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	// GET
	// URI - /hello-world
	@GetMapping("/hello-world") // Versión simplificada de la expresión "@RequestMapping(method = RequestMethod.GET, path = "/hello-world")"
	public String helloWorld() {
		return "Hello World";
	}
	
	// GET
	// URI - /hello-world-internationalized-v1
	// Internacionalización - i18n
	// Nota: Si la petición http contiene una cabecera llamada "Accept-Language" con el locale, se usa el archivo de propiedades "messages_" seguido de ese locale para la internacionalización
	//       En caso contrario, se usa el archivo de propiedades "messages"
	@GetMapping("/hello-world-internationalized-v1")
	public String helloWorldInternationalizedV1(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		// Si la propiedad "good.morning.message" no existe en los archivos de propiedades de la internacionalización, se usa el texto "Default Message" como valor por defecto 
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
	}
	
	// GET
	// URI - /hello-world-internationalized-v2
	// Internacionalización - i18n
	// Nota: En vez de pasar un argumento de entrada de tipo Locale anotado con "@RequestHeader" en cada método handler donde queramos implementar la internacionalización, usamos el método "getLocale" de la clase "LocaleContextHolder"
	@GetMapping("/hello-world-internationalized-v2")
	public String helloWorldInternationalizedV2() {
		// El método "getLocale" de la clase "LocaleContextHolder" obtiene el locale a partir del valor de la cabecera "Accept-Language" de la petición http
		// Si la propiedad "good.morning.message" no existe en los archivos de propiedades de la internacionalización, se usa el texto "Default Message" como valor por defecto 
		return messageSource.getMessage("good.morning.message", null, "Default Message", LocaleContextHolder.getLocale());
	}
	
	// GET
	// URI - /hello-world-bean
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	// GET
	// URI - /hello-world/path-variable/{name}
	@GetMapping("/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

}
