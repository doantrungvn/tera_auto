/**
 * Function allow tooggling division based on clicked tab
 * @param index
 * 	index of clicked tab
 */
function tabCheckbox(index) {
	$("#tabsLogContent-0" + index).toggle($("#tabsCheckbox-0" + index).prop('checked'));
	switch(index){
		case 1:
			var consoleLog = $("#tabsLogContent-01");
			consoleLog.find("input[name$='.configMode'][value$='1']").prop('checked',true);
			consoleLog.find("input[name$='.logLevel'][value$='3']").prop('checked',true);
			consoleLog.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);
			consoleLog.find("input[name$='.charset']").val("UTF-8");
			consoleLog.find("select[name$='.logTarget']").val('System.out').attr("selected","selected");
			consoleLog.find("textarea[name$='.conversionPattern']").val("[%d{yyyy-MM-dd HH:mm:ss}]\t[%-5level]\t[%-48logger{48}]\t%msg%n");
			consoleLog.find("input[name$='consoleLog.lstLogDetail0.patternId']").val("1");
			consoleLog.find("input[name$='consoleLog.lstLogDetail0.itemSequence']").val("1");
			consoleLog.find("textarea[name$='.conversionPattern']").attr( "readonly", "readonly");
			consoleLog.find("a.qp-button-action").attr('disabled', true);
			if ($("#tabsCheckbox-01").is(':checked')){
				var index = 0;
				$('#patternHidden_1_0').find('div').each(function(){
				    if (index != 0){
				    	$(this).remove();
				    }
				    index++;
				});
			}
			
			break;
		case 3:
			var databaseLog = $("#tabsLogContent-03");
			databaseLog.find("input[name$='.configMode'][value$='1']").prop('checked',true);
			databaseLog.find("input[name$='.logLevel'][value$='3']").prop('checked',true);
			databaseLog.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);
			onchangeDBType(databaseLog.find("select[name*='dbType']"));
			break;
	}
}

/**
 * Function to add a database log definition division
 */
function addDatabaseLog(){
	var template = $('#tmp_databaselog').html();
	$('#databaseWrapper').append(template);
	
	//Set attribute for new database log file
	var databaseLog = $("#databaseLogInfor");
	databaseLog.css({"border-color":"red","border-style":"dashed","border-width":"2px"});
	databaseLog.css({"margin-bottom":"20px"});
	var numberDBlog = parseInt($("#numberDatabaseLog").val());
	databaseLog.attr("id","databaseLogInfor"+numberDBlog+"");
	databaseLog.find("input[name$='.logLevel']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].logLevel");
	databaseLog.find("input[name$='.appenderAutoComplete']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].appenderAutoComplete");
	databaseLog.find("input[name$='.appender']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].appender");
	databaseLog.find("input[name$='.dbDriver']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbDriver");
	databaseLog.find("input[name$='.dbUrl']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbUrl");
	databaseLog.find("input[name$='.dbUserName']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbUserName");
	databaseLog.find("input[name$='.dbPassword']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbPassword");
	//databaseLog.find("select[name$='.layout']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].layout");
	//databaseLog.find("input[name$='.sqlString']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].sqlString");
	databaseLog.find("select[name$='.dbType']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbType");
	databaseLog.find("select[name$='.insertHeaders']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].insertHeaders");
	databaseLog.find(".qp-input-autocomplete").each(function() {// initial
		$.qp.initialAutocomplete($(this));
	});
	numberDBlog++;
	$("#numberDatabaseLog").val(numberDBlog);
}

/**
 * Function to add a file log definition division
 */
