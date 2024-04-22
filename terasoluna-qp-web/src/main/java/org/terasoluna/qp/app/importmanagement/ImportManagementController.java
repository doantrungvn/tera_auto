package org.terasoluna.qp.app.importmanagement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.ImportManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.importmanagement.ImportManagementConst;
import org.terasoluna.qp.domain.service.importmanagement.ImportManagementService;

@Controller
@RequestMapping(value = "importmanagement")
@TransactionTokenCheck(value = "importmanagement")
public class ImportManagementController {
	private static final Logger logger = LoggerFactory.getLogger(ImportManagementForm.class);
	private static final String IMPORT_PATH = "importmanagement/importManagementForm";
	private static final String REDIRECT_IMPORT_PATH = "redirect:/importmanagement/importmanagement";
	
	@Inject
	ImportManagementService importManagementService;
	
	@Inject
	ImportManagementValidator importManagementValidator;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	SystemService systemService;
	
	@InitBinder
	public void init() {
	    //Refactor fix current project 20160613
//		importManagementService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		importManagementService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		importManagementService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());
//		importManagementService.setWorkingLanguageDesign(SessionUtils.getCurrentLanguageDesign());
//		importManagementService.setWorkingProject(SessionUtils.getCurrentProject());
//		importManagementService.setCurrentAccounProfile(SessionUtils.getCurrentAccountProfile());
//		importManagementService.setCurrentAccount(SessionUtils.getCurrentAccount());
	}
	
	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return importManagementForm
	 */
	
	@InitBinder("importManagementForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(importManagementValidator);
	}
	
	@ModelAttribute
	public ImportManagementForm setUpImportManagementForm() {
		ImportManagementForm importManagementForm = new ImportManagementForm();
		logger.debug("Init form {0}", importManagementForm);
		return importManagementForm;
	}
	
	@RequestMapping(value = "importmanagement", method = RequestMethod.GET)
	public String displayImportScreen(@RequestParam(value = "init", required = false) String init, @ModelAttribute ImportManagementForm importManagementForm, Model model) {
		if(init != null) {
			importManagementForm = setUpImportManagementForm();
			model.addAttribute("importManagementForm", importManagementForm);
		}
		AccountProfile accountProfile = systemService.getDefaultProfile();
		importManagementForm.setMaxSize(accountProfile.getMaxSizeUpload());
		model.addAttribute("importManagementForm", importManagementForm);
		return IMPORT_PATH;
	}
	
	@RequestMapping(value = "importmanagement", method = RequestMethod.POST)
	public String processImportMessageDesign(@Validated ImportManagementForm importManagementForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			if (importManagementForm.getFile() != null && importManagementForm.getFile().getSize() > 0) {
				// add warning message : User choose file again.
				importManagementForm.setFileName("");
			}
			importManagementForm.setResultFileName("");
			return IMPORT_PATH;
		}
		
		ImportManagement importManagement = beanMapper.map(importManagementForm, ImportManagement.class);
		importManagement.setLanguageCode(SessionUtils.getCurrentLanguageDesign().getLanguageCode());
		MultipartFile file = importManagementForm.getFile();
		if (importManagementForm.getFile() != null && importManagementForm.getFile().getSize() >0 ) {
			String fileName = FilenameUtils.getName(importManagementForm.getFile().getOriginalFilename());
			importManagementForm.setFileName(fileName);
		}
		String importPath = FileUtilsQP.getUploadFolder();
		FileUtilsQP.createDirectory(importPath);
		
		String fileName = ImportManagementConst.IMP_FILE_NAME_PREFIX + GenerateUniqueKey.generateWithDatePrefix() + ImportManagementConst.XLSX_TYPE;
		String filePath =  importPath + fileName;
		try {
			file.transferTo(new File(filePath));
		}
		catch (IllegalStateException e) {
			model.addAttribute("message", e.getMessage());
			return IMPORT_PATH;
		}
		catch (IOException e) {
			model.addAttribute("message", e.getMessage());
			return IMPORT_PATH;
		}
		importManagement.setFilePath(filePath);
		
		int importCount = 0;
		int totalCount = 0;
		
		try {
			importManagement.setAccountProfile(SessionUtils.getCurrentAccountProfile());
			
			//Refactor fix current project 20160613
			CommonModel common = new CommonModel();
            common.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
            common.setWorkingProjectId(SessionUtils.getCurrentProjectId());
            Long accountId = SessionUtils.getAccountId();
            common.setCreatedBy(accountId);
            common.setUpdatedBy(accountId);

			Map<String, Object> returnMap = importManagementService.processImport(importManagement, common);
			importCount = Integer.parseInt(returnMap.get(ImportManagementConst.ROW_IMPPORTED).toString());
			if(importManagement.getDocumentType() == 4){
				totalCount = (int) returnMap.get(ImportManagementConst.ERR_WHEN_IMPORT);
			}else if(importManagement.getDocumentType() == 1){
				totalCount = (int) returnMap.get(ImportManagementConst.NUMBER_CODELIST);
			}else{
				@SuppressWarnings("unchecked")
				List<String[]> errors = (List<String[]>) returnMap.get(ImportManagementConst.ERR_WHEN_IMPORT);
				totalCount = errors.size();
			}
		} catch (BusinessException ex) {
			if(importManagementForm.getFile() != null && importManagementForm.getFile().getSize() >0){
				// add warning message : User choose file again.
				importManagementForm.setFileName("");
			}
			model.addAttribute("message", ex.getResultMessages());
			return IMPORT_PATH;
		} catch (Exception e) {
			if(importManagementForm.getFile() != null && importManagementForm.getFile().getSize() >0){
				// add warning message : User choose file again.
				importManagementForm.setFileName("");
			}
			e.printStackTrace();
			model.addAttribute("message", ResultMessages.error().add(ImportManagementConst.ERR_IMPORTMANAGEMENT_0006));
			return IMPORT_PATH;
		}

		importManagementForm.setResultFileName(fileName);
		redirectAttributes.addFlashAttribute("message", ResultMessages.info().add(ImportManagementMessageConst.INF_IMPORTMANAGEMENT_0001, importCount, totalCount));
		redirectAttributes.addFlashAttribute("importManagementForm", importManagementForm);
		
		return REDIRECT_IMPORT_PATH;
	}
}
