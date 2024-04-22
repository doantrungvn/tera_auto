<tiles:insertDefinition name="layouts">

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.project"></qp:message></span></li>
		<li><span><qp:message code="sc.problemlist.0015"/></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link"></tiles:putAttribute>
	<c:if test="${ not empty projectForm}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/project/modify" modelAttribute="projectForm">
			<div style="display:none">
				<input type="hidden" name="formJson" value="${f:h(formJson)}" />
			</div>
			<form:hidden path="projectId" />
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />

			<c:if test="${not empty projectForm.listOfSqlDesign}">
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.sqldesign.0021" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action">
						<colgroup>
							<col />
							<col width="30%" />
							<col width="30%" />
							<col width="20%" />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.sqldesign.0010" /></th>
								<th><qp:message code="sc.sqldesign.0011" /></th>
								<th><qp:message code="sc.sys.0055" /></th>
							</tr>
						</thead>
						<c:forEach items="${projectForm.listOfSqlDesign }" var="item" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td><qp:formatText value="${item.sqlDesignName }" /></td>
								<td><qp:formatText value="${item.sqlDesignCode }" /></td>
								<td><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			</c:if>
			
			<!-- //List affect table design -->
			<c:if test="${not empty projectForm.listOfTableDesign}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.tabledesign.0005"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-list-none-action">
							<colgroup>
								<col />
								<col width="30%" />
								<col width="30%" />
								<col width="20%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.tabledesign.0001" /></th>
									<th><qp:message code="sc.tabledesign.0002" /></th>
									<th><qp:message code="sc.sys.0055" /></th>
								</tr>
							</thead>
							<c:forEach items="${projectForm.listOfTableDesign }" var="item" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td><qp:formatText value="${item.tableName }" /></td>
									<td><qp:formatText value="${item.tableCode }" /></td>
									<td><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</c:if>

			<div class="qp-div-action">
				<qp:authorization permission="projectModify">
					<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0049" /></button>
					<button type="submit" class="btn qp-button qp-dialog-confirm" name="isJsonForm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>