package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
public class AccountController {
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<Object> getAccount(@PathVariable String accountNumber) {
		try {
			Account account = accountService.getAccount(accountNumber);
			return ResponseEntity.status(HttpStatus.FOUND).body(account);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createAccount(@RequestBody String owner) {
		try {
			Account account = accountService.createAccount(owner);
			return ResponseEntity.status(HttpStatus.CREATED).body(account);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account creation failed");
		}
	}

	@PostMapping("/credit/{accountNumber}")
	public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber,
													@RequestBody DepositTransaction depositTransaction)
			throws InsufficientBalanceException {
		try {
			TransactionStatus transactionStatus = accountService.credit(accountNumber, depositTransaction.getAmount());
			return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionStatus(transactionStatus.getStatus()
					, transactionStatus.getApprovalCode()));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionStatus("ERROR",
					"Deposit failed"));
		}
	}

	@PostMapping("/debit/{accountNumber}")
	public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber,
												   @RequestBody WithdrawalTransaction withdrawalTransaction)
			throws InsufficientBalanceException {
		try {
			TransactionStatus transactionStatus = accountService.debit(accountNumber, withdrawalTransaction.getAmount());
			return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionStatus(transactionStatus.getStatus()
					, transactionStatus.getApprovalCode()));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionStatus("ERROR",
					"Withdrawal failed."));
		}
	}

	@PostMapping("/phoneBill/{accountNumber}")
	public ResponseEntity<Object> phoneBill(@PathVariable String accountNumber, @RequestBody double amount) {
		try {
			TransactionStatus transactionStatus = accountService.debit(accountNumber, amount);
			return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionStatus(transactionStatus.getStatus()
					, transactionStatus.getApprovalCode()));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionStatus("ERROR",
					"Withdrawal failed."));
		}
	}
}