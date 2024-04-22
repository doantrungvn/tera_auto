<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.account"></qp:message></span></li>
         <li><span><qp:message code="sc.account.0008"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/account/register">
				<qp:message code="sc.account.0015"/>
			</a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/account/search" modelAttribute="accountSearchForm">
<%-- 			<qp:ColumnSortHidden/> --%>
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
							<th><form:label path="username"><qp:message code="sc.account.0002"/></form:label></th>
							<td><form:input path="username" cssClass="form-control qp-input-text" autofocus="true" /></td>
							<th><form:label path="accountNonLocked"><qp:message code="sc.account.0006"/></form:label></th>
							<td>
							<c:forEach var="item" items="${CL_SUPPORT_OPTION_VALUE_FLAG}">
								<label><form:checkbox path="accountNonLocked" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_SUPPORT_OPTION_VALUE_FLAG.get(item.key)}" /></label>
							</c:forEach>
							</td>
						</tr>
						<%-- <tr>
							<th><form:label path="accountNonExpired"><qp:message code="sc.account.0005"/></form:label></th>
							<td><form:checkboxes path="accountNonExpired" cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_TRUE_FALSE}"  /></td>
							<th><form:label path="credentialsNonExpired"><qp:message code="sc.account.0007"/></form:label></th>
							<td><form:checkboxes path="credentialsNonExpired" cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_TRUE_FALSE}"  /></td>
						</tr> --%>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001"></qp:message></button>
			</div>
		</form:form>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="accountSearchForm" action="/account/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col />
								<col />
								<col width="15%" />
								<col width="9%" />
								<col width="9%" />
								<col width="9%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:columnSort colName="username" colCode="sc.account.0002" form="accountSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="account_non_locked" colCode="sc.account.0006" form="accountSearchForm"></qp:columnSort></th>
									<th><qp:message code="sc.accountrolepermission.0012"/></th>
									<th><qp:message code="sc.accountrolepermission.0013"/></th>
									<th><qp:message code="sc.tqp.0011"/></th>
									<th></th>
								</tr>
							</thead>
							<c:forEach var="account" items="${page.content }" varStatus="look">
								<tr>
									<td>${(page.number * page.size) + look.count}</td>
									<td>
										<qp:authorization permission="accountView" isDisplay="true" displayValue="${account.username}">
											<a class="qp-link-popup" href="${pageContext.request.contextPath}/account/view?accountId=${f:h(account.accountId)}&mode=1"><qp:formatText value="${account.username}"/> </a>
										</qp:authorization>
									</td>
									<td class="qp-output-text"><qp:booleanFormatYesNo value="${account.accountNonLocked}" /></td>
									<td align="center">
										<qp:authorization permission="accountroleModify">
											<a class="btn qp-button glyphicon glyphicon-lock qp-link-button qp-link-action" href="${pageContext.request.contextPath}/accountrole/view?accountId=${f:h(account.accountId)}" data-toggle="tooltip" title="<qp:message code="sc.accountrolepermission.0020"/>"> </a>
										</qp:authorization>
									</td>
									<td align="center">
										<qp:authorization permission="accountpermissionModify">
											<a class="btn qp-button glyphicon glyphicon-lock qp-link-button qp-link-action" href="${pageContext.request.contextPath}/accountpermission/view?accountId=${f:h(account.accountId)}" data-toggle="tooltip" title="<qp:message code="sc.accountrolepermission.0025"/>"> </a>
										</qp:authorization>
									</td>
									<td align="center">
										<a class="btn qp-button glyphicon glyphicon-lock qp-link-button qp-link-action" href="${pageContext.request.contextPath}/accountproject/view?accountId=${f:h(account.accountId)}" data-toggle="tooltip" title="<qp:message code="sc.accountproject.0001"/>"> </a>
									</td>
									<td>
										<qp:authorization permission="accountModify">
											<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/account/modify?accountId=${f:h(account.accountId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.account.0016"/>"> </a>
										</qp:authorization> 
									</td>
								</tr>
							</c:forEach>
						</table>
						<div class="qp-div-action">
							<c:choose>
								<c:when test="${page.sort != null }">
									<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(accountSearchForm)}" maxDisplayCount="5" />
								</c:when>
								<c:otherwise>
									<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(accountSearchForm)}" maxDisplayCount="5" />
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>