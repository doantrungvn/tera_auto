<tiles:insertDefinition name="layouts">
  <tiles:putAttribute name="header-name">
    <qp:message code="sc.tabledesign.0079" />
  </tiles:putAttribute>

	<tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.tabledesign"></qp:message></span></li>
         <li><span><qp:message code="sc.functionmaster.0047"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/tabledesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=tabledesign"></script>
	</tiles:putAttribute>
	<c:if test="${ not empty tableDesignForm}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/tabledesign/modify" modelAttribute="tableDesignForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="tableDesignId" />
				<input type="hidden" name="formJson" value="${f:h(formJson)}" />
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
								<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
								<td><input type="hidden" name="designStatus" value="1"/><qp:message code="${CL_DESIGN_STATUS.get(tableDesignForm.designStatus.toString())}"  /></td>
								<th><qp:message code="sc.tabledesign.0013" /></th>
								<td><qp:formatRemark value="${tableDesignForm.remark }" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="tabledesignModify">
						<form:hidden path="updatedDate" value="${tableDesignForm.updatedDate}"/>
						<form:hidden path="tableName" value="${tableDesignForm.tableName}"/>
						<form:hidden path="tableCode" value="${tableDesignForm.tableCode}"/>
						<form:hidden path="remark" value="${tableDesignForm.remark}"/>
						<form:hidden path="projectId" value="${tableDesignForm.projectId}"/>
						<form:hidden path="projectName" value="${tableDesignForm.projectName}"/>
						<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0049" /></button>				
						<button type="submit" class="btn qp-button qp-dialog-confirm" name="isJsonForm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
				
				<c:if test="${not empty tableDesignForm.listTableDesignForeignKeyAffect}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.tabledesign.0078" /></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col width="5%">
									<col >
									<col width="30%">
									<col width="30%">
								</colgroup>
								<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.tabledesign.0019" /></th>
									<th><qp:message code="sc.tabledesign.0021" /></th>
									<th><qp:message code="sc.tabledesign.0022" /></th>
								</tr>
								</thead>
								<tbody >
									<c:forEach var="foreignKey" items="${tableDesignForm.listTableDesignForeignKeyAffect}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${foreignKey.fromTableName}" /></td>
											<td><qp:formatText value="${foreignKey.fromColumnName}" /></td>
											<td><qp:formatText value="${foreignKey.fromColumnCode}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
				
				<c:if test="${not empty tableDesignForm.listBusinessLogics}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.domaindesign.0040" /></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col width="5%">
									<col >
									<col width="30%">
									<col width="30%">
								</colgroup>
								<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.businesslogicdesign.0005" /></th>
									<th><qp:message code="sc.businesslogicdesign.0006" /></th>
									<th><qp:message code="sc.module.0007" /></th>
								</tr>
								</thead>
								<tbody >
									<c:forEach var="businessLogic" items="${tableDesignForm.listBusinessLogics}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
											<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
											<td><qp:formatText value="${businessLogic.moduleName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
				
				<c:if test="${not empty tableDesignForm.listSqlDesigns}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.domaindesign.0041" /></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col width="5%">
									<col >
									<col width="30%">
									<col width="30%">
								</colgroup>
								<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.sqldesign.0010"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0011"></qp:message></th>
									<th><qp:message code="sc.module.0007" /></th>
								</tr>
								</thead>
								<tbody >
									<c:forEach var="sqlDesign" items="${tableDesignForm.listSqlDesigns}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${sqlDesign.sqlDesignName}" /></td>
											<td><qp:formatText value="${sqlDesign.sqlDesignCode}" /></td>
											<td><qp:formatText value="${sqlDesign.moduleName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>