package org.terasoluna.qp.domain.repository.role;

import java.io.Serializable;

public class RoleSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleName;
	private String roleCd;
	private Integer[] status = new Integer[0];
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public Integer[] getStatus() {
		return status;
	}

	public void setStatus(Integer[] status) {
		this.status = status;
	}

}
