<div id="tabBusiness-inputbean" style="height: auto;" class="tab-pane">
	<div class="panel panel-default qp-div-select">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
		</div>
		<div class="panel-body">
			<table class="table table-bordered qp-table-list" id="tbl_inputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputFormCallback">
				<colgroup>
					<col width="40px" />
					<col width="50%"/>
					<col width="40%" style="min-width: 150px"/>
					<col width="180px"/>
					<col width="40px" class="bd-in-getscope"/>
					<col />
				</colgroup>
				<thead>
					<tr>
						<th><qp:message code="sc.businesslogicdesign.0036" /></th>
						<th><qp:message code="sc.businesslogicdesign.0037" /></th>
						<th><qp:message code="sc.businesslogicdesign.0038" /></th>
						<th><qp:message code="sc.businesslogicdesign.0039" /></th>
						<th class="bd-in-getscope"></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${businessDesignForm.lstInputBean }" var="inputObj" varStatus="status">
						<c:choose>
							<c:when test="${inputObj.dataType == '14' && inputObj.objectFlg}">
								<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-entity-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${inputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstInputBean[${status.index}].inputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].parentInputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanName" />
													<form:hidden path="lstInputBean[${status.index}].objectFlg" value="true" />
													<qp:autocomplete name="lstInputBean[${status.index}].tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="bdOnChangeTableDesignOfIn" value="${inputObj.tblDesignId}" displayValue="${inputObj.inputBeanName}"></qp:autocomplete>
													<form:hidden path="lstInputBean[${status.index}].moduleId"/>
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstInputBean[${status.index}].inputBeanCode" class="form-control qp-input-text" maxlength="50" /> <%-- 															<form:hidden path="lstInputBean[${status.index}].inputBeanCode"/> --%> <%-- 															<label name="lstInputBean[${status.index}].inputBeanCode" class="qp-output-text">${inputObj.inputBeanCode}</label> --%></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstInputBean[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstInputBean[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstInputBean[${status.index}].dataType" oldvalue="${inputObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstInputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-in-getscope" align="center">
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${inputObj.dataType == '16' && inputObj.objectFlg}">
								<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-common-object-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${inputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstInputBean[${status.index}].inputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].parentInputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstInputBean[${status.index}].screenItemId" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanType" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanName" />
                                                    <form:hidden path="lstInputBean[${status.index}].objectType" />
													<form:hidden path="lstInputBean[${status.index}].objectFlg" value="true" />
													<qp:autocomplete name="lstInputBean[${status.index}].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObjectForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeCommonObjectOfIn" value="${inputObj.objectId}" displayValue="${inputObj.inputBeanName}"></qp:autocomplete>
													<form:hidden path="lstInputBean[${status.index}].moduleId"/>
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstInputBean[${status.index}].inputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstInputBean[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstInputBean[${status.index}].dataType" onchange="$.qp.businessdesign.objectTypeChange(this);">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstInputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-in-getscope" align="center">
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${inputObj.dataType == '17' && inputObj.objectFlg}">
								<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-external-object-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${inputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstInputBean[${status.index}].inputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].parentInputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstInputBean[${status.index}].screenItemId" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanType" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanName" />
                                                       <form:hidden path="lstInputBean[${status.index}].objectType" />
													<form:hidden path="lstInputBean[${status.index}].objectFlg" value="true" />
													<qp:autocomplete name="lstInputBean[${status.index}].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeExternalObjectOfIn" value="${inputObj.objectId}" displayValue="${inputObj.inputBeanName}"></qp:autocomplete>
													<form:hidden path="lstInputBean[${status.index}].moduleId"/>
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstInputBean[${status.index}].inputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstInputBean[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstInputBean[${status.index}].dataType" onchange="$.qp.businessdesign.objectTypeChange(this);">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstInputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-in-getscope" align="center">
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${!inputObj.objectFlg}">
								<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-column-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${inputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstInputBean[${status.index}].inputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].screenItemId" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanType" value="1" />
													<form:hidden path="lstInputBean[${status.index}].parentInputBeanId" />
													<form:hidden path="lstInputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstInputBean[${status.index}].inputBeanName" />
													<form:hidden path="lstInputBean[${status.index}].tblDesignId" />
													<form:hidden path="lstInputBean[${status.index}].columnId" />
													<form:hidden path="lstInputBean[${status.index}].objectType" />
													<form:hidden path="lstInputBean[${status.index}].objectFlg" />
													<form:hidden path="lstInputBean[${status.index}].objectId"/>
													<label name="lstInputBean[${status.index}].inputBeanName" class="qp-output-text">${inputObj.inputBeanName}</label>
													<form:hidden path="lstInputBean[${status.index}].moduleId"/>
												</div>
											</div>
										</div>
									</td>
									<td><form:hidden path="lstInputBean[${status.index}].inputBeanCode" /> <label name="lstInputBean[${status.index}].inputBeanCode" class="qp-output-text">${inputObj.inputBeanCode}</label></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstInputBean[${status.index}].dataType" addChildFlg="false" />
											<label name="lstInputBean[${status.index}].dataType" class="qp-output-text">${CL_BD_DATATYPE.get(inputObj.dataType.toString())}</label>
											<c:if test="${inputObj.arrayFlg}">[]</c:if>
											<span class="input-group-addon" style="display: none"> <label><form:checkbox path="lstInputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-in-getscope" align="center">
									</td>
									<td></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${inputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:input path="lstInputBean[${status.index}].inputBeanName" class="form-control qp-input-text" maxlength="50" />
													<form:hidden path="lstInputBean[${status.index}].moduleId"/>
												</div>
											</div>
										</div>
									</td>
									<td><form:hidden path="lstInputBean[${status.index}].inputBeanId" /> <form:hidden path="lstInputBean[${status.index}].parentInputBeanId" /> <form:hidden path="lstInputBean[${status.index}].itemSequenceNo" /> <form:hidden path="lstInputBean[${status.index}].screenItemId" /> <form:hidden path="lstInputBean[${status.index}].inputBeanType" /> <form:hidden path="lstInputBean[${status.index}].objectFlg" value="true" /> <form:input path="lstInputBean[${status.index}].inputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:select cssClass="form-control qp-input-select" path="lstInputBean[${status.index}].dataType" onchange="$.qp.businessdesign.objectTypeChange(this);">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstInputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-in-getscope" align="center">
										<div class="dropdown">
											<c:choose>
												<c:when test="${inputObj.scopeType == 1 && inputObj.scopeValue != null}">
													<button class="btn btn-warning glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:otherwise>
											</c:choose>
											<ul class="dropdown-menu dropdown-nav-left">
												<li>
													<form:hidden path="lstInputBean[${status.index}].jsonValidationInputs" /> 
													<a class="qp-link qp-cursor" onclick="openModalValidationCheckDetail(this)"><qp:message code="sc.businesslogicdesign.0263"/></a>
												</li>
											</ul>
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
				<a title='<qp:message code="sc.businesslogicdesign.0200" />' type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl_inputbean_list_define',templateId:'tbl_inputbean_list_define-template',templateData:{groupId:''}});"></a>
			</div>
		</div>
	</div>
</div>