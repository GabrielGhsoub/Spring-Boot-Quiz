package com.springquiz.sales.mapper;

import org.apache.ibatis.annotations.*;

import com.springquiz.sales.model.Sale;
import com.springquiz.sales.model.SaleDetail;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SaleMapper {

	// Fetch all sales operations
	@Select("SELECT s.id, s.creation_date, c.name as clientName, s.seller_name, s.total FROM sales s JOIN clients c ON s.client_id = c.id")
	@Results(value = { @Result(property = "id", column = "id"),
			@Result(property = "creationDate", column = "creation_date", javaType = java.sql.Date.class),
			@Result(property = "clientName", column = "clientName"),
			@Result(property = "sellerName", column = "seller_name"), @Result(property = "total", column = "total") })
	List<SaleDetail> findAll();

	// Create a new sale
	@Insert("INSERT INTO sales (creation_date, client_id, seller_name, total) VALUES (#{creationDate}, #{clientId}, #{sellerName}, #{total})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Sale sale);

	// Update total of a sale (assuming this is part of editing a sale)
	@Update("UPDATE sales SET total = #{total} WHERE id = #{id}")
	int updateTotal(@Param("id") Integer id, @Param("total") Double total);

	// Find a sale by ID
	@Select("SELECT id, creation_date, client_id, seller_name, total FROM sales WHERE id = #{id}")
	@Results(value = { @Result(property = "id", column = "id"),
			@Result(property = "creationDate", column = "creation_date", javaType = java.sql.Date.class),
			@Result(property = "clientId", column = "client_id"),
			@Result(property = "sellerName", column = "seller_name"), @Result(property = "total", column = "total") })
	Optional<Sale> findById(@Param("id") Integer id);
}
