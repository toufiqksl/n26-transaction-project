package com.n26;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import com.n26.entity.StatisticsResult;
import com.n26.entity.Transaction;
import com.n26.service.StatisticsService;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTests {

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void whenOutdatedTimestamp_doNothing(){
        StatisticsResult summaryBefore = statisticsService.getStatistics();
        statisticsService.calculate(new Transaction(10.5, getStringFromCurrentTimeMilis(60000)));
        assertEquals(summaryBefore, statisticsService.getStatistics());
    }

    @Test
    public void whenValidTimestamp_computeStatistics(){
        statisticsService.calculate(new Transaction(5.5, getStringFromCurrentTimeMilis(10000)));
        statisticsService.calculate(new Transaction(15.5, getStringFromCurrentTimeMilis(9000)));
        statisticsService.calculate(new Transaction(25.2, getStringFromCurrentTimeMilis(8000)));
        statisticsService.calculate(new Transaction(65.5, getStringFromCurrentTimeMilis(7000)));
        statisticsService.calculate(new Transaction(5.7, getStringFromCurrentTimeMilis(6000)));
        statisticsService.calculate(new Transaction(5.8, getStringFromCurrentTimeMilis(5000)));
        statisticsService.calculate(new Transaction(3.5, getStringFromCurrentTimeMilis(4000)));
        statisticsService.calculate(new Transaction(2.8, getStringFromCurrentTimeMilis(3000)));
        statisticsService.calculate(new Transaction(9.5, getStringFromCurrentTimeMilis(2000)));
        statisticsService.calculate(new Transaction(12.3, getStringFromCurrentTimeMilis(1000)));

        StatisticsResult summary = statisticsService.getStatistics();
        assertEquals(summary.getCount(), 10);
        assertEquals(summary.getSum(), 151.3, 0.0);
        assertEquals(summary.getMax(), 65.5, 0.0);
        assertEquals(summary.getMin(), 2.8, 0.0);
        assertEquals(summary.getAvg(), 15.13, 0.0);
    }
    
    @Test
    public void whenValidTimestamp_computeStatistics111(){
        //statisticsService.calculate(new Transaction(127.96, getStringFromCurrentTimeMilis(getTimestamp("2018-10-07T06:58:27.865Z"))));
        //statisticsService.calculate(new Transaction(10.5, getStringFromCurrentTimeMilis(60000)));
        boolean flag = statisticsService.calculate(new Transaction(127.96, getStringFromCurrentTimeMilis(getTimestamp("2018-10-07T07:35:35.135Z"))));
        //StatisticsResult summary = statisticsService.getStatistics();
        System.out.println(">>>>>>>>>>>>>>>>>>> summary outpur : " + flag);
    }
    
    public static String getStringFromCurrentTimeMilis(long offset) {
    	ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - offset),
                ZoneId.systemDefault());
		return zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    
	public Long getTimestamp(String timestamp) {
		if (ObjectUtils.isEmpty(timestamp)) {
			return null;
		}
		ZonedDateTime d = ZonedDateTime.parse(timestamp);
		Instant instant = d.toInstant();
		Timestamp current = Timestamp.from(instant);
		return current.getTime();
	}

}
