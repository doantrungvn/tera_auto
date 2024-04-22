package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class TransactionComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long transactionComponentId;

	private Integer transactionType;

	public Long getTransactionComponentId() {
		return transactionComponentId;
	}

	public void setTransactionComponentId(Long transactionComponentId) {
		this.transactionComponentId = transactionComponentId;
	}

	/**
	 * @return the transactionType
	 */
	public Integer getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

}
