<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.module.0023" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<script type="text/javascript">
			function setFlag() {
				$("#actionDelete").val(true);
			}
		</script>
	</tiles:putAttribute>
	
	<c:if test="${not moduleForm.hasErrors}">
		<tiles:putAttribute name="body">
			<c:if test="${notExistFlg ne 1}">
				<form:form action="${pageContext.request.contextPath}/module/deleteConfirm" modelAttribute="moduleForm" method="post">
					<form:hidden path="moduleId"/>
					<form:hidden path="moduleName"/>
					<form:hidden path="updatedDate"/>
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
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
									<td><qp:formatText value="${moduleForm.moduleName}" /></td>
									<th><qp:message code="sc.module.0008" /></th>
									<td><qp:formatText value="${moduleForm.moduleCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.sys.0055" /></th>
									<td><input type="hidden" name="status" value="${moduleForm.status}"/><qp:message code="${CL_DESIGN_STATUS.get(moduleForm.status.toString())}"  /></td>
									<th><qp:message code="sc.businesstype.0001" /></th>
									<td><qp:formatText value="${moduleForm.businessTypeName}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.module.0034" /></th>
									<td><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(moduleForm.moduleType.toString())}"  /></td>
									<th><qp:message code="sc.sys.0028" /></th>
									<td><qp:formatRemark value="${moduleForm.remark}" /></td>
								</tr>
							</table>
						</div>
					</div>
					<c:if test="${moduleForm.moduleType eq 0}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.module.0020" /></span>
							</div>
							<%-- <div class="panel-body">
								<table class="table table-bordered qp-table-form">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th><qp:message code="sc.module.0009" /></th>
										<td><qp:message code="${CL_MODULE_CONFIRM_TYPE.get(moduleForm.confirmationType.toString())}" /></td>
										<th><qp:message code="sc.module.0010" /></th>
										<td><qp:message code="${CL_MODULE_COMPLETE_TYPE.get(moduleForm.completionType.toString())}" /></td>
									</tr>
								</table>
							</div> --%>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered qp-table-list-none-action" id="tmp_list_table">
										<colgroup>
											<col />
											<col width="50%" />
											<col width="50%" />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.sys.0004" /></th>
												<th><qp:message code="sc.tabledesign.0019" /></th>
												<th><qp:message code="sc.module.0015" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="moduleTableMapping" items="${moduleForm.moduleTableMappings}" varStatus="status">
												<tr>
													<td class="qp-output-fixlength tableIndex">${status.count}</td>
													<td><qp:formatText value="${moduleTableMapping.tblDesignName}" /></td>
													<td><qp:formatText value="${CL_ENTITY_TYPE.get(moduleTableMapping.tableMappingType.toString())}" /></td>
												</tr>
											</c:forEach>
											<c:if test="${empty moduleForm.moduleTableMappings}">
												<tr><td colspan="3"><qp:message code="inf.sys.0013"/></td></tr>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>

					<div class="qp-div-action">
						<c:if test="${moduleForm.openOwner eq 1}">
							<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && moduleForm.status eq 1}">
								<c:if test="${checkDesignStatus eq 0}">
									<qp:authorization permission="moduleModify">
										<form:hidden path="actionDelete" value="false"/>
										<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
									</qp:authorization>
								</c:if>
								<sec:authorize access="hasRole('moduleSpecialdelete')">
									<button type="button" class="btn btn-md btn-danger qp-dialog-confirm" messageId="inf.sys.0034" onclick="setFlag()"><qp:message code="sc.sys.0052" /></button>
								</sec:authorize>
								<sec:authorize access="!hasRole('moduleSpecialdelete')">
									<qp:authorization permission="moduleDelete">
									<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag()"><qp:message code="sc.sys.0008" /></button>
									</qp:authorization>
								</sec:authorize>
								<qp:authorization permission="moduleModify">
									<form:hidden path="actionDelete" value="false"/>
									<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/module/modify?moduleId=${moduleForm.moduleId}&mode=1"><qp:message code="sc.sys.0006" /></a>
								</qp:authorization>
							</c:if>
							<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && moduleForm.status eq 2}">
								<qp:authorization permission="changeDesignStatus">
									<form:hidden path="actionDelete" value="false"/>
									<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
								</qp:authorization>
							</c:if>
						</c:if>
					</div>
					
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.functiondesign.0011" /></span>
						</div>
						<table class="table table-bordered qp-table-list-none-action">
							<colgroup>
								<col />
								<col width="50%" />
								<col width="50%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.functionmaster.0005" /></th>
									<th><qp:message code="sc.functionmaster.0006" /></th>
								</tr>
							</thead>
							<c:forEach var="functionDesign" items="${moduleForm.listFunctionDesign}" varStatus="rowStatus"> 
							<tr>
								<td><qp:formatInteger value="${rowStatus.count}" /></td>
								<td>
									<a class="qp-link-popup" href="${pageContext.request.contextPath}/functiondesign/view?functionId=${f:h(functionDesign.functionId)}&status=0"><qp:formatText value="${functionDesign.functionName}" /></a>
								</td>
								<td><qp:formatText value="${functionDesign.functionCode}"/></td>
							</tr>
						</c:forEach>
						<c:if test="${empty moduleForm.listFunctionDesign}">
							<tr>
								<td colspan="3"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
						</table>
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
										<col width="25%" />
										<col width="30%" />
										<col width="10%" />
										
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:message code="sc.businesslogicdesign.0005"/></th>
											<th><qp:message code="sc.businesslogicdesign.0006" /></th>
											<th><qp:message code="sc.screendesign.0005" /></th>
											<th><qp:message code="sc.sqldesign.0022"/></th>
											
										</tr>
									</thead>
									<c:forEach var="blogic" items="${moduleForm.listBusinessDesign}" varStatus="rowStatus"> 
										<tr>
											<td><qp:formatInteger value="${rowStatus.count}" /></td>
											<td>
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/view?businessLogicId=${f:h(blogic.businessLogicId)}"><qp:formatText value="${blogic.businessLogicName}" /></a>
											</td>
											<td><qp:formatText value="${blogic.businessLogicCode}"/></td>
											
											<td >
												 <qp:authorization permission="screendesignView" isDisplay="true" displayValue="${blogic.messageString}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/screendesign/view?screenId=${f:h(blogic.screenId)}&openOwner=0">
														<qp:formatText value="${blogic.messageString}"/>
													</a>
												</qp:authorization>
											</td>
											<td><qp:message code="${CL_RETURN_TYPE.get(blogic.returnType.toString())}"/></td>
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
												<th><qp:message code="sc.sys.0004" /></th>
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
												<td class="qp-output-text"><qp:formatText value="${screenList.screenCode}"/></td>
												<td class="qp-output-text"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(screenList.screenPatternType.toString())}"/></td>
												<td class="qp-output-text"><qp:formatText value="${CL_TEMPLATE_TYPE.get(screenList.templateType.toString())}"/></td>
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
					<input type="hidden" name="updatedDate" value="${moduleForm.updatedDate}"/>
				</form:form>
			</c:if>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>