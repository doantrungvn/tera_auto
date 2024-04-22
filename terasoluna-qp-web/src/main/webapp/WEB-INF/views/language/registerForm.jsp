<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.language"></qp:message></span></li>
         <li><span><qp:message code="sc.language.0001"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="languageSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/language/search"><qp:message code="sc.language.0068"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<script>
			function typeChangeLanguage(obj){
				var name = $(obj).val();
				$("#txtLanguageCode").text(name);
				$(obj).next("input[type='hidden']").val($(obj).find("option[value='"+name+"']").text());
			};
	
			function typeChangeCountry(obj){
				var name = $(obj).val();
				$("#txtCountryCode").text(name);
				$(obj).next("input[type='hidden']").val($(obj).find("option[value='"+name+"']").text());
			};
		</script>
		<form:form method="post" action="${pageContext.request.contextPath}/language/register" modelAttribute="languageForm">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.messagedesign.0007"/></span>
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
							<th><form:label path="languageCode"><qp:message code="sc.language.0005"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td>
								<form:select path="languageCode" cssClass="form-control qp-input-select" onchange="typeChangeLanguage(this)">
									<form:option value=""><qp:message code="sc.sys.0030"/></form:option>
										<c:forEach var="item" items="${CL_LANGUAGE_CODE}">
                                           <c:if test="${not fn:contains(languagecodes, item.key)}">
                                               <form:option value="${item.key}"><qp:message code="${CL_LANGUAGE_CODE.get(item.key) }"/></form:option>
                                           </c:if>
							        	</c:forEach>
								</form:select>
								<form:hidden path="languageName"/>	
							</td>
							<%-- <th><form:label path="languageCode"><qp:message code="sc.language.0006"/>&nbsp <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td>
								<form:select path="countryCode" cssClass="form-control qp-input-select" onchange="typeChangeCountry(this)">
									<form:option value=""><qp:message code="sc.sys.0030"/></form:option>
									<form:options items="${CL_COUNTRY_CODE}" />
								</form:select>
							</td> --%>
							<th><form:label path="languageCode"><qp:message code="sc.language.0007"/></form:label></th>
							<td style="border-right: none; padding-top:8px;"><span id="txtLanguageCode"><qp:formatText value="${languageForm.languageCode}"/></span></td>
						</tr>
						<tr>
							<th><form:label path="translateFlg"><qp:message code="sc.language.0065" /></form:label></th>
							<td ><form:checkbox path="translateFlg" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="cl.sys.0021" /></td>
							<th></th>
							<td></td>
						</tr> 
					</table>
				</div>
			</div>
			 <div class="qp-div-action">         
                    <qp:authorization permission="languageRegister">
                        <button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
                    </qp:authorization>
                </div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>