<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.licensedesign"></qp:message></span></li>
		 <li><span><qp:message code="sc.permission.licensedesignModify.remark"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/licensedesign/search"><qp:message code="sc.permission.licensedesignSearch.remark"/></a>
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
			<form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/licensedesign/modify" modelAttribute="licenseDesignForm">
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<c:if test="${notExistFlg ne 1}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.licensedesign.0011"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
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
								<th><form:label path="customerName"><qp:message code="sc.licensedesign.0001"/></form:label> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name-row" path="customerName" maxlength="400" /></td>
								<th><form:label path="customerCode"><qp:message code="sc.licensedesign.0000"/></form:label> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="customerCode" maxlength="150" /></td>
							</tr>
							<tr>
								<th><form:label path="tel"><qp:message code="sc.licensedesign.0004"/></form:label> &nbsp;</th>
								<td><form:input type="text" cssClass="form-control qp-input-text" path="tel" maxlength="50" /></td>
								<th><form:label path="email"><qp:message code="sc.licensedesign.0005"/></form:label> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text" path="email" maxlength="50" /></td>
							</tr>
							<tr>
								<th><form:label path="address"><qp:message code="sc.licensedesign.0006"/></form:label> &nbsp;</th>
								<td><form:textarea path="address" type="text" rows="5" cssClass="form-control qp-input-textarea" maxlength="500" /></td>
								<th></th>
							</tr>
							<tr class="success form-inline">
								<td style="text-align: left;" colspan="4"><qp:message code="sc.licensedesign.0011"/></td>
							</tr>
							<tr>
								<th>
									<form:label path="projectName">
										<qp:message code="sc.project.0005"/>&nbsp;
										<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
									</form:label>
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
									<label id = "projectCode"><qp:formatText value="${licenseDesignForm.projectCode}" /></label>
									<form:hidden path="projectCode"/>
								</td>
							</tr>
							<tr>
								<th><form:label path="num"><qp:message code="sc.licensedesign.0007"/></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><form:input type="text" cssClass="form-control qp-input-serial" path="num"/></td>
								<th><form:label path="version"><qp:message code="sc.licensedesign.0008"/></form:label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text" path="version" maxlength="150" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.licensedesign.0009"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
									<div class='input-group date qp-input-datetimepicker-detail'>
										<form:input cssClass="form-control qp-input-text" path="startDate" size="50" value="${licenseDesignForm.startDate}" />
										<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									</div>
								</td>
								<th><qp:message code="sc.licensedesign.0010"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
								<div class='input-group date qp-input-datetimepicker-detail'>
									<form:input cssClass="form-control qp-input-text" path="expiredDate" size="50" value="${licenseDesignForm.expiredDate}" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</tr>
							<tr>
								<form:hidden path="status"/>
								<form:hidden path="projectId" value="${sessionScope.CURRENT_PROJECT.projectId}"/>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
						<qp:authorization permission="licensedesignModify">
							<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
							<form:hidden path="licenseId" value="${licenseDesignForm.licenseId}"/>
							<form:hidden path="updatedDate" />
							<form:hidden path="updatedBy" />
						</qp:authorization>
					</c:if>
				</div>
				</c:if>
			</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>