<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body">

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text"><qp:message
							code="sc.screendesign.0111"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="40%">
							<col width="60%">
						</colgroup>
						<tbody>
							<tr>
								<th><qp:message code="sc.screendesign.0113"></qp:message></th>
								<td><select class="form-control"
									name="modal-setting-form-method">
										<option value="2"><qp:message
												code="sc.screendesign.0160"></qp:message></option>
										<option value="1"><qp:message
												code="sc.screendesign.0161"></qp:message></option>
								</select></td>
							</tr>
							<tr>
								<th><qp:message code="sc.screendesign.0114"></qp:message></th>
								<td><qp:autocomplete optionLabel="optionLabel"
										selectSqlId="autocompleteGetScreenList"
										arg01="${screenDesignForm.projectId}" arg02="20"
										emptyLabel="sc.sys.0030" optionValue="optionValue"
										name="modal-setting-form-action" mustMatch="true"></qp:autocomplete>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.screendesign.0115"></qp:message></th>
								<td><select name="modal-setting-form-enctype"
									class="form-control">
										<option value="3"><qp:message
												code="sc.screendesign.0162"></qp:message></option>
										<option value="2"><qp:message
												code="sc.screendesign.0163"></qp:message></option>
										<option value="1"><qp:message
												code="sc.screendesign.0164"></qp:message></option>
								</select></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<input type="button" onclick="saveModalFormSetting()"
				class="btn qp-button-client" value='<qp:message code="sc.sys.0054"></qp:message>' />
		</div>
	</div>
	<!-- /.modal-content -->
</div>