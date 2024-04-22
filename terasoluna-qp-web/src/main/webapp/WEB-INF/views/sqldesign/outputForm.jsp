<table style="width:30%" class="table table-borderless">
	<colgroup>
		<col width="30%" />
		<col  />
	</colgroup>
	<tbody>
		<tr>
			<td><qp:message code='sc.sqldesign.0022'></qp:message></td>
			<td>
				<form:select path="sqlDesignForm.returnType" cssClass="form-control qp-input-select pull-left">
					<form:options items="${CL_SQL_RETURNTYPE }" />
				</form:select>
			</td>
		</tr>
	</tbody>
</table>
<table class="table table-bordered qp-table-list" id="outputForm-sql-builder" data-ar-actionTemplateId="outputForm-action-l0-template" data-ar-rowTemplateId="outputForm-l0-template" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.sqlbuilder.outputFormCallback">
	<colgroup>
		<col />
		<col width="40px"/>
		<col width="40px"/>
		<col />
		<col width="20%"/>
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
			<th><qp:message code="sc.sqldesign.0029"></qp:message></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designForm.outputForm }" var="item" varStatus="status">
			<tr data-ar-rgroup="${item.groupId }" data-ar-rgroupindex="${f:h(item.groupIndex) }" class="ar-dataRow">
				<c:if test="${designForm.sqlDesignForm.sqlPattern eq '0'}" >
					<c:choose>
						<c:when test="${designForm.outputForm[status.index].dataType eq '14' and designForm.outputForm[status.index].designType eq '0'}">
							<td colspan="4" class="qp-none-padding">
								<div style="height:100%">
									<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
									<div class="pull-right" style="height:100%;vertical-align: middle;">	
										<qp:autocomplete name = "outputForm[${status.index}].tableId" 
												optionLabel="optionLabel" 
												selectSqlId="getAutocompleteGetTableForBD" 
												optionValue="optionValue" 
												arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
												arg02="20" 
												mustMatch="true"
												onChangeEvent="$.qp.common.entityAutocompleteChange"
												value="${designForm.outputForm[status.index].tableId}"
												displayValue="${designForm.outputForm[status.index].sqlDesignOutputName}" ></qp:autocomplete>
										<form:hidden path="outputForm[${status.index}].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row" />
									</div>
								</div>
							</td>
							<td>
								<form:hidden path="outputForm[${status.index}].groupId" class="ar-groupId"/>
								<form:hidden path="outputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
								<form:hidden path="outputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputId"/>
								<form:hidden path="outputForm[${status.index}].columnId"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputParentId"/>
								<form:hidden path="outputForm[${status.index}].designType"/>
								<form:hidden path="outputForm[${status.index}].objectType"/>
								<form:input path="outputForm[${status.index}].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"  />
							</td>
							<td>
								<div class="input-group" style="width:100%">
									<form:select data-previousValue="${designForm.outputForm[status.index].dataType}" path="outputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l1-template');">
										<form:options items="${CL_SQL_DATATYPE }" />
									</form:select>
									<c:if test="false">
										<span class="input-group-addon">
											<form:checkbox path="outputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="<qp:message code='sc.sqldesign.0008'></qp:message>"/>
										</span>
									</c:if>
								</div>
							</td>
							<td>
							</td>
							<td>
								<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
							</td>
						</c:when>
						<c:when test="${designForm.outputForm[status.index].dataType eq '16'  and designForm.outputForm[status.index].designType eq '0'}">
							<td colspan="4" class="qp-none-padding">
								<div style="height:100%">
									<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
									<div class="pull-right" style="height:100%;vertical-align: middle;">	
										<qp:autocomplete name = "outputForm[${status.index}].tableId" 
												optionLabel="optionLabel" 
												selectSqlId="getAutocompleteCommonObjectForSQLDesign" 
												optionValue="optionValue" 
												arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
												arg02="20" 
												mustMatch="true"
												onChangeEvent="$.qp.common.commonObjectAutocompleteChange"
												value="${designForm.outputForm[status.index].tableId}"
												displayValue="${designForm.outputForm[status.index].sqlDesignOutputName}" ></qp:autocomplete>
										<form:hidden path="outputForm[${status.index}].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row" />
										<form:hidden path="outputForm[${status.index}].moduleId"/>
									</div>
								</div>
							</td>
							<td>
								<form:hidden path="outputForm[${status.index}].groupId" class="ar-groupId"/>
								<form:hidden path="outputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
								<form:hidden path="outputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputId"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputParentId"/>
								<form:hidden path="outputForm[${status.index}].columnId"/>
								<form:hidden path="outputForm[${status.index}].designType"/>
								<form:hidden path="outputForm[${status.index}].objectType"/>
								<form:input path="outputForm[${status.index}].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"  />
							</td>
							<td>
								<div class="input-group" style="width:100%">
									<form:select data-previousValue="${designForm.outputForm[status.index].dataType}" path="outputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l1-template');">
										<form:options items="${CL_SQL_DATATYPE }" />
									</form:select>
									<c:if test="false">
										<span class="input-group-addon">
											<form:checkbox path="outputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="<qp:message code='sc.sqldesign.0008'></qp:message>"/>
										</span>
									</c:if>
								</div>
							</td>
							<td>
							</td>
							<td>
								<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
							</td>
						</c:when>
						<c:when test="${designForm.outputForm[status.index].dataType eq '17'  and designForm.outputForm[status.index].designType eq '0'}">
							<td colspan="4" class="qp-none-padding">
								<div style="height:100%">
									<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
									<div class="pull-right" style="height:100%;vertical-align: middle;">	
										<qp:autocomplete name = "outputForm[${status.index}].tableId" 
												optionLabel="optionLabel" 
												selectSqlId="getAutocompleteExternalObjectForSQLDesign" 
												optionValue="optionValue" 
												arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
												arg02="20" 
												mustMatch="true"
												onChangeEvent="$.qp.common.externalObjectAutocompleteChange"
												value="${designForm.outputForm[status.index].tableId}"
												displayValue="${designForm.outputForm[status.index].sqlDesignOutputName}" ></qp:autocomplete>
										<form:hidden path="outputForm[${status.index}].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row" />
										<form:hidden path="outputForm[${status.index}].moduleId"/>
									</div>
								</div>
							</td>
							<td>
								<form:hidden path="outputForm[${status.index}].groupId" class="ar-groupId"/>
								<form:hidden path="outputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
								<form:hidden path="outputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputId"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputParentId"/>
								<form:hidden path="outputForm[${status.index}].designType"/>
								<form:hidden path="outputForm[${status.index}].columnId"/>
								<form:hidden path="outputForm[${status.index}].objectType"/>
								<form:input path="outputForm[${status.index}].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"  />
							</td>
							<td>
								<div class="input-group" style="width:100%">
									<form:select data-previousValue="${designForm.outputForm[status.index].dataType}" path="outputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l1-template');">
										<form:options items="${CL_SQL_DATATYPE }" />
									</form:select>
									<c:if test="false">
										<span class="input-group-addon">
											<form:checkbox path="outputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="<qp:message code='sc.sqldesign.0008'></qp:message>"/>
										</span>
									</c:if>
								</div>
							</td>
							<td>
							</td>
							<td>
								<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
							</td>
						</c:when>
						<c:when test="${designForm.outputForm[status.index].designType eq '1'}">
							<td colspan="4" class="qp-none-padding">
								<div style="height:100%">
									<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
									<div class="pull-right" style="height:100%;vertical-align: middle;text-align:left">	
										<span>${designForm.outputForm[status.index].sqlDesignOutputName }</span>
										<form:hidden path="outputForm[${status.index}].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row" />
									</div>
								</div>
							</td>
							<td>
								<form:hidden path="outputForm[${status.index}].groupId" class="ar-groupId"/>
								<form:hidden path="outputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
								<form:hidden path="outputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputId"/>
								<form:hidden path="outputForm[${status.index}].tableId"/>
								<form:hidden path="outputForm[${status.index}].columnId"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputParentId"/>
								<form:hidden path="outputForm[${status.index}].designType"/>
								<form:hidden path="outputForm[${status.index}].objectType"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"  />
								<span>${designForm.outputForm[status.index].sqlDesignOutputCode }</span>
							</td>
							<td>
								<div class="input-group" style="width:100%">
									 <form:hidden path="outputForm[${status.index}].dataType"/>
									<span>${CL_SQL_DATATYPE.get(designForm.outputForm[status.index].dataType) }</span>
									<c:if test="${designForm.outputForm[status.index].isArray }">
										[]
									</c:if> 
									<%-- <form:select path="outputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l1-template');">
										<form:options items="${CL_SQL_DATATYPE }" />
									</form:select> --%>
									<c:if test="false">
										<span class="input-group-addon">
											<form:checkbox path="outputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
										</span>
									</c:if>
								</div>
							</td>
							<td>
								<qp:autocomplete optionValue="optionValue" 
												name="outputForm[${status.index }].mappingColumn" 
												value="${designForm.outputForm[status.index].mappingColumn}"
												displayValue="${designForm.outputForm[status.index].mappingColumnAutocomplete}"
												mustMatch="true"
												sourceType="local"
												sourceCallback="$.qp.sqlbuilder.outputFormSourceCallback"
												onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange"
												optionLabel="optionLabel"></qp:autocomplete>
							</td>
							<td>
							</td>
						</c:when>
						<c:otherwise>
							<td colspan="4" class="qp-none-padding">
								<div style="height:100%">
									<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
									<div class="pull-right" style="height:100%;vertical-align: middle;">	
										<form:input class="form-control qp-input-text qp-convention-name-row" path="outputForm[${status.index}].sqlDesignOutputName" />
									</div>
								</div>
							</td>
							<td>
								<form:hidden path="outputForm[${status.index}].groupId" class="ar-groupId"/>
								<form:hidden path="outputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
								<form:hidden path="outputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputId"/>
								<form:hidden path="outputForm[${status.index}].columnId"/>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputParentId"/>
								<form:hidden path="outputForm[${status.index}].designType"/>
								<form:hidden path="outputForm[${status.index}].objectType"/>
								<form:input class="form-control qp-input-text qp-convention-code-row" path="outputForm[${status.index}].sqlDesignOutputCode" />
							</td>
							<td>
								<div class="input-group" style="width:100%">
									<form:select path="outputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l1-template');">
										<form:options items="${CL_SQL_DATATYPE }" />
									</form:select>
									<c:if test="${designForm.outputForm[status.index].dataType eq '1' && not empty designForm.outputForm[status.index].isArray  && designForm.outputForm[status.index].isArray eq 'true'}">
										<span class="input-group-addon">
											<form:checkbox path="outputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
										</span>
									</c:if>
								</div>
							</td>
							<td>
								<qp:autocomplete optionValue="optionValue" 
												name="outputForm[${status.index }].mappingColumn" 
												value="${designForm.outputForm[status.index].mappingColumn}"
												displayValue="${designForm.outputForm[status.index].mappingColumnAutocomplete}"
												mustMatch="true"
												sourceType="local"
												onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange"
												sourceCallback="$.qp.sqlbuilder.outputFormSourceCallback"
												optionLabel="optionLabel"></qp:autocomplete>
							</td>
							<td>
								<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
							</td>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${designForm.sqlDesignForm.sqlPattern ne '0'}">
					<td colspan="4" class="qp-none-padding">
						<div style="height:100%">
							<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
							<div class="pull-right vertical-midle-div" style="height:100%;vertical-align: middle; justify-content: flex-start">
								<span>${f:h(item.sqlDesignOutputName)}</span>
								<form:hidden path="outputForm[${status.index}].sqlDesignOutputName" />
							</div>
						</div>
					</td>
					<td>
						<form:hidden path="outputForm[${status.index}].groupId" class="ar-groupId"/>
						<form:hidden path="outputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
						<form:hidden path="outputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
						<form:hidden path="outputForm[${status.index}].sqlDesignOutputId"/>
						<form:hidden path="outputForm[${status.index}].columnId"/>
						<form:hidden path="outputForm[${status.index}].sqlDesignOutputParentId"/>
						<form:hidden path="outputForm[${status.index}].designType"/>
						<form:hidden path="outputForm[${status.index}].objectType"/>
						<form:hidden path="outputForm[${status.index}].sqlDesignOutputCode" />
							<span>${f:h(item.sqlDesignOutputCode)}</span>
					</td>
					<td>
						<div class="input-group" style="width:100%">
							<form:hidden path="outputForm[${status.index }].dataType"/>
							<span>${CL_SQL_DATATYPE.get('3')}</span>
							<c:if test="false">
								<span class="input-group-addon">
									<form:checkbox path="outputForm[${status.index}].isArray" cssClass="qp-input-checkbox" label="Array"/>
								</span>
							</c:if>
						</div>
					</td>
					<td>
						
					</td>
					<td>
						
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="qp-div-action-table">
<c:choose>
	<c:when test="${designForm.sqlDesignForm.sqlPattern eq '0'}">
		<a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'outputForm-sql-builder',templateId:'outputForm-l0-template'});"></a>
	</c:when>
	<c:otherwise>
		<a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" style="display: none" onclick="$.qp.ar.addRow({link:this,tableId:'outputForm-sql-builder',templateId:'outputForm-l0-template'});"></a>
	</c:otherwise>
</c:choose>
</div>