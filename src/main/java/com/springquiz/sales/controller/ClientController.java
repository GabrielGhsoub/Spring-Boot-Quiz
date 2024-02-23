package com.springquiz.sales.controller;

import com.springquiz.sales.model.Client;
import com.springquiz.sales.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping
	public List<Client> getAllClients() {
		return clientService.fetchAllClients();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client createClient(@RequestBody Client client) {
		return clientService.createClient(client);
	}

	@PutMapping("/{id}")
	public void updateClient(@PathVariable Integer id, @RequestBody Client client) {
		client.setId(id);
		clientService.updateClient(client);
	}
}
