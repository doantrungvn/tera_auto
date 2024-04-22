<!-- Dialog transaction setting -->
<div id="dialog-transaction-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0096" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063"/></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label" maxlength="200"></td>
								</tr>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0097" /></th>
									<td colspan="3">
										<label><input name="transactionType" class="qp-input-radio-margin qp-input-radio" type="radio" value="0" checked="checked"><qp:message code="sc.blogiccomponent.0001"/></label></br>
										<label><input name="transactionType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1"><qp:message code="sc.blogiccomponent.0098"/></label></br>
										<label><input name="transactionType" class="qp-input-radio-margin qp-input-radio" type="radio" value="2"><qp:message code="sc.blogiccomponent.0099"/></label>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"/></th>
									<td colspan="3"><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalTransactionSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>