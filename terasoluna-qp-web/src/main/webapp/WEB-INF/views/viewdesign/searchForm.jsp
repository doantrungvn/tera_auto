<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.viewdesign"></qp:message></span></li>
         <li><span><qp:message code="sc.viewdesign.0003"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="viewdesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/viewdesign/register"><qp:message code='sc.viewdesign.0004' /></a>
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/viewdesign/registerAdvanced"><qp:message code='sc.viewdesign.0005' /></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/viewdesign/search" modelAttribute="viewDesignSearchForm">
			<qp:ColumnSortHidden />
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text">
						<qp:message code="sc.sys.0002"></qp:message>
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
							<th><qp:message code='sc.viewdesign.0006' /></th>
							<td><form:input path="sqlDesignName" cssClass="form-control qp-input-text" /></td>
							<th><qp:message code='sc.viewdesign.0007' /></th>
							<td><form:input path="sqlDesignCode" cssClass="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><qp:message code='sc.sqldesign.0031' /></th>
							<td>
								<form:checkboxes cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_SQL_TYPE_VIEWDESIGN }" path="sqlDesignTypes" />
							</td>
							<th><qp:message code="sc.module.0007"></qp:message></th>
							<td><qp:autocomplete  
										selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
										name="moduleId"
										value="${viewDesignSearchForm.moduleId }"
										displayValue="${viewDesignSearchForm.moduleIdAutocomplete }"
										optionLabel="optionLabel"
										optionValue="optionValue"
										emptyLabel="Enter module here" 
										mustMatch="true"
										arg01="${sessionScope.CURRENT_PROJECT.projectId }"></qp:autocomplete></td>
						</tr>
						<tr>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label>
										<form:checkbox cssClass="qp-input-checkbox-margin qp-input-checkbox" path="designStatus" value="${item.key}" />&nbsp;<qp:message code="${CL_DESIGN_STATUS.get(item.key)}" />
									</label>
								</c:forEach>
							</td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="submit" class="btn qp-button">
					<qp:message code="sc.sys.0001"></qp:message>
				</button>
			</div>
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="viewDesignSearchForm" action="/viewdesign/search"></qp:itemPerPage>
				</div>
				<div class="panel-body">
					<c:if test="${page != null && page.totalPages gt 0 }">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col />
									<col width="20%" />
									<col />
									<col width="15%" />
									<col width="15%" />
									<col width="15%" />
									<col width="40px"/>
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="sql_design_name" form="viewDesignSearchForm" colCode="sc.viewdesign.0006"></qp:columnSort></th>
										<th><qp:columnSort colName="sql_design_code" form="viewDesignSearchForm" colCode="sc.viewdesign.0007"></qp:columnSort></th>
										<th><qp:columnSort colName="module_name" form="viewDesignSearchForm" colCode="sc.module.0007"></qp:columnSort></th>
										<th><qp:columnSort colName="design_type" form="viewDesignSearchForm" colCode="sc.sqldesign.0031"></qp:columnSort></th>
										<th><qp:columnSort colName="design_status" form="viewDesignSearchForm" colCode="sc.sys.0055"></qp:columnSort></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.content}" var="item" varStatus="sts">
										<tr class="form-inline">
											<td>${page.number * page.size + sts.index + 1}</td>
											<td class="com-output-text">
												<qp:authorization permission="viewdesignView" isDisplay="true" displayValue="${f:h(item.sqlDesignName) }">
													<a href="${pageContext.request.contextPath}/viewdesign/view?sqlDesignForm.sqlDesignId=${f:u(item.sqlDesignId)}" class="qp-link-popup">
														<qp:formatText value="${item.sqlDesignName}" />
													</a>
												</qp:authorization>
											</td>
											<td><qp:formatText value="${item.sqlDesignCode}"></qp:formatText></td>
											<td><qp:formatText value="${item.moduleName}"></qp:formatText></td>
											<td><qp:formatText value="${CL_SQL_TYPE_VIEWDESIGN.get(item.designType.toString()) }"></qp:formatText></td>
											<td><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString()) }"></qp:message></td>
											<td align="center">
												<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && item.designStatus eq '1' && (empty item.moduleId ||  item.moduleStatus eq '1')}">
													<qp:authorization permission="viewdesignModify">
														<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/viewdesign/modify?sqlDesignForm.sqlDesignId=${f:u(item.sqlDesignId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.viewdesign.0008"/>"></a>
													</qp:authorization>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="qp-div-action">
								<qp:authorization permission="viewdesignSearch">
									<c:choose>
										<c:when test="${page.sort != null }">
											<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(viewDesignSearchForm)}" maxDisplayCount="5" />
										</c:when>
										<c:otherwise>
											<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(viewDesignSearchForm)}" maxDisplayCount="5" />
										</c:otherwise>
									</c:choose>
								</qp:authorization>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>