<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.subareadesign"></qp:message></span></li>
		<li><span><qp:message code="sc.subareadesign.0001"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
			<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
				<qp:authorization permission="subjectareaRegister">
					<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
					<a href="${pageContext.request.contextPath}/subjectarea/register">
						<qp:message code="sc.subareadesign.0018"></qp:message>
					</a>
				</qp:authorization>
			</c:if>
			<qp:authorization permission="graphicdatabasedesignSearch">
					<span class="qp-link-header-icon glyphicon glyphicon-picture"></span>
				<a href="${pageContext.request.contextPath}/graphicdatabasedesign/search?init"><qp:message code="tqp.graphicdatabasedeisgn" /></a>
			</qp:authorization>
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/subjectarea/css/style.css" type="text/css" rel="stylesheet" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/subjectarea/search" modelAttribute="subjectAreaSearchForm">
		<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span
						class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message></span>
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
							<th><form:label path="areaName"><qp:message code="sc.subareadesign.0004"/></form:label></th>
							<td><form:input path="areaName" class="form-control qp-input-text" /> </td>
							<th><form:label path="areaCode"><qp:message code="sc.subareadesign.0005"/></form:label></th>
							<td><form:input path="areaCode" class="form-control qp-input-text" /> </td>
						</tr>
						<tr>
							<th><form:label path="tableName"><qp:message code="sc.subareadesign.0006"/></form:label></th>
							<td><form:input path="tableName" class="form-control qp-input-text" /></td>
							<th><form:label path="tableCode"><qp:message code="sc.subareadesign.0007"/></form:label></th>
							<td><form:input path="tableCode" class="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><form:label path="keyword"><qp:message code="sc.subareadesign.0008"/></form:label></th>
							<td><form:input path="keyword" class="form-control qp-input-text" /> </td>
							<th></th>
							<td></td>
							<%-- <th><form:label path="projectId"><qp:message code="sc.subareadesign.0010"/></form:label></th>
							<td>
								<qp:autocomplete optionValue="optionValue" 
										selectSqlId="getAllProjectForAutocomplete" 
										emptyLabel="sc.sys.0030"
										name="projectId" 
										displayValue="${subjectAreaSearchForm.projectIdAutocomplete}" 
										value="${subjectAreaSearchForm.projectId}" 
										optionLabel="optionLabel">
								</qp:autocomplete>
							</td> --%>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				 <qp:authorization permission="subjectareaSearch">
					 <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001"/></button>
				 </qp:authorization>
			</div>
		</form:form>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="subjectAreaSearchForm" action="/subjectarea/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col />
								<col width="45%" />
								<col width="40%" />
								<!-- <col width="30%" /> -->
								<col width="15%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:columnSort colName="sub_area_name" colCode="sc.subareadesign.0004" form="subjectAreaSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="sub_area_code" colCode="sc.subareadesign.0005" form="subjectAreaSearchForm"></qp:columnSort></th>
									<%-- <th><qp:columnSort colName="project_name" colCode="sc.subareadesign.0010" form="subjectAreaSearchForm"></qp:columnSort></th> --%>
									<th><qp:columnSort colName="default_flg" colCode="sc.subareadesign.0009" form="subjectAreaSearchForm"></qp:columnSort></th>
									<th></th>
								</tr>
							</thead>
							<c:forEach var="area" items="${page.content}" varStatus="rowStatus">
								<tr>
									<td>${(page.number * page.size) + rowStatus.count}</td>
									<td>
										<qp:authorization permission="subjectareaView" isDisplay="true" displayValue="${area.areaName}">
											<a class="qp-link-popup" 
												href="${pageContext.request.contextPath}/subjectarea/view?areaId=${f:h(area.areaId)}">
												<qp:formatText value="${area.areaName}"/>
											</a>
										</qp:authorization>
									</td>
									<td class="qp-output-text"><qp:formatText value="${area.areaCode}"/></td>
									<%-- <td class="qp-output-text"><qp:formatText value="${area.projectName}"/></td> --%>
									<td class="qp-output-text">
										<qp:integerFormatYesNo value="${area.defaultFlg.toString()}" yesValue="1" />
									</td>
									<td>
										<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
											<qp:authorization permission="subjectareaModify">
												<a href="${pageContext.request.contextPath}/subjectarea/modify?areaId=${f:h(area.areaId)}&mode=0"
													class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.subareadesign.0019"/>"></a>
											</qp:authorization>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
						<qp:authorization permission="subjectareaSearch">
							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(subjectAreaSearchForm)}" maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(subjectAreaSearchForm)}" maxDisplayCount="5" />
									</c:otherwise>
								</c:choose>
							</div>
						</qp:authorization>
					</c:if>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>