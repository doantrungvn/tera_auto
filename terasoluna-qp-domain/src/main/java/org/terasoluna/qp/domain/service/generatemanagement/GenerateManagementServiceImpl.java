package org.terasoluna.qp.domain.service.generatemanagement;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP.Folder;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.GenerateHistory;
import org.terasoluna.qp.domain.repository.generatemanagement.GenerateManagementRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;

@Service
@Transactional
public class GenerateManagementServiceImpl implements GenerateManagementService {
	
	@Inject
	GenerateManagementRepository generateManagementRepository;
	
	@Inject
	SystemService systemService;
	
	@Override
	public Page<GenerateHistory> findPageByCriteria(Pageable pageable, Long projectId) {
		
		this.checkGenerateException();
		List<GenerateHistory> generateLst;
		long totalCount = generateManagementRepository.countBySearchCriteria(String.valueOf(projectId));
		if (0 < totalCount) {
			generateLst = generateManagementRepository.findPageBySearchCriteria(String.valueOf(projectId), systemService.getDefaultProfile().getBatchJobTimeOut(), pageable);
		} else {
			generateLst = Collections.emptyList();
		}
		Page<GenerateHistory> page = new PageImpl<GenerateHistory>(generateLst, pageable, totalCount);
		return page;
	}

	@Override
	public void registerGenerateHistory(GenerateHistory generateHistory)throws BusinessException {
			
		ResultMessages resultMessages = ResultMessages.error();
		if( DbDomainConst.GenerateHistoryMode.DOCUMENT.equals(generateHistory.getGenerateMode())
				&& generateHistory.getLanguageId() == null){
			resultMessages.add(CommonMessageConst.ERR_SYS_0025, MessageUtils.getMessage(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0020));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} 
		
		// check batch job free
		AccountProfile config = systemService.getDefaultProfile();
		
		generateHistory.setGenerateDate(FunctionCommon.getCurrentTime());
		generateHistory.setCurAppStatus(GenerateAppStatus.INIT);
		//check generate status is generating
		Long countGenerating = generateManagementRepository.countGenerateHistoryIsGenerating(generateHistory, systemService.getDefaultProfile().getBatchJobTimeOut());
		
		if(countGenerating > 0){
			String str_message ="";
			if( DbDomainConst.GenerateHistoryMode.DOCUMENT.equals(generateHistory.getGenerateMode())){
				str_message = GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0015;
			}
			if( DbDomainConst.GenerateHistoryMode.SOURCE_CODE.equals(generateHistory.getGenerateMode())){
				str_message = GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0016;
			}
			if( DbDomainConst.GenerateHistoryMode.WAR_FILE.equals(generateHistory.getGenerateMode())){
				str_message = GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0026;
			}
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0017, MessageUtils.getMessage(str_message));
		}
		
		
		Long numJobRunning = generateManagementRepository.countAllGenerateHistoryIsGenerating(StringUtils.EMPTY, systemService.getDefaultProfile().getBatchJobTimeOut());
		if( numJobRunning >= config.getMaxJobNumber()){
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0018);
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} 
		
		// get batch job path 
		String batchJobPath = config.getBatchJobPath();
		/*if(StringUtils.isBlank(batchJobPath)){
			batchJobPath = FileUtilsQP.getBatchJobPath();
		}*/	
		batchJobPath = StringUtils.appendIfMissing(batchJobPath, File.separator, File.separator);
		String batFile = "";
		if(SystemUtils.IS_OS_WINDOWS){
			batFile = batchJobPath + GenerateSourceCodeConst.GENERATE_BAT;
		}
		else {
			batFile = batchJobPath + GenerateSourceCodeConst.GENERATE_SH;
		}
		
		// check bat file is exits
		if(!new File(batFile).exists()){
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0024, batchJobPath);
			throw new BusinessException(resultMessages);
		}
		
		// insert generate history
		generateManagementRepository.registerGenerateHistory(generateHistory);
		
	}

	@Override
	public void reGenerateHistory(GenerateHistory generateHistory) throws BusinessException {
			
		
		generateHistory = generateManagementRepository.getGenerateHistoryById(generateHistory.getGenerateId());
		generateHistory.setUpdatedDate(generateHistory.getGenerateDate());
		generateHistory.setGenerateDate(FunctionCommon.getCurrentTime());
		generateHistory.setCurAppStatus(GenerateAppStatus.INIT);
		
		//check generate status is generating
		Long countGenerating = generateManagementRepository.countGenerateHistoryIsGenerating(generateHistory, systemService.getDefaultProfile().getBatchJobTimeOut());
		ResultMessages resultMessages = ResultMessages.error();
		if(countGenerating > 0){
			String str_message ="";
			if( DbDomainConst.GenerateHistoryMode.DOCUMENT.equals(generateHistory.getGenerateMode())){
				str_message = GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0015;
			}
			if( DbDomainConst.GenerateHistoryMode.SOURCE_CODE.equals(generateHistory.getGenerateMode())){
				str_message = GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0016;
			}
			if( DbDomainConst.GenerateHistoryMode.WAR_FILE.equals(generateHistory.getGenerateMode())){
				str_message = GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0026;
			}
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0017,MessageUtils.getMessage(str_message));
		}
		
		// check batch job free		
		AccountProfile config = systemService.getDefaultProfile();
		Long numJobRunning = generateManagementRepository.countAllGenerateHistoryIsGenerating(generateHistory.getGenerateId(), systemService.getDefaultProfile().getBatchJobTimeOut());
		if( numJobRunning >= config.getMaxJobNumber()){
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0018);
		} 
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} 
		
		// get batch job path 
		String batchJobPath = config.getBatchJobPath();
		/*if(StringUtils.isBlank(batchJobPath)){
			batchJobPath = FileUtilsQP.getBatchJobPath();
		}*/	
		batchJobPath = StringUtils.appendIfMissing(batchJobPath, File.separator, File.separator);
		String batFile = "";
		if(SystemUtils.IS_OS_WINDOWS){
			batFile = batchJobPath + GenerateSourceCodeConst.GENERATE_BAT;
		}
		else {
			batFile = batchJobPath + GenerateSourceCodeConst.GENERATE_SH;
		}
		
		// check bat file is exits
		if(!new File(batFile).exists()){
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0024, batchJobPath);
			throw new BusinessException(resultMessages);
		}
		
		// remove error file of prev generate
		if(!StringUtils.isEmpty(generateHistory.getFileName())){
			File file = new File(batchJobPath +  Folder.EXPORT + File.separator + generateHistory.getFileName());
			FileUtilsQP.deleteQuietly(file); 	
		}
		generateHistory.setFileName(StringUtils.EMPTY);
		
		// update generate history
		if( !generateManagementRepository.updateGenerateHistoryById(generateHistory) ){
			resultMessages.add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0018);
			throw new BusinessException(resultMessages);
		}
		
	}
	
	@Override
	public boolean updateGenerateHistory(GenerateHistory generateHistory) {
		generateHistory.setUpdatedDate(FunctionCommon.getCurrentTime());
		return generateManagementRepository.modifyGenerateHistory(generateHistory);
	}

	@Override
	public String getLastFileName(GenerateHistory generateHistory) {
		
		String fileName = generateManagementRepository.getLastFileName(generateHistory);
		return fileName;
	}
	
	@Override
	public boolean updateDownloadFlag(GenerateHistory generateHistory) {
		return generateManagementRepository.updateDownloadFlag(generateHistory);
	}

	private void checkGenerateException() {
		
//		List<JobControl> jobContrlLst = generateManagementRepository.getBatchJobError();
//		for( JobControl jobControl : jobContrlLst){
//			// update generate history
//			Long generateId = Long.parseLong(jobControl.getJobArgNm1());
//			GenerateHistory generateHistory = generateManagementRepository.getGenerateHistoryById(generateId);
//			if ( generateHistory != null ){
//				generateHistory.setUpdatedDate(generateHistory.getGenerateDate());
//				generateHistory.setGenerateDate(generateHistory.getGenerateDate());
//				generateHistory.setGenerateStatus(DbDomainConst.GenerateHistoryStatus.GENERATE_ERROR);
//				generateManagementRepository.modifyGenerateHistory(generateHistory);
//				generateManagementRepository.updateBatchJobError(jobControl);
//			}
//		}
		
	}

	
}
