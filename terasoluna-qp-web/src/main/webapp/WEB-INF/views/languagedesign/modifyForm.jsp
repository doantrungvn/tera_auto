
<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.languagedesign"></qp:message></span></li>
         <li><span><qp:message code="sc.languagedesign.0022"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/languagedesign/search"><qp:message code="sc.languagedesign.0020"/></a>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/languagedesign/languagedesign.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#option").text();
			});
			
			/* var name = $("#option").val();
			$("#txtCountryCode").text(name);
			$(obj).next("input[type='hidden']").val($(obj).find("option[value='"+name+"']").text()); */
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="languageDesignForm" action="${pageContext.request.contextPath}/languagedesign/modify">
			<c:if test="${notExistFlg ne 1 }">
				<form:hidden path="languageId" />
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.languagedesign.0008"/></span>
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
								<th><form:label path="languageCode"><qp:message code="sc.languagedesign.0006"/> <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
								<td>
									<form:select path="languageCode" cssClass="form-control qp-input-select" onchange="typeChangeLanguage(this, 'modify')">
										<c:forEach var="item" items="${CL_LANGUAGE_CODE}">
		                                    <c:if test="${not fn:contains(languageDesignForm.currentLanguageProject , item.key)}">
		                                        <form:option value="${item.key}"><qp:message code="${CL_LANGUAGE_CODE.get(item.key)}"/></form:option>
		                                    </c:if>
							        	</c:forEach>
									</form:select>
									<form:hidden path="languageName"/>
								</td>
								<th><form:label path="languageCode"><qp:message code="sc.languagedesign.0001"/></form:label></th>
								<td style="border-right: none; padding-top:8px;">
									<span id="txtLanguageCode"><qp:formatText value="${languageDesignForm.languageCode}"/></span>
								</td>
							</tr>
						</table>
					</div>
				</div>
				
				<div class="qp-div-action">
					<qp:authorization permission="languagedesignModify">
						<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"/></button>
						<form:hidden path="updatedDate" />
						<form:hidden path="updatedBy" />
					</qp:authorization>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>