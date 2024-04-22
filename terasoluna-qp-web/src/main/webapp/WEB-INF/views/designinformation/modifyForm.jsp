<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.designinformation"></qp:message></span></li>
         <li><span><qp:message code="sc.designinformation.0003"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/designinformation/javascript/designinformation.js"></script>
		<qp:authorization permission="designinformationSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/designinformation/search"><qp:message code="sc.designinformation.0001"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<script id="tbl_list_detail-template" type="text/template">
				<tr>
						<td colspan="4">
									<span id="id-block-reload-data0" onclick="removeTableRow(this,'tbl_list_detail');" title="<qp:message code="sc.designinformation.0024"/>" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px"></span>
									<table class="table table-bordered qp-table-form" id="tbl_list_detail" >
										<colgroup>
											<col width="20%" />
											<col width="30%" />
											<col width="20%" />
											<col width="30%" />
										</colgroup>
										<tbody class="ui-sortable">
											<tr>
												<th><label for="designInformationDetail[].subtitle"><qp:message code="sc.designinformation.0010"/>&nbsp;&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029"/></label></th>
												<td colspan="3"><input id ="designInformationDetail[].subtitle" name="designInformationDetail[].subtitle" class="form-control qp-input-text" maxlength="50" value="" /></td>
											</tr>
											<tr>
												<th><label for="designInformationDetail[].remark"><qp:message code="sc.sys.0028"/></label></th>
												<td colspan="3"><textarea class="form-control qp-input-textarea" id="designInformationDetail[].remark" name="designInformationDetail[].remark"/></td>
											</tr>
										<tbody class="ui-sortable">
									</table>
						</td>
					</tr>
		</script>
		<script id="tbl_list_relation-template" type="text/template">
			<tr><td colspan="4">
									<span id="id-block-reload-data0" onclick="removeTableRow(this,'tbl_list_relation');" title="<qp:message code="sc.designinformation.0024"/>" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px"></span>
									<table class="table table-bordered qp-table-form" >
										<colgroup>
											<col width="20%" />
											<col width="30%" />
											<col width="20%" />
											<col width="30%" />
										</colgroup>
										<tbody class="ui-sortable">
												<tr>
													<th><label for="designRelationSetting[].moduleCode"><qp:message code="sc.module.0007"/>&nbsp;&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></label></th>
													<td colspan="3">
						                               <select name="designRelationSetting[].moduleCode" id="designRelationSetting[].moduleCode" class="form-control qp-input-select">
						                                   <option value=""><qp:message code="sc.sys.0030" /></option>
						                                   <c:forEach var="resource" items="${moduleResources}">
						                                       <option value="${resource.messageCode}"><qp:message code="${resource.messageCode}" /></option>
						                                   </c:forEach>
						                               </select>
													</td>
												</tr>
										</tbody>
									</table>
			</td></tr>
		</script>
		<form:form action="${pageContext.request.contextPath}/designinformation/modify" modelAttribute="designInformationForm" method="post">
			<c:if test="${notExistFlg ne 1 }">
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
								<th><form:label path="designName"><qp:message code="sc.designinformation.0013"/> </form:label></th>
								<td colspan="3"><form:label path="designName"><qp:formatText value="${designInformationForm.designName}" /></form:label></td>
								<form:hidden path="designName"/>
							</tr>
							<tr>
								<th><form:label path="remark"><qp:message code="sc.sys.0028"/></form:label></th>
								<td colspan="3"><form:textarea cssClass="form-control qp-input-textarea" path="remark" /></td>
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
				<!-- Detail information -->
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.designinformation.0015"/></span>
					</div>
					<div class="panel-body">
				<table class="table table-borderless qp-table-form" id="tbl_list_detail" >
					<colgroup>
						<col width="20%" />
						<col width="30%" />
						<col width="20%" />
						<col width="30%" />
					</colgroup>
					<tbody class="ui-sortable">
					<c:forEach var="designInformationDetail" items="${designInformationForm.designInformationDetail}" varStatus="status">
						<tr>
						<td colspan="4">
						<form:hidden path="designInformationDetail[${status.index}].designInformationDetailId"/>
						<c:if test="${not empty designInformationDetail.designInformationDetailId}">
								<%-- <span id="id-block-reload-data0" onclick="removeTableRow(this,'tbl_list_detail');" title="<qp:message code="sc.designinformation.0024"/>" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px"></span> --%>
									<table class="table table-bordered qp-table-form" id="tbl_list_detail" >
										<colgroup>
											<col width="20%" />
											<col width="30%" />
											<col width="20%" />
											<col width="30%" />
										</colgroup>
										<tbody class="ui-sortable">
								<tr>
									<th><form:label path="designInformationDetail[${status.index}].subtitle"><qp:message code="sc.designinformation.0010"/></form:label></th>
									<td colspan="3"><form:label path="designInformationDetail[${status.index}].subtitle"><qp:formatText value="${designInformationDetail.subtitle }"/></form:label></td>
									<form:hidden path="designInformationDetail[${status.index}].subtitle"/>
								</tr>
								<tr>
									<th><form:label path="designInformationDetail[${status.index}].remark"><qp:message code="sc.sys.0028"/></form:label></th>
									<td colspan="3"><form:label path="designInformationDetail[${status.index}].remark"><qp:formatText value="${designInformationDetail.remark }"/></form:label></td>
									<form:hidden path="designInformationDetail[${status.index}].remark"/>
								</tr>
									</table>
								
						</c:if>
						<c:if test="${empty designInformationDetail.designInformationDetailId}">
									<table class="table table-bordered qp-table-form" >
										<colgroup>
											<col width="20%" />
											<col width="30%" />
											<col width="20%" />
											<col width="30%" />
										</colgroup>
										<tbody class="ui-sortable">
											<tr>
												<th><form:label path="designInformationDetail[${status.index}].subtitle"><qp:message code="sc.designinformation.0010"/> <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
												<td colspan="3"><form:input path="designInformationDetail[${status.index}].subtitle" class="form-control qp-input-text" maxlength="50" value="" /></td>
											</tr>
											<tr>
												<th><form:label path="designInformationDetail[${status.index}].remark"><qp:message code="sc.sys.0028"/></form:label></th>
												<td colspan="3"><form:textarea cssClass="form-control qp-input-textarea" path="designInformationDetail[${status.index}].remark"/></td>
											</tr>
										</tbody>
									</table>
							</c:if>
							</td></tr>
						</c:forEach>
					</table>
					<div class="qp-add-left">
						<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkForRegister(this);" href="javascript:void(0)" style="" title="<qp:message code='sc.designinformation.0017'/>"></a> 
					</div>
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
						<table class="table table-borderless qp-table-form" style="border-style: none;" id="tbl_list_relation">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody class="ui-sortable">
							<c:forEach var="designRelationSetting" items="${designInformationForm.designRelationSetting}" varStatus="status">
								<tr>
								<td colspan="4">
								<form:hidden path="designRelationSetting[${status.index}].designInformationId"/>
								<c:if test="${not empty designRelationSetting.designInformationId}">
									<%-- <span id="id-block-reload-data0" onclick="removeTableRow(this,'tbl_list_detail');" title="<qp:message code="sc.designinformation.0024"/>" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px"></span> --%>
											<table class="table table-bordered qp-table-form" >
												<colgroup>
													<col width="20%" />
													<col width="30%" />
													<col width="20%" />
													<col width="30%" />
												</colgroup>
												<tbody class="ui-sortable">
													<tr>
														<th><form:label path="designRelationSetting[${status.index}].moduleCode"><qp:message code="sc.module.0007"/></form:label></th>
														<td colspan="3">
					                              		 <form:label path= "designRelationSetting[${status.index}].moduleCode" ><qp:message code="${designRelationSetting.moduleCode }"/></form:label>
														</td>
														<form:hidden path="designRelationSetting[${status.index}].moduleCode"/>
													</tr>
												</tbody>
											</table>
										
								</c:if>
								<c:if test="${empty designRelationSetting.designInformationId}">
										<table class="table table-bordered qp-table-form" >
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tbody class="ui-sortable">
												<tr>
													<th><form:label path="designRelationSetting[${status.index}].moduleCode"><qp:message code="sc.module.0007"/> <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
													<td colspan="3">
						                               <form:select path="designRelationSetting[${status.index}].moduleCode" cssClass="form-control qp-input-select">
						                                   <form:option value=""><qp:message code="sc.sys.0030" /></form:option>
						                                   <c:forEach var="resource" items="${moduleResources}">
						                                       <form:option value="${resource.messageCode}"><qp:message code="${resource.messageCode}" /></form:option>
						                                   </c:forEach>
						                               </form:select>
													</td>
												</tr>
											</tbody>
										</table>
								</c:if>
								</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					<div class="qp-add-left">
						<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkForRegister(this);" href="javascript:void(0)" style="" title="<qp:message code='sc.designinformation.0018'/>"></a> 
					</div>
				</div>
			</div>
				
				<br/>
				<!-- // Add comment information -->
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
								<th><form:label path="comment"><qp:message code="sc.designinformation.0012"/></form:label></th>
								<td colspan="3"><form:textarea cssClass="form-control qp-input-textarea" path="comment" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<form:hidden path="designInformationId"/>
					<form:hidden path="updatedBy"/>
					<form:hidden path="updatedDate"/>
					<form:hidden path="updatedByName"/>
					<form:hidden path="createdByName"/>
					<form:hidden path="createdBy"/>
					<form:hidden path="createdDate"/>
					<qp:authorization permission="designinformationModify">
						<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>