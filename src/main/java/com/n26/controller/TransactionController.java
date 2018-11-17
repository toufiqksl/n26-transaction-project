package com.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.entity.Transaction;
import com.n26.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transactions")
	public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
		boolean result = transactionService.addTransaction(transaction);
		if(result) {
			return new ResponseEntity<String>("No Content", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Created", HttpStatus.CREATED);

	}

	@DeleteMapping("/transactions")
	public ResponseEntity<Void> deleteAll() {
		try {
			transactionService.deleteAll();
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
