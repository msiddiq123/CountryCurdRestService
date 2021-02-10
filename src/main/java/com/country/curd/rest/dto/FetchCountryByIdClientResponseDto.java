package com.country.curd.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FetchCountryByIdClientResponseDto extends StatusClientResponseDto{

	@JsonProperty("id")
	private int id;
	@JsonProperty("countryname")
    private String countryName;
	@JsonProperty("cityname")
    private String cityName;
	@JsonProperty("currency")
    private String currency;
    
	public FetchCountryByIdClientResponseDto() {
		
	}

	public FetchCountryByIdClientResponseDto(int id, String countryName, String cityName, String currency) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.cityName = cityName;
		this.currency = currency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "FetchCountryByIdClientResponse [id=" + id + ", countryName=" + countryName + ", cityName=" + cityName
				+ ", currency=" + currency + "]";
	}
	
	
    
    
}
