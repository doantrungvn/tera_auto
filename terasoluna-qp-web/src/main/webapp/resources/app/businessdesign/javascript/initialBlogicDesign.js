var OPEN_PARENTHESIS = "(";
var CLOSE_PARENTHESIS = ")";
/**
 * 
 */
var instanceOfBlogic;
var arrConnectionComponent;
function loadJsPlumb(isOnlyView){
	jsPlumb.ready(function() {

		// setup some defaults for jsPlumb.
		instanceOfBlogic = iniJsPlumb("designArea",allInstanceConnect,isOnlyView);
		jsPlumb.fire("businessDesignLoad", instanceOfBlogic);
	});
	if(!isOnlyView){
		// Tab process
//		processTabs();

		// Drag - Drop for process business design
		processAllDragDropComp();

		// Disable - Enable all pop-up screen design area select
//		processAllPopup();
		// Disable - Enable all multi select
//		processMultiSelect(instanceOfBlogic);

		$("#designArea > .execution-class").click(function() {
			compDragClick($(this),instanceOfBlogic);
		});

		// Attach listeners to add/remove links
		$(".clearSelection a.com-link-popup.select-all").click(function(e) {
			$(".clearSelection").find("input[name='controlMultiSelect'][value='on']").prop("checked",true);
			$("#designArea").find(".execution-class").each(function() {
				$(this).attr("add", "on");
				instanceOfBlogic.addToDragSelection(this);
				e.preventDefault();
				e.stopPropagation();
			});
		});

		// Attach listeners to add/remove links
		$(".clearSelection a.com-link-popup.clear-all").click(function(e) {
			$("#designArea").find(".execution-class").attr("add", "off");
			instanceOfBlogic.clearDragSelection();
			e.preventDefault();
			e.stopPropagation();
		});
		
//		$(".clearSelection").find("input[name='controlMultiSelect'][value='off']").click(function(e) {
//			var checked = $(this).prop("checked");
//			if(checked){
//				$("#designArea").find(".execution-class").attr("add", "off");
//				instanceOfBlogic.clearDragSelection();
//				e.preventDefault();
//				e.stopPropagation();
//			}
//		});
		$(".clearSelection").find("input[name='controlMultiSelect'][value='off']").bind("click", function(){
			var checked = $(this).prop("checked");
			if(checked){
				$("#designArea").find(".execution-class").attr("add", "off");
				instanceOfBlogic.clearDragSelection();
			}
		});
	}
	
};
function onclickDisableMultiselect(thiz){
	var checked = $(thiz).prop("checked");
	if(checked){
		$("#designArea").find(".execution-class").attr("add", "off");
		instanceOfBlogic.clearDragSelection();
	}
}
function iniJsPlumb(containerId,allInstanceConnect,isOnlyView) {
	var container = $("#"+containerId);
	var instance = jsPlumb.getInstance({
		Endpoint : [ "Dot", {
			radius : 2
		} ],
		HoverPaintStyle : {
			strokeStyle : "#1e8151",
			lineWidth : 2
		},
		ConnectionOverlays : [ [ "Arrow", {
			location : 1,
			id : "arrow",
			length : 14,
			foldback : 0.8
		} ], [ "Label", {
			label : "",
			id : "label",
			cssClass : "aLabel",
		}, {
	        } ] ],
		Container : containerId
	});

	// Get all element for drag
	var windows = jsPlumb.getSelector("#"+containerId+" > .execution-class");
	var els = $(container).find(".execution-class");
	if(!isOnlyView){
		instance.draggable(els
			, {
			containment : containerId,
	        stop:function(e){
	            var position = getMaxPositionOfItemsBD();
	            $(container).height(position.top);
	            $(container).width(position.left);
	        },
			}
		);
	
		instance.bind("connection", function(info) {
			// info.connection.getOverlay("label").setLabel("normal");
			var currentConnection = info.connection;
			var componenttype = $(info.source).attr("componenttype")
			var label = info.connection.getOverlay("label").getLabel();
			var params = info.connection.getParameters();
			if (params.type == undefined || params.type == "") {
//				var returnInfor = calcLabelForConnection(componenttype,info);
//				if(returnInfor != undefined && returnInfor.validFlag){
//					info.connection.getOverlay("label").setLabel(returnInfor.label);
//				}else{
////					alert("This connection is invalid");
////					//To delete a single Endpoint
////					var arrEndpoints = currentConnection.endpoints;
////					for(var i=0;i <arrEndpoints.length;i++){
////						instance.deleteEndpoint(arrEndpoints[i],false);
////					}
////					$(container).find("circle:last").closest("div").remove()
////					return;
//				}
				if(componenttype == "9"){
					
				}
				info.connection.getOverlay("label").setLabel("");
			} else {
				info.connection.getOverlay("label").setLabel(params.label);
//				setStatusConnection(info,params.label,true);
				
			}
	
			currentConnection.bind("click", function(connection, originalEvent) {
				console.log('single Click');
				var info;
				if(connection.id!="label"){
					info = connection;
				}
				else{
					info = connection.component;
				}
	          	var currentConnection = info;
	      		var componenttype = $(info.source).attr("componenttype")
	      		var label = info.getOverlay("label").getLabel();
//	      		var returnInfor = calcLabelForConnection(componenttype,info);
//	  			if(returnInfor != undefined && returnInfor.validFlag){
//	  				info.getOverlay("label").setLabel(returnInfor.label);
//	  				
//	  				//update status of old label
//	  				setStatusConnection(info,label,false);
//	  			}else{
//	//  				alert("Can not change type of connection");
//	  			}
	      		
	      		if(componenttype=="9"){
	      			var newLabel = getLabelOfIfNode($(info.source), label);
	      			info.getOverlay("label").setLabel(newLabel);
	      		}
	      		if(componenttype=="10"){
	      			var newLabel = getLabelOfLoopNode($(info.source), label);
	      			info.getOverlay("label").setLabel(newLabel);
	      		}
				 
			});
			currentConnection.bind("dblclick", function(connection, originalEvent) {
				if(connection.sourceId == undefined){
					connection = connection.component;
				}
//				var sourceId = connection.sourceId;
//				if(arrConnectionComponent != undefined && arrConnectionComponent[sourceId] !=undefined){
//					var arrStatus = arrConnectionComponent[sourceId];
//					var label = connection.getOverlay("label").label
//					if(label == null){
//						label = "";
//					}
//					for(var i=0;i<arrStatus.length;i++){
//						if(label.trim() == arrStatus[i].type.trim()){
//							arrStatus[i].status =  false;
//							break;
//						}
//					}
//					arrConnectionComponent[sourceId] = arrStatus;
//					instance.detach(connection);
//				}else{
////					alert("SYSTEM ERROR");
//				}
				
				instance.detach(connection);
			});
		});
		instance.bind("connectionMoved", function(info, originalEvent){
			alert("move connection");
		});
	}else{
		//display label of connection
		instance.bind("connection", function(info) {
			// info.connection.getOverlay("label").setLabel("normal");
			var currentConnection = info.connection;
			var componenttype = $(info.source).attr("componenttype")
			var label = info.connection.getOverlay("label").getLabel();
			var params = info.connection.getParameters();
			if (params.type == undefined || params.type == "") {
//				var returnInfor = calcLabelForConnection(componenttype,info);
//				if(returnInfor != undefined && returnInfor.validFlag){
//					info.connection.getOverlay("label").setLabel(returnInfor.label);
//				}else{
////					alert("This connection is invalid");
////					//To delete a single Endpoint
////					var arrEndpoints = currentConnection.endpoints;
////					for(var i=0;i <arrEndpoints.length;i++){
////						instance.deleteEndpoint(arrEndpoints[i],false);
////					}
////					$(container).find("circle:last").closest("div").remove()
////					return;
//				}
				if(componenttype == "9"){
					
				}
				info.connection.getOverlay("label").setLabel("");
			} else {
				info.connection.getOverlay("label").setLabel(params.label);
//				setStatusConnection(info,params.label,true);
				
			}
		});
	}
	
	

	// suspend drawing and initialise.
	instance.batch(function() {
		
		var position = getMaxPositionOfItemsBD();
	    $(container).height(position.top);
	    $(container).width(position.left);
		
		for (var i = 0; i < windows.length; i++) {
			var obj = windows[i];
			initEndPointForComponent(obj,instance);
		}

		// and finally, make a couple of connections
		for (var i = 0; i < allInstanceConnect.length; i++) {
			instance.connect({
				source : allInstanceConnect[i].source,
				target : allInstanceConnect[i].target,
				overlays : [ [ "Arrow", {
					location : 1,
					id : "arrow",
					length : 14,
					width : 10,
					foldback : 0.8
				} ], [ "Label", {
					location : 45,
					cssClass : "aLabel"
				} ] ],
				parameters : {
					"type" : "modify",
					"label" : allInstanceConnect[i].label
				}
			});
		}
	});
	
	

	// and finally, make a couple of connections
	return instance;
}

