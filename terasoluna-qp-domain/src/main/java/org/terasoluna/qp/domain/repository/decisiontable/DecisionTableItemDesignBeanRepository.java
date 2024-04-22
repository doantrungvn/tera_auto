package org.terasoluna.qp.domain.repository.decisiontable;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;

public interface DecisionTableItemDesignBeanRepository {

	List<DecisionTableItemDesignBean> findDecisionItemDesignBeanById(@Param("decisionTbId") Long decisionTbId);
	
	Long getSequencesItemDesign(@Param("size") Integer size);

	int registerItemDesign(@Param("itemdesigns") List<DecisionTableItemDesignBean> itemDesigns);

	void deleteBeforModifyItemDesign(@Param("itemdesigns") List<DecisionTableItemDesignBean> lstItemDesignModify,
			@Param("id") Long decisionTbId);

	int modifyItemDesign(@Param("itemdesigns") List<DecisionTableItemDesignBean> lstItemDesignModify);

	int countItemDesignModifyIn(@Param("itemdesigns") List<DecisionTableItemDesignBean> lstItemDesignModify,
			@Param("id") Long decisionTbId);
	
	// For generate source code
	List<DecisionTableItemDesignBean> findAllDecisionItemDesignBeanByIOBeanId(
			@Param("inputBeans") List<DecisionTableInputBean> inputBeans,
			@Param("outputBeans") List<DecisionTableOutputBean> outputBeans);
	
	List<DecisionTableItemDesignBean> findAllDecisionItemDesignByLstDecisionInfor(@Param("decisionLst") List<DecisionTable> decisionLst);
}
