<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=account"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/account/javascript/account.js"></script>
		<script type="text/javascript">
			
			function showHideChangePass() {
				var checked = $("#selectedChangePass").prop("checked");
				if(checked === undefined) {
					checked = false;
				}
				if(checked) {
					checkRuleHasInitialPasswordForEdit();
				} else {
					$("#trChangePass").hide();
				}
			}
			$( document ).ready(function() {
				showHideChangePass();
			});
		</script>
	</tiles:putAttribute>
		
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.account"></qp:message></span></li>
		 <li><span><qp:message code="sc.account.0016"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/account/search"><qp:message code="sc.account.0008"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="accountForm" action="${pageContext.request.contextPath}/account/modify">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.account.0001"/></span>
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
								<td><qp:formatText value="${accountForm.username}"/></td>
								<th><qp:message code="sc.account.0006" /></th>
								<td>
									<form:label path="accountNonLocked">
										<label class="radio-inline"><form:radiobutton path="accountNonLocked" value="true" /> <qp:message code="sc.sys.0011"></qp:message></label>
										<label class="radio-inline"><form:radiobutton path="accountNonLocked" value="false" /> <qp:message code="sc.sys.0012"></qp:message></label>
									</form:label>
								</td>
							</tr>
							<%-- <tr>
								<th>
									<form:label path="accountNonExpired">
										<qp:message code="sc.account.0005" />
									</form:label>
								</th>
								<td>
									<label class="radio-inline"><form:radiobutton path="accountNonExpired" value="true" /> <qp:message code="sc.sys.0011"></qp:message></label>
									<label class="radio-inline"><form:radiobutton path="accountNonExpired" value="false" /> <qp:message code="sc.sys.0012"></qp:message></label>
								</td>
								<th>
									<form:label path="credentialsNonExpired">
										<qp:message code="sc.account.0007" />
									</form:label>
								</th>
								<td>
									<label class="radio-inline"><form:radiobutton path="credentialsNonExpired" value="true" /> <qp:message code="sc.sys.0011"></qp:message></label>
									<label class="radio-inline"><form:radiobutton path="credentialsNonExpired" value="false" /> <qp:message code="sc.sys.0012"></qp:message></label>
								</td>
							</tr> --%>
							<tr>
								<th><qp:message code="sc.accountruledefinition.0039" /></th>
								<td>
									<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllRuleDefinitionForAutocomplete" onSelectEvent="checkRuleHasInitialPasswordForEdit"
										value="${accountForm.accountRuleDefinitionId }" name="accountRuleDefinitionId" displayValue="${accountForm.accountRuleDefinitionIdAutocomplete }" 
										arg02="20" mustMatch="true" onChangeEvent="removeInputPasswordForEdit">
									</qp:autocomplete>
								</td>
								<th>
									<c:if test="${not initialPassword}">
										<form:label path="selectedChangePass">
											<qp:message code="sc.account.0014" />
										</form:label>
									</c:if>	
									<c:if test="${initialPassword}">
										<form:label path="selectedChangePass">
											<qp:message code="sc.account.0019" />
										</form:label>
									</c:if>
								</th>
								<td style="text-align: left;">
									<form:checkbox id="selectedChangePass" path="selectedChangePass" onchange="showHideChangePass()" cssClass="qp-input-checkbox qp-input-checkbox-margin" />
								</td>
							</tr>
							<tr id="trChangePass" style="display: none;">
								<th>
									<form:label path="password">
										<qp:message code="sc.account.0009" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
									</form:label>
								</th>
								<td><form:input type="password" cssClass="form-control qp-input-text" path="password" autofocus="true" autocomplete="new-password"/></td>
								<th>
									<form:label path="confirmPassword">
										<qp:message code="sc.account.0010" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
									</form:label>
								</th>
								<td><form:input type="password" cssClass="form-control qp-input-text" path="confirmPassword" autocomplete="new-password"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="accountModify">
						<form:hidden path="username" />
						<form:hidden path="accountId" />
						<form:hidden path="updatedDate" />
<%-- 						<c:if test="${accountForm.accountRuleDefinitionId != null}"> --%>
<%-- 							<form:hidden path="accountRuleDefinitionId" /> --%>
<%-- 						</c:if> --%>
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"></qp:message></button>
					</qp:authorization>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>