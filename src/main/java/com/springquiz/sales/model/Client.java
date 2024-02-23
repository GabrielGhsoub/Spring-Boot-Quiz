package com.springquiz.sales.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	private Integer id;
	private String name;
	private String lastName;
	private String mobile;
}
