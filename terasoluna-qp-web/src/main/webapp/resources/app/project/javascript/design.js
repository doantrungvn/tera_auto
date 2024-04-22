var templates = [
                {
                	template: 'link',
                	data: { 
                		type: 1,
                	}
                },
                {	
                	template: 'text',
                	data: { 
                		type: 0,
                	}
                },
                {
                	template: 'currentUser',
                	data: { 
                		type: 2,
                	}
                },
                {
                	template: 'currentDatetime',
                	data: {
                		type: 3,
                	}	
                },
                {
                	template: 'logo',
                	data: {
                		type: 4,
                	}	
                }                
                ];
var layoutTemplates = [
                       {
                    	   name: 'header-left',
                    	   css: 'pull-left',
                    	   position: 2,
                       },
                       {
                    	   name: 'header-right',
                    	   css: 'pull-right',
                    	   position: 3,
                       },
                       {
                    	   name: 'footer-left',
                    	   css: 'pull-left',
                    	   position: 4,
                       },
                       {
                    	   name: 'footer-right',
                    	   css: 'pull-right',
                    	   position: 5,
                       },
                       {
                    	   name: 'logo-header-left',
                    	   css: 'pull-left',
                    	   position: 0,
                       },
                       {
                    	   name: 'logo-header-right',
                    	   css: 'pull-right',
                    	   position: 1,
                       },
                       ];

function getTemplate(templateName) {
	for (var i = 0; i < templates.length; i++) {
		if (templateName == templates[i].template) {
			return templates[i];
		}
	}
}

function getTemplateByType(type) {
	for (var i = 0; i < templates.length; i++) {
		if (type == templates[i].data.type) {
			return templates[i];
		}
	}
}

function getLayout(layoutName) {
	for (var i = 0; i < layoutTemplates.length; i++) {
		if (layoutName == layoutTemplates[i].name) {
			return layoutTemplates[i];
		}
	}
} 
function getLayoutByPosition(position) {
	for (var i = 0; i < layoutTemplates.length; i++) {
		if (position == layoutTemplates[i].position) {
			return layoutTemplates[i];
		}
	}
} 

function settingHeader(obj) {
	var modal = $('#settingHeader');
	var data = $("#form-element").val();
	var json = convertToJson(data);

	for(var j = 0; j < 6; j++) {
		var tempLayout = getLayoutByPosition(j);
		$(modal).find('[layout-name="'+tempLayout.name+'"]').empty();
	}
	
	for(var i = 0; i < json.length; i++) {
		if (json[i].type == '4') {
			$('#logoHidden').val(convertToString(json[i]));
			if (json[i].position == '0') {
				$('.media-object').css("float", "left");
				$('#align').val("0");
			} else if (json[i].position == '1') {
				$('.media-object').css("float", "right");
				$('#align').val("1");
			}
		} else {
			var tempLayout = getLayoutByPosition(json[i].position);
			var temp = getTemplateByType(json[i].type);
			var content = $("#" + temp.template).tmpl();
			$(content.children()[0]).html(json[i].labelNameAutocomplete);
			$(content).addClass(tempLayout.css);
			
			if ((json[i].type == "2" || json[i].type == "3") && json[i].convertTo == "2") {
				$(content).find("span").replaceWith(function() {
					return '<a class="item" href="javascript:;" ondblclick="componentSetting(this)">' + json[i].labelNameAutocomplete + '</a>';
				});
			}
			
			$(modal).find('[layout-name="'+tempLayout.name+'"]').append(content);
			$(modal).find('[layout-name="'+tempLayout.name+'"]').find('input[name=formElement]:last').val(convertToString(json[i]));
		}
	}
	
	$(modal).modal('show');
}

