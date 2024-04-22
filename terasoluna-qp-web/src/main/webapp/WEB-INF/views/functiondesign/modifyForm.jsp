<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.functiondesign"></qp:message></span></li>
         <li><span><qp:message code="sc.functiondesign.0010"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<%-- <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }"> --%>
			<qp:authorization permission="functionmasterSearch">
				<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
				<a class="com-link-popup" href="${pageContext.request.contextPath}/functiondesign/search"><qp:message code="sc.functiondesign.0008"/></a>
			</qp:authorization>
		<%-- </c:if> --%>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		
			<form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/functiondesign/modify" modelAttribute="functionDesignForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<c:if test="${notExistFlg ne 1}">
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
								<th><qp:message code="sc.functiondesign.0002"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name-row" path="functionName" maxlength="200" /></td>
								<th><qp:message code="sc.functiondesign.0003"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="functionCode" maxlength="200" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.functionmaster.0007"/></th>
								<td>
									<form:hidden path="functionType"/>
									<qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(functionDesignForm.functionType.toString())}" />
								</td>
	                           	<th><form:label path="actor"><qp:message code="sc.functiondesign.0005"/></form:label></th>
								<td><form:input path="actor" type="text" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200" /></td>
							</tr>
							<tr>
								<th><form:label path="moduleId"><qp:message code="sc.module.0007"/></form:label></th>
								<td>
									<qp:formatText value="${functionDesignForm.moduleIdAutocomplete }" />
	                            	<form:hidden path="moduleIdAutocomplete" value="${functionDesignForm.moduleIdAutocomplete}"/>
	                            	<form:hidden path="moduleId" value="${functionDesignForm.moduleId}"/>
								</td>
	                            <th>
	                            	<form:label path="remark"><qp:message code="sc.sys.0028" /></form:label>
	                            </th>
	                            <td><form:textarea path="remark" type="text" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
						<qp:authorization permission="functionmasterModify">
							<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
							<form:hidden path="updatedDate" />
				            <form:hidden path="updatedBy" />
				            <form:hidden path="functionId" value="${functionDesignForm.functionId}"/>
						</qp:authorization>
					</c:if>
				</div>
				</c:if>
			</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>