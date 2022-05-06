package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	// method is implemented in the AdminController class, not CustomerController class
	Optional<Customer> findByEmail(String email);
}
