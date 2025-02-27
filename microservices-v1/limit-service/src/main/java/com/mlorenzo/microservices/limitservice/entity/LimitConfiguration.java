package com.mlorenzo.microservices.limitservice.entity;

public class LimitConfiguration {
	private Integer minimum;
	private Integer maximum;
	
	
	public LimitConfiguration() {

	}

	public LimitConfiguration(Integer minimum, Integer maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}
	
	public Integer getMinimum() {
		return minimum;
	}
	
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	
	public Integer getMaximum() {
		return maximum;
	}
	
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	
}
