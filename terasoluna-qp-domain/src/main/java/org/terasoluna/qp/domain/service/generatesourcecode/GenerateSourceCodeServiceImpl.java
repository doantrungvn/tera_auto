package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.processing.CompositeHandler;
import org.terasoluna.qp.app.processing.SequencingHandlersStrategy;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.GenerateSourceCodeItem;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;
import org.terasoluna.qp.domain.service.screendesign.ScreenGenerateHandler;

@Service
@Transactional
public class GenerateSourceCodeServiceImpl implements GenerateSourceCodeService {

	@Inject
	@Named("SqlDesignGenerationHandler")
	SqlDesignGenerationHandler sqlDesignGenerationHandler;

	@Inject
	@Named("BusinessLogicGenerateHandler")
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	@Named("CommonBusinessLogicGenerateHandler")
	CommonBusinessLogicGenerateHandler commonBusinessLogicGenerateHandler;

	@Inject
	@Named("ScreenGenerateHandler")
	ScreenGenerateHandler screenGenerateHandler;

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	@Named("CodelistGenerateHandler")
	CodelistGenerateHandler codelistGenerateHandler;

	@Inject
	@Named("GenerateStructProjectHandler")
	GenerateStructProjectHandler generateStructProjectHandler;

	@Inject
	@Named("FunctionMasterGenerationHandler")
	FunctionMasterGenerationHandler functionMasterGenerationHandler;

	@Inject
	@Named("DecisionTableGenerateHandler")
	DecisionTableGenerateHandler decisionTableGenerateHandler;
	
	@Inject
	@Named("CL_GENERATE_SOURCE_TYPE")
	SimpleMapCodeList generateSourceTypeCodeList;
	
	@Inject
	@Named("CommonObjectDefinitionGenerateHandler")
	CommonObjectDefinitionGenerateHandler commonObjectDefinitionGenerateHandler;
	
	@Inject
	@Named("BatchBusinessLogicGenerateHandler")
	BatchBusinessLogicGenerateHandler batchBusinessLogicGenerateHandler;
	
	@Inject
	@Named("BatchSqlDesignGenerationHandler")
	BatchSqlDesignGenerationHandler batchSqlDesignGenerationHandler;
	
	@Inject
	@Named("SessionManagementGenerateHandler")
	SessionManagementGenerateHandler sessionManagementGenerateHandler;
	
	@Inject
	ModuleService moduleService;

	@Inject
	ProjectService projectService;
	
	private static final Log log = LogFactory.getLog(GenerateSourceCodeServiceImpl.class);
	
	private GenerateSourceCode generateSourceCode(GenerateSourceCode generateSourceCode, CommonModel common) {
		generateSourceCode.setBatchModuleFlg(false);
		
		List<Module> listOfModules = new ArrayList<Module>();
		boolean isBathModuleSelect = false;
		
		if (CollectionUtils.isNotEmpty(generateSourceCode.getModules())) {
			for(Module module : generateSourceCode.getModules()) {
				if (GenerateSourceCodeConst.PROJECT_SCOPRE.equals(generateSourceCode.getScopeGenerateSource())) {
					if(DbDomainConst.FunctionType.BATCH.equals(module.getModuleType()) 
							&& Boolean.FALSE.equals(isBathModuleSelect)) {
						isBathModuleSelect = true;
					}
				} else {
					if(module.getSelectedGenerate() == GenerateSourceCodeConst.MODULE_SELECTED_GENERATE) {
						listOfModules.add(module);
						if(module.getModuleType() == DbDomainConst.FunctionType.BATCH 
								&& Boolean.FALSE.equals(isBathModuleSelect)) {
							isBathModuleSelect = true;
						}
					}
				}
			}

			if(isBathModuleSelect) generateSourceCode.setBatchModuleFlg(true);
		}

		if(generateSourceCode.getScopeGenerateSource().equals(GenerateSourceCodeConst.MODULE_SCOPRE)) {
			generateSourceCode.setModules(listOfModules);
		}

		if (generateSourceCode.getGenerateType().equals(GenerateSourceCodeConst.SOURCE_CODE)) {
			
			// Check if no item is checked, then generate all item
			Boolean genAll = true;
			for (GenerateSourceCodeItem item : generateSourceCode.getGenerateSourceCodeItemLst()) {
				if (item.getIsChecked()) {
					genAll = false;
					break;
				}
			}

			generateSourceCode.setGenAll(genAll);

			// Generate strust Project
			CompositeHandler<GenerateSourceCode> generationHandler = new CompositeHandler<GenerateSourceCode>(new SequencingHandlersStrategy<GenerateSourceCode>());
			generationHandler.addHandler(generateStructProjectHandler);

			for (GenerateSourceCodeItem item : generateSourceCode.getGenerateSourceCodeItemLst()) {
//				item.setIsChecked(true);
				if (item.getIsChecked() || genAll) {
					switch (item.getSourceCodeItemKey()) {
					case GenerateSourceCodeConst.BUSINESS_LOGIC:
						generationHandler.addHandler(functionMasterGenerationHandler);
						// not gen default: sonpn
						generationHandler.addHandler(decisionTableGenerateHandler);
						generationHandler.addHandler(businessLogicGenerateHandler);
						generationHandler.addHandler(commonBusinessLogicGenerateHandler);
						generationHandler.addHandler(commonObjectDefinitionGenerateHandler);
						if (isBathModuleSelect) {
							generationHandler.addHandler(batchBusinessLogicGenerateHandler);
						}
						generationHandler.addHandler(sessionManagementGenerateHandler);
						break;
					case GenerateSourceCodeConst.SQL:
						//sqlDesignGenerationHandler.initData(this.workingProjectId, this.workingLanguageId, this.accountId, this.workingProject);
						generationHandler.addHandler(sqlDesignGenerationHandler);
						//batchSqlDesignGenerationHandler.initData(this.workingProjectId, this.workingLanguageId, this.accountId, this.workingProject);
						if (isBathModuleSelect) {
							generationHandler.addHandler(batchSqlDesignGenerationHandler);
						}
						generationHandler.addHandler(codelistGenerateHandler);
						break;
					case GenerateSourceCodeConst.SCREEN:
						//screenGenerateHandler.importData(this.currentAccount, this.workingLanguageId, this.workingProjectId);
						generationHandler.addHandler(screenGenerateHandler);
						break;
					}
				}
			}
			generationHandler.handle(generateSourceCode, common);
		} else if (generateSourceCode.getGenerateType().equals(GenerateSourceCodeConst.WAR_FILE)) {
		}
		
		return generateSourceCode;
	}
	
