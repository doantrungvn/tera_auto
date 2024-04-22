<div id="tabBusiness-objectdefinition" style="height: auto;" class="tab-pane">
	<div class="panel panel-default qp-div-select">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
		</div>
		<div class="panel-body">
			<table class="table table-bordered qp-table-list" id="tbl_objectdefinition_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.obFormCallback">
				<colgroup>
					<col width="40px" />
					<col width="50%">
					<col width="30%" style="min-width: 150px"/>
					<col width="180px"/>
					<col width="40px"/>
				</colgroup>
				<thead>
					<tr>
						<th><qp:message code="sc.businesslogicdesign.0036" /></th>
						<th><qp:message code="sc.businesslogicdesign.0037" /></th>
						<th><qp:message code="sc.businesslogicdesign.0038" /></th>
						<th><qp:message code="sc.businesslogicdesign.0039" /></th>
						<th>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${businessDesignForm.lstObjectDefinition }" var="objectDefinitionObj" varStatus="status">
						<c:choose>
							<c:when test="${objectDefinitionObj.dataType == '14' && objectDefinitionObj.objectFlg}">
								<tr data-ar-rgroup="${objectDefinitionObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_objectdefinition_list_define-entity-template" data-ar-rindex="${objectDefinitionObj.itemSequenceNo }" data-ar-rgroupindex="${objectDefinitionObj.tableIndex }">
									<td colspan="2" >
										<div style="height: 100%">
											<div>
												<span class="ar-groupIndex">${objectDefinitionObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height: 100%; vertical-align: middle;">
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].parentObjectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].itemSequenceNo" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionName" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectFlg" value="true" />
												<qp:autocomplete name="lstObjectDefinition[${status.index}].tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="bdOnChangeTableDesignOfOb" value="${objectDefinitionObj.tblDesignId}" displayValue="${objectDefinitionObj.objectDefinitionName}"></qp:autocomplete>
												<form:hidden path="lstObjectDefinition[${status.index}].moduleId" />
											</div>
										</div>
									</td>
									<td>
										<form:input path="lstObjectDefinition[${status.index}].objectDefinitionCode" class="form-control qp-input-text" maxlength="50" />
									</td>
									<td>
										<div class="input-group">
											<form:hidden path="lstObjectDefinition[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstObjectDefinition[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstObjectDefinition[${status.index}].dataType" oldvalue="${objectDefinitionObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstObjectDefinition[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${objectDefinitionObj.dataType == '16' && objectDefinitionObj.objectFlg}">
								<tr data-ar-rgroup="${objectDefinitionObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_objectdefinition_list_define-entity-template" data-ar-rindex="${objectDefinitionObj.itemSequenceNo }" data-ar-rgroupindex="${objectDefinitionObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div>
												<span class="ar-groupIndex">${objectDefinitionObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height: 100%; vertical-align: middle;">
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].parentObjectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].itemSequenceNo" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionName" />
                                                <form:hidden path="lstObjectDefinition[${status.index}].objectType" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectFlg" value="true" />
												<qp:autocomplete name="lstObjectDefinition[${status.index}].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObjectForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeCommonObjectOfOb" value="${objectDefinitionObj.objectId}" displayValue="${objectDefinitionObj.objectDefinitionName}"></qp:autocomplete>
												<form:hidden path="lstObjectDefinition[${status.index}].moduleId" />
											</div>
										</div>
									</td>
									<td>
										<form:input path="lstObjectDefinition[${status.index}].objectDefinitionCode" class="form-control qp-input-text" maxlength="50" />
									</td>
									<td>
										<div class="input-group">
											<form:hidden path="lstObjectDefinition[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstObjectDefinition[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstObjectDefinition[${status.index}].dataType" oldvalue="${objectDefinitionObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstObjectDefinition[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${objectDefinitionObj.dataType == '17' && objectDefinitionObj.objectFlg}">
								<tr data-ar-rgroup="${objectDefinitionObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_objectdefinition_list_define-entity-template" data-ar-rindex="${objectDefinitionObj.itemSequenceNo }" data-ar-rgroupindex="${objectDefinitionObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div>
												<span class="ar-groupIndex">${objectDefinitionObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height: 100%; vertical-align: middle;">
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].parentObjectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].itemSequenceNo" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionName" />
                                                <form:hidden path="lstObjectDefinition[${status.index}].objectType" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectFlg" value="true" />
												<qp:autocomplete name="lstObjectDefinition[${status.index}].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeExternalObjectOfOb" value="${objectDefinitionObj.objectId}" displayValue="${objectDefinitionObj.objectDefinitionName}"></qp:autocomplete>
												<form:hidden path="lstObjectDefinition[${status.index}].moduleId" />
											</div>
										</div>
									</td>
									<td>
										<form:input path="lstObjectDefinition[${status.index}].objectDefinitionCode" class="form-control qp-input-text" maxlength="50" />
									</td>
									<td>
										<div class="input-group">
											<form:hidden path="lstObjectDefinition[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstObjectDefinition[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstObjectDefinition[${status.index}].dataType" oldvalue="${objectDefinitionObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstObjectDefinition[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${!objectDefinitionObj.objectFlg}">
								<tr data-ar-rgroup="${objectDefinitionObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_objectdefinition_list_define-entity-columm-template" data-ar-rindex="${objectDefinitionObj.itemSequenceNo }" data-ar-rgroupindex="${objectDefinitionObj.tableIndex }">
									<td colspan="2" >
										<div style="height: 100%">
											<div>
												<span class="ar-groupIndex">${objectDefinitionObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height: 100%; vertical-align: middle;">
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].parentObjectDefinitionId" />
												<form:hidden path="lstObjectDefinition[${status.index}].itemSequenceNo" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionName" />
												<form:hidden path="lstObjectDefinition[${status.index}].tblDesignId" />
												<form:hidden path="lstObjectDefinition[${status.index}].columnId" />
												<form:hidden path="lstObjectDefinition[${status.index}].objectFlg"/>
												<form:hidden path="lstObjectDefinition[${status.index}].objectId"/>
												<form:hidden path="lstObjectDefinition[${status.index}].objectType"/>
												<label name="lstObjectDefinition[${status.index}].objectDefinitionName" class="qp-output-text">${objectDefinitionObj.objectDefinitionName}</label>
												<form:hidden path="lstObjectDefinition[${status.index}].moduleId" />
											</div>
										</div>
									</td>
									<td><form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionCode" /> <label name="lstObjectDefinition[${status.index}].objectDefinitionCode" class="qp-output-text">${objectDefinitionObj.objectDefinitionCode}</label></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstObjectDefinition[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstObjectDefinition[${status.index}].dataType" addChildFlg="false" />
											<label name="lstObjectDefinition[${status.index}].dataType" class="qp-output-text">${CL_BD_DATATYPE.get(objectDefinitionObj.dataType.toString())}</label>
											<c:if test="${objectDefinitionObj.arrayFlg}">
																	[]
																</c:if>
											<span class="input-group-addon" style="display: none"> <label><form:checkbox path="lstObjectDefinition[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr data-ar-rgroup="${objectDefinitionObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_objectdefinition_list_define-template" data-ar-rindex="${objectDefinitionObj.itemSequenceNo }" data-ar-rgroupindex="${objectDefinitionObj.tableIndex }">
									<td colspan="2" >
										<div style="height: 100%">
											<div>
												<span class="ar-groupIndex">${objectDefinitionObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height: 100%; vertical-align: middle;">
												<form:input path="lstObjectDefinition[${status.index}].objectDefinitionName" class="form-control qp-input-text" maxlength="200" />
											</div>
										</div>
									</td>
									<td><form:hidden path="lstObjectDefinition[${status.index}].objectDefinitionId" /> <form:hidden path="lstObjectDefinition[${status.index}].parentObjectDefinitionId" /> <form:hidden path="lstObjectDefinition[${status.index}].itemSequenceNo" /> <form:input path="lstObjectDefinition[${status.index}].objectDefinitionCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstObjectDefinition[${status.index}].groupBaseTypeId" />
											<form:select cssClass="form-control qp-input-select" path="lstObjectDefinition[${status.index}].dataType" onchange="$.qp.businessdesign.objectTypeChange(this);">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstObjectDefinition[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
			<div class="qp-div-action-table">
				<a title='<qp:message code="sc.businesslogicdesign.0200" />' type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl_objectdefinition_list_define',templateId:'tbl_objectdefinition_list_define-template',templateData:{groupId:''}});"></a>
			</div>
		</div>
	</div>
</div>