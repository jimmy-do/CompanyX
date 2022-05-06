package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.CustomerRepository;
import com.cognixia.jump.repository.ProductRepository;
import com.cognixia.jump.service.CustomerService;
import com.cognixia.jump.service.ProductService;

@RequestMapping("/api/admin")
@RestController
public class AdminController {
	
	@Autowired
	CustomerService service;
	
	@Autowired
	ProductService prodService;
	
	@GetMapping("/customers/findall")
	public List<Customer> findAllCustomers(){
		
		return service.getAllCustomers();
	}
	
	@GetMapping("/customer/findbyemail/{email}")
	public Customer getCustomerByEmail(@PathVariable String email) throws ResourceNotFoundException {
		
		return service.findByEmail(email);
	}
	
	@GetMapping("/findallcustomers")
	public List<Customer> getAllCustomers() {
		
		return service.getAllCustomers();
	}
	
	@GetMapping("/customer/find/{id}")
	public Customer getCustomer(@PathVariable long id) throws ResourceNotFoundException {
		
		return service.getCustomerById(id);
	}
	
	@PostMapping("/add/customer")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		
		Customer added = service.addCustomer(customer);
		
		return new ResponseEntity<>(added, HttpStatus.CREATED);
	}
	
	@PostMapping("/add/product")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		
		Product added = prodService.addProduct(product);
		
		return new ResponseEntity<>(added, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/product")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) throws ResourceNotFoundException {
		
		Product updated = prodService.updateProduct(product);
		
		return new ResponseEntity<>(updated, HttpStatus.OK);	
	}
	
	// chose to implement deleteProductById instead via service layer
//	@DeleteMapping("/delete/product")
//	public ResponseEntity<Product> removeProduct(@RequestBody Product product) throws ResourceNotFoundException {
//		
//		Product deleted = prodService.deleteProduct(product);
//		
//		return new ResponseEntity<>(deleted, HttpStatus.OK);
//	}

	// chose to implement deleteCustomerById instead via service layer
	
//	@DeleteMapping("/delete/customer")
//	public ResponseEntity<Customer> removeCustomer(@RequestBody Customer customer) throws ResourceNotFoundException {
//		
//		Customer deleted = service.deleteCustomer(customer);
//		
//		return new ResponseEntity<>(deleted, HttpStatus.OK);
//	}	
	
	@PatchMapping("/update/customer")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) throws ResourceNotFoundException {
		
		Customer updated = service.updateCustomer(customer);
		
		return new ResponseEntity<>(updated, HttpStatus.OK);	
	}
	
	@DeleteMapping("/delete/product/{id}")
	public ResponseEntity<Object> deleteProductById(@PathVariable Long id) throws ResourceNotFoundException {
		
		prodService.deleteProductById(id);
		return new ResponseEntity<>("Customer by id: " + id + "deleted", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/customer/{id}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable Long customerId) throws ResourceNotFoundException {
		
		service.deleteCustomerById(customerId);
		return new ResponseEntity<>("Customer by id: " + customerId + "deleted", HttpStatus.OK);
	}
}