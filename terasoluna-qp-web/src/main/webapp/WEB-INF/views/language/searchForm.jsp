<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.language"></qp:message></span></li>
         <li><span><qp:message code="sc.language.0068"/></span></li>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="header-link">
        <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
            <qp:authorization permission="languageRegister">
                <span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
                <a href="${pageContext.request.contextPath}/language/register"><qp:message code="sc.language.0001"></qp:message> </a>
            </qp:authorization>
        </c:if>
    </tiles:putAttribute>
    
     <tiles:putAttribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/language/search" modelAttribute="languageSearchForm">
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
                            <th><form:label path="languageName"><qp:message code="sc.language.0010"/></form:label></th>
                            <td><form:input path="languageName" cssClass="form-control qp-input-text" autofocus="true"/>
                            <th><form:label path="languageCode"><qp:message code="sc.language.0007"/></form:label></th>
                            <td><form:input path="languageCode" cssClass="form-control qp-input-text" />
                        </tr>
                    </table>
                </div>
            </div>

            <div class="qp-div-action">
                <qp:authorization permission="languageSearch">
                    <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
                </qp:authorization>
            </div>
        </form:form>
        
        <div class="panel panel-default qp-div-search-result">
            <div class="panel-heading">
                <qp:itemPerPage form="languageSearchForm" action="/language/search"></qp:itemPerPage>
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <c:if test="${page != null && page.totalPages > 0 }">
                        <table class="table table-bordered qp-table-list-none-action">
                            <colgroup>
                                <col width="5%" />
                                <col width="45%" />
                                <col width="50%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><qp:message code="sc.sys.0004" /></th>
                                    <th><qp:columnSort colName="language_name" colCode="sc.language.0010" form="languageSearchForm" /></th>
                                    <th><qp:columnSort colName="language_code" colCode="sc.language.0007" form="languageSearchForm" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${page.content }" varStatus="look">
                                    <tr>
                                        <td>${(page.number * page.size) + look.count}</td>
                                        <td>
                                            <qp:authorization permission="languageView" isDisplay="true" displayValue="${f:h(item.languageName)}">
                                                <a href="${pageContext.request.contextPath}/language/view?languageCode=${item.languageCode}&countryCode=${item.countryCode}" class="qp-link-popup">
                                                    <qp:formatText value="${item.languageName}" />
                                                </a>
                                            </qp:authorization>
                                        </td>
                                        <td><qp:formatText value="${item.languageCode}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <qp:authorization permission="languageSearch">
                            <div class="qp-div-action">
                                <c:choose>
                                    <c:when test="${page.sort != null }">
                                        <t:pagination outerElementClass="pagination pagination-md" page="${page}" queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${f:query(languageSearchForm)}" maxDisplayCount="5" />
                                    </c:when>
                                    <c:otherwise>
                                        <t:pagination outerElementClass="pagination pagination-md" page="${page}" criteriaQuery="${f:query(languageSearchForm)}" maxDisplayCount="5" />
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