package org.terasoluna.qp.app.importmanagement;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ImportManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class ImportManagementValidator implements Validator {

	@Inject
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (ImportManagementForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ImportManagementForm importManagementForm = (ImportManagementForm) target;
		
		//validate document type
		ValidationUtils.validateRequired(String.valueOf(importManagementForm.getDocumentType()), errors, ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0002);
		
		//validate file name
		if(StringUtils.isEmpty(importManagementForm.getFileName())){
			String[] args =  {MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003)};
			errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
		}
		
		//String uploadFilePath = Func.cat(importFile.getRealPath(),"/",importFile.getRealFileName());
        //only allow xlsx to upload
		
		MultipartFile importFile = importManagementForm.getFile();
        if(importFile != null && importFile.getSize() > 0 && 
        		!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(importFile.getContentType())){
        	
        	errors.reject(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0001, new Object[] { MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003)}, null);       	       	
	    	//fileRemove(importFile,uploadFilePath);
        }
   
        //file size can't larger than limit size of system
        //int limtFileSize = ImportUtils.getLimitFileSize();
        
        AccountProfile accountProfile = systemService.getDefaultProfile();
        int maxSizeUpload = accountProfile.getMaxSizeUpload().intValue();
        if(importFile != null && importFile.getSize() > Long.valueOf(maxSizeUpload * 1024 * 1024)){
        	errors.reject(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0002, 
        			new Object[] { MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003), maxSizeUpload + ImportManagementMessageConst.UNIT_OF_FILE_SIZE}, null);       	        	
	    	//fileRemove(importFile,uploadFilePath);
        }
	}
}
