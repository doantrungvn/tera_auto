function buildNavTopProblemMenu() {
	var href = CONTEXT_PATH + "/Autocomplete/";
	var params = {
			"sourceType" : "getProblemInformationForNavTop",
			"arg01" : CURRENT_PROJECT_ID
		};

	var data = $.qp.getJson(href, params).outputGroup;
	var str = "";
	var total = 0;
	$("li#navTopProblem").find(".dropdown-alerts").html("");
	
	var url = CONTEXT_PATH + "/problemlist/search?";
	var params;
	var moduleName = "";
	if (data != null && data.length > 0 ) {
		str ="<ul class=\"dropdown-menu dropdown-nav-left dropdown-alerts\">";
		for (var i = 0; i < data.length; i++) {
			element = data[i];
			total += parseInt(element.output01);
			if (element.optionLabel == '') {
				moduleName = fcomMsgSource['sc.sys.0058'];
				params= { moduleId:'-1', moduleIdAutocomplete:'', resourceType:''};
			} else {
				moduleName = element.optionLabel;
				params= { moduleId:element.optionValue, moduleIdAutocomplete:moduleName, resourceType:''};
			}
			str+="<li><a class=\"active\" href=\"" + url + jQuery.param(params) + "\"><span class='pull-left'>"+ moduleName + "</span><span class=\"pull-right text-muted\">(" + element.output01 + ")</span></a></li>";
			str+="<li class=\"divider\"></li>";
		}
		str+="<li ><a href=\"" +CONTEXT_PATH+ "/problemlist/search?init\" class=\"text-center active\"><strong>"+fcomMsgSource['sc.sys.0057']+"</strong></a></li>";
		str+="</ul>";
		$("li#navTopProblem").find(".label-menu-corner").text(total);
		$("li#navTopProblem").find(".label-menu-corner").show();
	} else {
		$("li#navTopProblem").find(".label-menu-corner").text("");
		$("li#navTopProblem").find(".label-menu-corner").hide();
		/*$("li#navTopProblem").hide();*/
	}
	
	$("li#navTopProblem").append(str);
}