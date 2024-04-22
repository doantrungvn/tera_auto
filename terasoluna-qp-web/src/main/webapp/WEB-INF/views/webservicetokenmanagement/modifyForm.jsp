<tiles:insertDefinition name="layouts">

    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="sc.webservicetokenmanagement.0001"/></span></li>
         <li><span><qp:message code="sc.webservicetokenmanagement.0009"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="webservicetokenmanagementSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/webservicetokenmanagement/search"> <qp:message code="sc.webservicetokenmanagement.0002"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="webServiceTokenManagementForm" action="${pageContext.request.contextPath}/webservicetokenmanagement/modify">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"> <qp:message code="sc.webservicetokenmanagement.0008"/></span>
					</div>
					<form:hidden path="wsTokenId"/>
					<form:hidden path="projectId"/>
					<form:hidden path="updatedDate" />
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
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
								</th>
								<td>
									<form:input type="text" class="form-control" path="clientId" maxlength="100"/>
								</td>
								<th>
									<form:label path="clientSecret">
									<qp:message code="sc.webservicetokenmanagement.0006"/>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
								</th>
								<td>
									<form:input type="text" class="form-control" path="clientSecret" maxlength="100"/>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="webservicetokenmanagementModify">
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"></qp:message></button>
					</qp:authorization>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>