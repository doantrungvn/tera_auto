<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
			List Blogic affected
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>
<c:if test="${ not empty table}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/tabledesign/delete" modelAttribute="tableDesignForm">
			<form:hidden path="tableId" />
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.tabledesign.0005" /></span>
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
							<th><qp:message code="sc.tabledesign.0001" /></th>
							<td><qp:formatText value="Table design For Prototype" /></td>
							<th><qp:message code="sc.tabledesign.0002" /></th>
							<td><qp:formatText value="${table.tableCode }" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.tabledesign.0003" /></th>
							<td><qp:formatText value="${table.projectName}" /></td>
							<th><qp:message code="sc.tabledesign.0013" /></th>
							<td><qp:formatText value="Table design For Prototype" /></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">Dependent information list</span>
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
							<th>Module name</th>
							<th>Blogic name</th>
							<th>Blogic code</th>
						</tr>
						</thead>
						<tbody >
													<tr>
								<td>1</td>
								<td rowspan="2">Delivery</td>
								<td>Initial Search Order</td>
								<td>InitialSearchOrder</td>
							</tr>							
							<tr>
								<td>2</td>
								<td>Process Search Order</td>
								<td>ProcessSearchOrder</td>
							</tr>
							
														<tr>
								<td>3</td>
								<td rowspan="2">Delivery note</td>
								<td>Initial Register Order</td>
								<td>InitialRegisterOrder</td>
							</tr>							
							<tr>
								<td>4</td>
								<td>Process Register Order</td>
								<td>ProcessRegisterOrder</td>
							</tr>
							
						</tbody>
					</table>
					
				</div>
			</div>
			
			
			<div class="qp-div-action">
				<qp:authorization permission="domaindesignModify">
					<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/tabledesign/modify?tableId=${table.tableId}&mode=1">
						Save
					</a>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>