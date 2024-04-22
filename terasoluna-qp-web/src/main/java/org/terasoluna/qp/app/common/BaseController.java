package org.terasoluna.qp.app.common;

import org.springframework.stereotype.Controller;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;

@Controller
public class BaseController {

	protected String moduleCode;

	public void setOldProjectId(Long oldProjectId) {
		/* SessionUtils.setOldProjectId(moduleCode, oldProjectId); */
	}

	/**
	 * 
	 * @param checkProjectStatus
	 *            If action is: Delete, Insert, Update, set checkProjectStatus is TRUE
	 */
	public void checkChangeProject(boolean checkProjectStatus) {

		Project project = SessionUtils.getCurrentProject();
		/*Long projectId = project.getProjectId();
		Long oldProjectId = SessionUtils.getOldProjectId(moduleCode);

		if (projectId == null || (oldProjectId != null && !projectId.equals(oldProjectId))) {
			throw new BusinessException(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0105));
		}
		 */
		if (checkProjectStatus) {
			if (DbDomainConst.DesignStatus.FIXED.equals(project.getStatus())) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, project.getProjectName()));
			}
		}

	}

	public CommonModel initCommon(){
		CommonModel common = new CommonModel();
		LanguageDesign currentLanguageDesign = SessionUtils.getCurrentLanguageDesign();
		common.setWorkingLanguageId(currentLanguageDesign.getLanguageId());
		common.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		Long accountId = SessionUtils.getAccountId();
		common.setCreatedBy(accountId);
		common.setUpdatedBy(accountId);
		common.setWorkingLanguageDesign(currentLanguageDesign);
		return common;
	}

}