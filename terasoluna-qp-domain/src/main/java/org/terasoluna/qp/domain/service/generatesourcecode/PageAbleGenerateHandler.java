package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("PageAbleGenerateHandler")
public class PageAbleGenerateHandler extends SequenceLogicGenerationHandler {
	
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	@Inject
	ScreenAreaRepository screenAreaRepository;
	
	private Project project;
	
	@Override
	public void handle(StringBuilder additionParam, BLogicHandlerIo param) {
		// TODO Auto-generated method stub// Build declare page able for bussines
		if(param != null && CollectionUtils.isNotEmpty(param.getBusinessDesign().getLstOutputBean())) {
			
			project = param.getProject();
			
			Map<String, Set<ScreenArea>> mScrAreaLstObj = new HashMap<String, Set<ScreenArea>>();
			Map<String, Map<Long, ScreenArea>> mScrAreaTotalCount = new HashMap<String, Map<Long, ScreenArea>>();
			
			StringBuilder bdDeclarFirstPage = new StringBuilder(StringUtils.EMPTY);
			StringBuilder bdDeclarLastPage = new StringBuilder(StringUtils.EMPTY);
			StringBuilder bdDeclarMapPage = new StringBuilder(StringUtils.EMPTY);
			StringBuilder bdModelPageAttr = new StringBuilder(StringUtils.EMPTY);
			
			Set<OutputBean> uniqueParentOutputBean = new HashSet<OutputBean>();
			Map<String, OutputBean> mapOutputBean = new HashMap<String, OutputBean>();
			for (OutputBean ouBean : param.getBusinessDesign().getLstOutputBean()) mapOutputBean.put(ouBean.getOutputBeanId(), ouBean);
			
			for (OutputBean ouBean : param.getBusinessDesign().getLstOutputBean()) {
				List<ScreenArea> lstScreenArea = screenAreaRepository.findScreenAreaByOutputbeanId(Long.parseLong(ouBean.getOutputBeanId()));
				//List<ScreenArea> lstScreenAreaTmp = new ArrayList<ScreenArea>();
				Set<ScreenArea> lstScreenAreaTmp = new HashSet<ScreenArea>();
				OutputBean parent = mapOutputBean.get(ouBean.getParentOutputBeanId());
				if(ouBean.getParentOutputBeanId() != null && !uniqueParentOutputBean.contains(parent) && CollectionUtils.isNotEmpty(lstScreenArea)) {
					for (ScreenArea screenArea : lstScreenArea) {
						if (screenArea.getAreaTypeAction() != null && screenArea.getAreaTypeAction().intValue() == 2) {
							if(!uniqueParentOutputBean.contains(parent)) uniqueParentOutputBean.add(parent);
							if(!lstScreenAreaTmp.contains(screenArea)) lstScreenAreaTmp.add(screenArea);
						}
					}
					
					if(CollectionUtils.isNotEmpty(lstScreenAreaTmp)) mScrAreaLstObj.put(parent.getOutputBeanId(), lstScreenAreaTmp);
				}
				
				// Get total count mapping
				if(Boolean.FALSE.equals(ouBean.getArrayFlg()) 
						&& !BusinessDesignConst.DataType.TIMESTAMP.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.DATETIME.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.TIME.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.DATE.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.STRING.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.CHAR.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.OBJECT.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.ENTITY.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.OBJECT.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(ouBean.getDataType())
						&& !BusinessDesignConst.DataType.COMMON_OBJECT.equals(ouBean.getDataType())) {
					List<ScreenArea> lstScreenAreaTotalCount = screenAreaRepository.findScreenAreaByOutputbeanIdOfTotalCount(Long.parseLong(ouBean.getOutputBeanId()));
					if (CollectionUtils.isNotEmpty(lstScreenAreaTotalCount)) {
						Map<Long, ScreenArea> mScrArea = new HashMap<Long, ScreenArea>();
						for (ScreenArea screenArea : lstScreenAreaTotalCount) {
							if (screenArea.getAreaTypeAction() != null 
									&& screenArea.getAreaTypeAction().intValue() == 2 
									&& screenArea.getObjectMappingType() != null 
									&& screenArea.getObjectMappingType().equals(6)) {
								mScrArea.put(screenArea.getScreenAreaId(), screenArea);
							}
						}
						
						if(CollectionUtils.isNotEmpty(lstScreenAreaTotalCount)) mScrAreaTotalCount.put(ouBean.getOutputBeanId(), mScrArea);
					}
				}
			}

			Integer count = 0;
			if(CollectionUtils.isNotEmpty(uniqueParentOutputBean)) {
				for (OutputBean outputBean : uniqueParentOutputBean) {
					count++;
					String getter = GenerateSourceCodeUtil.normalizedVariantName(detailServiceImpHandler.getterAndSetterOfParameter(GenerateSourceCodeConst.GenerateScope.BLOGIC, param.getmAllParentAndSeflByLevelOfInOutObj(), true, outputBean.getOutputBeanId(), 2, param.getBlogicOutputSyntax(), null));
					String instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputBean);
					
					// Get new instance of object data type
					String instanceOf = getPackageName(param.getModule(), param.getBusinessDesign(), outputBean) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
					// Build first declare
					String page = "page"+instanceNmObj;
					String lstPageContent = "lstPageContent"+instanceNmObj;
					bdDeclarFirstPage.append(String.format("Page<%s> %s = null;", instanceOf, page));
					bdDeclarFirstPage.append("\n\t\t");
					bdDeclarFirstPage.append(String.format("List<%s> %s = new ArrayList<%s>();", instanceOf, lstPageContent , instanceOf));
					bdDeclarFirstPage.append("\n\t\t");
					
					// Build last declare
					// Append total count
					bdDeclarLastPage.append(String.format("%s = (%s == null?new ArrayList<%s>():%s);", lstPageContent, lstPageContent, instanceOf, getter));
					bdDeclarLastPage.append("\n\t\t");
					
					Set<ScreenArea> lstScreenArea = mScrAreaLstObj.getOrDefault(outputBean.getOutputBeanId(), new HashSet<ScreenArea>());
					OutputBean ouTotalCount = null;
					
					for (ScreenArea screenArea : lstScreenArea) {
						for (Map.Entry<String, Map<Long, ScreenArea>> entry : mScrAreaTotalCount.entrySet()) {
							System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
							if(entry.getValue().get(screenArea.getScreenAreaId()) != null) {
								ouTotalCount = mapOutputBean.get(entry.getKey());
								break;
							}
						}
					}
					
					String totalCount = "0";
					if(ouTotalCount != null) {
						// Build total count
						String getterOfOutputCount = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, ouTotalCount.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, param.getBlogicOutputSyntax(), null);
						totalCount = getterOfOutputCount + SPACE + "!= null ?" + SPACE + getterOfOutputCount + SPACE + ": 0";
					}
					
					bdDeclarLastPage.append(String.format("%s = new PageImpl<%s>(%s, pageable, %s);", page, instanceOf, lstPageContent, totalCount));
					bdDeclarLastPage.append("\n\t\t");
					
					// Build put to map output
					bdDeclarMapPage.append(String.format("map.put(%s, %s);", count.toString(), page));
					bdDeclarMapPage.append("\n\t\t");
					
//					if(param.getBusinessDesign().getBusinessLogicId() == 2617) {
//						System.out.println();
//					}
					
					// Setting model of page able on controller
					for (ScreenArea screenArea : lstScreenArea) {
						bdModelPageAttr.append(String.format("model.addAttribute(\"%s\", map.get(%s));", "page"+screenArea.getAreaCode(), count.toString())).append("\n\t\t\t");
					}
				}

				param.getBusinessDesign().setBdDeclarFirstPage(bdDeclarFirstPage.toString());
				param.getBusinessDesign().setBdDeclarLastPage(bdDeclarLastPage.toString());
				param.getBusinessDesign().setBdDeclarMapPage(bdDeclarMapPage.toString());
				param.getBusinessDesign().setBdModelPageAttr(bdModelPageAttr.toString());
			}
		}		
	}

	private String getPackageName(Module module, BusinessDesign blogic, Object obj) {
		StringBuilder pakageName = new StringBuilder(project.getPackageName());
		int dataType = -1;
		String code = null;
		String pakageExternal = StringUtils.EMPTY;

		if (obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			code = "InputBean";
			pakageExternal = in.getPackageNameObjExt();
		} else if (obj instanceof OutputBean) {
			OutputBean ou = (OutputBean) obj;
			dataType = ou.getDataType();
			code = "OutputBean";
			pakageExternal = ou.getPackageNameObjExt();
		}

		switch (dataType) {

			case GenerateSourceCodeConst.DataType.OBJECT:
				pakageName.append(".domain").append(".service").append(".").append(StringUtils.uncapitalize(module.getModuleCode())).append(".").append(StringUtils.uncapitalize(blogic.getBusinessLogicCode())).append(code).append(".");
				break;
			case GenerateSourceCodeConst.DataType.ENTITY:
				pakageName.append(".domain").append(".model").append(".");
				break;
				
			case GenerateSourceCodeConst.DataType.COMMON_OBJECT :
				pakageName.append(".domain.commonobject.");
				break;
			case GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT :
				pakageName.append(".").append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}

	@Override
	public void preGencode(StringBuilder additionParam, BLogicHandlerIo param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postGencode(StringBuilder additionParam, BLogicHandlerIo param) {
		// TODO Auto-generated method stub
		
	}

}
