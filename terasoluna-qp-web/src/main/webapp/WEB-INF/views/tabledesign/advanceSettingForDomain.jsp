<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogAdvanceSettingDomain" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 40%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.tabledesign.0074"/></span>
					</div>
					<script type="text/javascript">
						$(document).ready(function() {
							var operatorCode = {};
							<c:forEach items="${CL_QP_OPERATOR_CODE}" var="item"> 
								operatorCode['${item.key}'] = "<qp:message code='${item.value}'/>"; 
							</c:forEach>
							$("#operatorCode").val(JSON.stringify(operatorCode));
						});
					</script>
					<div class="panel-body" id="panelBody">
						<script id="tbl_list_Suport-template" type="text/template">
								<tr>
									<td><input type="radio" value="supportValue" name="supportValue"></td>
									<td class="td-word-wrap colOptionName" name="optionName"><input type="text" class="form-control" name="codelistName" value="" maxlength="200"/></td>
									<td class="td-word-wrap colOptionValue"><input type="text" class="form-control" name="codelistValue" value="" maxlength="200"/></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove table design details" onclick="$.qp.removeRowJS('tmp_list_table', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
						</script>
						<input type="hidden" id="currentRow"/>
						<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
						<input type="hidden" id="currentProjectId" value="${sessionScope.CURRENT_PROJECT.projectId}">
						<input type="hidden" id="groupTypeId">
						<input type="hidden" id="sqlCodeValue">
						<input type="hidden" id="setTimeNowStatus">
						<input type="hidden" id="datasourceTypeAdvance"/>
						<input type="hidden" id="operatorCodeAdvance" value="9"/>
						<input type="hidden" id="defaultType"/>
						<input type="hidden" id="operatorCode"/>
						<input type="hidden" id="timeValueTemp"/>
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableDialogAdvanceSetting" >
							<colgroup>
								<col width="30%" />
								<col width="30%" />
								<col width="20%" />
								<col width="20%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.domaindesign.0009"/></th>
									<td id="fmtCode" colspan="3">&nbsp;</td>
								</tr>
								<tr id="constrains">
									<th><qp:message code="sc.tabledesign.0041"/></th>
									<td colspan="3">
										<label class='radio-inline'><input type='radio' name='constrains' id='1' checked="checked" value='any' onchange ="changeConstrains(this)"/><qp:message code="sc.tabledesign.0035"/></label>
										<label class='radio-inline'><input type='radio' name='constrains' value='range' onchange ="changeConstrains(this)"/><qp:message code="sc.tabledesign.0036"/></label>
										<label class='radio-inline'><input type='radio' name='constrains' value='dataSource' onchange ="changeConstrains(this)"/><qp:message code="sc.tabledesign.0037"/></label>  
									</td>
								</tr>
								<tr id="valueAdvanceSetting">
									<th><qp:message code="sc.tabledesign.0043"/></th>
									<td>
									</td>
								</tr> 
								<tr id="defaultValueAdvanceSetting" >
									<th><qp:message code="sc.tabledesign.0042"/></th>
									<td colspan='3'>
									</td>
								</tr>
								<tr id="typeOfList">
									<th><qp:message code="sc.tabledesign.0044"/></th>
									<td colspan='3'>
										<label class='radio-inline'><input type='radio' name='typeOfList' id='1' value='userDefine' onchange ="sajhagsdhasdas(this)"/><qp:message code="sc.tabledesign.0038"/></label>
										<label class='radio-inline'><input type='radio' name='typeOfList' value='codeList' onchange ="sajhagsdhasdas(this)"/><qp:message code="sc.tabledesign.0039"/></label>
										<label class='radio-inline'><input type='radio' name='typeOfList' value='sqlBuilder' onchange ="sajhagsdhasdas(this)"/><qp:message code="sc.tabledesign.0040"/></label>
									</td>
								</tr>
								<tr id="codelistCode">
									<th><qp:message code="sc.tabledesign.0047"/></th>
									<td>
										<qp:autocomplete 
											name="codelistCode" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllCodeList" value="" displayValue="" arg02="" arg01="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="changeCodeListAC" mustMatch="true" maxlength="200">
										</qp:autocomplete>
									</td>
									<td>
										<a id="codelistView" href="${pageContext.request.contextPath}/codelist/view?codeListId=" class="qp-link-popup"><qp:message code="sc.tabledesign.0048"/></a> &nbsp;&nbsp;&nbsp;
									</td>
									<td>
										<a id="codelistRegis" class="codelistRegis" href="${pageContext.request.contextPath}/codelist/register"><qp:message code="sc.tabledesign.0049"/> </a>
									</td>
								</tr>
								<tr id="sqlCode">
									<th><qp:message code="sc.tabledesign.0050"/></th>
									<td>
										<qp:autocomplete name="sqlCode" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllSqlBuilderAC" value="" displayValue=""
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg04="0" onChangeEvent="changeSqlCodeAC" mustMatch="true" maxlength="200">
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
											name="codelistDefault" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllCodeListDetail" value="" displayValue="" arg02="" onChangeEvent="" mustMatch="true" maxlength="200">
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
								
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveDialogAdvanceForDomainSetting();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>