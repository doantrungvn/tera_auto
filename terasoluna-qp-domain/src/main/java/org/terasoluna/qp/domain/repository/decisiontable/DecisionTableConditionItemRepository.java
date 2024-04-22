package org.terasoluna.qp.domain.repository.decisiontable;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DecisionTableConditionGroup;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;

public interface DecisionTableConditionItemRepository {

	List<DecisionTableConditionItem> findConditionItemById(@Param("conditionGroupIds") List<DecisionTableConditionGroup> conditionGroupIds);

	int registerCondItem(@Param("conditionitems") List<DecisionTableConditionItem> conditionItems);

	void deleteBeforModifyCondItem(@Param("conditionitems") List<DecisionTableConditionItem> lstCondItemModify, @Param("conditiongroups") List<DecisionTableConditionGroup> lstCondGroupModify, @Param("itemdesigns") List<DecisionTableItemDesignBean> lstItemDesignModify, @Param("id") Long tableId);

	int modifyCondItem(@Param("conditionitems") List<DecisionTableConditionItem> lstCondItemModify);

	void deleteCondItem(@Param("conditiongroups") List<DecisionTableConditionGroup> conditionGroups);

}
