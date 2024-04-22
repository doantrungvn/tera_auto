<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.domaindesign"></qp:message></span></li>
         <li><span><qp:message code="sc.functionmaster.0047"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
<c:if test="${ not empty domainDesignForm}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/domaindesign/modify" modelAttribute="domainDesignForm">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="domainId" />
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
							<th><qp:message code="sc.domaindesign.0001" /></th>
							<td><qp:formatText value="${domainDesignForm.domainName }" /></td>
							<th><qp:message code="sc.domaindesign.0002" /></th>
							<td><qp:formatText value="${domainDesignForm.domainCode }" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.tabledesign.0013" /></th>
							<td colspan="3"><qp:formatRemark value="${domainDesignForm.remark }" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="domaindesignModify">
					<form:hidden path="updatedDate" value="${domainDesignForm.updatedDate}"/>
					<form:hidden path="domainName" value="${domainDesignForm.domainName}"/>
					<form:hidden path="domainCode" value="${domainDesignForm.domainCode}"/>
					<form:hidden path="remark" value="${domainDesignForm.remark}"/>
					
					<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0049" /></button>
					<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
				<c:if test="${not empty domainDesignForm.listOfTableDesign}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.domaindesign.0051" /></span>
						</div>
						<div class="panel-body">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col width="10%"/>
									<col width="40%"/>
									<col width="35%"/>
									<col width="15%"/>
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.domaindesign.0017" /></th>
										<th><qp:message code="sc.domaindesign.0018" /></th>
										<th><qp:message code="sc.sys.0055" /></th>
									</tr>
								</thead>
								<c:forEach items="${domainDesignForm.listOfTableDesign }" var="item" varStatus="status">
									<tr>
										<td>${status.count}</td>
										<td><qp:formatText value="${item.output01 }" /></td>
										<td><qp:formatText value="${item.output02 }" /></td>
										<td><qp:message code="${CL_DESIGN_STATUS.get(item.output04.toString())}"/></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</c:if>
				
				<c:if test="${not empty domainDesignForm.listBusinessLogics}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.module.0029" /></span>
							</div>
							<div class="panel-body">
								<table id="tbl_list_Subject"
									class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
									<colgroup>
										<col width="10%">
										<col width="30%">
										<col width="25%">
										<col width="10%">
										<col width="25%">
									</colgroup>
									<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.businesslogicdesign.0005" /></th>
										<th><qp:message code="sc.businesslogicdesign.0006" /></th>
										<th><qp:message code="sc.sys.0055" /></th>
										<th><qp:message code="sc.module.0007" /></th>
									</tr>
									</thead>
									<tbody >
										<c:forEach var="businessLogic" items="${domainDesignForm.listBusinessLogics}" varStatus="status">
											<tr>
												<td class="qp-output-fixlength tableIndex">${status.count}</td>
												<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
												<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
												<td><qp:message code="${CL_DESIGN_STATUS.get(businessLogic.designStatus.toString())}"/></td>
												<td><qp:formatText value="${businessLogic.moduleName}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								
							</div>
						</div>
				</c:if>
			
				<c:if test="${not empty domainDesignForm.listSqlDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.module.0028" /></span>
							</div>
							<div class="panel-body">
								<table id="tbl_list_Subject"
									class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
									<colgroup>
										<col width="10%">
										<col width="30%">
										<col width="25%">
										<col width="10%">
										<col width="25%">
									</colgroup>
									<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.sqldesign.0010"></qp:message></th>
										<th><qp:message code="sc.sqldesign.0011"></qp:message></th>
										<th><qp:message code="sc.sys.0055" /></th>
										<th><qp:message code="sc.module.0007" /></th>
									</tr>
									</thead>
									<tbody >
										<c:forEach var="sqlDesign" items="${domainDesignForm.listSqlDesigns}" varStatus="status">
											<tr>
												<td class="qp-output-fixlength tableIndex">${status.count}</td>
												<td><qp:formatText value="${sqlDesign.sqlDesignName}" /></td>
												<td><qp:formatText value="${sqlDesign.sqlDesignCode}" /></td>
												<td><qp:message code="${CL_DESIGN_STATUS.get(sqlDesign.designStatus.toString())}"/></td>
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