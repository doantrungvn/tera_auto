<script type="text/javascript">
	function addEntity(){
		$("#dialog-prototype-setting").modal('show');
	}
</script>

<!-- Dialog For each setting -->
<div id="dialog-prototype-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog" id="foreach">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0141"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0142"/></th>
									<td>
										<div class="input-group " style="">
											<input type="text" class="form-control qp-input-autocomplete ui-autocomplete-input" value="Search ..."> <span class="input-group-addon dropdown-toggle" data-dropdown="dropdown" style="cursor: pointer;"><span class="caret"></span></span>
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0143"/></th>
									<td><input type="radio" value="0" id="single" checked="checked" name="radioEntityType"><label style="font-weight: normal;" for="single"><qp:message code="sc.businesslogicdesign.0144"/></label>&nbsp;&nbsp; <input type="radio" value="1" id="list" name="radioEntityType"><label style="font-weight: normal;" for="list"><qp:message code="sc.businesslogicdesign.0145"/></label></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<br />
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client" data-dismiss="modal"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>