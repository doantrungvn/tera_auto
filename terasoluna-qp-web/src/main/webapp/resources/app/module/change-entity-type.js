var ENTITY_TYPE = {
	SINGLE : "0",
	LIST : "1"
}
function changeEntityType() {
	var select = $('select[name*="tableMappingType"]').eq(0);
	if (select.length > 0) {
		var entityType = select.val();
		if (entityType == ENTITY_TYPE.LIST) {
			// Disable screen pattern [Register screen], [View screen ], [Modify screen]
			$('[name="screenPatternTypes"]').filter(function() {
			     return (parseInt($(this).attr('value')) || 0) > 1;
			}).attr('disabled','disabled').attr('checked', false);
			// Disable confirmation type [Message], [Screen]
			$('[name="confirmationType"]').filter(function() {
			     return (parseInt($(this).attr('value')) || 0) > 0;
			}).attr('disabled','disabled').attr('checked', false);
			$('[name="confirmationType"]').val([0]);
			// Disable completion type [Screen]
			$('[name="completionType"]').filter(function() {
			     return (parseInt($(this).attr('value')) || 0) > 1;
			}).attr('disabled','disabled').attr('checked', false);
			$('[name="completionType"]').val([1]);
		} else {
			$('[type="checkbox"]').removeAttr('disabled');
			$('[name="confirmationType"]').removeAttr('disabled');
			$('[name="completionType"]').removeAttr('disabled');
		}
	} else {
		$('[type="checkbox"]').removeAttr('disabled');
		$('[name="confirmationType"]').removeAttr('disabled');
		$('[name="completionType"]').removeAttr('disabled');
	}
}

function changeEntityTypeForDropDown() {
	var select = $('select[name*="tableMappingType"]').eq(0);
	if (select.length > 0) {
		var entityType = select.val();
		if (entityType == ENTITY_TYPE.LIST) {
			$('#screenPatternType').val('1');
			$('#screenPatternType').find('option:not(:selected)').attr('disabled', true);
			$("#trNotification").hide();
		} else {
			$('#screenPatternType').find('option').removeAttr('disabled');
			$("#trNotification").show();
		}
	} else {
		$('#screenPatternType').find('option').removeAttr('disabled');
		if ($('[name="screenPatternType"]').val() != "1" && $('[name="screenPatternType"]').val() != "3") {
			$("#trNotification").show();
		}
	}
	changeScreenPattern($('#screenPatternType'));
}