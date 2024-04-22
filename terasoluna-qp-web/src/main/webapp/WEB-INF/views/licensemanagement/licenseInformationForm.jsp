<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">	
		<qp:message code="sc.permission.licensemanagementRegister.remark"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1}">
			<div id="wapper">
				<form:form method="post" action="${pageContext.request.contextPath}/licensemanagement/register" modelAttribute="licenseManagementForm">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.licensemanagement.0011"/></span>
						</div>
						<div class="panel-body">
							<table class="table table-bordered qp-table-form">
								<colgroup>
									<col width="20%" />
									<col width="30%" />
									<col width="20%" />
									<col width="30%" />
								</colgroup>
								<tr class="success form-inline">
									<td style="text-align: left;" colspan="4"><qp:message code="sc.licensemanagement.0012"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0001"/></th>
									<td><qp:formatRemark value="${licenseManagementForm.customerName}" /></td>
									<th><qp:message code="sc.licensemanagement.0000"/></th>
									<td><qp:formatRemark value="${licenseManagementForm.customerCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0004"/></th>
									<td><qp:formatText value="${licenseManagementForm.tel}" /></td>
									<th><qp:message code="sc.licensemanagement.0005"/></th>
									<td><qp:formatText value="${licenseManagementForm.email}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0006"/></th>
									<td><qp:formatRemark value="${licenseManagementForm.address}" /></td>
									<th></th>
									<td></td>
								</tr>
								<tr class="success form-inline">
									<td style="text-align: left;" colspan="4"><qp:message code="sc.licensemanagement.0011"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0003"/></th>
									<td><qp:formatRemark value="${licenseManagementForm.projectName}" /></td>
									<th><qp:message code="sc.licensemanagement.0002"/></th>
									<td><qp:formatRemark value="${licenseManagementForm.projectCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0007"/></th>
									<td><qp:formatInteger value="${licenseManagementForm.num}" /></td>
									<th><qp:message code="sc.licensemanagement.0008"/></th>
									<td><qp:formatText value="${licenseManagementForm.version}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0009"/></th>
									<td><qp:formatDateTime value="${licenseManagementForm.startDate}" /></td>
									<th><qp:message code="sc.licensemanagement.0010"/></th>
									<td><qp:formatDateTime value="${licenseManagementForm.expiredDate}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensemanagement.0014"/></th>
									<td><qp:formatDateTime value="${licenseManagementForm.appliedDate}" /></td>
									<th><qp:message code="sc.licensemanagement.0013"/></th>
									<td><qp:formatText value="${CL_LICENSEMANAGEMENT_STATUS.get(licenseManagementForm.status.toString())}" /></td>
								</tr>
							</table>
						</div>
					</div>
						<div class="qp-div-action">
								<form:hidden path="licenseId"/>
								<form:hidden path="customerName" />
								<form:hidden path="customerCode" />
								<form:hidden path="projectCode" />
								<form:hidden path="projectName" />
								<form:hidden path="projectId" />
								<form:hidden path="tel" />
								<form:hidden path="email" />
								<form:hidden path="address" />
								<form:hidden path="num" />
								<form:hidden path="version" />
								<form:hidden path="status" />
								<form:hidden path="startDate" />
								<form:hidden path="expiredDate" />
								<form:hidden path="appliedDate" />
								<form:hidden path="licenseFileName" />
								<qp:authorization permission="licensemanagementRegister">
									<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0018" /></button>
								</qp:authorization>
						</div>
				</form:form>
			</div>
		</c:if>
	</tiles:putAttribute>
	
</tiles:insertDefinition>