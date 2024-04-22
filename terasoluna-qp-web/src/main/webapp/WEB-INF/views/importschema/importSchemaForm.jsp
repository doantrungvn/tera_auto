<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="sc.importschema.0001"/></span></li>
         <%-- <li><span><qp:message code="sc.project.0013"/></span></li> --%>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/importschema/javascript/importschema.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=sys_project_importschema"></script>
		<script>
			$(document).ready(function() {
				$.qp.undoFormatNumericForm($("#tbl-database-setting"));
				$("#tbl-database-setting").find("#dbPort").on('focusout', function(e){
					$.qp.undoFormatNumericForm($("#tbl-database-setting"));	
				});
				$("#tbl-database-setting").find("#dbPort").on('change', function(e){
					$.qp.undoFormatNumericForm($("#tbl-database-setting"));	
				});
			});
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="importSchemaForm" action="${pageContext.request.contextPath}/importschema/importschema">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<%-- <div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.project.0010" /></span>
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
							<th><form:label path="projectName"><qp:message code="sc.project.0005"></qp:message></form:label></th>
							<td><qp:formatText value="${importSchemaForm.projectName}"></qp:formatText></td>
							<th><form:label path="projectCode"><qp:message code="sc.project.0006"></qp:message></form:label></th>
							<td><qp:formatText value="${importSchemaForm.projectCode}"></qp:formatText></td>
						</tr>
						<tr>
							<th><form:label path="status"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
							<td><input type="hidden" name="status" value="1"/><qp:message code="${CL_DESIGN_STATUS.get('1')}" /></td>
							<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
							<td><qp:formatText value="${importSchemaForm.remark}"></qp:formatText></td>
						</tr>
					</table>
				</div>
			</div> --%>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.importschema.0002"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl-database-setting">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path="dbType"><qp:message code="sc.project.0016" /></form:label></th>
							<td>
								<form:select path="dbType" onchange="changeDataType(this)">
									<form:options items="${CL_DATABASE_TYPE}"/>
								</form:select>
							</td>
							<th><form:label path="dbDriver"><qp:message code="sc.project.0022" /></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="dbDriver" maxlength="50" value="org.postgresql.Driver" readonly="true"/></td>
						</tr>
						<tr>
							<th id="dbNameLabelTH"><form:label path="dbName" id="dbNameLabel"></form:label>&nbsp;<label id="dbNameLabelR" class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td>
								<div id="dbNamePostges">
									<form:input type="text" cssClass="form-control qp-input-text" path="dbName" maxlength="50" value="${CL_DATABASE_TYPE.get(1)}"/>
								</div>
								<div id="dbNameOracle">
									<label class='radio-inline'><input type='radio' name='connection' id='sid' checked="checked" value='sid' onchange ="changeConnectionType()"/><qp:message code="sc.importschema.0006"/></label>
									<label class='radio-inline'><input type='radio' name='connection' id="service" value='service' onchange ="changeConnectionType()"/><qp:message code="sc.importschema.0007"/></label>
								</div>
								<form:hidden path="connectionType" value="${importSchemaForm.connectionType}"/>
							</td>
							<th><form:label path="schemaName" id="schemaName"><qp:message code="sc.importschema.0003"/></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input cssClass="form-control qp-input-text" path="schemaName" maxlength="50"/></td>
						</tr>
						<tr>
							<th><form:label path="dbHostName"><qp:message code="sc.project.0018" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="dbHostName" maxlength="50" /></td>
							<th><form:label path="dbPort"><qp:message code="sc.project.0019" /></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-serial" path="dbPort" maxlength="4"/></td>
						</tr>
						<tr>
							<th><form:label path="dbUser"><qp:message code="sc.project.0020" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="dbUser" maxlength="50" /></td>
							<th><form:label path="dbPassword"><qp:message code="sc.project.0021" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input type="password" cssClass="form-control qp-input-text" path="dbPassword" maxlength="50" autocomplete="new-password"/></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="importschema">
					<button type="submit" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0045" ><qp:message code="sc.importschema.0004"/></button>
					<form:hidden path="projectId" value="${importSchemaForm.projectId}"/>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>