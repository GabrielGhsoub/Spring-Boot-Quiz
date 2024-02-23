package com.springquiz.sales.controller;

import com.springquiz.sales.model.Sale;
import com.springquiz.sales.model.SaleDetail;
import com.springquiz.sales.model.SaleRequest;
import com.springquiz.sales.model.Transaction;
import com.springquiz.sales.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@GetMapping
	public List<SaleDetail> getAllSales() {
		return saleService.fetchAllSales();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Sale createSale(@RequestBody SaleRequest saleRequest) {
		return saleService.createSale(saleRequest.getSale(), saleRequest.getTransactions());
	}

	@PutMapping("/{saleId}/transactions")
	public void updateSaleTransactions(@PathVariable Integer saleId, @RequestBody List<Transaction> transactions) {
		saleService.updateSaleTransactions(saleId, transactions);
	}
}
