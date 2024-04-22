<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			$( document ).ready(function() {
				changeModule();
				$("#tblPermission tbody .parent-checkbox").each(function() {
					var parentCheckbox = $(this).attr("tag");
					var j = 0;
					var i = 0;
					$("#tblPermission tbody").find("input[type=checkbox][parentattribute='"+parentCheckbox+"']").each(function() {
						i++;
						if($(this).prop("checked")){
							j++;
						}
					});
					if(i==j){
						$("[id='"+parentCheckbox+"']").prop("checked" , true);
					}
				});
			});
			function changeModule() {
				if($("#moduleCd").val() == -1) {
					$("#tblPermission tbody").find("tr").each(function() {
						$(this).show();
					});
				} else {
					$("#tblPermission tbody").find("tr").each(function() {
						if($(this).attr("tag") != undefined) {
							var tabOfTr = $(this).attr("tag");
							if(tabOfTr == $("#moduleCd").val()) {
								$(this).show();
							} else {
								$(this).hide();
							}
						}
					});
				}
			}
			
			function childChek(obj){
				var checkedChild = $(obj).prop("checked");
				var parentId = $(obj).closest("tr").attr("tag");
				
				//if uncheck
				if(!checkedChild){
					$("[id='"+parentId+"']").prop("checked" , false);
				} else {
					var i = 0 ;
					$("#tblPermission tbody").find("input[type=checkbox][tag='"+parentId+"']").each(function() {
						if(!$(this).prop("checked")){
							i++;
						}
					});
					if(i == 1){
						$("[id='"+parentId+"']").prop("checked" , true);
					}
				} 
			}

			function changeChecked(obj) {
				var selectedModuleCode = $(obj).prop("checked");
				var moduleCodeValue = $(obj).attr("tag");
				$("#tblPermission tbody").find("input[type=checkbox][tag='"+moduleCodeValue+"']").each(function() {
					$(this).prop("checked" , selectedModuleCode);
				});
			}
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.rolemanagement"></qp:message></span></li>
		 <li><span><qp:message code="sc.role.0016"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="roleSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/role/search"><qp:message code="sc.role.0008"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="roleForm" action="${pageContext.request.contextPath}/role/modify">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
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
								<th><form:label path="roleName"><qp:message code="sc.role.0005" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input path="roleName" type="text" cssClass="form-control qp-input-text qp-convention-name" maxlength="200" /></td>
								<th><form:label path="roleCd"><qp:message code="sc.role.0006" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input path="roleCd" type="text" cssClass="form-control qp-input-text qp-convention-code" maxlength="50" /></td>
							</tr>
							<tr>
								<th><form:label path="status"><qp:message code="sc.sys.0027"></qp:message></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:radiobuttons class="qp-input-radio qp-input-radio-margin" path="status" items="${CL_ROLE_STATUS}" delimiter="<br/>"/></td>
								<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
								<td><form:textarea path="remark" type="text" rows="5" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
							</tr>
						</table>
					</div>
				</div>

				<div class="qp-div-action">
					<div class="pull-left">
						<qp:message code="sc.accountrolepermission.0017" />&nbsp;
						<div class="pull-right">
							<form:select path="moduleCd" onchange="changeModule()" cssClass="form-control qp-input-select">
								<form:option value="-1"><qp:message code="sc.sys.0030" /></form:option>
								<c:forEach var="module" items="${roleForm.lstModuleCode}">
									<option value="${module.moduleCode}"><qp:message code="${module.moduleCode}" /></option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<qp:authorization permission="roleModify">
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>

				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0014" /></span>
					</div>
					<div class="panel-body">
						
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%" id="tblPermission">
								<colgroup>
									<col width="25px"/>
									<col width="50%" />
									<col width="50%" />
								</colgroup>
								<thead>
									<tr>
										<th>&nbsp;</th>
										<th><qp:message code="sc.accountrolepermission.0013" /></th>
										<th><qp:message code="sc.sys.0028" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="parent" items="${roleForm.lstModuleCode}" varStatus="statusParent">
										<tr class="success form-inline" tag="${parent.moduleCode}">
											<td colspan="2" style="text-align: left;">
												<label>
													<form:checkbox tag="${parent.moduleCode}" path="lstModuleCode[${statusParent.index}].selected" 
														id="${parent.moduleCode}" onchange="changeChecked(this)" class="checkbox qp-input-checkbox parent-checkbox" />
														<!-- </td>
														<td class="com-output-text"> -->
													&nbsp;<qp:message code="${parent.moduleCode}" />
												</label>
											</td>
											<td />
											<td />
										</tr>
										<form:hidden path="lstModuleCode[${statusParent.index}].moduleCode" value="${parent.moduleCode}" />
										<c:forEach var="children" items="${roleForm.lstPermission}" varStatus="statusChildren">
											<c:if test="${children.moduleCode == parent.moduleCode}">
												<tr align="left" tag="${parent.moduleCode}" class="form-inline">
													<td />
													<td class="com-output-text">
														<label>
														<form:checkbox tag="${children.moduleCode}" path="lstPermission[${statusChildren.index}].selected" onchange="childChek(this)" class="checkbox qp-input-checkbox" parentattribute = "${parent.moduleCode}" />
														&nbsp;<qp:message code="${children.permissionName}" /></label>
													</td>
													<td align="left">
														<qp:message code="${children.remark}" />
													</td>
												</tr>
												<form:hidden path="lstPermission[${statusChildren.index}].moduleCode" value="${children.moduleCode}" />
												<form:hidden path="lstPermission[${statusChildren.index}].permissionId" value="${children.permissionId}" />
												<form:hidden path="lstPermission[${statusChildren.index}].permissionName" value="${children.permissionName}" />
												<form:hidden path="lstPermission[${statusChildren.index}].remark" value="${children.remark}" />
											</c:if>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="roleModify">
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"></qp:message></button>
						<form:hidden path="updatedDate" />
						<form:hidden path="updatedBy" />
						<form:hidden path="roleId" />
					</qp:authorization>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>