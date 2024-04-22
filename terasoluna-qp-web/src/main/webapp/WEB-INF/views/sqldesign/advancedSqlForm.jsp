<table class="table table-bordered qp-table-form" id="sqlEditForm">
	<colgroup>
		<col width="20%" />
		<col width="30%" />
		<col width="20%" />
		<col width="30%" />
	</colgroup>
	<tr>
		<th><qp:message code="sc.sqldesign.0020" /></th>
		<td>
		<form:select path="sqlDesignForm.sqlPattern" cssClass="form-control qp-input-select pull-left" onchange="$.qp.advancedsql.changeSqlPattern(this)">
			<form:options items="${CL_SQL_SQLPATTERN }" />
		</form:select>
<!-- 		<button	type="button" -->
<!-- 				id="insertSqlPatternButton" -->
<!-- 				class="btn btn-info qp-button-action pull-right"> -->
<!-- 				<span class="glyphicon glyphicon-circle-arrow-down"></span> -->
<!-- 				<span></span> -->
<!-- 		</button> -->
		</td>
		<th><qp:message code="sc.sqldesign.0026" /></th>
		<td>
		<qp:autocomplete selectSqlId="getAllTableDesignByProjectIdWithColumns" 
						optionLabel="optionLabel"
						optionValue="optionValue"
						cssStyle="width:90%;"
						cssClass="pull-left"></qp:autocomplete>
		<button	type="button"
				id="insertTableCodeButton"
				class="btn btn-info qp-button-action pull-right">
				<span class="glyphicon glyphicon-circle-arrow-down"></span>
				<span></span>
		</button>
	</tr>
	<tr>
		<th></th>
		<td></td>
		<th><qp:message code="sc.sqldesign.0025"></qp:message></th>
		<td>
			<div style="position:relative">
				<select class="form-control pull-left" style="width:90%" id="functionSelect">
					<c:forEach var="functionGroup" items="${sqlDesignFunctionGroups}" varStatus="status">
						<c:set var="groupCode"><qp:message code='${f:h(functionGroup.groupCode)}' /></c:set>
						<optgroup label="${groupCode}">
							<c:forEach var="function" items="${functionGroup.sqlDesignFunctions}" varStatus="nestedStatus">
								<option value="${status.index}${nestedStatus.index }" data-functionHint="${f:h(function.remark) }" data-functionText="${f:h(function.functionText) }">${f:h(function.functionName)}</option>
							</c:forEach>
						</optgroup>
					</c:forEach>
				</select>
				<a class="glyphicon glyphicon-info-sign qp-link-toggle" data-html="true" data-toggle="tooltip" data-placement="top" id="functionTooltip" style="position: absolute;right: 55px;top: 5px;"></a>
				<button	type="button"
					id="insertFunctionCodeButton"
					class="btn btn-info qp-button-action pull-right">
					<span class="glyphicon glyphicon-circle-arrow-down"></span>
					<span></span>
				</button>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<span id="lblPageableCodeOracle" hidden="true"></span>
			<form:textarea path="sqlDesignForm.sqlText" style="width: 100%; text-align: left; height: 400px" rows="6" />
			<span id="lblPageableCodeOracle2" hidden="true"></span>
			<p class="text-warning" style="margin-top:10px"><qp:message code="sc.sqldesign.0050"></qp:message></p>
			<div id="generatePageable" hidden="true">
				<a style="float: right;" href="javascript:void(0)" onclick="$.qp.advancedsql.removePageable()">Remove Pageable</a>
				<textarea rows="12" cols="20" id="txtPageableCode" style="width: 100%" readonly="readonly"></textarea>
				<form:hidden path="sqlDesignForm.pageable"/>
			</div>
		</td>
	</tr>
</table>
<div class="modal fade" id="sqlDesignConfirmDialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header qp-model-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title qp-model-header-title"><qp:message code="sc.sys.0038"/></h3>
			</div>
			<div class="modal-body qp-model-body">
			</div>
			<div class="modal-footer qp-model-footer">
				<button type="button" class="btn btn-primary" id="sqlDesignConfirmYes" data-dismiss="modal" tabindex="1"><qp:message code="sc.sys.0011"/></button>
				<button type="button" class="btn btn-default" id="sqlDesignConfirmNo" data-dismiss="modal" tabindex="2"><qp:message code="sc.sys.0012"/></button>
			</div>
		</div>
	</div>
</div>