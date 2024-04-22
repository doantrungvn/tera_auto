<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span
			class="qp-heading-text"><qp:message code="sc.autocomplete.0047"></qp:message></span>
	</div>
	<div class="panel-body">
		<table class="table table-borderless qp-table-form" id="groupByForm" data-ar-callback="$.qp.sqlbuilder.groupByFormCallback">
			<colgroup>
				<col width="30%"/>
				<col width="30%"/>
				<col/>
			</colgroup> 
			<tbody>
			<c:forEach items="${designForm.groupByForm}" varStatus="status">
				<tr class="ar-dataRow">
					<td>
						<form:hidden path="groupByForm[${status.index }].itemSeqNo" cssClass="ar-groupIndex"/>
						<form:hidden path="groupByForm[${status.index }].groupById"/>
						<qp:autocomplete
										onChangeEvent="$.qp.sqlbuilder.tableChangeToNextCell"  
										optionValue="optionValue" 
										selectSqlId="getAllTableDesignAC" 
										value="${designForm.groupByForm[status.index].tableId}"
										displayValue="${designForm.groupByForm[status.index].tableIdAutocomplete}"  
										name="groupByForm[${status.index }].tableId" 
										optionLabel="optionLabel"
										cssStyle="width:90%"
										cssClass="pull-left"></qp:autocomplete>
						<label class="qp-required-field pull-left">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
					</td>
					<td>
						<qp:autocomplete optionValue="optionValue" 
										selectSqlId="getAllTableDesignColumnAC"
										value="${designForm.groupByForm[status.index].columnId}"
										displayValue="${designForm.groupByForm[status.index].columnIdAutocomplete}"
										name="groupByForm[${status.index }].columnId"
										optionLabel="optionLabel"
										cssStyle="width:90%"
										cssClass="pull-left"></qp:autocomplete>
						<label class="qp-required-field pull-left">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
					</td>
					<td>
						<a style="cursor: move;" class="btn btn-default btn-xs glyphicon glyphicon-move qp-link-action sortable"></a>
						<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove GROUP BY" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
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