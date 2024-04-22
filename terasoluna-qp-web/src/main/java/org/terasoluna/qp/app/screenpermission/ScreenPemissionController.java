package org.terasoluna.qp.app.screenpermission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "screenpermission")
public class ScreenPemissionController {
	private static final Logger logger = LoggerFactory.getLogger(ScreenPemissionController.class);
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(ScreenPermissionForm form, Model model, RedirectAttributes redirectAttr) {
		if(form.getIndex() % 2 == 1)
			return "screenpermission/modifyForm";
		else
			return "screenpermission/viewForm";
	}
	
}
