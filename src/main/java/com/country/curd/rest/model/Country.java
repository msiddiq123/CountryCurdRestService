package com.country.curd.rest.model;

public class Country {
   
	
    private int id;
    private String countryName;
    private String cityName;
    private String currency;
    
	public Country() {
		
	}

	public Country(int id, String countryName, String cityName, String currency) {
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
		return "Country [id=" + id + ", countryName=" + countryName + ", cityName=" + cityName + ", currency="
				+ currency + "]";
	}
    
	
    
    

  }
