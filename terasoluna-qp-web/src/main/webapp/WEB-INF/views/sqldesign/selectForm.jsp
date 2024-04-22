<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text"><qp:message code="sc.autocomplete.0045" /></span>
	</div>
	<div class="panel-body">
		<form:checkbox path="sqlDesignForm.omitOverlap"/> 
		<label for="sqlDesignForm.omitOverlap1"><qp:message code="sc.autocomplete.0051"></qp:message></label>
		<br>
		<br>
		<a class="qp-link-toggle pull-right" id="showHideUncheckedColumnsAnchor" data-toggle="show"><qp:message code="sc.sqldesign.0028" /></a>
		<table class="table table-bordered qp-table-list-none-action" id="selectForm" data-ar-indexchange="$.qp.sqlbuilder.selectFormIndexChangeCallback" data-ar-callback="$.qp.sqlbuilder.selectFormCallback" style="margin-top:10px">
			<colgroup>
				<col />
				<col />
				<col width="30%"/>
				<col width="30%"/>
			</colgroup>
			<thead>
				<tr>
					<th>
						<input type="checkbox" id="checkAllAnchor" />
					</th>
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
				<tr class="ar-dataRow" data-ar-rgroupindex="${designForm.selectForm[status.index ].itemSeqNo}" data-tableid="${designForm.selectForm[status.index].tableId}">
					<td><form:checkbox path="selectForm[${status.index }].isSelected" onchange="$.qp.sqlbuilder.selectFunctionCodeChange(this);"/></td>
					<td>
						<form:hidden path="selectForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
						<form:hidden path="selectForm[${status.index }].resultId"/>
						<form:hidden path="selectForm[${status.index }].tableId" />
						<form:hidden path="selectForm[${status.index }].tableType" />
						<form:hidden path="selectForm[${status.index }].tableIdAutocomplete" />
						<span class="${designForm.selectForm[status.index].tableMissingFlag eq '1'?'form-control qp-missing':'' }">${designForm.selectForm[status.index].tableIdAutocomplete }</span>
					</td>
					<td>
						<form:hidden path="selectForm[${status.index }].columnId" />
						<form:hidden path="selectForm[${status.index }].columnIdAutocomplete" />
						<form:hidden path="selectForm[${status.index }].columnCode" />
						<form:hidden path="selectForm[${status.index }].dataType" />
						<label name="dataTypeBackup" value="${designForm.selectForm[status.index].dataTypeBackup}" style="display: none;"></label>
						<span class="${designForm.selectForm[status.index].columnMissingFlag eq '1'?'form-control qp-missing':'' }">${designForm.selectForm[status.index].columnIdAutocomplete }</span>
					</td>
					<td>
						<form:select path="selectForm[${status.index }].functionCode" cssClass="form-control qp-input-select" onchange="$.qp.sqlbuilder.selectFunctionCodeChange(this);">
							<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
							<form:options items="${CL_SQL_AGGREGATE_FUNCTIONS }" />
						</form:select>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>