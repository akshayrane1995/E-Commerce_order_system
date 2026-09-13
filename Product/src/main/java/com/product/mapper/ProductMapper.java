package com.product.mapper;

import com.product.dto.ProductDto;
import com.product.entity.Product;

public class ProductMapper {
	
	public static Product mapToProduct(ProductDto productDto){
		
		Product product = new Product(
						null,
						productDto.name(),
						productDto.description(),
						productDto.price(),
						productDto.category(),
						productDto.stockQuantity(),
						null,
						null);
		
		return product;
	}
	
	
	public static ProductDto mapToProductDto(Product product) {
		
		ProductDto productDto = new ProductDto(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getCategory(),
				product.getStockQuantity(),
				product.getCreatedAt(),
				product.getUpdatedAt());
		
		return productDto;
	}

}
