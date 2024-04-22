<tiles:insertDefinition name="layouts">

    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="sc.webservicetokenmanagement.0001"/></span></li>
         <li><span><qp:message code="sc.webservicetokenmanagement.0003"/></span></li>
    </tiles:putAttribute>
	
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="webservicetokenmanagementSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/webservicetokenmanagement/search"><qp:message code="sc.webservicetokenmanagement.0002"/></a>
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
		<form:form method="post" modelAttribute="webServiceTokenManagementForm" action="${pageContext.request.contextPath}/webservicetokenmanagement/register">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.webservicetokenmanagement.0008"/></span>
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
							<th>
								<form:label path="projectName">
									<qp:message code="sc.project.0005"/>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
								<form:hidden path="projectName"/>
							</th>
							<td>							
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel"  selectSqlId="getAllProjectForAutocomplete" 
									value="${webServiceTokenManagementForm.projectId}" name="projectId" displayValue="${webServiceTokenManagementForm.projectName}" 
									mustMatch="true" maxlength="200" onSelectEvent="selectProject" onChangeEvent="changeProject">
								</qp:autocomplete>
							</td>
							<th>
									<qp:message code="sc.project.0006"/>&nbsp;
									
							</th>
							<td>
								<label id = "projectCode" name ="projectCode"></label>
								<form:hidden path="projectCode"/>
							</td>
						</tr>
						<tr>
							<th>
								<form:label path="clientId">
									<qp:message code="sc.webservicetokenmanagement.0005"/>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td>							
								<form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="clientId" autofocus="true" />
							</td>
							<th>
								<form:label path="clientSecret">
									<qp:message code="sc.webservicetokenmanagement.0006"/>&nbsp;
									<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label>
							</th>
							<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="clientSecret" autofocus="true" /></td>
					</table>
				</div>
			</div>	
			
			<div class="qp-div-action">
				<qp:authorization permission="webservicetokenmanagementRegister">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>