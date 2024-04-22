package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class OrderByForm implements Serializable {
	
	private static final long serialVersionUID = -7017117372262068026L;
	private String orderId;
	private String sqlDesignId;
	private String tableColumn;
	private String tableColumnAutocomplete;
	private String orderType;
	private String itemSeqNo;
	private String sqlDesignResultId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(String sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	public String getTableColumnAutocomplete() {
		return tableColumnAutocomplete;
	}
	public void setTableColumnAutocomplete(String tableColumnAutocomplete) {
		this.tableColumnAutocomplete = tableColumnAutocomplete;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getSqlDesignResultId() {
		return sqlDesignResultId;
	}
	public void setSqlDesignResultId(String sqlDesignResultId) {
		this.sqlDesignResultId = sqlDesignResultId;
	}
}
