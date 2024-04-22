<!-- Dialog message parameter list setting -->
<div id="dialog-messagevalidateparameter-setting" class="modal fade" style="display: none;">
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
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0127"/></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col width="50%" />
									<col width="50%" />
								</colgroup>
								<thead>
									<tr>
										<th style="text-align: right;"><qp:message code="sc.businesslogicdesign.0130"/></th>
										<td style="text-align: left;">
											<span><qp:message code="err.sys.0025"/></span>
										</td>
									</tr>
								</thead>
							</table>
							<br/>
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col />
									<col width="70%" />
									<col width="30%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.businesslogicdesign.0036"/></th>
										<th><qp:message code="sc.businesslogicdesign.0128"/></th>
										<th><qp:message code="sc.businesslogicdesign.0131"/></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>
											<div class="input-group " style="">
												<input type="text" class="form-control qp-input-autocomplete ui-autocomplete-input" value="Order No">
												<span class="input-group-addon dropdown-toggle" data-dropdown="dropdown" style="cursor: pointer;"><span class="caret"></span></span>
											</div>
										</td>
										<td>sc.order.0001</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<br />
			</div>
			<div class="modal-footer">
				<br/>
				<div class="qp-div-action">
               		<button type="button" class="btn qp-button-client " onclick="saveDialogAutocompleteSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
               </div>
            </div>  
		</div>
	</div>
</div>