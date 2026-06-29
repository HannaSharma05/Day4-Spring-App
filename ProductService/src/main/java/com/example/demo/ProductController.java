package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	@GetMapping("/product")
	public String getProduct() {
		
		return "Laptop Price = Ruppees 65000";
		
	}

}
