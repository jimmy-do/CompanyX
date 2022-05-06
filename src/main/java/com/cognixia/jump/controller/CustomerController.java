package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.service.CustomerService;

@RequestMapping("/api")
@RestController
public class CustomerController {
	
	@Autowired
	CustomerService service;
	
	@PostMapping("/customer/add/product/{cId}")
	public void addProductForCustomer(@RequestBody Product product, @PathVariable long cId) throws ResourceNotFoundException, Exception{
		
		service.addProductForCustomer(product, cId);
	}
	
	@PatchMapping("/customer/product/update/{cId}")
	public void updateProductForCustomer(@RequestBody Product product, @PathVariable long cId) throws ResourceNotFoundException {
		
		service.updateProductForCustomer(product, cId);
	}
	
	
}
