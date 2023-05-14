package com.dee.Ecommerce.App.Service.Impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dee.Ecommerce.App.Dto.ProductDto;
import com.dee.Ecommerce.App.Model.Product;
import com.dee.Ecommerce.App.Repository.ProductRepository;
import com.dee.Ecommerce.App.Service.ProductService;
import com.dee.Ecommerce.App.utils.ImageUpload;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageUpload imageUpload;

	private boolean saveSccess;
	private boolean updateSuccess;
	private int pageNumber;

	@Override
	public List<ProductDto> findAll() {
		List<ProductDto> productDtoList = new ArrayList<>();
		List<Product> products = productRepository.findAll();
		for (Product product : products) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setName(product.getName());
			productDto.setDescription(product.getDescription());
			productDto.setCostPrice(product.getCostPrice());
			productDto.setSalePrice(product.getSalePrice());
			productDto.setCurrentQuantity(product.getCurrentQuantity());
			productDto.setCategory(product.getCategory());
			productDto.setId(product.getId());
			productDto.setImage(product.getImage());
			productDto.setIs_activated(product.isIs_activated());
			productDto.setIs_deleted(product.isIs_delected());
			productDtoList.add(productDto);
		}
		return productDtoList;
	}

	@Override
	public Product save(MultipartFile imageProduct, ProductDto productDto) {
		try {
			Product product = new Product();
			if (imageProduct == null) {
				product.setImage(null);
			} else {
				if (imageUpload.uploadImage(imageProduct)) {
					System.out.println("Upload successfully");
				}
				product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
			}
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setCategory(productDto.getCategory());
			product.setCostPrice(productDto.getCostPrice());
			product.setCurrentQuantity(productDto.getCurrentQuantity());
			product.setIs_activated(true);
			product.setIs_delected(false);
			saveSccess = true;
			return productRepository.save(product);
		} catch (Exception e) {
			saveSccess = false;
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Product update(MultipartFile imageProduct, ProductDto productDto) {
		try {
			Product product = productRepository.findById(productDto.getId()).get();
			if (imageProduct == null) {
				product.setImage(product.getImage());
				System.out.println("If");
			} else {
				System.out.println("Else");
				if (imageUpload.checkExisted(imageProduct) == false) {
					imageUpload.uploadImage(imageProduct);
				}
				product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
			}
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setSalePrice(productDto.getSalePrice());
			product.setCostPrice(productDto.getCostPrice());
			product.setCurrentQuantity(productDto.getCurrentQuantity());
			product.setCategory(productDto.getCategory());
			updateSuccess = true;
			return productRepository.save(product);
		} catch (Exception e) {
			updateSuccess = false;
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void deleteById(Long id) {
		Product product = productRepository.findById(id).get();
		product.setIs_activated(false);
		product.setIs_delected(true);
		productRepository.save(product);

	}

	@Override
	public void enableById(Long id) {
		Product product = productRepository.findById(id).get();
		product.setIs_activated(true);
		product.setIs_delected(false);
		productRepository.save(product);

	}

	@Override
	public ProductDto getById(Long id) {
		Product product = productRepository.findById(id).get();
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setCurrentQuantity(product.getCurrentQuantity());
		productDto.setCategory(product.getCategory());
		productDto.setSalePrice(product.getSalePrice());
		productDto.setCostPrice(product.getCostPrice());
		productDto.setImage(product.getImage());
		productDto.setIs_deleted(product.isIs_delected());
		productDto.setIs_activated(product.isIs_activated());
		return productDto;
	}

	public boolean isSaveSccess() {
		return saveSccess;
	}

	public void setSaveSccess(boolean saveSccess) {
		this.saveSccess = saveSccess;
	}

	public boolean isUpdateSuccess() {
		return updateSuccess;
	}

	public void setUpdateSuccess(boolean updateSuccess) {
		this.updateSuccess = updateSuccess;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public Page<Product> pageProduct(int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, 5);
		Page<Product> productPages = productRepository.pageProduct(pageable);
		pageNumber = pageable.getPageNumber();
		return productPages;
	}

	@Override
    public Page<Product> searchProduct(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Product> products = productRepository.searchProducts(keyword, pageable);
        return products;
    }
	
	private Page toPage(List<ProductDto> list , Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

	private List<ProductDto> transfer(List<Product> products){
		List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setIs_deleted(product.isIs_delected());
            productDto.setIs_activated(product.isIs_activated());
            productDtoList.add(productDto);
        }
        return productDtoList;
	}
	
}