function addFileLog(){
	var template = $('#tmp_filelog').html();
	$('#fileWrapper').append(template);
	
	//Set attribute for new file log
	var fileLog = $("#fileLogInfor");
	fileLog.css({"border-color":"red","border-style":"dashed","border-width":"2px"});
	fileLog.css({"margin-bottom":"20px"});
	var numberFileLog = parseInt($("#numberFileLog").val());
	fileLog.attr("id","fileLogInfor"+numberFileLog+"");
	
	fileLog.find("input[name$='.configMode']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].configMode");
	fileLog.find("input[name$='.logFileType']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].logFileType");
	fileLog.find("input[name$='.logLevel']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].logLevel");
	fileLog.find("input[name$='.appenderAutocomplete']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].appenderAutocomplete");
	fileLog.find("input[name$='.appender']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].appender");
	fileLog.find("input[name$='.patternFileName']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].patternFileName");
	fileLog.find("input[name$='.filePath']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].filePath");
	fileLog.find("input[name$='.charset']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].charset");
	fileLog.find("select[name$='.immediateFlush']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].immediateFlush");
	fileLog.find("select[name$='.appendType']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].appendType");
	fileLog.find("input[name$='.maxFileSize']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxFileSize");
	fileLog.find("input[name$='.maxBackupIndex']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxBackupIndex");
	fileLog.find("input[name$='.datePattern']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].datePattern");
	fileLog.find("select[name$='.layout']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].layout");
	fileLog.find("textarea[name$='.conversionPattern']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].conversionPattern");
	fileLog.find("a.qp-button-action").attr("onclick","openDialogConversionPattern('fileLog.lstLogDetail["+numberFileLog+"].',2,"+numberFileLog+");");
	fileLog.find("div[id^='patternHidden']").attr("id","patternHidden_2_"+numberFileLog);
	fileLog.find("select[name$='.rollingPolicy']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].rollingPolicy");
	fileLog.find("select[name$='.triggeringPolicy']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].triggeringPolicy");
	fileLog.find("input[name$='.maxHistory']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxHistory");
	fileLog.find("input[name$='.totalSizeCap']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].totalSizeCap");
	fileLog.find("input[name$='.minIndex']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].minIndex");
	fileLog.find("input[name$='.maxIndex']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxIndex");
	
	fileLog.find(".qp-input-autocomplete").each(function() {// initial
		$.qp.initialAutocomplete($(this));
	});
	
	fileLog.find("input[name$='fileLog.lstLogDetail["+numberFileLog+"].configMode'][value$='1']").prop('checked',true);
	fileLog.find("input[name$='fileLog.lstLogDetail["+numberFileLog+"].logLevel'][value$='3']").prop('checked',true);
	fileLog.find("input[name$='fileLog.lstLogDetail["+numberFileLog+"].logLevel']").attr( "disabled", true);
	fileLog.find("input[name$='fileLog.lstLogDetail["+numberFileLog+"].logFileType'][value$='1']").prop('checked',true);
	fileLog.find("input[name$='fileLog.lstLogDetail["+numberFileLog+"].filePath']").val("logs");
	fileLog.find("input[name$='fileLog.lstLogDetail["+numberFileLog+"].charset']").val("UTF-8");
	fileLog.find("select[name$='fileLog.lstLogDetail["+numberFileLog+"].immediateFlush']").val('1').attr("selected","selected");
	fileLog.find("select[name$='fileLog.lstLogDetail["+numberFileLog+"].appendType']").val('1').attr("selected","selected");
	fileLog.find("select[name$='fileLog.lstLogDetail["+numberFileLog+"].layout']").val('3').attr("selected","selected");
	fileLog.find("textarea[name$='fileLog.lstLogDetail["+numberFileLog+"].conversionPattern']").val("[%d{yyyy-MM-dd HH:mm:ss}]\t[%-5level]\t[%-48logger{48}]\t%msg%n");
	fileLog.find("textarea[name$='fileLog.lstLogDetail["+numberFileLog+"].conversionPattern']").attr( "readonly", "readonly");
	fileLog.find("textarea[name$='fileLog.lstLogDetail["+numberFileLog+"].conversionPattern']").parent().find("a.qp-button-action").attr("disabled", true);
	if ($("#tabsCheckbox-02").is(':checked')){
		var index = 0;
		$('#patternHidden_2_'+numberFileLog).find('div').each(function(){
		    if (index != 0){
		    	$(this).remove();
		    }
		    index++;
		});
		if (index < 1){
			$('#patternHidden_2_'+numberFileLog).append("<div id='pattern0'><input type='hidden' id='fileLog.lstLogDetail["+numberFileLog+"].lstConversionPattern[0].patternId' name='fileLog.lstLogDetail["+numberFileLog+"].lstConversionPattern[0].patternId' value='1'><input type='hidden' id='fileLog.lstLogDetail["+numberFileLog+"].lstConversionPattern[0].itemSequence' name='fileLog.lstLogDetail["+numberFileLog+"].lstConversionPattern[0].itemSequence' value='1'></div>")
		}
	}
	
	//Update the current number of file log
	numberFileLog++;
	$("#numberFileLog").val(numberFileLog);
	// Add event for radio button
	addRadioEvent(fileLog);
	// Add event for layout selector 
	addLayoutSelectorEvent(fileLog);
	// Set event for Rolling Policy selector and trigger it
	addRollingPolicySelectorEvent(fileLog);
	// Set event for Triggering Policy selector and trigger it
	addTriggeringPolicySelectorEvent(fileLog)
	// Set default selection (normal file log)
	fileLog.find("input[name$='.logFileType']").filter('[value=1]').click();
	
	$.qp.formatInteger(fileLog.find('.qp-input-integer'));
	$('[data-toggle="tooltip"]').tooltip();
}

