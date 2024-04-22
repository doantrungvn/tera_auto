<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.licensedesign"></qp:message></span></li>
		<li><span><qp:message code="sc.permission.licensedesignSearch.remark" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/licensedesign/javascript/licensedesign.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#projectCode").text($("#projectName").val());
			});
			
			function selectProject(event){
				 if(event.item != null || event.item == ""){
					// Label
				 	$("#projectCode").text(event.item.output01);
					// Input hidden
				 	$("input[name$='projectCode']").val(event.item.output01);
				 	$("#projectName").val(event.item.optionLabel);
				 }
			}
			
			function changeProject(event){
				var projectId = $("input[name='projectId']").val();
				if(projectId == null || projectId == ""){
					$("#projectCode").text("");
					$("input[name$='projectCode']").val("");
				 	$("#projectName").val("");
				}
			}
		</script>	
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="licensedesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/licensedesign/register">
					<qp:message code="sc.permission.licensedesignRegister.remark" />
				</a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post"
			action="${pageContext.request.contextPath}/licensedesign/search"
			modelAttribute="licenseDesignSearchForm">
			<input type="hidden" id="fileName" value="${fileName}">
			<input type="hidden" id="fileName" value="${fileName}">
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="20%">
							<col width="30%">
						</colgroup>
						<tbody>
							<tr>
								<th><form:label path="customerName"><qp:message code="sc.licensedesign.0001" /></form:label></th>
								<td><form:input path="customerName" class="form-control qp-input-text" maxlength="400" /></td>
								<th><form:label path="customerCode"><qp:message code="sc.licensedesign.0000" /></form:label></th>
								<td><form:input path="customerCode" class="form-control qp-input-text" maxlength="150" /></td>

							</tr>
							<tr>
							<th>
								<form:label path="projectId"><qp:message code="sc.project.0005"/></form:label>
							</th>
							<td>							
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getProjectForAutocomplete" 
									value="${licenseDesignSearchForm.projectId}" name="projectId" displayValue="${licenseDesignSearchForm.projectIdAutocomplete}" 
									mustMatch="true" maxlength="200" arg01="${sessionScope.ACCOUNT_PROFILE.accountId}">
								</qp:autocomplete>
							</td>
							<th></th>	
							<%-- <th>
								<qp:message code="sc.project.0006"/>
							</th>
							<td>
								<label id = "projectCode" path ="projectCode"></label>
								<form:hidden path="projectCode"/>
							</td> --%>
							</tr>
							<tr>
								<th><form:label path="num"><qp:message code="sc.licensedesign.0007" /></form:label></th>
								<td><form:input path="num" cssClass="form-control qp-input-serial" size="100"/></td>
								<th><form:label path="email"><qp:message code="sc.licensedesign.0005" /></form:label></th>
								<td><form:input path="email" class="form-control qp-input-text" maxlength="50" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.licensedesign.0009" /></th>
								<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromStartDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toStartDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
								<th><qp:message code="sc.licensedesign.0010" /></th>
								<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromExpiredDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toExpiredDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="button" class="btn qp-button" onclick="checkBeforeSubmit()">
					<qp:message code="sc.sys.0001" />
				</button>
			</div>
		</form:form>	
		<c:if test="${page!=null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="licenseDesignSearchForm"
						action="/licensedesign/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col width="4%">
									<col >
									<col width="15%">
									<col width="18%">
									<!-- <col width="11%"> -->
									<col width="7%">
									<col width="11%">
									<col width="10%">
									<col width="10%">
									<col width="7%">
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="customer_name" colCode="sc.licensedesign.0001" form="licenseDesignSearchForm" /></th>
										<th><qp:columnSort colName="customer_code" colCode="sc.licensedesign.0000" form="licenseDesignSearchForm" /></th>
										<th><qp:columnSort colName="project_name" colCode="sc.licensedesign.0003" form="licenseDesignSearchForm" /></th>
										<%-- <th><qp:columnSort colName="project_code" colCode="sc.licensedesign.0002" form="licenseDesignSearchForm" /></th> --%>
										<th><qp:columnSort colName="version" colCode="sc.licensedesign.0008" form="licenseDesignSearchForm" /></th>
										<th><qp:columnSort colName="number_of_user" colCode="sc.licensedesign.0007" form="licenseDesignSearchForm" /></th>
										<th><qp:columnSort colName="start_date" colCode="sc.licensedesign.0009" form="licenseDesignSearchForm" /></th>
										<th><qp:columnSort colName="expired_date" colCode="sc.licensedesign.0010" form="licenseDesignSearchForm" /></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="licenseDesign" items="${page.content}" varStatus="rowStatus">
										<tr class="form-inline">
											<td class="qp-output-fixlength"><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
											<td class="word-wrap">
												<qp:authorization permission="licensedesignView" isDisplay="true" displayValue="${licenseDesign.customerName}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/licensedesign/view?licenseId=${f:h(licenseDesign.licenseId)}&status=1"><qp:formatText value="${licenseDesign.customerName}" /></a>
												</qp:authorization>
											</td>
											<td class="qp-output-text"><qp:formatText value="${licenseDesign.customerCode}"/></td>
											<td class="qp-output-text"><qp:formatText value="${licenseDesign.projectName}"/></td>
											<%-- <td class="qp-output-text"><qp:formatText value="${licenseDesign.projectCode}"/></td> --%>
											<td class="qp-output-text"><qp:formatText value="${licenseDesign.version}"/></td>
											<td class="qp-output-text"><qp:formatText value="${licenseDesign.num}"/></td>
											<td class="qp-output-datetime"><qp:formatText value="${licenseDesign.startDate}"/></td>
											<td class="qp-output-datetime"><qp:formatText value="${licenseDesign.expiredDate}"/></td>
											<td class="qp-table-list-action-field">
												<qp:authorization permission="licensedesignModify">
													<a href="${pageContext.request.contextPath}/licensedesign/modify?licenseId=${f:h(licenseDesign.licenseId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.permission.licensedesignModify.remark"/>"></a>
												</qp:authorization>
												<qp:authorization permission="licensedesignModify">
													<a href="${pageContext.request.contextPath}/licensedesign/generate?licenseId=${f:h(licenseDesign.licenseId)}" class="btn qp-button glyphicon glyphicon-download-alt qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.generation.0008" />"></a>
												</qp:authorization>
												<%-- <form:form method="get" action="${pageContext.request.contextPath}/licensedesign/generate" class="separate-form" style="float:left; height:10px"> 
													<input type="hidden" name="licenseId" value="${f:h(licenseDesign.licenseId)}" />
													<button type="submit" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0025"><qp:message code="sc.generation.0008" /></button>
												</form:form>  --%>
											</td>
										</tr>
									</c:forEach>
								</tbody>
					</table>
						<div class="qp-div-action">
												<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md"
															page="${page}"
															queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}"
															criteriaQuery="${f:query(licensedesignSearchForm)}"
															maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md"
															page="${page}"
															criteriaQuery="${f:query(licensedesignSearchForm)}"
															maxDisplayCount="5" />
									</c:otherwise>
								</c:choose>
							</div>
					</c:if>
					</div>
				</div>
			</div>
		</c:if>
		
	</tiles:putAttribute>
</tiles:insertDefinition>