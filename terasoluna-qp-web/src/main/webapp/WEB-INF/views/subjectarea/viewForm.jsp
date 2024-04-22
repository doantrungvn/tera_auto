<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
			<qp:message code="sc.subareadesign.0020"></qp:message>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/subjectarea/css/style.css" type="text/css" rel="stylesheet" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<c:if test="${empty message}">
			<form:form method="post" modelAttribute="subjectAreaForm"
				action="${pageContext.request.contextPath}/subjectarea/delete">
				<form:errors path="*" cssClass="error" element="div" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.subareadesign.0011"></qp:message></span>
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
								<th>
									<form:label path="areaName"><qp:message code="sc.subareadesign.0004"/></form:label>
								</th>
								<td><form:label path="areaName" autofocus="true" /><qp:formatText value="${area.areaName}"/></td>
								<th>
									<form:label path="areaCode"><qp:message code="sc.subareadesign.0005"/></form:label>
								</th>
								<td><form:label path="areaCode" autofocus="true" /><qp:formatText value="${area.areaCode}"/></td>
							</tr>
							<tr>
								<th>
									<form:label path="remark"><qp:message code="sc.sys.0028"/></form:label>
								</th>
								<td>
									<form:label path="remark" autofocus="true"/><qp:formatRemark value="${area.remark}"/>
								</td>
								<th>
									<form:label path="defaultFlg"><qp:message code="sc.subareadesign.0009"/></form:label></th>
								<td>
									<form:label path="defaultFlg" autofocus="true"/><qp:integerFormatYesNo value="${area.defaultFlg.toString()}" yesValue="1" />
								</td>
							</tr>
						</table>
					</div>
				</div>
				
				<div class="qp-div-action">
					<qp:authorization permission="subjectareaDelete">
						<form:hidden path="areaId" value="${area.areaId}"/>
						<form:hidden path="updatedDate" value="${area.updatedDate}"/>
						<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" name="mode"
						value="3"><qp:message code="sc.sys.0008"></qp:message></button>
					</qp:authorization>
					<qp:authorization permission="subjectareaModify">
						<%-- <form:hidden path="areaId" value="${area.areaId}"/> --%>
						<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate"
							href="${pageContext.request.contextPath}/subjectarea/modify?areaId=${area.areaId}&mode=1"><qp:message code="sc.sys.0006"></qp:message></a>
					</qp:authorization>
				</div>
				
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.subareadesign.0015"></qp:message></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col />
									<col />
									<col />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:message code="sc.subareadesign.0006"></qp:message></th>
										<th><qp:message code="sc.subareadesign.0007"></qp:message></th>
									</tr>
								</thead>
								<c:forEach var="tableLst" items="${area.tableLst}" varStatus="loop">
									<tr>
										<td>${loop.index + 1}</td>
										<td><qp:formatText value="${tableLst.tableName}"/></td>
										<td><qp:formatText value="${tableLst.tableCode}"/></td>
									</tr>
								</c:forEach>
								<c:if test="${empty area.tableLst}">
									<tr>
										<td colspan="3"><qp:message code="inf.sys.0013"/></td>
									</tr>
								</c:if>
							</table>
						</div>
					</div>
				</div>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.subareadesign.0016"></qp:message></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col />
									<col />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:message code="sc.subareadesign.0008"></qp:message></th>
									</tr>
								</thead>
								<c:forEach var="keywordLst" items="${area.keywordLst}" varStatus="loop">
									<tr>
										<td>${loop.index + 1}</td>
										<td><qp:formatText value="${keywordLst.keyword}"/></td>
									</tr>
								</c:forEach>
								<c:if test="${empty area.keywordLst}">
									<tr>
										<td colspan="2"><qp:message code="inf.sys.0013"/></td>
									</tr>
								</c:if>
							</table>
						</div>
					</div>
				</div>
			</form:form>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>