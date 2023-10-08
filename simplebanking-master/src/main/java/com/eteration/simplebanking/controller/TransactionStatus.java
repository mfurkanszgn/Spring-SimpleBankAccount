package com.eteration.simplebanking.controller;


import java.util.Objects;

public class TransactionStatus {
	private String status;
	private String approvalCode;

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public String getStatus() {
		return status;
	}

	public TransactionStatus(String status, String approvalCode) {
		this.status = status;
		this.approvalCode = approvalCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TransactionStatus that = (TransactionStatus) o;
		return Objects.equals(status, that.status) && Objects.equals(approvalCode, that.approvalCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, approvalCode);
	}
}
