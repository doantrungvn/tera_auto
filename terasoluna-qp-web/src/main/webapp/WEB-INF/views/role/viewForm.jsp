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
			<qp:message code="sc.role.0017" />
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
	

	<c:if test="${notExistFlg ne 1}">
		<tiles:putAttribute name="body">
			<form:form method="post" modelAttribute="roleForm" action="${pageContext.request.contextPath}/role/delete">
				<form:errors path="*" cssClass="error" element="div" />
				<!-- begin role information -->
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.role.0010" /></span>
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
								<th><qp:message code="sc.role.0005" /></th>
								<td class="word-wrap"><qp:formatText value="${roleForm.roleName}" />
								<form:hidden path="roleName" /></td>
								<th><qp:message code="sc.role.0006" /></th>
								<td class="word-wrap"><qp:formatText value="${roleForm.roleCd}" />
								<form:hidden path="roleCd" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0027" /></th>
								<td><qp:formatText value="${CL_ROLE_STATUS.get(roleForm.status.toString())}" />
								<form:hidden path="status" /></td>
								<th><qp:message code="sc.sys.0028" /></th>
								<td class="word-wrap"><qp:formatRemark value="${roleForm.remark}" />
								<form:hidden path="remark" /></td>
							</tr>
						</table>
					</div>
				</div>
				<!-- begin operation button panel -->
				<br />
				<c:if test="${lstAccountName == null}">
				<div class="qp-div-action">
					<div class="pull-left">
						<qp:message code="sc.accountrolepermission.0017" />&nbsp;
						<div class="pull-right">
							<form:select path="moduleCd" onchange="changeModule(this)" cssClass="form-control qp-input-select">
								<form:option value="-1" label="" />
								<c:forEach var="moduleCode" items="${lstModuleCode}" varStatus="status">
									<option value="${moduleCode}"><qp:message code="${moduleCode}" />
									</option>
								</c:forEach>
							</form:select>
							<c:forEach var="moduleCode" items="${lstModuleCode}" varStatus="status">
								<form:hidden path="lstDistinctModuleCode[${status.index}]" value="${moduleCode}" />
							</c:forEach>
						</div>
					</div>
					<qp:authorization permission="roleDelete">
						<form:hidden path="roleId" />
						<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
					</qp:authorization>
					<qp:authorization permission="roleModify">
						<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/role/modify?roleId=${roleForm.roleId}&mode=1"><qp:message code="sc.sys.0006" /></a>
					</qp:authorization>
				</div>
				</c:if>
				<!-- end operation button panel -->
				<!-- end role information -->
				
				<!-- begin permission information -->
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0014" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list pull-right" style="width: 100%" id="tblPermission">
								<colgroup>
									<col width="50%" />
									<col width="50%" />
								</colgroup>
								<thead>
									<tr>
										<th style="text-align: left;"><qp:message code="sc.accountrolepermission.0013" /></th>
										<th style="text-align: left;"><qp:message code="sc.sys.0028" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="parent" items="${roleForm.lstDistinctModuleCode}" varStatus="statusParent">
										<tr class="success form-inline" tag=${parent }>
											<td class="com-output-text" style="text-align: left;">
												<qp:message code="${parent}" />
											</td>
											<td />
											<td />
										</tr>
										<c:forEach var="children" items="${roleForm.lstPermission}" varStatus="statusChildren">
											<c:if test="${children.moduleCode == parent}">
												<form:hidden path="lstPermission[${statusChildren.index}].moduleCode" value ="${children.moduleCode}" />
												<tr class="form-inline" tag="${children.moduleCode }">
													<td class="com-output-text" style="text-align: left;">
														&nbsp;&nbsp;&nbsp;&nbsp;<qp:message code= "${f:h(children.permissionName) }" />
														<form:hidden path="lstPermission[${statusChildren.index}].permissionName" value ="${children.permissionName}"/>
													</td>
													<td class="com-output-text" style="text-align: left;">
														<qp:message code= "${f:h(children.remark) }" />
														<form:hidden path="lstPermission[${statusChildren.index}].remark" value ="${children.remark }" />
													</td>
												</tr>
											</c:if>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<!-- end permission information -->
				<c:if test="${lstAccountName != null}">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.accountruledefinition.0054" /></span>
						</div>
						<div class="panel-body">
							<table class="table table-bordered qp-table-form">
								<colgroup>
									<col width="20%" />
									<col width="80%" />
								</colgroup>
								<tr>
									<th style="text-align: left"><qp:message code="sc.sys.0004" /></th>
									<th style="text-align: left"><qp:message code="sc.account.0002" /></th>
								</tr>
								
								<c:forEach items="${lstAccountName}" var="user" varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td>${f:h(user)}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</c:if>
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>