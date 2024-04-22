package org.terasoluna.qp.domain.repository.generatemanagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.GenerateHistory;

public interface GenerateManagementRepository {

	Long countBySearchCriteria(String projectId);
	
	List<GenerateHistory> findPageBySearchCriteria(@Param("projectId") String projectId, @Param("batchJobTimeOut") String batchJobTimeOut, @Param("pageable") Pageable pageable);
	
	void registerGenerateHistory(GenerateHistory generateHistory);
	
	Long countGenerateHistoryIsGenerating(@Param("generateHistory") GenerateHistory generateHistory, @Param("batchJobTimeOut") String batchJobTimeOut);
	
	//JobControl getJobSeqIdEnd();
	
	//List<JobControl> getBatchJobError();
	
	//boolean updateBatchJob(JobControl jobControl);
	
	//boolean updateBatchJobError(JobControl jobControl);
	
	boolean modifyGenerateHistory(GenerateHistory generateHistory);
	
	boolean updateGenerateHistoryById(GenerateHistory generateHistory);
	
	String getLastFileName(GenerateHistory generateHistory);
	
	Long countAllGenerateHistoryIsGenerating(@Param("generateId") String generateId, @Param("batchJobTimeOut") String batchJobTimeOut);
	
	GenerateHistory getGenerateHistoryById(String generateId);
	
	boolean updateDownloadFlag(GenerateHistory generateHistory);
	
}
