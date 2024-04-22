package org.terasoluna.qp.app.loggingmanagement;

import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LogManagementConst;
import org.terasoluna.qp.domain.model.ConversionPattern;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.loggingmanagement.LoggingManagementService;

@Controller
@RequestMapping(value = "loggingmanagement")
public class LoggingManagementController extends BaseController {

	private static final String LINK_MODIFY = "loggingmanagement/modifyForm";
	private static final String MODIFY_REDIRECT_PATH = "redirect:/loggingmanagement/modify";

	@Inject
	Mapper beanMapper;

	@Inject
	LoggingManagementService loggingManagementService;

	@Inject
	LoggingManagementValidator loggingManagementValidator;

	@InitBinder("loggingManagementForm")
	public void initBinder(WebDataBinder webDataBinder) {
		moduleCode = CommonMessageConst.TQP_LOGGINGMANAGEMENT;
		webDataBinder.addValidators(loggingManagementValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute
	public void initService() {
		moduleCode = CommonMessageConst.TQP_LOGGINGMANAGEMENT;
	}
	
	@ModelAttribute
	public LoggingManagementForm setUpFormLoggingManagement() {
		LoggingManagementForm loggingManagementForm = new LoggingManagementForm();
		// Get current working project Information
		Project project = SessionUtils.getCurrentProject();

		// Set project information to form
		loggingManagementForm.setCurrentProject(project);
		// Get list conversion pattern
		List<ConversionPattern> lstPattern = loggingManagementService.findAllConversionPattern();
		loggingManagementForm.setLstConversionPattern(lstPattern);
		return loggingManagementForm;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute LoggingManagementForm loggingManagementForm, Model model, SessionStatus sessionStatus) {

		// Get consoleLog of current project
		Log consoleLog = loggingManagementService.findLogByTypeAndProjectId(loggingManagementForm.getCurrentProject().getProjectId(), DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE);

		// Get fileLog of current project
		Log fileLog = loggingManagementService.findLogByTypeAndProjectId(loggingManagementForm.getCurrentProject().getProjectId(), DbDomainConst.LogManagementType.LOG_TYPE_FILE);

		// Get fileLog of current project
		Log databaseLog = loggingManagementService.findLogByTypeAndProjectId(loggingManagementForm.getCurrentProject().getProjectId(), DbDomainConst.LogManagementType.LOG_TYPE_DB);

		if (consoleLog != null) {
			// Set data to Form
			loggingManagementForm.setConsoleLog(consoleLog);
			loggingManagementForm.setConsoleLogStatus((consoleLog.getStatus() == 1) ? true : false);
		} else {
			loggingManagementForm.setConsoleLog(new Log());
			loggingManagementForm.getConsoleLog().setLogType(DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE);
		}

		if (fileLog != null) {
			// Set data to Form
			loggingManagementForm.setFileLog(fileLog);
			loggingManagementForm.setFileLogStatus((fileLog.getStatus() == 1) ? true : false);
		} else {
			loggingManagementForm.setFileLog(new Log());
			loggingManagementForm.getFileLog().setLogType(DbDomainConst.LogManagementType.LOG_TYPE_FILE);
		}

		if (databaseLog != null) {
			// Set data to Form
			loggingManagementForm.setDatabaseLog(databaseLog);
			loggingManagementForm.setDatabaseLogStatus((databaseLog.getStatus() == 1) ? true : false);
		} else {
			loggingManagementForm.setDatabaseLog(new Log());
			loggingManagementForm.getDatabaseLog().setLogType(DbDomainConst.LogManagementType.LOG_TYPE_DB);
		}
		return LINK_MODIFY;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processSave(@Validated @ModelAttribute LoggingManagementForm loggingManagementForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			ValidationUtils.setBindingResult(bindingResult, model);

			return LINK_MODIFY;
		}

		checkChangeProject(true);
		/** Get and set consoleLog information */
		Log consoleLog = loggingManagementForm.getConsoleLog();
		if (consoleLog == null) {
			consoleLog = new Log();
		}
		consoleLog.setStatus((loggingManagementForm.getConsoleLogStatus()) ? 1 : 0);
		// Prepare data before modify (in case new)
		consoleLog.setProjectId(SessionUtils.getCurrentProject().getProjectId());
		consoleLog.setLogType(DbDomainConst.LogManagementType.LOG_TYPE_CONSOLE);

		/** Get and set fileLog information */
		Log fileLog = loggingManagementForm.getFileLog();
		if (fileLog == null) {
			fileLog = new Log();
		}
		fileLog.setStatus((loggingManagementForm.getFileLogStatus()) ? 1 : 0);
		// Prepare data before modify (in case new)
		fileLog.setProjectId(SessionUtils.getCurrentProject().getProjectId());
		fileLog.setLogType(DbDomainConst.LogManagementType.LOG_TYPE_FILE);

		/** Get and set databaseLog information */
		Log databaseLog = loggingManagementForm.getDatabaseLog();
		if (databaseLog == null) {
			databaseLog = new Log();
		}
		databaseLog.setStatus((loggingManagementForm.getDatabaseLogStatus()) ? 1 : 0);
		// Prepare data before modify (in case new)
		databaseLog.setProjectId(SessionUtils.getCurrentProject().getProjectId());
		databaseLog.setLogType(DbDomainConst.LogManagementType.LOG_TYPE_DB);

		try {
			loggingManagementService.modifyLog(consoleLog);
			loggingManagementService.modifyLog(fileLog);
			loggingManagementService.modifyLog(databaseLog);
			attributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(LogManagementConst.SC_LOGGINGMANAGEMENT_0001)));
			return MODIFY_REDIRECT_PATH;
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return LINK_MODIFY;
		}
	}
}
