package org.terasoluna.qp.domain.service.codelist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CodelistMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListSearchCriteria;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CodeListServiceImpl implements CodeListService {

	@Inject
	CodeListRepository codeListRepository;

	@Inject
	CodeListDetailRepository codelistDetailRepository;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService projectService;

	@Override
	public CodeList getCodeList(long codeListId)  {
		CodeList codeList = codeListRepository.getCodeList(codeListId);
		if (codeList == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST)));
		}
		CodeListDetail[] codelistDetails = codelistDetailRepository.findCodeListDetailByCodeListId(codeList.getCodeListId());
		if (codelistDetails != null && ArrayUtils.isNotEmpty(codelistDetails)) {
			codeList.setCodelistDetails(codelistDetails);
		}
		return codeList;
	}

	@Override
	public CodeList validateCodeList(long codeListId, Long projectId, Long accountId, boolean checkFixdesign) {
		CodeList codeList = codeListRepository.getCodeList(codeListId);
		if (codeList == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST)));
		}

		if (codeList.getModuleId() != null) {
			Module module = moduleService.validateModule(codeList.getModuleId(), accountId, projectId, checkFixdesign);
			codeList.setModuleStatus(module.getStatus());
		} else {
			projectService.validateProject(codeList.getProjectId(), accountId, projectId, checkFixdesign);
			codeList.setModuleStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
		}
		return codeList;
	}
	
	@Override
	public CodeList registerCodelist(CodeList codeList, Long accountId, Long projectId) {

		if (codeList.getModuleId() != null) {
			moduleService.validateModule(codeList.getModuleId(), accountId, projectId, true);
		} else {
			projectService.validateProject(codeList.getProjectId(), accountId, projectId, true);
		}

		//Check Name or code exist
		Long totalCount = codeListRepository.countNameCodeByCodeListId(codeList);

		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002));
		} 
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// register codelist
			codeList.setCreatedBy(accountId);
			codeList.setCreatedDate(FunctionCommon.getCurrentTime());
			codeList.setUpdatedDate(FunctionCommon.getCurrentTime());

			if (codeList.getIsOptionValude() == null) {
				codeList.setIsOptionValude(DbDomainConst.YesNoFlg.NO);
			}
			
			codeListRepository.registerCodelist(codeList);
			// register codelistDetail
			CodeListDetail[] codeListDetails = codeList.getCodelistDetails();
	
			if (codeListDetails != null && ArrayUtils.isNotEmpty(codeListDetails)) {
				ArrayList<CodeListDetail> clDetail =new ArrayList<CodeListDetail>();
				for(CodeListDetail codeListDetail: codeListDetails){
						codeListDetail.setCodeListId(codeList.getCodeListId());
						codeListDetail.setCreatedBy(accountId);
						codeListDetail.setCreatedDate(FunctionCommon.getCurrentTime());
						
						if (DbDomainConst.YesNoFlg.YES.equals(codeList.getIsOptionValude())) {
							codeListDetail.setName(null);
						}
						
						if (DbDomainConst.YesNoFlg.NO.equals(codeList.getMultivalueFlg())) {
							codeListDetail.setValue1(null);
							codeListDetail.setValue2(null);
							codeListDetail.setValue3(null);
							codeListDetail.setValue4(null);
							codeListDetail.setValue5(null);
						}
						clDetail.add(codeListDetail);
					}
				codelistDetailRepository.registerArray(clDetail);
			}
		}
		return codeList;
	}

	@Override
	public void modifyCodelist(CodeList codeList, Long accountId, Long projectId) throws BusinessException {
		CodeList codeListExist = validateCodeList(codeList.getCodeListId(), projectId, accountId, true);
		
		if(!codeListExist.getCodeListName().equals(codeList.getCodeListName()) || !codeListExist.getCodeListCode().equals(codeList.getCodeListCode())){
			//Check Name or code exist
			Long totalCount = codeListRepository.countNameCodeByCodeListId(codeList);
			ResultMessages resultMessages = ResultMessages.error();
			
			if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003));
			} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002));
			} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003));
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002));
			} 
			if (resultMessages.isNotEmpty()) {
				throw new BusinessException(resultMessages);
			} 
		}
		codeList.setUpdatedBy(accountId);
		codeList.setSystemTime(FunctionCommon.getCurrentTime());

		if (codeList.getIsOptionValude() == null) {
			codeList.setIsOptionValude(DbDomainConst.YesNoFlg.NO);
		}
		
		if (!codeListRepository.modifyCodelist(codeList)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		CodeListDetail[] codeListDetails=codeList.getCodelistDetails();
		ArrayList<CodeListDetail> clDetail =new ArrayList<CodeListDetail>();
		if (codeListDetails != null && ArrayUtils.isNotEmpty(codeListDetails)) {
			for (CodeListDetail codeListDetail : codeListDetails) {
				codeListDetail.setCodeListId(codeList.getCodeListId());
				codeListDetail.setCreatedBy(accountId);
				codeListDetail.setCreatedDate(FunctionCommon.getCurrentTime());

				if (DbDomainConst.YesNoFlg.YES.equals(codeList.getIsOptionValude())) {
					codeListDetail.setName(null);
				}

				if (DbDomainConst.YesNoFlg.NO.equals(codeList.getMultivalueFlg())) {
					codeListDetail.setValue1(null);
					codeListDetail.setValue2(null);
					codeListDetail.setValue3(null);
					codeListDetail.setValue4(null);
					codeListDetail.setValue5(null);
				}
				clDetail.add(codeListDetail);
			}
			
			codelistDetailRepository.deleteCodeListDetail(codeList.getCodeListId());
			codelistDetailRepository.registerArray(clDetail);
		}
	}

	@Override
	public CodeList deleteCodelist(CodeList codeList, Long accountId, Long projectId) {
		// TODO Auto-generated method stub
		CodeList codeListExist = validateCodeList(codeList.getCodeListId(), projectId, accountId, true);
		if (codeListRepository.checkCodelistUsedByOther(codeList.getCodeListId()) > 0) {
			codeListExist.setListDomainDesign(codeListRepository.getDomainDesignUsedCodelist(codeList.getCodeListId()));
			codeListExist.setListTableDesignItems(codeListRepository.getTableDesignUsedCodelist(codeList.getCodeListId()));
			codeListExist.setListScreenItem(codeListRepository.getScreenItemUsedCodelist(projectId, codeList.getCodeListId()));
			codeListExist.setResultMessages(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097,MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST)));
		} else {
			if (DateUtils.compare(codeListExist.getUpdatedDate(), codeList.getUpdatedDate()) == 1) {
				codeListExist.setResultMessages(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			} else {
				int rowCount = 0;
				// delete codelist Table Mapping
				rowCount = codelistDetailRepository.deleteCodeListDetail(codeList.getCodeListId());
				if (0 <= rowCount) {
					// delete codelist
					rowCount = codeListRepository.deleteCodelist(codeList.getCodeListId());
				}
				if (0 >= rowCount) {
					codeListExist.setResultMessages(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(CommonMessageConst.TQP_CODELIST)));
				} else {
					codeListExist = null;
				}
			}
		}

		return codeListExist;
	}

	@Override
	public Page<CodeList> getBySearchCriteria(CodeListSearchCriteria criteria, Pageable pageable) {

		long total = codeListRepository.countBySearchCriteria(criteria);
		List<CodeList> codeLists;
		if (0 < total) {
			codeLists = codeListRepository.getBySearchCriteria(criteria,pageable);
			/*for (CodeList c : codeLists) {
				if (c.getModuleId() == null)
					c.setModuleIdAutocomplete("System");
			}*/
		} else {
			codeLists = Collections.emptyList();
		}

		Page<CodeList> page = new PageImpl<CodeList>(codeLists, pageable, total);
		return page;
	}

	@Override
	public CodeListDetail[] getCodeListDetailByCodeListId(long codeListId) {
		// TODO Auto-generated method stub
		return codelistDetailRepository.findCodeListDetailByCodeListId(codeListId);
	}

	@Override
	public CodeList getCodeListInformation(long codeListId, Long accountId, Long currentProjectId) {
		CodeList codeList = validateCodeList(codeListId, currentProjectId, accountId, false);
		if (codeListRepository.checkCodelistUsedByOther(codeListId) > 0) {
			codeList.setListDomainDesign(codeListRepository.getDomainDesignUsedCodelist(codeListId));
			codeList.setListTableDesignItems(codeListRepository.getTableDesignUsedCodelist(codeListId));
			codeList.setListScreenItem(codeListRepository.getScreenItemUsedCodelist(currentProjectId, codeListId));
		}
		return codeList;
	}

}
