var OPEN_PARENTHESIS = "(";
var CLOSE_PARENTHESIS = ")";
var countClick = 0;

$(document).ready(function() {
	$("#dialogAdvanceSetting").find("input[name$='Autocomplete']").each(function() {
		$(this).data("ui-autocomplete")._trigger("close");
	});
	$("#srcgenElements").find("div.bdesign-node").draggable({
		containment : '#allDragDropContent',
		stack : '#allDragDropContent',
		helper: 'clone',
		revert: 'invalid',
		stop : function(event, ui) {
			$(this).css("z-index", "auto");
		},
		zIndex: 99999999
	}).disableSelection();

	$("#transition-area").droppable({
		accept : "#srcgenElements div.bdesign-node",
		activeClass : "state-droppable",
		drop : function(event, ui) {
			// Process create component after drop
			if($("input[name=moduleId]").val() != "" && $("input[name=clicked]").val() == "true"){
				var offset = $(this).offset();
				var draggableOffsetLeft = ui.offset.left;
				var draggableOffsetTop= ui.offset.top + 8;
				var type =  $(ui.draggable).attr('type');
				var componentType =  $(ui.draggable).attr('elementtype');
				var newScreen = createNewScreen(event, $(ui.draggable),type,componentType, draggableOffsetLeft, draggableOffsetTop, offset,"transition-area", instance);
				
				if($(newScreen).attr("componenttype") != "-1"){
					if($(newScreen).attr("componenttype") != "3"){
						openScreenDesign($(newScreen).attr("id"), $(newScreen).attr("componenttype"));
					}else if($(newScreen).attr("componenttype") == "3"){
						openBranchInformation($(newScreen).attr("id"), $(newScreen).attr("componenttype"));
					}
				}
			}else{
				alert(dbMsgSource['sc.screendesign.0504']);
			}
		}
	});
});
var instance;
var arrConnectionComponent = [];
jsPlumb.ready(function() {
    // setup some defaults for jsPlumb.
	loadjsPlumb();
});

