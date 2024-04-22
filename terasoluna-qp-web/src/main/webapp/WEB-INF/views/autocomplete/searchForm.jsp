<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.autocomplete"></qp:message></span></li>
         <li><span><qp:message code="sc.autocomplete.0063"/></span></li>
    </tiles:putAttribute>
		
		<tiles:putAttribute name="header-link">
			<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
				<qp:authorization permission="autocompleteRegister">
					<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
					<a href="${pageContext.request.contextPath}/autocomplete/register"><qp:message code="sc.autocomplete.0064"></qp:message></a>
				</qp:authorization>
				<qp:authorization permission="autocompleteRegister">
					<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
					<a href="${pageContext.request.contextPath}/autocomplete/registerAdvanced"><qp:message code="sc.autocomplete.0067"></qp:message></a>
				</qp:authorization>
			</c:if>
		</tiles:putAttribute>
		<tiles:putAttribute name="body">	
			<script>
				function changeProjectAC(obj){
					var value = $(obj.target).next("input[type=hidden]").val();
					var moduleInput = $(obj.target).closest("table").find("input[id='moduleIdAutocompleteId']");
					var moduleHidden = moduleInput.next("input[type=hidden]");
					moduleInput.val("");
					moduleInput.attr("arg01",value);
					moduleHidden.val("");
					moduleInput.data("ui-autocomplete")._trigger("change");
					
					var tableInput = $(obj.target).closest("table").find("input[id='tableIdAutocompleteId']");
					var tableHidden = tableInput.next("input[type=hidden]");
					tableInput.val("");
					tableInput.attr("arg02",value);
					tableHidden.val("");
					tableInput.data("ui-autocomplete")._trigger("change");
				};	
				$(function(){
					$("#autocompleteForm").find("input[name='moduleIdAutocomplete']").attr("arg01",$("#autocompleteForm").find("input[name='projectId']").val());
					$("#autocompleteForm").find("input[name='tableIdAutocomplete']").attr("arg02",$("#autocompleteForm").find("input[name='projectId']").val());
				});
			</script>
			<form:form method="post" 
						role="form"
						action="${pageContext.request.contextPath}/autocomplete/search"
						modelAttribute="autocompleteSearchForm">
			<qp:ColumnSortHidden></qp:ColumnSortHidden>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="autocompleteForm">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><qp:message code="sc.autocomplete.0005"></qp:message></th>
							<td>
								<form:input type="text" path="autocompleteName" class="form-control qp-input-text" maxlength="200"/>
							</td>
							<th><qp:message code="sc.autocomplete.0006"></qp:message></th>
							<td>
								<form:input type="text" path="autocompleteCode" class="form-control qp-input-text" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.autocomplete.0009"></qp:message></th>
							<td>
								<c:forEach var="item" items="${CL_MATCHING_TYPE}">
									<label>
										<form:checkbox cssClass="qp-input-checkbox-margin qp-input-checkbox" path="matchingTypes" value="${item.key}" />
										<qp:message code="${CL_MATCHING_TYPE.get(item.key)}" />
									</label>
								</c:forEach>
							</td>	
							<th><qp:message code="sc.autocomplete.0007"></qp:message></th>
							<td>
								<qp:autocomplete optionValue="optionValue" 
										selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
										name="moduleId"
										value="${autocompleteSearchForm.moduleId }"
										displayValue="${autocompleteSearchForm.moduleIdAutocomplete }"
										optionLabel="optionLabel"
										emptyLabel="Enter module here" 
										mustMatch="true"
										arg01="${sessionScope.CURRENT_PROJECT.projectId }"></qp:autocomplete>
							</td>
						</tr>
						<tr> 
							<th><qp:message code="sc.autocomplete.0016" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue"
									selectSqlId="getAllTableDesignByProjectId" 
									value="${autocompleteSearchForm.tableId }"
									displayValue="${autocompleteSearchForm.tableIdAutocomplete }" 
									name="tableId" 
									optionLabel="optionLabel"
									arg02="${sessionScope.CURRENT_PROJECT.projectId }"></qp:autocomplete>
							</td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label>
										<form:checkbox path="designStatus" value="${item.key}" />&nbsp;<qp:message code="${CL_DESIGN_STATUS.get(item.key)}" />
									</label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.autocomplete.0071"></qp:message></th>
							<td>
								<c:forEach var="item" items="${CL_SQL_TYPE_AUTOCOMPLETE}">
									<label>
										<form:checkbox cssClass="qp-input-checkbox-margin qp-input-checkbox" path="autocompleteTypes" value="${item.key}" />
										<qp:message code="${CL_SQL_TYPE_AUTOCOMPLETE.get(item.key)}" />
									</label>
								</c:forEach>
							</td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			
			<!-- Style for button submit -->
			
			<div class="qp-div-action">
				<qp:authorization permission="autocompleteSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001"></qp:message></button>
				</qp:authorization>
			</div>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<qp:itemPerPage form="autocompleteSearchForm" action="/autocomplete/search"></qp:itemPerPage>
					</div>
					<div class="panel-body">
						<c:if test="${page != null && page.totalPages gt 0 }">
							<div class="table-responsive">
								<table class="table table-bordered qp-table-list">
									<colgroup>
										<col>
										<col>
										<col width="15%">
										<col width="15%">
										<col width="15%">
										<col width="15%">
										<col width="10%">
										<col />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:columnSort colName="autocomplete_name" form="autocompleteSearchForm" colCode="sc.autocomplete.0005"></qp:columnSort></th>
											<th><qp:columnSort colName="autocomplete_code" form="autocompleteSearchForm" colCode="sc.autocomplete.0006"></qp:columnSort></th>
											<th><qp:columnSort colName="module_name" form="autocompleteSearchForm" colCode="sc.autocomplete.0007"></qp:columnSort></th>
											<th><qp:columnSort colName="design_status" form="autocompleteSearchForm" colCode="sc.sys.0055"></qp:columnSort></th>
											<th><qp:columnSort colName="design_type" form="autocompleteSearchForm" colCode="sc.autocomplete.0071"></qp:columnSort></th>
											<th><qp:columnSort colName="matching_type" form="autocompleteSearchForm" colCode="sc.autocomplete.0009"></qp:columnSort></th>
											<th>&nbsp;</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="autocomplete" items="${page.content}" varStatus="sts">
											<tr>
												<td>${page.number * page.size + sts.index + 1}</td>
												<td>
													<qp:authorization permission="autocompleteView" isDisplay="true" displayValue="${f:h(autocomplete.autocompleteName) }">
														<a href="${pageContext.request.contextPath}/autocomplete/view?autocompleteForm.autocompleteId=${autocomplete.autocompleteId}&openOwner=1" class="qp-link-popup">
															<qp:formatText value="${autocomplete.autocompleteName}" />
														</a>
													</qp:authorization>
												</td>
												<td><qp:formatText value="${autocomplete.autocompleteCode}"></qp:formatText></td>
												<td><qp:formatText value="${autocomplete.moduleName}"></qp:formatText></td>
												<td><qp:message code="${CL_DESIGN_STATUS.get(autocomplete.designStatus.toString())}"></qp:message></td>
												<td><qp:formatText value="${CL_SQL_TYPE_AUTOCOMPLETE.get(autocomplete.sqlDesign.designType.toString()) }"></qp:formatText></td>
												<td><qp:message code="${CL_MATCHING_TYPE.get(autocomplete.matchingType.toString())}"></qp:message></td>
												<td>
													<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && autocomplete.designStatus eq '1' && (empty autocomplete.moduleId || autocomplete.moduleStatus eq '1')}">
														<qp:authorization permission="autocompleteModify">
															<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/autocomplete/modify?autocompleteForm.autocompleteId=${autocomplete.autocompleteId}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.autocomplete.0065"/>"></a>
														</qp:authorization>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="qp-div-action">
									<qp:authorization permission="autocompleteSearch">
										<c:choose>
											<c:when test="${page.sort != null }">
												<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(autocompleteSearchForm)}" maxDisplayCount="5" />
											</c:when>
											<c:otherwise>
												<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(autocompleteSearchForm)}" maxDisplayCount="5" />
											</c:otherwise>
										</c:choose>
									</qp:authorization>
								</div>
							</div>
						</c:if>	
					</div>
				</div>	
			</form:form>
		</tiles:putAttribute>
</tiles:insertDefinition>