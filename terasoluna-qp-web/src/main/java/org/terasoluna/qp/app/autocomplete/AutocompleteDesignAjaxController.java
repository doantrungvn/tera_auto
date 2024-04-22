package org.terasoluna.qp.app.autocomplete;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignService;


@RestController
@RequestMapping(value="autocomplete")
public class AutocompleteDesignAjaxController {
	
	@Inject
	AutocompleteDesignService autocompleteDesignService;
	
	
	@RequestMapping(value = "getForeignKeyBetweenTwoTables", method = RequestMethod.GET)
	public TableDesignForeignKey processGetForeignKeyBetweenTwoTables(@RequestParam("tableId") Long tableId,@RequestParam("joinTableId") Long joinTableId){
		return autocompleteDesignService.findForeignKeyBetweenTwoTables(tableId,joinTableId);
	}
	
	@RequestMapping(value = "getTableColumnCode", method = RequestMethod.GET)
	public List<TableDesignDetails> processGetTableColumnCode(){
		return autocompleteDesignService.getAllTableColumnByProject(SessionUtils.getCurrentProjectId());
	}
}
