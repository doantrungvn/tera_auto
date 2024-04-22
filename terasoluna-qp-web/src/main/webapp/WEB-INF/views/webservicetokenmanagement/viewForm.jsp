<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.webservicetokenmanagement.0007"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
	 		<c:if test="${notExistFlg ne 1}">
			<form:form method="post" modelAttribute="webServiceTokenManagementForm" action="${pageContext.request.contextPath}/webservicetokenmanagement/delete">
                <form:errors path="*" cssClass="error" element="div" />
                	<form:hidden path="wsTokenId"/>
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"> <qp:message code="sc.webservicetokenmanagement.0008"/></span>
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
									<th>
										<form:label path="projectName">
											<qp:message code="sc.project.0005"/>&nbsp;
										</form:label>
									</th>
									<td>
										<qp:formatText value="${webServiceTokenManagementForm.projectName}"/>
										<form:hidden path="projectName" />
									</td>
									<th>
										<form:label path="projectCode">
											<qp:message code="sc.project.0006"/>&nbsp;
										</form:label>
									</th>
									<td>
										<qp:formatText value="${webServiceTokenManagementForm.projectCode}"/>
										<form:hidden path="projectCode" />
									</td>
								</tr>
								<tr>
									<th>
										<form:label path="clientId">
											<qp:message code="sc.webservicetokenmanagement.0005"/>&nbsp;
										</form:label>
									</th>
									<td>
										<qp:formatText value="${webServiceTokenManagementForm.clientId}"/>
										<form:hidden path="clientId" />
									</td>
									<th>
										<form:label path="clientSecret">
											<qp:message code="sc.webservicetokenmanagement.0006"/>&nbsp;
										</form:label>
									</th>
									<td>
										<qp:formatText value="${webServiceTokenManagementForm.clientSecret}"/>
										<form:hidden path="clientSecret" />
									</td>
								</tr>
							</table>
						</div>
					</div>
					
					<div class="qp-div-action">
						<qp:authorization permission="webservicetokenmanagementDelete">
							<button type="button" class="btn btn-warning qp-dialog-confirm">
								<qp:message code="sc.sys.0008"></qp:message>
							</button>
						</qp:authorization>
						<qp:authorization permission="webservicetokenmanagementModify">
							<a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/webservicetokenmanagement/modify?wsTokenId=${f:h(webServiceTokenManagementForm.wsTokenId)}&mode=1">
								<qp:message code="sc.sys.0006"></qp:message>
							</a>
						</qp:authorization>
					</div>
			</form:form>
 		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>