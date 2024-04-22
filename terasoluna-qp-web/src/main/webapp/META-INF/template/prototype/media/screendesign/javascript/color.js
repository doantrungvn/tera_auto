var colorScreenDesign = [];
colorScreenDesign.push("00FFFF")
colorScreenDesign.push("FFEBCD")
colorScreenDesign.push("0000FF")
colorScreenDesign.push("8A2BE2")
colorScreenDesign.push("7FFF00")
colorScreenDesign.push("D2691E")
colorScreenDesign.push("FF7F50")
colorScreenDesign.push("6495ED")
colorScreenDesign.push("DC143C")
colorScreenDesign.push("00008B")
colorScreenDesign.push("008B8B")
colorScreenDesign.push("B8860B")
colorScreenDesign.push("BDB76B")
colorScreenDesign.push("8B008B")
colorScreenDesign.push("556B2F")
colorScreenDesign.push("FF8C00")
colorScreenDesign.push("9932CC")
colorScreenDesign.push("8B0000")
colorScreenDesign.push("E9967A")
colorScreenDesign.push("8FBC8F")
colorScreenDesign.push("483D8B")
colorScreenDesign.push("2F4F4F")
colorScreenDesign.push("00CED1")
colorScreenDesign.push("9400D3")
colorScreenDesign.push("FF1493")
colorScreenDesign.push("00BFFF")
colorScreenDesign.push("696969")
colorScreenDesign.push("1E90FF")
colorScreenDesign.push("B22222")
colorScreenDesign.push("228B22")
colorScreenDesign.push("FF00FF")
colorScreenDesign.push("FFD700")
colorScreenDesign.push("DAA520")
colorScreenDesign.push("808080")
colorScreenDesign.push("FF69B4")
colorScreenDesign.push("CD5C5C")
colorScreenDesign.push("4B0082")
colorScreenDesign.push("F0E68C")
colorScreenDesign.push("ADD8E6")
colorScreenDesign.push("F08080")
colorScreenDesign.push("FFB6C1")
colorScreenDesign.push("FFA07A")
colorScreenDesign.push("20B2AA")
colorScreenDesign.push("87CEFA")
colorScreenDesign.push("778899")
colorScreenDesign.push("00FF00")
colorScreenDesign.push("FF00FF")
colorScreenDesign.push("800000")
colorScreenDesign.push("66CDAA")
colorScreenDesign.push("0000CD")
colorScreenDesign.push("BA55D3")
colorScreenDesign.push("9370DB")
colorScreenDesign.push("3CB371")
colorScreenDesign.push("7B68EE")
colorScreenDesign.push("48D1CC")
colorScreenDesign.push("C71585")
colorScreenDesign.push("191970")
colorScreenDesign.push("FFE4B5")
colorScreenDesign.push("808000")
colorScreenDesign.push("6B8E23")
colorScreenDesign.push("FFA500")
colorScreenDesign.push("FF4500")
colorScreenDesign.push("DA70D6")
colorScreenDesign.push("98FB98")
colorScreenDesign.push("DB7093")
colorScreenDesign.push("CD853F")
colorScreenDesign.push("FFC0CB")
colorScreenDesign.push("DDA0DD")
colorScreenDesign.push("B0E0E6")
colorScreenDesign.push("800080")
colorScreenDesign.push("FF0000")
colorScreenDesign.push("BC8F8F")
colorScreenDesign.push("8B4513")
colorScreenDesign.push("FA8072")
colorScreenDesign.push("F4A460")
colorScreenDesign.push("2E8B57")
colorScreenDesign.push("FFF5EE")
colorScreenDesign.push("A0522D")
colorScreenDesign.push("C0C0C0")
colorScreenDesign.push("6A5ACD")
colorScreenDesign.push("708090")
colorScreenDesign.push("4682B4")
colorScreenDesign.push("D2B48C")
colorScreenDesign.push("D8BFD8")
colorScreenDesign.push("FF6347")
colorScreenDesign.push("EE82EE")
colorScreenDesign.push("FFFF00")


