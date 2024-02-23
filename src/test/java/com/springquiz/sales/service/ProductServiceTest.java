package com.springquiz.sales.service;

import com.springquiz.sales.mapper.ProductMapper;
import com.springquiz.sales.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

	@Mock
	private ProductMapper productMapper;

	@InjectMocks
	private ProductService productService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void fetchAllProducts_ShouldReturnProducts() {
		// Setup
		when(productMapper.findAll()).thenReturn(
				Arrays.asList(new Product(1, "Product A", "Description A", "Category A", Date.valueOf("2023-01-01")),
						new Product(2, "Product B", "Description B", "Category B", Date.valueOf("2023-01-02"))));

		// Execution
		List<Product> products = productService.fetchAllProducts();

		// Verification
		assertEquals(2, products.size(), "Should return 2 products");
		verify(productMapper, times(1)).findAll();
	}

	@Test
	void createProduct_ShouldCreateProduct() {
		// Setup
		Product newProduct = new Product(null, "New Product", "New Description", "New Category",
				Date.valueOf("2023-01-03"));
		doAnswer(invocation -> {
			Product product = invocation.getArgument(0);
			product.setId(3); // Simulate generated key
			return null;
		}).when(productMapper).insert(newProduct);

		// Execution
		Product createdProduct = productService.createProduct(newProduct);

		// Verification
		assertEquals(3, createdProduct.getId(), "Product ID should be set by the mapper");
		verify(productMapper, times(1)).insert(any(Product.class));
	}

	@Test
	void updateProduct_ShouldUpdateProduct() {
		// Setup
		Product existingProduct = new Product(1, "Updated Product", "Updated Description", "Updated Category",
				Date.valueOf("2023-01-04"));
		when(productMapper.update(existingProduct)).thenReturn(1);

		// Execution
		productService.updateProduct(existingProduct);

		// Verification
		verify(productMapper, times(1)).update(existingProduct);
	}

	@Test
	void updateProduct_WhenProductNotFound_ShouldThrowException() {
		// Setup
		Product nonExistentProduct = new Product(99, "Non-Existent Product", "Doesn't Exist", "No Category",
				Date.valueOf("2023-01-05"));
		when(productMapper.update(nonExistentProduct)).thenReturn(0);

		// Execution & Verification
		assertThrows(RuntimeException.class, () -> productService.updateProduct(nonExistentProduct),
				"Should throw RuntimeException when no product found with given ID");
	}
}
