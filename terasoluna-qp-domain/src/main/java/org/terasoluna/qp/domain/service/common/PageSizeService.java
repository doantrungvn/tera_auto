package org.terasoluna.qp.domain.service.common;

import java.util.Map;

import org.terasoluna.qp.domain.model.Account;


public interface PageSizeService {
	String getMapPageSize(Long accountId);
	PageSizeOutput setMapPageSize(Account account,String mapPage);
	Map<String,String> getPageSizeConfig();
}
