<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.functiondesign"></qp:message></span></li>
         <li><span><qp:message code="sc.functiondesign.0007"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
	<qp:authorization permission="functiondesignSearch">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/functiondesign/search"><qp:message code="sc.functiondesign.0008"/></a>
	</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			$(document).ready(function() {
				var $radios = $('input:radio[name=functionType]');
				if($radios.is(':checked') === false) {
					$radios.filter('[value=0]').prop('checked', true);
					changeFunctionType();
				}
			});
			
			function changeFunctionType(obj) {
				var value = $("#tblForm").find('input[name=functionType]:checked').val();
				$("#moduleIdAutocompleteId").data("ui-autocomplete")._trigger("change")
				$("#moduleIdAutocompleteId").attr("arg04",value);
				$("#moduleIdAutocompleteId").val("");
				$("#moduleId").val("");
			}
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="functionDesignForm" action="${pageContext.request.contextPath}/functiondesign/register">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.functiondesign.0006"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tblForm">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path="functionName"><qp:message code="sc.functiondesign.0002"/></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input path="functionName" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200" /></td>
							<th><form:label path="functionCode"><qp:message code="sc.functiondesign.0003"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td><form:input path="functionCode" type="text" cssClass="form-control qp-input-text qp-convention-code-row" maxlength="200" /></td>
						</tr>
						<tr>
							<th><form:label path="functionType"><qp:message code="sc.functionmaster.0007"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_FUNCTION_DESIGN_TYPE}">
									<label><form:radiobutton path="functionType" onchange="changeFunctionType(this)" value="${item.key}" class="qp-input-radio qp-input-radio-margin" /><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(item.key)}" /></label>
								</c:forEach>
								
							</td>
							<th><form:label path="actor"><qp:message code="sc.functiondesign.0005"/></form:label></th>
							<td><form:input path="actor" type="text" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200" /></td>
						</tr>
						<tr>
							<th><form:label path="moduleId"><qp:message code="sc.module.0007"/></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td>
								<qp:autocomplete
									name="moduleId" 
									optionValue="optionValue" 
									optionLabel="optionLabel" 
									selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" 
									value="${functionDesignForm.moduleId}" 
									displayValue="${functionDesignForm.moduleIdAutocomplete}" 
									arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
									arg04="${functionDesignForm.functionType}"
									arg03="1"
									mustMatch="true" 
									maxlength="200">
								</qp:autocomplete>
							</td>
							<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
							<td><form:textarea path="remark" type="text" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="functiondesignRegister">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>