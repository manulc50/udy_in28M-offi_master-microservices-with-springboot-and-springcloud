package com.mlorenzo.rest.webservices.restfullwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	// Opción 1 - Usando la URI
	// Llamado URI "Versioning"
	// Usado por Twitter
	@GetMapping("v1/person")
	public PersonV1 retrievePersonV1() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("v2/person")
	public PersonV2 retrievePersonV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	// Opción 2 - Usando un parámetro de la URI
	// Llamado "Request Parameter Versioning"
	// Usado por Amazon
	@GetMapping(value = "person/param", params = "version=1")
	public PersonV1 retrievePersonParamV1() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(value = "person/param", params = "version=2")
	public PersonV2 retrievePersonParamV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	// Opción 3 - Usando una cabecera
	// Llamado "(Custom) Header Versioning"
	// Usado por Microsoft
	@GetMapping(value = "person/header", headers = "X-API-VERSION=1")
	public PersonV1 retrievePersonHeaderV1() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(value = "person/header", headers = "X-API-VERSION=2")
	public PersonV2 retrievePersonHeaderV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	// Opción 4- Usando la cabecera "Accept"
	// Llamado "Accept Header Versioning" o "Content Negotiation Versioning" o "MIME Type Versioning" o "Media Type Versioning"
	// Usado por GitHub
	@GetMapping(value = "person/produces", produces = "application/vnd.company.app-v1+json")
	public PersonV1 retrievePersonProducesV1() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(value = "person/produces", produces = "application/vnd.company.app-v2+json")
	public PersonV2 retrievePersonProducesV2() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
}
