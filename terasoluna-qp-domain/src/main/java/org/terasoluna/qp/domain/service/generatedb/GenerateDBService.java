package org.terasoluna.qp.domain.service.generatedb;


public interface GenerateDBService{
	
	/**
	 * Create table & table detail from screen
	 * 
	 */
	void generateDbFromScreen(Long moduleId, boolean overwriteFlg);
}
