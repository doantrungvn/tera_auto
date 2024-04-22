<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code='sc.viewdesign.0009' />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/viewdesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<div id="qp-viewdesign">
		<form:form method="post" modelAttribute="viewDesignDesignForm"
			action="${pageContext.request.contextPath}/viewdesign/delete">
			<c:set var="designForm" value="${viewDesignDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.sqlDesignForm.sqlDesignId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code='sc.viewdesign.0010' /></span>
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
								<th><label><qp:message code='sc.viewdesign.0006' /></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignName }"></qp:formatText></td>
								<th><label><qp:message code='sc.viewdesign.0007' /></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignCode }"></qp:formatText></td>
							</tr>
							<tr>
								<th><label><qp:message code='sc.sqldesign.0031' /></label></th>
								<td><qp:formatText value="${CL_SQL_TYPE_VIEWDESIGN.get(designForm.sqlDesignForm.designType)}"></qp:formatText>
								</td>
								<th><label><qp:message code="sc.module.0007"></qp:message></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.moduleIdAutocomplete }"></qp:formatText>
								</td>
							</tr>
							<tr>
								<th><label><qp:message code="sc.sys.0055"></qp:message></label></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus.toString())}" /></td>
								<th><label><qp:message code="sc.sys.0028"></qp:message></label></th>
								<td><qp:formatRemark value="${designForm.sqlDesignForm.remark }"></qp:formatRemark></td>
							</tr>
						</table>
					</div>
				</div>
				<div>
					<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
						<c:if test="${designForm.sqlDesignForm.sqlPattern eq '0'}">
							<div class="panel panel-default qp-div-search-result">
								<div class="panel-heading">
									<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="qp-heading-text">
										<qp:message code="sc.autocomplete.0041"></qp:message>
									</span>
								</div>
								<div class="panel-body">
									<table class="table table-borderless qp-table-form" id="fromForm">
										<colgroup>
											<col width="182px"/>
											<col />
											<col width="35px" />
										</colgroup>
										<tr>
											<td>
												<span style="width: 78%; margin-left: 8px;" class="pull-left form-control${designForm.fromForm[0].tableMissingFlag eq '1'?' qp-missing':'' }">
													${f:h(designForm.fromForm[0].tableIdAutocomplete)}
												</span>
											</td>
										</tr>
										<c:if test="${not empty designForm.fromForm[0].joinTableIdAutocomplete }">
											<c:forEach items="${designForm.fromForm}" varStatus="status">
												<tr data-ar-rindex="${status.index }">
													<td colspan="2">
														<div class="qp-div-highlight-border">
															<table class="table table-borderless join-conditions-table" >
																<colgroup>
																	<col width="18%" />
																	<col width="30px" />
																	<col />
																</colgroup>
																<tr>
																	<td style="vertical-align: top;">
																		<span class="form-control${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }" style="width:88%;margin-top:15px">
																			${f:h(designForm.fromForm[status.index].joinTableIdAutocomplete)}
																		</span>
																	</td>
																	<td style="vertical-align:top;padding-top:20px" rowspan="2" class="text-center"><b class="glyphicon glyphicon-eye-open  joinColumnsFormToggle qp-link-toggle"></b></td>
																	<td rowspan="2" colspan="3">
																		<div style="display:none">
																			<div>
																				<label>
																					<c:if test="${designForm.fromForm[status.index ].joinType eq '1' }">
																						<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_INNER_JOIN_48.png" >
																					</c:if>
																					<c:if test="${designForm.fromForm[status.index ].joinType eq '2' }">
																						<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_LEFT_JOIN_48.png">
																					</c:if>
																					<c:if test="${designForm.fromForm[status.index ].joinType eq '3' }">
																						<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_RIGHT_JOIN_48.png">
																					</c:if>
																					<c:if test="${designForm.fromForm[status.index ].joinType eq '4' }">
																						<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_FULL_JOIN_48.png">
																					</c:if>
																					<c:if test="${designForm.fromForm[status.index ].joinType eq '5' }">
																						<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_CROSS_JOIN_48.png">
																					</c:if>
																				</label>
																			</div>
																			<table class="table table-borderless" id="joinPairTable">
																				<colgroup>
																					<col width="20%" />
																					<col width="20%" />
																					<col width="20%" />
																					<col width="20%" />
																					<col width="20%" />
																				</colgroup>
																				<c:forEach items="${designForm.fromForm[status.index].joinColumnsForm}" varStatus="nestedStatus">
																					<tr data-ar-rindex="${nestedStatus.index }" style="border-bottom: solid 1.5px rgba(94, 92, 113, 0.15);">
																						<td>
																							<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableMissingFlag eq '1'?' qp-missing':'' }">
																								${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableIdAutocomplete)}
																							</span> 
																						</td>
																						<td>
																							<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnMissingFlag eq '1'?' qp-missing':'' }">
																								${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnIdAutocomplete) }
																							</span>
																						</td>
																						<td>
																							<span class="form-control qp-form-control-label text-center" >
																								<qp:message code="${CL_SQL_OPERATOR.get(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].operatorCode) }"></qp:message>
																							</span>
																						</td>
																						<td>
																							<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }">
																								${f:h(designForm.fromForm[status.index].joinTableIdAutocomplete)}
																							</span>
																						</td>
																						<td>
																							<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnMissingFlag eq '1'?' qp-missing':'' }">
																								${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnIdAutocomplete) }
																							</span>
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
									<span class="qp-heading-text"><qp:message code="sc.autocomplete.0045" /></span>
								</div>
								<div class="panel-body">
									<table class="table table-bordered qp-table" id="selectForm" style="margin-top:10px">
										<colgroup>
											<col />
											<col width="30%"/>
											<col width="30%"/>
										</colgroup>
										<thead>
											<tr>
												<th>
													<qp:message code="sc.autocomplete.0016" />
												</th>
												<th>
													<qp:message code="sc.autocomplete.0017" />
												</th>
												<th>
													<qp:message code="sc.autocomplete.0015" />
												</th>
											</tr>
										</thead>
										<c:forEach items="${designForm.selectForm}" varStatus="status">
											<c:if test="${designForm.selectForm[status.index].isSelected}">
												<tr>
													<td>
														<span class="${designForm.selectForm[status.index].tableMissingFlag eq '1'?'form-control qp-form-control-label qp-missing':'' }">
															${f:h(designForm.selectForm[status.index].tableIdAutocomplete) }
														</span>
													</td>
													<td>
														<span class="${designForm.selectForm[status.index].columnMissingFlag eq '1'?'form-control qp-form-control-label qp-missing':'' }">
															${f:h(designForm.selectForm[status.index].columnIdAutocomplete) }
														</span>
													</td>
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
										<span class="qp-heading-text"><qp:message code="sc.autocomplete.0046"></qp:message></span>
									</div>
									<div class="panel-body">
										<table class="table table-borderless" id="whereForm" data-ar-callback="setArgumentForAllTableAC">
											<colgroup>
												<col>
												<col width="20px">
											</colgroup>
											<c:forEach items="${designForm.whereForm}" varStatus="status">
												<tr>
													<td>
														<div class="qp-div-highlight-border">
															<table class="table table-borderless qp-table-form">
																<colgroup>
																	<col width="15%" />
																	<col width="15px" />
																	<col width="35%" />
																	<col width="15%" />
																	<col />
																	<col width="15px" />
																</colgroup>
																<tr>
																	<td rowspan="3">
																		<c:if test="${not empty designForm.whereForm[status.index].logicCode.toString()}">
																			<span class="form-control qp-form-control-label text-center">
																				${f:h(CL_SQL_COMBINING_OPERATOR.get(designForm.whereForm[status.index].logicCode.toString())) }
																			</span>
																		</c:if>
																	</td>
																	<td rowspan="3">
																		<label class="qp-open-parenthesis"></label>
																	</td>
																	<td>
																		<span class="form-control qp-form-control-label${designForm.whereForm[status.index].leftTableMissingFlag eq '1'?' qp-missing':'' }">
																			${f:h(designForm.whereForm[status.index].leftTableIdAutocomplete)}
																		</span>
																	</td>
																	<td>
																		<span class="form-control qp-form-control-label text-center">
																			${f:h(CL_SQL_CONDITION_TYPE.get(designForm.whereForm[status.index].conditionType.toString())) }
																		</span>
																	</td>
																	<td>
																		<c:choose>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='1' }">
																				<span class="form-control qp-form-control-label${designForm.whereForm[status.index].rightTableMissingFlag eq '1'?' qp-missing':'' }">
																					${f:h(designForm.whereForm[status.index].rightTableIdAutocomplete)}
																				</span>
																			</c:when>
																		</c:choose>
																	</td>
																	<td rowspan="3">
																		<label class="qp-close-parenthesis"></label>
																	</td>
																</tr>
																<tr>
																	<td>
																		<span class="form-control qp-form-control-label${designForm.whereForm[status.index].leftColumnMissingFlag eq '1'?' qp-missing':'' }">
																			${f:h(designForm.whereForm[status.index].leftColumnIdAutocomplete)}
																		</span>
																	</td>
																	<td>
																		<span class="form-control qp-form-control-label  text-center">
																			<qp:message code="${CL_SQL_OPERATOR.get(designForm.whereForm[status.index].operatorCode.toString())}"></qp:message>
																		</span>
																	</td>
																	<td>
																		<c:choose>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='1' }">
																				<span class="form-control qp-form-control-label${designForm.whereForm[status.index].rightColumnMissingFlag eq '1'?' qp-missing':'' }">
																					${f:h(designForm.whereForm[status.index].rightColumnIdAutocomplete)}
																				</span>
																			</c:when>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='0' }">
																				<c:choose>
																					<c:when test="${designForm.whereForm[status.index].operatorCode=='7' }">
																						<span class="form-control qp-form-control-label pull-left text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].value) }
																						</span>
																						<div class="qp-separator-from-to">~</div>
																						<span class="form-control qp-form-control-label pull-right text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].value2) }
																						</span>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																								<span class="form-control qp-form-control-label  text-center">
																									${f:h(designForm.whereForm[status.index ].value) }
																								</span>
																							</c:when>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='2' }">
																				<c:choose>
																					<c:when test="${designForm.whereForm[status.index].operatorCode=='7' }">
																						<span class="form-control qp-form-control-label pull-left text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].arg) }
																						</span>
																						<div class="qp-separator-from-to">~</div>
																						<span class="form-control qp-form-control-label pull-right text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].arg2) }
																						</span>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																								<span class="form-control qp-form-control-label">
																									${f:h(designForm.whereForm[status.index ].arg) }
																								</span>
																							</c:when>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																		</c:choose>
																	</td>
																</tr>
																<c:if test="${not empty designForm.whereForm[status.index ].functionCode }">
																	<tr>
																		<td>
																			<span class="form-control qp-form-control-label">
																				${f:h(CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.whereForm[status.index ].functionCode)) }
																			</span>
																		</td>
																	</tr>
																</c:if>
															</table>
														</div>
													</td>
													<td>
														<form:checkbox cssStyle="display:none" path="whereForm[${status.index }].groupType" />
													</td>
												</tr>
											</c:forEach>
											<c:if test="${empty designForm.whereForm}">
												<tr>
													<td colspan="2">
														<qp:message code="inf.sys.0013"></qp:message>
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
										<table class="table table-bordered qp-table" id="orderByForm" data-ar-callback="setArgumentForAllTableAC">
											<colgroup>
												<col width="50%" />
												<col width="50%" />
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.viewdesign.0048"></qp:message></th>
													<th><qp:message code="sc.viewdesign.0049"></qp:message></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${designForm.orderByForm}"
													varStatus="status">
													<tr>
														<td><qp:formatText value="${designForm.orderByForm[status.index].tableColumnAutocomplete}"></qp:formatText>
														</td>
														<td><qp:formatText value="${CL_SQL_ORDER_DIRECTION.get(designForm.orderByForm[status.index].orderType) }"></qp:formatText>
														</td>
													</tr>
												</c:forEach>
												<c:if test="${empty designForm.orderByForm}">
													<tr>
														<td colspan="2">
															<qp:message code="inf.sys.0013"></qp:message>
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
					</div>
				</div>
				<div class="qp-div-action">
					<form:hidden path="sqlDesignForm.sqlDesignId" />
					<form:hidden path="sqlDesignForm.updatedDate" />
					<input type="hidden" name="sqlDesignForm.designStatus" value="${designForm.sqlDesignForm.designStatus}"/>
					<form:hidden path="actionDelete" value="false"/>
					<qp:authorization permission="viewdesignModify">
						<c:choose>
							<c:when test="${designForm.sqlDesignForm.designStatus eq '1'}">
								<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"></qp:message></button>
							</c:when>
							<c:when test="${designForm.sqlDesignForm.designStatus eq '2'}">
								<qp:authorization permission="changeDesignStatus">
									<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"></qp:message></button>
								</qp:authorization>
							</c:when>
						</c:choose>
					</qp:authorization>
					<c:if test="${designForm.sqlDesignForm.designStatus eq '1'}">
						<qp:authorization permission="viewdesignDelete">
							<button type="button" class="btn btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="$.qp.common.setFlag()"><qp:message code="sc.sys.0008" /></button>
						</qp:authorization>
						<qp:authorization permission="viewdesignModify">
							<a type="submit"
								class="btn btn-success qp-link-button qp-link-popup-navigate"
								href="${pageContext.request.contextPath}/viewdesign/modify?sqlDesignForm.sqlDesignId=${f:u(designForm.sqlDesignForm.sqlDesignId)}&mode=1">
								<qp:message code="sc.sys.0006"></qp:message>
							</a>
						</qp:authorization>
					</c:if>
				</div>
			</c:if>
			
		</form:form>
	</div>
	</tiles:putAttribute>
</tiles:insertDefinition>