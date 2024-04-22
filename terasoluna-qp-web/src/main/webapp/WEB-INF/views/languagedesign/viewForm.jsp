<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.languagedesign.0024"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/messagedesign/messagedesign.js"></script>
	</tiles:putAttribute>
	<c:if test="${not languageDesignForm.hasErrors}">
		<tiles:putAttribute name="body">
			<form:form action="${pageContext.request.contextPath}/languagedesign/delete" modelAttribute="languageDesignForm" method="post">
				<form:hidden path="languageId"/>
				<form:hidden path="updatedDate" value="${languageDesignForm.updatedDate}" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.languagedesign.0008"/></span>
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
								<th><qp:message code="sc.languagedesign.0002"/></th>
								<td><qp:formatText value="${languageDesignForm.languageName}"/></td>
								<th><qp:message code="sc.languagedesign.0001"/></th>
								<td><qp:formatText value="${languageDesignForm.languageCode}"/>_<qp:formatText value="${languageDesignForm.countryCode}"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<c:if test="${languageDesignForm.languageCode != 'en'}">
					   <c:if test="${languageDesignForm.isDefault != 'isDefault' }">
					       <qp:authorization permission="languagedesignDelete">
                                <form:hidden path="languageCode"/>
                                <button type="button" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008"/></button>
                            </qp:authorization>
					   </c:if>
							<qp:authorization permission="languagedesignModify">
								<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/languagedesign/modify?languageId=${languageDesignForm.languageId}&mode=1"><qp:message code="sc.sys.0006"/></a>
							</qp:authorization>
					</c:if>
				</div>
			</form:form>
				<br />
			<form:form action="${pageContext.request.contextPath}/languagedesign/translate" modelAttribute="languageDesignForm" method="post">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="languageId"/>
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.languagedesign.0010"/></span>
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
								<th><qp:message code="sc.languagedesign.0018"/></th>
								<td><qp:formatText value="${languageDesignForm.languageName}"/></td>
								<th><qp:message code="sc.languagedesign.0011"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
									<form:hidden path="languageCode" value="${languageDesignForm.languageCode}"/>
									<form:hidden path="countryCode" value="${languageDesignForm.countryCode}"/>
									<form:hidden path="regionCode" value="${languageDesignForm.regionCode}"/>
									<form:hidden path="languageName" value="${languageDesignForm.languageName}"/>
									<form:hidden path="toCountryCode" value="${languageDesignForm.toCountryCode}" />
									<form:hidden path="toLanguageCode" value="${languageDesignForm.toLanguageCode}"/>
									<qp:autocomplete name="toLanguageId" optionValue="output02" optionLabel="optionLabel" selectSqlId="getLanguageDesignForAutocomplete" value="${languageDesignForm.toLanguageId}" displayValue="${languageDesignForm.toLanguageIdAutocomplete}" arg01="${languageDesignForm.languageId}"  mustMatch="true" maxlength="200" onChangeEvent="changeToLanguageAC" arg02="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"/>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.languagedesign.0010"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
									<label><form:radiobutton path="translateMode" value="0" cssClass="qp-input-radio-margin qp-input-radio" /><qp:message code="sc.languagedesign.0014"/> <br/></label>
									<label><form:radiobutton path="translateMode" value="1" cssClass="qp-input-radio-margin qp-input-radio" /><qp:message code="sc.languagedesign.0015"/> <br/></label>
								</td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="languagedesignTranslate">
						<form:hidden path="updatedDate" value="${languageDesignForm.updatedDate}" />
						<form:hidden path="translateMode" value="${languageDesignForm.translateMode }"/>
						<button type="button" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0035"><qp:message code="sc.languagedesign.0013"/></button>
					</qp:authorization>
				</div>
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>