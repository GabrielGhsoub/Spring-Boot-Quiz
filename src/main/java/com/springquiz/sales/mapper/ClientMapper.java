package com.springquiz.sales.mapper;

import org.apache.ibatis.annotations.*;

import com.springquiz.sales.model.Client;

import java.util.List;

@Mapper
public interface ClientMapper {

	// Fetch all clients
	@Select("SELECT id, name, last_name, mobile FROM clients")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "lastName", column = "last_name"), @Result(property = "mobile", column = "mobile") })
	List<Client> findAll();

	// Create a new client
	@Insert("INSERT INTO clients (name, last_name, mobile) VALUES (#{name}, #{lastName}, #{mobile})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Client client);

	// Update an existing client
	@Update("UPDATE clients SET name = #{name}, last_name = #{lastName}, mobile = #{mobile} WHERE id = #{id}")
	int update(Client client);
}
