package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.services.AccountService;

import com.eteration.simplebanking.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests {

	@Spy
	@InjectMocks
	private AccountController controller;

	@Mock
	private AccountService service;

	@Mock
	private TransactionService transactionService;

	@Test
	public void test_credit_returnsTransactionStatusWithStatusCode201() throws InsufficientBalanceException {
		Account account = new Account("Kerem Karaca", "17892");
		TransactionStatus transactionStatus = new TransactionStatus("OK", "approvalCode");

		when(service.credit("17892", 100.0)).thenReturn(transactionStatus);
		doReturn(account).when(service).getAccount("17892");

		DepositTransaction depositTransaction = new DepositTransaction(100.0);
		ResponseEntity<TransactionStatus> response = controller.credit("17892", depositTransaction);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(transactionStatus, response.getBody());
	}

	@Test
	public void givenId_GetAccount_thenReturnJson()
			throws Exception {

		Account account = new Account("Kerem Karaca", "17892");

		doReturn(account).when(service).getAccount("17892");
		ResponseEntity<Object> result = controller.getAccount("17892");
		verify(service, times(1)).getAccount("17892");
		assertEquals(account, result.getBody());
	}

	@Test
	public void test_getAccount_returnsAccountWithStatusCode302() {
		// Mock AccountService
		AccountService accountService = mock(AccountService.class);

		// Create a sample account
		Account account = new Account("1234567890", "John Doe");

		// Set up mock behavior
		when(accountService.getAccount("1234567890")).thenReturn(account);

		AccountController accountController = new AccountController(accountService);

		ResponseEntity<Object> response = accountController.getAccount("1234567890");

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertEquals(account, response.getBody());
	}

	@Test
	public void test_createAccount_returnsAccountWithStatusCode201() {
		// Mock AccountService
		AccountService accountService = mock(AccountService.class);

		// Create a sample account
		Account account = new Account("1234567890", "John Doe");

		// Set up mock behavior
		when(accountService.createAccount("John Doe")).thenReturn(account);

		// Create AccountController instance with mocked AccountService
		AccountController accountController = new AccountController(accountService);

		// Perform POST request to /create with owner string "John Doe"
		ResponseEntity<Object> response = accountController.createAccount("John Doe");

		// Assert that the response status code is 201
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Assert that the response body is the account object
		assertEquals(account, response.getBody());
	}


	@Test
	public void test_debit_with_valid_withdrawal_transaction_returns_transaction_status_with_status_code_201() throws InsufficientBalanceException {

		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(100.0, transactionService);

		when(service.debit("1234567890", 100.0)).thenReturn(new TransactionStatus("OK", "approvalCode"));

		AccountController accountController = new AccountController(service);
		ResponseEntity<TransactionStatus> response = accountController.debit("1234567890", withdrawalTransaction);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		assertEquals(new TransactionStatus("OK", "approvalCode"), response.getBody());
	}

	@Test
	public void test_phone_bill_with_valid_amount_returns_transaction_status_with_status_code_201() throws InsufficientBalanceException {

		when(service.debit("1234567890", 50.0)).thenReturn(new TransactionStatus("OK", "approvalCode"));

		// Create AccountController instance with mocked AccountService
		AccountController accountController = new AccountController(service);

		// Perform POST request to /phoneBill/{accountNumber}
		ResponseEntity<Object> response = accountController.phoneBill("1234567890", 50.0);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(new TransactionStatus("OK", "approvalCode"), response.getBody());
	}
}