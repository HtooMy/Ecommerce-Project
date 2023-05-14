package com.dee.Ecommerce.App.Service;

import java.util.List;
import java.util.Optional;

import com.dee.Ecommerce.App.Model.Category;

public interface CategoryService {

	List<Category> findAll();
	Category save(Category category);
	Optional<Category> getById(Long id);
	Category update(Category category);
	void deleteById(Long id);
	void enabledById(Long id);
	public boolean checkCategory(String name);
	List<Category> findAllByActivated();
}
