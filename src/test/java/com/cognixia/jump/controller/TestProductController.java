package com.cognixia.jump.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.ProductRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest(controllers=ProductController.class) // pointing it to our productController class

@ActiveProfiles("test")
class TestProductController {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Autowired
	private MockMvc mockMvc;
	
	// Mockito 
	@MockBean
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MyUserDetailsService myUserDetails;
	
	private Product productList;
	
//	@BeforeEach
//	void setUp() {
//		this.productList = new ArrayList<>();
//		this.productList.add(new Product(-1L,"Something","49.99","1"));
//		this.productList.add(new Product(-1L,"SomethingElese","49.99","1"));
//	}
	
	@Test
	public void getProductsTest() {
		when(productRepository.findAll()).thenReturn(Stream.of(new Product(-1L,"Radeon","49.99","1")).collect(Collectors.toList()));
		assertEquals(4, productService.getAllProducts().size());
	}
	
	@Test
	void shouldFetchProductById(Long id) throws Exception{

		given(productService.getProductById(id)).willReturn(productList);
		
		this.mockMvc.perform(get("/api/products/{id}"))
//		/api/products
//		/product/{id}
			.andExpect(status().isOk());
//			.andExpect(jsonPath("$.size()", is(productList.size())));
	}
}
