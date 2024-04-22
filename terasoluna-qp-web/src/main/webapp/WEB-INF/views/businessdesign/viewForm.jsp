<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=blogiccomponent_businesslogicdesign_decisiontable_sys"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jsPlumb/jsPlumb-2.1.1.js"></script>

		<!-- view -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/common.js"></script>
		<!-- formula builder -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/formulaBuilder.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialBlogicDesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialViewBlogic.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processOverview.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/displayComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/validationComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/displayDialog.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processDialog.js"></script>
		<!-- from decision -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/constant.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/init.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/displayView.js"></script>
		<!-- function -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/functions.js"></script>
		<!-- Define variable in js -->
		<jsp:include page="commonVariable.jsp" />
	</tiles:putAttribute>
	<tiles:putAttribute name="header-name">
		<c:choose>
			<c:when test="${businessDesignForm.blogicType eq 2}">
				<span><qp:message code="sc.businesslogicdesign.0231" /></span>
			</c:when>
			<c:when test="${businessDesignForm.blogicType eq 1}">
				<span><qp:message code="sc.businesslogicdesign.0060" /></span>
			</c:when>
			<c:when test="${businessDesignForm.blogicType eq 3}">
				<span><qp:message code="sc.businesslogicdesign.0272" /></span>
			</c:when>
			<c:otherwise>
				<span><qp:message code="sc.businesslogicdesign.0061" /></span>
			</c:otherwise>
		</c:choose>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<script type="text/javascript">
			function setFlag(obj) {
				$("#flagStatus").val(false);
			}
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1}">
			<jsp:include page="componentView.jsp" />
			<c:choose>
				<c:when test="${businessDesignForm.blogicType eq 1}">
					<jsp:include page="template/rowCommonTemplate.jsp" />
				</c:when>
				<c:when test="${businessDesignForm.blogicType eq 2}">
					<jsp:include page="template/rowWSTemplate.jsp" />
				</c:when>
				<c:when test="${businessDesignForm.blogicType eq 3}">
					<jsp:include page="template/rowBatchTemplate.jsp" />
				</c:when>
				<c:otherwise>
					<jsp:include page="template/rowTemplate.jsp" />
				</c:otherwise>
			</c:choose>
			<form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/businessdesign/deleteConfirm" modelAttribute="businessDesignForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="jsonInputBean" />
				<form:hidden path="jsonOutputBean" />
				<form:hidden path="jsonObjectDefinition" />
				<form:hidden path="jsonConnector" />
				<form:hidden path="jsonComponent" />
				<form:hidden path="returnType" />
				<form:hidden path="businessLogicName" />
				<form:hidden path="businessLogicCode" />
				<form:hidden path="remark" />
				<form:hidden path="designStatus" />
				<form:hidden path="moduleId" />
				<form:hidden path="moduleIdAutocomplete" />
				<form:hidden path="moduleType" />
				<form:hidden path="blogicType"/>
				<form:hidden path="projectId" />
                <form:hidden path="moduleCode"/>
                <form:hidden path="requestMethod"/>
                <form:hidden path="screenIdAutocomplete"/>
                <form:hidden path="screenFormIdAutocomplete"/>
			
				<!-- End define dialog for setting -->
				<!-- Block for display information maybe edit data -->
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0018" /></span>
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
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0005" /></th>
								<td><qp:formatText value="${businessDesignForm.businessLogicName}" /></td>
								
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0006" /></th>
								<td><qp:formatText value="${businessDesignForm.businessLogicCode}" /></td>
							</tr>
							<c:choose>
									<c:when test="${businessDesignForm.blogicType == 1}">
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0019" /> </th>
											<td colspan="3">
												<qp:message code="${CL_DESIGN_TYPE_COMMON_BLOGIC.get(businessDesignForm.customizeFlg.toString())}" />
											</td>
										</tr>
										<c:if test="${businessDesignForm.customizeFlg}">
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0021" /></th>
												<td id="valueContent" >
													<qp:formatText value="${businessDesignForm.fileName}" />
												</td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0022" /></th>
												<td >
													<qp:formatText value="${businessDesignForm.packageName}" />
												</td>
											</tr>
										</c:if>
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0266" /></th>
											<td><qp:message code="${CL_BD_DESIGN_MODE.get(businessDesignForm.designMode.toString())}" /></td>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0020" /></th>
											<td><qp:message code="${CL_DESIGN_STATUS.get(businessDesignForm.designStatus.toString())}" /></td>
										</tr>
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0028" /></th>
											<td colspan="3"><qp:formatRemark value="${businessDesignForm.remark}" /></td>
										</tr>
									</c:when>
									<c:when test="${businessDesignForm.blogicType == 2}">
										<tr>
											<th><qp:message code="sc.businesslogicdesign.0023" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
											<td colspan="3"><qp:formatText value="${CL_RETURN_TYPE_WEBSERVICE.get(businessDesignForm.returnType.toString())}"/></td>
										</tr>
										<tr>
											<th><form:label path="requestMethod"><qp:message code="sc.businesslogicdesign.0227"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
											<td><qp:message code="${CL_REQUEST_METHOD_WEBSERVICE.get(businessDesignForm.requestMethod.toString())}" /></td>
											<th><form:label path="authenticatedFlg"><qp:message code="sc.businesslogicdesign.0228"/></form:label></th>
											<td><qp:booleanFormatYesNo type="1" value="${businessDesignForm.authenticatedFlg}"></qp:booleanFormatYesNo></td>
										</tr>
										<tr>
											<th><qp:message code="sc.businesslogicdesign.0024" /></th>
											<td><qp:formatText value="${businessDesignForm.moduleIdAutocomplete}" /></td>
											<th><qp:message code="sc.businesslogicdesign.0229"/></th>
											<td><label name="url"></label></td>
										</tr>
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0266" /></th>
											<td><qp:message code="${CL_BD_DESIGN_MODE.get(businessDesignForm.designMode.toString())}" /></td>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0020" /></th>
											<td><qp:message code="${CL_DESIGN_STATUS.get(businessDesignForm.designStatus.toString())}" /></td>
										</tr>
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0028" /></th>
											<td colspan="3"><qp:formatRemark value="${businessDesignForm.remark}" /></td>
										</tr>
									</c:when>
									<c:otherwise>
										<!-- In case of standrand Blogic -->
										<c:if test="${businessDesignForm.blogicType eq '0'}">
											<tr>
											    <th><qp:message code="sc.businesslogicdesign.0023" /></th>
                                                <td><qp:message code="${CL_RETURN_TYPE.get(businessDesignForm.returnType.toString())}" /></td>
												<th><qp:message code="sc.businesslogicdesign.0227"/></th>
												<td colspan="3">
													<qp:message code="${CL_REQUEST_METHOD_STANDARD_BLOGIC.get(businessDesignForm.requestMethod.toString())}" />
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.businesslogicdesign.0024" /></th>
												<td><qp:formatText value="${businessDesignForm.moduleIdAutocomplete}" /></td>
												<th><qp:message code="sc.functiondesign.0002" /></th>
												<td><qp:formatText value="${businessDesignForm.functionDesignIdAutocomplete}" /></td>
											</tr>
											<tr>
												<c:if test="${businessDesignForm.requestMethod == 4}">
													<th><qp:message code="sc.businesslogicdesign.0257" /></th>
													<td><qp:formatText value="${businessDesignForm.screenIdAutocomplete}" /></td>
													<th>Form name</th>
													<td><qp:formatText value="${businessDesignForm.screenFormIdAutocomplete}" /></td>
												</c:if>
												<c:if test="${businessDesignForm.requestMethod != 4}">
													<th><qp:message code="sc.businesslogicdesign.0256" /></th>
													<td colspan="3"><qp:formatText value="${businessDesignForm.screenIdAutocomplete}" /></td>
												</c:if>
											</tr>
										</c:if>
										<c:if test="${businessDesignForm.blogicType eq '3'}">
											<tr>
											     <th><qp:message code="sc.businesslogicdesign.0169" /></th>
                                                <td colspan="3"><qp:message code="${CL_BATCH_TYPE.get(businessDesignForm.batchType.toString())}" /></td>
											</tr>
											<tr>
												<th><qp:message code="sc.businesslogicdesign.0024" /></th>
												<td><qp:formatText value="${businessDesignForm.moduleIdAutocomplete}" /></td>
												<th><qp:message code="sc.functiondesign.0002" /></th>
												<td><qp:formatText value="${businessDesignForm.functionDesignIdAutocomplete}" /></td>
											</tr>
										</c:if>
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0266" /></th>
											<td><qp:message code="${CL_BD_DESIGN_MODE.get(businessDesignForm.designMode.toString())}" /></td>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0020" /></th>
											<td><qp:message code="${CL_DESIGN_STATUS.get(businessDesignForm.designStatus.toString())}" /></td>
										</tr>
										<tr>
											<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0028" /></th>
											<td colspan="3"><qp:formatRemark value="${businessDesignForm.remark}" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
						</table>
					</div>
				</div>
				
				<div class="qp-div-action">
					<div class="form-inline form-group">
                        <c:if test="${businessDesignForm.openOwner eq 1}">
                            <input type="hidden" name="businessLogicId" value="${f:h(businessDesignForm.businessLogicId)}"/>
                            <input type="hidden" name="updatedDate" value="${businessDesignForm.updatedDate}"/>
                            <form:hidden path="flagAction" value="true" id="flagStatus"/>
                            <c:choose>
                            	<c:when test="${businessDesignForm.blogicType eq 1}">
                            		<c:choose>
	                                    <c:when test="${sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicDelete">
												<div class="checkbox">
													<label><form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
												</div>
                                            	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicModify">
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                </c:choose>
                            	</c:when>
                            	<c:when test="${businessDesignForm.blogicType eq 2}">
                            		<c:choose>
	                                    <c:when test="${businessDesignForm.moduleId == null && sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicDelete">
                                            	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicModify">
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId == null && sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId != null && businessDesignForm.moduleStatus eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicDelete">
                                            	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicModify">
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId != null && businessDesignForm.moduleStatus eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                </c:choose>
                            	</c:when>
                            	<c:when test="${businessDesignForm.blogicType eq 3}">
                            		<c:choose>
	                                    <c:when test="${businessDesignForm.moduleId == null && sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicDelete">
                                            	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicModify">
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId == null && sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId != null && businessDesignForm.moduleStatus eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicDelete">
                                            	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </qp:authorization>
	                                        <qp:authorization permission="businesslogicModify">
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId != null && businessDesignForm.moduleStatus eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                </c:choose>
                            	</c:when>
                            	<c:otherwise>
                            		<c:choose>
	                                    <c:when test="${businessDesignForm.moduleId == null && sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <c:if test="${businessDesignForm.designMode != 0}">
		                                        <div class="checkbox">
														<label><form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
												</div>
                                               	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </c:if>
	                                        <qp:authorization permission="businesslogicModify">
	                                        	<c:if test="${businessDesignForm.blogicType == 0}">
	                                        		<a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1&convertWSFlg=true">
	                                                    <qp:message code="sc.businesslogicdesign.0267"/>
	                                                </a>
	                                        	</c:if>
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId == null && sessionScope.CURRENT_PROJECT.status eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId != null && businessDesignForm.moduleStatus eq 1 && businessDesignForm.designStatus eq 1}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
	                                        </qp:authorization>
	                                        <c:if test="${businessDesignForm.designMode != 0}">
		                                        <div class="checkbox">
														<label><form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
												</div>
                                               	<button type="submit" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag(this)" ><qp:message code="sc.sys.0008"></qp:message></button>
	                                        </c:if>
	                                        <qp:authorization permission="businesslogicModify">
	                                        	<c:if test="${businessDesignForm.blogicType == 0}">
	                                        		<a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1&convertWSFlg=true">
	                                                    <qp:message code="sc.businesslogicdesign.0267"/>
	                                                </a>
	                                        	</c:if>
	                                            <a type="submit" class="btn btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:u(businessDesignForm.businessLogicId)}&mode=1">
	                                                <qp:message code="sc.sys.0006"></qp:message>
	                                            </a>
	                                        </qp:authorization>
	                                    </c:when>
	                                    <c:when test="${businessDesignForm.moduleId != null && businessDesignForm.moduleStatus eq 1 && businessDesignForm.designStatus eq 2}">
	                                        <qp:authorization permission="changeDesignStatus">
	                                            <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
	                                        </qp:authorization>
	                                    </c:when>
	                                </c:choose>
                            	</c:otherwise>
                            </c:choose>
                        </c:if>
                        </div>
                    </div>
                	
				<!-- Block for display tab -->
				
				<div id="tabs">
					<ul class="nav nav-tabs" id="com-menu-sidebar">
						<li id="tabInput"><a href="#tabBusiness-inputbean" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0025" /></a></li>
						<c:choose>
							<c:when test="${businessDesignForm.blogicType == 1}">
								<c:if test="${businessDesignForm.customizeFlg}">
									<li id="tabOutput"><a id="tabOutputBean" href="#tabBusiness-outputbean" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0026" /></a></li>
									<li id="tabListUsedBlogic" class="active"><a id="tabListUsedBlogic" href="#tabBusiness-listUsedBlogic" id="tabListUsedBlogic" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0157" /></a></li>
								</c:if>
								<c:if test="${!businessDesignForm.customizeFlg}">
									<li id="tabOutput"><a id="tabOutputBean" href="#tabBusiness-outputbean" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0026" /></a></li>
									<li id="tabObj"><a id="tabObjectDefinition" href="#tabBusiness-objectdefinition" id="tabObjectDefinition" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0027" /></a></li>
									<li id="tabListUsedBlogic"><a id="tabListUsedBlogic" href="#tabBusiness-listUsedBlogic" id="tabListUsedBlogic" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0157" /></a></li>
									<li id="tabBlogic" class="active"><a id="tabBlogicSetting" href="#tabBusiness-blogicsetting" id="tabBlogicSetting" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0028" /></a></li>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${businessDesignForm.blogicType != 3}">
									<li id="tabOutput"><a id="tabOutputBean" href="#tabBusiness-outputbean" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0026" /></a></li>
								</c:if>
								<li id="tabObj"><a id="tabObjectDefinition" href="#tabBusiness-objectdefinition" id="tabObjectDefinition" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0027" /></a></li>
								<c:if test="${businessDesignForm.clientCheckFlg}">
									<li id="tabClientCheck"><a href="#tabBusiness-clientcheck" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0048" /></a></li>
								</c:if>
								<li id="tabBlogic" class="active"><a id="tabBlogicSetting" href="#tabBusiness-blogicsetting" id="tabBlogicSetting" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0028" /></a></li>
							</c:otherwise>
						</c:choose>
					</ul>
					
					<div class="tab-content">
						<div id="tabBusiness-blogicsetting" class="tab-pane ${(businessDesignForm.blogicType == 1 && businessDesignForm.customizeFlg) ? '' : 'active'}" style="height: auto;">
							<table class="table table-bordered qp-table-list" id="allDragDropContent">
									<colgroup>
										<col width="100%"/>
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.businesslogicdesign.0030" /></th>
										</tr>
									</thead>
									<tr>
										<td style="vertical-align: top;height:530px;">
								            <div class="design-area" id="designArea">
								            	<div class="clearSelection" style="display: none">
								            		<a class="com-link-popup clear-all" href="#"><qp:message code="sc.businesslogicdesign.0031" /></a>&nbsp;&nbsp;
								            		<a class="com-link-popup select-all" href="#"><qp:message code="sc.businesslogicdesign.0032" /></a>
								            		<label class="qp-output-text" style="cursor: pointer;"> <input type="radio" name="controlMultiSelect" checked="checked"  class="qp-input-radio qp-input-radio-margin" value="off" add="off" onchange="onChangeDisableMultiSelect(this)"/><qp:message code="sc.businesslogicdesign.0033" />&nbsp;&nbsp;</label>
								            		<label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" class="qp-input-radio qp-input-radio-margin" value="on"/><qp:message code="sc.businesslogicdesign.0034" /></label>
								            	</div>
								            	<c:forEach var="scrComponent" items="${businessDesignForm.lstSequenceLogics}" varStatus="rowStatus">
					                                 <c:if test="${scrComponent.componentType == 14}">
							            				<div class="execution-class ${f:h(scrComponent.cssClass)}" id="${f:h(scrComponent.sequenceLogicId)}" componenttype = "${scrComponent.componentType}" ondblclick="${scrComponent.actionPath}" style="left: ${scrComponent.xCoordinates}px; top: ${scrComponent.yCoordinates}px;" add="off"
						                                	 data-toggle="tooltip" data-placement="right" title="${f:h(scrComponent.remark)}">
						                                	 <a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/designBlogic" style="display: none;"></a>
															<div style="border: 2px solid orange; padding: 3px; border-radius: 8px;">
																<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${scrComponent.imagePath}" class="qp-bdesign-node-image"/>
											            		${f:h(scrComponent.prefixLabel)}<span class="component-name">${f:h(scrComponent.sequenceLogicName)}</span>
															</div>
											            	<div class="ep"></div>
											            	<input type="hidden" name="componentElement" value="${f:h(scrComponent.strData)}">
											            </div>
							            			</c:if>
							            			<c:if test="${scrComponent.componentType != 14}">
						                                <div class="execution-class ${f:h(scrComponent.cssClass)}" id="${f:h(scrComponent.sequenceLogicId)}" componenttype = "${scrComponent.componentType}" ondblclick="${scrComponent.actionPath}" style="left: ${scrComponent.xCoordinates}px; top: ${scrComponent.yCoordinates}px;" add="off"
						                                	 data-toggle="tooltip" data-placement="right" title="${f:h(scrComponent.remark)}">
											            	<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${scrComponent.imagePath}" class="qp-bdesign-node-image"/>
											            	${f:h(scrComponent.prefixLabel)}<span class="component-name">${f:h(scrComponent.sequenceLogicName)}</span>
											            	<div class="ep"></div>
											            	<input type="hidden" name="componentElement" value="${f:h(scrComponent.strData)}">
											            </div>
										            </c:if>
					                        	</c:forEach>
								            </div>
										</td>
									</tr>
								</table>
							</div>
						<!-- List used blogic  tab -->
						<div id="tabBusiness-listUsedBlogic"  class="tab-pane ${(businessDesignForm.blogicType == 1 && businessDesignForm.customizeFlg) ? 'active' : ''}" style="height: auto;">
							<div class="panel panel-default qp-div-select">
				                <div class="panel-heading">
				                    <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
				                    <span class="pq-heading-text"><qp:message code="sc.decisiontable.0034"></qp:message></span>
				                </div>
				                <div class="panel-body">
				                    <div class="table-responsive">
				                        <table class="table table-bordered qp-table-list-none-action">
				                             <colgroup>
				                          <col />
				                          <col width="40%" />
				                          <col width="30%" />
				                          <col width="30%" />
				                      </colgroup>
				                            <thead>
				                                <tr>
				                                    <th><qp:message code="sc.sys.0004"></qp:message></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0005"></qp:message></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0006"></qp:message></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0013"></qp:message></th>
				                                </tr>
				                            </thead>
				                            <c:forEach var="listBD" items="${businessDesignForm.lstAffectedBlogicCommon}" varStatus="loop">
				                                <tr>
				                                    <td class="qp-output-fixlength tableIndex">${loop.index + 1}</td>
				                                    <td><qp:formatText value="${listBD.businessLogicName}"/></td>
				                                    <td><qp:formatText value="${listBD.businessLogicCode}"/></td>
				                                    <td><qp:message code="${CL_RETURN_TYPE.get(listBD.returnType.toString())}"/></td>
				                                </tr>
				                            </c:forEach>
				                            <c:if test="${empty businessDesignForm.lstAffectedBlogicCommon}">
				                                <tr>
				                                    <td colspan="4"><qp:message code="inf.sys.0013"/></td>
				                                </tr>
				                            </c:if>
				                        </table>
				                    </div>
				                </div>
				            </div>
						</div>
						<!-- Default client check  tab -->
						<div id="tabBusiness-clientcheck"  class="tab-pane" style="height: auto;">
							<div class="panel panel-default qp-div-select">
								<div class="panel-heading">
									<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0049" /></span>
								</div>
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-bordered qp-table-list-none-action" id="tmp_list_table">
											<colgroup>
												<col />
												<col width="20%" />
												<col width="30%" />
												<col width="50%" />
												<col width="100px" />
												<col width="80px" />
												<col width="80px" />
												<col width="110px" />
											</colgroup>
											<thead>
												<tr>
													<th ><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0050" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0051" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0052" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0053" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0054" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0055" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0056" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${businessDesignForm.lstItemValidations }" var="screenItemChecks" varStatus="status">
													<tr>
														<td class="com-output-fixlength">${status.index + 1}</td>
														<c:if test="${screenItemChecks.formRowspan > 0 }">
															<td rowspan="${screenItemChecks.formRowspan}">${screenItemChecks.screenFormCode}</td>
														</c:if>
														<c:if test="${screenItemChecks.areaRowspan > 0 }">
															<td rowspan="${screenItemChecks.areaRowspan}">${screenItemChecks.areaName}</td>
														</c:if>
								          				<td >${screenItemChecks.itemCode}</td>
								          				<td >
								          					<c:if test="${screenItemChecks.mandatoryFlg}">
								          						<qp:message code="sc.businesslogicdesign.0057" />
								          					</c:if>
								          				</td>
								          				<td >${screenItemChecks.maxlength}</td>
								          				<td >
								          					<c:if test="${not empty screenItemChecks.minVal || not empty screenItemChecks.maxVal}">
								          						${screenItemChecks.minVal} ~ ${screenItemChecks.maxVal}
								          					</c:if>
								          				</td>
								          				<td >${screenItemChecks.fmtCode}</td>
								          			</tr>
												</c:forEach>
												<c:if test="${empty businessDesignForm.lstItemValidations}">
					                                 <tr data-ar-rgroupindex=''>
					                                    <td colspan="8"><qp:message code="inf.sys.0013"/></td>
					                                </tr>
					                            </c:if>
							          		</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- Input Bean(in.) Tab  -->
						<div id="tabBusiness-inputbean" style="height: auto;" class="tab-pane">
							<div class="panel panel-default qp-div-select">
								<div class="panel-heading">
									<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
								</div>
								<div class="panel-body">
									<c:if test="${businessDesignForm.blogicType eq 0}">
										<table class="table table-bordered qp-table-list-none-action" id="tbl_inputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputFormCallback">
											<colgroup>
                                                <col width="40px"/>
												<col width="300px"/>
												<col width="40%" style="min-width: 150px"/>
												<col width="120px"/>
												<col width="40%" class="bd-in-screenitem-hidden" />
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th><qp:message code="sc.businesslogicdesign.0037" /></th>
													<th><qp:message code="sc.businesslogicdesign.0038" /></th>
													<th><qp:message code="sc.businesslogicdesign.0039" /></th>
													<th class="bd-in-screenitem-hidden"><qp:message code="sc.businesslogicdesign.0041" /></th>
