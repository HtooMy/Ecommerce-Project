package com.ecommerce.admin.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dee.Ecommerce.App.Model.Admin;
import com.dee.Ecommerce.App.Repository.AdminRepository;

public class AdminDetailsService implements UserDetailsService {
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByUsername(username);
		System.out.println("Admin find by username is " + adminRepository.findByUsername(username));
		if(admin == null) {
			System.out.println("Email is not found and userDetailsServiceImpl NOT Success");
			throw new UsernameNotFoundException("User is not available");
			
		} else {
			System.out.println("Admin is " + new AdminDetails(admin) + admin.getUsername() + admin.getPassword());
			System.out.println("You entered password 123@321 and it's encrypted version is " + passwordEncoder.encode("123@321"));
			return new AdminDetails(admin);
		}
	}
}