package com.n26.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.entity.Statistics;
import com.n26.entity.StatisticsResult;
import com.n26.entity.Transaction;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private static final int SECS = 60;
    private static final Map<Integer, Statistics> transactionMap = new HashMap<>(SECS);

    @Override
    public boolean calculate(Transaction transaction) {
        logger.info("Calculating statistics based on transaction data : ", transaction);
        boolean flag = false;
        if ((System.currentTimeMillis() - transaction.getTimestamp()) / 1000 < SECS) {
            int second = LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()), ZoneId.systemDefault()).getSecond();
            transactionMap.compute(second, (k, v) -> {
                if (v == null || (System.currentTimeMillis() - v.getTimestamp()) / 1000 >= SECS) {
                    v = new Statistics();
                    v.setTimestamp(transaction.getTimestamp());
                    v.setSum(transaction.getAmount());
                    v.setMax(transaction.getAmount());
                    v.setMin(transaction.getAmount());
                    v.setCount(1l);
                    return v;
                }

                v.setCount(v.getCount() + 1);
                v.setSum(v.getSum() + transaction.getAmount());
                if (Double.compare(transaction.getAmount(), v.getMax()) > 0) v.setMax(transaction.getAmount());
                if (Double.compare(transaction.getAmount(), v.getMin()) < 0) v.setMin(transaction.getAmount());
                return v;
            });
        } else {
        	flag = true;
        }
        
        return flag;
    }

    @Override
    public StatisticsResult getStatistics() {
        StatisticsResult result = transactionMap.values().stream()
                .filter(s -> (System.currentTimeMillis() - s.getTimestamp()) / 1000 < SECS)
                .map(StatisticsResult::new)
                .reduce(new StatisticsResult(), (s1, s2) -> {
                    s1.setSum(s1.getSum() + s2.getSum());
                    s1.setCount(s1.getCount() + s2.getCount());
                    s1.setMax(Double.compare(s1.getMax(), s2.getMax()) > 0 ? s1.getMax() : s2.getMax());
                    s1.setMin(Double.compare(s1.getMin(), s2.getMin()) < 0 ? s1.getMin() : s2.getMin());
                    return s1;
                });

        result.setMin(Double.compare(result.getMin(), Double.MAX_VALUE) == 0 ? 0.00 : result.getMin());
        result.setMax(Double.compare(result.getMax(), Double.MIN_VALUE) == 0 ? 0.00 : result.getMax());
        result.setAvg(result.getCount() > 0l ? result.getSum() / result.getCount() : 0.0);

        if(result.getSum() == 0.0 && result.getMax() == 0.0 && result.getAvg() == 0.0 && result.getCount() == 0) {
        	result.setMin(0.0);
        }
        
        logger.info("Statistics result : ", result);
        return result;
    }
    
	@Override
	public void deleteAll() {
		transactionMap.clear();
	}
}
