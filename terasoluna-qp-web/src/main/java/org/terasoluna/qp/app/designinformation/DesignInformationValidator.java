package org.terasoluna.qp.app.designinformation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.DesignInformationMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.DesignInformationDetail;
import org.terasoluna.qp.domain.model.DesignRelationSetting;
import org.terasoluna.qp.domain.service.common.SystemService;
@Component
public class DesignInformationValidator implements Validator {
	
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (DesignInformationForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DesignInformationForm designInformationForm =  (DesignInformationForm)target;
		this.validateDesignInformation(designInformationForm, errors);
		this.validateDesignInformationDetail(designInformationForm, errors);
		this.validateDesignRelationSetting(designInformationForm, errors);
	}

	/**
	 * Validate for design information
	 * @param designInformationForm
	 * @param errors
	 */
	private void validateDesignInformation(DesignInformationForm designInformationForm, Errors errors){
		/*if(StringUtils.isEmpty(designInformationForm.getDesignName())){
			errors.reject(CommonMessageConst.ERR_SYS_0025,new Object[] {MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0021) }, null);
		}*/
		String designName = designInformationForm.getDesignName();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		ValidationUtils.validateRequired(designName, errors, DesignInformationMessageConst.SC_DESIGNINFORMATION_0021);
		ValidationUtils.validateMaxLength(designName, errors,accountProfile.getNameMaxLength(), DesignInformationMessageConst.SC_DESIGNINFORMATION_0021);
		ValidationUtils.validateMaskName(designName, errors, accountProfile.getNamePattern(), DesignInformationMessageConst.SC_DESIGNINFORMATION_0021);
		
	}
	
	/**
	 * Validate for design information detail list
	 * @param designInformationForm
	 * @param errors
	 */
	private void validateDesignInformationDetail(DesignInformationForm designInformationForm, Errors errors){
		List<DesignInformationDetail> lstdesignInformationDetails = designInformationForm.getDesignInformationDetail();
		int idxDetail = 1;
		if(lstdesignInformationDetails != null && lstdesignInformationDetails.size() > 0){
			for(DesignInformationDetail detail : lstdesignInformationDetails){
				// Check mandatory subtitle
				if(StringUtils.isEmpty(detail.getSubtitle())){
					errors.reject(DesignInformationMessageConst.SC_DESIGNINFORMATION_0020,new Object[] {MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0015),idxDetail,MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0010)}, null);
				}
				idxDetail++;
			}
		}
	}
	
	/**
	 * Validate for design relation setting list 
	 * @param designInformationForm
	 * @param errors
	 */
	private void validateDesignRelationSetting(DesignInformationForm designInformationForm, Errors errors){
		List<DesignRelationSetting> lstdesignRelationSetting = designInformationForm.getDesignRelationSetting();
		int idxDetail = 1;
		Set<String> moduleCodeSet =  new HashSet<String>();
		if(lstdesignRelationSetting != null && lstdesignRelationSetting.size() > 0){
			for(DesignRelationSetting detail : lstdesignRelationSetting){
				// Check mandatory subtitle
				if(StringUtils.isEmpty(detail.getModuleCode())){
					errors.reject(DesignInformationMessageConst.SC_DESIGNINFORMATION_0020,new Object[] {MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0016),idxDetail,MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0022)}, null);
				}else if(moduleCodeSet.contains(detail.getModuleCode())){
					// Check duplicate module code
					errors.reject(DesignInformationMessageConst.SC_DESIGNINFORMATION_0023,new Object[] {MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0016),idxDetail,MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0022)}, null);
				}else{
					moduleCodeSet.add(detail.getModuleCode());
				}
				idxDetail++;
				
			}
		}
	}
}
