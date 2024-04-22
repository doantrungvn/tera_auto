package org.terasoluna.qp.domain.service.functionmaster;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterCriteria;

@Service
public interface FunctionMasterService {

	Page<FunctionMaster> searchFunctionMaster(FunctionMasterCriteria criteria, Pageable pageable);

	FunctionMaster findOneFuntionMasterById(Long functionMasterId);

	FunctionMaster loadFunctionMaster(Long functionMasterId);

	List<FunctionMethod> findFuntionMethodByFunctionMasterId(Long functionMasterId);

	List<FunctionMethodInput> findFunctionMethodInputByFunctionMethodId(Long functionMethodId);

	List<FunctionMethodOutput> findFunctionMethodOutputByFunctionMethodId(Long functionMethodId);

	void checkFunctionMasterCommon(FunctionMaster functionMaster);

	List<FunctionMaster> findFunctionMasterDefault();

	List<FunctionMethod> findFuntionMethodDefault();

	List<FunctionMethodInput> findFunctionMethodInputDefault();

	List<FunctionMethodOutput> findFunctionMethodOutputDefault();

	void registerFunctionMasterDefault(FunctionMaster functionMaster);

	FunctionMaster loadFunctionMasterWithAffectDeleted(Long functionMasterId);

	void registerFunctionMaster(FunctionMaster functionMaster, CommonModel common);

	void modifyFunctionMaster(FunctionMaster functionMaster, CommonModel common);

	List<FunctionMaster> loadAllFunctionMasterByProject(CommonModel common);

	List<FunctionMaster> loadAllFunctionMasterByProjectExcludeFuntionName(CommonModel common);

	List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(Long commonObjectDefinitionId, CommonModel common);

	List<ExternalObjectAttribute> findExternalObjectAttributeByCommonObject(Long externalObjectDefinitionId, CommonModel common);

	void delete(FunctionMaster functionMaster, CommonModel common);
}
