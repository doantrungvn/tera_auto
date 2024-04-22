package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.FunctionType;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ImportFileComponentRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component(value="CommonObjectDefinitionGenerateHandler")
public class CommonObjectDefinitionGenerateHandler extends GenerationHandler {
	
	@Inject
	SystemService systemService;

    @Inject
    CommonObjectDefinitionRepository commonObjectDefinitionRepository;

    @Inject
    CommonObjectAttributeRepository commonObjectAttributeRepository;
    
    @Inject
    ExternalObjectDefinitionRepository externalObjectDefinitionRepository;
    
    @Inject
    ImportFileComponentRepository importFileComponentRepository;
	
	private static final String TEMPLATE_COMMON_OBJECT_DEFINITION = "common_object_definition_java.ftl";
    private static final String TEMPLATE_OBJECT_OF_COMMON_OBJECT_DEFINITION = "common_object_definition_object_java.ftl";

	private StringBuilder pathPackage;

    private List<CommonObjectDefinition> listOfCommonObjectDefinition;
    private List<CommonObjectAttribute> listOfCommonObjectAttribute;
	
	private void init(GenerateSourceCode generateSourceCode) {
		// Setting pakage folder source
		String[] split = null;
		if (StringUtils.isNotBlank(generateSourceCode.getProject().getPackageName())) {
			split = generateSourceCode.getProject().getPackageName().split("\\.");
		}
		if (split != null && split.length > 0) {
			pathPackage = new StringBuilder();
			for (String str : split) {
				pathPackage.append(str).append(File.separator);
			}
		}
	}
	
	@Override
	public void handle(GenerateSourceCode generateSourceCode,  CommonModel common) {
		init(generateSourceCode);	
		if (generateSourceCode.getBatchModuleFlg()) {
			generateCommonObject(generateSourceCode, FunctionType.BATCH, common);
		}
		generateCommonObject(generateSourceCode, FunctionType.ONLINE, common);
	}

