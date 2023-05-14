package com.ecommerce.customer.SecurityConfig;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dee.Ecommerce.App.Model.Customer;
import com.dee.Ecommerce.App.Repository.CustomerRepository;


public class CustomerDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByUsername(username);
		if(customer == null) {
			throw new UsernameNotFoundException("Could not find username");
		}
		return new User(customer.getUsername(),
				customer.getPassword(),
				customer.getRoles()
				.stream()
				.map(role -> new  SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList()));
	}

}
