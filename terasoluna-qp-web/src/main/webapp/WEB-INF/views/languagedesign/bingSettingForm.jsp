<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="sc.languagedesign.0009"></qp:message></span></li>
         <li><span><qp:message code="sc.accountprofile.0036"></qp:message></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="languagedesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/languagedesign/search"><qp:message code="sc.languagedesign.0020"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		
		
		
		
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/languagedesign/languagedesignBingsetting" method="post" modelAttribute="bingTranslateSettingForm">
        <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.languagedesign.0026" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl_bing_setting_information">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th>
								<form:label path="bingClientId">
									<qp:message code="sc.accountprofile.0029" />&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td>
								<form:input path="bingClientId" value="${bingTranslateSettingForm.bingClientId}" cssClass="form-control qp-input-text qp-convention-name" maxlength="50" />
							</td>
							<th>
								<form:label path="bingClientSecret">
									<qp:message code="sc.accountprofile.0030" />&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td>
								<form:password path="bingClientSecret" value="${bingTranslateSettingForm.bingClientSecret}" cssClass="form-control qp-input-text qp-convention-code" maxlength="50" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="languagedesignBingsetting">
					<button type="submit" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0045" name="testFlag" value="true"><qp:message code="sc.accountprofile.0054" /></button>
				</qp:authorization>
				<qp:authorization permission="languagedesignBingsetting">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>