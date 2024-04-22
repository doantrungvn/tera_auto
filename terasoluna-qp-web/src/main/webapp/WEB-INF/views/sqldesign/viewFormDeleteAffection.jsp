<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.problemlist.0015"></qp:message>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/sqldesign.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div id="qp-sqldesign">
			<form:form method="post" modelAttribute="sqlDesignDesignForm" action="${pageContext.request.contextPath}/sqldesign/delete">
				<c:set var="designForm" value="${sqlDesignDesignForm }" scope="request"></c:set>
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<c:if test="${not empty sqlDesignDesignForm.sqlDesignForm.sqlDesignId}">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.sqldesign.0021" /></span>
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
									<th><label><qp:message code="sc.sqldesign.0010"></qp:message></label></th>
									<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignName }"></qp:formatText></td>
									<th><label><qp:message code="sc.sqldesign.0011"></qp:message></label></th>
									<td><qp:formatText value="${sqlDesignDesignForm.sqlDesignForm.sqlDesignCode }"></qp:formatText></td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.sqldesign.0017"></qp:message></label></th>
									<td><qp:formatText value="${CL_SQL_TYPE.get(designForm.sqlDesignForm.designType)}"></qp:formatText></td>
									<th><label><qp:message code="sc.module.0007"></qp:message></label></th>
									<td><qp:formatText value="${designForm.sqlDesignForm.moduleIdAutocomplete }"></qp:formatText></td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.sys.0055"></qp:message></label></th>
									<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus.toString())}" /></td>
									<th><label><qp:message code="sc.sqldesign.0020"></qp:message></label></th>
									<td><qp:formatText value="${CL_SQL_SQLPATTERN.get(sqlDesignDesignForm.sqlDesignForm.sqlPattern)}" /></td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.sys.0028"></qp:message></label></th>
									<td><qp:formatRemark value="${designForm.sqlDesignForm.remark }" /></td>
									<th></th>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="qp-div-action">
						<form:hidden path="sqlDesignForm.sqlDesignId" />
						<form:hidden path="sqlDesignForm.updatedDate" />
						<input type="hidden" name="sqlDesignForm.designStatus" value="${designForm.sqlDesignForm.designStatus}" />
						<form:hidden path="actionDelete" value="false" />
						<qp:authorization permission="sqldesignView">
							<a href="${pageContext.request.contextPath}/sqldesign/view?sqlDesignForm.sqlDesignId=${f:u(designForm.sqlDesignForm.sqlDesignId)}&openOwner=1&showImpactFlag=${f:u(designForm.showImpactFlag)}" class="btn qp-button qp-link-button qp-link-button"><qp:message code="sc.sys.0023" /></a>
						</qp:authorization>
						<qp:authorization permission="deleteObjectHasFk">
							<button type="button" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="$.qp.common.setFlag()">
								<qp:message code="sc.sys.0008" />
							</button>
						</qp:authorization>
					</div>
					<c:if test="${not empty affectedBusinessDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0008" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col>
										<col>
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
										<c:forEach var="businessLogic" items="${affectedBusinessDesigns}" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
												<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
												<td><qp:formatText value="${businessLogic.moduleName}" /></td>
											</tr>
										</c:forEach>
										<c:if test="${empty affectedBusinessDesigns}">
											<tr>
												<td colspan="4"><qp:message code="inf.sys.0013" /></td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty affectedDomainDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.domaindesign.0027" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="40%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.domaindesign.0001" /></th>
											<th><qp:message code="sc.domaindesign.0002" /></th>
										</tr>
									</thead>
									<c:forEach var="domainDesign" items="${affectedDomainDesigns}" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td><qp:formatText value="${domainDesign.domainName}" /></td>
											<td><qp:formatText value="${domainDesign.domainCode}" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty affectedDomainDesigns}">
										<tr>
											<td colspan="3"><qp:message code="inf.sys.0013" /></td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty affectedTableDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.tabledesign.0075" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="40%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.tabledesign.0019" /></th>
											<th><qp:message code="sc.tabledesign.0020" /></th>
										</tr>
									</thead>
									<c:forEach var="tableDesign" items="${affectedTableDesigns}" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td><qp:formatText value="${tableDesign.tableName}" /></td>
											<td><qp:formatText value="${tableDesign.tableCode}" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty affectedTableDesigns}">
										<tr>
											<td colspan="3"><qp:message code="inf.sys.0013" /></td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</c:if>
				</c:if>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>