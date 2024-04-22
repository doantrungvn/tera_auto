<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">	
		<qp:message code="sc.permission.licensedesignView.remark"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/licensemaster/javascript/init.js" ></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1}">
			<div id="wapper">
				<form:form method="post" action="${pageContext.request.contextPath}/licensedesign/delete" modelAttribute="licenseDesignForm">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.licensedesign.0011"/></span>
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
									<td style="text-align: left;" colspan="4"><qp:message code="sc.licensedesign.0012"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensedesign.0001"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.customerName}" /></td>
									<th><qp:message code="sc.licensedesign.0000"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.customerCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensedesign.0004"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.tel}" /></td>
									<th><qp:message code="sc.licensedesign.0005"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.email}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensedesign.0006"/></th>
									<td class="word-wrap"><qp:formatRemark value="${licenseDesignForm.address}" /></td>
									<th></th>
									<td></td>
								</tr>
								<tr class="success form-inline">
									<td style="text-align: left;" colspan="4"><qp:message code="sc.licensedesign.0011"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensedesign.0003"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.projectName}" /></td>
									<th><qp:message code="sc.licensedesign.0002"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.projectCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensedesign.0007"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.num}" /></td>
									<th><qp:message code="sc.licensedesign.0008"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.version}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.licensedesign.0009"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.startDate}" /></td>
									<th><qp:message code="sc.licensedesign.0010"/></th>
									<td class="word-wrap"><qp:formatText value="${licenseDesignForm.expiredDate}" /></td>
								</tr>
							</table>
						</div>
					</div>
						<div class="qp-div-action">
								<form:hidden path="licenseId" />
								<form:hidden path="projectId" value="${sessionScope.CURRENT_PROJECT.projectId}"/>
								<form:hidden path="customerName" />
								<form:hidden path="customerCode" />
								<qp:authorization permission="licensedesignDelete">
									<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008"/></button>
					            </qp:authorization>
					            <qp:authorization permission="licensedesignModify">
									<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/licensedesign/modify?licenseId=${licenseDesignForm.licenseId}&mode=1"><qp:message code="sc.sys.0006" /></a>
					            </qp:authorization>
						</div>
				</form:form>
			</div>
		</c:if>
	</tiles:putAttribute>
	
</tiles:insertDefinition>