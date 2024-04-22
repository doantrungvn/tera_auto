<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.businesstype"></qp:message></span></li>
		 <li><span><qp:message code="sc.businesstype.0006"/></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businesstype/businesstype.js"></script>
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="businesstypeRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/businesstype/register"><qp:message code="sc.businesstype.0007"/></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post" role="form" action="${pageContext.request.contextPath}/businesstype/search" modelAttribute="businessTypeSearchForm">
			<qp:ColumnSortHidden/>
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
							<th><form:label path="businessTypeName"><qp:message code="sc.businesstype.0001" /></form:label></th>
							<td><form:input path="businessTypeName" cssClass="form-control qp-input-text" maxlength="200" /></td>
							<th><form:label path="businessTypeCode"><qp:message code="sc.businesstype.0002" /></form:label></th>
							<td><form:input path="businessTypeCode" cssClass="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
							<%-- <th><form:label path="projectId"><qp:message code="sc.project.0005" /></form:label></th>
							<td><qp:autocomplete name="projectId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllProjectForAutocomplete" value="${businessTypeSearchForm.projectId}" displayValue="${businessTypeSearchForm.projectIdAutocomplete}" onChangeEvent="changeProjectAC" mustMatch="true" maxlength="200" ></qp:autocomplete></td> --%>
							<th><form:label path="moduleId"><qp:message code="sc.module.0007" /></form:label></th>
							<td>
								<qp:autocomplete 
										name="moduleId" 
										optionValue="optionValue" 
										optionLabel="optionLabel" 
										selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" 
										value="${businessTypeSearchForm.moduleId}" 
										displayValue="${businessTypeSearchForm.moduleIdAutocomplete}" 
										arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
										mustMatch="true" 
										maxlength="200">
								</qp:autocomplete>
							</td>
						<!-- </tr>
						<tr> -->
							<th><form:label path="parentBusinessTypeId"><qp:message code="sc.businesstype.0003" /></form:label></th>
							<td style="border-right: none"><jsp:include page="parentBusinessTypeTree.jsp" /></td>
							<!-- <td colspan="2" style="border-left: none"></td> -->
						</tr>
					</table>
				</div>
			</div>
			<!-- Style for button submit -->
			<div class="qp-div-action">
				<qp:authorization permission="businesstypeSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>

		</form:form>
			<c:if test="${page != null}">
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<qp:itemPerPage form="businessTypeSearchForm" action="/businesstype/search" />
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<c:if test="${page != null && page.totalPages > 0 }">
								<table class="table table-bordered qp-table-list">
									<colgroup>
										<col />
										<col width="40%" />
										<col width="20%" />
										<col width="40%" />
										<col />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:columnSort colName="business_type_name" colCode="sc.businesstype.0001" form="businessTypeSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="business_type_code" colCode="sc.businesstype.0002" form="businessTypeSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="parent_business_type_id" colCode="sc.businesstype.0003" form="businessTypeSearchForm"></qp:columnSort></th>
											<th></th>
										</tr>
									</thead>
									<c:forEach var="item" items="${page.content}" varStatus="status">
										<tr>
											<td><qp:formatInteger value="${page.number * page.size + status.count}" /></td>
											<td class="word-wrap">
												<qp:authorization permission="businesstypeView" isDisplay="true" displayValue="${item.businessTypeName}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/businesstype/view?businessTypeId=${item.businessTypeId}"><qp:formatText value="${item.businessTypeName}" /></a>
												</qp:authorization>
											</td>
											<td class="word-wrap"><qp:formatText value="${item.businessTypeCode}" /></td>
											<td class="word-wrap"><qp:formatText value="${item.parentBusinessTypeName}" /></td>
											<td>
												<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
													<qp:authorization permission="businesstypeModify">
														<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/businesstype/modify?businessTypeId=${item.businessTypeId}&mode=0" style="margin: auto" data-toggle="tooltip" title="<qp:message code="sc.businesstype.0008"/>"></a>
													</qp:authorization>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</table>
								<qp:authorization permission="businesstypeSearch">
									<div class="qp-div-action">
										<c:choose>
											<c:when test="${page.sort != null }">
												<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(businessTypeSearchForm)}" maxDisplayCount="5" />
											</c:when>
											<c:otherwise>
												<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(businessTypeSearchForm)}" maxDisplayCount="5" />
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
