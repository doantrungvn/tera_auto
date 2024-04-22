$.qp.advancedsql = (function($$module){
	$$module.defaultSchema = {
			//"!top": ["select","update","delete","insert","sql","cache","cache-ref","parameterMap","resultMap"],
			"!top":["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>","selectKey></selectKey>"],
			"!attrs": null,
			"select": {
				attrs: {
					id:null,
					parameterType:null,
					resultMap:null,
					databaseId:null,
					fetchSize:null,
					flushCache:null,
					lang:null,
					parameterMap:null,
					resultOrdered:null,
					resultSets:null,
					resultSetType:null,
					resultType:null,
					statementType:null,
					timeout:null,
					useCache:null
				},
				children: ["![CDATA[]]>","bind","choose","foreach","if","include","set","trim","where"]
			},
			"update": {
				attrs: {
					id:null,
					databaseId:null,
					flushCache:null,
					keyColumn:null,
					keyProperty:null,
					lang:null,
					parameterMap:null,
					parameterType:null,
					statementType:null,
					timeout:null,
					useGeneratedKeys:null
				},
				children: ["![CDATA[]]>","bind","choose","foreach","if","include","set","trim","where","selectKey"]
			},
			"insert" :{
				attrs:{
					id:null,
					databaseId:null,
					flushCache:null,
					keyColumn:null,
					keyProperty:null,
					lang:null,
					parameterMap:null,
					parameterType:null,
					statementType:null,
					timeout:null,
					useGeneratedKeys:null
				},
				children: ["![CDATA[]]>","bind","choose","foreach","if","include","set","trim","where","selectKey"]
			},
			"selectKey":{
				attrs:{
					"keyProperty":null,
					"resultType":null,
					"order":null,
					"databaseId":null,
					"keyColumn":null,
					"statementType":null
				},
				children: ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"]
			},
			"delete" :{
				attrs:{
					id:null,
					databaseId:null,
					flushCache:null,
					lang:null,
					parameterMap:null,
					parameterType:null,
					statementType:null,
					timeout:null
				},
				children: ["![CDATA[]]>","bind","choose","foreach","if","include","set","trim","where"]
			},
			"sql":{
				attrs : {
					id:null,
					databaseId:null,
					lang:null
				},
				children: ["![CDATA[]]>","bind","choose","foreach","if","include","set","trim","where"]
			},
			"cache":{
				attrs:{
					blocking:null,
					eviction:null,
					flushInterval:null,
					readOnly:null,
					size:null,
					type:null
				},
				children:[]
			},
			"cache-ref":{
				attrs:{
					namespace:null
				}
			},
			"resultMap":{
				attrs:{
					type:null,
					id:null,
					autoMappings:null,
					extends:null
				},
				children:[]
			},
			"parameterMap":{
				attrs:{
					id:null,
					type:null
				},
				children:[]
			},
			"bind":{
				attrs:{
					name : null,
					value: null
				},
				children : []
			},
			"choose":{
				attrs:{},
				children : ["when></when>","otherwise></otherwise>"]
			},
			"when":{
				attrs:{
					test :null
				},
				children : []
			},
			"foreach":{
				attrs:{
					item:null,
					index:null,
					collection:null,
					open:null,
					separator:null,
					close:null
				},
				children: ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"]
			},
			"if":{
				attrs:{
					test : null
				},
				children: ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"]
			},
			"include":{
				attrs:{
					refid : null
				},
				children: ["property></property>"]
			},
			"property":{
				attrs:{
					name:null,
					value:null
				},
				children:[]
			},
			"set":{
				attrs:{},
				children: ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"]
			},
			"trim":{
				attrs:{
					prefix : null,
					prefixOverrides:null,
					suffix:null,
					suffixOverrides:null
				},
				children: ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"]
			},
			"where":{
				attrs:{},
				children: ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"]
			}
		};
	var CODE_PAGEABLE = '<if test="pageable.sort != null">'+"\n"+
					'ORDER BY'+"\n"+
				 	'<foreach collection="pageable.sort" item="sort" separator=",">'+"\n"+
				 		'${sort.property} ${sort.direction}'+ "\n"+
				 	'</foreach>'+ "\n"+
				'</if>'+ "\n"+
				'<![CDATA['+ "\n"+		    	
					   'LIMIT'+ "\n"+
						   '#{pageable.pageSize}'+ "\n"+
					   'OFFSET'+ "\n"+
						   '#{pageable.offset}' + "\n"+
				 ']]>';
	var CODE_PAGEABLE_ORACLE =    "WHERE" + "\n" +
								  "<![CDATA["  + "\n" +
								   "rn BETWEEN  (#{pageable.offset} +1) AND (#{pageable.offset} + #{pageable.pageSize})" + "\n" +     
								  "]]>"
	var DATABASE_TYPE = {
		postgre_sql : 1,
		oracle : 2	
	};
	var OUTPUT_TAB = "[Output]";
	var INPUT_TAB = "[Input]";
	$$module.limitRowsInputAutocomplte = function(){
		var divAutocomplete = $("div#qp-autocomplete");
		if(divAutocomplete.length == 0){
			return;
		}
		var inputTbl = $(divAutocomplete).find("table#inputForm");
		var maxRows = $(inputTbl).attr("max-rows");
		var rows = $(inputTbl).find("tbody tr");
		if(rows.length >= maxRows){
			$(inputTbl).next().hide();
		}else{
			$(inputTbl).next().show();
		}
	}
	$$module.inputFormCallback = function(table,direction,row){
		$$module.limitRowsInputAutocomplte();
		if(direction=="add"){
			$.qp.common.restyleRow(row);
			$(row).find("select[name$='.dataType']").val("9");
			//set type autocomplete
			var dataArRindex = $(row).attr("data-ar-rindex");
			var typeAuto = dataArRindex > 0 ? dbMsgSource["sc.sqldesign.0057"] : dbMsgSource["sc.sqldesign.0056"];
			$(row).find("td.autocomleteType").html(typeAuto);
		}else if(direction="remove"){
			//set type autocomplete
			var tr = $(table).find("tbody > tr").first()
			var typeAuto = dbMsgSource["sc.sqldesign.0056"];
			$(tr).find("td.autocomleteType").html(typeAuto);
		}
		$("#qp-autocomplete #inputForm").find("select[name$='.dataType'] option[value='0'],select[name$='.dataType'] option[value='14'],select[name$='.dataType'] option[value='16'],select[name$='.dataType'] option[value='17']").remove();
		
		var $InputForm = $("#inputForm");
	};
	$$module.outputFormCallback = function(table,direction,row){
		if(direction=="add"){
			$.qp.common.restyleRow(row);
			$(row).find("select[name$='.dataType']").val("9");
		}
	};
	$$module.initSqlPattern = function(){
		var $sqlPatternSelect = $("#qp-autocomplete [id='sqlDesignForm.sqlPattern'], #qp-viewdesign [id='sqlDesignForm.sqlPattern']").first();
		$sqlPatternSelect.find("option:gt(0)").remove();
		$sqlPatternSelect.after("SELECT");
		$sqlPatternSelect.hide();
		
	};
	$$module.insertSqlPattern = function(patternId,target){
		var $target = $(target);
		
		$target.focus();
	};
	$$module.insertTableInfo = function(tableInfo,target){
		var $target = $(target);
		var sqlPattern = $("[id='sqlDesignForm.sqlPattern']").val();
		var sqlText = "";
		
		switch(sqlPattern){
		case "0":
			var columns = [];
			$.each(tableInfo.output03.split(','),function(){
				var columnInfos = this.split(":");
				columns.push('t1.' + columnInfos[2]);
			});
			sqlText +="SELECT "+columns.join(',');
			sqlText +="\nFROM "+tableInfo.output01 + ' t1';
			break;
		case "1":
			var columns = [];
			$.each(tableInfo.output03.split(','),function(){
				var columnInfos = this.split(":");
				columns.push(columnInfos[2]);
			});
			sqlText +="INSERT INTO "+tableInfo.output01+"("+columns.join(',')+")";
			sqlText +="\nVALUES(";
			for(var i=0;i<tableInfo.output02;i++){
				if(i>0){
					sqlText += ", "
				}
				sqlText += "?";
			}
			sqlText += ")";
			
			break;
		case "2":
			var columns = [];
			$.each(tableInfo.output03.split(','), function(){
				var columnInfos = this.split(":");
				columns.push(columnInfos[2]);
			});
			
			sqlText +="UPDATE "+tableInfo.output01
			sqlText +="\nSET ";
			sqlText+="\n\t";
			sqlText +=columns.join(' = ?,\n\t');
			sqlText += ' = ?';
			break;
		case "3":
			sqlText += "DELETE FROM "+tableInfo.output01;
			var columns = [];
			if(tableInfo.output04){
				$.each(tableInfo.output04.split(','), function(){
					var columnInfos = this.split(":");
					columns.push(columnInfos[2]);
				});
				sqlText += "\nWHERE";
				sqlText+="\n\t";
				sqlText+= columns.join('= ?,\n\tAND\n\t');
				sqlText+= '= ?';
			}
			break;
		}
		//$$module.insertAtCaret($target[0],sqlText);
		if(sqlPattern == '0'){
			$$module.editorDoc.replaceSelection(sqlText,"around");
		} else {
//			$("#sqlDesignConfirmDialog")
			if($$module.editorDoc.getValue().length >0){
				$$module.showConfirmationDialog(fcomMsgSource['inf.sys.0038'],function(){
					$$module.editorDoc.setValue(sqlText);
				});
			}else{
				$$module.editorDoc.setValue(sqlText);
			}
		}
		$target.focus();
	};
	$$module.insertFunctionCode = function(functionText,target){
		var checkPageable = $("div#generatePageable").is(":visible");
		if(functionText == 'PAGEABLE' && checkPageable){
			return;
		}
		if(functionText == 'PAGEABLE' && !checkPageable){
			$$module.addPageableSqlDesign();
			return;
		}
		var $target = $(target);
		//$$module.insertAtCaret($target[0],functionText);
		$$module.editorDoc.replaceSelection(functionText,"around");
		$target.focus();
	};
	$$module.insertAtCaret = function(textArea, text) {
		var scrollPos = textArea.scrollTop;
		var caretPos = textArea.selectionStart;

		var front = (textArea.value).substring(0, caretPos);
		var back = (textArea.value).substring(textArea.selectionEnd, textArea.value.length);
		textArea.value = front + text + back;
		caretPos = caretPos + text.length;
		textArea.selectionStart = caretPos;
		textArea.selectionEnd = caretPos;
		textArea.focus();
		textArea.scrollTop = scrollPos;
	};
	
	$$module.addPageableSqlDesign = function(){
		$("#txtPageableCode").val("");
		$("#txtPageableCode").val(CODE_PAGEABLE);
		if(DATABASE_TYPE.oracle == DB_TYPE_PROJECT){
			$$module.addPaginationForOracle();
		}
		$("div#generatePageable").show();
		$("input[type=hidden][name='sqlDesignForm.pageable']").val(1);
		
		var textPageAble = document.getElementById("txtPageableCode");
		$$module.aplyCodeMirror(textPageAble);
		if(DATABASE_TYPE.oracle == DB_TYPE_PROJECT){
			$("#generatePageable>.CodeMirror").css("height","81px");
		}
	}
	
	$$module.addPaginationForOracle = function(){
		$("#lblPageableCodeOracle").text("SELECT * ( \n");
		$("#lblPageableCodeOracle2").text(")");
		$("#txtPageableCode").val(CODE_PAGEABLE_ORACLE);
		$("#txtPageableCode").attr("rows",4)
		$("#lblPageableCodeOracle").show();
		$("#lblPageableCodeOracle2").show();
	}
	
	$$module.aplyCodeMirror = function(textArea){
		var mirror = CodeMirror.fromTextArea(textArea, {
		    lineNumbers: true,
		    mode: "text/mybatis-xml",
		    readOnly: true,
		    lineWrapping: true,
		    
		});
	}
	
	$$module.removePageable = function(){
		if($("div#generatePageable").is(":hidden")){
			return;
		}
		$("div#generatePageableOracle").hide();
		$("div#generatePageable").hide();
		$("#txtPageableCode").val("");
		$("#txtPageableCodeOracle").val("");
		$("input[type=hidden][name='sqlDesignForm.pageable']").val(0);
		$("div#generatePageable>.CodeMirror").remove();
		$("#lblPageableCodeOracle").hide();
		$("#lblPageableCodeOracle2").hide();
	}
	
	
	$$module.initAdvancedSqlEditor = function(){
		var tablesHint = {
				"users": {name : null, score : null, birthDate : null},
				"countries" : {name: null, population: null, size: null},
				"company" : {name: null, employers: null, place: null}
		}
		
		function completeAfter(cm, pred) {
			var cur = cm.getCursor();
			if (!pred || pred()) setTimeout(function() {
				if (!cm.state.completionActive)
				cm.showHint({completeSingle: false});
			}, 100);
			return CodeMirror.Pass;
		}

		function completeIfAfterLt(cm) {
			return completeAfter(cm, function() {
				var cur = cm.getCursor();
				return cm.getRange(CodeMirror.Pos(cur.line, cur.ch - 1), cur) == "<";
			});
		}

		function completeIfInTag(cm) {
			return completeAfter(cm, function() {
			var tok = cm.getTokenAt(cm.getCursor());
			if (tok.type == "string" && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1)) return false;
			var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
			return inner.tagName;
			});
			}
		var mime = "text/mybatis-xml"//'text/x-sql';//
		if(!document.getElementById('sqlDesignForm.sqlText')){
			return;
		}
		$$module.editorDoc = CodeMirror.fromTextArea(document.getElementById('sqlDesignForm.sqlText'), {
			mode: mime,
			indentWithTabs: true,
			smartIndent: true,
			lineNumbers: true,
			matchBrackets : true,
			autofocus: true,
			extraKeys: {
				// "'<'": completeAfter,
				//	"'/'": completeIfAfterLt,
					"' '": completeIfInTag,
					"'='": completeIfInTag,
				"Ctrl-Space": "autocomplete"
					},
			hintOptions: 
				{
					tables : function(){ return $$module.buildTableList()},
					disableKeywords: true,
					schemaInfo: function(){
						if($("[id='sqlDesignForm.sqlPattern']").val()== "1" || $("[id='sqlDesignForm.sqlPattern']").val()== "1"){
							$$module.defaultSchema["!top"] = ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>","selectKey></selectKey>"];
						} else {
							$$module.defaultSchema["!top"] = ["![CDATA[]]>","bind name=\"\" value=\"\"></bind>","choose></choose>","foreach collection=\"\"></foreach>","if test=\"\"></if>","include refid=\"\"></include>","set></set>","trim></trim>","where></where>"];
						}
						return $$module.defaultSchema;
					}
				},
			readOnly:	$("[id='sqlDesignForm.sqlText']").is("[readonly=readonly]")?"nocursor":""
		});
		$$module.editorDoc.on("blur",$$module.sqlTextChange);
		// Edit style
		$('.CodeMirror').css('text-align', 'left');
		$('.CodeMirror-cursor').css('left','0px');
	};
	$$module.sqlTextChange = function(doc){
		var sqlText = doc.getValue();
		var selectIndex = sqlText.search(/select/i);
		var fromIndex = sqlText.search(/from/i);
		if(selectIndex>-1 && fromIndex>-1){
			var columns = $.trim(sqlText.substring(selectIndex+6,fromIndex-1)).split(",");
			$("#qp-autocomplete #outputForm input[type=text][name$=Column]").val('');
			$("#qp-autocomplete #outputForm input[type=text][name$=Column]").slice(0,columns.length).each(function(){
				$(this).val($.trim(columns.shift().split(/as/i).pop()));
			});
			
			$("#qp-sqldesign #outputForm>tbody>tr").each(function(){
				$(this).find("input[type=text][name$=Column]").val($.trim(columns.shift().split(/as/i).pop()));
			});
		}
	};
	$$module.buildTableList = function(){
		var href = CONTEXT_PATH + "/autocomplete/getTableColumnCode?r="+Math.random();
		var tables={};
		$.ajax({
			method : "GET",
			url : href,
			async : false,
			dataType:'json',
			success : function(result,status,jqXHR){
				for(var i=0;i<result.length;i++){
					$.qp.common.namespace(result[i].tableDesignCode+"."+result[i].code,tables);
				}
			},
			error : function(jqXHR,status){
				
			}
		})
		
		$("[id='sqlDesignForm.sqlText']").closest(".tab-content").find("#tab-input #inputForm").find("tbody tr").find("input[name$='.sqlDesignInputCode']").each(function(){
			$.qp.common.namespace('in.' + $(this).val(),tables);
		});
		
		return tables;
	};
	$$module.initConfirmDialog = function(){
		 var top = Math.round(screen.height / 4);
			top = top > 0 ? top : 0;
			$("#sqlDesignConfirmDialog").find('.modal-content').css("margin-top", top);
			$('#sqlDesignConfirmDialog').off();
	};
	$$module.showConfirmationDialog = function(message,callback){
		$("#sqlDesignConfirmYes").on("click", function(e) {
			if(typeof callback=="function"){
				callback();
			}
			$("#sqlDesignConfirmYes").off("click");
		});
		$("#sqlDesignConfirmDialog .modal-body").html(message);
		$("#sqlDesignConfirmDialog").modal({ 
				show: true,
				closable: false,
				keyboard:false,
				backdrop:'static'
		});
		$("#sqlDesignConfirmDialog #sqlDesignConfirmYes").focus();
	};
	$$module.validateBeforeSubmit = function(){
		var message = $.qp.common.validation.validateTypeReturnOfOutput();
		if(message != ""){
			$.qp.showAlertModal(message);
			return false;
		}
		return true;
		var messages = $.qp.common.validation.validateSqlText($.trim($$module.editorDoc.getValue()),$("[id='sqlDesignForm.sqlPattern']").val());
		//		messages = messages.concat($.qp.common.validation.validateInputForm());
//		messages = messages.concat($.qp.common.validation.validateOutputForm());
		if(messages.length>0){
//			var errors = $("form").find(".qp-error")[0] || $("<div class='alert qp-error'>");
//			errors = $(errors).empty();
//			$.each(messages,function(key,value){
//				errors.append(value);
//				errors.append($("<br>"));
//			});
//			$("form").prepend(errors);
			$.qp.showAlertModal(messages.join("\n"));
			$$module.editorDoc.focus();
			return false;
		}
		
		return true;
	};
	$$module.initOutputForm = function(){
		$("[id^='outputForm'] select[name$='dataType']").each(function(){
			var type = $(this).val();
			if (type == '0') {
				$(this).closest("td").next("td").find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
			}
		});
		
	};
	$$module.initPageableSqlAd = function(){
		if(typeof DB_TYPE_PROJECT == 'undefined'){
			$$module.removePageable();
			$$module.hidePageableInFunctionCode();
			return;
		}
		var pageable = $("input[type=hidden][name='sqlDesignForm.pageable']").val();
		if(pageable == 1){
			$$module.addPageableSqlDesign();
		}
		var patternValue = $("#sqlEditForm select[name$='.sqlPattern']").val();
		$$module.showHidePageableBySqlPatterValue(patternValue);
	}
	
	
	
	$$module.changeSqlPattern = function(thiz){
		var val = $(thiz).val();
		$$module.showHidePageableBySqlPatterValue(val);
		$.qp.common.initOuputForInsertUpdateDelete();
	}
	
	$$module.showHidePageableBySqlPatterValue = function(val){
		if(!!val && val != 0){
			$$module.removePageable();
			$$module.hidePageableInFunctionCode();
		}else{
			$$module.showPageableInFunctionCode();
		}
	}
	
	$$module.showPageableInFunctionCode = function(){
		var optgroup = $("select#functionSelect option[data-functiontext='PAGEABLE']").closest("optgroup");
		optgroup.show();
	}
	$$module.hidePageableInFunctionCode = function(){
		var optgroup = $("select#functionSelect option[data-functiontext='PAGEABLE']").closest("optgroup");
		optgroup.hide();
		$("select#functionSelect").val("00");
	}
	
	/**
	 * Begin synchronize
	 */
	$$module.initSynchronize = function(){
		if($$module.isRegister()){
			$("#synchronizeLink").hide();
			return;
		}

		$("#synchronizeLink").click(function(){
			$$module.synchronizeAdvanceSql(true);
		});
	};
	
	$$module.hideModalSynchronize = function(){
		$("#fcomConfirmDialogSynchronize").modal('hide');
	}
	
	$$module.showMessageConfirmSynchronize = function(message){
		if(message != ""){
			var uniqueMessages = [];
			messages = message.split("\n");
			$.each(messages, function(i, el){
			    if($.inArray(el, uniqueMessages) === -1) uniqueMessages.push(el);
			});
			$("#fcomConfirmDialogSynchronize .modal-body").html(uniqueMessages.join("</br>"));
			$.qp.initConfirmModels('#fcomConfirmDialogSynchronize');
			$("#fcomConfirmDialogSynchronize").modal('show');
		}else{
			$.qp.showAlertModal(dbMsgSource["sc.sqldesign.0060"]);
		}
	}
	
	$$module.synchronizeAdvanceSql = function(isMessage){
		var message = "";
		message = $$module.synchronize(isMessage);
		if(!isMessage){
			$$module.hideModalSynchronize();
			return;
		}
		$$module.showMessageConfirmSynchronize(message);
	}
	
	/**
	 * synchronize sql advance
	 * 
	 */
	$$module.synchronize = function(isMessage){
		var message = "";
		if($("#tab-output").hasClass("active")){
			message += $$module.updateOutputTab(isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-output']").tab("show");
		}
		if($("#tab-input").hasClass("active")){
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-output']").tab("show");
			message += $$module.updateOutputTab(isMessage);
			$("a[href='#tab-input']").tab("show");
		}
		if($("#tab-sql-design").hasClass("active")){
			$("a[href='#tab-output']").tab("show");
			message += $$module.updateOutputTab(isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-sql-design']").tab("show");
		}
		return message;
	}
	
	$$module.updateInputTab = function(isMessage){
		var message = "";
		var table = $("table#inputForm");
		var rowsEntityType = $$module.getRowsOfTableByType(table, '14');
		var rowsExternalObject = $$module.getRowsOfTableByType(table, '17');
		var rowsCommonObject = $$module.getRowsOfTableByType(table, '16');
		if(rowsEntityType.length != 0){
			message += $$module.updateRowsEntityType(rowsEntityType, isMessage,'INPUT');
		}
		if(rowsExternalObject.length != 0){
			message += $$module.updateRowsExternalObjectType(rowsExternalObject, isMessage,'INPUT');
		}
		if(rowsCommonObject.length != 0){
			message += $$module.updateCommonObjectType(rowsCommonObject, isMessage,'INPUT');
		}
		return message;
	}
	
	$$module.removeTableWhenSynchronize = function(row,tableName, isMessage ,dataTypeMessage){
		var message = "";
		if(!isMessage){
			$.qp.ar.removeRow({
				link : row
			});
		}
		return dataTypeMessage + " " + $$module.messageDeleteTableSynchronize(tableName);
	}
	
	$$module.updateOutputTab = function(isMessage){
		var table = $("table#outputForm-advanced-sql");
		var rowsEntityType = $$module.getRowsOfTableByType(table, '14');
		var rowsExternalObject = $$module.getRowsOfTableByType(table,'17');
		var rowsCommonObject = $$module.getRowsOfTableByType(table, '16');
		var message = "";
		if(rowsEntityType.length != 0){
			message += $$module.updateRowsEntityType(rowsEntityType, isMessage, 'OUTPUT');
		}

		if(rowsExternalObject.length != 0){
			message += $$module.updateRowsExternalObjectType(rowsExternalObject, isMessage, 'OUTPUT');
		}

		if(rowsCommonObject.length != 0){
			message += $$module.updateCommonObjectType(rowsCommonObject, isMessage, 'OUTPUT');
		}
		return message;
	};
	
	//begin update table name code
	$$module.getUrlByTypeObject = function(typeObject, id){
		switch (typeObject) {
			case "entity":
				return CONTEXT_PATH + "/tabledesign/getTableDesignByTableId?tableId="+id+"&r="+Math.random();
			case "externalObject":
				return CONTEXT_PATH + "/externalobjectdefinition/getExternalObjectDefinitionById?externalObjectDefinitionId="+id+"&r="+Math.random();
			case "commonObject":
				return CONTEXT_PATH + "/commonobjectdefinition/getCommonObjectDefinitionById?commonObjectDefinitionId="+id+"&r="+Math.random();
			}
	}
	
	$$module.getNameByObjectType = function(typeObject, data){
		switch (typeObject) {
			case "entity":
				return data.tableName;
			case "externalObject":
				return data.externalObjectDefinitionName;
			case "commonObject":
				return data.commonObjectDefinitionName;
		}
	}
	
	$$module.getCodeByObjectType = function(typeObject, data){
		switch (typeObject) {
			case "entity":
				return data.tableCode;
			case "externalObject":
				return data.externalObjectDefinitionCode;
			case "commonObject":
				return data.commonObjectDefinitionCode;
		}
	}
	
	$$module.updateTableNameAndTablecode = function(entityRow,typeObject, isMessage, tab){
		var message = "";
		var tableId = $(entityRow).find("input[name$='.tableId']").val();
		var url = $$module.getUrlByTypeObject(typeObject,tableId);
		var dataDb = $.qp.getData(url);
		if($.isEmptyObject(dataDb)){
			return message;
		}
		var name = $$module.getNameByObjectType(typeObject, dataDb);
		var code = $$module.getCodeByObjectType(typeObject, dataDb);
		message += $$module.updateTableName(name, entityRow, isMessage, tab);
		message += $$module.updateTableCode(code, entityRow, isMessage, tab);
		return message;
	}
	
	$$module.updateTableCode = function(tableCodeDb, entityRow, isMessage, tab){
		var message = "";
		var tableCodeTxt = $(entityRow).find("input[type=text][name$='Code']");
		var tableCode = tableCodeTxt.val();
		if(tableCode == tableCodeDb){
			return message;
		}
		if(isMessage){
			message = tab + " " + $$module.messageChangeCodeTable(tableCode,tableCodeDb); 
		}else{
			tableCodeTxt.val(tableCodeDb);
		}
		return message;
	}
	
	$$module.updateTableName = function(tblNameDb, entityRow, isMessage, tab){
		var message = "";
		var autoCompleteTable = $(entityRow).find("input[name$='.tableIdAutocomplete']");
		var tableName = autoCompleteTable.val();
		if(!tblNameDb || tableName == tblNameDb){
			return message;
		}
		if(isMessage){
			message = tab +" " + $$module.messageChangeNameTable(tableName,tblNameDb); 
		}else{
			autoCompleteTable.val(tblNameDb);
			$(entityRow).find("input[type=hidden][name$='Name']").val(tblNameDb);
		}
		return message;
	}
	
	//End update table name code
	
	$$module.getRowsChildAgainOfRowsChid = function(rowsChild){
		var rowParent = $$module.getParentOfRowsChild(rowsChild)
		var arRgroupindex = $(rowParent).data("ar-rgroupindex");
		return $("#tab-output #outputForm-advanced-sql tbody tr[data-ar-rgroup='"+arRgroupindex+"']");
	}
	
	$$module.getParentOfRowsChild = function(rowsChild){
		var arRgroup = $(rowsChild).first().data("ar-rgroup");
		return $("#tab-output #outputForm-advanced-sql tbody tr[data-ar-rgroupindex='"+arRgroup+"']");
	};
	
	$$module.getRowsOfTableByType = function(table, type){
		var rowsResult  = [];
		$(table).find('tr').each(function(index){
			var select = $(this).find("select[name$='dataType']");
			if((select).val() == type){
				rowsResult.push(this);
			}
		});
		return rowsResult;
	};
	
	$$module.updateRowsEntityType = function(rowsEntityType, isMessage,type){
		var message = "";
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB
		$(rowsEntityType).each(function(){
			var tableId = $(this).find("input[name$='.tableId']").val();
			var tableName = $(this).find("input[name$='.tableIdAutocomplete']").val();
			if(tableId != ''){
				var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+tableId+"&r="+Math.random();
				var data = $.qp.getData(url);
				if($.isEmptyObject(data)){
					message	+= $$module.removeTableWhenSynchronize(this,tableName, isMessage, tab);
				}else{
					message += $$module.updateTableNameAndTablecode(this,"entity",isMessage, tab);
					message += $$module.compareOldAndNewValues(this,data,tableName,"entity" ,isMessage,type);
				}
			}
		});
		return message;
	};
	
	$$module.removeTableWhenSynchronize = function(row,tableName, isMessage ,dataTypeMessage){
		var message = "";
		if(!isMessage){
			$.qp.ar.removeRow({
				link : row
			});
		}
		return dataTypeMessage + " " + $$module.messageDeleteTableSynchronize(tableName);
	}
	
	$$module.compareOldAndNewValues = function(rows,data,tableName,typeData, isMessage, type){
		var dataArRgroupindex = $(rows).attr("data-ar-rgroupindex");
		var table = $(rows).closest("table");
		var rowsChild = table.find("tr[data-ar-rgroup='"+dataArRgroupindex+"'][data-ar-rgroupindex^='"+dataArRgroupindex+"']");
		var message = "";
		message +=  $$module.updateRowsEdited(rowsChild, data,tableName,typeData,isMessage, type);
		return message;
	}
	
	$$module.updateRowsEdited = function(rows, data, tableName,dataType,isMessage, type){
		var message = "";
		var removeRow = 0;
		var rowIndex = 0;
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB;
		var patterColumnName = "input[type=hidden][name$='.sqlDesignOutputName']";
		if(type == 'INPUT'){
			patterColumnName =  "input[type=hidden][name$='.sqlDesignInputName']"
		}
		$(rows).each(function(index){
			rowIndex = index;
			
			var columnName = $(this).find(patterColumnName).val();
			var columnId = $(this).find("input[type=hidden][name$='.columnId']").val();
			var fielNameId = $$module.factoryGetFieldNameIdByType(dataType);
			var table = $$module.getDataByColumIdDynamic(columnId, data, fielNameId);
			if($.isEmptyObject(table)){
				if(!isMessage){
					$.qp.ar.removeRow({link:this});
				}
				removeRow += 1;
				message += tab + " " + $$module.messageDeleleteSynchronize(columnName,tableName);
			}else{
				message += $$module.updateTdChild(this, table, tableName, dataType, isMessage, type);
			}
		});
		if(removeRow != 0 || rows.length < data.length){
			if(type == 'INPUT'){
				message += $$module.addFieldForTableInput(rows, data,tableName, rowIndex - removeRow,dataType,isMessage);
			}else{
				message += $$module.addFieldForTableOutput(rows, data,tableName, rowIndex - removeRow,dataType,isMessage);
			}
		}
		return message;
	};
	
	$$module.addFieldForTableOutput = function (rows, data,tableName,index, dataType, isMessage) {
		var message = "";
		var rowChild = $$module.getRowsChildAgainOfRowsChid(rows);
		var rowParrent = $$module.getParentOfRowsChild(rows);
		var newRow = rowChild.length == 0 ? rowParrent : $(rowChild.last());
		for(var i = index + 1; i < data.length; i++){
			if(!isMessage){
				if(i == 0){
					newRow = $$module.factoryAddMethodOutput(dataType,newRow, data[i], "data-ar-rgroupindex");
				}else{
					newRow = $$module.factoryAddMethodOutput(dataType,newRow, data[i], "data-ar-rgroup");
				}
			}
			var columnName = $$module.factoryGetFieldNameByType(dataType);
			message += OUTPUT_TAB + " " + $$module.messageAddSynchronize(data[i][columnName],tableName);
		}
		return message;
	}
	
	$$module.getRowsChildAgainOfRowsChidInput = function(rowsChild){
		var rowParent = $$module.getParentOfRowsChildInput(rowsChild)
		var arRgroupindex = $(rowParent).data("ar-rgroupindex");
		return $("#tab-input #inputForm tbody tr[data-ar-rgroup='"+arRgroupindex+"']");
	}
	
	$$module.getParentOfRowsChildInput = function(rowsChild){
		var arRgroup = $(rowsChild).first().data("ar-rgroup");
		return $("#tab-input #inputForm tbody tr[data-ar-rgroupindex='"+arRgroup+"']");
	};
	
	$$module.getLevelOfRow = function(row){
		var rgroup = $(row).attr("data-ar-rgroupindex");
		var level = 1;
		if(rgroup == undefined || rgroup == ""){
			level = 1;
		}else{
			level = (rgroup.match(/\./g) || []).length + 1;
		}
		return level;
	}
	
	$$module.updateCommonObjectType = function(rowsCommonObject, isMessage, type){
		var message = "";
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB;
		$(rowsCommonObject).each(function(){
			var tableId = $(this).find("input[name$='.tableId']").val();
			var tableName = $(this).find("input[name$='.tableIdAutocomplete']").val();
			if(tableId == ''){
				return message;
			}
			var urlcheck = CONTEXT_PATH + "/commonobjectdefinition/checkCommonObjectDefinitionExistById?commonObjectDefinitionId="+tableId+"&r="+Math.random();
			var check = $.qp.getData(urlcheck);
			if(!check){
				message	+= $$module.removeTableWhenSynchronize(this,tableName, isMessage, tab);
			}else{
				message += $$module.updateTableNameAndTablecode(this,"commonObject",isMessage, tab);
				var level = $$module.getLevelOfRow(this);
				var url = CONTEXT_PATH + "/commonobjectdefinition/getDetailsCommonObjectDefinition?commonObjectDefinitionId="+tableId+"&level="+level+"&r="+Math.random();
				var data = $.qp.getData(url);
				message += $$module.compareOldAndNewValues(this,data,tableName,"commonObject",isMessage, type);
			}

		});
		return message;
	};
	
	$$module.updateRowsExternalObjectType = function(rowsExternalObject, isMessage,type){
		var message = "";
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB;
		$(rowsExternalObject).each(function(){
			var tableId = $(this).find("input[name$='.tableId']").val();
			var tableName = $(this).find("input[name$='.tableIdAutocomplete']").val();
			if(tableId != ''){
				var urlcheck = CONTEXT_PATH + "/externalobjectdefinition/checkExternalObjectDefinitionById?externalObjectDefinitionId="+tableId+"&r="+Math.random();
				var check = $.qp.getData(urlcheck);
				if(!check){
					message	+= $$module.removeTableWhenSynchronize(this,tableName,isMessage, tab);
				}else{
					message += $$module.updateTableNameAndTablecode(this,"externalObject",isMessage, tab);
					var level = $$module.getLevelOfRow(this);
					var url = CONTEXT_PATH + "/externalobjectdefinition/getDetailsExternalObjectDefinition?externalObjectDefinitionId="+tableId+"&level="+level+"&r="+Math.random();
					var data = $.qp.getData(url);
					message += $$module.compareOldAndNewValues(this,data,tableName,"externalObject" ,isMessage,type);
				}
			}
		});
		return message;
	};
	
	$$module.factoryGetFieldNameIdByType = function(dataType){
		if(dataType == "commonObject"){
			return "commonObjectAttributeId";
		}
		if(dataType == "externalObject"){
			return "externalObjectAttributeId";
		}
		return "columnId";
	};
	
	$$module.getDataByColumIdDynamic = function(columnId, data, fieldCompare){
		result = {};
		for(var i = 0; i < data.length; i++){
			if(columnId == data[i][fieldCompare]){
				result = data[i];
				break;
			}
		}
		return result;
	}
	
	$$module.factoryGetFieldNameByType = function(dataType){
		if(dataType == "commonObject"){
			return "commonObjectAttributeName";
		}
		if(dataType == "externalObject"){
			return "externalObjectAttributeName";
		}
		return "columnName";
	};
	
	$$module.factoryAddMethodOutput = function(dataType, newRow, data, fieldIndex){
		if(dataType == "entity"){
			return $$module.addNewRowForEntityTypeOutput(newRow, data, fieldIndex);
		}
		if(dataType == "commonObject"){
			return $$module.addNewRowCommonObjectTypeOutput(newRow, data, fieldIndex);
		}
		if(dataType == "externalObject"){
			return $$module.addNewRowExternalObjectTypeOutput(newRow, data, fieldIndex);
		}
	};
	
	$$module.addNewRowExternalObjectTypeOutput = function(row, data, fieldIndex){
		$$module.addNewRowExternalObjectType(row, data, fieldIndex, "OUTPUT");
	}
	
	$$module.addNewRowExternalObjectTypeInput = function(row, data, fieldIndex){
		$$module.addNewRowExternalObjectType(row, data, fieldIndex, "INPUT");
	}
	
	$$module.addNewRowExternalObjectType = function(row, data, fieldIndex, type){
		var newRow = [];
		if(type == "INPUT"){
			newRow = $$module.addRowForInputSyschronize(row,fieldIndex);
		}else{
			newRow = $$module.addRowForSyschronize(row,fieldIndex);
		}
		$(newRow).find("input[name$='Name']").val(data.externalObjectAttributeName);
		$(newRow).find("label[name$='Name']").text(data.externalObjectAttributeName);
		$(newRow).find("input[name$='Code']").val(data.externalObjectAttributeCode);
		$(newRow).find("label[name$='Code']").text(data.externalObjectAttributeCode);
		$(newRow).find("input[name$='dataType']").val(data.dataType);
		var dataType = CL_SQL_DATATYPE[data.dataType];
		if(eval(data.arrayFlg)){dataType += "[]";}
		$(newRow).find("label[name*='dataType']").text(dataType);
		$(newRow).find("input[name*='arrayFlg']").val(data.arrayFlg);
		$(newRow).find("input[name$='tableId']").val(data.externalObjectDefinitionId);
		$(newRow).find("input[name$='columnId']").val(data.externalObjectAttributeId);
		$anchor = $(newRow);
		if(data.dataType==17){
			$anchor = $.qp.common.buildExternalObjectAttribute(data.externalObjectDefinition.externalObjectAttributes,$anchor);
		} else {
			$(newRow).find("input[name$='objectType']").val("3");
		}
		return newRow;
	}
	
	$$module.addNewRowCommonObjectTypeOutput = function(row, data, fieldIndex){
		return $$module.addNewRowCommonObjectType(row, data, fieldIndex,"OUTPUT")
	}
	
	$$module.addNewRowCommonObjectTypeInput = function(row, data, fieldIndex){
		return $$module.addNewRowCommonObjectType(row, data, fieldIndex,"INPUT")
	}
	
	$$module.addNewRowCommonObjectType = function(row, data, fieldIndex,type){
		var newRow = [];
		if(type == "INPUT"){
			newRow = $$module.addRowForInputSyschronize(row,fieldIndex);
		}else{
			newRow = $$module.addRowForSyschronize(row,fieldIndex);
		}
		$(newRow).find("input[name$='Name']").val(data.commonObjectAttributeName);
		$(newRow).find("label[name$='Name']").text(data.commonObjectAttributeName);
		$(newRow).find("input[name$='Code']").val(data.commonObjectAttributeCode);
		$(newRow).find("label[name$='Code']").text(data.commonObjectAttributeCode);
		$(newRow).find("input[name$='dataType']").val(data.dataType);
		var dataType = CL_SQL_DATATYPE[data.dataType];
		if(eval(data.arrayFlg)){
			dataType += "[]";
		}
		$(newRow).find("label[name*='dataType']").text(dataType);
		$(newRow).find("input[name*='arrayFlg']").val(data.arrayFlg);
		$(newRow).find("input[name$='tableId']").val(data.commonObjectDefinitionId);
		$(newRow).find("input[name$='columnId']").val(data.commonObjectAttributeId);
		$anchor = $(newRow);
		if(data.dataType==16){
			$(newRow).find("input[name$='objectType']").val("0");
			$anchor = $.qp.common.buildCommonObjectAttribute(data.commonObjectDefinition.commonObjectAttributes,$anchor);
		} else if(data.dataType==17){
			$(newRow).find("input[name$='objectType']").val("1");
			$anchor = $.qp.common.buildExternalObjectAttribute(data.externalObjectDefinition.externalObjectAttributes,$anchor);
		} else {
			$(newRow).find("input[name$='objectType']").val("2");
		}
		return newRow;
	}
	
	$$module.addNewRowForEntityTypeOutput = function(row, data, fieldIndex){
		return $$module.addNewRowForEntityType(row, data, fieldIndex,"OUTPUT");
	}
	
	$$module.addNewRowForEntityTypeInput = function(row, data, fieldIndex){
		return $$module.addNewRowForEntityType(row, data, fieldIndex,"INPUT");
	}
	
	$$module.addNewRowForEntityType = function(row, data, fieldIndex, type){
		var newRow = [];
		if(type == "INPUT"){
			newRow = $$module.addRowForInputSyschronize(row,fieldIndex)
		}else{
			newRow = $$module.addRowForSyschronize(row,fieldIndex);
		}
		$(newRow).find("input[name$='Name']").val(data.columnName);
		$(newRow).find("label[name$='Name']").text(data.columnName);
		$(newRow).find("input[name$='Code']").val(data.columnCode);
		$(newRow).find("label[name$='Code']").text(data.columnCode);
		$(newRow).find("input[name$='tableId']").val(data.tableDesignId);
		$(newRow).find("input[name$='columnId']").val(data.columnId);
		$(newRow).find("input[name$='dataType']").val(data.dataType);
		var dataType = CL_SQL_DATATYPE[data.dataType];
		if(eval(data.arrayFlg)){
			dataType += "[]";
		}
		$(newRow).find("label[name$='dataType']").text(dataType);
		$(newRow).find("input[name$='objectType']").val("5");
		return newRow;
	}
	
	$$module.addFieldForTableInput = function (rows, data,tableName,index, dataType, isMessage) {
		var message = "";
		var rowChild = $$module.getRowsChildAgainOfRowsChidInput(rows);
		var rowParrent = $$module.getParentOfRowsChildInput(rows);
		var newRow = rowChild.length == 0 ? rowParrent : $(rowChild.last());
		for(var i = index + 1; i < data.length; i++){
			if(!isMessage){
				if(i == 0){
					newRow = $$module.factoryAddMethodInput(dataType,newRow, data[i], "data-ar-rgroupindex");
				}else{
					newRow = $$module.factoryAddMethodInput(dataType,newRow, data[i], "data-ar-rgroup");
				}
			}
			var columnName = $$module.factoryGetFieldNameByType(dataType);
			message += INPUT_TAB + " " + $$module.messageAddSynchronize(data[i][columnName],tableName);
		}
		return message;
	}
	
	$$module.factoryAddMethodInput = function(dataType, newRow, data, fieldIndex){
		if(dataType == "entity"){
			return $$module.addNewRowForEntityTypeInput(newRow, data, fieldIndex);
		}
		if(dataType == "commonObject"){
			return $$module.addNewRowCommonObjectTypeInput(newRow, data, fieldIndex);
		}
		if(dataType == "externalObject"){
			return $$module.addNewRowExternalObjectTypeInput(newRow, data, fieldIndex);
		}
	};
	
	$$module.updateTdChild = function(row, table,tableName, dataType,isMessage,type){
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB;
		var message = "";
		columName = 'columnName';
		columnCode = "columnCode";
		var sqlName = 'sqlDesignOutputName';
		var sqlCode = 'sqlDesignOutputCode';
		if(dataType == "externalObject"){
			columName = "externalObjectAttributeName";
			columnCode = "externalObjectAttributeCode";
		}
		if(dataType == "commonObject"){
			columName = "commonObjectAttributeName";
			columnCode = "commonObjectAttributeCode";
		}
		if(type == 'INPUT'){
			sqlName = 'sqlDesignInputName';
			sqlCode = 'sqlDesignInputCode';
		}
		message += $$module.updateTdOfRowsChildEntityType(row, table, sqlName,columName,tableName,tab,isMessage);
		message += $$module.updateTdOfRowsChildEntityType(row, table, sqlCode,columnCode,tableName,tab,isMessage);
		message += $$module.updateTdOfRowsChildDataTypeEntityType(row, table, 'dataType','dataType',tableName,type, isMessage);
		return message;
	}
	
	$$module.updateTdOfRowsChildDataTypeEntityType = function(row,data,inputName,columnName,tableName,type,isMessage){
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB;
		var columName = $(row).find("input[type=hidden][name$='.sqlDesignOutputName']").val();
		if (type == 'INPUT'){
			var columName = $(row).find("input[type=hidden][name$='.sqlDesignInputName']").val();
		}
		var input = $(row).find("input[type=hidden][name$='."+inputName+"']");
		var message = "";
		if(input.val() != data[columnName]){
			if(!isMessage){
				input.val(data[columnName]);
				input.parent().find('span').text(CL_SQL_DATATYPE[data[columnName]]);
				input.parent().find('label').text(CL_SQL_DATATYPE[data[columnName]]);
			}
			message = tab + " " + $$module.messageChangeDataTypeSynchronize(columName, tableName, CL_SQL_DATATYPE[data[columnName]]);
		}
		return message;
	}
	
	$$module.updateTdOfRowsChildEntityType = function(row,data,inputName,columnName,tableName,tab ,isMessage){
		var input = $(row).find("input[type=hidden][name$='."+inputName+"']");
		var message = "";
		if(input.val() != data[columnName]){
			message = tab + " " + $$module.messageChangeSynchronize(input.val(), tableName, data[columnName]);
			if(!isMessage){
				input.val(data[columnName]);
				input.parent().find('span').text(data[columnName]);
				input.parent().find('label').text(data[columnName]);
			}
			
		}
		return message;
	}
	
	$$module.addRowForSyschronize = function(element, fieldIndex){
		var groupId = $(element).attr(fieldIndex);
		var newRow = $.qp.ar.addRow({
			container: $("table[id^='outputForm-advanced-sql']"),
			link: element,
			templateId:$("table[id^='outputForm-advanced-sql']").attr("data-ar-actionTemplateId").replace("action","child-entity"),
			templateData:{groupId : groupId},
			position:{anchor:$(element),string:'after'}
		});

		return newRow;
	}
	
	$$module.addRowForInputSyschronize = function(element, fieldIndex){
		var groupId = $(element).attr(fieldIndex);
		var newRow = $.qp.ar.addRow({
			container: $("table[id^='inputForm']"),
			link: element,
			templateId:$("table[id^='inputForm']").attr("data-ar-actionTemplateId").replace("action","child-entity"),
			templateData:{groupId : groupId},
			position:{anchor:$(element),string:'after'}
		});

		return newRow;
	}
	
	$$module.messageAddSynchronize = function(columnName, tableName){
		return fcomMsgSource['err.sys.0204'].replace("{0}", columnName).replace("{1}",tableName) + "\n";
	}

	$$module.messageDeleleteSynchronize = function(clolumnName, tableName){
		return fcomMsgSource['err.sys.0203'].replace("{0}", clolumnName).replace("{1}", tableName) + "\n";
	}

	$$module.messageChangeDataTypeSynchronize = function(columnName, tableName, dataUpdate){
		return fcomMsgSource['err.sys.0202'].replace("{0}",columnName).replace("{1}",tableName).replace("{2}",dataUpdate) + "\n";
	}

	$$module.messageChangeSynchronize = function(columnName, tableName, dataUpdate){
		return fcomMsgSource['err.sys.0206'].replace("{0}",columnName).replace("{1}",tableName).replace("{2}",dataUpdate) + "\n";
	}
	$$module.messageDeleteTableSynchronize = function(tableName){
		return fcomMsgSource['err.sys.0207'].replace("{0}", tableName) + "\n";
	}
	
	$$module.messageChangeNameTable = function(tableName, tableUpdate){
		return fcomMsgSource['err.sys.0274'].replace("{0}", tableName).replace("{1}", tableUpdate) + "\n";
	}
	
	$$module.messageChangeCodeTable = function(tableName, tableUpdate){
		return fcomMsgSource['err.sys.0275'].replace("{0}", tableName).replace("{1}", tableUpdate) + "\n";
	}
	
	/**
	 * End synchronize
	 */
	
	$$module.isRegister = function(){
		var sqlId = $('input:hidden[name="sqlDesignForm.sqlDesignId"]').val();
		if(!!sqlId && sqlId != ""){
			return false;
		}
		return true;
	}
	
	$$module.init = function(isView){
		$(function(){
			if(!isView){
				$("#inputForm").attr("data-ar-callback","$.qp.advancedsql.inputFormCallback");
				$("[id^='outputForm']").attr("data-ar-callback","$.qp.advancedsql.outputFormCallback");
				$("#sqlEditForm").find("input[selectsqlid='getAllTableDesignByProjectId']").attr("arg02",$("input[name='autocompleteForm.projectId']").val());
				$("#insertSqlPatternButton").on("click",function(){
					$$module.insertSqlPattern($("#AutocompleteId").data("uiAutocomplete").selectedItem,$("[id='sqlDesignForm.sqlText']"));
				});
				$("#insertTableCodeButton").on("click",function(){
					if($("#AutocompleteId").val() != undefined && $("#AutocompleteId").val() != ""){
						$$module.insertTableInfo($("#AutocompleteId").data("uiAutocomplete").selectedItem,$("[id='sqlDesignForm.sqlText']"));
					}else{
						var message = fcomMsgSource['inf.sys.0040'] != undefined ? fcomMsgSource['inf.sys.0040'] : "Please choose entity for generating data.";
						alert(message);
					}
				});
				$("#insertFunctionCodeButton").on("click",function(){
					$$module.insertFunctionCode($("#functionSelect").find("option[value='"+$("#functionSelect").val()+"']").attr("data-functionText"),$("[id='sqlDesignForm.sqlText']"));
				});
				$("#functionSelect").change(function(){
					$("#functionTooltip").data('tooltip').options.title=$("#functionSelect").find("option[value='"+$("#functionSelect").val()+"']").attr("data-functionHint");
				});
				if(!!$("#functionTooltip").data('tooltip')){
					$("#functionTooltip").data('tooltip').options.title=$("#functionSelect").find("option[value='"+$("#functionSelect").val()+"']").attr("data-functionHint");
				}
				$$module.initSqlPattern();
				$$module.initConfirmDialog();
				
			}
			$$module.initAdvancedSqlEditor();
			$$module.initOutputForm();
			$.qp.common.initTabs();
			$$module.initPageableSqlAd();
			$$module.initSynchronize();
			$$module.limitRowsInputAutocomplte();
			$.qp.common.setModuleIdForExternalCommonObject()
		});
	};
	
	return $$module;
})($.namespace("$.qp.advancedsql"));

