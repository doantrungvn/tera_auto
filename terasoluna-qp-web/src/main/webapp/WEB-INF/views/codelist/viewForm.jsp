<tiles:insertDefinition name="layouts-popup">

	<tiles:putAttribute name="header-name">
		<qp:message code="sc.codelist.0018"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link"></tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
	</tiles:putAttribute>

<c:if test="${notExistFlg ne 1}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/codelist/delete" modelAttribute="codeListForm">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.codelist.0006"></qp:message></span>
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
							<th><qp:message code="sc.codelist.0003"></qp:message></th>
							<td><form:label path="codeListName" /><qp:formatText value="${codeListForm.codeListName}"></qp:formatText></td>
							<th><qp:message code="sc.codelist.0002"></qp:message></th>
							<td><form:label path="codeListCode" /><qp:formatText value="${codeListForm.codeListCode}"></qp:formatText></td>
						</tr>
						<tr>
							<%-- <th><qp:message code="sc.project.0005"></qp:message></th>
							<td><qp:formatText value="${codeListForm.projectIdAutocomplete}"/>
									<form:hidden path="projectIdAutocomplete"/>
							</td> --%>
							<th><qp:message code="sc.tabledesign.0053"></qp:message></th>
							<td><c:if test="${codeListForm.isOptionValude eq 1 }"><qp:message code="sc.sys.0011" /></c:if>
								<c:if test="${codeListForm.isOptionValude ne 1 }"><qp:message code="sc.sys.0012" /></c:if>
							</td>
							<th><qp:message code="sc.module.0007"></qp:message></th>
							<td>
								<qp:formatText value="${codeListForm.moduleIdAutocomplete}"/>
								<form:hidden path="moduleIdAutocomplete"/>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0028"></qp:message></th>
							<td><form:label path="remark" /><qp:formatRemark value="${codeListForm.remark}" /></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<c:if test="${codeListForm.openOwner == 1 && 1 eq sessionScope.CURRENT_PROJECT.status && moduleStatus eq 1}">
						<c:if test="${hasFkFlg ne 1}">
							<qp:authorization permission="codelistDelete">
								<form:hidden path="codeListId" />
								<form:hidden path="updatedDate" />
								<button type="button" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014">
									<qp:message code="sc.sys.0008" />
								</button>
							</qp:authorization>
						</c:if>
					<qp:authorization permission="codelistModify">
						<a href="${pageContext.request.contextPath}/codelist/modify?codeListId=${codeListForm.codeListId}&mode=1"
							class="btn qp-button qp-link-button qp-link-popup-navigate"
							type="submit"><qp:message code="sc.sys.0006" /></a>
					</qp:authorization>
				</c:if>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.codelist.0007" /></span>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:set var="numOfColumn" value="2" />
						<table class="table table-bordered qp-table-list-none-action" id="tbl_list_result">
							<colgroup>
								<col>
								<c:if test="${codeListForm.isOptionValude ne 1 }">
									<col>
									<c:set var="numOfColumn" value="${numOfColumn + 1}" />
								</c:if>
								<col>
								<c:if test="${codeListForm.multivalueFlg eq 1 }">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<c:set var="numOfColumn" value="${numOfColumn + 5}" />
								</c:if>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<c:if test="${codeListForm.isOptionValude ne 1 }">
										<th><qp:message code="sc.codelist.0008"/></th>
									</c:if>
									<th><qp:message code="sc.codelist.0009"/></th>
									<c:if test="${codeListForm.multivalueFlg eq 1 }">
										<th><qp:message code="sc.codelist.0011"/></th>
										<th><qp:message code="sc.codelist.0012"/></th>
										<th><qp:message code="sc.codelist.0013"/></th>
										<th><qp:message code="sc.codelist.0014"/></th>
										<th><qp:message code="sc.codelist.0015"/></th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="codeListDetails" items="${codeListForm.codeListDetails}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">${status.count}</td>
										<c:if test="${codeListForm.isOptionValude ne 1 }">
											<td><qp:formatText value="${codeListDetails.name}" /></td>
										</c:if>
										<td><qp:formatText value="${codeListDetails.value}" /></td>
										<c:if test="${codeListForm.multivalueFlg eq 1 }">
											<td><qp:formatText value="${codeListDetails.value1}" /></td>
											<td><qp:formatText value="${codeListDetails.value2}" /></td>
											<td><qp:formatText value="${codeListDetails.value3}" /></td>
											<td><qp:formatText value="${codeListDetails.value4}" /></td>
											<td><qp:formatText value="${codeListDetails.value5}" /></td>
										</c:if>
									</tr>
								</c:forEach>
									<c:if test="${empty codeListForm.codeListDetails}">
							<tr>
								<td colspan="${numOfColumn}"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<c:if test="${hasFkFlg eq 1}">
			<c:if test="${!empty codeListForm.listDomainDesign}">
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.codelist.0019" /></span>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-bordered qp-table-list-none-action">
							<colgroup>
									<col width="10%">
									<col width="45%">
									<col width="45%">
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"/></th>
									<th><qp:message code="sc.domaindesign.0001"/></th>
									<th><qp:message code="sc.domaindesign.0002"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="listDomainDesign" items="${codeListForm.listDomainDesign}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">${status.count}</td>
										<td><qp:formatText value="${listDomainDesign.optionLabel}" /></td>
										<td><qp:formatText value="${listDomainDesign.optionValue}" /></td>
										<td></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${!empty codeListForm.listTableDesignItems}">
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.codelist.0020" /></span>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-bordered qp-table-list-none-action">
							<colgroup>
									<col width="10%">
									<col width="30%">
									<col width="30%">
									<col width="30%">
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"/></th>
									<th><qp:message code="sc.tabledesign.0006"/></th>
									<th><qp:message code="sc.tabledesign.0012"/></th>
									<th><qp:message code="sc.tabledesign.0001"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="listTableDesignItems" items="${codeListForm.listTableDesignItems}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">${status.count}</td>
										<td><qp:formatText value="${listTableDesignItems.optionLabel}" /></td>
										<td><qp:formatText value="${listTableDesignItems.optionValue}" /></td>
										<td><qp:formatText value="${listTableDesignItems.output01}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${!empty codeListForm.listScreenItem}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.codelist.0021" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
										<col width="10%">
										<col width="30%">
										<col width="30%">
										<col width="30%">
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"/></th>
										<th><qp:message code="sc.screendesign.0194"/></th>
										<th><qp:message code="sc.screendesign.0093"/></th>
										<th><qp:message code="sc.screendesign.0005"/></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="listScreenItem" items="${codeListForm.listScreenItem}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${listScreenItem.optionLabel}" /></td>
											<td><qp:formatText value="${listScreenItem.optionValue}" /></td>
											<td><qp:formatText value="${listScreenItem.output01}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>