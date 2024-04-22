package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignOrder implements Serializable{
	
	private static final long serialVersionUID = 4229817044794404420L;
	private Long orderId;
	private Long sqlDesignId;
	private String orderType;
	private Integer itemSeqNo;
	private Long sqlDesignResultId;
	private String tableName;
	
	public Long getSqlDesignResultId() {
		return sqlDesignResultId;
	}
	public void setSqlDesignResultId(Long sqlDesignResultId) {
		this.sqlDesignResultId = sqlDesignResultId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
