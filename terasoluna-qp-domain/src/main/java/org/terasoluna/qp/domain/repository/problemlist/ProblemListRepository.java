package org.terasoluna.qp.domain.repository.problemlist;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.ProblemList;

public interface ProblemListRepository {
	
	long countBySearchCriteria(@Param("criteria") ProblemListCriteria criteria);
	
	List<ProblemList> findPageBySearchCriteria(@Param("criteria") ProblemListCriteria criteria, @Param("pageable") Pageable pageable,@Param("languageId") Long languageId);
	
	void registerProblemList(ProblemList problemList);
	
	void multiRegisterProblem(@Param("problemLists") List<ProblemList> problemLists);
	
	int deleteProblem(Long problemId);
	
	void deleteResourceType(@Param("fromResourceType") Integer fromResourceType, @Param("listFromResouceId") List<Long> listFromResouceId);
	
	void deleteFromResourceTypeOfTblDesign(@Param("listFromResourceType") List<Integer> listFromResourceType, @Param("listFromResouceId") List<Long> listFromResouceId);
	
	void deleteFromResourceTypeOfProject(@Param("resourceType") int resourceType, @Param("resourceId") Long resourceId);

	void deleteResourceOfBLogic(@Param("fromResourceType") Integer fromResourceType, @Param("fromResourceId") Long fromResouceId);
	
	void deleteByTargetId(@Param("resourceType") Integer resourceType, @Param("resourceId") Long resourceId);
	
	ProblemList findOneById(Long problemId);
	
	ProblemList findByResourceType(ProblemList problemList);
	
	void deleteByLstProblemList(@Param("lstProblemLists") List<ProblemList> lstProblemLists);

}
