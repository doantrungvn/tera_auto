package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.TransactionComponent;

public interface TransactionComponentRepository {
	Long getSequencesTransactionComponent(@Param("size") Integer size);

	int registerTransactionComponent(@Param("transactionComponentItems") List<TransactionComponent> transactionComponentItems);

	List<TransactionComponent> findTransactionComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<TransactionComponent> findAllTransactionComponentByModule(@Param("moduleId") Long moduleId);

	List<TransactionComponent> findAllTransactionComponentByModuleCommon(@Param("projectId") Long projectId);

}
