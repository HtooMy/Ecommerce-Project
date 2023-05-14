package com.dee.Ecommerce.App.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.dee.Ecommerce.App.Dto.ProductDto;
import com.dee.Ecommerce.App.Model.Product;

public interface ProductService {

	List<ProductDto> findAll();
	Product save(MultipartFile imageProduct, ProductDto productDto);
	void deleteById(Long id);
	void enableById(Long id);
	Product update(MultipartFile imageProduct, ProductDto productDto);
	ProductDto getById(Long id);
	Page<Product> pageProduct(int pageNumber);
	Page<Product> searchProduct(int pageNo, String keyword);
}
