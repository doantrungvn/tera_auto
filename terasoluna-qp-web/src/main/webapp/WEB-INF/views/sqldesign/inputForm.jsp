<table class="table table-bordered qp-table-list" data-ar-actionTemplateId="inputForm-action-l1-template" data-ar-rowTemplateId="inputForm-l1-template" id="inputForm" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.sqlbuilder.inputFormCallback">
	<colgroup>
		<col width="42px"/>
		<col width="40px"/>
		<col width="40px"/>
		<col />
		<col width="20%"/>
		<col width="20%"/>
		<col />
	</colgroup>
	<thead>
		<tr>
			<th><qp:message code="sc.sys.0004"></qp:message></th>
			<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designForm.inputForm }" var="item" varStatus="status">
			<tr data-ar-rgroup="${item.groupId }" data-ar-rgroupindex="${f:h(item.groupIndex) }" class="ar-dataRow">
				<c:choose>
					<c:when test="${designForm.inputForm[status.index].dataType eq '14' and designForm.inputForm[status.index].designType eq '0'}">
						<td colspan="4" class="qp-none-padding">
							<div style="height:100%">
								<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
								<div class="pull-right" style="height:100%;vertical-align: middle;">	
									<qp:autocomplete name = "inputForm[${status.index}].tableId" 
											optionLabel="optionLabel" 
											selectSqlId="getAutocompleteGetTableForBD" 
											optionValue="optionValue" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
											arg02="20" 
											mustMatch="true"
											onChangeEvent="$.qp.common.entityAutocompleteChange"
											value="${designForm.inputForm[status.index].tableId}"
											displayValue="${designForm.inputForm[status.index].sqlDesignInputName}" ></qp:autocomplete>
									<form:hidden path="inputForm[${status.index}].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row" />
								</div>
							</div>
						</td>
						<td>
							<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
							<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
							<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputParentId"/>
							<form:hidden path="inputForm[${status.index}].designType"/>
							<form:hidden path="inputForm[${status.index}].objectType"/>
							<form:input path="inputForm[${status.index}].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  />
						</td>
						<td>
							<div class="input-group" style="width:100%">
								<form:select path="inputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
									<form:options items="${CL_SQL_DATATYPE }" />
								</form:select>
								<span class="input-group-addon">
									<form:checkbox path="inputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
								</span>
							</div>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
						</td>
					</c:when>
					<c:when test="${designForm.inputForm[status.index].dataType eq '16' and designForm.inputForm[status.index].designType eq '0'}">
						<td colspan="4" class="qp-none-padding">
							<div style="height:100%">
								<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
								<div class="pull-right" style="height:100%;vertical-align: middle;">	
									<qp:autocomplete name = "inputForm[${status.index}].tableId" 
											optionLabel="optionLabel" 
											selectSqlId="getAutocompleteCommonObjectForSQLDesign" 
											optionValue="optionValue" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
											arg02="20" 
											mustMatch="true"
											onChangeEvent="$.qp.common.commonObjectAutocompleteChange"
											value="${designForm.inputForm[status.index].tableId}"
											displayValue="${designForm.inputForm[status.index].sqlDesignInputName}" ></qp:autocomplete>
									<form:hidden path="inputForm[${status.index}].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row" />
									<form:hidden path="inputForm[${status.index}].moduleId"/>
								</div>
							</div>
						</td>
						<td>
							<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
							<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
							<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputParentId"/>
							<form:hidden path="inputForm[${status.index}].designType"/>
							<form:hidden path="inputForm[${status.index}].objectType"/>
							<form:input path="inputForm[${status.index}].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  />
						</td>
						<td>
							<div class="input-group" style="width:100%">
								<form:select path="inputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
									<form:options items="${CL_SQL_DATATYPE }" />
								</form:select>
								<span class="input-group-addon">
									<form:checkbox path="inputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
								</span>
							</div>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
						</td>
					</c:when>
					<c:when test="${designForm.inputForm[status.index].dataType eq '17' and designForm.inputForm[status.index].designType eq '0'}">
						<td colspan="4" class="qp-none-padding">
							<div style="height:100%">
								<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
								<div class="pull-right" style="height:100%;vertical-align: middle;">	
									<qp:autocomplete name = "inputForm[${status.index}].tableId" 
											optionLabel="optionLabel" 
											selectSqlId="getAutocompleteExternalObjectForSQLDesign" 
											optionValue="optionValue" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
											arg02="20" 
											mustMatch="true"
											onChangeEvent="$.qp.common.externalObjectAutocompleteChange"
											value="${designForm.inputForm[status.index].tableId}"
											displayValue="${designForm.inputForm[status.index].sqlDesignInputName}" ></qp:autocomplete>
									<form:hidden path="inputForm[${status.index}].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row" />
									<form:hidden path="inputForm[${status.index}].moduleId"/>
								</div>
							</div>
						</td>
						<td>
							<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
							<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
							<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputParentId"/>
							<form:hidden path="inputForm[${status.index}].designType"/>
							<form:hidden path="inputForm[${status.index}].objectType"/>
							<form:input path="inputForm[${status.index}].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  />
						</td>
						<td>
							<div class="input-group" style="width:100%">
								<form:select path="inputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
									<form:options items="${CL_SQL_DATATYPE }" />
								</form:select>
								<span class="input-group-addon">
									<form:checkbox path="inputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
								</span>
							</div>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
						</td>
					</c:when>
					<c:when test="${designForm.inputForm[status.index].designType eq '1'}">
						<td colspan="4" class="qp-none-padding">
							<div style="height:100%">
								<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
								<div class="pull-right" style="height:100%;vertical-align: middle;text-align:left">	
									<span>${designForm.inputForm[status.index].sqlDesignInputName }</span>
									<form:hidden path="inputForm[${status.index}].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row" />
								</div>
							</div>
						</td>
						<td>
							<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
							<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
							<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputParentId"/>
							<form:hidden path="inputForm[${status.index}].tableId"/>
							<form:hidden path="inputForm[${status.index}].columnId"/>
							<form:hidden path="inputForm[${status.index}].designType"/>
							<form:hidden path="inputForm[${status.index}].objectType"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  />
							<span>${designForm.inputForm[status.index].sqlDesignInputCode }</span>
						</td>
						<td>
							<div class="input-group" style="width:100%">
								<form:hidden path="inputForm[${status.index}].dataType"/>
								<span>${CL_SQL_DATATYPE.get(designForm.inputForm[status.index].dataType) }</span>
								<c:if test="${designForm.inputForm[status.index].isArray }">
									[]
								</c:if>
								<form:hidden path="inputForm[${status.index}].isArray" />
							</div>
						</td>
						<td>
						</td>
					</c:when>
					<c:otherwise>
						<td colspan="4" class="qp-none-padding">
							<div style="height:100%">
								<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
								<div class="pull-right" style="height:100%;vertical-align: middle;">	
									<form:input class="form-control qp-input-text qp-convention-name-row" path="inputForm[${status.index}].sqlDesignInputName" />
								</div>
							</div>
						</td>
						<td>
							<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
							<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
							<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
							<form:hidden path="inputForm[${status.index}].sqlDesignInputParentId"/>
							<form:hidden path="inputForm[${status.index}].designType"/>
							<form:hidden path="inputForm[${status.index}].objectType"/>
							<form:input class="form-control qp-input-text qp-convention-code-row" path="inputForm[${status.index}].sqlDesignInputCode" />
						</td>
						<td>
							<div class="input-group" style="width:100%">
								<form:select path="inputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
									<form:options items="${CL_SQL_DATATYPE }" />
								</form:select>
								<span class="input-group-addon">
									<form:checkbox path="inputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
								</span>
							</div>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
						</td>
					</c:otherwise>
				</c:choose>
				
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="qp-div-action-table">
	<a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'inputForm',templateId:'inputForm-l1-template',templateData:{groupId:''}});"></a>
</div>