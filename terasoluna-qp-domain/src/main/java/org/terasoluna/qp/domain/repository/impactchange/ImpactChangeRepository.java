package org.terasoluna.qp.domain.repository.impactchange;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;

public interface ImpactChangeRepository {

	void registerImpactChange(ImpactChangeJobControl impactChangeJobControl);
	
	void modifyImpactChange(ImpactChangeJobControl impactChangeJobControl);
	
	void modifyStatusOfImpactChange(ImpactChangeJobControl impactChangeJobControl);
	
	Long countImpactChangeByType(@Param("impactChangeJobControl") ImpactChangeJobControl impactChangeJobControl);
}
