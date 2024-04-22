<tiles:insertDefinition name="layouts-popup">
    <tiles:putAttribute name="header-name">
      <qp:message code="sc.screendesign.0030" />
    </tiles:putAttribute>
    
    <tiles:putAttribute name="additionalHeading">
    	<jsp:useBean id="random" class="java.util.Random" scope="application" />
    	<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=screendesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/search.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/common.js?r=012"></script>
		<link href="${pageContext.request.contextPath}/resources/app/screendesign/css/style.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/app/screendesign/css/bootstrap.vertical-tabs.min.css" type="text/css" rel="stylesheet" />
		<style>
			#dragDropContent{
				${projectStyle['screenStyle']}
			}
			
			#dragDropContent .panel-default>.panel-heading{
				${projectStyle['panelHeader']}
			}
			
			#dragDropContent .panel-body{
				${projectStyle['panelBody']}
			}
			
			#dragDropContent .qp-table-list{
				${projectStyle['panelListTable']}
			}
			
			#dragDropContent .qp-table-list thead tr th{
				${projectStyle['panelListTh']}
			}
			
			#dragDropContent .result-text{
				${projectStyle['panelListTdText']}
			}
			
			#dragDropContent .result-numeric{
				${projectStyle['panelListTdNumeric']}
			}
			
			#dragDropContent .result-date{
				${projectStyle['panelListTdDate']}
			}
			
			#dragDropContent .result-date-time{
				${projectStyle['panelListTdDateTime']}
			}
			
			#dragDropContent .result-no-number{
				${projectStyle['panelListTdNoNumber']}
			}
			
			#dragDropContent .result-action-column{
				${projectStyle['panelListTdActionColumn']}
			}
			
			#dragDropContent .qp-table-form{
				${projectStyle['panelTableForm']}
			}
			
			#dragDropContent .qp-table-form tr th{
				${projectStyle['panelTableFormTh']}
			}
			
			#dragDropContent .qp-table-form tr td{
				${projectStyle['panelTableFormTd']}
			}
			
			.form-area-content .qp-button-type-design {
				${projectStyle['commonButtonBgColor']}
				${projectStyle['commonButtonTextColor']}
			}
			
			.form-area-content .qp-button-type-design:ACTIVE {
				${projectStyle['commonButtonBgActiveColor']}
			}
			
			.form-area-content .qp-button-type-warning-design {
				${projectStyle['commonButtonDeleteBgColor']}
				${projectStyle['commonButtonDeleteTextColor']}
			}
			
			.form-area-content .qp-button-type-warning-design:ACTIVE {
				${projectStyle['commonButtonDeleteBgActiveColor']}
			}
			
			.form-area-content .qp-button-type-client-design {
				${projectStyle['clientButtonDeleteBgColor']}
				${projectStyle['clientButtonDeleteTextColor']}
			}
			
			.form-area-content .qp-button-type-client-design:ACTIVE {
				${projectStyle['clientButtonDeleteBgActiveColor']}
			}
			
			.qp-item-custom {
		    	border-style:dotted;
		    	border-color: #ff9999
		    }
		</style>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <form:form action="${pageContext.request.contextPath}/screendesign/view" method="POST" modelAttribute="screenDesignForm">
        <c:if test="${notExistFlg ne 1}">
        <c:if test="${!screenDesignForm.error }">
        <form:hidden path="screenId"/>
        <form:hidden path="projectId"/>
        <div class="panel panel-default qp-div-information">
				<div class="panel-heading" ><span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.screendesign.0252"/></span></div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form"
						id="tableDialogFormAutocomplete">
						<colgroup>
							<col width="20%" /><col width="30%" /><col width="20%" /><col width="30%" />
						</colgroup>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0005"></qp:message></th>
							<td><qp:formatText value="${screenDesignForm.screenName }"></qp:formatText></td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0006"></qp:message> </th>
							<td>${module.moduleName}</td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0007"></qp:message></th>
							<td><qp:formatText value="${screenDesignForm.screenCode }"></qp:formatText></td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0028"></qp:message></th>
							<td><qp:formatText value="${screenDesignForm.screenUrlCode }"></qp:formatText></td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0009"></qp:message></th>
							<td>
								<c:choose>
									<c:when test="${screenDesignForm.screenPatternType == 1 }"><qp:message code="sc.screendesign.0019"></qp:message></c:when>
									<c:when test="${screenDesignForm.screenPatternType == 2 }"><qp:message code="sc.screendesign.0021"></qp:message></c:when>
									<c:when test="${screenDesignForm.screenPatternType == 3 }"><qp:message code="sc.screendesign.0030"></qp:message></c:when>
									<c:otherwise><qp:message code="sc.screendesign.0029"></qp:message></c:otherwise>
								</c:choose>
							</td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0031"></qp:message> </th>
							<td>
								<c:choose>
									<c:when test="${screenDesignForm.templateType == 1 }"><qp:message code="sc.screendesign.0142"></qp:message></c:when>
									<c:when test="${screenDesignForm.templateType == 2}"><qp:message code="sc.screendesign.0143"></qp:message></c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0250"/></th>
							<td>
								<c:choose>
									<c:when test="${screenDesignForm.designMode == 1 }">
										<qp:message code="sc.screendesign.0306"/>
									</c:when>
									<c:when test="${screenDesignForm.designMode == 2 }">
										<qp:message code="sc.screendesign.0316"/>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0055"/></th>
							<td>
								<qp:message code="${CL_DESIGN_STATUS.get(screenDesignForm.designStatus.toString())}"/>
								<input type="hidden" name="designStatus" value="${screenDesignForm.designStatus }" />
							</td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0057"></qp:message></th>
							<td>
								<qp:formatRemark value="${screenDesignForm.remark }" />
							</td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.functiondesign.0002"></qp:message> </th>
							<td>${screenDesignForm.functionDesignName}<form:hidden path="functionDesignName"/> </td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<script>
						function deleteScreen(obj) {
							$(obj).closest('form').attr('action', '${pageContext.request.contextPath}/screendesign/delete');
						}
						
						function fixDesignScreen(obj) {
							$(obj).closest('form').attr('action', '${pageContext.request.contextPath}/screendesign/fixdesign');
						}
						function underDesignScreen(obj) {
							$(obj).closest('form').attr('action', '${pageContext.request.contextPath}/screendesign/underdesign');
						}
					</script>
				<c:if test="${screenDesignForm.openOwner eq 1}">
					<input type="hidden" name="openOwner" value="${screenDesignForm.openOwner }">
					<c:if test="${module.status eq 1}">
						<c:if test="${screenDesignForm.designMode == 1}">
							<button type="button" class="btn qp-button-warning qp-dialog-confirm" onclick="deleteScreen(this)" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
							<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/screendesign/prototype?screenId=${f:h(screenDesignForm.screenId)}&sourceForm=1">
								<qp:message code="sc.sys.0006" />
							</a>
						</c:if>
						<c:if test="${screenDesignForm.designMode == 2}">
							<c:if test="${screenDesignForm.designStatus == 1}">
								<button type="button" onclick="fixDesignScreen(this)" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="cl.sys.0010" /></button>
								<button type="button" class="btn qp-button-warning qp-dialog-confirm" onclick="deleteScreen(this)" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
								<a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/screendesign/design?screenId=${f:h(screenDesignForm.screenId)}&sourceForm=1">
									<qp:message code="sc.sys.0006" />
								</a>
							</c:if>
							<c:if test="${screenDesignForm.designStatus == 2}">
								<button type="button" onclick="underDesignScreen(this)" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="cl.sys.0009" /></button>
							</c:if>
						</c:if>
					</c:if>
				</c:if>
				
			</div>
			<div id="loadingScreen"><qp:message code="sc.screendesign.0374"></qp:message> </div>
			<div id="dragDropContent" style="border: 2px; border-style: solid; border-color: #7895CF; min-height: 550px; margin-top: 5px; padding: 4px;visibility: hidden;">
			<%-- <h4><span class="qp-header-main-text"><qp:message code="sc.screendesign.0188"></qp:message></span></h4> --%>
            <c:if test="${not empty screenDesignForm.headerLinkItems && fn:length(screenDesignForm.headerLinkItems) > 0 }">
            <div id="srcgenHeaderLinkPanel" style="text-align: right;">
									<div class="qp-div-action ui-droppable" id="srcgenHeaderLinkArea" style="line-height: 25px;min-height: 27px;">
										<c:forEach var="screenArea" items="${screenDesignForm.areaNonGroup }" varStatus="areaStatus">
											<c:forEach var="headerArea" items="${screenDesignForm.headerLinkItems}" varStatus="status">
												<c:if test="${screenArea.screenAreaId == headerArea.screenAreaId &&  screenArea.areaType == -1}">
													<a style="${headerArea.style}" onmouseover="this.setAttribute('style', '${headerArea.hoverStyle}')" onmouseout="this.setAttribute('style', '${headerArea.style}')" href="#">${headerArea.messageDesign.messageString }</a>
												</c:if>
											</c:forEach>
										</c:forEach>
									</div>
								</div>
								</c:if>
								
								<!-- Screen design area -->
								<div class="ui-sortable" id="srcgenScreen">
								<!--Start load data  -->
								<c:forEach var="screenForm" items="${screenDesignForm.screenForms }" varStatus="formStatus">
									<div class="com-sd-cover ui-sortable form-area-content" style="border-color: white;" id="srcgenScreen">
									<c:forEach var="screenArea" items="${screenDesignForm.screenAreas }" varStatus="areaStatus">
										<c:if test="${screenForm.screenFormId ==  screenArea.screenFormId}">
										${screenArea.startHtml }
										<c:set var="withAreaContent" value="100%" /><c:if test="${not empty screenArea.tblWidthUnit}"><c:set var="withAreaContent" value="${screenArea.tblWidthUnit}" /></c:if><c:set var="alignAreaContent" value="left" />
											<c:if test="${3 == screenArea.alignPositionType}"><c:set var="alignAreaContent" value="right" /></c:if>
											<c:choose>
											<c:when test="${screenArea.areaType == 0 }">
											<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
												<div class="panel panel-default qp-div-information">
													<div class="panel-heading" style="${screenArea.panelStyle}">
														<span aria-hidden="true" class="${screenArea.areaIcon}">&nbsp;</span>
														<span class="pq-heading-text">${screenArea.messageDesign.messageString}</span>
														<input type="hidden" name="formAreaCode" value="${screenArea.areaCode }">
													</div>
														<div class="panel-body">
															<c:if test="${screenArea.areaCustomType == 1}">
																<div class="qp-add-right" style="margin-bottom: 5px;">
																	<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" href="javascript:void(0)" title='<qp:message code="sc.sys.0014" />'></a>
																</div>
															</c:if>
															<table class="table table-bordered qp-table-form" id="srcgenTableId${areaStatus.index}">
																<colgroup>
																	<c:set var="columnIndexColGroup" value="0"></c:set>
																	<c:forTokens var="itemWidth" items="${screenArea.colWidthUnit }" delims="," >
																		<col width="${itemWidth }"></col>
																		<c:set var="columnIndexColGroup" value="${columnIndexColGroup + 1 }"></c:set>
																	</c:forTokens>	
																	<c:if test="${columnIndexColGroup < screenArea.totalCol }">
																		<c:forEach begin="1" end="${screenArea.totalCol - columnIndexColGroup }">
																			<col width="${100/screenArea.totalCol }%"></col>
																		</c:forEach>
																	</c:if>	
																</colgroup>
																<tbody class="ui-sortable">
																	<c:set var="rowIndex" value="0"></c:set>
																	<c:set var="colIndex" value="0"></c:set>
																	<c:set var="col" value="${screenArea.totalCol }" />
																	<c:set var="row" value="${screenArea.totalCol/col }" />
																	<c:if test="${screenArea.totalCol%col != 0 }">
																		<c:set var="row" value="${row + 1 }" />
																	</c:if>
																	
																	<c:set var="colRowSpanItem" value=""></c:set>
																	<c:set var="currentIndex" value=""></c:set>
																	<c:set var="arrColRow" />
																	<c:set var="arrCurrentIndex" />
																	
																	<c:set var="elementGroup" value="0" />
																	<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">
																		<c:if test="${screenItem.screenAreaId == screenArea.screenAreaId }">
																			<c:if test="${colIndex == 0 }">																					
																				<tr index="${rowIndex }">				
																			</c:if>
																			<c:set var="colSpanProperties" value="" />
																			<c:set var="rowSpanProperties" value="" />
																			<c:set var="hasRowColSpan" value="false" />
																			
																			<c:if test="${not empty screenItem.colSpan &&  screenItem.colSpan > 1}">
																				<c:set var="colSpanProperties" value="colspan='${screenItem.colSpan }'" />
																				<c:set var="hasRowColSpan" value="true" />
																			</c:if>
																			
																			<c:if test="${not empty screenItem.rowSpan &&  screenItem.rowSpan > 1}">
																				<c:set var="rowSpanProperties" value="rowspan='${screenItem.rowSpan }'" />
																				<c:set var="hasRowColSpan" value="true" />
																			</c:if>
																			
																			<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<c:set var="isDisable" value=""></c:set>
																			<c:if test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId)}">
																				<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																				<c:set var="isDisable" value="${screenGroup.styleColRowSpan }"></c:set>
																			</c:if>
																				<c:choose>
																					<c:when test="${screenItem.logicalDataType == 20 && screenItem.itemType != 5}">
																						
																						<th index="${colIndex }" style="${isDisable } ${screenArea.headerStyle}" ${colSpanProperties} ${rowSpanProperties} >
																							
																							<c:choose>
																								<c:when test="${empty screenItem.value || empty screenItem.messageDesign }">
																									
																								</c:when>
																								<c:otherwise>
																									<script type="text/javascript">
																										var obj = convertToJson('${screenItem.value }');
																										document.write(returnElementTHPreview(obj));
																									</script>
																								</c:otherwise>
																							</c:choose>
																						</th>
																						<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																					</c:when>
																					<c:otherwise>	
																						<c:if test="${not empty screenItem.groupItemId }">	
																							<c:choose>
																								<c:when test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId) && not empty screenDesignForm.screenGroups[screenItem.groupItemId] }">
																									<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																									<c:if test="${screenItem.itemSeqNo == screenGroup.elementStart }">
																										<td index="${colIndex }" style="${isDisable } ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																											
																									</c:if>
																									<c:choose>
																										<c:when test="${empty screenItem.value || empty screenItem.logicalDataType  }">
																											
																											
																										</c:when>
																										<c:otherwise>
																											<c:choose>
																												<c:when test="${screenDesignForm.screenPatternType == 1 }">
																													<script type="text/javascript">
																														var obj = convertToJson('${screenItem.value }');
																														var html = returnElementTDSearchEntityPreview(obj, '${screenGroup.groupType }');
																														document.write(html);
																													</script>
																												</c:when>
																												<c:otherwise>
																													<script type="text/javascript">
																														var obj = convertToJson('${screenItem.value }');
																														var html = returnElementTDPreview(obj, '${screenGroup.groupType }');
																														document.write(html);
																													</script>
																												</c:otherwise>
																											</c:choose>
																										</c:otherwise>																											
																									</c:choose>
																									<c:if test="${screenItem.itemSeqNo == screenGroup.elementEnd }">																											
																										</td>
																										<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<td index="${colIndex }" style="${isDisable } ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																										
																									</td>
																								</c:otherwise>
																							</c:choose>																							
																						</c:if>																																														
																					</c:otherwise>
																				</c:choose>	
																				<c:if test="${ colIndex == col}">																	
																					</tr>			
																					<c:set var="colIndex" value="0"></c:set>
																					<c:set var="rowIndex" value="${rowIndex + 1 }"></c:set>
																				</c:if>
																			</c:if>	
																		</c:if>																																																							
																	</c:forEach>
																</tbody>
															</table>
															<c:if test="${screenArea.areaCustomType == 1}">
																<div class="qp-add-left">
																	<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"  style="margin-top: 3px;" href="javascript:void(0)"></a>
																</div>
															</c:if>
														</div>
												</div>
												</div>
											</c:when>
											<c:when test="${screenArea.areaType == 1 }">
												 <c:set var="componentRowRemove" value="0" />
											<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
												<div class="panel panel-default qp-div-select">
														<div class="panel-heading" style="${screenArea.panelStyle}">
															<span class="${screenArea.areaIcon}" aria-hidden="true">&nbsp;</span>
															<span class="pq-heading-text">${screenArea.messageDesign.messageString}</span>
															<input type="hidden" name="formAreaCode" value="${screenArea.areaCode }">
														</div>
														<div class="panel-body">
															<c:set var="rowIndex" value="0"></c:set>
															<c:set var="colIndex" value="0"></c:set>
															<c:set var="col" value="${screenArea.totalCol }" />
															<table class="table table-bordered qp-table-list-none-action" id="srcgenTableId${areaStatus.index}">																
																<colgroup>
																	<c:if test="${empty screenArea.fixedRow || screenArea.fixedRow != 1 }">
																		<col width="35px"></col>
																	</c:if>
																	<c:set var="columnIndexColGroup" value="0"></c:set>
																	<c:forTokens var="itemWidth" items="${screenArea.colWidthUnit }" delims="," >
																		<col width="${itemWidth }"></col>
																		<c:set var="columnIndexColGroup" value="${columnIndexColGroup + 1 }"></c:set>
																	</c:forTokens>	
																	<c:if test="${columnIndexColGroup < screenArea.totalCol }">
																		<c:forEach begin="1" end="${screenArea.totalCol - columnIndexColGroup }">
																			<col width="${100/screenArea.totalCol }%"></col>
																		</c:forEach>
																	</c:if>	
																		<c:if test="${(screenArea.areaPatternType == 1 || screenArea.areaPatternType == 2) && screenArea.areaTypeAction == 1 }">
																			<c:if test="${screenArea.fixedRow == 0}">
																				<col width="35px">
																			</c:if>
																		</c:if>
																</colgroup>
																<thead>
																	<c:set var="colRowSpanItem" value=""></c:set>
																	<c:set var="currentIndex" value=""></c:set>
																	<c:set var="arrColRow" />
																	<c:set var="arrCurrentIndex" />
																	<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">	
																		<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<c:if test="${screenItem.logicalDataType == 20 && screenItem.itemType != 5}">
																				<c:set var="colSpanProperties" value="" />
																				<c:set var="rowSpanProperties" value="" />
																				<c:set var="hasRowColSpan" value="false" />
																				
																				<c:if test="${not empty screenItem.colSpan &&  screenItem.colSpan > 1}">
																					<c:set var="colSpanProperties" value="colspan='${screenItem.colSpan }'" />
																					<c:set var="hasRowColSpan" value="true" />
																				</c:if>
																				
																				<c:if test="${not empty screenItem.rowSpan &&  screenItem.rowSpan > 1}">
																					<c:set var="rowSpanProperties" value="rowspan='${screenItem.rowSpan }'" />
																					<c:set var="hasRowColSpan" value="true" />
																				</c:if>
																				<c:set var="isDisable" value=""></c:set>
																		
																				<c:if test="${ colIndex == 0}">
																					<tr index="${rowIndex }" style="${screenArea.rowStyle}">
																						<c:if test="${(empty screenArea.fixedRow || screenArea.fixedRow != 1) && rowIndex == 0 }">
																							<th style="vertical-align: middle;" rowspan="${screenArea.tblHeaderRow }">
																								<qp:message code="sc.sys.0004"></qp:message>
																							</th>
																						</c:if>
																				</c:if>
																				<c:if test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId)}">
																				<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																				<c:set var="isDisable" value="${screenGroup.styleColRowSpan }"></c:set>
																			</c:if>
																				<th style="text-align: left;${isDisable} ${screenArea.headerStyle}" class="align-left" index="${colIndex }" ${colSpanProperties} ${rowSpanProperties} >
																					<c:choose>
																						<c:when test="${empty screenItem.value || empty screenItem.messageDesign}">
																							
																						</c:when>
																						<c:otherwise>																										
																							<script type="text/javascript">
																								var obj = convertToJson('${screenItem.value }');
																								var html = returnElementTHPreview(obj, '${hasRowColSpan}');
																								document.write(html);
																							</script>
																						</c:otherwise>
																					</c:choose>																								
																				</th>
																				<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																				<c:if test="${ colIndex == col }">		
																				<c:if test="${(screenArea.areaPatternType == 1 || screenArea.areaPatternType == 2) && screenArea.areaTypeAction == 1 }">
																					<c:if test="${empty screenArea.fixedRow || screenArea.fixedRow == 0}">
																						<th rowspan="${screenArea.tblComponentRow}" width="35px"></th>
																					</c:if>
																				</c:if>
																					</tr>
																					<c:set var="colIndex" value="0"></c:set>																						
																					<c:set var="rowIndex" value="${rowIndex + 1 }"></c:set>	
																				</c:if>
																			</c:if>	
																		</c:if>																																																									
																	</c:forEach>
																</thead>
																<tbody>																
																	<c:set var="colRowSpanItem" value=""></c:set>											
																	<c:set var="colIndex" value="0"></c:set>	
																	<c:set var="currentIndex" value=""></c:set>
																	<c:set var="arrColRow" />
																	<c:set var="arrCurrentIndex" />		
																	<c:set var="isGroup" value="false" />		
																	<c:set var="rowIndexTemp" value="0" />											
																	<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">	
																		<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<c:if test="${empty screenItem.logicalDataType || (screenItem.logicalDataType != 20) || screenItem.itemType == 5}">
																				<c:set var="isDisable" value=""></c:set>
																				<c:if test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId)}">
																				<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																				<c:set var="isDisable" value="${screenGroup.styleColRowSpan }"></c:set>
																			</c:if>
																				<c:set var="colSpanProperties" value="" />
																				<c:set var="rowSpanProperties" value="" />
																				<c:set var="hasRowColSpan" value="false" />
																				
																				<c:if test="${not empty screenItem.colSpan &&  screenItem.colSpan > 1}">
																					<c:set var="colSpanProperties" value="colspan='${screenItem.colSpan }'" />
																					<c:set var="hasRowColSpan" value="true" />
																				</c:if>
																				
																				<c:if test="${not empty screenItem.rowSpan &&  screenItem.rowSpan > 1}">
																					<c:set var="rowSpanProperties" value="rowspan='${screenItem.rowSpan }'" />
																					<c:set var="hasRowColSpan" value="true" />
																				</c:if>
																		
																		<c:set var="styleFirstCol" value="" ></c:set>
																		<c:if test="${ colIndex == 0 && !isGroup}">
																				<c:set var="styleFirstCol" value="align-left" ></c:set>
																						<tr index="${rowIndex }" style="${screenArea.rowStyle}">
																							<c:if test="${(empty screenArea.fixedRow || screenArea.fixedRow != 1) && rowIndexTemp == 0}">
																								<c:set var="rowIndexTemp" value="1" />
																								<td rowspan="${screenArea.tblComponentRow }">
																									1
																								</td>
																							</c:if>
																					</c:if>
																				<c:if test="${not empty screenItem.groupItemId }">	<c:set var="isGroup" value="true" />
																						<c:choose><c:when test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId) && not empty screenDesignForm.screenGroups[screenItem.groupItemId] }">
																								<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																								<c:if test="${screenItem.itemSeqNo == screenGroup.elementStart }">
																								<c:set var="styleGroup" value="0" ></c:set>
																									<c:if test="${screenItem.itemGroupType == 1 }"><c:set var="styleGroup" value=" enableGroupTd" ></c:set></c:if>
																									<c:set var="styleFirstColumn" value=""></c:set>
																									<c:if test="${colIndex == 0 }">
																										<c:set var="styleFirstColumn" value="text-align: left;"></c:set>	
																									</c:if>
																									<c:set var="fieldFormat" value=" ${screenItem.fieldStyle}"></c:set>
																									<c:if test="${screenGroup.elementStart != screenGroup.elementEnd }"><c:set var="fieldFormat" value=" result-text"></c:set></c:if>
																									<td class='${styleFirstCol } ${styleGroup } ${screenArea.inputStyle} ${fieldFormat}'  index="${colIndex }" style="${styleFirstColumn} ${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																								</c:if>
																								<c:choose>
																									<c:when test="${empty screenItem.value || empty screenItem.logicalDataType || screenItem.logicalDataType < 0  }">
																										<c:if test="${screenItem.itemGroupType == 0 || empty screenItem.logicalDataType || screenItem.logicalDataType < 0 }">
																											<div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																										</c:if>
																									</c:when>
																									<c:otherwise>	
																										<script type="text/javascript">
																											var obj = convertToJson('${screenItem.value }');
																											var html = returnElementTDPreview(obj, '${screenGroup.groupType }');
																											document.write(html);
																										</script>
																									</c:otherwise>
																								</c:choose>
																								<c:if test="${screenItem.itemSeqNo == screenGroup.elementEnd }">
																									</td>
																									<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																									<c:set var="isGroup" value="false" />
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<td index="${colIndex }" style="${isDisable } ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																									<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																									<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }"><div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																								</td>
																							</c:otherwise>
																						</c:choose>
																					</c:if>
																				<c:if test="${ colIndex == col}">
																						<c:if test="${(screenArea.areaPatternType == 1 || screenArea.areaPatternType == 2) && screenArea.areaTypeAction == 1 }">
																							<c:if test="${(empty screenArea.fixedRow || screenArea.fixedRow == 0) and componentRowRemove == 0}">
																								<td rowspan="${screenArea.tblComponentRow}" class="result-action-column" style='text-align: center;'>
																									<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" style="margin-top: 3px;" href="javascript:void(0)"></a>
																									<c:set var="componentRowRemove" value="${componentRowRemove + 1 }" />
																								</td>
																							</c:if>
																						</c:if>
																					</tr>			
																					<c:set var="colIndex" value="0"></c:set>
																					<c:set var="rowIndex" value="${rowIndex + 1 }"></c:set>
																				</c:if>
																			</c:if>
																		</c:if>
																	</c:forEach>
																</tbody>
															</table>
															<c:if test="${screenArea.areaTypeAction == 1 && (empty screenArea.fixedRow || screenArea.fixedRow == 0) }">
																<div class="qp-add-left">
																	<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
																</div>
															</c:if>
														</div>
													</div>
													</div>
											</c:when>
											<c:when test="${screenArea.areaType == 3 }">
												<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
													<div class="panel panel-default qp-div-information">
														<div class="panel-heading" style="${screenArea.panelStyle}">
															<span aria-hidden="true" class="${screenArea.areaIcon}">&nbsp;</span>
															<span class="qp-heading-text">
																<span>${screenArea.messageDesign.messageString}</span>
																<input type="hidden" name="formAreaCode" value="${screenArea.areaCode }">
															</span>
														</div>
														<div class="panel-body">
															<div class="section-area ui-droppable ui-sortable">
																<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">																	
																		<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<script type="text/javascript">
																			var style = 'padding: 2px; ';
																			var align = '${screenArea.elementPositionType }';//0:left, 1 right
																			var displayType = '${screenArea.elementDipslayType }';//0: horial, 1: vertical
																			
																			if (align == "0") {
																				style += "float: left;";
																			} else if (align == "1") {
																				style += "float: right;";
																			} else {
																				style += "float: left;";
																			}
																			
																			if (displayType == "1") {
																				style += "clear: both;";
																			}
																			
																			var width = '${screenItem.itemWidthUnit}';
																			if (width.length > 0) {
																				style += 'width: ' + width + ';';
																			}
																			
																			var obj = convertToJson('${screenItem.value }');
																			
																			if (obj.datatype != 10) {
																				style += '';
																			}
																			var html = returnElemenActionSection(obj, style);
																			document.write(html);
																			</script>
																		</c:if>																																																								
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
											</c:when>
											<c:when test="${screenArea.areaType == 2 }">
											<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
												<div class="qp-div-action">
													<div class="action-area ui-droppable">
														<input type="hidden" name="formAreaCode" value="${screenArea.areaCode }">
														<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">																	
																		<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<c:set var="displayType" value=""></c:set>
																			<c:set var="elementDisplayType" value=""></c:set>
																			<c:if test="${screenArea.elementDipslayType == 1 }" >
																				<c:set var="displayType" value="clear: both;"></c:set>
																			</c:if>
																			
																			<c:choose>
																				<c:when test="${screenArea.elementPositionType == 0 }">
																					<c:set var="elementDisplayType" value="float: left; padding-right: 4px"></c:set>	
																				</c:when>
																				<c:when test="${screenArea.elementPositionType == 1 }">
																					<c:set var="elementDisplayType" value="float: right;padding-left: 4px"></c:set>
																				</c:when>
																				<c:otherwise>
																					<c:set var="elementDisplayType" value="float: right;padding-left: 4px"></c:set>
																				</c:otherwise>
																			</c:choose>
																			
																			
																			<script type="text/javascript">
																			var style = 'padding: 2px; ';
																			var align = '${screenArea.elementPositionType }';//0:left, 1 right
																			var displayType = '${screenArea.elementDipslayType }';//0: horial, 1: vertical
																			
																			if (align == "0") {
																				style += "float: left;";
																			} else if (align == "1") {
																				style += "float: right;";
																			} else {
																				style += "float: right;";
																			}
																			
																			if (displayType == "1") {
																				style += "clear: both;";
																			}
																			
																			/* var width = '${screenItem.itemWidthUnit}';
																			if (width.length > 0) {
																				style += 'width: ' + width + ';';
																			} */
																			
																			var obj = convertToJson('${screenItem.value }');
																			
																			if (obj.datatype != 10) {
																				style += '';
																			}
																			var html = returnElemenActionSection(obj, style);
																			document.write(html);
																			</script>
																			
																		</c:if>																																																								
																</c:forEach>
													</div>
												</div>
												</div>
											</c:when>
											
											<c:when test="${screenArea.areaType == 4 }">
												<div class="areaContent " style="width: ${withAreaContent}; float:${alignAreaContent};">
													<div class="panel panel-default">
														<div class="panel-heading" style="background-color:#ebebe1;${screenArea.panelStyle}">
															<span class="glyphicon" aria-hidden="true"></span>
															<c:set var="style" value="${screenArea.panelStyle}" />
															<span class="qp-heading-text"><a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;color: #337AB7;" class="" title="Move"></a>
																<c:if test="${screenArea.panelStyle eq null or screenArea.panelStyle eq '' }">
																	<c:set var="style" value="color: #337AB7" />
																</c:if>
																<span style="${style}"><qp:message code="sc.screendesign.0434" /></span>
															</span>
														</div>
													</div>
												</div>
											</c:when>
										</c:choose>
										${screenArea.endHtml }
										</c:if>										
									</c:forEach>
									<div class="form-content">
										<input type="hidden" name="screenFormFormActionCode" value="${screenForm.formCode }" ><input type="hidden" name="screenFormTab" value='${ screenForm.tabValue}' />
									</div>
									</div>
								</c:forEach>
									<div id="srcgenAreaTemplate" style="line-height: 24px; min-height: 27px;" class="ui-droppable">&nbsp;</div>
								</div>
			</div>
			</c:if>
			</c:if>
			<c:if test="${!empty screenDesignForm.listScreenChangeParameter}">
				<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.module.0028" /></span>
						</div>
						<table class="table table-bordered qp-table-list-none-action">
			                   <colgroup>
			                       <col/>
			                       <col width="25%" />
			                       <col width="35%" />
			                       <col width="20%" />
			                   </colgroup>
			                   <thead>
			                       <tr>
			                           <th><qp:message code="sc.sys.0004" /></th>
			                           <th><qp:message code="sc.screendesign.0005" /></th>
			                           <th><qp:message code="sc.screendesign.0007" /></th>
			                           <th><qp:message code="sc.sys.0055" /></th>
			                       </tr>
			                   </thead>
			                   <c:forEach var="screenList" items="${screenDesignForm.listScreenChangeParameter}" varStatus="rowStatus">
			                       <tr>
			                           <td>${rowStatus.count}</td>
			                           <td class="qp-output-text"><qp:formatText value="${screenList.messageDesign.messageString}"/></td>
			                           <td class="qp-output-text"><qp:formatText value="${screenList.screenCode}"/></td>
			                           <td class="qp-output-text"><qp:message code="${CL_DESIGN_STATUS.get(screenList.designStatus.toString())}"  /></td>
			                       </tr>
			                   </c:forEach>
			                   <c:if test="${empty screenDesignForm.listScreenChangeParameter}">
									<tr>
										<td colspan="4"><qp:message code="inf.sys.0013"/></td>
									</tr>
							   </c:if>
			               </table>
	                </div>
			</c:if>
		<script>
			$(document).ready(function() {
        		//initTab(true);
        		
        		$("#loadingScreen").hide();
        		$("#dragDropContent").css("visibility", "");
        		
			});
        </script>
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>