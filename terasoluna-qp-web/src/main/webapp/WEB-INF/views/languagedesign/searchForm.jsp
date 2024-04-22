<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="sc.languagedesign.0009"></qp:message></span></li>
		 <li><span><qp:message code="sc.languagedesign.0020"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		
			<qp:authorization permission="languagedesignBingsetting">
				<span class="qp-link-header-icon glyphicon glyphicon-cog"></span>
				<a href="${pageContext.request.contextPath}/languagedesign/languagedesignBingsetting"><qp:message code="sc.accountprofile.0036"></qp:message> </a>
			</qp:authorization>
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="languagedesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-cog"></span>
				<a href="${pageContext.request.contextPath}/languagedesign/register"><qp:message code="sc.languagedesign.0027"></qp:message> </a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	 <tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/languagedesign/search" modelAttribute="languageDesignSearchForm">
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
							<th><form:label path="languageName"><qp:message code="sc.languagedesign.0002"/></form:label></th>
							<td><form:input path="languageName" cssClass="form-control qp-input-text" autofocus="true"/>
							<th><form:label path="languageCode"><qp:message code="sc.languagedesign.0001"/></form:label></th>
							<td><form:input path="languageCode" cssClass="form-control qp-input-text" />
						</tr>
					</table>
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="languagedesignSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="languageDesignSearchForm" action="/languagedesign/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col width="5%" />
								<col />
								<col width="35%" />
								<col width="10%" />
								<col width="5%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:columnSort colName="language_name" colCode="sc.languagedesign.0002" form="languageDesignSearchForm" /></th>
									<th><qp:columnSort colName="language_code" colCode="sc.languagedesign.0001" form="languageDesignSearchForm" /></th>
									<th><qp:message code="sc.subareadesign.0009"/></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${page.content }" varStatus="look">
									<tr>
										<td>${(page.number * page.size) + look.count}</td>
										<td>
											<qp:authorization permission="languagedesignView" isDisplay="true" displayValue="${f:h(item.languageName)}">
												<a href="${pageContext.request.contextPath}/languagedesign/view?languageId=${item.languageId}" class="qp-link-popup">
													<qp:formatText value="${item.languageName}" />
												</a>
											</qp:authorization>
										</td>
										<td><qp:formatText value="${item.languageCode}" /></td>
										<td><qp:booleanFormatTrueFalse value="${item.defaultFlg}"/></td>
										<td>
											<c:if test="${item.languageCode ne 'en' && sessionScope.CURRENT_PROJECT.status eq 1 }">
												<qp:authorization permission="languagedesignModify">
													<a href="${pageContext.request.contextPath}/languagedesign/modify?languageId=${item.languageId}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.languagedesign.0020"/>"></a>
												</qp:authorization>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<qp:authorization permission="languagedesignSearch">
							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(languageDesignSearchForm)}" maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(languageDesignSearchForm)}" maxDisplayCount="5" />
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