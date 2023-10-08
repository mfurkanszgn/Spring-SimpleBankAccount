package com.eteration.simplebanking.model;

import com.eteration.simplebanking.services.TransactionService;

import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class WithdrawalTransaction extends Transaction {

	public WithdrawalTransaction(double amount, TransactionService transactionService) {
		super(amount, "WithdrawalTransaction");
	}
	public WithdrawalTransaction() {
	}

	public void execute(Account account) throws InsufficientBalanceException {
		account.withdraw(getAmount());
	}
}

