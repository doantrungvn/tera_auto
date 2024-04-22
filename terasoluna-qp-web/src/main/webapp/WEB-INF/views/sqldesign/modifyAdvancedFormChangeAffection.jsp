<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.sqldesign"></qp:message></span></li>
		<li><span><qp:message code="sc.problemlist.0015"></qp:message></span></li>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<a href="${pageContext.request.contextPath}/sqldesign/search"><qp:message code='sc.sqldesign.0014'></qp:message></a>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/advancedsql.js"></script>
		<!-- Adding sql editor -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/codemirror.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/codemirror.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sql.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/show-hint.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/show-hint.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sql-hint.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/dialog.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/searchcursor.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/search.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/dialog.css" />
		<script type="text/javascript">
			$.qp.advancedsql.init();
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/sqldesign/modifyAdvanced" modelAttribute="sqlDesignAdvancedDesignForm">
			<c:set var="designForm" value="${sqlDesignAdvancedDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.sqlDesignForm.sqlDesignId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="qp-heading-text"><qp:message code="sc.sqldesign.0021"></qp:message></span>
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
								<th><qp:message code="sc.sqldesign.0010"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignName }"></qp:formatText></td>
								<th><qp:message code="sc.sqldesign.0011"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignCode }"></qp:formatText></td>
							</tr>
							<tr>
								<th><qp:message code="sc.sqldesign.0031"></qp:message></th>
								<td>${CL_SQL_TYPE_SQLDESIGN.get('5') }</td>
								<th><qp:message code="sc.module.0007"></qp:message></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.moduleIdAutocomplete }"></qp:formatText></td>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0055"></qp:message></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus) }"></qp:message></td>
								<th><qp:message code='sc.sys.0028'></qp:message></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.remark }"></qp:formatText></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="display: none">
					<%-- 				<jsp:include page="advancedEditTemplate.jsp"></jsp:include> --%>
				</div>
				<div class="qp-div-action">
					<input type="hidden" name="formJson" value="${f:h(formJson)}" /> <input type="hidden" name="isAdvanced" value="true" />
					<qp:authorization permission="sqldesignModify">
						<button type="submit" class="btn qp-button" name="jsonBack">
							<qp:message code="sc.sys.0023" />
						</button>
					</qp:authorization>
					<qp:authorization permission="sqldesignModify">
						<button type="submit" class="btn qp-button qp-dialog-confirm" data-confirm-bcallback="$.qp.common.formSubmitCallback" data-confirm-pcallback="$.qp.sqlbuilder.validateBeforeSubmit" name="isJsonForm">
							<qp:message code="sc.sys.0031"></qp:message>
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
	</tiles:putAttribute>
</tiles:insertDefinition>