function acceptElement(ev, ui, obj) {
	var elementTmpl = getTemplate($(ui.draggable).attr('item'));
	var layout = getLayout($(obj).attr('layout-name'));
	
	var templateHtml = $("#" + elementTmpl.template).tmpl();
	
	
	$(templateHtml).addClass(layout.css);
	$(obj).append(templateHtml);
	
	var data = elementTmpl.data;
	data['position'] = layout.position;
	
	if (elementTmpl.data.type == 0) {
		data['labelNameAutocomplete'] = "Text";
		data['labelName'] = "Text";
	} else if (elementTmpl.data.type == 1) {
		data['labelNameAutocomplete'] = "Link";
		data['labelName'] = "Link";
	} else if (elementTmpl.data.type == 2) {
		data['labelNameAutocomplete'] = "{Current user}";
		data['labelName'] = "{Current user}";
	} else if (elementTmpl.data.type == 3) {
		data['labelNameAutocomplete'] = "{Current datetime}";
		data['labelName'] = "{Current datetime}";
	}
	
	var value = convertToString(data);
	
	$(obj).find('input[name=formElement]:last').val(value);
}

function settingLink(obj) {
	var modal = $('#linkSetting');
	var value = $(obj).next().val();
	
	$(modal).find("input[name='labelNameAutocomplete']").val("");
	$(modal).find("input[name='labelName']").val("");
	$(modal).find("input[name='moduleIdAutocomplete']").val("");
	$(modal).find("input[name='moduleId']").val("");
	$(modal).find("input[name='navigateToAutocomplete']").val("");
	$(modal).find("input[name='navigateTo']").val("");
	
	$(modal).data('data', convertToJson(value));
	$(modal).data('obj', obj);
	
	displayStyle(modal, convertToJson(value));
	var data = convertToJson(value);
	if (data.labelNameAutocomplete != undefined) {
		$(modal).find("input[name='labelNameAutocomplete']").val(data.labelNameAutocomplete);
	}
	if (data.labelName != undefined) {
		$(modal).find("input[name='labelName']").val(data.labelName);
	}
	if (data.moduleIdAutocomplete != undefined) {
		$(modal).find("input[name='moduleIdAutocomplete']").val(data.moduleIdAutocomplete);
	}
	if (data.moduleId != undefined) {
		$(modal).find("input[name='moduleId']").val(data.moduleId);
		$(modal).find("input[name='navigateToAutocomplete']").attr("arg01", data.moduleId);
	}
	if (data.navigateToAutocomplete != undefined) {
		$(modal).find("input[name='navigateToAutocomplete']").val(data.navigateToAutocomplete);
	}
	if (data.navigateTo != undefined) {
		$(modal).find("input[name='navigateTo']").val(data.navigateTo);
	}
	
	$(modal).modal('show');
}

function settingText(obj) {
	var modal = $('#textSetting');
	var value = $(obj).next().val();
	
	$(modal).find("input[name='labelNameAutocomplete']").val("");
	$(modal).find("input[name='labelName']").val("");
	
	$(modal).data('data', convertToJson(value));
	$(modal).data('obj', obj);
	
	displayStyle(modal, convertToJson(value));
	var data = convertToJson(value);
	if (data.labelNameAutocomplete != undefined) {
		$(modal).find("input[name='labelNameAutocomplete']").val(data.labelNameAutocomplete);
	}
	if (data.labelName != undefined) {
		$(modal).find("input[name='labelName']").val(data.labelName);
	}
	
	$(modal).modal('show');
}

function imageSetting(obj) {
	var modal = $('#imageSetting');
	var value = $(obj).next().val();
	
	$(modal).find("input[name='labelNameAutocomplete']").val("");
	$(modal).find("input[name='labelName']").val("");
	
	$(modal).data('data', convertToJson(value));
	$(modal).data('obj', obj);
	
	displayStyle(modal, convertToJson(value));
	var data = convertToJson(value);
	if (data.labelNameAutocomplete != undefined) {
		$(modal).find("input[name='labelNameAutocomplete']").val(data.labelNameAutocomplete);
	}
	if (data.labelName != undefined) {
		$(modal).find("input[name='labelName']").val(data.labelName);
	}
	
	$(modal).modal('show');
}

