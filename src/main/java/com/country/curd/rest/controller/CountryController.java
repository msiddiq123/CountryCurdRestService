package com.country.curd.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.country.curd.rest.dto.AddCountryClientRequestDto;
import com.country.curd.rest.dto.AddCountryClientResponseDto;
import com.country.curd.rest.dto.CountryDto;
import com.country.curd.rest.dto.FetchCountryByIdClientResponseDto;
import com.country.curd.rest.dto.FetchAllCountryClientResponseDto;
import com.country.curd.rest.model.Country;
import com.country.curd.rest.service.impl.CountryServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "CountryControllerAPI", description = "REST APIs for Country Service...")
@RestController
@RequestMapping(path= "/country")
public class CountryController{
	
    @Autowired
    private CountryServiceImpl countryServiceImpl;
    
    /*
    1)@RequestParam is used to extract query parameters from URL, while @PathVariable extracts values from URI template.          
    2)Spring RESTFul --> @PathVariable, @RequestParam
      Jersey (JAX-RS) --> @PathParam, @QueryParam
    */
    
    
    @ApiOperation(value = "Get list of all countries", response = FetchAllCountryClientResponseDto.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Suceess|OK..."),
            @ApiResponse(code = 401, message = "Not Authorized..."), 
            @ApiResponse(code = 403, message = "Forbidden..."),
            @ApiResponse(code = 404, message = "Not Found...") })
    @GetMapping(path="/getCountries", produces = "application/json")     
    public FetchAllCountryClientResponseDto getCountries(HttpServletRequest httpRequest){
    	
       	FetchAllCountryClientResponseDto cres = new FetchAllCountryClientResponseDto();
    	List<Country> lstCountries =  countryServiceImpl.getAllCountries();
    	
    	List<CountryDto> lstCountryDto = new ArrayList<CountryDto>();
    	for(Country c1 : lstCountries){
    		CountryDto c2 = new CountryDto();
    		BeanUtils.copyProperties(c1, c2); 
    		lstCountryDto.add(c2);
    	}
    	
    	cres.setCountryList(lstCountryDto); 
    	cres.setResponseCode("1");
    	cres.setResponseMessage("Transaction Complete");
    	System.out.println("FetchAllCountryClientResponseDto >> "+cres);
    	
        return cres;
    }
    
    @ApiOperation(value = "Get country details by ID", response = FetchCountryByIdClientResponseDto.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Suceess|OK..."),
            @ApiResponse(code = 401, message = "Not Authorized..."), 
            @ApiResponse(code = 403, message = "Forbidden..."),
            @ApiResponse(code = 404, message = "Not Found...") })
    @GetMapping(path="/getCountryById/{countryId}", produces = "application/json")
    public FetchCountryByIdClientResponseDto getCountryById(HttpServletRequest httpRequest, @PathVariable(name = "countryId") int countryId){
    	
    	FetchCountryByIdClientResponseDto cres = new FetchCountryByIdClientResponseDto();
       	Country country = countryServiceImpl.getCountryById(countryId);	    	
    	cres.setId(country.getId());
    	cres.setCountryName(country.getCountryName());
    	cres.setCityName(country.getCityName());
    	cres.setCurrency(country.getCurrency());
    	cres.setResponseCode("1");
    	cres.setResponseMessage("Transaction Complete");
    	System.out.println("FetchCountryByIdClientResponse >> "+cres);
    	
        return cres;
    }
    
    
    @ApiOperation(value = "Create new country", response = AddCountryClientResponseDto.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Suceess|OK..."),
            @ApiResponse(code = 401, message = "Not Authorized..."), 
            @ApiResponse(code = 403, message = "Forbidden..."),
            @ApiResponse(code = 404, message = "Not Found...") })
    @SuppressWarnings("unused")
	@PostMapping(path="/createCountry", consumes = "application/json", produces = "application/json")
    public AddCountryClientResponseDto createCountry(HttpServletRequest httpRequest, @RequestBody AddCountryClientRequestDto creq){
    	    	
    	AddCountryClientResponseDto cres = new AddCountryClientResponseDto(); 	
    	Country country = null;
    	if(creq != null){
    		country = countryServiceImpl.addCountry(creq); 		
    		BeanUtils.copyProperties(country,cres);
        	cres.setResponseCode("1");
        	cres.setResponseMessage("Transaction Complete");
        	System.out.format("Added Country with Id : %s , Name-City : %s\n", country.getId(), country.getCountryName()+"-"+country.getCityName());
        	System.out.println("AddCountryClientResponse >> "+cres);
    	}else{
    		cres.setResponseCode("0");
        	cres.setResponseMessage("Transaction Not Complete"); 
    		System.out.println("Request Body empty, could not add employee...");
    		System.out.println("AddCountryClientResponse >> "+cres);
    	}
    	
    	return cres;
    }
    
    
    
}