	private void generateCommonObject(GenerateSourceCode generateSourceCode, Integer moduleType, CommonModel common){
		
		OutputStreamWriter out = null;
	    try {
            Long projectId = common.getWorkingProjectId();
            listOfCommonObjectDefinition = commonObjectDefinitionRepository.findAllByModuleType(projectId, common.getWorkingLanguageId(), moduleType);
            // Modify by HungHX
//            listOfCommonObjectAttribute = commonObjectAttributeRepository.findAllByModuleType(projectId, generateSourceCode.getLanguageId(), moduleType);
            listOfCommonObjectAttribute = commonObjectAttributeRepository.findAllCommonObjAttrByModuleType(projectId, common.getWorkingLanguageId(), moduleType);
            
            if (CollectionUtils.isNotEmpty(listOfCommonObjectDefinition)) {
            	String moduleTypeStr = "";
                String outputDir = "";
                String branchFolderPath = "";
                if( moduleType == FunctionType.ONLINE){
            		outputDir = createFileOutputFolder(branchFolderPath, generateSourceCode.getSourcePathDomain(), "domain");
            		moduleTypeStr = "domain";
            	}
            	else {
            		outputDir = createFileOutputFolder(branchFolderPath, generateSourceCode.getSourcePathBatch(), "batch");
            		moduleTypeStr = "batch";
            	}
                Map<String, String> mapCommonModule = new HashMap<String, String>();
                String ouputDirCommonObject = "";
                for (CommonObjectDefinition commonObjectDefinition : listOfCommonObjectDefinition) {
                    ouputDirCommonObject = outputDir;
                    //generate Common Object difinition of module
                    if( moduleType == FunctionType.ONLINE){
                        //Generate package of module define
                        if (commonObjectDefinition.getModule()!= null && StringUtils.isNotEmpty(commonObjectDefinition.getModule().getModuleCode())) {
                            if (mapCommonModule.containsKey(commonObjectDefinition.getModule().getModuleCode())) {
                                ouputDirCommonObject = mapCommonModule.get(commonObjectDefinition.getModule().getModuleCode());
                            }else {
                                branchFolderPath = commonObjectDefinition.getModule().getModuleCode();
                                ouputDirCommonObject = createFileOutputFolder(branchFolderPath, generateSourceCode.getSourcePathDomain(), "domain");
                                mapCommonModule.put(commonObjectDefinition.getModule().getModuleCode(), ouputDirCommonObject);
                            }
                        }else {
                            //do nothing
                        }
                    }
                    else {
                    	//Generate package of module define
                        if (commonObjectDefinition.getModule()!= null && StringUtils.isNotEmpty(commonObjectDefinition.getModule().getModuleCode())) {
                            if (mapCommonModule.containsKey(commonObjectDefinition.getModule().getModuleCode())) {
                                ouputDirCommonObject = mapCommonModule.get(commonObjectDefinition.getModule().getModuleCode());
                            }else {
                                branchFolderPath = commonObjectDefinition.getModule().getModuleCode();
                                ouputDirCommonObject = createFileOutputFolder(branchFolderPath, generateSourceCode.getSourcePathBatch(), "batch");
                                mapCommonModule.put(commonObjectDefinition.getModule().getModuleCode(), ouputDirCommonObject);
                            }
                        }else {
                            //do nothing
                        }
                    }
                	prepareCommonObject(commonObjectDefinition);
                    List<CommonObjectAttribute> singleList = getSingleList(commonObjectDefinition.getCommonObjectAttributes());
                    
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("package", generateSourceCode.getProject().getPackageName());
                    data.put("commonObjectDefinition", commonObjectDefinition);
                    data.put("singleList", singleList);
                    data.put("moduleType", moduleTypeStr);
                    
                    generateObjectLst(commonObjectDefinition, generateSourceCode, getObjectList(commonObjectDefinition.getCommonObjectAttributes()), moduleType);
                    
                    this.process(data, TEMPLATE_COMMON_OBJECT_DEFINITION, ouputDirCommonObject
                            + WordUtils.capitalize(commonObjectDefinition.getCommonObjectDefinitionCode())
                            + GenerateSourceCodeConst.JAVA_EXTEND);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
	}
	   
    private void generateObjectLst(CommonObjectDefinition commonObjectDefinition, GenerateSourceCode generateSourceCode, 
    		List<CommonObjectAttribute> objCommonObjectAttributeLst, Integer moduleType) {
    	
    	if(CollectionUtils.isNotEmpty(objCommonObjectAttributeLst)) {
    		for (CommonObjectAttribute element : objCommonObjectAttributeLst) {
                if (CollectionUtils.isNotEmpty(element.getObjectList())) {
                    generateObjectLst(commonObjectDefinition, generateSourceCode, element.getObjectList(), moduleType);
                }
                if (CollectionUtils.isNotEmpty(element.getSingleList())) {
                    generateObject(commonObjectDefinition, generateSourceCode, element, moduleType);
                }
            }
    	}
    }

    private void generateObject(CommonObjectDefinition commonObjectDefinition, GenerateSourceCode generateSourceCode, CommonObjectAttribute commonObjectAttribute, Integer moduleType) {
        OutputStreamWriter out = null;
        try {
           
        	String outputDir = "";
        	String moduleTypeStr = "";
        	if( moduleType == FunctionType.ONLINE){
        	    //Check generate object of Common Object of Module
        	    if (commonObjectDefinition.getModule() != null && StringUtils.isNotEmpty(commonObjectDefinition.getModule().getModuleCode())) {
        	        String branchPath = commonObjectDefinition.getModule().getModuleCode() +File.separator +commonObjectDefinition.getCommonObjectDefinitionCode();
        	        outputDir = createFileOutputFolder(branchPath, generateSourceCode.getSourcePathDomain(), "domain");
        	    } else {
        	        outputDir = createFileOutputFolder(commonObjectDefinition.getCommonObjectDefinitionCode() + "Object", generateSourceCode.getSourcePathDomain(), "domain");
        	    }
        		moduleTypeStr = "domain";
        	}
        	else {
        		outputDir = createFileOutputFolder(commonObjectDefinition.getCommonObjectDefinitionCode() + "Object", generateSourceCode.getSourcePathBatch(), "batch");
        		moduleTypeStr = "batch";
        	}
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("package", generateSourceCode.getProject().getPackageName());
            data.put("commonObjectDefinition", commonObjectDefinition);
            data.put("commonObjectAttribute", commonObjectAttribute);
            data.put("moduleType", moduleTypeStr);
            
            this.process(data, TEMPLATE_OBJECT_OF_COMMON_OBJECT_DEFINITION, outputDir
                            + WordUtils.capitalize(commonObjectAttribute.getCommonObjectAttributeCode())
                            + GenerateSourceCodeConst.JAVA_EXTEND);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    
    private List<CommonObjectAttribute> getSingleList(List<CommonObjectAttribute> commonObjectAttributeList) {
        List<CommonObjectAttribute> listInputSingle = new ArrayList<CommonObjectAttribute>();
        
        if(CollectionUtils.isNotEmpty(commonObjectAttributeList)) {
        	for (CommonObjectAttribute commonObjectAttribute : commonObjectAttributeList) {
                if (commonObjectAttribute.getParentCommonObjectAttributeId() == null) {
                    listInputSingle.add(commonObjectAttribute);
                }
            }
        }
        
        return listInputSingle;
    }
    
    private List<CommonObjectAttribute> getObjectList(List<CommonObjectAttribute> commonObjectAttributeList) {
        List<CommonObjectAttribute> listCommonObjectAttribute = new ArrayList<CommonObjectAttribute>();
        
        if(CollectionUtils.isNotEmpty(commonObjectAttributeList)) {
        	for (CommonObjectAttribute commonObjectAttribute : commonObjectAttributeList) {
                if (commonObjectAttribute.getParentCommonObjectAttributeId() == null 
                        && (commonObjectAttribute.getDataType().equals(GenerateSourceCodeConst.ObjectType.OBJECT))) {
                    settingFieldObject(commonObjectAttribute, commonObjectAttributeList);
                    listCommonObjectAttribute.add(commonObjectAttribute);
                }
            }
        }
        
        return listCommonObjectAttribute;
    }
    
    private void settingFieldObject(CommonObjectAttribute item, List<CommonObjectAttribute> commonObjectAttributeList) {
        List<CommonObjectAttribute> listSingle = new ArrayList<CommonObjectAttribute>();
        List<CommonObjectAttribute> listObject = new ArrayList<CommonObjectAttribute>();
        
        if(CollectionUtils.isNotEmpty(commonObjectAttributeList)) {
        	for (CommonObjectAttribute commonObjectAttribute : commonObjectAttributeList) {
                if (item.getCommonObjectAttributeId().equals(commonObjectAttribute.getParentCommonObjectAttributeId())){
                    listSingle.add(commonObjectAttribute);
                    if (commonObjectAttribute.getDataType().equals(GenerateSourceCodeConst.ObjectType.OBJECT)) {
                        settingFieldObject(commonObjectAttribute, commonObjectAttributeList);
                        listObject.add(commonObjectAttribute);
                    }
                }
            }
        }
        
        item.setSingleList(listSingle);
        item.setObjectList(listObject);
    }
    
    private String createFileOutputFolder(String branchFolderPath, String pathRoot, String moduleType) {
        StringBuilder pathOutput = new StringBuilder();
        
        pathOutput.append("src").append(File.separator)
                  .append("main").append(File.separator)
                  .append("java").append(File.separator)
                  .append(pathPackage.toString())
                  .append(moduleType).append(File.separator)
                  .append("commonobject").append(File.separator)
                  .append(branchFolderPath);

        return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
    }

    /**
     * recursive input child object
     *
     * @param eod
     */
    private void prepareCommonObject(CommonObjectDefinition eod) {
        if (CollectionUtils.isNotEmpty(listOfCommonObjectAttribute)) {
            List<CommonObjectAttribute> ListOfEodATemp = new ArrayList<CommonObjectAttribute>();
            for (CommonObjectAttribute eoa : listOfCommonObjectAttribute) {
                if (FunctionCommon.equals(eod.getCommonObjectDefinitionId(), eoa.getCommonObjectDefinitionId())) {
                    if (DbDomainConst.ObjectDefinitionType.EXTERNAL.equals(eoa.getDataType())) {
                        CommonObjectDefinition eodTemp = getCommonObjectDefinitionFromList(eoa.getObjectDefinitionId());
                        if (null != eodTemp) {
                            prepareCommonObject(eodTemp);
                            eoa.setCommonObjectDefinition(eodTemp);
                        }
                    }
                    ListOfEodATemp.add(eoa);
                }
            }
            eod.setCommonObjectAttributes(ListOfEodATemp);
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
            for (CommonObjectDefinition eod : listOfCommonObjectDefinition) {
                if (FunctionCommon.equals(objectId, eod.getCommonObjectDefinitionId())) {
                    return eod;
                }
            }
        }
        return null;
    }
}
