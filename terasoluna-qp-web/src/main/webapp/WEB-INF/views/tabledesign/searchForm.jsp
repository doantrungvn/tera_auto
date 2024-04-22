<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.tabledesign"></qp:message></span></li>
		 <li><span><qp:message code="sc.tabledesign.0069"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/tabledesign.js"></script>
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="tabledesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/tabledesign/register"><qp:message code="sc.tabledesign.0068"/></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/tabledesign/search" modelAttribute="tableDesignSearchForm">
		<input type="hidden" name="backJson"/>
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"> <qp:message code="sc.sys.0002"/></span>
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
							<th><form:label path="tableName"><qp:message code="sc.tabledesign.0019"/></form:label></th>
							<td><form:input path="tableName" cssClass="form-control qp-input-text" maxlength="200" />
							<th><form:label path="tableCode"><qp:message code="sc.tabledesign.0020"/></form:label></th>
							<td><form:input path="tableCode" cssClass="form-control qp-input-text" maxlength="25" />
						</tr>
						<tr>
							<th><form:label path="status"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label><form:checkbox path="designStatus" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_DESIGN_STATUS.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th><form:label path="subjectAreaId"><qp:message code="sc.subareadesign.0004" /></form:label></th>
							<td>
								<qp:autocomplete 
									name="subjectAreaId" 
									optionValue="optionValue" 
									optionLabel="optionLabel" 
									selectSqlId="getAllSubjectAreaByProjectIdForAutocomplete" 
									value="${tableDesignSearchForm.subjectAreaId}" 
									displayValue="${tableDesignSearchForm.subjectAreaIdAutocomplete}" 
									arg01="${tableDesignSearchForm.projectId}" 
									mustMatch="true" maxlength="200">
								</qp:autocomplete>
							</td>
						</tr>
						<tr>
							<th><form:label path="typeTable"><qp:message code="sc.sys.0059"></qp:message></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_TABLE_TYPE_ALL}">
									<label><form:checkbox path="types" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_TABLE_TYPE_ALL.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="tabledesignSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="tableDesignSearchForm" action="/tabledesign/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col />
								<col />
								<col width="25%" />
								<col width="15%" />
								<col width="15%" />
								<col width="3%" />
								<col width="3%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:columnSort colName="tbl_design_name" colCode="sc.tabledesign.0019" form="tableDesignSearchForm" /><input type="hidden" name="designStatus" value="${item.designStatus}" /></th>
									<th><qp:columnSort colName="tbl_design_code" colCode="sc.tabledesign.0020" form="tableDesignSearchForm" /></th>
									<th><qp:columnSort colName="type" colCode="sc.sys.0059" form="tableDesignSearchForm" /></th>
									<th><qp:columnSort colName="design_status" colCode="sc.sys.0055" form="tableDesignSearchForm" /></th>
									<th colspan="2"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${page.content }" varStatus="look">
									<tr>
										<td>${(page.number * page.size) + look.count}</td>
										<td>
											<%-- <c:choose>
												<c:when test="${item.type eq 2 }">
													<qp:formatText value="${item.tableName}" />	
												</c:when>
												<c:otherwise>														
													<qp:authorization permission="tabledesignView" isDisplay="true" displayValue="${item.tableName}">
														<a href="${pageContext.request.contextPath}/tabledesign/view?tableDesignId=${item.tableDesignId}&mode=1" class="qp-link-popup"><qp:formatText value="${item.tableName}" /></a>
													</qp:authorization>
												</c:otherwise>
											</c:choose> --%>
											<qp:authorization permission="tabledesignView" isDisplay="true" displayValue="${item.tableName}">
												<a href="${pageContext.request.contextPath}/tabledesign/view?tableDesignId=${item.tableDesignId}&mode=1" class="qp-link-popup"><qp:formatText value="${item.tableName}" /></a>
											</qp:authorization>
										</td>
										<td><qp:formatText value="${item.tableCode}" /></td>
										<td><qp:message code="${CL_TABLE_TYPE_ALL.get(item.type.toString())}"/></td>
										<td><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
										<td>
											<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
												<c:if test="${item.designStatus eq 1 }">
													<qp:authorization permission="tabledesignModify">
														<a href="${pageContext.request.contextPath}/tabledesign/viewScreenSetting?tableDesignId=${item.tableDesignId}&mode=2" class="btn qp-button glyphicon glyphicon-cog qp-link-button qp-button-popup" data-toggle="tooltip" title="<qp:message code="sc.tabledesign.0034"/>"></a>
													</qp:authorization>
												</c:if>
											</c:if>
										</td>
										<td>
											<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
												<c:if test="${item.type ne 2 && item.designStatus eq 1}">
													<qp:authorization permission="tabledesignModify">
														<a name="backJson" href="${pageContext.request.contextPath}/tabledesign/modify?tableDesignId=${item.tableDesignId}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.tabledesign.0070" />"></a>
													</qp:authorization>
												</c:if>
												<c:if test="${item.type eq 2}">
													<qp:authorization permission="tabledesignModify">
														<a name="backJson" href="${pageContext.request.contextPath}/tabledesign/modifyTableCommonForm?tableDesignId=${item.tableDesignId}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.tabledesign.0070" />"></a>
													</qp:authorization>
												</c:if>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<qp:authorization permission="tabledesignSearch">
							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}"  maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" maxDisplayCount="5" />
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
