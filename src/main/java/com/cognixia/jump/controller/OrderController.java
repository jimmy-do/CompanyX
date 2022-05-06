package com.cognixia.jump.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.CustomerRepository;

@RequestMapping("/api")
@RestController
public class OrderController {

	@Autowired
	private CustomerRepository repo;
	
	// saves customer order to database
	@PostMapping("/order/place")
	public Customer placeOrder(@RequestBody Customer customer, Product product, long customerId) throws ResourceNotFoundException{

		// this method will allow a customer to place an order, but to able to add
		// onto existing order, customer will need to be redirected to methods inside 
		// CustomerController class to continue to add more products to their order
		
		Optional<Customer> foundCustomer = repo.findById(customerId);
		
		if(foundCustomer.isPresent()) {
			
			Customer editCustomer = foundCustomer.get();
			
			if(editCustomer.getProduct(product.getProductId()).getProductId() !=  -1L ) 
				throw new ResourceNotFoundException("Product with id " + product.getProductId() 
									+ " does not exist, order was not placed!");
		}
		
		Customer placeOrder = repo.save(customer);
		return placeOrder;
	}
}
