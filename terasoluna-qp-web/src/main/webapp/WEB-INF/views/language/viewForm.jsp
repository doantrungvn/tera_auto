<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.language.0002"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/messagedesign/messagedesign.js"></script>
	</tiles:putAttribute>
	<c:if test="${notExistFlg ne 1}">
	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/language/delete" modelAttribute="languageForm" method="post">
			<form:hidden path="languageCode"/>
			<form:hidden path="countryCode" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.language.0004"/></span>
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
							<th><qp:message code="sc.language.0010"/></th>
							<td><qp:formatText value="${languageForm.languageName}"/></td>
							<th><qp:message code="sc.language.0007"/></th>
							<td><qp:formatText value="${languageForm.languageCode }"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.language.0008"/></th>
							<td><qp:formatText value="${languageForm.countryCode }"/></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<c:if test="${languageForm.languageCode != 'en'}">
					<qp:authorization permission="languageDelete">
						<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" name="mode" value="3"><qp:message code="sc.sys.0008"/></button>
					</qp:authorization>
				</c:if>
			</div>
		</form:form>
		<br />
			<%-- <form:form action="${pageContext.request.contextPath}/language/translate" modelAttribute="languageForm" method="post">
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="languageCode"/>
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.language.0010"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="15%" />
								<col width="35%" />
								<col width="15%" />
								<col width="35%" />
							</colgroup>
							<tr>
								<th><qp:message code="sc.language.0069"/></th>
								<td><qp:formatText value="${languageForm.languageName}"/></td>
								<th><qp:message code="sc.language.0063"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
									<form:hidden path="countryCode" value="${languageForm.countryCode}"/>
									<form:hidden path="regionCode" value="${languageForm.regionCode}"/>
									<form:hidden path="languageName" value="${languageForm.languageName}"/>
									<form:hidden path="toCountryCode" value="${languageForm.toCountryCode}" />
									<qp:autocomplete name="toLanguageCode" optionValue="output02" optionLabel="optionLabel" selectSqlId="getLanguageForAutocomplete" value="${languageForm.toLanguageCode}" displayValue="${languageForm.toLanguageCodeAutocomplete}" arg01="${languageForm.languageCode}"  mustMatch="true" maxlength="200" onChangeEvent="changeToLanguageOfSystemAC"/>
								</td>
							</tr>

						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="languageTranslate">
						<button type="button" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0035"><qp:message code="sc.language.0073"/></button>
					</qp:authorization>
				</div>
			</form:form> --%>
	</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>