<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.module"></qp:message></span></li>
         <li><span><qp:message code="sc.module.0022"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="moduleSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/module/search"><qp:message code="sc.module.0019" /></a>
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/functiondesign/register"><qp:message code="sc.functiondesign.0007"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">		
		<form:form action="${pageContext.request.contextPath}/module/modify" modelAttribute="moduleForm" method="post">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
			
			<form:hidden path="moduleId"/>		
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
							<th>
								<form:label path="moduleName">
									<qp:message code="sc.module.0007" />
									<span class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029" /></span>
								</form:label>
							</th>
							<td><form:input path="moduleName" value="${moduleForm.moduleName}" cssClass="form-control qp-input-text qp-convention-name" maxlength="200" /></td>
							<th><form:label path="moduleCode"><qp:message code="sc.module.0008" /></form:label><span class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029" /></span></th>
							<td><form:input path="moduleCode" value="${moduleForm.moduleCode}" cssClass="form-control qp-input-text qp-convention-code" maxlength="200"  /></td>
						</tr>
						<tr>
							<th><form:label path="status"><qp:message code="sc.sys.0055" /></form:label></th>
							<td><form:hidden path="status"/><qp:message code="${CL_DESIGN_STATUS.get(moduleForm.status.toString())}" /></td>
							<th>
								<form:label path="businessTypeName"><qp:message code="sc.businesstype.0001" /></form:label>
							</th>
							<td><jsp:include page="../businesstype/businessTypeTree.jsp" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.module.0034" /></th>
							<td><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(moduleForm.moduleType.toString())}" /><input type="hidden" name="moduleType" value="${moduleForm.moduleType}"/></td>
							<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
							<td><form:textarea path="remark" type="text" rows="3" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
						</tr>
					</table>
				</div>
			</div>
			<%-- <c:if test="${moduleForm.moduleType eq 0}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.module.0020" /></span>
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
								<th><form:label path="confirmationType"><qp:message code="sc.module.0009" /></form:label></th>
								<td>
									<c:forEach var="item" items="${CL_MODULE_CONFIRM_TYPE}">
										<label><form:radiobutton path="confirmationType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/><qp:message code="${CL_MODULE_CONFIRM_TYPE.get(item.key)}" /></label>
									</c:forEach>
								</td>
								<th><form:label path="completionType"><qp:message code="sc.module.0010" /></form:label>
								</th>
								<td>
									<c:forEach var="item" items="${CL_MODULE_COMPLETE_TYPE}">
										<label><form:radiobutton path="completionType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/><qp:message code="${CL_MODULE_COMPLETE_TYPE.get(item.key)}" /></label>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</c:if> --%>
			
			<div class="qp-div-action">
				<qp:authorization permission="moduleModify">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					<form:hidden path="updatedDate" />
					<form:hidden path="updatedBy" />
				</qp:authorization>
			</div>
			
			<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.functiondesign.0011" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col />
									<col width="50%" />
									<col width="45%" />
									<col width="5%"/>
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.functionmaster.0005" /></th>
										<th><qp:message code="sc.functionmaster.0006" /></th>
										<th></th>
									</tr>
								</thead>
								<c:forEach var="functionDesign" items="${moduleForm.listFunctionDesign}" varStatus="rowStatus"> 
									<tr>
										<td><qp:formatInteger value="${rowStatus.count}" /></td>
										<td><qp:formatText value="${functionDesign.functionName}"/></td>
										<td><qp:formatText value="${functionDesign.functionCode}"/></td>
										<td align="center" >
											<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
												<qp:authorization permission="functiondesignModify">
													<a href="${pageContext.request.contextPath}/functiondesign/modify?functionId=${f:h(functionDesign.functionId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action"></a>
												</qp:authorization>
											</c:if>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty moduleForm.listFunctionDesign}">
									<tr>
										<td colspan="4"><qp:message code="inf.sys.0013"/></td>
									</tr>
								</c:if>
							</table>
					</div>
				</div>
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
								<col />
								<col width="30%" />
								<col width="10%" />
								<col width="20%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.businesslogicdesign.0005" /></th>
									<th><qp:message code="sc.businesslogicdesign.0006" /></th>
									<th><qp:message code="sc.sqldesign.0022" /></th>
									<th><qp:message code="sc.screendesign.0005" /></th>
								</tr>
							</thead>
							<c:forEach var="blogic" items="${moduleForm.listBusinessDesign}" varStatus="rowStatus"> 
								<tr>
									<td><qp:formatInteger value="${rowStatus.count}" /></td>
									<td>
										<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/view?businessLogicId=${f:h(blogic.businessLogicId)}"><qp:formatText value="${blogic.businessLogicName}" /></a>
									</td>
									<td><qp:formatText value="${blogic.businessLogicCode}"/></td>
									<td><qp:message code="${CL_RETURN_TYPE.get(blogic.returnType.toString())}"/></td>
									<td>
										<qp:authorization permission="screendesignView" isDisplay="true" displayValue="${blogic.messageString}">
											<a class="qp-link-popup" href="${pageContext.request.contextPath}/screendesign/view?screenId=${f:h(blogic.screenId)}&openOwner=0">
												<qp:formatText value="${blogic.messageString}"/>
											</a>
										</qp:authorization>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty moduleForm.listBusinessDesign}">
									<tr>
										<td colspan="5"><qp:message code="inf.sys.0013"/></td>
									</tr>
							</c:if>
						</table>
					</div>
				</div>
			</div>
				<div align="right">
					<a href="${pageContext.request.contextPath}/screendesign/transition?moduleId=${f:h(moduleForm.moduleId)}&moduleIdAutocomplete=${f:u(moduleForm.moduleName)}&mode=9" target="_blank"><qp:message code="tqp.screentransition" /></a>
				</div>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.module.0028" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="30%" />
										<col width="15%" />
										<col width="10%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.screendesign.0005" /></th>
											<th><qp:message code="sc.screendesign.0007" /></th>
											<th><qp:message code="sc.screendesign.0009" /></th>
											<th><qp:message code="sc.screendesign.0183" /></th>
										</tr>
									</thead>
									<c:forEach var="screenList" items="${moduleForm.listScreenDesign}" varStatus="rowStatus">
										<tr>
											<td>${rowStatus.count}</td>
											<td>
												<qp:authorization permission="screendesignView" isDisplay="true" displayValue="${screenList.messageDesign.messageString}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/screendesign/view?screenId=${f:h(screenList.screenId)}&openOwner=0">
														<qp:formatText value="${screenList.messageDesign.messageString}"/>
													</a>
												</qp:authorization>
											</td>
											<td><qp:formatText value="${screenList.screenCode}"/></td>
											<td><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(screenList.screenPatternType.toString())}"/></td>
											<td><qp:formatText value="${CL_TEMPLATE_TYPE.get(screenList.templateType.toString())}"/></td>
										</tr>
									</c:forEach>
									<c:if test="${empty moduleForm.listScreenDesign}">
										<tr>
											<td colspan="5"><qp:message code="inf.sys.0013"/></td>
										</tr>
									</c:if>
								</table>
						</div>
					</div>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>