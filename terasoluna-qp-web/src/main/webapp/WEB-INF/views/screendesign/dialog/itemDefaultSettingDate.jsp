<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogAdvanceSettingDate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 40%; height: 500px">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0488"/></span>
					</div>
					<script type="text/javascript">
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
						<input type="hidden" id="setTimeNowStatus">
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableItemDefaultSettingDate" >
							<colgroup>
								<col width="30%" />
								<col width="40%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr id="default">
									
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveDialogSettingDate();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>