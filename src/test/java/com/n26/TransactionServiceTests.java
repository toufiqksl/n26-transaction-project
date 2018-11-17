package com.n26;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.entity.Transaction;
import com.n26.exception.DataValidatorException;
import com.n26.service.StatisticsServiceImpl;
import com.n26.service.TransactionService;
import com.n26.service.TransactionServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTests {

    @Autowired
    private TransactionService transactionService;


    @Mock
    private StatisticsServiceImpl statisticsServiceMock;

    @InjectMocks
    private TransactionServiceImpl transactionServiceMock;


    @Test(expected = DataValidatorException.class)
    public void whenEmptyRequestBody_exceptionThrown(){
        transactionService.addTransaction(null);
    }

    @Test(expected = DataValidatorException.class)
    public void whenMissingTimestampField_exceptionThrown(){
        transactionService.addTransaction(new Transaction(12.5, null));
    }

    @Test(expected = DataValidatorException.class)
    public void whenMissingAmountField_exceptionThrown(){
        transactionService.addTransaction(new Transaction(null, getStringFromCurrentTimeMilis()));
    }

    @Test
    public void whenValidTransaction_flowSucceeds(){
        //doNothing().when(statisticsServiceMock).calculate(any(Transaction.class));
        transactionServiceMock.addTransaction(new Transaction(12.5, getStringFromCurrentTimeMilis()));

        verify(statisticsServiceMock, times(1)).calculate(any(Transaction.class));
    }
    
    @Test
    public void whenDeleteAllTransaction(){
    	transactionServiceMock.deleteAll();
        verify(statisticsServiceMock, times(1)).deleteAll();
    }
    
    public static String getStringFromCurrentTimeMilis() {
    	ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneId.systemDefault());
		return zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
