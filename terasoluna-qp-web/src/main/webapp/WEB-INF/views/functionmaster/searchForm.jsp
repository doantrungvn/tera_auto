<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.functionmaster"></qp:message></span></li>
         <li><span><qp:message code="sc.functionmaster.0008"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="functionmasterRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/functionmaster/register">
						<qp:message code="sc.functionmaster.0015" />
				</a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/functionmaster/search" modelAttribute="functionMasterSearchForm">
			<qp:ColumnSortHidden/>
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
							<th><qp:message code="sc.functionmaster.0005" /></th>
							<td><form:input path="functionMasterName" cssClass="form-control qp-input-text" maxlength="200" autofocus="true"/></td>
							<th><qp:message code="sc.functionmaster.0006" /></th>
							<td><form:input path="functionMasterCode" cssClass="form-control qp-input-text" maxlength="50"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.functionmaster.0007" /></th>
							<td>
                                <c:forEach var="item" items="${CL_FUNCTION_TYPE}">
                                    <label><form:checkbox path="functionMasterType" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><qp:message code="${CL_FUNCTION_TYPE.get(item.key)}" /></label>
                                </c:forEach>
							</td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
			</div>
		</form:form>
		<c:if test="${page!=null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
	                <qp:itemPerPage form="functionMasterSearchForm" action="/functionmaster/search" />
	            </div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col />
									<col width="47%" />
									<col width="48%" />
									<col width="15%" />
									<col />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="function_master_name" colCode="sc.functionmaster.0005" form="functionMasterSearchForm" /></th>
										<th><qp:columnSort colName="function_master_code" colCode="sc.functionmaster.0006" form="functionMasterSearchForm" /></th>
										<th><qp:columnSort colName="function_master_type" colCode="sc.functionmaster.0007" form="functionMasterSearchForm" /></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="function" items="${page.content }" varStatus="rowStatus">
										<tr class="form-inline">
											<td><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
											<td class="word-wrap">
												<qp:authorization permission="functionmasterView" isDisplay="true" displayValue="${function.functionMasterName}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/functionmaster/view?functionMasterId=${f:h(function.functionMasterId)}"><qp:formatText value="${function.functionMasterName}" /></a>
												</qp:authorization>
											</td>
											<td class="word-wrap">
												<qp:formatText value="${function.functionMasterCode}"/>
											</td>
											<td><qp:message code="${CL_FUNCTION_TYPE.get(function.functionMasterType.toString())}"/></td>
											<td class="word-wrap">
												<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
													<qp:authorization permission="functionmasterModify" isDisplay="false">
														<c:if test="${function.functionMasterType != null && function.functionMasterType == 1 }">
															<a href="${pageContext.request.contextPath}/functionmaster/modify?functionMasterId=${f:h(function.functionMasterId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action"></a>
														</c:if>
													</qp:authorization>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="qp-div-action">
	                            <c:choose>
	                                <c:when test="${page.sort != null }">
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(functionMasterSearchForm)}" maxDisplayCount="5" />
	                                </c:when>
	                                <c:otherwise>
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(functionMasterSearchForm)}" maxDisplayCount="5" />
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