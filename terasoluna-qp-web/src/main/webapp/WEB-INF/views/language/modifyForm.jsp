
<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.messagedesign"></qp:message></span></li>
         <li><span><qp:message code="sc.sys.0006"/> <qp:message code="sc.messagedesign.0018"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/messagedesign/search"><qp:message code="sc.sys.0001"/><qp:message code="sc.messagedesign.0001"/></a>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="languageForm" action="${pageContext.request.contextPath}/language/modify">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.message.0005"/> <qp:message code="sc.messagedesign.0011"/></span>
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
							<th><form:label path="languageCode"><qp:message code="sc.language.0001"/></form:label> <label class="qp-required-field">(*)</label></th>
							<td><form:input path="languageCode" type="text" cssClass="form-control qp-input-text"></form:input></td>
							<th><form:label path="languageName"><qp:message code="sc.language.0002"/></form:label> <label class="qp-required-field">(*)</label></th>
							<td><form:input path="languageName" type="text" cssClass="form-control qp-input-text"></form:input></td>
						</tr>
						<tr>
						   <th><form:label path="countryCode"><qp:message code="sc.language.0003"/></form:label></th>
							<td><form:input path="countryCode" type="text" cssClass="form-control qp-input-text"></form:input></td>
							<th><form:label path="regionCode"><qp:message code="sc.language.0004"/></form:label></th>
							<td><form:input path="regionCode" type="text" cssClass="form-control qp-input-text"></form:input></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="languageModify">
					<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"/></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>