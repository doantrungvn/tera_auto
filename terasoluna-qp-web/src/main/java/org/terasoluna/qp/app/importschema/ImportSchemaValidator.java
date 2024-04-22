package org.terasoluna.qp.app.importschema;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class ImportSchemaValidator implements Validator{
	
	@Inject 
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (ImportSchemaForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ImportSchemaForm importSchemaForm = (ImportSchemaForm) target;
		if(DbDomainConst.DatabaseType.PostgreSQL.equals(importSchemaForm.getDbType())){
			
			if(StringUtils.isBlank(importSchemaForm.getDbName())){
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.project.0017")}, null);
			}
			if(StringUtils.isBlank(importSchemaForm.getSchemaName())){
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.importschema.0003")}, null);
			}
		}if(DbDomainConst.DatabaseType.ORACLE.equals(importSchemaForm.getDbType())){
			if("0".equals(importSchemaForm.getConnectionType())){
				if(StringUtils.isBlank(importSchemaForm.getSchemaName())){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.importschema.0006")}, null);
				}
			}else if("1".equals(importSchemaForm.getConnectionType())){
				if(StringUtils.isBlank(importSchemaForm.getSchemaName())){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.importschema.0007")}, null);
				}
			}
		}

		if(StringUtils.isBlank(importSchemaForm.getDbHostName())){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.project.0018")}, null);
		}
		
		if(StringUtils.isBlank(importSchemaForm.getDbUser())){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.project.0020")}, null);
		}
		if(StringUtils.isBlank(importSchemaForm.getDbPassword())){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.project.0021")}, null);
		}
	}

}
