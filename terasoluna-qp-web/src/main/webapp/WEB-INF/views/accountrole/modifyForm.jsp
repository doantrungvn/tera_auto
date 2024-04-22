<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.account"></qp:message></span></li>
         <li><span><qp:message code="sc.accountrolepermission.0020"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/account/search"><qp:message code="sc.account.0008" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
	<form:form method="post" action="${pageContext.request.contextPath}/accountrole/modify" modelAttribute="accountRoleListWrapper">
		<c:if test="${notExistFlg ne 1}">
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.accountrolepermission.0010" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tr >
								<th><qp:message code="sc.accountrolepermission.0005" /></th>
								<td>${accountForm.username}</td>
								<th><qp:message code="sc.accountrolepermission.0006" /></th>
								<td>${accountForm.accountNonLocked}</td>
							</tr>
						</table>
					</div>
				</div>
				<br />
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0010" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list pull-left"
								style="width: 100%">
								<colgroup>
									<col width="30px"/>
									<col width="30%" />
									<col width="30%" />
									<col width="40%" />
								<thead>
									<tr>
										<th></th>
										<th style="text-align: left;"><qp:message code="sc.role.0005" /></th>
										<th style="text-align: left;"><qp:message code="sc.role.0006" /></th>
										<th style="text-align: left;"><qp:message code="sc.sys.0028" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="role"
										items="${accountRoleListWrapper.accRoleList }"
										varStatus="statusRole">
										<tr class="form-inline">
											<td><form:checkbox path="accRoleList[${statusRole.index}].selected" cssClass="checkbox qp-input-checkbox"/> </td>
											<td>${f:h(role.roleName)}</td>
											<td>${f:h(role.roleCd)}</td>
											<td style="text-align: left">${f:h(role.remark)}</td>
											<form:hidden path="accRoleList[${statusRole.index}].roleId" value="${role.roleId }"/>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="accountroleModify">
						<form:hidden path="accountId" value="${accountForm.accountId}" />
						<form:hidden path="username" value="${accountForm.username}" />
						<form:hidden path="updatedDate" value="${accountForm.updatedDate}" />
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
		</c:if>	
			</form:form>
	</tiles:putAttribute>

</tiles:insertDefinition>