package org.terasoluna.qp.domain.service.functionmaster;

import java.util.List;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;

@Service
public interface FunctionMasterShareService {

	public void detectListAffectedWhenModifyMethodOfBatch(Long functionMethodId, CommonModel common);
	
	public ImpactChangeOfFunctionMaster detectListAffectedWhenModifyMethod(List<FunctionMethod> lstFunctionMethods, String functionMasterCode,CommonModel common,Boolean isRunBatch);
	
	public ImpactChangeOfFunctionMaster detectListAffectedWhenModifyMaster(FunctionMaster functionMaster, CommonModel common, Boolean isRunBatch);

	public void detectListAffectedWhenDeleteMethodOfBatch(Long functionMethodId, String functionMethodCode, String functionMasterCode, CommonModel common);
	
	public ImpactChangeOfFunctionMaster detectListAffectedWhenDeleteMethod(List<FunctionMethod> lstFunctionMethods, String functionMasterCode,CommonModel common, Boolean isRunBatch);
	
	public ImpactChangeOfFunctionMaster detectListAffectedWhenDeleteMaster(FunctionMaster functionMaster, CommonModel common, Boolean isRunBatch);

}
