package org.terasoluna.qp.domain.service.common;

import java.sql.Timestamp;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;

@Transactional
@Service
public class PageSizeServiceImpl implements PageSizeService {
	@Inject
	AccountProfileRepository accountProfileRepository;

	@Override
	public String getMapPageSize(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageSizeOutput setMapPageSize(Account account, String mapPage) {
		PageSizeOutput output = new PageSizeOutput();
		Timestamp currentime = FunctionCommon.getCurrentTime();
		if (account != null) {
			AccountProfile accountProfile = accountProfileRepository.findOne(account.getAccountId());
			if (accountProfile != null) {
				accountProfile.setPagesizeValue(mapPage);
				accountProfile.setUpdatedBy(account.getAccountId());
				accountProfile.setUpdatedDate(currentime);
				accountProfileRepository.update(accountProfile);
			} else {
				accountProfile = new AccountProfile();
				accountProfile.setAccountId(account.getAccountId());
				accountProfile.setPagesizeValue(mapPage);
				accountProfile.setCreatedBy(account.getAccountId());
				accountProfile.setCreatedDate(currentime);
				accountProfile.setUpdatedBy(account.getAccountId());
				accountProfile.setUpdatedDate(currentime);
				accountProfileRepository.create(accountProfile);
			}
			output.setStatus(PageSizeServiceHelper.RESULT_OK);
		}
		else
		{
			output.setStatus(PageSizeServiceHelper.RESULT_FAIL);
		}
		return output;
	}

	@Override
	public Map<String, String> getPageSizeConfig() {
		return null;
	}
}
