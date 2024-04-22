package org.terasoluna.qp.domain.service.sessionmanagement;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SessionManagementMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementSearchCriteria;

@Service
@Transactional
public class SessionManagementServiceImpl implements SessionManagementService {
	
    @Inject
    SessionManagementRepository sessionManagementRepository;
    
    @Inject
    private BusinessDesignRepository businessDesignRepository;
    
    @Inject ImpactChangeRepository impactChangeRepository;

	@Override
	public Page<SessionManagement> findSessionManagementBySearchCriteria(SessionManagementSearchCriteria criteria, Pageable pageable, CommonModel common) {
	    List<SessionManagement> lstSessionManagementns;
        criteria.setProjectId(common.getWorkingProjectId());
        long totalCount = sessionManagementRepository.countBySearchCriteria(criteria);
        if (0 < totalCount) {
            lstSessionManagementns = sessionManagementRepository.findPageBySearchCriteria(criteria, pageable);
        } else {
            lstSessionManagementns = Collections.emptyList();
        }
        Page<SessionManagement> page = new PageImpl<SessionManagement>(lstSessionManagementns, pageable, totalCount);
        return page;
	}

	@Override
	public SessionManagement findSessionManagementById(Long sessionManagementId) {
	    SessionManagement sessionManagement = sessionManagementRepository.findById(sessionManagementId);
	    
	    if (sessionManagement == null) {
            throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT)));
        }
	    
	    List<BusinessDesign> lstBusinessDesign = getBusinessDesign(sessionManagementId);
	    sessionManagement.setListBusinessDesign(lstBusinessDesign);
        
        return sessionManagement;
	}

	@Override
	public SessionManagement registerSessionManagement(SessionManagement sessionManagement, CommonModel common) {
	    Timestamp currentTime = FunctionCommon.getCurrentTime();
	    sessionManagement.setSessionManagementType(SessionManagementMessageConst.TYPE_CUSTOMIZE);
	    sessionManagement.setProjectId(common.getWorkingProjectId());
        sessionManagement.setCreatedDate(currentTime);
        sessionManagement.setUpdatedDate(currentTime);
        // Check Name or code exist
        this.checkDuplidateNameAndCode(sessionManagement);
        sessionManagementRepository.register(sessionManagement);
        return sessionManagement;
	}

	@Override
	public void modifySessionManagement(SessionManagement sessionManagement, CommonModel common) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
	    SessionManagement sessionManagementExist = validateSessionManagement(sessionManagement.getSessionManagementId(),false);
        sessionManagement.setProjectId(common.getWorkingProjectId());
        if(!sessionManagementExist.getSessionManagementName().equals(sessionManagement.getSessionManagementName()) || !sessionManagementExist.getSessionManagementCode().equals(sessionManagement.getSessionManagementCode())){
            // Check Name or code exist
            this.checkDuplidateNameAndCode(sessionManagement);
        }
        sessionManagement.setSystemTime(currentTime);

        if (!sessionManagementRepository.modify(sessionManagement)) {
            throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
        }
        
        //insert batch job
        modifyAffected(sessionManagement,common);
	}

	@Override
	public void deleteSessionManagement(SessionManagement sessionManagement, CommonModel common) {
	    SessionManagement oldSession =  validateSessionManagement(sessionManagement.getSessionManagementId(),true);
        int rowCount = 0;
        rowCount = sessionManagementRepository.delete(sessionManagement.getSessionManagementId());

        if (0 >= rowCount) {
            throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT)));
        }
      //insert batch job
        deleteAffected(oldSession,common);
	}

    private SessionManagement validateSessionManagement(long sessionManagementId, boolean isDeleteAction) {
        SessionManagement sessionManagement = sessionManagementRepository.findById(sessionManagementId);
        if (sessionManagement == null) {
            throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT)));
        } else if (1 != sessionManagement.getSessionManagementType()){
			if(isDeleteAction){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0199, 
						MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT),sessionManagement.getSessionManagementName()));
			} else {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0200, 
						MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT),sessionManagement.getSessionManagementName()));
			}
		}

        return sessionManagement;
    }
    
    /**
     * @param sessionManagement
     * @throws BusinessException
     */
    private void checkDuplidateNameAndCode(SessionManagement sessionManagement) throws BusinessException {
        // Check Name or code exist
        Long totalCount = sessionManagementRepository.countNameCodeById(sessionManagement);
        ResultMessages resultMessages = ResultMessages.error();

        if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
            resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0002));
        } else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
            resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0003));
        } else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
            resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0002));
            resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0003));
        }
        if (resultMessages.isNotEmpty()) {
            throw new BusinessException(resultMessages);
        }
    }
    
    public List<BusinessDesign> getBusinessDesign(Long sessionManagementId){
        return businessDesignRepository.findBlogicBySessionManagementId(sessionManagementId);
    }
    
    private void modifyAffected(SessionManagement session,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(session.getProjectId()));
		jobControl.setModuleId(null);
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.SESSION_MANAGEMENT));
		jobControl.setImpactId(String.valueOf(session.getSessionManagementId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
    
    private void deleteAffected(SessionManagement session,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(session.getProjectId()));
		jobControl.setModuleId(null);
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.SESSION_MANAGEMENT));
		jobControl.setImpactId(String.valueOf(session.getSessionManagementId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(session.getSessionManagementCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
}
