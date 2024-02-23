package com.springquiz.sales.service;

import com.springquiz.sales.mapper.SaleMapper;
import com.springquiz.sales.mapper.TransactionMapper;
import com.springquiz.sales.model.Sale;
import com.springquiz.sales.model.SaleDetail;
import com.springquiz.sales.model.Transaction;
import com.springquiz.sales.model.TransactionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaleService {

	private static final Logger logger = LoggerFactory.getLogger(SaleService.class);

	@Autowired
	private SaleMapper saleMapper;

	@Autowired
	private TransactionMapper transactionMapper;

	public List<SaleDetail> fetchAllSales() {
		try {
			return saleMapper.findAll();
		} catch (Exception e) {
			logger.error("Error fetching sales", e);
			throw new RuntimeException("Error fetching sales", e);
		}
	}

	@Transactional
	public Sale createSale(Sale sale, List<Transaction> transactions) {
		try {
			saleMapper.insert(sale);
			transactions.forEach(transaction -> {
				transaction.setSaleId(sale.getId());
				transactionMapper.insert(transaction);
			});
			return sale;
		} catch (Exception e) {
			logger.error("Error creating sale with transactions", e);
			throw new RuntimeException("Error creating sale with transactions", e);
		}
	}

	@Transactional
	public void updateSaleTransactions(Integer saleId, List<Transaction> updatedTransactions) {
		try {
			// Fetch existing transactions for the sale
			List<Transaction> existingTransactions = transactionMapper.findBySaleId(saleId);

			// Log updates for matching transactions and identify transactions to be soft-deleted
			Set<Integer> updatedTransactionIds = updatedTransactions.stream().map(Transaction::getId)
					.collect(Collectors.toSet());

			existingTransactions.forEach(existingTransaction -> {
				if (updatedTransactionIds.contains(existingTransaction.getId())) {
					Transaction updatedTransaction = updatedTransactions.stream()
							.filter(t -> t.getId().equals(existingTransaction.getId())).findFirst().orElse(null);

					if (updatedTransaction != null) {
						transactionMapper.logTransactionUpdate(new TransactionLog(null, existingTransaction.getId(),
								null, existingTransaction.getQuantity(), updatedTransaction.getQuantity(),
								existingTransaction.getPrice(), updatedTransaction.getPrice()));

						// Update the existing transaction
						existingTransaction.setQuantity(updatedTransaction.getQuantity());
						existingTransaction.setPrice(updatedTransaction.getPrice());
						transactionMapper.update(existingTransaction);
					}
				} else {
					// Mark transactions not present in the updated list as deleted
					transactionMapper.softDeleteByTransactionId(existingTransaction.getId());
				}
			});

			// Insert new transactions
			updatedTransactions.stream().filter(t -> t.getId() == null).forEach(newTransaction -> {
				newTransaction.setSaleId(saleId);
				transactionMapper.insert(newTransaction);
			});

			// Recalculate the sale's total
			double newTotal = updatedTransactions.stream().filter(t -> !t.isDeleted())
					.mapToDouble(t -> t.getPrice() * t.getQuantity()).sum();

			// Update the sale's total
			saleMapper.updateTotal(saleId, newTotal);
		} catch (Exception e) {
			logger.error("Error updating sale transactions: {}", e.getMessage(), e);
			throw new RuntimeException("Error updating sale transactions", e);
		}
	}
}
