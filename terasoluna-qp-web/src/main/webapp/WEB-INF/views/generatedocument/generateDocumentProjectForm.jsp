<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span><qp:message code="sc.generation.0015"/></span></li>
    </tiles:putAttribute>
        
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=generatedocument"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generatedocument/javascript/initial.js"></script>
		<script type="text/javascript">
			$.qp.initial.init();
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/generatedocument/confirmgeneratedocument" modelAttribute="generateDocumentForm">
			<input type="hidden" id="fileName" value="${fileName}">
			<form:hidden path="module.moduleId"/>
			<form:hidden path="selectType"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generation.0016"/></span>
				</div>
				<div class="panel-body">
					<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
					<table class="table table-bordered qp-table-form" id="tbl_list_result">
						<colgroup>
							<col width="31%" />
							<col width="69%" />
						</colgroup>
						<tr>
							<th style="text-align: left;">
								<qp:message code="sc.generation.0017"/>
							</th>
							<th style="text-align: left;">
								<label style="margin-right: 30px"><input id="selectType1" name="selectType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1" checked="checked" onclick="$.qp.initial.selectTypeScope(this)"><qp:message code="sc.project.0010"/></label>
								<label><input id="selectType2" name="selectType" class="qp-input-radio-margin qp-input-radio" type="radio" value="2" onclick="$.qp.initial.selectTypeScope(this)"><qp:message code="sc.generation.0020"/></label>
							</th>
						</tr>
						<tr >
							<td rowspan="2" style="vertical-align: top;">
								<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%" id="tblGenerateSetting">
									<colgroup>
										<col width="8%">
										<col width="92%">
									</colgroup>
									<thead>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${generateDocumentForm.selectType eq 1}">
												<tr class="success form-inline" id="project">
													<td colspan="2" style="text-align: left;"><label scope="project_rd"><qp:message code="sc.generation.0018"/></label></td>
												</tr>
												<!-- Output RD document of project -->
												<c:forEach var="rd" items="${generateDocumentForm.generateDocumentProjectTypeRDLst}" varStatus="loop">
													<tr align="left" class="form-inline item-checked-project hidden" name="rowProject">
													<td></td>
													<td class="com-output-text"><label> <input id="projectRD${rd.documentItemType }" name="projectDocument" class="qp-input-radio-margin qp-input-radio" type="radio" value="${rd.documentItemType }"  scope="project_rd_doc">&nbsp;${rd.documentItemTemplateName }</label></td>
													<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemScopeItemType" value="${rd.documentItemScopeItemType }"/>
													<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemParenItemType" value="${rd.documentItemParenItemType }"/>
													<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemType" value="${rd.documentItemType }"/>
													<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemTemplateName" value="${rd.documentItemTemplateName }"/>
													<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].isChecked" value="${rd.isChecked}"/>
												</tr>
												</c:forEach>
												<tr align="left" class="success form-inline" id="project">
													<td colspan="2" style="text-align: left;"><label scope="project_ed"><qp:message code="sc.generation.0019"/></label></td>
												</tr>
												<!-- Output ED document of project -->
												<c:forEach var="ed" items="${generateDocumentForm.generateDocumentProjectTypeEDLst }" varStatus="loop">
													<tr align="left" class="form-inline item-checked-project hidden" name="rowProject">
													<td></td>
													<td class="com-output-text"><label> <input id="projectED${rd.documentItemType }" name="projectDocument" class="qp-input-radio-margin qp-input-radio" type="radio" value="${ed.documentItemType }"  scope="project_ed_doc">&nbsp;${ed.documentItemTemplateName }</label></td>
													<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemScopeItemType" value="${ed.documentItemScopeItemType }"/>
													<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemParenItemType" value="${ed.documentItemParenItemType }"/>
													<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemType" value="${ed.documentItemType }"/>
													<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemTemplateName" value="${ed.documentItemTemplateName }"/>
													<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].isChecked" value="${ed.isChecked }"/>
												</tr>
												</c:forEach>
												<!-- Start select document of module -->
												<tr class="success form-inline hidden" id="module">
													<td colspan="2" style="text-align: left;"><label scope="module_ed"><qp:message code="sc.generation.0019"/></label></td>
												</tr>
												<!-- Output ED document of module -->
												<c:forEach var="ed" items="${generateDocumentForm.generateDocumentModuleTypeEDLst }" varStatus="loop">
													<tr align="left" class="form-inline item-checked-module hidden" name="rowModule">
														<td></td>
														<td class="com-output-text"><label> <input id="moduleED${rd.documentItemType }" name="moduleED" class="qp-input-radio-margin qp-input-radio" type="radio" value="${ed.documentItemType }" scope="module_ed_doc">&nbsp;${ed.documentItemTemplateName }</label></td>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemScopeItemType" value="${ed.documentItemScopeItemType }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemParenItemType" value="${ed.documentItemParenItemType }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemType" value="${ed.documentItemType }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemTemplateName" value="${ed.documentItemTemplateName }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].isChecked" value="${ed.isChecked }"/>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="success form-inline" id="project">
													<td colspan="2" style="text-align: left;"><label scope="project_rd"><qp:message code="sc.generation.0018"/></label></td>
												</tr>
												<!-- Output RD document of project -->
												<c:forEach var="rd" items="${generateDocumentForm.generateDocumentProjectTypeRDLst}" varStatus="loop">
													<tr align="left" class="form-inline item-checked-project hidden"  name="rowProject">
														<td></td>
														<td class="com-output-text"><label> <input id="projectRD${rd.documentItemType }" name="projectDocument" class="qp-input-radio-margin qp-input-radio" type="radio" value="${rd.documentItemType }"  scope="project_rd_doc">&nbsp;${rd.documentItemTemplateName }</label></td>
														<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemScopeItemType" value="${rd.documentItemScopeItemType }"/>
														<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemParenItemType" value="${rd.documentItemParenItemType }"/>
														<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemType" value="${rd.documentItemType }"/>
														<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].documentItemTemplateName" value="${rd.documentItemTemplateName }"/>
														<form:hidden path="generateDocumentProjectTypeRDLst[${loop.index}].isChecked" value="${rd.isChecked}"/>
													</tr>
												</c:forEach>
												<tr align="left" class="success form-inline" id="project">
													<td colspan="2" style="text-align: left;"><label scope="project_ed"><qp:message code="sc.generation.0019"/></label></td>
												</tr>
												<!-- Output ED document of project -->
												<c:forEach var="ed" items="${generateDocumentForm.generateDocumentProjectTypeEDLst }" varStatus="loop">
													<tr align="left" class="form-inline item-checked-project hidden"  name="rowProject">
														<td></td>
														<td class="com-output-text"><label> <input id="projectED${rd.documentItemType }" name="projectDocument" class="qp-input-radio-margin qp-input-radio" type="radio" value="${ed.documentItemType }"  scope="project_ed_doc">&nbsp;${ed.documentItemTemplateName }</label></td>
														<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemScopeItemType" value="${ed.documentItemScopeItemType }"/>
														<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemParenItemType" value="${ed.documentItemParenItemType }"/>
														<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemType" value="${ed.documentItemType }"/>
														<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].documentItemTemplateName" value="${ed.documentItemTemplateName }"/>
														<form:hidden path="generateDocumentProjectTypeEDLst[${loop.index}].isChecked" value="${ed.isChecked }"/>
													</tr>
												</c:forEach>
												<!-- Start select document of module -->
												<tr class="success form-inline hidden" id="module">
													<td colspan="2" style="text-align: left;"><label scope="module_ed"><qp:message code="sc.generation.0019"/></label></td>
												</tr>
												<!-- Output ED document of module -->
												<c:forEach var="ed" items="${generateDocumentForm.generateDocumentModuleTypeEDLst }" varStatus="loop">
													<tr align="left" class="form-inline item-checked-module hidden" name="rowModule">
														<td></td>
														<td class="com-output-text"><label> <input id="moduleED${rd.documentItemType }" name="moduleED" class="qp-input-radio-margin qp-input-radio" type="radio" value="${ed.documentItemType }" scope="module_ed_doc">&nbsp;${ed.documentItemTemplateName }</label></td>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemScopeItemType" value="${ed.documentItemScopeItemType }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemParenItemType" value="${ed.documentItemParenItemType }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemType" value="${ed.documentItemType }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].documentItemTemplateName" value="${ed.documentItemTemplateName }"/>
														<form:hidden path="generateDocumentModuleTypeEDLst[${loop.index}].isChecked" value="${ed.isChecked }"/>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
						 	</td>
							<td style="vertical-align: top;">
								<table class="table table-bordered qp-table-form" style="width: 100%; vertical-align: top;" id="tblProject">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th><qp:message code="sc.project.0005" /></th>
										<td class="word-wrap"><form:label path="project.projectName"><qp:formatText value="${generateDocumentForm.project.projectName }"/></form:label>
											<form:hidden path="project.projectId" value="${generateDocumentForm.project.projectId }"/>
											<form:hidden path="project.projectName" value="${generateDocumentForm.project.projectName }"/>
										</td>
										<th><qp:message code="sc.project.0006" /></th>
										<td class="word-wrap"><form:label path="project.projectCode"><qp:formatText value="${generateDocumentForm.project.projectCode }" /></form:label>
											<form:hidden path="project.projectCode" value="${generateDocumentForm.project.projectCode }"/>
										</td>
									</tr>
									<tr>
										<th><qp:message code="sc.sys.0055"></qp:message></th>
										<td>
											<form:hidden path="project.status"/><form:label path="project.status"><qp:message code="${CL_DESIGN_STATUS.get(generateDocumentForm.project.status.toString())}"/></form:label>
											<form:hidden path="project.status" value="${generateDocumentForm.project.status }"/>
										</td>
										<th><qp:message code="sc.sys.0028"/></th>
										<td><form:label path="project.remark"><qp:formatText value="${generateDocumentForm.project.remark }" /></form:label>
											<form:hidden path="project.remark" value="${generateDocumentForm.project.remark }"/>
										</td>
									</tr>
								</table>
								<table class="table table-bordered qp-table-list-none-action pull-right hidden" style="width: 100%; vertical-align: top;" id="tblModule">
									<colgroup>
										<col width="35%">
										<col width="35%">
										<col width="30%">
									</colgroup>
									<thead>
										<tr>
											<th style="text-align: left;"><qp:message code="sc.module.0007" /></th>
											<th><qp:message code="sc.module.0008" /></th>
											<th><qp:message code="sc.sys.0055" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${generateDocumentForm.moduleList}" varStatus="loop">
											<tr align="left" class="form-inline">
												<td style="text-align: left;"> <label><input class="qp-input-radio-margin qp-input-radio" id="${item.moduleId }" name="module.moduleId" type="radio" value="${item.moduleId }" scope="module_item">&nbsp;<qp:formatText value="${item.moduleName}"/></label>
													<!-- hidden field -->
													<input type="hidden" name="moduleList[${loop.index}].moduleId" value="${item.moduleId }">
													<input type="hidden" name="moduleList[${loop.index}].moduleName" value="${item.moduleName }">
													<input type="hidden" name="moduleList[${loop.index}].moduleCode" value="${item.moduleCode }">
													<input type="hidden" name="moduleList[${loop.index}].status" value="${item.status }">
													<!-- selectedGenerate equal 1 it mean seleted -->
													<input type="hidden" name="moduleList[${loop.index}].selectedGenerate" value="0">
												</td>
												<td><qp:formatText value="${item.moduleCode}"/></td>
												<td align="left"><qp:message code="${CL_DESIGN_STATUS.get(item.status.toString())}"/></td>
											</tr>
										</c:forEach>
										<c:if test="${empty generateDocumentForm.moduleList}">
											<tr>
												<td colspan="4"><qp:message code="inf.sys.0013"/></td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
			<!-- Set document for module -->
				<form:hidden path="generateDocumentItem.documentItemScopeItemType"/>
				<form:hidden path="generateDocumentItem.documentItemParenItemType"/>
				<form:hidden path="generateDocumentItem.documentItemType"/>
				<form:hidden path="generateDocumentItem.documentItemTemplateName"/>
				<!-- Setting for module -->
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
	                <qp:authorization permission="generationGeneratedocument">
	                    <button id="projectscreen" scope="project" type="submit" class="btn qp-button qp-dialog-confirm" name="scope" value="isProject" 
	                    	data-confirm-pcallback="$.qp.initial.validateBeforeSubmit" data-confirm-dialog-flg="true" messageId="inf.sys.0025"><qp:message code="sc.sys.0050"/></button>
	                </qp:authorization>
	            </c:if>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>