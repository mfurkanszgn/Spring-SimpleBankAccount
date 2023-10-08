package com.eteration.simplebanking.model;

import javax.persistence.Entity;

@Entity
public class DepositTransaction extends Transaction {
	public DepositTransaction(double amount) {
		super(amount, "DepositTransaction");
	}

	public DepositTransaction() {
	}

	@Override
	public void execute(Account account) throws InsufficientBalanceException {
		account.deposit(getAmount());
	}
}