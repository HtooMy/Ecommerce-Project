package com.dee.Ecommerce.App.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dee.Ecommerce.App.Model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findByUsername(String username);
}
