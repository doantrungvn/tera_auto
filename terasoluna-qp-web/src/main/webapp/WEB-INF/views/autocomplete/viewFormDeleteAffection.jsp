<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.problemlist.0015"></qp:message>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/autocompleteDesign/css/autocompleteDesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="designForm"
			action="${pageContext.request.contextPath}/autocomplete/delete">
			<c:set var="designForm" value="${autocompleteDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.autocompleteForm.autocompleteId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.autocomplete.0003" /></span>
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
								<th><label><qp:message code="sc.autocomplete.0005"></qp:message></label></th>
								<td><qp:formatText value="${designForm.autocompleteForm.autocompleteName }"></qp:formatText></td>
								<th><label><qp:message code="sc.autocomplete.0006"></qp:message></label></th>
								<td><qp:formatText value="${designForm.autocompleteForm.autocompleteCode }"></qp:formatText></td>
							</tr>
							<tr>
								<th><label for="autocompleteForm.matchingTypes"><qp:message code="sc.autocomplete.0009"></qp:message></label></th>
								<td><qp:message code="${CL_MATCHING_TYPE.get(designForm.autocompleteForm.matchingType.toString())}"></qp:message>
								</td>
								<th><label for="autocompleteForm.moduleIdAutocomplete"><qp:message code="sc.autocomplete.0007"></qp:message></label></th>
								<td><qp:formatText value="${designForm.autocompleteForm.moduleIdAutocomplete }"></qp:formatText>
								</td>
							</tr>
							<tr>
								<th><label for="autocompleteForm.minLength"><qp:message code="sc.autocomplete.0062"></qp:message></label></th>
								<td><qp:formatInteger value="${designForm.autocompleteForm.minLength }"></qp:formatInteger></td>
								<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.autocompleteForm.designStatus.toString())}" /></td>
							</tr>
							<tr>
								<th><label><qp:message code="sc.sys.0028"></qp:message></label></th>
								<td colspan="3"><qp:formatRemark value="${designForm.autocompleteForm.remark}" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<form:hidden path="autocompleteForm.autocompleteId" />
					<form:hidden path="autocompleteForm.updatedDate" />
					<input type="hidden" name="designStatus" value="${designForm.autocompleteForm.designStatus}"/>
					<form:hidden path="actionDelete" value="false"/>
					<qp:authorization permission="autocompleteView">
						<a href="${pageContext.request.contextPath}/autocomplete/view?autocompleteForm.autocompleteId=${f:u(designForm.autocompleteForm.autocompleteId)}&openOwner=1&showImpactFlag=${f:u(designForm.showImpactFlag)}" class="btn qp-button qp-link-button qp-link-button"><qp:message code="sc.sys.0023" /></a>
					</qp:authorization>
					<qp:authorization permission="autocompleteDelete">
						<button type="button" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="$.qp.common.setFlag()"><qp:message code="sc.sys.0008" /></button>
					</qp:authorization>
				</div>
				<c:if test="${not empty affectedScreenDesgins}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0003"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-list-none-action">
                            <colgroup>
                                <col/>
                                <col/>
                                <col width="20%" />
                                <col width="20%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><qp:message code="sc.sys.0004"></qp:message></th>
                                    <th><qp:message code="sc.screendesign.0005"></qp:message></th>
                                    <th><qp:message code="sc.screendesign.0007"></qp:message></th>
                                    <th><qp:message code="sc.module.0007"></qp:message></th>
                                </tr>
                            </thead>
                            <c:forEach var="screen" items="${affectedScreenDesgins}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>
                                        <qp:formatText value="${screen.messageDesign.messageString}"/>
                                    </td>
                                    <td><qp:formatText value="${screen.screenCode}"/></td>
                                   	<td><qp:formatText value="${screen.moduleName}"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty affectedScreenDesgins}">
								<tr>
									<td colspan="4"><qp:message code="inf.sys.0013"/></td>
								</tr>
							</c:if>
                        </table>
					</div>
				</div>
				</c:if>
				<c:if test="${not empty affectedTableDesigns}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.tabledesign.0075"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-list-none-action">
                            <colgroup>
                                <col/>
                                <col width="20%" />
                                <col width="20%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><qp:message code="sc.sys.0004"></qp:message></th>
                                    <th><qp:message code="sc.tabledesign.0001"></qp:message></th>
                                    <th><qp:message code="sc.tabledesign.0002"></qp:message></th>
                                </tr>
                            </thead>
                            <c:forEach var="tableDesign" items="${affectedTableDesigns}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>
                                        <qp:formatText value="${tableDesign.tableName}"/>
                                    </td>
                                    <td><qp:formatText value="${tableDesign.tableCode}"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty affectedTableDesigns}">
								<tr>
									<td colspan="4"><qp:message code="inf.sys.0013"/></td>
								</tr>
							</c:if>
                        </table>
					</div>
				</div>
				</c:if>
				<c:if test="${not empty affectedDomainDesigns}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.domaindesign.0027"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-list-none-action">
                            <colgroup>
                                <col/>
                                <col width="20%" />
                                <col width="20%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><qp:message code="sc.sys.0004"></qp:message></th>
                                    <th><qp:message code="sc.domaindesign.0001"></qp:message></th>
                                    <th><qp:message code="sc.domaindesign.0002"></qp:message></th>
                                </tr>
                            </thead>
                            <c:forEach var="domainDesign" items="${affectedDomainDesigns}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>
                                        <qp:formatText value="${domainDesign.domainName}"/>
                                    </td>
                                    <td><qp:formatText value="${domainDesign.domainCode}"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty affectedDomainDesigns}">
								<tr>
									<td colspan="4"><qp:message code="inf.sys.0013"/></td>
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