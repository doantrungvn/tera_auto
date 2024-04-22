<!-- dialog option setting  -->
	<div class="modal fade" id="settingRemarkColumn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="modalDialog">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.tabledesign.0076" /></span>
					</div>
					<input type="hidden" id="classificationTemp"/>
					<div class="panel-body" id="panelBody">
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableRemark" >
							<colgroup>
								<col width="25%" />
								<col width="75%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.tabledesign.0013" /></th>
									<td>
										<textarea id="remarkColumn" name="remarkColumn" class="form-control qp-input-textarea" rows="3"></textarea>
									</td>
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveRemarkSetting(this);"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>