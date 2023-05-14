package com.dee.Ecommerce.App.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dee.Ecommerce.App.Model.Category;
import com.dee.Ecommerce.App.Repository.CategoryRepository;
import com.dee.Ecommerce.App.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) {
		try {
			Category categorySave = new Category(category.getName());
			return categoryRepository.save(categorySave);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Optional<Category> getById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category update(Category category) {
		Category categoryUpdate;
		try {
			categoryUpdate = categoryRepository.findById(category.getId()).get();
			categoryUpdate.setId(category.getId());
			categoryUpdate.setName(category.getName());
			categoryUpdate.setIs_activated(category.isIs_activated());
			categoryUpdate.setIs_deleted(category.isIs_deleted());
			return categoryRepository.save(categoryUpdate);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteById(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
	    if (optionalCategory.isPresent()) {
	        Category category = optionalCategory.get();
	        category.setIs_activated(false);
	        category.setIs_deleted(true);
	        categoryRepository.save(category);
	    }
	}

	@Override
	public void enabledById(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			category.setIs_activated(true);
			category.setIs_deleted(false);
			categoryRepository.save(category);
			}
	}

	@Override
	public boolean checkCategory(String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public List<Category> findAllByActivated() {
		return categoryRepository.findAllByActivated();
	}

	
	

}