<!-- 													<th></th> -->
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${businessDesignForm.lstInputBean }" var="inputObj" varStatus="status">
													<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }" >
														<td colspan="2">
															<div style="height:100%">
																<div>
																	<span  class="ar-groupIndex">${inputObj.tableIndex }</span>
																</div>
																<div class="pull-right" style="height:100%;vertical-align: middle;">
																	<qp:formatText value="${inputObj.messageStringAutocomplete}" />
																</div>
															</div>
														</td>
														<td>
											                <input type="hidden" name="lstInputBean[${status.index}].itemSequenceNo" value="${inputObj.itemSequenceNo}"/>
															<qp:formatText value="${inputObj.inputBeanCode}" />
														</td>
														<td>
															<div class="input-group">
																<c:if test="${inputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(inputObj.dataType.toString())}[]' />
																</c:if>
																<c:if test="${!inputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(inputObj.dataType.toString())}' />
																</c:if>
																<input type="hidden" name="lstInputBean[${status.index}].dataType" value='${inputObj.dataType}'/>
																<input type="hidden" name="lstInputBean[${status.index}].arrayFlg" value='${inputObj.arrayFlg}'/>
															</div>
														</td>
                                                        <td  class="bd-in-screenitem-hidden">
                                                            <!-- Adding HungHX -->
                                                            <input type="hidden" name="lstInputBean[${status.index}].screenItemMapping.screenItemId" value="${inputObj.screenItemMapping.screenItemId}"/>
                                                            <label name="lstInputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${inputObj.screenItemMapping.itemName}</label>
                                                        </td>
