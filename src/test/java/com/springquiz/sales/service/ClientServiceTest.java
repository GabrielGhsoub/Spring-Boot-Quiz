package com.springquiz.sales.service;

import com.springquiz.sales.mapper.ClientMapper;
import com.springquiz.sales.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClientServiceTest {

	@Mock
	private ClientMapper clientMapper;

	@InjectMocks
	private ClientService clientService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void fetchAllClients_ShouldReturnClients() {
		// Setup
		when(clientMapper.findAll()).thenReturn(
				Arrays.asList(new Client(1, "John", "Doe", "1234567890"), new Client(2, "Jane", "Doe", "0987654321")));

		// Execution
		List<Client> clients = clientService.fetchAllClients();

		// Verification
		assertEquals(2, clients.size(), "Should return 2 clients");
		verify(clientMapper, times(1)).findAll();
	}

	@Test
	void createClient_ShouldCreateClient() {
		// Setup
		Client newClient = new Client(null, "New", "Client", "0123456789");
		doAnswer(invocation -> {
			Client client = invocation.getArgument(0);
			client.setId(1); // Simulate generated key
			return null;
		}).when(clientMapper).insert(newClient);

		// Execution
		Client createdClient = clientService.createClient(newClient);

		// Verification
		assertEquals(1, createdClient.getId(), "Client ID should be set by the mapper");
		verify(clientMapper, times(1)).insert(any(Client.class));
	}

	@Test
	void updateClient_ShouldUpdateClient() {
		// Setup
		Client existingClient = new Client(1, "Existing", "Client", "1234567890");
		when(clientMapper.update(existingClient)).thenReturn(1);

		// Execution
		clientService.updateClient(existingClient);

		// Verification
		verify(clientMapper, times(1)).update(existingClient);
	}

	@Test
	void updateClient_WhenClientNotFound_ShouldThrowException() {
		// Setup
		Client nonExistentClient = new Client(99, "Non-Existent", "Client", "9999999999");
		when(clientMapper.update(nonExistentClient)).thenReturn(0);

		// Execution & Verification
		assertThrows(RuntimeException.class, () -> clientService.updateClient(nonExistentClient),
				"Should throw RuntimeException when no client found with given ID");
	}
}
