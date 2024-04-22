<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">	
	    <qp:message code="sc.functionmaster.0017" />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/init.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/functionmaster.js" ></script>
        
         <script type="text/javascript">
			$( document ).ready(function() {

	        	$.qp.ar.recalculateRowIndex($("#wapper>table>tbody").closest("table"),"");
	        	$.qp.ar.renameAttributes($("#wapper>table>tbody").closest("table"));

				reIndexTable();
				initAddButton();
			});
		</script>
	</tiles:putAttribute>
	<c:if test="${notExistFlg ne 1}">
		<tiles:putAttribute name="body">
			<form:form method="post" action="${pageContext.request.contextPath}/functionmaster/deleteConfirm" modelAttribute="functionMasterForm">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.functionmaster.0010" /></span>
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
								<th><qp:message code="sc.functionmaster.0005" /></th>
								<td class="word-wrap"><qp:formatText value="${functionMasterForm.functionMasterName}" /></td>
								<th><qp:message code="sc.functionmaster.0006" /></th>
								<td class="word-wrap"><qp:formatText value="${functionMasterForm.functionMasterCode}" /></td>
							</tr>
							<c:choose>
								<c:when test="${functionMasterForm.functionMasterType == 1}">
									<tr>
										<th><labeL ><qp:message code="sc.functionmaster.0029" /></labeL></th>
										<td>
											<a href="${pageContext.request.contextPath}/functionmaster/downloadfile?uploadFileId=${f:h(functionMasterForm.uploadFileId)}"><qp:formatText value="${functionMasterForm.fileName}" /></a>
										</td>
										<th><qp:message code="sc.functionmaster.0030" /></th>
										<td class="word-wrap"><qp:formatText value="${functionMasterForm.packageName}" /></td>
									</tr>
									<tr>
										<th><qp:message code="sc.functionmaster.0007" /></th> 
										<td>
											<input type="hidden" name="functionMasterType" value="${functionMasterForm.functionMasterType}"/>
											<qp:message code="${CL_FUNCTION_TYPE.get(functionMasterForm.functionMasterType.toString())}"  />
										</td>
										<th><qp:message code="sc.sys.0028" /></th>
										<td class="word-wrap"><qp:formatRemark value="${functionMasterForm.remark}" /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<th><qp:message code="sc.functionmaster.0007" /></th> 
										<td><qp:message code="${CL_FUNCTION_TYPE.get(functionMasterForm.functionMasterType.toString())}" /></td>
										<th><qp:message code="sc.sys.0028" /></th>
										<td class="word-wrap"><qp:formatRemark value="${functionMasterForm.remark}" /></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
				</div>
                
                <c:if test="${functionMasterForm.functionMasterType == 1}">
                    <div class="qp-div-action form-inline form-group">
                        <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
                            <form:hidden path="functionMasterId" />
                            <form:hidden path="functionMasterName" />
                            <form:hidden path="functionMasterCode" />
                            <form:hidden path="remark" />
                            <form:hidden path="uploadFileId" />
                            <form:hidden path="updatedDate" />
                            <qp:authorization permission="functionmasterDelete">
                            	<div class="checkbox">
									<label>	
										<form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" />
									</label>
								</div>
                                <button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
                            </qp:authorization>
                            <qp:authorization permission="functionmasterModify">
                                <a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/functionmaster/modify?functionMasterId=${functionMasterForm.functionMasterId}&mode=1"><qp:message code="sc.sys.0006" /></a>
                            </qp:authorization>
                        </c:if>
                    </div>
                </c:if>
				
				<c:if test="${ not empty functionMasterForm.functionMethod}">
                <div id="wapper">
                <table id='tblLstMethod'>
					<c:forEach var="functionMethod" items="${functionMasterForm.functionMethod}" varStatus="status">
                        <tr data-ar-rgroup="${functionMethod.groupId}" class="ar-dataRow" data-ar-rindex="${functionMethod.itemSeqNo }" data-ar-rgroupindex="${functionMethod.tableIndex }">
                        <td>
						<div id="methodInfor${status.index}" style="border-color: red; border-style: dashed; border-width: 2px; margin-bottom: 20px">
						<table class="table table-bordered qp-table-form" name="methodInfo" id="tblMethod" data-ar-tlevel="0" >
                            <input type="hidden" id="currentMethod" name="currentMethod" class="ar-rIndex"/>  
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody>
								<tr><th style="text-align: left; vertical-align: middle;" colspan="4"><qp:message code="sc.functionmaster.0032" /></th></tr>
								<tr>
									<th><qp:message code="sc.functionmaster.0027" /></th>
									<td class="word-wrap"><qp:formatText value="${functionMethod.functionMethodName}" /></td>
									<th><qp:message code="sc.functionmaster.0028" /></th>
									<td class="word-wrap"><qp:formatText value="${functionMethod.functionMethodCode}" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.sys.0028" /></th>
									<td colspan="3"><qp:formatRemark value="${functionMethod.remark}" /></td>
								</tr>
								<tr data-ar-rgroup="${functionMethod.groupId}" class="ar-dataRow" data-ar-rindex="${functionMethod.itemSeqNo }" data-ar-rgroupindex="${functionMethod.tableIndex }">
									<td colspan="4">
										<ul class="nav nav-tabs" id="com-menu-sidebar">
											<li class="active" name="liInputLogic"><a href="#tabsDecision-${status.index}1" data-toggle="tab" style="font: bold;"><qp:message code="sc.functionmaster.0033" /></a></li>
											<li name="liOutputLogic"><a href="#tabsDecision-${status.index}2" data-toggle="tab" style="font: bold;"><qp:message code="sc.functionmaster.0034" /></a></li>
										</ul>
										<div class="tab-content">
											<div id="tabsDecision-${status.index}1" name="divInputLogic" class="tab-pane active" style="height: auto;">
												<div class="panel panel-default">
													<div class="panel-body">
														<table class="table table-bordered qp-table-list-none-action" id="tblInput" name="tblInput" data-ar-callback="$.qp.functionmaster.formCallback" data-ar-tlevel="1">
															<colgroup>
																<col width="40px"/>
																<col width="360px"/>
																<col width="30%"/>
																<col/>
															</colgroup>
															<thead>
																<tr>
																	<th><qp:message code="sc.sys.0004" /></th>
																	<th><qp:message code="sc.functionmaster.0035" /></th>
																	<th><qp:message code="sc.functionmaster.0036" /></th>
																	<th><qp:message code="sc.functionmaster.0037" /></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach var="functionMethodInput" items="${functionMethod.functionMethodInput}" varStatus="statusInput">
																	<tr data-ar-rgroup="${functionMethodInput.groupId}" class="ar-dataRow" data-ar-templateid="tblInput-common-object-template" data-ar-rindex="${functionMethodInput.itemSeqNo }" data-ar-rgroupindex="${functionMethodInput.tableIndex }">
																		<td colspan="2" style="width: 400px">
                                                                            <div style="height:100%">
                                                                                <div>
                                                                                    <span  class="ar-groupIndex">${functionMethodInput.tableIndex }</span>
                                                                                </div>
                                                                                <div class="pull-right" style="height:100%;vertical-align: middle;">
                                                                                    <label class="qp-output-text">${functionMethodInput.methodInputName}</label>
                                                                                </div>
                                                                            </div>
                                                                        </td>
																		<td class="word-wrap"><qp:formatText value="${functionMethodInput.methodInputCode}" /></td>
																		<td>
																			<div style="text-align: left;">
                                                                                <c:if test="${functionMethodInput.arrayFlg}">
                                                                                    <qp:formatText value='${CL_QP_DATATYPE.get(functionMethodInput.dataType.toString())}[]' />
                                                                                </c:if>
                                                                                <c:if test="${!functionMethodInput.arrayFlg}">
                                                                                    <qp:formatText value='${CL_QP_DATATYPE.get(functionMethodInput.dataType.toString())}' />
                                                                                </c:if>
																			</div>
																		</td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											
											<div id="tabsDecision-${status.index}2" name="divOutputLogic" class="tab-pane" style="height: auto;">
												<div class="panel panel-default">
													<div class="panel-body">
														<table class="table table-bordered qp-table-list-none-action" id="tblOutput" name="tblOutput" data-ar-callback="$.qp.functionmaster.formCallback" data-ar-tlevel="1">
															<colgroup>
																<col width="40px"/>
																<col width="360px"/>
																<col width="30%"/>
																<col/>
															</colgroup>
															<thead>
																<tr>
																	<th><qp:message code="sc.sys.0004" /></th>
																	<th><qp:message code="sc.functionmaster.0035" /></th>
																	<th><qp:message code="sc.functionmaster.0036" /></th>
																	<th><qp:message code="sc.functionmaster.0037" /></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach var="functionMethodOutput" items="${functionMethod.functionMethodOutput}" varStatus="statusOutput">
																	<tr data-ar-rgroup="${functionMethodOutput.groupId}" class="ar-dataRow" data-ar-templateid="tblOutput-common-object-template" data-ar-rindex="${functionMethodOutput.itemSeqNo }" data-ar-rgroupindex="${functionMethodOutput.tableIndex }">
																		<td colspan="2" style="width: 400px">
                                                                            <div style="height:100%">
                                                                                <div>
                                                                                    <span  class="ar-groupIndex">${functionMethodOutput.tableIndex }</span>
                                                                                </div>
                                                                                <div class="pull-right" style="height:100%;vertical-align: middle;">
                                                                                    <label class="qp-output-text">${functionMethodOutput.methodOutputName}</label>
                                                                                </div>
                                                                            </div>
                                                                        </td>
																		<td class="word-wrap"><qp:formatText value="${functionMethodOutput.methodOutputCode}" /></td>
																		<td>
																			<div style="text-align: left;">
                                                                                <c:if test="${functionMethodOutput.arrayFlg}">
                                                                                    <qp:formatText value='${CL_QP_DATATYPE.get(functionMethodOutput.dataType.toString())}[]' />
                                                                                </c:if>
                                                                                <c:if test="${!functionMethodOutput.arrayFlg}">
                                                                                    <qp:formatText value='${CL_QP_DATATYPE.get(functionMethodOutput.dataType.toString())}' />
                                                                                </c:if>
																			</div>
																		</td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
                    </td>
                    </tr>
				</c:forEach>
                </table>
                </div>
				</c:if>
			</form:form>
				</div>
	</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>