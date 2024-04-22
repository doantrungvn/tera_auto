<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.designinformation.0019"/>
	</tiles:putAttribute>
	
	<c:if test="${notExistFlg ne 1 }">
	<tiles:putAttribute name="body">
	<form:form action="${pageContext.request.contextPath}/designinformation/delete" modelAttribute="designInformationForm" method="post">
	
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<!-- Basic information -->
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.designinformation.0014"/></span>
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
							<th><qp:message code="sc.designinformation.0013"/> </th>
							<td colspan="3"><form:label path="designName"><qp:formatText value="${designInformationForm.designName}" /></form:label></td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0028"/></th>
							<td colspan="3"><form:label path="remark"><qp:formatRemark value="${designInformationForm.remark}" /></form:label></td>
						</tr>
						<tr>
							<th><form:label path="createdBy"><qp:message code="sc.designinformation.0008"/></form:label></th>
	                           <td><form:label path="createdBy"/><qp:formatText value="${designInformationForm.createdByName }"/></td>
	                           <th><form:label path="createdDate"><qp:message code="sc.designinformation.0009"/></form:label></th>
	                           <td><form:label path="createdDate"/><qp:formatText value="${designInformationForm.createdDate }"/></td>
						</tr>
						<tr>
							<th><form:label path="updatedBy"><qp:message code="sc.designinformation.0006"/></form:label></th>
	                           <td><form:label path="updatedBy"/><qp:formatText value="${designInformationForm.updatedByName }"/></td>
	                           <th><form:label path="updatedDate"><qp:message code="sc.designinformation.0007"/></form:label></th>
	                           <td><form:label path="updatedDate"/><qp:formatText value="${designInformationForm.updatedDate }"/></td>
						</tr>
					</table>
				</div>
			</div>
			<br/>
			<!-- Detail information -->
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.designinformation.0015"/></span>
				</div>
				<div class="panel-body">
					<c:forEach var="designInformationDetail" items="${designInformationForm.designInformationDetail}" varStatus="status">
						<table class="table table-bordered qp-table-form" id="tbl_list_detail" >
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody class="ui-sortable">
								<tr>
									<th><qp:message code="sc.designinformation.0010"/> </th>
									<td colspan="3"><form:label path="designInformationDetail[${status.index}].subtitle"><qp:formatText value="${designInformationDetail.subtitle }"/></form:label></td>
								</tr>
								<tr>
									<th><qp:message code="sc.sys.0028"/></th>
									<td colspan="3"><form:label path="designInformationDetail[${status.index}].remark"><qp:formatRemark value="${designInformationDetail.remark }"/></form:label></td>
								</tr>
							<tbody class="ui-sortable">
						</table>
						<br/>
					</c:forEach>
				</div>
			</div>
			<br/>
			
			<!-- Relation setting -->
			<div class="panel panel-default qp-div-information" >
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.designinformation.0016"/></span>
				</div>
				<div class="panel-body">
					<c:forEach var="designRelationSetting" items="${designInformationForm.designRelationSetting}" varStatus="status">
						<table class="table table-bordered qp-table-form" >
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody class="ui-sortable">
								<tr>
									<th><qp:message code="sc.module.0007"/></th>
									<td colspan="3">
		                               <form:label path= "designRelationSetting[${status.index}].moduleCode" ><qp:message code="${designRelationSetting.moduleCode }"/></form:label>
									</td>
								</tr>
							</tbody>
						</table>
						<br/>
					</c:forEach>
					</div>
				</div>
			<br/>
			<!-- // Add comment information -->
			<c:if test="${!empty designInformationForm.comment}">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span><qp:message code="sc.designinformation.0011"/></span>
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
							<th><qp:message code="sc.designinformation.0012"/></th>
							<td colspan="3"><form:label path="comment"><qp:formatText value="${designInformationForm.comment }"/></form:label></td>
						</tr>
					</table>
				</div>
			</div>
			</c:if>
			<div class="qp-div-action">
				<form:hidden path="designInformationId"/>
				<form:hidden path="comment" />
				<form:hidden path="updatedBy"/>
				<form:hidden path="updatedDate"/>
				<form:hidden path="updatedByName"/>
				<form:hidden path="createdByName"/>
				<form:hidden path="createdBy"/>
				<form:hidden path="createdDate"/>
				<qp:authorization permission="designinformationDelete">
					<button type="button" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008"/></button>
				</qp:authorization>
				<qp:authorization permission="designinformationModify">
					<a href="${pageContext.request.contextPath}/designinformation/modify?designInformationId=${designInformationForm.designInformationId}&mode=1" class="btn qp-button qp-link-button qp-link-popup-navigate" type="submit"><qp:message code="sc.sys.0006"/></a>
				</qp:authorization>
			</div>
			</form:form>
			</tiles:putAttribute>
		</c:if>

</tiles:insertDefinition>