package org.terasoluna.qp.domain.service.problemlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListCriteria;

@Service
public interface ProblemListService{

	Page<ProblemList> searchProblemList(ProblemListCriteria criteria, Pageable pageable, Long languageId);
	
	void registerProblemList(ProblemList problemList);
	
	void deleteProblem(Long problemId);

	void autoFix(ProblemList map);
	
}
