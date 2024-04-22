<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.problemlist.0015"/>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
	<c:if test="${ not empty functionMasterForm}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/functionmaster/delete" modelAttribute="functionMasterForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.functionmaster.0010" /> </span>
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
								<th><qp:message code="sc.functionmaster.0043" /> </th>
								<td><qp:formatText value="${functionMasterForm.functionMasterName }" /></td>
								<th><qp:message code="sc.functionmaster.0044" /> </th>
								<td><qp:formatText value="${functionMasterForm.functionMasterCode }" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.tabledesign.0013" /></th>
								<td><qp:formatRemark value="${functionMasterForm.remark }" /></td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="functionmasterView">
						<form:hidden path="updatedDate" value="${functionMasterForm.updatedDate}"/>
						<form:hidden path="functionMasterId" value="${functionMasterForm.functionMasterId}"/>
						<form:hidden path="functionMasterName" value="${functionMasterForm.functionMasterName}"/>
						<form:hidden path="functionMasterCode" value="${functionMasterForm.functionMasterCode}"/>
						<form:hidden path="functionMasterType" value="${functionMasterForm.functionMasterType}"/>
						
						<form:hidden path="uploadFileId" value="${functionMasterForm.uploadFileId}"/>
						<form:hidden path="packageName" value="${functionMasterForm.packageName}"/>
						<form:hidden path="fileName" value="${functionMasterForm.fileName}"/>
						<form:hidden path="remark" value="${functionMasterForm.remark}"/>
						<a type="submit" class="btn qp-button qp-link-button qp-link-button" href="${pageContext.request.contextPath}/functionmaster/view?functionMasterId=${functionMasterForm.functionMasterId}&mode=1">
							<qp:message code="sc.sys.0023" />
						</a>
					</qp:authorization>
					<qp:authorization permission="deleteObjectHasFk">
						<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
					</qp:authorization>
				</div>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.functionmaster.0045" /> </span>
					</div>
					<div class="panel-body">
						<table id="tbl_list_Subject" class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
							<colgroup>
								<col >
								<col width="30%">
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
							
							<tbody>
								<c:forEach var="businessLogic" items="${functionMasterForm.listOfBusinessDesign}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">${status.count}</td>
										<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
										<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
										<td><qp:formatText value="${businessLogic.moduleName}"/></td>
									</tr>
								</c:forEach>
								<c:if test="${empty functionMasterForm.listOfBusinessDesign}">
										<tr>
											<td colspan="4"><qp:message code="inf.sys.0013"/></td>
										</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.functionmaster.0046" /></span>
					</div>
					<div class="panel-body">
						<table id="tbl_list_Subject" class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
							<colgroup>
								<col >
								<col width="30%">
								<col width="30%">
								<col width="30%">
							</colgroup>
							<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.decisiontable.0005" /> </th>
								<th><qp:message code="sc.decisiontable.0006" /> </th>
								<th><qp:message code="sc.module.0007" /></th>
							</tr>
							</thead>
							
							<tbody>
								<c:forEach var="decisionTable" items="${functionMasterForm.listOfDecisionTable}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">${status.count}</td>
										<td><qp:formatText value="${decisionTable.decisionTbName}" /></td>
										<td><qp:formatText value="${decisionTable.decisionTbCode}" /></td>
										<td><qp:formatText value="${decisionTable.moduleName}"/></td>
									</tr>
								</c:forEach>
								<c:if test="${empty functionMasterForm.listOfDecisionTable}">
										<tr>
											<td colspan="4"><qp:message code="inf.sys.0013"/></td>
										</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>