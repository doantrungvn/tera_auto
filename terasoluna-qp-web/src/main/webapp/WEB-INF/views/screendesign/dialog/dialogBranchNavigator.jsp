<script id="tbl-ifcondition-template" type="text/template">
	<tr>
		<td class="qp-output-fixlength tableIndex">1</td>
		<td>
			<input type="text" class="form-control" name="caption" />
			<input type="hidden" class="form-control" name="detailId" />
		</td>
		<td><input type="text" class="form-control" name="conditionRemark" /></td>
		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJSEx(this,false);" style="margin-top: -2px;" href="javascript:void(0)"></a></td>
	</tr>
</script>
<!-- Dialog for advance setting -->
<div class="modal fade" id="dialogBranchNavigator" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 45%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0497"/></span>
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
						<table class="table table-bordered qp-table-form" id="tableBranchInfor" >
							<colgroup>
								<col width="30%" />
								<col width="70%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.screendesign.0496"></qp:message></th>
									<td>
										<input maxlength="255" class="form-control qp-input-text" name="name"/>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"></qp:message></th>
									<td>
										<input maxlength="200" class="form-control qp-input-text" name="remark"/>
									</td>
								</tr>
							</tbody>	
						</table>
						<br/>
						<table class="table table-bordered qp-table-list" id="tbl-ifcondition" data-ar-callback="updateIndexOfCondtionDetail">
							<colgroup>
								<col />
								<col width="50%" />
								<col width="50%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0036"/></th>
									<th><qp:message code="sc.screendesign.0500"/><%--  &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label> --%></th>
									<th><qp:message code="sc.businesslogicdesign.0064"/></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="qp-add-left">
	                    	<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" id="addNew" onclick="$.qp.addRowJSByLinkEx(this,'tbl-ifcondition','tbl-ifcondition-template');" style="margin-top: 3px;" href="javascript:void(0)"></a>
	                   	</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="removeASetting" class="btn qp-button-client" onclick="removeBranchNavigator(this);"><qp:message code="sc.sys.0014" /></button>
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveBranchInformation(this);"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>