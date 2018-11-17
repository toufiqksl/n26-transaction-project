package com.n26.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.entity.Transaction;
import com.n26.exception.DataValidatorException;
import com.n26.exception.ErrorEunm;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) throw new DataValidatorException(ErrorEunm.VALIDATION_EMPTY_REQUEST_BODY);
        if(transaction.getTimestamp() == null) throw new DataValidatorException(ErrorEunm.VALIDATION_MISSING_TIMESTAMP);
        if(transaction.getAmount() == null) throw new DataValidatorException(ErrorEunm.VALIDATION_MISSING_AMOUNT);

        logger.info("New Transaction Data : ", transaction);
        boolean result = statisticsService.calculate(transaction);
		return result;
    }
    
    @Override
    public void deleteAll() {
    	statisticsService.deleteAll();
    }
}
