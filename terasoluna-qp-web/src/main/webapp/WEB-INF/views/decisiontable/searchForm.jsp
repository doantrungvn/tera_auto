<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.decisiontable"></qp:message></span></li>
         <li><span><qp:message code="sc.decisiontable.0008"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
			<qp:authorization permission="decisiontableRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			    <a class="com-link-popup" href="${pageContext.request.contextPath}/decisiontable/register"><qp:message code="sc.decisiontable.0009" /></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
    
	<tiles:putAttribute name="body">
	
		<form:form action="${pageContext.request.contextPath}/decisiontable/search"
			modelAttribute="decisionTableSearchForm" method="post">
            <form:errors path="*" cssClass="error" element="div" />
			
			<!-- Style for block search -->
			<div class="panel panel-default  qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span
                        class="qp-heading-text"><qp:message code="sc.sys.0002"/></span>
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
                            <th ><form:label path="decisionTbName"><qp:message code="sc.decisiontable.0005"/></form:label></th>
                            <td><form:input path="decisionTbName" cssClass="form-control qp-input-text" value="" autofocus="true"/></td>
                            <th><form:label path="decisionTbCode"><qp:message code="sc.decisiontable.0006"/></form:label></th>
                            <td><form:input path="decisionTbCode" cssClass="form-control qp-input-text" value=""/></td>
                        </tr>
						<tr>
						    <th><form:label path="moduleId"><qp:message code="sc.module.0007"/></form:label></th>
	                        <td>
	                            <qp:autocomplete 
	                                 optionValue="optionValue" optionLabel="optionLabel"
	                                 selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" emptyLabel="Enter module here"
	                                 name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId }"
	                                 displayValue="${decisionTableSearchForm.moduleIdAutocomplete}" 
	                                 value="${decisionTableSearchForm.moduleId}" >
                                 </qp:autocomplete>
	                        </td>
	                         <th><form:label path="status"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
							<td>
							    <c:forEach var="item" items="${CL_DESIGN_STATUS}">
							        <label><form:checkbox path="designStatus" value="${item.key}" />&nbsp;<qp:message code="${CL_DESIGN_STATUS.get(item.key)}" /></label>
							    </c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<!-- Style for button submit -->
			<div class="qp-div-action">
			     <qp:authorization permission="decisiontableSearch">
                    <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
                </qp:authorization>
            </div>
			
			<!-- Style for header weapper table -->
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="decisionTableSearchForm" action="/decisiontable/search" />
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
						    <table class="table table-bordered qp-table-list">
	                            <colgroup>
	                                <col/>
	                                <col width="30%"/>
	                                <col width="30%"/>
	                                <col width="25%"/>
	                                <col width="15%" />
	                                <col/>
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <th><qp:message code="sc.sys.0004" /></th>
	                                    <th><qp:columnSort colName="decision_table_name" colCode="sc.decisiontable.0005" form="decisionTableSearchForm"/></th>
                                        <th><qp:columnSort colName="decision_table_code" colCode="sc.decisiontable.0006" form="decisionTableSearchForm"/></th>
	                                    <th><qp:columnSort colName="module_name" colCode="sc.module.0007" form="decisionTableSearchForm"/></th>
	                                    <th><qp:columnSort colName="design_status" colCode="sc.sys.0055" form="decisionTableSearchForm" /></th>
	                                    <th></th>
	                                </tr>
	                            </thead>
	                            <c:forEach var="decisiontable" items="${page.content}" varStatus="rowStatus">
	                               <tr>
	                                   <td><qp:formatInteger value="${(page.number * page.size) + rowStatus.count}" /></td>
	                                   <td class="word-wrap">
	                                       <qp:authorization permission="decisiontableView" isDisplay="true" displayValue="${decisiontable.decisionTbName}">
	                                           <a class="qp-link-popup" href="${pageContext.request.contextPath}/decisiontable/view?decisionTbId=${f:h(decisiontable.decisionTbId)}&mode=0">
	                                               <qp:formatText value="${decisiontable.decisionTbName}" /></a>
	                                       </qp:authorization>
	                                   </td>
	                                   <td class="word-wrap"><qp:formatText value="${decisiontable.decisionTbCode}" /></td>
	                                   <td class="word-wrap"><qp:formatText value="${decisiontable.moduleName}" /></td>
	                                   <td class="word-wrap"><qp:message code="${CL_DESIGN_STATUS.get(decisiontable.designStatus.toString())}"/></td>
	                                   <td>
	                                   		<c:if test="${not empty decisiontable.moduleId && decisiontable.designStatusParent eq 1 && decisiontable.designStatus eq 1 }">
	                                           <qp:authorization permission="decisiontableModify">
	                                               <a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" 
                                                        href="${pageContext.request.contextPath}/decisiontable/modify?decisionTbId=${f:h(decisiontable.decisionTbId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.decisiontable.0020"/>"> </a>
	                                           </qp:authorization>
	                                        </c:if>
	                                   	    <c:if test="${empty decisiontable.moduleId && sessionScope.CURRENT_PROJECT.status eq 1 && decisiontable.designStatus eq 1 }">
	                                           <qp:authorization permission="decisiontableModify">
	                                               <a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" 
                                                        href="${pageContext.request.contextPath}/decisiontable/modify?decisionTbId=${f:h(decisiontable.decisionTbId)}&mode=0" data-toggle="tooltip" title="<qp:message code="sc.decisiontable.0020"/>"> </a>
	                                           </qp:authorization>
	                                        </c:if>
                                       </td>
	                               </tr>
	                            </c:forEach>
	                        </table>
						</c:if>
						<!-- Style for using pagination -->
						<qp:authorization permission="decisiontableSearch">
	                        <div class="qp-div-action">
	                            <c:choose>
	                                <c:when test="${page.sort != null }">
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" 
	                                       criteriaQuery="${f:query(decisionTableSearchForm)}" maxDisplayCount="5" />
	                                </c:when>
	                                <c:otherwise>
	                                    <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(decisionTableSearchForm)}" maxDisplayCount="5" />
	                                </c:otherwise>
	                            </c:choose>
	                        </div>
	                    </qp:authorization>
					</div>
				</div>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
