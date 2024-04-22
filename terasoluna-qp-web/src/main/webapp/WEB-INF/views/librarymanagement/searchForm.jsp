<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">	
			var CL_LIBRARY_SCOPE = {};
			<c:forEach items="${CL_LIBRARY_SCOPE}" var="item">
				CL_LIBRARY_SCOPE['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_LIBRARY_TYPE= {};
			<c:forEach items="${CL_LIBRARY_TYPE}" var="item">
				CL_LIBRARY_TYPE['${item.key}'] = '${item.value}';
			</c:forEach>	
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/librarymanagement/javascript/common.js"></script>
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.librarymanagement"></qp:message></span></li>
         <li><span><qp:message code="sc.librarymanagement.0002"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="librarymanagementRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a class="com-link-popup" href="${pageContext.request.contextPath}/librarymanagement/register"><qp:message code="sc.librarymanagement.0003"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post"
			modelAttribute="libraryManagementSearchForm"
			action="${pageContext.request.contextPath}/librarymanagement/search">
			<div class="panel panel-default qp-div-search-condition">
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>"
						element="div" cssStyle="" />
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message>
					</span>
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
							<th><qp:message code="sc.librarymanagement.0005"/></th>
							<td><form:input path="groupId"
									cssClass="form-control qp-input-text" /></td>
							<th><qp:message code="sc.librarymanagement.0006"/></th>
							<td><form:input path="artifactId"
									cssClass="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.librarymanagement.0009"/></th>
							<td>
								<qp:autocomplete mustMatch="false" name="scope" displayValue="${libraryManagementSearchForm.scope}" value="${libraryManagementSearchForm.scope}"
									optionValue="optionValue" optionLabel="optionLabel" sourceCallback="loadLibraryScopeDefault" sourceType="local">
								</qp:autocomplete>
							</td>
							<th><qp:message code="sc.librarymanagement.0010"/></th>
							<td>
								<qp:autocomplete mustMatch="false" name="type" displayValue="${libraryManagementSearchForm.type}" value="${libraryManagementSearchForm.type}"
									optionValue="optionValue" optionLabel="optionLabel" sourceCallback="loadLibraryTypeDefault" sourceType="local">
								</qp:autocomplete>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<input type="button" value="Search"
					class="btn qp-button qp-button-search" />
			</div>
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="libraryManagementSearchForm" action="/librarymanagement/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list"
							id="businessTypesTable">
							<colgroup>
								<col />
								<col width="20%" />
								<col width="20%" />
								<col width="20%" />
								<col width="20%" />
								<col width="20%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:columnSort colName="artifact_id" colCode="sc.librarymanagement.0006" form="libraryManagementSearchForm" /></th>
									<th><qp:columnSort colName="group_id" colCode="sc.librarymanagement.0005" form="libraryManagementSearchForm" /></th>
									<th><qp:columnSort colName="scope" colCode="sc.librarymanagement.0009" form="libraryManagementSearchForm" /></th>
									<th><qp:columnSort colName="version" colCode="sc.librarymanagement.0007" form="libraryManagementSearchForm" /></th>
									<th><qp:columnSort colName="type" colCode="sc.librarymanagement.0010" form="libraryManagementSearchForm" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
 								<c:forEach var="item" items="${page.content}" varStatus="status"> 
									<tr> 
 										<td><qp:formatInteger value="${page.number * page.size + status.count}" /></td> 
										<td class="word-wrap">
											<qp:authorization permission="librarymanagementView" isDisplay="true" displayValue="${item.artifactId}">
											<a class="qp-link-popup"  href="${pageContext.request.contextPath}/librarymanagement/view?libraryId=${item.libraryId}">
												<qp:formatText value="${item.artifactId}" />
											</a>
											</qp:authorization>
										</td>
										<td class="word-wrap"><qp:formatText value="${item.groupId}" /></td>
										<td class="word-wrap"><qp:formatText value="${item.scope}" /></td>
										<td class="word-wrap"><qp:formatText value="${item.version}" /></td>
										<td class="word-wrap"><qp:formatText value="${item.type}" /></td>
										<td>
											<qp:authorization permission="librarymanagementModify">
												<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action"
													href="${pageContext.request.contextPath}/librarymanagement/modify?libraryId=${item.libraryId}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.librarymanagement.0002"/>">
												</a>
											</qp:authorization>
										</td>
 									</tr>
 								</c:forEach>
							</tbody>
						</table>
					</c:if>
						<qp:authorization permission="librarymanagementSearch">
						<div class="qp-div-action">
							<c:choose>
								<c:when test="${page.sort != null }">
									<t:pagination outerElementClass="pagination pagination-md"
										page="${page}"
										queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}"
										maxDisplayCount="5" />
								</c:when>
								<c:otherwise>
									<t:pagination outerElementClass="pagination pagination-md"
										page="${page}"
										maxDisplayCount="5" />
								</c:otherwise>
							</c:choose>
						</div>
						</qp:authorization>
					</div>
				</div>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
