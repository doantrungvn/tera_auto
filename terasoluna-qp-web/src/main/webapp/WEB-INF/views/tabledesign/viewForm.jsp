<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
			<qp:message code="sc.tabledesign.0033" />
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=tabledesign"></script>
	</tiles:putAttribute>
<c:if test="${ not empty table}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/tabledesign/delete" modelAttribute="tableDesignForm">
			<form:hidden path="tableDesignId" />
			<input type="hidden" name="formJson" value="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
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
							<td><qp:formatText value="${table.tableName }" /></td>
							<th><qp:message code="sc.tabledesign.0020" /></th>
							<td><qp:formatText value="${table.tableCode }" /></td>
						</tr>
						<tr>
							<th><form:label path="type"><qp:message code="sc.sys.0059"></qp:message></form:label></th>
							<td><input type="hidden" name="type" value="${table.type}"/><qp:message code="${CL_TABLE_TYPE_ALL.get(table.type.toString())}"  /></td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
							<td><input type="hidden" name="designStatus" value="${table.designStatus}"/><qp:message code="${CL_DESIGN_STATUS.get(table.designStatus.toString())}"  /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.tabledesign.0013" /></th>
							<td><qp:formatRemark value="${table.remark}" /></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="qp-div-action">
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
						<c:if test="${table.type ne 2 && table.designStatus eq 1}">
							<qp:authorization permission="tabledesignModify">
								<form:hidden path="actionDelete" value="false"/>
								<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"  /></button>
							</qp:authorization>
							<qp:authorization permission="tabledesignDelete">
								<label><form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
								<a type="button" class="btn qp-button-warning qp-link-button qp-link-button qp-dialog-confirm" data-confirm-bcallback="setFlag" messageId="inf.sys.0014">
									<qp:message code="sc.sys.0008" />
								</a>
							</qp:authorization>
							<qp:authorization permission="tabledesignModify">
								<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/tabledesign/modify?tableDesignId=${table.tableDesignId}&mode=1"><qp:message code="sc.sys.0006" /></a>
							</qp:authorization>
						</c:if>
						<c:if test="${table.type eq 2}">
							<qp:authorization permission="tabledesignModify">
								<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/tabledesign/modifyTableCommonForm?tableDesignId=${table.tableDesignId}&mode=1"><qp:message code="sc.sys.0006" /></a>
							</qp:authorization>
						</c:if>
						<c:if test="${table.type ne 2}">
							<c:if test="${table.designStatus eq 2}">
								<qp:authorization permission="changeDesignStatus">
									<form:hidden path="actionDelete" value="false"/>
									<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"  /></button>
								</qp:authorization>
							</c:if>
						</c:if>
				</c:if>
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
							<col width="10%">
							<col width="45%">
							<col width="45%">
						</colgroup>
						<thead>
						<tr>
							<th><qp:message code="sc.sys.0004" /></th>
							<th><qp:message code="sc.subareadesign.0004" /></th>
							<th style="text-align: left;"><qp:message code="sc.subareadesign.0005" /></th>
						</tr>
						</thead>
						<tbody class="ui-sortable">
						<c:forEach var="subArea" items="${table.subjectAreas}" varStatus="status">
							<tr>
								<td class="qp-output-fixlength tableIndex">${status.count}</td>
								<td><qp:formatText value="${subArea.areaName}" /></td>
								<td style="text-align:left;"><qp:formatText value="${subArea.areaCode}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${empty table.subjectAreas}">
								<tr>
									<td colspan="3"><qp:message code="inf.sys.0013"/></td>
								</tr>
						</c:if>
						</tbody>
					</table>
					
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
						<c:forEach items="${table.listTableDesignDetails}" var="item" varStatus="status">
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
								
								<c:if test="${item.groupBaseTypeId ne '7'}">
									<td><qp:formatText value="${item.defaultValue}" /></td>
								</c:if>

								<c:if test="${item.groupBaseTypeId eq '7'}">
									<td>
										<c:set var="defaultValueMessageCode" value="${CL_BOOLEAN_DEFAULT_VALUE.get(item.defaultValue)}" />
										<c:if test="${empty defaultValueMessageCode}">
											<c:set var="defaultValueMessageCode" value="sc.sys.0095" /> <!-- None -->
										</c:if>
										<qp:message code="${defaultValueMessageCode}"  />
									</td>
								</c:if>
								
							</tr>
						</c:forEach>
						<c:if test="${empty table.listTableDesignDetails}">
							<tr>
								<td colspan="9"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
			<input type="hidden" name="updatedDate" value="${table.updatedDate}"/>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>