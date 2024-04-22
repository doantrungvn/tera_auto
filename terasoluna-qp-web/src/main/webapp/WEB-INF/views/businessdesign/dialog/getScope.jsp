<!-- Dialog get scope setting -->
<div id="dialog-getscope-setting" class="modal fade" style="display: none;">
    <input type="hidden" name="dataTypeInput" />
    <input type="hidden" name="arrayFlgInput" />
    
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0252" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0042" /></th>
									<td>
                                        <c:forEach var="item" items="${CL_BD_INPUT_SCOPE}">
                                            <label><input type="radio" name="scopeType" value="${item.key}" class="qp-input-radio qp-input-radio-margin" onchange="onchangeScopeTypeOfGetScope(this)"/><qp:message code="${item.value}" /></label>
                                        </c:forEach>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0111" /></th>
									<td>
										<qp:autocomplete name = "scopeValue" optionLabel="optionLabel" selectSqlId="getAutocompleteSession" optionValue="optionValue" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="false" onChangeEvent="onchangeValue"></qp:autocomplete>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn qp-button-client " onclick="saveDialogGetScope(this)">
					<qp:message code="sc.sys.0031"></qp:message>
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Dialog get scope setting -->