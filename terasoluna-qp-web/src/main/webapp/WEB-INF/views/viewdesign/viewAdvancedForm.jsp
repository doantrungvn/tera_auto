<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code='sc.viewdesign.0009' />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/viewdesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=viewdesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/advancedsql.js"></script>
		<!-- Adding sql editor -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/codemirror.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/codemirror.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sql.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/show-hint.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/show-hint.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sql-hint.js"></script>
		<script type="text/javascript">
			$.qp.advancedsql.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="viewDesignAdvancedDesignForm" action="${pageContext.request.contextPath}/viewdesign/delete">
			<c:set var="designForm" value="${viewDesignAdvancedDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.sqlDesignForm.sqlDesignId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code='sc.viewdesign.0010' /></span>
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
								<th><qp:message code='sc.viewdesign.0006' /></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignName }"></qp:formatText></td>
								<th><qp:message code='sc.viewdesign.0007' /></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignCode }"></qp:formatText></td>
							</tr>
							<tr>
								<th><label for="sqlDesignForm.designTypes"><qp:message code='sc.sqldesign.0031' /></label></th>
								<td><qp:formatText value="${CL_SQL_TYPE_VIEWDESIGN.get(designForm.sqlDesignForm.designType.toString())}"></qp:formatText>
								</td>
								<th><label for="sqlDesignForm.moduleIdAutocomplete"><qp:message code="sc.autocomplete.0007"></qp:message></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.moduleIdAutocomplete }"></qp:formatText>
								</td>
							</tr>
							<tr>
								<th><label><qp:message code="sc.sys.0055"></qp:message></label></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus.toString())}" /></td>
								<th><label for="remark"><qp:message code="sc.sys.0028"></qp:message></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.remark}" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div>
					<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
						<form:textarea path="sqlDesignForm.sqlText" style="width: 100%; text-align: left; height: 400px" rows="6" readonly="true"/>
					</div>
				</div>
				<div class="qp-div-action">
					<form:hidden path="sqlDesignForm.sqlDesignId" />
					<form:hidden path="sqlDesignForm.updatedDate" />
					<input type="hidden" name="designStatus" value="${designForm.sqlDesignForm.designStatus}"/>
					<form:hidden path="actionDelete" value="false"/>
					<qp:authorization permission="viewdesignModify">
						<c:choose>
							<c:when test="${designForm.sqlDesignForm.designStatus eq '1'}">
								<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"></qp:message></button>
							</c:when>
							<c:when test="${designForm.sqlDesignForm.designStatus eq '2'}">
								<qp:authorization permission="changeDesignStatus">
									<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"></qp:message></button>
								</qp:authorization>
							</c:when>
						</c:choose>
					</qp:authorization>
					<c:if test="${designForm.sqlDesignForm.designStatus eq '1'}">
						<qp:authorization permission="viewdesignDelete">
							<button type="button" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="$.qp.common.setFlag()"><qp:message code="sc.sys.0008" /></button>
						</qp:authorization>
						<qp:authorization permission="viewdesignModify">
							<a type="submit"
								class="btn btn-success qp-link-button qp-link-popup-navigate"
								href="${pageContext.request.contextPath}/viewdesign/modify?sqlDesignForm.sqlDesignId=${f:u(designForm.sqlDesignForm.sqlDesignId)}&mode=1">
								<qp:message code="sc.sys.0006"></qp:message>
							</a>
						</qp:authorization>
					</c:if>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>