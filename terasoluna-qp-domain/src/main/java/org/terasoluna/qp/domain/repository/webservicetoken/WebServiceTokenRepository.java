package org.terasoluna.qp.domain.repository.webservicetoken;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.WebServiceToken;
import org.terasoluna.qp.domain.service.webservicetoken.WebServiceTokenSearchCriteria;

public interface WebServiceTokenRepository {

	/**
	 * Get web service token by search criteria
	 * @param webServiceToken
	 * @param pageable
	 * @return
	 */
	List<WebServiceToken> getBySearchCriteria(@Param("webServiceToken") WebServiceTokenSearchCriteria webServiceToken,
			@Param("pageable") Pageable pageable);
	
	/**
	 * Count web service token by search criteria
	 * @param criteria
	 * @return
	 */
	Long countBySearchCriteria(@Param("webServiceToken") WebServiceTokenSearchCriteria criteria);
	
	/**
	 * Get web service token by id
	 * @param wsTokenId
	 * @return
	 */
	WebServiceToken getWebServiceToken(long wsTokenId);
	
	/**
	 * Register new web service token
	 * @param wsToken
	 */
	void registerWebServiceToken(WebServiceToken wsToken);

	/**
	 * Modify web service token
	 * @param wsToken
	 * @return
	 */
	boolean modifyWebServiceToken(WebServiceToken wsToken);
	
	/**
	 * Delete web service token
	 * @param wsTokenId
	 * @return number of deleted record
	 */
	int deleteWebServiceToken(long wsTokenId);
	
	/**
	 * Get web service token by project id
	 * @return
	 */
	WebServiceToken getWebServiceTokenByProjectId(@Param("projectId")long projectId);
}
