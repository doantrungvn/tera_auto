<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.sessionmanagement"></qp:message></span></li>
         <li><span><qp:message code="sc.sessionmanagement.0004"/></span></li>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="header-link">
        <qp:authorization permission="sessionmanagementRegister">
            <span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
            <a class="com-link-popup" href="${pageContext.request.contextPath}/sessionmanagement/register"><qp:message code="sc.sessionmanagement.0006"></qp:message></a>
        </qp:authorization>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <form:form method="post" modelAttribute="sessionManagementSearchForm" action="${pageContext.request.contextPath}/sessionmanagement/search">
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
                            <th><qp:message code="sc.sessionmanagement.0002"></qp:message></th>
                            <td><form:input path="sessionManagementName" cssClass="form-control qp-input-text" /></td>
                            <th><qp:message code="sc.sessionmanagement.0003"></qp:message></th>
                            <td><form:input path="sessionManagementCode" cssClass="form-control qp-input-text" /></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="qp-div-action">
                <qp:authorization permission="sessionmanagementSearch">
<!--                     <input type="button" value="Search" class="btn qp-button qp-button-search" /> -->
                         <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
                </qp:authorization>
            </div>
            <div class="panel panel-default qp-div-search-result">
                <div class="panel-heading">
                    <qp:itemPerPage form="sessionManagementSearchForm" action="/sessionmanagement/search"></qp:itemPerPage>
                </div>
                <div class="panel-body">
                    <c:if test="${page != null && page.totalPages gt 0 }">
                        <div class="table-responsive">
                            <table class="table table-bordered qp-table-list" id="">
                                <colgroup>
                                    <col />
                                    <col width="23%" />
                                    <col width="23%" />
                                    <col width="23%" />
                                    <col width="23%" />
                                    <col width="8%" />
                                    <col />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th><qp:message code="sc.sys.0004"></qp:message></th>
                                        <th><qp:columnSort colName="session_management_name" form="sessionManagementSearchForm" colCode="sc.sessionmanagement.0002"></qp:columnSort></th>
                                        <th><qp:columnSort colName="session_management_code" form="sessionManagementSearchForm" colCode="sc.sessionmanagement.0003"></qp:columnSort></th>
                                        <th><qp:columnSort colName="data_type" form="sessionManagementSearchForm" colCode="sc.sessionmanagement.0008"></qp:columnSort></th>
                                        <th><qp:columnSort colName="object_id" form="sessionManagementSearchForm" colCode="sc.sessionmanagement.0009"></qp:columnSort></th>
                                        <th><qp:columnSort colName="session_management_type" form="sessionManagementSearchForm" colCode="sc.sessionmanagement.0010"></qp:columnSort></th>
                                        <th></th>
                                    </tr>

                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${page.content}" varStatus="status">
                                        <tr class="form-inline">
                                            <td><qp:formatInteger value="${page.number * page.size + status.count}" /></td>
                                            <td class="word-wrap"><qp:authorization permission="sessionmanagementView" isDisplay="true" displayValue="${f:h(item.sessionManagementName)}">
                                                    <a class="qp-link-popup" href="${pageContext.request.contextPath}/sessionmanagement/view?sessionManagementId=${f:u(item.sessionManagementId)}"><qp:formatText value="${item.sessionManagementName}" /></a>
                                                </qp:authorization></td>
                                            <td class="word-wrap"><qp:formatText value="${item.sessionManagementCode}" /></td>
                                            <td class="word-wrap">${CL_SM_DATATYPE.get(item.dataType.toString())}<c:if test="${item.arrayFlg }"> []</c:if></td>
                                            <td class="word-wrap"><qp:formatText value="${item.objectIdAutocomplete}" /></td>
                                            <td class="word-wrap">${CL_SM_TYPE.get(item.sessionManagementType.toString())}</td>
                                            <td align="center">
                                            <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
                                                <qp:authorization permission="sessionmanagementModify">
                                                    <c:if test="${item.sessionManagementType != null && item.sessionManagementType == 1 }">
                                                        <a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/sessionmanagement/modify?sessionManagementId=${f:u(item.sessionManagementId)}&mode=0&sessionManagementType=${f:u(item.sessionManagementType)}"></a>
                                                    </c:if>
                                                </qp:authorization>
                                            </c:if></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

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
                        </div>
                    </c:if>
                </div>
            </div>
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>