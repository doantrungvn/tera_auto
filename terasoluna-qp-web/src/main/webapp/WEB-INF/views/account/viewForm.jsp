<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			function changeModule(obj) {
				if($(obj).val() == -1) {
					$("#tblPermission tbody").find("tr").each(function() {
						$(this).show();
					});
				} else {
					$("#tblPermission tbody").find("tr").each(function() {
						if($(this).attr("tag") != undefined) {
							var tabOfTr = $(this).attr("tag");
							if(tabOfTr == $(obj).val()) {
								$(this).show();
							} else {
								$(this).hide();
							}
						}
					});
				}
			}
			
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.account.0017"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1}">
			<form:form method="post" modelAttribute="accountForm" action="${pageContext.request.contextPath}/account/delete">
                <form:errors path="*" cssClass="error" element="div" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.account.0001" /></span>
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
									<form:label path="username">
										<qp:message code="sc.account.0002" />
									</form:label>
								</th>
								<td><form:label path="username" autofocus="true" /><qp:formatText value="${accountForm.username}"/></td>
								<th><qp:message code="sc.account.0006" /></th>
								<td><qp:booleanFormatTrueFalse value="${accountForm.accountNonLocked}"/></td>
							</tr>
							<tr>
								<th><qp:message code="sc.accountruledefinition.0039" /></th>
								<td><form:label path="accountRuleDefinitionIdAutocomplete" /><qp:formatText value="${accountForm.accountRuleDefinitionIdAutocomplete}"/></td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="accountDelete">
						<form:hidden path="accountId" />
						<form:hidden path="updatedDate" />
						<button type="button" class="btn btn-warning qp-dialog-confirm">
							<qp:message code="sc.sys.0008"></qp:message>
						</button>
					</qp:authorization>
					<qp:authorization permission="accountModify">
						<a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/account/modify?accountId=${f:h(accountForm.accountId)}&mode=1">
							<qp:message code="sc.sys.0006"></qp:message>
						</a>
					</qp:authorization>
				</div>
				
				<!-- Begin project information -->
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span aria-hidden="true" class="glyphicon qp-heading-icon">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.project.0010" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table id="tblProject" class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col >
									<col >
									<col width="40%">
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.project.0005" /></th>
										<th><qp:message code="sc.project.0006" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="project" items="${lstProject}" varStatus="rowStatus">
										<tr>
											<td><qp:formatInteger value="${rowStatus.count}" /></td>
											<td >
											    <qp:authorization permission="projectView" isDisplay="true" displayValue="${project.projectName}">
	                                           		<a class="qp-link-popup" href="${pageContext.request.contextPath}/project/view?projectId=${f:h(project.projectId)}"><qp:formatText value="${project.projectName}" /></a>
		                                       	</qp:authorization>
		                                   	</td>
											<td ><qp:formatText value="${project.projectCode}"/></td>
										</tr>
									</c:forEach>
									<c:if test="${empty lstProject}">
									<tr>
										<td colspan="3"><qp:message code="inf.sys.0013"/></td>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- End project information -->
				
				<!-- Begin role information -->
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0010" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table id="tblRole" class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col >
									<col >
									<col width="10%">
									<col width="40%">
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.role.0005" /></th>
										<th><qp:message code="sc.role.0007" /></th>
										<th><qp:message code="sc.sys.0028" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${lstRole }" var="role" varStatus="rowStatus">
										<tr>
											<td><qp:formatInteger value="${rowStatus.count}" /></td>
											<td ><qp:formatText value="${role.roleName}" /></td>
											<td ><qp:formatText value="${CL_ROLE_STATUS.get(role.status.toString())}" /></td>
											<td ><qp:formatRemark value="${role.remark}" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty lstRole}">
									<tr>
										<td colspan="4"><qp:message code="inf.sys.0013"/></td>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- End role information -->
				<br />
				<div class="qp-div-action-select">
					<qp:message code="sc.accountrolepermission.0017" />&nbsp;
					<div class="pull-right">
						<form:select path="moduleCd" onchange="changeModule(this)" cssClass="form-control qp-input-select">
							<form:option value="-1" label="" />
							<c:forEach var="moduleCode" items="${lstModuleCode}">
								<option value="${moduleCode}"><qp:message code="${moduleCode}" /></option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- Begin permission information -->
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span aria-hidden="true" class="glyphicon qp-heading-icon">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0014" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table id="tblPermission" class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col width="50%">
									<col width="50%">
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.accountrolepermission.0021" /></th>
										<th><qp:message code="sc.sys.0028" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach  var="moduleCode" items="${lstModuleCode }" varStatus="rowStatus"> 
										<tr tag=${moduleCode } class="success form-inline">
											<td colspan="2"  style="text-align: left;"><qp:message code="${moduleCode}" /></td>
										</tr>
										<c:forEach items="${lstPermission }" var="per">
											<c:if test="${per.moduleCode == moduleCode }">
												<tr tag=${per.moduleCode } class="form-inline">
													<td style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<qp:message code="${per.permissionName}" /></td>
													<td ><qp:message code="${per.remark}" /></td>
												</tr>
											</c:if>
										</c:forEach>
									</c:forEach>
									<c:if test="${empty lstModuleCode}">
									<tr>
										<td colspan="2"><qp:message code="inf.sys.0013"/></td>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- End permission information -->
			</form:form>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>