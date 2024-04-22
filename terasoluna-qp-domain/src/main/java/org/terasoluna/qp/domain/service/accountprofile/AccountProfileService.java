package org.terasoluna.qp.domain.service.accountprofile;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.ChangePassword;

public interface AccountProfileService {
	
	AccountProfile findOne(Long accountProfileId) throws BusinessException;
	
	void update(AccountProfile accountProfile) throws BusinessException;
	
	void updatecurrentproject(AccountProfile accountProfile, String projectName) throws BusinessException;
	
	AccountProfile getAccountProfile(Long accountId) throws BusinessException;
	
	AccountProfile getDefaultProfile() throws BusinessException;
	
	ChangePassword getInformationForPasswordForm(long accountId);
}
