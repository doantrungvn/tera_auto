/**
 * generate document setting
 * @author hunghx
 */
$.qp.initial = (function($$module){

	var INITIAL_PROJECT_IS_SELECT = 0
	var INITIAL_MODULE_IS_SELECT = 1;
		
	$$module.init = function(screenMain) {
		$(function() {
//			$('table[id="tblProject"]').show();
//			$('table[id="tblModule"]').hide();
			$('table[id="tblProject"]').removeClass("hidden");
			$('table[id="tblModule"]').addClass("hidden");
/*			$('table[id="tblGenerateSetting"]').find('tbody>tr[id="module"]').hide().nextAll().hide();*/
			$('button[id="projectscreen"]').attr('scope', INITIAL_PROJECT_IS_SELECT);
			if ($("#fileName").val() != null && $("#fileName").val() != '') {
				window.location.href = CONTEXT_PATH + "/downloadLarge?fileName=" + $("#fileName").val();
			}

			if($('input:hidden[name="selectType"]').val() == "1") {
				$('input:radio[id="selectType1"]').prop('checked', true);
				// Setting display
				$('table[id="tblGenerateSetting"]').find('tbody>tr[id="module"]').addClass("hidden");
				$('table[id="tblGenerateSetting"]').find('tbody>tr[id="project"]').removeClass("hidden");
				// Setting checked
				if($('input:hidden[name="generateDocumentItem.documentItemType"]').val().length > 0) {
					var $tr = $('table[id="tblGenerateSetting"]').find('tbody>tr[class$="item-checked-project"]');
					$tr.each(function() {

						if($(this).find('input:hidden[name$=".documentItemType"]').val() == $('input:hidden[name="generateDocumentItem.documentItemType"]').val()
								&& $(this).find('input:hidden[name$=".documentItemParenItemType"]').val() == $('input:hidden[name="generateDocumentItem.documentItemParenItemType"]').val()){
							$(this).find('input:radio').prop('checked', true);
							return false;
						}
					});
				}
			} else {
				$('input:radio[id="selectType2"]').prop('checked', true);
				// Setting display
//				$('table[id="tblProject"]').hide();
//				$('table[id="tblModule"]').show();
				$('table[id="tblProject"]').addClass("hidden");
				$('table[id="tblModule"]').removeClass("hidden");
				$('table[id="tblGenerateSetting"]').find('tbody>tr[id="module"]').removeClass("hidden");
				$('table[id="tblGenerateSetting"]').find('tbody>tr[id="project"]').addClass("hidden");
				// Setting checked
				if($('input:hidden[name="generateDocumentItem.documentItemType"]').val().length > 0) {
					var $tr = $('table[id="tblGenerateSetting"]').find('tbody>tr[class$="item-checked-module"]');
					$tr.each(function() {
						
						if($(this).find('input:hidden[name$=".documentItemType"]').val() == $('input:hidden[name="generateDocumentItem.documentItemType"]').val()){
							$(this).find('input:radio').prop('checked', true);
							return false;
						}
					});
				}
				
				// Setting checked for module id
				if($('input:hidden[name="module.moduleId"]').val().length > 0) {
					var $tr = $('table[id="tblModule"]').find('>tbody>tr');
					$tr.each(function() {
						if($(this).find('input:hidden[name$=".moduleId"]').val() == $('input:hidden[name="module.moduleId"]').val()){
							$(this).find('input:radio').prop('checked', true);
							return false;
						}
					});
				}
			}
			
			
			// Hidden all document not yes implement
			$$module.hiddenAllDocumentNotImplement();
		});
	};
	
	$$module.hiddenAllDocumentNotImplement = function() {
		var $trProject = $('table[id="tblGenerateSetting"]').find('tbody>tr[name$="rowProject"]');
		var $trModule = $('table[id="tblGenerateSetting"]').find('tbody>tr[name$="rowModule"]');
		
		if($('input:radio[id="selectType1"]').prop('checked') == true) {
			$trProject.each(function(){
				
				switch ($(this).find(':hidden[name$=".documentItemParenItemType"]').val()) {
				case "rd":
					
					switch ($(this).find(':hidden[name$=".documentItemType"]').val()) {
					case "1":
					case "3":
					case "4":
					case "5":
					case "7":
					case "9":
					case "10":
						$(this).addClass("hidden");
						break;
					default:
						$(this).removeClass("hidden");
						break;
					}
					
					break;
	
				case "ed":
					
					switch ($(this).find(':hidden[name$=".documentItemType"]').val()) {
					case "0":
					case "1":
					case "2":
					case "4":
						$(this).addClass("hidden");
						break;
					default:
						$(this).removeClass("hidden");
						break;
					}
	
					break;
				}
			});
		} else {
			$trModule.each(function(){
				
				switch ($(this).find(':hidden[name$=".documentItemParenItemType"]').val()) {
				case "rd":
					
					break;
	
				case "ed":
					
					switch ($(this).find(':hidden[name$=".documentItemType"]').val()) {
					case "0":
					case "1":
					case "2":
					case "3":
						$(this).addClass("hidden");
						break;
					default:
						$(this).removeClass("hidden");
						break;
					}
					
					break;
				}
				
			});
		}
	};

	$$module.selectTypeScope = function(thiz) {
		$('table[id="tblGenerateSetting"]').find('>tbody>tr').show();
		if($(thiz).val() == '1' && $(thiz).is(':checked')) {
			$('button[id="projectscreen"]').attr('scope', INITIAL_PROJECT_IS_SELECT);
			$('table[id="tblProject"]').removeClass("hidden");
			$('table[id="tblModule"]').addClass("hidden");
			$trModule = $('table[id="tblGenerateSetting"]').find('>tbody>tr[id="project"]').removeClass("hidden");
//			$trModule.hide();
//			$trModule.nextAll().hide();
			var $trFirst = $('table[id="tblGenerateSetting"]').find('>tbody>tr:first');
			$trFirst.nextAll().each(function(){
				if($(this).attr('id') == 'project') {
					$(this).removeClass("hidden");
				} else {
					$(this).addClass("hidden");
				}
			});
			
		} else if($(thiz).val() == '2' && $(thiz).is(':checked')) {
			$('button[id="projectscreen"]').attr('scope', INITIAL_MODULE_IS_SELECT);
			$('table[id="tblProject"]').addClass("hidden");
			$('table[id="tblModule"]').removeClass("hidden");
			var $trFirst = $('table[id="tblGenerateSetting"]').find('>tbody>tr:first');
			$trFirst.hide();
			$trFirst.nextAll().each(function(){
				if($(this).attr('id') == 'module') {
					$(this).removeClass("hidden");
				} else {
					$(this).addClass("hidden");
				}
			});
		}
		
		// Hidden all document not yes implement
		$$module.hiddenAllDocumentNotImplement();
		
		$('#projectscreen').attr('data-confirm-dialog-flg', 'true');
	};

	$$module.validateBeforeSubmit = function() {
		var messageErr = "";
		if($('input:radio[id="selectType1"]').is(':checked')) {
			var $inputRadio = $('table[id="tblGenerateSetting"]').find('tbody>tr[class$="item-checked-project"] input:radio:checked');
			if($inputRadio.length == 0) {
				messageErr = dbMsgSource['sc.generatedocument.0063'].replace("{0}", dbMsgSource['sc.generatedocument.0003']) + "\r";
			}

			if(messageErr != "") {
				alert(messageErr);
				return false;
			}
			
			$$module.settingHiddenDocument($inputRadio.closest('tr'));
			$('input:hidden[name="selectType"]').val(1);
			$$module.switchOnSelectDocument($inputRadio, "item-checked-project");
		} else {
			var $inputRadio = $('table[id="tblGenerateSetting"]').find('tbody>tr[class$="item-checked-module"] input:radio:checked');
			if($inputRadio.length == 0) {
				messageErr = dbMsgSource['sc.generatedocument.0063'].replace("{0}", dbMsgSource['sc.generatedocument.0003']) + "\r";
			}
			
			var $trModule = $('table[id="tblModule"]').find('tbody>tr input:radio:checked').closest('tr');
			if($trModule.length == 0) {
				messageErr += dbMsgSource['sc.generatedocument.0062'].replace("{0}", dbMsgSource['sc.generatedocument.0006']) + "\r";
			};

			if(messageErr != "") {
				alert(messageErr);
				return false;
			}
			
			$$module.settingHiddenDocument($inputRadio.closest('tr'));
			$('input:hidden[name="selectType"]').val(2);
			$$module.switchOnSelectDocument($inputRadio, "item-checked-module");
			$$module.switchOnSelectModuleLst($trModule);
		}
		
		if ($('input:radio[name=moduleED]:checked').val() == "4" && $('input:radio[name=selectType]:checked').val() == "2") {
			$('#projectscreen').attr('data-confirm-dialog-flg', 'false');
		} else {
			$('#projectscreen').attr('data-confirm-dialog-flg', 'true');
		}
		
		return true;
	};
	
	$$module.settingHiddenDocument = function(obj){
		
		$('input:hidden[name="generateDocumentItem.documentItemScopeItemType"]')
			.val($(obj).find('input[name$=".documentItemScopeItemType"]').val());
		$('input:hidden[name="generateDocumentItem.documentItemParenItemType"]')
			.val($(obj).find('input[name$=".documentItemParenItemType"]').val());
		$('input:hidden[name="generateDocumentItem.documentItemType"]')
			.val(parseInt($(obj).find('input[name$=".documentItemType"]').val()));
		$('input:hidden[name="generateDocumentItem.documentItemTemplateName"]')
			.val($(obj).find('input[name$=".documentItemTemplateName"]').val());
	};

	$$module.switchOnSelectDocument = function(obj, clazz) {
		// setting none select for on document level same
		if($(obj) == undefined || $(obj).length == 0) {
			$.each($('table[id="tblGenerateSetting"]').find('>tbody>tr'), function() {
				$(this).find(':hidden[name$=".isChecked"]').val(false);
			});
		} else {
			$.each($(obj).closest('table').find('>tbody>tr[class$='+clazz+']'), function(){
				$(this).find(':hidden[name$=".isChecked"]').val(false);
			});
			$(obj).closest('tr').find(':hidden[name$=".isChecked"]').val(true);
		}
	};
	
	$$module.switchOnSelectModuleLst = function(obj) {
		// setting none select for on module list level same
		if($(obj) == undefined || $(obj).length == 0) {
			$.each($('table[id="tblModule"]').find('>tbody>tr'), function() {
				$(this).find(':hidden[name$=".selectedGenerate"]').val(0);
			});
		} else {
			$.each($(obj).closest('table').find('>tbody>tr'), function() {
				$(this).find(':hidden[name$=".selectedGenerate"]').val(0);
			});
			$(obj).find(':hidden[name$=".selectedGenerate"]').val(1);
			$('input:hidden[name="module.moduleId"]').val($(obj).find(':hidden[name$=".moduleId"]').val());
		}
	};
	
return $$module;
})(jQuery.namespace('$.qp.initial'));