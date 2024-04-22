<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.functionmaster"></qp:message></span></li>
         <li><span><qp:message code="sc.functionmaster.0047"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
	
	<c:if test="${ not empty functionMasterForm}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/functionmaster/modify" modelAttribute="functionMasterForm">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.functionmaster.0010" /> </span>
					</div>
				
					<div class="panel-body">
						<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
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
								<td><qp:formatText value="${functionMasterForm.remark }" /></td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="display:none">
					<input type="hidden" name="formJson" value="${f:h(formJson)}" />
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="functionmasterModify">
						<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0023" /></button>
						<button type="submit" class="btn qp-button qp-dialog-confirm" name="isJsonForm"><qp:message code="sc.sys.0031" /></button>
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