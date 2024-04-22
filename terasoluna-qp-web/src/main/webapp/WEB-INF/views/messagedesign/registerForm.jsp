<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.messagedesign"></qp:message></span></li>
		 <li><span><qp:message code="sc.messagedesign.0021"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="messagedesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/messagedesign/search"><qp:message code="sc.messagedesign.0020" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/messagedesign/messagedesign.js"></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/messagedesign/register" modelAttribute="messageDesignForm">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<script id="tbl_list_result-template" type="text/template">
				<tr>
					<td class="qp-output-fixlength tableIndex">1</td>
					<td><input type="text" class="form-control" name="multipleMessageDesignForm[0].messageString" maxlength="255" /></td>
					<td><textarea class="form-control qp-input-textarea" name="multipleMessageDesignForm[0].remark"/></td>
					<td>
						<form:select cssClass="form-control qp-input-select" path="multipleMessageDesignForm[0].messageType">
							<form:option value=""><qp:message code="sc.sys.0030" /></form:option>
							<form:option value="sc"><qp:message code="${CL_MESSAGE_TYPE.get('sc')}"/></form:option>
											<form:option value="inf"><qp:message code="${CL_MESSAGE_TYPE.get('inf')}"/></form:option> 
											<form:option value="wrn"><qp:message code="${CL_MESSAGE_TYPE.get('wrn')}"/></form:option> 
											<form:option value="err"><qp:message code="${CL_MESSAGE_TYPE.get('err')}"/></form:option> 
						</form:select>
						<form:hidden path="multipleMessageDesignForm[0].messageLevel" />
					</td>
					<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove message design" onclick="$.qp.removeRowJS('tbl_list_result', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
				</tr>
			</script>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.messagedesign.0007" /></span>
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
							<%-- <th><form:label path="projectId"><qp:message code="sc.project.0005" /></form:label></th>
							<td><qp:autocomplete name="projectId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllProjectForAutocomplete" value="${messageDesignForm.projectId}" displayValue="${messageDesignForm.projectIdAutocomplete}" onChangeEvent="changeProjectAC" mustMatch="true" maxlength="200" /></td> --%>
							<th><form:label path="moduleId"><qp:message code="sc.module.0007" /></form:label></th>
							<td>
								<qp:autocomplete name="moduleId" optionValue="optionValue" optionLabel="optionLabel" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" arg02="20" arg03="1" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" value="${messageDesignForm.moduleId}" displayValue="${messageDesignForm.moduleIdAutocomplete}" onSelectEvent="updateModuleAC" mustMatch="true" maxlength="200" />
								<form:hidden path="moduleCode" />
							</td>
							<th><form:label path="languageCode"><qp:message code="sc.languagedesign.0002" />&nbsp; <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
							<td style="border-right: none">
								<qp:autocomplete name="languageId" optionValue="output02" optionLabel="optionLabel" value="${messageDesignForm.languageId}"  selectSqlId="getLanguageDesignForAutocomplete" displayValue="${messageDesignForm.languageIdAutocomplete}" onChangeEvent="updateCountryAC" arg02="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" mustMatch="true" maxlength="200" />
								<form:hidden path="languageCode" />
								<form:hidden path="countryCode" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.messagedesign.0008" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list" id="tbl_list_result" data-ar-mrows ="200">
						<colgroup>
							<col />
							<col width="40%" />
							<col width="40%" />
							<col width="20%" />
							<col />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.message.0002" />&nbsp; <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<th><qp:message code="sc.sys.0028" /></th>
								<th><qp:message code="sc.message.0006" />&nbsp; <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${messageDesignForm.multipleMessageDesignForm}" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex"><qp:formatInteger value="${status.count}" /></td>
									<td><form:input type="text" class="form-control" path="multipleMessageDesignForm[${status.index}].messageString" maxlength="255" /></td>
									<td><form:textarea cssClass="form-control qp-input-textarea" path="multipleMessageDesignForm[${status.index}].remark" maxlength="2000" /></td>
									<td>
									   <form:select cssClass="form-control qp-input-select" path="multipleMessageDesignForm[${status.index}].messageType">
											<form:option value=""><qp:message code="sc.sys.0030" /></form:option>
											<form:option value="sc"><qp:message code="${CL_MESSAGE_TYPE.get('sc')}"/></form:option>
											<form:option value="inf"><qp:message code="${CL_MESSAGE_TYPE.get('inf')}"/></form:option> 
											<form:option value="wrn"><qp:message code="${CL_MESSAGE_TYPE.get('wrn')}"/></form:option> 
											<form:option value="err"><qp:message code="${CL_MESSAGE_TYPE.get('err')}"/></form:option> 
											<%-- <form:option value="cl"><qp:message code="${CL_MESSAGE_TYPE.get('cl')}"/></form:option> --%> 
										</form:select>
										<form:hidden path="multipleMessageDesignForm[${status.index}].messageLevel" />
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove message design" onclick="$.qp.removeRowJS('tbl_list_result', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="qp-add-left">
						<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="messagedesignRegister">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
			<form:hidden path="screenType" value="0" />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>