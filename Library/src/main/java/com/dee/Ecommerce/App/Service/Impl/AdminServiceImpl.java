package com.dee.Ecommerce.App.Service.Impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dee.Ecommerce.App.Dto.AdminDto;
import com.dee.Ecommerce.App.Model.Admin;
import com.dee.Ecommerce.App.Repository.AdminRepository;
import com.dee.Ecommerce.App.Repository.RoleRepository;
import com.dee.Ecommerce.App.Service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Admin findByUsername(String username) {
		System.out.println("AdminServiceImpl Admin find by username is " + adminRepository.findByUsername(username));
		return adminRepository.findByUsername(username);
	}

	@Override
	public Admin save(AdminDto adminDto) {
		Admin admin = new Admin();
		admin.setFirstName(adminDto.getFirstName());
		admin.setLastName(adminDto.getLastName());
		admin.setUsername(adminDto.getUsername());
		admin.setPassword(adminDto.getPassword());
		admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
		return adminRepository.save(admin);
	}

}
