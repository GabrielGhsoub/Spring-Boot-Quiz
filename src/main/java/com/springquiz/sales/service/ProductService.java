package com.springquiz.sales.service;

import com.springquiz.sales.mapper.ProductMapper;
import com.springquiz.sales.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductMapper productMapper;

	public List<Product> fetchAllProducts() {
		try {
			return productMapper.findAll();
		} catch (Exception e) {
			logger.error("Error fetching products", e);
			throw new RuntimeException("Error fetching products", e);
		}
	}

	@Transactional
	public Product createProduct(Product product) {
		try {
			productMapper.insert(product);
			return product; // After insertion, the product object will contain the generated ID.
		} catch (Exception e) {
			logger.error("Error creating product: {}", product, e);
			throw new RuntimeException("Error creating product", e);
		}
	}

	@Transactional
	public void updateProduct(Product product) {
		try {
			int updated = productMapper.update(product);
			if (updated == 0) {
				throw new RuntimeException("No product found with id " + product.getId());
			}
		} catch (Exception e) {
			logger.error("Error updating product: {}", product, e);
			throw new RuntimeException("Error updating product", e);
		}
	}
}
