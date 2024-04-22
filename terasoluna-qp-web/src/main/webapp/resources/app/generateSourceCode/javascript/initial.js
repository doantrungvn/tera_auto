/**
 * generate document setting
 * @author hunghx
 */
$.qp.initial = (function($$module){

	var INITIAL_PROJECT_IS_SELECT = 0
	var INITIAL_MODULE_IS_SELECT = 1;
	
	$$module.switchAllModuleSelect = function(thiz) {
		var $table = $(thiz).closest('table');
		switch ($(thiz).attr('scope')) {
		case "root":
			$table.find('input[scope="node"]').prop('checked', $(thiz).is(':checked'));
			break;
	
		default:
			var $checkBoxChecked = $table.find('input[scope="node"]:checked');
			var $checkBoxAll = $table.find('input[scope="node"]');
			if($checkBoxChecked.length == $checkBoxAll.length && $(thiz).is(':checked')) {
				$table.find('input[scope="root"]').prop('checked', $(thiz).is(':checked'));
			} else {
				$table.find('input[scope="root"]').prop('checked', false);
			}
			break;
		}
	};
	
	$$module.moduleClick = function(thiz) {
		$('#generateType1').prop('checked', true);
	}
	
	$$module.warFileClick = function(thiz) {
		var $listCheckbox = $(thiz).closest('table').find('tbody>tr input:checkbox');
		$listCheckbox.each(function() {
			$(this).prop('checked', false);
		});
	}

	$$module.validateBeforeSubmit = function() {
		if ($('#generateType2').is(':checked')) {
			return true;
		}
		
		var $listSourceCodeSelect = $('table[id="tblGenerateSetting"]').find('tbody>tr input[name="generateTypeItem"]:checkbox:checked');
		var $listSourceCode = $('table[id="tblGenerateSetting"]').find('tbody>tr input[name="generateTypeItem"]:checkbox');
		
		$listSourceCode.closest('tr').each(function(){
			$(this).find('input:hidden[name$=".isChecked"]').val(false);
		});
		
		if ($('[name="selectType"]:checked').val() == 2) {
			if($listSourceCodeSelect.length > 0 ) {
				$listSourceCodeSelect.closest('tr').find('input:hidden[name$=".isChecked"]').val(true);
			}
//			else {
//				alert(dbMsgSource["sc.generatesourcecode.0014"]);
//				return false;
//			}
		}
		
		var flgErr = false;
		if ($('[name="selectType"]:checked').val() == 1) {
			flgErr = true;
		} else {
			$('table[id="tblModule"]').find('>tbody>tr').each(function(){
				if($(this).find('td:first :checkbox').is(':checked')) {
					$(this).find('td:first input:hidden[name$=".selectedGenerate"]').val(1);
					flgErr = true;
				} else {
					$(this).find('td:first input:hidden[name$=".selectedGenerate"]').val(0);
				}
			});
		}
		
		if(!flgErr) {
			alert(dbMsgSource["sc.generatesourcecode.0013"]);
			return false;
		}
		
		return true;
	};
				
return $$module;
})(jQuery.namespace('$.qp.initial'));