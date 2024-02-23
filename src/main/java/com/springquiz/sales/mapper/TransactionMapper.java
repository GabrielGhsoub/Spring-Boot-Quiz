package com.springquiz.sales.mapper;

import org.apache.ibatis.annotations.*;

import com.springquiz.sales.model.Transaction;
import com.springquiz.sales.model.TransactionLog;

import java.util.List;

@Mapper
public interface TransactionMapper {

	// Insert a transaction
	@Insert("INSERT INTO transactions (sale_id, product_id, quantity, price) VALUES (#{saleId}, #{productId}, #{quantity}, #{price})")
	int insert(Transaction transaction);

	// Update a transaction
	@Update("UPDATE transactions SET quantity = #{quantity}, price = #{price} WHERE id = #{id}")
	int update(Transaction transaction);

	// Fetch transactions for a sale
	@Select("SELECT id, sale_id, product_id, quantity, price FROM transactions WHERE sale_id = #{saleId} AND is_deleted = FALSE")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "saleId", column = "sale_id"),
			@Result(property = "productId", column = "product_id"), @Result(property = "quantity", column = "quantity"),
			@Result(property = "price", column = "price") })
	List<Transaction> findBySaleId(@Param("saleId") Integer saleId);

	// Method to log transaction updates
	@Insert("INSERT INTO sale_transaction_logs (transaction_id, old_quantity, new_quantity, old_price, new_price) "
			+ "VALUES (#{transactionId}, #{oldQuantity}, #{newQuantity}, #{oldPrice}, #{newPrice})")
	void logTransactionUpdate(TransactionLog transactionLog);

	@Update("UPDATE transactions SET is_deleted = TRUE WHERE sale_id = #{saleId}")
	void softDeleteBySaleId(@Param("saleId") Integer saleId);

	@Update("UPDATE transactions SET is_deleted = TRUE WHERE id = #{id}")
	void softDeleteByTransactionId(@Param("id") Integer id);

}
