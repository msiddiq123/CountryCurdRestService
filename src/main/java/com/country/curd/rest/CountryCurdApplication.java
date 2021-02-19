package com.country.curd.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 

@SpringBootApplication 
public class CountryCurdApplication {

	
	//mvn clean install -Dmaven.test.skip=true
	//mvn clean deploy -Psnapshot OR mvn clean install deploy -Psnapshot -Dmaven.test.skip=true (Incase the artifact is uploaded to Nexus repo doe snapshots)
	//java -jar target/CountryCurdRestService-0.0.1-SNAPSHOT.jar
	//http://localhost:8081/countryservice/swagger-ui.html
	//http://localhost:8081/countryservice/v2/api-docs
    public static void main(String[] args) {
    	
        SpringApplication.run(CountryCurdApplication.class, args);
    }
    
}
