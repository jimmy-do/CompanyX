package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filters.JwtRequestFilter;
import com.cognixia.jump.service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(myUserDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
			.antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/order/place").permitAll()
			.antMatchers(HttpMethod.GET, "/api/orders").permitAll()
			.antMatchers(HttpMethod.GET, "/api/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PATCH, "/api/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/api/createAdmin").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/products").permitAll()
			.antMatchers(HttpMethod.GET, "/api/product/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/customer/**").permitAll()
			.antMatchers("/v3/api-docs/**").permitAll() 
			.antMatchers("/swagger-resources/**").permitAll()  
			.antMatchers("/swagger-ui/index.html").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/swagger.json").permitAll()
			.antMatchers("/openapi.html").permitAll()
			.antMatchers(HttpMethod.GET, "/api/hello").hasRole("USER")
			.anyRequest().authenticated()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
