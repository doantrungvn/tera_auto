$.qp.addRowJSByLinkForRegister = function(link) {
		var table = $(link).closest("div").prev("table");
		var obj = $("#" + $(table).attr("id") + "-template").tmpl();

		$(table).append(obj);
		$(obj).find(".input-datepicker").each(function() {// initial
			$.qp.initialDatePicker(this);
		});
		$(obj).find(".qp-input-datetimepicker-detail").each(function() {// initial
			$.qp.initialDateTimePickerDetail($(this));
		});
		$(obj).find(".qp-input-autocomplete").each(function() {// initial
			$.qp.initialAutocomplete($(this));
		});
		$.qp.initialAutoNumeric(obj);// initial Auto numeric
		$.qp.reCalIndexForDesignInformation(table); // refresh row index (No.)
		$.qp.reArrayIndexForDesignInformation(table);
		$.qp.alternateRowColorInTable($(table).attr("id")); // alternate color
};

$.qp.reArrayIndexForDesignInformation = function(table) {
		$table=$(table);
		var ignoreRows = parseInt($table.attr("data-ar-irows"));
		var tableTreeLevel = parseInt($table.attr("data-ar-tlevel"));
		if(isNaN(ignoreRows)) {
			ignoreRows = 0;
		}
		if(isNaN(tableTreeLevel)) {
			tableTreeLevel = 0;
		}
		$table	.children("tbody").find(">tr")
				.each(function(i) {
					if(i>=ignoreRows) {
							$(this)	.find("[name*='['][name*='].']")
									.each(function() {
												var regex = /\[\d*\]/g;
												var index=0;
												var replacements = [];
												var tempTable = $table;
												for(var j=tableTreeLevel-1;j>-1;j--) {
													tempTable=$(tempTable).parents("table:first");
													replacements[j] ='[' + tempTable.parents("tr:first").attr("data-ar-rindex")+ ']';
												}
												if ($(this).attr("name").match(regex)[tableTreeLevel]!='undefined') {
													$(this).attr("name",$(this).attr("name").replace(regex,function(match,stringIndex){
														var replacement = "[]";
														if(index==tableTreeLevel){
															replacement = '[' + (i-ignoreRows) + ']';
														} else {
															if(index>tableTreeLevel){
																replacement =  match;
															} else replacement=replacements[index];
														}
														index++;
														return replacement;
													}));
												}
												
									});
							$(this)	.find("[id*='['][id*='].']")
							.each(function() {
										var regex = /\[\d*\]/g;
										var index=0;
										var replacements = [];
										var tempTable = $table;
										for(var j=tableTreeLevel-1;j>-1;j--) {
											tempTable=$(tempTable).parents("table:first");
											replacements[j] ='[' + tempTable.parents("tr:first").attr("data-ar-rindex")+ ']';
										}
										if ($(this).attr("id").match(regex)[tableTreeLevel]!='undefined') {
											$(this).attr("id",$(this).attr("id").replace(regex,function(match,stringIndex){
												var replacement = "[]";
												if(index==tableTreeLevel){
													replacement = '[' + (i-ignoreRows) + ']';
												} else {
													if(index>tableTreeLevel){
														replacement =  match;
													} else replacement=replacements[index];
												}
												index++;
												return replacement;
											}));
										}
							});
							$(this)	.find("[for*='['][for*='].']")
							.each(function() {
										var regex = /\[\d*\]/g;
										var index=0;
										var replacements = [];
										var tempTable = $table;
										for(var j=tableTreeLevel-1;j>-1;j--) {
											tempTable=$(tempTable).parents("table:first");
											replacements[j] ='[' + tempTable.parents("tr:first").attr("data-ar-rindex")+ ']';
										}
										if ($(this).attr("for").match(regex)[tableTreeLevel]!='undefined') {
											$(this).attr("for",$(this).attr("for").replace(regex,function(match,stringIndex){
												var replacement = "[]";
												if(index==tableTreeLevel){
													replacement = '[' + (i-ignoreRows) + ']';
												} else {
													if(index>tableTreeLevel){
														replacement =  match;
													} else replacement=replacements[index];
												}
												index++;
												return replacement;
											}));
										}
							});
						}
					});
	};
	
	// Refresh Row Index in Table
	$.qp.reCalIndexForDesignInformation = function(table) {
		$table=$(table);
		var ignoreRows = parseInt($table.attr("data-ar-irows"));
		if(isNaN(ignoreRows)) {
			ignoreRows = 0;
		}
		$table.children("tbody").find(">tr").each(function(i) {
			if(i>=ignoreRows){
				$(this).find(">td.tableIndex").html(i-ignoreRows+1);
				$(this).find("input.tableIndex").val(i-ignoreRows);
				$(this).attr("data-ar-rindex",i-ignoreRows);
			}
		});

	};
	
	function removeTableRow(obj, tablename){
		if($.qp.confirm("Do you want to delete this row?")){
			var currentDiv = obj.closest("tr");
			currentDiv.remove();
			table = $("#"+tablename);
			$.qp.reCalIndexForDesignInformation(table); // refresh row index (No.)
			$.qp.reArrayIndexForDesignInformation(table);
		}
	}


