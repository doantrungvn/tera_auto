package org.terasoluna.qp.domain.service.screendesign;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.constants.DbDomainConst.FromResourceType;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.screenactionparam.ScreenActionParamRepository;

/**
 * @author quangvd
 *
 */
@Service
public class ScreenDesignShareServiceImpl implements ScreenDesignShareService {

	@Inject
	ScreenActionParamRepository screenActionParamRepository;
	
	@Override
	public void fix(ProblemList problemList) {
		if (problemList != null) {
			switch (problemList.getFromResourceType()) {
			case FromResourceType.BLOGIC_RELATED_SCREEN_INPUTBEAN:
				screenActionParamRepository.deleteScreenActionParamByAutofixProblem(problemList.getFromResourceId(), problemList.getResourceId());
				break;
			}
		}

	}

}