/**
 * Add event for log type radio button of each log file
 */
function addRadioEvent(thiz){
	 $(thiz).find("input:radio[name$='.logFileType']").each(function(){
   	  $(this).click( function() {
   		  var type = $(this).val();
   		  var currentDiv = $(this).closest("div");
   		  if (type == "2") {
   			  currentDiv.find("tr[name$='.rollingPatternFileName']").show();
   			  currentDiv.find("tr[name$='.rollingPatternFileName']").find('.maxFileSize').hide();
   			  currentDiv.find("tr[name$='.policyFile']").show();
   			  if (document.readyState == "complete"){
   				currentDiv.find("tr[name$='.rollingPatternFileName']").find("select[id$='triggeringPolicy']").val('').attr("selected","selected");
   				currentDiv.find("tr[name$='.policyFile']").find("select[id$='rollingPolicy']").val('').attr("selected","selected");
   			  }
   		  } else {
   			  currentDiv.find("tr.rollingFile").hide();
   			  currentDiv.find("tr.rollingFile").find("input[type='text']").val('');
   		  }
   	  });
   	  
   	  if($(this).is(':checked')){
   		$(this).trigger("click");
   	  }
     });
	 
	 $(thiz).find("input:radio[name$='.configMode']").each(function(){
	   	  $(this).click( function() {
	   		  var currentDiv = $(this).closest("div");
	   		  if (currentDiv.attr('id').indexOf("tabsLogContent-01") != -1){
	   			  currentDiv.find("input[name$='.logLevel']").attr( "disabled", false);
		   		  currentDiv.find("textarea[name$='.conversionPattern']").attr( "readonly", "readonly");
		   		  currentDiv.find("input[name$='.charset']").attr( "readonly", "readonly");
		   		  currentDiv.find("a.qp-button-action").attr('disabled', true);
		   		  currentDiv.find("select[name$='.logTarget']").find("option:not(:selected)").attr( "disabled", false);
		   		  if ($(this).val() == "3") {
		   			  currentDiv.find("textarea[name$='.conversionPattern']").removeAttr('readonly');
		   			  currentDiv.find("textarea[name$='.conversionPattern']").attr('disabled', false);
		   			  currentDiv.find("input[name$='.charset']").removeAttr('readonly');
		   			  currentDiv.find("a.qp-button-action").attr('disabled', false);
		   		  } else if ($(this).val() == "2"){
		   			  currentDiv.find("input[name$='.logLevel'][value$='1']").prop('checked',true);
		   			  currentDiv.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);	   			  
		   			  currentDiv.find("textarea[name$='.conversionPattern']").val("[%d{yyyy-MM-dd HH:mm:ss}]\t[%-5level]\t[%-48logger{48}]\t%msg%n");
		   			  currentDiv.find("select[name$='.logTarget']").val('System.out').attr("selected","selected");
		   			  currentDiv.find("select[name$='.logTarget']").find("option:not(:selected)").attr( "disabled", true);
		   			  currentDiv.find("input[name$='.charset']").val("UTF-8");
		   		  } else if ($(this).val() == "1"){
		   			  currentDiv.find("input[name$='.logLevel'][value$='3']").prop('checked',true);
		   			  currentDiv.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);
		   			  currentDiv.find("textarea[name$='.conversionPattern']").val("[%d{yyyy-MM-dd HH:mm:ss}]\t[%-5level]\t[%-48logger{48}]\t%msg%n");
		   			  currentDiv.find("select[name$='.logTarget']").val('System.out').attr("selected","selected");
		   			  currentDiv.find("select[name$='.logTarget']").find("option:not(:selected)").attr( "disabled", true);
		   			  currentDiv.find("input[name$='.charset']").val("UTF-8");
		   		  }
	   		  } else if (currentDiv.attr('id').indexOf("fileLogInfor") != -1){
	   			  currentDiv.find("input[name$='.logLevel']").attr( "disabled", false);
	   			  currentDiv.find("input[name$='.logFileType']").attr( "disabled", false);
		   		  currentDiv.find("textarea[name$='.conversionPattern']").attr( "readonly", "readonly");
		   		  currentDiv.find("input[name$='.charset']").attr( "readonly", "readonly");
		   		  currentDiv.find("a.qp-button-action").attr('disabled', true);
		   		  currentDiv.find("select[name$='.layout']").find("option:not(:selected)").attr("disabled",false);
		   		  currentDiv.find("input[name$='.filePath']").attr( "readonly", "readonly");
		   		  currentDiv.find("select[name$='.immediateFlush']").find("option:not(selected)").attr("disabled",false);
		   		  currentDiv.find("select[name$='.appendType']").find("option:not(selected)").attr("disabled",false);
		   		  if ($(this).val() == "3") {
		   			  currentDiv.find("textarea[name$='.conversionPattern']").removeAttr('readonly');
		   			  currentDiv.find("textarea[name$='.conversionPattern']").attr('disabled', false);
		   			  currentDiv.find("input[name$='.charset']").removeAttr('readonly');
		   			  currentDiv.find("a.qp-button-action").attr('disabled', false);
		   			  currentDiv.find("input[name$='.filePath']").removeAttr('readonly');
		   		  } else if ($(this).val() == "2"){
		   			  currentDiv.find("input[name$='.logFileType'][value$='1']").prop('checked',true);
		   			  currentDiv.find("tr.rollingFile").hide();
		   			  currentDiv.find("tr.rollingFile").find("input[type='text']").val('');
		   			  currentDiv.find("input[name$='.logLevel'][value$='1']").prop('checked',true);
		   			  currentDiv.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);	
		   			  currentDiv.find("input[name$='.logFileType'][value$='1']").prop('checked',true);
		   			  currentDiv.find("input[name$='.logFileType']").not(':checked').attr( "disabled", true);	
		   			  currentDiv.find("textarea[name$='.conversionPattern']").val("[%d{yyyy-MM-dd HH:mm:ss}]\t[%-5level]\t[%-48logger{48}]\t%msg%n");
		   			  currentDiv.find("input[name$='.charset']").val("UTF-8");
		   			  currentDiv.find("select[name$='.layout']").val('3').attr("selected","selected");
		   			  currentDiv.find("select[name$='.layout']").find("option:not(:selected)").attr( "disabled", true);
		   			  currentDiv.find("input[name$='.filePath']").val("logs");
		   			  currentDiv.find("select[name$='.immediateFlush']").val('1').attr("selected","selected");
		   			  currentDiv.find("select[name$='.immediateFlush']").find("option:not(:selected)").attr( "disabled", true);
		   			  currentDiv.find("select[name$='.appendType']").val('1').attr("selected","selected");
		   			  currentDiv.find("select[name$='.appendType']").find("option:not(:selected)").attr( "disabled", true);
		   		  } else if ($(this).val() == "1"){
		   			  currentDiv.find("input[name$='.logFileType'][value$='1']").prop('checked',true);
		   			  currentDiv.find("tr.rollingFile").hide();
		   			  currentDiv.find("tr.rollingFile").find("input[type='text']").val('');
		   			  currentDiv.find("input[name$='.logLevel'][value$='3']").prop('checked',true);
		   			  currentDiv.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);
		   			  currentDiv.find("input[name$='.logFileType'][value$='1']").prop('checked',true);
		   			  currentDiv.find("input[name$='.logFileType']").not(':checked').attr( "disabled", true);	
		   			  currentDiv.find("textarea[name$='.conversionPattern']").val("[%d{yyyy-MM-dd HH:mm:ss}]\t[%-5level]\t[%-48logger{48}]\t%msg%n");
		   			  currentDiv.find("input[name$='.charset']").val("UTF-8");
		   			  currentDiv.find("select[name$='.layout']").val('3').attr("selected","selected");
		   			  currentDiv.find("select[name$='.layout']").find("option:not(:selected)").attr( "disabled", true);
		   			  currentDiv.find("input[name$='.filePath']").val("logs");
		   			  currentDiv.find("select[name$='.immediateFlush']").val('1').attr("selected","selected");
		   			  currentDiv.find("select[name$='.immediateFlush']").find("option:not(:selected)").attr( "disabled", true);
		   			  currentDiv.find("select[name$='.appendType']").val('1').attr("selected","selected");
		   			  currentDiv.find("select[name$='.appendType']").find("option:not(:selected)").attr( "disabled", true);
		   		  }
	   		  } else if (currentDiv.attr('id').indexOf("databaseLogInfor") != -1){
	   			  currentDiv.find("input[name$='.logLevel']").attr( "disabled", false);
	   			  if ($(this).val() == "1"){
	   				  currentDiv.find("input[name$='.logLevel'][value$='3']").prop('checked',true);
	   				  currentDiv.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);	
	   			  } else if ($(this).val() == "2"){
	   				  currentDiv.find("input[name$='.logLevel'][value$='1']").prop('checked',true);
	   				  currentDiv.find("input[name$='.logLevel']").not(':checked').attr( "disabled", true);	
	   			  }
	   		  }
	   		 
	   	  });
	   	  
	   	  if($(this).is(':checked')){
	   		$(this).trigger("click");
	   	  }
	 });
}

