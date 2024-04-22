<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span
			class="qp-heading-text"><qp:message code="sc.autocomplete.0048"></qp:message></span>
	</div>
	<div class="panel-body">
		<table class="table table-borderless" id="havingForm" data-ar-callback="$.qp.sqlbuilder.havingFormCallback">
			<colgroup>
				 <col width="90%" />
				 <col />
			 </colgroup>
			<c:forEach items="${designForm.havingForm}" varStatus="status">
				<tr class="ar-dataRow">
					<td colspan="4">	 
						<div class="qp-div-highlight-border">
							<form:hidden path="havingForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
							<form:hidden path="havingForm[${status.index }].havingId"/>
							<form:hidden path="havingForm[${status.index }].columnType" />
							<table class="table table-borderless qp-table-form">
								<colgroup>
									<col width="15%" />
									<col width="35%" />
									<col width="15%" />
									<col width="" />
								</colgroup>
								<tr>
									<td rowspan="3">
										<form:select path="havingForm[${status.index }].logicCode" cssClass="form-control qp-input-select pull-left" cssStyle="width:85%">
											<form:option value="">
												<qp:message code="sc.sys.0030"></qp:message>
											</form:option>
											<form:options items="${CL_SQL_COMBINING_OPERATOR }" />
										</form:select>
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td>
										<qp:autocomplete
											onChangeEvent="$.qp.sqlbuilder.havingTableChange"
											optionValue="optionValue"
											selectSqlId="getAllTableDesignAC"
											value="${designForm.havingForm[status.index].tableId}"
											displayValue="${designForm.havingForm[status.index].tableIdAutocomplete}"
											name="havingForm[${status.index }].tableId"
											optionLabel="optionLabel"
											cssStyle="width:90%"
											cssClass="pull-left"></qp:autocomplete>
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
								</tr>
								<tr>
									<td>
										<qp:autocomplete optionValue="optionValue"
											selectSqlId="getAllTableDesignColumnAC"
											value="${designForm.havingForm[status.index].columnId}"
											displayValue="${designForm.havingForm[status.index].columnIdAutocomplete}"
											name="havingForm[${status.index }].columnId"
											optionLabel="optionLabel"
											onChangeEvent="$.qp.sqlbuilder.formatHavingValue"
											cssClass="pull-left"
											cssStyle="width:90%"></qp:autocomplete>
											<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td>
										<form:select path="havingForm[${status.index }].operatorCode" cssClass="form-control qp-input-select pull-left" cssStyle="width:85%" onchange="$.qp.sqlbuilder.operatorTypeChange(this);">
											<form:option value="">
												<qp:message code="sc.sys.0030"></qp:message>
											</form:option>
											<c:forEach items="${CL_SQL_OPERATOR }" var="item">
												<form:option value="${item.key }"><qp:message code="${item.value }"></qp:message></form:option>
											</c:forEach>
										</form:select>
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
									<td>
										<form:input cssClass="form-control qp-input-text pull-left" path="havingForm[${status.index }].value" cssStyle="display:none;width:90%"/>
										<label class="qp-required-field pull-left" style="display:none">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										<div class="qp-separator-from-to" style="display:none">~</div>
										<label class="qp-required-field pull-right" style="display:none">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
										<form:input cssClass="form-control qp-input-text float-right" path="havingForm[${status.index }].value2" cssStyle="display:none;width:90%"/>
									</td>
								</tr>
								<tr>
									<td>
										<form:select path="havingForm[${status.index }].functionCode" cssClass="form-control qp-input-select pull-left" style="width:90%">
											<form:option value="">
												<qp:message code="sc.sys.0030"></qp:message>
											</form:option>
											<form:options items="${CL_SQL_AGGREGATE_FUNCTIONS }" />
										</form:select>
										<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
									</td>
								</tr>
							</table>									
						</div>
					</td>
					<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove conditions" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a></td>
				</tr>
			</c:forEach>
		</table>
		<div class="qp-add-left">
			<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
		</div>
	</div>
</div>