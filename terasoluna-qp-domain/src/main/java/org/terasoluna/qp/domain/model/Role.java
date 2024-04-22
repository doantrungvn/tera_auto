package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long roleId;
	private String roleCd;
	private String roleName;
	private Integer status;
	private String remark;

	private Module modul;
	private String modulName;
	private int modulId;

	private Long accountId;
	private boolean selected;
	private List<Permission> lstPermission = new ArrayList<Permission>();
	private List<String> lstDistinctModuleCode = new ArrayList<String>();
	private List<Permission> lstModuleCode = new ArrayList<Permission>();
	
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private Timestamp sysDatetime;
	private Long permissionId;
		
	public List<Permission> getLstModuleCode() {
		return lstModuleCode;
	}

	public void setLstModuleCode(List<Permission> lstModuleCode) {
		this.lstModuleCode = lstModuleCode;
	}

	public List<String> getLstDistinctModuleCode() {
		return lstDistinctModuleCode;
	}

	public void setLstDistinctModuleCode(List<String> lstDistinctModuleCode) {
		this.lstDistinctModuleCode = lstDistinctModuleCode;
	}

	public List<Permission> getLstPermission() {
		return lstPermission;
	}

	public void setLstPermission(List<Permission> lstPermission) {
		this.lstPermission = lstPermission;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	public Module getModul() {
		return modul;
	}

	public void setModul(Module modul) {
		this.modul = modul;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModulName() {
		return modulName;
	}

	public void setModulName(String modulName) {
		this.modulName = modulName;
	}

	public int getModulId() {
		return modulId;
	}

	public void setModulId(int modulId) {
		this.modulId = modulId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Long getPermissionId() {
	    return permissionId;
    }

	public void setPermissionId(Long permissionId) {
	    this.permissionId = permissionId;
    }

}
