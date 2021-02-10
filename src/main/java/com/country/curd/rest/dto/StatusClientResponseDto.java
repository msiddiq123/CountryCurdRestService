package com.country.curd.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class StatusClientResponseDto {
	
	@JsonProperty("responsecode")
	String responseCode;
	@JsonProperty("responsemessage")
	String responseMessage;
	
	public StatusClientResponseDto() {
	
	}

	public StatusClientResponseDto(String responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "StatusClientResponseDto [responseCode=" + responseCode + ", responseMessage=" + responseMessage + "]";
	}
	
	
	

}
