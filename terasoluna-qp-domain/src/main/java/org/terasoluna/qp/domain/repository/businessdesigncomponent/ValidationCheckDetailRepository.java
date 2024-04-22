package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;

public interface ValidationCheckDetailRepository {

	Long getSequencesValidationCheckDetail(@Param("size") Integer size);

	int registerValidationCheckDetails(@Param("validationCheckDetailItems") List<ValidationCheckDetail> validationCheckDetailItems);

	List<ValidationCheckDetail> findValidationCheckDetailsByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ValidationCheckDetail> findValidationCheckDetailsByInputBeanIds(@Param("lstInputbeanId") List<String> lstInputbeanId);
	
	int deleteValidationCheckDetail(@Param("validationCheckDetailItems") List<ValidationCheckDetail> validationCheckDetailItems);
}
