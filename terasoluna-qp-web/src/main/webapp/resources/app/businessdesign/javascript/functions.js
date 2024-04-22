/**
 * function display
 * @author hunghx
 */
$.qp.functions = (function($$module){

	var mapFullCodeOfFormula = [];
	var BD_MODAL_NAME_FUNCTION = "#dialog-function-display";
	var lstCollapseExpanded = [];
	$$module.mapFullCodeOfFormula = mapFullCodeOfFormula;
	
	$$module.init = function(param) {
		$(function(){
			//
		});
	};
	
	$$module.openModal = function(param) {
		var modal = BD_MODAL_NAME_FUNCTION;
		
		//get function
		var url = CONTEXT_PATH + "/businessdesign/getFunctionMastersOfProject?&r="+Math.random();
		var arrRequestData = $.qp.getData(url);
		/*$(modal).find('#tbl-function-list tbody').empty()
		if(arrRequestData.length > 0) {
			for(var i =0; i < arrRequestData.length;i++) {
				var functionMaster = arrRequestData[i];
				if(functionMaster != undefined && functionMaster.functionMethod != undefined && functionMaster.functionMethod.length >0){
					var functionMasterCode = functionMaster.functionMasterCode == undefined ? "" : functionMaster.functionMasterCode;
					for(var j =0; j < functionMaster.functionMethod.length;j++){
						functionMaster.functionMethod[j].functionMasterCode = functionMasterCode;
						var obj = new Object();
						obj.functionMasterCode = functionMasterCode;
						obj.functionMethod = $$module.convertToString(functionMaster.functionMethod[j]);
						obj.functionMethodId = functionMaster.functionMethod[j].functionMethodId;
						obj.functionMethodCode = functionMaster.functionMethod[j].functionMethodCode;
						obj.idx = j+1;
						
						var $newTr = $("#tbl-function-list-template").tmpl(obj);
						$newTr.find('td:eq(1)').attr('rowspan', functionMaster.functionMethod.length);
						if(j > 0) {
							$newTr.find('td:eq(1)').remove();
						}

						$(modal).find('#tbl-function-list tbody').append($newTr);
					}
				}
			}
		} else {
			var newTr = $("#tbl-function-list-empty-template").tmpl();
			$(newTr).appendTo($(modal).find('#tbl-function-list tbody'));
		}
		*/
		
		//bangnl
		$(modal).find("#collapse-function-master").empty();
		if(arrRequestData.length <= 0){
			return;
		}
		var template = "#collapse-function-master-template";
		var container = "#collapse-function-master";
		$(modal).find(template).tmpl(arrRequestData).appendTo(container);
		lstCollapseExpanded = [];
		// Open setting
		$(modal).modal(
				  { 
				   show: true,
				   closable: false,
				   keyboard:false,
				   backdrop:'static'
				  }
				 );
	};
	
	$$module.expandCollapse = function(idFunctionMaster, functionMasterCode){
		var modal = BD_MODAL_NAME_FUNCTION;
		var $collapse = $(modal).find("#collapse-"+idFunctionMaster);
		var $container = $($collapse).find("table tbody");
		var $template = $(modal).find("#tbl-function-list-template");
		var url = CONTEXT_PATH + "/businessdesign/getFuntionMethodByFunctionMasterId?functionMasterId="+idFunctionMaster+"&r="+Math.random();
		$$module.fillDataToCollapse(url,$container,$template,functionMasterCode,idFunctionMaster);
		$collapse.collapse('toggle');
	};
	
	$$module.fillDataToCollapse = function(url,$container,$template,functionMasterCode,idFunctionMaster){
		if($.inArray(idFunctionMaster, lstCollapseExpanded) > -1){
			return;
		}
		lstCollapseExpanded.push(idFunctionMaster);
		var arrRequestData = $.qp.getData(url);
		$container.empty();
		for(var i = 0; i < arrRequestData.length; i++){
			arrRequestData[i].functionMasterCode = functionMasterCode;
			arrRequestData[i].functionMasterId = idFunctionMaster;
			arrRequestData[i].functionMethod = JSON.stringify(arrRequestData[i]);
			arrRequestData[i].idx = i+1;
		}
		$template.tmpl(arrRequestData).appendTo($container);
	};
	
	$$module.saveSelectMethod = function(obj) {
		
		var modal = BD_MODAL_NAME_FUNCTION;
		//var itemCheck = $(modal).find('#tbl-function-list tbody>tr input[name="submitColumn"]:checked');
		//bangnl
		var itemCheck = $(modal).find('.collapse table tbody>tr input[name="submitColumn"]:checked');
		if(itemCheck.length == 0) {
			alert('Please select one function!');
		} else {
			var $foumalarContent = $('#dialog-formula-setting').find('div[id="bdFormulaValueFormula"]');
			var data = $(itemCheck).attr("method");
			var value = $$module.convertToJson(data);
			var $divContent =  $$module.initialDataForFunctionOperators(value);
			$foumalarContent.append($divContent);

			$(modal).modal("hide");
		}
	};
	
	$$module.initialDataForFunctionOperators = function(objFunction){
		var template = "#tbl-formula-content-function-template";
		var divContent = $(template).tmpl();
		var divDetail = objFunction.functionMasterCode +"."+objFunction.functionMethodCode;
		var obj = new Object();
		obj.type = 17;
		obj.functionMethodId = objFunction.functionMethodId;
		obj.functionMethodCode = objFunction.functionMethodCode;
		obj.functionMasterCode = objFunction.functionMasterCode;
		divDetail+="(";
		for(var i=0; i < objFunction.functionMethodInput.length;i++){
			divDetail+=",";
		}
		divDetail+=")";
		$(divContent).find("div.qp-formula-component-content").append(divDetail);
		$(divContent).find("input[name=formulaitem]").val($$module.convertToString(obj));
		$(divContent).find("div.qp-formula-component-content").attr("hiddenvalue",$$module.convertToString(objFunction));
		return divContent;
		
	}
	
	$$module.convertToJson = function(string) {
		var json = {};
		try {
			json = JSON.parse(string);
		} catch (e) {
			json = JSON.parse("{" + string + "}");
		}
		return json;
	};

	$$module.convertToString = function(json) {
		return  JSON.stringify(json);
	};
return $$module;
})(jQuery.namespace('$.qp.functions'));