/**
 * Function to set event for layout selector
 */
function addLayoutSelectorEvent(thiz){
	$(thiz).find("select[name$='layout']").each(function(){
   	 $(this).change(function(){
   		 var currentDiv = $(this).closest("div");
   		 //Just enable conversion pattern when layout are HTML Layout( value = 2) PatternLayout( value = 3)
   		 if(($(this).val() == "3" || $(this).val() == "2") && currentDiv.find("input[name$='.configMode']:checked").val() == "3"){
   			 currentDiv.find("textarea[name$='.conversionPattern']").removeAttr("readonly");
   			 currentDiv.find("a").removeAttr( "disabled" );
   		 }else{
   			 currentDiv.find("textarea[name$='.conversionPattern']").attr( "readonly", "readonly" );
   			 currentDiv.find("a").attr( "disabled", true );
   		 }
   	 }) ;
   	 // Trigger change event for selector
	 $(this).trigger("change");
     });
}

/**
 * Function to set event for Rolling Policy selector
 */
function addRollingPolicySelectorEvent(thiz){
	$(thiz).find("select[name$='rollingPolicy']").each(function(){
   	 $(this).change(function(){
   		 var currentDiv = $(this).closest("div");
   		 currentDiv.find("tr.timeBasedRollingPolicy").hide();
   		 currentDiv.find("tr.fixedWindowRollingPolicy").hide();
   		 if (currentDiv.find("input[name$='.logFileType']:checked").val() == 2){
   			 if($(this).val() == "3"){
   	   			currentDiv.find("tr.fixedWindowRollingPolicy").show();
   	   		 }else if ($(this).val() == "2" || $(this).val() == "1"){
   	   			currentDiv.find("tr.timeBasedRollingPolicy").show();
   	   		 }
   		 }
   	 }) ;
   	 // Trigger change event for selector
	 $(this).trigger("change");
     });
}

