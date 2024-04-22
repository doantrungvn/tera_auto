<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="header-name">
		<qp:message code="sc.accountprofile.0003" />
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.accountprofile"></qp:message></span></li>
         <li><span><qp:message code="sc.accountprofile.0003"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="body">
		<sec:authentication var="user" property="principal" />
		<form:form method="post" role="form" action="${pageContext.request.contextPath}/accountprofile/modifyPassword" modelAttribute="passwordForm">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.accountprofile.0004" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col />
						</colgroup>
						<tr>
							<th><form:label path="userName">
									<qp:message code="sc.account.0002"></qp:message>
								</form:label></th>
							<td><qp:formatText value="${user.username}" /> <input type="hidden" name="userName" value="${f:h(user.username)}" /></td>
						</tr>
						<tr>
							<th><form:label path="currentPassword">
									<qp:message code="sc.accountprofile.0005"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label></th>
							<td><form:password cssClass="form-control qp-input-password" path="currentPassword" /></td>
						</tr>
						<tr>
							<th><form:label path="newPassword">
									<qp:message code="sc.accountprofile.0006"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label></th>
							<td><form:password cssClass="form-control qp-input-password" path="newPassword" /></td>
						</tr>
						<tr>
							<th><form:label path="confirmNewPassword">
									<qp:message code="sc.accountprofile.0007"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label></th>
							<td><form:password cssClass="form-control qp-input-password" path="confirmNewPassword" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<form:hidden path="accountId"/>
				<form:hidden path="accountRuleDefinitionId"/>
				<form:hidden path="redirectFromLogin"/>
				<button type="submit" class="btn qp-button qp-dialog-confirm" style="margin-right: 2px;">
					<qp:message code="sc.sys.0031"></qp:message>
				</button>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>