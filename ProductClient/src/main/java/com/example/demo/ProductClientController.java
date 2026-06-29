package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductClientController {
	@Autowired
 private RestTemplate restTemplate;
	public String getProduct(){
		String response = restTemplate.getForObject("http://localhost:8081/product", String.class);
		return response;
	}
	
	
}