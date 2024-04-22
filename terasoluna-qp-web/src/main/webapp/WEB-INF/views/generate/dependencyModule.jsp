<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span>Dependency Module</span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="moduleSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/module/search"><qp:message code="sc.module.0019" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="dependencyModuleForm" action="${pageContext.request.contextPath}/generation/dependencymodule">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
		<form:hidden path="projectId"/>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">Dependency of module</span>
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
							<th><form:label path="moduleId"><qp:message code="sc.screendesign.0006" /></form:label></th>
							<td><input type="hidden" name = "moduleName" value="${dependencyModuleForm.moduleName}"/><qp:formatText value="${dependencyModuleForm.moduleName}" /></td>
							<th><qp:message code="sc.module.0008" /></th>
							<td><input type="hidden" name = "moduleCode" value="${dependencyModuleForm.moduleCode}"/><qp:formatText value="${dependencyModuleForm.moduleCode}" /></td>
							<td><form:input type="hidden" path = "moduleId" value = "${dependencyModuleForm.moduleId}"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0055" /></th>
							<td><input type="hidden" name = "status" value="${dependencyModuleForm.status}"/><qp:message code="${CL_DESIGN_STATUS.get(dependencyModuleForm.status.toString())}" /></td>
							<th><qp:message code="sc.businesstype.0001" /></th>
							<td><input type="hidden" name = "businessTypeName" value="${dependencyModuleForm.businessTypeName}"/><qp:formatText value="${dependencyModuleForm.businessTypeName}" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">All Module in Project</span>
				</div>
				<div class="panel-body">
					<table id="tmp_list_table" class="table table-bordered qp-table-list">
						<colgroup>
							<col />
							<col width="40%" />
							<col width="40%" />
							<col width="15%" />
							<col width="5%" />
						</colgroup>
					<thead>
					<tr>
						<th></th>
						<th><qp:message code="sc.module.0007" /></th>
						<th><qp:message code="sc.module.0008" /></th>
						<th><qp:message code="sc.sys.0055" /></th>
						<th></th>
					</tr>
					</thead>
						<tbody>
							<c:forEach var="item" items="${dependencyModuleForm.listModuleDependency}" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex"><qp:formatInteger value="${status.count}" /></td>
									<td class="qp-output-text"><qp:formatText value="${item.moduleName}"/></td>
									<td class="qp-output-text"><qp:formatText value="${item.moduleCode}"/></td>
									<td class="qp-output-text"><qp:message code="${CL_DESIGN_STATUS.get(item.status.toString())}"/></td>
									<td style="text-align: center;"> <input type="checkbox" name="listModuleDependency[${status.index}]" /> </td>
								</tr>
							</c:forEach>
							<c:if test="${empty dependencyModuleForm.listModuleDependency}">
									<tr>
										<td colspan="5"><qp:message code="inf.sys.0013"/></td>
									</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="generationGeneratescreen">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>