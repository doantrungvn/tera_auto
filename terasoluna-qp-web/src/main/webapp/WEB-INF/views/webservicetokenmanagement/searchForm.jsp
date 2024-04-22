<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="sc.webservicetokenmanagement.0001"/></span></li>
         <li><span><qp:message code="sc.webservicetokenmanagement.0002"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="webservicetokenmanagementRegister">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/webservicetokenmanagement/register">
				<qp:message code="sc.webservicetokenmanagement.0003"/>
			</a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/webservicetokenmanagement/search" modelAttribute="webServiceTokenManagementSearchForm">
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
							<th><qp:message code="sc.project.0006"/></th>
							<td><form:input path="projectCode" cssClass="form-control qp-input-text" autofocus="true" /></td>
							<th><qp:message code="sc.project.0005"/></th>
							<td><form:input path="projectName" cssClass="form-control qp-input-text" autofocus="true" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001"></qp:message></button>
			</div>
		</form:form>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="webServiceTokenManagementSearchForm" action="/webservicetokenmanagement/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }"> 
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col />
								<col width="20%" />
								<col width="20%" />
								<col width="20%" />
								<col width="20%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:message code="sc.project.0006"/></th>
									<th><qp:message code="sc.project.0005"/></th>
									<th><qp:message code="sc.webservicetokenmanagement.0005"/></th>
									<th><qp:message code="sc.webservicetokenmanagement.0006"/></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="wsToken" items="${page.content}" varStatus="look">
								<tr>
									<td>${(page.number * page.size) + look.count}</td>
									<td > 
 											<qp:authorization permission="webservicetokenmanagementView" isDisplay="true" displayValue="${f:h(wsToken.projectCode)}">
 												<a class="qp-link-popup" href="${pageContext.request.contextPath}/webservicetokenmanagement/view?wsTokenId=${wsTokenId=wsToken.wsTokenId}&openOwner=1" >
												<qp:formatText value="${wsToken.projectCode}" />
											</a>
											</qp:authorization>
									</td>
									<td class="com-output-text"> <qp:formatText value="${wsToken.projectName}" /></td>
									<td class="com-output-text">
										<qp:formatText value="${wsToken.clientId}" />
									</td>
									<td class="com-output-text"><qp:formatText value="${wsToken.clientSecret}" /></td>
									<td class="com-table-detail-action">
										<qp:authorization permission="webservicetokenmanagementModify">
											<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/webservicetokenmanagement/modify?wsTokenId=${wsTokenId=wsToken.wsTokenId}&mode=0"></a>
										</qp:authorization>
									</td>
								</tr>
							</c:forEach>	
							</tbody>
						</table>
                        
                        <div class="qp-div-action">
                            <c:choose>
                                <c:when test="${page.sort != null }">
                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(tableSearchForm)}" maxDisplayCount="5" />
                                </c:when>
                                <c:otherwise>
                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(tableSearchForm)}" maxDisplayCount="5" />
                                </c:otherwise>
                            </c:choose>
                        </div>
					</c:if>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>