<tiles:insertDefinition name="layouts">
	<%-- <tiles:putAttribute name="header-name">
			List Affected Change Design
	</tiles:putAttribute> --%>

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.graphicdatabasedeisgn"></qp:message></span></li>
		<li><span><qp:message code="sc.functionmaster.0047"/></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
	<c:if test="${ not empty graphicDbDesignForm}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/graphicdatabasedesign/modifyGraphic" modelAttribute="graphicDbDesignForm">
				<input type="hidden" name="formJson" value='${f:h(formJson)}' />
				<div class="qp-div-action">
					<qp:authorization permission="tabledesignModify">
						<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0049" /></button>
						<button type="submit" class="btn qp-button qp-dialog-confirm" name="isJsonForm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
				
				<c:if test="${ not empty graphicDbDesignForm.listBusinessLogics}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.domaindesign.0040" /></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col width="5%">
									<col>
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
								<tbody >
									<c:forEach var="businessLogic" items="${graphicDbDesignForm.listBusinessLogics}" varStatus="status1">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status1.count}</td>
											<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
											<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
											<td><qp:formatText value="${businessLogic.moduleName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>

				<c:if test="${ not empty graphicDbDesignForm.listSqlDesigns}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.domaindesign.0041" /></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col width="5%">
									<col>
									<col width="30%">
									<col width="30%">
								</colgroup>	
								<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.sqldesign.0010"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0011"></qp:message></th>
									<th><qp:message code="sc.module.0007" /></th>
								</tr>
								</thead>
								<tbody >
									<c:forEach var="sqlDesign" items="${graphicDbDesignForm.listSqlDesigns}" varStatus="status1">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status1.count}</td>
											<td><qp:formatText value="${sqlDesign.sqlDesignName}" /></td>
											<td><qp:formatText value="${sqlDesign.sqlDesignCode}" /></td>
											<td><qp:formatText value="${sqlDesign.moduleName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>