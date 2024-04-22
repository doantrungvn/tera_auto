<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
		var parentId = [];
		<c:forEach var="parent" items="${listparent}">
			var parentCode = "${parent.moduleCode}";
			parentId.push(parentCode);
		</c:forEach>
		</script>
		<script src="${pageContext.request.contextPath}/resources/app/accountPermission/javascript/accountPermission.js" type="text/javascript"></script>
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.account" /></span></li>
         <li><span><qp:message code="sc.accountrolepermission.0025"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/account/search"><qp:message code="sc.account.0008" /></a>
		</qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<input type="hidden" value="${notExistFlg}"/>
		<form:form method="post" action="${pageContext.request.contextPath}/accountpermission/modify" modelAttribute="accountPermissionListWrapper">
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
							<%-- <tr>
								<th><qp:message code="sc.accountrolepermission.0007" /></th>
								<td>${accountForm.accountNonExpired}</td>
								<th><qp:message code="sc.accountrolepermission.0008" /></th>
								<td>${accountForm.credentialsNonExpired}</td>
							</tr> --%>
							<tr>
								<th><qp:message code="sc.accountrolepermission.0012" /></th>
								<td>
									<c:forEach items="${lstAccRole}" var="role">
										${role.roleName}<br/>
									</c:forEach>
								</td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<br />
				<div class="qp-div-action">
					<div class="pull-left">
						<qp:message code="sc.accountrolepermission.0017" />&nbsp;
						<div class="pull-right">
							<form:select path="moduleCd" onchange="changeModule(this)" cssClass="form-control qp-input-select">
								<form:option value="-1"><qp:message code="sc.sys.0030" /></form:option>
								<c:forEach var="module" items="${listModule}">
									<option value="${module.moduleCode}"><qp:message code="${module.moduleCode}" /></option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<qp:authorization permission="accountpermissionModify">
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization> 
				</div>

				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0014" /></span>
					</div>

					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list" id="tblPermission">
								<colgroup>
									<col width="35%">
									<col width="32%">
									<col width="28%">
									<col width="9%">
									<col width="70px">
								</colgroup>
								<thead>
									<tr>
										<th style="text-align: left;"><qp:message code="sc.accountrolepermission.0021" /></th>
										<th style="text-align: left;"><qp:message code="sc.role.0005" /></th>
										<th style="text-align: left;"><qp:message code="sc.sys.0028" /></th>
										<th style="text-align: center;"><qp:message code="sc.accountrolepermission.0022" /></th>
										<th style="text-align: center;"><qp:message code="sc.accountrolepermission.0023" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="parent" items="${listparent}" varStatus="statusParent">
										<tr class="success form-inline" tag="${parent.moduleCode}">
											<td style="text-align: left;" colspan="3"><qp:message code="${parent.moduleCode}" /></td>
											<td style="text-align: center;">
												<form:checkbox id="${parent.moduleCode}" tag="${parent.moduleCode}" path="accRoleList[${statusParent.index}].selected" onchange="changeChecked(this)" cssClass="checkbox qp-input-checkbox"/>
											</td>
											<td></td>
										</tr>
										<c:forEach var="children" items="${accountPermissionListWrapper.accPermissionList}" varStatus="statusChildren">
											<c:if test="${children.moduleCode == parent.moduleCode}">
												<tr tag="${parent.moduleCode}" class="form-inline">
													<td style="text-align: left;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<qp:message code="${children.permissionName}" />
														<form:hidden path="accPermissionList[${statusChildren.index}].hiddenHaveSameRole" value="${children.hiddenHaveSameRole}" />
													</td>
													<td style="text-align: left;" >${f:h(children.rolesName)}</td>
													<td style="text-align: left;" ><qp:message code="${children.remark}" /></td>
													<td style="text-align: center;"> <form:checkbox path="accPermissionList[${statusChildren.index}].selected" tag="${children.moduleCode}" class="checkbox qp-input-checkbox"  onchange="onChangePermission(this);"/> </td>
													<td style="text-align: center;" name = "summary" tag="${children.moduleCode}">
														<c:if test="${children.hiddenHaveSameRole ||  children.selected}">
															 <span class="glyphicon glyphicon-ok"></span>
														</c:if>
													 </td>
												</tr>
												<form:hidden path="accPermissionList[${statusChildren.index}].accountId" value="${children.accountId}" />
												<form:hidden path="accPermissionList[${statusChildren.index}].permissionId" value="${children.permissionId}" />
											</c:if>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="accountpermissionModify">
						<form:hidden path="accountId" value="${accountForm.accountId}" />
						<form:hidden path="username" value="${accountForm.username}" />
						<form:hidden path="updatedDate" value="${accountForm.updatedDate}" />
						<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
		</c:if>
	</form:form>
	</tiles:putAttribute>

</tiles:insertDefinition>