/**
 * Function to set event for Triggering Policy selector
 */
function addTriggeringPolicySelectorEvent(thiz){
	$(thiz).find("select[name$='triggeringPolicy']").each(function(){
   	 $(this).change(function(){
   		 var currentDiv = $(this).closest("div");
   		 currentDiv.find("label.maxFileSize").hide();
   		 currentDiv.find("input.maxFileSize").hide();
   		 if($(this).val() == "1"){
   			 currentDiv.find("label.maxFileSize").show();
      		 currentDiv.find("input.maxFileSize").show();
   		 }
   	 }) ;
   	 // Trigger change event for selector
	 $(this).trigger("change");
     });
}

/**
 * Reindex table of database log after update (add or delete a row)
 * @returns {Number}
 */
function reIndexDatabaseLog(){
	var numberDBlog = 0;
	$("#databaseWrapper").find("div").each(function(){
		var nameDBLog = "databaseLogInfor";
		var currentName = "";
		var currentDBLog = 0;
		if($(this).attr("id") != undefined){
			currentName = $(this).attr("id").replace(new RegExp("[0-9]", "g"), "")
			currentDBLog = parseInt($(this).attr("id").match(/\d+/)[0]);
		}
		if(currentName == nameDBLog){
			$(this).attr("id","databaseLogInfor"+numberDBlog+"");
			$(this).css({"border-color":"red","border-style":"dashed","border-width":"2px"});
			$(this).css({"margin-bottom":"20px"});
			$(this).find("input[name$='.logLevel']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].logLevel");
			$(this).find("input[name$='.appenderAutocomplete']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].appenderAutocomplete");
			$(this).find("input[name$='.appender']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].appender");
			$(this).find("input[name$='.dbDriver']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbDriver");
			$(this).find("input[name$='.dbUrl']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbUrl");
			$(this).find("input[name$='.dbUserName']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbUserName");
			$(this).find("input[name$='.dbPassword']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].dbPassword");
			$(this).find("select[name$='.layout']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].layout");
			$(this).find("input[name$='.sqlString']").attr("name", "databaseLog.lstLogDetail["+numberDBlog+"].sqlString");
		}
		else{
			return;
		}
		numberDBlog++;
	});
	return numberDBlog;
}

