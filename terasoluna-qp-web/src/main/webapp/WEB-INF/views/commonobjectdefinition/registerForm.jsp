<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=businesslogicdesign_sys"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialCommonTable.js"></script>

		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/commonobjectdefinition/javascript/commonobjectdefinition.js"></script>

		<script type="text/javascript">
			var returnType = 1;
			var CL_EX_DATATYPE = {}//CL_QP_Datatype
			<c:forEach items="${CL_EX_DATATYPE}" var="item">
			CL_EX_DATATYPE['${item.key}'] = '${item.value}';
			</c:forEach>
			$(document).ready(function() {
				reIndexAllRowOfMenu();
			});

			var MAX_NESTED_OBJECT = '${CL_SYSTEM_SETTING["maxNestedObject"]}';
		</script>

		<!-- Begin include business check component -->
		<jsp:include page="rowTemplate.jsp" />
		<!-- End include business check component -->
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.commonobjectdefinition"></qp:message></span></li>
         <li><span><qp:message code="sc.commonobjectdefinition.0006"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="commonobjectdefinitionSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/commonobjectdefinition/search"><qp:message code="sc.commonobjectdefinition.0004"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/commonobjectdefinition/register" modelAttribute="commonObjectDefinitionForm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="qp-heading-text"><qp:message code="sc.commonobjectdefinition.0006"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="obj-infor-master-tbl">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path="commonObjectDefinitionName">
									<qp:message code="sc.commonobjectdefinition.0002"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label></th>
							<td><form:input path="commonObjectDefinitionName" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200" /></td>
							<th><form:label path="commonObjectDefinitionCode">
									<qp:message code="sc.commonobjectdefinition.0003"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</form:label></th>
							<td><form:input path="commonObjectDefinitionCode" cssClass="form-control qp-input-text qp-convention-code-row out-focus-lower" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="moduleId">
									<qp:message code="sc.module.0007" />
								</form:label></th>
							<td><qp:autocomplete optionValue="optionValue" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" name="moduleId" 
									value="${commonObjectDefinitionForm.moduleId}" displayValue="${commonObjectDefinitionForm.moduleIdAutocomplete}" 
									optionLabel="optionLabel" arg01="${sessionScope.CURRENT_PROJECT.projectId }" onChangeEvent="changeModule">
								</qp:autocomplete></td>
							<th><form:label path="remark">
									<qp:message code="sc.sys.0028" />
								</form:label></th>
							<td><form:textarea path="remark" type="text" rows="2" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-search-result" id="tableSelection">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.commonobjectdefinition.0008"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list" id="tbl_attribute_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="changeRowOfAttributeCallBackfunction">
						<colgroup>
							<col width="40px" />
							<col width="450px" />
							<col width="50%" />
							<col width="50%" />
							<col />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th><qp:message code="sc.commonobjectdefinition.0009"></qp:message></th>
								<th><qp:message code="sc.commonobjectdefinition.0010"></qp:message></th>
								<th><qp:message code="sc.commonobjectdefinition.0011"></qp:message></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${commonObjectDefinitionForm.commonObjectAttributes }" var="item" varStatus="status">
								<qp:commonModifyRow itemValue="${item}" />
							</c:forEach>
						</tbody>
					</table>
					<div class="qp-div-action-table">
						<a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl_attribute_list_define',templateId:'tbl_attribute_list_define-template',templateDate:{groupId:''}});"></a>
					</div>
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="commonobjectdefinitionRegister">
					<button type="button" onclick="saveCommonOject" class="btn qp-button qp-dialog-confirm">
						<qp:message code="sc.sys.0031" />
					</button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
