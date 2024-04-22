package org.terasoluna.qp.domain.service.webservicetoken;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.WebServiceToken;

public interface WebServiceTokenService {

	/**
	 * Get list of web service token by search criteria
	 * @param criteria
	 * @param pageable
	 * @return
	 */
	Page<WebServiceToken> getBySearchCriteria(WebServiceTokenSearchCriteria criteria, Pageable pageable);

	/**
	 * Register new web service token
	 * @param wsToken
	 * @return
	 */
	WebServiceToken registerWebServiceToken(WebServiceToken wsToken);
	
	/**
	 * Get web service token by Id
	 * @param wsTokenId
	 * @return
	 */
	WebServiceToken getWebServiceToken(long wsTokenId);
	
	/**
	 * Modify web service token
	 * @param wsToken
	 */
	void modifyWebServiceToken(WebServiceToken wsToken);
	
	/**
	 * Delete web service token
	 * @param wsToken
	 */
	void deleteWebServiceToken(WebServiceToken wsToken);
}
