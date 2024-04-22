package org.terasoluna.qp.domain.service.impactchange;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;

@Service
public interface ImpactSQLDesignShareService {
	
	public void detectListAffectedWhenModifyOfBatch(Long sqlDesignId,CommonModel common);
	
	public ImpactSQLDesign detectListAffectedWhenModify(SqlDesignCompound sqlDesignCompound,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenDeleteOfBatch(Long sqlDesignId,String sqlDesignCode,CommonModel common);
	
	public ImpactSQLDesign detectListAffectedWhenDelete(SqlDesign sqlDesign,CommonModel common,Boolean isRunBatch);
	

}
