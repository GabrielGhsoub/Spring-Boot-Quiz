package com.springquiz.sales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog {
	private Integer id;
	private Integer transactionId;
	private Timestamp updatedAt;
	private Integer oldQuantity;
	private Integer newQuantity;
	private Double oldPrice;
	private Double newPrice;
}
