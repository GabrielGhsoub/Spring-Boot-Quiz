package com.springquiz.sales.controller;

import com.springquiz.sales.model.Product;
import com.springquiz.sales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.fetchAllProducts();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProduct(@RequestBody Product product) {
		return productService.createProduct(product);
	}

	@PutMapping("/{id}")
	public void updateProduct(@PathVariable Integer id, @RequestBody Product product) {
		product.setId(id);
		productService.updateProduct(product);
	}
}
