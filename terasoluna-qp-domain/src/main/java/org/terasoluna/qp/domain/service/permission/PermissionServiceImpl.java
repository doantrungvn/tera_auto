package org.terasoluna.qp.domain.service.permission;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.Permission;
import org.terasoluna.qp.domain.repository.permission.PermissionRepository;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Inject
	PermissionRepository permissionRepository;

	@Override
	public List<Permission> getAll() {
		return permissionRepository.getAll();
	}

	@Override
	public Collection<Permission> getPermissionById(Long moduleId) {
		return permissionRepository.getPermissionById(moduleId);
	}

	@Override
	public List<Permission> getPermissionOfAccount(Long accountId) {
		// TODO Auto-generated method stub
		return permissionRepository.getPermissionOfAccount(accountId);
	}

	@Override
	public List<Permission> getAuthorityInformation(Long accountId) {
		// TODO Auto-generated method stub
		List<Permission> lstTemp =  permissionRepository.getAuthorityInformation(accountId);
		for(int i =0;i<lstTemp.size();i++)
		{
			if (lstTemp.get(i).getAccountId()!=null) {
				lstTemp.get(i).setSelected(true);
			}
			else
				lstTemp.get(i).setSelected(false);
		}
		return lstTemp;
	}
	
	@Override
	public List<Permission> findAllModuleCode() {
		return permissionRepository.findAllModuleCode();
	}

	@Override
	public List<Permission> getRoleAndPermissionOfAccount(Long accountId) {
		return permissionRepository.getRoleAndPermissionOfAccount(accountId);
	}
}