function processAllDragDropComp() {
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

	$("#designArea").droppable({
		accept : "#srcgenElements div.bdesign-node",
		activeClass : "state-droppable",
		drop : function(event, ui) {
			// Process create component after drop
			var offset = $(this).offset();
			var draggableOffsetLeft = ui.offset.left;
			var draggableOffsetTop= ui.offset.top + 8;
			var type =  $(ui.draggable).attr('type');
			var componentType =  $(ui.draggable).attr('elementtype');
			createNewComponent(event, $(ui.draggable),type,componentType, draggableOffsetLeft, draggableOffsetTop, offset,"designArea", instanceOfBlogic);
		}
	});
}

// Disable - Enable all pop-up screen design area select
function processAllPopup() {
	$("input:radio[name ='controlpopup']").change(function() {
		var chkbxValue = $("input:radio[name ='controlpopup']:checked").val();
		if (chkbxValue == "off") {
			// Disable on pop-up area design
			$("#designArea").find(".execution-class").each(function() {
				if ($(this).find("a#bkavd").attr("href") != "#") {
					$(this).find("a#bkavd").attr("bkhref", $(this).find("a#bkavd").attr("href"));
					$(this).find("a#bkavd").toggleClass("qp-link-popup");
					$(this).find("a#bkavd").attr("href", "#");
				}

				$(this).attr("bkondblclick", $(this).attr("ondblclick"));
				$(this).removeAttr("ondblclick");
			});
		} else {
			$("#designArea").find(".execution-class").each(function() {
				if ($(this).find("a#bkavd").attr("bkhref") != null) {
					$(this).find("a#bkavd").attr("href", $(this).find("a#bkavd").attr("bkhref"));
					$(this).find("a#bkavd").toggleClass("qp-link-popup");
					return;
				}

				$(this).attr("ondblclick", $(this).attr("bkondblclick"));
			})
		}
	});
}