/**
 * Remove a row in database log table
 * @param obj
 */
function removeDatabaseLog(obj){
	if($.qp.confirm(dbMsgSource['sc.loggingmanagement.0035'])){
		var currentDiv = obj.closest("div");
		currentDiv.remove();
		reIndexDatabaseLog();
		var numberDBlog = parseInt($("#numberDatabaseLog").val());
		numberDBlog--;
		$("#numberDatabaseLog").val(numberDBlog);
	}
}

/**
 * Reindex file log table after update(add or delete a row)
 * @returns {Number}
 */
function reIndexFileLog(){
	var numberFileLog = 0;
	$("#fileWrapper").find("div").each(function(){
		var nameFileLog = "fileLogInfor";
		var currentName = "";
		var currentFileLog = 0;
		if($(this).attr("id") != undefined){
			currentName = $(this).attr("id").replace(new RegExp("[0-9]", "g"), "")
			currentFileLog = parseInt($(this).attr("id").match(/\d+/)[0]);
		}
		if(currentName == nameFileLog){
			$(this).css({"border-color":"red","border-style":"dashed","border-width":"2px"});
			$(this).css({"margin-bottom":"20px"});
			$(this).attr("id","fileLogInfor"+numberFileLog+"");
			
			$(this).find("input[name$='.logDetailId']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].logDetailId");
			$(this).find("input[name$='.logFileType']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].logFileType");
			$(this).find("input[name$='.logLevel']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].logLevel");
			$(this).find("input[name$='.appenderAutocomplete']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].appenderAutocomplete");
			$(this).find("input[name$='.appender']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].appender");
			$(this).find("input[name$='.patternFileName']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].patternFileName");
			$(this).find("input[name$='.filePath']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].filePath");
			//$(this).find("input[name$='.fileEncoding']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].fileEncoding");
			$(this).find("select[name$='.immediateFlush']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].immediateFlush");
			$(this).find("select[name$='.appendType']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].appendType");
			$(this).find("input[name$='.maxFileSize']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxFileSize");
			$(this).find("input[name$='.maxBackupIndex']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxBackupIndex");
			$(this).find("input[name$='.datePattern']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].datePattern");
			$(this).find("select[name$='.layout']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].layout");
			$(this).find("textarea[name$='.conversionPattern']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].conversionPattern");
			//$(this).find("a").attr("onclick","openDialogConversionPattern('fileLog.lstLogDetail["+numberFileLog+"].',2,"+numberFileLog+");");
			
			$(this).find("input[name$='.configMode']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].configMode");
			$(this).find("input[name$='.charset']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].charset");
			$(this).find("a.qp-button-action").attr("onclick","openDialogConversionPattern('fileLog.lstLogDetail["+numberFileLog+"].',2,"+numberFileLog+");");
			$(this).find("select[name$='.rollingPolicy']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].rollingPolicy");
			$(this).find("select[name$='.triggeringPolicy']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].triggeringPolicy");
			$(this).find("input[name$='.maxHistory']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxHistory");
			$(this).find("input[name$='.totalSizeCap']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].totalSizeCap");
			$(this).find("input[name$='.minIndex']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].minIndex");
			$(this).find("input[name$='.maxIndex']").attr("name", "fileLog.lstLogDetail["+numberFileLog+"].maxIndex");
			
			var patternHiddenDiv = $(this).find("div[id^='patternHidden']");
			patternHiddenDiv.attr("id","patternHidden_2_"+numberFileLog);
			patternHiddenDiv.find("input").each(function(){
				var oldName = $(this).attr("name").split("lstConversionPattern");
				$(this).attr("name", "fileLog.lstLogDetail["+numberFileLog+"].lstConversionPattern" + oldName[1]);
			});
		}
		else{
			return;
		}
		numberFileLog++;
	});
	return numberFileLog;
}

