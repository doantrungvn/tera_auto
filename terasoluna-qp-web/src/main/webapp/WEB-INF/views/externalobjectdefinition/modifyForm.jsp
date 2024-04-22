<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialBlogicDesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialCommonTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/displayComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/validationComponent.js"></script>

		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/externalobjectdefinition/javascript/externalobjectdefinition.js"></script>

		<script type="text/javascript">
			var returnType = 1;
			var CL_EX_DATATYPE_NOT_COMMON_OBJECT = {}//CL_QP_Datatype
			<c:forEach items="${CL_EX_DATATYPE_NOT_COMMON_OBJECT}" var="item">
			CL_EX_DATATYPE_NOT_COMMON_OBJECT['${item.key}'] = '${item.value}';
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
		<li><span><qp:message code="tqp.externalobjectdefinition"></qp:message></span></li>
		<li><span><qp:message code="sc.externalobjectdefinition.0007" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<qp:authorization permission="externalobjectdefinitionSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/externalobjectdefinition/search"><qp:message code="sc.externalobjectdefinition.0004"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/externalobjectdefinition/modify" modelAttribute="externalObjectDefinitionForm">
			<c:if test="${notExistFlg ne 1}">
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="externalObjectType" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="qp-heading-text"><qp:message code="sc.externalobjectdefinition.0007"></qp:message></span>
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
								<th><form:label path="externalObjectDefinitionName">
										<qp:message code="sc.externalobjectdefinition.0002"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
									</form:label></th>
								<td><form:input path="externalObjectDefinitionName" value="" cssClass="form-control qp-input-text qp-convention-name" maxlength="200" /></td>
								<th><form:label path="externalObjectDefinitionCode">
										<qp:message code="sc.externalobjectdefinition.0003"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
									</form:label></th>
								<td><form:input path="externalObjectDefinitionCode" value="" cssClass="form-control qp-input-text qp-convention-code" maxlength="50" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.module.0007" /></th>
								<td>
									<label><qp:formatText value="${externalObjectDefinitionForm.moduleIdAutocomplete}" /></label> 
									<form:hidden path="moduleId" />
									<form:hidden path="moduleIdAutocomplete" />
								</td>
								<th><form:label path="packageName">
										<qp:message code="sc.externalobjectdefinition.0013"></qp:message>
										&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
										<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.project.0028"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
									</form:label></th>
								<td><form:input path="packageName" cssClass="form-control qp-input-text" maxlength="200" /></td>
							</tr>
							<tr>
								<th><form:label path="remark">
										<qp:message code="sc.sys.0028" />
									</form:label></th>
								<td colspan="3"><form:textarea path="remark" type="text" rows="2" cssClass="form-control qp-input-textarea" maxlength="2000" cols="3" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-default qp-div-search-result" id="tableSelection">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.externalobjectdefinition.0008"></qp:message></span>
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
									<th class="colName"><qp:message code="sc.externalobjectdefinition.0009"></qp:message></th>
									<th><qp:message code="sc.externalobjectdefinition.0010"></qp:message></th>
									<th><qp:message code="sc.externalobjectdefinition.0011"></qp:message></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${externalObjectDefinitionForm.externalObjectAttributes }" var="item" varStatus="status">
									<qp:externalModifyRow itemValue="${item}" />
								</c:forEach>
							</tbody>
						</table>
						<div class="qp-div-action-table">
							<a type="button" id="addRowParent" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl_attribute_list_define',templateId:'tbl_attribute_list_define-template',templateDate:{groupId:''}});"></a>
						</div>
					</div>
				</div>
				<div class="qp-div-action">
					<form:hidden path="externalObjectDefinitionId" />
					<form:hidden path="updatedDate" />
					<form:hidden path="updatedBy" />
					<qp:authorization permission="externalobjectdefinitionModify">
						<button type="button" onclick="saveExternalOject" class="btn qp-button qp-dialog-confirm">
							<qp:message code="sc.sys.0031" />
						</button>
					</qp:authorization>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