function componentSetting(obj) {
	var modal = $('#componentSetting');
	var value = $(obj).next().val();
	$(modal).find('tr[name=isLink]').hide();
	$(modal).find('table .hover').hide();
	
	$(modal).find("input[name='labelNameAutocomplete']").val("");
	$(modal).find("input[name='labelName']").val("");
	$(modal).find("input[name='moduleIdAutocomplete']").val("");
	$(modal).find("input[name='moduleId']").val("");
	$(modal).find("input[name='navigateToAutocomplete']").val("");
	$(modal).find("input[name='navigateTo']").val("");
	
	$(modal).data('data', convertToJson(value));
	$(modal).data('obj', obj);
	
	displayStyle(modal, convertToJson(value));
	var data = convertToJson(value);
	if (data.labelNameAutocomplete != undefined) {
		$(modal).find("input[name='labelNameAutocomplete']").val(data.labelNameAutocomplete);
	}
	if (data.labelName != undefined) {
		$(modal).find("input[name='labelName']").val(data.labelName);
	}
	if (data.convertTo == "1") {
		if (data.moduleIdAutocomplete != undefined) {
			$(modal).find("input[name='moduleIdAutocomplete']").val("");
		}
		if (data.moduleId != undefined) {
			$(modal).find("input[name='moduleId']").val("");
		}
		if (data.navigateToAutocomplete != undefined) {
			$(modal).find("input[name='navigateToAutocomplete']").val("");
		}
		if (data.navigateTo != undefined) {
			$(modal).find("input[name='navigateTo']").val("");
		}
		$('input:radio[name=convertTo]').filter('[value=1]').prop('checked', true);
		$(modal).find('tr[name=isLink]').hide();
		$(modal).find('table .hover').hide();
	} else if (data.convertTo == "2") {
		if (data.moduleIdAutocomplete != undefined) {
			$(modal).find("input[name='moduleIdAutocomplete']").val(data.moduleIdAutocomplete);
		}
		if (data.moduleId != undefined) {
			$(modal).find("input[name='moduleId']").val(data.moduleId);
			$(modal).find("input[name='navigateToAutocomplete']").attr("arg01", data.moduleId);
		}
		if (data.navigateToAutocomplete != undefined) {
			$(modal).find("input[name='navigateToAutocomplete']").val(data.navigateToAutocomplete);
		}
		if (data.navigateTo != undefined) {
			$(modal).find("input[name='navigateTo']").val(data.navigateTo);
		}
		$('input:radio[name=convertTo]').filter('[value=2]').prop('checked', true);
		$(modal).find('tr[name=isLink]').show();
		$(modal).find('table .hover').show();
	}
	
	$(modal).modal('show');
}


