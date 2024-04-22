<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">	
		Search problem
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/domaindatatype/search" modelAttribute="domainDatatypeSearchForm">
		<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="15%" />
							<col width="30%" />
							<col width="15%" />
							<col width="40%" />
						</colgroup>
						<tr>
							<th><label for="domainTableName">Problem name</label></th>
							<td><form:input path="domainTableName" cssClass="form-control qp-input-text" /></td>
							<th><label for="domainTableCode">Resource type</label></th>
							<td>
								<input type="checkbox"> Blogic
								<input type="checkbox"> Screen design
								<input type="checkbox"> SQL design
								<input type="checkbox"> Other
							</td>
						</tr>
						<tr>
							<th>Module</th>
							<td><qp:autocomplete name="moduleId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" value="0" displayValue="" onChangeEvent="changeModuleAC" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" mustMatch="true" maxlength="200" /></td>
							<th><label for="status">Problem type</label></th>
							<td>
								<input type="checkbox"> Unmatched
								<input type="checkbox"> Change database
								<input type="checkbox"> Miss setting
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="domaindatatypeSearch">
					<button type="submit" class="btn qp-button qp-button-search"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="domainDatatypeSearchForm" action="/domaindatatype/search"></qp:itemPerPage>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-bordered qp-table-list">
						<colgroup>
							<col width="5%" />
							<col  />
							<col width="25%" />
							<col width="20%" />
							<col width="12%" />
							<col width="7%" />
						</colgroup>
						<c:if test="${page != null && page.totalPages > 0 }">
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"/></th>
								<th>Problem name</th>
								<th>Resource type</th>
								<th>Resource name</th>
								<th>Problem type</th>
								<th>Autofix</th>
							</tr>
						</thead>
						<tbody>
							<%-- <c:forEach var="item" items="${page.content}" varStatus="look">
								<tr>
									<td>${(page.number * page.size) + look.count}</td>
									<td>
										<qp:authorization permission="domaindatatypeView" isDisplay="true" displayValue="${f:h(item.domainDatatypeName)}">
											<a href="${pageContext.request.contextPath}/domaindatatype/view?id=${item.domainDatatypeId }" class="qp-link-popup">
												<qp:formatText value="${item.domainDatatypeName}"/>
											</a>
										</qp:authorization>
									</td>
									<td><qp:formatText value="${item.domainDatatypeCode}"/></td>
									<td><qp:formatText value="${item.tableDesignName}"/></td>
									
									<td>
										<qp:authorization permission="domaindatatypeModify">
											<a href="${pageContext.request.contextPath}/domaindatatype/modify?domainDatatypeId=${item.domainDatatypeId}" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action"></a>
										</qp:authorization>
									</td>
								</tr>
								</c:forEach> --%>

								<!-- <tr>
									<td>1</td>
									<td>
										Column order_date was changed datatype
									</td>
									<td>SQL design</td>
									<td>
										<a href="/terasoluna-qp-web/domaindatatype/view?id=108" class="qp-link-popup">order_date </a>
									</td>
									<td>Change database</td>
									
									<td>
										<a href="/terasoluna-qp-web/domaindatatype/modify?domainDatatypeId=108" class="btn qp-button glyphicon glyphicon-wrench qp-link-button qp-link-action"></a>
									</td>
								</tr>
								<tr>
									<td>2</td>
									<td>
										Edit screen order unmatched
									</td>
									<td>Blogic</td>
									<td>
										<a href="/terasoluna-qp-web/businessdesign/modify?businessLogicId=11&mode=0" target="_blank">Init edit screen order</a>
									</td>
									<td>Unmatched</td>
									<td>
										<a href="/terasoluna-qp-web/domaindatatype/modify?domainDatatypeId=109" class="btn qp-button glyphicon glyphicon-wrench qp-link-button qp-link-action"></a>
									</td>
								</tr> -->
								<tr>
									<td>1</td>
									<td> Execution node using column "order_name", but it was deleted from SQL design</td>
									<td>Blogic</td>
									<td>
										<a href="/terasoluna-qp-web/screendesign/design?screenId=813">business mode</a>
									</td>
									<td>Unmatched</td>
									<td>
									</td>
								</tr>
							</tbody>
						</c:if>
					</table>
					<qp:authorization permission="domaindatatypeSearch">
						<div class="qp-div-action">
							<c:choose>
								<c:when test="${page.sort != null }">
									<t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(domainDatatypeSearchForm)}" maxDisplayCount="5" />
								</c:when>
								<c:otherwise>
									<t:pagination outerElementClass="pagination pagination-md" page="${page}"  criteriaQuery="${f:query(domainDatatypeSearchForm)}" maxDisplayCount="5" />
								</c:otherwise>
							</c:choose>
						</div>
					</qp:authorization>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
