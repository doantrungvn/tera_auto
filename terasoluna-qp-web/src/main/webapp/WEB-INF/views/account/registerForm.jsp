<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.account"></qp:message></span></li>
         <li><span><qp:message code="sc.account.0015"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/account/search"><qp:message code="sc.account.0008"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/account/javascript/account.js"></script>
		<script type="text/javascript">
			function showHideInputPassword() {
				var checked = $("#initialPassword").val();
				if(checked == "true") {
					$("#inputPassword").hide();
				} else {
					$("#inputPassword").show();
				}
			}
			$( document ).ready(function() {
				showHideInputPassword();
			});
		</script>	
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="accountForm" action="${pageContext.request.contextPath}/account/register">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.account.0001"/></span>
				</div>
				<div class="panel-body">
					<input type="password" style="display:none">
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
									<qp:message code="sc.account.0002" />&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.account.0023"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</form:label>
							</th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="username" autofocus="true" /></td>
							<th>
								<form:label path="accountNonLocked">
									<qp:message code="sc.account.0006" />
								</form:label>
							</th>
							<td>
								<label class="qp-input-radio-margin qp-input-radio radio-inline"><form:radiobutton path="accountNonLocked" value="true" /><qp:message code="sc.sys.0011"></qp:message></label> 
								<label class="qp-input-radio-margin qp-input-radio radio-inline"><form:radiobutton path="accountNonLocked" value="false" /><qp:message code="sc.sys.0012"></qp:message></label>
							</td>
						</tr>
						<%-- <tr>
							<th>
								<form:label path="accountNonExpired">
									<qp:message code="sc.account.0005"></qp:message>
								</form:label>
							</th>
							<td>
								<label class="radio-inline"><form:radiobutton path="accountNonExpired" value="true" /><qp:message code="sc.sys.0011"></qp:message></label>
								<label class="radio-inline"><form:radiobutton path="accountNonExpired" value="false" /><qp:message code="sc.sys.0012"></qp:message></label>
							</td>
							<th>
								<form:label path="credentialsNonExpired">
									<qp:message code="sc.account.0007"></qp:message>
								</form:label>
							</th>
							<td>
								<label class="radio-inline"><form:radiobutton path="credentialsNonExpired" value="true" /><qp:message code="sc.sys.0011"></qp:message></label>
								<label class="radio-inline"><form:radiobutton path="credentialsNonExpired" value="false" /> <qp:message code="sc.sys.0012"></qp:message></label>
							</td>
						</tr> --%>
						<tr>
							<th>
								<qp:message code="sc.accountruledefinition.0039"></qp:message>
							</th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllRuleDefinitionForAutocomplete" onSelectEvent="checkRuleHasInitialPassword"
									value="${accountForm.accountRuleDefinitionId }" name="accountRuleDefinitionId" displayValue="${accountForm.accountRuleDefinitionIdAutocomplete }" 
									arg02="20" mustMatch="true" onChangeEvent="removeInputPassword">
								</qp:autocomplete>
								<form:hidden path="initialPassword" styleId="initialPassword"/>
							</td>
							<th></th>
							<td></td>
						</tr>
						<tr id="inputPassword">
							<th>
								<form:label path="password">
									<qp:message code="sc.account.0003"></qp:message>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td><form:input type="password" cssClass="form-control qp-input-text" path="password" autocomplete="new-password"/></td>
							<th>
								<form:label path="confirmPassword">
									<qp:message code="sc.account.0004"></qp:message>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td><form:input type="password" cssClass="form-control qp-input-text" path="confirmPassword" autocomplete="new-password"/></td>
						</tr>
					</table>
				</div>
			</div>
			
<%-- 			<form:form method="post" modelAttribute="accountRoleListWrapper" action="${pageContext.request.contextPath}/account/register"> --%>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.role.0010"/></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col width="3%" />
									<col width="30%" />
									<col width="17%" />
									<col width="50%" />
								</colgroup>
								<thead>
									<tr>
										<th></th>
										<th><qp:message code="sc.role.0005" /></th>
										<th><qp:message code="sc.role.0006" /></th>
										<th style="text-align: left;"><qp:message code="sc.sys.0028" /></th>
									</tr>
								</thead>
								<c:forEach var="role" items="${accountForm.accRoleList}" varStatus="statusRole">
									<tr>
										<td><form:checkbox path="accRoleList[${statusRole.index}].selected" cssClass="qp-input-checkbox qp-input-checkbox-margin"/></td>
										<td>
											${f:h(role.roleName)}
											<form:hidden path="accRoleList[${statusRole.index}].roleName" value="${f:h(role.roleName)}"/>
										</td>
										<td>
											${f:h(role.roleCd)}
											<form:hidden path="accRoleList[${statusRole.index}].roleCd" value="${f:h(role.roleCd)}"/>
										</td>
										<td style="text-align: left;">
											${f:h(role.remark)}
											<form:hidden path="accRoleList[${statusRole.index}].remark" value="${f:h(role.remark)}"/>
										</td>
										<form:hidden path="accRoleList[${statusRole.index}].roleId" value="${role.roleId}"/>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
<%-- 			</form:form> --%>
			
			<div class="qp-div-action">
				<qp:authorization permission="accountRegister">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>