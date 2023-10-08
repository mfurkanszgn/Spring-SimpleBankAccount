package com.eteration.simplebanking.model;

public class PhoneBillPaymentTransaction extends Transaction {
	private String payee;
	private String phoneNumber;

	public PhoneBillPaymentTransaction(String payee, String phoneNumber, double amount) {
		super(amount, "PhoneBillPaymentTransaction");
		this.phoneNumber = phoneNumber;
		this.payee = payee;
	}

	@Override
	public void execute(Account account) throws InsufficientBalanceException {
		account.withdraw(getAmount());
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
