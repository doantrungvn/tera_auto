package org.terasoluna.qp.app.librarymanagement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LibraryManagementMessageConst;

@Component
public class LibraryManagementValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (LibraryManagementForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LibraryManagementForm libraryManagementForm = (LibraryManagementForm) target;
		if(DataTypeUtils.equals(libraryManagementForm.getScope(),"system")){
			if(DataTypeUtils.equals(libraryManagementForm.getSystemFlag(),"1")){
				if(StringUtils.isEmpty(libraryManagementForm.getSystemPath())){
					errors.reject(CommonMessageConst.ERR_SYS_0025,
							new Object[]{MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0024)},
							null);
				}
			} else if(DataTypeUtils.equals(libraryManagementForm.getSystemFlag(),"2")){
				if(StringUtils.isEmpty(libraryManagementForm.getUploadFileName())){
					String[] args =  {MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0011)};
					errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
				}
			}
		}
		
		ValidationUtils.validatePackageName(libraryManagementForm.getGroupId(), errors, LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0005,CommonMessageConst.ERR_SYS_0025,LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0005,CommonMessageConst.ERR_SYS_0018);
	
		ValidationUtils.validateArtifactId(libraryManagementForm.getArtifactId(),errors,LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0006);
	
	}
	

}
