package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Transaction initiateMoney(Account account, Transaction transaction) throws InsufficientBalanceException {
		transaction.setAccount(account);
		return transactionRepository.save(transaction);
	}

	public Transaction saveTransaction(Account account, Transaction transaction) throws InsufficientBalanceException {
		transaction.setAccount(account);
		return transactionRepository.save(transaction);
	}
}
