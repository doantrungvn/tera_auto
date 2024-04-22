<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.designinformation"></qp:message></span></li>
         <li><span><qp:message code="sc.designinformation.0001"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="designinformationRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/designinformation/register"><qp:message code="sc.designinformation.0002"/></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/designinformation/search" modelAttribute="designInformationSearchForm">
		<qp:ColumnSortHidden />
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="27%" />
							<col width="20%" />
							<col width="33%" />
						</colgroup>
						<tr>
							<th><qp:message code="sc.module.0007"/></th>
							<td>
                                <form:select path="moduleCode" cssClass="form-control qp-input-select">
                                    <form:option value=""><qp:message code="sc.sys.0030" /></form:option>
                                    <c:forEach var="resource" items="${moduleResources}">
                                        <form:option value="${resource.messageCode}"><qp:message code="${resource.messageCode}" /></form:option>
                                    </c:forEach>
                                </form:select>
							</td>
							<th><form:label path="title"><qp:message code="sc.designinformation.0005"/></form:label></th>
							<td><form:input path="title" class="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="remark"><qp:message code="sc.sys.0028"/></form:label></th>
							<td><form:input path="remark" class="form-control qp-input-text" maxlength="50" /></td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th><form:label path="updatedBy"><qp:message code="sc.designinformation.0006"/></form:label></th>
							<td><qp:autocomplete name="updatedBy" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllAccountForAutocomplete" value="${designInformationSearchForm.updatedBy}" displayValue="${designInformationSearchForm.updatedByAutocomplete}" mustMatch="true" maxlength="200" /></td>
							<th><form:label path="updatedDate"><qp:message code="sc.designinformation.0007"/></form:label></th>
							<td><div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="dateFrom" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="dateTo" cssClass="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
                <qp:authorization permission="designinformationSearch">
				    <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		<c:if test="${page!=null}">
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
                <qp:itemPerPage form="designInformationSearchForm" action="/project/search" />
            </div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col />
								<col width="25%" />
								<col width="25%" />
								<col width="25%" />
								<col width="25%"/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:columnSort colName="design_name" colCode="sc.designinformation.0013" form="designInformationSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="module_code" colCode="sc.module.0007" form="designInformationSearchForm"></qp:columnSort></th>								
									<th><qp:columnSort colName="updated_by" colCode="sc.designinformation.0006" form="designInformationSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="updated_date" colCode="sc.designinformation.0007" form="designInformationSearchForm"></qp:columnSort></th>
									<th></th>
								</tr>
							</thead>
							<c:forEach var="designInformation" items="${page.content}" varStatus="rowStatus">
								<tr>
									<td><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
									<td class="word-wrap">
										<qp:authorization permission="designinformationView" isDisplay="true" displayValue="${f:h(designInformation.designName)}">
											<a class="qp-link-popup" href="${pageContext.request.contextPath}//designinformation/view?designInformationId=${f:h(designInformation.designInformationId)}&openOwner=1">
												<qp:formatText value="${designInformation.designName}"/>
											</a>
										</qp:authorization>
									</td>
									<td class="word-wrap"><qp:message code="${f:h(designInformation.moduleCode)}"/></td>									
									<td class="word-wrap"><qp:formatText value="${designInformation.userName}"/></td>
									<td class="word-wrap"><qp:formatDateTime value="${designInformation.updatedDate}"/></td>
									<td align="center">
										<qp:authorization permission="designinformationModify">
											<a href="${pageContext.request.contextPath}/designinformation/modify?designInformationId=${f:h(designInformation.designInformationId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.designinformation.0003"/>" ></a>
										</qp:authorization>
									</td>
								</tr>
							</c:forEach>
						</table>
						<qp:authorization permission="designinformationSearch">
							<div class="qp-div-action">
	                            <c:choose>
	                                <c:when test="${page.sort != null }">
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(projectSearchForm)}" maxDisplayCount="5" />
	                                </c:when>
	                                <c:otherwise>
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(projectSearchForm)}" maxDisplayCount="5" />
	                                </c:otherwise>
	                            </c:choose>
	                        </div>
                        </qp:authorization>
                        
					</c:if>
				</div>
			</div>
		</div>
		</c:if>
		<div></div>
	</tiles:putAttribute>
</tiles:insertDefinition>