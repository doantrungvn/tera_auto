package org.terasoluna.qp.domain.service.problemlist;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst.AutoFix;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ResourceType;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListCriteria;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignShareService;

@Service
@Transactional
public class ProblemListServiceImpl implements ProblemListService{

	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	BusinessDesignShareService businessDesignShareService;
	
	@Inject
	ScreenDesignShareService screenDesignShareService;
	
	@Override
	public Page<ProblemList> searchProblemList(ProblemListCriteria criteria,Pageable pageable, Long languageId) {
		long totalCount = problemListRepository.countBySearchCriteria(criteria);
		List<ProblemList> problemLists;
		if( 0 < totalCount) {
			problemLists = problemListRepository.findPageBySearchCriteria(criteria, pageable, languageId);
		} else {
			problemLists = Collections.emptyList();
		}
		Page<ProblemList> page = new PageImpl<ProblemList>(problemLists, pageable, totalCount);
		return page;
	}

	@Override
	public void registerProblemList(ProblemList problemList) {
		
		if (problemList != null) {
			problemListRepository.registerProblemList(problemList);
		}
	}

	@Override
	public void deleteProblem(Long problemId) {
		int result = problemListRepository.deleteProblem(problemId);
		if(result == 0 ){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037,
				MessageUtils.getMessage(ProblemListMessageConst.SC_PROBLEMLIST_0012)));
		}
	}

	@Override
	public void autoFix(ProblemList problemList) {
		problemList = problemListRepository.findOneById(problemList.getProblemId());
		if(!(problemList == null || !StringUtils.isBlank(problemList.getResourceName()))){
			if(problemList.getAutofixFlg()==AutoFix.ENABLE){
				switch(problemList.getResourceType()){
					case ResourceType.BLOGIC:
						businessDesignShareService.fix(problemList);
						break;
					case ResourceType.SCREEN_DESIGN:
						screenDesignShareService.fix(problemList);
						break;
				}
			}
			problemListRepository.deleteProblem(problemList.getProblemId());
		} else {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037,
					MessageUtils.getMessage(ProblemListMessageConst.SC_PROBLEMLIST_0012)));
		}
		
	}

}
