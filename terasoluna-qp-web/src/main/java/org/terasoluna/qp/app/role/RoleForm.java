package org.terasoluna.qp.app.role;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.RoleMessageConst;
import org.terasoluna.qp.app.rolepermission.RolePermissionForm;
import org.terasoluna.qp.domain.model.Permission;

public class RoleForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long roleId;

	@NotEmpty(message = RoleMessageConst.SC_ROLE_0005)
	@QpNameSize(message = RoleMessageConst.SC_ROLE_0005)
	@QpNamePattern(message= RoleMessageConst.SC_ROLE_0005)
	private String roleName;
	
	@NotEmpty(message = RoleMessageConst.SC_ROLE_0006)
	@QpCodeSize(message = RoleMessageConst.SC_ROLE_0006)
	@QpCodePattern(message= RoleMessageConst.SC_ROLE_0006)
	private String roleCd;

	@NotNull(message = RoleMessageConst.SC_ROLE_0007)
	private Integer status;

	@QpRemarkSize(message= CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	private String mode;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private boolean hasErrors = false;
	
	private List<RolePermissionForm> rolePermissionList = new ArrayList<RolePermissionForm>();
	
	private List<Permission> lstPermission = new ArrayList<Permission>();
	
	private String moduleCd;
	
	private boolean selected;
	
	private Long permissionId;
	
	private String permissionName;
	
	private List<String> lstDistinctModuleCode = new ArrayList<String>();
	
	private List<Permission> lstModuleCode = new ArrayList<Permission>();
	
	private String[] arrPermissionChecked;
	
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

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public List<RolePermissionForm> getRolePermissionList() {
		return rolePermissionList;
	}

	public void setRolePermissionList(List<RolePermissionForm> rolePermissionList) {
		this.rolePermissionList = rolePermissionList;
	}

	public String getModuleCd() {
		return moduleCd;
	}

	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String[] getArrPermissionChecked() {
	    return arrPermissionChecked;
    }

	public void setArrPermissionChecked(String[] arrPermissionChecked) {
	    this.arrPermissionChecked = arrPermissionChecked;
    }



}
