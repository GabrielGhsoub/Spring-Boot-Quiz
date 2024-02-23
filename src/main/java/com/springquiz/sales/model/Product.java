package com.springquiz.sales.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private Integer id;
	private String name;
	private String description;
	private String category;
	private Date creationDate; // Used java.sql.Date to match the SQL DATE type
}
