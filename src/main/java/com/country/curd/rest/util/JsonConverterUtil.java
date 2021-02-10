package com.country.curd.rest.util;

import java.io.IOException;

import com.country.curd.rest.model.Country;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonConverterUtil {
	
	
	public String convertObjectToJson(Country emp){
		
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		String jsonStr = null;
		try {
			jsonStr = objMapper.writeValueAsString(emp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonStr;
	}

	
	public Country convertJsonToObject(String jsonStr){
		
		Country emp = null;
		try {
			emp = new ObjectMapper().readValue(jsonStr, Country.class);
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return emp;
	}
	
	
}
