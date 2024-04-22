<!-- Dialog Feedback setting -->
<div id="dialog-feedback-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0100"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063"/></th>
									<td ><input type="text" class="form-control qp-input-text" style="width: 100%" name="label" maxlength="200"></td>
									<th><label><qp:message code="sc.businesslogicdesign.0101"/> </label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<select class="form-control qp-input-select" name="type" onchange="onchangeFeedbackTypeOfFeedbackSet(this)">
											<option value="">
												<qp:message code="sc.sys.0030"></qp:message>
											</option>
											<c:forEach items="${CL_BD_FEEDBACK_MESSAGE_TYPE}" var="item">
												<option value="${item.key}"><qp:message code="${item.value}"/></option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.businesslogicdesign.0072"/> </label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td colspan="3">
										<div style="width: 95%" class="pull-left">
											<qp:autocomplete name = "messageCode" mustMatch="false" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet"></qp:autocomplete>
										</div>
										<div style="width: 5%" class="pull-right">
											<span title='<qp:message code="sc.blogiccomponent.0181" />' name="btnChooseParameter" class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action pull-right qp-cursor" style="float: right; margin-right: 5px;" onclick="openParameterOfFeeback(this)" callback="onClickChooseParamMsg"></span>
											<input type="hidden" name="messageParameter" value=""/>
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"/>
									</th>
									<td colspan="3">
										<textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
        		<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        		<button type="button" class="btn qp-button-client " onclick="saveModalFeedbackSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
			</div>
		</div>
	</div>
</div>