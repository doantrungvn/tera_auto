package org.terasoluna.qp.domain.service.screendesign;

public class Bean {
	private String beanid;
	private String itemcode;
	public String getBeanid() {
		if (this.beanid == null || this.beanid.equals("null") || this.beanid.equals("undefined")) {
			return "";
		}
		return beanid;
	}
	public void setBeanid(String beanid) {
		this.beanid = beanid;
	}
	public String getItemcode() {
		if (this.itemcode == null || this.itemcode.equals("null") || this.itemcode.equals("undefined")) {
			return "";
		}
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	
}
