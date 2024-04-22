/*
 *
 * Copyright (c) 

 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *
 */
 
/* private Utilities */
	function JSEL(context) {
		var thiz = this;
		var re = new RegExp("{([^}]+)}", "g");
		// nested parse
		var _parseFormat = function(contexts, exp) {
			if (contexts == null) return null;
			
			var hasNextExp = (exp.indexOf(".") != -1);
			var contextName = exp.substring(0, hasNextExp ? exp.indexOf(".") : exp.length);
			var object = contexts[contextName];
			// no more expression
			if (!hasNextExp)
				return object;
			
			var nextExp = exp.substring(exp.indexOf(".") + 1);
			return _parseFormat(object, nextExp);
		};
		// context
		this.context = (context == null ? {} : context);
		// #parse()
		this.parse = function(expression) {
			if (!re.test(expression))
				return expression;
			
			return expression.replace(re, function(group, match, index) {
				return _parseFormat(thiz.context, match);
			});
		};
		// #setContext()
		this.setContext = function(contextName, value) {
			thiz.context[contextName] = value;
		};
	}

;(function($) {
	
	var AUTOCOMPLETE_PREFIX_ID = "autocompleteex";
	var AUTOCOMPLETE_CLASS_PATTERN = ".*(\\s+)?" + AUTOCOMPLETE_PREFIX_ID + "_([a-zA-Z0-9]+)(\\s+)?.*";
	
	// jQuery#autocompleteEx()
	$.fn.autocompleteEx = function(href, opt) {
		
		/* inner functions */
		var _UUID = function(length) {
			if (length == 0) length = 5;
			var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			var uuid = "";
			
			for (var i = 0; i < length; i++) {
				uuid += possible.charAt(Math.floor(Math.random() * possible.length));
			}
			
			return uuid;
		};
		
		var again = (arguments.length == 0);
		
		var defaults = {
			formatLabel: function(item) {},
			formatValue: function(item) {},
			formatId: function(item) {},//
			onchange: function(event, ui) {},
			onselect: function(event, ui) {},//
			extraParams: {},
			minLength: 0,
			mustMatch: true,
			matchContains: true,
			autoFill: true
		};
		
		var options = $.extend(defaults, opt);
		
		// default label is value
		if (typeof options.formatLabel != "function")
			options.formatLabel = options.formatValue;
			
		var _fireOnChange = function(caller, event, ui) {
			if (typeof options.onchange == "function") {
				//console.log('_fireOnChange');
				options.onchange.call(caller, event, ui);
			}
		};
		
		var _fireOnSelect = function(caller, event, ui) {//
			if (typeof options.onselect == "function") {
				options.onselect.call(caller, event, ui);
			}
		};
		
		var _isValueChanged = function(target) {
			var oldValue = target._oldValue;
			var value = target.value;
			if (typeof oldValue == "undefined") return true;
			return oldValue != value;
		};
		
		/*
		var _keydownListener = function(event) {
			var unchangedKeyCodes = [9, 16, 17, 18, 20, 33, 34, 35, 36, 37, 38, 39, 40, 45, 91, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 144];
			var element = event.target;
			var selectedItem = null;
			var autoMap = $(element).data("autocomplete_map");
			var hasMap = (autoMap != null);
			// ignore this
			for (var i = 0; i < unchangedKeyCodes.length; i++) {
				if (event.keyCode == unchangedKeyCodes[i]){
					alert(unchangedKeyCodes[i]);
					return;
				}
			}
			
			if (options.mustMatch) {
				// if ENTER with no value matched in suggestion list
				if (event.keyCode == 10 || event.keyCode == 13) {
					if (hasMap && element.value != null && element.value != "") {
						//selectedItem = (element.value == null || element.value == "" ? null : autoMap[element.value]);
						selectedItem = autoMap[element.value];
						if (!selectedItem) {
							event.preventDefault();
							return;
						}
					}
				} else if (!hasMap) {
					$(element).data("autocomplete_map", {});
				}
				
				// allow to submit because the value is one of suggestion list items
				//if (typeof currentTimeout != "undefined" && currentTimeout != null)
				//	clearTimeout(currentTimeout);
				//currentTimeout = setTimeout(function() {
				//	
				//	element._submittable = (element.value == "" || (autoMap != null && autoMap[element.value] != null));
				//}, 500);				
			} else {
				// always allow submit, no mustMatch required
				//element._submittable = true;
			}
			
			//if (event.keyCode == 10 || event.keyCode == 13) {
				// do not fire change when enter
			//	return;
			//}
			
			//var thiz = event.target;
			//if (_isValueChanged(thiz)) {
			//	thiz._oldValue = thiz.value;
			//	if (hasMap)
			//		_fireOnChange(thiz, event, { item: selectedItem });
			//}
		};
		*/
		
		var _keypressListener = function(event) {
			/*
			var element = event.target;
			if (options.mustMatch) {
				// if ENTER with no value matched in suggestion list
				if (event.keyCode == 10 || event.keyCode == 13) {
					if (!element._submittable) {
						event.preventDefault();
						return;
					}
				}
				// allow to submit because the value is one of suggestion list items
				if (currentTimeout != null)
					clearTimeout(currentTimeout);
				currentTimeout = setTimeout(function() {
					element._submittable = (element.value == "" || (element._autocomplete_map != null && element._autocomplete_map[element.value] != null));
				}, 500);
			} else {
				// always allow submit, no mustMatch required
				element._submittable = true;
			}
			*/
		};
		
		var _keyupListener = function(event) {
		};
		
		var _source = function(thiz, url, opts, extra, request, response) {
			var actualExtraParams = $.extend((typeof opts.extraParams == "function" ? opts.extraParams.call(thiz, thiz) : opts.extraParams), extra);
			$.post(url, actualExtraParams, function(data) {
				var mappedData = {};
				var distinctMap = {};
				//console.log(data);
				var parsedData = $.map(data.outputGroup, function(item) {
					/*
					if (typeof opts.formatDistinct == "function") {
						var distinctValue = opts.formatDistinct.call(thiz, item);
						if (distinctMap[distinctValue])
							return null; // distinct
						distinctMap[distinctValue] = distinctValue;
					}
					*/
					var label = opts.formatLabel.call(thiz, item);
					var value = opts.formatValue.call(thiz, item);
					var id = opts.formatId.call(thiz, item);
					//console.log($(item));
					var stored;
					if($(item).attr('resourceType') == '1'){
						stored = $.extend({
							id:$(item).attr(id),
							label: fcomMsgSource[$(item).attr(label)],
							value: fcomMsgSource[$(item).attr(label)]
						}, item);
					}else {
						stored = $.extend({
							id:$(item).attr(id),
							label: $(item).attr(label),
							value: $(item).attr(value)
						}, item);
					}
					mappedData[$(item).attr(value)] = stored;					
					return stored;
				});
				$(thiz).data("autocomplete_map", mappedData);
				
				//thiz._autocomplete_map = mappedData;
			
				response(parsedData);
			}, 'json');
		};
		
		// #suggest()
		var _suggest = function(thiz) {
			// prevent from initialize twice
			if (thiz.is("[class*=" + AUTOCOMPLETE_PREFIX_ID + "_]")) {
				return;
			}
			
			var uuid = _UUID(5);
			// add class id for this
			thiz.addClass(AUTOCOMPLETE_PREFIX_ID + "_" + uuid);
			// handle keypress
			//thiz.keydown(_keydownListener);
			//thiz.keypress(_keypressListener);
			//thiz.keyup(_keyupListener);
			thiz._oldValue = thiz.value;
			// make jQuery UI Autocomplete
			if (typeof SUGGEST == "undefined") SUGGEST = {};
			if (SUGGEST.cached == null) SUGGEST.cached = {};
			if (SUGGEST.cached.parameters == null) SUGGEST.cached.parameters = {};
			// cache suggest parameters
			SUGGEST.cached.parameters[uuid] = {
				href: href,
				autocompleteOptions: options,
				jQueryAutocompleteOptions: {
					minLength: options.minLength,
					mustMatch: options.mustMatch,
					matchContains: options.matchContains,
					autoFill: options.autoFill,
					source: function(request, response) {
						_source(thiz, href, options, {
							q: function() { return $(thiz).val(); }
						}, request, response);
					},
					focus: function (event, ui) {
	    	                $(thiz).val(ui.item.label);
	    	                return false;
	    	        },
					select: function(event, ui) {
						
						$(thiz).val(ui.item.label);
						$(thiz).next().val(ui.item.id);
						
						var oldValue = event.target._oldValue;
						if (oldValue == null) oldValue = "";
						if (oldValue != ui.item.value) {
							_fireOnChange(this, event, ui);
						}
						event.target._oldValue = ui.item.value;
						
						//event.target._submittable = true;
						//$(thiz).closest("tr").find("span#FPRO_itName").text(ui.item.label);
						_fireOnSelect(this, event, ui);
						//$(thiz).val(ui.item.label);
						
						
					    return false;
					},
					open: function( event, ui ) {
						//console.log('open');
						if($(thiz).val() == null || $(thiz).val().length == 0){
							$(this).val("");
							$(this).next().val("");
							//console.log('open: _fireOnChange');
							_fireOnChange(this, event, ui);
						}
					},
					change: function(event, ui) {
						
						if (options.mustMatch !='false'){							
						// system handler
						var labelTmp = event.target.value;
						var valueTmp ="";
						try {
							valueTmp = ui.item.value;
						} catch (e) {
							valueTmp =null;
						}
						if ("" != labelTmp) {
							var autoMap = $(this).data("autocomplete_map");
							if (options.mustMatch && (autoMap != null && valueTmp == null)) {
								
								$(this).val("");
								$(this).next().val("");
								// custom handler
								//console.log('change: _fireOnChange');
								_fireOnChange(this, event, ui);
							}	
						}
						}
						/*if (!ui.item || ui.item == null || ui.item.value.length == 0) {
							
			                this.value = '';
			                $(this).next().val("");
			                _fireOnChange(this, event, ui);
			            }*/
						
					}
				}
			};
			
			// suggest
			thiz.autocomplete(SUGGEST.cached.parameters[uuid].jQueryAutocompleteOptions).focus(function(event, ui){
				thiz.autocomplete("search");
			});
		};
		
		// #suggestAgain()
		var _suggestAgain = function(thiz) {
			var classes = thiz.attr("class");
			// this suggestion has not been initialized yet
			var reg = new RegExp(AUTOCOMPLETE_CLASS_PATTERN, "gi");
			if (!classes.match(reg)) return;
			var uuid = classes.replace(reg, "$2");
			if (typeof SUGGEST == "undefined") SUGGEST = {};
			if (SUGGEST.cached == null) SUGGEST.cached = {};
			if (SUGGEST.cached.parameters == null) SUGGEST.cached.parameters = {};
			if (SUGGEST.cached.parameters[uuid] == null)
				return;
			
			// handle keypress
			thiz.keydown(_keydownListener);
			//thiz.keypress(_keypressListener);
			//thiz.keyup(_keyupListener);
			// suggest again
			var cached = SUGGEST.cached.parameters[uuid];
			var cachedOptions = cached.autocompleteOptions;
			var cachedJQueryOptions = cached.jQueryAutocompleteOptions;
			thiz.autocomplete($.extend(cachedJQueryOptions, {
				source: function(request, response) {
					_source(thiz, SUGGEST.cached.parameters[uuid].href, cachedOptions, {
						q: function() { return $(thiz).val(); }
					}, request, response);
				}
			}));
		};
		
		// valid pattern
		this.each(function() {
			// working with just text box
			if ($(this).is("input[type=text]")) {
				var thiz = $(this);
				/*
				// handle onFocus
				thiz.focus(function() {
				});
				*/
				if (again) {
					_suggestAgain(thiz);
				} else {
					_suggest(thiz);
				}
			}
		});
	};
	
})(jQuery);