<!-- 														<td> -->
<!-- 															<div class="dropdown"> -->
<!-- 																<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button> -->
<!-- 																<ul class="dropdown-menu dropdown-menu-left"> -->
<!-- 																	<li> -->
<%-- 																		<form:hidden path="lstInputBean[${status.index}].scopeType" /> --%>
<%-- 																		<form:hidden path="lstInputBean[${status.index}].scopeValue" /> --%>
<%-- 					                                                    <form:hidden path="lstInputBean[${status.index}].scopeValueAutocomplete" /> --%>
<!-- 																		<a class="qp-link" onclick="openDialogGetScope(this,true)">Scope setting</a> -->
<!-- 																	</li> -->
<!-- 																	<li> -->
<%-- 																		<form:hidden path="lstInputBean[${status.index}].jsonValidationInputs" />  --%>
<!-- 																		<a class="qp-link" onclick="openModalValidationCheckDetail(this,true)">Validation check</a> -->
<!-- 																	</li> -->
<!-- 																</ul> -->
<!-- 															</div> -->
<!-- 														</td> -->
													</tr>
												</c:forEach>
												<c:if test="${empty businessDesignForm.lstInputBean}">
					                                <tr data-ar-rgroupindex=''>
					                                	<c:if test="${businessDesignForm.blogicType == 0 && businessDesignForm.returnType == 1 && businessDesignForm.requestMethod == 4}">	
					                                		<td colspan="5"><qp:message code="inf.sys.0013"/></td>
					                                	</c:if>
					                                	<c:if test="${businessDesignForm.blogicType != 0 || businessDesignForm.returnType != 1 || businessDesignForm.requestMethod != 4}">
					                                		<td colspan="4"><qp:message code="inf.sys.0013"/></td>
					                                	</c:if>
					                                </tr>
					                            </c:if>
											</tbody>
										</table>
									</c:if>
									<c:if test="${businessDesignForm.blogicType ne 0 }">
										<table class="table table-bordered qp-table-list-none-action" id="tbl_inputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputFormCallback">
											<colgroup>
												<col width="40px"/>
												<col width="300px"/>
												<col width="30%" style="min-width: 150px"/>
												<col width="120px"/>
											</colgroup>
											<thead>
												<tr>
													<th style="width=40px;"><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th><qp:message code="sc.businesslogicdesign.0037" /></th>
													<th><qp:message code="sc.businesslogicdesign.0038" /></th>
													<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${businessDesignForm.lstInputBean }" var="inputObj" varStatus="status">
													<tr data-ar-rgroup="${inputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-template" data-ar-rindex="${inputObj.itemSequenceNo }" data-ar-rgroupindex="${inputObj.tableIndex }" >
														<td colspan="2">
															<div style="height:100%">
																<div>
																	<span  class="ar-groupIndex">${inputObj.tableIndex }</span>
																</div>
																<div class="pull-right" style="height:100%;vertical-align: middle;">
																	<qp:formatText value="${inputObj.messageStringAutocomplete}" />
																</div>
															</div>
														</td>
														<td>
											                <input type="hidden" name="lstInputBean[${status.index}].itemSequenceNo" value="${inputObj.itemSequenceNo}"/>
															<qp:formatText value="${inputObj.inputBeanCode}" />
														</td>
														<td>
															<div class="input-group">
																<c:if test="${inputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(inputObj.dataType.toString())}[]' />
																</c:if>
																<c:if test="${!inputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(inputObj.dataType.toString())}' />
																</c:if>
															</div>
                                                        </td>
													</tr>
												</c:forEach>
												<c:if test="${empty businessDesignForm.lstInputBean}">
					                                <tr data-ar-rgroupindex=''>
					                                    <td colspan="4"><qp:message code="inf.sys.0013"/></td>
					                                </tr>
					                            </c:if>
											</tbody>
										</table>
									</c:if>
								</div>
							</div>
						</div>
						
						
						
						<!-- Output Bean(ou.) Tab -->
						<div id="tabBusiness-outputbean" style="height: auto;" class="tab-pane">
							<div class="panel panel-default qp-div-select">
								<div class="panel-heading">
									<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
								</div>
								<div class="panel-body">
									<c:if test="${businessDesignForm.blogicType == 0}">
										<table class="table table-bordered qp-table-list-none-action" id="tbl_outputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.outputFormCallback">
											<colgroup>
												<col width="40px"/>
												<col width="300px"/>
												<col width="40%" style="min-width: 150px"/>
												<col width="120px"/>
												<col width="40%" class="bd-ou-screenitem-hidden" />
											</colgroup>
											<thead>
												<tr>
													<th ><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0037" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0038" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0039" /></th>
													<th class="bd-ou-screenitem-hidden"><qp:message code="sc.businesslogicdesign.0041" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${businessDesignForm.lstOutputBean }" var="outputObj" varStatus="status">
													<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }" >
														<td colspan="2">
															<div style="height:100%">
																<div>
																	<span  class="ar-groupIndex">${outputObj.tableIndex }</span>
																</div>
																<div class="pull-right" style="height:100%;vertical-align: middle;">
																	<qp:formatText value="${outputObj.outputBeanName}" />
																</div>
															</div>
														</td>
														<td>
											                <input type="hidden" name="lstOutputBean[${status.index}].itemSequenceNo" value="${outputObj.itemSequenceNo}"/>
															<qp:formatText value="${outputObj.outputBeanCode}" />
														</td>
														<td>
															<div class="input-group">
																<c:if test="${outputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(outputObj.dataType.toString())}[]' />
																</c:if>
																<c:if test="${!outputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(outputObj.dataType.toString())}' />
																</c:if>
															</div>
														</td>
														<td  class="bd-ou-screenitem-hidden">
															<c:set var="myVar"/>
															<c:forEach var="screenItems" items="${outputObj.lstScreenItemMapping}" varStatus="loop">
																<c:if test="${loop.first == true}">
																	<c:set var="myVar" value="${screenItems.itemName}" />
																</c:if>
																<c:if test="${loop.first == false}">
																	<c:set var="myVar" value="${myVar};${screenItems.itemName}" />
																</c:if>
																<input type="hidden" name="lstOutputBean[${status.index}].lstScreenItemMapping[${loop.index}].screenItemId" value="${screenItems.screenItemId}"/>
															</c:forEach>
															<label name="lstOutputBean[${status.index}].screenItemIdAutocomplete" class="qp-output-text">${myVar}</label>
												        </td>
													</tr>
												</c:forEach>
												<c:if test="${empty businessDesignForm.lstOutputBean}">
					                                 <tr data-ar-rgroupindex=''>
					                                 	<c:choose>
					                                 		<c:when test="${(businessDesignForm.blogicType == 0 && (businessDesignForm.returnType == 0 || (businessDesignForm.returnType == 1 && businessDesignForm.requestMethod == 0))) || businessDesignForm.patternType == 1}">
					                                 			<td colspan="5"><qp:message code="inf.sys.0013"/></td>
					                                 		</c:when>
					                                 		<c:otherwise>
					                                 			<td colspan="4"><qp:message code="inf.sys.0013"/></td>
					                                 		</c:otherwise>
					                                 	</c:choose>
					                                </tr>
					                            </c:if>
											</tbody>
										</table>
									</c:if>
									<c:if test="${businessDesignForm.blogicType != 0}">
										<table class="table table-bordered qp-table-list-none-action" id="tbl_outputbean_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputFormCallback">
											<colgroup>
												<col width="40px"/>
												<col width="300px"/>
												<col width="100%" style="min-width: 150px"/>
												<col width="120px"/>
											</colgroup>
											<thead>
												<tr>
													<th ><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0037" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0038" /></th>
													<th ><qp:message code="sc.businesslogicdesign.0039" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${businessDesignForm.lstOutputBean }" var="outputObj" varStatus="status">
													<tr data-ar-rgroup="${outputObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-template" data-ar-rindex="${outputObj.itemSequenceNo }" data-ar-rgroupindex="${outputObj.tableIndex }" >
														<td colspan="2">
															<div style="height:100%">
																<div>
																	<span  class="ar-groupIndex">${outputObj.tableIndex }</span>
																</div>
																<div class="pull-right" style="height:100%;vertical-align: middle;">
			<%-- 														<qp:formatText value="${outputObj.messageStringAutocomplete}" /> --%>
																	<qp:formatText value="${outputObj.outputBeanName}" />
																</div>
															</div>
														</td>
														<td>
											                <input type="hidden" name="lstOutputBean[${status.index}].itemSequenceNo" value="${outputObj.itemSequenceNo}"/>
															<qp:formatText value="${outputObj.outputBeanCode}" />
														</td>
														<td>
															<div class="input-group">
																<c:if test="${outputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(outputObj.dataType.toString())}[]' />
																</c:if>
																<c:if test="${!outputObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(outputObj.dataType.toString())}' />
																</c:if>
															</div>
														</td>
													</tr>
												</c:forEach>
												<c:if test="${empty businessDesignForm.lstOutputBean}">
					                                 <tr data-ar-rgroupindex=''>
					                                    <td colspan="4"><qp:message code="inf.sys.0013"/></td>
					                                </tr>
					                            </c:if>
											</tbody>
										</table>
									</c:if>
								</div>	
							</div>
						</div>
						
						<!-- Object Definition tab -->
						<div id="tabBusiness-objectdefinition" style="height: auto;" class="tab-pane">
							<div class="panel panel-default qp-div-select">
								<div class="panel-heading">
									<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
								</div>
								<div class="panel-body">
									<table class="table table-bordered qp-table-list-none-action" id="tbl_objectdefinition_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.obFormCallback">
											<colgroup>
												<col width="40px" />
												<col />
												<col width="30%" style="min-width: 150px"/>
												<col width="120px"/>
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th><qp:message code="sc.businesslogicdesign.0037" /></th>
													<th><qp:message code="sc.businesslogicdesign.0038" /></th>
													<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${businessDesignForm.lstObjectDefinition }" var="objectDefinitionObj" varStatus="status">
													<tr data-ar-rgroup="${objectDefinitionObj.groupId}" class="ar-dataRow" data-ar-templateid="tbl_objectdefinition_list_define-template" data-ar-rindex="${objectDefinitionObj.itemSequenceNo }" data-ar-rgroupindex="${objectDefinitionObj.tableIndex }" >
														<td colspan="2">
															<div style="height:100%">
																<div>
																	<span  class="ar-groupIndex">${objectDefinitionObj.tableIndex }</span>
																</div>
																<div class="pull-right" style="height:100%;vertical-align: middle;">
																	<qp:formatText value="${objectDefinitionObj.objectDefinitionName}" />
																</div>
															</div>
														</td>
														<td>
															<qp:formatText value="${objectDefinitionObj.objectDefinitionCode}" />
														</td>
														<td>
															<div class="input-group">
																<c:if test="${objectDefinitionObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(objectDefinitionObj.dataType.toString())}[]' />
																</c:if>
																<c:if test="${!objectDefinitionObj.arrayFlg}">
																	<qp:formatText value='${CL_BD_DATATYPE.get(objectDefinitionObj.dataType.toString())}' />
																</c:if>
															</div>
														</td>
													</tr>
												</c:forEach>
												<c:if test="${empty businessDesignForm.lstObjectDefinition}">
					                                 <tr data-ar-rgroupindex=''>
					                                    <td colspan="4"><qp:message code="inf.sys.0013"/></td>
					                                </tr>
					                            </c:if>
											</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<br/>
					</div>
			</form:form>
		</c:if>
		
		<!-- Begin include message parameter component -->
		<jsp:include page="component/messageParameterBuilder.jsp" />
		<jsp:include page="component/messageValidateParameterBuilder.jsp" />
		<!-- End include message parameter component -->
	</tiles:putAttribute>
</tiles:insertDefinition>