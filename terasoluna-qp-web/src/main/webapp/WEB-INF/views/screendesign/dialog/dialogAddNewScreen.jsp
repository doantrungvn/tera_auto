<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogAddNewScreen" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 45%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0498"/></span>
					</div>
					<script type="text/javascript">
						$(document).ready(function() {
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
						<input type="hidden" id="currentTempScreenId"/>
						<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
						<input type="hidden" id="currentProjectId" value="${sessionScope.CURRENT_PROJECT.projectId}">
						<input type="hidden" id="fromScreenId">
						<input type="hidden" id="toScreenId">
						<input type="hidden" id="connectionId">
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableScreenInformation" >
							<colgroup>
								<col width="35%" />
								<col width="65%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.screendesign.0005"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<input maxlength="255" class="form-control qp-input-text qp-convention-name" name="screenName"/>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0007"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<input maxlength="200" class="form-control qp-input-text qp-convention-code" name="screenCode"/>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0006"/></th>
									<td id="moduleName">&nbsp;</td>
								</tr>
								<tr>
									<th><qp:message code="sc.functiondesign.0002"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<qp:autocomplete optionValue="optionValue"
											selectSqlId="getAllFunctionDesignForAutocomplete"
											name="functionDesignId" value=""
											displayValue=""
											arg01="" arg02="20" arg03="0"
											optionLabel="optionLabel"
											maxlength="200">
										</qp:autocomplete>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0009"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<select name="screenPatternType" class="form-control qp-input-select" onchange="changeScreenPattern(this)">
											<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
											<option value="1"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('1')}"/></option>
											<option value="2"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('2')}"/></option>
											<option value="3"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('3')}"/></option>
											<option value="4"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('4')}"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0008"></qp:message></th>
									<td id="templateType">&nbsp;</td>
								</tr>
								<tr id="confirmationType">
			                        <th><qp:message code="sc.screendesign.0010"></qp:message></th>
			                        <td>
			                          	<c:forEach var="item" items="${CL_MODULE_CONFIRM_TYPE}">
											<label><form:radiobutton path="" name="confirmationType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/>&nbsp;<qp:message code="${CL_MODULE_CONFIRM_TYPE.get(item.key)}" /></label>
										</c:forEach>
									</td>
			                    </tr>
			                    <tr id="completionType">
			                        <th><qp:message code="sc.screendesign.0011"></qp:message></th>
			                    	<td>
										<c:forEach var="item" items="${CL_MODULE_COMPLETE_TYPE}">
											<label><form:radiobutton path="" name="completionType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/>&nbsp;<qp:message code="${CL_MODULE_COMPLETE_TYPE.get(item.key)}" /></label>
										</c:forEach>
									</td>
			                    </tr>
								<tr>
									<th><qp:message code="sc.sys.0028" /></th>
									<td><textarea name="remark" rows="3" class="form-control qp-input-textarea" maxlength="2000"></textarea></td>
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="removeASetting" class="btn qp-button-client" onclick="removeScreenInformation(this);"><qp:message code="sc.sys.0014" /></button>
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveScreenInformation();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>