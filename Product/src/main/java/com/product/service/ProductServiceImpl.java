package com.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.*;

import org.springframework.stereotype.Service;

import com.product.dto.ProductDto;
import com.product.entity.Product;
import com.product.exception.ResourceNotFoundException;
import com.product.mapper.ProductMapper;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		Product product = ProductMapper.mapToProduct(productDto);
		LocalDateTime now = LocalDateTime.now();
	    product.setCreatedAt(now);
	    product.setUpdatedAt(now);
		Product save = productRepository.save(product);
		return ProductMapper.mapToProductDto(save);
	}

	@Override
	public ProductDto getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product does not exists"));
		return ProductMapper.mapToProductDto(product);
	}

	@Override
	public ProductDto getProductByName(String name) {
		Product product = productRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Product does not exists"));
		return ProductMapper.mapToProductDto(product);
	}

	@Override
	public List<ProductDto> getAllProduct() {
		List<Product> products = productRepository.findAll();
		return products.stream().map((product) -> ProductMapper.mapToProductDto(product)).collect(Collectors.toList());
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product does not exists"));
		
		product.setName(productDto.name());
		product.setDescription(productDto.description());
		product.setPrice(productDto.price());
		product.setStockQuantity(productDto.stockQuantity());
		product.setCategory(productDto.category());
		product.setUpdatedAt(LocalDateTime.now());
		
		Product updatedProduct = productRepository.save(product);		
		return ProductMapper.mapToProductDto(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		if(!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Product does not exists");
		}
		productRepository.deleteById(id);
	}

}
