<!-- Dialog function setting -->
<div id="dialog-function-display" class="modal fade"
	style="display: none;">
	<script id="tbl-function-list-template" type="text/x-jquery-tmpl">
		<tr>
			<td>\${idx}</td>
			<!-- <td rowspan="1" style="text-align: left" class="com-output-fixlength">\${functionMasterName}</td> -->
			<td style="text-align: left">\${functionMethodName}</td>
			<td>
				<input id="submitColumn\${idx}" method="\${functionMethod}" name="submitColumn" type="radio" value="\${functionMethodId}" />
			</td>
		</tr>
	</script>
	<script id="collapse-function-master-template" type="text/x-jquery-tmpl">
		<div class="panel panel-default qp-collapse-function">
			<div onclick=$.qp.functions.expandCollapse("\${functionMasterId}","\${functionMasterCode}") style="cursor: pointer;" class="qp-collapse-function-header">
				<span data-toggle="collapse">\${functionMasterName}</span>
			</div>
			<div id="collapse-\${functionMasterId}" class="panel-collapse collapse">
				<table class="table table-bordered qp-table-list">
					<colgroup>
						<!-- <col /> -->
						<col>
						<col width="90%" />
						<col width="60px" />
					</colgroup>
					<thead>
						<tr>
							<th><qp:message code="sc.sys.0004" /></th>
							<th><qp:message code="sc.businesslogicdesign.0165" /></th>
							<th style="text-align: center"><qp:message
									code="sc.businesslogicdesign.0119" /></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</script>
	<script id="tbl-function-list-empty-template" type="text/x-jquery-tmpl">
		<tr>
			<td colspan="4s"><qp:message code="inf.sys.0013"/></td>
		</tr>
	</script>

	<script id="tbl-formula-content-function-template" type="text/template">
		<div class= 'form-inline qp-formula-component' contenteditable='false' type="17">
    		<div class='qp-formula-component-content'  onclick ="$.qp.formulabuilder.onOpenFunctionFormulaSetting(this)">
    		</div>
    		<input type="hidden" name="formulaitem" value="" />
    		<div class='qp-formula-component-remove' onclick='$.qp.formulabuilder.onRemoveComponent(this)'>-</div>
		</div>
	</script>
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message
								code="sc.businesslogicdesign.0165" /></span>
					</div>
					<div class="panel-body">
						<!-- bangnl collapse-->
						<div class="panel-group" id="collapse-function-master">
							
						</div>
						<!-- end  collapse-->
						<%-- <table class="table table-bordered qp-table-list"
							id="tbl-function-list">
							<colgroup>
								<!-- <col /> -->
								<col>
								<col width="40%" />
								<col width="50%" />
								<col width="60px" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.businesslogicdesign.0166" /></th>
									<th><qp:message code="sc.businesslogicdesign.0165" /></th>
									<th style="text-align: center"><qp:message
											code="sc.businesslogicdesign.0119" /></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table> --%>
						<div class="qp-add-left" style="display: none;">
							<a
								class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"
								onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;"
								href="javascript:void(0)"></a>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client "
						onclick="$.qp.functions.saveSelectMethod(this);">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>