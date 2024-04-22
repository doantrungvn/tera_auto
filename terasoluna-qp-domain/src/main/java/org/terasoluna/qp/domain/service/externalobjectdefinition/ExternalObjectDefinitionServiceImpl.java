package org.terasoluna.qp.domain.service.externalobjectdefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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
import org.terasoluna.qp.app.message.ExternalObjectDefinitionMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionSearchCriteria;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class ExternalObjectDefinitionServiceImpl implements ExternalObjectDefinitionService {
	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;
	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;
	@Inject
	ModuleService moduleService;
	@Inject
	ProjectService projectService;

	@Inject
	ExternalObjectDefinitionSharedService externalObjectDefinitionSharedService;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;
	
	@Inject
	SessionManagementRepository sessionManagementRepository;
	
	@Inject
	CommonObjectDefinitionRepository commonObjectDefinitionRepository;
	
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

	private static final String OBJECT_CODE = "eo";

	@Override
	public Page<ExternalObjectDefinition> findPageByCriteria(ExternalObjectDefinitionSearchCriteria criteria, Pageable pageable, CommonModel common) {
		List<ExternalObjectDefinition> lstExternalObjectDefinitions;
		criteria.setProjectId(common.getWorkingProjectId());
		long totalCount = externalObjectDefinitionRepository.countBySearchCriteria(criteria);
		if (0 < totalCount) {
			lstExternalObjectDefinitions = externalObjectDefinitionRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			lstExternalObjectDefinitions = Collections.emptyList();
		}
		Page<ExternalObjectDefinition> page = new PageImpl<ExternalObjectDefinition>(lstExternalObjectDefinitions, pageable, totalCount);
		return page;
	}

	@Override
	public ExternalObjectDefinition findExternalObjectDefinition(Long externalObjectDefinitionId,Integer level, CommonModel common) {
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionSharedService.getExternalObjectDefinition(externalObjectDefinitionId, null, common.getWorkingProjectId(), common.getWorkingLanguageId(),level);

		if (externalObjectDefinition == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION)));
		}
		return externalObjectDefinition;
	}

	@Override
	public ExternalObjectDefinition registerExternalObjectDefinition(ExternalObjectDefinition externalObjectDefinition, CommonModel common) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		externalObjectDefinition.setClassName(externalObjectDefinition.getPackageName()+DbDomainConst.CHARACTER_DOT + externalObjectDefinition.getExternalObjectDefinitionCode());
		externalObjectDefinition.setCreatedDate(currentTime);
		externalObjectDefinition.setCreatedBy(common.getCreatedBy());
		externalObjectDefinition.setUpdatedDate(currentTime);
		externalObjectDefinition.setUpdatedBy(common.getCreatedBy());
		externalObjectDefinition.setProjectId(common.getWorkingProjectId());
		// Check Name or code exist
		this.checkDuplidateNameAndCode(externalObjectDefinition);
		externalObjectDefinitionRepository.registerExternalObjectDefinition(externalObjectDefinition);

		Long externalObjectDefinitionId = externalObjectDefinition.getExternalObjectDefinitionId();
		List<ExternalObjectAttribute> lstExternalObjectAttributes = new ArrayList<ExternalObjectAttribute>();

		if (!FunctionCommon.isEmpty(externalObjectDefinition.getExternalObjectAttributes())) {
			int itemSeqNo = 1;
			for (ExternalObjectAttribute externalObjectAttribute : externalObjectDefinition.getExternalObjectAttributes()) {
				if (Boolean.TRUE.equals(externalObjectAttribute.getSaveFlg())) {
					externalObjectAttribute.setItemSeqNo(itemSeqNo);
					lstExternalObjectAttributes.add(externalObjectAttribute);
					itemSeqNo++;
				}
			}
			this.registerAttribute(externalObjectDefinitionId, lstExternalObjectAttributes, true);
		}
		return externalObjectDefinition;
	}

	private List<ExternalObjectAttribute> registerAttribute(Long externalObjectDefinitionId, List<ExternalObjectAttribute> externalObjectAttributes, boolean isRegister) {
		if (FunctionCommon.isNotEmpty(externalObjectAttributes)) {
			Long startSequence;
			Long sequencesExternalObjectAttribute = externalObjectDefinitionRepository.getSequencesExternalObjectAttribute(externalObjectAttributes.size() - 1);
			startSequence = sequencesExternalObjectAttribute - (externalObjectAttributes.size() - 1);
			Map<String, Long> mapKeyInput = new HashMap<String, Long>();

			for (ExternalObjectAttribute objectAttribute : externalObjectAttributes) {
				mapKeyInput.put(objectAttribute.getExternalObjectAttributeId(), startSequence);
				objectAttribute.setExternalObjectAttributeId(startSequence.toString());
				startSequence++;

				// map key of parent
				if (StringUtils.isNotBlank(objectAttribute.getParentExternalObjectAttributeId()) && mapKeyInput.containsKey(objectAttribute.getParentExternalObjectAttributeId())){
					objectAttribute.setParentExternalObjectAttributeId(mapKeyInput.get(objectAttribute.getParentExternalObjectAttributeId()).toString());
				}else{
					objectAttribute.setParentExternalObjectAttributeId(null);
				}

				objectAttribute.setExternalObjectDefinitionId(externalObjectDefinitionId);
			}

			externalObjectAttributeRepository.registerArray(externalObjectAttributes);
		}

		return externalObjectAttributes;
	}

	private ExternalObjectDefinition validateExternalObjectDefinition(long externalObjectDefinitionId, boolean isDeleteAction) {
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExternalObjectDefinition(externalObjectDefinitionId);
		if (externalObjectDefinition == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION)));
		} else if (externalObjectDefinition.getExternalObjectType() == null ||  "0".equals(externalObjectDefinition.getExternalObjectType())){
			if(isDeleteAction){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0199, 
						MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION),externalObjectDefinition.getExternalObjectDefinitionName()));
			} else {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0200, 
						MessageUtils.getMessage(CommonMessageConst.TQP_EXTERNALOBJECTDEFINITION),externalObjectDefinition.getExternalObjectDefinitionName()));
			}
		}

		return externalObjectDefinition;
	}

	@Override
	public ExternalObjectDefinition deleteExternalObjectDefinition(ExternalObjectDefinition externalObjectDefinition, CommonModel common) {
		ExternalObjectDefinition externalObjectDefinitionExist = validateExternalObjectDefinition(externalObjectDefinition.getExternalObjectDefinitionId(),true);

		int rowCount = 0;
		rowCount = externalObjectAttributeRepository.deleteExternalObjectAttribute(externalObjectDefinition.getExternalObjectDefinitionId());
		if (0 <= rowCount) {
			rowCount = externalObjectDefinitionRepository.deleteExternalObjectDefinition(externalObjectDefinition.getExternalObjectDefinitionId());
			//insert batch job
			deleteAffected(externalObjectDefinitionExist, common);
			externalObjectDefinitionExist = null;
		}
		return externalObjectDefinitionExist;
	}

	@Override
	public void modifyExternalObjectDefinition(ExternalObjectDefinition externalObjectDefinition, CommonModel common) {
		ExternalObjectDefinition externalObjectDefinitionExist = validateExternalObjectDefinition(externalObjectDefinition.getExternalObjectDefinitionId(),false);

		externalObjectDefinition.setClassName(externalObjectDefinition.getPackageName()+DbDomainConst.CHARACTER_DOT + externalObjectDefinition.getExternalObjectDefinitionCode());
		externalObjectDefinition.setUpdatedBy(common.getCreatedBy());
		if (!externalObjectDefinitionExist.getExternalObjectDefinitionName().equals(externalObjectDefinition.getExternalObjectDefinitionName()) || !externalObjectDefinitionExist.getExternalObjectDefinitionCode().equals(externalObjectDefinition.getExternalObjectDefinitionCode()) || !externalObjectDefinitionExist.getPackageName().equals(externalObjectDefinition.getPackageName())) {
			// Check Name or code exist
			this.checkDuplidateNameAndCode(externalObjectDefinition);
		}
		externalObjectDefinition.setSystemTime(FunctionCommon.getCurrentTime());

		if (!externalObjectDefinitionRepository.modifyExternalObjectDefinition(externalObjectDefinition)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		List<ExternalObjectAttribute> before = externalObjectAttributeRepository.findExternalObjectAttributeFillterDataType(externalObjectDefinition.getExternalObjectDefinitionId());
		List<ExternalObjectAttribute> after = externalObjectDefinition.getExternalObjectAttributes();

		List<ExternalObjectAttribute> externalObjectAttributesInsert = new ArrayList<ExternalObjectAttribute>();
		List<ExternalObjectAttribute> externalObjectAttributesUpdate = new ArrayList<ExternalObjectAttribute>();
		List<ExternalObjectAttribute> externalObjectAttributesUpdateWithChange = new ArrayList<ExternalObjectAttribute>();
		Map<ExternalObjectAttribute,ExternalObjectAttribute> mapExternalOBAttributesUpdates = new HashMap<ExternalObjectAttribute, ExternalObjectAttribute>();
		List<ExternalObjectAttribute> externalObjectAttributesDelete = new ArrayList<ExternalObjectAttribute>();

		int index = 0;
		for (ExternalObjectAttribute input : after) {
			input.setItemSeqNo(index);
			if (input.getSaveFlg()){
				index++;

				if (input.getExternalObjectAttributeId() != null && input.getExternalObjectAttributeId().contains(OBJECT_CODE)) {
					externalObjectAttributesInsert.add(input);
					input.setExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
				}
			}
		}

		boolean check = false;
		for (ExternalObjectAttribute inputBefore : before) {
			check = false;
			for (ExternalObjectAttribute inputAfter : after) {
				if (inputBefore.getExternalObjectAttributeId().equals(inputAfter.getExternalObjectAttributeId())) {
					externalObjectAttributesUpdate.add(inputAfter);
					check = true;
					
					if(FunctionCommon.notEquals(inputBefore.getExternalObjectAttributeName(), inputAfter.getExternalObjectAttributeName())
							|| FunctionCommon.notEquals(inputBefore.getExternalObjectAttributeCode(), inputAfter.getExternalObjectAttributeCode())
							|| FunctionCommon.notEquals(inputBefore.getDataType(), inputAfter.getDataType())
							|| FunctionCommon.notEquals(inputBefore.getArrayFlg(), inputAfter.getArrayFlg())){
						mapExternalOBAttributesUpdates.put(inputAfter, inputBefore);
						externalObjectAttributesUpdateWithChange.add(inputAfter);
					}
					break;
				}
			}
			if (!check) {
				externalObjectAttributesDelete.add(inputBefore);
			}
		}

		if (FunctionCommon.isNotEmpty(externalObjectAttributesInsert)) {
			this.registerAttribute(externalObjectDefinition.getExternalObjectDefinitionId(), externalObjectAttributesInsert, false);
		}
		if (FunctionCommon.isNotEmpty(externalObjectAttributesUpdate)) {
			externalObjectAttributeRepository.multiUpdateExternalObjectAttribut(externalObjectAttributesUpdate);
		}
		if (FunctionCommon.isNotEmpty(externalObjectAttributesDelete)) {
			externalObjectAttributeRepository.multiDeleteExternalObjectAttribut(externalObjectAttributesDelete);
		}
		//insert batchjob
		modifyAffected(externalObjectDefinitionExist, common);
//		detectProblemWhenModify(externalObjectDefinition,externalObjectDefinitionExist,externalObjectAttributesInsert,externalObjectAttributesUpdateWithChange,mapExternalOBAttributesUpdates,externalObjectAttributesDelete, common);
	}

	/**
	 * @param externalObjectDefinition
	 * @throws BusinessException
	 */
	private void checkDuplidateNameAndCode(ExternalObjectDefinition externalObjectDefinition) throws BusinessException {
		// Check Name or code exist
		Long totalCount = externalObjectDefinitionRepository.countNameCodeByExternalObjectDefinitionId(externalObjectDefinition);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0002));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0016));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0002));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0016));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}
	}

	@Override
	public boolean checkExternalObjectDefinitionById(Long externalObjectDefinitionId) {
		int check = this.externalObjectDefinitionRepository.checkExternalObjectDefinitionById(externalObjectDefinitionId);
		return check == 0 ? false : true;
	}
	
	private void modifyAffected(ExternalObjectDefinition externalObjectDefinition,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
		jobControl.setModuleId(String.valueOf(externalObjectDefinition.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.EXTERNAL_OBJECT));
		jobControl.setImpactId(String.valueOf(externalObjectDefinition.getExternalObjectDefinitionId()));
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
    
    private void deleteAffected(ExternalObjectDefinition externalObjectDefinition,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
		jobControl.setModuleId(String.valueOf(externalObjectDefinition.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.EXTERNAL_OBJECT));
		jobControl.setImpactId(String.valueOf(externalObjectDefinition.getExternalObjectDefinitionId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(externalObjectDefinition.getExternalObjectDefinitionCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
}