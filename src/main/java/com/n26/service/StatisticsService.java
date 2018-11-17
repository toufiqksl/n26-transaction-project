package com.n26.service;

import com.n26.entity.StatisticsResult;
import com.n26.entity.Transaction;

public interface StatisticsService {

    boolean calculate(Transaction transaction);
    StatisticsResult getStatistics();
    void deleteAll();

}
