package org.terasoluna.qp.domain.repository.screentransition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.ScreenTransitionBranch;
import org.terasoluna.qp.domain.model.ScreenTransitionBranchDetail;

@Repository
public interface ScreenTransitionRepository {
	
	List<ScreenTransition> findAllTransitionByModuleId(@Param("moduleId") Long moduleId, @Param("projectId") Long projectId, @Param("languageId") Long languageId);
	
	List<ScreenTransitionBranch> findAllTransitionBranchByModuleId(@Param("moduleId") Long moduleId, @Param("projectId") Long projectId);
	
	List<ScreenTransitionBranchDetail> findAllTransitionBranchDetailByModuleId(@Param("moduleId") Long moduleId, @Param("projectId") Long projectId);
	
	Long getSequences(@Param("size") Integer size);
	
	void createMultiTransition(@Param("listScreenTransitions") List<ScreenTransition> listScreenTransitions);
	
	void createMultiTransitionHaveId(@Param("listScreenTransitions") List<ScreenTransition> listScreenTransitions);
	
	void updateMultiTransition(@Param("listScreenTransitions") List<ScreenTransition> listScreenTransitions);
	
	void updateScreenTransitionBranch(@Param("updateScreenTransitionBranch") ScreenTransitionBranch updateScreenTransitionBranch);
	
	void updateScreenTransitionBranchDetail(@Param("updateScreenTransitionBranchDetail") List<ScreenTransitionBranchDetail> updateScreenTransitionBranchDetail);
	
	void deleteMultiTransition(@Param("listScreenTransitions") List<ScreenTransition> listScreenTransitions);
	
	void deleteMultiBranch(@Param("listBranch") List<ScreenTransitionBranch> listBranch);
	
	void deleteScreenTransitionBranchDetail(@Param("deleteScreenTransitionBranchDetail") List<ScreenTransitionBranchDetail> deleteScreenTransitionBranchDetail);
	
	long createTransitionBranch(ScreenTransitionBranch screenTransitionBranch);
	
	void createMultiTransitionBranchDetail(@Param("transitionBranchDetails") List<ScreenTransitionBranchDetail> transitionBranchDetails);
}
