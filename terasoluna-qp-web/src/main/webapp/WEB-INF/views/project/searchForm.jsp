<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.project"></qp:message></span></li>
         <li><span><qp:message code="sc.project.0008"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="projectRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/project/register"><qp:message code="sc.project.0013"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/project/search" modelAttribute="projectSearchForm">
		<qp:ColumnSortHidden />
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="25%" />
							<col width="20%" />
							<col width="35%" />
						</colgroup>
						<tr>
							<th><form:label path="projectName"><qp:message code="sc.project.0005" /></form:label></th>
							<td><form:input path="projectName" class="form-control qp-input-text" maxlength="200" /></td>
							<th><form:label path="projectCode"><qp:message code="sc.project.0006" /></form:label></th>
							<td><form:input path="projectCode" class="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="statuses"><qp:message code="sc.sys.0055" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label><form:checkbox path="statuses" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><qp:message code="${CL_DESIGN_STATUS.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th></th>
							<td></td>
						</tr>
						
						<tr>
							<th><qp:message code="sc.sys.0078" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllAccountForAutocomplete" 
									value="${projectSearchForm.createdBy}" name="createdBy" displayValue="${projectSearchForm.createdByAutocomplete}" 
									arg02="20" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th><form:label path="fromCreatedDate"><qp:message code="sc.sys.0079" /></form:label></th>
							<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromCreatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toCreatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
						</tr>
						
						<tr>
						
							<th><qp:message code="sc.sys.0080" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllAccountForAutocomplete" 
									value="${projectSearchForm.updatedBy}" name="updatedBy" displayValue="${projectSearchForm.updatedByAutocomplete}" 
									arg02="20" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th><qp:message code="sc.sys.0081" /></th>
							<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromUpdatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toUpdatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="projectSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		<c:if test="${page!=null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="projectSearchForm" action="/project/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col />
									<col  />
									<col width="30%" />
									<col width="10%" />
									<col width="10%" />
									<col width="10%" />
									<col width="7%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:columnSort colName="project_name" colCode="sc.project.0005" form="projectSearchForm"></qp:columnSort></th>
										<th><qp:columnSort colName="project_code" colCode="sc.project.0006" form="projectSearchForm"></qp:columnSort></th>
										<th><qp:columnSort colName="status" colCode="sc.sys.0055" form="projectSearchForm"></qp:columnSort></th>
										<th><qp:columnSort colName="updated_by" colCode="sc.sys.0080" form="projectSearchForm"></qp:columnSort></th>
										<th><qp:columnSort colName="updated_date" colCode="sc.sys.0081" form="projectSearchForm"></qp:columnSort></th>
										<th></th>
									</tr>
								</thead>
								<c:forEach var="project" items="${page.content}" varStatus="rowStatus">
									<tr>
										<td class="qp-output-fixlength"><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
										<td class="qp-output-text word-wrap">
											<qp:authorization permission="projectView" isDisplay="true" displayValue="${project.projectName}">
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/project/view?projectId=${f:h(project.projectId)}&openOwner=1&mode=1"><qp:formatText value="${project.projectName}" /></a>
											</qp:authorization>
										</td>
										<td class="qp-output-text word-wrap"><qp:formatText value="${project.projectCode}"/></td>
										<td class="qp-output-text word-wrap"><qp:message code="${CL_DESIGN_STATUS.get(project.status.toString())}"/></td>
										<td class="qp-output-text word-wrap"><qp:message code="${project.updatedByName}"/></td>
										<td class="qp-output-datetime word-wrap"><qp:formatDateTime value="${project.updatedDate}" /></td>
										<td class="qp-table-list-action-field">
											<c:if test="${project.status eq 1 }">
												<qp:authorization permission="projectSetting">
													<a href="${pageContext.request.contextPath}/styledesign/view?projectId=${f:h(project.projectId)}" class="btn qp-button glyphicon glyphicon-cog qp-link-button qp-button-popup" data-toggle="tooltip" title="<qp:message code="sc.permission.projectSetting.remark"/>"></a>
												</qp:authorization>
												<qp:authorization permission="projectModify">
													<a href="${pageContext.request.contextPath}/project/modify?projectId=${f:h(project.projectId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.project.0014"/>"></a>
												</qp:authorization>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</table>
							<qp:authorization permission="projectSearch">
								<div class="qp-div-action">
									<c:choose>
										<c:when test="${page.sort != null }">
											<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(projectSearchForm)}" maxDisplayCount="5" />
										</c:when>
										<c:otherwise>
											<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(projectSearchForm)}" maxDisplayCount="5" />
										</c:otherwise>
									</c:choose>
								</div>
							</qp:authorization>
						</c:if>
					</div>
				</div>
			</div>
		</c:if>
		<div></div>
	</tiles:putAttribute>
</tiles:insertDefinition>