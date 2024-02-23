package com.springquiz.sales.service;

import com.springquiz.sales.mapper.ClientMapper;
import com.springquiz.sales.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Autowired
	private ClientMapper clientMapper;

	public List<Client> fetchAllClients() {
		try {
			return clientMapper.findAll();
		} catch (Exception e) {
			logger.error("Error fetching clients", e);
			throw new RuntimeException("Error fetching clients", e);
		}
	}

	@Transactional
	public Client createClient(Client client) {
		try {
			clientMapper.insert(client);
			return client; // After insertion, the client object will contain the generated ID.
		} catch (Exception e) {
			logger.error("Error creating client: {}", client, e);
			throw new RuntimeException("Error creating client", e);
		}
	}

	@Transactional
	public void updateClient(Client client) {
		try {
			int updated = clientMapper.update(client);
			if (updated == 0) {
				throw new RuntimeException("No client found with id " + client.getId());
			}
		} catch (Exception e) {
			logger.error("Error updating client: {}", client, e);
			throw new RuntimeException("Error updating client", e);
		}
	}
}
