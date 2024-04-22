package org.terasoluna.qp.domain.service.functiondesign;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignCriteria;

@Service
public interface FunctionDesignService {

	Page<FunctionDesign> searchFunctionDesign(FunctionDesignCriteria criteria, Pageable pageable, Long workingProjectId);
	
	List<FunctionDesign> findAllFunctionDesignByModuleId(Long moduleId);
	
	void register(FunctionDesign functionDesign);
	
	FunctionDesign findFunctionDesignById(Long functionId);
	
	void modify(FunctionDesign functionDesign);
	
	void delete(FunctionDesign functionDesign);
}
