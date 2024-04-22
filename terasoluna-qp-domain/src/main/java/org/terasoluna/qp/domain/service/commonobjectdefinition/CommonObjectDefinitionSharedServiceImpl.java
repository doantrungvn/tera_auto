package org.terasoluna.qp.domain.service.commonobjectdefinition;

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
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionSearchCriteria;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.common.SystemService;

@Service
@Transactional
public class CommonObjectDefinitionSharedServiceImpl implements CommonObjectDefinitionSharedService {

	@Inject
	CommonObjectDefinitionRepository commonObjectDefinitionRepository;

	@Inject
	CommonObjectAttributeRepository commonObjectAttributeRepository;

	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;

	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;

	@Inject
	SystemService systemService;

	private List<CommonObjectDefinition> listOfCommonObjectDefinition;
	private List<CommonObjectAttribute> listOfCommonObjectAttribute;
	private List<ExternalObjectDefinition> listOfExternalObjectDefinition;
	private List<ExternalObjectAttribute> listOfExternalObjectAttribute;

	int itemSeqNo = 0;
	int maxLevel = 3;

	@Override
	public CommonObjectDefinition getCommonObjectDefinition(Long objectId, Long moduleId, Long projectId, Long languageId, Integer level) {

		CommonObjectDefinitionSearchCriteria criteria = new CommonObjectDefinitionSearchCriteria();
		criteria.setProjectId(projectId);
		criteria.setModuleId(moduleId);
		criteria.setLanguageId(languageId);

		CommonObjectDefinition commonObj = null;
		if(level == null){
			level = 0;
		}
		listOfCommonObjectDefinition = commonObjectDefinitionRepository.findAllOfProject(criteria);
		if (CollectionUtils.isNotEmpty(listOfCommonObjectDefinition)) {
			// Find object from list
			commonObj = getCommonObjectDefinitionFromList(objectId);
			if (null == commonObj) {
				return null;
			}

			listOfCommonObjectAttribute = commonObjectAttributeRepository.findAllOfProject(criteria);
			listOfExternalObjectDefinition = externalObjectDefinitionRepository.findAllOfProject(criteria);

			if (CollectionUtils.isNotEmpty(listOfExternalObjectDefinition)) {
				listOfExternalObjectAttribute = externalObjectAttributeRepository.findAllOfProject(criteria);
			}
			// get max level from confirm
			Integer maxLevelDefault = systemService.getDefaultProfile().getMaxNestedObject();
			if (maxLevelDefault != null) {
				maxLevel = maxLevelDefault.intValue();
			}

			prepareCommonObject(commonObj, level);

		}
		itemSeqNo = 0;

		calcTableIndexForCommonObject(commonObj.getCommonObjectAttributes(), "", level);
		return commonObj;
	}

