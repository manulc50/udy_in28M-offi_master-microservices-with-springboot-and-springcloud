package com.mlorenzo.rest.webservices.restfullwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

// Las anotaciones "@JsonIgnore" y "@JsonIgnoreProperties" sirven para ignorar propiedades de una clase a la hora de serializar objetos de esa clase a objeto Json y viceversa, es decir, de objeto Json a objetos de esa clase
// 2 opciones equivalentes:
// Opción 1 - Usar la anotación "@JsonIgnore" a nivel de propiedad
// Opción 2 - Usar la anotación "@JsonIgnoreProperties" a nivel de clase

// Nota: Usando alguna de estas 2 anotaciones, conseguimos realizar filtros estáticos. Estáticos porque, para todos los casos, siempre se ignoran las propiedades que se hayan indicado on estas anotaciones
//       Pero, si, por ejemplo, queremos ignorar unas propiedades en un caso determinado y otras en otro caso determinado, tenemos que usar filtros dinámicos(Ver clase controlador "FilteringController")

//@JsonIgnoreProperties(value = {"field1", "field2"})
@JsonFilter("SomeBeanFilter") // Con esta anotación se aplica el filtro "SomeBeanFilter"(ver clase controlador "FilteringController") a los objetos de esta clase
public class SomeBean {

	private String field1;
	
	//@JsonIgnore
	private String field2;
	
	//@JsonIgnore
	private String field3;
	
	public SomeBean(String field1, String field2, String field3) {
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}
	
}
