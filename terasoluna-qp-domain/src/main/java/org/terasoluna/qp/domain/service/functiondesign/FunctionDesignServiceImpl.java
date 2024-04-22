package org.terasoluna.qp.domain.service.functiondesign;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionDesignMessageConst;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignCriteria;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignRepository;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional	
public class FunctionDesignServiceImpl implements FunctionDesignService {
	
	@Inject
	FunctionDesignRepository functionDesignRepository;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService projectService;
	
	@Override
	public Page<FunctionDesign> searchFunctionDesign(FunctionDesignCriteria criteria, Pageable pageable, Long workingProjectId) {
		criteria.setProjectId(workingProjectId);
		long totalCount = functionDesignRepository.countBySearchCriteria(criteria);
		
		List<FunctionDesign> functionDesigns;
		if (0 < totalCount) {
			functionDesigns = functionDesignRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			functionDesigns = Collections.emptyList();
		}
		Page<FunctionDesign> page = new PageImpl<FunctionDesign>(functionDesigns, pageable, totalCount);

		return page;
	}

	@Override
	public void register(FunctionDesign functionDesign) {
		/*moduleService.initData(getWorkingProjectId(), getAccountId());*/
		Module moduleCheck = moduleService.validateModule(functionDesign.getModuleId(),functionDesign.getAccountId(), functionDesign.getProjectId(), true);
		functionDesign.setProjectId(moduleCheck.getProjectId());
		Long totalCount = functionDesignRepository.countNameCodeByFunctionId(functionDesign);
		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003));
		}
		
		if(resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			Timestamp systemDate = FunctionCommon.getCurrentTime();
			functionDesign.setCreatedBy(functionDesign.getAccountId());
			functionDesign.setCreatedDate(systemDate);
			functionDesign.setUpdatedBy(functionDesign.getAccountId());
			functionDesign.setUpdatedDate(systemDate);
			functionDesignRepository.register(functionDesign);
		}
	}

	@Override
	public FunctionDesign findFunctionDesignById(Long functionId) {
		FunctionDesign functionDesign = functionDesignRepository.findFunctionDesignById(functionId);
		if (functionDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		}
		return functionDesign;
	}

	@Override
	public void modify(FunctionDesign functionDesign) {
		functionDesign.setUpdatedBy(functionDesign.getAccountId());
		functionDesign.setSysDateTime(FunctionCommon.getCurrentTime());
		
		FunctionDesign fd = functionDesignRepository.findFunctionDesignById(functionDesign.getFunctionId());
		if(fd == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		}
		
		/*moduleService.initData(getWorkingProjectId(), getAccountId());*/
		Module moduleCheck =  moduleService.validateModule(functionDesign.getModuleId(),functionDesign.getAccountId(), functionDesign.getProjectId(), true);
		functionDesign.setProjectId(moduleCheck.getProjectId());

		//Check duplicate name and code
		Long totalCount = functionDesignRepository.countNameCodeByFunctionId(functionDesign);
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0003));
		}
		
		if(resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			functionDesign.setModuleId(fd.getModuleId());
			functionDesign.setFunctionType(fd.getFunctionType());

			if(functionDesignRepository.modify(functionDesign) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}
	}

	@Override
	public void delete(FunctionDesign functionDesign) {
		FunctionDesign functionDesignDb = functionDesignRepository.findFunctionDesignById(functionDesign.getFunctionId());
		
		if(functionDesignDb == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		}
		
		/*moduleService.initData(getWorkingProjectId(), getAccountId());*/
		moduleService.validateModule(functionDesignDb.getModuleId(), functionDesign.getAccountId(), functionDesign.getProjectId(), true);
		
		if (DateUtils.compare(functionDesignDb.getUpdatedDate(), functionDesign.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		if(functionDesignRepository.delete(functionDesign) <= 0){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		}
	}

	@Override
	public List<FunctionDesign> findAllFunctionDesignByModuleId(Long moduleId) {
		return functionDesignRepository.findAllFunctionDesignByModuleId(moduleId);
	}
		
}