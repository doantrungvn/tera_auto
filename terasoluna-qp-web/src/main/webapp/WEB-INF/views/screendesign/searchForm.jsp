<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.screendesign"></qp:message></span></li>
         <li><span><qp:message code="sc.screendesign.0019"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="screendesignTransition">
			<span class="qp-link-header-icon glyphicon glyphicon-link"></span>
			<a href="${pageContext.request.contextPath}/screendesign/transition?init"><qp:message code="tqp.screentransition" /></a>
		</qp:authorization>
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="screendesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a id="registerLink" href="${pageContext.request.contextPath}/screendesign/register"><qp:message code="sc.screendesign.0021"/></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute> 
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/search.js" ></script>
		<script type="text/javascript">
		function changeModule(event) {
			var value = $(event.target).next("input[type=hidden]").val();
			var nextInput = $(event.target).closest("tr").find("input[name$='functionDesignIdAutocomplete']");
			var nextHidden = nextInput.next("input[type=hidden]");
			nextInput.val("");
			nextInput.attr("arg01",value);
			nextHidden.val("");
			nextInput.data("ui-autocomplete")._trigger("change");	
		};
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/screendesign/search"
			method="POST" modelAttribute="screenDesignSearchForm">
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-search" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"/></span>
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
							<th><qp:message code="sc.screendesign.0018"/></th>
							<td>
								<qp:autocomplete 
										optionValue="optionValue" optionLabel="optionLabel"
										selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
										name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}"
										displayValue="${screenDesignSearchForm.moduleIdAutocomplete}" 
										onChangeEvent="changeModule"
										arg04="0"
										value="${screenDesignSearchForm.moduleId}" >
								</qp:autocomplete>
							</td>
							<th><qp:message code="sc.functiondesign.0002" /></th>
							<td>
								<qp:autocomplete optionValue="optionValue"
									selectSqlId="getAllFunctionDesignForAutocomplete"
									name="functionDesignId" value="${screenRegisterForm.functionDesignId }"
									displayValue="${screenDesignSearchForm.functionDesignIdAutocomplete }"
									arg01="${screenDesignSearchForm.moduleId}" 
									optionLabel="optionLabel"
									maxlength="200">
							</qp:autocomplete>
							</td>
						</tr>
						<tr>
							<th><form:label path="screenName"><qp:message code="sc.screendesign.0005"/></form:label></th>
							<td>
								<form:input type="text" path="screenName" class="form-control qp-input-text" />
							</td>
							<th><form:label path="screenCode"><qp:message code="sc.screendesign.0007"/></form:label></th>
							<td>
								<form:input type="text" path="screenCode" class="form-control qp-input-text" />
							</td>
						</tr>
						<tr>
							<th><form:label path="templateTypes"><qp:message code="sc.screendesign.0008"/></form:label></th>
							<td>
								<form:checkboxes cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_TEMPLATE_TYPE}" path="templateTypes" />
							</td>
							<th><form:label path="designMode"><qp:message code="sc.screendesign.0250"/></form:label></th>
							<td>
								<form:checkboxes class="qp-input-checkbox-margin qp-input-checkbox" items="${CL_SCREEN_DESIGN_MODE }" path="designMode"/>
							</td>
						</tr>
						<tr>
							<th><form:label path="screenParternTypes"><qp:message code="sc.screendesign.0009"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_SCREEN_PARTTERN_TYPES}" varStatus="rowStatus">
								 	<c:if test="${rowStatus.count == 3}">
								 		<br>
								 	</c:if>
									<label><form:checkbox path="screenParternTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/>&nbsp;<qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label><form:checkbox path="designStatus" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_DESIGN_STATUS.get(item.key)}" /></label>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="screendesignSearch">
					 <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001"/></button>
				</qp:authorization>
			</div>
		</form:form>

		<!-- List result -->
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<qp:itemPerPage form="screenDesignSearchForm" action="/screendesign/search"/>
			</div>
			<div class="panel-body">
				<div>
					<c:if test="${page != null && page.totalPages > 0 }">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col width="4%"/>
								<col width="20%" />
								<col width="20%" />
								<col width="15%" />
								<col width="12%" />
								<col width="12%" />
								<col width="10%" />
								<col width="4%" />
								<col width="4%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:columnSort colName="message_string" colCode="sc.screendesign.0005" form="screenDesignSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="screen_code" colCode="sc.screendesign.0007" form="screenDesignSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="screen_pattern_type" colCode="sc.screendesign.0009" form="screenDesignSearchForm"></qp:columnSort></th>
									<th><qp:columnSort colName="template_type" colCode="sc.screendesign.0008" form="screenDesignSearchForm"></qp:columnSort></th>
									<th><qp:message code="sc.screendesign.0250"/></th>
									<th><qp:message code="sc.sys.0055"/></th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<c:forEach var="screenList" items="${page.content}" varStatus="rowStatus">
								<tr>
									<td>${(page.number * page.size) + rowStatus.count}</td>
									<td class="word-wrap">
										<qp:authorization permission="screendesignView" isDisplay="true" displayValue="${screenList.messageDesign.messageString}">
											<a class="qp-link-popup" href="${pageContext.request.contextPath}/screendesign/view?screenId=${f:h(screenList.screenId)}&openOwner=1">
												<qp:formatText value="${screenList.messageDesign.messageString}"/>
											</a>
										</qp:authorization>
									</td>
									<td class="qp-output-text word-wrap"><qp:formatText value="${screenList.screenCode}"/></td>
									<td class="qp-output-text word-wrap"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(screenList.screenPatternType.toString())}"/></td>
									<td class="qp-output-text word-wrap"><qp:formatText value="${CL_TEMPLATE_TYPE.get(screenList.templateType.toString())}"/></td>
									<td><qp:formatText value="${CL_SCREEN_DESIGN_MODE.get(screenList.designMode.toString())}"/></td>
									<td>
										<qp:message code="${CL_DESIGN_STATUS.get(screenList.designStatus.toString())}"></qp:message>
									</td>
									<td align="center">
									<!-- KhanhTH -->
									<div class="dropdown">
										<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
										<ul class="dropdown-menu dropdown-menu-left">
												<li><a href="${pageContext.request.contextPath}/screendesign/modifysettingitemlist?screenId=${f:h(screenList.screenId)}" class="qp-link"><qp:message code="sc.screendesign.0474" /></a></li>
												<li><a href="${pageContext.request.contextPath}/screendesign/screenStatus?screenId=${f:h(screenList.screenId)}" class="qp-link"><qp:message code="sc.screendesign.0475" /></a></li>
										</ul>
									</div>
									<!-- KhanhTH -->
									</td>
									<td align="center">
										<c:if test="${screenList.designStatus eq 1}">
											<qp:authorization permission="screendesignDesign">
<%--											 <a href="${pageContext.request.contextPath}/screendesign/modifysettingitemlist?screenId=${f:h(screenList.screenId)}" class="btn qp-button glyphicon glyphicon-cog qp-link-button qp-link-action"></a> --%>
											<c:choose>
												<c:when test="${screenList.designMode == 1 }">
													<a href="${pageContext.request.contextPath}/screendesign/prototype?screenId=${f:h(screenList.screenId)}"
												class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.screendesign.0001"/>" ></a>
												</c:when>
												<c:when test="${screenList.designMode == 2 }">
													<a href="${pageContext.request.contextPath}/screendesign/design?screenId=${f:h(screenList.screenId)}"
												class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.screendesign.0001"/>" ></a>
												</c:when>
												<c:otherwise><a href="${pageContext.request.contextPath}/screendesign/design?screenId=${f:h(screenList.screenId)}"
												class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.screendesign.0001"/>" ></a></c:otherwise>
											</c:choose>
											</qp:authorization>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
						<qp:authorization permission="screendesignSearch">
							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" 
											queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" 
											criteriaQuery="${f:query(screenDesignSearchForm)}" maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md" page="${page}" 
											criteriaQuery="${f:query(screenDesignSearchForm)}" maxDisplayCount="5" />
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
