<tiles:insertDefinition name="layouts-capture">
    
    <tiles:putAttribute name="additionalHeading">
       <link type="text/css" href="${pageContext.request.contextPath}/resources/app/screendesign/css/transition.css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=screendesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jsPlumb/jsPlumb-2.1.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/search.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/transition.js" ></script>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="body">
        <script type="text/javascript">
            // Array store all connections
            var allInstanceConnect = [];
            // Iterator
            //instance.reset();
            <c:forEach items="${scrConnects}" var="item">
                  instanceConnect = {
                          source : '${item.fromScreenId}',
                          target : '${item.toScreenId}',
                          label  : '${item.connectionMsg}',
                          screenActionId : '${item.screenActionId}'
                  };
                  // Adding array
                  allInstanceConnect.push(instanceConnect);
            </c:forEach>
        </script>
        
        <form:form action="${pageContext.request.contextPath}/screendesign/transition"
            method="POST" modelAttribute="screenTransitionForm">
            <form:hidden path="jsonInfo" value= "${jsonInfo }"/>
            <input type="hidden" name="parameters" />
            <input type="hidden" name="jsonConnector" />
            <input type="hidden" name="jsonInfo" />
            <qp:ColumnSortHidden/>
        <!-- Screen transattion -->
        <c:if test="${empty screenTransitionForm.mode}">
<!--         <div class="panel panel-default qp-div-search-result"> -->
<%--             <div class="panel-heading">        ${screenTransitionForm.mode} --%>
<!--                 <span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span> -->
<%--                 <span class="qp-heading-text"><qp:message code="tqp.screentransition"/>&nbsp; --%>
<%--                     <span class="badge">&nbsp;${fn:length(scrDesigns)}&nbsp;</span> --%>
<!--                 </span> -->
<!--             </div> -->
<!--              <div class="panel-body" id="main-transition"> -->
             <c:if test="${not empty scrDesigns}">
                 <div class="transition-area" id="transition-area" style="overflow: visible">
                 <input type="hidden" name="jsonConnector"/>
                      <c:forEach var="scrComponent" items="${scrDesigns}" varStatus="rowStatus">
                         <c:if test="${scrComponent.templateType eq 1 }">
                             <div class="transition-class"
                                  id="${f:h(scrComponent.screenId)}" style="left: ${scrComponent.xCoordinate}px; top: ${scrComponent.yCoordinate}px;"
                                  ondblclick="openScreenDesign('${f:h(scrComponent.screenId)}')">
                                  <span class='component-name'><qp:formatText value="${scrComponent.messageDesign.messageString}"/></span>
                                  <div class="ep"></div>
                             </div>
                         </c:if>
                         <c:if test="${scrComponent.templateType eq 2 }">
                             <div class="transition-class" 
                                  id="${f:h(scrComponent.screenId)}" style="left: ${scrComponent.xCoordinate}px; top: ${scrComponent.yCoordinate}px; border-color: #2F73FC;"
                                  ondblclick="openScreenDesign('${f:h(scrComponent.screenId)}')">
                                  <qp:formatText value="${scrComponent.messageDesign.messageString}"/>
                                  <div class="ep"></div>	
                             </div>
                         </c:if>
                     </c:forEach>
                 </div>
             </c:if>
<!--             </div> -->
<!--         </div> -->
        </c:if>
    </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>
