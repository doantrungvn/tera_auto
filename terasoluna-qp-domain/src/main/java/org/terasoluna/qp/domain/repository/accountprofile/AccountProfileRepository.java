package org.terasoluna.qp.domain.repository.accountprofile;

import java.util.HashMap;
import java.util.List;

import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.ChangePassword;

public interface AccountProfileRepository {

	AccountProfile findOne(Long accountId);

	int create(AccountProfile accountProfile);

	int update(AccountProfile accountProfile);

	int updatecurrentproject(AccountProfile accountProfile);

	boolean updateSystemSetting(AccountProfile accountProfile);

	boolean delete(AccountProfile accountProfile);

	List<HashMap<String, String>> getDefaultProfile();

	boolean deleteByAccountId(Long accountId);
	
	ChangePassword getInformationForPasswordForm(long accountId);
	
	List<HashMap<String, String>> getBatchSetting();
}
