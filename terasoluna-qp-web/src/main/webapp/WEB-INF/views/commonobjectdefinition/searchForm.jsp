<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.commonobjectdefinition"></qp:message></span></li>
         <li><span><qp:message code="sc.commonobjectdefinition.0004"/></span></li>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="header-link">
        <qp:authorization permission="commonobjectdefinitionRegister">
            <span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
            <a class="com-link-popup" href="${pageContext.request.contextPath}/commonobjectdefinition/register"><qp:message code="sc.commonobjectdefinition.0006"></qp:message></a>
        </qp:authorization>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <form:form method="post" modelAttribute="commonObjectDefinitionSearchForm" action="${pageContext.request.contextPath}/commonobjectdefinition/search">
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
                            <th><qp:message code="sc.commonobjectdefinition.0002"></qp:message></th>
                            <td><form:input path="commonObjectDefinitionName" cssClass="form-control qp-input-text" /></td>
                            <th><qp:message code="sc.commonobjectdefinition.0003"></qp:message></th>
                            <td><form:input path="commonObjectDefinitionCode" cssClass="form-control qp-input-text" /></td>
                        </tr>
                        <tr>
                            <th><form:label path="moduleId">
                                    <qp:message code="sc.module.0007" />
                                </form:label></th>
                            <td><qp:autocomplete name="moduleId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" value="${commonObjectDefinitionSearchForm.moduleId}" displayValue="${commonObjectDefinitionSearchForm.moduleIdAutocomplete}" arg01="${sessionScope.CURRENT_PROJECT.projectId}" mustMatch="true" maxlength="200">
                                </qp:autocomplete></td>
                            <th></th>
                            <td></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="qp-div-action">
                <qp:authorization permission="commonobjectdefinitionSearch">
                    <input type="button" value="Search" class="btn qp-button qp-button-search" />
                </qp:authorization>
            </div>
            <div class="panel panel-default qp-div-search-result">
                <div class="panel-heading">
                    <qp:itemPerPage form="commonObjectDefinitionSearchForm" action="/commonobjectdefinition/search"></qp:itemPerPage>
                </div>
                <div class="panel-body">
                    <c:if test="${page != null && page.totalPages gt 0 }">
                        <div class="table-responsive">
                            <table class="table table-bordered qp-table-list" id="">
                                <colgroup>
                                    <col />
                                    <col width="33%" />
                                    <col width="33%" />
                                    <col width="33%" />
                                    <col />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th><qp:message code="sc.sys.0004"></qp:message></th>
                                        <th><qp:columnSort colName="common_object_definition_name" form="commonObjectDefinitionSearchForm" colCode="sc.commonobjectdefinition.0002"></qp:columnSort></th>
                                        <th><qp:columnSort colName="common_object_definition_code" form="commonObjectDefinitionSearchForm" colCode="sc.commonobjectdefinition.0003"></qp:columnSort></th>
                                        <th><qp:columnSort colName="common_object_definition_module_name" form="commonObjectDefinitionSearchForm" colCode="sc.module.0007"></qp:columnSort></th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${page.content}" varStatus="status">
                                        <tr class="form-inline">
                                            <td><qp:formatInteger value="${page.number * page.size + status.count}" /></td>
                                            <td class="word-wrap"><qp:authorization permission="commonobjectdefinitionView" isDisplay="true" displayValue="${f:h(item.commonObjectDefinitionName)}">
                                                    <a class="qp-link-popup" href="${pageContext.request.contextPath}/commonobjectdefinition/view?commonObjectDefinitionId=${f:u(item.commonObjectDefinitionId)}"><qp:formatText value="${item.commonObjectDefinitionName}" /></a>
                                                </qp:authorization></td>
                                            <td class="word-wrap"><qp:formatText value="${item.commonObjectDefinitionCode}" /></td>
                                            <td class="word-wrap"><qp:formatText value="${item.moduleIdAutocomplete}" /></td>
                                            <td align="center"><c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
                                                    <qp:authorization permission="commonobjectdefinitionModify">
                                                        <a href="${pageContext.request.contextPath}/commonobjectdefinition/modify?commonObjectDefinitionId=${f:u(item.commonObjectDefinitionId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.commonobjectdefinition.0007"/>"></a>
                                                    </qp:authorization>
                                                </c:if></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>

                            <%-- <qp:authorization permission="tabledesignSearch"> --%>
                            <div class="qp-div-action">
                                <c:choose>
                                    <c:when test="${page.sort != null }">
                                        <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}"  maxDisplayCount="5" />
                                    </c:when>
                                    <c:otherwise>
                                        <t:pagination outerElementClass="pagination pagination-md" page="${page}"  maxDisplayCount="5" />
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
