<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.domaindesign"></qp:message></span></li>
         <li><span><qp:message code="sc.domaindesign.0000"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="domaindesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/domaindesign/register"><qp:message code="sc.domaindesign.0030"/></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/domaindesign/search" modelAttribute="domainDesignSearchForm">
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"> <qp:message code="sc.sys.0002"/></span>
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
							<th><form:label path="domainName"><qp:message code="sc.domaindesign.0001"/></form:label></th>
							<td><form:input path="domainName" cssClass="form-control qp-input-text" autofocus="true"/>
							<th><form:label path="domainCode"><qp:message code="sc.domaindesign.0002"/></form:label></th>
							<td><form:input path="domainCode" cssClass="form-control qp-input-text" />
						</tr>
						<tr>
							<th><qp:message code="sc.domaindesign.0003"/></th>
							<td>
								<qp:autocomplete
									optionValue="optionValue" optionLabel="optionLabel"
									selectSqlId="getAllBasetypeAC" 
									emptyLabel="sc.sys.0030"
									name="domainBaseType" 
									displayValue="${domainDesignSearchForm.domainBaseTypeAutocomplete}" 
									value="${domainDesignSearchForm.domainBaseType}">
								</qp:autocomplete>
							</td>
							<th><%-- <qp:message code="sc.domaindesign.0026"/> --%></th>
							<td>
<%-- 								<qp:autocomplete
									optionValue="optionValue" optionLabel="optionLabel"
									selectSqlId="getAllProjectForAutocomplete" 
									emptyLabel="sc.sys.0030"
									name="projectId" 
									displayValue="${domainDesignSearchForm.projectIdAutocomplete}" 
									value="${domainDesignSearchForm.projectId}">
								</qp:autocomplete>
 --%>							</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="domaindesignSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="domainDesignSearchForm" action="/domaindesign/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col width="5%" />
								<col width="22%" />
								<col width="18%" />
								<col width="15%" />
								<col width="10%" />
								<!-- <col width="10%" /> -->
								<!-- <col width="15%" /> -->
								<col width="5%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:columnSort colName="domain_name" colCode="sc.domaindesign.0001" form="domainDesignSearchForm" /></th>
									<th><qp:columnSort colName="domain_code" colCode="sc.domaindesign.0002" form="domainDesignSearchForm" /></th>
									<th><qp:columnSort colName="base_type" colCode="sc.domaindesign.0003" form="domainDesignSearchForm" /></th>
									<th><qp:columnSort colName="default_value" colCode="sc.domaindesign.0004" form="domainDesignSearchForm" /></th>
									<%-- <th><qp:columnSort colName="fmt_code" colCode="sc.domaindesign.0005" form="domainDesignSearchForm" /></th> --%>
									<%-- <th><qp:columnSort colName="project_name" colCode="sc.domaindesign.0026" form="domainDesignSearchForm" /></th> --%>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${page.content }" varStatus="look">
									<tr>
										<td>${(page.number * page.size) + look.count}</td>
										<td>
											<qp:authorization permission="domaindesignView" isDisplay="true" displayValue="${f:h(item.domainName)}">
												<a href="${pageContext.request.contextPath}/domaindesign/view?domainId=${item.domainId}" class="qp-link-popup">
													<qp:formatText value="${item.domainName}" />
												</a>
											</qp:authorization>
										</td>
										<td><qp:formatText value="${item.domainCode}" /></td>
										<td>
											<qp:formatText value="${item.baseTypeAutocomplete}" />
										</td>
										<td>
											<c:choose>
												<c:when test="${item.groupBasetypeId == 4}">
											       <qp:formatDate value="${item.defaultValue}" />
											    </c:when>
											    <c:when test="${item.groupBasetypeId == 8}">
											       <qp:formatTime value="${item.defaultValue}" />
											    </c:when>
											    <c:when test="${item.groupBasetypeId == 9}">
											       <qp:formatDateTime value="${item.defaultValue}" />
											    </c:when>
											    <c:when test="${item.groupBasetypeId == 7}">
											       <c:choose>
												       	<c:when test="${item.defaultValue == null}">
												       		<qp:message code="sc.sys.0095"/>
												       	</c:when>
												       	<c:otherwise>
												       		<qp:message code="${CL_BOOLEAN_DEFAULT_VALUE.get(item.defaultValue.toString())}"/>
												       	</c:otherwise>
											       </c:choose>
											    </c:when>
											    <c:otherwise>
											        <qp:formatText value="${item.defaultValue}" />
											    </c:otherwise>
										    </c:choose>
										</td>
										<%-- <td>
											<c:if test="${item.mandatoryFlg eq 1}"><qp:message code="sc.sys.0011" /></c:if> 
											<c:if test="${item.mandatoryFlg ne 1}"><qp:message code="sc.sys.0012" /></c:if>
										</td> --%>
										<%-- <td><qp:formatText value="${item.projectIdAutocomplete}" /></td> --%>
										<td>
											<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
												<qp:authorization permission="domaindesignModify">
													<a href="${pageContext.request.contextPath}/domaindesign/modify?domainId=${item.domainId}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.domaindesign.0031"/>"></a>
												</qp:authorization>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<qp:authorization permission="domaindesignSearch">
							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(domainDesignSearchForm)}" maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(domainDesignSearchForm)}" maxDisplayCount="5" />
									</c:otherwise>
								</c:choose>
							</div>
						</qp:authorization>
					</c:if>
				</div>
			</div>
		</div>

	</tiles:putAttribute>
</tiles:insertDefinition>