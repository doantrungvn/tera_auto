package org.terasoluna.qp.domain.repository.decisiontable;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;

public interface DecisionTableOutputBeanRepository {

	List<DecisionTableOutputBean> findDecisionOutputBeanTypeTree(@Param("decisionTbId") Long decisionTbId);

	Long getSequencesOutputBean(@Param("size") Integer size);

	int registerOutputBean(@Param("outputbeanItems") List<DecisionTableOutputBean> outputBeans);

	void deleteBeforModifyOutputBean(@Param("outputbeanItems") List<DecisionTableOutputBean> lstOutputBeanModify,
			@Param("id") Long decisionTbId);

	int modifyOutputBean(@Param("outputbeanItems") List<DecisionTableOutputBean> lstOutputBeanModify);
	
	//TungHT
	List<DecisionTableOutputBean> getListOutputBeanBefore(@Param("decisionTbId") Long decisionTbId);
	
	List<DecisionTableOutputBean> selectListOutputBeanIsDeleted(@Param("outputbeanItems") List<DecisionTableOutputBean> lstOutputBeanModify, @Param("id") Long decisionTbId);

	List<DecisionTableOutputBean> findAllDecisionOutputBeanByListDecisionId(@Param("decisionLst") List<DecisionTable> decisionLst);

}
