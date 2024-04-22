<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.screendesign"></qp:message></span></li>
         <li><span><qp:message code="sc.screendesign.0024"/></span></li>
    </tiles:putAttribute>

    <tiles:putAttribute name="header-link">
        <qp:authorization permission="screendesignSearch">
        	<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
            <a href="${pageContext.request.contextPath}/screendesign/search"> <qp:message code="sc.screendesign.0019"/></a>
        </qp:authorization>
        <qp:authorization permission="moduleSearch">
        	<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
            <a href="${pageContext.request.contextPath}/module/search"><qp:message code="sc.screendesign.0020"/></a>
        </qp:authorization>
    </tiles:putAttribute> 
    
    <tiles:putAttribute name="additionalHeading">
        <link type="text/css" href="${pageContext.request.contextPath}/resources/app/screendesign/css/transition.css" rel="stylesheet" />
        <link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/validation.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=screendesign_businesslogicdesign_functiondesign"></script>
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
                          /* source : '${item.fromScreenId}',
                          target : '${item.toScreenId}',
                          label  : '${item.connectionMsg}',
                          submitMethodType  : '${item.submitMethodType}',
                          navigateToBlogicId  : '${item.navigateToBlogicId}',
                          navigateToBlogicText  : '${item.navigateToBlogicText}',
                          moduleName  : '${item.moduleName}',
                          connectionMsg  : '${item.connectionMsg}',
                          screenActionId : '${item.screenActionId}', */
                          // Add new
                          screenTransitionId : '${item.screenTransitionId}',
                          transitionName : '${item.transitionName}',
                          transitionCode : '${item.transitionCode}',
                          fromScreen : '${item.fromScreen}',
                          toScreen : '${item.toScreen}',
                          status : '${item.status}',
                          type : '${item.type}',
                          buttonOrLinkName : '${item.buttonOrLinkName}',
                          bLogicName : '${item.bLogicName}'
                  };
                  // Adding array
                  allInstanceConnect.push(instanceConnect);
            </c:forEach>
    			var TEMPLATE_TYPE = {};
    			<c:forEach items="${CL_TEMPLATE_TYPE}" var="item"> 
    				TEMPLATE_TYPE['${item.key}'] = '${item.value}';
    			</c:forEach>
        </script>
        
        <form:form action="${pageContext.request.contextPath}/screendesign/transition"
            method="POST" modelAttribute="screenTransitionForm">
            <%@include file="dialog/connectorTransitionSetting.jsp" %>
            <%@include file="dialog/dialogAddNewScreen.jsp" %>
            <%@include file="dialog/dialogBranchNavigator.jsp" %>
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
            <form:hidden path="jsonInfo" value= "${jsonInfo }"/>
            <input type="hidden" name="parameters" />
            <input type="hidden" name="jsonConnector" />
            <input type="hidden" name="jsonBranch" />
            <input type="hidden" name="jsonInfo" />
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
                                        selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" emptyLabel="sc.sys.0030" 
                                        name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}"
                                        displayValue="${screenTransitionForm.moduleIdAutocomplete}" 
                                        value="${screenTransitionForm.moduleId}" 
                                        onChangeEvent="removeModuleId"
                                        arg04="0">
                                </qp:autocomplete>
                            </td>
                            <td colspan="2">
                                    <div class="qp-div-action" style="float: left">
						                <qp:authorization permission="screendesignTransition">
						                    <button type="submit" class="btn qp-button" onclick="checkClickShow()"><qp:message code="sc.sys.0025"/></button>
						                </qp:authorization>
						            </div>
						            <form:hidden path="clicked" value="${screenTransitionForm.clicked}"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        

        <!-- Screen transattion -->
        <c:if test="${empty screenTransitionForm.mode}">
        <div class="panel panel-default qp-div-search-result">
            <div class="panel-heading">${screenTransitionForm.mode}
                <span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
                <span class="qp-heading-text"><qp:message code="tqp.screentransition"/>&nbsp;
                    <span class="badge">&nbsp;${fn:length(scrDesigns)}&nbsp;</span>
                </span>
            </div>
                <div class="panel-body" id="main-transition" style="padding: 0px">
                	<table class="table table-bordered qp-table-list" id="allDragDropContent">
                		<colgroup>
							<col width="150px" />
							<col width="100%" />
						</colgroup>
						<tbody>
							<tr>
								<td valign="top" id="srcgenElements" style="vertical-align: top; padding: 0px 0px;" level="1">
									<!-- Begin include componentList --> 
									<jsp:include page="screenList.jsp" />
									<!-- End include componentList -->
								</td>
								<td>
									<c:if test="${not empty scrDesigns}">
					                    <div class="transition-area" id="transition-area">
					                    <input type="hidden" name="jsonConnector"/>
					                    
					                         <c:forEach var="scrComponent" items="${scrDesigns}" varStatus="rowStatus">
					                            <c:if test="${scrComponent.templateType eq 1 }">
					                                <div class="transition-class bdesign-node" componenttype="1"
					                                     id="${f:h(scrComponent.screenId)}" style="left: ${scrComponent.xCoordinate}px; top: ${scrComponent.yCoordinate}px;"
					                                     ondblclick="openScreenDesign('${f:h(scrComponent.screenId)}')">
					                                     <i class="glyphicon glyphicon-unchecked" ></i>
					                                     <span class='component-name'><qp:formatText value="${scrComponent.messageDesign.messageString}"/></span>
					                                     <div class="ep"></div>
					                                     <form:hidden path="jSonScreenTransition" value="0"/>
					                                     <input type="hidden" name="screenDesignElement" value="">
					                                     <input type="hidden" name="screenDesignCode" value="${scrComponent.screenCode}">
					                                </div>
					                            </c:if>
					                            <c:if test="${scrComponent.templateType eq 2 }">
					                                <div class="transition-class bdesign-node"  componenttype="2"
					                                     id="${f:h(scrComponent.screenId)}" style="left: ${scrComponent.xCoordinate}px; top: ${scrComponent.yCoordinate}px; border-color: #2F73FC;"
					                                     ondblclick="openScreenDesign('${f:h(scrComponent.screenId)}')">
					                                     <i class="glyphicon glyphicon-modal-window" ></i>
					                                     <span class='component-name'><qp:formatText value="${scrComponent.messageDesign.messageString}"/></span>
					                                     <div class="ep"></div>
					                                     <form:hidden path="jSonScreenTransition" value="0"/>
					                                     <input type="hidden" name="screenDesignElement" value="">
					                                     <input type="hidden" name="screenDesignCode" value="${scrComponent.screenCode}">
					                                </div>
					                            </c:if>
					                            <c:if test="${scrComponent.templateType eq -1 }">
					                                <div class="transition-class bdesign-node" componenttype="-1"
					                                     id="${f:h(scrComponent.screenId)}" style="left: ${scrComponent.xCoordinate}px; top: ${scrComponent.yCoordinate}px; border-color: #000000;"
					                                     ondblclick="openScreenDesign('${f:h(scrComponent.screenId)}')">
					                                     <span class='component-name'><qp:formatText value="${scrComponent.messageDesign.messageString}"/></span>
					                                     <div class="ep"></div>
					                                     <form:hidden path="jSonScreenTransition" value="0"/>
					                                     <input type="hidden" name="screenDesignElement" value="">
					                                     <input type="hidden" name="screenDesignCode" value="${scrComponent.screenCode}">
					                                </div>
					                            </c:if>
					                        </c:forEach>
					                        <c:forEach var="scrBranch" items="${scrBranch}" varStatus="rowStatus1">
					                        	<div class="transition-class bdesign-node node-branch" componenttype="3" status="${f:h(scrBranch.status)}"
					                                     id="${f:h(scrBranch.branchId)}" style="left: ${scrBranch.xCoordinates}px; top: ${scrBranch.yCoordinates}px; border-color: #4B0707"
					                                     ondblclick="openBranchInformation(this)">
					                                     <img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/if.png" class="tool-class-img" />
					                                     <qp:formatText value="${scrBranch.name}"/>
					                                     <div class="ep"></div>
					                                     <input type="hidden" name="screenDesignElement" value="${f:h(scrBranch.screenDesignElement)}" />
					                                </div>
					                        </c:forEach>
					                    </div>
					                    
					                </c:if>
					                <c:if test="${empty scrDesigns}">
					                	<div class="transition-area" id="transition-area">
					                	</div>
					                </c:if>
								</td>
							</tr>
						</tbody>
                	</table>
               </div>
        </div>
        </c:if>
        <c:if test="${not empty screenTransitionForm.moduleId }">
       		<div class="qp-div-action">
            	<button type="button" onclick="saveScreenPosition" name="saveBtn" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"/></button>
        	</div>
       </c:if>
    </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>