function saveStyle(modal, data, obj) {
	var style = "";
	
	var marginLeft = $(modal).find("[name='margin-left']").val();
	var marginTop = $(modal).find("[name='margin-top']").val();
	var margiRight = $(modal).find("[name='margin-right']").val();
	var marginBottom = $(modal).find("[name='margin-bottom']").val();
	var color = $(modal).find("[name='color']").val();
	var fontSize = $(modal).find("[name='font-size']").val();
	var fontStyle = $(modal).find("[name='font-style']").val();
	var fontWeight = $(modal).find("[name='font-weight']").val();
	var textDecoration = $(modal).find("[name='text-decoration']").val();
	var width = $(modal).find("[name='width']").val();
	var height = $(modal).find("[name='height']").val();
	
	if (width != undefined && width != null && width.length > 0) {
		style += "width:" + width + ";"; 
	}
	
	if (height != undefined && height != null && height.length > 0) {
		style += "height:" + height + ";"; 
	}
	
	if (textDecoration != undefined && textDecoration != null && textDecoration.length > 0) {
		style += "text-decoration:" + textDecoration + ";"; 
	}
	
	if (marginLeft != undefined && marginLeft != null && marginLeft.length > 0) {
		style += "margin-left:" + marginLeft + ";"; 
	}
	
	if (marginTop != undefined && marginTop != null && marginTop.length > 0) {
		style += "margin-top:" + marginTop + ";"; 
	}
	
	if (margiRight != undefined && margiRight != null && margiRight.length > 0) {
		style += "margin-right:" + margiRight + ";"; 
	}
	
	if (marginBottom != undefined && marginBottom != null && marginBottom.length > 0) {
		style += "margin-bottom:" + marginBottom + ";"; 
	}
	
	if (color != undefined && color != null && color.length > 0) {
		style += "color:" + color + ";"; 
	}
	
	if (fontSize != undefined && fontSize != null && fontSize.length > 0) {
		style += "font-size:" + fontSize + ";"; 
	}
	
	if (fontStyle != undefined && fontStyle != null && fontStyle.length > 0) {
		style += "font-style:" + fontStyle + ";"; 
	}
	
	if (fontWeight != undefined && fontWeight != null && fontWeight.length > 0) {
		style += "font-weight:" + fontWeight + ";"; 
	}
	
	var hoverStyle = "";
	
	$(modal).find('[name^="hoverStyle="]').each(function() {
		if ($(this).val() != null && $(this).val().length > 0) {
			var name = $(this).attr('name').replace('hoverStyle=', '');
			hoverStyle += name + ":" +$(this).val() + ";";
		}
		
	});
	if (data.datatype == 13) {
		var icon = $(modal).find('input[name=iconButton]').val();
		
		//save label
		data['icon'] = icon;
		var styleIcon = "";
		if ($(modal).find('input[name=showLabel]').prop('checked')) {
			data['showLabel'] = 1;
			$(obj).html(data['labelText']);
			styleIcon = 'style="margin-right: 2px;"';
		} else {
			data['showLabel'] = 0;
			$(obj).html("");
		}
		$(obj).prepend('<i class="'+icon+'" '+styleIcon+'></i>');
	}
	data['hoverStyle'] = hoverStyle;
	
	data['style'] = style;
	
	return data;
}

function displayStyle(modal, data) {
	$(modal).find("[name='margin-left']").val("");
	$(modal).find("[name='margin-top']").val("");
	$(modal).find("[name='margin-right']").val("");
	$(modal).find("[name='margin-bottom']").val("");
	$(modal).find("[name='color']").val("");
	$(modal).find("[name='font-size']").val("");
	$(modal).find("[name='font-style']").val("");
	$(modal).find("[name='font-weight']").val("");
	$(modal).find("[name='font-weightAutocomplete']").val("");
	$(modal).find("[name='text-decoration']").val("");
	$(modal).find("[name='width']").val("");
	$(modal).find("[name='height']").val("");
	$(modal).find('[name=iconButton]').val('');
	$(modal).find('span[name=iconPreview]').attr('class', '');
	$(modal).find('input[name=showLabel]').prop('checked', false);
	$(modal).find('[name^="hoverStyle="]').val('');
	
	if (data['style'] != undefined && data['style'] != null && data['style'].length > 0) {
		var styles = data['style'].split(';'); 
		
		for (var i = 0; i < styles.length; i++) {
			if (styles[i] == null || styles[i].length == 0) continue;
			
			var style = styles[i].split(':');
			
			$(modal).find("input[name='"+style[0]+"']").val(style[1]);
			if ($(modal).find("input[name='"+style[0]+"Autocomplete']") != undefined && $(modal).find("input[name='"+style[0]+"Autocomplete']") != null
					&& $(modal).find("input[name='"+style[0]+"Autocomplete']").length > 0) {
				$(modal).find("input[name='"+style[0]+"Autocomplete']").val(style[1]);
			}
			
		}
	}

	if (data.hoverStyle != undefined && data.hoverStyle != null && data.hoverStyle.length > 0) {
		var hoverStyle = data.hoverStyle;
		var hoverStyleArr = hoverStyle.split(';');
		
		for (var i = 0; i < hoverStyleArr.length; i++) {
			var style = hoverStyleArr[i].split(':');
			if (style != null && style.length ==0) continue;
			var name = 'hoverStyle=' + style[0];
			var nameAutocomplete = name + 'Autocomplete';
			
			$(modal).find('[name="'+name+'"]').val(style[1]);
			$(modal).find('[name="'+nameAutocomplete+'"]').val(style[1]);
		}
	}
	
	if (data.datatype == 13) {
		$(modal).find('input[name=iconButton]').val(data['icon']);
		$(modal).find('span[name=iconPreview]').attr('class', data['icon']);
		if (data['icon'] != undefined && data['icon'].length > 0) {
			$(modal).find('span[name=iconPreview]').html('');
		}
		
		if (data['showLabel'] != undefined && (data['showLabel'] == '1' || data['showLabel'] == 1)) {
			$(modal).find('input[name=showLabel]').prop('checked', true);
		} else {
			$(modal).find('input[name=showLabel]').prop('checked', false);
		}
	} 
}

