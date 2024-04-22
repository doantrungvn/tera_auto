<!-- Dialog connection setting -->
<div id="dialog-connection-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog" id="connection">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0083"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0084"/></th>
									<td class="form-inline">
										<input type="text" style="width: 100%" class="form-control qp-input-text" name="connectionLabel"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<br/>
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client" data-dismiss="modal" onclick="saveConnectionSetting(this)"><qp:message code="sc.sys.0031"/></button>
				</div>
			</div>
		</div>
	</div>
</div>