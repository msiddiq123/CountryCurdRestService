package com.country.curd.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.country.curd.rest.dao.CountryDAO;
import com.country.curd.rest.dto.AddCountryClientRequestDto;
import com.country.curd.rest.model.Country;
import com.country.curd.rest.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService{
	
	 @Autowired
	 private CountryDAO countryDao;
	
	
	public List<Country> getAllCountries(){
		List<Country> lstCountries = countryDao.fetchAllCountries();
    	return lstCountries;
	}
	
	
	public Country getCountryById(int countryId){
		List<Country> lstCountries = countryDao.fetchAllCountries();
		for(Country c : lstCountries){
			int cId = c.getId();
			if(cId == countryId){
				return c;
			}
		}
		return null;
	}
	
	
	public Country addCountry(AddCountryClientRequestDto creq){
		int countryId = countryDao.fetchAllCountries().size() + 1;
		
		Country country = new Country();
		country.setId(countryId);
		country.setCountryName(creq.getCountryName());
		country.setCityName(creq.getCityName());
		country.setCurrency(creq.getCurrency());
		/*----OR (use BeanCopy utility) ----	
		BeanUtils.copyProperties(creq, country); */ 
    	
		//----Prior to calling DAO class, do Some Validation for mandatory attributes----
		
		country = countryDao.insertCountry(country);
		return country;		
	}
	

}
