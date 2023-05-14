package com.dee.Ecommerce.App.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dee.Ecommerce.App.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	public boolean existsByName(String name);
	
	@Query("select c from Category c where c.is_activated = true and c.is_deleted = false")
	List<Category> findAllByActivated();

}
