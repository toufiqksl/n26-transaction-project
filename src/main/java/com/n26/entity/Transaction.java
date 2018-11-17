package com.n26.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;

import org.springframework.util.ObjectUtils;

public class Transaction {
	private Double amount;
	private String timestamp;
	private int second;

	public Transaction() {
	}

	public Transaction(Double amount, String timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		if (ObjectUtils.isEmpty(timestamp)) {
			return null;
		}
		ZonedDateTime d = ZonedDateTime.parse(timestamp);
		setSecond(d.getSecond());
		Instant instant = d.toInstant();
		Timestamp current = Timestamp.from(instant);
		return current.getTime();
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Transaction{");
		sb.append("amount=").append(amount);
		sb.append(", timestamp=").append(timestamp);
		sb.append('}');
		return sb.toString();
	}
}
