<!-- Dialog for advance setting -->
<div class="modal fade" id="classification" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" id="modalDialog" style="width: 40%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.domaindesign.0045" /></span>
					</div>
					<input type="hidden" id="classificationTemp"/>
					<div class="panel-body" id="panelBody">
						<div id="dialog-boolean-options-error" align="center"></div>
						<table class="table table-bordered qp-table-form" id="tableclassification" >
							<colgroup>
								<col width="30%" />
								<col width="70%" />
							</colgroup>
							<tbody>
								<tr>
									<th><form:label path="majorClassification"><qp:message code="sc.domaindesign.0042" /></form:label></th>
									<td><form:input path="majorClassification" cssClass="form-control qp-input-text"/></td>
								</tr>
								<tr>
									<th><form:label path="subClassification"><qp:message code="sc.domaindesign.0043" /></form:label></th>
									<td><form:input path="subClassification" cssClass="form-control qp-input-text"/></td>
								</tr>
								<tr>
									<th><form:label path="minorClassification"><qp:message code="sc.domaindesign.0044" /></form:label></th>
									<td><form:input path="minorClassification" cssClass="form-control qp-input-text"/></td>
								</tr>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveclassification();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>