function convertToJson(string) {
	var json = {};
	try {
		json = JSON.parse(string);
    } catch (e) {
    	json = JSON.parse("{" + string + "}");
    }
    return json;
}

function convertToString(json) {
	return  JSON.stringify(json);
}

function saveDialogTextSetting(obj) {
	var modal = "#textSetting";
	
	if ($(modal).find("input[name='labelNameAutocomplete']").val().length == 0) {
		alert(dbMsgSource['sc.project.0028']);
		return false;
	}
	
	var data = $(modal).data('data');
	var target = $(modal).data('obj');
	
	data = saveStyle(modal, data, target);
	
	var labelNameAutocomplete = $(modal).find("input[name='labelNameAutocomplete']").val();
	var labelName = $(modal).find("input[name='labelName']").val();
	
	if (labelNameAutocomplete == null || labelNameAutocomplete == "" || labelNameAutocomplete == undefined) {
		data['labelNameAutocomplete'] = "Text";
		$(target).text("Text");
	} else {
		data['labelNameAutocomplete'] = labelNameAutocomplete;
		$(target).text(labelNameAutocomplete);
	}
	
	if (labelName == null || labelName == "" || labelName == undefined) {
		data['labelName'] = "Text";
	} else {
		data['labelName'] = labelName;
	}
	
	data['convertTo'] = "0";
	
	$(target).next().val(convertToString(data));
	
	$(modal).modal('hide');
}

function saveDialogLinkAreaSetting(obj) {
	var modal = "#linkSetting";
	
	if ($(modal).find("input[name='labelNameAutocomplete']").val().length == 0) {
		alert(dbMsgSource['sc.project.0029']);
		return false;
	}
	
	if ($(modal).find("input[name='moduleIdAutocomplete']").val().length == 0) {
		alert(dbMsgSource['sc.project.0030']);
		return false;
	}
	
	if ($(modal).find("input[name='navigateToAutocomplete']").val().length == 0) {
		alert(dbMsgSource['sc.project.0031']);
		return false;
	}
	
	var data = $(modal).data('data');
	var target = $(modal).data('obj');
	
	data = saveStyle(modal, data, target);
	
	var labelNameAutocomplete = $(modal).find("input[name='labelNameAutocomplete']").val();
	var labelName = $(modal).find("input[name='labelName']").val();
	var moduleIdAutocomplete = $(modal).find("input[name='moduleIdAutocomplete']").val();
	var moduleId = $(modal).find("input[name='moduleId']").val();
	var navigateToAutocomplete = $(modal).find("input[name='navigateToAutocomplete']").val();
	var navigateTo = $(modal).find("input[name='navigateTo']").val();
	
	if (labelNameAutocomplete == null || labelNameAutocomplete == "" || labelNameAutocomplete == undefined) {
		data['labelNameAutocomplete'] = "Link";
		$(target).text("Link");
	} else {
		data['labelNameAutocomplete'] = labelNameAutocomplete;
		$(target).text(labelNameAutocomplete);
	}
	
	if (labelName == null || labelName == "" || labelName == undefined) {
		data['labelName'] = "Link";
	} else {
		data['labelName'] = labelName;
	}
	
	data['moduleIdAutocomplete'] = moduleIdAutocomplete;
	data['moduleId'] = moduleId;
	data['navigateToAutocomplete'] = navigateToAutocomplete;
	data['navigateTo'] = navigateTo;
	data['convertTo'] = "0";
	
	$(target).next().val(convertToString(data));
	
	$(modal).modal('hide');
}

