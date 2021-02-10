package com.country.curd.rest.service;

import java.util.List;

import com.country.curd.rest.dto.AddCountryClientRequestDto;
import com.country.curd.rest.model.Country;


public interface CountryService {
	
	
	public List<Country> getAllCountries();
	
	public Country getCountryById(int countryId);
	
	public Country addCountry(AddCountryClientRequestDto creq);

}
