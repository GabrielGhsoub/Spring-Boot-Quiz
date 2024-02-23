package com.springquiz.sales.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	private Integer id;
	private Integer saleId;
	private Integer productId;
	private Integer quantity;
	private boolean Deleted;
	private Double price;
}
