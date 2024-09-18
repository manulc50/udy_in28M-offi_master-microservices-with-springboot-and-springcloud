package com.mlorenzo.rest.webservices.restfullwebservices.exception;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.mlorenzo.rest.webservices.restfullwebservices.user.UserNotFoundException;


@RestControllerAdvice // Esta anotación es una combinación de las anotaciones "@ControllerAdvice" y "@ResponseBody"
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		// Usamos false en el método "getDescription" de la petición http para no incluir información del cliente por temas de seguridad
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
		return new ExceptionResponse(new Date(), "Validation Failed", ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ExceptionResponse handleAllExceptions(Exception ex, WebRequest request) {
		// Usamos false en el método "getDescription" de la petición http para no incluir información del cliente por temas de seguridad
		return new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	}

}
