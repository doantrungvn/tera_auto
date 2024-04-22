<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.accountruledefinition"></qp:message></span></li>
         <li><span><qp:message code="sc.accountruledefinition.0002"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountruledefinitionRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/accountruledefinition/register">
				<qp:message code="sc.accountruledefinition.0003"/>
			</a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/accountruledefinition/search" modelAttribute="accountRuleDefinitionSearchForm">
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message></span>
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
							<th><form:label path="accountRuleDefinitionName"><qp:message code="sc.accountruledefinition.0005"/></form:label></th>
							<td><form:input path="accountRuleDefinitionName" cssClass="form-control qp-input-text" autofocus="true" /></td>
							<th><form:label path="accountRuleDefinitionCode"><qp:message code="sc.accountruledefinition.0004"/></form:label></th>
							<td><form:input path="accountRuleDefinitionCode" cssClass="form-control qp-input-text" autofocus="true" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="accountruledefinitionSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001"></qp:message></button>
				</qp:authorization>
			</div>
		</form:form>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="accountRuleDefinitionSearchForm" action="/accountruledefinition/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col />
								<col width="60%" />
								<col width="30%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:columnSort colName="account_rule_definition_name" colCode="sc.accountruledefinition.0005" form="accountRuleDefinitionSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="account_rule_definition_code" colCode="sc.accountruledefinition.0004" form="accountRuleDefinitionSearchForm"></qp:columnSort></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="accountRuleDefinition" items="${page.content}" varStatus="look">
									<tr>
										<td>${(page.number * page.size) + look.count}</td>
										<td class="qp-output-text">
											<qp:authorization permission="accountruledefinitionView" isDisplay="true" displayValue="${accountRuleDefinition.accountRuleDefinitionName}">
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/accountruledefinition/view?accountRuleDefinitionId=${f:h(accountRuleDefinition.accountRuleDefinitionId)}"><qp:formatText value="${accountRuleDefinition.accountRuleDefinitionName}"/> </a>
											</qp:authorization>
										</td>
										<td class="qp-output-text">${accountRuleDefinition.accountRuleDefinitionCode}</td>
										<td>
											<qp:authorization permission="accountruledefinitionModify">
												<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/accountruledefinition/modify?accountRuleDefinitionId=${f:h(accountRuleDefinition.accountRuleDefinitionId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.accountruledefinition.0036"/>"> </a>
											</qp:authorization> 
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="qp-div-action">
							<c:choose>
								<c:when test="${page.sort != null }">
									<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(accountRuleDefinitionSearchForm)}" maxDisplayCount="5" />
								</c:when>
								<c:otherwise>
									<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(accountRuleDefinitionSearchForm)}" maxDisplayCount="5" />
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>