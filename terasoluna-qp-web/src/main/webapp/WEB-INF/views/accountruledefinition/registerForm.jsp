<tiles:insertDefinition name="layouts">

    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.accountruledefinition"></qp:message></span></li>
         <li><span><qp:message code="sc.accountruledefinition.0003"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountruledefinitionSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/accountruledefinition/search"><qp:message code="sc.accountruledefinition.0002"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/accountruledefinition/javascript/accountruledefinition.js"></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="accountRuleDefinitionForm" action="${pageContext.request.contextPath}/accountruledefinition/register">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountruledefinition.0006"/></span>
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
								<form:label path="accountRuleDefinitionName">
									<qp:message code="sc.accountruledefinition.0005"></qp:message>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name-row" path="accountRuleDefinitionName" autofocus="true" /></td>
							<th>
								<form:label path="accountRuleDefinitionCode">
									<qp:message code="sc.accountruledefinition.0004"></qp:message>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="accountRuleDefinitionCode" autofocus="true" /></td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountruledefinition.0007"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="50%" />
						</colgroup>
						<tr>
							<th style="text-align: left"><qp:message code="sc.accountruledefinition.0008"/></th>
							<th style="text-align: left"><qp:message code="sc.accountruledefinition.0009"/></th>
							<th style="text-align: left"><qp:message code="sc.accountruledefinition.0010"/></th>
						</tr>
						<tr class="success form-inline">
							<td colspan="3"><qp:message code="sc.accountruledefinition.0011"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="rangeOfStringCheckbox" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="rangeOfStringCheckbox1"><qp:message code="sc.accountruledefinition.0012"/></label>
							</td>
							<td>
								<form:input type="text" cssClass="form-control qp-input-from-integer pull-left" path="rangeOfStringMinimum" autofocus="true" />
								<div class="qp-separator-from-to">~</div>
								<form:input type="text" cssClass="form-control qp-input-from-integer pull-right" path="rangeOfStringMaximum" autofocus="true" />
							</td>
							<td><qp:message code="sc.accountruledefinition.0027"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="charactersTypeCheckbox" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="charactersTypeCheckbox1"><qp:message code="sc.accountruledefinition.0013"/></label>
							</td>
							<td>
								<form:checkbox path="charactersTypeUpper" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><label for="charactersTypeUpper1"><qp:message code="sc.accountruledefinition.0022"/></label><br/>
								<form:checkbox path="charactersTypeLower" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><label for="charactersTypeLower1"><qp:message code="sc.accountruledefinition.0023"/></label><br/>
								<form:checkbox path="charactersTypeNumeric" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><label for="charactersTypeNumeric1"><qp:message code="sc.accountruledefinition.0024"/></label>
							</td>
							<td><qp:message code="sc.accountruledefinition.0028"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="generationsCountCheckbox" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="generationsCountCheckbox1"><qp:message code="sc.accountruledefinition.0014"/></label>
							</td>
							<td>
								<form:input type="text" cssClass="form-control qp-input-from-integer pull-left" path="generationsCount" autofocus="true" />&nbsp;
								<qp:message code="sc.accountruledefinition.0025"/>
							</td>
							<td><qp:message code="sc.accountruledefinition.0029"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="lifeTimeCheckbox" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="lifeTimeCheckbox1"><qp:message code="sc.accountruledefinition.0015"/></label>
							</td>
							<td>
								<form:input type="text" cssClass="form-control qp-input-from-integer pull-left" path="lifeTime" autofocus="true" />&nbsp;
								<qp:message code="sc.accountruledefinition.0026"/>
							</td>
							<td><qp:message code="sc.accountruledefinition.0030"/></td>
						</tr>
						<tr class="success form-inline">
							<td colspan="3"><qp:message code="sc.accountruledefinition.0016"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="loginContinuousFailureCountCheckbox" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="loginContinuousFailureCountCheckbox1"><qp:message code="sc.accountruledefinition.0017"/></label>
							</td>
							<td>
								<form:input type="text" cssClass="form-control qp-input-from-integer pull-left" path="loginContinuousFailureCount" autofocus="true" />&nbsp;
								<qp:message code="sc.accountruledefinition.0025"/>
							</td>
							<td><qp:message code="sc.accountruledefinition.0031"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="accountLockTimeCheckbox" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="accountLockTimeCheckbox1"><qp:message code="sc.accountruledefinition.0018"/></label>
							</td>
							<td>
								<form:input type="text" cssClass="form-control qp-input-from-integer pull-left" path="accountLockTime" autofocus="true" />&nbsp;
								<qp:message code="sc.accountruledefinition.0025"/>
							</td>
							<td><qp:message code="sc.accountruledefinition.0032"/></td>
						</tr>
						<tr class="success form-inline">
							<td colspan="3"><qp:message code="sc.accountruledefinition.0019"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="initialPassword" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="initialPassword1"><qp:message code="sc.accountruledefinition.0020"/></label>
							</td>
							<td></td>
							<td><qp:message code="sc.accountruledefinition.0033"/></td>
						</tr>
						<tr>
							<td>
								<form:checkbox path="initialPasswordForceChange" cssClass="qp-input-checkbox-margin qp-input-checkbox" />
								<label for="initialPasswordForceChange1"><qp:message code="sc.accountruledefinition.0021"/></label>
							</td>
							<td></td>
							<td><qp:message code="sc.accountruledefinition.0034"/></td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="qp-div-action">
				<qp:authorization permission="accountruledefinitionRegister">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"></qp:message></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>