/**
 * Remove a file log
 * @param obj
 */
function removeFileLog(obj){
	if($.qp.confirm("Do you want to delete this log?")){
		var currentDiv = $(obj).closest("div");
		currentDiv.remove();
		reIndexFileLog();
		var numberFileLog = parseInt($("#numberFileLog").val());
		numberFileLog--;
		$("#numberFileLog").val(numberFileLog);
	}
}

/**
 * Allow a table to sort
 * @param table
 */
function sortTable(table){
	if(table!= undefined){
		table.find("tbody").sortable({
			helper : function(e, ui) {
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			update : function(event, ui) {
	
			$.qp.reCalIndex(table);
			$.qp.reArrayIndex(table);
			reIndex();
			},
			items : 'tr',
			cursor : 'move',
			handle : '.sortable'
		});
	}
}

function loadAppenderDefault(obj) {
	
	var results = [];
	jQuery.each(CL_LOG_APPENDER, function(key, value) {
		results.push({
			"optionLabel" : value,
			"optionValue" :value
		});
	});
	return results;
}

/*function changeLayout(obj) {
	var currentDatabaseLog = obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"))
	if($(obj).val() == 3) {
		$("[name='databaseLog.lstLogDetail["+currentDatabaseLog+"].sqlString']").prop('disabled', false);
	} else {
		$("[name='databaseLog.lstLogDetail["+currentDatabaseLog+"].sqlString']").prop('disabled', true);
	}
}*/

function changeLogFileType(obj) {
	var currentFileLog = obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"))
	if($(obj).val() == 1) {
		$("[name='fileLog.lstLogDetail["+currentFileLog+"].patternFileName']").prop('disabled', true);
	} else {
		$("[name='fileLog.lstLogDetail["+currentFileLog+"].patternFileName']").prop('disabled', false);
	}
}

function onchangeDBType(thiz){
	var value = $(thiz).val();
	var divInfor = $(thiz).closest("div.databaseLogInfor");
	if(value == 1){
		$(divInfor).find("#dbDriver").val(CL_POSTGRESQL_CONFIG['dbDriver']);
		$(divInfor).find("#dbUser").val(CL_POSTGRESQL_CONFIG['dbUser']);
		$(divInfor).find("#dbPassword").val(CL_POSTGRESQL_CONFIG['dbPassword']);
		$(divInfor).find("#dbConnection").val(CL_POSTGRESQL_CONFIG['dbHostName'] +":"+ CL_POSTGRESQL_CONFIG['dbPort']);
	} else if(value == 2){
		$(divInfor).find("#dbDriver").val(CL_ORACLE_CONFIG['dbDriver']);
		$(divInfor).find("#dbUser").val(CL_ORACLE_CONFIG['dbUser']);
		$(divInfor).find("#dbPassword").val(CL_ORACLE_CONFIG['dbPassword']);
		$(divInfor).find("#dbConnection").val(CL_ORACLE_CONFIG['dbHostName'] +":"+ CL_POSTGRESQL_CONFIG['dbPort']);
	}
}