<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.rolemanagement"></qp:message></span></li>
		 <li><span><qp:message code="sc.role.0008"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="roleRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/role/register"><qp:message code="sc.role.0015"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/role/search" modelAttribute="roleSearchForm">
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
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
							<th><form:label path="roleName"><qp:message code="sc.role.0005" /></form:label></th>
							<td><form:input path="roleName" cssClass="form-control qp-input-text" /></td>
							<th><form:label path="roleCd"><qp:message code="sc.role.0006" /></form:label></th>
							<td><form:input path="roleCd" cssClass="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><form:label path="status"><qp:message code="sc.sys.0027" /></form:label></th>
							<td><form:checkboxes cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_ROLE_STATUS}" path="status" /></td>
							<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
							
							<td><form:input path="remark" cssClass="form-control qp-input-text" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="roleSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		<c:if test="${page != null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="roleSearchForm" action="/role/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
                        <c:if test="${page != null && page.totalPages > 0 }">
    						<table class="table table-bordered qp-table-list">
    							<colgroup>
    								<col />
    								<col />
    								<col width="20%" />
    								<col width="10%" />
    								<col />
    								<col width="5%" />
    							</colgroup>
    							<thead>
    								<tr>
    									<th><qp:message code="sc.sys.0004" /></th>
    									<th><qp:columnSort colName="role_name" colCode="sc.role.0005" form="roleSearchForm"></qp:columnSort></th>
    									<th><qp:columnSort colName="role_code" colCode="sc.role.0006" form="roleSearchForm"></qp:columnSort></th>
    									<th><qp:columnSort colName="status" colCode="sc.sys.0027" form="roleSearchForm"></qp:columnSort></th>
    									<th><qp:columnSort colName="remark" colCode="sc.sys.0028" form="roleSearchForm"></qp:columnSort></th>
    									<th></th>
    								</tr>
    							</thead>
    							<c:forEach var="role" items="${page.content}" varStatus="sts">
    								<tr>
    									<td><qp:formatInteger value="${(page.number * page.size) + sts.count}" /></td>
    									<td class="word-wrap">
    										<qp:authorization permission="roleView" isDisplay="true" displayValue="${role.roleName}">
    											<a class="qp-link-popup" href="${pageContext.request.contextPath}/role/view?roleId=${f:h(role.roleId)}&mode=1"><qp:formatText value="${role.roleName}" /></a>
    										</qp:authorization>
    									</td>
    									<td class="word-wrap"><qp:formatText value="${role.roleCd}"/></td>
    									<td><qp:formatText value="${CL_ROLE_STATUS.get(role.status.toString())}"/></td>
    									<td class="word-wrap"><qp:formatText value="${role.remark}"/></td>
    									<td>
    										<qp:authorization permission="roleModify">
    											<a href="${pageContext.request.contextPath}/role/modify?roleId=${f:h(role.roleId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.role.0016"/>"></a>
    										</qp:authorization>
    									</td>
    								</tr>
    							</c:forEach>
    						</table>
    						<qp:authorization permission="roleSearch">
    							<div class="qp-div-action">
    									<c:choose>
    										<c:when test="${page.sort != null }">
    											<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(roleSearchForm)}" maxDisplayCount="5" />
    										</c:when>
    										<c:otherwise>
    											<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(roleSearchForm)}" maxDisplayCount="5" />
    										</c:otherwise>
    									</c:choose>
    							</div>
    						</qp:authorization>
                        </c:if>
					</div>
				</div>
			</div>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>
