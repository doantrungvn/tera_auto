package org.terasoluna.qp.app.screendesign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="screen")
public class ScreenRegisterController {

	@RequestMapping(value="register")
	public String screenRegisterForm(Model model) {
		return "screenDesign/screenregisterForm";
	}
}
