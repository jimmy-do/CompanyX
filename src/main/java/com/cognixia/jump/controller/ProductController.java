package com.cognixia.jump.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.ProductRepository;
import com.cognixia.jump.service.ProductService;


@RequestMapping("/api")
@RestController
public class ProductController {
	
	@Autowired
	ProductService service;

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		
		return service.getAllProducts();
	}
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable long id) throws ResourceNotFoundException {
		
		return service.getProductById(id);
	}
}