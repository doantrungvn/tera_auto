<tiles:insertDefinition name="layouts-popup">

	<tiles:putAttribute name="header-name">	
	    <qp:message code="sc.decisiontable.0010"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=decisiontable"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/constant.js" ></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/init.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/displayView.js" ></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<c:if test="${ not empty decisionTableForm}">
		
		<script type="text/javascript">
             var CL_QP_DATATYPE = {};//CL_QP_Datatype
             <c:forEach items="${CL_BD_DATATYPE_NOT_ENTITY}" var="item">
                 CL_QP_DATATYPE['${item.key}'] = "<qp:message code='${item.value}'/>";
             </c:forEach>

             var CL_QP_OPERATORTYPE = {};
             <c:forEach items="${CL_QP_OPERATORTYPE}" var="item">
                 CL_QP_OPERATORTYPE['${item.key}'] = "<qp:message code='${item.value}'/>";
             </c:forEach>
             
             var CL_SUPPORT_OPTION_VALUE_FLAG = {};
             <c:forEach items="${CL_SUPPORT_OPTION_VALUE_FLAG}" var="item">
                 CL_SUPPORT_OPTION_VALUE_FLAG['${item.key}'] = "<qp:message code='${item.value}'/>";
             </c:forEach>
             
             $( document ).ready(function() {
             	$.qp.decisiontable.initAddButtonRow('tbl_input_list_result', 'tbl_input_list_result-action-template');
             	$.qp.decisiontable.initAddButtonRow('tbl_output_list_result', 'tbl_output_list_result-action-template');
     		});
        </script>
        <c:if test="${empty message}">
		<form:form method="post" action="${pageContext.request.contextPath}/decisiontable/viewListAffectedChangeDesignDeleteForm" modelAttribute="decisionTableForm">

		    <!-- Hidden define -->
            <form:hidden path="listInput"/>
            <form:hidden path="listOutput"/>
            <form:hidden path="listItemCondition"/>
            <form:hidden path="listItemAction"/>
            <form:hidden path="listConditionGroup"/>
            <form:hidden path="listConditionItem"/>
            
			<div class="panel panel-default qp-div-information" >
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.decisiontable.0007"/></span>
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
							<th>
							    <form:label path="decisionTbName"><qp:message code="sc.decisiontable.0005"/></form:label></th>
							<td class="word-wrap">
							    <qp:formatText value="${decisionTableForm.decisionTbName}"/><form:hidden path="decisionTbName"/></td>
							<th>
							    <form:label path="decisionTbCode"><qp:message code="sc.decisiontable.0006"/></form:label></th>
							<td class="word-wrap">
							    <qp:formatText value="${decisionTableForm.decisionTbCode}"/><form:hidden path="decisionTbCode"/></td>
						</tr>
						<tr>
							<th>
							    <form:label path="moduleName"><qp:message code="sc.module.0007"/></form:label></th>
							<td class="word-wrap">
							    <qp:formatText value="${decisionTableForm.moduleName}"/><form:hidden path="moduleName"/></td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
                            <td><input type="hidden" name="designStatus" value="${decisionTableForm.designStatus}"/>
                            	<qp:message code="${CL_DESIGN_STATUS.get(decisionTableForm.designStatus.toString())}"/></td>
						</tr>
						<tr>
                            <th><form:label path="remark"><qp:message code="sc.sys.0028"/></form:label></th>
                            <td class="word-wrap" colspan="3"><qp:formatRemark value="${decisionTableForm.remark}"/><form:hidden path="remark"/></td>
						</tr>
					</table>
				</div>
			</div>
            
            <div class="form-inline form-group qp-div-action">
                <c:if test="${not empty decisionTableForm.moduleId && decisionTableForm.designStatusParent eq 1 && decisionTableForm.designStatus eq 1 }">
                    <qp:authorization permission="changeDesignStatus">
                        <form:hidden path="actionDelete" value="true"/>
                        <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
                    </qp:authorization>
                    <qp:authorization permission="decisiontableDelete">
                        <c:if test="${empty decisionTableForm.listBD || (not empty decisionTableForm.listBD && deleteObjectHasFk)}">
                        	<%-- <div class="checkbox">
								<label><form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
							</div> --%>
                            <button type="submit" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag()"><qp:message code="sc.sys.0008" /></button>
                        </c:if>
                    </qp:authorization>
                    <qp:authorization permission="decisiontableModify">
                        <a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/decisiontable/modify?decisionTbId=${f:h(decisionTableForm.decisionTbId)}&mode=1">
                            <qp:message code="sc.sys.0006" />
                        </a>
                    </qp:authorization>
                </c:if>
                <c:if test="${not empty decisionTableForm.moduleId && decisionTableForm.designStatusParent eq 1 && decisionTableForm.designStatus == 2}">
                    <qp:authorization permission="changeDesignStatus">
                        <form:hidden path="actionDelete" value="true"/>
                        <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
                    </qp:authorization>
                </c:if>
                <c:if test="${empty decisionTableForm.moduleId && sessionScope.CURRENT_PROJECT.status eq 1 && decisionTableForm.designStatus eq 1 }">
                    <qp:authorization permission="changeDesignStatus">
                        <form:hidden path="actionDelete" value="true"/>
                        <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
                    </qp:authorization>
                    <qp:authorization permission="decisiontableDelete">
                    	<%-- <div class="checkbox">
							<label><form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
						</div> --%>
                        <c:if test="${empty decisionTableForm.listBD || (not empty decisionTableForm.listBD && deleteObjectHasFk)}">
                            <button type="submit" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag()"><qp:message code="sc.sys.0008" /></button>
                        </c:if>
                    </qp:authorization>
                    <qp:authorization permission="decisiontableModify">
                        <a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/decisiontable/modify?decisionTbId=${f:h(decisionTableForm.decisionTbId)}&mode=1">
                            <qp:message code="sc.sys.0006" />
                        </a>
                    </qp:authorization>
                </c:if>
                <c:if test="${empty decisionTableForm.moduleId && sessionScope.CURRENT_PROJECT.status eq 1 && decisionTableForm.designStatus == 2}">
                    <qp:authorization permission="changeDesignStatus">
                        <form:hidden path="actionDelete" value="true"/>
                        <button type="submit" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
                    </qp:authorization>
                </c:if>
                
                <!-- Hidden field -->
                <form:hidden path="decisionTbId" value="${decisionTableForm.decisionTbId}"/>
                <form:hidden path="updatedDate" value="${decisionTableForm.updatedDate}"/>
                <form:hidden path="moduleId" value="${decisionTableForm.moduleId}"/>
                <form:hidden path="projectId" value="${decisionTableForm.projectId}"/>
            </div>
			
			<!-- Block for display tab -->
			<div id="tabsDecision">
				<ul class="nav nav-tabs" id="com-menu-sidebar">
					<li><a href="#tabsDecision-2" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0011"/></a></li>
					<li><a href="#tabsDecision-3" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0012"/></a></li>
					<li><a href="#tabsDecision-4" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0013"/></a></li>
					<li class="active"><a href="#tabsDecision-1" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0014"/></a></li>
				</ul>
				
				<div class="tab-content">
					<div id="tabsDecision-1" class="tab-pane active" style="height: auto;">
						<div class="panel panel-default" style="display: none;">
							<div class="panel-body">
								<table class="table table-bordered qp-table-list" id="tbl_logic_design"></table>
							</div>
						</div>
					</div>

					<div id="tabsDecision-2" class="tab-pane" style="height: auto;">
						<div class="panel panel-default">
							<div class="panel-body">
								<table class="table table-bordered qp-table-list" id="tbl_input_list_result">
									<colgroup>
										<col width="35px"/>
										<col width="320px"/>
										<col width="25%"/>
										<col width="25%"/>
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"/></th>
											<th><qp:message code="sc.decisiontable.0015"/></th>
											<th><qp:message code="sc.decisiontable.0016"/></th>
											<th><qp:message code="sc.decisiontable.0017"/></th>
										</tr>
									</thead>
									<tbody>
                                        <c:forEach items="${decisionTableForm.inputLst }" var="inputBean" varStatus="status">
                                            <tr data-ar-rgroup="${inputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-common-object-template" data-ar-rindex="${inputBean.itemSequenceNo }" data-ar-rgroupindex="${inputBean.tableIndex }">
                                                <td colspan="2" style="width: 410px">
                                                    <div style="height: 100%">
                                                        <div>
                                                            <span class="ar-groupIndex">${inputBean.tableIndex }</span>
                                                        </div>
                                                        <div class="pull-right" style="height: 100%; text-align: left; vertical-align: middle;">
                                                            <label>${inputBean.decisionInputBeanName }</label>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <label>${inputBean.decisionInputBeanCode }</label>
                                                </td>
                                                <td>
                                                    <qp:formatText value='${CL_QP_DATATYPE.get(inputBean.dataType.toString())}' />
                                                </td>
                                            </tr>
                                        </c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
					<div id="tabsDecision-3" class="tab-pane" style="height: auto;">
						<div class="panel panel-default">
							<div class="panel-body">
								<table class="table table-bordered qp-table-list" id="tbl_output_list_result">
									<colgroup>
										<col width="35px"/>
										<col width="320px"/>
										<col width="25%"/>
										<col width="25%"/>
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"/></th>
											<th><qp:message code="sc.decisiontable.0015"/></th>
											<th><qp:message code="sc.decisiontable.0016"/></th>
											<th><qp:message code="sc.decisiontable.0017"/></th>
										</tr>
									</thead>
									<tbody>
                                        <c:forEach items="${decisionTableForm.outputLst }" var="outputBean" varStatus="status">
                                            <tr data-ar-rgroup="${outputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-common-object-template" data-ar-rindex="${outputBean.itemSequenceNo }" data-ar-rgroupindex="${outputBean.tableIndex }">
                                                <td colspan="2" style="width: 410px">
                                                    <div style="height: 100%">
                                                        <div>
                                                            <span class="ar-groupIndex">${outputBean.tableIndex }</span>
                                                        </div>
                                                        <div class="pull-right" style="height: 100%; text-align: left; vertical-align: middle;">
                                                            <label>${outputBean.decisionOutputBeanName }</label>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <label>${outputBean.decisionOutputBeanCode }</label>
                                                </td>
                                                <td>
                                                    <qp:formatText value='${CL_QP_DATATYPE.get(outputBean.dataType.toString())}' />
                                                </td>
                                            </tr>
                                        </c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- Item condition tabs -->
					<div id="tabsDecision-4" class="tab-pane" style="height: auto;">
						<div class="panel panel-default">
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action" id="tbl_item_condition">
									<colgroup>
										<col width="4%"/>
                                           <col width="30%"/>
										<col width="30%"/>
										<col width="20%"/>
										<col width="16%"/>
									</colgroup>
									<thead>
										<tr>
											<th colspan="5">
												<qp:message code="sc.decisiontable.0018"/>
											</th>
										</tr>
										<tr>
											<th><qp:message code="sc.sys.0004"/></th>
											<th><qp:message code="sc.decisiontable.0042"/></th>
                                                <th><qp:message code="sc.decisiontable.0036"/></th>
                                                <th><qp:message code="sc.decisiontable.0037"/></th>
                                                <th><qp:message code="sc.decisiontable.0038"/></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="panel-body">
								<table class="table table-bordered qp-table-list-none-action" id="tbl_item_action">
									<colgroup>
										<col width="4%"/>
                                           <col width="30%"/>
										<col width="30%"/>
										<col width="20%"/>
										<col width="16%"/>
									</colgroup>
									<thead>
										<tr>
											<th colspan="5">
												<qp:message code="sc.decisiontable.0019"/>
											</th>
										</tr>
										<tr>
											<th><qp:message code="sc.sys.0004"/></th>
											<th><qp:message code="sc.decisiontable.0043"/></th>
                                            <th><qp:message code="sc.decisiontable.0039"/></th>
                                            <th><qp:message code="sc.decisiontable.0040"/></th>
                                            <th><qp:message code="sc.decisiontable.0041"/></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br />
			
			<!-- List of business logic -->
            <div class="panel panel-default qp-div-select">
                <div class="panel-heading">
                    <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
                    <span class="pq-heading-text"><qp:message code="sc.decisiontable.0034"></qp:message></span>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-bordered qp-table-list-none-action">
                             <colgroup>
                          <col />
                          <col width="40%" />
                          <col width="30%" />
                          <col width="30%" />
                      </colgroup>
                            <thead>
                                <tr>
                                    <th><qp:message code="sc.sys.0004"></qp:message></th>
                                    <th><qp:message code="sc.businesslogicdesign.0005"></qp:message></th>
                                    <th><qp:message code="sc.businesslogicdesign.0006"></qp:message></th>
                                    <th><qp:message code="sc.businesslogicdesign.0013"></qp:message></th>
                                </tr>
                            </thead>
                            <c:forEach var="listBD" items="${decisionTableForm.listBD}" varStatus="loop">
                                <tr>
                                    <td class="qp-output-fixlength tableIndex">${loop.index + 1}
                                        <form:hidden path="listBD[${loop.index}].businessLogicName"/>
                                        <form:hidden path="listBD[${loop.index}].businessLogicCode"/>
                                        <form:hidden path="listBD[${loop.index}].blogicType"/>
                                    </td>
                                    <td><qp:formatText value="${listBD.businessLogicName}"/></td>
                                    <td><qp:formatText value="${listBD.businessLogicCode}"/></td>
                                    <td><qp:message code="${CL_BLOGIC_TYPE.get(listBD.blogicType.toString())}"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty decisionTableForm.listBD}">
                                <tr>
                                    <td colspan="4"><qp:message code="inf.sys.0013"/></td>
                                </tr>
                            </c:if>
                        </table>
                    </div>
                </div>
            </div>

			
			<br/>
			<script type="text/javascript">
		         var arrTmp = [];
			     var listInputs = $.qp.decisiontable.convertToJson('${decisionTableForm.listInput }');
			     arrTmp[0] = listInputs;
			     var listOutputs = $.qp.decisiontable.convertToJson('${decisionTableForm.listOutput }');
			     arrTmp[1] = listOutputs;
			     var listConds = $.qp.decisiontable.convertToJson('${decisionTableForm.listItemCondition }');
			     arrTmp[2] = listConds;
			     var listActs = $.qp.decisiontable.convertToJson('${decisionTableForm.listItemAction }');
			     arrTmp[3] = listActs;
			     var listConditionGroup = $.qp.decisiontable.convertToJson('${decisionTableForm.listConditionGroup }');
			     arrTmp[4] = listConditionGroup;
                 var listConditionItem = $.qp.decisiontable.convertToJson('${decisionTableForm.listConditionItem }');
                 arrTmp[5] = listConditionItem;
                 
                 displayView(arrTmp);
                 
                 function setFlag() {
                     $("#actionDelete").val(false); 
                 }
            </script>
		</form:form>
		</c:if>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>