	/**
	 * Generate document item for generate source
	 * 
	 * @param generateSourceCodeForm
	 */
	private void initGenerateSourceCodeByCodeList(GenerateSourceCode generateSourceCode) {
		List<GenerateSourceCodeItem> generateDocumentItemLst = new ArrayList<GenerateSourceCodeItem>();

		for (String key : generateSourceTypeCodeList.asMap().keySet()) {
			GenerateSourceCodeItem item = new GenerateSourceCodeItem();
			item.setSourceCodeItemKey(Integer.valueOf(key));
			item.setSourceCodeItemTemplateName(generateSourceTypeCodeList.asMap().get(key));
			item.setIsChecked(false);
			generateDocumentItemLst.add(item);
		}
		
		generateSourceCode.setGenerateSourceCodeItemLst(generateDocumentItemLst);
	}
	
	@Override
	public GenerateSourceCode processGenerateSourceCode(GenerateSourceCode generateSourceCode, CommonModel common) {
		generateSourceCode.setProject(projectService.getProjectInformation(common.getWorkingProjectId(), common.getUpdatedBy()));
		GenerateSourceCodeUtil.createSourceFolder(generateSourceCode);
		this.generateSourceCode(generateSourceCode, common);
		GenerateSourceCodeUtil.generateZipSourceFile(generateSourceCode);
		return generateSourceCode;
	}

	@Override
	public String processGenerateAllSourceCode(Project project, String exportPath, String sourceExportPath, String filePattern, String tempFolder, CommonModel common) {

		GenerateSourceCode generateSourceCode = new GenerateSourceCode();
		generateSourceCode.setProject(project);
		generateSourceCode.setLanguageId(common.getWorkingLanguageId());
		generateSourceCode.setTempFolder(tempFolder);
		try {
			// Get list module information
			List<Module> moduleList = moduleService.findAllModule(project.getProjectId(), null);
			generateSourceCode.setModules(moduleList);
			generateSourceCode.setSourcePath(sourceExportPath);
			generateSourceCode.setScopeGenerateSource(GenerateSourceCodeConst.PROJECT_SCOPRE);
			generateSourceCode.setGenerateType(GenerateSourceCodeConst.SOURCE_CODE);	
			log.info("createDirectory: " + generateSourceCode.getSourcePath());
			FileUtilsQP.createDirectory(generateSourceCode.getSourcePath());

			generateSourceCode.setFileName(filePattern);
			this.initGenerateSourceCodeByCodeList(generateSourceCode);
			this.generateSourceCode(generateSourceCode, common);
			GenerateSourceCodeUtil.zipSourceFileBatch(generateSourceCode, exportPath);
		}
		catch (BusinessException be){
			throw new BusinessException(be.getResultMessages());
		}
		catch (Exception ex){
			throw new SystemException("", ex);
		}
		finally{
			log.info("deleteFolder: " + generateSourceCode.getSourcePath());
			/*GenerateDocumentUtilsQP.deleteFolder(generateSourceCode.getSourcePath());*/
		}
		return generateSourceCode.getFileName();
	}

	@Override
	public GenerateSourceCode processGenerateWarFile(Project project, String tempFolder, String sourceExportPath, CommonModel common) {
		
		GenerateSourceCode generateSourceCode = new GenerateSourceCode();
		generateSourceCode.setProject(project);
		generateSourceCode.setLanguageId(common.getWorkingLanguageId());
		generateSourceCode.setTempFolder(tempFolder);
		try {
			// Get list module information
			List<Module> moduleList = moduleService.findAllModuleOfOnline(project.getProjectId(), DbDomainConst.FunctionType.ONLINE);
			generateSourceCode.setModules(moduleList);
			generateSourceCode.setSourcePath(sourceExportPath);
			generateSourceCode.setScopeGenerateSource(GenerateSourceCodeConst.PROJECT_SCOPRE);
			generateSourceCode.setGenerateType(GenerateSourceCodeConst.SOURCE_CODE);	
			log.info("createDirectory: " + generateSourceCode.getSourcePath());
			FileUtilsQP.createDirectory(generateSourceCode.getSourcePath());
			
			this.initGenerateSourceCodeByCodeList(generateSourceCode);
			this.generateSourceCode(generateSourceCode, common);
		}
		catch (BusinessException be){
			throw new BusinessException(be.getResultMessages());
		}
		catch (Exception ex){
			throw new SystemException("", ex);
		}
		return generateSourceCode;
	}

}
