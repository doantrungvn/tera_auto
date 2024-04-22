<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.tabledesign.0070" />
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.tabledesign"></qp:message></span></li>
         <li><span><qp:message code="sc.tabledesign.0070"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="tabledesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/tabledesign/search"><qp:message code="sc.tabledesign.0069"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/app/tabledesign/css/advanceSetting.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/tabledesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/initData.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/processPK.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/processFK.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/advanceSetting.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/settingRemarkColumn.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=domaindesign_tabledesign"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<script id="tbl_list_Subject-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex">1</td>
				<td><qp:autocomplete name="subjectAreas[].areaId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllSubjectAreaBySubAreaIdForAutocomplete" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" onChangeEvent="changeTableCodeLabel" mustMatch="true" maxlength="200"></qp:autocomplete></td>
				<td><span></span></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJSEx(this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
			</tr>
		</script>
		<form:form method="post" action="${pageContext.request.contextPath}/tabledesign/modifyTableCommonForm" modelAttribute="tableDesignForm">
			<c:if test="${notExistFlg ne 1}">
			<%@include file="advanceSetting.jsp" %>
			<%@include file="advanceSettingForDomain.jsp" %>
			<%@include file="settingRemarkColumn.jsp" %>
			<%@include file="settingKeys.jsp" %>
			<%@include file="settingForeignKeys.jsp" %>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default  qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.tabledesign.0005" /></span>
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
							<th><qp:message code="sc.tabledesign.0019" /></th>
							<td><qp:formatText value="${tableDesignForm.tableName }" /></td>
							<th><qp:message code="sc.tabledesign.0020" /></th>
							<td><qp:formatText value="${tableDesignForm.tableCode }" /></td>
						</tr>
						<tr>
							<th><form:label path="type"><qp:message code="sc.sys.0059"></qp:message></form:label></th>
                            <td><input type="hidden" name="type" value="${tableDesignForm.type}"/><qp:message code="${CL_TABLE_TYPE_ALL.get(tableDesignForm.type.toString())}"  /></td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
                            <td><input type="hidden" name="designStatus" value="${tableDesignForm.designStatus}"/><qp:message code="${CL_DESIGN_STATUS.get(tableDesignForm.designStatus.toString())}"  /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.tabledesign.0013" /></th>
							<td><qp:formatText value="${tableDesignForm.remark}" /></td>
							<th></th>
							<td></td>
						</tr>
					</table>
					<br />
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.subareadesign.0017"></qp:message></span>
				</div>
				<div class="panel-body">
					<table id="tbl_list_Subject"
						class="table table-bordered qp-table-list" data-ar-callback="callback">
						<colgroup>
							<col>
							<col>
							<col width="45%">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.subareadesign.0004" /></th>
								<th><qp:message code="sc.subareadesign.0005" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody class="ui-sortable">
							<c:forEach var="subArea" items="${tableDesignForm.subjectAreas}" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">${status.count}</td>
									<td>
										<qp:autocomplete 
											name="subjectAreas[${status.index}].areaId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllSubjectAreaBySubAreaIdForAutocomplete" value="${subArea.areaId}" displayValue="${subArea.areaIdAutocomplete}" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" onChangeEvent="changeTableCodeLabel" mustMatch="true" maxlength="200">
										</qp:autocomplete>
									</td>
									<td><span><qp:formatText value="${subArea.areaCode}"/></span>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_list_Subject', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div>
						<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this);" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.tabledesign.0014" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action">
						<colgroup>
								<col width="4%">
								<col >
								<col width="17%">
								<col width="10%">
								<col width="6%">
								<col width="7%">
								<col width="7%">
								<col width="11%">
								<col width="9%">
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.tabledesign.0021" /></th><!-- Column Name -->
									<th><qp:message code="sc.tabledesign.0022" /></th><!-- Column Code -->
									<th><qp:message code="sc.tabledesign.0007" /></th><!-- Data Type -->
									<th><qp:message code="sc.tabledesign.0008" /></th><!-- Length -->
									<th><qp:message code="sc.tabledesign.0016" /></th><!-- Precision -->
									<th><qp:message code="sc.tabledesign.0017" /></th><!-- Madatory -->
									<th><qp:message code="sc.tabledesign.0025" /></th><!-- Auto Increment -->
									<th><qp:message code="sc.tabledesign.0009" /></th><!-- Default Value -->
								</tr>
						</thead>
						<c:forEach items="${tableDesignForm.listTableDesignDetails}" var="item" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>
									<qp:formatText value="${item.name}" />
									<c:if test="${item.isPrimaryKey() eq '1'}">
										<span class="qp-required-field">(PK)</span>
									</c:if>
									<c:if test="${item.isKey(2) eq '1'}">
										<span class="qp-fk-field">(FK)</span>
									</c:if>
								</td>
								<td><qp:formatText value="${item.code}" /></td> 
								<td><qp:formatText value="${item.dataTypeName}" /></td>
								<td><qp:formatInteger value="${item.maxlength}" /></td>
								<td><qp:formatInteger value="${item.decimalPart}" /></td>
								<td><qp:booleanFormatYesNo value="${item.isMandatory}" /></td>
								<td><qp:integerFormatYesNo value="${item.autoIncrementFlag}" yesValue="1" /></td>
								<td><qp:formatText value="${item.defaultValue}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${empty tableDesignForm.listTableDesignDetails}">
							<tr>
								<td colspan="9"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="tabledesignModify">
					<form:hidden path="tableDesignId" value="${tableDesignForm.tableDesignId}"/>
					<form:hidden path="updatedDate" value="${tableDesignForm.updatedDate}"/>
					<form:hidden path="type" value="${tableDesignForm.type}"/>
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"/></button>
				</qp:authorization>
			</div>
		</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>