// Disable - Enable multi select
function processMultiSelect(instance) {
	$("input:radio[name ='controlpopup']").change(function() {
		var chkbxValue = $("input:radio[name ='controlpopup']:checked").val();
		if (chkbxValue == "off") {
			// Disable on pop-up area design
			instance.clearDragSelection();
		} else {

		}
	});
}

// event when click component
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

function toggleFunction(obj) {
	$(obj).prev("input").toggleClass("bgFunction");
}

function createNewComponent(event, div,listType,componentType, draggableOffsetLeft, draggableOffsetTop, offset, containerId, instance) {
	var container = $("#"+containerId);
	var prefixId = $(container).attr("prefixId");
	if(prefixId == undefined || prefixId == ""){
		prefixId = "jsPlumb_";
	}
	if(container != null && container.length >0){
		var chkbxValue = $("input:radio[name ='controlpopup']:checked").val();
//		var xPos = ui.draggable.offset().left - offset.left;
//		var yPos = ui.draggable.offset().top - offset.top;
		var xPos = draggableOffsetLeft - offset.left;
		var yPos = draggableOffsetTop - offset.top;
		console.log("\n" + yPos);
		var connectionTarget = 0;
	
		if(listType != "pattern"){
			var newElement = $("<div>").addClass("execution-class");
			var connect = $("<div>").addClass("ep");
			var elementSetting = "<input type='hidden' value=''/>";
			$(newElement).attr("data-toggle", "tooltip");
			$(newElement).attr("data-placement", "right");
			$(newElement).attr("componenttype", componentType);
			switch (componentType) {
				case "2":
					$(newElement).attr("ondblclick", "openModalExecution(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/excution.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0002') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "3":
					$(newElement).attr("ondblclick", "openModalValidationCheck(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/validationcheck.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0003') + CLOSE_PARENTHESIS+"<span class='component-name'></span").append(connect);
					break;
					
				case "4":
					$(newElement).attr("ondblclick", "openModalBusinessCheck(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/businesscheck.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0004') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "5":
					$(newElement).attr("ondblclick", "openModalDecision(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/decision.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0005') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				
				case "6":
					$(newElement).addClass("bdesign-node-two");
					$(newElement).attr("ondblclick", "openModalAdvance(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/advanced.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0006') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "7":
					$(newElement).addClass("bdesign-node-two");
					$(newElement).attr("ondblclick", "openModalCommon(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/common.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0007') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "8":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalAssign(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/assign.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0008') + CLOSE_PARENTHESIS+"<span class='component-name'><span>").append(connect);
					break;
					
				case "9":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalIf(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/if.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0009') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					connectionTarget = 1;
					var newTop = draggableOffsetTop + 200;
					var idEndIf = createNewComponent(event, div,null,"19", draggableOffsetLeft, newTop, offset, containerId, instance);
					$(newElement).attr("id-endif", idEndIf);
					break;
					
				case "10":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalLoop(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/foreach.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0010') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					connectionTarget = 1;
					break;
					
				case "11":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalFeedback(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/feedback.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0011') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "12":
					$(newElement).attr("ondblclick", "openModalNavigator(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/navigator.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0012') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "13":
					$(newElement).addClass("bdesign-node-three");
					$(newElement).attr("ondblclick", "deleteNode(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/end.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+$.qp.getModuleMessage('sc.blogiccomponent.0013')+"<span class='component-name'></span>");
					break;
					
				case "14":
					var defaultDisplayFunction = "openModalNestedLogic(this)";
					$(newElement).attr("ondblclick", defaultDisplayFunction).attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<a class='qp-link-popup' href='"+CONTEXT_PATH +"/businessdesign/designBlogic' style='display:none;'></a><div style='border: 2px solid orange;padding: 3px;border-radius: 8px;'><img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/nestedlogic.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0126') + CLOSE_PARENTHESIS+"<span class='component-name'></span></div>").append(connect);
					break;
					
				case "15":
					$(newElement).addClass("bdesign-node-two");
					$(newElement).attr("ondblclick", "openModalFileOperation(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/common.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0125') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "16":
					$(newElement).addClass("bdesign-node-two");
					$(newElement).attr("ondblclick", "openModalReadFile(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/common.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0081') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "17":
					$(newElement).addClass("bdesign-node-two");
					$(newElement).attr("ondblclick", "openModalExportFile(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/common.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0026') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
					
				case "18":
					$(newElement).addClass("bdesign-node-two");
					$(newElement).attr("ondblclick", "openModalTransaction(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/advanced.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0127') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				case "19":
					$(newElement).addClass("bdesign-node-three");
					$(newElement).attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/end.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0128') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				case "20":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalEmail(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/email.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0143') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				case "21":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalLog(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/log.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0144') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				case "22":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalUtility(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/utility.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0148') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				case "23":
					$(newElement).addClass("bdesign-node-one");
					$(newElement).attr("ondblclick", "openModalDownloadFile(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/download.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0162') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				case "24":
					$(newElement).attr("ondblclick", "openModalException(this)").attr("style", "position: absolute; left: " + xPos + "px; top: " + yPos + "px;");
					$(newElement).append("<img src=\""+CONTEXT_PATH +"/resources/media/images/businessdesign/exception.png\" class=\"qp-bdesign-node-image\"/>&nbsp;"+OPEN_PARENTHESIS + $.qp.getModuleMessage('sc.blogiccomponent.0196') + CLOSE_PARENTHESIS+"<span class='component-name'></span>").append(connect);
					break;
				default:
					break;
			}
			$(newElement).attr("connectionTarget",connectionTarget);
			elementSetting = returnInformationComponent(newElement);
			$(newElement).append(elementSetting);
	
			// Add attribute release choice item for multi drag
			$(newElement).attr("add", "off");
			$(newElement).click(function() {
				compDragClick($(newElement),instanceOfBlogic);
			});
			
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
			instance.setIdChanged(oldId,newId);
			initEndPointForComponent(newElement,instance);
			initConnectionTargetForComponent($(newElement).attr("id"),componentType);
		}else{
			var strComponent = $(div).find("input[name='lstComponents']").val();
			var strConnector = $(div).find("input[name='lstConnectors']").val();
			var componentList = convertToJson(strComponent);
			var connectorList = convertToJson(strConnector);
			
			//load component
			var newTop = draggableOffsetTop;
			for(var i=0; i<componentList.length; i++){
				var componentId = componentList[i].patternedDetailId;
				var newIDdiv =  createNewComponent(event, div,null,componentList[i].componentType.toString(), draggableOffsetLeft, newTop, offset, containerId, instance);
				for(var ixConnector =0; ixConnector < connectorList.length; ixConnector++ ){
					if(connectorList[ixConnector].connectorSource==componentId){
						connectorList[ixConnector].connectorSource=newIDdiv;
					}
					if(connectorList[ixConnector].connectorDest==componentId){
						connectorList[ixConnector].connectorDest=newIDdiv;
					}
				} 

				newTop += 100;
			}
			//load connector
			for(var ixConnector =0; ixConnector < connectorList.length; ixConnector++ ){
				instance.connect({
					source : connectorList[ixConnector].connectorSource,
					target : connectorList[ixConnector].connectorDest,
					overlays: [
					           ["Label", {
					        	   location: 40,
					        	   label:connectorList[ixConnector].connectorType
					           }]
					           ]
				});
			}
		}
	
		// In the case all pop-up is disable
		// Remove all attribute ondbclick
		if (chkbxValue == "off") {
			$(container).find(".execution-class").each(function() {
				$(this).attr("bkondblclick", $(this).attr("ondblclick"));
				$(this).removeAttr("ondblclick");
			});
		}
		return newId;
	}
}


/*
 * register
 */
function returnInformationComponent(selectedComponent) {
	var type = $(selectedComponent).attr("componenttype");
	var element = "<input type=\"hidden\" name=\"componentElement\" " + "value='";
	var value= "";
	switch (type) {
		case "2":
			var executionNode = new Object();
			executionNode.label = "";
			executionNode.remark = "";
			executionNode.sqlDesignId = null;
			executionNode.sqlDesignIdAutocomplete = null;
			executionNode.sqlDesignCode = null;
			executionNode.concurrencyFlg = false;
			executionNode.parameterInputBeans = [];
			executionNode.parameterOutputBeans = [];
			if(typeof moduleIdOfBD != "undefined" && moduleIdOfBD != null){
				executionNode.moduleId = moduleIdOfBD;
				executionNode.moduleIdAutocomplete = moduleIdAutocompleteOfBD;
			}else{
				executionNode.moduleId = null;
				executionNode.moduleIdAutocomplete = null;
			}
			value = convertToString(executionNode);
			break;
		case "3":
			var validationNode = new Object();
			validationNode.label = "";
			validationNode.remark = "";
			validationNode.validationCheckDetails = [];
			value = convertToString(validationNode);
			break;
		case "4":
			var businessNode = new Object();
			businessNode.label = "";
			businessNode.remark = "";
			businessNode.businessCheckDetails = [];
			value = convertToString(businessNode);
			break;
		case "5":
			var decisionNode = new Object();
			decisionNode.label = "";
			decisionNode.remark = "";
			decisionNode.decisionTableId = null;
			decisionNode.decisionTableIdAutocomplete = null;
			decisionNode.decisionTableCode = null;
			decisionNode.parameterInputBeans = [];
			decisionNode.parameterOutputBeans = [];
			if(typeof moduleIdOfBD != "undefined" && moduleIdOfBD != null){
				decisionNode.moduleId = moduleIdOfBD;
				decisionNode.moduleIdAutocomplete = moduleIdAutocompleteOfBD;
			}else{
				decisionNode.moduleId = null;
				decisionNode.moduleIdAutocomplete = null;
			}
			value = convertToString(decisionNode);
			break;
		case "6":
			var advanceNode = new Object();
			advanceNode.label = "";
			advanceNode.remark = "";
			advanceNode.parameterInputBeans = [];
			advanceNode.parameterOutputBeans = [];
			value = convertToString(advanceNode);
			break;
		case "7":
			var commonNode = new Object();
			commonNode.label = "";
			commonNode.remark = "";
			commonNode.businessLogicId = null;
			commonNode.businessLogicIdAutocomplete = null;
			commonNode.businessLogicCode = null;
			commonNode.parameterInputBeans = [];
			commonNode.parameterOutputBeans = [];
			value = convertToString(commonNode);
			break;
		case "8":
			var assignNode = new Object();
			assignNode.label = "";
			assignNode.remark = "";
			assignNode.details = [];
			value = convertToString(assignNode);
			break;
		case "9":
			var ifNode = new Object();
			ifNode.label = "";
			ifNode.remark = "";
			
			var elseCondition = new Object();
			elseCondition.conditionRemark="";
			elseCondition.formulaDefinitionContent = "";
			elseCondition.formulaDefinitionDetails = [];
			elseCondition.usedConditionFlg = true;
			
			ifNode.ifConditionDetails = [elseCondition];
			value = convertToString(ifNode);
			break;
		case "10":
			var loopNode = new Object();
			loopNode.label = "";
			loopNode.remark = "";
			loopNode.loopType = 0;
			loopNode.parameterId = null;
			loopNode.parameterIdAutocomplete = null;
			loopNode.parameterScope = null;
			loopNode.fromValue = null;
			loopNode.fromScope = 3;
			loopNode.toValue = null;
			loopNode.toScope = 3;
			loopNode.formulaDefinitionId = null;
			loopNode.formulaDefinitionContent = null;
			loopNode.formulaDefinitionDetails = [];
			loopNode.index = "index"+$("#designArea").find("div.execution-class[componenttype='10']").length;
			value = convertToString(loopNode);
			break;
		case "11":
			var feedbackNode = new Object();
			feedbackNode.label = "";
			feedbackNode.remark = "";
			feedbackNode.type = "";
			feedbackNode.messageCode = "";
			feedbackNode.messageCodeAutocomplete = "";
			feedbackNode.messageParameter = [];
			value = convertToString(feedbackNode);
			break;
		case "12":
			var navigatorNode = new Object();
			navigatorNode.label = "";
			navigatorNode.remark = "";
			navigatorNode.navigatorToType = "0";
			navigatorNode.transitionType = "0";
			navigatorNode.parameterInputBeans = [];
			navigatorNode.navigatorToId = "";
			navigatorNode.navigatorToIdAutocomplete = "";
			if(typeof moduleIdOfBD != "undefined" && moduleIdOfBD != null){
				navigatorNode.moduleId = moduleIdOfBD;
				navigatorNode.moduleIdAutocomplete = moduleIdAutocompleteOfBD;
			}else{
				navigatorNode.moduleId = null;
				navigatorNode.moduleIdAutocomplete = null;
			}
			value = convertToString(navigatorNode);
			break;
		case "14":
			var nestedLogicNode = new Object();
			nestedLogicNode.label = "";
			nestedLogicNode.remark = "";
			var arrComponent = [];
			arrComponent.push({"sequenceLogicId":"",
				"xCoordinates":"379",
				"yCoordinates":"55",
				"sequenceNo":0,
				"sequenceLogicName":"",
				"sequenceLogicId":"",
				"componentType":"1",});
			arrComponent.push({"sequenceLogicId":"",
				"xCoordinates":"383",
				"yCoordinates":"422",
				"sequenceNo":1,
				"sequenceLogicName":"",
				"sequenceLogicId":"",
				"componentType":"13",});
			nestedLogicNode.arrComponent = arrComponent;
			value = convertToString(nestedLogicNode);
			break;
		case "16":
			var readFileNode = new Object();
			readFileNode.label = "";
			readFileNode.remark = "";
			readFileNode.sourcePathType = "0";
			readFileNode.sourcePathContent = "";
			readFileNode.sourcePathFormulaDetails = [];
			readFileNode.fileFormat = {
					"fileEncoding":"0",
					"lineFeedCharType":"0",
					"lineFeedChar":"",
					"delimiter":",",
					"encloseCharType":"0",
					"encloseChar":"",
					"headLineCount":"0",
					"trailerLineCount":"0",
					"overwriteFlg":false
					};
			value = convertToString(readFileNode);
			break;
		case "17":
			var exportFileNode = new Object();
			exportFileNode.label = "";
			exportFileNode.remark = "";
			exportFileNode.destinationPathType = "0";
			exportFileNode.destinationPathContent = "";
			exportFileNode.destinationPathFormulaDetails = [];
			exportFileNode.fileFormat = {
					"fileEncoding":"0",
					"lineFeedCharType":"0",
					"lineFeedChar":"",
					"delimiter":",",
					"encloseCharType":"0",
					"encloseChar":"",
					"headLineCount":"0",
					"trailerLineCount":"0",
					"overwriteFlg":false
					};
			value = convertToString(exportFileNode);
			break;
		case "18":
			var transactionNode = new Object();
			transactionNode.label = "";
			transactionNode.remark = "";
			transactionNode.transactionType = "0";
			value = convertToString(transactionNode);
			break;
		case "20":
			var emailNode = new Object();
			emailNode.label = "";
			emailNode.remark = "";
			emailNode.priorityType = "0";
			emailNode.emailRecipients = [];
			emailNode.subjectType = "0";
			emailNode.subjectContent = "";
			emailNode.subjectFormulaDetails = [];
			emailNode.emailContent = {
					"content":""
			};
			
			value = convertToString(emailNode);
			break;
		case "21":
			var logNode = new Object();
			logNode.label = "";
			logNode.remark = "";
			logNode.level = "0";
			logNode.messageType = "0";
			logNode.messageContent = "";
			logNode.messageFormulaDetails = [];
			value = convertToString(logNode);
			break;
		case "22":
			var utilityNode = new Object();
			utilityNode.label = "";
			utilityNode.remark = "";
			utilityNode.type = "0";
			utilityNode.targetId = null;
			utilityNode.targetIdAutocomplete = null;
			utilityNode.targetScope = null;
			utilityNode.parameterId = null;
			utilityNode.parameterIdAutocomplete = null;
			utilityNode.parameterScope = null;
			utilityNode.messageFormulaDetails = [];
			utilityNode.indexId = null;
			utilityNode.indexIdAutocomplete = null;
			utilityNode.indexScope = null;
			utilityNode.lstIndex = [];
			value = convertToString(utilityNode);
			break;
		case "23":
			var downloadNode = new Object();
			downloadNode.label = "";
			downloadNode.remark = "";
			downloadNode.parameterId = null;
			downloadNode.parameterIdAutocomplete = null;
			downloadNode.parameterScope = null;
			downloadNode.fileNameType = 0;
			downloadNode.fileNameContent = "";
			downloadNode.fileNameFormulaDetails = [];
			value = convertToString(downloadNode);
			break;
		case "24":
			var exceptionNode = new Object();
			exceptionNode.label = "";
			exceptionNode.remark = "";
			exceptionNode.exceptionToType = "0";
			exceptionNode.transitionType = "0";
			exceptionNode.parameterInputBeans = [];
			exceptionNode.exceptionToId = "";
			exceptionNode.exceptionToIdAutocomplete = "";
			if(typeof moduleIdOfBD != "undefined" && moduleIdOfBD != null){
				exceptionNode.moduleId = moduleIdOfBD;
				exceptionNode.moduleIdAutocomplete = moduleIdAutocompleteOfBD;
			}else{
				exceptionNode.moduleId = null;
				exceptionNode.moduleIdAutocomplete = null;
			}
			value = convertToString(exceptionNode);
			break;
		default:
			break;
	}
	element += value+"'>";
	return element;
}
function initEndPointForComponent(obj,instance){
	var type = $(obj).attr("componenttype");
	var maxConnectionSource = 1;
	var maxConnectionTarget = 1;
	switch (type) {
		case "9":
			maxConnectionTarget = 3;
			maxConnectionSource = 3;
			break;
		case "10":
			maxConnectionTarget = 2;
			maxConnectionSource = 2;
			break;

		default:
			break;
	}
	// Component End is not a source
	if ("13" != type) {
		instance.makeSource(obj, {
			filter : ".ep",
			anchor : "Continuous",
//			maxConnections : 1,
//			onMaxConnections : function(info, e) {
//				alert("Maximum connections (" + info.maxConnections + ") reached");
//			},
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

	// Component Start is not a target
	if ("1" != type) {
		instance.makeTarget(obj, {
			dropOptions : {
				hoverClass : "dragHover"
			},
			anchor : "Continuous",
			allowLoopback : false
//			maxConnections : maxConnectionTarget,
//			onMaxConnections : function(info, e) {
//				alert("Maximum connections (" + info.maxConnections + ") reached");
//			}
		});
	}
}
function initConnectionTargetForComponent(id,type){
	switch (type.toString()) {
	case "9":
		var value = $("#"+id).find("input[name='componentElement']").val();
		var data = convertToJson(value);
		var arrStatus = new Array();
		if(data.conditions != undefined && data.conditions != ""){
			var arrConditions = data.conditions.split("�");
			if(arrConditions.length <=2){
				arrStatus = [{type:"TRUE", status:false, connectionId:""},{type:"FALSE", status:false, connectionId:""}];
			}else{
				for(var i =0;i<arrConditions.length;i++){
					if(arrConditions[i] != ""){
						var dataParameter = arrConditions[i].split("π");
						var connection = new Object();
						connection.type = i + "."+dataParameter[0];
						connection.status = false;
						arrStatus.push(connection);
					}
				}
			}
		}else{
			arrStatus = [{type:"TRUE", status:false, connectionId:""},{type:"FALSE", status:false, connectionId:""}];
		}
		arrStatus.push({type:"", status:false, connectionId:""});
		arrConnectionComponent[id] = arrStatus;
		break;
	case "10":
		arrConnectionComponent[id] = [{type:"CYCLE", status:false, connectionId:""},{type:"", status:false, connectionId:""}];
		break;
	case "13":
		arrConnectionComponent[id] = [];
		break;

	default:
		arrConnectionComponent[id] = [{type:"", status:false, connectionId:""}];
		break;
	}
}
function calcLabelForConnection(componenttype,info){
	var returnConnection = new Object();
	returnConnection.validFlag = false;
	var arrStatus = arrConnectionComponent[info.sourceId];
	for(var i=0;i<arrStatus.length;i++){
		if(!arrStatus[i].status){
			returnConnection.label = arrStatus[i].type;
			returnConnection.validFlag = true;
			arrStatus[i].status = true;
			break;
		}
	}
	arrConnectionComponent[$(info.sourceid)] = arrStatus;
	return returnConnection;
}
function setStatusConnection(info,label,status){
	var componentType = parseInt($(info.source).attr("componenttype"));
	var arrStatus = arrConnectionComponent[info.sourceId];
	switch (componentType) {
	case 9:
		for(var i=0;i<arrStatus.length;i++){
			if(label.trim()  == arrStatus[i].type.trim()){
				arrStatus[i].status = status;
				break;
			}
		}
		break;
	default:
		for(var i=0;i<arrStatus.length;i++){
			if(label.trim() == arrStatus[i].type.trim()){
				arrStatus[i].status = status;
				break;
			}
		}
		break;
	}
	arrConnectionComponent[info.sourceId] = arrStatus;
}
function getMaxPositionOfItemsBD(){
//	var defaultHeight = $("#designArea").closest("tr").outerHeight();
	var defaultHeight = 715;
	var defaultWidth = $("#designArea").closest("td").outerWidth() -12;
	var sizeExpand = 250;
	var tempMaxTop = 0;
	var tempMaxLeft = 0;
	var position = new Object();
	$("#designArea").find("div.execution-class").each(function (){
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
function getLabelOfIfNode(source,currentLabel){
	var strData = $(source).find("input[name='componentElement']").val();
	var label = "";
	if(strData != undefined){
		var data = convertToJson(strData);
		var ifConditionDetails = data.ifConditionDetails;
		if(ifConditionDetails == null || ifConditionDetails.length == 0){
			alert($.qp.getModuleMessage('err.blogiccomponent.0141'));
			return;
		}
		else if(ifConditionDetails.length <= 2){
			switch (currentLabel) {
			case "TRUE":
				label = "FALSE";
				break;
			case "FALSE":
				label = "";
				break;
			default:
				label = "TRUE";
				break;
			}
		}else{
			if(currentLabel == ""){
				label = 1 + "."+ifConditionDetails[0].caption;
			}else{
				var index = -1;
				for (var i = 0; i < ifConditionDetails.length; i++) {
					var temp = (i+1) + "."+ifConditionDetails[i].caption;
					if(temp == currentLabel){
						index = i+1;
					}
				}
//				if(index >= ifConditionDetails.length){
//					label = "None of the above";
//				}else if(index == -1){
//					label = "";
//				}else{
//					label = (index +1) + "."+ifConditionDetails[index].caption;
//				}
				if(index >= ifConditionDetails.length || index == -1){
					label = "";
				}else{
					label = (index +1) + "."+ifConditionDetails[index].caption;
				}
			}
			
			
		}
	}
	return label;
}
function getLabelOfLoopNode(source,currentLabel){
	var label = "";
	switch (currentLabel) {
	case "CYCLE":
		label = "";
		break;
	default:
		label = "CYCLE";
		break;
	}
	return label;
}