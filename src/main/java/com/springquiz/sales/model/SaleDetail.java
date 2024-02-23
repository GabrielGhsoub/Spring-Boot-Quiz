package com.springquiz.sales.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetail {
	private Integer id;
	private Date creationDate;
	private String clientName;
	private String sellerName;
	private Double total;
}
