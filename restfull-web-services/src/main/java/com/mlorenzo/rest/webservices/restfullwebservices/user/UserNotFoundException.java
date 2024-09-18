package com.mlorenzo.rest.webservices.restfullwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8163629044720791252L;

	public UserNotFoundException(String message) {
		super(message);
	}

	
}
