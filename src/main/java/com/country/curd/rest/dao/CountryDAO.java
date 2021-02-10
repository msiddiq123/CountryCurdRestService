package com.country.curd.rest.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.country.curd.rest.model.Country;


@Repository
public class CountryDAO 
{
	
    private static List<Country> list = new ArrayList<Country>();    
   
    static {    
    		Country country1 = new Country(1, "India", "Kolkata", "INR");
    		list.add(country1);
    		Country country2 = new Country(2, "England", "London", "GBP");
    		list.add(country2);
    		Country country3 = new Country(3, "America", "NewYork", "USD");
    		list.add(country3);
    }
    
    
    
    public List<Country> fetchAllCountries() {
        return list;
    }
    
    public Country insertCountry(Country country) {
        list.add(country);
        return country;
    }
    
}
