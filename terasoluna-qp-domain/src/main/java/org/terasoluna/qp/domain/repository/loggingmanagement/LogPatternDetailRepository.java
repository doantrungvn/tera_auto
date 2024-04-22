package org.terasoluna.qp.domain.repository.loggingmanagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.ConversionPattern;

public interface LogPatternDetailRepository {
	// Get List conversion pattern by LogID
	List<ConversionPattern> findAllConversionPatternByLogDetailId(Long logDetailId);
	
	// Delete all conversion pattern of a log detail
	int deleteAllConversionPatternByLogDetailId(Long logDetailId);
		
	// Get all conversion pattern exists in DB
	List<ConversionPattern> findAllConversionPattern();
	
	// Insert list of conversion pattern detail
	void registerConversionPatternDetail(@Param("listConversionPattern")List<ConversionPattern> listConversionPattern);
}
