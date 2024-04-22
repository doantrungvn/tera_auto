/**
 * 
 */
$(document).ready(function(){
	
//	$.qp.initalFancybox("qp-link-popup", null); 
	$.qp.initialDateTimePicker($(".qp-input-datetimepicker"));
	$.qp.initialDateTimePicker($(".qp-input-from-datetimepicker"));
	$.qp.initialDateTimePicker($(".qp-input-to-datetimepicker"));	
	
	$.qp.initialDateTimePickerDetail($(".qp-input-datetimepicker-detail"));
	$.qp.initialDateTimePickerDetail($(".qp-input-from-datetimepicker-detail"));
	$.qp.initialDateTimePickerDetail($(".qp-input-to-datetimepicker-detail"));
	
	$.qp.initialDatePicker($(".qp-input-datepicker"));
	$.qp.initialDatePicker($(".qp-input-from-datepicker"));
	$.qp.initialDatePicker($(".qp-input-to-datepicker"));
	
	$.qp.initialTimePicker($(".qp-input-timepicker"));
	$.qp.initialTimePicker($(".qp-input-from-timepicker"));
	$.qp.initialTimePicker($(".qp-input-to-timepicker"));
	
	$.qp.initialTimePicker($(".qp-input-timepicker-detail"));
	$.qp.initialTimePicker($(".qp-input-from-timepicker-detail"));
	$.qp.initialTimePicker($(".qp-input-to-timepicker-detail"));
	
	$.qp.autoCheckbox();
	$.qp.formatInteger($('.qp-input-integer'));
	$.qp.formatInteger($('.qp-input-from-integer'));
	$.qp.formatInteger($('.qp-input-to-integer'));

	$.qp.formatBigint($('.qp-input-bigint'));
	$.qp.formatBigint($('.qp-input-from-bigint'));
	$.qp.formatBigint($('.qp-input-to-bigint'));
	
	$.qp.formatSerial($('.qp-input-serial'));
	$.qp.formatSerial($('.qp-input-from-serial'));
	$.qp.formatSerial($('.qp-input-to-serial'));
	
	$.qp.formatBSerial($('.qp-input-bigserial'));
	$.qp.formatBSerial($('.qp-input-from-bigserial'));
	$.qp.formatBSerial($('.qp-input-to-bigserial'));
	
	$.qp.formatSmallint($('.qp-input-smallint'));
	$.qp.formatSmallint($('.qp-input-from-smallint'));
	$.qp.formatSmallint($('.qp-input-to-smallint'));
	
	$.qp.formatFloat($('.qp-input-float'));
	$.qp.formatFloat($('.qp-input-from-float'));
	$.qp.formatFloat($('.qp-input-to-float'));
	
	$.qp.formatCurrency($('.qp-input-currency'));
	$.qp.formatCurrency($('.qp-input-from-currency'));
	$.qp.formatCurrency($('.qp-input-to-currency'));
	
	$.qp.formatPercentage($('.qp-input-percentage'));
	$.qp.formatPercentage($('.qp-input-from-percentage'));
	$.qp.formatPercentage($('.qp-input-to-percentage'));
	
/*	$.qp.formatInteger($('.qp-input-integer-detail')); 
	$.qp.formatFloat($('.qp-input-float-detail')); 
	$.qp.formatCurrency($('.qp-input-currency-detail')); 
	$.qp.formatPercentage($('.qp-input-percentage-detail'));	*/
	
	$.qp.initialPopupNavigationLink('.qp-link-popup-navigate');
	$.qp.formatPercentageDecimal($('.qp-input-percentage-decimal'));
	$.qp.formatPercentageDecimal($('.qp-input-from-percentage-decimal'));
	$.qp.formatPercentageDecimal($('.qp-input-to-percentage-decimal'));
	
	$.qp.initConfirm($(".qp-dialog-confirm"));
	$.qp.initConfirmCustom($(".qp-confirm-custom"));
	
	$(".qp-input-customselect .dropdown-menu .option").click(function(){
		var selText = $(this).text();
		$(this).parents('.qp-input-customselect').find('.dropdown-toggle').val(selText);
	});
	$.qp.initMoreToggle('qp-toggle-more');
	$.qp.initalButton($(".qp-button-search"));
	$.qp.initalButton($(".qp-button-save"));
	$.qp.initalButton($(".qp-button-cancel"));
	$.qp.initalButton($(".qp-button-export"));
	$.qp.initalButton($(".qp-button-register"));
	$.qp.initalButton($(".qp-button-create"));
	$.qp.initalButton($(".qp-button-edit"));
	$.qp.inititalInputFile(".qp-input-file");
	$.qp.initialTouchSpin($(".qp-numeric-up-down"));
	$.qp.initialAutocomplete($(".qp-input-autocomplete"));
	$('.qp-disable').attr('disabled', 'disabled');
	$.qp.initialPopstate();
	$.qp.initialTableAddRemove();
	
	$.qp.initialConventionNameCode();
	
	
	$('[data-submenu]').each(function(){
		$(this).submenupicker();
	});
	$.qp.initFloatVerticalMenu();
	
	
	
	$("form").find('input[type=text],textarea,select').filter(':visible:first:not(.qp-input-autocomplete)').focus();

	$('[data-toggle="tooltip"]').tooltip();
	//end add by DungNN
});

//start add by KhanhTH
//prevent backspace key from navigating back and prevent load error screen when hit F5 key while screen has validation errors
$(document).unbind('keydown').bind('keydown', function (event) {
	if (event.keyCode === 8) {
		var doPrevent = false;
		var d = event.srcElement || event.target;
		if ((d.tagName.toUpperCase() === 'INPUT' && 
			(
				d.type.toUpperCase() === 'TEXT' ||
				d.type.toUpperCase() === 'PASSWORD' || 
				d.type.toUpperCase() === 'FILE' || 
				d.type.toUpperCase() === 'SEARCH' || 
				d.type.toUpperCase() === 'EMAIL' || 
				d.type.toUpperCase() === 'NUMBER' || 
				d.type.toUpperCase() === 'DATE' )
			) || 
			d.tagName.toUpperCase() === 'TEXTAREA') {
			doPrevent = d.readOnly || d.disabled;
		}
		else {
			doPrevent = true;
		}
		if (doPrevent) {
			event.preventDefault();
		}
	} else if (event.keyCode === 116) {
		if ($("div .qp-error").length > 0) {
			window.location = window.location.href;
			event.preventDefault();
		}
	}
});
//end add by KhanhTH