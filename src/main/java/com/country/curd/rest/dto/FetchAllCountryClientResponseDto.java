package com.country.curd.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown=true)
public class FetchAllCountryClientResponseDto extends StatusClientResponseDto{
			
	@JsonProperty("countrylist")
    private List<CountryDto> countryList;
    

    public FetchAllCountryClientResponseDto() {
		super();
	}

    public FetchAllCountryClientResponseDto(List<CountryDto> countryList) {
		super();
		this.countryList = countryList;
	}


	public List<CountryDto> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryDto> countryList) {
		this.countryList = countryList;
	}

	@Override
	public String toString() {
		return "FetchAllCountryClientResponseDto [countryList=" + countryList + "]";
	}

	
	

	
    
    
}
