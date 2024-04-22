package org.terasoluna.qp.domain.service.decisiontable;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DecisionTable;

@Service
public interface DecisionTableShareService {
	
	public ImpactChangeOfDecisionTable detectListAffectedWhenModify(DecisionTable decisionTable,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenDeleteOfBatch(Long decisionTableId,String decisionTableCode,CommonModel common);
	
	public void detectListAffectedWhenModifyOfBatch(Long decisionTableId,CommonModel common);

}
