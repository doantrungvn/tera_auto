<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text"><qp:message code="sc.autocomplete.0041"></qp:message></span>
	</div>
	<div class="panel-body">
		<table class="table table-borderless qp-table-form" id="fromForm" data-ar-callback="$.qp.sqlbuilder.fromFormCallback">
			<colgroup>
				<col width="182px"/>
				<col />
				<col width="35px" />
			</colgroup>
			<tr>
				<td>
					<qp:autocomplete optionValue="optionValue"
												selectSqlId="getAllTableDesignByProjectIdWithColumns" 
												name="fromForm[0].tableId" 
												value="${designForm.fromForm[0].tableId}"
												displayValue="${designForm.fromForm[0].tableIdAutocomplete}"
												optionLabel="optionLabel"
												onChangeEvent="$.qp.sqlbuilder.passTableNameLabel"
												cssStyle="width:78%;margin-left:10px;"
												cssClass="pull-left${designForm.fromForm[0].tableMissingFlag eq '1'?' qp-missing':'' }"
												placeHolder = "sc.autocomplete.0016"
												></qp:autocomplete>
					<form:hidden path="fromForm[0].tableType"/>
					<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
				</td>
			</tr>
			<c:forEach items="${designForm.fromForm}" varStatus="status">
				<c:if test="${status.index ge 0}">
					<tr data-ar-rindex="${status.index }"  class="ar-dataRow">
						<td colspan="2">
							<div class="qp-div-highlight-border">
								<table class="table table-borderless join-conditions-table" data-ar-tlevel="0"  data-ar-callback="$.qp.sqlbuilder.fromFormCallback">
									<colgroup>
										<col width="18%" />
										<col width="30px" />
										<col />
									</colgroup>
									<tr>
										<td style="vertical-align:top;">
											<form:hidden path="fromForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
											<form:hidden path="fromForm[${status.index }].sqlDesignTableId"/>
												<qp:autocomplete optionValue="optionValue"
													selectSqlId="getAllTableDesignByProjectIdWithColumns" 
													name="fromForm[${status.index }].joinTableId" 
													value="${designForm.fromForm[status.index].joinTableId}"
													displayValue="${designForm.fromForm[status.index].joinTableIdAutocomplete}" 
													optionLabel="optionLabel"
													onChangeEvent="$.qp.sqlbuilder.passJoinTableNameLabel"
													cssClass="pull-left${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }"
													cssStyle="width:88%;margin-top:15px"
													placeHolder = "sc.autocomplete.0016"
													>
												</qp:autocomplete>
												<form:hidden path="fromForm[${status.index }].joinTableType"/>
											<label class="qp-required-field" style="margin-top:15px">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										</td>
										<td style="vertical-align:top;padding-top:20px" rowspan="2" class="text-center">
											<b class="glyphicon ${designForm.fromForm[status.index].isShow?'glyphicon-eye-close':'glyphicon-eye-open' } joinColumnsFormToggle qp-link-toggle"></b>
											<form:hidden path="fromForm[${status.index }].isShow"/>
										</td>
										<td rowspan="2" colspan="3">
											<div ${not designForm.fromForm[status.index].isShow?"style='display:none'":'' }>
												<div>
													<c:forEach items="${CL_SQL_JOIN_TYPE }" var="clItem" varStatus="clStatus">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<form:radiobutton path="fromForm[${status.index}].joinType" value="${clItem.key }" style="vertical-align: middle" onclick="$.qp.sqlbuilder.joinTypeClick(this);"/>
														<label for="fromForm${status.index}.joinType${clStatus.index+1 }">
															<c:if test="${clItem.key eq '1' }">
																<img title="INNER JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_INNER_JOIN_48.png" >
															</c:if>
															<c:if test="${clItem.key eq '2' }">
																<img title="LEFT JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_LEFT_JOIN_48.png">
															</c:if>
															<c:if test="${clItem.key eq '3' }">
																<img title="RIGHT JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_RIGHT_JOIN_48.png">
															</c:if>
															<c:if test="${clItem.key eq '4' }">
																<img title="FULL OUTER JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_FULL_JOIN_48.png">
															</c:if>
															<c:if test="${clItem.key eq '5' }">
																<img title="CROSS JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_CROSS_JOIN_48.png">
															</c:if>
														</label>
													</c:forEach>
												</div>
												<table class="table table-borderless" id="joinPairTable" data-ar-tlevel="1" data-ar-callback="$.qp.sqlbuilder.joinPairCallback" data-ar-precheck="$.qp.sqlbuilder.joinPairPrecheck">
													<colgroup>
														<col width="20%" />
														<col width="20%" />
														<col width="15%" />
														<col width="20%" />
														<col width="20%" />
														<col width="5%" />
													</colgroup>
													<c:forEach items="${designForm.fromForm[status.index].joinColumnsForm}" varStatus="nestedStatus">
														<tr  data-ar-rindex="${nestedStatus.index }" style="border-bottom:solid 1.5px rgba(94, 92, 113, 0.15);"  class="ar-dataRow">
															<td>
																<form:hidden path="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index}].itemSeqNo" cssClass="ar-groupIndex"/>
																<form:hidden path="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index}].sqlDesignTableItemId"/>
																<qp:autocomplete optionValue="optionValue"
																	selectSqlId="getAllTableDesignByProjectId" 
																	name="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index }].tableId" 
																	value="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableId}"
																	displayValue="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableIdAutocomplete}"
																	optionLabel="optionLabel"
																	onChangeEvent="$.qp.sqlbuilder.passTableId"
																	cssStyle="width:85%"
																	cssClass="pull-left${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableMissingFlag eq '1'?' qp-missing':'' }"
																	placeHolder = "sc.autocomplete.0016"
																	>
																	</qp:autocomplete>
																<form:hidden path="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index }].tableType"/>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td>
																<qp:autocomplete optionValue="optionValue" 
																				selectSqlId="getAllTableDesignColumnAC"
																				value="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnId }" 
																				displayValue="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnIdAutocomplete }" 
																				name="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index }].columnId" 
																				optionLabel="optionLabel"
																				cssStyle="width:85%" onChangeEvent="$.qp.sqlbuilder.setDisplayOperatorCode"
																				cssClass="pull-left${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnMissingFlag eq '1'?' qp-missing':'' }"
																				arg02="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableType}"
																				placeHolder = "sc.autocomplete.0017"
																				></qp:autocomplete>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td>
																<form:select path="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index }].operatorCode" 
																			cssClass="form-control qp-input-select pull-left" 
																			cssStyle="width:80%;white-space: nowrap;overflow: hidden;"
																			onfocus="$.qp.sqlbuilder.initOperatorOptions(this,'1');">
																	<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
																	<c:forEach items="${CL_SQL_OPERATOR }" var="item">
																		<form:option value="${item.key }"><qp:message code="${item.value }"></qp:message></form:option>
																	</c:forEach>
																</form:select>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td><b name="fromForm[${status.index }].rightTableName" class="qp-form-control-label form-control${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }" style="padding-top: 2px;">${designForm.fromForm[status.index].joinTableIdAutocomplete}</b></td>
															<td>
																<qp:autocomplete optionValue="optionValue"
																	selectSqlId="getAllTableDesignColumnAC" 
																	value="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnId }" 
																	displayValue="${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnIdAutocomplete }" 
																	name="fromForm[${status.index }].joinColumnsForm[${nestedStatus.index }].joinColumnId" 
																	optionLabel="optionLabel"
																	cssStyle="width:85%"
																	cssClass="pull-left${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnMissingFlag eq '1'?' qp-missing':'' }"
																	placeHolder = "sc.autocomplete.0017"
																	arg02="${designForm.fromForm[status.index].joinTableType}"></qp:autocomplete>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td>
																<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action"  onclick="$.qp.ar.removeRow(this);" href="javascript:void(0)"></a>
															</td>
														</tr>
													</c:forEach>
													<c:if test="${empty designForm.fromForm[status.index].joinColumnsForm }">
														<tr  data-ar-rindex="${nestedStatus.index }" style="border-bottom:solid 1.5px rgba(94, 92, 113, 0.15);"  class="ar-dataRow">
															<td>
																<form:hidden path="fromForm[${status.index }].joinColumnsForm[0].itemSeqNo" cssClass="ar-groupIndex"/>
																<form:hidden path="fromForm[${status.index }].joinColumnsForm[0].sqlDesignTableItemId"/>
																<qp:autocomplete optionValue="optionValue"
																	selectSqlId="getAllTableDesignByProjectId" 
																	name="fromForm[${status.index }].joinColumnsForm[0].tableId" 
																	optionLabel="optionLabel"
																	onChangeEvent="$.qp.sqlbuilder.passTableId"
																	cssStyle="width:85%"
																	cssClass="pull-left"
																	placeHolder = "sc.autocomplete.0016"
																	>
																	</qp:autocomplete>
																<form:hidden path="fromForm[${status.index }].joinColumnsForm[0].tableType"/>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
																
															</td>
															<td>
																<qp:autocomplete optionValue="optionValue" 
																				selectSqlId="getAllTableDesignColumnAC"
																				name="fromForm[${status.index }].joinColumnsForm[0].columnId" 
																				optionLabel="optionLabel" onChangeEvent="$.qp.sqlbuilder.setDisplayOperatorCode" 
																				cssStyle="width:85%"
																				cssClass="pull-left"
																				placeHolder = "sc.autocomplete.0017"></qp:autocomplete>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td>
																<form:select path="fromForm[${status.index }].joinColumnsForm[0].operatorCode" 
																			cssClass="form-control qp-input-select pull-left" 
																			cssStyle="width:80%;white-space: nowrap;overflow: hidden;"
																			onfocus="$.qp.sqlbuilder.initOperatorOptions(this,'1');">
																	<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
																	<c:forEach items="${CL_SQL_OPERATOR }" var="item">
																		<form:option value="${item.key }"><qp:message code="${item.value }"></qp:message></form:option>
																	</c:forEach>
																</form:select>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td><b name="fromForm[${status.index }].rightTableName" class="form-control qp-form-control-label" style="padding-top: 2px;">${designForm.fromForm[status.index].joinTableIdAutocomplete}</b></td>
															<td>
																<qp:autocomplete optionValue="optionValue"
																	selectSqlId="getAllTableDesignColumnAC" 
																	name="fromForm[${status.index }].joinColumnsForm[0].joinColumnId" 
																	optionLabel="optionLabel"
																	cssStyle="width:85%"
																	cssClass="pull-left"
																	placeHolder = "sc.autocomplete.0017"></qp:autocomplete>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
															</td>
															<td>
																<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action"  onclick="$.qp.ar.removeRow(this);" href="javascript:void(0)"></a>
															</td>
														</tr>
													</c:if>
												</table>
												<div class="qp-add-left">
													<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
												</div>
											</div>
										</td>
									</tr>
									<tr>
									</tr>
								</table> 
							</div>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove tables" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<c:if test="${empty designForm.fromForm}">
					<tr data-ar-rindex="0"  class="ar-dataRow">
						<td colspan="2">
							<div class="qp-div-highlight-border">
								<table class="table table-borderless join-conditions-table" data-ar-tlevel="0"  data-ar-callback="$.qp.sqlbuilder.fromFormCallback">
									<colgroup>
										<col width="18%" />
										<col width="30px" />
										<col />
									</colgroup>
									<tr>
										<td style="vertical-align:top;">
											<form:hidden path="fromForm[0].itemSeqNo" cssClass="ar-groupIndex"/>
											<form:hidden path="fromForm[0].sqlDesignTableId"/>
											<qp:autocomplete optionValue="optionValue"
												selectSqlId="getAllTableDesignByProjectIdWithColumns" 
												name="fromForm[0].joinTableId" 
												optionLabel="optionLabel"
												onChangeEvent="$.qp.sqlbuilder.passJoinTableNameLabel"
												cssClass="pull-left"
												cssStyle="width:88%;margin-top:15px"
												placeHolder = "sc.autocomplete.0016"
												>
											</qp:autocomplete>
											<label class="qp-required-field" style="margin-top:15px">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										</td>
										<td style="vertical-align:top;padding-top:20px" rowspan="2" class="text-center">
											<b class="glyphicon ${designForm.fromForm[status.index].isShow?'glyphicon-eye-close':'glyphicon-eye-open' } joinColumnsFormToggle qp-link-toggle"></b>
											<form:hidden path="fromForm[0].isShow"/>
										</td>
										<td rowspan="2" colspan="3">
											<div style="display:none">
												<div>
													<c:forEach items="${CL_SQL_JOIN_TYPE }" var="clItem" varStatus="clStatus">
														&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="fromForm[0].joinType${clStatus.index }" name="fromForm[0].joinType" value="${clItem.key }" style="vertical-align: middle" onclick="$.qp.sqlbuilder.joinTypeClick(this);">
														<label for="fromForm[0].joinType${clStatus.index }">
															<c:if test="${clItem.key eq '1' }">
																<img title="INNER JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_INNER_JOIN_48.png" >
															</c:if>
															<c:if test="${clItem.key eq '2' }">
																<img title="LEFT JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_LEFT_JOIN_48.png">
															</c:if>
															<c:if test="${clItem.key eq '3' }">
																<img title="RIGHT JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_RIGHT_JOIN_48.png">
															</c:if>
															<c:if test="${clItem.key eq '4' }">
																<img title="FULL OUTER JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_FULL_JOIN_48.png">
															</c:if>
															<c:if test="${clItem.key eq '5' }">
																<img title="CROSS JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_CROSS_JOIN_48.png">
															</c:if>
														</label>
													</c:forEach>
												</div>
												<table class="table table-borderless" id="joinPairTable" data-ar-tlevel="1" data-ar-callback="$.qp.sqlbuilder.joinPairCallback" data-ar-precheck="$.qp.sqlbuilder.joinPairPrecheck">
													<colgroup>
														<col width="20%" />
														<col width="20%" />
														<col width="15%" />
														<col width="20%" />
														<col width="20%" />
														<col width="5%" />
													</colgroup>
													<tr data-ar-rindex="0" style="border-bottom:solid 1.5px rgba(94, 92, 113, 0.15);" data-ar-templateid="joinPairTable-v1-template"  class="ar-dataRow">
														<td  style="vertical-align:bottom;">
															<form:hidden path="fromForm[0].joinColumnsForm[0].itemSeqNo" cssClass="ar-groupIndex"/>
															<form:hidden path="fromForm[0].joinColumnsForm[0].sqlDesignTableItemId"/>
															<qp:autocomplete optionValue="optionValue"
																selectSqlId="getAllTableDesignByProjectId" 
																name="fromForm[0].joinColumnsForm[0].tableId" 
																optionLabel="optionLabel"
																onChangeEvent="$.qp.sqlbuilder.passTableNameLabel"
																cssStyle="width:85%;"
																cssClass="pull-left"
																placeHolder = "sc.autocomplete.0016"
																/>
															<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
														</td>
														<td>
															<qp:autocomplete optionValue="optionValue" 
																			selectSqlId="getAllTableDesignColumnAC"
																			name="fromForm[0].joinColumnsForm[0].columnId"
																			optionLabel="optionLabel" onChangeEvent="$.qp.sqlbuilder.setDisplayOperatorCode" 
																			cssStyle="width:85%"
																			cssClass="pull-left"
																			placeHolder = "sc.autocomplete.0017"></qp:autocomplete>
															<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
														</td>
														<td>
															<form:select path="fromForm[0].joinColumnsForm[0].operatorCode" 
																		cssClass="form-control qp-input-select pull-left" 
																		onfocus="$.qp.sqlbuilder.initOperatorOptions(this,'1');"
																		cssStyle="width:80%;white-space: nowrap;overflow: hidden;">
																<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
																<c:forEach items="${CL_SQL_OPERATOR }" var="item">
																		<form:option value="${item.key }"><qp:message code="${item.value }"></qp:message></form:option>
																	</c:forEach>
															</form:select>
															<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
														</td>
														<td><b name="fromForm[0].rightTableName" class="form-control qp-form-control-label" style="padding-top: 2px;"></b></td>
														<td  style="vertical-align:bottom;">
															<qp:autocomplete optionValue="optionValue"
																selectSqlId="getAllTableDesignColumnAC" 
																name="fromForm[0].joinColumnsForm[0].joinColumnId" 
																optionLabel="optionLabel"
																cssStyle="width:85%"
																cssClass="pull-left"
																placeHolder = "sc.autocomplete.0017"></qp:autocomplete>
																<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
														</td>
														<td>
															<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
														</td>
													</tr>
												</table>
												<div class="qp-add-left">
													<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
												</div>
											</div>
										</td>
									</tr>
									<tr>
									</tr>
								</table> 
							</div>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove tables" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
						</td>
					</tr>
				</c:if>
		</table>
		<div class="qp-add-left">
			<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link : this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
		</div>
		<p class="text-warning" style="margin-top:10px"><qp:message code="sc.autocomplete.0004"></qp:message></p>
	</div>
</div>