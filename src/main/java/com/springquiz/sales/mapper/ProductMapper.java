package com.springquiz.sales.mapper;

import org.apache.ibatis.annotations.*;

import com.springquiz.sales.model.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

	@Select("SELECT id, name, description, category, creation_date FROM products")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "description", column = "description"),
			@Result(property = "category", column = "category"),
			@Result(property = "creationDate", column = "creation_date", javaType = java.sql.Date.class) })
	List<Product> findAll();

	// Create a new product
	@Insert("INSERT INTO products (name, description, category, creation_date) VALUES (#{name}, #{description}, #{category}, #{creationDate})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Product product);

	// Update an existing product
	@Update("UPDATE products SET name = #{name}, description = #{description}, category = #{category}, creation_date = #{creationDate} WHERE id = #{id}")
	int update(Product product);
}
