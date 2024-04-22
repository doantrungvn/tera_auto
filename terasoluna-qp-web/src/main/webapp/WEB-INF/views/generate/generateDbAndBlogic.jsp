<tiles:insertDefinition name="layouts">

	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generate/javascript/validation.js"></script>
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span><qp:message code="sc.generation.0003"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<qp:authorization permission="moduleSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/module/search"><qp:message code="sc.module.0019" /></a>
		</qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/generation/generatedbblogic" method="POST" modelAttribute="generateDbAndBlogicForm">
			<form:hidden path="moduleId"/>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.module.0005" /></span>
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
							<th><qp:message code="sc.module.0007" /></th>
							<td><qp:formatText value="${module.moduleName}" /></td>
							<th><qp:message code="sc.module.0008" /></th>
							<td><qp:formatText value="${module.moduleCode}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0055" /></th>
							<td><input type="hidden" name="status" value="${module.status}"/><qp:message code="${CL_DESIGN_STATUS.get(module.status.toString())}" /></td>
							<th><qp:message code="sc.businesstype.0001" /></th>
							<td><qp:formatText value="${module.businessTypeName}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.generation.0005" /></th>
							<td>
								<label><form:radiobutton path="generateMode" value="2" cssClass="qp-input-radio-margin qp-input-radio" /><qp:message code="cl.generation.0006"/></label><br/>
								<label><form:radiobutton path="generateMode" value="1" cssClass="qp-input-radio-margin qp-input-radio" /><qp:message code="cl.generation.0007"/></label><br/>
							</td>
							<th><qp:message code="sc.sys.0028" /></th>
							<td><qp:formatText value="${module.remark}" /></td>
						</tr>
					</table>
				</div>
			</div>
		<!-- List result -->
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<c:choose>
					<c:when test="${not empty listOfScreen}">
						<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.generation.0004" />&nbsp;
						<span class="badge">&nbsp;${listOfScreen.size()}&nbsp;</span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">
							<qp:message code="sc.generation.0004" /><span class="badge">&nbsp;0&nbsp;</span>
						</span>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${not empty listOfScreen }">
						<table class="table table-bordered qp-table-list" id="tblListScreen">
							<colgroup>
								<col/>
								<col />
								<col width="30%" />
								<col width="15%" />
								<col width="10%" />
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:message code="sc.screendesign.0005" /></th>
									<th><qp:message code="sc.screendesign.0007" /></th>
									<th><qp:message code="sc.screendesign.0009" /></th>
									<th><qp:message code="sc.screendesign.0008" /></th>
									<th><input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="screenCode" onclick="changeChecked(this);"></th>
								</tr>
							</thead>
							<c:forEach var="screenList" items="${listOfScreen}" varStatus="rowStatus">
								<tr>
									<td>${rowStatus.count}</td>
									<td>
										<qp:authorization permission="screendesignView" isDisplay="true" displayValue="${screenList.messageDesign.messageString}">
											<a class="qp-link-popup" href="${pageContext.request.contextPath}/screendesign/view?screenId=${f:h(screenList.screenId)}&openOwner=0">
												<qp:formatText value="${screenList.messageDesign.messageString}"/>
											</a>
										</qp:authorization>
									</td>
									<td class="qp-output-text"><qp:formatText value="${screenList.screenCode}"/></td>
									<td class="qp-output-text"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(screenList.screenPatternType.toString())}"/></td>
									<td class="qp-output-text"><qp:formatText value="${CL_TEMPLATE_TYPE.get(screenList.templateType.toString())}"/></td>
									<td>
										<form:checkbox path="listScreenIds" value="${screenList.screenId}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
			</div>
		</div>
		<div class="qp-div-action">
			<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && not empty listOfScreen}">
				<qp:authorization permission="changeDesignStatus">
					<input type="hidden" id="messageScreen" value="<qp:message code="tqp.screendesign" />" />
					<button type="submit" class="btn qp-button qp-dialog-confirm" onclick="requireScreenDesign" messageId="inf.sys.0025"><qp:message code="sc.sys.0050" /></button>
				</qp:authorization>
			</c:if>
		</div>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>