package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="customers")
public class Customer implements Serializable{

	private static final long serialVersionUID = -1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private Long customerId;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
	private List<Product> products;

	public Customer() {
		this(-1L, "N/A", "N/A", new ArrayList<Product>());
	}

	public Customer(Long customerId, @NotBlank String name, @NotBlank String email,
			List<Product> products) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.products = products;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		product.setCustomer(this);
		products.add(product);
	}
	
	public Product getProduct(Long id) {
		for(int idx = 0; idx < products.size(); idx++) {
			
			if(products.get(idx).getProductId() == id) {
				return products.get(idx);
			}
		}
		
		return new Product();
	}
	
	public void setProducts(List<Product> products) {
		
		for(int acc = 0; acc < products.size(); acc++) {
			addProduct(products.get(acc));
		}	
	}
	
	public void updateProduct(Product product) {
		
		Product toUpdate = getProduct(product.getProductId());
		
		if(toUpdate.getProductId() != -1) {
			toUpdate.setName(product.getName());
			toUpdate.setPrice(product.getPrice());
			toUpdate.setQuantity(product.getQuantity());
			
		}
		
	}
	@Override
	public String toString() {
		return "Customer [id=" + customerId + ", name=" + name + ", email=" + email + ", products=" + products + "]";
	}

}

