package com.eteration.simplebanking.services;


import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final TransactionService transactionService;

	@Autowired
	public AccountService(AccountRepository accountRepository, TransactionService transactionService) {
		this.accountRepository = accountRepository;
		this.transactionService = transactionService;
	}

	public Account createAccount(String owner) {
		Account account = new Account(owner ,generateAccountNumber());
		accountRepository.save(account);
		return account;
	}

	public Account getAccount(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	public TransactionStatus credit(String accountNumber, double amount) throws InsufficientBalanceException {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		try {
			if (account != null) {
				Transaction transaction = new DepositTransaction(amount);
				transactionService.initiateMoney(account, transaction);
				account.post(transaction);
				accountRepository.save(account);
				return new TransactionStatus("OK", transaction.getApprovalCode());
			} else {
				return new TransactionStatus("ERROR", "Account not found.");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public TransactionStatus debit(String accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			try {
				Transaction transaction = new WithdrawalTransaction(amount, transactionService);
				transactionService.initiateMoney(account, transaction);
				account.post(transaction);
				accountRepository.save(account);
				return new TransactionStatus("OK", transaction.getApprovalCode());
			} catch(InsufficientBalanceException e) {
				return new TransactionStatus("ERROR", "Insufficient funds.");
			}
		} else {
			return new TransactionStatus("ERROR", "Account not found.");
		}
	}

	private String generateAccountNumber() {
		Random random = new Random();
		int accountNumber = 0 + random.nextInt(90000);
		return String.valueOf(accountNumber);
	}
}