function getColor(index) {
	var item = "#" + colorScreenDesign[index];
	return item
}
function previewDialog(obj) {
	
	
	$(".form-area-content").find("table").each(function (i){ 
		$(this).find("td:not(td[class*='srcgenControl'])").each(function(){
			var totalElement = 0;
			$(this).children().each(function(){
				if ($(this).prop("tagName") == "DIV" || $(this).prop("tagName") == "SPAN" && !$(this).is(".glyphicon-screenshot")) {
					totalElement++;
				}
			});
			$(this).find("input[name=groupTotalElement]").val(totalElement);
		});
		var divParent = $(this).closest("div[class*=panel-body]").prev();
		if($(this).attr("class") == "table table-bordered qp-table-form"){
			var tds = $(this).find("input[name=formElement]").length;
			var cols = ($(this).find("tr:first").find("td").length - 2) * 2;
			
			$(divParent).find("input[name=formElementTable]").val(cols+","+tds);
			
			var totalGroup = $(this).find("input[name=groupTotalElement]").length;
			$(divParent).find("input[name=formTotalGroup]").val(totalGroup);
			// set width
			if($(divParent).find("input[name=formTableColumnSize]").val() == undefined || $(divParent).find("input[name=formTableColumnSize]").val().length == 0){
				var width = "";
				for(y=0;y<$(this).find("tr:eq(1)").children().length - 2; y++){
					if($(this).find("tr:eq(1)").children().eq(y).attr("width") != undefined){
						var widthUnit = $(this).find("tr:eq(1)").children().eq(y).attr("width");
						if(widthUnit == ""){
							widthUnit = parseInt(100/(cols*2)) + "%";
						}
						width += widthUnit;
					}
					width += ",";
				}
				$(divParent).find("input[name=formTableColumnSize]").val(width.substring(0,width.length - 1));
			}
		} else {
			var tds = $(this).find("input[name=formElement]").length;
			var cols = $(this).find("tr:last").find("td").length;
			$(divParent).find("input[name=formElementTable]").val(cols+","+tds);
			
			var totalGroup = $(this).find("input[name=groupTotalElement]").length;
			$(divParent).find("input[name=formTotalGroup]").val(totalGroup);
			
			// set width
			if($(divParent).find("input[name=formTableColumnSize]").val() == undefined || $(divParent).find("input[name=formTableColumnSize]").val().length == 0){
				var width = "";
				for(y=0;y<$(this).find("tr:eq(0)").find("th").length; y++){
					if($(this).find("tr:eq(0)").find("th:eq("+y+")").attr("width") != undefined){
						var widthUnit = $(this).find("tr:eq(0)").find("th:eq("+y+")").attr("width");
						if(widthUnit == ""){
							widthUnit = parseInt(100/cols) + "%";
						}
						width += widthUnit;
					}
					width += ",";
				}
				$(divParent).find("input[name=formTableColumnSize]").val(width.substring(0,width.length - 1));
			}
		}
	});
	$(".form-area-content").find(".section-area,.action-area").each(function (i){
		var cols = $(this).find("input[name=formElement]").length;
		$(this).closest(".areaContent").find("input[name=formElementTable]").val(cols+","+cols);
	});
	
	var headerLinkLength = $("#srcgenHeaderLinkPanel").find("input[name=formElement]").length;
	$("#srcgenHeaderLinkPanel").find("input[name=formElementTable]").val("0," + headerLinkLength);
	$("#srcgenHeaderLinkPanel").find("input[name=formTotalGroup]").val("0");
	
	//$(obj).after("<input type=\"hidden\" name=\"preview\" value=\"true\">");
	//$(obj).closest('form').submit();
	
    var postData = $('form[name=screenDesignForm]').serializeArray();
    var formURL = CONTEXT_PATH + "/screendesign/previewTemp";
    $.ajax(
    {
        url : formURL,
        type: "POST",
        data : postData,
        success:function(data, textStatus, jqXHR) 
        {
        	var href = CONTEXT_PATH + "/screendesign/preview";
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
