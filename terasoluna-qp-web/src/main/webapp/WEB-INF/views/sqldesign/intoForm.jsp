<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text"><qp:message code="sc.sqldesign.0032" /></span>
	</div>
	<div class="panel-body">
		<table class="table table-borderless qp-table-form" id="intoForm">
			<colgroup>
				<col width="265px">
				<col>
				<col width="35px">
			</colgroup>
			<tbody>
				<tr data-ar-rindex="0" class="ar-dataRow">
					<td colspan="2">
						<div class="qp-div-highlight-border">
							<table class="table table-borderless join-conditions-table" data-ar-tlevel="0" data-ar-callback="$.qp.sqlbuilder.fromFormCallback">
								<colgroup>
									<col width="25%">
									<col width="5%">
									<col width="25%">
									<col width="8%">
									<col width="25%">
								</colgroup>
								<tbody>
									<tr>
										<td>
											<qp:autocomplete optionValue="optionValue"
												selectSqlId="getAllTableDesignByProjectIdWithColumns" 
												name="intoForm.tableId" 
												value="${designForm.intoForm.tableId}"
												displayValue="${designForm.intoForm.tableIdAutocomplete}"
												optionLabel="optionLabel"
												cssClass="pull-left${designForm.intoForm.tableMissingFlag eq '1'?' qp-missing':'' }"
												cssStyle="width:90%"
												onChangeEvent="$.qp.sqlbuilder.intoTableChange"
												></qp:autocomplete>
											<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
											<form:hidden path="intoForm.dataTypeText" />
											<form:hidden path="intoForm.sqlDesignTableId"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<p class="text-warning" style="margin-top:10px"><qp:message code="sc.autocomplete.0004"></qp:message></p>
	</div>
</div>