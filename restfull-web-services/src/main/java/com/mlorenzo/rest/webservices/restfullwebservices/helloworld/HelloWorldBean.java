package com.mlorenzo.rest.webservices.restfullwebservices.helloworld;

public class HelloWorldBean {
	
	private String message;
	
	public HelloWorldBean() {
		
	}
	
	public HelloWorldBean(String message) {
		this.message = message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return String.format("Message: %s", this.message);
	}

}
