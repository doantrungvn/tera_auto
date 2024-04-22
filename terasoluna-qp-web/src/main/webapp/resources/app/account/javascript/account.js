$(document).ready(function() {
	if($('#selectedChangePass').is(':checked')) {
		if($('input[name=accountRuleDefinitionId]').val() != 0) {
			$.ajax({
				url: CONTEXT_PATH + "/account/getAccountRuleDefinition?accountRuleDefinitionId=" + $('input[name=accountRuleDefinitionId]').val(),
				dataType: 'json',
				async: false,
				type: 'GET',
				success: function(data) {
					if(data != '' && data != null) {
						if(!data.initialPassword) {
							$("#trChangePass").show();
						} else {
							$("#trChangePass").hide();
						}
					}
				}
			})
		} else {
			$("#trChangePass").show();
		}
		
	}
});

function checkRuleHasInitialPassword(event, ui) {
	$('#inputPassword').css("display", "none");
	
	$.ajax({
		url: CONTEXT_PATH + "/account/getAccountRuleDefinition?accountRuleDefinitionId=" + $(event.target).next().val(),
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if(data != '' && data != null) {
				if(!data.initialPassword) {
					$('#inputPassword').css("display", "");
				}
				$('#initialPassword').val(data.initialPassword.toString());
			}
		}
	})
}

function removeInputPassword(event, ui) {
	if($(event.target).next().val() == "") {
		$('#inputPassword').css("display", "");
		$('#initialPassword').val("false");
	}
}

function checkRuleHasInitialPasswordForEdit() {
	$('#trChangePass').css("display", "none");

	var accountRuleDefinitionId = $('input[name=accountRuleDefinitionId]').val();
	
	if(accountRuleDefinitionId != 0) {
		$.ajax({
			url: CONTEXT_PATH + "/account/getAccountRuleDefinition?accountRuleDefinitionId=" + accountRuleDefinitionId + "&r=" + Math.random(),
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				if(data != '' && data != null) {
					if(data.initialPassword) {
						$("input[name='selectedChangePass']").parent().prev().html(dbMsgSource["sc.account.0019"]);
					} else if(!data.initialPassword) {
						$("input[name='selectedChangePass']").parent().prev().html(dbMsgSource["sc.account.0014"]);
					}
					
					if(!data.initialPassword && $('#selectedChangePass').is(':checked')) {
						$("#trChangePass").show();
					} else {
						$("#trChangePass").hide();
					}
				}
			}
		})
	} else {
		$("#trChangePass").show();
		$("input[name='selectedChangePass']").parent().prev().html(dbMsgSource["sc.account.0014"]);
	}
}

function removeInputPasswordForEdit(event, ui) {
	if($(event.target).next().val() == "" && !$('#selectedChangePass').is(':checked')) {
		$('#trChangePass').css("display", "none");
	} else if ($(event.target).next().val() == "" && $('#selectedChangePass').is(':checked')) {
		$('#trChangePass').css("display", "");
	}
}