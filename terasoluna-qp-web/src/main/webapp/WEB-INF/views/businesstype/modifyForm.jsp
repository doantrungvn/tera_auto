<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.businesstype"></qp:message></span></li>
		 <li><span><qp:message code="sc.businesstype.0008"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="businesstypeSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/businesstype/search"><qp:message code="sc.businesstype.0006" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="businessTypeForm" action="${pageContext.request.contextPath}/businesstype/modify">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesstype.0004" /></span>
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
								<th><form:label path="businessTypeName"><qp:message code="sc.businesstype.0001" /></form:label>&nbsp <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name-row" path="businessTypeName" maxlength="200" /></td>
								<th><form:label path="businessTypeCode"><qp:message code="sc.businesstype.0002" /></form:label>&nbsp <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="businessTypeCode" maxlength="50" /></td>
							</tr>
							<tr>
								<th><form:label path="parentBusinessTypeName"><qp:message code="sc.businesstype.0003" /></form:label></th>
								<td><jsp:include page="parentBusinessTypeTree.jsp" /></td>
								<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
								<td><form:textarea path="remark" type="text" rows="3" cssClass="form-control qp-input-textarea" maxlength="2000"></form:textarea></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="businesstypeRegister">
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
						<form:hidden path="updatedDate" />
						<form:hidden path="updatedBy" />
					</qp:authorization>
				</div>
				<form:hidden path="businessTypeId" />
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>