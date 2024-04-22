<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span
			class="qp-heading-text"><qp:message code="sc.autocomplete.0046"></qp:message></span>
	</div>
	<div class="panel-body">
		<table class="table table-borderless" id="whereForm" data-ar-callback="$.qp.sqlbuilder.whereFormCallback">
			<colgroup>
				<col>
				 <col width="20px">
				 <col width="35px">
			</colgroup>
			<c:forEach items="${designForm.whereForm}" varStatus="status">
				<tr class="ar-dataRow">
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
										<form:hidden path="whereForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
										<form:hidden path="whereForm[${status.index }].conditionsId"/>
										<form:select
													path="whereForm[${status.index }].logicCode"
													cssClass="form-control qp-input-select pull-left"
													cssStyle="width:85%">
													<form:options items="${CL_SQL_COMBINING_OPERATOR }" />
										</form:select>
									</td>
									<td rowspan="3">
										<label class="qp-open-parenthesis"></label>
									</td>
									<td>
										<qp:autocomplete optionValue="optionValue"
												selectSqlId="getAllTableDesignAC"
												value="${designForm.whereForm[status.index].leftTableId}"
												displayValue="${designForm.whereForm[status.index].leftTableIdAutocomplete}"
												name="whereForm[${status.index }].leftTableId"
												onChangeEvent="$.qp.sqlbuilder.whereLeftTableChange"
												optionLabel="optionLabel"
												cssClass="pull-left${designForm.whereForm[status.index].leftTableMissingFlag eq '1'?' qp-missing':'' }"
												cssStyle="width:90%"
												placeHolder="sc.autocomplete.0016"></qp:autocomplete>
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td>
										<form:select path="whereForm[${status.index }].operatorCode" onchange="$.qp.sqlbuilder.operatorTypeChange(this);"
												cssClass="form-control qp-input-select pull-left" cssStyle="width:85%">
												<c:forEach items="${CL_SQL_OPERATOR }" var="item">
													<form:option value="${item.key }"><qp:message code="${item.value }"></qp:message></form:option>
												</c:forEach>
										</form:select>
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td>
										<qp:autocomplete optionValue="optionValue"
											selectSqlId="getAllTableDesignAC"
											value="${designForm.whereForm[status.index].rightTableId}"
											displayValue="${designForm.whereForm[status.index].rightTableIdAutocomplete}"
											name="whereForm[${status.index }].rightTableId"
											onChangeEvent="$.qp.sqlbuilder.whereRightTableChange"
											cssStyle="display:none;width:93.58%"
											cssClass="pull-left${designForm.whereForm[status.index].rightTableMissingFlag eq '1'?' qp-missing':'' }"
											optionLabel="optionLabel"
											placeHolder="sc.autocomplete.0016"></qp:autocomplete>
										<label class="qp-required-field pull-left" style="display:none">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td rowspan="3">
										<label class="qp-close-parenthesis"></label>
									</td>
								</tr>
								<tr>
									<td>
										<qp:autocomplete optionValue="optionValue"
												selectSqlId="getAllTableDesignColumnAC"
												value="${designForm.whereForm[status.index].leftColumnId}"
												displayValue="${designForm.whereForm[status.index].leftColumnIdAutocomplete}"
												name="whereForm[${status.index }].leftColumnId"
												optionLabel="optionLabel" onChangeEvent="$.qp.sqlbuilder.callSetDisplayFunctionCode"
												cssStyle="width:90%" 
												cssClass="pull-left${designForm.whereForm[status.index].leftColumnMissingFlag eq '1'?' qp-missing':'' }"
												placeHolder="sc.autocomplete.0017"></qp:autocomplete>		
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										<form:hidden path="whereForm[${status.index }].dataType"/>
										<form:hidden path="whereForm[${status.index }].patternFormat"/>
									</td>
									<td>
										<form:select path="whereForm[${status.index }].conditionType" onchange="$.qp.sqlbuilder.conditionTypeChange(this);" cssClass="form-control qp-input-select pull-left" cssStyle="width:85%">
											<form:options items="${CL_SQL_CONDITION_TYPE }"/>
										</form:select>
										<label class="qp-required-field pull-left">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td>
										<qp:autocomplete optionValue="optionValue"
												selectSqlId="getAllTableDesignColumnAC"
												value="${designForm.whereForm[status.index].rightColumnId}"
												displayValue="${designForm.whereForm[status.index].rightColumnIdAutocomplete}"
												name="whereForm[${status.index }].rightColumnId"
												optionLabel="optionLabel"
												cssStyle="display:none;width:93.58%"
												cssClass="pull-left${designForm.whereForm[status.index].rightColumnMissingFlag eq '1'?' qp-missing':'' }"
												placeHolder="sc.autocomplete.0017">
										</qp:autocomplete>
										<form:select path="whereForm[${status.index }].arg"	cssClass="form-control qp-input-select pull-left" onmouseover="$.qp.common.buildInputList('table#inputForm',this)" cssStyle="display:none;width:93.58%">
											<form:option value="">
												<qp:message code="sc.sys.0030"></qp:message>
											</form:option>
										</form:select>	
										<input type="hidden" value="${designForm.whereForm[status.index].arg}"  onchange="$.qp.sqlbuilder.whereParameterChange(this);"/>	
										<form:input path="whereForm[${status.index }].value" cssClass="form-control qp-input-text pull-left" cssStyle="display:none;"/>
										<label class="qp-required-field pull-left" style="display:none;">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										<div class="qp-separator-from-to" style="display:none">~</div>
										<label class="qp-required-field pull-right" style="display:none;">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										<form:select path="whereForm[${status.index }].arg2"	cssClass="form-control qp-input-select pull-right" cssStyle="display:none;width:47%" onmouseover="$.qp.common.buildInputList('table#inputForm',this)"  onchange="$.qp.sqlbuilder.whereParameterChange(this);">
												<form:option value="">
													<qp:message code="sc.sys.0030"></qp:message>
												</form:option>
										</form:select>
										<input type="hidden" value="${designForm.whereForm[status.index].arg2}"/>			
										<form:input path="whereForm[${status.index }].value2" cssClass="form-control qp-input-text pull-right" cssStyle="display:none;width:47%"/>
									</td>
								</tr>
								<tr>
									<td>
										<form:select path="whereForm[${status.index }].functionCode" cssClass="form-control qp-input-select pull-left" style="width:90%" onchange="$.qp.sqlbuilder.whereFunctionCodeChange(this)">
											<form:option value="">
												<qp:message code="sc.sys.0030"></qp:message>
											</form:option>
											<form:options items="${CL_SQL_AGGREGATE_FUNCTIONS }" />
										</form:select>
									</td>
								</tr>
						</table>
					</div>
				</td>
				<td><form:checkbox cssClass="qp-input-checkbox" path="whereForm[${status.index }].groupType" onchange="$.qp.sqlbuilder.groupTypeChange(this)"/></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove conditions" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a></td>
			</tr>
			</c:forEach>
		</table>
		<div class="qp-add-left">
			<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
		</div>
	</div>
</div>