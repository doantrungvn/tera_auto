package org.terasoluna.qp.domain.service.role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.RoleMessageConst;
import org.terasoluna.qp.domain.model.Permission;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.model.RolePermission;
import org.terasoluna.qp.domain.repository.permission.PermissionRepository;
import org.terasoluna.qp.domain.repository.role.RoleRepository;
import org.terasoluna.qp.domain.repository.role.RoleSearchCriteria;
import org.terasoluna.qp.domain.repository.rolepermission.RolePermissionRepository;

@Service
@Transactional
public class RoleServiceImp implements RoleService {
	private static final String ACCOUNT_ROLE_REF_COUNT = "account_role_ref_count";

	@Inject
	RoleRepository roleRepository;
	
	@Inject
	RolePermissionRepository rolePermissionRepository;
	
	@Inject
	PermissionRepository permissionRepository;

	/**
	 * Finds a role information with role identify
	 * @param roleId identify
	 * @return role information
	 */
	@Override
	public Role findOne(Long roleId) {
		Role role = roleRepository.findOne(roleId);
		if (role == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		}
		return role;
	}

	/**
	 * Find role information and permissions of role
	 * @param roleId identify
	 * @return role information
	 */
	@Override
	public Role findRoleInfoAndPermission(Long roleId) {
		Role role = this.findOne(roleId);
		if (role == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		}
		List<Permission> lstPermissionsOfRole = permissionRepository.findPermissionOfRole(role.getRoleId());
		List<String> lstDistinctModuleCode = new ArrayList<String>();
		for (Permission obj : lstPermissionsOfRole) {
			if (obj.getModuleCode() != null && !lstDistinctModuleCode.contains(obj.getModuleCode())) {
				lstDistinctModuleCode.add(obj.getModuleCode());
			}
		}
		role.setLstPermission(lstPermissionsOfRole);
		role.setLstDistinctModuleCode(lstDistinctModuleCode);
		return role;
	}

	/**
	 * Find role information and all permissions
	 * @return role information
	 */
	@Override
    public Role displayRegister() {
		List<Permission> lstModuleCode = permissionRepository.findAllModuleCode();
		List<Permission> lstPermission = permissionRepository.getAll();
		Role role = new Role();
		role.setLstModuleCode(lstModuleCode);
		role.setLstPermission(lstPermission);
		
	    return role;
    }

