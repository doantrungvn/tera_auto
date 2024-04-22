<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.sqldesign.0030" />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
		</script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/sqldesign.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=autocomplete_sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div id="qp-sqldesign">
			<form:form method="post" modelAttribute="sqlDesignDesignForm"
				action="${pageContext.request.contextPath}/sqldesign/deleteConfirm">
				<c:set var="designForm" value="${sqlDesignDesignForm }"
					scope="request"></c:set>
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>"
					element="div" cssStyle="" />
				<c:if
					test="${not empty sqlDesignDesignForm.sqlDesignForm.sqlDesignId}">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message
									code="sc.sqldesign.0021" /></span>
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
									<th><label><qp:message code="sc.sqldesign.0010"></qp:message></label></th>
									<td><qp:formatText
											value="${designForm.sqlDesignForm.sqlDesignName }"></qp:formatText></td>
									<th><label><qp:message code="sc.sqldesign.0011"></qp:message></label></th>
									<td><qp:formatText
											value="${designForm.sqlDesignForm.sqlDesignCode }"></qp:formatText></td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.sqldesign.0017"></qp:message></label></th>
									<td><qp:formatText
											value="${CL_SQL_TYPE.get(designForm.sqlDesignForm.designType)}"></qp:formatText>
									</td>
									<th><label><qp:message code="sc.module.0007"></qp:message></label></th>
									<td><qp:formatText
											value="${designForm.sqlDesignForm.moduleIdAutocomplete }"></qp:formatText>
									</td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.sys.0055"></qp:message></label></th>
									<td><qp:message
											code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus.toString())}" /></td>
									<th><label><qp:message code="sc.sqldesign.0020"></qp:message></label></th>
									<td><qp:formatText
											value="${CL_SQL_SQLPATTERN.get(sqlDesignDesignForm.sqlDesignForm.sqlPattern)}" />
										<form:hidden path="sqlDesignForm.sqlPattern" /></td>
								</tr>
								<tr>
									<th><label><qp:message code="sc.sys.0028"></qp:message></label></th>
									<td><qp:formatRemark value="${designForm.sqlDesignForm.remark }" /></td>
									<th></th>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
                    <div class="form-inline form-group">
                    <c:if test="${designForm.openOwner eq '1' }">
                        <div class="qp-div-action">
                            <form:hidden path="sqlDesignForm.sqlDesignId" />
                            <form:hidden path="sqlDesignForm.updatedDate" />
                            <input type="hidden" name="sqlDesignForm.designStatus"
                                value="${designForm.sqlDesignForm.designStatus}" />
                            <form:hidden path="actionDelete" value="false" />
                            <qp:authorization permission="changeDesignStatus">
                                <c:choose>
                                    <c:when test="${designForm.sqlDesignForm.designStatus eq '1'}">
                                        <button type="button" style="background-color: #419641"
                                            class="btn qp-button-warning qp-dialog-confirm"
                                            messageId="inf.sys.0036">
                                            <qp:message code="${CL_DESIGN_STATUS.get('2')}"></qp:message>
                                        </button>
                                    </c:when>
                                    <c:when test="${designForm.sqlDesignForm.designStatus eq '2'}">
                                        <qp:authorization permission="changeDesignStatus">
                                            <button type="button" style="background-color: #419641"
                                                class="btn qp-button-warning qp-dialog-confirm"
                                                messageId="inf.sys.0036">
                                                <qp:message code="${CL_DESIGN_STATUS.get('1')}"></qp:message>
                                            </button>
                                        </qp:authorization>
                                    </c:when>
                                </c:choose>
                            </qp:authorization>
                            <c:if test="${designForm.sqlDesignForm.designStatus eq '1'}">
                                <qp:authorization permission="sqldesignDelete">
                                	
										<div class="checkbox">
											<label> <form:checkbox path="showImpactFlag" />
												<qp:message code="sc.sys.0097" /></label>
										</div>
										 <button type="button"
                                        class="btn qp-button-warning qp-dialog-confirm"
                                        messageId="inf.sys.0014" onclick="$.qp.common.setFlag()"
                                        name="openOwner" value="1">
                                        <qp:message code="sc.sys.0008" />
                                    </button>	            
                                </qp:authorization>
                                <qp:authorization permission="sqldesignModify">
                                    <a
                                        class="btn btn-success qp-link-button qp-link-popup-navigate"
                                        href="${pageContext.request.contextPath}/sqldesign/modify?sqlDesignForm.sqlDesignId=${f:u(designForm.sqlDesignForm.sqlDesignId)}&mode=1">
                                        <qp:message code="sc.sys.0006"></qp:message>
                                    </a>
                                </qp:authorization>
                            </c:if>
                        </div>
                    </c:if>
                    </div>
					<ul class="nav nav-tabs">
						<li><a href="#tab-input" data-toggle="tab"
							style="font: bold;"><qp:message code='sc.sqldesign.0003'></qp:message></a></li>
						<li><a href="#tab-output" data-toggle="tab"
							style="font: bold;"><qp:message code='sc.sqldesign.0004'></qp:message></a></li>
						<li class="active"><a href="#tab-sql-design" data-toggle="tab" style="font: bold;"><qp:message
									code='sc.sqldesign.0009'></qp:message></a>
						</li>
					</ul>
					<div class="tab-content">
						
						<div id="tab-input" class="tab-pane">
							<table class="table table-bordered qp-table-list-none-action"
								id="inputForm">
								<colgroup>
									<col />
									<col width="40px" />
									<col width="40px" />
									<col />
									<col width="20%" />
									<col width="20%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
										<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
										<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${designForm.inputForm }" var="item"
										varStatus="status">
										<tr data-ar-rgroup="${item.groupId }"
											data-ar-rgroupindex="${f:h(item.groupIndex) }"
											class="ar-dataRow">
											<td colspan="4" class="qp-none-padding">
												<div style="height: 100%">
													<div>
														<span class="ar-groupIndex">${item.groupIndex }</span>
													</div>
													<div class="pull-right"
														style="height: 100%; vertical-align: middle; text-align: left;">
														<span>${f:h(item.sqlDesignInputName)}</span>
													</div>
												</div>
											</td>
											<td>${f:h(item.sqlDesignInputCode)}</td>
											<td>${f:h(CL_SQL_DATATYPE.get(item.dataType))}</span> <c:if
													test="${designForm.inputForm[status.index].isArray }">
												[]
											</c:if>
											</td>
										</tr>
									</c:forEach>
									<c:if test="${empty designForm.inputForm}">
										<tr>
											<td colspan="6"><qp:message code="inf.sys.0013"></qp:message>
											</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<div id="tab-output" class="tab-pane">
							<table style="width: 30%" class="table table-borderless">
								<colgroup>
									<col width="30%" />
									<col />
								</colgroup>
								<tbody>
									<tr>
										<td><qp:message code='sc.sqldesign.0022'></qp:message></td>
										<td><span class="form-control qp-form-control-label">${f:h(CL_SQL_RETURNTYPE.get(designForm.sqlDesignForm.returnType.toString())) }</span>
										</td>
									</tr>
								</tbody>
							</table>
							<table class="table table-bordered qp-table-list-none-action"
								id="outputForm">
								<colgroup>
									<col />
									<col width="40px" />
									<col width="40px" />
									<col />
									<col width="20%" />
									<col width="20%" />
									<col width="20%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
										<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
										<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
										<th><qp:message code="sc.sqldesign.0029"></qp:message></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${designForm.outputForm }" var="item"
										varStatus="status">
										<tr data-ar-rgroup="${item.groupId }"
											data-ar-rgroupindex="${f:h(item.groupIndex) }"
											class="ar-dataRow">
											<td colspan="4" class="qp-none-padding">
												<div style="height: 100%">
													<div>
														<span class="ar-groupIndex">${item.groupIndex }</span>
													</div>
													<div class="pull-right"
														style="height: 100%; vertical-align: middle; text-align: left;">
														<span>${f:h(item.sqlDesignOutputName)}</span>
													</div>
												</div>
											</td>
											<td>${f:h(item.sqlDesignOutputCode)}</td>
											<td>${f:h(CL_SQL_DATATYPE.get(item.dataType))} <c:if
													test="${designForm.outputForm[status.index].isArray }">
												[]
											</c:if>
											</td>
											<td>${f:h(item.mappingColumnAutocomplete)}</td>
										</tr>
									</c:forEach>
									<c:if test="${empty designForm.outputForm}">
										<tr>
											<td colspan="7"><qp:message code="inf.sys.0013"></qp:message>
											</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
							<c:if test="${designForm.sqlDesignForm.sqlPattern eq '0'}">
								<div class="panel panel-default qp-div-search-result">
									<div class="panel-heading">
										<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
										<span class="qp-heading-text"> <qp:message
												code="sc.autocomplete.0041"></qp:message>
										</span>
									</div>
									<div class="panel-body">
										<table class="table table-borderless qp-table-form qp-table-none-collapse"
											id="fromForm">
											<colgroup>
												<col width="182px" />
												<col />
												<col width="35px" />
											</colgroup>
											<tr>
												<td class="qp-table-cell-round${designForm.fromForm[0].tableMissingFlag eq '1'?' qp-missing':'' }">
													<span style="width: 78%; margin-left: 8px;" class="pull-left">
														${f:h(designForm.fromForm[0].tableIdAutocomplete)} </span></td>
											</tr>
											<c:if
												test="${not empty designForm.fromForm[0].joinTableIdAutocomplete }">
												<c:forEach items="${designForm.fromForm}" varStatus="status">
													<tr data-ar-rindex="${status.index }">
														<td colspan="2">
															<div class="qp-div-highlight-border">
																<table
																	class="table table-borderless join-conditions-table qp-table-none-collapse">
																	<colgroup>
																		<col width="18%" />
																		<col width="30px" />
																		<col />
																	</colgroup>
																	<tr>
																		<td style="vertical-align: top;" class="qp-table-cell-round${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }">
																			<span style="width: 88%; margin-top: 15px">
																					${f:h(designForm.fromForm[status.index].joinTableIdAutocomplete)}
																			</span>
																		</td>
																		<td style="vertical-align: top; padding-top: 20px"
																			rowspan="2" class="text-center"><b
																			class="glyphicon glyphicon-eye-open  joinColumnsFormToggle qp-link-toggle"></b></td>
																		<td rowspan="2" colspan="3">
																			<div style="display: none">
																				<div>
																					<label> <c:if
																							test="${designForm.fromForm[status.index ].joinType eq '1' }">
																							<img
																								src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_INNER_JOIN_48.png">
																						</c:if> <c:if
																							test="${designForm.fromForm[status.index ].joinType eq '2' }">
																							<img
																								src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_LEFT_JOIN_48.png">
																						</c:if> <c:if
																							test="${designForm.fromForm[status.index ].joinType eq '3' }">
																							<img
																								src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_RIGHT_JOIN_48.png">
																						</c:if> <c:if
																							test="${designForm.fromForm[status.index ].joinType eq '4' }">
																							<img
																								src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_FULL_JOIN_48.png">
																						</c:if> <c:if
																							test="${designForm.fromForm[status.index ].joinType eq '5' }">
																							<img
																								src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_CROSS_JOIN_48.png">
																						</c:if>
																					</label>
																				</div>
																				<table class="table table-borderless qp-table-none-collapse"
																					id="joinPairTable">
																					<colgroup>
																						<col width="20%" />
																						<col width="20%" />
																						<col width="20%" />
																						<col width="20%" />
																						<col width="20%" />
																					</colgroup>
																					<c:forEach
																						items="${designForm.fromForm[status.index].joinColumnsForm}"
																						varStatus="nestedStatus">
																						<tr data-ar-rindex="${nestedStatus.index }"
																							style="border-bottom: solid 1.5px rgba(94, 92, 113, 0.15);">
																							<td class="qp-table-cell-round${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableMissingFlag eq '1'?' qp-missing':'' }">
																								<span>${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableIdAutocomplete)}</span>
																							</td>
																							<td class="qp-table-cell-round${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnMissingFlag eq '1'?' qp-missing':'' }">
																								<span>${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnIdAutocomplete) }</span>
																							</td>
																							<td class="qp-table-cell-round text-center">
																								<span><qp:message code="${CL_SQL_OPERATOR.get(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].operatorCode) }"></qp:message></span>
																							</td>
																							<td class="qp-table-cell-round${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }">
																								<span>${f:h(designForm.fromForm[status.index].joinTableIdAutocomplete)}</span>
																							</td>
																							<td class="qp-table-cell-round${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnMissingFlag eq '1'?' qp-missing':'' }">
																								<span>${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnIdAutocomplete) }</span>
																							</td>
																						</tr>
																					</c:forEach>
																				</table>
																			</div>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</table>
									</div>
								</div>
								<div class="panel panel-default qp-div-search-result">
									<div class="panel-heading">
										<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
										<span class="qp-heading-text"><qp:message
												code="sc.autocomplete.0045" /></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table" id="selectForm"
											style="margin-top: 10px">
											<colgroup>
												<col />
												<col width="30%" />
												<col width="30%" />
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.autocomplete.0016" /></th>
													<th><qp:message code="sc.autocomplete.0017" /></th>
													<th><qp:message code="sc.autocomplete.0015" /></th>
												</tr>
											</thead>
											<c:forEach items="${designForm.selectForm}"
												varStatus="status">
												<c:if
													test="${designForm.selectForm[status.index].isSelected}">
													<tr>
														<td><span
															class="${designForm.selectForm[status.index].tableMissingFlag eq '1'?'form-control qp-form-control-label qp-missing':'' }">
																${f:h(designForm.selectForm[status.index].tableIdAutocomplete) }
														</span></td>
														<td><span
															class="${designForm.selectForm[status.index].columnMissingFlag eq '1'?'form-control qp-form-control-label qp-missing':'' }">
																${f:h(designForm.selectForm[status.index].columnIdAutocomplete) }
														</span></td>
														<td>
															${CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.selectForm[status.index].functionCode)}
														</td>
													</tr>
												</c:if>
											</c:forEach>

										</table>
									</div>
								</div>
								<c:if test="${not empty designForm.whereForm}">
									<div class="panel panel-default qp-div-search-result">
										<div class="panel-heading">
											<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="qp-heading-text"><qp:message
													code="sc.autocomplete.0046"></qp:message></span>
										</div>
										<div class="panel-body">
											<table class="table table-borderless" id="whereForm"
												data-ar-callback="setArgumentForAllTableAC">
												<colgroup>
													<col>
													<col width="20px">
												</colgroup>
												<c:forEach items="${designForm.whereForm}"
													varStatus="status">
													<tr>
														<td>
															<div class="qp-div-highlight-border">
																<table class="table table-borderless qp-table-form qp-table-none-collapse">
																	<colgroup>
																		<col width="15%" />
																		<col width="15px" />
																		<col width="35%" />
																		<col width="15%" />
																		<col />
																		<col width="15px" />
																	</colgroup>
																	<tr>
																		<td class="qp-table-cell-round text-center" rowspan="3"><c:if
																				test="${not empty designForm.whereForm[status.index].logicCode.toString()}">
																				<span>
																					${f:h(CL_SQL_COMBINING_OPERATOR.get(designForm.whereForm[status.index].logicCode.toString())) }
																				</span>
																			</c:if></td>
																		<td rowspan="3"><label
																			class="qp-open-parenthesis"></label></td>
																		<td class="qp-table-cell-round${designForm.whereForm[status.index].leftTableMissingFlag eq '1'?' qp-missing':'' }">
																			<span>
																				${f:h(designForm.whereForm[status.index].leftTableIdAutocomplete)}
																		</span></td>
																		<td class="qp-table-cell-round text-center">
																			<span>${f:h(CL_SQL_CONDITION_TYPE.get(designForm.whereForm[status.index].conditionType.toString())) }</span>
																		</td>
																		<c:choose>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='1' }">
																				<td class="qp-table-cell-round${designForm.whereForm[status.index].rightTableMissingFlag eq '1'?' qp-missing':'' }">
																					<span>${f:h(designForm.whereForm[status.index].rightTableIdAutocomplete)}</span>
																				</td>
																			</c:when>
																			<c:otherwise>
																				<td>
																				</td>
																			</c:otherwise>
																		</c:choose>
																		<td rowspan="3"><label
																			class="qp-close-parenthesis"></label></td>
																	</tr>
																	<tr>
																		<td class="qp-table-cell-round${designForm.whereForm[status.index].leftColumnMissingFlag eq '1'?' qp-missing':'' }">
																			<span>${f:h(designForm.whereForm[status.index].leftColumnIdAutocomplete)}</span>
																		</td>
																		<td class="qp-table-cell-round text-center">
																			<span>
																				<qp:message code="${CL_SQL_OPERATOR.get(designForm.whereForm[status.index].operatorCode.toString())}"></qp:message>
																			</span>
																		</td>
																		<td class="qp-table-cell-round">
																			<c:choose>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='1' }">
																					<span
																						class="${designForm.whereForm[status.index].rightColumnMissingFlag eq '1'?' qp-missing':'' }">
																						${f:h(designForm.whereForm[status.index].rightColumnIdAutocomplete)}
																					</span>
																				</c:when>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='0' }">
																					<c:choose>
																						<c:when
																							test="${designForm.whereForm[status.index].operatorCode=='7' }">
																							<span
																								class="pull-left text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].value) }
																							</span>
																							<div class="qp-separator-from-to">~</div>
																							<span
																								class="pull-right text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].value2) }
																							</span>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when
																									test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																									<span
																										class="text-center">
																										${f:h(designForm.whereForm[status.index ].value) }
																									</span>
																								</c:when>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='2' }">
																					<c:choose>
																						<c:when
																							test="${designForm.whereForm[status.index].operatorCode=='7' }">
																							<span
																								class="pull-left text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].displayArg) }
																							</span>
																							<div class="qp-separator-from-to">~</div>
																							<span
																								class="pull-right text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].displayArg2) }
																							</span>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when
																									test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																									<span
																										class="">
																										${f:h(designForm.whereForm[status.index ].displayArg) }
																									</span>
																								</c:when>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																			</c:choose></td>
																	</tr>
																	<c:if
																		test="${not empty designForm.whereForm[status.index ].functionCode }">
																		<tr>
																			<td><span
																				class="form-control qp-form-control-label">
																					${f:h(CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.whereForm[status.index ].functionCode)) }
																			</span></td>
																		</tr>
																	</c:if>
																</table>
															</div>
														</td>
														<td><form:checkbox cssStyle="display:none"
																path="whereForm[${status.index }].groupType" /></td>
													</tr>
												</c:forEach>
												<c:if test="${empty designForm.whereForm}">
													<tr>
														<td colspan="2"><qp:message code="inf.sys.0013"></qp:message>
														</td>
													</tr>
												</c:if>
											</table>
										</div>
									</div>
								</c:if>
								<c:if test="${not empty designForm.orderByForm}">
									<div class="panel panel-default qp-div-search-result">
										<div class="panel-heading">
											<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="qp-heading-text"><qp:message
													code="sc.autocomplete.0044"></qp:message></span>
										</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table" id="orderByForm"
												data-ar-callback="setArgumentForAllTableAC">
												<colgroup>
													<col width="50%" />
													<col width="50%" />
												</colgroup>
												<thead>
													<tr>
														<th><qp:message code="sc.sqldesign.0048"></qp:message></th>
														<th><qp:message code="sc.sqldesign.0049"></qp:message></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${designForm.orderByForm}"
														varStatus="status">
														<tr>
															<td><qp:formatText
																	value="${designForm.orderByForm[status.index].tableColumnAutocomplete}"></qp:formatText>
															</td>
															<td><qp:formatText
																	value="${CL_SQL_ORDER_DIRECTION.get(designForm.orderByForm[status.index].orderType) }"></qp:formatText>
															</td>
														</tr>
													</c:forEach>
													<c:if test="${empty designForm.orderByForm}">
														<tr>
															<td colspan="2"><qp:message code="inf.sys.0013"></qp:message>
															</td>
														</tr>
													</c:if>
												</tbody>
											</table>
											<div class="qp-add-left"></div>
										</div>
									</div>
								</c:if>
							</c:if>
							<c:if test="${designForm.sqlDesignForm.sqlPattern ne '0'}">
								<c:if test="${not empty designForm.intoForm}">
									<div class="panel panel-default qp-div-search-result">
										<div class="panel-heading">
											<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="qp-heading-text"><qp:message
													code="sc.sqldesign.0032" /></span>
										</div>
										<div class="panel-body">
											<table class="table table-borderless qp-table-form"
												id="intoForm">
												<colgroup>
													<col width="265px">
													<col>
													<col width="35px">
												</colgroup>
												<tbody>
													<tr data-ar-rindex="0">
														<td colspan="2">
															<div class="qp-div-highlight-border">
																<table
																	class="table table-borderless join-conditions-table qp-table-none-collapse"
																	data-ar-tlevel="0"
																	data-ar-callback="$.qp.sqlbuilder.fromFormCallback">
																	<colgroup>
																		<col width="25%">
																		<col width="5%">
																		<col width="25%">
																		<col width="8%">
																		<col width="25%">
																	</colgroup>
																	<tbody>
																		<tr>
																			<td class="qp-table-cell-round"><span>
																					${f:h(designForm.intoForm.tableIdAutocomplete) } </span>
																					<span name="intoForm.tableId" style="display: none">${f:h(designForm.intoForm.tableId)}</span>
																			</td>
																					
																		</tr>
																	</tbody>
																</table>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</c:if>
								<c:if
									test="${not empty designForm.valueForm and designForm.sqlDesignForm.sqlPattern ne '3'}">
									<div class="panel panel-default qp-div-search-result">
										<div class="panel-heading">
											<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="qp-heading-text"><qp:message
													code="sc.sqldesign.0034"></qp:message></span>
										</div>
										<div class="panel-body">
											<table class="table table-borderless qp-table-form qp-table-none-collapse"
												id="valueForm"
												data-ar-callback="$.qp.sqlbuilder.valueFormCallback"
												style="width: 50%">
												<colgroup>
													<col />
													<col width="3%" />
													<col />
													<col width="35px" />
												</colgroup>
												<c:forEach items="${designForm.valueForm}"
													varStatus="status">
													<tr>
														<td class="qp-table-cell-round"><span>
																${f:h(designForm.valueForm[status.index].columnIdAutocomplete)}
														</span></td>
														<td><qp:message code="sc.sqldesign.0035"></qp:message>
														</td>
														<td class="qp-table-cell-round"><span>
																<c:choose>
																	<c:when test="${designForm.valueForm[status.index].valueType == 0}">
																		${f:h(designForm.valueForm[status.index].displayParameter)}
																	</c:when>
																	<c:when test="${designForm.valueForm[status.index].valueType == 1}">
																		${f:h(designForm.valueForm[status.index].parameter)}
																	</c:when>
																	<c:otherwise>
																		<span name="valueForm[${status.index}].parameter">${f:h(designForm.valueForm[status.index].parameter)}</span>
																	</c:otherwise>
																</c:choose>
																
														</span></td>
													</tr>
												</c:forEach>
											</table>
										</div>
									</div>
								</c:if>
								<c:if
									test="${not empty designForm.whereForm and designForm.sqlDesignForm.sqlPattern ne '1'}">
									<div class="panel panel-default qp-div-search-result">
										<div class="panel-heading">
											<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="qp-heading-text"><qp:message
													code="sc.autocomplete.0046"></qp:message></span>
										</div>
										<div class="panel-body">
											<table class="table table-borderless" id="whereForm"
												data-ar-callback="setArgumentForAllTableAC">
												<colgroup>
													<col>
													<col width="20px">
												</colgroup>
												<c:forEach items="${designForm.whereForm}"
													varStatus="status">
													<tr>
														<td>
															<div class="qp-div-highlight-border">
																<table class="table table-borderless qp-table-form qp-table-none-collapse">
																	<colgroup>
																		<col width="15%" />
																		<col width="15px" />
																		<col width="35%" />
																		<col width="15%" />
																		<col />
																		<col width="15px" />
																	</colgroup>
																	<tr>
																		<c:choose>
																			<c:when test="${not empty designForm.whereForm[status.index].logicCode.toString()}">
																				<td class="qp-table-cell-round" rowspan="3">
																				<span
																					class="text-center">
																					${f:h(CL_SQL_COMBINING_OPERATOR.get(designForm.whereForm[status.index].logicCode.toString())) }
																				</span>
																				</td>
																			</c:when>
																			<c:otherwise>
																				<td rowspan="3"></td>
																			</c:otherwise>
																		</c:choose>
																		<td rowspan="3"><label
																			class="qp-open-parenthesis"></label></td>
																		<td class="qp-table-cell-round"><span >
																				${f:h(designForm.whereForm[status.index].leftTableIdAutocomplete)}
																		</span></td>
																		<td class="qp-table-cell-round text-center"><span>
																				${f:h(CL_SQL_CONDITION_TYPE.get(designForm.whereForm[status.index].conditionType.toString())) }
																		</span></td>
																		<c:choose>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='1' }">
																					<td class="qp-table-cell-round">
																					<span>
																						${f:h(designForm.whereForm[status.index].rightTableIdAutocomplete)}
																					</span>
																					</td>
																				</c:when>
																				<c:otherwise>
																					<td></td>
																				</c:otherwise>
																		</c:choose>
																		<td rowspan="3"><label
																			class="qp-close-parenthesis"></label></td>
																	</tr>
																	<tr>
																		<td class="qp-table-cell-round"><span
																			>
																				${f:h(designForm.whereForm[status.index].leftColumnIdAutocomplete)}
																		</span></td>
																		<td class="qp-table-cell-round text-center"><span>
																				<qp:message
																					code="${CL_SQL_OPERATOR.get(designForm.whereForm[status.index].operatorCode.toString()) }"></qp:message>
																		</span></td>
																		<td class="qp-table-cell-round"><c:choose>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='1' }">
																					<span>
																						${f:h(designForm.whereForm[status.index].leftColumnIdAutocomplete)}
																					</span>
																				</c:when>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='0' }">
																					<c:choose>
																						<c:when
																							test="${designForm.whereForm[status.index].operatorCode=='7' }">
																							<span
																								class="pull-left text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].value) }
																							</span>
																							<div class="qp-separator-from-to">~</div>
																							<span
																								class="pull-right text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].value2) }
																							</span>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when
																									test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																									<span
																										class="text-center">
																										${f:h(designForm.whereForm[status.index ].value) }
																									</span>
																								</c:when>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:when
																					test="${designForm.whereForm[status.index].conditionType=='2' }">
																					<c:choose>
																						<c:when
																							test="${designForm.whereForm[status.index].operatorCode=='7' }">
																							<span
																								class="pull-left text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].displayArg) }
																							</span>
																							<div class="qp-separator-from-to">~</div>
																							<span
																								class="pull-right text-center"
																								style="width: 47%">
																								${f:h(designForm.whereForm[status.index ].displayArg2) }
																							</span>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when
																									test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																									<span
																										class="">
																										${f:h(designForm.whereForm[status.index ].displayArg) }
																									</span>
																								</c:when>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																			</c:choose></td>
																	</tr>
																	<c:if
																		test="${not empty designForm.whereForm[status.index ].functionCode }">
																		<tr>
																			<td class="qp-table-cell-round"><span
																				class="">
																					${f:h(CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.whereForm[status.index ].functionCode)) }
																			</span></td>
																		</tr>
																	</c:if>
																</table>
															</div>
														</td>
														<td><form:checkbox cssStyle="display:none"
																path="whereForm[${status.index }].groupType" /></td>
													</tr>
												</c:forEach>
											</table>
										</div>
									</div>
								</c:if>
							</c:if>
						</div>
					</div>
				</c:if>

			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>