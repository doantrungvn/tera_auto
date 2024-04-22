<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.problemlist.0015"></qp:message>
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
				<form:form action="${pageContext.request.contextPath}/module/delete" modelAttribute="moduleForm" method="post">
					<form:hidden path="moduleId"/>
					<form:hidden path="moduleName"/>
					<input type="hidden" name="updatedDate" value="${moduleForm.updatedDate}"/>
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
					<div class="qp-div-action">
						<form:hidden path="actionDelete" value="false" />
						<qp:authorization permission="sqldesignView">
							<a
								href="${pageContext.request.contextPath}/module/view?moduleId=${f:u(moduleForm.moduleId)}&openOwner=1"
								class="btn qp-button qp-link-button qp-link-button"><qp:message
									code="sc.sys.0023" /></a>
						</qp:authorization>
						<qp:authorization permission="deleteObjectHasFk">
							<button type="button" class="btn btn-warning qp-dialog-confirm"
								messageId="inf.sys.0014" onclick="$.qp.common.setFlag()">
								<qp:message code="sc.sys.0008" />
							</button>
						</qp:authorization>
					</div>
					<c:if test="${not empty affectedScreenDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message
										code="sc.screendesign.0003" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
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
									<c:forEach var="screenDesign" items="${affectedScreenDesigns}"
										varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td><qp:formatText
													value="${screenDesign.messageDesign.messageString}" /></td>
											<td><qp:formatText value="${screenDesign.screenCode}" /></td>
											<td><qp:formatText value="${screenDesign.moduleName}" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty affectedTableDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message
										code="sc.tabledesign.0075" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="40%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.tabledesign.0019" /></th>
											<th><qp:message code="sc.tabledesign.0020" /></th>
										</tr>
									</thead>
									<c:forEach var="tableDesign" items="${affectedTableDesigns}"
										varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td><qp:formatText value="${tableDesign.tableName}" /></td>
											<td><qp:formatText value="${tableDesign.tableCode}" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty affectedTableDesigns}">
										<tr>
											<td colspan="3"><qp:message code="inf.sys.0013" /></td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty affectedBusinessDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message
										code="sc.businesslogicdesign.0008" /></span>
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
									<tbody>
										<c:forEach var="businessLogic"
											items="${affectedBusinessDesigns}" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td><qp:formatText
														value="${businessLogic.businessLogicName}" /></td>
												<td><qp:formatText
														value="${businessLogic.businessLogicCode}" /></td>
												<td><qp:formatText
														value="${businessLogic.moduleIdAutocomplete}" /></td>
											</tr>
										</c:forEach>
										<c:if test="${empty affectedBusinessDesigns}">
											<tr>
												<td colspan="4"><qp:message code="inf.sys.0013" /></td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty affectedDomainDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message
										code="sc.domaindesign.0027" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="40%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.domaindesign.0001" /></th>
											<th><qp:message code="sc.domaindesign.0002" /></th>
										</tr>
									</thead>
									<c:forEach var="domainDesign" items="${affectedDomainDesigns}"
										varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td><qp:formatText value="${domainDesign.domainName}" />
											</td>
											<td><qp:formatText value="${domainDesign.domainCode}" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty affectedDomainDesigns}">
										<tr>
											<td colspan="3"><qp:message code="inf.sys.0013" /></td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty affectedSqlDesigns}">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.sqldesign.0009" /></span>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="40%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.sqldesign.0010" /></th>
											<th><qp:message code="sc.sqldesign.0011" /></th>
										</tr>
									</thead>
									<c:forEach var="sqlDesign" items="${affectedSqlDesigns}"
										varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td><qp:formatText value="${sqlDesign.sqlDesignName}" />
											</td>
											<td><qp:formatText value="${sqlDesign.sqlDesignCode}" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty affectedSqlDesigns}">
										<tr>
											<td colspan="3"><qp:message code="inf.sys.0013" /></td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</c:if>
				</form:form>
			</c:if>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>