package com.dee.Ecommerce.App.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dee.Ecommerce.App.Dto.ProductDto;
import com.dee.Ecommerce.App.Model.Category;
import com.dee.Ecommerce.App.Model.Product;
import com.dee.Ecommerce.App.Service.CategoryService;
import com.dee.Ecommerce.App.Service.ProductService;
import com.dee.Ecommerce.App.Service.Impl.ProductServiceImpl;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/products")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String products(Model model, Principal principal) {
		if(principal == null){
            return "redirect:/login";
        }
		List<ProductDto> productDtoList = productService.findAll();
		List<Category> categories = categoryService.findAllByActivated();
		model.addAttribute("categories", categories);
		model.addAttribute("products", productDtoList);
		model.addAttribute("title", "Manage Product");
		model.addAttribute("size", productDtoList.size());
		return "products";
	}

	@GetMapping("/add-product")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addProductForm(Model model, Principal principal) {
		if(principal == null){
            return "redirect:/login";
        }
		List<Category> categories = categoryService.findAllByActivated();
		model.addAttribute("product", new ProductDto());
		model.addAttribute("categories", categories);
		return "add-product";
	}

	@PostMapping("save-product")
	public String saveProduct(@ModelAttribute("product") ProductDto productDto,
			@RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes attributes) {
		productService.save(imageProduct, productDto);
		boolean yes = productServiceImpl.isSaveSccess();
		if (yes) {
			attributes.addFlashAttribute("success", "Add Successfully");
		} else if (!yes) {
			attributes.addFlashAttribute("error", "Error Occurs");
		}

		return "redirect:/products/0";
	}

	@GetMapping("/update-product/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
		if(principal == null){
            return "redirect:/login";
        }
		model.addAttribute("title", "Update products");
		List<Category> categories = categoryService.findAllByActivated();
		ProductDto productDto = productService.getById(id);
		model.addAttribute("categories", categories);
		model.addAttribute("productDto", productDto);
		return "update-product";
	}

	@PostMapping("/update-product/{id}")
	public String processUpdate(@PathVariable("id") Long id, @ModelAttribute("productDto") ProductDto productDto,
			@RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes attributes) {
		boolean yes = productServiceImpl.isUpdateSuccess();
		productService.update(imageProduct, productDto);
		if (yes) {
			attributes.addFlashAttribute("success", "Update successfully!");
		} else if (!yes) {
			attributes.addFlashAttribute("error", "Failed to update!");
		}

		return "redirect:/products/0";

	}

	@RequestMapping(value = "/enable-product/{id}", method = { RequestMethod.PUT, RequestMethod.GET })
	public String enabledProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			productService.enableById(id);
			attributes.addFlashAttribute("success", "Successfully Enabled !");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "Failed to enabled!");
		}
		return "redirect:/products/" + productServiceImpl.getPageNumber();
	}

	@RequestMapping(value = "/delete-product/{id}", method = { RequestMethod.PUT, RequestMethod.GET })
	public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			productService.deleteById(id);
			attributes.addFlashAttribute("success", "Successfully Deleted !");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "Failed to deleted");
		}
		return "redirect:/products/" + productServiceImpl.getPageNumber();
	}

	@GetMapping("/products/{pageNo}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
		if(principal == null){
            return "redirect:/login";
        }
		Page<Product> products = productService.pageProduct(pageNo);
		model.addAttribute("title", "Manage Product");
		model.addAttribute("size", products.getSize());
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("products", products);
		System.out.println("Controller success");
		return "products";
	}

	@GetMapping("/search-result/{pageNo}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String searchProducts(@PathVariable("pageNo") int pageNo, 
			@RequestParam("keyword") String keyword,
			Model model,
			Principal principal) {
		if(principal == null){
            return "redirect:/login";
        }
		Page<Product> products = productService.searchProduct(pageNo, keyword);
		model.addAttribute("title", "Search Result");
		model.addAttribute("products", products);
		model.addAttribute("size", products.getSize());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", products.getTotalPages());
		return "result-product";
	}
	
	
}
