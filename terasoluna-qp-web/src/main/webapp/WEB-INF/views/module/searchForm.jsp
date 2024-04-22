	<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.module"></qp:message></span></li>
		<li><span><qp:message code="sc.module.0019"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="moduleRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/module/register"><qp:message code="sc.module.0021" /></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="moduleSearchForm" role="form" action="${pageContext.request.contextPath}/module/search">
			<qp:ColumnSortHidden/>
			<!-- Style for block search -->
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-search" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="25%" />
							<col width="20%" />
							<col width="35%" />
						</colgroup>
						<tr>
							<th><form:label path="moduleName"><qp:message code="sc.module.0007" /></form:label></th>
							<td><form:input path="moduleName" cssClass="form-control qp-input-text" maxlength="200" /></td>
							<th><form:label path="moduleCode"><qp:message code="sc.module.0008" /></form:label></th>
							<td><form:input path="moduleCode" cssClass="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>							
							<th><form:label path="businessTypeId"><qp:message code="sc.businesstype.0001" /></form:label></th>
							<td><jsp:include page="../businesstype/businessTypeTree.jsp" /></td>
							<th><form:label path="statuses"><qp:message code="sc.sys.0055" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label><form:checkbox path="statuses" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_DESIGN_STATUS.get(item.key)}" /></label>
								</c:forEach>
							</td>
						</tr>
						<%-- <tr>
							<th><form:label path="completionTypes"><qp:message code="sc.module.0010" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_MODULE_COMPLETE_TYPE}">
									<label><form:checkbox path="completionTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_MODULE_COMPLETE_TYPE.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th><form:label path="completionTypes"><qp:message code="sc.module.0010" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_MODULE_COMPLETE_TYPE}">
									<label><form:checkbox path="completionTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_MODULE_COMPLETE_TYPE.get(item.key)}" /></label>
								</c:forEach>
							</td>
						</tr> --%>
						<tr>
							<th><qp:message code="sc.sys.0078" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllAccountForAutocomplete" 
									value="${moduleSearchForm.createdBy}" name="createdBy" displayValue="${moduleSearchForm.createdByAutocomplete}" 
									arg02="20" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th><form:label path="fromCreatedDate"><qp:message code="sc.sys.0079"/></form:label></th>
							<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromCreatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toCreatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0080" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllAccountForAutocomplete" 
									value="${moduleSearchForm.updatedBy}" name="updatedBy" displayValue="${moduleSearchForm.updatedByAutocomplete}" 
									arg02="20" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th><form:label path="fromUpdatedDate"><qp:message code="sc.sys.0081"/></form:label></th>
							<td>
								<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="fromUpdatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="toUpdatedDate" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
						</tr>
						<tr>
							<th><form:label path="moduleTypes"><qp:message code="sc.module.0034"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_FUNCTION_DESIGN_TYPE}">
									<label><form:checkbox path="moduleTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(item.key)}" /></label>
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
				<qp:authorization permission="moduleSearch">
				 	<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
			
			<!-- Style for header weapper table -->
			<c:if test="${page!=null}">
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<qp:itemPerPage form="moduleSearchForm" action="/module/search" />
					</div>
					<div class="panel-body">
						<!-- <div class="table-responsive"> -->
							<c:if test="${page != null && page.totalPages > 0 }">
								<table class="table table-bordered qp-table-list">
									<colgroup>
										<col />
										<col width="20%"/>
										<col width="20%"/>
										<col width="18%"/>
										<col width="12%"/>
										<col width="10%"/>
										<col width="10%"/>
										<col width="10%"/>
										<col width="4%"/>
										<col width="4%"/>
									</colgroup>
									<thead>
										<tr>
											<!-- Style for item No. -->
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:columnSort colName="module_name" colCode="sc.module.0007" form="moduleSearchForm" /></th>
											<th><qp:columnSort colName="module_code" colCode="sc.module.0008" form="moduleSearchForm" /></th>
											<th><qp:columnSort colName="business_type_name" colCode="sc.businesstype.0001" form="moduleSearchForm" /></th>
											<th><qp:columnSort colName="status" colCode="sc.sys.0055" form="moduleSearchForm" /></th>
											<th><qp:columnSort colName="module_type" colCode="sc.module.0034" form="moduleSearchForm" /></th>
											<th><qp:columnSort colName="updated_by" colCode="sc.sys.0080" form="moduleSearchForm" /></th>
											<th><qp:columnSort colName="updated_date" colCode="sc.sys.0081" form="moduleSearchForm" /></th>
											<th colspan="2"><qp:message code="sc.module.0012" /></th>
										</tr>
									</thead>
									<c:forEach var="item" items="${page.content}" varStatus="status">
										<tr>
											<td><qp:formatInteger value="${(page.number * page.size) + status.count}" /></td>
											<td>
												<qp:authorization permission="moduleView" isDisplay="true" displayValue="${item.moduleName}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/module/view?moduleId=${item.moduleId}&openOwner=1"><qp:formatText value="${item.moduleName}" /></a>
												</qp:authorization>	
											</td>
											<td><qp:formatText value="${item.moduleCode}" /></td>
											<td><qp:formatText value="${item.businessTypeName}" /></td>
											<td><qp:message code="${CL_DESIGN_STATUS.get(item.status.toString())}"/></td>
											<td><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(item.moduleType.toString())}"/></td>
											<td><qp:formatText value="${item.updatedByName}" /></td>
											<td align="center"><qp:formatDateTime value="${item.updatedDate}" /></td>
											<td style="text-align: center;">
												<div class="dropdown">
													<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
													<ul class="dropdown-menu dropdown-menu-left">
														<c:if test = "${item.status eq 1 }">
															<c:if test= "${item.moduleType eq 0}">
																<qp:authorization permission="screendesignTransition">
																	<li><a href="${pageContext.request.contextPath}/screendesign/transition?init&moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}&mode=9" class="qp-link"><qp:message code="tqp.screentransition" /></a></li>
																</qp:authorization>
																<qp:authorization permission="screendesignSearch">
																	<li><a href="${pageContext.request.contextPath}/screendesign/search?init&moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}" class="qp-link"><qp:message code="tqp.screenlist" /></a></li>
																</qp:authorization>
																<qp:authorization permission="screendesignRegister">
																	<li><a href="${pageContext.request.contextPath}/screendesign/register?moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}" class="qp-link"><qp:message code="sc.screendesign.0021"></qp:message></a></li>
																</qp:authorization>
																<li class="divider"></li>
																<qp:authorization permission="businesslogicSearch">
																	<li><a href="${pageContext.request.contextPath}/businessdesign/search?init&moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}" class="qp-link"><qp:message code="sc.module.0025" /></a></li>
																</qp:authorization>
																<qp:authorization permission="businesslogicRegister">
																	<li><a href="${pageContext.request.contextPath}/businessdesign/registerWeb?moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}&moduleType=${f:u(item.moduleType)}" class="qp-link"><qp:message code="sc.businesslogicdesign.0016" /></a></li>
																</qp:authorization>
																<qp:authorization permission="generationGeneratedbblogic">
																	<li class="divider"></li>
																	<li><a href="${pageContext.request.contextPath}/generation/generatedbblogic?init&moduleId=${item.moduleId}"><qp:message code="sc.module.0033" /></a></li>
																</qp:authorization>
																<qp:authorization permission="generationGeneratescreen">
																	<li class="divider"></li>
																	<li><a href="${pageContext.request.contextPath}/generation/generatescreen?init&moduleId=${item.moduleId}"><qp:message code="sc.module.0035" /></a></li>
																</qp:authorization>
															</c:if>
															<c:if test= "${item.moduleType eq 1}">
																<qp:authorization permission="businesslogicSearch">
																	<li><a href="${pageContext.request.contextPath}/businessdesign/search?init&moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}" class="qp-link"><qp:message code="sc.module.0025" /></a></li>
																</qp:authorization>
																<qp:authorization permission="businesslogicRegister">
																	<li><a href="${pageContext.request.contextPath}/businessdesign/registerBatch?moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}&moduleType=${f:u(item.moduleType)}" class="qp-link"><qp:message code="sc.businesslogicdesign.0270" /></a></li>
																</qp:authorization>
															</c:if>
															
															<%-- <qp:authorization permission="generationGeneratescreen">
																<li class="divider"></li>
																<li><a href="${pageContext.request.contextPath}/generation/dependencymodule?moduleId=${item.moduleId}">Module Dependency</a></li>
															</qp:authorization> --%>
														</c:if>

														<c:if test="${item.status eq 2 }">
															<qp:authorization permission="screendesignSearch">
																<li><a href="${pageContext.request.contextPath}/screendesign/search?moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}" class="qp-link"><qp:message code="sc.module.0024" /></a></li>
															</qp:authorization>
															<li class="divider"></li>
															<qp:authorization permission="businesslogicSearch">
																<li><a href="${pageContext.request.contextPath}/businessdesign/search?moduleId=${f:h(item.moduleId)}&moduleIdAutocomplete=${f:u(item.moduleName)}" class="qp-link"><qp:message code="sc.module.0025" /></a></li>
															</qp:authorization>
														</c:if>
													</ul>
												</div>
											</td>
											<td>
												<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && item.status eq 1}">
													<qp:authorization permission="moduleModify">
														<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/module/modify?moduleId=${item.moduleId}&mode=0" style="margin: auto" data-toggle="tooltip" title="<qp:message code="sc.module.0022"/>"></a>
													</qp:authorization>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</table> 
								<qp:authorization permission="moduleSearch">
									<div class="qp-div-action">
										<c:choose>
											<c:when test="${page.sort != null}">
												<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(moduleSearchForm)}" maxDisplayCount="5" />
											</c:when>
											<c:otherwise>
												<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(moduleSearchForm)}" maxDisplayCount="5" />
											</c:otherwise>
										</c:choose>
									</div>
								</qp:authorization>
							</c:if>	
						</div>
					<!-- </div> -->
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
