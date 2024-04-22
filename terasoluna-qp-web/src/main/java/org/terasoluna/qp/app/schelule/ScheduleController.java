package org.terasoluna.qp.app.schelule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScheduleController {
	
	@RequestMapping(value="schedule/search")
	public String displaySchedule() {
		
		return "schedule/searchForm";
	}
	
	@RequestMapping(value="schedule/register")
	public String displayRegister() {
		
		return "schedule/registerForm";
	}
}
