<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">	
		<qp:message code="sc.functiondesign.0009"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/init.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/functionmaster.js" ></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1}">
			<div id="wapper">
				<form:form method="post" action="${pageContext.request.contextPath}/functiondesign/delete" modelAttribute="functionDesignForm">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.functiondesign.0006"/></span>
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
									<th><qp:message code="sc.functiondesign.0002"/></th>
									<td class="word-wrap"><qp:formatText value="${functionDesignForm.functionName}" /></td>
									<th><qp:message code="sc.functiondesign.0003"/></th>
									<td class="word-wrap"><qp:formatText value="${functionDesignForm.functionCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.functionmaster.0007"/></th>
									<td class="word-wrap">
										<qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(functionDesignForm.functionType.toString())}" />
										<form:hidden path="functionType" value="${functionDesignForm.functionType}"/>
									</td>
									<th><qp:message code="sc.functiondesign.0005"/></th>
									<td class="word-wrap"><qp:formatText value="${functionDesignForm.actor}" /></td>
								</tr>
							</table>
						</div>
					</div>
						<div class="qp-div-action">
							<c:if test="${sessionScope.CURRENT_PROJECT.status eq functionDesignForm.status }">
								<qp:authorization permission="functiondesignDelete">
									<form:hidden path="functionId" />
									<form:hidden path="functionName" />
									<form:hidden path="functionCode" />
									<form:hidden path="remark" />
									<form:hidden path="updatedDate"/>
									<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
								</qp:authorization>
								<qp:authorization permission="functiondesignModify">
									<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/functiondesign/modify?functionId=${functionDesignForm.functionId}&mode=1"><qp:message code="sc.sys.0006" /></a>
								</qp:authorization>
							</c:if>
						</div>
				</form:form>
			</div>
		</c:if>
	</tiles:putAttribute>
	
</tiles:insertDefinition>