<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.librarymanagement.0015"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
<c:if test="${notExistFlg ne 1}">	
	<tiles:putAttribute name="body">
		<form:form method="post"
			action="${pageContext.request.contextPath}/librarymanagement/delete"
			modelAttribute="libraryManagementForm">
			<form:hidden path="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.librarymanagement.0004"/></span>
				</div>
				<div class="panel-body">
					<form:hidden path="libraryId" />
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path=""><qp:message code="sc.librarymanagement.0005"/></form:label></th>
							<td><qp:formatText value="${libraryManagementForm.groupId}"></qp:formatText></td>
							<th><form:label path=""><qp:message code="sc.librarymanagement.0006"/></form:label></th>
							<td><qp:formatText value="${libraryManagementForm.artifactId}"></qp:formatText></td>
						</tr>
						<tr>
							<th><form:label path=""><qp:message code="sc.librarymanagement.0007"/></form:label></th>
							<td><qp:formatText value="${libraryManagementForm.version}"></qp:formatText></td>
							<th><form:label path=""><qp:message code="sc.librarymanagement.0008"/></form:label></th>
							<td><qp:formatText value="${libraryManagementForm.classifier}"></qp:formatText></td>
						</tr>
						<tr>
							<th><form:label path=""><qp:message code="sc.librarymanagement.0009"/></form:label></th>
							<td><qp:formatText value="${libraryManagementForm.scope}"></qp:formatText></td>
							<th><form:label path=""><qp:message code="sc.librarymanagement.0010"/></form:label></th>
							<td><qp:formatText value="${libraryManagementForm.type}"></qp:formatText></td>
						</tr>
						<tr>
							<c:if test="${libraryManagementForm.systemFlag eq '2' }">
								<th><form:label path=""><qp:message code="sc.librarymanagement.0011"/></form:label></th>
								<td><qp:formatText value="${libraryManagementForm.uploadFileName}"></qp:formatText></td>
							</c:if>
							<c:if test="${libraryManagementForm.systemFlag eq '1' }">
								<th><form:label path=""><qp:message code="sc.librarymanagement.0024"/></form:label></th>
								<td><qp:formatText value="${libraryManagementForm.systemPath}"></qp:formatText></td>
							</c:if>
							
							<th><form:label path=""><qp:message code="sc.librarymanagement.0012"/></form:label></th>
							<td>
								<c:if test="${libraryManagementForm.optionalFlg eq 1 }"><qp:message code="sc.sys.0011" /></c:if>
								<c:if test="${libraryManagementForm.optionalFlg ne 1 }"><qp:message code="sc.sys.0012" /></c:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="librarymanagementDelete">
					<a type="button" class="btn qp-button-warning qp-link-button qp-link-button qp-dialog-confirm" messageId="inf.sys.0014">
						<qp:message code="sc.sys.0008" />	
					</a>
				</qp:authorization>
				<qp:authorization permission="librarymanagementModify">
					<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate"
						href="${pageContext.request.contextPath}/librarymanagement/modify?libraryId=${libraryManagementForm.libraryId}&mode=1">
						<qp:message code="sc.sys.0006" />
					</a>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>