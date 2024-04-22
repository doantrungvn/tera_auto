/*@Author AnLT */
$.qp.sqlbuilder = (function($$module,moduleName) {

	var PHYSICAL_DATATYPE = {
			CHARACTER_VARYING : 1,
			TEXT : 3,
			INTEGER : 5,
			SMALLINT : 6,
			BIGINT : 7,
			SERIAL : 13,
			BIGSERIAL : 14,
			NUMERIC : 4,
			DATE : 10,
			CHAR : 2,
			CURRENCY : 15,
			BOOLEAN : 8,
			TIME : 11,
			DATETIME : 12,
			BINARY : 9,
			FLOAT : 16,
			DOUBLE : 17,
			TIMESTAMP : 18,
			isDateTimeType: function(dataType){
				if(!dataType){
					return false;
				}
				return dataType == this.DATE 
					|| dataType == this.TIME 
					|| dataType == this.DATETIME 
					|| dataType == this.TIMESTAMP;
			} 
	};
	
	var OBJECT_DATATYPE = {
			OBJECT : 0,
			ENTITY : 14,
			EXTERNAL_OBJECT: 17,
			COMMON_OBJECT: 16,
			isNotOjectDataType: function(dataType){
				return dataType != this.OBJECT && dataType != this.ENTITY &&
				dataType != this.EXTERNAL_OBJECT && dataType != this.COMMON_OBJECT;
			},
			isObjectDataType: function(dataType){
				return !this.isNotOjectDataType(dataType);
			}
	}

	var JAVA_DATATYPE = {
			BYTE : 1,
			SHORT : 2,
			INTEGER : 3,
			LONG : 4,
			FLOAT : 5,
			DOUBLE : 6,
			CHARACTER : 7,
			BOOLEAN : 8,
			STRING : 9,
			BIGDECIMAL : 10,
			TIMESTAMP : 11,
			DATETIME : 12,
			TIME : 13,
			DATE : 15
	};

	var PHYSICAL_TO_JAVA_TYPES = {
		'1' : '9',
		'3' : '9',
		'5' : '3',
		'19' : '1',
		'6' : '2',
		'7' : '4',
		'13' : '3',
		'14' : '4',
		'4' : '10',
		'17' : '6',
		'16' : '5',
		'10' : '15',
		'2' : '9',
		'15' : '10',
		'8' : '8',
		'11' : '13',
		'12' : '12',
		'18' : '11',
		'9' : '1'
	};

	var AGGREGATE_TO_JAVA_TYPES = {
		'0':'6',
		'1':'4',
		'6': '4',
	};
	
	var sqlPattern = {
		SELECT : 0,
		INSERT : 1,
		UPDATE : 2,
		DELETE : 3
	}

	var MAP_COLUMN_ID = [];
	var MAP_COLUMN_ID_BK = [];
	var MAP_SEQUENCE_FROM_SELECT = [];
	var OUTPUT_TAB = "["+dbMsgSource["sc.sqldesign.0004"]+"]";
	var SQL_DESIGN_TAB = "["+dbMsgSource["sc.sqldesign.0009"]+"]";
	var INPUT_TAB = "["+dbMsgSource["sc.sqldesign.0003"]+"]";
	$$module.OUTPUT_TAB = OUTPUT_TAB;
	$$module.PHYSICAL_TO_JAVA_TYPES = PHYSICAL_TO_JAVA_TYPES;
	$$module.PHYSICAL_DATATYPE = PHYSICAL_DATATYPE;
	$$module.JAVA_DATATYPE = JAVA_DATATYPE;
	$$module.MAP_SEQUENCE_FROM_SELECT = MAP_SEQUENCE_FROM_SELECT;
	$$module.AGGREGATE_TO_JAVA_TYPES = AGGREGATE_TO_JAVA_TYPES;
	$$module.buildMapByKey = function(mapData ,key, datatype) {
		var mapTmp = [];
		switch (parseInt(datatype)) {

		case JAVA_DATATYPE.BYTE:
			mapTmp[PHYSICAL_DATATYPE.BINARY] = PHYSICAL_DATATYPE.BINARY;
			mapData[key] = mapTmp;
			break;
		case JAVA_DATATYPE.SHORT:
			mapTmp[PHYSICAL_DATATYPE.SMALLINT] = PHYSICAL_DATATYPE.SMALLINT;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.INTEGER:
			mapTmp[PHYSICAL_DATATYPE.INTEGER] = PHYSICAL_DATATYPE.INTEGER;
			mapTmp[PHYSICAL_DATATYPE.SERIAL] = PHYSICAL_DATATYPE.SERIAL;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.LONG:
			mapTmp[PHYSICAL_DATATYPE.BIGINT] = PHYSICAL_DATATYPE.BIGINT;
			mapTmp[PHYSICAL_DATATYPE.BIGSERIAL] = PHYSICAL_DATATYPE.BIGSERIAL;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.FLOAT:
			mapTmp[PHYSICAL_DATATYPE.FLOAT] = PHYSICAL_DATATYPE.FLOAT;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.DOUBLE:
			mapTmp[PHYSICAL_DATATYPE.DOUBLE] = PHYSICAL_DATATYPE.DOUBLE;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.CHARACTER:
			mapTmp[PHYSICAL_DATATYPE.CHAR] = PHYSICAL_DATATYPE.CHAR;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.BOOLEAN:
			mapTmp[PHYSICAL_DATATYPE.BOOLEAN] = PHYSICAL_DATATYPE.BOOLEAN;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.STRING:
			mapTmp[PHYSICAL_DATATYPE.CHARACTER_VARYING] = PHYSICAL_DATATYPE.CHARACTER_VARYING;
			mapTmp[PHYSICAL_DATATYPE.TEXT] = PHYSICAL_DATATYPE.TEXT;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.BIGDECIMAL:
			mapTmp[PHYSICAL_DATATYPE.NUMERIC] = PHYSICAL_DATATYPE.NUMERIC;
			mapTmp[PHYSICAL_DATATYPE.CURRENCY] = PHYSICAL_DATATYPE.CURRENCY;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.TIMESTAMP:
			mapTmp[PHYSICAL_DATATYPE.TIMESTAMP] = PHYSICAL_DATATYPE.TIMESTAMP;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.DATETIME:
			mapTmp[PHYSICAL_DATATYPE.DATE] = PHYSICAL_DATATYPE.DATE;
			mapTmp[PHYSICAL_DATATYPE.DATETIME] = PHYSICAL_DATATYPE.DATETIME;
			mapData[key]  = mapTmp;
			break;
		case JAVA_DATATYPE.DATE:
			mapTmp[PHYSICAL_DATATYPE.DATE] = PHYSICAL_DATATYPE.DATE;
			mapData[key]  = mapTmp;
			break;	
		case JAVA_DATATYPE.TIME:
			mapTmp[PHYSICAL_DATATYPE.TIME] = PHYSICAL_DATATYPE.TIME;
			mapData[key]  = mapTmp;
			break;
		}

		return mapData;
	};

	$$module.buildMapInputByCode = function(table, inputName) {
		var mapInput = [];

		$(table).find('tbody>tr').each(function() {
			//var key = $.trim($(this).find("input[name$=" + inputName + "]").val());
			var key = $.trim($(this).attr('data-ar-rgroupindex'));
			var dataType = ($(this).find('select[name$=".dataType"]').val()==undefined?$(this).find('[name$=".dataType"]').val():$(this).find('select[name$=".dataType"]').val());
			mapInput = $$module.buildMapByKey(mapInput,key, dataType);
		});

		return mapInput;
	};

	$$module.reloadMapColumnId = function() {
		MAP_COLUMN_ID = [];
		MAP_COLUMN_ID_BK = [];

		// In the case create columnId for select
		if($('select[name="sqlDesignForm.sqlPattern"]').length == 0
				|| $('select[name="sqlDesignForm.sqlPattern"]').val() == "0") {
			$('#selectForm tbody>tr').each(function(sequence) {
				MAP_COLUMN_ID[$(this).find('[name$=".columnId"]').val()] = $(this).find('[name$=".dataType"]').val();

				if($(this).find('[name$=".dataTypeBackup"]').length > 0){
					MAP_SEQUENCE_FROM_SELECT[$(this).find('[name$=".itemSeqNo"]').val()] = $(this).find('[name$=".dataTypeBackup"]').val();
				} else {
					MAP_SEQUENCE_FROM_SELECT[$(this).find('[name$=".itemSeqNo"]').val()] = $(this).find('[name$=".dataType"]').val();
				}

				if($(this).find('label[name="dataTypeBackup"]') != undefined && $(this).find('label[name="dataTypeBackup"]').attr('value') != "") {
					MAP_COLUMN_ID_BK[$(this).find('[name$=".columnId"]').val()] = $(this).find('label[name="dataTypeBackup"]').attr('value');
				}
			});
		} else  {
				var tabId;
				if($('select[name="sqlDesignForm.sqlPattern"]').val() == "2"
					|| $('select[name="sqlDesignForm.sqlPattern"]').val() == "1") tabId = "#tab-sql-design-insert-update";
				if($('select[name="sqlDesignForm.sqlPattern"]').val() == "3") tabId = "#tab-sql-design-delete";

				$.each($(tabId+' #intoForm').find('[name="intoForm.dataTypeText"]').val().split(","),function() {
					var columnInfos = this.split(":");
					MAP_COLUMN_ID[columnInfos[0]] = columnInfos[3];
				});
		}

		return MAP_COLUMN_ID;
	};

	$$module.tableChangeToNextCell = function(obj){
		var value = $(obj.target).next("input[type=hidden]").val();
		var $NextInput = $(obj.target).parents("td").next().find("input[type=text]");
		var $NextHidden = $NextInput.next("input[type=hidden]");
		$NextInput.val("");
		$NextInput.attr("arg01",value);
		$NextHidden.val("");
		$NextInput.data("ui-autocomplete")._trigger("change");
	};
	$$module.havingTableChange = function(obj){
		var value = $(obj.target).next("input[type=hidden]").val();
		var $NextInput = $(obj.target).closest("table").find("input[name$='.columnIdAutocomplete']");
		var $NextHidden = $NextInput.next("input[type=hidden]");
		$NextInput.val("");
		$NextInput.attr("arg01",value);
		$NextHidden.val("");
		$NextInput.data("ui-autocomplete")._trigger("change");
	};
	$$module.passTableNameLabel = function(obj){
		var $scope = $(obj.target).closest("tr");
		 $(obj.target).removeClass("qp-missing");
		$$module.fromFormCallback($scope.parents("table[id='fromForm']"),"",$scope.closest("table").parents("tr:first"));
		$$module.refreshInputValueForTable($(obj.target).attr("previousValue"));

		var tableId = $(obj.target).attr("previousValue");
		$.each($("#selectForm").find(">tbody>tr[data-tableId='"+tableId+"']").get().reverse(),function(){
			$.qp.ar.removeRow({link:this});
		});
		var tableName = $(obj.target).val();
		tableId = $(obj.target).next().val();

		var dataToggle = $("#showHideUncheckedColumnsAnchor").attr("data-toggle");
		if(tableId){
			var selectedItem = $(obj.target).data("uiAutocomplete").selectedItem;
			$(obj.target).closest("td").find("input[name$='.tableType']").val(selectedItem.output05);
			var tableType = selectedItem.output05;
			var tableCode = $(obj.target).data("selectedItem").output01;
			if(selectedItem.output03){
				$.each(selectedItem.output03.split(","),function(){
					var columnInfos = this.split(":");
					$.qp.ar.addRow({tableId:'selectForm',templateData:{'tableId':tableId,'tableType':tableType,'tableName':tableName,'tableCode':tableCode,'columnId':columnInfos[0],'columnName':columnInfos[1],'columnCode':columnInfos[2], 'dataType':columnInfos[3],'style':(dataToggle=='hide'?'display:none':'')}});
					var $select = $('#selectForm tbody>tr:last select');
					$$module.setDisplayFunctionCode($select, columnInfos[3]);
				});
			}
		}

		MAP_COLUMN_ID = $$module.reloadMapColumnId();
		$$module.generateSqlExplanation();
		$$module.clearSqlScirpt();
		if($(obj.target).val() != ''){
			$$module.autoCheckDisplayAndSubmitOutput();
		}
		$$module.reloadAllSelectClone();
	};
	
	$$module.autoCheckDisplayAndSubmitOutput = function(){
		var row = $("#qp-autocomplete table#outputForm tbody tr:nth-child(1)");
		var displayCb = $(row).find("input[type=checkbox][name*='Display']");
		var submitRd = $(row).find("input[type=radio][name$='.submitColumn']");
		displayCb.prop("checked",true);
		submitRd.prop("checked",true);
	}

	$$module.passJoinTableNameLabel = function(obj){
		var tableName =  $(obj.target).val();
		 $(obj.target).removeClass("qp-missing");
		$(obj.target).closest("table").find("input[name$='.rightTableName']").val(tableName);
		$(obj.target).closest("table").find("b[name$='.rightTableName']").text(tableName);
		var $scope = $(obj.target).parents("table:first").find("table:first");
		$scope.find("input[type='text'][name$='.joinColumnIdAutocomplete']").each(function(){
			$(this).val('');
			$(this).data("ui-autocomplete")._trigger("change");
		});
		$scope.find("input[type='hidden'][name$='.joinColumnId']").val("");
		$$module.joinPairCallback($scope);
		$$module.refreshInputValueForTable($(obj.target).attr("previousValue"));

		$$module.initFirstFromFormItem();

		var tableId = $(obj.target).attr("previousValue");
		$.each($("#selectForm").find(">tbody>tr[data-tableId='"+tableId+"']").get().reverse(),function(){
			$.qp.ar.removeRow({link:this});
		});

		var tableName = $(obj.target).val();

		tableId = $(obj.target).next().val();

		var dataToggle = $("#showHideUncheckedColumnsAnchor").attr("data-toggle");
		if(tableId){
			var selectedItem = $(obj.target).data("uiAutocomplete").selectedItem;
			var tableCode = $(obj.target).data("selectedItem").output01;
			$(obj.target).closest("td").find("input[name$='.joinTableType']").val(selectedItem.output05);
			var tableType = selectedItem.output05;
			if(selectedItem.output03){
				$.each(selectedItem.output03.split(","),function(){
					var columnInfos = this.split(":");
					$.qp.ar.addRow({tableId:'selectForm',templateData:{'tableId':tableId,'tableType':tableType,'tableName':tableName,'tableCode':tableCode,'columnId':columnInfos[0],'columnName':columnInfos[1],'columnCode':columnInfos[2],'dataType':columnInfos[3],'style':(dataToggle=='hide'?'display:none':'')}});
					var $select = $('#selectForm tbody>tr:last select');
					$$module.setDisplayFunctionCode($select, columnInfos[3]);
				});
			}
		}

		$$module.reloadMapColumnId();

		$(obj.target).closest("tr").find("#joinPairTable>tbody>tr").each(function(){
			$.qp.ar.removeRow({link:this});
		});
		var previousTableId = $(obj.target).parents("tr:eq(1)").prev().find("input[name$='.tableId'],input[name$='.joinTableId']").first().val();
		var templateData= {};
		if(tableId & previousTableId){
			var href = CONTEXT_PATH + "/autocomplete/getForeignKeyBetweenTwoTables?r="+Math.random();

			$.ajax({
				method : "GET",
				url : href,
				data : { 'tableId' : previousTableId, 'joinTableId' : tableId },
				context: $(obj.target).closest("tr"),
				async : false,
				dataType:'json',
				success : function(result,status,jqXHR){
					if(result){
						if(result.fromTableId != tableId){
							templateData = {
								tableId : result.fromTableId,
								tableName : result.fromTableName,
								columnId : result.fromColumnId,
								columnName: result.fromColumnName,
								joinTableId: tableId,
								joinTableName : tableName,
								joinColumnId : result.toColumnId,
								joinColumnName: result.toColumnName,
							};
						} else {
							templateData = {
									tableId : result.toTableId,
									tableName : result.toTableName,
									columnId : result.toColumnId,
									columnName: result.toColumnName,
									joinTableId: tableId,
									joinTableName : tableName,
									joinColumnId : result.fromColumnId,
									joinColumnName: result.fromColumnName,
								};
						}
					}
				},
				error : function(jqXHR,status){

				}
			})
		}
		var tableCreated = $.qp.ar.addRow({'container':$(obj.target).closest("tr").find("#joinPairTable"),'templateData':templateData});
		$$module.displayOperatorCodeForJoin(tableCreated);
		$$module.generateSqlExplanation();
		$$module.clearSqlScirpt();
		$$module.reloadAllSelectClone();
	};
	
	$$module.displayOperatorCodeForJoin = function(table){
		var columnAuto = $(table).find("input[type=text][name$='columnIdAutocomplete']");
		var columnId = $(table).find("input[type=hidden][name$='columnId']:first");
		if(!columnId.val()){
			return;
		}
		var url = CONTEXT_PATH + "/tabledesign/findOneTableDesignDetail?columnId="+columnId.val()+"&r="+Math.random();
		var data = $.qp.getData(url);
		if($.isEmptyObject(data)){
			return;
		}
		$$module.setvalueforAutoComlumnAndColumId(data, columnAuto, columnId);
		var item = $$module.getItemForObjectByData(data);
		var objFake = { "target" : columnAuto,"item" : item}
		$$module.setDisplayOperatorCode(objFake);
	}
	
	$$module.getItemForObjectByData = function(data){
		var item = {"optionvalue": data.columnId,
			"optionlabel": data.name,
			"output02" : data.code,
			"output01" : data.dataTypeFlg,
			"output03" : data.baseType,
			"output04" :data.autoIncrementFlag}
		return item;
	}
	
	$$module.setvalueforAutoComlumnAndColumId = function(data, columnAuto, columnId){
		$(columnAuto).attr("optionvalue", data.columnId);
		$(columnAuto).attr("optionlabel", data.name);
		$(columnId).attr("output02",data.code);
		$(columnId).attr("output01",data.dataTypeFlg);
		$(columnId).attr("output03",data.baseType);
		$(columnId).attr("output04",data.autoIncrementFlag);
	}
	
	$$module.passTableId = function(obj){
		$(obj.target).removeClass("qp-missing");
		var tableId = $(obj.target).next("input[type=hidden]").val();


		var $NextInput = $(obj.target).closest("tr").find("input[name$='.columnIdAutocomplete'][type=text]");
		var $NextHidden = $NextInput.next("input[type=hidden]");
		$NextInput.val("");
		$NextInput.attr("arg01",tableId);
		if(tableId){
			var selectedItem = $(obj.target).data("uiAutocomplete").selectedItem;
			$(obj.target).closest("td").find("input[name$='.joinTableType']").val(selectedItem.output05);
			$NextInput.attr("arg02",selectedItem.output05);
		}

		$NextHidden.val("");
		$NextInput.data("ui-autocomplete")._trigger("change");
	};
	$$module.groupByFormCallback = function(){
		$$module.setArgumentForAllTableAC();
	};
	$$module.fromFormCallback = function(tableScope,direction,rowScope){
		var tableName = $(rowScope).find("input[name$='.tableIdAutocomplete']").val();
		var initTableId = $("#fromForm").find("input[name$='.tableId']:first").val();
		var initTableName = $("#fromForm").find("input[name$='.tableId']:first").attr("name");
		$(rowScope).find("input[name$='.leftTableName']").val(tableName);
		$(rowScope).find("b[name$='.leftTableName']").text(tableName);
		$(rowScope).find("input[type='text'][name$='.columnIdAutocomplete']").attr("arg01",initTableId);

		if(typeof window.selectedTables == 'undefined') {
			window.selectedTables = {};
		}
		if(initTableId){
			window.selectedTables[initTableName]=initTableId;
		} else {
			delete window.selectedTables[initTableName];
		}

		$$module.setArgumentForAllTableAC();

		if(tableScope.attr("id")=="fromForm") {
			if(direction!="remove") {
				$$module.recheckSelectedTables(tableScope.closest("table:first"));
			} else {
//				var removeTableId = $(rowScope).find("input[name$='.tableId']").val();
				var removeJoinTableId = $(rowScope).find("input[name$='.joinTableId']").val();
//				$$module.refreshInputValueForTable(removeTableId);
				$$module.refreshInputValueForTable(removeJoinTableId);

				var tableId = $(rowScope).find("input[name$='.joinTableId']").val();
				$("#selectForm").find(">tbody>tr[data-tableId='"+tableId+"']").each(function(){
					$.qp.ar.removeRow({link:this});
				});
			}
			//$("#fromForm").find("input[name$='ableIdAutocomplete']").attr("arg02",$("input[name='autocompleteForm.projectId']").val());
		}
		if(direction==$.qp.ar.CONST.DIRECTION_ADD){
			$(rowScope).find(".joinColumnsFormToggle").click(function(){
				var $glyph = $(this);
				$glyph.toggleClass("glyphicon-eye-close");
				$glyph.toggleClass("glyphicon-eye-open");
				$glyph.closest("td").next().find(">div").toggle();
			})
		}
		$$module.initFirstFromFormItem();
		$.qp.common.initProjectIdParameter();
		$$module.generateSqlExplanation();
	};
	$$module.joinPairCallback = function(tableScope,direction,rowScope) {
		var selectedTableName = $(tableScope).parents("table:first").find("input[type='hidden'][name$='.joinTableId']").attr("name");
		var selectedTableId = $(tableScope).parents("table:first").find("input[type='hidden'][name$='.joinTableId']").val();
		var selectedTableType = $(tableScope).parents("table:first").find("input[type='hidden'][name$='.joinTableType']").val();
		//$(tableScope).find("input[type='text'][id$='.columnIdAutocompleteId']").attr("arg01",$("input[name='fromForm[0].tableId']").val());
		$(tableScope).find("input[type='text'][id$='.joinColumnIdAutocompleteId']").attr("arg01", selectedTableId);
		$(tableScope).find("input[type='text'][id$='.joinColumnIdAutocompleteId']").attr("arg02", selectedTableType);
		$(tableScope).find("input[name$='ableIdAutocomplete']").attr("arg02",$("input[name='autocompleteForm.projectId']").val());

		if(typeof window.selectedTables == 'undefined') {
			window.selectedTables = {};
		}
		if(selectedTableId){
			window.selectedTables[selectedTableName]=selectedTableId;
		} else {
			delete window.selectedTables[selectedTableName];
		}
		$(tableScope).find("select[name$='joinColumnsForm[0].logicCode']").each(function(){
			$(this).val("");
			//$(this).prop("disabled",true);
			$(this).hide();
			$(this).next(".qp-required-field").hide();
		});

		var tableName =  $(tableScope).parents("table:first").find("input[name$='.joinTableIdAutocomplete']").val();
		$(tableScope).find("input[name$='.rightTableName']").val(tableName);
		$(tableScope).find("b[name$='.rightTableName']").text(tableName);

		if($(rowScope).find("[name$='.columnId']").val() & $(rowScope).find("[name$='.joinColumnId']").val()){
			$(rowScope).find("select[name$='.operatorCode']").val(0);
			$(rowScope).parents("table:eq(1)").find("input[name$='.joinType']:eq(0)").prop("checked",true);
		}


		$$module.setArgumentForAllTableAC();
		$$module.initFirstFromFormItem();
		$.qp.common.initProjectIdParameter();
		$$module.joinTypeClick($(tableScope).parents("table:eq(0)").find("input[name$='joinType']:checked"));
	};
	$$module.joinPairPrecheck = function(tableScope,link,direction) {
		var joinType = $(tableScope).parents("table:first").find("input[name$='.joinType']:checked").val();
		var joinTableId = $(tableScope).parents("table:first").find("input[name$='.joinTableId']").val()
		if(direction=="add"){
			if(joinType == '5'){
				return false;
			}
			if(!joinTableId && $(tableScope).parents("table:first").find("tr").length==0){
				return false;
			}
		} else {

		}
		return true;
	};
	$$module.whereFormCallback = function(table,direction,row){
		$$module.setArgumentForAllTableAC();
		$("[id='whereForm']").find("select[name$='whereForm[0].logicCode']").each(function(){
			$(this).val("");
			//$(this).prop("disabled",true);
			$(this).hide();
			$(this).next(".qp-required-field").hide();
		});

		if(direction=="add"){
			var $InputForm = $("#inputForm");
			$(row).find("select[name$='arg'], select[name$='arg2']").each(function(){
				$.qp.common.buildInputList($InputForm,$(this));
			});

			$("#tab-sql-design-insert-update #whereForm,#tab-sql-design-delete #whereForm").find("[name$='.leftTableIdAutocomplete'],[name$='.rightTableIdAutocomplete']").each(function(){
				$(this).attr("sourceType","local");
				$(this).attr("sourceCallback","$.qp.sqlbuilder.whereIntoSourceCallback");
			});

			$("#tab-sql-design-insert-update #whereForm,#tab-sql-design-delete #whereForm").find(">tbody>tr").each(function(){
				$(this).find("tr:eq(2)").remove();
			});
			$("#qp-viewdesign #whereForm select[name$='.conditionType'] option[value='2']").remove();
		}
		$$module.generateSqlExplanation();
		var inputHidden = $(row).find("input[type='hidden']");
	};
	$$module.valueFormCallback = function(table,direction,row){
		if(direction=="add"){
			var $InputForm = $("#inputForm");
			$(row).find("select[name$='parameter']").each(function(){
				$.qp.common.buildInputList($InputForm,$(this));
			});
			$(row).find("input[type='text'][name$='.columnIdAutocomplete']").attr("arg01",$("#intoForm").find("input[name$='.tableId']").val());
			var sqlPattern = $("select[name$='.sqlPattern']").val();
			if(!!sqlPattern && sqlPattern == 1){
				var option = $(row).find("select[name$='.valueType']").find("option");
				$$module.hideOptionEntityOfValueType(option);
			}
		}
	};
	$$module.hideOptionEntityOfValueType = function(option){
		option.each(function(){
			if($(this).attr("value") == 2){
				$(this).hide();
			}
		});
	}
	$$module.havingFormCallback = function(){
		$$module.setArgumentForAllTableAC();
		$("#havingForm").find("select[name$='havingForm[0].logicCode']").each(function(){
			$(this).val("");
			//$(this).prop("disabled",true);
			$(this).hide()
			$(this).next(".qp-required-field").hide();
		});
	};
	$$module.reInitTableInput = function(){
		var $tableInput=$(this);
		var inputAutocomplete = $(this).prev();
		$tableInput.val('');
		inputAutocomplete.val('');
		inputAutocomplete.data("ui-autocomplete")._trigger("change");
	}
	$$module.initFirstFromFormItem = function(){
		if($("#fromForm>tbody>tr").length==2 &
				$("#fromForm").find("input[name$='fromForm[0].joinTableId']").val() == ''){

				$("#fromForm tr:eq(1)").find(".qp-required-field").hide();
				//$("#fromForm tr:eq(1)").find("#joinPairTable tr").remove();
		} else {
			if($("#fromForm").find("select[name$='fromForm[0].joinType']").val() == '5'){
				//$("#fromForm tr:eq(1)").find("#joinPairTable tr").remove();
			}
			$("#fromForm tr:eq(1)").find(".qp-required-field:not([name$='.logicCode'] + .qp-required-field)").show();
		}
	};
	$$module.refreshInputValueForTable = function(removeTableId) {
		if(removeTableId) {
			$("[id='whereForm']").find("input[name$='.leftTableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			$("[id='whereForm']").find("input[name$='.rightTableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			$("#groupByForm").find("input[name$='.tableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			$("#havingForm").find("input[name$='.tableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			$("#orderByForm").find("input[name$='.tableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			//$("#selectForm").find("input[name$='.tableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			$("#autocompleteForm2").find("input[name$='TableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
			$("#fromForm").find("input[name$='.tableId'][value='"+removeTableId+"']").each($$module.reInitTableInput);
		}
	}
	$$module.setArgumentForAllTableAC = function(){
		var arg=$$module.getArgs(window.selectedTables);
		$("[id='whereForm']").find("input[name$='.leftTableIdAutocomplete']").attr("arg01",arg);
		$("[id='whereForm']").find("input[name$='.rightTableIdAutocomplete']").attr("arg01",arg);

		$("#groupByForm").find("input[name$='.tableIdAutocomplete']").attr("arg01",arg);

		$("#havingForm").find("input[name$='.tableIdAutocomplete']").attr("arg01",arg);

		$("#orderByForm").find("input[name$='.tableIdAutocomplete']").attr("arg01",arg);

		$("#selectForm").find("input[name$='.tableIdAutocomplete']").attr("arg01",arg);

		$("#autocompleteForm2").find("input[name$='TableIdAutocomplete']").attr("arg01",arg);

		$("#fromForm>tbody>tr").find("input[type='text'][name$='.tableIdAutocomplete']").first()
								.add($("#fromForm>tbody>tr").find("input[type='text'][name$='.joinTableIdAutocomplete']"))
								.each(function(){
									$(this).attr("arg03",$$module.getArgs(window.selectedTables,$(this).next().val()))
								});
		$("#fromForm>tbody>tr:gt(0)").each(function(i){
			$(this).find("input[name$='.tableIdAutocomplete']").attr("arg01",$$module.getArgs(null,null,i));
		});


	};
	$$module.recheckSelectedTables = function(table) {
		var $scope = $(table);
		$scope.find("input[name$='.joinTableId']").each(function(){
			if(typeof window.selectedTables == 'undefined') {
				window.selectedTables = {};
			}
			var tableId = $(this).val();
			var tableName = $(this).attr("name");
			if(tableId!=''){
				window.selectedTables[tableName]=tableId;
			} else {
				delete window.selectedTables[tableName];
			}
		});
		for(var key in window.selectedTables) {
			if(!$("#fromForm").find("input[name='"+key+"']").length) {
				delete window.selectedTables[key];
			}
		}
	};
	$$module.getArgs = function(selectedTables,ignoreValue,untilNum) {
		var arg ="";
		var limitUntilRow  = parseInt(untilNum);
		var inputTables = $("#fromForm>tbody>tr").find("input[type='hidden'][name$='.tableId']").first();
		if(isNaN(limitUntilRow)){
			inputTables = inputTables.add($("#fromForm>tbody>tr").find("input[type='hidden'][name$='.joinTableId']"));
		} else {
			inputTables = inputTables.add($("#fromForm>tbody>tr").find("input[type='hidden'][name$='.joinTableId']").slice(0,limitUntilRow<1?0:limitUntilRow));
		}

		var length = inputTables.length;
		if(length>0) {
			arg = arg +"(";
			var i=0;
			var value;
			for(var i=0;i<inputTables.length;i++) {
				value = inputTables.eq(i).val();
				if(value && value!=ignoreValue){
					if(i!=0 & arg!="(") {
						arg=arg+",";
					}
					arg=arg + value;
				}
			}
			arg = arg + ")";
		}
//		if(typeof selectedTables != 'undefined' && Object.keys(selectedTables).length>0) {
//			var length = Object.keys(selectedTables).length;
//			if(length>0) {
//				arg = arg +"(";
//				var i=0;
//				for(var key in selectedTables) {
//					if(i!=0) {
//						arg=arg+",";
//					}
//					arg=arg + selectedTables[key];
//					i++;
//				}
//				arg = arg + ")";
//			}
//		} else arg ="()";
		return arg;
	};
	$$module.getArgsV2 = function(selectedTables,ignoreValue) {
		var arg ="";

		var inputTables = $("#fromForm>tbody>tr").find("input[type='hidden'][name$='.tableId']")
												.first()
												.add($("#fromForm>tbody>tr").find("input[type='hidden'][name$='.joinTableId']"));
		var length = inputTables.length;
		if(length>0) {
			arg = arg +"(";
			var i=0;
			var value;
			for(var i=0;i<inputTables.length;i++) {
				value = inputTables.eq(i).val();
				if(value && value!=ignoreValue){
					if(i!=0 & arg!="(") {
						arg=arg+",";
					}
					arg=arg + value;
				}
			}
			arg = arg + ")";
		}
		return arg;
	};
	$$module.formatHavingValue = function(obj) {
		var value = $(obj.target).closest("td").find("input[type=hidden]").val();
		var input =  $(obj.target).parents("td").siblings().eq(1).find("input");
		if(obj.item){
			var columnType = obj.item.output01;
			switch(columnType){
			case '5':
			case '6':
			case '7':
				$.qp.formatInteger(input);
				break;
			case'4':
				$.qp.formatFloat(input);
				break;
			default:
				input.autoNumeric("destroy");
				break;
			}
		} else {
			input.autoNumeric("destroy");
		}
	};
	$$module.whereLeftTableChange = function(obj){
		 $(obj.target).removeClass("qp-missing");
		var value = $(obj.target).next("input[type=hidden]").val();
		var nextInput = $(obj.target).closest("tr").next().find("input[type='text'][name$='.leftColumnIdAutocomplete']");
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.val("");
		nextInput.attr("arg01",value);
		nextHidden.val("");
		nextInput.data("ui-autocomplete")._trigger("change");
		$$module.clearSqlScirpt();
	};
	$$module.intoTableChange = function(obj){
		if($("#valueForm tbody tr").length > 0){
			$$module.clearValues();
		}
		
		$$module.refreshInputValueForTable($(obj.target).attr("previousValue"));
		$$module.generateSqlExplanation();

		if($(obj.target).val() == "") {
			$(obj.target).closest('td').find('[name="intoForm.dataTypeText"]').val('');
		} else {
			$(obj.target).closest('td').find('[name="intoForm.dataTypeText"]').val(obj.item.output03);
			// reload map columId
			$$module.reloadMapColumnId();
		}

		var nextInput = $("#valueForm").find("input[type='text'][name$='.columnIdAutocomplete']");
		var value = $(obj.target).next("input[type=hidden]").val();
		
		var columns = $$module.getColumnsObjectTable(value,"",'true');
		if(!!columns && columns.length > 0){
			$$module.addRowToInsert(columns,value);
		}
		
		/*if(nextInput.length>0){
			var nextHidden = nextInput.next("input[type=hidden]");
			nextInput.val("");
			nextInput.attr("arg01",value);
			nextHidden.val("");

			nextInput.data("ui-autocomplete")._trigger("change");
			// set datatype for Into from
		}*/
		
		$$module.clearSqlScirpt();
	};
	
	$$module.changeTypeInsertValue = function(thiz){
		var parameter = $(thiz).closest("tr").find("[name$='.parameter']");
		var name = $(parameter).attr('name');
		var container = $(parameter).closest("td");
		$(container).empty();
		if(eval($(thiz).val()) == 0){
			$$module.buildSelectParamValue(name,container);
		}else if($(thiz).val() == 1){
			$("#valueForm-template-input").tmpl({name: name}).appendTo(container);
			$$module.buildDatepickerWithDatimeDataType(name,container);
			$$module.buildNumberInputWithNumberDataType(container);
			$$module.buildCheckBoxInputWithBooleanDataType(name,container);
		}else{
			$$module.buildAutocompleteValue(name,container);
		}
	}
	
	$$module.setValueForparam = function(thiz){
		var input = $(thiz).parent().find("input[type=hidden][name$='.parameter']");
		if($(thiz).is(":checked")){
			$(input).val("true");
		}else{
			$(input).val("false");
		}
	}
	
	$$module.buildCheckBoxInputWithBooleanDataType = function(name, container){
		var input = $(container).find("input[name$='.parameter']");
		if(input.length == 0){
			return;
		}
		var dataType = $(container).closest("tr").find("input[type=hidden][name$='.dataType']").val();
		if(PHYSICAL_DATATYPE.BOOLEAN != dataType){
			return;
		}
		$(container).empty();
		$("#valueForm-template-input-checkbox").tmpl({name: name}).appendTo(container);
	}
	
	$$module.buildNumberInputWithNumberDataType = function(container){
		$$module.addClassNumberForInput(container);
		$.qp.initialAutoNumeric(container)
	}
	
	$$module.addClassNumberForInput = function(container){
		var input = $(container).find("input[name$='.parameter']");
		if(input.length == 0){
			return;
		}
		var dataType = $(container).closest("tr").find("input[type=hidden][name$='.dataType']").val();

		if(PHYSICAL_DATATYPE.BINARY == dataType){
			$(input).attr("qp-min","-128");
			$(input).attr("qp-max","127");
			$(input.addClass("qp-input-smallint"));
		}
		if(PHYSICAL_DATATYPE.INTEGER == dataType){
			$(input.addClass("qp-input-integer"));
		}
		if(PHYSICAL_DATATYPE.SMALLINT == dataType){
			$(input.addClass("qp-input-smallint"));
		}
		if(PHYSICAL_DATATYPE.BIGINT == dataType){
			$(input.addClass("qp-input-bigint"));
		}
		if(PHYSICAL_DATATYPE.SERIAL == dataType){
			$(input.addClass("qp-input-serial"));
		}
		if(PHYSICAL_DATATYPE.BIGSERIAL == dataType){
			$(input.addClass("qp-input-bigserial"));
		}
		if(PHYSICAL_DATATYPE.NUMERIC == dataType){
			$(input.addClass("qp-input-to-float"));
		}
		if(PHYSICAL_DATATYPE.CURRENCY == dataType){
			$(input.addClass("qp-input-bigserial"));
		}
		if(PHYSICAL_DATATYPE.FLOAT == dataType){
			$(input.addClass("qp-input-float"));
		}
		if(PHYSICAL_DATATYPE.DOUBLE == dataType){
			$(input.addClass("qp-input-float"));
		}
	}
	
	$$module.buildDatepickerWithDatimeDataType = function(name,container){
		$$module.addClassQpDatimePickerForInput(name, container);
		$.qp.initialAllPicker($(container));
	}
	
	$$module.addClassQpDatimePickerForInput = function(name,container){
		var dataType = $(container).closest("tr").find("input[type=hidden][name$='.dataType']").val();
		if(PHYSICAL_DATATYPE.TIME == dataType){
			$$module.addTempleteDatpicker(name, container, "qp-input-timepicker");
		}
		if(PHYSICAL_DATATYPE.DATE == dataType){
			$$module.addTempleteDatpicker(name, container, "qp-input-datepicker");
		}
		if(PHYSICAL_DATATYPE.DATETIME == dataType || PHYSICAL_DATATYPE.TIMESTAMP == dataType){
			$$module.addTempleteDatpicker(name, container, "qp-input-datetimepicker");
		}
	}
	
	$$module.addTempleteDatpicker = function(name,container,className){
		var input = $(container).find("input[name$='.parameter']");
		if(input.length == 0){
			return;
		}
		$(container).empty();
		$("#valueForm-template-input-datepicker").tmpl({name: name}).appendTo(container);
		var div = $(container).find("div.date").first();
		if(!$(div).hasClass(className)){
			$(div).addClass(className);
		}
	}
	
	$$module.buildAutocompleteValue = function(name,container){
		var tableId = $("#intoForm").find("input[type='hidden'][name$='.tableId']").val();
		var template = $("#valueForm-template-autoComplete").tmpl({name: name,tableId: tableId}).appendTo(container);
		$(template).parent().find(".qp-input-autocomplete").each(function(){
			$.qp.initialAutocomplete($(this));
		});
	}
	
	$$module.buildSelectParamValue = function(name,container){
		var template = $("#valueForm-template-select").tmpl({name: name}).appendTo(container);
		var inputForm = $("#inputForm");
		var select = $(template).parent().find('select');
		$.qp.common.buildInputList(inputForm,select);
	}
	
	$$module.addRowToInsert = function(columns,tableId){
		var btnAddRow = $("#valueForm").parent().find("a[class*='glyphicon-plus']").last();
		var columsFilter = columns.filter(function(m){
			return (eval(m.output04) == 0);
		});
		
		//Add row for each column of table
		$(columsFilter).each(function(){
			$(btnAddRow).trigger("click");
		});
		
		$$module.fillDataColumnToAutoComplete(columsFilter, tableId);
		
	}
	
	$$module.fillDataColumnToAutoComplete = function(columsFilter, tableId){
		var autoCompletes = $("#valueForm").find("input[type='text'][name$='.columnIdAutocomplete']");
		autoCompletes.each(function(index) {
			$$module.updateAutoComplete($(this), columsFilter[index], tableId);
			$$module.setPatternFormat(columsFilter[index]["output03"], $(this).closest("tr"));
		});
	}
	
	$$module.clearValues = function(){
		$("#valueForm").find("tr").remove();
	}
	
	$$module.updateAutoComplete = function(autoComplete, data, tableId){
		var nextHidden = autoComplete.next("input[type=hidden]");
		autoComplete.val(data.optionLabel);
		autoComplete.attr("arg01",tableId);
		nextHidden.val(data.optionValue);
		$(autoComplete).closest("td").find("input[type=hidden][name$='.dataType']").val(data.output03);
		autoComplete.data("ui-autocomplete")._trigger("close");
		// set datatype for Into from
	}
	
	$$module.getColumnsObjectTable = function (value, searchKey, isUnLimit){
		var key = !!searchKey ? searchKey : "";
		var limit = !!isUnLimit ? isUnLimit : "";
		var href = CONTEXT_PATH + "/Autocomplete/";
		var dataObject = $.qp.getJson(href, {"searchKey":key,"sourceType":"getAllTableDesignColumnAC","arg01":value, "arg04":limit}).outputGroup;
		return dataObject;
	}
	
	$$module.whereRightTableChange = function(obj){
		 $(obj.target).removeClass("qp-missing");
		var value = $(obj.target).next("input[type=hidden]").val();
		var nextInput = $(obj.target).closest("tr").next().find("input[type='text'][name$='.rightColumnIdAutocomplete']");
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.val("");
		nextInput.attr("arg01",value);
		nextHidden.val("");
		nextInput.data("ui-autocomplete")._trigger("change");
	};
	$$module.whereParameterChange = function(obj){
		$(obj).next("input[type=hidden]").val($(obj).val());
	};
	$$module.conditionTypeChange = function(obj){
		var value = $(obj).val();
		var rightTableInput = $(obj).closest("table").find("input[name$='.rightTableIdAutocomplete']").closest(".input-group");
		var rightColumnInput = $(obj).closest("table").find("input[name$='.rightColumnIdAutocomplete']").closest(".input-group");
		var value1Parent = $(obj).closest("table").find("input[name$='.value']").parent("div");
		var valueInput = value1Parent.length>0?$(value1Parent[0]):$(obj).closest("table").find("input[name$='.value']");
		var require1 = $(valueInput).nextAll(".qp-required-field:first");
		var value2Parent = $(obj).closest("table").find("input[name$='.value2']").parent("div");
		var value2Input = value2Parent.length>0?$(value2Parent[0]):$(obj).closest("table").find("input[name$='.value2']");
		var require2 = $(value2Input).prevAll(".qp-required-field:first");
		var select1Input = $(obj).closest("table").find("select[name$='.arg']");
		var select1Input2 = $(obj).closest("table").find("select[name$='.arg2']");
		var separator = valueInput.siblings(".qp-separator-from-to");
		var require3 = $(rightTableInput).nextAll(".qp-required-field:first");
		var operatorSelect = $(obj).closest("table").find("select[name$='.operatorCode']");
		$$module.initOperatorOptions(operatorSelect,value);

		if(value=='0') {
			valueInput.show();
			require1.show();
			select1Input.hide();
			select1Input2.hide();
			require2.hide();
			require3.hide();
			rightTableInput.hide();
			rightColumnInput.hide();
		} else if(value == '2') {
			select1Input.show();
			valueInput.hide();
			value2Input.hide();
			require1.show();
			rightTableInput.hide();
			rightColumnInput.hide();
			require3.hide();
		} else	if(value=='1'){
			valueInput.hide();
			select1Input.hide();
			rightTableInput.show();
			rightColumnInput.show();
			require1.show();
			require3.show();
		} else {
			valueInput.hide();
			select1Input.hide();
			require1.hide();
			require2.hide();
			require3.hide();
			rightTableInput.hide();
			rightColumnInput.hide();
		}
		$$module.initOperatorOptions(operatorSelect,value);

		$$module.initOperatorOptionsOfWhere(operatorSelect,value);

		$$module.operatorTypeChange(operatorSelect,obj);
	};

	$$module.changeDisplayFormatInput = function(inputObj1, inputObj2, datatype) {

		var input1Parent = $(inputObj1).parent("div");
		if(input1Parent.length>0){
			input1Parent.replaceWith(inputObj1);
		}
		var input2Parent = $(inputObj2).parent("div");
		if(input2Parent.length>0){
			input2Parent.replaceWith(inputObj2);
		}
		try{
			$(inputObj1).autoNumeric('destroy');
			$(inputObj2).autoNumeric('destroy');
			$(input1Parent).data("DateTimePicker").destroy();
			$(input2Parent).data("DateTimePicker").destroy();
		} catch(e){

		}
		var $htmlFromVal;
		var $htmlToVal;
		var input1, input2;

		if(datatype != undefined && datatype != ""){
			if($(inputObj1).is(':checkbox')){
				var hideInput = $(inputObj1).next();
				if($(hideInput).is(":hidden")){
					$(hideInput).remove();
				}
			}
			$(inputObj1).removeAttr('class').attr('type','text');

			if(inputObj2 != undefined){
				$(inputObj2).removeAttr('class').attr('type','text');
			}
			switch (parseInt(datatype)) {
			case PHYSICAL_DATATYPE.INTEGER :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-integer pull-left');

				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-integer pull-left');
					$(inputObj2).addClass('form-control qp-input-to-integer pull-right');
					input2 = inputObj2;
				}

				input1 = $(inputObj1);
				break;

			case PHYSICAL_DATATYPE.SMALLINT :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-smallint pull-left');

				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-smallint pull-left');
					$(inputObj2).addClass('form-control qp-input-to-smallint pull-right');
					input2 = inputObj2;
				}

				input1 = $(inputObj1);
				break;

			case PHYSICAL_DATATYPE.BIGINT :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-bigint pull-left');

				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-bigint pull-left');
					$(inputObj2).addClass('form-control qp-input-to-bigint pull-right');
					input2 = inputObj2;
				}

				input1 = $(inputObj1);
				break;

			case PHYSICAL_DATATYPE.SERIAL :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-serial pull-left');

				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-serial pull-left');
					$(inputObj2).addClass('form-control qp-input-to-serial pull-right');
					input2 = inputObj2;
				}

				input1 = $(inputObj1);
				break;

			case PHYSICAL_DATATYPE.BIGSERIAL :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-bigserial pull-left');

				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-bigserial pull-left');
					$(inputObj2).addClass('form-control qp-input-to-bigserial pull-right');
					input2 = inputObj2;
				}

				input1 = $(inputObj1);
				break;

			case PHYSICAL_DATATYPE.NUMERIC :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-float pull-left');
				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-float pull-left');
					$(inputObj2).addClass('form-control qp-input-to-float pull-right');
					input2 = inputObj2;
				}
				input1 = inputObj1;
				break;

			case PHYSICAL_DATATYPE.CURRENCY :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-currency pull-left');
				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-currency pull-left');
					$(inputObj2).addClass('form-control qp-input-to-currency pull-right');
					input2 = inputObj2;
				}
				input1 = inputObj1;
				break;

			case PHYSICAL_DATATYPE.BINARY :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-smallint pull-left');
				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-smallint pull-left');
					$(inputObj2).addClass('form-control qp-input-to-smallint pull-right');
					input2 = inputObj2;
				}
				input1 = inputObj1;
				break;

			case PHYSICAL_DATATYPE.FLOAT :
			case PHYSICAL_DATATYPE.DOUBLE :	
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-float pull-left');
				if(inputObj2 != undefined) {
					$(inputObj1).attr('class','').addClass('form-control qp-input-from-float pull-left');
					$(inputObj2).addClass('form-control qp-input-to-float pull-right');
					input2 = inputObj2;
				}
				input1 = inputObj1;
				break;

			case PHYSICAL_DATATYPE.DATE :
				$$module.removeValueBoolean(inputObj1);
				$$module.convertValueDateTime(inputObj1);
				$htmlFromVal = $('<div>').addClass('input-group date qp-input-datepicker pull-left').css('width', $(inputObj1).css("width"));
				if(inputObj1.is(":hidden")){
					$htmlFromVal.hide();
					inputObj1.show();
				}
				inputObj1.replaceWith($htmlFromVal);
				$(inputObj1).attr('class','').css('width','100%').attr('type','text');
				$htmlFromVal.append($(inputObj1).addClass('form-control'));
				$htmlFromVal.append($('<span>').addClass('input-group-addon').append($('<span>').attr('class', 'glyphicon glyphicon-calendar')));

				if(inputObj2 != undefined) {
					$$module.convertValueDateTime(inputObj2);
					$htmlFromVal.attr('class','input-group date qp-input-from-datepicker pull-left')
					$htmlToVal = $('<div>').addClass('input-group date qp-input-to-datepicker pull-rigth').css('width', $(inputObj2).css("width"));
					$htmlToVal.hide();
					inputObj2.replaceWith($htmlToVal);
					inputObj2.show();
					$(inputObj2).attr('class','').css('width','100%').attr('type','text');
					$htmlToVal.append($(inputObj2).addClass('form-control'));
					$htmlToVal.append($('<span>').addClass('input-group-addon').append($('<span>').attr('class', 'glyphicon glyphicon-calendar')));
					input2 = $htmlToVal;
				}
				input1 = $htmlFromVal;
				break;
			case PHYSICAL_DATATYPE.DATETIME :
			case PHYSICAL_DATATYPE.TIMESTAMP :
				$$module.convertValueDateTime(inputObj1);
				$$module.removeValueBoolean(inputObj1);
				$htmlFromVal = $('<div>').addClass('input-group date qp-input-datetimepicker-detail pull-left').css('width', $(inputObj1).css("width"));
				if(inputObj1.is(":hidden")){
					$htmlFromVal.hide();
					inputObj1.show();
				}
				inputObj1.replaceWith($htmlFromVal);
				$(inputObj1).attr('class','').css('width','100%').attr('type','text');
				$htmlFromVal.append($(inputObj1).addClass('form-control'));
				$htmlFromVal.append($('<span>').addClass('input-group-addon').append($('<span>').attr('class', 'glyphicon glyphicon-calendar')));

				if(inputObj2 != undefined) {
					$$module.convertValueDateTime(inputObj2);
					$htmlFromVal.attr('class','input-group date qp-input-from-datetimepicker-detail pull-left')
					$htmlToVal = $('<div>').addClass('input-group date qp-input-to-datetimepicker-detail pull-rigth').css('width', $(inputObj2).css("width"));
					$htmlToVal.hide();
					inputObj2.replaceWith($htmlToVal);
					inputObj2.show();
					$(inputObj2).attr('class','').css('width','100%').attr('type','text');
					$htmlToVal.append($(inputObj2).addClass('form-control'));
					$htmlToVal.append($('<span>').addClass('input-group-addon').append($('<span>').attr('class', 'glyphicon glyphicon-calendar')));
					input2 = $htmlToVal;
				}
				input1 = $htmlFromVal;

				break;

			case PHYSICAL_DATATYPE.TIME :
				$$module.removeValueBoolean(inputObj1);
				$$module.convertValueDateTime(inputObj1);
				$htmlFromVal = $('<div>').addClass('input-group date qp-input-timepicker pull-left').css('width', $(inputObj1).css("width"));
				if(inputObj1.is(":hidden")){
					$htmlFromVal.hide();
					inputObj1.show();
				}
				inputObj1.replaceWith($htmlFromVal);
				$(inputObj1).attr('class','').css('width','100%').attr('type', 'text');
				$htmlFromVal.append($(inputObj1).addClass('form-control'));
				$htmlFromVal.append($('<span>').addClass('input-group-addon').append($('<span>').attr('class', 'glyphicon glyphicon-calendar')));

				if(inputObj2 != undefined) {
					$$module.convertValueDateTime(inputObj2);
					$htmlFromVal.attr('class','input-group date qp-input-from-timepicker pull-left')
					$htmlToVal = $('<div>').addClass('input-group date qp-input-to-timepicker pull-rigth').css('width', $(inputObj2).css("width"));
					$htmlToVal.hide();
					inputObj2.replaceWith($htmlToVal);
					inputObj2.show();
					$(inputObj2).attr('class','').css('width','100%').attr('type', 'text');
					$htmlToVal.append($(inputObj2).addClass('form-control'));
					$htmlToVal.append($('<span>').addClass('input-group-addon').append($('<span>').attr('class', 'glyphicon glyphicon-calendar')));

					input2 = $htmlToVal;
				}
				input1 = $htmlFromVal;

				break;

			case PHYSICAL_DATATYPE.CHARACTER_VARYING :
			case PHYSICAL_DATATYPE.TEXT :
			case PHYSICAL_DATATYPE.CHAR :
				$$module.removeValueBoolean(inputObj1);
				$(inputObj1).addClass('form-control qp-input-text pull-left');
				input1 = inputObj1;
				break;

			case PHYSICAL_DATATYPE.BOOLEAN :
				input1 = $$module.buildInputForBoolean(inputObj1);
				break;
			}
			var functionCode = $(inputObj1).closest("tbody").find("select[name$='.functionCode']");
			if($(functionCode).val() == '1'){
				try{
					$(input1Parent).data("DateTimePicker").destroy();
					$(input2Parent).data("DateTimePicker").destroy();
				}catch(e){}
				var input1Parent = $(inputObj1).parent("div");
				if(input1Parent.length>0){
					input1Parent.replaceWith(inputObj1);
				}
				var input2Parent = $(inputObj2).parent("div");
				if(input2Parent.length>0){
					input2Parent.replaceWith(inputObj2);
				}
				input1 = $$module.setingInputForCount(inputObj1,'qp-input-from-serial');
				input2 = $$module.setingInputForCount(inputObj2,'qp-input-to-serial');
			}
		} else {
			$(inputObj1).removeAttr('class').addClass('form-control qp-input-text pull-left');
			input1 = inputObj1;
			if(inputObj2 != undefined)
				$(inputObj2).removeAttr('class').addClass('form-control qp-input-text pull-right');
			input2 = inputObj2;
		}
		try{
			$.qp.initialAutoNumeric(inputObj1.closest("td"));
			$.qp.initialAllPicker(inputObj1.closest("td"));
			$.qp.initialAutoNumeric(inputObj2.closest("td"));
			$.qp.initialAllPicker(inputObj2.closest("td"));
		}catch(e){
			
		}
		var arrTmp = [];
		arrTmp[0] = input1;
		arrTmp[1] = input2;

		return arrTmp;
	};
	
	$$module.convertValueDateTime = function(input){
		//TODO convert value date
		var validate = $$module.validateBeforeConvertDatetime(input);
		if(!validate){
			return;
		}
		switch (eval(dataType)) {
			case PHYSICAL_DATATYPE.DATE :
				$$module.convertValueDateType(patternFormat, input);
				break;
			case PHYSICAL_DATATYPE.DATETIME :
			case PHYSICAL_DATATYPE.TIMESTAMP :
				$$module.convertValueDatetimeType(patternFormat, input);
				break;
			case PHYSICAL_DATATYPE.TIME :
				$$module.convertValueTimeType(patternFormat, input);
				break;
		}
		
	}
	
	$$module.validateBeforeConvertDatetime = function(input){
		var functionCode = $(input).closest("table").find("select[name$='.functionCode']").val();
		if(functionCode == 1){
			return false;
		}
		var tr = $(input).closest("tr");
		var dataType = tr.find("input[type=hidden][name$='.dataType']").val();
		if(!PHYSICAL_DATATYPE.isDateTimeType){
			return false;
		}
		var patternFormat = tr.find("input[type=hidden][name$='.patternFormat']");
		if(!patternFormat.val()){
			return false;
		}
		return true;
	}
	
	$$module.convertValueDateType = function(patternFormat, input){
		var patternFomratConverted = mapDateFormat[$(patternFormat).val()];
		if(patternFomratConverted == fcomSysDateFormat){
			return;
		}
		var date = moment($(input).val(), patternFomratConverted).toDate();
		var strDatetime = moment(date).format(fcomSysDateFormat);
		patternFormat.val(keyDateFormat);
		$(input).val(strDatetime);
	}
	
	$$module.convertValueDatetimeType = function(patternFormat, input){
		var patternFomratConverted = mapDateTimeFormat[$(patternFormat).val()];
		if(patternFomratConverted == fcomSysDatetimeFormat){
			return;
		}
		var date = moment($(input).val(), patternFomratConverted).toDate();
		var strDatetime = moment(date).format(fcomSysDatetimeFormat);
		patternFormat.val(keyDateTimeFormat);
		$(input).val(strDatetime);
	}
	
	$$module.convertValueTimeType = function(patternFormat, input){
		var patternFomratConverted = mapTimeFormat[$(patternFormat).val()];
		if(patternFomratConverted == fcomSysDatetimeFormat){
			return;
		}
		var date = moment($(input).val(), patternFomratConverted).toDate();
		var strDatetime = moment(date).format(fcomSysTimeFormat);
		patternFormat.val(keyTimeFormat);
		$(input).val(strDatetime);
	}
	
	$$module.destroyNumberic = function(input){
		try{
			$(input).autoNumeric('destroy');
		} catch(e){

		}
	}
	
	$$module.setingInputForCount = function(input,css){
		$$module.destroyNumberic(input);
		$(input).removeAttr('class').attr('type','text');
		$(input).addClass('form-control '+css+' pull-left');
		var valInput = $(input).val();
		if(isNaN(valInput) || valInput != parseInt(valInput, 10) || valInput < 0){
			$(input).val("");
		}
		return input;
	}
	
	$$module.removeValueBoolean = function(inputObj1){
		var val = $(inputObj1).val();
		if(val == 'TRUE' || val == 'FALSE'){
			 $(inputObj1).val("");
		}
	}
	
	$$module.buildInputForBoolean = function(inputObj1){
		var functionCode = $(inputObj1).closest("table").find("select[name$='.functionCode']").val();
		if(functionCode == 1){
			return;
		}
		$(inputObj1).attr('type', 'checkbox');
		$(inputObj1).addClass('qp-input-checkbox qp-input-checkbox-margin');
		var name = $(inputObj1).attr("name");
		var inputHidden = "<input type='hidden' name='"+name+"'/>"
		if($(inputObj1).val() == 'TRUE'){
			$(inputObj1).prop('checked',true);
			$(inputObj1).next().val("TRUE");
		}
		if($(inputObj1).val() == 'FALSE'){
			$(inputObj1).after(inputHidden);
			$(inputObj1).next().val("FALSE");
		}
		if($(inputObj1).val() != 'TRUE' && $(inputObj1).val() != 'FALSE'){
			$(inputObj1).val('FALSE');
			$(inputObj1).after(inputHidden);
			$(inputObj1).next().val("FALSE");
		}
		$(inputObj1).change(function(event){
			$$module.changeValueInput(event,inputHidden);
		});
		return inputObj1;
	}
	
	$$module.changeValueInput = function(event,inputHidden){
		var input = event.target;
		var isNotCheckBox = !$(input).is(':checkbox');
		if(isNotCheckBox){
			return;
		}
		if($(input).val()== 'FALSE'){
			$(input).val('TRUE');
			$(input).next().remove();
		}else{
			$(input).val('FALSE');
			$(input).after(inputHidden);
			$(input).next().val('FALSE');
		}
	}

	$$module.initOperatorOptions = function(operatorSelect,conditionType) {
		operatorSelect = $(operatorSelect);

		if(conditionType=="1") {
			operatorSelect.find("option[value='7']").hide();
			operatorSelect.find("option[value='8']").hide();
			operatorSelect.find("option[value='9']").hide();
			var value = operatorSelect.val();
			if(operatorSelect.val()=='7' ||operatorSelect.val()=='8' ||operatorSelect.val()=='9'){
				operatorSelect.val('');
			}
		} else if(conditionType=="2") {
			operatorSelect.find("option[value='7']").show();
			operatorSelect.find("option[value='8']").hide();
			operatorSelect.find("option[value='9']").hide();
			var value = operatorSelect.val();
			if(operatorSelect.val()=='8' ||operatorSelect.val()=='9'){
				operatorSelect.val('');
			}
		} else{
			operatorSelect.find("option[value='7']").show();
			operatorSelect.find("option[value='8']").show();
			operatorSelect.find("option[value='9']").show();
		}
	};

	$$module.selectFormIndexChangeCallback = function(table,row,oldIndex,newIndex){
		$("[id^='outputForm']").find("[name$='Column'][type=hidden][value='"+oldIndex+"']")
						.each(function(){
							$(this).val(newIndex);
						});
		$("#orderByForm").find("[name$='tableColumn'][type=hidden][value='"+oldIndex+"']")
		.each(function(){
			$(this).val(newIndex);
		});
	};
	$$module.selectFormCallback = function(table,direction,row){
		if(direction==$.qp.ar.CONST.DIRECTION_ADD){
			$(table).show();
			$("#showHideUncheckedColumnsAnchor").show();
		} else if(direction==$.qp.ar.CONST.DIRECTION_REMOVE){
			$(row).find("input[name$='.isSelected']").prop("checked",false);
			if($(table).find(">tbody>tr").length==0){
				$(table).hide();
				$("#showHideUncheckedColumnsAnchor").hide();
			}
			$("#orderByForm").find(">tbody>tr input[name$='.tableColumn'][value='"+$(row).find("input[name$='.itemSeqNo']").val()+"']").each(function(){
				$.qp.removeAutocompleteData($(this).nextAll('.dropdown-toggle:first'));
			});
		}
		$(row).find("[name$='.isSelected']").trigger("change");
	};
	$$module.getSeqNoByElementSqlDesign = function(element){
		var itemSqNo = $(element).closest("tr").find("input[type=hidden][name$='.itemSeqNo']");
		return itemSqNo.val();
	};
	
	$$module.getAutoCompleteBySeqNoOutput = function(seqNo){
		var outputTable = $("#qp-sqldesign table#outputForm-sql-builder");
		var mappingColumn = $(outputTable).find("input[type=hidden][name$='.mappingColumn'][value='"+seqNo+"']");
		var autocomplete = $(mappingColumn).parent().find("input[name$='.mappingColumnAutocomplete']");
		return autocomplete;
	}
	
	$$module.getAutoCompleteInOutputByElementInSqlDesign = function(element){
		var seqNo = $$module.getSeqNoByElementSqlDesign(element);
		var autocomplete = $$module.getAutoCompleteBySeqNoOutput(seqNo);
		return autocomplete;
	}
	
	$$module.selectFunctionCodeChange = function(obj){
		if($(obj).is("select")){
			$("#orderByForm").find(">tbody>tr input[name$='.tableColumn'][value='"+$(obj).closest("tr").find("input[name$='.itemSeqNo']").val()+"']").each(function(){
				$.qp.removeAutocompleteData($(this).nextAll('.dropdown-toggle:first'));
			});

			//find autocomplete by value ex [tableName].[columnName]
			var tableName = $(obj).closest("tr").find("input[type=hidden][name$='.tableIdAutocomplete']").val();
			var columnName =  $(obj).closest("tr").find("input[type=hidden][name$='.columnIdAutocomplete']").val();
			var tableColumnName = "["+tableName+"]"+"."+"["+columnName+"]";
			var  autocomplete = $$module.getAutoCompleteInOutputByElementInSqlDesign(obj);


			//Change mapping column in output tab
			var columnName = $(obj).closest("tr").find("[name$='.columnIdAutocomplete']").val();
			var functionCode = $(obj).closest("tr").find("[name$='.functionCode'] option:selected").text();
			var label =functionCode+"(["+$(obj).closest("tr").find("[name$='.tableIdAutocomplete']").val()+"].["+columnName+"])";
			autocomplete.val(label);

			//update data type coressponding with mapping columns
			var selectBox = $(autocomplete).closest("tr").find("select[name$='.dataType']");
			var dataType = $(obj).closest("tr").find("input[type=hidden][name$='.dataType']").val();
			var javaType = PHYSICAL_TO_JAVA_TYPES[dataType];
			var aggregateType = $(obj).val();
			var dataTypeAggregate = aggregateType in AGGREGATE_TO_JAVA_TYPES ? AGGREGATE_TO_JAVA_TYPES[aggregateType] : javaType;
			if(functionCode == "SUM" && $$module.checkDataTypeIsDoubleOrFloat(javaType)){
				dataTypeAggregate = JAVA_DATATYPE.DOUBLE;
			}
			if(functionCode == "SUM" && javaType == JAVA_DATATYPE.BIGDECIMAL){
				dataTypeAggregate = JAVA_DATATYPE.BIGDECIMAL;
			}
			
			if(OBJECT_DATATYPE.isNotOjectDataType(selectBox.val())){
				selectBox.val(dataTypeAggregate);
			}
			
			//if user select again is --Select--
			if(typeof $(obj).val() == "undefined" || $(obj).val() == ""){
				if(OBJECT_DATATYPE.isNotOjectDataType(selectBox.val())){
					autocomplete.val(tableColumnName);
				}
			}

		} else {
			if($("#showHideUncheckedColumnsAnchor").attr("data-toggle")=="hide" & $(obj).is(":not(:checked)")){
				$(obj).closest("tr").hide();
			}
			if($(obj).is(":not(:checked)")){
				$("#checkAllAnchor").prop("checked",false);
			} else {
				var checkCount = $("#selectForm").find("[name$='.isSelected'][type=checkbox]").length;
				if($("#selectForm").find("[name$='.isSelected'][type=checkbox]:checked").length==checkCount & checkCount>0){
					$("#checkAllAnchor").prop("checked",true);
				}
			}

			$("[id^='outputForm']")
				.find("[name$='Column'][type=hidden][value='"+$(obj).closest("tr").find("input[name$='.itemSeqNo']").val()+"']")
				//.nextAll('.dropdown-toggle:first')
				.each(function(){
				$.qp.removeAutocompleteData($(this).next());
				$(this).closest("tr").find("input").prop("checked",false);
			});
		}

		if($(obj).is(":checked")){
			var tableName = $.trim($(obj).closest("td").next().text());
			var columnName = $.trim($(obj).closest("td").next().next().text());
			var columnCode = $.trim($(obj).closest("td").next().next().find("input[name$='.columnCode']").val());
			var dataType = PHYSICAL_TO_JAVA_TYPES[$.trim($(obj).closest("td").next().next().find("[name$='.dataType']").val())];
			//get column Id
			var columnId = $(obj).closest("tr").find("input[name$='.columnId']").val();
			var rowIndex = $(obj).closest("tr").attr("data-ar-rgroupindex");
			var functionCode = $(obj).closest("tr").find("[name$='.functionCode'] option[value!='']:selected").text();
			var $output = $("[id^='outputForm']").find("[type=hidden][name$='Column'][value=''],[type=hidden][name$='Column']:not([value])").first();
			if($output.length==0){
				if($("#qp-sqldesign").find("[id^='outputForm']").length>0){
					$("#qp-sqldesign a[href='#tab-output']").tab("show");
					$output = $($.qp.ar.addRow({container : $("#qp-sqldesign").find("[id^='outputForm']"),templateId:'outputForm-l0-template'})).find("[type=hidden][name$='Column']");
					$("#qp-sqldesign a[href='#tab-sql-design']").tab("show");
					//add columnId to output row
					$output.closest("tr").find("input[name$='.columnId']").val(columnId);
				}
			}
			var label;
			// set select
			if(functionCode){
				label = functionCode+"(["+tableName+"].["+columnName+"])";
			} else {
				label = "["+tableName+"].["+columnName+"]";
			}
			if(!!$output.prev().data("ui-autocomplete")){
				$output.prev().data("ui-autocomplete")._trigger('select', 'autocompleteselect', {item:{optionLabel:label,optionValue:rowIndex,output01:dataType,output02:columnName,output03:columnCode}});
			}
			
			if($output.prev().data("ui-autocomplete")){
				$output.prev().data("ui-autocomplete")._trigger("close");
			}
			
			if(!!$output.prev().data("ui-autocomplete")){
				$output.prev().data("ui-autocomplete")._trigger('change', 'autocompleteselect', {item:{optionLabel:label,optionValue:rowIndex,output01:dataType,output02:columnName,output03:columnCode}});
			}
		}
	};
	
	$$module.checkDataTypeIsDoubleOrFloat = function(dataType){
		if(dataType == JAVA_DATATYPE.DOUBLE || dataType == JAVA_DATATYPE.FLOAT){
			return true;
		}
		return false;
	}
	$$module.whereFunctionCodeChange = function(obj){
		var $conditionSelect = $(obj).closest("table").find("[name$='.conditionType']");
		if($(obj).val()){
			$conditionSelect.val("0");
			$conditionSelect.change();
			$conditionSelect.prop("disabled",true);
			var $input = $("<input>");
			$input.attr("type","hidden");
			$input.attr("name",$conditionSelect.attr("name"));
			$input.val("0");
			$conditionSelect.after($input);
			var leftColumnId = $(obj).closest("table").find('input[name$=".leftColumnIdAutocomplete"]');
			$$module.callSetDisplayFunctionCode({target:leftColumnId});
		} else {
//			$conditionSelect.val("");
//			$conditionSelect.change();
			$conditionSelect.prop("disabled",false);
//			$conditionSelect.next("input[name='"+$conditionSelect.attr("name")+"']").remove();
		}
	};
	$$module.operatorTypeChange = function(obj,conditionSelect){
		var value = $(obj).val();
		var value1Parent = $(obj).closest("table").find("input[name$='.value']").parent("div");
		var value2Parent = $(obj).closest("table").find("input[name$='.value2']").parent("div");

		var value1Input = value1Parent.length>0?$(value1Parent[0]):$(obj).closest("table").find("input[name$='.value']");
		var value2Input = value2Parent.length>0?$(value2Parent[0]):$(obj).closest("table").find("input[name$='.value2']");
		var select1Input = $(obj).closest("table").find("select[name$='.arg']");
		var select2Input = $(obj).closest("table").find("select[name$='.arg2']");
		var require1 = $(value1Input).nextAll(".qp-required-field:first");
		var require2 = $(value2Input).prevAll(".qp-required-field:first");
		var require3 = $(obj).closest("table").find("input[name$='.rightTableIdAutocomplete']").parent().nextAll(".qp-required-field:first");

		var separator = value1Input.siblings(".qp-separator-from-to");

		if(typeof conditionSelect=='undefined') {
			conditionSelect = $(obj).closest("table").find("select[name$='.conditionType']");
			if(conditionSelect.length>0){
				$$module.conditionTypeChange(conditionSelect);
			}
		} else {
			conditionSelect = $(conditionSelect);
		}
		var conditionValue = conditionSelect.length>0?conditionSelect.val():'0';

		if(value=='7') {
			if(conditionValue =='0') {
				value1Input.css({"width":"40%"});
				value1Input.show();
				value2Input.css({"width":"40%"});
				value2Input.show();
				require1.show();
				require2.show();
				separator.show();
			}
			if(conditionValue =='2') {
				select1Input.css({"width":"40%"});
				select1Input.addClass("pull-left");
				select1Input.show();
				select2Input.css({"width":"40%"});
				select2Input.show();
				require1.show();
				require2.show();
				separator.show();
			}
		} else if(value) {
			value2Input.hide();
			require2.hide();
			select2Input.hide();
			separator.hide();
			if(conditionValue =='0') {
				value1Input.css({"width":"93.58%"});
				value1Input.show();
				require1.show();
				select1Input.hide();
			}
			if(conditionValue =='2') {
				select1Input.css({"width":"93.58%"});
				select1Input.show();
				value1Input.hide();
				require1.show();
			}
			if(value=='8' || value == '9') {
				select1Input.hide();
				value1Input.hide();
				require1.hide();
			}
			if(conditionValue =='1') {
				select1Input.hide();
				value1Input.hide();
			}

		} else {
			value2Input.hide();
			value1Input.hide()
			require2.hide();
			require1.hide();
			select2Input.hide();
			select1Input.hide();
			separator.hide();
		}
	};
	$$module.havingOperatorChange = function(obj){
		var value = $(obj).val();
		var valueInput = $(obj).closest("td").next().find("input[name$='.value']");
		if(value=='7' && conditionValue !='1') {
			valueInput.show();
		} else {
			valueInput.show();
		}
	};
	$$module.groupTypeChange = function(obj){
		var rowCompound$ = [];
		var rowCompoundGroup$ = [];
		var flag = 0;
		var $WhereForm = $(obj).closest("#whereForm");
		$WhereForm.find(">tbody>tr").each(function(){
			var currentCheck = $(this).find("[name$='.groupType']").prop("checked");
			$(this).find(".qp-open-parenthesis").removeClass("glyphicon-open-parenthesis");
			$(this).find(".qp-close-parenthesis").removeClass("glyphicon-close-parenthesis");
			switch(flag){
			case 0:
				if(currentCheck){
					flag = 1;
					rowCompound$.push([$(this).find("[name$='.groupType']"),
										$(this).find(".qp-open-parenthesis"),
										$(this).find(".qp-close-parenthesis")
										]);
				};
				break;
			case 1:
				if(!currentCheck){
					rowCompoundGroup$.push(rowCompound$);
					rowCompound$ = [];
					flag=0;

				} else {
					rowCompound$.push([$(this).find("[name$='.groupType']"),
										$(this).find(".qp-open-parenthesis"),
										$(this).find(".qp-close-parenthesis")
										]);
				}
				break;
			}
		});
		if(flag==1){
			rowCompoundGroup$.push(rowCompound$);
		}
		for(var i=0;i<rowCompoundGroup$.length;i++){
			rowCompoundGroup$[i][0][1].addClass("glyphicon-open-parenthesis");
			rowCompoundGroup$[i][rowCompoundGroup$[i].length-1][2].addClass("glyphicon-close-parenthesis");
		}
	};
	$$module.changeProjectAC = function(obj){
		var value = $(obj.target).next("input[type=hidden]").val();
		var nextInput = $(obj.target).closest("tr").find("input[id='autocompleteForm.moduleIdAutocompleteId']");
		var nextHidden = nextInput.next("input[type=hidden]");
		nextInput.val("");
		nextInput.attr("arg01",value);
		nextHidden.val("");
		nextInput.data("ui-autocomplete")._trigger("change");
		$("#fromForm").find("input[name$='ableIdAutocomplete']").each(function(){
			$(this).attr("arg02",value);
			$.qp.removeAutocompleteData($(this).nextAll('.dropdown-toggle:first'));
		});
	};
	$$module.joinTypeChange = function(select){
		var $select = $(select);
		switch($select.val()) {
		case '5':
			$select.closest("table").find("#joinPairTable tr").remove();
			break;
		default:
			if($select.closest("table").find("#joinPairTable tr").length==0){
				$select.closest("table").find('a.glyphicon-plus').click();
			}
			break;
		}
	};
	$$module.objectTypeChange = function(obj){
		var type = $(obj).val();
		if(type=='0'){
			var $Caller = $(obj);
			var $ParentRow = $Caller.closest("tr");
			var rgroupindex = $ParentRow.attr("data-ar-rgroupindex") || "";
			var actionTemplateId = "inputForm-action-l"+1+"-template";//rgroupindex.split(".").length
			$.qp.ar.addRow({link:obj,tableId:"inputForm",templateId:actionTemplateId,templateData:{ groupId:rgroupindex},position:{anchor:$Caller.parents("tr:first"),string:"after"} });
		} else {
			$.qp.ar.removeRow({link:obj,removeType:'onlyDescendants'});
		}
		var autoComplete = $(obj).closest("td").find("input[name$=.mappingColumnAutocomplete]");
	};

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
		var checkbox = $(row).find("input[type=checkbox][name*='isArray']");
		$$module.cancelCheckBoxArray(checkbox);
		if(direction=="add"){
			$.qp.common.restyleRow(row);

			$(row).find("select[name$='.dataType']").val("9");
			/*$(row).find("[name$='.sqlDesignInputCode']").change(function(){
				$("[id='whereForm']").find("select[name$='arg'], select[name$='arg2']").each(function(){
					var value = $(this).next().val();
					$.qp.common.buildInputList($InputForm,$(this));
					$(this).val(value);
				});
				$("[id='valueForm']").find("select[name$='parameter']").each(function(){
					var value = $(this).next().val();
					$.qp.common.buildInputList($InputForm,$(this));
					$(this).val(value);
				});
			});*/

			//set type autocomplete
			var dataArRindex = $(row).attr("data-ar-rindex");
			var typeAuto = dataArRindex > 0 ? dbMsgSource["sc.sqldesign.0057"] : dbMsgSource["sc.sqldesign.0056"];
			$(row).find("td.autocomleteType").html(typeAuto);

		} else if(direction="remove"){
			//set type autocomplete
			var tr = $(table).find("tbody > tr").first()
			var typeAuto = dbMsgSource["sc.sqldesign.0056"];
			$(tr).find("td.autocomleteType").html(typeAuto);
			
			
			$("[id='whereForm']").find("select[name$='arg'], select[name$='arg2']").each(function(){
				$(this).trigger("mouseover");
			});
			$("[id='valueForm']").find("select[name$='parameter']").each(function(){
				$(this).trigger("mouseover");
			});

		}

		/*var $InputForm = $("#inputForm");
		$("[id='whereForm']").find("select[name$='arg'], select[name$='arg2']").each(function(){
			$.qp.common.buildInputList($InputForm,$(this));
		});*/

		/*$("[id='valueForm']").find("select[name$='parameter']").each(function(){
			$.qp.common.buildInputList($InputForm,$(this));
		});*/

		// remove object type from list
		$("#qp-autocomplete #inputForm").find("select[name$='.dataType'] option[value='0'],select[name$='.dataType'] option[value='14'],select[name$='.dataType'] option[value='16'],select[name$='.dataType'] option[value='17']").remove();

	};

	$$module.cancelCheckBoxArray = function(checkbox){
		if(typeof isSqlPage != 'undefined' && isSqlPage == true){
			$$module.addTooltipForCheckBox(checkbox);
			$$module.preventCheckCheckBox(checkbox);
		}
	}
	
	$$module.preventCheckCheckBox = function(checkbox){
		$(checkbox).val(false); 
		$(checkbox).prop('checked', false); 
		$(checkbox).on("click",function(event){
			event.preventDefault()
		});
	}
	
	$$module.enableCancelCheckBoxArray = function(checkbox){
		if(typeof isSqlPage != 'undefined' && isSqlPage == true){
			$$module.enableTootipForCheckbox(checkbox);
			$$module.preventCheckCheckBox(checkbox);
		}
	}
	
	$$module.allowCheckArrayForByteDataType = function(checkbox){
		if(typeof isSqlPage != 'undefined' && isSqlPage == true){
			$$module.disableTootipForCheckbox(checkbox);
			$(checkbox).unbind("click").click(function(){
				$$module.setEventClickForCheckBoxIsArray(this);
		    });
		}
	}
	
	$$module.setEventClickForCheckBoxIsArray = function(checkbox){
		if($(checkbox).val() == 'false'){
    	   $(checkbox).val(true);
    	   $(checkbox).prop('checked', true);
        }else{
    	   $(checkbox).val(false);
    	   $(checkbox).prop('checked', false);
        }
	}
	
	$$module.disableTootipForCheckbox = function(checkbox){
		var dataTooltip = $(checkbox).attr("data-original-title");
		if(!!dataTooltip && dataTooltip != ""){
			$(checkbox).tooltip('disable');
		}
	}
	
	$$module.enableTootipForCheckbox = function(checkbox){
		var dataTooltip = $(checkbox).attr("data-original-title");
		if(!!dataTooltip && dataTooltip != ""){
			$(checkbox).tooltip('enable');
		}else{
			$$module.addTooltipForCheckBox(checkbox);
		}
		
	}
	
	$$module.addTooltipForCheckBox = function(checkbox){
		$(checkbox).attr("title", dbMsgSource["sc.sqldesign.0055"]);
		$(checkbox).attr("data-toggle","tooltip");
		$(checkbox).tooltip();
	}

	$$module.removeTooltipForCheckBox = function(checkbox){
		$(checkbox).next().remove();
	}

	$$module.outputFormCallback = function(table,direction,row){
		if(direction=="add"){
			$.qp.common.restyleRow(row);
			$(row).find("select[name$='.dataType']").val("9");

			$(row).find("[name$='ColumnAutocomplete']").each(function(){
				$(this).attr("sourceType",'local');
				$(this).attr("sourceCallback",'$.qp.sqlbuilder.outputFormSourceCallback');
			});
		}
	};
	$$module.initValueForm = function(){
		$("[id='valueForm']").find("select[name$='parameter']").each(function(){
			var value = $(this).next().val();
			$.qp.common.buildInputList($("#inputForm"),$(this));
			$(this).val(value);
		});
		$("[id='valueForm']").find("input[name$='.columnIdAutocomplete']").attr("arg01",$("#intoForm").find("input[name='intoForm.tableId']").val());
		var tableId = $("[id='intoForm']").find("input[type=hidden][name$='tableId']").val();
		var columns = $$module.getColumnsObjectTable(tableId);
		$$module.initAutoCompleteValues(tableId,columns);
		$$module.displayParameterForView();
		$$module.initValueDatetime($("[id='valueForm'] tbody tr"));
	};
	
	$$module.initValueDatetime = function(rows){
		if(rows.length == 0){
			return;
		}
		$(rows).each(function(){
			$$module.convertValueDateTime($(this).find("input[name$='.parameter']"));
		});
	}
	
	$$module.getColumsByTableIdIntoForm = function(){
		var tableId = $("[id='intoForm']").find("input[type=hidden][name$='tableId']").val();
		var columns = $$module.getColumnsObjectTable(tableId);
		return columns;
	}
	
	$$module.displayParameterForView = function(){
		var tableId = $("[id='intoForm']").find("span[name$='tableId']").text();
		var columns = $$module.getColumnsObjectTable(tableId);
		$("#valueForm").find("span[name$='.parameter']").each(function(){
			var value = $(this).text();
			var column = $$module.getColumnInColumnsById(columns,value);
			$(this).text(column.optionLabel);
		});
	}
	
	$$module.initAutoCompleteValues = function(tableId,columns){
		$("[id='valueForm']").find("input[name$='.parameterAutocomplete']").each(function(){
			$(this).attr("arg01",!!tableId ? tableId : '');
			var value = $(this).val();
			var column = $$module.getColumnInColumnsById(columns,value);
			$(this).val(column.optionLabel);
		});
	}
	
	$$module.getColumnInColumnsById = function(columns,columnId){
		var column = {
					optionLabel:"",
					optionValue:"",
					output01:"",
					output02:"",
					output03:"",
					output04:""
				};
		for(var i = 0; i < columns.length; i++){
			if(columns[i].optionValue == columnId){
				column = columns[i];
				break;
			}
		}
		return column;
	}
	$$module.initWhereForm = function(){
		var $InputForm = $("#inputForm");
		$("[id='whereForm']").find("select[name$='arg'], select[name$='arg2']").each(function(){
			var value = $(this).next().val();
			$.qp.common.buildInputList($InputForm,$(this));
			$(this).val(value);
		});

		$("#inputForm [name$='.sqlDesignInputCode']").change(function(){
			$("[id='whereForm']").find("select[name$='arg'], select[name$='arg2']").each(function(){
				var value = $(this).next().val();
				$.qp.common.buildInputList($InputForm,$(this));
				$(this).val(value);
			});
			$("[id='valueForm']").find("select[name$='parameter']").each(function(){
				var value = $(this).next().val();
				$.qp.common.buildInputList($InputForm,$(this));
				$(this).val(value);
			});
		});
		$("#whereForm").find("select[name$='.functionCode']").each(function(){
			var $conditionSelect = $(this).closest("table").find("[name$='.conditionType']");
			if($(this).val()){
				$conditionSelect.val("0");
				$conditionSelect.change();
				$conditionSelect.prop("disabled",true);
				var $input = $("<input>");
				$input.attr("type","hidden");
				$input.attr("name",$conditionSelect.attr("name"));
				$input.val("0");
				$conditionSelect.after($input);
			}
		});
		$("[id='whereForm']").find(">tbody>tr").each(function(){
			$(this).find("input[name$='.leftColumnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.leftTableId']").val());
			$(this).find("input[name$='.rightColumnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.rightTableId']").val());
		});

		$("[id='whereForm']").find("select[name$='whereForm[0].logicCode']").each(function(){
			$(this).val("");
			//$(this).prop("disabled",true);
			$(this).hide();
			$(this).next(".qp-required-field").hide();
		});

		$("#tab-sql-design-insert-update #whereForm,#tab-sql-design-delete #whereForm").find(">tbody>tr").each(function(){
			$(this).find("tr:eq(2)").remove();
		});
		$("#tab-sql-design-insert-update #whereForm,#tab-sql-design-delete #whereForm").find("[name$='.leftTableIdAutocomplete'],[name$='.rightTableIdAutocomplete']").each(function(){
			$(this).attr("sourceType","local");
			$(this).attr("sourceCallback","$.qp.sqlbuilder.whereIntoSourceCallback");
		});
		$("#qp-viewdesign #whereForm select[name$='.conditionType'] option[value='2']").remove();
		$$module.groupTypeChange($("#whereForm"));
	};
	$$module.initAutocompleteOutput = function(){
		$("[id^='outputForm']").find("[name$='ColumnAutocomplete']").each(function(){
			$(this).attr("sourceType",'local');
			$(this).attr("sourceCallback",'$.qp.sqlbuilder.outputFormSourceCallback');
		});
	};
	$$module.outputFormSourceCallback = function(param){
		var results = [];
		$("#selectForm").find("tbody>tr").each(function(){
			if($(this).find("input[name$='.isSelected']").is(":checked")){
				var columnName = $(this).find("[name$='.columnIdAutocomplete']").val();
				var columnCode = $(this).find("[name$='.columnCode']").val();
				var label = '';
				var functionCode = $(this).find("[name$='.functionCode'] option:selected").text();
				var functionValue = $(this).find("[name$='.functionCode'] option:selected").val();
				var checkAggregateNeedConvert = functionValue in AGGREGATE_TO_JAVA_TYPES  ? true : false;
				var functionValueConvert = checkAggregateNeedConvert ? AGGREGATE_TO_JAVA_TYPES[functionValue] : '';
				if($(this).find("[name$='.functionCode'] option:selected").val()){
					label+=functionCode+"(["+$(this).find("[name$='.tableIdAutocomplete']").val()+"].["+columnName+"])";
				} else {
					label+="["+$(this).find("[name$='.tableIdAutocomplete']").val()+"].["+columnName+"]";
				}
				if(columnName && label.toLowerCase().indexOf(param.searchKey.toLowerCase()) > -1){
					results.push({
						"optionLabel" : label,
						"optionValue" : $(this).find("[name$='.itemSeqNo']").val(),
						"output01" : PHYSICAL_TO_JAVA_TYPES[$(this).find("[name$='.dataType']").val()],
						"output02" : columnName,
						"output03" : columnCode,
						"output04" : functionValueConvert != '' ? functionValueConvert : ''
					});
				}
			}
		});
		return results;
	};
	$$module.outputFormMappingColumnChange = function(obj,a,b){
		$(obj.target).removeClass("qp-missing");
		var dataType = 9;
/*		var $dataTypeSelect = $(obj.target).closest("tr").find("select[name$='.dataType']");
		var $dataTypeLabel = $dataTypeSelect.next("label");
		var $dataTypeHidden = $dataTypeSelect.nextAll("input");*/

		if(obj.item){
			dataType = obj.item.output01;
			var columnName = obj.item.output02;
			var columnCode = obj.item.output03;
			var functionValue = obj.item.output04;
			if(!$(obj.target).closest("tr").find("input[name$='Name']").val() || !$(obj.target).closest("tr").find("input[name$='Code']").val()){
				$(obj.target).closest("tr").find("input[name$='Name']").val(columnName);
				$(obj.target).closest("tr").find("input[name$='Code']").val(columnCode);
			}
			if(!!obj.item.output04 && obj.item.output04 != ''){
				dataType = functionValue;
			}
			/*$dataTypeSelect.hide();
			var $dataTypeInput = $(obj.target).closest("tr").find("input[name$='.dataType']");
			if($dataTypeInput.length==0){
				$dataTypeHidden = $("<input type=\"hidden\">").attr("name",$dataTypeSelect.attr("name"));
				$dataTypeLabel = $("<label>");
				$dataTypeSelect.after($dataTypeHidden);
				$dataTypeSelect.after($dataTypeLabel);
			}

			$dataTypeLabel.text($(obj.target).closest("tr").find("select[name$='.dataType'] option[value='"+dataType+"']").text());

			$dataTypeSelect.prop("disabled",true);
			$dataTypeSelect.hide();
			$dataTypeHidden.prop("disabled",false);
			$dataTypeHidden.show();
			$dataTypeLabel.show();*/

			//change value of select data type for validate
			var selectBox = $(obj.target).closest("tr").find("select[name$='.dataType']");
			if(OBJECT_DATATYPE.isNotOjectDataType(selectBox.val())){
				$(selectBox).val(dataType);
			}

			//set columnId for outputRow
			var mappingColumn = $(obj.target).closest("tr").find("input[type=hidden][name$='.mappingColumn']").val();
			var itemSeqNo = $("#selectForm input[type=hidden][name$='.itemSeqNo'][value='"+mappingColumn+"']");
			var columnId = itemSeqNo.closest("tr").find("input[type=hidden][name$='.columnId']").val();
			var row = $(obj.target).closest("tr");
			if(row.attr("data-ar-rgroup") == "" || row.attr("data-ar-rgroup") == '0'){
				var columnIdOuput = $(row).find("input[type=hidden][name$='.columnId']")
				if(columnIdOuput.val() == ""){
					columnIdOuput.val(columnId);
				}
			}
			
			//Validate mapping column
			$$module.validateMappingColumn(obj.target);

			//if mapping column not match data type, yellow background display

		} /*else {
			$dataTypeSelect.prop("disabled",false);
			$dataTypeSelect.show();
			$dataTypeHidden.prop("disabled",true);
			$dataTypeHidden.hide();
			$dataTypeLabel.hide();
		}*/

	};

	/*
	 * bangnl
	 * remove backgourd yellow when data type match with mapping column
	 */
	$$module.validateMappingColumn = function(autoComplete){
		var sequence = $(autoComplete).closest("td").find("input[name$='.mappingColumn']").val();
		var typeJava = PHYSICAL_TO_JAVA_TYPES[MAP_SEQUENCE_FROM_SELECT[sequence]];
		if(!typeJava){
			return;
		}
		
		if($$module.isAggregate(autoComplete)){
			var valAggregate = $$module.validateAggregateFunction(autoComplete);
			if(!valAggregate){
				$(autoComplete).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
				return;
			}
			$(autoComplete).closest("tr").css({"background": ""});
			return;
		}
		
		if(!!sequence && typeJava == $(autoComplete).closest("tr").find('[name$=".dataType"]').val()) {
			$(autoComplete).closest("tr").css({"background": ""});
			return;
		}
		$(autoComplete).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
	};
	
	$$module.isAggregate = function(autoComplete){
		if(autoComplete.length == 0){
			return false;
		}
		if($(autoComplete).val() == ""){
			return false;
		}
		var sqItem = $(autoComplete).closest("td").find("input[type=hidden][name$='.mappingColumn']").val();
		var selectFunction = $("#qp-sqldesign #selectForm tbody tr input[type=hidden][name$='.itemSeqNo'][value='"+sqItem+"']").closest("tr").find("select");
		if(selectFunction.val() != ""){
			return true;
		}
		return false;
	}
	
	$$module.validateAggregateFunction = function(autoComplete){
		var sqItem = $(autoComplete).closest("td").find("input[type=hidden][name$='.mappingColumn']").val();
		var selectFunction = $("#qp-sqldesign #selectForm tbody tr input[type=hidden][name$='.itemSeqNo'][value='"+sqItem+"']").closest("tr").find("select");
		if(selectFunction.val() == ""){
			return true;
		}
		var datatype = $(autoComplete).closest("tr").find('[name$=".dataType"]').val();
		var typeJava = PHYSICAL_TO_JAVA_TYPES[MAP_SEQUENCE_FROM_SELECT[sqItem]];
		var aggregates = selectFunction.val() in AGGREGATE_TO_JAVA_TYPES ? AGGREGATE_TO_JAVA_TYPES[selectFunction.val()] : typeJava;
		if(datatype != aggregates){
			return false;
		}
		return true;
	}

	$$module.resultColumnSourceCallback = function(param){
		var results = [];
		var aggregates = [];
		$("#selectForm").find("tbody>tr").each(function(){
			var columnName = $(this).find("[name$='.columnIdAutocomplete']").val();
			var label = '';
			var functionCode = $(this).find("[name$='.functionCode'] option:selected").text();
			var functionValue = $(this).find("[name$='.functionCode'] option:selected").val();
			if($(this).find("[name$='.functionCode'] option:selected").val()){
				label+=functionCode+"(["+$(this).find("[name$='.tableIdAutocomplete']").val()+"].["+columnName+"])";
			} else {
				label+="["+$(this).find("[name$='.tableIdAutocomplete']").val()+"].["+columnName+"]";
			}
			if(columnName && label.toLowerCase().indexOf(param.searchKey.toLowerCase()) > -1){
				var option = {"optionLabel" : label,
						"optionValue" : $(this).find("[name$='.itemSeqNo']").val()};
				if(functionValue != ''){
					aggregates.push(option);
				}else{
					results.push(option);
				}
			}
		});
		if(aggregates.length > 0){
			return aggregates;
		}
		return results;
	};
	$$module.whereIntoSourceCallback = function(param,autocomplete){
		var results = [];
		$(autocomplete).closest("[id^='tab-sql-design']").find("#intoForm [name='intoForm.tableIdAutocomplete']").each(function(){
			var $tableInput = $(this);
			var tableName = $tableInput.val();
			if(tableName && tableName.toLowerCase().indexOf(param.searchKey.toLowerCase()) > -1){
				results.push({
					"optionLabel" : tableName,
					"optionValue" :$tableInput.next().val()
				});
			}
		});
		return results;
	};
	$$module.columnAutocompleteChange = function(obj){
		$("[id^='outputForm']").find("[name$='Column'][value='"+$(obj.target).closest("tr").attr("data-ar-rgroupindex")+"']")
		.nextAll('.dropdown-toggle:first').each(function(){
			$.qp.removeAutocompleteData($(this));
		});
	};
	$$module.initShowHidePattern = function(isInit){
		if($("#qp-sqldesign [name$='.sqlPattern']").val()=='0'){
			$("#qp-sqldesign #tab-sql-design-select").show();
			$("#qp-sqldesign #tab-sql-design-select *").removeAttr("disabled");

			$("#qp-sqldesign #tab-sql-design-insert-update *").attr("disabled","disabled");
			$("#qp-sqldesign #tab-sql-design-insert-update").hide();

			$("#qp-sqldesign #tab-sql-design-delete *").attr("disabled","disabled");
			$("#qp-sqldesign #tab-sql-design-delete").hide();

			$("a[href='#tab-output']").show();
			$("#tab-output *").removeAttr("disabled","disabled");

			if(!isInit){
				$("[id='orderByForm']").find(">tbody>tr").remove();
				$("[id='whereForm']").find(">tbody>tr").remove();
				$("[id='selectForm']").find(">tbody>tr").remove();
				$("[id='fromForm']").find(">tbody>tr:gt(1)").remove();
				$("[id='fromForm']").find("input,select").val('');
				$("[id='valueForm']").find(">tbody>tr").remove();
				$("[id='intoForm']").find("input").val('');
			}
		} else if($("#qp-sqldesign [name$='.sqlPattern']").val()=='1' || $("#qp-sqldesign [name$='.sqlPattern']").val()=='2'){
			$("#qp-sqldesign #tab-sql-design-select *").attr("disabled","disabled");
			$("#qp-sqldesign #tab-sql-design-select").hide();

			$("#qp-sqldesign #tab-sql-design-insert-update").show();
			$("#qp-sqldesign #tab-sql-design-insert-update *").removeAttr("disabled");

			$("#qp-sqldesign #tab-sql-design-delete *").attr("disabled","disabled");
			$("#qp-sqldesign #tab-sql-design-delete").hide();

			if($("#qp-sqldesign [name$='.sqlPattern']").val()=='1'){
				$("#qp-sqldesign #tab-sql-design-insert-update").find("#whereForm").closest(".panel").find("*").attr("disabled","disabled");
				$("#qp-sqldesign #tab-sql-design-insert-update").find("#whereForm").closest(".panel").hide();
				$("#valueForm").find("select[name$='.valueType']").each(function(){
					var options = $(this).find("option");
					$$module.hideOptionEntityOfValueType(options);
				});
			} else {
				$("#qp-sqldesign #tab-sql-design-insert-update").find("#whereForm").closest(".panel").find("*").removeAttr("disabled");
				$("#qp-sqldesign #tab-sql-design-insert-update").find("#whereForm").closest(".panel").show();
			}

			if($("a[href='#tab-output']").closest("li").has("active")){
				$("a[href='#tab-sql-design']").tab("show");
			}
			
			$("a[href='#tab-output']").show();
			$("#tab-output *").removeAttr("disabled","disabled");
		
			
			if(!isInit){
				$("[id='orderByForm']").find(">tbody>tr").remove();
				$("[id='whereForm']").find(">tbody>tr").remove();
				$("[id='selectForm']").find(">tbody>tr").remove();
				$("[id='fromForm']").find(">tbody>tr:gt(1)").remove();
				$("[id='fromForm']").find("input,select").val('');
				$("[id='valueForm']").find(">tbody>tr").remove();
				$("[id='intoForm']").find("input").val('');
			}
		} else if($("#qp-sqldesign [name$='.sqlPattern']").val()=='3'){
			$("#qp-sqldesign #tab-sql-design-select *").attr("disabled","disabled");
			$("#qp-sqldesign #tab-sql-design-select").hide();

			$("#qp-sqldesign #tab-sql-design-insert-update").hide();
			$("#qp-sqldesign #tab-sql-design-insert-update *").attr("disabled","disabled");

			$("#qp-sqldesign #tab-sql-design-delete *").removeAttr("disabled");
			if(typeof dbMsgSource != 'undefined'){
				$("#qp-sqldesign #tab-sql-design-delete #intoForm").closest(".panel").find(".qp-heading-text").text(dbMsgSource['sc.autocomplete.0041']);
			}
			$("#qp-sqldesign #tab-sql-design-delete").show();

			if($("a[href='#tab-output']").closest("li").has("active")){
				$("a[href='#tab-sql-design']").tab("show");
			}
			/*$("a[href='#tab-output'").hide();
			$("#tab-output *").attr("disabled","disabled");*/
			$("a[href='#tab-output']").show();
			$("#tab-output *").removeAttr("disabled","disabled");
			if(!isInit){
				$("[id='orderByForm']").find(">tbody>tr").remove();
				$("[id='whereForm']").find(">tbody>tr").remove();
				$("[id='selectForm']").find(">tbody>tr").remove();
				$("[id='fromForm']").find(">tbody>tr:gt(1)").remove();
				$("[id='fromForm']").find("input,select").val('');
				$("[id='valueForm']").find(">tbody>tr").remove();
				$("[id='intoForm']").find("input").val('');
			}
		}
		//$$module.showHideSynchronizeWithSqlPattern();
		if(!isInit){
			$.qp.common.initOuputForInsertUpdateDelete();
		}
	};
	
	
	$$module.initSelectForm = function(){
		$("#selectForm").find(">tbody>tr").each(function(){
			$(this).find("input[name$='.columnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.tableId']").val());
		});
		$("#showHideUncheckedColumnsAnchor").click(function(){
			if($(this).attr("data-toggle")=="show"){
				$(this).attr("data-toggle","hide");
				var message = $.qp.getModuleMessage("sc.sqldesign.0027");
				$(this).text(message);
				$("#selectForm>tbody>tr").each(function(){
					if($(this).find("input[type=checkbox][name$='.isSelected']").not(":checked").length>0){
						$(this).hide();
					}
				});

			} else {
				$(this).attr("data-toggle","show");
				var message = $.qp.getModuleMessage("sc.sqldesign.0028");
				$(this).text(message);
				$("#selectForm>tbody>tr").each(function(){
					//if($(this).find("input[type=checkbox][name$='.isSelected']").not(":checked").length>0){
						$(this).show();
					//}
				});
			}

		});
		$("#checkAllAnchor").change(function(obj){
			if($(obj.target).is(":checked")){
				$("#selectForm>tbody>tr").show();
				$(obj.target).closest("#selectForm").find("input[name$='.isSelected']").each(function(){
					$(this).prop("checked",true);
					$(this).trigger('change');
				});
			} else {
				$(obj.target).closest("#selectForm").find("input[name$='.isSelected']").each(function(){
					$(this).prop("checked",false);
					$(this).trigger('change');
				});
			}
		});
		var checkCount = $("#selectForm").find("[name$='.isSelected'][type=checkbox]").length;
		if($("#selectForm").find("[name$='.isSelected'][type=checkbox]:checked").length==checkCount & checkCount>0){
			$("#checkAllAnchor").prop("checked",true);
		}
	};
	$$module.initHavingForm = function(){
		$("#havingForm").find(">tbody>tr").each(function(){
			$(this).find("input[name$='.columnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.tableId']").val());
		});
		$("#havingForm").find("select[name$='havingForm[0].logicCode']").each(function(){
			$(this).val("");
			//$(this).prop("disabled",true);
			$(this).hide();
			$(this).next(".qp-required-field").hide();
		});
	};
	$$module.initFromForm = function(){
		$("#fromForm").find(">tbody>tr").each(function(){
			$(this).find("input[name$='.columnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.tableId']").val());
			$(this).find("input[name$='.joinColumnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.joinTableId']").val());
			$(this).find(".joinColumnsFormToggle").click(function(){
				var $glyph = $(this);
				$glyph.toggleClass("glyphicon-eye-close");
				$glyph.toggleClass("glyphicon-eye-open");
				$glyph.closest("td").next().find(">div").toggle();
				if($glyph.hasClass("glyphicon-eye-open")){
					$(this).next("input").val("false");
				} else {
					$(this).next("input").val("true");
				}
			})
		});
		$("#fromForm").find("input[name$='ableIdAutocomplete']").attr("arg02",$("input[name='autocompleteForm.projectId']").val());

		$("table[id='joinPairTable']").each(function(){
			$(this).find("select[name$='joinColumnsForm[0].logicCode']").each(function(){
				$(this).val("");
				//$(this).prop("disabled",true);
				$(this).hide();
				$(this).next(".qp-required-field").hide();
			});
		});
	};
	$$module.initGroupByForm = function(){
		$('#groupByForm').find("tbody").sortable({
			helper : function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			update : function(event, ui) {
				$.qp.ar.reArrayIndex($('#groupByForm'));
			},
			items : 'tr',
			cursor : 'move',
			handle : '.sortable'
		});
		$("#groupByForm").find(">tbody>tr").each(function(){
			$(this).find("input[name$='.columnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.tableId']").val());
		});
	};
	$$module.initOrderByForm = function(){
		$('#orderByForm').find("tbody").sortable({
			helper : function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			update : function(event, ui) {
				$.qp.reArrayIndex($('#orderByForm'));
			},
			items : 'tr',
			cursor : 'move',
			handle : '.sortable'
		});

		$("#orderByForm").find(">tbody>tr").each(function(){
			$(this).find("input[name$='.columnIdAutocomplete']").attr("arg01",$(this).find("input[name$='.tableId']").val());
		});
	};
	$$module.initSelectedTables = function(){
		if(typeof window.selectedTables == 'undefined') {
			window.selectedTables = {};
		}

		$("#fromForm").find("input[name$='.tableIdAutocomplete']").each(function(){
			var tableName = $(this).val();
			var initTableId = $(this).next().val();
			var initTableName = $(this).next().attr("name");
			if(initTableId!=''){
				window.selectedTables[initTableName]=initTableId;
			} else {
				delete window.selectedTables[initTableName];
			}
		});
		$("#fromForm").find("input[name$='.joinTableIdAutocomplete']").each(function(){
			var tableName = $(this).val();
			var initTableId = $(this).next().val();
			var initTableName = $(this).next().attr("name");
			if(initTableId!=''){
				window.selectedTables[initTableName]=initTableId;
			} else {
				delete window.selectedTables[initTableName];
			}
		});
		$$module.setArgumentForAllTableAC();

	};
	$$module.beforeSubmitConversion = function(){
		sqlGenerateByPattern(true);
	};

	$$module.addMessageCustom = function(lstbtn, msgValidateMappingDataTypeOutputForm){
		var messageDefault =  msgValidateMappingDataTypeOutputForm + " " + fcomMsgSource["inf.sys.0015"];
		$(lstbtn).each(function(){
			messageId = $(this).attr("messageid") || "";
			message = messageId == "" ? messageDefault : msgValidateMappingDataTypeOutputForm + " " + fcomMsgSource[messageId];
			$(this).attr("messageCustom", message);
		});
	}

	$$module.removeMessageCustom = function(lstbtn){

		$(lstbtn).each(function(){
			var messageCustom = $(this).attr("messageCustom") || "";
			if(messageCustom != ""){
				$(this).removeAttr("messageCustom");
			}
		});

	}

	$$module.validateEmptySqlName = function(){
		var message = "";
		var sqlDesignName = $("input[name$='.sqlDesignName']").val().trim();
		if(!sqlDesignName){
			message = fcomMsgSource["err.sys.0025"].replace("{0}", dbMsgSource["sc.sqldesign.0010"])+"\n";
		}
		return message;
	}
	
	$$module.validateEmptySqlCode = function(){
		var message = "";
		var sqlDesignName = $("input[name$='.sqlDesignCode']").val().trim();
		if(!sqlDesignName){
			message = fcomMsgSource["err.sys.0025"].replace("{0}", dbMsgSource["sc.sqldesign.0011"])+"\n";
		}
		return message;
	}
	
	$$module.validateBeforeSubmit = function() {

		var isValid = true;
		var value = $($('select[id="sqlDesignForm.sqlPattern"]')).val();
		var errMsg = "";
		errMsg += $$module.validateEmptySqlName();
		errMsg += $$module.validateEmptySqlCode();
		switch(value) {

		case "0":
			/* SELECT statement*/
			var messageConfirm = $.qp.common.validation.validateInputBeanInWhereCondition() || "";
			messageConfirm +=  $.qp.common.validation.validateMappingDataTypeOutputForm("#outputForm-sql-builder", "mappingColumn");

			errMsg += $.qp.common.validation.validateInputForm('#inputForm');
			errMsg += $.qp.common.validation.validateOutputForm("#outputForm-sql-builder");
			//errMsg += $.qp.common.validation.validateMappingDataTypeOutputForm("#outputForm-sql-builder", "mappingColumn");
			errMsg += $.qp.common.validation.validateRequiredFromForm();
			errMsg += $.qp.common.validation.validateMappingDataTypeFromForm();
			errMsg += $.qp.common.validation.validateSelectForm();
			errMsg += $.qp.common.validation.validateWhereRequire();
			errMsg += $.qp.common.validation.validateMappingDataTypeWhere();
			errMsg += $.qp.common.validation.validateGroup();
			errMsg += $.qp.common.validation.validateRequireOrderForm();

			//Add message err for confirm dialog when validate mapping data type output form invalid
			var lstbtn = $("button[type=submit]");
			if(messageConfirm != ""){
				$$module.addMessageCustom(lstbtn, messageConfirm);
			}else{
				$$module.removeMessageCustom(lstbtn);
			}



			if(errMsg != "") {
				$.qp.showAlertModal(errMsg);
				isValid = false;
			}

			break;
		case "1":
			/* INSERT statement */
			errMsg += $.qp.common.validation.validateInputForm('#inputForm');
			errMsg += $.qp.common.validation.validateRequireTableForm('#tab-sql-design-insert-update');
			errMsg += $.qp.common.validation.validateRequireValueForm();
			errMsg += $.qp.common.validation.validateMappingValueForm();
			if(errMsg != "") {
				$.qp.showAlertModal(errMsg);
				isValid = false;
			}

			break;
		case "2":
			/* UPDATE statement */
			errMsg += $.qp.common.validation.validateInputForm('#inputForm');
			errMsg += $.qp.common.validation.validateRequireTableForm('#tab-sql-design-insert-update');
			errMsg += $.qp.common.validation.validateRequireValueForm();
			errMsg += $.qp.common.validation.validateMappingValueForm();
			errMsg += $.qp.common.validation.validateWhereRequire('#tab-sql-design-insert-update');
			errMsg += $.qp.common.validation.validateMappingDataTypeWhere('#tab-sql-design-insert-update');
			errMsg += $.qp.common.validation.validateGroup();

			if(errMsg != "") {
				$.qp.showAlertModal(errMsg);
				isValid = false;
			}

			break;
		case "3":
			/* DELETE statement */
			errMsg += $.qp.common.validation.validateInputForm('#inputForm');
			errMsg += $.qp.common.validation.validateRequireTableForm('#tab-sql-design-delete');
			errMsg += $.qp.common.validation.validateWhereRequire('#tab-sql-design-delete');
			errMsg += $.qp.common.validation.validateMappingDataTypeWhere('#tab-sql-design-delete');
			errMsg += $.qp.common.validation.validateGroup();

			if(errMsg != "") {
				$.qp.showAlertModal(errMsg);
				isValid = false;
			}

			break;
		default :
			// For autocomplete design
			errMsg += $.qp.common.validation.validateInputForm('#inputForm');
			errMsg += $.qp.common.validation.validateOutputForm("#outputForm");
			errMsg += $.qp.common.validation.validateRequireCheckedOutputForm("#outputForm");
			errMsg += $.qp.common.validation.validateRequireCheckedDisplayOutputForm("#outputForm");
			errMsg += $.qp.common.validation.validateRequiredFromForm();
			errMsg += $.qp.common.validation.validateMappingDataTypeFromForm();
			errMsg += $.qp.common.validation.validateSelectForm();
			errMsg += $.qp.common.validation.validateWhereRequire();
			errMsg += $.qp.common.validation.validateMappingDataTypeWhere();
			errMsg += $.qp.common.validation.validateGroup();
			errMsg += $.qp.common.validation.validateRequireOrderForm();

			if(errMsg != "") {
				$.qp.showAlertModal(errMsg);
				isValid = false;
			}

			break;
		}

		if (!isValid) {
			return false;
		}

		MAP_COLUMN_ID_BK = [];

		return true;
	};

	$$module.generateSqlExplanation = function(){
		var explanation = '';
		var sqlPattern = $("[id='sqlDesignForm.sqlPattern']").val();
		var tables = '';

		switch(sqlPattern || '0'){
		case '0':
			$("#tab-sql-design-select #fromForm").find("[name$='.tableIdAutocomplete']:nth(0),[name$='.joinTableIdAutocomplete']").each(function(){
				if($(this).val()){
					if(tables){
						tables += ", ";
					}
					tables += $(this).val();
				}
			});
			if(tables!=''){
				if($("#tab-sql-design-select #whereForm").find(">tbody>tr").length>0){
					explanation = dbMsgSource['sc.sqldesign.0037'].replace("{0}",tables);
				} else {
					explanation = dbMsgSource['sc.sqldesign.0036'].replace("{0}",tables);
				}
			}
			break;
		case '1':
			$("#tab-sql-design-insert-update #intoForm").find("[name$='.tableIdAutocomplete']").each(function(){
				if($(this).val()){
					if(tables){
						tables += ", ";
					}
					tables += $(this).val();
				}
			});
			if(tables!=''){
				explanation = dbMsgSource['sc.sqldesign.0038'].replace("{0}",tables);
			}
			break;
		case '2':
			$("#tab-sql-design-insert-update #intoForm").find("[name$='.tableIdAutocomplete']").each(function(){
				if($(this).val()){
					if(tables){
						tables += ", ";
					}
					tables += $(this).val();
				}
			});
			if(tables!=''){
				if($("#tab-sql-design-insert-update #whereForm").find(">tbody>tr").length>0){
					explanation = dbMsgSource['sc.sqldesign.0042'].replace("{0}",tables);
				} else {
					explanation = dbMsgSource['sc.sqldesign.0039'].replace("{0}",tables);
				}
			}
			break;
		case '3':
			$("#tab-sql-design-delete #intoForm").find("[name$='.tableIdAutocomplete']").each(function(){
				if($(this).val()){
					if(tables){
						tables += ", ";
					}
					tables += $(this).val();
				}
			});
			if(tables!=''){
				if($("#tab-sql-design-delete #whereForm").find(">tbody>tr").length>0){
					explanation = dbMsgSource['sc.sqldesign.0041'].replace("{0}",tables);
				} else {
					explanation = dbMsgSource['sc.sqldesign.0040'].replace("{0}",tables);
				}
			}
			break;
		}
//		var remark = $("#autocompleteDesignForm").find("[id='autocompleteForm.remark']");
//		if(remark.length >0){
//			$(remark).val(explanation);
//		}
	};

	$$module.setDisplayOperatorCode = function(obj) {
		var flgdirectCall = true;
		var $tr = $(obj.target).closest('tr');
		var value;
		if($tr.length != 0) {
			value = $(obj.target).val();
		} else {
			$tr = $(obj).closest('tr');
			value = $(obj).next().val();
			flgdirectCall = false;
		}

		if(value == "") {
			$tr.find('td:eq(2)>select>option').show();

		} else {
			var datatype;

			if(flgdirectCall) {
				datatype = obj.item.output03;
			} else {
				// In the case reload when return error from sever side
				datatype = MAP_COLUMN_ID[value];
			}

			var $select = $tr.find('td:eq(2)>select');

			$select.children().hide();
			$$module.setDisplayOperatorByDataType(datatype, $select);
		
		}
	};
	
	$$module.setDisplayOperatorByDataType = function(datatype, $select){
		switch (parseInt(datatype)) {

		case PHYSICAL_DATATYPE.INTEGER :
		case PHYSICAL_DATATYPE.SMALLINT :
		case PHYSICAL_DATATYPE.DECIMAL :
		case PHYSICAL_DATATYPE.BIGINT :
		case PHYSICAL_DATATYPE.SERIAL :
		case PHYSICAL_DATATYPE.BIGSERIAL :
		case PHYSICAL_DATATYPE.NUMERIC :
		case PHYSICAL_DATATYPE.DATE :
		case PHYSICAL_DATATYPE.CURRENCY :
		case PHYSICAL_DATATYPE.TIME :
		case PHYSICAL_DATATYPE.DATETIME :
		case PHYSICAL_DATATYPE.BINARY :
		case PHYSICAL_DATATYPE.FLOAT :
		case PHYSICAL_DATATYPE.TIMESTAMP :
		case PHYSICAL_DATATYPE.DOUBLE:
			$select.find('option:lt(7)').show();
			$select.find('option:eq(6)').hide();
			if(parseInt($select.val()) > 5) {
				$select.val('');
			}

			break;

		case PHYSICAL_DATATYPE.CHARACTER_VARYING :
		case PHYSICAL_DATATYPE.TEXT :
		case PHYSICAL_DATATYPE.CHAR :
			$select.find('option:eq(0),option:eq(1),option:eq(6),option:eq(7)').show();
			if(parseInt($select.val()) != 0 && parseInt($select.val()) != 5 && parseInt($select.val()) != 6) {
				$select.val('');
			}

			break;

		case PHYSICAL_DATATYPE.BOOLEAN :
			$select.find('option:eq(0),option:eq(1),option:eq(6)').show();
			if(parseInt($select.val()) != 0 && parseInt($select.val()) != 5) {
				$select.val('');
			}
			break;

		default:
			$select.children().show();
			$select.val('');
			break;
		}
	}

	$$module.setDisplayFunctionCode = function(obj, datatype) {
		var $select = $(obj);
		$select.find('option:gt(0)').hide();

		if(MAP_COLUMN_ID_BK.length > 0) {
			var clmId = $(obj).closest('tr').find('[name$=".columnId"]').val();

		}

		switch (parseInt(datatype)) {

		case PHYSICAL_DATATYPE.INTEGER :
		case PHYSICAL_DATATYPE.SMALLINT :
		case PHYSICAL_DATATYPE.DECIMAL :
		case PHYSICAL_DATATYPE.BIGINT :
		case PHYSICAL_DATATYPE.SERIAL :
		case PHYSICAL_DATATYPE.BIGSERIAL :
		case PHYSICAL_DATATYPE.NUMERIC :
		case PHYSICAL_DATATYPE.CURRENCY :
		case PHYSICAL_DATATYPE.BINARY :
		case PHYSICAL_DATATYPE.FLOAT :
		case PHYSICAL_DATATYPE:DOUBLE:	
			$select.find('option').show();
			break;
		case PHYSICAL_DATATYPE.DATE :	
		case PHYSICAL_DATATYPE.DATETIME :	
		case PHYSICAL_DATATYPE.TIMESTAMP :
		case PHYSICAL_DATATYPE.TIME :
			$select.find('option:gt(1):lt(5)').show();
			break;
		case PHYSICAL_DATATYPE.CHARACTER_VARYING :
		case PHYSICAL_DATATYPE.TEXT :
		case PHYSICAL_DATATYPE.CHAR :
		case PHYSICAL_DATATYPE.BOOLEAN :
			$select.find('option:gt(1):lt(3)').show();
			break;

		default:
			$select.children().show();
			$select.val('');
			break;
		}
	};
	
	$$module.changeSourceValueItem = function(params, autocomplete){
		var keySearch = ($(autocomplete).val());
		var data = $$module.getColumnsObjectTable(params.arg01,keySearch);
		
		return data;
	}
	
	$$module.changeDataTypeColumnValueItem = function(obj) {
		if($(obj.target).val() != ""){
			$(obj.target).closest('tr').find('[name$=".dataType"]').val(obj.item.output03);
			$$module.setPatternFormat(obj.item.output03, $(obj.target).closest('tr'));
		}else {
			$(obj.target).closest('tr').find('[name$=".dataType"]').val('');
		}

		return;
	};

	$$module.setPatternFormat = function(datatype, row){
		//TODO setpattern format
		var patternFormat = $(row).find("input[type=hidden][name$='.patternFormat']");
		if(!PHYSICAL_DATATYPE.isDateTimeType(datatype)){
			patternFormat.val("");
			return;
		}
		switch (eval(datatype)) {
			case PHYSICAL_DATATYPE.DATE:
				$(patternFormat).val(keyDateFormat);
				break;
			case PHYSICAL_DATATYPE.TIMESTAMP:	
			case PHYSICAL_DATATYPE.DATETIME:
				$(patternFormat).val(keyDateTimeFormat);
				break;
			case PHYSICAL_DATATYPE.TIME:
				$(patternFormat).val(keyTimeFormat);
				break;	
		}
	}
	
	$$module.callSetDisplayFunctionCode = function(obj) {
		var conditionSelect;
		var $select = $(obj.target).closest('tr').next().find('select[name$=".functionCode"]');
		var datatype;
		var value;

		if($select.length == 0){
			$select = $(obj.target).closest('tr').next().find('select[name$=".functionCode"]');
			if($(obj.target).next().val() != ""){
				datatype = MAP_COLUMN_ID[$(obj.target).next().val()];
			}
		} else {
			if(obj.type){
				if(obj.item){
					datatype = obj.item.output03;
					$(obj.target).closest('tr').find('[name$=".dataType"]').val(datatype);
					$$module.setPatternFormat(datatype, $(obj.target).closest('tr'));
				} else {
					$(obj.target).closest('tr').find('[name$=".dataType"]').val('');
				}
			} else {
				datatype = $(obj.target).closest('tr').find('[name$=".dataType"]').val();
			}
		}
		// In the case of select
		if($('select[name="sqlDesignForm.sqlPattern"]').length == 0
				|| $('select[name="sqlDesignForm.sqlPattern"]').val() == "0") {
			$$module.setDisplayFunctionCode($select, datatype);
			conditionSelect = $select.closest('table').find('select[name$=".conditionType"]');
			$$module.conditionTypeChange($(obj.target).closest("table").find("select[name$='.conditionType']"));
		} else {

			var $select = $(obj.target).closest('table').find('select[name$=".conditionType"]');
			// when click onchangeevent
			if($select.length == 0) {
				$select = $(obj).closest('table').find('select[name$=".conditionType"]');
			} else {
				if($(obj.target).val() != "") {
					$(obj.target).closest('tr').find('[name$=".dataType"]').val(obj.item.output03);
				} else {
					$(obj.target).closest('tr').find('[name$=".dataType"]').val('');
				}
			}

			conditionSelect = $select;
		}
		$$module.changeDisplayFormatInput($(obj.target).closest("table").find("input[name$='.value']").not(":hidden"),$(obj.target).closest("table").find("input[name$='.value2']"),datatype);
		$$module.conditionTypeChange(conditionSelect);
		$$module.activeSelectBoxConditionType(obj.target);
		$$module.clearSqlScirpt();
	};
	
	$$module.activeSelectBoxConditionType = function(autocomplete){
		var table = $(autocomplete).closest("table");
		var slConditionType = table.find("select[name$='.conditionType']");
		if(slConditionType.length == 0){
			return;
		}
		var slFunctionCode = table.find("select[name$='.functionCode']");
		if(!$(slFunctionCode).val()){
			$(slConditionType).prop('disabled', false);
		}
	}

	$$module.initOperatorOptionsOfWhere = function(operaSelect, conditionType) {
		var operatorSelect = $(operaSelect);

		var valueLeftColumnId = $(operatorSelect).closest('table').find('tr:eq(1) [name$=".leftColumnId"]').val();

		if(valueLeftColumnId != "") {

			var datatype = MAP_COLUMN_ID[valueLeftColumnId];

			switch (parseInt(datatype)) {

			case PHYSICAL_DATATYPE.INTEGER :
			case PHYSICAL_DATATYPE.SMALLINT :
			case PHYSICAL_DATATYPE.DECIMAL :
			case PHYSICAL_DATATYPE.BIGINT :
			case PHYSICAL_DATATYPE.SERIAL :
			case PHYSICAL_DATATYPE.BIGSERIAL :
			case PHYSICAL_DATATYPE.NUMERIC :
			case PHYSICAL_DATATYPE.DATE :
			case PHYSICAL_DATATYPE.CURRENCY :
			case PHYSICAL_DATATYPE.TIME :
			case PHYSICAL_DATATYPE.DATETIME :
			case PHYSICAL_DATATYPE.BINARY :
			case PHYSICAL_DATATYPE.FLOAT :
			case PHYSICAL_DATATYPE.DOUBLE :	
			case PHYSICAL_DATATYPE.TIMESTAMP :
				
				$(operatorSelect).find('option[value="6"]').hide();
				if($(operatorSelect).val() == '6') {
					$(operatorSelect).val('');
				}

				break;

			case PHYSICAL_DATATYPE.CHARACTER_VARYING :
			case PHYSICAL_DATATYPE.TEXT :
			case PHYSICAL_DATATYPE.CHAR :
				if(conditionType != undefined && conditionType != "") {
					$(operatorSelect).find('option:gt(1):lt(4)').hide();
					$(operatorSelect).find('option:eq(5)').show();
					$(operatorSelect).find('option:eq(1)').hide();
					$(operatorSelect).find('option[value="7"]').hide();
					if(conditionType == "0") {
						if(parseInt($(operatorSelect).val()) >=1 && parseInt($(operatorSelect).val()) <= 4
								|| $(operatorSelect).val() == "7") {
							$(operatorSelect).val('');
						}
					} else if(conditionType == "1" && parseInt($(operatorSelect).val()) >=1 && parseInt($(operatorSelect).val()) <= 4
							|| parseInt($(operatorSelect).val()) >=8 && parseInt($(operatorSelect).val()) <= 9) {
						$(operatorSelect).val('');
					} else if(conditionType == "2" && parseInt($(operatorSelect).val()) >=1 && parseInt($(operatorSelect).val()) <= 4
							|| parseInt($(operatorSelect).val()) >=8 && parseInt($(operatorSelect).val()) <= 9) {
						$(operatorSelect).val('');
					}
				} else {
					$(operatorSelect).find('option:gt(1):lt(4)').hide();
					$(operatorSelect).find('option[value="7"]').hide();
					if(parseInt($(operatorSelect).val()) >=1 && parseInt($(operatorSelect).val()) <= 4 || $(operatorSelect).val() == "7") {
						$(operatorSelect).val('');
					}
				}

				break;

			case PHYSICAL_DATATYPE.BOOLEAN :

				if(conditionType != undefined && conditionType != "") {
					// Value
					if(conditionType == "0") {
						$(operatorSelect).find('option:gt(1):lt(4)').hide();
						$(operatorSelect).find('option:gt(6):lt(9)').hide();

						if($(operatorSelect).val() != "0" 
							&& $(operatorSelect).val() != "5" && $(operatorSelect).val() != "8" && $(operatorSelect).val() != "9") {
							$(operatorSelect).val('');
						}
					// Entity
					} else if (conditionType == "1") {
						$(operatorSelect).find('option:gt(1):lt(4)').hide();
						$(operatorSelect).find('option:eq(7)').hide();
						if($(operatorSelect).val() != "0" 
							&& $(operatorSelect).val() != "5" && $(operatorSelect).val() != "8" && $(operatorSelect).val() != "9") {
							$(operatorSelect).val('');
						}
					// Parameter
					} else {
						$(operatorSelect).find('option:gt(1):lt(4)').hide();
						$(operatorSelect).find('option:eq(7),option:eq(8)').hide();
						if($(operatorSelect).val() != "0" 
							&& $(operatorSelect).val() != "5" && $(operatorSelect).val() != "8" && $(operatorSelect).val() != "9") {
							$(operatorSelect).val('');
						}
					}
				} else {
					$(operatorSelect).find('option:gt(1):lt(4)').hide();
					$(operatorSelect).find('option:gt(6):lt(7)').hide();
					if(parseInt($(operatorSelect).val()) >=1 && parseInt($(operatorSelect).val()) <= 4
							|| parseInt($(operatorSelect).val()) >=7 && parseInt($(operatorSelect).val()) <= 9) {
						$(operatorSelect).val('');
					}
				}
				$(operatorSelect).find('option:eq(1)').hide();
				$(operatorSelect).find('option:eq(6)').hide();
				$(operatorSelect).find('option:eq(5)').show();
				$(operatorSelect).find('option:eq(8)').show();
				$(operatorSelect).find('option:eq(9)').show();
				break;
			}
		} else {
			$(operatorSelect).children().show();
			$$module.initOperatorOptions($(operatorSelect),conditionType);
		}
	};


	$$module.reloadAllSelect = function() {
		// In the case modify is display
		var scrTypeModifyFlg = false;
		if($('input:hidden[name="sqlDesignForm.sqlDesignId"]') != undefined
				&& $('input:hidden[name="sqlDesignForm.sqlDesignId"]').val() != "") {
			scrTypeModifyFlg = true;
		}
		switch ($('select[name="sqlDesignForm.sqlPattern"]').val()) {
		// Insert
		case "1":
			if(scrTypeModifyFlg) {
				$('#tab-sql-design-insert-update #intoForm').find('>tbody>tr').each(function() {
					var clmId = $(this).find('[name$=".columnId"]').val();
					if(MAP_COLUMN_ID[clmId] != $(this).find('[name$=".dataType"]').val()) {
						$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
						$(this).find('select').val('');
					}
				});
			}

			break;
		// Update
		case "2":
			if(scrTypeModifyFlg) {
				$('#tab-sql-design-insert-update #intoForm').find('>tbody>tr').each(function() {
					var clmId = $(this).find('[name$=".columnId"]').val();
					if(MAP_COLUMN_ID[clmId] != $(this).find('[name$=".dataType"]').val()) {
						$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
						$(this).find('select').val('');
					}
				});
			}

			$('#tab-sql-design-insert-update #whereForm').find('>tbody>tr').each(function(idx) {
				var leftColumnId = $(this).find('table>tbody>tr:eq(1) input[name$=".leftColumnIdAutocomplete"]');
				var argValue = $(this).find('[name$=".rightTableId"]').val();
				if(leftColumnId.next().val() in MAP_COLUMN_ID
						&& MAP_COLUMN_ID[leftColumnId.next().val()] != $(leftColumnId).parent().nextAll('[name$=".dataType"]').val()) {
					// marking style color is not mapping datatype
					$(leftColumnId).closest('tr').css('background', 'none repeat scroll 0 0 #F1F3BB');
					$(this).find('[name$=".value"]').val('');
					$(this).find('[name$=".value2"]').val('');
					$(this).find('select[name$=".arg"]').val('');
					$(this).find('select[name$=".arg2"]').val('');
					$(this).find('[name$=".rightColumnIdAutocomplete"]').attr('arg01',argValue);
					$(this).find('[name$=".rightColumnIdAutocomplete"]').next().val("");
					$.qp.removeAutocompleteData($(this).find('[name$=".rightColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
					$(this).find('[name$=".operatorCode"]').val('');
				}

				$$module.conditionTypeChange($(leftColumnId).closest('tr').find('select[name$=".conditionType"]'));
			});

			break;
		// Delete
		case "3":
			// where form
			$('#tab-sql-design-delete #whereForm').find('>tbody>tr').each(function(idx) {
				var leftColumnId = $(this).find('table>tbody>tr:eq(1) input[name$=".leftColumnIdAutocomplete"]');
				var argValue = $(this).find('[name$=".rightTableId"]').val();

				if(leftColumnId.next().val() in MAP_COLUMN_ID
						&& MAP_COLUMN_ID[leftColumnId.next().val()] != $(leftColumnId).parent().nextAll('[name$=".dataType"]').val()) {
					// marking style color is not mapping datatype
					$(leftColumnId).closest('tr').css('background', 'none repeat scroll 0 0 #F1F3BB');
					$(this).find('[name$=".value"]').val('');
					$(this).find('[name$=".value2"]').val('');
					$(this).find('select[name$=".arg"]').val('');
					$(this).find('select[name$=".arg2"]').val('');
					$(this).find('[name$=".rightColumnIdAutocomplete"]').attr('arg01',argValue);
					$(this).find('[name$=".rightColumnIdAutocomplete"]').next().val("");
					$.qp.removeAutocompleteData($(this).find('[name$=".rightColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
					$(this).find('[name$=".operatorCode"]').val('');
				}

				$$module.conditionTypeChange($(leftColumnId).closest('tr').find('select[name$=".conditionType"]'));
			});

			break;
		// Select
		default:
			// Is autocomplete and sql design select
			// Scan of From form
			$('#fromForm').find('>tbody>tr:gt(0)').each(function(idx) {
				var $pos = $(this).find('table:first>tbody>tr:first');
				if($pos.find('td:first>label').is(':visible')){
					if($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != ""
						&& $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != undefined) {
						var argValue = $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val();
						var $posJoinCond = $pos.find('>td:eq(2)>div');
						$posJoinCond.find('table>tbody>tr').each(function(i) {
							var obj = $(this).find('input[name$=".columnIdAutocomplete"]');
							if(scrTypeModifyFlg) {
								var clmId = $(this).find('[name$=".columnId"]').val();
								if(clmId in MAP_COLUMN_ID
										&& clmId in MAP_COLUMN_ID_BK
										&& MAP_COLUMN_ID[clmId] != MAP_COLUMN_ID_BK[clmId]) {
									// marking style color is not mapping datatype
									$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
									$(this).find('select').val('');
									$(this).find('input[name$=".joinColumnIdAutocomplete"]').attr("arg01", argValue);
									$(this).find('input[name$=".joinColumnIdAutocomplete"]').next().val("");
									$.qp.removeAutocompleteData($(this).find('input[name$=".joinColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
								}
							}

							$$module.setDisplayOperatorCode(obj);
						});
					}
				}
			});
			// Select Form
			$('#selectForm tbody>tr').each(function() {

				if(scrTypeModifyFlg) {
					var clmId = $(this).find('[name$=".columnId"]').val();
					if($(this).find('[name$=".isSelected"]').is(':checked')
							&& clmId in MAP_COLUMN_ID
							&& clmId in MAP_COLUMN_ID_BK
							&& MAP_COLUMN_ID[clmId] != MAP_COLUMN_ID_BK[clmId]) {
						// marking style color is not mapping datatype
						$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
						$(this).find('select').val('');
					}
				}

				$$module.setDisplayFunctionCode($(this).find('select'), $(this).find('[name$=".dataType"]').val());
			});
			// Where from
			$('#whereForm').find('>tbody>tr').each(function(idx) {
				var leftColumnId = $(this).find('table>tbody>tr:eq(1) input[name$=".leftColumnIdAutocomplete"]');
				var argValue = $(this).find('[name$=".rightTableId"]').val();

				if(leftColumnId.next().val() in MAP_COLUMN_ID
						&& leftColumnId.next().val() in MAP_COLUMN_ID_BK
						&& MAP_COLUMN_ID[leftColumnId.next().val()] != MAP_COLUMN_ID_BK[leftColumnId.next().val()]) {
					// marking style color is not mapping datatype
					$(leftColumnId).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
//					$(this).find('[name$=".value"]').val('');
//					$(this).find('[name$=".value2"]').val('');
//					$(this).find('select[name$=".arg"]').val('');
//					$(this).find('select[name$=".arg2"]').val('');
//					$(this).find('[name$=".rightColumnIdAutocomplete"]').attr('arg01',argValue);
//					$(this).find('[name$=".rightColumnIdAutocomplete"]').next().val("");
//					$.qp.removeAutocompleteData($(this).find('[name$=".rightColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
//					$(this).find('[name$=".operatorCode"]').val('');
//					$(this).find('select[name$=".functionCode"]').val('');
//					$$module.whereFunctionCodeChange($(this).find('select[name$=".functionCode"]'));
				}

				$$module.callSetDisplayFunctionCode({target:leftColumnId});
			});

			// Mapping color for output form of sql design
			var $Select = $('#selectForm').find('>tbody>tr').find('td:first>input:checkbox:checked').length;
			if($Select != 0) {
				var mapOutput = [];
				// create map sequence of select
				$.each($('#outputForm-sql-builder').find('>tbody>tr'), function() {
					var autoComplete = $(this).find("input[name$='.mappingColumnAutocomplete']");
					var datatype = $(this).find("select[name$='.dataType']").val();
					if(!!datatype && OBJECT_DATATYPE.isObjectDataType(datatype)){
						$(autoComplete).closest("tr").css({"background": ""});
						return true;
					}
					if($$module.isAggregate(autoComplete)){
						var valAggregate = $$module.validateAggregateFunction(autoComplete);
						if(!valAggregate){
							$(autoComplete).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
						}else{
							$(autoComplete).closest("tr").css({"background": ""});
						}
					}else{
						var sequence = $(this).find("[name$='.mappingColumn']").val();
						if(sequence != undefined && PHYSICAL_TO_JAVA_TYPES[MAP_SEQUENCE_FROM_SELECT[sequence]] != $(this).find('[name$=".dataType"]').val()) {
							$(this).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
						}
					}
					
				});
			}

			break;
		}
	};
	
	$$module.reloadAllSelectClone = function() {
		// In the case modify is display
		var scrTypeModifyFlg = false;
		if($('input:hidden[name="sqlDesignForm.sqlDesignId"]') != undefined
				&& $('input:hidden[name="sqlDesignForm.sqlDesignId"]').val() != "") {
			scrTypeModifyFlg = true;
		}
		switch ($('select[name="sqlDesignForm.sqlPattern"]').val()) {
		// Insert
		case "1":
			if(scrTypeModifyFlg) {
				$('#tab-sql-design-insert-update #intoForm').find('>tbody>tr').each(function() {
					var clmId = $(this).find('[name$=".columnId"]').val();
					if(MAP_COLUMN_ID[clmId] != $(this).find('[name$=".dataType"]').val()) {
						$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
						$(this).find('select').val('');
					}else{
						$(this).css("background","");
					}
				});
			}

			break;
		// Update
		case "2":
			if(scrTypeModifyFlg) {
				$('#tab-sql-design-insert-update #intoForm').find('>tbody>tr').each(function() {
					var clmId = $(this).find('[name$=".columnId"]').val();
					if(MAP_COLUMN_ID[clmId] != $(this).find('[name$=".dataType"]').val()) {
						$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
						$(this).find('select').val('');
					}else{
						$(this).css("background","");
					}
				});
			}

			$('#tab-sql-design-insert-update #whereForm').find('>tbody>tr').each(function(idx) {
				var leftColumnId = $(this).find('table>tbody>tr:eq(1) input[name$=".leftColumnIdAutocomplete"]');
				var argValue = $(this).find('[name$=".rightTableId"]').val();
				if(leftColumnId.next().val() in MAP_COLUMN_ID
						&& MAP_COLUMN_ID[leftColumnId.next().val()] != $(leftColumnId).parent().nextAll('[name$=".dataType"]').val()) {
					// marking style color is not mapping datatype
					$(leftColumnId).closest('tr').css('background', 'none repeat scroll 0 0 #F1F3BB');
					$(this).find('[name$=".value"]').val('');
					$(this).find('[name$=".value2"]').val('');
					$(this).find('select[name$=".arg"]').val('');
					$(this).find('select[name$=".arg2"]').val('');
					$(this).find('[name$=".rightColumnIdAutocomplete"]').attr('arg01',argValue);
					$(this).find('[name$=".rightColumnIdAutocomplete"]').next().val("");
					$.qp.removeAutocompleteData($(this).find('[name$=".rightColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
					$(this).find('[name$=".operatorCode"]').val('');
				}else{
					$(this).css("background","");
				}

				$$module.conditionTypeChange($(leftColumnId).closest('tr').find('select[name$=".conditionType"]'));
			});

			break;
		// Delete
		case "3":
			// where form
			$('#tab-sql-design-delete #whereForm').find('>tbody>tr').each(function(idx) {
				var leftColumnId = $(this).find('table>tbody>tr:eq(1) input[name$=".leftColumnIdAutocomplete"]');
				var argValue = $(this).find('[name$=".rightTableId"]').val();

				if(leftColumnId.next().val() in MAP_COLUMN_ID
						&& MAP_COLUMN_ID[leftColumnId.next().val()] != $(leftColumnId).parent().nextAll('[name$=".dataType"]').val()) {
					// marking style color is not mapping datatype
					$(leftColumnId).closest('tr').css('background', 'none repeat scroll 0 0 #F1F3BB');
					$(this).find('[name$=".value"]').val('');
					$(this).find('[name$=".value2"]').val('');
					$(this).find('select[name$=".arg"]').val('');
					$(this).find('select[name$=".arg2"]').val('');
					$(this).find('[name$=".rightColumnIdAutocomplete"]').attr('arg01',argValue);
					$(this).find('[name$=".rightColumnIdAutocomplete"]').next().val("");
					$.qp.removeAutocompleteData($(this).find('[name$=".rightColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
					$(this).find('[name$=".operatorCode"]').val('');
				}else{
					$(this).css("background","");
				}

				$$module.conditionTypeChange($(leftColumnId).closest('tr').find('select[name$=".conditionType"]'));
			});

			break;
		// Select
		default:
			// Is autocomplete and sql design select
			// Scan of From form
			$('#fromForm').find('>tbody>tr:gt(0)').each(function(idx) {
				var $pos = $(this).find('table:first>tbody>tr:first');
				if($pos.find('td:first>label').is(':visible')){
					if($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != ""
						&& $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != undefined) {
						var argValue = $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val();
						var $posJoinCond = $pos.find('>td:eq(2)>div');
						$posJoinCond.find('table>tbody>tr').each(function(i) {
							var obj = $(this).find('input[name$=".columnIdAutocomplete"]');
							if(scrTypeModifyFlg) {
								var clmId = $(this).find('[name$=".columnId"]').val();
								if(clmId in MAP_COLUMN_ID
										&& clmId in MAP_COLUMN_ID_BK
										&& MAP_COLUMN_ID[clmId] != MAP_COLUMN_ID_BK[clmId]) {
									// marking style color is not mapping datatype
									$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
									$(this).find('select').val('');
									$(this).find('input[name$=".joinColumnIdAutocomplete"]').attr("arg01", argValue);
									$(this).find('input[name$=".joinColumnIdAutocomplete"]').next().val("");
									$.qp.removeAutocompleteData($(this).find('input[name$=".joinColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
								}
							}else{
								$(this).css("background","");
							}

							$$module.setDisplayOperatorCode(obj);
						});
					}
				}
			});
			// Select Form
			$('#selectForm tbody>tr').each(function() {

				if(scrTypeModifyFlg) {
					var clmId = $(this).find('[name$=".columnId"]').val();
					if($(this).find('[name$=".isSelected"]').is(':checked')
							&& clmId in MAP_COLUMN_ID
							&& clmId in MAP_COLUMN_ID_BK
							&& MAP_COLUMN_ID[clmId] != MAP_COLUMN_ID_BK[clmId]) {
						// marking style color is not mapping datatype
						$(this).css('background', 'none repeat scroll 0 0 #F1F3BB');
						$(this).find('select').val('');
					}else{
						$(this).css("background","");
					}
				}

				$$module.setDisplayFunctionCode($(this).find('select'), $(this).find('[name$=".dataType"]').val());
			});
			// Where from
			$('#whereForm').find('>tbody>tr').each(function(idx) {
				var leftColumnId = $(this).find('table>tbody>tr:eq(1) input[name$=".leftColumnIdAutocomplete"]');
				var argValue = $(this).find('[name$=".rightTableId"]').val();

				if(leftColumnId.next().val() in MAP_COLUMN_ID
						&& leftColumnId.next().val() in MAP_COLUMN_ID_BK
						&& MAP_COLUMN_ID[leftColumnId.next().val()] != MAP_COLUMN_ID_BK[leftColumnId.next().val()]) {
					// marking style color is not mapping datatype
					$(leftColumnId).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
//					$(this).find('[name$=".value"]').val('');
//					$(this).find('[name$=".value2"]').val('');
//					$(this).find('select[name$=".arg"]').val('');
//					$(this).find('select[name$=".arg2"]').val('');
//					$(this).find('[name$=".rightColumnIdAutocomplete"]').attr('arg01',argValue);
//					$(this).find('[name$=".rightColumnIdAutocomplete"]').next().val("");
//					$.qp.removeAutocompleteData($(this).find('[name$=".rightColumnIdAutocomplete"]').nextAll('.dropdown-toggle:first'));
//					$(this).find('[name$=".operatorCode"]').val('');
//					$(this).find('select[name$=".functionCode"]').val('');
//					$$module.whereFunctionCodeChange($(this).find('select[name$=".functionCode"]'));
				}else{
					$(this).css("background","");
				}

				$$module.callSetDisplayFunctionCode({target:leftColumnId});
			});

			// Mapping color for output form of sql design
			var $Select = $('#selectForm').find('>tbody>tr').find('td:first>input:checkbox:checked').length;
			if($Select != 0) {
				var mapOutput = [];
				// create map sequence of select
				$.each($('#outputForm-sql-builder').find('>tbody>tr'), function() {
					var datatype = $(this).find("select[name$='.dataType']").val();
					if(!!datatype && OBJECT_DATATYPE.isObjectDataType(datatype)){
						$(this).css("background","");
						return true;
					} 
					var sequence = $(this).find("[name$='.mappingColumn']").val();
					if(sequence != undefined && PHYSICAL_TO_JAVA_TYPES[MAP_SEQUENCE_FROM_SELECT[sequence]] != $(this).find('[name$=".dataType"]').val()) {
						$(this).closest('tr').css('background','none repeat scroll 0 0 #F1F3BB');
					}else{
						$(this).css("background","");
					}
				});
			}

			break;
		}
	};
	
	$$module.initOutputForm = function(){
		$("[id^='outputForm'] select[name$='dataType']").each(function(){

			var type = $(this).val();
			if (type == '0') {
				$(this).closest("td").next("td").find("input[name$='mappingColumn']").closest("td").find("*").prop("disabled",true).hide();
			}
		});

	};
	$$module.joinTypeClick = function(radio){
		var columnId = $(radio).closest("td").find("input[type=hidden][name$='columnId']").val();
		var isNotColumnId = !columnId;
		if($(radio).val() == 4){
			//$(radio).closest("td").find("select[name$='.operatorCode'] option").filter("[value=5],[value=6]").hide();
			var operatorCode = $(radio).closest("td").find("select[name$='.operatorCode']").val();
			if((operatorCode == 5 ||  operatorCode == 6) && isNotColumnId){
				$(radio).closest("td").find("select[name$='.operatorCode']").val("");
			}

			$(radio).closest("td").find(".qp-add-left").show();
		} else if($(radio).val() == 5){
			$(radio).closest("td").find("#joinPairTable tr").remove();
			if(isNotColumnId){
				$(radio).closest("td").find("select[name$='.operatorCode'] option").filter("[value=5],[value=6]").show();
			}
			$(radio).closest("td").find(".qp-add-left").hide();
		} else {
			$(radio).closest("td").find(".qp-add-left").show();
			if(isNotColumnId){
				$(radio).closest("td").find("select[name$='.operatorCode'] option").filter("[value=5],[value=6]").show();
			}
		}
	};
	$$module.orderByChange = function(){
		$$module.clearSqlScirpt();
	}


	$$module.updateSqlDesignResults = function(result){
		var selectRows = $("#selectForm tbody>tr");
		var messages = "";
		var dataToggle = $("#showHideUncheckedColumnsAnchor").attr("data-toggle");
		selectRows.each(function(){
			var $thiz = $(this);
			var itemSeqNo = $thiz.find("[name$='itemSeqNo']").val();
			var $dataType = $thiz.find("[name$='dataType']");
			var tableId = $thiz.find("[name$='tableId']").val();
			var columnId = $thiz.find("[name$='columnId']").val();
			var sqlDesignResult = $.grep(result,function(obj){
				return obj.tableId == tableId && obj.columnId == columnId;
			})[0];
			var outputRows = $("#outputForm-sql-builder tbody>tr");
			var mappingOutputRows = $.grep(outputRows, function(obj){
				return $(obj).find("[name$='mappingColumn']").val() == itemSeqNo;
			});

			if(sqlDesignResult){
				if(sqlDesignResult.dataType){
					if($dataType.val() != sqlDesignResult.dataType){
						$dataType.val(sqlDesignResult.dataType);

						$(mappingOutputRows).each(function(){
							messages += fcomMsgSource['err.sys.0202'].replace("{0}",$.trim($thiz.find("td:eq(2)").text())).replace("{1}",$.trim($thiz.find("td:eq(1)").text())).replace("{2}",PHYSICAL_TO_JAVA_TYPES[sqlDesignResult.dataType]) + "\r";
							$dataType.val(PHYSICAL_TO_JAVA_TYPES[sqlDesignResult.dataType]);
							$(this).find("select[name$='dataType']").next("label").text($(this).find("select[name$='dataType'] option[value='"+PHYSICAL_TO_JAVA_TYPES[sqlDesignResult.dataType]+"']").text());
						});
					}
				} else {
					$(mappingOutputRows).each(function(){
						messages += fcomMsgSource['err.sys.0203'].replace("{0}",$.trim($thiz.find("td:eq(2)").text())).replace("{1}",$.trim($thiz.find("td:eq(1)").text())) + "\r";
						var mappingColumnAC = $(this).find("input[name$='mappingColumnAutocomplete']");
						$.qp.removeAutocompleteData(mappingColumnAC.next().next());
						mappingColumnAC.addClass("qp-missing");
					});
					$thiz.remove();
				}
				$thiz.find("[name$='dataType']").val(sqlDesignResult.dataType);
			}
		});
		$.each(result, function(i){
			if(selectRows.find("[name$='columnId'][value='"+result[i].columnId+"']").length==0){
				messages += fcomMsgSource['err.sys.0204'].replace("{0}",result[i].columnName).replace("{1}",result[i].tableName) + "\r";
				$.qp.ar.addRow({tableId:'selectForm',templateData:{'tableId':result[i].tableId,'tableType':0,'tableName':result[i].tableName,'tableCode':result[i].tableCode,'columnId':result[i].columnId,'columnName':result[i].columnName,'columnCode':result[i].columnCode, 'dataType':result[i].dataType,'style':(dataToggle=='hide'?'display:none':'')}});
			}
		});
		if(messages){
			alert(messages);
		} else {
			alert("Synchronization finished");
		}
	}
	$$module.initSynchronize = function(){
		if($$module.isRegister()){
			$("#synchronizeLink").hide();
			return;
		}
		$("#outputForm-sql-builder tbody>tr").each(function(){
			$$module.outputFormMappingColumnChange({target:$(this).find("input[name$='mappingColumnAutocomplete']")});
		});

		$("#synchronizeLink").click(function(){
			$$module.navigateSynchronize(true);
		});
	};

	/**
	 * navigate synchronize
	 */
	$$module.navigateSynchronize = function(isMessage){
		var message = "";
	
		if($("#synchronizeLink").hasClass("autocompleteDesign")){
			//similar with viewDesign
			message = $$module.synchronizeViewDesgin(isMessage);
		}
		else if($("#synchronizeLink").hasClass("viewDesign")){
			message = $$module.synchronizeViewDesgin(isMessage);
		}else if($$module.checkIsNotSelectPattern()){
			message = $$module.synchronizeNotSelectPattern(isMessage);
		} else{
			message = $$module.synchronize(isMessage);
		}
		if(!isMessage){
			$$module.hideModalSynchronize();
			return;
		}
		$$module.showMessageConfirmSynchronize(message);
	}
	
	$$module.hideModalSynchronize = function(){
		$("#fcomConfirmDialogSynchronize").modal('hide');
		$$module.reloadMapColumnId();
		$$module.reloadAllSelectClone();
	}
	
	$$module.checkIsNotSelectPattern = function(){
		var select = $("select[name$='.sqlPattern']");
		if(eval($(select).val() != 0)){
			return true;
		}
		return false;
	}
	
	/**
	 * synchronize UPDATE, INSERT, DELETE pattern
	 */
	$$module.synchronizeNotSelectPattern = function(isMessage){
		var message = "";
		var tableId = $("table#intoForm input[type=hidden][name$='.tableId']").first().val();
		var table = $$module.getColumnsForSynchronizeUpdateInsertDelete(tableId);
		if($.isEmptyObject(table)){
			message = $$module.deleteTableInFormForm(isMessage)
		}
		var pattern = $("select[name$='.sqlPattern']").val();
		switch (eval(pattern)) {
		case sqlPattern.INSERT:
			message += $$module.sysnchronizeInsertPattern(table, isMessage);
			break;
		case sqlPattern.UPDATE:
			message += $$module.sysnchronizeUpdatePattern(tableId, table, isMessage);
			break;
		case sqlPattern.DELETE:
			message += $$module.sysnchronizeDeletePattern(tableId,table, isMessage);
			break;	
		}
		return message;
	}
	
	/**
	 * synchronize INSERT pattern
	 */
	$$module.sysnchronizeInsertPattern = function(table, isMessage){
		var message = "";
		if($("#tab-input").hasClass("active")){
			message += $$module.updateInputTab(isMessage);
			message += $$module.synchronizeFormValues(table,isMessage);
		}else if($("#tab-sql-design").hasClass("active")){
			message += $$module.synchronizeFormValues(table,isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-sql-design']").tab("show");
		}else{
			message += $$module.synchronizeFormValues(table,isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-output']").tab("show");
		}
		return message;
	}
	
	/**
	 * synchronize UPDATE pattern
	 */
	$$module.sysnchronizeUpdatePattern = function(tableId, table, isMessage){
		var message = "";
		if($("#tab-input").hasClass("active")){
			message += $$module.updateInputTab(isMessage);
			message += $$module.synchronizeFormValues(table, isMessage);
			message +=  $$module.synchronizeWhereFormNotSelect(tableId, table, isMessage);
		}else if($("#tab-sql-design").hasClass("active")){
			message += $$module.synchronizeFormValues(table, isMessage);
			message +=  $$module.synchronizeWhereFormNotSelect(tableId, table, isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-sql-design']").tab("show");
		}else{
			message += $$module.synchronizeFormValues(table, isMessage);
			message +=  $$module.synchronizeWhereFormNotSelect(tableId, table, isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-output']").tab("show");
		}
		return message;
	}
	
	/**
	 * synchronize DELETE pattern
	 */
	$$module.sysnchronizeDeletePattern = function(tableId, table, isMessage){
		var message = "";
		if($("#tab-input").hasClass("active")){
			message += $$module.updateInputTab(isMessage);
			message += $$module.synchronizeWhereFormNotSelect(tableId, table,isMessage);
		}else if($("#tab-sql-design").hasClass("active")){
			message += $$module.synchronizeWhereFormNotSelect(tableId, table,isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-sql-design']").tab("show");
		}else{
			message += $$module.synchronizeWhereFormNotSelect(tableId, table,isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-output']").tab("show");
		}
		return message;
	}
	
	/**
	 * synchronize view design
	 */
	$$module.synchronizeViewDesgin = function(isMessage){
		var message =  $$module.updateSqldesignTab(isMessage);
		return message;
	}
	
	/**
	 * synchronize sql design
	 * 
	 */
	$$module.synchronize = function(isMessage){
		var message = "";
		if($("#tab-output").hasClass("active")){
			message += $$module.updateOutputTab(isMessage);
			$("a[href='#tab-input']").tab("show");
			message += $$module.updateInputTab(isMessage);
			message += $$module.updateSqldesignTab(isMessage);
			$("a[href='#tab-output']").tab("show");
		}
		if($("#tab-input").hasClass("active")){
			message += $$module.updateInputTab(isMessage);
			$("a[href='#tab-output']").tab("show");
			message += $$module.updateOutputTab(isMessage);
			message += $$module.updateSqldesignTab(isMessage);
			$("a[href='#tab-input']").tab("show");
		}
		if($("#tab-sql-design").hasClass("active")){
			if(isMessage){
				message += $$module.getMessageSynchronizeTabSqlDesign(isMessage);
			}else{
				$$module.executeSynchronizeSqlDesignTab(isMessage);
			}
		}
		return message;
	}
	
	$$module.synchronizeWhereFormNotSelect = function(tableId,table, isMessage){
		var tableName = $("table#intoForm").find("input[name$='.tableIdAutocomplete']").val();
		var message = $$module.updateWhereTableSqlDesign(table, tableName, tableId, isMessage);
		return message;
	}
	
	$$module.deleteTableInFormForm = function(isMessage){
		var message = "";
		var tableName = $("table#intoForm").find("input[name$='.tableIdAutocomplete']");
		if($(tableName).val() == ""){
			return message;
		}
		if(isMessage){
			message = $$module.messageDeleteTableSynchronize($(tableName).val());
		}else{
			var tableId =  $(tableName).closest("td").find("input[type=hidden][name$='.tableId']").first();
			tableName.val("");
			$(tableId).val("");
			$$module.clearValues();
		}
		return message;
	}
	
	$$module.getColumnsForSynchronizeUpdateInsertDelete = function(tableId){
		var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+tableId+"&r="+Math.random();
		var data = $.qp.getData(url);
		return data;
	}
	
	$$module.synchronizeFormValues = function(table,isMessage){
		var message = "";
		var columns = $("table#valueForm").find("input[type=hidden][name$='.columnId']");
		var selectsEntity = $("table#valueForm").find("select[name$='valueType']");
		if(selectsEntity.length > 0){
			message += $$module.updateValueTypeEntityInValues(selectsEntity, table, isMessage);
		}
		$(columns).each(function(){
			message += $$module.updateColumInValues(this,table,isMessage);
		});
		return message;
	}
	
	$$module.updateValueTypeEntityInValues = function(selectsEntity,table, isMessage){
		var message = "";
		var selectsEntityFilter = $(selectsEntity).filter(function(){
			eval($(this).val()) == 2
		});
		if(selectsEntityFilter.length > 0){
			$(selectsEntityFilter).each(function(){
				message += $$module.updateValueOfValueTypeEntityBySelect(this, table, isMessage);
			});
		}
		return message;
	}
	
	$$module.updateValueOfValueTypeEntityBySelect = function(selectEntity, table, isMessage){
		var message = "";
		var parameter = $(selectEntity).closest("td").find("input[type='hidden'][name$='.parameter']");
		if($(parameter).val() == ""){
			return message;
		}
		var inforColum = $$module.getDataByColumnIdSelectFormSqlTab(columnId, table);
		if($.isEmptyObject(inforColum)){
			message = $$module.clearColumnInValuesForm(parameter, isMessage);
		}else{
			message =  $$module.editColumnInvaluesForm(parameter, inforColum, isMessage);
		}
		return message;
	}
	
	$$module.updateColumInValues = function(column, data, isMessage){
		var message = "";
		var columnId = $(column).val();
		if(!columnId || columnId == ""){
			return;
		}
		var objectColumn = $$module.getDataByColumnIdSelectFormSqlTab(columnId, data);
		if($.isEmptyObject(objectColumn)){
			message = $$module.clearColumnInValuesForm(column,isMessage);
		}else{
			message = $$module.editColumnInvaluesForm(column,objectColumn,isMessage);
		}
		return message;
	}
	
	$$module.editColumnInvaluesForm = function(column,objectColumn,isMessage){
		var message = "";
		var columnName = $(column).closest("td").find("input[name*='Autocomplete']");
		if(!isMessage){
			if($(columnName).val() != objectColumn.columnName){
				$(columnName).val(objectColumn.columnName);
			}
		}else{
			if($(columnName).val() != objectColumn.columnName){
				var tableName = $("table#intoForm").find("input[name$='.tableIdAutocomplete']").val();
				message = message = $$module.getMessageSynSqlDesign("sc.sqldesign.0034") + $$module.messageChangeSynchronize($(columnName).val(), tableName, objectColumn.columnName);
			}
		}
		return message;
	}
	
	$$module.clearColumnInValuesForm = function(column,isMessage){
		var message = "";
		var autoColumn = $(column).closest("td").find("input[name*='Autocomplete']");
		if(!isMessage){
			$(column).val("");
			$(autoColumn).val("");
		}else{
			var tableName = $("table#intoForm").find("input[name$='.tableIdAutocomplete']").val();
			message = $$module.getMessageSynSqlDesign("sc.sqldesign.0034") + $$module.messageDeleleteSynchronize(tableName, $(autoColumn).val());
		}
		return message;
	}
	
	$$module.getMessageSynchronizeTabSqlDesign = function(isMessage){
		var message = "";
		message += $$module.updateSqldesignTab(isMessage);
		message += $$module.updateOutputTab(isMessage);
		message += $$module.updateInputTab(isMessage);
		return message;
	}
	
	$$module.executeSynchronizeSqlDesignTab = function(isMessage){
		$("a[href='#tab-output']").tab("show");
		$$module.updateOutputTab(isMessage);
		$("a[href='#tab-input']").tab("show");
		$$module.updateInputTab(isMessage);
		$$module.updateSqldesignTab(isMessage);
		$("a[href='#tab-sql-design']").tab("show");
	};
	
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
	
	$$module.updateSqldesignTab = function(isMessage){
		var tableIdIputs = $$module.getListTableIdInSqlDesignTab();
		var message = "";
		$(tableIdIputs).each(function(index, tableIdInput){
			message += $$module.updateForEachTableIdInput(tableIdInput, isMessage);
		});
		return message;
	}
	
	$$module.updateForEachTableIdInput = function(tableIdInput, isMessage){
		var message = "";
		var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+tableIdInput.value+"&r="+Math.random();
		var table = $.qp.getData(url);
		var tableName = $(tableIdInput).parent().find("input[name$='Autocomplete']").val();
		var tableId = $(tableIdInput).val();
		message += $$module.updateFromFormSqlDesign(table,tableName, tableId, isMessage);
		message += $$module.updateSelectTableSqlDesign(table, tableName,tableId, isMessage);
		message += $$module.updateWhereTableSqlDesign(table, tableName,tableId, isMessage);
		message += $$module.updateOrderByTableSqlDesign(table, tableName,tableId, isMessage);
		return message;
	};
	
	$$module.updateFromFormSqlDesign = function(table,tableName, tableId, isMessage){
		var message = "";
		if(table.length == 0){
			message += $$module.removeTableLeftFromForm(tableId, tableName, isMessage);
			message += $$module.removeTableRigthFromForm(table, tableName, tableId, isMessage);
			return message;
		}
		message = $$module.updateTableRigthFromForm(table, tableName, tableId, isMessage);
		return message;
	}
	
	$$module.updateTableRigthFromForm = function(table, tableName, tableId, isMessage){
		var message = "";
		 var inputTableLeftJoinId = $("[id^=tab-sql-design]").find("#fromForm>tbody input[type=hidden][name$='.tableId'][name*='joinColumnsForm'][value='"+tableId+"']");
		 var inputTableRigthJoinId = $("[id^=tab-sql-design]").find("#fromForm>tbody input[type=hidden][name$='.joinTableId'][value='"+tableId+"']");
		 if(inputTableLeftJoinId.length != 0){
			var columnIdLeft = $(inputTableLeftJoinId).closest("td").next().find("input[type=hidden][name$='.columnId']");
			message += $$module.updateColumnNameFromFormSqlDesign(columnIdLeft, table, "columnId",tableName, isMessage);
		 }
		 if(inputTableRigthJoinId.length != 0){
			var columnIdRight = $(inputTableRigthJoinId).closest("tr").find("input[type=hidden][name$='.joinColumnId']");
			$$module.updateColumnNameFromFormSqlDesign(columnIdRight, table, "columnId", tableName, isMessage);
		 }
		return message;
	};
	
	$$module.updateColumnNameFromFormSqlDesign = function(column, table, columnCompare, tableName, isMessage){
		var message = "";
		var data = $$module.getDataByColumIdDynamic(column.val(), table, columnCompare);
	 	var autocomplete = $(column).parent().find("input[type=text][name*='Autocomplete']");
		if($.isEmptyObject(data)){
			message = $$module.clearColumnNameFromFormSqlDesign(column, autocomplete, tableName, isMessage);
			return message;
		}
		if(autocomplete.val() != data.columnName){
			$$moduleUpdateColumnNameFromFormWhenEdited(column,data, autocomplete, tableName, isMessage);
		}
		return message;
	};
	
	$$moduleUpdateColumnNameFromFormWhenEdited = function(column,data, autocomplete, tableName, isMessage){
		var message = "";
		if(!isMessage){
			column.val(data[columnCompare]);
		}
		message = $$module.getMessageSynSqlDesign("sc.autocomplete.0041") + $$module.messageChangeSynchronize(tableName, autocomplete, data.columnName);
		return message;
	}
	
	$$module.clearColumnNameFromFormSqlDesign = function(column, autocomplete,tableName,isMessage){
		var message = "";
		if(!isMessage){
			column.val("");
			autocomplete.val("");
		}
		message = $$module.getMessageSynSqlDesign("sc.autocomplete.0041") + $$module.messageDeleleteSynchronize(tableName, autocomplete);
		return message;
	};
	
	$$module.removeTableRigthFromForm = function(table, tableName, tableId,isMessage){
		var message = "";
		var tableIdInputJoin = $("[id^=tab-sql-design]").find("#fromForm>tbody input[type=hidden][name$='.joinTableId'][value='"+tableId+"']");
		if(tableIdInputJoin.val() == ""){
			return message;
		}
		var tdMatch = tableIdInputJoin.closest("tr.ar-dataRow").find("td:first");
		var allRadio = $(tdMatch).find("input[type=radio]");
		var allAutoComplete = $(tdMatch).find("input[type=text][name*='Autocomplete']");
		var allInputHidden = $(allAutoComplete).parent().find("input[type=hidden][name*='Id']");
		message = $$module.clearAllInputForTablesRightFromForm(allRadio, allAutoComplete, allInputHidden, tdMatch,tableName, isMessage);
		return message;
	};
	
	$$module.clearAllInputForTablesRightFromForm = function(allRadio, allAutoComplete, allInputHidden, tdMatch,tableName, isMessage){
		var message = "";
		if(!isMessage){
			$(allRadio).prop('checked', false);
			allAutoComplete.val("");
			allInputHidden.val("");
			tdMatch.find("b[name$='.rightTableName']").text("");
		}
		message = $$module.getMessageSynSqlDesign("sc.autocomplete.0041") + $$module.messageDeleteTableSynchronize(tableName);	
		return message;
	}
	
	$$module.removeTableLeftFromForm = function(tableId, tableName, isMessage){
		var message = "";
		var tableIdInput = $("[id^=tab-sql-design]").find("#fromForm>tbody input[name$='.tableId'][value='"+tableId+"']").not("[name*='.joinColumnsForm']");
		var autoCompleteInput = $(tableIdInput).parent().find("input[name$='.tableIdAutocomplete']");
		if(!isMessage){
			tableIdInput.val("");
			autoCompleteInput.val("");
		}
		message = $$module.getMessageSynSqlDesign("sc.autocomplete.0041") + $$module.messageDeleteTableSynchronize(tableName);
		return message;
	};
	 
	 $$module.updateOrderByTableSqlDesign = function(table, tableName, tableId, isMessage){
		 var message = "";
		 var rowsMatch = $$module.getInputByTableNameOrderByFormSqlDesign(tableName); 
		 if(rowsMatch.length == 0){
			 return message;
		 }
		 if(table.length == 0){
			 message = $$module.clearTableColumnNameOrderByFormSqlDesign(rowsMatch,tableName,isMessage);
			 return message;
		 }
		 $(rowsMatch).each(function(){
			 message += $$module.updateInputOrderByFormSqlDesign(this, table, tableName, isMessage);
		 });
		 return message;
	 };
	 
	 $$module.clearTableColumnNameOrderByFormSqlDesign = function(rowsMatch,tableName,isMessage){
		 var message = "";
		 if(!isMessage){
			 $(rowsMatch).each(function(){
					$(this).find("input[name$='.tableColumnAutocompleteId']").val("");
					$(this).find("input[name$='.tableColumn']").val("");
					//Can remove row
				}); 
		 }
		 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0044") + $$module.messageDeleteTableSynchronize(tableName);
		 return message;
	 }
	 
	 $$module.updateInputOrderByFormSqlDesign = function(row, table,tableName, isMessage){
		var message = "";
		var inputAuto = $(row).find("input[name$='.tableColumnAutocomplete']");
		var value = inputAuto.val();
		var columnName = value.split(".")[1].substring(1, value.split(".")[1].length - 1);
		if($$module.CheckColumnNameExistOrderFormSqlDesign(columnName,table)){
			return message;
		}
		message =  $$module.updateColumnNameOerderByFormSqlDesign(row, columnName,inputAuto,tableName,value,isMessage);
		return message;
	 }
	 
	 $$module.updateColumnNameOerderByFormSqlDesign = function(row, columnName,inputAuto,tableName,value,isMessage){
		 var message = "";
		 var itemsq = $(row).find("input[type=hidden][name$='.tableColumn']").val();
			var columnNameUpdate = $$module.getColumnNameByItemSq(itemsq);
			if(columnName != columnNameUpdate){
				if(!isMessage){
					inputAuto.val(value.split(".")[0]+"."+"["+columnNameUpdate+"]");
				}
				message = $$module.getMessageSynSqlDesign("sc.autocomplete.0044") + $$module.messageChangeSynchronize(columnName, tableName, columnNameUpdate);
			}
		 return message;
	 }
	 
	 $$module.getColumnNameByItemSq = function(itemsq){
		 var itemsq = $("#qp-sqldesign #selectForm tbody input[type=hidden][name$='.itemSeqNo'][value='"+itemsq+"']");
		 var columnName = $(itemsq).closest("tr").find("input[type=hidden][name$='.columnIdAutocomplete']").val();
		 return columnName;
	 }
	 
	 $$module.CheckColumnNameExistOrderFormSqlDesign = function(columnName, table){
		 var check = false;
		 for(var i = 0; i < table.length; i++){
			 if(table[i].columnName == columnName){
				 check = true; 
				 break;
			 }
		 }
		 return check;
	 }
	 
	 $$module.getInputByTableNameOrderByFormSqlDesign = function(tableName){
		var result = [];
		var rows = $("#orderByForm tbody>tr");
		$(rows).each(function(){
			var input = $(this).find("input[name$='.tableColumnAutocomplete']");
			if(input.val() == ""){
				return true;
			}
			var tableNameInput = $$module.getTableNameOfInputInOrderByFormSqlDesign(input);
			if(tableNameInput == tableName){
				result.push(this);
			}
		});
		return result;
	 };
	 
	 $$module.getTableNameOfInputInOrderByFormSqlDesign = function(input){
		 var value = $(input).val();
		 var tableName = value.split(".")[0];
		 var result = tableName.substring(1,tableName.length - 1);
		 return result;
	 }
	 
	 $$module.getDataByColumnNameOrderFormSqlDesign = function(columName, table){
		 var result = {};
		 for(var i = 0; i < table.length; i++){
			 if(table[i].columnName != columName){
				 result.data = table[i];
				 result.index = i;
				 break;
			 }
		 }
		 return result;
	 }
	 
	 $$module.updateWhereTableSqlDesign = function(table, tableName, tableId, isMessage){
		var message = $$module.updateWhereTableSqlDesignLeftTable(table, tableName, tableId, isMessage);
		message += $$module.updateWhereTableSqlDesignRightTable(table, tableName, tableId, isMessage);
		return message;
	 }
	 
	 $$module.updateWhereTableSqlDesignLeftTable = function(table, tableName, tableId, isMessage){
		 var message = "";
		 var inputTableIds = $("table[id^='whereForm']").not("[disabled='disabled']").find("input[name$='.leftTableId'][value='"+tableId+"']");
		 var inputColumnId = inputTableIds.closest("table").find("input[type=hidden][name$='.leftColumnId']");
		 if(inputTableIds.length == 0){
			 return message;
		 }
		 message = $$module.updateForTablesAndColumnsInWhereForm(inputTableIds,inputColumnId, table, tableName, tableId, isMessage);
		 return message;
	 }
	 
	 $$module.updateWhereTableSqlDesignRightTable = function(table, tableName, tableId, isMessage){
		 var message = "";
		 var inputTableIds = $("table[id^='whereForm']").not("[disabled='disabled']").find("input[name$='.rightTableId'][value='"+tableId+"']");
		 var inputColumnId = inputTableIds.closest("table").find("input[type=hidden][name$='.rightColumnId']");
		 if(inputTableIds.length == 0){
			 return message;
		 }
		 message = $$module.updateForTablesAndColumnsInWhereForm(inputTableIds,inputColumnId, table, tableName, tableId, isMessage);
		 return message;
	 }
	 
	 $$module.updateForTablesAndColumnsInWhereForm = function(inputTableIds,inputColumnId,table, tableName, tableId, isMessage){
		 var message = "";
		 if(table.length == 0){
			 var autocompleteTable = $(inputTableIds).parent().find("input[type=text][name*='Autocomplete']");
			 var autocompleteColum = $(inputColumnId).parent().find("input[type=text][name*='Autocomplete']");
			 if(!isMessage){
				 inputTableIds.val("");
				 inputColumnId.val("");
				 autocompleteTable.val("");
				 autocompleteColum.val(""); 
			 }
			 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0046") + $$module.messageDeleteTableSynchronize(tableName);
			 return message;
		 }
		 $(inputTableIds).each(function(){
			 var inputColumnId = $(this).closest("table").find("input[type=hidden][name$='.leftColumnId']");
			 message += $$module.updateColumnIdWhereFormSqlDesign(inputColumnId, table, tableName, isMessage);
		 });
		return message; 
	 }
	
	 $$module.updateColumnIdWhereFormSqlDesign = function(inputColumnId, table, tableName, isMessage){
		 var message = "";
		 var autoCompleteColumnId = $(inputColumnId).parent().find("input[name$='ColumnIdAutocomplete']");
		 if($(autoCompleteColumnId).val() == ""){
			 return message;
		 }
		 var data =  $$module.getDataByColumnIdSelectFormSqlTab(inputColumnId.val(), table);
		 if($.isEmptyObject(data)){
			 if(!isMessage){
				 autoCompleteColumnId.attr("value","");
				 inputColumnId.val(""); 
			 }else{
				 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0046") + $$module.messageDeleleteSynchronize($(autoCompleteColumnId).attr("value"), tableName); 
			 }
			 return message;
		 }
		 if(!!autoCompleteColumnId.val() && autoCompleteColumnId.val().trim() != "" && autoCompleteColumnId.val() != data["columnName"]){
			 if(!isMessage){
				 autoCompleteColumnId.val(data["columnName"]); 
			 }else{
				 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0046") + $$module.messageChangeSynchronize(autoCompleteColumnId.val(), tableName, data.columnName);
			 }
		 }
		 return message;
	 };
	 
	 $$module.getMessageSynSqlDesign = function(key){
		return SQL_DESIGN_TAB + " " + " ["+ dbMsgSource[key] +"] ";
	 }
	 
	 $$module.updateSelectTableSqlDesign = function(table, tableName,tableId, isMessage){
		 var message = "";
		 var rowSelects = $$module.getRowsByTableIdSqlDesginTab(tableId);
		 if(table.length == 0){
			 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0045") + $$module.messageDeleteTableSynchronize(tableName);
			 if(!isMessage){
				 $(rowSelects).each(function(){ $.qp.ar.removeRow({link : this});});
			 }
			return message;
		 }
		 message += $$module.updateRowWhenTableEditedColumnSqlDesignTab(rowSelects, table, tableName, isMessage);
		 return message;
	 }
	 
	 $$module.updateRowWhenTableAddedColumnSqlDesignTab = function(rows, table, tableName, index, isMessage){
		 var message = "";
		 var tableId = $(rows[index]).find("input[type=hidden][name$='.tableId']").val();
		 var tableType = $(rows[index]).find("input[type=hidden][name$='.tableType']").val();
		 var tableCode = $(rows[index]).find("input[type=hidden][name$='.']");
		 var dataToggle = $("#showHideUncheckedColumnsAnchor").attr("data-toggle");
		 for(var i= index + 1; i < table.length; i++){
			 if(!isMessage){
				 $.qp.ar.addRow({tableId:'selectForm',templateData:{'tableId':tableId,'tableType':tableType,'tableName':tableName,'columnId':table[i].columnId,'columnName':table[i].columnName,'columnCode':table[i].columnCode, 'dataType': table[i].baseType,'style':(dataToggle == 'hide'?'display:none':'')}});
				 var $select = $('#selectForm tbody>tr:last select');
				 $$module.setDisplayFunctionCode($select, table.baseType);
				 $$module.removeRowAddedOutputTabSqlDesign();
			 }
			 message += $$module.getMessageSynSqlDesign("sc.autocomplete.0045") + $$module.messageAddSynchronize(table[i].columnName, tableName);
		 }
		 return message;
	 };
	 
	 $$module.removeRowAddedOutputTabSqlDesign = function(){
		 var row = $('#outputForm-sql-builder tbody>tr:last');
		 var arRgroupindex = row.data("ar-rgroupindex");
		 if(Number(arRgroupindex) % 1 == 0){
			 $.qp.ar.removeRow({link : row}); 
		 }
	 }
	 
	 $$module.updateRowWhenTableEditedColumnSqlDesignTab = function(rows, table, tableName, isMessage){
		 var message = "";
		 var rowIndex = 0;
		 var rowRemove = 0;
		 $(rows).each(function(index){
			 rowIndex = index;
			 var columnId = $(this).find("input[type=hidden][name$='columnId']");
			 var data = $$module.getDataByColumnIdSelectFormSqlTab(columnId.val(), table);
			 if ($.isEmptyObject(data)){
				 if(!isMessage){
					 $.qp.ar.removeRow({link : this}); 
					 $$module.clearMappingColumnOutputSqldesign(this);
				 }
				 rowRemove += 1;
				 message += $$module.getMessageSynSqlDesign("sc.autocomplete.0045") + $$module.messageDeleleteSynchronize(columnId.parent().find("span").text(), tableName);
			 }else{
				 message += $$module.updateRowDefaultSelectTableSqlDesigTab(this, data, tableName ,isMessage);
			 }
		 });

		 if(rowRemove != 0 || rows.length < table.length){
			 message += $$module.updateRowWhenTableAddedColumnSqlDesignTab(rows, table, tableName,rowIndex - rowRemove,isMessage); 
		 }
		 return message;
	 };
	 
	 $$module.clearMappingColumnOutputSqldesign = function(row){
		 var inputAuto = $$module.getAutoCompleteInOutputBeanByTableAndColumnName(row);
		 if(inputAuto.length == 0){
			 return;
		 }
		 var inputAutoHidden = $(inputAuto).parent().find("input[name$='.mappingColumn']");
		 inputAuto.val("");
		 inputAutoHidden.val("");
	 }
	 
	 $$module.getAutoCompleteInOutputBeanByTableAndColumnName = function(row){
		 var tableName = $(row).find("input[type=hidden][name$='.tableIdAutocomplete']").val();
		 var columnName = $(row).find("input[type=hidden][name$='.columnIdAutocomplete']").val();
		 var tableColumnName = "["+tableName+"]"+"."+"["+columnName+"]";
		 var inputAuto = $("#outputForm-sql-builder tbody>tr").find("input[value='"+tableColumnName+"']");
		 return inputAuto;
	 }
	 
	 $$module.getDataByColumnIdSelectFormSqlTab = function(columnId, table){
		 var result = {};
		 for(var i=0; i < table.length; i++){
			 if(table[i].columnId == columnId){
				 result = table[i];
				 break
			 }
		 }
		 return result;
	 }
	 
	 $$module.updateRowDefaultSelectTableSqlDesigTab = function(row, data, tableName, isMessage){
		 message = "";
		 message += $$module.updateColumnNameSelectFormSqlDesignTab(row, data, tableName, isMessage);
		 message += $$module.updateTdRowInSelectTableSqlDesignTab(row, data, tableName,"columnCode","columnCode", isMessage);
		 message +=  $$module.updateTdDataTypeRowInSelectTableSqlDesignTab(row, data, tableName,"dataType","dataType", isMessage);
		 return message;
	 };
	 
	 $$module.updateColumnNameSelectFormSqlDesignTab = function(row, data, tableName, isMessage){
		 message = "";
		 message += $$module.updateTdRowInSelectTableSqlDesignTab(row, data, tableName,"columnIdAutocomplete","columnName",isMessage);
		 if(message != ""){
			 var label = $(row).find("input[type=hidden][name$='.columnIdAutocomplete']").parent().find("span");
			 label.text(data.columnName);
			 $$module.updateColumnNameForOutputSqldesign(row, data, tableName);
			 $$module.updateOrderByRowSqlDesign(row, data.columnName);
		 }
		 return message;
	 };
	 
	 $$module.updateOrderByRowSqlDesign = function(row, columName){
		 var itemsq = $(row).find("input[type=hidden][name$='itemSeqNo']").val();
		 var rowOrderBy = $("#qp-sqldesign table#orderByForm input[type=hidden][name$='.tableColumn'][value='"+itemsq+"']");
		 if(rowOrderBy.length == 0){
			 return;
		 }
		 var autocomplete = $(rowOrderBy).parent().find("input[name$='.tableColumnAutocomplete']");
		 if(autocomplete.val == ""){
			 return;
		 }
		 var tableName = autocomplete.val().split(".")[0];
		 var tableColumName = tableName + "." + "["+columName+"]";
		 autocomplete.val(tableColumName);
	 }
	 
	 $$module.updateColumnNameForOutputSqldesign = function(row, data, tableName){
		 var inputAuto = $$module.getAutoCompleteInOutputBeanByTableAndColumnName(row);
		 if(inputAuto.length == 0){
			 return;
		 }
		 var tableColumnName = "["+tableName+"]"+"."+"["+data.columnName+"]";
		 inputAuto.val(tableColumnName);
	 }
	 
	 $$module.updateTdRowInSelectTableSqlDesignTab = function (row, data, tableName, inputName, fieldName, isMessage){
		 var message = "";
		 var input = $(row).find("input[type=hidden][name$='."+inputName+"']");
		 if(!!input.val() && input.val() != data[fieldName]){
			 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0045")+ $$module.messageChangeSynchronize(input.val(), tableName, data[fieldName]);
			 if(!isMessage){
				 input.val(data[fieldName]); 
			 }
		 }
		 return message;
	 };
	 
	 $$module.updateTdDataTypeRowInSelectTableSqlDesignTab = function(row, data, tableName, inputName, fieldName, isMessage){
		 var message = "";
		 var input = $(row).find("input[type=hidden][name$='."+inputName+"']");
		 var columnName = $(row).find("input[type=hidden][name$='.columnCode']").parent().find("span").text();
		 var label = $(row).find("label[name='dataTypeBackup']");
		 if(input.val() != data.baseType){
			 if(!isMessage){
				 input.val(data.baseType); 
				 var $select = $(row).find("select");
				 $$module.setDisplayFunctionCode($select, data.baseType);
				 $$module.setDataTypeOutputbeanSqldesign(row,data.dataType);
			 }
			
			 message = $$module.getMessageSynSqlDesign("sc.autocomplete.0045") + $$module.messageChangeDataTypeSynchronize(columnName, tableName,CL_SQL_DATATYPE[data[fieldName]]);
		 }
		 if(label.attr("value") != data.baseType){
			 if(!isMessage){
				 label.attr("value",data.baseType);
				 $$module.setDataTypeOutputbeanSqldesign(row,data.dataType);
			 }
			
			 message =  $$module.getMessageSynSqlDesign("sc.autocomplete.0045") + $$module.messageChangeDataTypeSynchronize(columnName, tableName,CL_SQL_DATATYPE[data[fieldName]]);
		 }
		 return message;
	 }
	 
	 $$module.setDataTypeOutputbeanSqldesign = function(row, dataType){
		 var autocomplete = $$module.getAutoCompleteInOutputBeanByTableAndColumnName(row);
		 var selectbox = $(autocomplete).closest("tr").find("select[name$='.dataType']");
		 selectbox.val(dataType);
	 }
	 
	 $$module.getRowsByTableIdSqlDesginTab = function(tableId){
		 var rows = [];
		 var trSelects = $("[id*='sql-design']").find("#selectForm tr");
		 trSelects.each(function(){
			 var value = $(this).find("input[type=hidden][name$='.tableId']").val();
			 if(value == tableId){
				 rows.push(this);
			 }
		 });
		 return rows;
	 }
 
	
	$$module.getListTableIdInSqlDesignTab = function(){
		var tableInputs = [];
		var tableIdInput = $("[id^=tab-sql-design]").find("#fromForm>tbody input[name$='.tableId']").not("[name*='.joinColumnsForm']");
		var tableIdInputJoin = $("[id^=tab-sql-design]").find("#fromForm>tbody input[type=hidden][name$='.joinTableId']");
		tableIdInput.each(function(){
			if(!!$(this).val() && !!$(this).val() != ""){
				tableInputs.push(this);
			}
		});
		tableIdInputJoin.each(function(){
			if(!!$(this).val() && !!$(this).val() != ""){
				tableInputs.push(this);
			}
		});
		return tableInputs;
	}

	$$module.updateOutputTab = function(isMessage){
		var table = $("table#outputForm-sql-builder");
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
	
	/**
	 * synchronize for input in sql builder
	 */
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
	
	$$module.updateRowsEntityType = function(rowsEntityType, isMessage,type){
		var message = "";
		var tab = type == 'INPUT' ? INPUT_TAB : OUTPUT_TAB
		$(rowsEntityType).each(function(){
			message += $$module.updateForEachEntity(this, isMessage,type, tab);
		});
		return message;
	};
	
	$$module.updateForEachEntity = function(entityRow,isMessage,type,tab){
		var message = "";
		var typeObject = "entity";
		var tableId = $(entityRow).find("input[name$='.tableId']").val();
		var tableName = $(entityRow).find("input[name$='.tableIdAutocomplete']").val();
		if(tableId == ''){
			return message;
		}
		var url = CONTEXT_PATH + "/businessdesign/getColumnsOfTableDesign?tableId="+tableId+"&r="+Math.random();
		var data = $.qp.getData(url);
		if($.isEmptyObject(data)){
			message	+= $$module.removeTableWhenSynchronize(entityRow,tableName, isMessage, tab);
		}else{
			message += $$module.updateTableNameAndTablecode(entityRow,typeObject,isMessage, tab);
			message += $$module.compareOldAndNewValues(entityRow,data,tableName,typeObject ,isMessage,type);
		}
		return message;
	}
	
	//Begin update table name code
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
	
	$$module.compareOldAndNewValues = function(rows,data,tableName,typeData, isMessage, type){
		var dataArRgroupindex = $(rows).attr("data-ar-rgroupindex");
		var table = $(rows).closest("table");
		var rowsChild = table.find("tr[data-ar-rgroup='"+dataArRgroupindex+"'][data-ar-rgroupindex^='"+dataArRgroupindex+"']");
		var message = "";
		message +=  $$module.updateRowsEdited(rowsChild, data,tableName,typeData,isMessage, type);
		return message;
	}
	
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
	
	$$module.factoryGetFieldNameIdByType = function(dataType){
		if(dataType == "commonObject"){
			return "commonObjectAttributeId";
		}
		if(dataType == "externalObject"){
			return "externalObjectAttributeId";
		}
		return "columnId";
	};
	
	$$module.factoryGetFieldNameByType = function(dataType){
		if(dataType == "commonObject"){
			return "commonObjectAttributeName";
		}
		if(dataType == "externalObject"){
			return "externalObjectAttributeName";
		}
		return "columnName";
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
	
	
	$$module.getParentOfRowsChild = function(rowsChild){
		var arRgroup = $(rowsChild).first().data("ar-rgroup");
		return $("#tab-output #outputForm-sql-builder tbody tr[data-ar-rgroupindex='"+arRgroup+"']");
	};
	
	$$module.getParentOfRowsChildInput = function(rowsChild){
		var arRgroup = $(rowsChild).first().data("ar-rgroup");
		return $("#tab-input #inputForm tbody tr[data-ar-rgroupindex='"+arRgroup+"']");
	};
	
	$$module.getRowsChildAgainOfRowsChid = function(rowsChild){
		var rowParent = $$module.getParentOfRowsChild(rowsChild)
		var arRgroupindex = $(rowParent).data("ar-rgroupindex");
		return $("#tab-output #outputForm-sql-builder tbody tr[data-ar-rgroup='"+arRgroupindex+"']");
	}
	
	$$module.getRowsChildAgainOfRowsChidInput = function(rowsChild){
		var rowParent = $$module.getParentOfRowsChildInput(rowsChild)
		var arRgroupindex = $(rowParent).data("ar-rgroupindex");
		return $("#tab-input #inputForm tbody tr[data-ar-rgroup='"+arRgroupindex+"']");
	}
	
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


	$$module.addRowForSyschronize = function(element, fieldIndex){
		var groupId = $(element).attr(fieldIndex);
		var newRow = $.qp.ar.addRow({
			container: $("table[id^='outputForm-sql-builder']"),
			link: element,
			templateId:$("table[id^='outputForm-sql-builder']").attr("data-ar-actionTemplateId").replace("action","child-entity"),
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

	$$module.init = function(isView){
		//alert($("select[name='outputForm[0].dataType']").val());
		$(function(){
			$("#qp-sqldesign select[name$='.sqlPattern']").change(function(){
				$$module.initShowHidePattern();
				$$module.generateSqlExplanation();
				$$module.showHideSynchronize();
			});
			$("select[name$='.operatorCode']").each(function(){
				$$module.operatorTypeChange(this);
			});
			$$module.initHavingForm();
			$$module.initSelectForm();
			$$module.initGroupByForm();
			$$module.initOrderByForm();
			$$module.initSelectedTables();
			$$module.initFromForm();
			$$module.initFirstFromFormItem();
			$$module.initWhereForm();
			$$module.initValueForm();
			$$module.initAutocompleteOutput();
			//$$module.generateSqlExplanation();
			$$module.initShowHidePattern(true);
			$$module.initOutputForm();
			$.qp.common.initTabs();
			$$module.initSynchronize();
			$$module.reloadMapColumnId();
			$$module.reloadAllSelect();
			$$module.cleanSqlScriptWhenOptionsChange($("#qp-viewdesign #tab-sql-design-select"));
			$$module.cleanSqlScriptWhenOptionsChange($("#qp-autocomplete #tab-sql-design-select"));
			$$module.cleanSqlScriptWhenOptionsChange($("#qp-sqldesign #tab-sql-design-select"));
			$$module.cleanSqlScriptWhenOptionsChange($("#qp-sqldesign #tab-sql-design #tab-sql-design-area"));
			$$module.initCheckboxIsArray();
			$$module.hideTableWhenCrossJoin();
			$$module.limitRowsInputAutocomplte();
			$.qp.common.setModuleIdForExternalCommonObject();
			//$$module.initForrmatPatterWhereCondition();
		});
	};
	
	$$module.initForrmatPatterWhereCondition = function(){
		var rows = $("table#whereForm tbody tr");
		$(rows).each(function(){
			var dataType = $(this).find("input[type=hidden][name$='.dataType']").val();
			$$module.setPatternFormat(dataType,this);
		});
	}
	
	$$module.hideTableWhenCrossJoin = function(){
		var radios = $("input[type=radio][name$='.joinType']:checked");
		if(radios.length == 0){
			return;
		}
		$(radios).each(function(){
			var val = $(this).val();
			if(!!val && val == '5'){
				$$module.joinTypeClick($(this));
			}
		});
	}
	
	$$module.initCheckboxIsArray = function(){
		var checkboxs = $("#inputForm input[type=checkbox][name*='isArray']");
		$(checkboxs).each(function(){
			var selectbox = $(this).closest("tr").find("select[name$='.dataType']");
			if(selectbox.val() != '1'){
				$$module.cancelCheckBoxArray(this);
			}
			
		});
	}
	
	$$module.showHideSynchronize = function(){
		if($$module.isRegister()){
			return;
		}
		//$$module.showHideSynchronizeWithSqlPattern();
	}
	
	$$module.showHideSynchronizeWithSqlPattern = function(){
		var sqlPatternVal = $("#qp-sqldesign select[name$='.sqlPattern']").val();
		if (!sqlPatternVal){
			return;
		}
		if(sqlPatternVal == sqlPattern.SELECT)
			$("#synchronizeLink").show();
		else
			$("#synchronizeLink").hide();
	}
	
	$$module.isRegister = function(){
		var sqlId = $('input:hidden[name="sqlDesignForm.sqlDesignId"]').val();
		if(!!sqlId && sqlId != ""){
			return false;
		}
		return true;
	}
	
	$$module.cleanSqlScriptWhenOptionsChange = function($div){
		$div.on('change', function(){
			$$module.clearSqlScirpt();
		});
	}

	$$module.clearSqlScirpt = function(){
		var $txtSqlText = $('#generateSQLPanel').find('textarea[id="sqlDesignForm.sqlText"]');
		if(!!$txtSqlText.val() && $txtSqlText.val().length > 0){
			$txtSqlText.val('');
		}
	}

	return $$module;
})(jQuery.namespace('$.qp.sqlbuilder'));