function saveDialogComponentSetting(obj) {
	var modal = "#componentSetting";
	
	var data = $(modal).data('data');
	var target = $(modal).data('obj');
	
	data = saveStyle(modal, data, target);
	
	/*var labelNameAutocomplete = $(modal).find("input[name='labelNameAutocomplete']").val();
	var labelName = $(modal).find("input[name='labelName']").val();*/
	var convertTo = $(modal).find("input[name='convertTo']:checked").val();
	if (convertTo == 2) {
		var moduleIdAutocomplete = $(modal).find("input[name='moduleIdAutocomplete']").val();
		var moduleId = $(modal).find("input[name='moduleId']").val();
		var navigateToAutocomplete = $(modal).find("input[name='navigateToAutocomplete']").val();
		var navigateTo = $(modal).find("input[name='navigateTo']").val();
	}
	
	/*if (labelNameAutocomplete == null || labelNameAutocomplete == "" || labelNameAutocomplete == undefined) {
		data['labelNameAutocomplete'] = "Label";
		$(target).text("Label");
	} else {
		data['labelNameAutocomplete'] = labelNameAutocomplete;
		$(target).text(labelNameAutocomplete);
	}
	
	if (labelName == null || labelName == "" || labelName == undefined) {
		data['labelName'] = "Label";
	} else {
		data['labelName'] = labelName;
	}*/
	
	data['moduleIdAutocomplete'] = "";
	data['moduleId'] = "";
	data['navigateToAutocomplete'] = "";
	data['navigateTo'] = "";
	data['convertTo'] = convertTo;
	if (convertTo == 2) {
		data['moduleIdAutocomplete'] = moduleIdAutocomplete;
		data['moduleId'] = moduleId;
		data['navigateToAutocomplete'] = navigateToAutocomplete;
		data['navigateTo'] = navigateTo;
	}
	
	$(target).next().val(convertToString(data));
	
	if (convertTo == 1) {
		$(target).replaceWith(function() {
			/*var text = "";
			
			if (labelNameAutocomplete == null || labelNameAutocomplete == "" || labelNameAutocomplete == undefined) {
				text = "Label";
			} else {
				text = labelNameAutocomplete;
			}*/
			
			return '<span class="item" ondblclick="componentSetting(this)">' + $(target).text() + '</span>';
		});
	} else if (convertTo == 2) {
		$(target).replaceWith(function() {
			/*var text = "";
			
			if (labelNameAutocomplete == null || labelNameAutocomplete == "" || labelNameAutocomplete == undefined) {
				text = "Label";
			} else {
				text = labelNameAutocomplete;
			}*/
			
			return '<a class="item" href="javascript:;" ondblclick="componentSetting(this)">' + $(target).text() + '</a>';
		});
	}
	
	$(modal).modal('hide');
}

function hideHeaderFooterSetting() {
	var formElement = "[";
	$("#settingHeader").find("input[name='formElement']").each(function(i) {
		var currentFormElement = $(this).val();
		
		if (currentFormElement != "") {
			formElement += currentFormElement;
			if (i < $("#settingHeader").find("input[name='formElement']").length - 1) {
				formElement += ',';
			}
		}
	});
	formElement += "]";
	$("#form-element").val(formElement);
	$('#settingHeader').modal('hide');
}

