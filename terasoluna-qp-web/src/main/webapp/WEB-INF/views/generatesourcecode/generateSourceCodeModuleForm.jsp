<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span><qp:message code="sc.generatesourcecode.0009"/></span></li>
    </tiles:putAttribute>
		
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/generatesourcecode/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generateSourceCode/javascript/generateSourceCode.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generateSourceCode/javascript/initial.js"></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/generatesourcecode/generatesourcecode" modelAttribute="generateSourceCodeForm">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.module.0005" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><qp:message code="sc.module.0007" /></th>
							<td class="word-wrap">
								<form:label path="module.moduleName"><qp:formatText value="${generateSourceCodeForm.module.moduleName }" /></form:label>
								<form:hidden path="module.moduleName" value="${generateDocumentForm.module.moduleName }"/>
                                <form:hidden path="module.author" value="${generateDocumentForm.module.author }"/>
							</td>
							<th><qp:message code="sc.module.0008" /></th>
							<td class="word-wrap">
								<form:label path="module.moduleCode"><qp:formatText value="${generateSourceCodeForm.module.moduleCode }" /></form:label>
								<form:hidden path="module.moduleCode" value="${generateDocumentForm.module.moduleCode }"/>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0055"></qp:message></th>
							<td>
								<form:label path="module.status"><qp:message code="${CL_DESIGN_STATUS.get(generateSourceCodeForm.module.status.toString())}"/></form:label>
								<form:hidden path="module.status" value="${generateDocumentForm.module.status }"/>
							</td>
							<th><qp:message code="sc.sys.0028"/></th>
							<td>
								<form:label path="module.remark"><qp:formatText value="${generateSourceCodeForm.module.remark }" /></form:label>
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
	                    	data-confirm-pcallback="$.qp.generateSourceCode.validateBeforeSubmit"><qp:message code="sc.generation.0008"/></button>
	                </qp:authorization>
	            </c:if>
			</div>
            <c:if test="${generateSourceCodeForm.listOfScreenDesign != null && generateSourceCodeForm.listOfScreenDesign.size() > 0}">
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generatesourcecode.0010" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%;vertical-align: top;" id="tblScreen">
						<colgroup>
							<col>
							<col width="35%">
							<col width="35%">
							<col width="30%">
						</colgroup>
						<thead>
							<tr>
								<th><input class="checkbox qp-input-checkbox" type="checkbox" value="true" checked="checked" scope="root" onclick="$.qp.initial.switchAllModuleSelect(this)"></th>
								<th><qp:message code="sc.screendesign.0005"/></th>
								<th><qp:message code="sc.screendesign.0007"/></th>
								<th><qp:message code="sc.sys.0055" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${generateSourceCodeForm.listOfScreenDesign}" varStatus="rowStatus">
								<tr>
									<td><input class="checkbox qp-input-checkbox" type="checkbox" value="true" checked="checked" scope="node" onclick="$.qp.initial.switchAllModuleSelect(this)"></td>
									<td class="com-output-text"><qp:formatText value="${item.messageDesign.messageString}"/>
										<input type="hidden" name="listOfScreenDesign[${rowStatus.index}].screenId" value="${item.screenId }"/>
										<input type="hidden" name="listOfScreenDesign[${rowStatus.index}].screenCode" value="${item.screenCode }"/>
										<input type="hidden" name="listOfScreenDesign[${rowStatus.index}].remark" value="${item.remark }"/>
										<input type="hidden" name="listOfScreenDesign[${rowStatus.index}].screenTypeName" value="${item.screenTypeName }"/>										
									</td>
									<td><qp:formatText value="${item.screenCode}"/></td>
									<td><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
									
								</tr>
							</c:forEach>
							<c:if test="${empty generateSourceCodeForm.listOfScreenDesign}">
								<tr>
									<td colspan="4"><qp:message code="inf.sys.0013"/></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
            </c:if>
            <c:if test="${generateSourceCodeForm.listOfBusinessDesign != null && generateSourceCodeForm.listOfBusinessDesign.size() > 0}">
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generatesourcecode.0011" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%;vertical-align: top;" id="tblBlogic">
						<colgroup>
							<col>
							<col width="35%">
							<col width="35%">
							<col width="30%">
						</colgroup>
						<thead>
							<tr>
								<th><input class="checkbox qp-input-checkbox" type="checkbox" value="true" checked="checked" scope="root" onclick="$.qp.initial.switchAllModuleSelect(this)"></th>
								<th style="text-align: left;"><qp:message code="sc.businesslogicdesign.0005"/></th>
								<th><qp:message code="sc.businesslogicdesign.0006"/></th>
								<th><qp:message code="sc.sys.0055" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${generateSourceCodeForm.listOfBusinessDesign}" varStatus="rowStatus">
								<tr>
									<td><input class="checkbox qp-input-checkbox" type="checkbox" value="true" checked="checked" scope="node" onclick="$.qp.initial.switchAllModuleSelect(this)"></td>
									<td style="text-align: left;" class="com-output-text"><qp:formatText value="${item.businessLogicName }"/>
										<input type="hidden" name="listOfBusinessDesign[${rowStatus.index}].businessLogicId" value="${item.businessLogicId }"/>
										<input type="hidden" name="listOfBusinessDesign[${rowStatus.index}].businessLogicCode" value="${item.businessLogicCode }"/>
										<input type="hidden" name="listOfBusinessDesign[${rowStatus.index}].businessLogicName" value="${item.businessLogicName }"/>
										<input type="hidden" name="listOfBusinessDesign[${rowStatus.index}].remark" value="${item.remark }"/>		
									</td>
									<td><qp:formatText value="${item.businessLogicCode }"/></td>
									<td align="left"><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
								</tr>
							</c:forEach>
							<c:if test="${empty generateSourceCodeForm.listOfBusinessDesign}">
								<tr>
									<td colspan="4"><qp:message code="inf.sys.0013"/></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
            </c:if>
            <c:if test="${generateSourceCodeForm.listOfDecisionTable != null && generateSourceCodeForm.listOfDecisionTable.size() > 0}">
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generatesourcecode.0012" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%;vertical-align: top;" id="tblDecision">
						<colgroup>
							<col>
							<col width="35%">
							<col width="35%">
							<col width="30%">
						</colgroup>
						<thead>
							<tr>
								<th><input class="checkbox qp-input-checkbox" type="checkbox" value="true" checked="checked" scope="root" onclick="$.qp.initial.switchAllModuleSelect(this)"></th>
								<th style="text-align: left;"><qp:message code="sc.decisiontable.0005"/></th>
								<th><qp:message code="sc.decisiontable.0006"/></th>
								<th><qp:message code="sc.sys.0055" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${generateSourceCodeForm.listOfDecisionTable}" varStatus="status">
								<tr>
									<td><input class="checkbox qp-input-checkbox" type="checkbox" value="true" checked="checked" scope="node" onclick="$.qp.initial.switchAllModuleSelect(this)"></td>
									<td style="text-align: left;" class="com-output-text"><qp:formatText value="${item.decisionTbName }"/>
										<input type="hidden" name="listOfDecisionTable[${rowStatus.index}].decisionTbId" value="${item.decisionTbId }"/>
										<input type="hidden" name="listOfDecisionTable[${rowStatus.index}].decisionTbName" value="${item.decisionTbName }"/>
										<input type="hidden" name="listOfDecisionTable[${rowStatus.index}].decisionTbCode" value="${item.decisionTbCode }"/>
										<input type="hidden" name="listOfDecisionTable[${rowStatus.index}].remark" value="${item.remark }"/>	
									</td>
									<td><qp:formatText value="${item.decisionTbCode }"/></td>
									<td align="left"><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
								</tr>
							</c:forEach>
							<c:if test="${empty generateSourceCodeForm.listOfDecisionTable}">
								<tr>
									<td colspan="4"><qp:message code="inf.sys.0013"/></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
            </c:if>
			<div class="qp-div-action">
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
	                <qp:authorization permission="generationGeneratesourcecode">
	                	<button type="submit" class="btn qp-button" name="direct" value="prev" ><qp:message code="sc.sys.0049" /></button>
	                    <button id="projectscreen" type="submit" class="btn qp-button qp-dialog-confirm" name="direct" value="next"
	                    	data-confirm-pcallback="$.qp.generateSourceCode.validateBeforeSubmit"><qp:message code="sc.generation.0008"/></button>
	                </qp:authorization>
	            </c:if>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>