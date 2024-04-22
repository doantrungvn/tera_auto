<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogAdvanceSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 55%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0488"/></span>
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
						<input type="hidden" id="constrainsTypeAdvance"/>
						<input type="hidden" id="operatorCodeAdvance" value="9"/>
						<input type="hidden" id="defaultType"/>
						<input type="hidden" id="operatorCode"/>
						<input type="hidden" id="timeValueTemp"/>
						<input type="hidden" id="optionLabelAdvance"/>
						<input type="hidden" id="optionValueAdvance"/>
						<input type="hidden" id="optionLabelAutocpmleteAdvance"/>
						<input type="hidden" id="optionValueAutocpmleteAdvance"/>
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableItemDefaultSetting" >
							<colgroup>
								<col width="50%" />
								<col width="50%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.screendesign.0155"/></th>
									<td id="typeOfCodelist"></td>
								</tr>
								<tr id="codelistCode">
									<th><qp:message code="sc.screendesign.0095"/></th>
									<td id="codelistCodeValue">&nbsp;</td>
								</tr>
								<tr id="supportValue">
									<td colspan="2">
										<table id='tbl_list_Suport' class='table table-bordered qp-table-list' data-ar-callback="removeColumnValue">
											
										</table>
									</td>
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveDialogSetting();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>