package org.terasoluna.qp.domain.repository.screenitemvalidation;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenItemValidation;

@Repository
public interface ScreenItemValidationRepository {

	void insertScreenItemValidation(ScreenItemValidation screenItemValidation);
	List<ScreenItemValidation> getLstItemValidationByScreenItemId(@Param("lstScreenItemIds") List<Long> lstScreenItemIds);
	int modifyScreenItemValidation(@Param("lstItems") List<ScreenItemValidation> lstItems);
}