function createNewScreen(event, div,listType,componentType, draggableOffsetLeft, draggableOffsetTop, offset, containerId, instance) {
	var container = $("#"+containerId);
	var prefixId = $(container).attr("prefixId");
	if(prefixId == undefined || prefixId == ""){
		prefixId = "jsPlumb_";
	}
	
	var screenName = "";
	if(componentType == "1"){
		screenName = "New Screen Normal";
	}else if(componentType == "2"){
		screenName = "New ScreenPopup";
	}else if(componentType == "-1"){
		screenName = "New Screen Common";
	}else if(componentType == "3"){
		screenName = "Branch Navigator";
	}
	
	if(container != null && container.length >0){
		//var chkbxValue = $("input:radio[name ='controlpopup']:checked").val();
		var xPos = draggableOffsetLeft - offset.left;
		var yPos = draggableOffsetTop - offset.top;
		console.log("\n" + yPos);
		var connectionTarget = 0;
		
		var newElement = $("<div>").addClass("execution-class bdesign-node transition-class _jsPlumb_endpoint_anchor _jsPlumb_connected");
		var connect = $("<div>").addClass("ep");
		var elementSetting = "<input type='hidden' value=''/>";
		$(newElement).attr("data-toggle", "tooltip");
		$(newElement).attr("data-placement", "right");
		$(newElement).attr("componenttype", componentType);
		
		if(componentType == "1"){
			$(newElement).attr("style", "border-color: #FFA500; margin-bottom: 4px position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
			$(newElement).append("<i class=\"glyphicon glyphicon-unchecked\" ></i>");
		}else if(componentType == "2"){
			$(newElement).attr("style", "border-color: #2F73FC; margin-bottom: 4px position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
			$(newElement).append("<i class=\"glyphicon glyphicon-modal-window\" ></i>");
		}else if(componentType == "3"){
			$(newElement).attr("style", "border-color: #4B0707; margin-bottom: 4px position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
			$(newElement).attr("status", 2);
			$(newElement).addClass("node-branch");
			$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/if.png\" class=\"qp-bdesign-node-image\"/>&nbsp;");
		}else if(componentType == "-1"){
			$(newElement).attr("style", "border-color: #000000; margin-bottom: 4px position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
		}
			
		$(newElement).append("<span class='component-name'>"+screenName+"</span>").append(connect);
		
		$(newElement).attr("connectionTarget",connectionTarget);
		elementSetting = returnInformationComponent(newElement);
		$(newElement).append(elementSetting);
		$(newElement).append("<input type='hidden' name='screenDesignCode' value=''/>");

		// Add attribute release choice item for multi drag
		$(newElement).attr("add", "off");
//		$(newElement).click(function() {
//			compDragClick($(newElement),instance);
//		});
		
		//init tooltip
		$(newElement).tooltip();
		$(container).append(newElement);

		instance.draggable(newElement, {
			containment : containerId,
	        stop:function(e){
	            var position = getMaxPositionOfItemsBD();
	            $(container).height(position.top);
	            $(container).width(position.left);
	        },
		});
		var oldId = $(newElement).attr("id");
		var newId = prefixId + oldId;
		$(newElement).attr("id",newId);
		if(componentType == "-1"){
			$(newElement).attr("ondblclick", "");
		}else if(componentType == "3"){
			$(newElement).attr("ondblclick", "openBranchInformation("+newId+")");
		}else{
			$(newElement).attr("ondblclick", "openScreenDesign("+newId+")");
		}
		instance.setIdChanged(oldId,newId);
		
		if(componentType != "-1"){
			instance.makeSource($(newElement), {
				filter : ".ep",
				anchor : "Continuous",
				connector : [ "StateMachine", {
					curviness : 20
				} ],
				connectorStyle : {
					strokeStyle : "#5c96bc",
					lineWidth : 2,
					outlineColor : "transparent",
					outlineWidth : 4
				},
				deleteEndpointsOnDetach:true
			});
		}
			
		instance.makeTarget($(newElement), {
			dropOptions : {
				hoverClass : "dragHover"
			},
			anchor : "Continuous",
			allowLoopback : false
		});
		
		$("#transition-area").append($(newElement));
		
//		var screenPosition = "";
//		$("#transition-area").find("div.transition-class").each(function (i){
//	        var screenId = $(this).attr("id");
//	        //screenId = screenId.substring(6, screenId.length);
//	        var position = $(this).position();
//	        var left = Math.round(position.left);
//	        var top = Math.round(position.top);
//	        // Append string
//	        screenPosition += "{\"screenId\":\""+screenId+"\",\"xCoordinate\":\""+left+"\",\"yCoordinate\":\""+top+"\"};";
//	    });
//	    
//	    screenPosition = screenPosition.substring(0, screenPosition.length - 1);
//	    
//	    alert(screenPosition);
		
		return $(newElement);
	}
}

function getMaxPositionOfItemsBD(){
	var defaultHeight = 715;
	var defaultWidth = $("#transition-area").closest("td").outerWidth() -12;
	var sizeExpand = 250;
	var tempMaxTop = 0;
	var tempMaxLeft = 0;
	var position = new Object();
	$("#transition-area").find("div.execution-class").each(function (){
		var height = $(this).outerHeight();
		var top = parseInt($(this).css("top"), 10);
		if(tempMaxTop <= top + height){
			tempMaxTop = top + height + sizeExpand;
		}
		var width = $(this).outerWidth();
		var left = parseInt($(this).css("left"), 10);
		if(tempMaxLeft <= left + width){
			tempMaxLeft = left + width + sizeExpand;
		}
	});
	position.top =  (tempMaxTop >defaultHeight) ? tempMaxTop  : defaultHeight;
	position.left =  (tempMaxLeft >defaultWidth) ? tempMaxLeft : defaultWidth;
	return position;
}

function returnInformationComponent(selectedComponent) {
	var type = $(selectedComponent).attr("componenttype");
	var element = "<input type=\"hidden\" name=\"screenDesignElement\" " + "value='";
	var value= "";
	
//	var executionNode = new Object();
//	executionNode.label = "";
//	executionNode.remark = "";
//	executionNode.sqlDesignId = null;
//	executionNode.sqlDesignIdAutocomplete = null;
//	executionNode.sqlDesignCode = null;
//	executionNode.concurrencyFlg = false;
//	executionNode.parameterInputBeans = [];
//	executionNode.parameterOutputBeans = [];
//	if(typeof moduleIdOfBD != "undefined" && moduleIdOfBD != null){
//		executionNode.moduleId = moduleIdOfBD;
//		executionNode.moduleIdAutocomplete = moduleIdAutocompleteOfBD;
//	}else{
//		executionNode.moduleId = null;
//		executionNode.moduleIdAutocomplete = null;
//	}
//	value = JSON.stringify(executionNode);
	
	element += value+"'>";
	return element;
}

//event when click new screen
function compDragClick(obj,instance) {
	var chkbxValue = $("input:radio[name ='controlMultiSelect']:checked").val();
	if (chkbxValue == "off") {
		$("#designArea").find(".execution-class").attr("add", "off");
		instance.clearDragSelection();
	} else {
		var statusComponent = $(obj).attr("add");
		if (statusComponent == "off") {
			$(obj).attr("add", "on");
			instance.addToDragSelection($(obj));
		} else {
			$(obj).attr("add", "off");
			instance.removeFromDragSelection($(obj));
		}
	}
}

function loadjsPlumb(){
	
	
	instance = jsPlumb.getInstance({
        Endpoint : ["Dot", {radius:2}],
        HoverPaintStyle : {strokeStyle:"#1e8151", lineWidth:2 },
        ConnectionOverlays : [
                                [ "Arrow", {
                                    location:1,
                                    id:"arrow",
                                    length:14,
                                    width:10,
                                    foldback:0.8
                                } ],
                                [ "Label", { label:"", id:"label", cssClass:"aLabel" }]
                            ],
        Container:"transition-area"
    });
    
    var windows = jsPlumb.getSelector(".transition-area .transition-class");

    // initialise draggable elements.
    instance.draggable(windows, {
		filter : ".ep"
	});

    // bind a click listener to each connection; the connection is deleted. you could of course
    // just do this: jsPlumb.bind("click", jsPlumb.detach), but I wanted to make it clear what was
    // happening.
    
    instance.bind("dblclick", function(c) {
        //instance.detach(c);
    });
    
    
    
    // bind a connection listener. note that the parameter passed to this function contains more than
    // just the new connection - see the documentation for a full list of what is included in 'info'.
    // this listener sets the connection's internal
    // id as the label overlay's text.
    
    instance.bind("connection", function(info) {
    	var currentConnection = info.connection;
    	currentConnection.bind("dblclick", function(connection, originalEvent) {
    		if(info.source.attributes.componenttype.value != "-1" &&  info.source.attributes.componenttype.value != "3"){
    			openDialogConnectTransitionSetting(info, 1);
    		} else if(info.target.attributes.componenttype.value != "3"){
    			instance.detach(connection);
    			for (var i = 0; i < allInstanceConnect.length; i++) {
	    			if(allInstanceConnect[i].screenTransitionId == connection.id){
	    				if($.isNumeric(connection.id)){
	    					allInstanceConnect[i].status = 0;
	    				}else{
	    					allInstanceConnect.splice(i,1);
	    				}
	    			}
    			}
    		}
    	});
    	currentConnection.bind("click", function(connection, originalEvent) {
//    		alert("Click");
    		if(info.target.attributes.componenttype.value != "3"){
    			var infoA;
				if(connection.id!="label"){
					infoA = connection;
				}
				else{
					infoA = connection.component;
				}
    			var currentConnection = infoA;
	      		var componenttype = $(infoA.source).attr("componenttype")
	      		var label = infoA.getOverlay("label").getLabel();
	      		var objNavigatorInfor = JSON.parse($(infoA.source).find("input[name=screenDesignElement]").val());
	      		
	      		if(label == ""){
	      			infoA.getOverlay("label").setLabel(1 + "." + objNavigatorInfor.objNavigatorInfoDetail[0].caption);
	      			for (var i = 0; i < allInstanceConnect.length; i++) {
	      				if(allInstanceConnect[i].screenTransitionId == infoA.id){
	      					allInstanceConnect[i].transitionName = 1 + "." + objNavigatorInfor.objNavigatorInfoDetail[0].caption;
	      				}
	      			}
	      		}else{
	      			var index = -1;
	      			for (i = 0; i < objNavigatorInfor.objNavigatorInfoDetail.length; i++) {
						var temp = (i+1) + "."+objNavigatorInfor.objNavigatorInfoDetail[i].caption;
						if(temp == label){
							index = i+1;
						}
					}
	      			if(index >= objNavigatorInfor.objNavigatorInfoDetail.length || index == -1){
						label = "";
					}else{
						label = (index +1) + "."+objNavigatorInfor.objNavigatorInfoDetail[index].caption;
					}
	      			infoA.getOverlay("label").setLabel(label);
	      			for (var i = 0; i < allInstanceConnect.length; i++) {
	      				if(allInstanceConnect[i].screenTransitionId == infoA.id){
	      					allInstanceConnect[i].transitionName = label;
	      				}
	      			}
	      		}
    		}
		});
    	
    });

    instance.bind("beforeDrop", function(info, currentConnection) {
    	var source = info.sourceId;
    	var target = info.targetId;
    	
//    	if($.isNumeric(source) && $.isNumeric(target)){
//    		var url = CONTEXT_PATH + "/screendesign/checkTransition?sourceId="+source+ "&targetId=" + target +"&r="+Math.random();	
//    		
//    		var result = $.qp.getString(url);
//    		
//    		var data = convertToJson(result);
//    		if (!data.status) {
//    			alert(data.message);
//    			return false;
//    		}
//    	}
    	/*var allConnection = instance.getAllConnections();
    	var isValid = true;
    	for (var i = 0; i < allConnection.length; i++) {
    		if (allConnection[i]["sourceId"] == sourceId && allConnection[i]["targetId"] == targetId) {
    			isValid = false;
    			break;
    		}
    	}*/
    	
    	/*if (!isValid) {
    		alert(dbMsgSource['sc.screendesign.0277']);
    		return false;
    	}*/
    	if($("input[name=moduleId]").val() != ""){
    		var count = 0;
    		if($(info.connection.target).attr("componenttype") == "3"){
    			for (var i = 0; i < allInstanceConnect.length; i++) {
    				if(allInstanceConnect[i].toScreen == target){
    					count++;
    				}
    			}
    		}
    		if(count == 0){
    			if($(info.connection.source).attr("componenttype") != "-1"){
    				var connection = new Object();
    				connection.fromScreen = source;
    				if($(info.dropEndpoint.element).attr("componenttype") == "-1"){
    					connection.toScreen = null;
    				}else{
    					connection.toScreen = target;
    				}
    				connection.screenTransitionId = info.connection.id;
    				connection.transitionName = "";
    				connection.transitionCode = "";
    				connection.status = 2;
    				if(checkDuplicateBranchTransition(connection.fromScreen, connection.toScreen, 1)){
    					return false;
    				}
    				allInstanceConnect.push(connection);
    				
    				if($(info.connection.source).attr("componenttype") == "3"){
    					connection.type = 1;
    					
    				}else if($(info.connection.target).attr("componenttype") == "3"){
    					connection.type = 2;
    				}else if($(info.connection.source).attr("componenttype") == "-1"){
    					connection.type = -1;
    					connection.fromScreen = null;
    				}else{
    					connection.type = 0;
    				}
    				
    				if($(info.connection.source).attr("componenttype") != "3"){
    					openDialogConnectTransitionSetting(info, 0);
    				}
    			}else{
    				return false;
    			}
    		}else{
				return false;
			}
    	}else{
    		alert(dbMsgSource['sc.screendesign.0504']);
    		return false;
    	}
    	return true;
    });
    
    // suspend drawing and initialise.
    instance.batch(function() {
		instance.makeSource(windows, {
			filter : ".ep",
			anchor : "Continuous",
			connector : [ "StateMachine", {
				curviness : 20
			} ],
			connectorStyle : {
				strokeStyle : "#5c96bc",
				lineWidth : 2,
				outlineColor : "transparent",
				outlineWidth : 4
			},
			onMaxConnections : function(info, e) {
				alert("Maximum connections (" + info.maxConnections + ") reached");
			}
		});

		// initialise all '.w' elements as connection targets.
		instance.makeTarget(windows, {
			dropOptions : {
				hoverClass : "dragHover"
			},
			anchor : "Continuous",
			allowLoopback : true
		});

		// and finally, make a couple of connections
		var screenTransitionId = "";
		//instance.reset();
		instance.clear();
		for (var i = 0; i < allInstanceConnect.length; i++) {
			screenTransitionId = allInstanceConnect[i].screenTransitionId;
			var c = instance.connect({
				source : allInstanceConnect[i].fromScreen,
				target : allInstanceConnect[i].toScreen,
				submitMethodType : allInstanceConnect[i].submitMethodType,
				overlays: [
				           ["Label", {
				        	   location: 40,
				        	   label:allInstanceConnect[i].transitionName
				           }]
				           ]
			});
			$(c).attr('id', screenTransitionId);
		}
	});
    
    jsPlumb.fire("screenTransitionLoad", instance);
}

function openBranchInformation(branchId, componenttype){
	$($("#dialogBranchNavigator")).modal(
			{ 
				show: true,
				closable: false,
				keyboard:false,
				backdrop:'static'
			}
		);
	var dialogBranchNavigator = $("#dialogBranchNavigator");
	$(dialogBranchNavigator).find("#currentTempScreenId").val($(branchId).attr("id") == null ? branchId : $(branchId).attr("id"));
	
	if($(branchId).find("input[name=screenDesignElement]").val() != undefined){
		var objNavigatorInfo = convertToJson($(branchId).find("input[name=screenDesignElement]").val());
	}else{
		var objNavigatorInfo = new Object();
	}
	
	var tableBranchInfor = $(dialogBranchNavigator).find("#tableBranchInfor");
	var tblIfcondition = $(dialogBranchNavigator).find("#tbl-ifcondition");
	
	tableBranchInfor.find("input[name=name]").val(objNavigatorInfo.name);
	tableBranchInfor.find("input[name=remark]").val(objNavigatorInfo.remark);
	
	$("#tbl-ifcondition tbody").html("")
	
	if(objNavigatorInfo.objNavigatorInfoDetail != undefined){
		for (i = 0; i < objNavigatorInfo.objNavigatorInfoDetail.length; i++) {
			var row = $.qp.addRowJSByLinkEx($(tblIfcondition).find("#addNew"),'tbl-ifcondition','tbl-ifcondition-template');
			$(row).find("input[name=caption]").val(objNavigatorInfo.objNavigatorInfoDetail[i].caption);
			$(row).find("input[name=detailId]").val(objNavigatorInfo.objNavigatorInfoDetail[i].branchDetailsId);
			$(row).find("input[name=conditionRemark]").val(objNavigatorInfo.objNavigatorInfoDetail[i].conditionRemark);
		}
	}
}

function saveBranchInformation(thiz){
	var dialogBranchNavigator = $("#dialogBranchNavigator");
	var currentTempScreenId = $(dialogBranchNavigator).find("#currentTempScreenId").val();
	var tableBranchInfor = $(dialogBranchNavigator).find("#tableBranchInfor");
	
	// Validate Branch
	if($(tableBranchInfor).find("input[name=name]").val() == ""){
		alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.screendesign.0496']));
		$(tableBranchInfor).find("input[name=name]").focus();
		return;
	}
	var flagEmpty = false;
	var strAlert = "";
	
	var count = 1;
	$('#tbl-ifcondition').find("tbody tr").each(function() {
		if($(this).find("input[name=caption]").val() == ""){
			strAlert += fcomMsgSource['err.sys.0077'].replace("{0}",dbMsgSource['sc.screendesign.0500']).replace("{1}",count);
			strAlert += "\n";
			flagEmpty = true;
		}
		count++;
	});
	
	if(strAlert != ""){
		alert(strAlert);
	}
	
	var flagCheckDuplicate = false;
	if(!flagEmpty){
		$('#tbl-ifcondition').find("tbody tr").each(function() {
			if(!flagCheckDuplicate){
				if(!validateDuplication($('#tbl-ifcondition'),"caption",dbMsgSource['sc.screendesign.0500'],true)){
					flagCheckDuplicate = true;
				}
			}
		});
	}
	
	if(flagCheckDuplicate || flagEmpty){
		return;
	}else{
		$("#transition-area").find("div.transition-class").each(function(i) {
			var screenId = $(this).attr("id");
			var position = $(this).position();
			var left = Math.round(position.left);
			var top = Math.round(position.top);
			
			if(screenId == currentTempScreenId){
				var objNavigatorInfo = new Object();
				objNavigatorInfo.branchId = screenId;
				objNavigatorInfo.branchIdTemp = screenId;
				objNavigatorInfo.name = $(tableBranchInfor).find("input[name=name]").val();
				objNavigatorInfo.remark = $(tableBranchInfor).find("input[name=remark]").val();
				objNavigatorInfo.status = $(this).attr("status");
				objNavigatorInfo.moduleId = $("input[name=moduleId]").val();
				objNavigatorInfo.yCoordinates = top;
				objNavigatorInfo.xCoordinates = left;
				
				var objNavigatorInfoDetail = [];
				
				$('#tbl-ifcondition').find("tbody tr").each(function() {
					var obj = new Object();
					obj.branchDetailsId = $(this).find("input[name=detailId]").val();
					obj.branchId = screenId;
					obj.caption = $(this).find("input[name=caption]").val();
					obj.conditionRemark = $(this).find("input[name=conditionRemark]").val();
					objNavigatorInfoDetail.push(obj);
				});
				
				objNavigatorInfo.objNavigatorInfoDetail = objNavigatorInfoDetail;
				$(this).addClass("node-branch");
				$(this).find("input[name=screenDesignElement]").val(convertToString(objNavigatorInfo));
			}
		});
		$("#dialogBranchNavigator").modal("hide");
	}
}

function openScreenDesign(screenId, componenttype){
	
	if(!$.isNumeric(screenId)){
		$($("#dialogAddNewScreen")).modal(
			{ 
				show: true,
				closable: false,
				keyboard:false,
				backdrop:'static'
			}
		);
		$("#dialogAdvanceSetting").find("input[name$='Autocomplete']").each(function(){
			$(this).data("ui-autocomplete")._trigger("close");
		});
		
		var dialogAddNewScreen = $("#dialogAddNewScreen");
		$(dialogAddNewScreen).find("#currentTempScreenId").val($(screenId).attr("id") == null ? screenId : $(screenId).attr("id"));
		
		var tableScreenInformation = $(dialogAddNewScreen).find("#tableScreenInformation");
		
		if($(screenId).find("input[name=screenDesignElement]").val() != undefined){
			var screenInformation = convertToJson($(screenId).find("input[name=screenDesignElement]").val());
		}else{
			var screenInformation = new Object();
		}
		
		
		$(tableScreenInformation).find("input[name=screenName]").val(screenInformation.screenName);
		$(tableScreenInformation).find("input[name=screenCode]").val(screenInformation.screenCode);
		$(tableScreenInformation).find("select[name=screenPatternType]").val(screenInformation.screenPatternType);
		if(screenInformation.confirmationType != undefined){
			$(tableScreenInformation).find("input[name=confirmationType][value=" + screenInformation.confirmationType + "]").prop('checked', true);
		}else{
			$(tableScreenInformation).find("input[name=confirmationType][value=1]").prop('checked', true);
		}
		
		if(screenInformation.completionType != undefined){
			$(tableScreenInformation).find("input[name=completionType][value=" + screenInformation.completionType + "]").prop('checked', true);
		}else{
			$(tableScreenInformation).find("input[name=completionType][value=1]").prop('checked', true);
		}
		
		$(tableScreenInformation).find("textarea[name=remark]").val(screenInformation.remark);
		
		$(tableScreenInformation).find("#moduleName").text($("#moduleIdAutocompleteId").val());
		$(tableScreenInformation).find("#templateType").text($(screenId).attr("componenttype") == undefined ? (componenttype == 1 ? TEMPLATE_TYPE[1] : TEMPLATE_TYPE[2]) : ($(screenId).attr("componenttype") == 1 ? TEMPLATE_TYPE[1] : TEMPLATE_TYPE[2]));
		$(tableScreenInformation).find("#functionDesignIdAutocompleteId").attr("arg01", $("input[name=moduleId]").val());
		$(tableScreenInformation).find("#functionDesignIdAutocompleteId").val(screenInformation.functionDesignIdAutocomplete);
		$(tableScreenInformation).find("input[name=functionDesignId]").val(screenInformation.functionDesignId);
		
		if(screenInformation.screenPatternType == 1 || screenInformation.screenPatternType == 3){
			$("#confirmationType").hide();
			$("#completionType").hide();
		}
		if(screenInformation.screenPatternType == 2 || screenInformation.screenPatternType == 4){
			$("#confirmationType").show();
			$("#completionType").show();
		}
	}else{
		if(parseInt(screenId) > 0){
			if ($.qp.confirm(dbMsgSource['sc.screendesign.0505']) == true) {
				var url = "/screendesign/design?r="+Math.random()+"&screenId=" + screenId;
				location.href = CONTEXT_PATH+url;
			}
		}
	}
}

function saveScreenInformation(){
	var dialogAddNewScreen = $("#dialogAddNewScreen");
	var currentTempScreenId = $(dialogAddNewScreen).find("#currentTempScreenId").val();
	var tableScreenInformation = $(dialogAddNewScreen).find("#tableScreenInformation");
	
	// Check empty
	var isNotValid = false;
	$("#transition-area").find("div.transition-class").each(function(i) {
		var screenId = $(this).attr("id");
		if(screenId == currentTempScreenId){
				
			if($(tableScreenInformation).find("input[name=screenName]").val() == ""){
				alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.screendesign.0005']));
				$(tableScreenInformation).find("input[name=screenName]").focus();
				isNotValid = true;
				return;
			}
			
			if($(tableScreenInformation).find("input[name=screenCode]").val() == ""){
				alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.screendesign.0007']));
				$(tableScreenInformation).find("input[name=screenCode]").focus();
				isNotValid = true;
				return;
			}
			
			if($(tableScreenInformation).find("input[name=functionDesignId]").val() == ""){
				alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.functiondesign.0002']));
				$(tableScreenInformation).find("input[name=functionDesignIdAutocomplete]").focus();
				isNotValid = true;
				return;
			}
			
			if($(tableScreenInformation).find("select[name=screenPatternType] option:selected").val() == ""){
				alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.screendesign.0009']));
				$(tableScreenInformation).find("select[name=screenPatternType]").focus();
				isNotValid = true;
				return;
			}
		}
	});
	
	// Check duplicate screen name and screen code
	if(!isNotValid){
		var flagErr = false
		var screenName = $(tableScreenInformation).find("input[name=screenName]").val();
		var screenCode = $(tableScreenInformation).find("input[name=screenCode]").val();
		$("#transition-area").find("div.transition-class").each(function(i) {
			var screenId = $(this).attr("id");
			if(screenId != currentTempScreenId){
				var screenCodeParent = $.trim($(this).find("input[name=screenDesignCode]").val());
				var screenNameParent = $.trim($(this).find("span[class=component-name]").text());
				if(!flagErr){
					if(screenNameParent != "" && screenNameParent == screenName ){
						alert($.qp.getMessage('err.sys.0036').replace("{0}",dbMsgSource['sc.screendesign.0005']));
						flagErr = true;
						return;
					}
					if(screenCodeParent != "" && screenCodeParent == screenCode){
						alert($.qp.getMessage('err.sys.0036').replace("{0}",dbMsgSource['sc.screendesign.0007']));
						flagErr = true;
						return;
					}
				}
			}
		});
	}
	
	if(flagErr || isNotValid){
		return;
	}else{
		$("#transition-area").find("div.transition-class").each(function(i) {
			var screenId = $(this).attr("id");
			if(screenId == currentTempScreenId){
				var container = $("#transition-area");
				var screenInformation = new Object();
				screenInformation.screenId = screenId;
				screenInformation.screenName = $(tableScreenInformation).find("input[name=screenName]").val();
				screenInformation.screenCode = $(tableScreenInformation).find("input[name=screenCode]").val();
				screenInformation.moduleId = $("input[name=moduleId]").val();
				screenInformation.functionDesignId = $(tableScreenInformation).find("input[name=functionDesignId]").val();
				screenInformation.functionDesignIdAutocomplete = $(tableScreenInformation).find("input[name=functionDesignIdAutocomplete]").val();
				
				screenInformation.screenPatternType = $(tableScreenInformation).find("select[name=screenPatternType] option:selected").val();
				screenInformation.templateType = $(this).attr("componenttype") == "1" ? 1 : 2;
				screenInformation.confirmationType = $(tableScreenInformation).find("input:radio[name=confirmationType]:checked" ).val();
				screenInformation.completionType = $(tableScreenInformation).find("input:radio[name=completionType]:checked" ).val();
				screenInformation.remark = $(tableScreenInformation).find("textarea[name=remark]").val();
				$(this).find("input[name=screenDesignCode]").val($(tableScreenInformation).find("input[name=screenCode]").val());
				$(this).find("input[name=screenDesignElement]").val(convertToString(screenInformation));
				$(this).find("span[class=component-name]").html($(tableScreenInformation).find("input[name=screenName]").val());
				$(this).removeClass("node-error");
				
				instance.repaintEverything();
			}
		});
		$("#dialogAddNewScreen").modal("hide");
	}
}

function removeScreenInformation(thiz){
	var dialogAddNewScreen = $("#dialogAddNewScreen");
	var currentTempScreenId = $(dialogAddNewScreen).find("#currentTempScreenId").val();

	$("#transition-area").find("div.transition-class").each(function(i) {
		var screenId = $(this).attr("id");
		if(screenId == currentTempScreenId){
			if ($.qp.confirm(dbMsgSource['sc.screendesign.0506']) == true) {
				deleteNode($(this),instance);
				$(this).remove();
				$("#dialogAddNewScreen").modal("hide");
			}
		}
	});
}

function removeBranchNavigator(thiz){
	var dialogAddNewScreen = $("#dialogBranchNavigator");
	var currentTempScreenId = $(dialogAddNewScreen).find("#currentTempScreenId").val();

	$("#transition-area").find("div.transition-class").each(function(i) {
		var screenId = $(this).attr("id");
		if(screenId == currentTempScreenId){
			if ($.qp.confirm(dbMsgSource['sc.screendesign.0506']) == true) {
				deleteNode($(this),instance);
				$(this).attr("status","0");
				$(this).hide();
				$("#dialogBranchNavigator").modal("hide");
			}
		}
	});
}

function deleteNode(thiz,instance){
	if(instance != undefined){
		deleteNodeNotConfirm(thiz,instance);
		instance.repaintEverything();
		//clear tooltip of end node
		$("div.tooltip").remove();
	}
}

function deleteNodeNotConfirm(thiz,instance){
	var targetBoxId = $(thiz).attr("id");
	var componentType =  $(thiz).attr("componenttype");
	if(componentType == "9"){
		//remove end-if
		var idEndIf =  $(thiz).attr("id-endif");
		if(idEndIf != undefined && idEndIf != null){
			var endIfNode = $(thiz).closest("div.design-area").find("div[id='"+idEndIf+"']");
			if(endIfNode.length > 0){
				deleteNodeNotConfirm(endIfNode,instance);
			}
		}
	}
	
	var connectionSources = instance.getConnections({ source:targetBoxId });
	var connectionTargets = instance.getConnections({ target:targetBoxId });
	for(var i = 0;i<connectionSources.length;i++){
		processDeleteConnectionOfDeletedNode(connectionSources[i],instance, 1);
	}
	for(var i = 0;i<connectionTargets.length;i++){
		processDeleteConnectionOfDeletedNode(connectionTargets[i],instance, 2);
	}
	instance.removeAllEndpoints(targetBoxId);
	instance.detach(targetBoxId);
	$(instance.getContainer()).find("#" + targetBoxId).attr("status","0")
	var objBranch = convertToJson($(instance.getContainer()).find("#" + targetBoxId).find("input[name=screenDesignElement]").val());
	objBranch.status = "0";
	$(instance.getContainer()).find("#" + targetBoxId).find("input[name=screenDesignElement]").val(convertToString(objBranch));
}

function processDeleteConnectionOfDeletedNode(connection,instance, type){
	if(connection.sourceId == undefined){
		connection = connection.component;
	}
	
	for (var i = 0; i < allInstanceConnect.length; i++) {
		arrConnectionComponent.push(allInstanceConnect[i].fromScreen);
		arrConnectionComponent.push(allInstanceConnect[i].toScreen);
		if(type == 1){
			var id = connection.sourceId;
			if(allInstanceConnect[i].fromScreen == id){
				//allInstanceConnect.splice(i,1);
				allInstanceConnect[i].status = 0;
			}
		}else{
			var id = connection.targetId;
			if(allInstanceConnect[i].toScreen == id){
				//allInstanceConnect.splice(i,1);
				allInstanceConnect[i].status = 0;
			}
		}
	}
}

function changeScreenPattern(obj){
	if($(obj).val() == 1 || $(obj).val() == 3){
		$("#confirmationType").hide();
		$("#completionType").hide();
	}
	if($(obj).val() == 2 || $(obj).val() == 4){
		$("#confirmationType").show();
		$("#completionType").show();
	}
}

function getScreenName(screenId){
	var screenName = "";
	if(screenId == null){
		screenName = "New Screen Common";
	}else{
		$("#transition-area").find("div.transition-class").each(function (i){
			if(screenId == $(this).attr("id") && screenName == ""){
				screenName = $(this).find("span[class=component-name]").text();
			}
		});
	}
	return $.trim(screenName);
}

function saveScreenPosition(target){
	
	var arrConnection = new Array();
	var arrBranch = new Array();
	var strAlert = "";
	var hasErr = false;
	for (var i = 0; i < allInstanceConnect.length; i++) {
		var connection = new Object();
//		connection.connectorSource = allInstanceConnect[i].source;
//		connection.connectorDest = allInstanceConnect[i].target;
//		connection.source = allInstanceConnect[i].source;
//		connection.target = allInstanceConnect[i].target;
//		connection.submitMethodType = allInstanceConnect[i].submitMethodType;
//		connection.navigateToBlogicId = allInstanceConnect[i].navigateToBlogicId;
//		connection.navigateToBlogicText = allInstanceConnect[i].navigateToBlogicText;
//		connection.connectionMsg = allInstanceConnect[i].connectionMsg;
//		connection.screenActionId = allInstanceConnect[i].screenActionId;
		
		connection.screenTransitionId = allInstanceConnect[i].screenTransitionId;
		connection.transitionName = allInstanceConnect[i].transitionName;
		connection.transitionCode = allInstanceConnect[i].transitionCode;
		connection.fromScreen = allInstanceConnect[i].fromScreen;
		connection.toScreen = allInstanceConnect[i].toScreen;
		connection.status = allInstanceConnect[i].status;
		connection.type = allInstanceConnect[i].type;
		if(allInstanceConnect[i].type != "1"){
			if(connection.transitionName == "" || connection.transitionCode == ""){
				hasErr = true;
				strAlert += "+++++++++++++++++++++++++\n";
				strAlert += "Traansition from screen ["+getScreenName(connection.fromScreen)+"] to screen ["+getScreenName(connection.toScreen)+"] is not enough info\n";
			}
		}
		
		arrConnection.push(connection);
	}
	var jsonConnectorParam =  convertToString(arrConnection);
	var jsonInfo = $("input[name=jsonInfo").val();
	
	var screenPosition = "";
	var jsonBranch = "";
	hasErr = false;
	var hasBranchErr = false;
    $("#transition-area").find("div.transition-class").each(function (i){
        var screenId = $(this).attr("id");
        var position = $(this).position();
        var left = Math.round(position.left);
        var top = Math.round(position.top);
        
        if($(this).attr("componenttype") == "1" || $(this).attr("componenttype") == "2"){
        	var screenInformation = convertToJson($(this).find("input[name=screenDesignElement]").val());
        	
        	//screenId = screenId.substring(6, screenId.length);
        	// Append string
        	if($.isNumeric(screenId)){
        		if(screenId != "-1"){
        			screenPosition += "{\"screenId\":\""+screenId+"\",\"xCoordinate\":\""+left+"\",\"yCoordinate\":\""+top+"\"};";
        		}
        	}else{
        		
        		if(screenInformation.screenName == undefined || screenInformation.screenCode == undefined){
            		$(this).addClass("node-error");
            		hasErr = true;
            	}
        		
    			screenPosition += "{\"screenId\":\"";
    			screenPosition += "{\"screenId\":\""+$.isNumeric(screenId) == 1 ? screenId : null+"\",\"screenIdTemp\":\""+screenId+"\",\"xCoordinate\":\""+left+"\",\"yCoordinate\":\""+top+"\"";
    			screenPosition += ",\"screenName\":\""+screenInformation.screenName+"\",\"screenCode\":\""+screenInformation.screenCode+"\",\"moduleId\":\""+screenInformation.moduleId+"\"";
    			screenPosition += ",\"functionDesignId\":\""+screenInformation.functionDesignId+"\",\"screenPatternType\":\""+screenInformation.screenPatternType+"\",\"templateType\":\""+screenInformation.templateType+"\"";
    			screenPosition += ",\"confirmationType\":\""+screenInformation.confirmationType+"\",\"completionType\":\""+screenInformation.completionType+"\",\"remark\":\""+screenInformation.remark+"\"};";
        	}
        }else if($(this).attr("componenttype") == "3"){
        	if($(this).find("input[name=screenDesignElement]").val() == ""){
        		$(this).removeClass("node-branch").addClass("node-error");
				hasBranchErr = true;
        	}else{
        		var objBranch = convertToJson($(this).find("input[name=screenDesignElement]").val());
        		
        		if(objBranch.name == ""){
        			$(this).removeClass("node-branch").addClass("node-error");
        			hasBranchErr = true;
        		}
        		
        		for(var j = 0; j < objBranch.objNavigatorInfoDetail.length; j++){
        			if(objBranch.objNavigatorInfoDetail[j].caption == ""){
        				$(this).removeClass("node-branch").addClass("node-error");
        				hasBranchErr = true;
        			}
        		}
        		
        		objBranch.yCoordinates = top;
        		objBranch.xCoordinates = left;
        		arrBranch.push(objBranch);
        	}
        }
    });
    
    if(hasErr){
    	strAlert += "+++++++++++++++++++++++++\n";
    	strAlert += "Screen is not enough info\n";
    	strAlert += "+++++++++++++++++++++++++";
    }
    
    if(hasBranchErr){
    	strAlert += "+++++++++++++++++++++++++++++++++++++++++\n";
    	strAlert += "Branch navigator is not enough infomation\n";
    	strAlert += "+++++++++++++++++++++++++++++++++++++++++";
    }
    
    screenPosition = screenPosition.substring(0, screenPosition.length - 1);
    var jsonBranchParam =  convertToString(arrBranch);
	
	if(strAlert != ""){
		$.qp.showAlertModal(strAlert);
		return false;
	}else{
		var form = $(target).closest("form");   
		$(form).find("input[name=parameters]").val(screenPosition);
		$(form).find("input[name=jsonConnector]").val(jsonConnectorParam);
		$(form).find("input[name=jsonBranch]").val(jsonBranchParam);
		$(form).find("input[name=jsonInfo]").val(jsonInfo);
		var url = CONTEXT_PATH + "/screendesign/saveTransition";
		$(form).attr("action", url);
		return true;
	}
};

function convertToString(json) {
	return  JSON.stringify(json);
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

function isSubmitEvent(obj){
	var tableItemDefaultSetting = $("#dialogAdvanceSetting").find("#tableItemDefaultSetting");
	if(obj.checked){
		$(tableItemDefaultSetting).find("#navigateToBlogicAutocompleteId").attr("arg04", 1);
	}else{
		$(tableItemDefaultSetting).find("#navigateToBlogicAutocompleteId").attr("arg04", 0);
	}
}

function openDialogConnectTransitionSetting(obj, type) {
	$($("#dialogAdvanceSetting")).modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
	$("#connectionId").val(obj.connection.id);
	var modal = ("#dialogAdvanceSetting");
	$(modal).data("obj", obj);
	$("#dialogAdvanceSetting").find("input[name$='Autocomplete']").each(function(){
		$(this).data("ui-autocomplete")._trigger("close");
	});
	var tableItemDefaultSetting = $("#dialogAdvanceSetting").find("#tableItemDefaultSetting");
	$(tableItemDefaultSetting).find("#naviTo").text($(obj.target).text());
	var map = new Object();
	var jSonScreenTransition = $(obj.source).find("#jSonScreenTransition").val();
	
	for (var i = 0; i < allInstanceConnect.length; i++) {
		if(allInstanceConnect[i].screenTransitionId == $("#connectionId").val()){
			var connection = new Object();
			connection = allInstanceConnect[i];
		}
	}
	
	if(connection != undefined){
		
		$(tableItemDefaultSetting).find("input[name=transitionName]").val(connection.transitionName);
		$(tableItemDefaultSetting).find("input[name=transitionCode]").val(connection.transitionCode);
		
		if(connection.bLogicName != "" && connection.buttonOrLinkName == ""){
			$(tableItemDefaultSetting).find("#buttonOrLink").html("").append("<i class=\"glyphicon glyphicon-pencil\" ></i> &nbsp(Modify)")
		}else{
			$(tableItemDefaultSetting).find("#buttonOrLink").html(connection.buttonOrLinkName);
		}
			
		$(tableItemDefaultSetting).find("#bLogicName").html(connection.bLogicName);
		$(tableItemDefaultSetting).find("#fromScreen").html(getScreenName(connection.fromScreen));
		if(connection.type == "2"){
			$(tableItemDefaultSetting).find("#toScreen").html("Branch Navigator");
		}else{
			$(tableItemDefaultSetting).find("#toScreen").html(getScreenName(connection.toScreen));
		}
	}else{
		$(tableItemDefaultSetting).find("input[name=transitionName]").val("");
		$(tableItemDefaultSetting).find("input[name=transitionCode]").val("");
	}
	
	if(type == "0"){
		$(tableItemDefaultSetting).find("#buttonOrLink").html("");
		$(tableItemDefaultSetting).find("#bLogicName").html("");
	}
	
	$("#toScreenId").val($(obj.target).attr("id"));
	$("#fromScreenId").val($(obj.source).attr("id"));
	$("#connectionId").val($(obj.connection).attr("id"));
}

function saveDialogSetting(){
	var tableItemDefaultSetting = $("#dialogAdvanceSetting").find("#tableItemDefaultSetting");
	var toScreenId = $("#toScreenId").val();
	var fromScreenId = $("#fromScreenId").val();
	var modal = $("#dialogAdvanceSetting");
	var obj = $(modal).data("obj");
	var isNotValid = false;
	
	for (var i = 0; i < allInstanceConnect.length; i++) {
		if(allInstanceConnect[i].screenTransitionId == $("#connectionId").val()){
			var connection = new Object();
//			connection.source = allInstanceConnect[i].source;
//			connection.target = allInstanceConnect[i].target;
//			connection.submitMethodType = allInstanceConnect[i].submitMethodType;
//			connection.navigateToBlogicId = allInstanceConnect[i].navigateToBlogicId;
//			connection.navigateToBlogicText = allInstanceConnect[i].navigateToBlogicText;
//			connection.connectionMsg = allInstanceConnect[i].connectionMsg;
//			connection.screenActionId = allInstanceConnect[i].screenActionId;
			
			connection.screenTransitionId = allInstanceConnect[i].screenTransitionId;
			connection.transitionName = allInstanceConnect[i].transitionName;
			connection.transitionCode = allInstanceConnect[i].transitionCode;
			connection.fromScreen = allInstanceConnect[i].fromScreen;
			connection.toScreen = allInstanceConnect[i].toScreen;
			connection.status = allInstanceConnect[i].status;
		}
	}
	
	if(connection != undefined){
//		connection.submitMethodType = $(tableItemDefaultSetting).find("#isSubmit").prop('checked') ? 2 : 1;
//		connection.navigateToBlogicId = $(tableItemDefaultSetting).find("input[name=navigateToBlogic]").val();
//		connection.navigateToBlogicText = $(tableItemDefaultSetting).find("input[name=navigateToBlogicAutocomplete]").val();
//		connection.connectionMsg = $(tableItemDefaultSetting).find("input[name=actionName]").val();
		
		if($(tableItemDefaultSetting).find("input[name=transitionName]").val() == ""){
			alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.screendesign.0492']));
			$(tableItemDefaultSetting).find("input[name=transitionName]").focus();
			return;
		}
		
		if($(tableItemDefaultSetting).find("input[name=transitionCode]").val() == ""){
			alert($.qp.getMessage('err.sys.0025').replace("{0}",dbMsgSource['sc.screendesign.0493']));
			$(tableItemDefaultSetting).find("input[name=transitionCode]").focus();
			return;
		}
		
		connection.transitionName = $(tableItemDefaultSetting).find("input[name=transitionName]").val();
		connection.transitionCode = $(tableItemDefaultSetting).find("input[name=transitionCode]").val();
		
	}
	
	
	
	for (var i = 0; i < allInstanceConnect.length; i++) {
		if(allInstanceConnect[i].screenTransitionId == $("#connectionId").val()){
			allInstanceConnect[i] = connection;
			//loadjsPlumb();
			break; //Stop this loop, we found it!
		}
	}
	
	for (var i = 0; i < allInstanceConnect.length; i++) {
		if(allInstanceConnect[i].status	 != "0"){
			for (var j = 0; j < allInstanceConnect.length; j++) {
				if(i != j){
					if(allInstanceConnect[j].status	 != "0"){
						if(allInstanceConnect[i].transitionName == allInstanceConnect[j].transitionName){
							if(allInstanceConnect[i].transitionName != "" && allInstanceConnect[i].type	 != "1"){
								alert($.qp.getMessage('err.sys.0036').replace("{0}",dbMsgSource['sc.screendesign.0492']));
								isNotValid = true;
								return;
								
							}
						}
						
						if(allInstanceConnect[i].transitionCode == allInstanceConnect[j].transitionCode){
							if(allInstanceConnect[i].transitionCode != "" && allInstanceConnect[i].type != "1" && allInstanceConnect[i].status	 != "0"){
								alert($.qp.getMessage('err.sys.0036').replace("{0}",dbMsgSource['sc.screendesign.0493']));
								isNotValid = true;
								return;
							}
						}
					}
				}
			}
		}
	}
	
	if(isNotValid){
		return;
	}
	//quangvd
	//change label of connection
	if(instance != undefined){
		var connectionId = $("#connectionId").val();
		var connectionLabel = $(tableItemDefaultSetting).find("input[name=transitionName]").val();
		for(var i=0;i < instance.getConnections().length;i++){
			var connection = instance.getConnections()[i];
			//find current connection
			if(connection.id  == connectionId){
				connection.getOverlay("label").setLabel(connectionLabel);
				break;
			}
		}
	}
	//
	$("#dialogAdvanceSetting").modal("hide");
}

function checkDuplicateBranchTransition(fromScr, toScr, type){
	for (var i = 0; i < allInstanceConnect.length; i++) {
		if(allInstanceConnect[i].fromScreen == fromScr && allInstanceConnect[i].toScreen == toScr && allInstanceConnect[i].type == type){
			return true;
			break;
		}
	}
	return false;
}

function removeSetting(){
	if ($.qp.confirm(dbMsgSource['sc.screendesign.0507']) == true) {
		var tableItemDefaultSetting = $("#dialogAdvanceSetting").find("#tableItemDefaultSetting");
		var toScreenId = $("#toScreenId").val();
		var fromScreenId = $("#fromScreenId").val();
		var modal = $("#dialogAdvanceSetting");
		var obj = $(modal).data("obj");
		instance.detach(obj);
		
		for (var i = 0; i < allInstanceConnect.length; i++) {
			if(allInstanceConnect[i].screenTransitionId == $("#connectionId").val()){
				if($.isNumeric(allInstanceConnect[i].screenTransitionId)){
					allInstanceConnect[i].status = 0;
					var toScreen = allInstanceConnect[i].toScreen;
					$("#transition-area").find("div.transition-class").each(function (i){
						var screenId = $(this).attr("id");
						if(parseInt(toScreen) < 0 && toScreen == screenId){
							if(!$.isNumeric(screenId)){
								$(this).remove();
							}else{
								if(parseInt(screenId) == toScreen){
									$(this).remove();
								}
							}
						}
					});
				}else{
					allInstanceConnect.splice(i,1);
				}
				break; //Stop this loop, we found it!
			}
		}
		$("#dialogAdvanceSetting").modal("hide");
	}
}

function validateDuplication(modal,inputName,inputLabel, flagCheck) {
	var $Inputs = $(modal).find("input[name$="+inputName+"]");
	var messages="";
	for(var i=$Inputs.length-1;i>0;i--){
		for(var j=0;j<i;j++){
			if($.trim($Inputs.eq(i).val()) == $.trim($Inputs.eq(j).val())){
				messages +="\r\n" + fcomMsgSource['err.sys.0041'].replace("{0}",inputLabel).replace("{1}",i+1);
				break;
			}
		}
	}

	if(messages!="") {
		$($Inputs).closest(".modal-body").find("a[href='#"+$Inputs.closest(".tab-pane").attr("id")+"']").tab("show");
		setFocus(messages);
		if(flagCheck){
			alert(messages);
		}
		return false;
	}
	return true;
}

function checkClickShow(){
	$("input[name=clicked]").val("true");
}

function removeModuleId(){
	if($("input[name=moduleId]").val() == ""){
		$("button[name=saveBtn]").hide();
	}else{
		$("button[name=saveBtn]").show();
	}
}

function setFocus(messages){
	var indexRow = parseInt(messages.match(/\d+/)[0]);
	var numberRow = 1;
	$('#tbl-ifcondition').find("tbody tr").each(function() {
		if(numberRow == indexRow){
			$(this).focus();
		}
		numberRow++;
	});
}

