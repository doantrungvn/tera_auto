<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.autocomplete"></qp:message></span></li>
         <li><span><qp:message code="sc.problemlist.0015"></qp:message></span></li>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/sqldesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<div id="qp-sqldesign">
		<form:form method="post" modelAttribute="autocompleteDesignForm"
			action="${pageContext.request.contextPath}/autocomplete/modify">
			<c:set var="designForm" value="${autocompleteDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.autocompleteForm.autocompleteId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.sqldesign.0021" /></span>
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
					<input type="hidden" name="formJson" value="${f:h(formJson)}" />
						<input type="hidden" name="isAdvanced" value="false" />
					<qp:authorization permission="autocompleteModify">
						<button type="submit" 
							class="btn qp-button" 
							name="jsonBack"
							>
							<qp:message code="sc.sys.0023" />
						</button>
						<button type="submit" name="isJsonForm"
								class="btn qp-button qp-dialog-confirm" 
								data-confirm-bcallback="$.qp.common.formSubmitCallback"
								data-confirm-pcallback="$.qp.sqlbuilder.validateBeforeSubmit">
								<qp:message code="sc.sys.0031"></qp:message>
						</button>
					</qp:authorization>
				</div>
				<%-- <c:if test="${not empty affectedBusinessDesigns}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0008" /></span>
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
							<tbody >
								<c:forEach var="businessLogic" items="${affectedBusinessDesigns}" varStatus="status">
									<tr>
										<td>${status.count}</td>
										<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
										<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
										<td><qp:formatText value="${businessLogic.moduleIdAutocomplete}" /></td>
									</tr>
								</c:forEach>
								<c:if test="${empty affectedBusinessDesigns}">
										<tr>
											<td colspan="4"><qp:message code="inf.sys.0013"/></td>
										</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				</c:if> --%>
				<c:if test="${not empty affectedScreenDesigns}">
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text">Screen design</span>
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
                            <c:forEach var="screenDesign" items="${affectedScreenDesigns}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>
                                        <qp:formatText value="${screenDesign.messageDesign.messageString}"/>
                                    </td>
                                    <td><qp:formatText value="${screenDesign.screenCode}"/></td>
                                   	<td><qp:formatText value="${screenDesign.moduleName}"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty affectedScreenDesigns}">
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
	</div>
	</tiles:putAttribute>
</tiles:insertDefinition>