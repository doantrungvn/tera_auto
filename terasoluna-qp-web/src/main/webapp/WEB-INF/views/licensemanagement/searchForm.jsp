<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.licensemanagement"></qp:message></span></li>
		<li><span><qp:message code="sc.permission.licensemanagementSearch.remark" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<%-- 	<qp:authorization permission="licensemanagementRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/licensemanagement/register">
				<qp:message code="sc.permission.licensemanagementImport.remark" />
			</a>
		</qp:authorization> --%>
		<qp:authorization permission="licensemanagementRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a class="qp-link-popup" href="${pageContext.request.contextPath}/licensemanagement/register"><qp:message code="sc.permission.licensemanagementRegister.remark"/></a>
			
		</qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post"
			action="${pageContext.request.contextPath}/licensemanagement/search"
			modelAttribute="licenseManagementSearchForm">
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="20%">
							<col width="30%">
						</colgroup>
						<tbody>
							<tr>
								<th><form:label path="customerName"><qp:message code="sc.licensemanagement.0001" /></form:label></th>
								<td><form:input path="customerName" class="form-control qp-input-text" maxlength="400" /></td>
								<th><form:label path="customerCode"><qp:message code="sc.licensemanagement.0000" /></form:label></th>
								<td><form:input path="customerCode" class="form-control qp-input-text" maxlength="150" /></td>
							</tr>
							<tr>
								<th><form:label path="projectName"><qp:message code="sc.licensemanagement.0003" /></form:label></th>
								<td><form:input path="projectName" class="form-control qp-input-text" maxlength="400" /></td>
								<th><form:label path="projectCode"><qp:message code="sc.licensemanagement.0002" /></form:label></th>
								<td><form:input path="projectCode" class="form-control qp-input-text" maxlength="150" /></td>
							</tr>
							<tr>
								<th><form:label path="num"><qp:message code="sc.licensemanagement.0007" /></form:label></th>
								<td><form:input path="num" cssClass="form-control qp-input-integer" size="100"/></td>
								<th><form:label path="email"><qp:message code="sc.licensemanagement.0005" /></form:label></th>
								<td><form:input path="email" class="form-control qp-input-text" maxlength="50" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.licensemanagement.0009" /></th>
								<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromStartDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toStartDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								</td>
								<th><qp:message code="sc.licensemanagement.0010" /></th>
								<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromExpiredDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toExpiredDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.licensemanagement.0014" /></th>
								<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromAppliedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toAppliedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								</td>
								<th><form:label path="status"><qp:message code="sc.sys.0027" /></form:label></th>
								<td><form:checkboxes cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_LICENSEMANAGEMENT_STATUS}" path="status" /></td>
							</tr>
							
						</tbody>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="submit" class="btn qp-button">
					<qp:message code="sc.sys.0001" />
				</button>
			</div>
		</form:form>
		<c:if test="${page!=null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="licenseManagementSearchForm" action="/licensemanagement/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col width="5%">
									<col >
									<col width="15%">
									<col width="15%">
									<!-- <col width="15%"> -->
									<col width="6%">
									<col width="12%">
									<!-- <col width="8%"> -->
									<col width="10%">
									<col width="10%">
									<col width="8%">
									
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="customer_name" colCode="sc.licensemanagement.0001" form="licenseManagementSearchForm" /></th>
										<th><qp:columnSort colName="customer_code" colCode="sc.licensemanagement.0000" form="licenseManagementSearchForm" /></th>
										<th><qp:columnSort colName="project_name" colCode="sc.licensemanagement.0003" form="licenseManagementSearchForm" /></th>
										<%-- <th><qp:columnSort colName="project_code" colCode="sc.licensemanagement.0002" form="licenseManagementSearchForm" /></th> --%>
										<th><qp:columnSort colName="version" colCode="sc.licensemanagement.0008" form="licenseManagementSearchForm" /></th>
										<th><qp:columnSort colName="number_of_user" colCode="sc.licensemanagement.0007" form="licenseManagementSearchForm" /></th>
										<%-- <th><qp:columnSort colName="start_date" colCode="sc.licensemanagement.0009" form="licenseManagementSearchForm" /></th> --%>
										<th><qp:columnSort colName="applied_date" colCode="sc.licensemanagement.0014" form="licenseManagementSearchForm" /></th>
										<th><qp:columnSort colName="expired_date" colCode="sc.licensemanagement.0010" form="licenseManagementSearchForm" /></th>
										<th><qp:columnSort colName="status" colCode="sc.licensemanagement.0013" form="licenseManagementSearchForm" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="licensemanagement" items="${page.content }" varStatus="rowStatus">
										<tr class="form-inline">
											<td class="qp-output-fixlength"><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
											<td class="word-wrap">
												<qp:authorization permission="licensemanagementView" isDisplay="true" displayValue="${licensemanagement.customerName}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/licensemanagement/view?licenseId=${f:h(licensemanagement.licenseId)}&status=1"><qp:formatText value="${licensemanagement.customerName}" /></a>
												</qp:authorization>
											</td>
											<td class="qp-output-text"><qp:formatText value="${licensemanagement.customerCode}"/></td>
											<td class="qp-output-text"><qp:formatText value="${licensemanagement.projectName}"/></td>
											<%-- <td class="qp-output-text"><qp:formatText value="${licensemanagement.projectCode}"/></td> --%>
											<td class="qp-output-text"><qp:formatText value="${licensemanagement.version}"/></td>
											<td class="qp-output-text"><qp:formatText value="${licensemanagement.num}"/></td>
											<%-- <td class="qp-output-datetime"><qp:formatText value="${licensemanagement.startDate}"/></td> --%>
											<td class="qp-output-datetime"><qp:formatDateTime value="${licensemanagement.appliedDate}"/></td>
											<td class="qp-output-datetime"><qp:formatDateTime value="${licensemanagement.expiredDate}"/></td>
											<td class="qp-output-text"><qp:formatText value="${CL_LICENSEMANAGEMENT_STATUS.get(licensemanagement.status.toString())}"/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md"
															page="${page}"
															queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}"
															criteriaQuery="${f:query(licenseSearchForm)}"
															maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md"
															page="${page}"
															criteriaQuery="${f:query(licenseSearchForm)}"
															maxDisplayCount="5" />
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>