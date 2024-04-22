<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.licensedesign"></qp:message></span></li>
         <li><span><qp:message code="sc.permission.licensedesignRegister.remark"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
	<qp:authorization permission="licensedesignSearch">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/licensedesign/search"><qp:message code="sc.permission.licensedesignSearch.remark"/></a>
	</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#projectCode").text($("#projectName").val());
			});
			
			function selectProject(event){
				 if(event.item != null || event.item == ""){
					// Label
				 	$("#projectCode").text(event.item.output01);
					// Input hidden
				 	$("input[name$='projectCode']").val(event.item.output01);
				 	$("#projectName").val(event.item.optionLabel);
				 }
			}
			
			function changeProject(event){
				var projectId = $("input[name='projectId']").val();
				if(projectId == null || projectId == ""){
					$("#projectCode").text("");
					$("input[name$='projectCode']").val("");
				 	$("#projectName").val("");
				}
			}
		</script>	
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="licenseDesignForm" action="${pageContext.request.contextPath}/licensedesign/register">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.licensedesign.0011"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tblForm">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr class="success form-inline">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.licensedesign.0012"/></td>
						</tr>
						<tr>
							<th><form:label path="customerName"><qp:message code="sc.licensedesign.0001"/></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input path="customerName" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="400" /></td>
							<th><form:label path="customerCode"><qp:message code="sc.licensedesign.0000"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td><form:input path="customerCode" type="text" cssClass="form-control qp-input-text qp-convention-code-row" maxlength="150" /></td>
						</tr>
						<tr>
							<th><form:label path="tel"><qp:message code="sc.licensedesign.0004"/></form:label></th>
							<td><form:input path="tel" cssClass="form-control qp-input-text" maxlength="50" /></td>
							<th><form:label path="email"><qp:message code="sc.licensedesign.0005"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td><form:input path="email" type="text" cssClass="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="address"><qp:message code="sc.licensedesign.0006"/></form:label></th>
							<td><form:textarea path="address" type="text" rows="5" cssClass="form-control qp-input-textarea" maxlength="500" /></td>
							<th></th>
							<td></td>
						</tr>
						<tr class="success form-inline">
									<td style="text-align: left;" colspan="4"><qp:message code="sc.licensedesign.0011"/></td>
						</tr>
						<tr>
							<th>
								<form:label path="projectName"> <qp:message code="sc.project.0005"/>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
								<form:hidden path="projectName"/>
							</th>
							<td>							
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getProjectForAutocomplete" 
									value="${licenseDesignForm.projectId}" name="projectId" displayValue="${licenseDesignForm.projectName}" 
									mustMatch="true" maxlength="200" onSelectEvent="selectProject" onChangeEvent="changeProject" arg01="${sessionScope.ACCOUNT_PROFILE.accountId}">
								</qp:autocomplete>
							</td>
							<th>
								<qp:message code="sc.project.0006"/>
							</th>
							<td>
								<label id = "projectCode" path ="projectCode"></label>
								<form:hidden path="projectCode"/>
							</td>
						</tr>
						<tr>
							<th><form:label path="num"><qp:message code="sc.licensedesign.0007"/>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td><form:input path="num" cssClass="form-control qp-input-serial" maxlength="50" /></td>
							<th><form:label path="version"><qp:message code="sc.licensedesign.0008"/></form:label></th>
							<td><form:input path="version" cssClass="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.licensedesign.0009" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td>
								<div class='input-group date qp-input-datetimepicker-detail'>
									<form:input cssClass="form-control qp-input-text" path="startDate" value="${licenseDesignForm.startDate}"/>
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
						    </td>
							<th><qp:message code="sc.licensedesign.0010" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td>
								<div class='input-group date qp-input-datetimepicker-detail'>
									<form:input cssClass="form-control qp-input-text" path="expiredDate" value="${licenseDesignForm.expiredDate}" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
						</tr>
						<tr>
								<form:hidden path="status"/>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="licensedesignRegister">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					<form:hidden path="accountId" value="${account.accountId}" />
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>