<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.accountruledefinition.0035"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
	 		<c:if test="${notExistFlg ne 1}">
			<form:form method="post" modelAttribute="accountRuleDefinitionForm" action="${pageContext.request.contextPath}/accountruledefinition/delete">
                <form:errors path="*" cssClass="error" element="div" />
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.accountruledefinition.0006" /></span>
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
											<qp:message code="sc.accountruledefinition.0005"></qp:message>
										</form:label>
									</th>
									<td>
										<qp:formatText value="${accountRuleDefinitionForm.accountRuleDefinitionName}"/>
										<form:hidden path="accountRuleDefinitionName" />
									</td>
									<th>
										<form:label path="accountRuleDefinitionCode">
											<qp:message code="sc.accountruledefinition.0004" />
										</form:label>
									</th>
									<td>
										<qp:formatText value="${accountRuleDefinitionForm.accountRuleDefinitionCode}"/>
										<form:hidden path="accountRuleDefinitionCode" />
									</td>
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
										<qp:message code="sc.accountruledefinition.0012"/>
									</td>
									<td>
										<c:if test="${accountRuleDefinitionForm.rangeOfStringMinimum > 0 and accountRuleDefinitionForm.rangeOfStringMaximum > 0}">
											<qp:formatInteger value="${accountRuleDefinitionForm.rangeOfStringMinimum}"/> ~ <qp:formatInteger value="${accountRuleDefinitionForm.rangeOfStringMaximum}"/>
											<form:hidden path="rangeOfStringMinimum" />
											<form:hidden path="rangeOfStringMaximum" />
										</c:if>
									</td>
									<td><qp:message code="sc.accountruledefinition.0027"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0013"/>
									</td>
									<td>
										<c:if test="${accountRuleDefinitionForm.charactersTypeUpper}">
											<qp:message code="sc.accountruledefinition.0022"/>
											<br/>
											<form:hidden path="charactersTypeUpper" />
										</c:if>
										<c:if test="${accountRuleDefinitionForm.charactersTypeLower}">
											<qp:message code="sc.accountruledefinition.0023"/>
											<br/>
											<form:hidden path="charactersTypeLower" />
										</c:if>
										<c:if test="${accountRuleDefinitionForm.charactersTypeNumeric}">
											<qp:message code="sc.accountruledefinition.0024"/>
											<form:hidden path="charactersTypeNumeric" />
										</c:if>
									</td>
									<td><qp:message code="sc.accountruledefinition.0028"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0014"/>
									</td>
									<td>
										<c:if test="${accountRuleDefinitionForm.generationsCount > 0}">
											<qp:formatInteger value="${accountRuleDefinitionForm.generationsCount}"/>
											<qp:message code="sc.accountruledefinition.0025"/>
										</c:if>
										<form:hidden path="generationsCount" />
									</td>
									<td><qp:message code="sc.accountruledefinition.0029"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0015"/>
									</td>
									<td>
										<c:if test="${accountRuleDefinitionForm.lifeTime > 0}">
											<qp:formatInteger value="${accountRuleDefinitionForm.lifeTime}"/>
											<qp:message code="sc.accountruledefinition.0026"/>
										</c:if>
										<form:hidden path="lifeTime" />
									</td>
									<td><qp:message code="sc.accountruledefinition.0030"/></td>
								</tr>
								<tr class="success form-inline">
									<td colspan="3"><qp:message code="sc.accountruledefinition.0016"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0017"/>
									</td>
									<td>
										<c:if test="${accountRuleDefinitionForm.loginContinuousFailureCount > 0}">
											<qp:formatInteger value="${accountRuleDefinitionForm.loginContinuousFailureCount}"/>
											<qp:message code="sc.accountruledefinition.0025"/>
										</c:if>
										<form:hidden path="loginContinuousFailureCount" />
									</td>
									<td><qp:message code="sc.accountruledefinition.0031"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0018"/>
									</td>
									<td>
										<c:if test="${accountRuleDefinitionForm.accountLockTime > 0}">
											<qp:formatInteger value="${accountRuleDefinitionForm.accountLockTime}"/>
											<qp:message code="sc.accountruledefinition.0025"/>
										</c:if>
										<form:hidden path="accountLockTime" />
									</td>
									<td><qp:message code="sc.accountruledefinition.0032"/></td>
								</tr>
								<tr class="success form-inline">
									<td colspan="3"><qp:message code="sc.accountruledefinition.0019"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0020"/>
									</td>
									<td>
										<qp:booleanFormatTrueFalse value="${accountRuleDefinitionForm.initialPassword}"/>
										<form:hidden path="initialPassword" />
									</td>
									<td><qp:message code="sc.accountruledefinition.0033"/></td>
								</tr>
								<tr>
									<td>
										<qp:message code="sc.accountruledefinition.0021"/>
									</td>
									<td>
										<qp:booleanFormatTrueFalse value="${accountRuleDefinitionForm.initialPasswordForceChange}"/>
										<form:hidden path="initialPasswordForceChange" />
									</td>
									<td><qp:message code="sc.accountruledefinition.0034"/></td>
								</tr>
							</table>
						</div>
					</div>
				
				<c:if test="${listAppliedUserAccount != null}">
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
								
								<c:forEach items="${listAppliedUserAccount}" var="user" varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td>${f:h(user)}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</c:if>
				
				<c:if test="${listAppliedUserAccount == null}">
					<div class="qp-div-action">
						<qp:authorization permission="accountruledefinitionDelete">
							<form:hidden path="accountRuleDefinitionId" />
							<form:hidden path="updatedDate" />
							<button type="button" class="btn btn-warning qp-dialog-confirm">
								<qp:message code="sc.sys.0008"></qp:message>
							</button>
						</qp:authorization>
						<qp:authorization permission="accountruledefinitionModify">
							<a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/accountruledefinition/modify?accountRuleDefinitionId=${f:h(accountRuleDefinitionForm.accountRuleDefinitionId)}&mode=1">
								<qp:message code="sc.sys.0006"></qp:message>
							</a>
						</qp:authorization>
					</div>
				</c:if>
			</form:form>
 		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>