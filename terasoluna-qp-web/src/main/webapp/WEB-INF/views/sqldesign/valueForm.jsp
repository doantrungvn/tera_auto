<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span
			class="qp-heading-text"><qp:message code="sc.sqldesign.0034"></qp:message></span>
	</div>
	<div class="panel-body">
		<table class="table table-borderless qp-table-form" id="valueForm" data-ar-callback="$.qp.sqlbuilder.valueFormCallback" style="width:80%">
			<colgroup>
				<col/>
				<col width="3%" />
				<col/>
				<col/>
				<col width="35px"/>
			</colgroup> 
			<c:forEach items="${designForm.valueForm}" varStatus="status">
				<tr class="ar-dataRow">
					<td>
						<form:hidden path="valueForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
						<form:hidden path="valueForm[${status.index }].sqlDesignValueId"/>
						<qp:autocomplete
										optionValue="optionValue" 
										selectSqlId="getAllTableDesignColumnAC" 
										value="${designForm.valueForm[status.index].columnId}"
										displayValue="${designForm.valueForm[status.index].columnIdAutocomplete}"  
										name="valueForm[${status.index }].columnId" 
										sourceCallback="$.qp.sqlbuilder.changeSourceValueItem"
										sourceType="local"
										optionLabel="optionLabel" onChangeEvent="$.qp.sqlbuilder.changeDataTypeColumnValueItem"
										cssStyle="width:90%"
										cssClass="pull-left"></qp:autocomplete>
						<label class="qp-required-field pull-left">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
						<form:hidden path="valueForm[${status.index }].dataType"/>
						<form:hidden path="valueForm[${status.index}].patternFormat"/>
					</td>
					<td>
						<qp:message code="sc.sqldesign.0035"></qp:message>
					</td>
					<td>
						<form:select path="valueForm[${status.index}].valueType" onchange="$.qp.sqlbuilder.changeTypeInsertValue(this)" cssClass="form-control qp-input-select pull-left" cssStyle="width:90%">
							<c:forEach items="${CL_QP_VALUE_TYPE}" var="item">
								<form:option value="${item.key }"><qp:message code="${item.value}"></qp:message></form:option>
							</c:forEach>
							
						</form:select>
						<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
					</td>	
					<td>
						<c:choose>
							<c:when test="${designForm.valueForm[status.index].valueType == 0}">
								<form:select path="valueForm[${status.index}].parameter" onmouseover="$.qp.common.buildInputList('table#inputForm',this)" cssClass="form-control qp-input-select pull-left" cssStyle="width:90%">
									<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
								</form:select>
							</c:when>
							<c:when test="${designForm.valueForm[status.index].valueType == 1}">
								<c:choose>
									<c:when test="${designForm.valueForm[status.index].dataType == 10}">
										<div style="width:90%; display:inline-block; vertical-align: middle;">
											<div class="input-group date qp-input-datepicker">
												<form:input path="valueForm[${status.index}].parameter" class="form-control"/>
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
											</div>
										</div>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 11}">
										<div style="width:90%; display:inline-block; vertical-align: middle;">
											<div class="input-group date qp-input-timepicker">
												<form:input path="valueForm[${status.index}].parameter" class="form-control"/>
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
											</div>
										</div>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 12 || designForm.valueForm[status.index].dataType == 18}">
										<div style="width:90%; display:inline-block; vertical-align: middle;">
											<div class="input-group date qp-input-datetimepicker">
												<form:input path="valueForm[${status.index}].parameter" class="form-control"/>
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
											</div>
										</div>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 9}">
										<form:input path="valueForm[${status.index}].parameter" qp-min="-128" qp-max="127" class="form-control pull-left qp-input-smallint" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 5}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-integer" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 6}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-smallint" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 7}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-bigint" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 13}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-serial" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 14}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-bigserial" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 4}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-float" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 15}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-bigserial" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 16}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-float" style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 17}">
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left qp-input-float"  style="width:90%"/>
									</c:when>
									<c:when test="${designForm.valueForm[status.index].dataType == 8}">
										<c:if test="${designForm.valueForm[status.index].parameter eq 'true'}">
											<div style="width:90%; display:inline-block; vertical-align: middle; text-align: center;">
												<input type="checkbox" checked="checked" onchange="$.qp.sqlbuilder.setValueForparam(this)"/>
												<form:hidden path="valueForm[${status.index}].parameter"/>
											</div>
										</c:if>
										<c:if test="${designForm.valueForm[status.index].parameter ne 'true'}">
											<div style="width:90%; display:inline-block; vertical-align: middle; text-align: center;">
												<input type="checkbox" onchange="$.qp.sqlbuilder.setValueForparam(this)"/>
												<form:hidden path="valueForm[${status.index}].parameter"/>
											</div>
										</c:if>
									</c:when>
									<c:otherwise>
										<form:input path="valueForm[${status.index}].parameter" class="form-control pull-left" style="width:90%"/>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<qp:autocomplete
										optionValue="optionValue" 
										selectSqlId="getAllTableDesignColumnAC" 
										value="${designForm.valueForm[status.index].parameter}"
										displayValue="${designForm.valueForm[status.index].parameter}"  
										name="valueForm[${status.index }].parameter" 
										optionLabel="optionLabel"
										onChangeEvent="$.qp.sqlbuilder.clearSqlScirpt"
										cssStyle="width:90%"
										cssClass="pull-left"></qp:autocomplete>
							</c:otherwise>
						</c:choose>
						<input type="hidden" value="${designForm.valueForm[status.index].parameter}"/>	
						<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
					</td>
					<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});"></a></td>
				</tr>
			</c:forEach>
		</table>
		<div class="qp-add-left">
			<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
		</div>
	</div>
</div>