function saveDialogLogoSetting(obj) {
	var modal = "#imageSetting";
	
	var data = $(modal).data('data');
	var target = $(modal).data('obj');
	
	data = saveStyle(modal, data, target);
	
	var labelNameAutocomplete = $(modal).find("input[name='labelNameAutocomplete']").val();
	var labelName = $(modal).find("input[name='labelName']").val();
	
	data['labelNameAutocomplete'] = labelNameAutocomplete;
	data['labelName'] = labelName;
	data['type'] = "4";
	if ($('#align').val() == 0) {
		data['position'] = "0";
		$('.media-object').css("float", "left");
	} else if ($('#align').val() == 1) {
		data['position'] = "1";
		$('.media-object').css("float", "right");
	}
	
	$(target).next().val(convertToString(data));
	
	$(modal).modal('hide');
}

function selectModuleNameLinkSetting() {
	var modal = "#linkSetting";
	var moduleId = $(modal).find("input[name='moduleId']").val();
	$(modal).find("input[name='navigateToAutocomplete']").attr("arg01", moduleId);
}

function selectModuleNameComponentSetting() {
	var modal = "#componentSetting";
	var moduleId = $(modal).find("input[name='moduleId']").val();
	$(modal).find("input[name='navigateToAutocomplete']").attr("arg01", moduleId);
}

function deleteDialogTextSetting(obj) {
	var modal = "#textSetting";
	var target = $(modal).data('obj');
	var hiddenTarget = $(target).next();
	target.remove();
	hiddenTarget.remove();
	$(modal).modal('hide');
}

function deleteDialogLinkAreaSetting(obj) {
	var modal = "#linkSetting";
	var target = $(modal).data('obj');
	var hiddenTarget = $(target).next();
	target.remove();
	hiddenTarget.remove();
	$(modal).modal('hide');
}

function deleteDialogComponentSetting(obj) {
	var modal = "#componentSetting";
	var target = $(modal).data('obj');
	var hiddenTarget = $(target).next();
	target.remove();
	hiddenTarget.remove();
	$(modal).modal('hide');
}

function deleteDialogLogoSetting(obj) {
	var modal = "#imageSetting";
	var target = $(modal).data('obj');
	var hiddenTarget = $(target).next();
	target.remove();
	hiddenTarget.remove();
	$(modal).modal('hide');
}

function previewDialog(obj) {
	
	$('#styleDesignForm').find('.input-group-addon').each(function() {
		if ($(this).prev().val() != undefined && $(this).prev().val().length > 0)
			$(this).prev().val($(this).prev().val() + 'px');
	});
    var postData = $('#styleDesignForm').serializeArray();
    
    $('#styleDesignForm').find('.input-group-addon').each(function() {
    	if ($(this).prev().val() != undefined && $(this).prev().val().length > 0)
    		$(this).prev().val($(this).prev().val().replace("px", ""));
	});
    
    var formURL = CONTEXT_PATH + "/styledesign/previewTemp";
    $.ajax(
    {
        url : formURL,
        type: "POST",
        data : postData,
        success:function(data, textStatus, jqXHR) 
        {
        	var href = CONTEXT_PATH + "/styledesign/preview";
			$.fancybox.open([
			                 {
			                     'type': 'iframe',
			                     'href' : href,
			             		'width' : 1100,
			            		'height' : '100%',
			            		'autoSize' : false,
			            		'autoScale' : true,
			            		'autoDimensions' : true,
			            		'hideOnOverlayClick' : false,
			            		'transitionIn' : 'none',
			            		'transitionOut' : 'none',
			            		'type' : 'iframe',
			            		'centerOnScroll' : true,
			            		'enableEscapeButton' : false,
			            		'onStart' : function() {
			            			$("body").css("overflow", "hidden");
			            		},
			            		'onClosed' : function() {
			            			$("body").css("overflow", "auto");
			            		},
			                 }
			             ], {
			                 padding : 0
			});
			
        },
        error: function(jqXHR, textStatus, errorThrown) 
        {
            //if fails      
        }
    });
    
}