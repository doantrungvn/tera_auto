<tiles:insertDefinition name="layouts">

	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generate/javascript/validateHTML.js"></script>
	</tiles:putAttribute>
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span><qp:message code="sc.generation.0012"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/screendesign/generateHTML" method="POST" modelAttribute="generateHTMLForm">
			<form:hidden path="projectName"/>
			<form:hidden path="projectCode"/>
			<form:hidden path="projectId" value="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"/>
			<form:hidden path="scopeGenerate" value="project"/>
			<input type="hidden" id="fileName" value="${fileName}">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generatesourcecode.0090"/></span>
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
							<th style="text-align: left;">
								<qp:message code="sc.generatesourcecode.0002"/>
							</th>
							<th style="text-align: left;" colspan="3">
								<label><input id="selectType1" name="selectType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1" checked="checked" onclick="selectTypeScope(this)"><qp:message code="sc.generatesourcecode.0003"/></label>
								<label><input id="selectType2" name="selectType" class="qp-input-radio-margin qp-input-radio" type="radio" value="2" onclick="selectTypeScope(this)"><qp:message code="sc.generatesourcecode.0004"/></label>
							</th>
						</tr>
						<tr id="project">
							<td colspan="4">
								<table class="table table-bordered qp-table-form">
									<colgroup>
										<col width="19.55%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th><qp:message code="sc.project.0005"/></th>
										<td><qp:formatText value="${generateHTMLForm.projectName}" /></td>
										<th><qp:message code="sc.project.0006"/></th>
										<td><qp:formatText value="${generateHTMLForm.projectCode}" /></td>
									</tr>
									<tr>
			                            <th><qp:message code="sc.sys.0055" /></th>
			                            <td><input type="hidden" name="status" value="${generateHTMLForm.status}"/><qp:message code="${CL_DESIGN_STATUS.get(generateHTMLForm.status.toString())}" /></td>
			                            <th><form:label path="remark"><qp:message code="sc.sys.0028"/></form:label></th>
										<td><qp:formatText value="${generateHTMLForm.remark}" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr id="listModule" class="hidden">
							<td colspan="4">
								<table class="table table-bordered qp-table-list-none-action" id="tblListModule">
                            <colgroup>
                                <col width="4%"/>
                                <col width="40%"/>
                                <col width="30%"/>
                                <col width="10%"/>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="btnCheckAll" onclick="changeChecked(this);"></th>
                                    <th><qp:message code="sc.module.0007"/></th>
                                    <th><qp:message code="sc.module.0008"/></th>
                                    <th><qp:message code="sc.sys.0055" /></th>
                                </tr>
                            </thead>
                            <c:forEach var="module" items="${generateHTMLForm.modules}" varStatus="rowStatus">
                                <tr>
                                	<td/>
                                    <td>
                                    	<label><form:checkbox path="listModuleId" value="${module.moduleId}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/>
                                    	<qp:formatText value="${module.moduleName}"/></label>
                                    	<input type="hidden" name="modules[${rowStatus.index}].moduleName" value="${module.moduleName }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].moduleId" value="${module.moduleId }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].createdBy" value="${module.createdBy }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].createdDate" value="${module.createdDate }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].updatedBy" value="${module.updatedBy }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].updatedDate" value="${module.updatedDate }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].businessTypeId" value="${module.businessTypeId }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].confirmationType" value="${module.confirmationType }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].completionType" value="${module.completionType }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].remark" value="${module.remark }"/>
                                    	<input type="hidden" name="modules[${rowStatus.index}].selectedGenerate" value="${module.selectedGenerate }"/>
                                    </td>
                                    <td class="qp-output-text"><input type="hidden" name="modules[${rowStatus.index}].moduleCode" value="${module.moduleCode }"/><qp:formatText value="${module.moduleCode}"/></td>
                                    <td class="qp-output-text"><input type="hidden" name="modules[${rowStatus.index}].status" value="${module.status }"/><qp:message code="${CL_DESIGN_STATUS.get(module.status.toString())}"  /></td>
                                    
                                </tr>
                            </c:forEach>
                        </table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<div class="qp-div-action">
			<qp:authorization permission="generationGeneratehtml">
				<input type="hidden" id="module" value="<qp:message code="sc.screendesign.0322" />" />
				<button type="submit" class="btn qp-button qp-dialog-confirm" onclick="requireModule" id="btnSubmit" messageId="inf.sys.0025"><qp:message code="sc.generation.0008" /></button>
			</qp:authorization>
		</div>
	</form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>