<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.businesslogicdesign"></qp:message></span></li>
         <li><span><qp:message code="sc.businesslogicdesign.0058"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="businesslogicRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/businessdesign/registerWS"><qp:message code="sc.businesslogicdesign.0226" /></a>
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/businessdesign/registerWeb"><qp:message code="sc.businesslogicdesign.0016" /></a>
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/businessdesign/registerCommon"><qp:message code="sc.businesslogicdesign.0015" /></a>
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/businessdesign/registerBatch"><qp:message code="sc.businesslogicdesign.0270" /></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
        <script src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/search.js" type="text/javascript" ></script>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/businessdesign/search" modelAttribute="businessDesignSearchForm" method="post">
			<!-- Style for block search -->
			<div class="panel panel-default  qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-search" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
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
							<th><form:label path="businessLogicName"><qp:message code="sc.businesslogicdesign.0005" /></form:label></th>
							<td><form:input path="businessLogicName" class="form-control qp-input-text" maxlength="200" /></td>
							<th><form:label path="businessLogicCode"><qp:message code="sc.businesslogicdesign.0006" /></form:label></th>
							<td><form:input path="businessLogicCode" class="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
                            <th><qp:message code="sc.businesslogicdesign.0013" /></th>
                            <td colspan="3">
                            	<c:forEach var="item" items="${CL_BLOGIC_TYPE}">
									<label><form:checkbox path="blogicType" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><qp:message code="${item.value}" /></label>
								</c:forEach>
                            </td>
                        </tr>
                        <tr>
                       		<th><qp:message code="sc.module.0007" /></th>
                            <td>
                                <qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" emptyLabel='sc.sys.0030'
                                        name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}" displayValue="${businessDesignSearchForm.moduleIdAutocomplete}" value="${businessDesignSearchForm.moduleId}" >
                                </qp:autocomplete>
                            </td>
                            <th><qp:message code="sc.businesslogicdesign.0047" /></th>
                            <td>
                                <qp:autocomplete optionValue="optionValue" optionLabel="optionLabel"  selectSqlId="getAutocompleteGetScreenNameForBD" emptyLabel='sc.sys.0030'
                                        name="screenId" arg01="${sessionScope.CURRENT_PROJECT.projectId}" displayValue="${businessDesignSearchForm.screenIdAutocomplete}" value="${businessDesignSearchForm.screenId}" >
                                </qp:autocomplete>
                            </td>
                        </tr>
                        <tr>
                        	<th><qp:message code="sc.businesslogicdesign.0266" /></th>
                            <td>
                                <c:forEach var="item" items="${CL_BD_DESIGN_MODE}">
									<label><form:checkbox path="designMode" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><qp:message code="${item.value}" /></label>
								</c:forEach>
                            </td>
                            <th><qp:message code="sc.businesslogicdesign.0020" /></th>
                            <td>
                                <c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label><form:checkbox path="designStatus" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox" /><qp:message code="${item.value}" /></label>
								</c:forEach>
                            </td>
                        </tr>
					</table>
				</div>
			</div>
			<!-- Style for button submit -->
			<div class="qp-div-action">
				<qp:authorization permission="businesslogicSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
			<c:if test="${page!=null}">
				<!-- Style for header weapper table -->
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
		                <qp:itemPerPage form="businessDesignSearchForm" action="/businessdesign/search" />
		            </div>
					<div class="panel-body">
						<div class="table-responsive">
							<c:if test="${page != null && page.totalPages > 0 }">
								<table class="table table-bordered qp-table-list">
									<colgroup>
										<col />
										<col width="20%" />
										<col width="20%" />
										<col width="12%" />
										<col width="15%" />
										<col width="10%" />
										<col width="10%" />
										<col />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:columnSort colName="business_logic_name" colCode="sc.businesslogicdesign.0005" form="businessDesignSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="business_logic_code" colCode="sc.businesslogicdesign.0006" form="businessDesignSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="blogic_type" colCode="Business logic type" form="businessDesignSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="screen_id" colCode="sc.businesslogicdesign.0047" form="businessDesignSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="design_mode" colCode="sc.businesslogicdesign.0266" form="businessDesignSearchForm"></qp:columnSort></th>
											<th><qp:columnSort colName="design_status" colCode="sc.businesslogicdesign.0020" form="businessDesignSearchForm"></qp:columnSort></th>
											<th></th>
										</tr>
									</thead>
									<c:forEach var="blogic" items="${page.content}" varStatus="rowStatus"> 
										<tr>
											<td><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
											<td class="word-wrap">
												<qp:authorization permission="businesslogicView" isDisplay="true" displayValue="${blogic.businessLogicName}">
			                                		<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/view?businessLogicId=${f:h(blogic.businessLogicId)}&openOwner=1&mode=0"><qp:formatText value="${blogic.businessLogicName}" /></a>
												</qp:authorization>
											</td>
											<td class="word-wrap"><qp:formatText value="${blogic.businessLogicCode}"/></td>
											<td><qp:message code="${CL_BLOGIC_TYPE.get(blogic.blogicType.toString())}"/></td>
											<td><qp:formatText value="${blogic.screenIdAutocomplete}"/></td>
											<td>
		                                    	<qp:message code="${CL_BD_DESIGN_MODE.get(blogic.designMode.toString())}"></qp:message>
		                                    </td>
											<td>
		                                    	<qp:message code="${CL_DESIGN_STATUS.get(blogic.designStatus.toString())}"></qp:message>
		                                    </td>
											<td>
												<qp:authorization permission="businesslogicModify">
													<c:if test="${businessDesignForm.moduleId == null}">
														<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && blogic.designStatus eq 1}">
															<qp:authorization permission="businesslogicModify">
																<a class="btn qp-button glyphicon glyphicon-cog qp-link-button qp-link-action" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:h(blogic.businessLogicId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.sys.0006"/>"> </a>
															</qp:authorization>
														</c:if>
													</c:if>
													<c:if test="${businessDesignForm.moduleId != null}">
														<c:if test="${blogic.moduleStatus eq 1 && blogic.designStatus eq 1 }">
															<qp:authorization permission="businesslogicModify">
																<a class="btn qp-button glyphicon glyphicon-cog qp-link-button qp-link-action" href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=${f:h(blogic.businessLogicId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.sys.0006"/>"> </a>
															</qp:authorization>
														</c:if>
													</c:if>
												</qp:authorization>
											</td>
										</tr>
									</c:forEach>
								</table>
								<qp:authorization permission="businesslogicSearch">
									<div class="qp-div-action">
			                            <c:choose>
			                                <c:when test="${page.sort != null }">
			                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(businessDesignSearchForm)}" maxDisplayCount="5" />
			                                </c:when>
			                                <c:otherwise>
			                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(businessDesignSearchForm)}" maxDisplayCount="5" />
			                                </c:otherwise>
			                            </c:choose>
			                        </div>
	                        	</qp:authorization>
							</c:if>
							<!-- Style for using pagination -->
						</div>
					</div>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
