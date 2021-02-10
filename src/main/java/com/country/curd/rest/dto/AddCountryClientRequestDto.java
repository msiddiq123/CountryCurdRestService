package com.country.curd.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCountryClientRequestDto {

	//@JsonProperty("id")
	//private int id;
	@JsonProperty("countryname")
    private String countryName;
	@JsonProperty("cityname")
    private String cityName;
	@JsonProperty("currency")
    private String currency;
	
    public AddCountryClientRequestDto() {
	
	}

	public AddCountryClientRequestDto(String countryName, String cityName, String currency) {
		super();
		this.countryName = countryName;
		this.cityName = cityName;
		this.currency = currency;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "AddCountryClientRequestDto [countryName=" + countryName + ", cityName=" + cityName + ", currency="
				+ currency + "]";
	}

	
    
	
    
    
}
