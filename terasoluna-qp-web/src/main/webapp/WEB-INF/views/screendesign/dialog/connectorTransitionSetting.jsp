<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogAdvanceSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 45%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0499"/></span>
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
						<input type="hidden" id="currentRow"/>
						<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
						<input type="hidden" id="currentProjectId" value="${sessionScope.CURRENT_PROJECT.projectId}">
						<input type="hidden" id="fromScreenId">
						<input type="hidden" id="toScreenId">
						<input type="hidden" id="connectionId">
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableItemDefaultSetting" >
							<colgroup>
								<col width="40%" />
								<col width="60%" />
							</colgroup>
							<tbody>
								<%-- <tr>
									<th><qp:message code="sc.screendesign.0083"/></th>
									<td id="typeOfCodelist">
										<input type='checkbox' name='isSubmit' id='isSubmit' onclick="isSubmitEvent(this)" style='margin-top: 1px'>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0006"/></th>
									<td id="moduleName">&nbsp;</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0068"/></th>
									<td id="naviTo">&nbsp;</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0341"/></th>
									<td>
										<qp:autocomplete optionLabel="optionLabel" onChangeEvent="changeBlogic" arg05="" arg04="0" selectSqlId="getBlogicNavigateByScreenIdAutocomplete" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateToBlogic" arg03="${f:h(sessionScope.CURRENT_LANGUAGE.languageId)}" arg01="" arg02="20"/>
									</td>
								</tr> --%>
								<tr>
									<th><qp:message code="sc.screendesign.0492"/><span class="qp-required-field"> (*) </span></th>
									<td>
										<input type="text" class="form-control qp-input-text qp-convention-name" name="transitionName">
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0493"/><span class="qp-required-field"> (*) </span></th>
									<td>
										<input type="text" class="form-control qp-input-text qp-convention-code" name="transitionCode">
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0494"/></th>
									<td id="buttonOrLink">
										<!-- <input type="text" class="form-control qp-input-text" name="transitionCode"> -->
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0495"/></th>
									<td id="bLogicName">
										<!-- <input type="text" class="form-control qp-input-text" name="transitionCode"> -->
									</td>
								</tr>
								<tr>
									<th>From Screen</th>
									<td id="fromScreen">
										<!-- <input type="text" class="form-control qp-input-text" name="transitionCode"> -->
									</td>
								</tr>
								<tr>
									<th>To Screen</th>
									<td id="toScreen">
										<!-- <input type="text" class="form-control qp-input-text" name="transitionCode"> -->
									</td>
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="removeASetting" class="btn qp-button-client" onclick="removeSetting();"><qp:message code="sc.sys.0014" /></button>
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveDialogSetting();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>