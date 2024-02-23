package com.springquiz.sales.service;

import com.springquiz.sales.mapper.SaleMapper;
import com.springquiz.sales.mapper.TransactionMapper;
import com.springquiz.sales.model.Sale;
import com.springquiz.sales.model.SaleDetail;
import com.springquiz.sales.model.Transaction;
import com.springquiz.sales.model.TransactionLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SaleServiceTest {

	@Mock
	private SaleMapper saleMapper;

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private SaleService saleService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void fetchAllSales_ShouldReturnSalesDetails() {
		when(saleMapper.findAll()).thenReturn(Arrays.asList(
				new SaleDetail(1, new java.sql.Date(System.currentTimeMillis()), "John Doe", "Seller A", 100.00),
				new SaleDetail(2, new java.sql.Date(System.currentTimeMillis()), "Jane Doe", "Seller B", 200.00)));

		List<SaleDetail> sales = saleService.fetchAllSales();

		assertNotNull(sales);
		assertEquals(2, sales.size());
		verify(saleMapper, times(1)).findAll();
	}

	@Test
	void createSale_WithTransactions_ShouldCreateSaleAndTransactions() {
		Sale sale = new Sale(null, new java.sql.Date(System.currentTimeMillis()), 1, "Seller A", 0.0);
		// Adjusted to match your Transaction constructor
		List<Transaction> transactions = Arrays.asList(new Transaction(null, null, 1, 5, false, 10.00),
				new Transaction(null, null, 2, 2, false, 20.00));

		doAnswer(invocation -> {
			Sale s = invocation.getArgument(0);
			s.setId(1); // Simulate setting ID after insertion
			return null;
		}).when(saleMapper).insert(any(Sale.class));

		doAnswer(invocation -> {
			Transaction t = invocation.getArgument(0);
			t.setId(1); // Simulate setting ID after insertion, for the first transaction
			return null;
		}).when(transactionMapper).insert(any(Transaction.class));

		Sale createdSale = saleService.createSale(sale, transactions);

		assertNotNull(createdSale.getId());
		verify(saleMapper, times(1)).insert(any(Sale.class));
		verify(transactionMapper, times(transactions.size())).insert(any(Transaction.class));
	}

	@Test
	void updateSaleTransactions_ShouldUpdateTransactionsAndLogChanges() {
		Integer saleId = 1;
		List<Transaction> existingTransactions = Arrays.asList(new Transaction(1, saleId, 1, 3, false, 15.00));
		List<Transaction> updatedTransactions = Arrays.asList(new Transaction(1, saleId, 1, 4, false, 20.00));

		when(transactionMapper.findBySaleId(saleId)).thenReturn(existingTransactions);

		when(transactionMapper.update(any(Transaction.class))).thenReturn(1);

		doNothing().when(transactionMapper).softDeleteByTransactionId(anyInt());
		doNothing().when(transactionMapper).logTransactionUpdate(any(TransactionLog.class));

		saleService.updateSaleTransactions(saleId, updatedTransactions);

		verify(transactionMapper, times(updatedTransactions.size())).update(any(Transaction.class));
		verify(transactionMapper, times(updatedTransactions.size())).logTransactionUpdate(any(TransactionLog.class));
	}

}
