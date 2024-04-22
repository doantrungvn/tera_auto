<div id="tabBusiness-inputbean" style="height: auto;" class="tab-pane">
	<div class="panel panel-default qp-div-select">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
		</div>
		<div class="panel-body">
			<table class="table table-bordered qp-table-list" id="tbl_inputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputFormCallback" max-row="20">
				<colgroup>
					<col width="40px" />
					<col width="50%" />
					<col width="30%" style="min-width: 150px" />
					<col width="180px" />
					<col width="40px" />
				</colgroup>
				<thead>
					<tr>
						<th><qp:message code="sc.businesslogicdesign.0036" /></th>
						<th><qp:message code="sc.businesslogicdesign.0037" /></th>
						<th><qp:message code="sc.businesslogicdesign.0038" /></th>
						<th><qp:message code="sc.businesslogicdesign.0039" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${businessDesignForm.lstInputBean }" var="inputObj" varStatus="status">
						<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }">
							<td colspan="2">
								<div style="height: 100%">
									<div class="vertical-midle-div">
										<span class="ar-groupIndex">${inputObj.tableIndex }</span>
									</div>
									<div class="pull-right" style="height: 100%">
										<span style="vertical-align: middle; display: inline-block; height: 100%;"></span>
										<div style="width: 98%; display: inline-block; vertical-align: middle;">
											<form:input path="lstInputBean[${status.index}].inputBeanName" class="form-control qp-input-text" maxlength="50" />
										</div>
									</div>
								</div>
							</td>
							<td><form:hidden path="lstInputBean[${status.index}].inputBeanId" /> <form:hidden path="lstInputBean[${status.index}].parentInputBeanId" /> <form:hidden path="lstInputBean[${status.index}].itemSequenceNo" /> <form:hidden path="lstInputBean[${status.index}].screenItemId" /> <form:hidden path="lstInputBean[${status.index}].inputBeanType" /> <form:hidden path="lstInputBean[${status.index}].objectFlg" value="true" /> <form:input path="lstInputBean[${status.index}].inputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
							<td>
								<form:hidden path="lstInputBean[${status.index}].arrayFlg"/>
								<form:select cssClass="form-control qp-input-select" path="lstInputBean[${status.index}].dataType" onchange="$.qp.businessdesign.objectTypeChange(this);">
									<form:option value="">
										<qp:message code="sc.sys.0030"></qp:message>
									</form:option>
									<form:options items="${CL_BD_DATATYPE_NOT_OBJECT_ENTITY}" />
								</form:select>
							</td>
							<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014"/>'></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="qp-div-action-table">
				<a title='<qp:message code="sc.businesslogicdesign.0200" />' type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl_inputbean_list_define',templateId:'tbl_inputbean_list_define-template',templateData:{groupId:''}});"></a>
			</div>
		</div>
	</div>
</div>