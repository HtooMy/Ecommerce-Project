package com.dee.Ecommerce.App.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dee.Ecommerce.App.Model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	//@Query("select a from admins where a.username like %?1%")
	Admin findByUsername(String userName);
}
