var instance;
jsPlumb.ready(function() {
    // setup some defaults for jsPlumb.
    
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
    	
    });

    instance.bind("beforeDrop", function(info) {
    	var sourceId = info.sourceId;
    	var targetId = info.targetId;
    	
    	var url = CONTEXT_PATH + "/screendesign/checkTransition?sourceId="+sourceId+ "&targetId=" + targetId +"&r="+Math.random();	
    	
    	var result = $.qp.getString(url);
    	
    	var data = convertToJson(result);
    	if (!data.status) {
    		alert(data.message);
    		return false;
    	}
    	var allConnection = instance.getAllConnections();
    	var isValid = true;
    	for (var i = 0; i < allConnection.length; i++) {
    		if (allConnection[i]["sourceId"] == sourceId && allConnection[i]["targetId"] == targetId) {
    			isValid = false;
    			break;
    		}
    	}
    	
    	/*if (!isValid) {
    		alert(dbMsgSource['sc.screendesign.0277']);
    		return false;
    	}*/
    	
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
			maxConnections : 5,
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
		var screenActionId = "";
		//instance.reset();
		instance.clear();
		for (var i = 0; i < allInstanceConnect.length; i++) {
			screenActionId = allInstanceConnect[i].screenActionId;
			var c = instance.connect({
				source : allInstanceConnect[i].source,
				target : allInstanceConnect[i].target,
				overlays: [
				           ["Label", {
				        	   location: 40,
				        	   label:allInstanceConnect[i].label
				           }]
				           ]
			});
			$(c).attr('id', screenActionId);
		}
	});
    
    jsPlumb.fire("screenTransitionLoad", instance);
});

function openScreenDesign(screenId){
    var url = "/screendesign/design?r="+Math.random()+"&screenId=" + screenId;
    location.href = CONTEXT_PATH+url;
}

function saveScreenPosition(target){
	
	var arrConnection = new Array();
	var arrAllConnection = instance.getAllConnections();
	for(var i=0;i<arrAllConnection.length;i++){
		var connection = new Object();
		connection.connectorSource = arrAllConnection[i].sourceId;
		connection.connectorDest = arrAllConnection[i].targetId;
		connection.screenActionId = arrAllConnection[i].id;
		arrConnection.push(connection);
	}
	var jsonConnectorParam =  convertToString(arrConnection);
	var jsonInfo = $("input[name=jsonInfo").val();
	
	var screenPosition = "";
	    $("#transition-area").find("div.transition-class").each(function (i){
	        var screenId = $(this).attr("id");
	        //screenId = screenId.substring(6, screenId.length);
	        var position = $(this).position();
	        var left = Math.round(position.left);
	        var top = Math.round(position.top);
	        // Append string
	        screenPosition += "{\"screenId\":\""+screenId+"\",\"xCoordinate\":\""+left+"\",\"yCoordinate\":\""+top+"\"};";
	    });
	    
	    screenPosition = screenPosition.substring(0, screenPosition.length - 1);
	
	    
	var form = $(target).closest("form");   
	
	$(form).find("input[name=parameters]").val(screenPosition);
	$(form).find("input[name=jsonConnector]").val(jsonConnectorParam);
	$(form).find("input[name=jsonInfo]").val(jsonInfo);
	
	var url = CONTEXT_PATH + "/screendesign/saveTransition";
	
	$(form).attr("action", url);
	return true;
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



