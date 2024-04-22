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
							code="sc.screendesign.0111"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="40%">
							<col width="60%">
						</colgroup>
						<tbody>
							<tr>
								<th><qp:message code="sc.screendesign.0237"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><input type="text" class="form-control"
									name="modal-setting-form-action" /></td>
							</tr>
							<tr style="display: none;">
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0005"/></th>
								<td set-blogic><qp:message code="sc.screendesign.0238"/>
								</td>
								<th></th>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<input type="button" onclick="saveModalFormSetting()"
				class="btn qp-button-client"
				value='<qp:message code="sc.sys.0054"></qp:message>' />
		</div>
	</div>
	<!-- /.modal-content -->
</div>