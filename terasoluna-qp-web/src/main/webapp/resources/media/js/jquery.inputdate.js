/*
 *
 * Copyright (c) 2010 ThinhTV (http://nttdata.com.vn)

 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *
 * Version 1.0
 *
 * $LastChangedDate: 2010-07-06 00:00:00 +0000 (Tue, 06 Jul 2010) $
 * $Rev: 6185 $
 *
 */
 
;(function($) {
	
	var fillZero = function(number, length) {
		for (var i = (number + "").length; i < length; i++)
			number = "0" + number;
		return number;
	};
	
	var isDate = function(year, month, day) {
		var yyyy = ntt.Convert.toInteger(year);
		var M = ntt.Convert.toInteger(month) - 1;
		var d = ntt.Convert.toInteger(day);
		
		//alert(yyyy + "/" + M + "/" + d);
		
		var date = new Date(yyyy, M, d);
		if (yyyy != date.getFullYear())
			return false;
		if (M != date.getMonth())
			return false;
		if (d != date.getDate())
			return false;
		return true;
	};
	
	var isHour = function(hour, minute) {
		var HH = parseInt(hour);
		var mm = parseInt(minute);
		return (HH >= 0 && HH <= 23 && mm >= 0 && mm <= 59);
	};
	
	$.fn.inputDate = function(opt) {
		var options = $.extend({
			mustValid: true,
			pattern: "dd/mm/yyyy",
			watermark: true
		}, opt);
		// today
		var now = new Date();
		var nowMap = {
			d: now.getDate(),
			dd: now.getDate(),
			MM: now.getMonth() + 1,
			M: now.getMonth() + 1,
			yyyy: now.getFullYear()
		};
		
		var complete = function(value) {
			// process
			var valueMap = {
				d: now.getDate(),
				dd: now.getDate(),
				MM: now.getMonth() + 1,
				M: now.getMonth() + 1,
				yyyy: now.getFullYear()
			};
			
			// use current date if not provided
			if ("yyyy/mm/dd" == options.pattern) {
				if (value.match(/([0-9]{4})[\/-]([0-9]{1,2})[\/-]([0-9]{1,2})/)) {
					var matches = /([0-9]{4})[\/-]([0-9]{1,2})[\/-]([0-9]{1,2})/.exec(value);
					valueMap.yyyy = matches[1];
					valueMap.MM = matches[2];
					valueMap.dd = matches[3];
				} else if (value.match(/[0-9]{8}/)) {
					valueMap.yyyy = value.substring(0, 4);
					valueMap.MM = value.substring(4, 6);
					valueMap.dd = value.substring(6, 8);
				} else if (value.match(/([0-9]{1,2})[\/-]([0-9]{1,2})/)) {
					var matches = /([0-9]{1,2})[\/-]([0-9]{1,2})/.exec(value);
					valueMap.MM= matches[1];
					valueMap.dd= matches[2];
				} else if (value.length > 2 && value.match(/([0-9]{1,2})([0-9]{1,2})/)) {
					if (value.length == 3) {
						if (parseInt(value.substring(0, 2)) <= 12) {
							valueMap.MM = value.substring(0, 2);
							valueMap.dd = value.substring(2);
						} else {
							valueMap.MM = value.substring(0, 1);
							valueMap.dd = value.substring(1);
						}
					} else {
						valueMap.MM = value.substring(0, 2);
						valueMap.dd = value.substring(2);
					}
				} else if (value.match(/[0-9]{1,2}/)) {
					valueMap.dd = value;
				} else {
					return "";
				}
				if (!options.mustValid || isDate(valueMap.yyyy, valueMap.MM, valueMap.dd)) {
					return fillZero(valueMap.yyyy, 4) + "/" + fillZero(valueMap.MM, 2) + "/" + fillZero(valueMap.dd, 2);
				}
			} else if ("dd/mm/yyyy" == options.pattern) {
				if (value.match(/([0-9]{1,2})[\/-]([0-9]{1,2})[\/-]([0-9]{4})/)) {
					var matches = /([0-9]{1,2})[\/-]([0-9]{1,2})[\/-]([0-9]{4})/.exec(value);
					valueMap.dd = matches[1];
					valueMap.MM = matches[2];
					valueMap.yyyy = matches[3];
				} else if (value.match(/[0-9]{8}/)) {
					valueMap.dd = value.substring(0, 2);
					valueMap.MM = value.substring(2, 4);
					valueMap.yyyy = value.substring(4, 8);
				} else if (value.match(/([0-9]{1,2})[\/-]([0-9]{1,2})/)) {
					var matches = /([0-9]{1,2})[\/-]([0-9]{1,2})/.exec(value);
					valueMap.dd= matches[1];
					valueMap.MM= matches[2];
				} else if (value.length > 2 && value.match(/([0-9]{1,2})([0-9]{1,2})/)) {
					if (value.length == 3) {
						if (parseInt(value.substring(0, 2)) > 0 && parseInt(value.substring(0, 2)) <= 31) {
							valueMap.dd = value.substring(0, 2);
							valueMap.MM = value.substring(2);
						} else {
							valueMap.MM = value.substring(0, 1);
							valueMap.dd = value.substring(1);
						}
					} else {
						valueMap.dd = value.substring(0, 2);
						valueMap.MM = value.substring(2);
					}
				} else if (value.match(/[0-9]{1,2}/)) {
					valueMap.dd = value;
				} else {
					return "";
				}
				if (!options.mustValid || isDate(valueMap.yyyy, valueMap.MM, valueMap.dd)) {
					return  fillZero(valueMap.dd, 2) + "/" + fillZero(valueMap.MM, 2) + "/" + fillZero(valueMap.yyyy, 4);
				}
			}
			
			return ""; 
		};
		/*
		var watermark = function(thiz, value) {
			var input = $(thiz);
			if (options.watermark && "" == value) {
				input.css("color", "#CCCCCC");
				input.val(options.pattern);
				return;
			}
			input.css("color", "");
			input.val(value);
		};
		
		var clearWatermark = function(thiz) {
			if ($(thiz).val() == options.pattern) {
				$(thiz).val("");
			}
			$(thiz).css("color", "");
		};
		*/
		// valid pattern
		this.each(function() {
			// working with just text box
			if ($(this).is("input[type='text']")) {
				var thiz = $(this);
				/*
				// handle onFocus
				thiz.focus(function() {
					clearWatermark(this);
				});
				*/
				// handle onBlur
				thiz.blur(function() {
					var value = $(this).val();
					//watermark(this, complete(value));
					$(this).val(complete($(this).val()));
				});
				/*
				// handle form submit
				thiz.parents("form").submit(function() {
					clearWatermark(thiz);
				});
				// first one
				watermark(this, complete($(this).val()));
				*/
				$(this).val(complete($(this).val()));
				// watermark support
				if (options.watermark)
					$(this).watermark(options.pattern);
			}
		});
	};
	
	$.fn.inputMonth = function(opt) {
		var options = $.extend({
			mustValid: true,
			pattern: "mm/dd",
			watermark: true
		}, opt);
		// today
		var now = new Date();
		var nowMap = {
			MM: now.getMonth() + 1,
			M: now.getMonth() + 1,
			yyyy: now.getFullYear()
		};
		
		var complete = function(value) {
			// process
			var valueMap = {
				MM: now.getMonth() + 1,
				M: now.getMonth() + 1,
				yyyy: now.getFullYear()
			};
			if (value.match(/([0-9]{4})[\/-]([0-9]{1,2})/)) {
				var matches = /([0-9]{4})[\/-]([0-9]{1,2})/.exec(value);
				valueMap.yyyy = matches[1];
				valueMap.MM = matches[2];
			} else if (value.match(/[0-9]{6}/)) {
				valueMap.yyyy = value.substring(0, 4);
				valueMap.MM = value.substring(4, 6);
			} else if (value.match(/[0-9]{4}/)) {
				valueMap.yyyy = value;
			} else if (value.match(/[0-9]{1,2}/)) {
				valueMap.MM = value;
			} else {
				return "";
			}
			
			if (!options.mustValid || isDate(valueMap.yyyy, valueMap.MM, 1)) {
				return fillZero(valueMap.yyyy, 4) + "/" + fillZero(valueMap.MM, 2);
			}
			
			return ""; 
		};
		
		/*
		var watermark = function(thiz, value) {
			var input = $(thiz);
			if (options.watermark && "" == value) {
				input.css("color", "#CCCCCC");
				input.val(options.pattern);
				return;
			}
			input.css("color", "");
			input.val(value);
		};
		
		var clearWatermark = function(thiz) {
			if ($(thiz).val() == options.pattern) {
				$(thiz).val("");
			}
			$(thiz).css("color", "");
		};
		*/
		// valid pattern
		this.each(function() {
			// working with just text box
			if ($(this).is("input[type='text']")) {
				var thiz = $(this);
				/*
				// handle onFocus
				thiz.focus(function() {
					clearWatermark(this);
				});
				*/
				// handle onBlur
				thiz.blur(function() {
					var value = $(this).val();
					//watermark(this, complete(value));
					$(this).val(complete($(this).val()));
				});
				/*
				// handle form submit
				thiz.parents("form").submit(function() {
					clearWatermark(thiz);
				});
				// first one
				watermark(this, complete($(this).val()));
				*/
				$(this).val(complete($(this).val()));
				// watermark support
				if (options.watermark)
					$(this).watermark(options.pattern);
			}
		});
	};
	
	$.fn.inputTime = function(opt) {
		var options = $.extend({
			mustValid: true,
			pattern: "hh:mm",
			watermark: true
		}, opt);
		// today
		var now = new Date();
		var nowMap = {
			HH: now.getHours(),
			mm: now.getMinutes()
		};
		
		var complete = function(value) {
			// process
			var valueMap = {
				HH: now.getHours(),
				mm: now.getMinutes()
			};
			if (value.match(/([0-9]{1,2}):([0-9]{1,2})/)) {
				var matches = /([0-9]{1,2}):([0-9]{1,2})/.exec(value);
				valueMap.HH = matches[1];
				valueMap.mm = matches[2];
			} else if (value.match(/[0-9]{4}/)) {
				valueMap.HH = value.substring(0, 2);
				valueMap.mm = value.substring(2, 4);
			} else if (value.match(/[0-9]{1,2}/)) {
				valueMap.HH = value;
			} else {
				return "";
			}
			if (!options.mustValid || isHour(valueMap.HH, valueMap.mm)) {
				return fillZero(valueMap.HH, 2) + ":" + fillZero(valueMap.mm, 2);
			}
			
			return ""; 
		};
		
		/*
		var watermark = function(thiz, value) {
			var input = $(thiz);
			if (options.watermark && "" == value) {
				input.css("color", "#CCCCCC");
				input.val(options.pattern);
				return;
			}
			input.css("color", "");
			input.val(value);
		};
		
		var clearWatermark = function(thiz) {
			if ($(thiz).val() == options.pattern) {
				$(thiz).val("");
			}
			$(thiz).css("color", "");
		};
		*/
		// valid pattern
		this.each(function() {
			// working with just text box
			if ($(this).is("input[type='text']")) {
				var thiz = $(this);
				/*
				// handle onFocus
				thiz.focus(function() {
					clearWatermark(this);
				});
				*/
				// handle onBlur
				thiz.blur(function() {
					var value = $(this).val();
					//watermark(this, complete(value));
					$(this).val(complete($(this).val()));
				});
				/*
				// handle form submit
				thiz.parents("form").submit(function() {
					clearWatermark(thiz);
				});
				// first one
				watermark(this, complete($(this).val()));
				*/
				$(this).val(complete($(this).val()));
				// watermark support
				if (options.watermark)
					$(this).watermark(options.pattern);
			}
		});
	};
	
})(jQuery);