	private void calcTableIndexForCommonObject(List<CommonObjectAttribute> commonObjectAttributes, String groupPref, int level) {
		if (level > maxLevel) {
			return;
		}

		Map<String, String> mapTableIndex = new HashMap<String, String>();
		// set level
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		if (!FunctionCommon.isEmpty(commonObjectAttributes)) {
			for (CommonObjectAttribute commonObjectAttribute : commonObjectAttributes) {
				String currentGroup = groupPref;
				if (!FunctionCommon.isEmpty(commonObjectAttribute.getParentCommonObjectAttributeId())) {
					currentGroup = mapTableIndex.getOrDefault(commonObjectAttribute.getParentCommonObjectAttributeId(),currentGroup);
				} else {
					currentGroup = groupPref;
				}

				commonObjectAttribute.setGroupId(currentGroup);
				commonObjectAttribute.setItemSeqNo(itemSeqNo);
				itemSeqNo++;

				int maxIndex = mapSequence.getOrDefault(commonObjectAttribute.getGroupId(), 0);
				maxIndex++;
				String tableIndex;
				if (FunctionCommon.isEmpty(currentGroup)) {
					tableIndex = String.valueOf(maxIndex);
				} else {
					tableIndex = currentGroup + "." + maxIndex;
				}
				commonObjectAttribute.setTableIndex(tableIndex);

				mapTableIndex.put(commonObjectAttribute.getCommonObjectAttributeId(), commonObjectAttribute.getTableIndex());
				mapSequence.put(commonObjectAttribute.getGroupId(), maxIndex);
				if (BusinessDesignConst.DataType.COMMON_OBJECT.equals(commonObjectAttribute.getDataType())) {
					if (commonObjectAttribute.getCommonObjectDefinition() != null) {
						calcTableIndexForCommonObject(commonObjectAttribute.getCommonObjectDefinition().getCommonObjectAttributes(), commonObjectAttribute.getTableIndex(), level + 1);
					}
				}
				if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(commonObjectAttribute.getDataType())) {
					if (commonObjectAttribute.getExternalObjectDefinition() != null) {
						calcTableIndexForExternalObject(commonObjectAttribute.getExternalObjectDefinition().getExternalObjectAttributes(), commonObjectAttribute.getTableIndex(), level + 1);
					}
				}
			}
		}
	}

	private void calcTableIndexForExternalObject(List<ExternalObjectAttribute> externalObjectAttributes, String groupPref, int level) {
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
						calcTableIndexForExternalObject(externalObjectAttribute.getExternalObjectDefinition().getExternalObjectAttributes(), externalObjectAttribute.getTableIndex(), level + 1);
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
	private CommonObjectDefinition getCommonObjectDefinitionFromList(Long objectId) {
		if (CollectionUtils.isNotEmpty(listOfCommonObjectDefinition)) {
			for (CommonObjectDefinition cod : listOfCommonObjectDefinition) {
				if (FunctionCommon.equals(objectId, cod.getCommonObjectDefinitionId())) {
					CommonObjectDefinition temp = SerializationUtils.clone(cod);
					return temp;
				}
			}
		}
		return null;
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
	 * @param cod
	 */
	private void prepareCommonObject(CommonObjectDefinition cod, int level) {
		int levelTemp = level;
		List<CommonObjectAttribute> lstAttributes = new ArrayList<CommonObjectAttribute>();
		if (level <= maxLevel && CollectionUtils.isNotEmpty(listOfCommonObjectAttribute)) {
			Map<String, Integer> mapSequence = new HashMap<String, Integer>();
			for (CommonObjectAttribute coa : listOfCommonObjectAttribute) {
				if (FunctionCommon.equals(cod.getCommonObjectDefinitionId(), coa.getCommonObjectDefinitionId())) {
					CommonObjectAttribute attributeTemp = SerializationUtils.clone(coa);
					if(attributeTemp.getParentCommonObjectAttributeId() != null){
						levelTemp = mapSequence.getOrDefault(attributeTemp.getParentCommonObjectAttributeId(), level);
					}else{
						levelTemp = level;
					}
					if (BusinessDesignConst.DataType.COMMON_OBJECT.equals(coa.getDataType())) {
						CommonObjectDefinition codTemp = getCommonObjectDefinitionFromList(attributeTemp.getObjectDefinitionId());
						if (null != codTemp) {
							prepareCommonObject(codTemp, levelTemp + 1);
							attributeTemp.setCommonObjectDefinition(codTemp);
						}
					} else if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(coa.getDataType())) {
						ExternalObjectDefinition eodTemp = getExternalObjectDefinitionFromList(attributeTemp.getObjectDefinitionId());
						if (null != eodTemp) {
							prepareExternalObject(eodTemp, levelTemp + 1);
							attributeTemp.setExternalObjectDefinition(eodTemp);
						}
					}else if(BusinessDesignConst.DataType.OBJECT.equals(coa.getDataType())){
						mapSequence.put(coa.getCommonObjectAttributeId(), levelTemp + 1);
					}

					lstAttributes.add(attributeTemp);
				}
			}
			
		}
		cod.setCommonObjectAttributes(lstAttributes);
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
				if (FunctionCommon.equals(eod.getExternalObjectDefinitionId(), eoa.getExternalObjectDefinitionId())) {
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
