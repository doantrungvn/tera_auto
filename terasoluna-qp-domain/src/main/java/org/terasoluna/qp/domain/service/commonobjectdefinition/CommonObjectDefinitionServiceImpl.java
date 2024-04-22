package org.terasoluna.qp.domain.service.commonobjectdefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.CommonObjectDefinitionMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionSearchCriteria;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class CommonObjectDefinitionServiceImpl implements CommonObjectDefinitionService {
	@Inject
	CommonObjectDefinitionRepository commonObjectDefinitionRepository;
	
	@Inject
	CommonObjectAttributeRepository commonObjectAttributeRepository;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	CommonObjectDefinitionSharedService commonShareService;

	@Inject
	CommonObjectDefinitionSharedService commonObjectDefinitionSharedService;

	@Inject
	ExternalObjectDefinitionSharedService externalObjectDefinitionSharedService;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;
	
	@Inject
	SessionManagementRepository sessionManagementRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	DecisionTableRepository decisionTableRepository;
	
	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject
	ImpactChangeRepository impactChangeRepository;

	private Map<String, Long> mKeyDesignItemClient = new HashMap<String, Long>();

	private static final String OBJECT_CODE = "co";

	@Override
	public Page<CommonObjectDefinition> findPageByCriteria(CommonObjectDefinitionSearchCriteria criteria, Pageable pageable, CommonModel common) {
		List<CommonObjectDefinition> lstCommonObjectDefinitions;
		criteria.setProjectId(common.getWorkingProjectId());
		long totalCount = commonObjectDefinitionRepository.countBySearchCriteria(criteria);
		if (0 < totalCount) {
			lstCommonObjectDefinitions = commonObjectDefinitionRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			lstCommonObjectDefinitions = Collections.emptyList();
		}
		Page<CommonObjectDefinition> page = new PageImpl<CommonObjectDefinition>(lstCommonObjectDefinitions, pageable, totalCount);
		return page;
	}

	@Override
	public CommonObjectDefinition findCommonObjectDefinition(Long commonObjectDefinitionSearchCriteriaId, CommonModel common) {

		CommonObjectDefinition commonObjectDefinition = commonShareService.getCommonObjectDefinition(commonObjectDefinitionSearchCriteriaId, null, common.getWorkingProjectId(), common.getWorkingLanguageId(),null);

		if (commonObjectDefinition == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_COMMONOBJECTDEFINITION)));
		}
		return commonObjectDefinition;
	}

	@Override
	public CommonObjectDefinition registerCommonObjectDefinition(CommonObjectDefinition commonObjectDefinition, CommonModel common) {

		// Check Name or code exist
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		commonObjectDefinition.setCreatedDate(currentTime);
		commonObjectDefinition.setUpdatedDate(currentTime);
		commonObjectDefinition.setProjectId(common.getWorkingProjectId());
		this.checkDuplidateNameAndCode(commonObjectDefinition);
		commonObjectDefinitionRepository.registerCommonObjectDefinition(commonObjectDefinition);

		mKeyDesignItemClient.clear();
		Long commonObjectDefinitionId = commonObjectDefinition.getCommonObjectDefinitionId();
		List<CommonObjectAttribute> lstCommonObjectAttributes = new ArrayList<CommonObjectAttribute>();

		int itemSeqNo = 0;
		
		if (commonObjectDefinition.getCommonObjectAttributes() != null) {
	        for (CommonObjectAttribute commonObjectAttribute : commonObjectDefinition.getCommonObjectAttributes()) {
	            if (commonObjectAttribute.getSaveFlg()) {
	                commonObjectAttribute.setItemSeqNo(itemSeqNo);
	                lstCommonObjectAttributes.add(commonObjectAttribute);
	                itemSeqNo++;
	            }
	        }

	        this.registerAttribute(commonObjectDefinitionId, lstCommonObjectAttributes);
		}
		return commonObjectDefinition;
	}

	/**
	 * @param commonObjectDefinitionId
	 * @param commonObjectAttributes
	 */
	private void registerAttribute(Long commonObjectDefinitionId, List<CommonObjectAttribute> commonObjectAttributes) {
		if (FunctionCommon.isNotEmpty(commonObjectAttributes)) {
			Long startSequence;
			// int itemSeqNo = 0;
			Long sequencesCommonObjectAttribute = commonObjectDefinitionRepository.getSequencesCommonObjectAttribute(commonObjectAttributes.size() - 1);
			startSequence = sequencesCommonObjectAttribute - (commonObjectAttributes.size() - 1);
			Map<String, Long> mapKeyInput = new HashMap<String, Long>();

			for (CommonObjectAttribute objectAttribute : commonObjectAttributes) {
				// objectAttribute.setItemSeqNo(itemSeqNo);
				mapKeyInput.put(objectAttribute.getCommonObjectAttributeId(), startSequence);
				mKeyDesignItemClient.put(objectAttribute.getCommonObjectAttributeId().toString(), startSequence);
				objectAttribute.setCommonObjectAttributeId(startSequence.toString());
				startSequence++;
				if ("".equals(objectAttribute.getParentCommonObjectAttributeId())) {
					objectAttribute.setParentCommonObjectAttributeId(null);
				}

				// map key of parent
				if (mapKeyInput.containsKey(objectAttribute.getParentCommonObjectAttributeId())) {
					objectAttribute.setParentCommonObjectAttributeId(mapKeyInput.get(objectAttribute.getParentCommonObjectAttributeId()).toString());
				}

				objectAttribute.setCommonObjectDefinitionId(commonObjectDefinitionId);
			}

			commonObjectAttributeRepository.registerArray(commonObjectAttributes);
		}
	}

	private CommonObjectDefinition validateCommonObjectDefinition(long commonObjectDefinitionId) {
		CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionRepository.findCommonObjectDefinition(commonObjectDefinitionId);
		if (commonObjectDefinition == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_COMMONOBJECTDEFINITION)));
		}
		return commonObjectDefinition;
	}

	@Override
	public CommonObjectDefinition deleteCommonObjectDefinition(CommonObjectDefinition commonObjectDefinition, CommonModel common) {
		CommonObjectDefinition commonObjectDefinitionExist = validateCommonObjectDefinition(commonObjectDefinition.getCommonObjectDefinitionId());
		int rowCount = 0;
		// delete detail
		rowCount = commonObjectAttributeRepository.deleteCommonObjectAttribute(commonObjectDefinition.getCommonObjectDefinitionId());
		if (0 <= rowCount) {
			// delete master
			rowCount = commonObjectDefinitionRepository.deleteCommonObjectDefinition(commonObjectDefinition.getCommonObjectDefinitionId());
			deleteAffected(commonObjectDefinitionExist, common);
		}
		commonObjectDefinitionExist = null;

		return commonObjectDefinitionExist;
	}

	@Override
	public void modifyCommonObjectDefinition(CommonObjectDefinition commonObjectDefinition, CommonModel common) {

		commonObjectDefinition.setProjectId(common.getWorkingProjectId());
		commonObjectDefinition.setUpdatedBy(common.getUpdatedBy());
		CommonObjectDefinition commonObjectDefinitionExist = validateCommonObjectDefinition(commonObjectDefinition.getCommonObjectDefinitionId());

		if (!commonObjectDefinitionExist.getCommonObjectDefinitionName().equals(commonObjectDefinition.getCommonObjectDefinitionName()) || !commonObjectDefinitionExist.getCommonObjectDefinitionCode().equals(commonObjectDefinition.getCommonObjectDefinitionCode())) {
			// Check Name or code exist
			this.checkDuplidateNameAndCode(commonObjectDefinition);
		}
		commonObjectDefinition.setSystemTime(FunctionCommon.getCurrentTime());

		if (!commonObjectDefinitionRepository.modifyCommonObjectDefinition(commonObjectDefinition)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		List<CommonObjectAttribute> before = commonObjectAttributeRepository.findCommonObjectAttributeFillterDataType(commonObjectDefinition.getCommonObjectDefinitionId());
		List<CommonObjectAttribute> after = commonObjectDefinition.getCommonObjectAttributes();

		List<CommonObjectAttribute> commonObjectAttributesInsert = new ArrayList<CommonObjectAttribute>();
		List<CommonObjectAttribute> commonObjectAttributesUpdate = new ArrayList<CommonObjectAttribute>();
		List<CommonObjectAttribute> commonObjectAttributesDelete = new ArrayList<CommonObjectAttribute>();

		int index = 0;
		for (CommonObjectAttribute input : after) {
			input.setItemSeqNo(index);
			if (input.getSaveFlg()) {
				index++;
				if (input.getCommonObjectAttributeId() != null && input.getCommonObjectAttributeId().contains(OBJECT_CODE)) {
					commonObjectAttributesInsert.add(input);
					input.setCommonObjectDefinitionId(commonObjectDefinition.getCommonObjectDefinitionId());
				}
			}
		}

		boolean check = false;
		for (CommonObjectAttribute inputBefore : before) {
			check = false;
			for (CommonObjectAttribute inputAfter : after) {
				if (inputBefore.getCommonObjectAttributeId().equals(inputAfter.getCommonObjectAttributeId())) {
					commonObjectAttributesUpdate.add(inputAfter);
					check = true;
					break;
				}
			}
			if (!check) {
				commonObjectAttributesDelete.add(inputBefore);
			}
		}

		if (FunctionCommon.isNotEmpty(commonObjectAttributesInsert)) {
			this.registerAttribute(commonObjectDefinition.getCommonObjectDefinitionId(), commonObjectAttributesInsert);
		}
		if (FunctionCommon.isNotEmpty(commonObjectAttributesUpdate)) {
			commonObjectAttributeRepository.multiUpdateCommonObjectAttribut(commonObjectAttributesUpdate);
		}
		if (FunctionCommon.isNotEmpty(commonObjectAttributesDelete)) {
			commonObjectAttributeRepository.multiDeleteCommonObjectAttribut(commonObjectAttributesDelete);
		}
		modifyAffected(commonObjectDefinition, common);
	}

	/**
	 * @param commonObjectDefinition
	 * @throws BusinessException
	 */
	private void checkDuplidateNameAndCode(CommonObjectDefinition commonObjectDefinition) throws BusinessException {

		Long totalCount = commonObjectDefinitionRepository.countNameCodeByCommonObjectDefinitionId(commonObjectDefinition);
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0002));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0003));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0002));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0003));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}
	}

	@Override
	public void setLevelCommonObject(List<CommonObjectAttribute> commonObjectAttributes) {

		if (FunctionCommon.isEmpty(commonObjectAttributes)) {
			return;
		}

		// set level of input bean
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		for (CommonObjectAttribute commonObjectAttribute : commonObjectAttributes) {
			if (commonObjectAttribute.getCommonObjectAttributeId().split(",").length > 0) {
				commonObjectAttribute.setCommonObjectAttributeId(commonObjectAttribute.getCommonObjectAttributeId().split(",")[0]);
			}
			String currentGroup = "";
			if (!FunctionCommon.isEmpty(commonObjectAttribute.getParentCommonObjectAttributeId())) {
				currentGroup = mapTableIndex.get(commonObjectAttribute.getParentCommonObjectAttributeId());
			}

			commonObjectAttribute.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(commonObjectAttribute.getGroupId(), 0);
			maxIndex++;
			if (commonObjectAttribute.getParentCommonObjectAttributeId() == null || commonObjectAttribute.getParentCommonObjectAttributeId() == "") {
				commonObjectAttribute.setTableIndex(String.valueOf(maxIndex));
			} else {
				commonObjectAttribute.setTableIndex(currentGroup + "." + maxIndex);
			}

			mapTableIndex.put(commonObjectAttribute.getCommonObjectAttributeId(), commonObjectAttribute.getTableIndex());
			mapSequence.put(commonObjectAttribute.getGroupId(), maxIndex);
		}

	}

	@Override
	public List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(Long commonObjectDefinitionId,Integer level, CommonModel common) {
		List<CommonObjectAttribute> lstCommonObjectAttributes = new ArrayList<CommonObjectAttribute>();

		CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionSharedService.getCommonObjectDefinition(commonObjectDefinitionId, null, common.getWorkingProjectId(), common.getWorkingLanguageId(),level);

		for (CommonObjectAttribute objDetail : commonObjectDefinition.getCommonObjectAttributes()) {
			lstCommonObjectAttributes.add(objDetail);
		}
		return lstCommonObjectAttributes;
	}

	@Override
	public List<ExternalObjectAttribute> findExternalObjectAttributeByCommonObject(Long externalObjectDefinitionId, CommonModel common) {
		List<ExternalObjectAttribute> lstExternalObjectAttributes = new ArrayList<ExternalObjectAttribute>();

		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionSharedService.getExternalObjectDefinition(externalObjectDefinitionId, null, common.getWorkingProjectId(), common.getWorkingLanguageId(),1);

		for (ExternalObjectAttribute objDetail : externalObjectDefinition.getExternalObjectAttributes()) {
			lstExternalObjectAttributes.add(objDetail);
		}

		return lstExternalObjectAttributes;
	}

	@Override
	public boolean checkCommonObjectDefinitionExistById(Long commonObjectDefinitionId) {
		int count = this.commonObjectDefinitionRepository.checkCommonObjectDefinitionExistById(commonObjectDefinitionId);
		if(count == 0){
			return false;
		}
		return true;
	}
	
	private void modifyAffected(CommonObjectDefinition commonObjectDefinition,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
		jobControl.setModuleId(String.valueOf(commonObjectDefinition.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.COMMON_OBJECT));
		jobControl.setImpactId(String.valueOf(commonObjectDefinition.getCommonObjectDefinitionId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
    
    private void deleteAffected(CommonObjectDefinition commonObjectDefinition,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
		jobControl.setModuleId(String.valueOf(commonObjectDefinition.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.COMMON_OBJECT));
		jobControl.setImpactId(String.valueOf(commonObjectDefinition.getCommonObjectDefinitionId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(commonObjectDefinition.getCommonObjectDefinitionCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
}
