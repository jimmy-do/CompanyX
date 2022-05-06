package com.cognixia.jump.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.ProductRepository;


@Service
public class ProductService {
	
	@Autowired
	ProductRepository repo;
	
	public List<Product> getAllProducts() {
		
		return repo.findAll();
	}
	
	public Product getProductById(long id) throws ResourceNotFoundException {
		
		Optional<Product> foundProduct = repo.findById(id);
		
		if(!foundProduct.isPresent()) {
			
			throw new ResourceNotFoundException("Product with id " + id + " was not found :(");
		}
		return foundProduct.get();
	}
	
	public Product addProduct(Product product) {
		
		product.setProductId(-1L);
		
		Product saved = repo.save(product);
		
		return saved;

	}
	
	public Product updateProduct(Product product) throws ResourceNotFoundException {
		
		if(!repo.existsById(product.getProductId())) {
			
			throw new ResourceNotFoundException("Product with id " + product.getProductId() + " does not exist, no Product records were updated");
		}
		
		Product savedProduct = repo.save(product);
		return savedProduct;
	}
	
	// chose to implement deleteProductById instead
//	public Product deleteProduct(Product product) throws ResourceNotFoundException {
//		
//		Long id = product.getProductId();
//		
//		Product toDelete = getProductById(id);
//		
//		repo.deleteById(id);
//		
//		return toDelete;
//	}
	
	public void deleteProductById(Long id) throws ResourceNotFoundException{
		
		Optional<Product> found = repo.findById(id);

		if(found.isPresent()) {

			repo.deleteById(id);
		} throw new ResourceNotFoundException("Product by id: " + id + " not found.");
	}
}