	/**
	 * Register role information and permissions of role
	 * @param Role information
	 */
	@Override
	public void register(Role role) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		// Role code and name are duplicated
		Long totalCount = roleRepository.countNameCodeByRoleId(role);
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0006));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// register role
			//role.setCreatedBy(this.getAccountId());
			role.setCreatedDate(currentTime);
			role.setUpdatedBy(role.getCreatedBy());
			role.setUpdatedDate(currentTime);
			if (roleRepository.register(role) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
			}
			// register permissions of role
			Long roleId = role.getRoleId();
			List<Permission> lstPermission = role.getLstPermission();
			List<RolePermission> lstRolePermission = new ArrayList<RolePermission>();
			if (lstPermission != null && lstPermission.size() > 0) {
				for (Permission obj : lstPermission) {
					if (obj.isSelected()) {
						RolePermission rpObj = new RolePermission();
						rpObj.setRoleId(roleId);
						rpObj.setPermissionId(obj.getPermissionId());
						lstRolePermission.add(rpObj);
					}
				}
			}
			if (lstRolePermission != null && lstRolePermission.size() > 0) {
				if (rolePermissionRepository.register(lstRolePermission) <= 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
				}
			}
		}
	}

	/**
	 * Find role information to display
	 * @param roleId identity
	 * @return Role information
	 */
	@Override
	public Role displayModify(Long roleId) {
		Role role = roleRepository.findOne(roleId);
		if (role == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		}
		// get distinct module code
		List<Permission> lstModuleCode = permissionRepository.findAllModuleCode();
		// get permission
		List<Permission> lstPermission = permissionRepository.getPermissionByRole(roleId);
		for(Permission perObj : lstPermission) {
			if(perObj.getRoleId() != null) {
				perObj.setSelected(true);
			} else {
				perObj.setSelected(false);
			}
		}
		role.setLstModuleCode(lstModuleCode);
		role.setLstPermission(lstPermission);
		return role;
	}

	/**
	 * Modify role information
	 * @param role Role information
	 */
	@Override
	public void modify(Role role) {
		Role roleForUpdate = findOne(role.getRoleId());
		if (roleForUpdate == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		}
		ResultMessages resultMessages = ResultMessages.error();
		// Role code and name are duplicated
		Long totalCount = roleRepository.countNameCodeByRoleId(role);
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0006));
		}
		
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			roleForUpdate.setRoleCd(role.getRoleCd());
			roleForUpdate.setRoleName(role.getRoleName());
			roleForUpdate.setRemark(role.getRemark());
			roleForUpdate.setStatus(role.getStatus());
			roleForUpdate.setUpdatedDate(role.getUpdatedDate());
			roleForUpdate.setUpdatedBy(role.getUpdatedBy());
			roleForUpdate.setSysDatetime(FunctionCommon.getCurrentTime());
			if (roleRepository.modify(roleForUpdate) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
			}
			// delete all permissions of this role
			rolePermissionRepository.delete(roleForUpdate.getRoleId());
			// insert permission of role
			List<Permission> lstPermission = role.getLstPermission();
			List<RolePermission> lstRolePermission = new ArrayList<RolePermission>();
			if (lstPermission != null && lstPermission.size() > 0) {
				for (Permission obj : lstPermission) {
					if (obj.isSelected()) {
						RolePermission rp = new RolePermission();
						rp.setRoleId(roleForUpdate.getRoleId());
						rp.setPermissionId(obj.getPermissionId());
						lstRolePermission.add(rp);
					}
				}
			}
			if (lstRolePermission != null && lstRolePermission.size() > 0) {
				if (rolePermissionRepository.register(lstRolePermission) <= 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
				}
			}
		}
	}
	
	/**
	 * Delete role information
	 * @param roleId identity
	 */
	@Override
	public void delete(Long roleId) {
		ResultMessages resultMessages = ResultMessages.error();
		Role roleForUpdate = findOne(roleId);
		if (roleForUpdate == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
		}
		// Data is being used by another function
		HashMap<String, Long> referenceHashMap = roleRepository.countReferenceByRoleId(roleForUpdate.getRoleId());
		if (0 < referenceHashMap.get(ACCOUNT_ROLE_REF_COUNT)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_ACCOUNTROLE));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// get permissions of role for delete table role_permission
			List<RolePermission> lstRolePermission = rolePermissionRepository.getPermissionByRole(roleId);
			if (lstRolePermission != null && lstRolePermission.size() > 0) {
				if (rolePermissionRepository.delete(roleId) <= 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
				}
			}
			// delete table role
			if (roleRepository.delete(roleId) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(RoleMessageConst.SC_ROLE_0010)));
			}
		}
	}

	/**
	 * Find list of roles information have paging
	 * @param role RoleSearchCriteria information
	 * @param pageable Pageable information 
	 * @return List of roles
	 */
	@Override
	public Page<Role> searchRoles(RoleSearchCriteria role, Pageable pageable) {
		long total = roleRepository.countBySearchCriteria(role);
		List<Role> roles;
		if (0 < total) {
			roles = roleRepository.findBySearchCriteria(role, pageable);
		} else {
			roles = Collections.emptyList();
		}

		Page<Role> page = new PageImpl<Role>(roles, pageable, total);

		return page;
	}

	/**
	 * Find list of roles information by accountId
	 * @param accountId identity
	 * @return List of roles
	 */
	@Override
	public List<Role> findRoleOfAccount(Long accountId) {
		return roleRepository.findRoleOfAccount(accountId);
	}

	/**
	 * Find list of roles information by permission
	 * @return List of roles
	 */
	@Override
    public List<Role> getRolePermission() {
	    return roleRepository.getRolePermission();
    }

	@Override
	public List<String> getRoleAppliedUserAccount(Long roleId) {
		return roleRepository.getRoleAppliedUserAccount(roleId);
	}
	
}
