package org.terasoluna.qp.domain.service.webservicetoken;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.app.message.WebServiceTokenManagementMessageConst;
import org.terasoluna.qp.domain.model.WebServiceToken;
import org.terasoluna.qp.domain.repository.webservicetoken.WebServiceTokenRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class WebServiceTokenServiceImpl implements WebServiceTokenService {

	@Inject
	WebServiceTokenRepository webServiceRepository;
	
	@Override
	public WebServiceToken registerWebServiceToken(WebServiceToken wsToken) {
		// Check duplicate project id
		WebServiceToken wsTokenExist = webServiceRepository.getWebServiceTokenByProjectId(wsToken.getProjectId());
		if(wsTokenExist != null){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0005)));
		}
		
		// Initial data
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		wsToken.setCreatedDate(currentTime);
		wsToken.setUpdatedDate(currentTime);
		
		// Insert to database
		webServiceRepository.registerWebServiceToken(wsToken);
		return wsToken;
	}

	@Override
	public Page<WebServiceToken> getBySearchCriteria(WebServiceTokenSearchCriteria criteria,Pageable pageable) {
		// Get total record from database
		long total = webServiceRepository.countBySearchCriteria(criteria);
		List<WebServiceToken> wsTokenList;
		
		if(0 < total){
			wsTokenList = webServiceRepository.getBySearchCriteria(criteria, pageable);
		}else {
			wsTokenList = Collections.emptyList();
		}
		Page<WebServiceToken> page = new PageImpl<WebServiceToken>(wsTokenList, pageable, total);
		return page;
	}

	@Override
	public WebServiceToken getWebServiceToken(long wsTokenId) {
		WebServiceToken wsToken = webServiceRepository.getWebServiceToken(wsTokenId);
		if(wsToken == null){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0010)));
		}
		return wsToken;
	}

	@Override
	public void modifyWebServiceToken(WebServiceToken wsToken) {
		// Get wsToken from database (check exist)
		getWebServiceToken(wsToken.getWsTokenId());
				
		// Set update information
		wsToken.setSystemTime(FunctionCommon.getCurrentTime());
		
		// Do update in database
		if(!webServiceRepository.modifyWebServiceToken(wsToken)){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
	}

	@Override
	public void deleteWebServiceToken(WebServiceToken wsToken) {
		// Check wsToken exists in database
		this.getWebServiceToken(wsToken.getWsTokenId());
		
		// Do delete
		if(webServiceRepository.deleteWebServiceToken(wsToken.getWsTokenId()) == 0){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0010)));
		}
	}

}
