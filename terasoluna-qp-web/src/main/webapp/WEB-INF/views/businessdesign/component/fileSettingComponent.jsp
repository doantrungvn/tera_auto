<!-- Dialog transaction setting -->
<div id="dialog-filesetting-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0064" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl-filesetting">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063"/></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0057" /></th>
									<td colspan="3">
										<select class="form-control qp-input-select" onchange="onchangeTypeOfFileSetting(this)" name="type">
												<option value='0' selected="selected"><qp:message code="sc.blogiccomponent.0065" /></option>
												<option value='1'><qp:message code="sc.blogiccomponent.0066" /></option>
												<option value='2'><qp:message code="sc.blogiccomponent.0067" /></option>
												<option value='3'><qp:message code="sc.blogiccomponent.0068" /></option>
												<option value='4'><qp:message code="sc.blogiccomponent.0069" /></option>
												<option value='5'><qp:message code="sc.blogiccomponent.0070" /></option>
										</select>
									</td>
								</tr>
								<tr type="0">
									<th><qp:message code="sc.blogiccomponent.0071" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
									
								</tr>
								<tr type="0">
									<th><qp:message code="sc.blogiccomponent.0072" /></th>
									<td>
										<label><input name="transactionType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1" checked="checked"><qp:message code="sc.blogiccomponent.0073" /></label>
										<label><input name="transactionType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1" checked="checked"><qp:message code="sc.blogiccomponent.0040" /></label>
									</td>
									<th><qp:message code="sc.blogiccomponent.0074" /></th>
									<td>
										<div class="input-group" style="width:100%">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
							                	arg01="in,ob" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onChangeAutoObjectOfLoop"></qp:autocomplete>
							            </div>
									</td>
								</tr>
								<tr type="1">
									<th><qp:message code="sc.blogiccomponent.0060" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr type="1">
									<th><qp:message code="sc.blogiccomponent.0061" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr type="2">
									<th><qp:message code="sc.blogiccomponent.0060" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr type="3">
									<th><qp:message code="sc.blogiccomponent.0060" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr type="3">
									<th>Assign to</th>
									<td colspan="1">
										<div class="input-group" style="width:100%">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
							                	arg01="in,ob" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onChangeAutoObjectOfLoop"></qp:autocomplete>
							            </div>
									</td>
								</tr>
								<tr type="4">
									<th><qp:message code="sc.blogiccomponent.0060" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr type="4">
									<th><qp:message code="sc.blogiccomponent.0075" /></th>
									<td><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
									<th><qp:message code="sc.blogiccomponent.0076" /></th>
									<td colspan="1">
										<div class="input-group" style="width:100%">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
							                	arg01="in,ob" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onChangeAutoObjectOfLoop"></qp:autocomplete>
							            </div>
									</td>
								</tr>
								<tr type="5">
									<th><qp:message code="sc.blogiccomponent.0060" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label"></td>
								</tr>
								<tr type="5">
									<th><qp:message code="sc.blogiccomponent.0076" /></th>
									<td colspan="1">
										<div class="input-group" style="width:100%">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
							                	arg01="in,ob" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onChangeAutoObjectOfLoop"></qp:autocomplete>
							            </div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"/></th>
									<td colspan="3"><textarea rows="2" cols="1" style="width: 100%" name="remark"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalLoop(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>