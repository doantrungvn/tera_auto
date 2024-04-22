<script type="text/javascript">
	$(function(){
		var operatorCode = {};
		<c:forEach items="${CL_QP_OPERATOR_CODE}" var="item"> 
			operatorCode['${item.key}'] = "<qp:message code='${item.value}'/>"; 
		</c:forEach>
		console.log(JSON.stringify(operatorCode));
		$("#operatorCodeAdvance").val(JSON.stringify(operatorCode));
		console.log($("#operatorCodeAdvance").val());
	});
	var booleanDefaultValue = [];
	<c:forEach items="${CL_BOOLEAN_DEFAULT_VALUE}" var="item">
		booleanDefaultValue.push({
			"value" : "${item.key}",
			"label" : "<qp:message code='${item.value}'/>"
		});
	</c:forEach>
</script>
<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogAdvanceSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 55%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.tabledesign.0074"/></span>
					</div>
					<div class="panel-body" id="panelBody">
						<script id="tbl_list_Suport-template" type="text/template">
								<tr>
									<td><input type="radio" value="supportValue" name="supportValue"></td>
									<td class="td-word-wrap colOptionName" name="optionName"><input type="text" class="form-control" name="codelistName" value="" maxlength="200"/></td>
									<td class="td-word-wrap colOptionValue"><input type="text" class="form-control" name="codelistValue" value="" maxlength="200"/></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove table design details" onclick="$.qp.removeRowJS('tmp_list_table', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
						</script>
						<input type="hidden" id="flagOperation" value="9"/>
						<input type="hidden" id="currentRow"/>
						<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
						<input type="hidden" id="currentProjectId" value="${sessionScope.CURRENT_PROJECT.projectId}">
						<input type="hidden" id="defaultValueAdvanceSettingTemp">
						<input type="hidden" id="groupTypeId">
						<input type="hidden" id="sqlCodeValue">
						<input type="hidden" id="setTimeNowStatus">
						<input type="hidden" id="datasourceTypeAdvance"/>
						<input type="hidden" id="defaultTypeAdvance"/>
						<input type="hidden" id="operatorCodeAdvance"/>
						<input type="hidden" id="minValAdvance"/>
						<input type="hidden" id="maxValAdvance"/>
						<input type="hidden" id="optionLabelAdvance"/>
						<input type="hidden" id="optionValueAdvance"/>
						<input type="hidden" id="optionLabelAutocpmleteAdvance"/>
						<input type="hidden" id="optionValueAutocpmleteAdvance"/>
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableDialogAdvanceSetting" >
							<colgroup>
								<col width="20%" />
								<col width="40%" />
								<col width="20%" />
								<col width="20%" />
							</colgroup>
							<tbody>
								<tr id="constrains">
									<th><qp:message code="sc.tabledesign.0041"/></th>
									<td colspan="3">
										<label class='radio-inline'><input type='radio' name='constrains' id='1' checked="checked" value='any' onchange ="changeConstrains(this)"/><qp:message code="sc.tabledesign.0035"/></label>
										<label class='radio-inline'><input type='radio' name='constrains' value='range' onchange ="changeConstrains(this)"/><qp:message code="sc.tabledesign.0036"/></label>
										<label class='radio-inline'><input type='radio' name='constrains' value='dataSource' onchange ="changeConstrains(this)"/><qp:message code="sc.tabledesign.0037"/></label>  
									</td>
								</tr>
								<tr id="valueAdvanceSetting"></tr> 
								<tr id="defaultValueAdvanceSetting" ></tr>
								<tr id="typeOfList">
									<th><qp:message code="sc.tabledesign.0044"/></th>
									<td colspan='3' style="border-right-color:red;border-right-style:dashed;border-right-width:2px">
										<label class='radio-inline'><input type='radio' name='typeOfList' id='1' value='userDefine' onchange ="changeDataSource(this)"/><qp:message code="sc.tabledesign.0038"/></label>
										<label class='radio-inline'><input type='radio' name='typeOfList' value='codeList' onchange ="changeDataSource(this)"/><qp:message code="sc.tabledesign.0039"/></label>
										<label class='radio-inline'><input type='radio' name='typeOfList' value='sqlBuilder' onchange ="changeDataSource(this)"/><qp:message code="sc.tabledesign.0040"/></label>
									</td>
								</tr>
								<tr id="codelistCode">
									<th><qp:message code="sc.codelist.0002"/>&nbsp;<span class="qp-required-field">(*)</span></th>
									<td>
										<qp:autocomplete 
											name="codelistCode" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllCodeList" value="${domainDesignForm.datasourceId}" displayValue="${domainDesignForm.codelistCodeAutocomplete}" arg02="" arg01="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="changeCodeListAC" mustMatch="true" maxlength="200">
										</qp:autocomplete>
									</td>
									<td>
										<a id="codelistView" href="${pageContext.request.contextPath}/codelist/view?codeListId=" class="qp-link-popup"><qp:message code="sc.tabledesign.0048"/></a> &nbsp;&nbsp;&nbsp;
									</td>
									<td style="border-right-color:red;border-right-style:dashed;border-right-width:2px">
										<a id="codelistRegis" class="codelistRegis" href="${pageContext.request.contextPath}/codelist/register"><qp:message code="sc.tabledesign.0049"/></a>
									</td>
								</tr>
								<tr id="sqlCode">
									<th><qp:message code="sc.tabledesign.0050"/>&nbsp;<span class="qp-required-field">(*)</span></th>
									<td>
										<qp:autocomplete name="sqlCode" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllSqlBuilderAC" 
											value="${domainDesignForm.datasourceId}" displayValue="${domainDesignForm.sqlCodeAutocomplete}"
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg04="0"
											onChangeEvent="changeSqlCodeAC" mustMatch="true" maxlength="200">
										</qp:autocomplete>
									</td>
									<td>
										<a id="sqlView" href="${pageContext.request.contextPath}/" class="qp-link-popup"><qp:message code="sc.tabledesign.0051"/></a>
									</td>
									<td>
										<a id="sqlRegis" class="sqlRegis" href="${pageContext.request.contextPath}/sqldesign/register"><qp:message code="sc.tabledesign.0052"/> </a>
									</td>
								</tr>
								<tr id="default">
									<th><qp:message code="sc.tabledesign.0042"/></th>
									<td>
										<qp:autocomplete 
											name="codelistDefault" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllCodeListDetail" value="${domainDesignForm.defaultValue}" displayValue="${domainDesignForm.codelistDefaultAutocomplete}" arg02="" onChangeEvent="changeDefaultCodeListAC" mustMatch="true" maxlength="200">
										</qp:autocomplete>
									</td>
									<td colspan='3'></td>
								</tr>
								<tr id="isSupport">
									<th></th>
									<td colspan='3'>
										<div class='checkbox'>
											<label for='supportOptionValue'>
												<input type='checkbox' name='supportOptionValue' id='supportOptionValue' onclick="changeSupportOptionValue(this)" style='margin-top: 1px'>
												<b><qp:message code="sc.tabledesign.0053"/></b>
											</label>
										</div>
									</td>
								</tr>
								<tr id="supportValue">
									<th></th>
									<td colspan='3'>
										<table id='tbl_list_Suport' class='table table-bordered qp-table-list' data-ar-callback="removeColumnValue"></table>
									 	<div class='qp-add-left'>
									 		<a class='btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action' onclick='$.qp.addRowJSByLinkEx(this);' style='margin-top: 3px;' href='javascript:void(0)'></a>
									 	</div>
									</td>
								</tr>
								<tr id="sqlDesignOutputOptionValue">
									<th><qp:message code="sc.screendesign.0201"/></th>
									<td style="font-size: 12px;">
										<div class="input-group " style="">
											<input type="text" name="sqlOutputOptionLabelAutocomplete" id="sqlOutputOptionLabelAutocompleteId" class="form-control" value="" 
												optionValue="optionValue" optionLabel="optionLabel" selectSqlId="" emptyLabel="sc.sys.0030" onselectevent="" 
												onchangeevent="changeTableAC" onremoveevent="" mustmatch="true" maxlength="200" minlength=""
												sourcetype="local" sourcecallback="getOptionNameOutputSql" placeholder="Search…" autocomplete="off" previousvalue="" previouslabel="" selectedvalue="false">
											<input type="hidden" class="" name="sqlOutputOptionLabel" value=""><span class="input-group-addon dropdown-toggle" data-dropdown="dropdown" style="cursor: pointer;" onclick="$.qp.searchAutocompleteData(this)"> <span class="caret"></span></span>
										</div>
									</td>
									<td></td>
									<td></td>
								</tr>
								
								<tr id="sqlDesignOutputDisplayValue">
									<th><qp:message code="sc.tabledesign.0046"/>&nbsp;<span class="qp-required-field">(*)</span></th>
									<td style="font-size: 12px;">
										<div class="input-group " style="">
											<input type="text" name="sqlOutputOptionValueAutocomplete" id="sqlOutputOptionValueAutocompleteId" class="form-control" value="" 
												optionValue="optionValue" optionLabel="optionLabel" selectSqlId="" emptyLabel="sc.sys.0030" onselectevent="" 
												onchangeevent="changeTableAC" onremoveevent="" mustmatch="true" maxlength="200" minlength=""
												sourcetype="local" sourcecallback="getOptionNameOutputSql" placeholder="Search…" autocomplete="off" previousvalue="" previouslabel="" selectedvalue="false">
											<input type="hidden" class="" name="sqlOutputOptionValue" value=""><span class="input-group-addon dropdown-toggle" data-dropdown="dropdown" style="cursor: pointer;" onclick="$.qp.searchAutocompleteData(this)"> <span class="caret"></span></span>
										</div>
									</td>
									<td></td>
									<td></td>
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveDialogAdvanceSetting();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>