<div id="tabBusiness-outputbean" style="height: auto;" class='tab-pane ${(businessDesignForm.returnType == 4 && businessDesignForm.customizeFlg) ? "active" : ""}'>
	<div class="panel panel-default qp-div-select">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
		</div>
		<div class="panel-body">
			<table class="table table-bordered qp-table-list" id="tbl_outputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.outputFormCallback">
				<colgroup>
					<col width="40px" />
					<col width="50%"/>
					<col width="30%" style="min-width: 150px" />
					<col width="180px" />
					<col width="30%" class="bd-ou-screenitem-hidden" />
					<col width="40px"/>
					<col width="40px" />
				</colgroup>
				<thead>
					<tr>
						<th><qp:message code="sc.businesslogicdesign.0036" /></th>
						<th><qp:message code="sc.businesslogicdesign.0037" /></th>
						<th><qp:message code="sc.businesslogicdesign.0038" /></th>
						<th><qp:message code="sc.businesslogicdesign.0039" /></th>
						<th class="bd-ou-screenitem-hidden"><qp:message code="sc.businesslogicdesign.0041" /></th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${businessDesignForm.lstOutputBean }" var="outputObj" varStatus="status">
						<c:choose>
							<c:when test="${outputObj.dataType == '14' && outputObj.objectFlg}">
								<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-entity-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${outputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstOutputBean[${status.index}].outputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].parentOutputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstOutputBean[${status.index}].outputBeanName" />
													<form:hidden path="lstOutputBean[${status.index}].objectFlg" value="true" />
													<qp:autocomplete name="lstOutputBean[${status.index}].tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="bdOnChangeTableDesignOfOu" value="${outputObj.tblDesignId}" displayValue="${outputObj.outputBeanName}"></qp:autocomplete>
													<form:hidden path="lstOutputBean[${status.index}].moduleId" />
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstOutputBean[${status.index}].outputBeanCode" class="form-control qp-input-text" maxlength="50" /> <%-- 															<form:hidden path="lstOutputBean[${status.index}].outputBeanCode"/> --%> <%-- 															<label name="lstOutputBean[${status.index}].outputBeanCode" class="qp-output-text">${outputObj.outputBeanCode}</label> --%></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstOutputBean[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstOutputBean[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstOutputBean[${status.index}].dataType" oldvalue="${outputObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstOutputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-ou-screenitem-hidden">
										<c:set var="myVar" /> 
										<c:forEach var="screenItems" items="${outputObj.lstScreenItemMapping}" varStatus="loop">
											<c:if test="${loop.first == true}">
												<c:set var="myVar" value="${screenItems.itemName}" />
											</c:if>
											<c:if test="${loop.first == false}">
												<c:set var="myVar" value="${myVar};${screenItems.itemName}" />
											</c:if>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].screenItemId" value="${screenItems.screenItemId}" mappingType="${screenItems.mappingType}"/>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].mappingType" value="${screenItems.mappingType}" />
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].itemName" value="${screenItems.itemName}" />
										</c:forEach>
										<label name="lstOutputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${myVar}</label> 
										<span title='<qp:message code="sc.businesslogicdesign.0261" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action bd-ou-screenitem-hidden qp-cursor" style="float: right; margin-right: 5px;" onclick="openModalAssignScreenItem(this)"></span>
									</td>
									<td align="center">
										<div class="dropdown">
											<c:choose>
												<c:when test="${outputObj.scopeType == 1 && outputObj.scopeValue != null}">
													<button class="btn btn-warning glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:otherwise>
											</c:choose>
											<ul class="dropdown-menu dropdown-nav-left">
												<li>
													<form:hidden path="lstOutputBean[${status.index}].scopeType"/>
													<form:hidden path="lstOutputBean[${status.index}].scopeValue"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].scopeValueAutocomplete"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].dataTypeSession" />
                                                    <form:hidden path="lstOutputBean[${status.index}].arrayFlagSession" />
													<a class="qp-link qp-cursor" onclick="openDialogSetScope(this,false)"><qp:message code="sc.businesslogicdesign.0262"/> </a>
												</li>
											</ul>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${outputObj.dataType == '16' && outputObj.objectFlg}">
								<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-common-object-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${outputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstOutputBean[${status.index}].outputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].parentOutputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstOutputBean[${status.index}].outputBeanName" />
	                                                <form:hidden path="lstOutputBean[${status.index}].objectType" />
													<form:hidden path="lstOutputBean[${status.index}].objectFlg" value="true" />
													<qp:autocomplete name="lstOutputBean[${status.index}].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObjectForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeCommonObjectOfOu" value="${outputObj.objectId}" displayValue="${outputObj.outputBeanName}"></qp:autocomplete>
													<form:hidden path="lstOutputBean[${status.index}].moduleId" />
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstOutputBean[${status.index}].outputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstOutputBean[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstOutputBean[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstOutputBean[${status.index}].dataType" oldvalue="${outputObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstOutputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-ou-screenitem-hidden">
										<c:set var="myVar" /> 
										<c:forEach var="screenItems" items="${outputObj.lstScreenItemMapping}" varStatus="loop">
											<c:if test="${loop.first == true}">
												<c:set var="myVar" value="${screenItems.itemName}" />
											</c:if>
											<c:if test="${loop.first == false}">
												<c:set var="myVar" value="${myVar};${screenItems.itemName}" />
											</c:if>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].screenItemId" value="${screenItems.screenItemId}" mappingType="${screenItems.mappingType}"/>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].mappingType" value="${screenItems.mappingType}" />
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].itemName" value="${screenItems.itemName}" />
										</c:forEach>
										<label name="lstOutputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${myVar}</label> 
										<span title='<qp:message code="sc.businesslogicdesign.0261" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action bd-ou-screenitem-hidden qp-cursor" style="float: right; margin-right: 5px;" onclick="openModalAssignScreenItem(this)"></span>
									</td>
									<td align="center">
										<div class="dropdown">
											<c:choose>
												<c:when test="${outputObj.scopeType == 1 && outputObj.scopeValue != null}">
													<button class="btn btn-warning glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:otherwise>
											</c:choose>
											<ul class="dropdown-menu dropdown-nav-left">
												<li>
													<form:hidden path="lstOutputBean[${status.index}].scopeType"/>
													<form:hidden path="lstOutputBean[${status.index}].scopeValue"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].scopeValueAutocomplete"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].dataTypeSession" />
                                                    <form:hidden path="lstOutputBean[${status.index}].arrayFlagSession" />
													<a class="qp-link qp-cursor" onclick="openDialogSetScope(this,false)"><qp:message code="sc.businesslogicdesign.0262"/></a>
												</li>
											</ul>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${outputObj.dataType == '17' && outputObj.objectFlg}">
								<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-external-object-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${outputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstOutputBean[${status.index}].outputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].parentOutputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstOutputBean[${status.index}].outputBeanName" />
	                                                <form:hidden path="lstOutputBean[${status.index}].objectType" />
													<form:hidden path="lstOutputBean[${status.index}].objectFlg" value="true" />
													<qp:autocomplete name="lstOutputBean[${status.index}].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForBD" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeExternalObjectOfOu" value="${outputObj.objectId}" displayValue="${outputObj.outputBeanName}"></qp:autocomplete>
													<form:hidden path="lstOutputBean[${status.index}].moduleId" />
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstOutputBean[${status.index}].outputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstOutputBean[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstOutputBean[${status.index}].dataType" />
											<form:select cssClass="form-control qp-input-select" path="lstOutputBean[${status.index}].dataType" oldvalue="${outputObj.dataType}" onchange="$.qp.businessdesign.objectTypeChange(this);" disabled="disabled">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstOutputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-ou-screenitem-hidden">
										<c:set var="myVar" /> 
										<c:forEach var="screenItems" items="${outputObj.lstScreenItemMapping}" varStatus="loop">
											<c:if test="${loop.first == true}">
												<c:set var="myVar" value="${screenItems.itemName}" />
											</c:if>
											<c:if test="${loop.first == false}">
												<c:set var="myVar" value="${myVar};${screenItems.itemName}" />
											</c:if>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].screenItemId" value="${screenItems.screenItemId}" mappingType="${screenItems.mappingType}"/>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].mappingType" value="${screenItems.mappingType}" />
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].itemName" value="${screenItems.itemName}" />
										</c:forEach>
										<label name="lstOutputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${myVar}</label> 
										<span title='<qp:message code="sc.businesslogicdesign.0261" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action bd-ou-screenitem-hidden qp-cursor" style="float: right; margin-right: 5px;" onclick="openModalAssignScreenItem(this)"></span>
									</td>
									<td align="center">
										<div class="dropdown">
											<c:choose>
												<c:when test="${outputObj.scopeType == 1 && outputObj.scopeValue != null}">
													<button class="btn btn-warning glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:otherwise>
											</c:choose>
											<ul class="dropdown-menu dropdown-nav-left">
												<li>
													<form:hidden path="lstOutputBean[${status.index}].scopeType"/>
													<form:hidden path="lstOutputBean[${status.index}].scopeValue"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].scopeValueAutocomplete"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].dataTypeSession" />
                                                    <form:hidden path="lstOutputBean[${status.index}].arrayFlagSession" />
													<a class="qp-link qp-cursor" onclick="openDialogSetScope(this,false)"><qp:message code="sc.businesslogicdesign.0262"/></a>
												</li>
											</ul>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
								</tr>
							</c:when>
							<c:when test="${!outputObj.objectFlg}">
								<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-columm-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${outputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<form:hidden path="lstOutputBean[${status.index}].outputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].parentOutputBeanId" />
													<form:hidden path="lstOutputBean[${status.index}].itemSequenceNo" />
													<form:hidden path="lstOutputBean[${status.index}].outputBeanName" />
													<form:hidden path="lstOutputBean[${status.index}].tblDesignId" />
													<form:hidden path="lstOutputBean[${status.index}].columnId" />
													<form:hidden path="lstOutputBean[${status.index}].objectFlg"/>
													<form:hidden path="lstOutputBean[${status.index}].objectId"/>
													<form:hidden path="lstOutputBean[${status.index}].objectType"/>
													<label name="lstOutputBean[${status.index}].outputBeanName" class="qp-output-text">${outputObj.outputBeanName}</label>
													<form:hidden path="lstOutputBean[${status.index}].moduleId" />
												</div>
											</div>
										</div>
									</td>
									<td><form:hidden path="lstOutputBean[${status.index}].outputBeanCode" /> <label name="lstOutputBean[${status.index}].outputBeanCode" class="qp-output-text">${outputObj.outputBeanCode}</label></td>
									<td>
										<div class="input-group">
											<form:hidden path="lstOutputBean[${status.index}].groupBaseTypeId" />
											<form:hidden path="lstOutputBean[${status.index}].dataType" addChildFlg="false" />
											<label name="lstOutputBean[${status.index}].dataType" class="qp-output-text">${CL_BD_DATATYPE.get(outputObj.dataType.toString())}</label>
											<c:if test="${outputObj.arrayFlg}">
																	[]
																</c:if>
											<span class="input-group-addon" style="display: none"> <label><form:checkbox path="lstOutputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
										</div>
									</td>
									<td class="bd-ou-screenitem-hidden">
										<c:set var="myVar" /> 
										<c:forEach var="screenItems" items="${outputObj.lstScreenItemMapping}" varStatus="loop">
											<c:if test="${loop.first == true}">
												<c:set var="myVar" value="${screenItems.itemName}" />
											</c:if>
											<c:if test="${loop.first == false}">
												<c:set var="myVar" value="${myVar};${screenItems.itemName}" />
											</c:if>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].screenItemId" value="${screenItems.screenItemId}" mappingType="${screenItems.mappingType}"/>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].mappingType" value="${screenItems.mappingType}" />
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].itemName" value="${screenItems.itemName}" />
										</c:forEach>
										<label name="lstOutputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${myVar}</label> 
										<span title='<qp:message code="sc.businesslogicdesign.0261" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action bd-ou-screenitem-hidden qp-cursor" style="float: right; margin-right: 5px;" onclick="openModalAssignScreenItem(this)"></span>
									</td>
									<td align="center">
										<div class="dropdown">
											<c:choose>
												<c:when test="${outputObj.scopeType == 1 && outputObj.scopeValue != null}">
													<button class="btn btn-warning glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:otherwise>
											</c:choose>
											<ul class="dropdown-menu dropdown-nav-left">
												<li>
													<form:hidden path="lstOutputBean[${status.index}].scopeType"/>
													<form:hidden path="lstOutputBean[${status.index}].scopeValue"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].scopeValueAutocomplete"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].dataTypeSession" />
                                                    <form:hidden path="lstOutputBean[${status.index}].arrayFlagSession" />
													<a class="qp-link qp-cursor" onclick="openDialogSetScope(this,false)"><qp:message code="sc.businesslogicdesign.0262"/></a>
												</li>
											</ul>
										</div>
									</td>
									<td></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }">
									<td colspan="2">
										<div style="height: 100%">
											<div class="vertical-midle-div">
												<span class="ar-groupIndex">${outputObj.tableIndex }</span>
											</div>
											<div class="pull-right" style="height:100%">	
												<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
												<div style="width:98%; display: inline-block; vertical-align: middle;">
													<input type="hidden" name="lstOutputBean[${status.index}].outputBeanId" value="${outputObj.outputBeanId}" /> <input type="hidden" name="lstOutputBean[${status.index}].parentOutputBeanId" value="${outputObj.parentOutputBeanId}" /> <input type="hidden" name="lstOutputBean[${status.index}].itemSequenceNo" value="${outputObj.itemSequenceNo}" /> <input type="hidden" name="lstOutputBean[${status.index}].objectFlg" value="true" />
													<form:input path="lstOutputBean[${status.index}].outputBeanName" class="form-control qp-input-text" maxlength="200" />
													<form:hidden path="lstOutputBean[${status.index}].moduleId" />
												</div>
											</div>
										</div>
									</td>
									<td><form:input path="lstOutputBean[${status.index}].outputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
									<td>
										<div class="input-group">
											<form:select cssClass="form-control qp-input-select" path="lstOutputBean[${status.index}].dataType" onchange="$.qp.businessdesign.objectTypeChange(this);">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
												<form:options items="${CL_BD_DATATYPE}" />
											</form:select>
											<span class="input-group-addon"> <label><form:checkbox path="lstOutputBean[${status.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
											</span>
											<form:hidden path="lstOutputBean[${status.index}].groupBaseTypeId" />
										</div>
									</td>
									<td class="bd-ou-screenitem-hidden">
										<c:set var="myVar" />
										<c:forEach var="screenItems" items="${outputObj.lstScreenItemMapping}" varStatus="loop">
											<c:if test="${loop.first == true}">
												<c:set var="myVar" value="${screenItems.itemName}" />
											</c:if>
											<c:if test="${loop.first == false}">
												<c:set var="myVar" value="${myVar};${screenItems.itemName}" />
											</c:if>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].screenItemId" value="${screenItems.screenItemId}" mappingType="${screenItems.mappingType}"/>
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].mappingType" value="${screenItems.mappingType}" />
											<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].itemName" value="${screenItems.itemName}" />
										</c:forEach> 
										<label name="lstOutputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${myVar}</label> 
										<span title='<qp:message code="sc.businesslogicdesign.0261" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action qp-cursor" style="float: right; margin-right: 5px;" onclick="openModalAssignScreenItem(this)"></span>
									</td>
									<td align="center">
										<div class="dropdown">
											<c:choose>
												<c:when test="${outputObj.scopeType == 1 && outputObj.scopeValue != null}">
													<button class="btn btn-warning glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
												</c:otherwise>
											</c:choose>
											<ul class="dropdown-menu dropdown-nav-left">
												<li>
													<form:hidden path="lstOutputBean[${status.index}].scopeType"/>
													<form:hidden path="lstOutputBean[${status.index}].scopeValue"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].scopeValueAutocomplete"/>
                                                    <form:hidden path="lstOutputBean[${status.index}].dataTypeSession" />
                                                    <form:hidden path="lstOutputBean[${status.index}].arrayFlagSession" />
													<a class="qp-link qp-cursor" onclick="openDialogSetScope(this,false)"><qp:message code="sc.businesslogicdesign.0262"/></a>
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
				<a title='<qp:message code="sc.businesslogicdesign.0200" />' type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl_outputbean_list_define',templateId:'tbl_outputbean_list_define-template',templateData:{groupId:''}});"></a>
			</div>
		</div>
	</div>
</div>