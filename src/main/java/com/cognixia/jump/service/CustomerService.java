package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository repo;
	
	public List<Customer> getAllCustomers() {
		return repo.findAll();
	}
	
	public Customer findByEmail(String email) throws ResourceNotFoundException{
		
		Optional<Customer> foundCustomer = repo.findByEmail(email);
		
		if(!foundCustomer.isPresent()) {
			
			throw new ResourceNotFoundException("Customer with email " + email + " was not found :(");
		}
		return foundCustomer.get();
	}
	
	public Customer getCustomerById(long id) throws ResourceNotFoundException {
		
		Optional<Customer> foundCustomer = repo.findById(id);
		
		if(!foundCustomer.isPresent()) {
			
			throw new ResourceNotFoundException("Customer with id " + id + " was not found :(");
		}
		return foundCustomer.get();
		
	}
	
	public Customer addCustomer(Customer customer) {
		
		customer.setCustomerId(-1L);
		
		Customer saved = repo.save(customer);
		
		return saved;

	}
	
	public Customer updateCustomer(Customer customer) throws ResourceNotFoundException {
		
		if(!repo.existsById(customer.getCustomerId())) {
			
			throw new ResourceNotFoundException("Customer with id " + customer.getCustomerId() + " does not exist, no Customer records were updated");
		}
		
		Customer savedCustomer = repo.save(customer);
		return savedCustomer;
	}
	
	public void deleteCustomerById(Long customerId) throws ResourceNotFoundException{
		
		Optional<Customer> found = repo.findById(customerId);

		if(found.isPresent()) {

			repo.deleteById(customerId);
		} throw new ResourceNotFoundException("Customer by id: " + customerId + " not found.");
	}
	
	// chose to implement deleteCustomerById instead 
//	public Customer deleteCustomer(Customer customer) throws ResourceNotFoundException {
//		
//		Long id = customer.getCustomerId();
//		
//		Customer toDelete = getCustomerById(id);
//		
//		repo.deleteById(id);
//		
//		return toDelete;
//	}
	
	
	public void addProductForCustomer(Product product, long cId) throws ResourceNotFoundException {
		
		Optional<Customer> foundCustomer = repo.findById(cId);
		
		if(!foundCustomer.isPresent()) {
			throw new ResourceNotFoundException("Customer with ID " 
					+ cId + " does not exist, product record NOT created & added :(");
		}
		
		Customer editCustomer = foundCustomer.get();
		
		if(editCustomer.getProduct(product.getProductId()).getProductId() !=  -1L ) {
			
			throw new ResourceNotFoundException("Product with id " + product.getProductId() 
								+ " does not exist, order was not placed!");
		}
		

		editCustomer.addProduct(product);
		repo.save(editCustomer);
	}
	
	public void updateProductForCustomer(Product product, long cId) throws ResourceNotFoundException {
			
		Optional<Customer> foundCustomer = repo.findById(cId);
		
		if(!foundCustomer.isPresent()) {
			
			throw new ResourceNotFoundException("Customer with ID " 
					+ cId + " does not exist, product was not updated!");
		}
		
		Customer editCustomer = foundCustomer.get();
		
		if(editCustomer.getProduct(product.getProductId()).getProductId() !=  -1L ) {
			
			throw new ResourceNotFoundException("Product with id " + product.getProductId() 
								+ " does not exist, order was not placed!");
		}
		
		editCustomer.updateProduct(product);
		repo.save(editCustomer);
	}
}