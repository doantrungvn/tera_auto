package org.terasoluna.qp.app.sqldesign.ajax;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

@Controller
@RequestMapping(value="sqldesign")
public class SqlDesignAjaxController {
	
	private static final Logger logger = LoggerFactory.getLogger(SqlDesignAjaxController.class);
	
	@Inject
	public SqlDesignService sqlDesignService;
	
	@RequestMapping(value="getListTableDesignDetails", method=RequestMethod.GET)
	@ResponseBody
	public List<Map<String , String>> getListTableDesignDetails(@RequestParam("tblDesignId") String tblDesignId,
			@RequestParam("projectId") Long projectId) {
		
		String[] tblDesignIds = tblDesignId.split(":");
		
		List<Map<String , String>> lstMapTableDetails = sqlDesignService.getLstTableDesignDetails(tblDesignIds, projectId);
		
		return lstMapTableDetails;
		
	}
}
