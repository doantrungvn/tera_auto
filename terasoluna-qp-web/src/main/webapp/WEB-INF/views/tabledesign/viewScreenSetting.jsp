<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.tabledesign.0034" />
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<link href="${pageContext.request.contextPath}/resources/app/tabledesign/css/advanceSetting.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/screenItemSetting.js"></script>
	</tiles:putAttribute>

	<c:if test="${ not empty tableDesignForm}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/tabledesign/modifyTableSetting" modelAttribute="tableDesignForm">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="tableDesignId" />
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
							<td><qp:formatText value="${tableDesignForm.tableName }" /></td>
							<th><qp:message code="sc.tabledesign.0020" /></th>
							<td><qp:formatText value="${tableDesignForm.tableCode }" /></td>
						</tr>
						<tr>
							<th><form:label path="type"><qp:message code="sc.sys.0059"></qp:message></form:label></th>
							<td><qp:message code="${CL_TABLE_TYPE.get(tableDesignForm.type.toString())}"  /></td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
							<td><qp:message code="${CL_DESIGN_STATUS.get(tableDesignForm.designStatus.toString())}"  /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.tabledesign.0013" /></th>
							<td><qp:formatRemark value="${tableDesignForm.remark}" /></td>
							<th><form:hidden path="hasCompositeKey" /></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.tabledesign.0014" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action" id="tblScreenItemSetting">
						<colgroup>
								<col width="5%">
								<col width="34%">
								<col width="15%">
								<col width="15%">
								<col width="8%">
								<col width="15%">
								<col width="5%">
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.tabledesign.0021" /></th><!-- Column Name -->
									<th><qp:message code="sc.tabledesign.0022" /></th><!-- Column Code -->
									<th><qp:message code="sc.tabledesign.0007" /></th><!-- Data Type -->
									<th><qp:message code="sc.tabledesign.0008" /></th><!-- Length -->
									<th><qp:message code="sc.tabledesign.0031" /></th><!-- Item Type -->
									<th><qp:message code="sc.tabledesign.0032" /></th><!-- Used -->
								</tr>
						</thead>
						<c:forEach items="${tableDesignForm.listTableDesignDetails}" var="item" varStatus="status">
							<tr <c:if test="${item.displayType eq 0}">class= "trDisable"</c:if>>
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
								<td>
									<form:select id ="selectItemType${status.index}" class="form-control qp-input-select" path="listTableDesignDetails[${status.index}].itemType" >
										<c:forEach var="itemType" items="${item.listItemtype}">
											<form:option value="${itemType.key}"><qp:message code="${itemType.value}"/></form:option>
										</c:forEach>
									</form:select>
								</td>
								<td align="center">
									<c:choose>
										<c:when test="${item.isMandatory}">
											<c:choose>
												<c:when test="${not empty item.defaultValue || tableDesignForm.hasCompositeKey gt 1}">
													<form:checkbox path="listTableDesignDetails[${status.index}].used" value="${item.displayType}" class="checkbox qp-input-checkbox parent-checkbox" />
												</c:when>
												<c:otherwise>
													<form:checkbox path="listTableDesignDetails[${status.index}].used" value="${item.displayType}" class="checkbox qp-input-checkbox parent-checkbox" disabled = "true"/>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<form:checkbox path="listTableDesignDetails[${status.index}].used" value="${item.displayType}" class="checkbox qp-input-checkbox parent-checkbox" />
										</c:otherwise>
									</c:choose>

									<form:hidden path="listTableDesignDetails[${status.index}].displayType"/>
									<form:hidden path="listTableDesignDetails[${status.index}].dataTypeName"/>
									<form:hidden path="listTableDesignDetails[${status.index}].maxlength"/>
									<form:hidden path="listTableDesignDetails[${status.index}].dataType"/>
									<form:hidden path="listTableDesignDetails[${status.index}].columnId"/>
									<form:hidden path="listTableDesignDetails[${status.index}].name"/>
									<form:hidden path="listTableDesignDetails[${status.index}].code"/>
									<form:hidden path="listTableDesignDetails[${status.index}].datasourceId"/>
									<form:hidden path="listTableDesignDetails[${status.index}].datasourceType"/>
									<form:hidden path="listTableDesignDetails[${status.index}].groupBaseTypeId"/>
									<form:hidden path="listTableDesignDetails[${status.index}].fmtCode"/>
									<form:hidden path="listTableDesignDetails[${status.index}].isMandatory" value = "${item.isMandatory}"/>
									<form:hidden path="listTableDesignDetails[${status.index}].binKeyType"/>
									<form:hidden path="listTableDesignDetails[${status.index}].autoIncrementFlag"/>
									<form:hidden path="listTableDesignDetails[${status.index}].defaultValue"/>
									<form:hidden path="listTableDesignDetails[${status.index}].baseType"/>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty tableDesignForm.listTableDesignDetails}">
							<tr>
								<td colspan="7"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="tabledesignModify">
					<form:hidden path="updatedDate" />
					<form:hidden path="tableName" />
					<form:hidden path="tableCode" />
					<form:hidden path="remark" />
					<form:hidden path="projectId" />
					<form:hidden path="type" />
					<form:hidden path="designStatus" />
					<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
			</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>