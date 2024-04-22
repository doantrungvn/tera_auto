package org.terasoluna.qp.batch.impactchange;

import javax.inject.Inject;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst.FromResourceType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactCommonObjectRepository;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignShareService;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.businessdesign.CommonBusinessDesignShareService;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableShareService;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterShareService;
import org.terasoluna.qp.domain.service.impactchange.ImpactCommonObjectShareService;
import org.terasoluna.qp.domain.service.impactchange.ImpactExternalObjectShareService;
import org.terasoluna.qp.domain.service.impactchange.ImpactSQLDesignShareService;
import org.terasoluna.qp.domain.service.sessionmanagement.SessionManagementShareService;

@Component
public class DetectImptBLogic extends AbstractTransactionBLogic {

	private static final Logger log = LoggerFactory.getLogger(DetectImptBLogic.class);
	
	@Inject
	ImpactChangeRepository impactChangeRepository;
	
	@Inject
	DecisionTableShareService decisionTableShareService;
	
	@Inject
	SessionManagementShareService sessionManagementShareService;
	
	@Inject
	CommonBusinessDesignShareService commonBusinessDesignShareService;
	
	@Inject
	BusinessDesignShareService businessDesignShareService;
	
	@Inject
	AutocompleteDesignShareService autocompleteDesignShareService;
	
	@Inject
	ImpactExternalObjectShareService impactExternalObjectShareService;
	
	@Inject
	FunctionMasterShareService functionMasterShareService;
	
	@Inject
	ImpactSQLDesignShareService impactSQLDesignShareService;
	
	@Inject
	ImpactCommonObjectShareService impactCommonObjectShareService;
	
	@Override
    public int doMain(BLogicParam arg0) {
		log.info("Start detect impact " + arg0.getJobSequenceId());
		Integer impactType = Integer.valueOf(arg0.getJobArgNm4());
		CommonModel common = new CommonModel();
		common.setCreatedBy(Long.valueOf(arg0.getJobArgNm3()));
		common.setCreatedDate(FunctionCommon.getCurrentTime());
		common.setProjectId(Long.valueOf(arg0.getJobArgNm1()));
		common.setWorkingProjectId(Long.valueOf(arg0.getJobArgNm1()));
		Long impactId = Long.valueOf(arg0.getJobArgNm5());
		ImpactChangeJobControl job = new ImpactChangeJobControl();
		job.setJobSeqId(arg0.getJobSequenceId());
		String caseOfImpact = arg0.getJobArgNm6();
		try{
			switch (impactType) {
				// in case of change design of decision table
				case FromResourceType.DECISION_TABLE:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						decisionTableShareService.detectListAffectedWhenModifyOfBatch(impactId, common);
					}
					else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						decisionTableShareService.detectListAffectedWhenDeleteOfBatch(impactId,arg0.getJobArgNm7(), common);
					}
					break;
				// in case of change design of session management
				case FromResourceType.SESSION_MANAGEMENT:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						sessionManagementShareService.detectListAffectedWhenModifyOfBatch(impactId, common);
					}
					else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						sessionManagementShareService.detectListAffectedWhenDeleteOfBatch(impactId,arg0.getJobArgNm7(), common);
					}
					break;
				// in case of change design of common blogic
				case FromResourceType.COMMON_BLOGIC:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						commonBusinessDesignShareService.detectListAffectedWhenModifyOfBatch(impactId, common);
					}
					else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						commonBusinessDesignShareService.detectListAffectedWhenDeleteOfBatch(impactId,arg0.getJobArgNm7(), common);
					}
					break;
				// in case of change design of navigator blogic
				case FromResourceType.NAVIGATOR_BLOGIC:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						businessDesignShareService.detectListAffectedWhenModifyOfBatch(impactId, common);
					}
					else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						businessDesignShareService.detectListAffectedWhenDeleteOfBatch(impactId,arg0.getJobArgNm7(), common);
					}
					break;
				// in case of change design of external object
				case FromResourceType.EXTERNAL_OBJECT:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						impactExternalObjectShareService.detectListAffectedWhenModifyOfBatch(impactId, common);
					}
					else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						impactExternalObjectShareService.detectListAffectedWhenDeleteOfBatch(impactId,arg0.getJobArgNm7(), common);
					}
					break;
				case FromResourceType.AUTOCOMPLETE_DESIGN:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						this.autocompleteDesignShareService.detectListAffectedWhenModifyOfBatch(impactId, common);
					}else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						this.autocompleteDesignShareService.detectListAffectedWhenDeleteOfBatch(impactId, arg0.getJobArgNm7(), common);
					}
					break;
				case FromResourceType.FUNCTION_METHOD:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						functionMasterShareService.detectListAffectedWhenModifyMethodOfBatch(impactId,common);
					}else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						functionMasterShareService.detectListAffectedWhenDeleteMethodOfBatch(impactId, arg0.getJobArgNm7(), arg0.getJobArgNm8(), common);
					}
					break;
				case FromResourceType.SQL_DESIGN:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						impactSQLDesignShareService.detectListAffectedWhenModifyOfBatch(impactId,common);
					}else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						impactSQLDesignShareService.detectListAffectedWhenDeleteOfBatch(impactId, arg0.getJobArgNm7(), common);
					}
					break;
				case FromResourceType.COMMON_OBJECT:
					if(ImpactChangeDesign.CASE_MODIFY.equals(caseOfImpact)){
						impactCommonObjectShareService.detectListAffectedWhenModifyOfBatch(impactId,common);
					}else if(ImpactChangeDesign.CASE_DELETE.equals(caseOfImpact)){
						impactCommonObjectShareService.detectListAffectedWhenDeleteOfBatch(impactId, arg0.getJobArgNm7(), common);
					}
					break;
				default:
					break;
			}
			job.setBlogicAppStatus(ImpactChangeDesign.BLOGIC_APP_STATUS_SUCCESS);
		
		}catch(Exception ex){
			job.setBlogicAppStatus(ImpactChangeDesign.BLOGIC_APP_STATUS_ERROR);
			log.error(ex.getMessage());
		}finally{
			job.setCurAppStatus(GenerateAppStatus.GENERATED);
			job.setUpdDateTime(FunctionCommon.getCurrentTime());
			impactChangeRepository.modifyStatusOfImpactChange(job);
			log.info("Stop detect impact " + arg0.getJobSequenceId());
		}
	    return 0;
    }

}
