package com.product.service;

import java.util.List;

import com.product.dto.ProductDto;

public interface ProductService {

	ProductDto createProduct(ProductDto productDto);

	ProductDto getProductById(Long id);

	ProductDto getProductByName(String name);

	List<ProductDto> getAllProduct();

	ProductDto updateProduct(ProductDto productDto, Long id);

	void deleteProduct(Long id);

}
