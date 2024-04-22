$(document).ready(function() {
	$(".fileLogInfor").each(function () {
		var table = $(this).find(".tblFileLog");
	  	// Function to process hide, show value based on Radio click
		addRadioEvent(table);
		
		// Set event for layout selector and trigger it
		addLayoutSelectorEvent(table);
		
		// Set event for Rolling Policy selector and trigger it
		addRollingPolicySelectorEvent(table);
		
		// Set event for Triggering Policy selector and trigger it
		addTriggeringPolicySelectorEvent(table);
		
		// Set event for logType status checkbox
		$("input:checkbox[name$='LogStatus']").each(function(){
				$(this).change(function(){
					var id = $(this).attr('id');
					if($(this).is(':checked')){
						$("#tabsLogContent-0" + id.substr(id.length - 1)).toggle(true);
					}else{
						$("#tabsLogContent-0" + id.substr(id.length - 1)).toggle(false);
					}
				})
				$(this).trigger("change");
		})
	});
});
          