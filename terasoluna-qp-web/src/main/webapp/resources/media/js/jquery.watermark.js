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
	
	$.fn.watermark = function(opt) {
		var defaults = {
			text: "",
			color: "#CCCCCC",
			clearOnSubmit: true
		};
		if (typeof opt == "string") {
			opt = {	"text": opt };
		} else if (typeof opt == "boolean") {
			// remove
		}
		var options = $.extend(defaults, opt);
		
		var _watermark = function(thiz) {
			var input = $(thiz);
			if ("" == input.val()) {
				input.css("color", defaults.color);
				input.val(options.text);
				return;
			}
			input.css("color", "");
		};
		
		var _clearWatermark = function(thiz) {
			if ($(thiz).val() == options.text) {
				$(thiz).val("");
			}
			$(thiz).css("color", "");
		};
		
		return this.each(function() {
			// working with just text box
			if ($(this).is("input[type='text']")) {
				var thiz = $(this);
				// handle onFocus
				thiz.focus(function() {
					_clearWatermark(this);
				});
				// handle onBlur
				thiz.blur(function() {
					_watermark(this);
				});
				// handle form submit
				if (options.clearOnSubmit) {
					thiz.parents("form").submit(function() {
						_clearWatermark(thiz);
					});
				}
				// first one
				_watermark(this);
			}
		});
	};
	
})(jQuery);
