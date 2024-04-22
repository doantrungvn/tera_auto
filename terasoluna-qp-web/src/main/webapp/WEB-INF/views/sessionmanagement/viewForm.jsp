<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.sessionmanagement.0005"></qp:message>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialCommonTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sessionmanagement/javascript/sessionmanagement.js"></script>
	</tiles:putAttribute>
	<c:if test="${not sessionManagementForm.hasErrors}">
		<tiles:putAttribute name="body">
			<c:if test="${notExistFlg ne 1}">
				<form:form method="post" action="${pageContext.request.contextPath}/sessionmanagement/delete" modelAttribute="sessionManagementForm">
					<form:hidden path="sessionManagementId" />
					<form:hidden path="sessionManagementType" />
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.sessionmanagement.0005"></qp:message></span>
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
									<th><form:label path="sessionManagementName">
											<qp:message code="sc.sessionmanagement.0002"></qp:message>
										</form:label></th>
									<td>${sessionManagementForm.sessionManagementName}</td>
									<th><form:label path="sessionManagementCode">
											<qp:message code="sc.sessionmanagement.0003"></qp:message>
										</form:label></th>
									<td>${sessionManagementForm.sessionManagementCode}</td>
								</tr>
								<tr>
									<th><qp:message code="sc.sessionmanagement.0008" /></th>
									<td>${CL_SM_DATATYPE.get(sessionManagementForm.dataType.toString())}<c:if test="${sessionManagementForm.arrayFlg }"> []</c:if></td>
									<th>
                                        <c:if test="${sessionManagementForm.dataType == 14 || sessionManagementForm.dataType == 16 || sessionManagementForm.dataType == 17 }">
                                            <qp:message code="sc.sessionmanagement.0009" />
                                        </c:if>
                                    </th>
									<td>${sessionManagementForm.objectIdAutocomplete}</td>
								</tr>
							</table>
						</div>
					</div>

					<div class="qp-div-action">
					<c:if test="${sessionManagementForm.sessionManagementType != null && sessionManagementForm.sessionManagementType == 1 }">
						<qp:authorization permission="sessionmanagementDelete">
							<a type="button" class="btn qp-button-warning qp-link-button qp-link-button qp-dialog-confirm"> <qp:message code="sc.sys.0008" /></a>
						</qp:authorization>
						<qp:authorization permission="sessionmanagementModify">
							<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/sessionmanagement/modify?sessionManagementId=${f:u(sessionManagementForm.sessionManagementId)}&mode=1&sessionManagementType=${f:u(sessionManagementForm.sessionManagementType)}"> <qp:message code="sc.sys.0006" /></a>
						</qp:authorization>
					</c:if>
					</div>
                    
                    <div class="panel panel-default qp-div-select">
                        <div class="panel-heading">
                            <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
                            <span class="pq-heading-text"><qp:message code="sc.module.0029" /></span>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-bordered qp-table-list-none-action">
                                    <colgroup>
                                        <col />
                                        <col width="33%" />
                                        <col width="33%" />
                                        <col width="33%" />
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th><qp:message code="sc.sys.0004" /></th>
                                            <th><qp:message code="sc.businesslogicdesign.0005" /></th>
                                            <th><qp:message code="sc.businesslogicdesign.0006" /></th>
                                            <th><qp:message code="sc.module.0007" /></th>
                                        </tr>
                                    </thead>
                                    <c:forEach var="blogic" items="${sessionManagementForm.listBusinessDesign}" varStatus="rowStatus"> 
                                        <tr>
                                            <td><qp:formatInteger value="${rowStatus.count}" /></td>
                                            <td>
                                                <a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/view?businessLogicId=${f:h(blogic.businessLogicId)}"><qp:formatText value="${blogic.businessLogicName}" /></a>
                                            </td>
                                            <td><qp:formatText value="${blogic.businessLogicCode}"/></td>
                                            <td>
                                                <qp:formatText value="${blogic.moduleName}" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty sessionManagementForm.listBusinessDesign}">
                                        <tr>
                                            <td colspan="5"><qp:message code="inf.sys.0013"/></td>
                                        </tr>
                                    </c:if>
                                </table>
                            </div>
                        </div>
                    </div>
				</form:form>
			</c:if>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>