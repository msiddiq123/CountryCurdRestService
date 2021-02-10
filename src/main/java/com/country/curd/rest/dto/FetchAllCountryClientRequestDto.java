package com.country.curd.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FetchAllCountryClientRequestDto {

	@JsonProperty("id")
	private int id;
	
	public FetchAllCountryClientRequestDto() {
		
	}

	public FetchAllCountryClientRequestDto(int id) {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EmployeeGetDetailsClientRequestDto [id=" + id + "]";
	}
    	
    
}
