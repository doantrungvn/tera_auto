<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span><qp:message code="sc.generation.0015"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/generatedocument/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=generatedocument"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generatedocument/javascript/generateDocument.js"></script>
		<script type="text/javascript">
			$.qp.generateDocument.init('<c:out value="${generateDocumentForm.selectType}"/>');
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
	
		<form:form method="post" action="${pageContext.request.contextPath}/generatedocument/generatedocument" modelAttribute="generateDocumentForm">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="selectType"/>
			<form:hidden path="jsonBackup"/>
			<div class="panel panel-default qp-div-information project-item">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.project.0010"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tblProject">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><qp:message code="sc.project.0005"/></th>
							<td class="word-wrap">
								<qp:formatText value="${generateDocumentForm.project.projectName }" />
								<form:hidden path="project.projectId" value="${generateDocumentForm.project.projectId }"/>
								<form:hidden path="project.projectName" value="${generateDocumentForm.project.projectName }"/>
							</td>
							<th><qp:message code="sc.project.0006"/></th>
							<td class="word-wrap">
								<qp:formatText value="${generateDocumentForm.project.projectCode }" />
								<form:hidden path="project.projectCode" value="${generateDocumentForm.project.projectCode }"/>	
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0055"></qp:message></th>
							<td><form:hidden path="project.status"/>
								<qp:message code="${CL_DESIGN_STATUS.get(generateDocumentForm.project.status.toString())}"/>
								<form:hidden path="project.status" value="${generateDocumentForm.project.status }"/>	
							</td>
							<th><qp:message code="sc.sys.0028"/></th>
							<td>
								<form:label path="project.remark"><qp:formatText value="${generateDocumentForm.project.remark}" /></form:label>
								<form:hidden path="project.remark" value="${generateDocumentForm.project.remark }"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default qp-div-information module-item">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.module.0005"></qp:message></span>
				</div>
				<div class="panel-body">
					<form:errors path="*" cssClass="error" element="div" />
					<table class="table table-bordered qp-table-form" id="tblModuleLst">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><qp:message code="sc.module.0007" /></th>
							<td class="word-wrap">
								<qp:formatText value="${generateDocumentForm.module.moduleName }" />
								<form:hidden path="module.moduleId" value="${generateDocumentForm.module.moduleId }"/>
								<form:hidden path="module.moduleName" value="${generateDocumentForm.project.projectName }"/>
							</td>
							<th><qp:message code="sc.module.0008" /></th>
							<td class="word-wrap">
								<qp:formatText value="${generateDocumentForm.module.moduleCode }" />
								<form:hidden path="module.moduleCode" value="${generateDocumentForm.project.projectCode }"/>	
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0055"></qp:message></th>
							<td><form:hidden path="module.status"/>
								<qp:message code="${CL_DESIGN_STATUS.get(generateDocumentForm.module.status.toString())}"/>
								<form:hidden path="module.status" value="${generateDocumentForm.module.status }"/>	
							</td>
							<th><qp:message code="sc.sys.0028"/></th>
							<td>
								<form:label path="module.remark"><qp:formatText value="${generateDocumentForm.module.remark}" /></form:label>
								<form:hidden path="module.remark" value="${generateDocumentForm.module.remark }"/>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="qp-div-action">
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
	                <qp:authorization permission="changeDesignStatus">
	                	<button type="submit" class="btn qp-button" name="direct" value="prev" ><qp:message code="sc.sys.0049" /></button>
	                    <button id="projectscreen" type="submit" class="btn qp-button qp-dialog-confirm" name="direct" value="next"
	                    	data-confirm-pcallback="$.qp.generateDocument.validateBeforeSubmit" messageId="inf.sys.0025"><qp:message code="sc.generation.0008"/></button>
	                </qp:authorization>
	            </c:if>
			</div>
			
			<!-- New block item -->
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generatedocument.0067"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%" id="tblGenerateSetting">
						<colgroup>
							<col width="30%">
							<col width="30%">
							<col width="40%">
						</colgroup>
						<!-- Start new block -->
						<thead>
							<tr>
								<th style="text-align: left;" colspan="3">
									<qp:message code="sc.generatedocument.0000"/>&nbsp;<label><qp:formatText value="${generateDocumentForm.generateDocumentItem.documentItemCollaseName}"/></label>
								</th>
							</tr>
							<tr>
								<th style="text-align: left;">
									<qp:formatText value="${generateDocumentForm.generateDocumentItem.documentItemName}"/>
								</th>
								<th style="text-align: left;">
									<qp:formatText value="${generateDocumentForm.generateDocumentItem.documentItemCode}"/>
								</th>
								<th style="text-align: left;">
									<qp:message code="sc.generatedocument.0071"/>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty generateDocumentForm.generateDocumentItemLst}">
								<c:forEach var="item" items="${generateDocumentForm.generateDocumentItemLst}" varStatus="loop">
									<tr align="left" class="form-inline">
										<td class="com-output-text" style="text-align: left;" ><label><input id="${item.id}" name="generateDocumentItem.id" class="qp-input-radio-margin qp-input-radio" type="radio" value="${item.id}" checked="" onclick="">&nbsp;${item.documentItemName }</label></td>
										<td class="com-output-text"><label><qp:formatText value="${item.documentItemCode }"/></label></td>
										<td class="com-output-text"><label><qp:formatText value="${item.documentItemFileName }"/></label></td>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemScopeItemType" value="${item.documentItemScopeItemType }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemParenItemType" value="${item.documentItemParenItemType }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemType" value="${item.documentItemType }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemTemplateName" value="${item.documentItemTemplateName }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].id" value="${item.id }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemFileName" value="${item.documentItemFileName }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].isChecked" value="${item.isChecked }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemCode" value="${item.documentItemCode }"/>
										<form:hidden path="generateDocumentItemLst[${loop.index}].documentItemName" value="${item.documentItemName }"/>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty generateDocumentForm.generateDocumentItemLst}">
								<tr>
									<td colspan="3"><qp:message code="inf.sys.0013"/></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<!-- End new block -->
			
			<div class="qp-div-action">
				<form:hidden path="generateDocumentItem.documentItemScopeItemType"/>
				<form:hidden path="generateDocumentItem.documentItemParenItemType"/>
				<form:hidden path="generateDocumentItem.documentItemType"/>
				<form:hidden path="generateDocumentItem.documentItemTemplateName"/>
				<form:hidden path="generateDocumentItem.documentItemCollaseName"/>
				<form:hidden path="generateDocumentItem.documentItemFileName"/>
				<form:hidden path="generateDocumentItem.documentItemName"/>
				<form:hidden path="generateDocumentItem.documentItemCode"/>
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
	                <qp:authorization permission="generationGeneratedocument">
	                	<button type="submit" class="btn qp-button" name="direct" value="prev"><qp:message code="sc.sys.0049" /></button>
	                    <button id="projectscreen" type="submit" class="btn qp-button qp-dialog-confirm" name="direct" value="next" 
	                    	data-confirm-pcallback="$.qp.generateDocument.validateBeforeSubmit" messageId="inf.sys.0025"><qp:message code="sc.generation.0008"/></button>
	                </qp:authorization>
	            </c:if>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>