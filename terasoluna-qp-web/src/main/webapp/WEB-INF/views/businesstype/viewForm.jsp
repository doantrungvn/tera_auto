<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
			<qp:message code="sc.businesstype.0009"></qp:message>
	</tiles:putAttribute>
	<c:if test="${not businessTypeForm.hasErrors}">
		<tiles:putAttribute name="body">
			<c:if test="${notExistFlg ne 1}">
				<form:form action="${pageContext.request.contextPath}/businesstype/delete" method="post" modelAttribute="businessTypeForm">
					<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
					
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.businesstype.0004" /></span>
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
									<th><qp:message code="sc.businesstype.0001" /></th>
									<td class="word-wrap"><qp:formatText value="${businessTypeForm.businessTypeName}" /></td>
									<th><qp:message code="sc.businesstype.0002" /></th>
									<td class="word-wrap"><qp:formatText value="${businessTypeForm.businessTypeCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesstype.0003" /></th>
									<td class="word-wrap"><qp:formatText value="${businessTypeForm.parentBusinessTypeName}" /></td>
									<th><qp:message code="sc.sys.0028" /></th>
									<td class="word-wrap"><qp:formatRemark value="${businessTypeForm.remark}" /></td>
								</tr>
							</table>
						</div>
					</div>
					<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
						<div class="qp-div-action">
							<qp:authorization permission="businesstypeDelete">
								<form:hidden path="businessTypeId"/>
								<form:hidden path="updatedDate"/>
								<button type="button" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
							</qp:authorization>
							<qp:authorization permission="businesstypeModify">
								<a type="submit" class="btn qp-button qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/businesstype/modify?businessTypeId=${businessTypeForm.businessTypeId}&mode=1"><qp:message code="sc.sys.0006" /></a>
							</qp:authorization>
						</div>
					</c:if>
					 <!-- begin list of related module -->
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.project.0012" /></span>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col  />
										<col width="40%" />
										<!-- <col width="30%" /> -->
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:message code="sc.module.0007" /></th>
											<th><qp:message code="sc.module.0008" /></th>
											<%-- <th><qp:message code="sc.project.0005" /></th> --%>
										</tr>
									</thead>
									<c:forEach var="module" items="${businessTypeForm.modules}" varStatus="status">
										<tr>
											<td><qp:formatInteger value="${status.count}" /></td>
											<td>
											   <qp:authorization permission="moduleView" isDisplay="true" displayValue="${module.moduleName}">
												   <a class="qp-link-popup" href="${pageContext.request.contextPath}/module/view?moduleId=${f:h(module.moduleId)}"><qp:formatText value="${module.moduleName}" /></a>
											   </qp:authorization>
											</td>
											<td class="word-wrap"><qp:formatText value="${module.moduleCode}" /></td>
											<%-- <td class="word-wrap"><qp:formatText value="${module.projectName}" /></td> --%>
										</tr>
									</c:forEach>
									<c:if test="${empty businessTypeForm.modules}">
										<tr><td colspan="3"><qp:message code="inf.sys.0013"/></td></tr>
									</c:if>
								</table>
							</div>
						</div>
					</div>
					<!-- end list of related module -->
				</form:form>
			</c:if>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>