package org.terasoluna.qp.domain.repository.decisiontable;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DecisionTableConditionGroup;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;

public interface DecisionTableConditionGroupRepository {

	List<DecisionTableConditionGroup> findConditionGroupById(@Param("decisionItemDesignIds") List<DecisionTableItemDesignBean> decisionItemDesignIds);

	Long getSequencesCondGroup(@Param("size") Integer size);

	int registerCondGroup(@Param("conditiongroups") List<DecisionTableConditionGroup> conditionGroups);

	void deleteBeforModifyCondGroup(
			@Param("conditiongroups") List<DecisionTableConditionGroup> lstCondGroupModify, 
			@Param("itemdesigns") List<DecisionTableItemDesignBean> lstItemDesignModify,
			@Param("id") Long id);

	int modifyCondGroup(@Param("conditiongroups") List<DecisionTableConditionGroup> lstCondGroupModify);

	void deleteCondGroup(@Param("itemdesigns") List<DecisionTableItemDesignBean> itemDesigns);
	
}
