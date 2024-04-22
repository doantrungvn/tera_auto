<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.codelist"></qp:message></span></li>
         <li><span><qp:message code="sc.codelist.0001"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<c:if test="${1 eq sessionScope.CURRENT_PROJECT.status}">
			<qp:authorization permission="codelistRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/codelist/register"><qp:message code="sc.codelist.0016" /></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<script>
				function changeProjectAC(obj){
					var value = $(obj.target).next("input[type=hidden]").val();
					var nextInput = $(obj.target).closest("tr").find("input[id='moduleIdAutocompleteId']");
					var nextHidden = nextInput.next("input[type=hidden]");
					nextInput.val("");
					nextInput.attr("arg01",value);
					nextHidden.val("");
					nextInput.data("ui-autocomplete")._trigger("change");
				};	

		</script>
		<form:form method="post" action="${pageContext.request.contextPath}/codelist/search" modelAttribute="codeListSearchForm" role="form">
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading"> <span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"> <qp:message code="sc.sys.0002"></qp:message></span> </div>
				
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path="codeListName"><qp:message code="sc.codelist.0003" /></form:label></th>
							<td><form:input path="codeListName" cssClass="form-control qp-input-text" /></td>
							<th><form:label path="codeListCode"><qp:message code="sc.codelist.0002" /></form:label></th>
							<td><form:input path="codeListCode" cssClass="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.module.0007" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" 
									value="${codeListSearchForm.moduleId}" name="moduleId" displayValue="${codeListSearchForm.moduleIdAutocomplete}" 
									arg01="${codeListSearchForm.projectId}" mustMatch="true">
								</qp:autocomplete>
							</td>
								<th><qp:message code="sc.tabledesign.0053"/></th>
							<td>
								<c:forEach var="item" items="${CL_SUPPORT_OPTION_VALUE_FLAG}">
									<label><form:checkbox path="OptionValude" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_SUPPORT_OPTION_VALUE_FLAG.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="qp-div-action">
				<qp:authorization permission="codelistSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>

			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="codeListSearchForm" action="/codelist/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col width="4%" />
									<col width="20%" />
									<col width="20%" />
									<col width="20%" />
									<col width="20%" />
									<col width="4%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="codelist_name" colCode="sc.codelist.0003" form="codeListSearchForm" /></th>
										<th><qp:columnSort colName="codelist_code" colCode="sc.codelist.0002" form="codeListSearchForm" /></th>
										<th><qp:columnSort colName="option_value_flg" colCode="sc.tabledesign.0053" form="codeListSearchForm" /></th>
										<th><qp:columnSort colName="module_name" colCode="sc.module.0007" form="codeListSearchForm" /></th>
										<th></th>
									</tr>
								</thead>
								
								<c:forEach var="codelist" items="${page.content}" varStatus="look">
									<tr>
										<td>${(page.number * page.size) + look.count}</td>
										<td > 
											<qp:authorization permission="codelistView" isDisplay="true" displayValue="${f:h(codelist.codeListName)}">
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/codelist/view?codeListId=${codelist.codeListId}&openOwner=1" >
													<qp:formatText value="${codelist.codeListName}" />
												</a>
											</qp:authorization>
										</td>
										<td class="com-output-text"> <qp:formatText value="${codelist.codeListCode}" /></td>
										<td class="com-output-text">
											<c:if test="${codelist.isOptionValude==1}"><qp:message code="sc.sys.0011" /></c:if> 
											<c:if test="${codelist.isOptionValude ne 1}"><qp:message code="sc.sys.0012" /></c:if>
										</td>
										<td class="com-output-text"><qp:formatText value="${codelist.moduleIdAutocomplete}" /></td>
										<td class="com-table-detail-action">
											<c:if test="${1 eq sessionScope.CURRENT_PROJECT.status && 1 eq codelist.moduleStatus}">
												<qp:authorization permission="codelistModify">
													<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/codelist/modify?codeListId=${codelist.codeListId}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.codelist.0017"/>"></a>
												</qp:authorization>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</table>

							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(codeListSearchForm)}" maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(codeListSearchForm)}" maxDisplayCount="5" />
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
					</div>
				</div>
			</div>
	</form:form>
	</tiles:putAttribute>
	
</tiles:insertDefinition>