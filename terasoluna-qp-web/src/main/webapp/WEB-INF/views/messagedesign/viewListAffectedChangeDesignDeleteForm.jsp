<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
			<qp:message code="sc.problemlist.0015"/>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
	
	<c:if test="${ not empty messageDesignForm}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/messagedesign/delete" modelAttribute="messageDesignForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.messagedesign.0034"/></span>
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
								<th><qp:message code="sc.messagedesign.0009"/></th>
								<td><qp:formatText value="${messageDesignForm.messageString}"/></td>
								<th><qp:message code="sc.message.0003"/></th>
								<td><qp:formatText value="${messageDesignForm.messageCode}"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="messagedesignModify">
						<form:hidden path="messageCode" value="${messageDesignForm.messageCode }"/>
						<form:hidden path="messageDesignId" value="${messageDesignForm.messageDesignId }" />
						<form:hidden path="updatedDate" value="${messageDesignForm.updatedDate}"/>
						<form:hidden path="projectId" value="${messageDesignForm.projectId}"/>
						<a type="submit" class="btn qp-button qp-link-button qp-link-button" href="${pageContext.request.contextPath}/messagedesign/view?messageCode=${messageDesignForm.messageCode}&mode=1"><qp:message code="sc.sys.0023" /></a>
						<button type="button" class="btn qp-button-warning qp-dialog-confirm"><qp:message code="sc.sys.0008" /></button>
					</qp:authorization>
				</div>
				
				<!-- //List screen item afftected -->
				<c:if test="${not empty messageDesignForm.listOfScreenItem}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.screendesign.0248"/></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col />
									<col width="30%">
									<col width="30%">
									<col width="30%">
								</colgroup>
								<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.screendesign.0007"/></th>
									<th><qp:message code="sc.screendesign.0178"/></th>
									<th><qp:message code="sc.messagedesign.0039"/></th>
								</tr>
								</thead>
								<tbody >
									<c:forEach var="screenItem" items="${messageDesignForm.listOfScreenItem}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${screenItem.screenCode}" /></td>
											<td><qp:formatText value="${screenItem.screenAreaCode}" /></td>
											<td><qp:formatText value="${screenItem.groupItemName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
				
				<!-- //List menu design afftected -->
				<c:if test="${not empty messageDesignForm.listOfMenuDesignItem}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.messagedesign.0038"/></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col />
									<col width="100%">
								</colgroup>
								<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th style="text-align: center;"><qp:message code="sc.menudesign.0002" /></th>
								</tr>
								</thead>
								<tbody >
									<c:forEach var="menuDesign" items="${messageDesignForm.listOfMenuDesignItem}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${menuDesign.menuName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
				
				<!-- //List of business design -->
				<c:if test="${not empty messageDesignForm.listOfBusinessDesign}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.functionmaster.0045"/></span>
						</div>
						<div class="panel-body">
							<table id="tbl_list_Subject"
								class="table table-bordered qp-table-list-none-action" data-ar-callback="callback">
								<colgroup>
									<col width="10%">
									<col width="30%">
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
									<c:forEach var="businessDesign" items="${messageDesignForm.listOfBusinessDesign}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><qp:formatText value="${businessDesign.businessLogicName}" /></td>
											<td><qp:formatText value="${businessDesign.businessLogicCode}" /></td>
											<td><qp:formatText value="${businessDesign.moduleName}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>