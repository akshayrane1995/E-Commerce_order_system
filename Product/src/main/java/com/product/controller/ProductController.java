package com.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ProductDto;
import com.product.service.ProductService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;
	
	public ProductController(ProductService productService){
		this.productService = productService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<ProductDto> createProduct( @Valid @RequestBody ProductDto productDto){
		ProductDto saveProduct = productService.createProduct(productDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveProduct);
	}
 
	@GetMapping("/id/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
		ProductDto productDto = productService.getProductById(id);
		return ResponseEntity.ok(productDto);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ProductDto> getProductByName(@PathVariable String name){
		ProductDto productByName = productService.getProductByName(name);
		return ResponseEntity.ok(productByName);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		List<ProductDto> products = productService.getAllProduct();
		return ResponseEntity.ok(products);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable Long id){
		ProductDto updatedProduct = productService.updateProduct(productDto,id);
		return ResponseEntity.ok(updatedProduct);
	}
	
	@DeleteMapping("/{id}/remove")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted sucessfully");
	}
}
