package com.dee.Ecommerce.App.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dee.Ecommerce.App.Model.Category;
import com.dee.Ecommerce.App.Service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String categories(Model model, Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		List<Category> categoryList = categoryService.findAll();
		model.addAttribute("categories", categoryList);
		model.addAttribute("title", "category");
		model.addAttribute("size", categoryList.size());
		model.addAttribute("categoryNew", new Category());
		return "categories";
	}

	@PostMapping("/add-category")
	public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
		boolean yes = categoryService.checkCategory(category.getName());
		if (yes) {
			attributes.addAttribute("failed", "Failed to add due to Category's name Duplication");
			attributes.addFlashAttribute("failed", "Failed to add due to Category's name Duplication");
		} else if(!yes){
			categoryService.save(category);
			attributes.addFlashAttribute("success", "Added Successfully");
		} else if(category.getName().equals(null)) {
			attributes.addFlashAttribute("nullError", "No data is entered");
		}
		return "redirect:/categories";
	}
	
	@RequestMapping("/findById") 
	@ResponseBody
	public Optional<Category> findById(Long id) {
		return categoryService.getById(id);
	}
	
	@PostMapping("/update-category")
	public String update(Category category, RedirectAttributes attributes) {
		boolean yes = categoryService.checkCategory(category.getName());
		if (yes) {
			attributes.addAttribute("failed", "Failed to update due to Category's name Duplication");
			attributes.addFlashAttribute("failed", "Failed to update due to Category's name Duplication");
		} else if(!yes){
			categoryService.update(category);
			attributes.addFlashAttribute("success", "Update Successfully");
		} else if(category.getName().equals(null)) {
			attributes.addFlashAttribute("nullError", "No data is entered");
		}
		return "redirect:/categories";
	}
	
	@RequestMapping(value="delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
	public String delete(Long id, RedirectAttributes attributes) {
		categoryService.deleteById(id);
		attributes.addFlashAttribute("success", "Successfully Deleted");
		return "redirect:/categories";
	}
	
	@RequestMapping(value="enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
	public String enable(Long id, RedirectAttributes attributes) {
		categoryService.enabledById(id);
		attributes.addFlashAttribute("success", "Successfully Enabled");
		return "redirect:/categories";
	}
}
