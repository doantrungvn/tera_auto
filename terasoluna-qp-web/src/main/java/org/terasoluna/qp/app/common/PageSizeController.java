package org.terasoluna.qp.app.common;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.service.common.PageSizeInput;
import org.terasoluna.qp.domain.service.common.PageSizeOutput;
import org.terasoluna.qp.domain.service.common.PageSizeService;
import org.terasoluna.qp.domain.service.common.PageSizeServiceHelper;

@Controller
@RequestMapping(value = "PageSize")
public class PageSizeController {

	private static final Logger logger = LoggerFactory.getLogger(PageSizeController.class);
	@Inject
	PageSizeService pageSizeService;


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setPageSize", method = RequestMethod.GET)
	@ResponseBody
	public PageSizeOutput pageSize(@RequestParam("data") String parameters, Model model) {

		PageSizeInput param = new PageSizeInput();
		PageSizeOutput output = new PageSizeOutput();
		if (parameters.isEmpty()) {
			logger.debug("");
			output.setStatus(PageSizeServiceHelper.RESULT_FAIL);
			return output;
		}

		// convert string json to object
		param = DataTypeUtils.toObject(parameters, PageSizeInput.class);
		Map<String,String> mapPageSizeSession = (Map<String, String>) SessionUtils.get(SessionUtils.PAGESIZE_INFOR);
		mapPageSizeSession = PageSizeServiceHelper.addPageSize(mapPageSizeSession, param);
		String jsonValue = DataTypeUtils.toJson(mapPageSizeSession);
		Account account = (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR);
		output = pageSizeService.setMapPageSize(account, jsonValue);
		if(PageSizeServiceHelper.RESULT_OK.equals(output.getStatus()))
		{
			SessionUtils.set(SessionUtils.PAGESIZE_INFOR, mapPageSizeSession);
		}
		return output;
	}
}
