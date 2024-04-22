<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.problemlist"></qp:message></span></li>
         <li><span><qp:message code="sc.problemlist.0000"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/problemlist/search" modelAttribute="problemListSearchForm">
		<qp:ColumnSortHidden />
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col />
						</colgroup>
						<tr>
							<th><form:label path="problemName"><qp:message code="sc.problemlist.0001"/></form:label></th>
							<td><form:input path="problemName" class="form-control qp-input-text" maxlength="50" /></td>
							<th><form:label path="problemType"><qp:message code="sc.problemlist.0003"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_PROBLEM_TYPE}">
									<label><form:checkbox path="problemType" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_PROBLEM_TYPE.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th><form:label path="resourceType"><qp:message code="sc.problemlist.0002"/></form:label></th>
							<td colspan="3">
								<c:forEach var="item" items="${CL_RESOURCE_TYPE}">
									<label><form:checkbox path="resourceType" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_RESOURCE_TYPE.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.module.0007" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" 
									value="${problemListSearchForm.moduleId}" name="moduleId" displayValue="${problemListSearchForm.moduleIdAutocomplete}" 
									arg01="${problemListSearchForm.projectId}" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0078" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllAccountForAutocomplete" 
									value="${problemListSearchForm.createdBy}" name="createdBy" displayValue="${problemListSearchForm.createdByAutocomplete}" 
									arg02="20" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th><form:label path="fromCreatedDate"><qp:message code="sc.sys.0079"/></form:label></th>
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
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="problemlistSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		
		<c:if test="${page!=null}">
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="problemListSearchForm" action="/problemlist/search" />
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col width="5%"/>
								<col />
								<col width="12%" />
								<col width="20%"/>
								<col width="10%"/>
								<col width="8%"/>
								<col width="9%"/>
								<col width="40px"/>
								<col width="40px"/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:columnSort colName="problem_name" colCode="sc.problemlist.0001" form="problemListSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="resource_type" colCode="sc.problemlist.0002" form="problemListSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="resource_name" colCode="sc.problemlist.0004" form="problemListSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="problem_type" colCode="sc.problemlist.0003" form="problemListSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="created_by" colCode="sc.problemlist.0013" form="problemListSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="created_date" colCode="sc.problemlist.0014" form="problemListSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="autofix_flg" colCode="sc.problemlist.0011" form="problemListSearchForm"></qp:columnSort></th>
									<th></th>
								</tr>
							</thead>
							<c:forEach var="item" items="${page.content}" varStatus="rowStatus">
								<tr>
									<td><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
									<td><qp:message code="${f:h(item.problemName)}"/></td>
									<td><qp:message code="${CL_RESOURCE_TYPE.get(item.resourceType.toString())}"></qp:message></td>
									<td>
										<qp:authorization permission="problemlistView" isDisplay="true" displayValue="${item.resourceName}">
											<a href="${pageContext.request.contextPath}/${CL_PROBLEM_RESOURCE_URL.get(item.urlId.toString())}${item.resourceId}&mode=1"><qp:formatText value="${item.resourceName}" /></a>
										</qp:authorization>	
									</td>
									<td><qp:message code="${CL_PROBLEM_TYPE.get(item.problemType.toString())}"></qp:message></td>
									<td><qp:formatText value="${item.createdByName}" /></td>
									<td align="center"><qp:formatDateTime value="${item.createdDate}" /></td>
									<td align="center">
										<c:if test="${item.autofixFlg eq 1 || empty item.resourceName}">
											<qp:authorization permission="problemlistAutofix">
												<a href="${pageContext.request.contextPath}/problemlist/autofix?problemId=${f:h(item.problemId)}&mode=0" class="btn qp-button glyphicon glyphicon-wrench  qp-link-button qp-link-action"></a>
											</qp:authorization>
										</c:if>
									</td>
									<td>
										<a href="${pageContext.request.contextPath}/problemlist/delete?problemId=${f:h(item.problemId)}" class="btn qp-button glyphicon glyphicon-remove  qp-link-button qp-link-action"></a>
									</td>
								</tr>
							</c:forEach>
						</table>
						<qp:authorization permission="problemlistSearch">
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
	</tiles:putAttribute>
</tiles:insertDefinition>