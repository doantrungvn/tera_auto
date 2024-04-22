
<div class="modal-dialog modal-lg" style="min-height: 500px;width: 800px;">
	<div class="modal-content">
		<div class="modal-header qp-model-header" style="border-bottom: 0px;">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.screendesign.0221"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list mapping-bean">
						<colgroup>
							<col width="55px"/>
							<col width="35%" />
							<col width="20%" />
							<col width="15%" />
							<col width="25%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="4"><qp:message code="sc.screendesign.0222"/></th>
								<th style="color: blue;"><qp:message code="sc.screendesign.0223"/></th>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0004"/></th>
								<th><qp:message code="sc.decisiontable.0015"/></th>
								<th><qp:message code="sc.decisiontable.0016"/></th>
								<th><qp:message code="sc.decisiontable.0017"/></th>
								<th><qp:message code="sc.screendesign.0224"/> <label name="areaName"></label></th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<br />
			<div class="qp-div-action">
				<button type="button" class="btn btn-md btn-success qp-link-button" onclick="saveOutputParameterSetting()"><qp:message code="sc.sys.0054"/></button>
			</div>
		</div>
	</div>
</div>