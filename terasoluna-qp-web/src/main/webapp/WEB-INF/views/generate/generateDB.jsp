<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span>Generate database</span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			function checkForAll(obj) {
				var checked = $(obj).prop("checked");
				$("#tblSearchResult").find("tr").find("input[name^='generateDbSearchForm']").each(function(){
					$(this).prop("checked",checked);
				});
			}
			
			function checkForElement(obj) {
				var checkboxAll = $('#checkAll');
				var checkChild = $(obj).prop("checked");
				if(!checkChild) {
					checkboxAll.attr("checked",false);
				} else {
					var i = 0 ;
				}
			}
		</script>
	</tiles:putAttribute>
    <tiles:putAttribute name="body">
        <form:form action="${pageContext.request.contextPath}/generatedb/search"
            method="POST" modelAttribute="generateDbSearchForm">
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
                            <th><qp:message code="sc.screendesign.0018"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                            <td>
                                <qp:autocomplete 
                                        optionValue="optionValue" optionLabel="optionLabel"
                                        selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
                                        name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}"
                                        displayValue="${generateDbSearchForm.moduleIdAutocomplete}" 
                                        value="${generateDbSearchForm.moduleId}" >
                                </qp:autocomplete>
                            </td>
                            <th><form:label path="templateTypes">Screen pattern types</form:label></th>
                            <td>
                                <qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('2')}" />
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
				<c:choose>
					<c:when test="${page != null }">
						<c:set var="fcomSize" value="${page.size == null ? 10 : page.size}"></c:set>
						<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">
							<qp:message code="sc.sys.0003"></qp:message>
							&nbsp;
							<span class="badge">&nbsp;${page.totalElements}&nbsp;</span>
						</span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">
							<qp:message code="sc.sys.0003"></qp:message>
							&nbsp;
							<span class="badge">&nbsp;0&nbsp;</span>
						</span>
					</c:otherwise>
				</c:choose>
			</div>
            <div class="panel-body">
                <div class="table-responsive">
                    <c:if test="${page != null && page.totalPages > 0 }">
                        <table class="table table-bordered qp-table-list" id="tblSearchResult">
                            <colgroup>
                                <col/>
                                <col width="48%" />
                                <col width="48%" />
                                <!-- <col width="20%" /> -->
                              <!--   <col width="15%" /> -->
                                <!-- <col width="13%" />
                                <col width="10%" /> -->
                                <col width="5%" />
                                <col/>
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><qp:message code="sc.sys.0004"></qp:message></th>
                                    <th><qp:columnSort colName="message_string" colCode="sc.screendesign.0005" form="generateDbSearchForm"></qp:columnSort></th>
                                    <th><qp:columnSort colName="screen_code" colCode="sc.screendesign.0007" form="generateDbSearchForm"></qp:columnSort></th>
                                    <%-- <th><qp:columnSort colName="screen_pattern_type" colCode="sc.screendesign.0009" form="generateDbSearchForm"></qp:columnSort></th> --%>
                                    <%-- <th><qp:columnSort colName="template_type" colCode="sc.screendesign.0183" form="generateDbSearchForm"></qp:columnSort></th> --%>
                                    <%-- <th><qp:message code="sc.screendesign.0250"/></th>
                                    <th><qp:message code="sc.sys.0055"/></th> --%>
                                    <th><input type="checkbox" id="checkAll" onchange="checkForAll(this)" class="qp-input-checkbox-margin qp-input-checkbox" name="screenCode"></th>
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
                                    <%-- <td class="qp-output-text word-wrap"><qp:formatText value="${CL_SCREEN_PARTTERN_TYPES.get(screenList.screenPatternType.toString())}"/></td> --%>
                                    <%-- <td class="qp-output-text word-wrap"><qp:formatText value="${CL_TEMPLATE_TYPE.get(screenList.templateType.toString())}"/></td> --%>
                                    <%-- <td><qp:formatText value="${CL_SCREEN_DESIGN_MODE.get(screenList.designMode.toString())}"/></td>
                                    <td>
                                    	<qp:message code="${CL_DESIGN_STATUS.get(screenList.designStatus.toString())}"></qp:message>
                                    </td> --%>
                                    <td>
                                       <input type="checkbox" id="checkElement" onchange="checkForElement(this)" class="qp-input-checkbox-margin qp-input-checkbox" name="generateDbSearchForm[${rowStatus.index}].screenCode">
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
          <div class="qp-div-action">
					<%-- <qp:authorization permission="generationGenerateddl"> --%>
						<button type="submit" class="btn qp-button">Generate DB</button>
					<%-- </qp:authorization> --%>
				</div>
    </tiles:putAttribute>
</tiles:insertDefinition>
