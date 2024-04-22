package org.terasoluna.qp.domain.service.externalobjectdefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableInputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableOutputBeanRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionSearchCriteria;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.common.SystemService;

@Service
@Transactional
public class ExternalObjectDefinitionSharedServiceImpl implements ExternalObjectDefinitionSharedService {

	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;

	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;

	private List<ExternalObjectDefinition> listOfExternalObjectDefinition;
	private List<ExternalObjectAttribute> listOfExternalObjectAttribute;
	
	@Inject
	ExternalObjectDefinitionService externalObjectDefinitionService;
	
	@Inject
	SystemService systemService;
	
	@Inject
	CommonObjectAttributeRepository commonObjectAttributeRepository;
	
	@Inject
	SessionManagementRepository sessionManagementRepository;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;
	
	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;
	
	@Inject
	SqlDesignOutputRepository sqlDesignOutputRepository;
	
	@Inject
	DecisionTableInputBeanRepository decisionTableInputBeanRepository;
	
	@Inject
	DecisionTableOutputBeanRepository decisionTableOutputBeanRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;

    int itemSeqNo = 0;
    int maxLevel  = 3;
    
	@Override
	public ExternalObjectDefinition getExternalObjectDefinition(Long objectId, Long moduleId, Long projectId, Long languageId,Integer level) {

		ExternalObjectDefinitionSearchCriteria criteria = new ExternalObjectDefinitionSearchCriteria();
		criteria.setProjectId(projectId);
		criteria.setModuleId(moduleId);
		criteria.setLanguageId(languageId);

		ExternalObjectDefinition externalObj = null;

		listOfExternalObjectDefinition = externalObjectDefinitionRepository.findAllOfProject(criteria);
		if(level == null){
			level = 0;
		}

		if (CollectionUtils.isNotEmpty(listOfExternalObjectDefinition)) {
			listOfExternalObjectAttribute = externalObjectAttributeRepository.findAllOfProject(criteria);

			externalObj = getExternalObjectDefinitionFromList(objectId);

			if (null == externalObj) {
				return null;
			}

			//get max level from configuration
			Integer maxLevelDefault = systemService.getDefaultProfile().getMaxNestedObject();
			if (maxLevelDefault != null){
				maxLevel = maxLevelDefault.intValue();
			}
			
			prepareExternalObject(externalObj, level);
		}
		
		itemSeqNo = 0;
		calcTableIndex(externalObj.getExternalObjectAttributes(), "", level);
		return externalObj;
	}

	private void calcTableIndex(List<ExternalObjectAttribute> externalObjectAttributes, String groupPref, int level) {
		if (level > maxLevel) {
			return;
		}
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		// set level
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		if (!FunctionCommon.isEmpty(externalObjectAttributes)) {
			for (ExternalObjectAttribute externalObjectAttribute : externalObjectAttributes) {
				String currentGroup = groupPref;
				if (!FunctionCommon.isEmpty(externalObjectAttribute.getParentExternalObjectAttributeId())) {
					currentGroup = mapTableIndex.get(externalObjectAttribute.getParentExternalObjectAttributeId());
				} else {
					currentGroup = groupPref;
				}

				externalObjectAttribute.setGroupId(currentGroup);
				externalObjectAttribute.setItemSeqNo(itemSeqNo);
				itemSeqNo++;    
				int maxIndex = mapSequence.getOrDefault(externalObjectAttribute.getGroupId(), 0);
				maxIndex++;
				String tableIndex;
				if (FunctionCommon.isEmpty(currentGroup)) {
					tableIndex = String.valueOf(maxIndex);
				} else {
					tableIndex = currentGroup + "." + maxIndex;
				}
				externalObjectAttribute.setTableIndex(tableIndex);

				mapTableIndex.put(externalObjectAttribute.getExternalObjectAttributeId(), externalObjectAttribute.getTableIndex());
				mapSequence.put(externalObjectAttribute.getGroupId(), maxIndex);
				if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(externalObjectAttribute.getDataType())) {
					if (externalObjectAttribute.getExternalObjectDefinition() != null) {
						calcTableIndex(externalObjectAttribute.getExternalObjectDefinition().getExternalObjectAttributes(), externalObjectAttribute.getTableIndex(), level+1);
					}
				}
			}
		}
	}

	/**
	 * Find object from list
	 *
	 * @param objectId
	 * @return
	 */
	private ExternalObjectDefinition getExternalObjectDefinitionFromList(Long objectId) {
		if (CollectionUtils.isNotEmpty(listOfExternalObjectDefinition)) {
			for (ExternalObjectDefinition eod : listOfExternalObjectDefinition) {
				if (FunctionCommon.equals(objectId, eod.getExternalObjectDefinitionId())) {
					ExternalObjectDefinition temp = SerializationUtils.clone(eod);
					return temp;
				}
			}
		}
		return null;
	}

	/**
	 * recursive input child object
	 *
	 * @param eod
	 */
	
	private void prepareExternalObject(ExternalObjectDefinition eod, int level) {
		List<ExternalObjectAttribute> lstExternalObjectAttributes = new ArrayList<ExternalObjectAttribute>();
		if (level <= maxLevel && CollectionUtils.isNotEmpty(listOfExternalObjectAttribute)) {
			for (ExternalObjectAttribute eoa : listOfExternalObjectAttribute) {
				if(FunctionCommon.equals(eod.getExternalObjectDefinitionId(), eoa.getExternalObjectDefinitionId())){
					ExternalObjectAttribute attributeTemp = SerializationUtils.clone(eoa);
					if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(eoa.getDataType())) {
						ExternalObjectDefinition eodTemp = getExternalObjectDefinitionFromList(attributeTemp.getObjectDefinitionId());
						if (null != eodTemp) {
							prepareExternalObject(eodTemp, level+1);
							attributeTemp.setExternalObjectDefinition(eodTemp);
						}
					}
					lstExternalObjectAttributes.add(attributeTemp);
				}
			}
		}
		eod.setExternalObjectAttributes(lstExternalObjectAttributes);
	}

}
