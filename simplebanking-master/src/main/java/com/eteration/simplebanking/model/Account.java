package com.eteration.simplebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String accountNumber;

	@Column(nullable = false)
	private String owner;

	@Column(nullable = false)
	private double balance;

	@Column(nullable = false)
	private Date createDate;

	@JsonManagedReference
	@OneToMany(mappedBy = "account")
	private List<Transaction> transactions;

	public Account() {
	}

	public Account(String owner, String accountNumber) {
		this.accountNumber = accountNumber;
		this.owner = owner;
		this.balance = 0.0;
		this.transactions = new ArrayList<>();
		this.createDate = new Date();
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	public void withdraw(double amount) throws InsufficientBalanceException {
		if (this.balance >= amount) {
			this.balance -= amount;
		} else {
			throw new InsufficientBalanceException();
		}
	}

	public void post(Transaction transaction) throws InsufficientBalanceException {
		this.getTransactions().add(transaction);
		transaction.execute(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return Double.compare(balance, account.balance) == 0 && Objects.equals(id, account.id) &&
				Objects.equals(accountNumber, account.accountNumber) && Objects.equals(owner, account.owner) &&
				Objects.equals(createDate, account.createDate) && Objects.equals(transactions, account.transactions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, accountNumber, owner, balance, createDate);
	}
}