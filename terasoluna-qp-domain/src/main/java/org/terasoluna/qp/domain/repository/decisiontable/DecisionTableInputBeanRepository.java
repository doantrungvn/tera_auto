package org.terasoluna.qp.domain.repository.decisiontable;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;

public interface DecisionTableInputBeanRepository {

	List<DecisionTableInputBean> findDecisionInputBeanTypeTree(@Param("decisionTbId") Long decisionTbId);

	Long getSequencesInputBean(@Param("size") Integer size);
	
	int registerInputBean(@Param("inputbeanItems") List<DecisionTableInputBean> inputBeans);

	void deleteBeforModifyInputBean(@Param("inputbeanItems") List<DecisionTableInputBean> lstInputBeanModify, 
			@Param("id") Long decisionTbId);

	int modifyInputBean(@Param("inputbeanItems") List<DecisionTableInputBean> lstInputBeanModify);
	
	//TungHT
	List<DecisionTableInputBean> getListInputBeanBefore(@Param("decisionTbId") Long decisionTbId);
	
	List<DecisionTableInputBean> selectListInputBeanIsDeleted(@Param("inputbeanItems") List<DecisionTableInputBean> lstInputBeanModify, @Param("id") Long decisionTbId);
	
	List<DecisionTableInputBean> findAllDecisionInputBeanByListDecisionId(@Param("decisionLst") List<DecisionTable> decisionLst);

}
