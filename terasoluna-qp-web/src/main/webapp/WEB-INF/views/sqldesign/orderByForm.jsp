<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text"><qp:message code="sc.autocomplete.0044"></qp:message></span>
	</div>
	<div class="panel-body">
		<table class="table table-bordered qp-table-list-none-action" id="orderByForm" data-ar-callback="$.qp.sqlbuilder.setArgumentForAllTableAC">
			<colgroup>
				<col />
				<col width="50%"/>
				<col />
				<col width="35px"/>
			</colgroup>
			<thead>
				<tr>
					<th></th>
					<th><qp:message code="sc.sqldesign.0048" /></th>
					<th><qp:message code="sc.sqldesign.0049" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${designForm.orderByForm}" varStatus="status">
					<tr class="ar-dataRow">
						<td class="sortable"style="cursor: move;">
							<span style="margin-top: 3px;" class="qp-glyphicon glyphicon glyphicon-sort"></span>
						</td>
						<td>
							<form:hidden path="orderByForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
							<form:hidden path="orderByForm[${status.index }].orderId"/>
							<qp:autocomplete 
								optionValue="optionValue"
								optionLabel="optionLabel"
								value="${designForm.orderByForm[status.index].tableColumn}"
								displayValue="${designForm.orderByForm[status.index].tableColumnAutocomplete}"
								name="orderByForm[${status.index }].tableColumn"
								sourceType="local" 
								sourceCallback="$.qp.sqlbuilder.resultColumnSourceCallback"
								onChangeEvent="$.qp.sqlbuilder.orderByChange"
								cssClass="pull-left"
								cssStyle="width:95%"
								placeHolder="sc.sqldesign.0048"></qp:autocomplete>
							<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
						</td>
						<td>
						 	<form:select path="orderByForm[${status.index}].orderType" cssClass="form-control qp-input-select pull-left" cssStyle="width:95%">
								<form:options items="${CL_SQL_ORDER_DIRECTION }" />
							</form:select>
							<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
						</td>
						<td>
							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="qp-add-left">
			<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
		</div>
	</div>
</div>