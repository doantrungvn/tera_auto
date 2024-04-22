<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.functiondesign"></qp:message></span></li>
         <li><span><qp:message code="sc.functiondesign.0008"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="functiondesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/functiondesign/register">
					<qp:message code="sc.functiondesign.0007"/>
				</a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/functiondesign/search" modelAttribute="functionDesignSearchForm">
			<qp:ColumnSortHidden/>
			<form:hidden path="actor"/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message></span>
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
							<th><qp:message code="sc.functiondesign.0002"/></th>
							<td><form:input path="functionName" cssClass="form-control qp-input-text" autofocus="true"/></td>
							<th><qp:message code="sc.functiondesign.0003"/></th>
							<td><form:input path="functionCode" cssClass="form-control qp-input-text"/></td>
						</tr>
						<tr>
							<th><form:label path="moduleId"><qp:message code="sc.module.0007"/></form:label></th>
							<td>
                            	<qp:autocomplete 
                    					name="moduleId" 
                    					optionValue="optionValue" 
                    					optionLabel="optionLabel" 
                    					selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" 
                    					value="${functionDesignSearchForm.moduleId}" 
                    					displayValue="${functionDesignSearchForm.moduleIdAutocomplete}" 
                    					arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
                    					mustMatch="true" 
                    					maxlength="200">
                            	</qp:autocomplete>
                            </td>
							<th><qp:message code="sc.functionmaster.0007"/></th>
							<td>
					          	<c:forEach var="item" items="${CL_FUNCTION_DESIGN_TYPE}">
									<label><form:checkbox path="functionType" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
			</div>
		</form:form>
		<c:if test="${page!=null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
	                <qp:itemPerPage form="functionDesignSearchForm" action="/functiondesign/search" />
	            </div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col />
									<col width="30%" />
									<col width="30%" />
									<col width="25%"/>
									<col width="15%" />
									<col />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="function_name" colCode="sc.functionmaster.0005" form="functionDesignSearchForm" /></th>
										<th><qp:columnSort colName="function_code" colCode="sc.functionmaster.0006" form="functionDesignSearchForm" /></th>
										<th><qp:columnSort colName="module_id" colCode="sc.message.0004" form="functionDesignSearchForm" /></th>
										<th><qp:columnSort colName="function_type" colCode="sc.functionmaster.0007" form="functionDesignSearchForm" /></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="functionDesign" items="${page.content }" varStatus="rowStatus">
										<tr class="form-inline">
											<td><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
											<td class="word-wrap">
												<qp:authorization permission="functiondesignView" isDisplay="true" displayValue="${functionDesign.functionName}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/functiondesign/view?functionId=${f:h(functionDesign.functionId)}&status=1"><qp:formatText value="${functionDesign.functionName}" /></a>
												</qp:authorization>
											</td>
											<td class="word-wrap"><qp:formatText value="${functionDesign.functionCode}"/></td>
											<td class="word-wrap"><qp:formatText value="${functionDesign.moduleIdAutocomplete}"/></td>
											<td><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(functionDesign.functionType.toString())}"/></td>
											<td class="word-wrap">
												<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
													<qp:authorization permission="functiondesignModify">
														<%-- <c:if test="${functionDesign.functionMasterType != null && functionDesign.functionMasterType == 1 }"> --%>
															<a href="${pageContext.request.contextPath}/functiondesign/modify?functionId=${f:h(functionDesign.functionId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.functiondesign.0010"/>"></a>
														<%-- </c:if> --%>
													</qp:authorization>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="qp-div-action">
	                            <c:choose>
	                                <c:when test="${page.sort != null }">
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(functionDesignSearchForm)}" maxDisplayCount="5" />
	                                </c:when>
	                                <c:otherwise>
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(functionDesignSearchForm)}" maxDisplayCount="5" />
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