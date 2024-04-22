<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.externalobjectdefinition"></qp:message></span></li>
         <li><span><qp:message code="sc.externalobjectdefinition.0004"/></span></li>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="header-link">
        <qp:authorization permission="externalobjectdefinitionRegister">
            <span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
            <a class="com-link-popup" href="${pageContext.request.contextPath}/externalobjectdefinition/register"><qp:message code="sc.externalobjectdefinition.0006"></qp:message></a>
        </qp:authorization>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <form:form method="post" modelAttribute="externalObjectDefinitionSearchForm" action="${pageContext.request.contextPath}/externalobjectdefinition/search">
            <div class="panel panel-default qp-div-search-condition">
                <div class="panel-heading">
                    <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message> </span>
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
                            <th><qp:message code="sc.externalobjectdefinition.0002"></qp:message></th>
                            <td><form:input path="externalObjectDefinitionName" cssClass="form-control qp-input-text" /></td>
                            <th><qp:message code="sc.externalobjectdefinition.0003"></qp:message></th>
                            <td><form:input path="externalObjectDefinitionCode" cssClass="form-control qp-input-text" /></td>
                        </tr>
                        <tr>
                            <th><form:label path="moduleId">
                                    <qp:message code="sc.module.0007" />
                                </form:label></th>
                            <td><qp:autocomplete name="moduleId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" value="${externalObjectDefinitionSearchForm.moduleId}" displayValue="${externalObjectDefinitionSearchForm.moduleIdAutocomplete}" arg01="${sessionScope.CURRENT_PROJECT.projectId}" mustMatch="true" maxlength="200">
                                </qp:autocomplete></td>
                            <th></th>
                            <td></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="qp-div-action">
                <qp:authorization permission="externalobjectdefinitionSearch">
                    <input type="button" value="Search" class="btn qp-button qp-button-search" />
                </qp:authorization>
            </div>
            <div class="panel panel-default qp-div-search-result">
                <div class="panel-heading">
                    <qp:itemPerPage form="externalObjectDefinitionSearchForm" action="/externalobjectdefinition/search"></qp:itemPerPage>
                </div>
                <div class="panel-body">
                    <c:if test="${page != null && page.totalPages gt 0 }">
                        <div class="table-responsive">
                            <table class="table table-bordered qp-table-list" id="">
                                <colgroup>
                                    <col />
                                    <col width="30%" />
                                    <col width="30%" />
                                    <col width="30%" />
                                    <col width="9%" />
                                    <col />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th><qp:message code="sc.sys.0004"></qp:message></th>
                                        <th><qp:columnSort colName="external_object_definition_name" form="externalObjectDefinitionSearchForm" colCode="sc.externalobjectdefinition.0002"></qp:columnSort></th>
                                        <th><qp:columnSort colName="external_object_definition_code" form="externalObjectDefinitionSearchForm" colCode="sc.externalobjectdefinition.0003"></qp:columnSort></th>
                                        <th><qp:columnSort colName="module_name" form="externalObjectDefinitionSearchForm" colCode="sc.module.0007"></qp:columnSort></th>
                                        <th><qp:columnSort colName="external_object_type" form="externalObjectDefinitionSearchForm" colCode="sc.externalobjectdefinition.0015"></qp:columnSort></th>
                                        <th></th>
                                    </tr>

                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${page.content}" varStatus="status">
                                        <tr class="form-inline">
                                            <td><qp:formatInteger value="${page.number * page.size + status.count}" /></td>
                                            <td class="word-wrap"><qp:authorization permission="externalobjectdefinitionView" isDisplay="true" displayValue="${f:h(item.externalObjectDefinitionName)}">
                                                    <a class="qp-link-popup" href="${pageContext.request.contextPath}/externalobjectdefinition/view?externalObjectDefinitionId=${f:u(item.externalObjectDefinitionId)}"><qp:formatText value="${item.externalObjectDefinitionName}" /></a>
                                                </qp:authorization></td>
                                            <td class="word-wrap"><qp:formatText value="${item.externalObjectDefinitionCode}" /></td>
                                            <td class="word-wrap"><qp:formatText value="${item.moduleIdAutocomplete}" /></td>
                                            <td class="word-wrap">${CL_SM_TYPE.get(item.externalObjectType.toString())}</td>
                                            <td align="center">
                                            	<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && item.externalObjectType != null && item.externalObjectType != '0' }">
                                                    <qp:authorization permission="externalobjectdefinitionModify">
                                                        <a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/externalobjectdefinition/modify?externalObjectDefinitionId=${f:u(item.externalObjectDefinitionId)}&mode=0&externalObjectType=${f:u(item.externalObjectType)}"></a>
                                                    </qp:authorization>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>

                            <%-- <qp:authorization permission="tabledesignSearch"> --%>
                            <div class="qp-div-action">
                                <c:choose>
                                    <c:when test="${page.sort != null }">
                                        <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" maxDisplayCount="5" />
                                    </c:when>
                                    <c:otherwise>
                                        <t:pagination outerElementClass="pagination pagination-md" page="${page}" maxDisplayCount="5" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <%-- </qp:authorization> --%>
                        </div>
                    </c:if>
                </div>
            </div>
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>
