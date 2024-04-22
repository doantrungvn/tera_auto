/**
 * generate document setting
 * @author hunghx
 */
$.qp.generateDocument = (function($$module){

	var PROJECT_IS_SELECT = "1"
	var MODULE_IS_SELECT = "2";
	
	$$module.init = function(isDisplay) {
		if(isDisplay == PROJECT_IS_SELECT) {
			$(function() {
//				$('[class$="project-item"]').show();
//				$('[class$="module-item"]').hide();
				$('[class$="project-item"]').removeClass("hidden");
				$('[class$="module-item"]').addClass("hidden");
				$$module.settingCheckedForGenerate();
			});
		} else {
			$(function() {
//				$('[class$="project-item"]').hide();
//				$('[class$="module-item"]').show();
				$('[class$="project-item"]').addClass("hidden");
				$('[class$="module-item"]').removeClass("hidden");
				$$module.settingCheckedForGenerate();
			});
		}
	};

	$$module.settingCheckedForGenerate = function() {
		var $tr = $('table[id="tblGenerateSetting"]').find('>tbody>tr');
		if($tr!= undefined && $tr.length > 0){
			$tr.each(function() {
				if($(this).find(':hidden[name$=".isChecked"]').val() == 'true'){
					$(this).find(':radio').prop('checked', true);
					return false;
				}
			});
		}
	};
	
	$$module.validateBeforeSubmit = function() {
		var $tr = $('table[id="tblGenerateSetting"]').find('>tbody>tr');
		if($tr!= undefined && $tr.length > 0){
			$tr.each(function(){
				if($(this).find(':radio').is(':checked')){
					$(this).find('input[name$=".isChecked"]').val(true);
				} else {
					$(this).find('input[name$=".isChecked"]').val(false);
				}
			});
		}

		return true;
	};
	
return $$module;
})(jQuery.namespace('$.qp.generateDocument'));