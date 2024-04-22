<tiles:insertDefinition name="layouts">

	<tiles:putAttribute name="header-name">
			<qp:message code="sc.functionmaster.0047" />
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
	
	<c:if test="${ not empty decisionTableForm}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/decisiontable/modify" modelAttribute="decisionTableForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="decisionTbId" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.decisiontable.0007"/></span>
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
								<th><qp:message code="sc.decisiontable.0005"/></th>
								<td><qp:formatText value="${decisionTableForm.decisionTbName }" /></td>
								<th><qp:message code="sc.decisiontable.0006"/></th>
								<td><qp:formatText value="${decisionTableForm.decisionTbCode }" /></td>
							</tr>
							<tr>
								<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
								<td><input type="hidden" name="designStatus" value="1"/><qp:message code="${CL_DESIGN_STATUS.get(decisionTableForm.designStatus.toString())}"  /></td>
								<th><qp:message code="sc.tabledesign.0013" /></th>
								<td><qp:formatRemark value="${decisionTableForm.remark }" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="display:none">
					<input type="hidden" name="formJson" value="${f:h(formJson)}" />
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="decisiontableModify">
						<form:hidden path="updatedDate" value="${decisionTableForm.updatedDate}"/>
						<form:hidden path="decisionTbName" value="${decisionTableForm.decisionTbName}"/>
						<form:hidden path="decisionTbCode" value="${decisionTableForm.decisionTbCode}"/>
						<form:hidden path="remark" value="${decisionTableForm.remark}"/>
						<form:hidden path="projectId" value="${decisionTableForm.projectId}"/>
						<form:hidden path="projectName" value="${decisionTableForm.projectName}"/>
						
						<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0023" /></button>
						<button type="submit" class="btn qp-button qp-dialog-confirm" name="isJsonForm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.domaindesign.0040"/></span>
					</div>
					<div class="panel-body">
						<table id="tbl_list_Subject"
							class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
							<colgroup>
								<col >
								<col width="30%">
								<col width="30%">
								<col width="30%">
							</colgroup>
							<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.businesslogicdesign.0005" /></th>
								<th><qp:message code="sc.businesslogicdesign.0006" /></th>
								<th><qp:message code="sc.module.0007" /></th>
							</tr>
							</thead>
							
							<tbody>
								<c:forEach var="businessLogic" items="${decisionTableForm.listBD}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">${status.count}</td>
										<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
										<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
										<td><qp:formatText value="${businessLogic.moduleName}"/></td>
									</tr>
								</c:forEach>
								<c:if test="${empty decisionTableForm.listBD}">
										<tr>
											<td colspan="4"><qp:message code="inf.sys.0013"/></td>
										</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>