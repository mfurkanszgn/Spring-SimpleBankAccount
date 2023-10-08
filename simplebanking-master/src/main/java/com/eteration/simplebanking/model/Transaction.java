package com.eteration.simplebanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private double amount;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String approvalCode;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	protected Transaction(double amount, String type) {
		this.date = new Date();
		this.amount = amount;
		this.type = type;
		this.approvalCode = generateApprovalCode();
	}

	public Transaction() {
	}

	public Date getDate() {
		return date;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public double getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	private String generateApprovalCode() {
		return UUID.randomUUID().toString();
	}

	public void execute(Account account) throws InsufficientBalanceException {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Transaction that = (Transaction) o;
		return Double.compare(amount, that.amount) == 0 && Objects.equals(id, that.id) && Objects.equals(date, that.date)
				&& Objects.equals(type, that.type) && Objects.equals(approvalCode, that.approvalCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, date, amount, type, approvalCode);
	}

}