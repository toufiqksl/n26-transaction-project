package com.n26.service;

import com.n26.entity.Transaction;

public interface TransactionService {
	boolean addTransaction(Transaction transaction);
    void deleteAll();
}
