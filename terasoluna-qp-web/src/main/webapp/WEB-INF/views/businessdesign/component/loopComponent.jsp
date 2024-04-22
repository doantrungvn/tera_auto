<!-- Dialog For each setting -->
<div id="dialog-loop-setting" class="modal fade" style="display: none;">

<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
    <qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
        selectSqlId="" optionValue="optionValue" arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
</script>
<script id="div-from-template" type="text/x-jquery-tmpl">
	<div class="bd-content" style="width:100%" pattern="0" id="from" name="from">                             
		<qp:autocomplete name = "fromValue" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
			arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
	</div>
</script>
<script id="div-to-template" type="text/x-jquery-tmpl">
	<div class="bd-content" style="width:100%" pattern="0" id="to" name="to">
		<qp:autocomplete name = "toValue" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
	    	arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
	</div>
</script>
<script id="div-parameter-template" type="text/x-jquery-tmpl">
    <tr name="trContent" class="bd-for-type-foreach">
		<th><qp:message code="sc.businesslogicdesign.0246"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
		<td>
        	<div class="input-group">
				<div class="bd-content" style="width:100%" pattern="0" id="from" name="from">
                	<qp:autocomplete name = "fromValue" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                		arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
                </div>
				<span class="input-group-addon">      
				<label><input type="checkbox" aria-label="Array" name="fromType" onchange="onchooseLengthOfLoop(this)"><qp:message code="sc.businesslogicdesign.0112"/></label></span>    
			</div>
		</td>
									
		<th><qp:message code="sc.businesslogicdesign.0247"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
		<td>
			<div class="input-group">
				<div class="bd-content" style="width:100%" pattern="0" id="to" name="to">
                	<qp:autocomplete name = "toValue" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                    	arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
                </div>
                <span class="input-group-addon">
				<label><input type="checkbox" aria-label="Array" name="toType" onchange="onchooseLengthOfLoop(this)"><qp:message code="sc.businesslogicdesign.0112"/></label></span>    
			</div>
		</td>
	</tr>
</script>
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
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0121"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063"/></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label" maxlength="200"></td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0122"/> &nbsp; <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<select class="form-control qp-input-select" onchange="onchangeTypeOfLoop(this)" name="loopType">
												<option value='0' selected="selected"><qp:message code="sc.businesslogicdesign.0123"/></option>
												<option value='1'><qp:message code="sc.businesslogicdesign.0124"/></option>
										</select>
									</td>
									<th colspan="2"></th>
								</tr>
								<tr class="bd-for-type-foreach">
									<th><qp:message code="sc.businesslogicdesign.0125"/></th>
									<td>
										<div class="input-group" style="width:100%">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
							                	arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onChangeAutoObjectOfLoop"></qp:autocomplete>
							            </div>
									</td>
									<th colspan="2"></th>
									
								</tr>
								<tr class="bd-for-type-foreach">
									<th><qp:message code="sc.blogiccomponent.0080" /></th>
									<td>
										<input type="text" class="form-control qp-input-text" style="width: 100%" name="index" maxlength="50">
									</td>
									<th>
										<label><qp:message code="sc.blogiccomponent.0147"/></label>
									</th>
									<td class="form-inline">
										<div class="input-group  pull-left" style="width : 48%">
											<select class="form-control qp-input-select"  name="loopStepType" style="border-radius: 4px;">
												<option value='0' selected="selected"><qp:message code="sc.blogiccomponent.0145"/></option>
												<option value='1'><qp:message code="sc.blogiccomponent.0146"/></option>
											</select>
										</div>
										<div class="input-group  pull-right" style="width : 48%">
											<input type="text" name="loopStepValue" class="form-control qp-input-integer" style="border-radius: 4px;"/>
										</div>
									</td>
								</tr>
								<tr name="trContent" class="bd-for-type-foreach">
									<th><qp:message code="sc.businesslogicdesign.0246"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
                                        <div class="input-group">
											<div class="bd-content" style="width:100%" pattern="0" id="from" name="from">
	                            				<qp:autocomplete name = "fromValue" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
	                            					arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
                                        	</div>
											<span class="input-group-addon">      
											<label><input type="checkbox" aria-label="Array" name="fromType" onchange="onchooseLengthOfLoop(this)"><qp:message code="sc.businesslogicdesign.0112"/></label></span>    
										</div>
									</td>
									
									<th><qp:message code="sc.businesslogicdesign.0247"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td >
										<div class="input-group">
											<div class="bd-content" style="width:100%" pattern="0" id="to" name="to">
	                            				<qp:autocomplete name = "toValue" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
	                            					arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
                                        	</div>
                                        	<span class="input-group-addon">
											<label><input type="checkbox" aria-label="Array" name="toType" onchange="onchooseLengthOfLoop(this)"><qp:message code="sc.businesslogicdesign.0112"/></label></span>    
										</div>
									</td>
								</tr>
								<tr class="bd-for-type-while" style="vertical-align: middle;">
									<th><qp:message code="sc.businesslogicdesign.0076"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td colspan="3">
										<div style="width: 100%">
											<label class="qp-output-text" name="formulaDefinitionContent"></label> 
											<input type="hidden" name="formulaDefinitionDetails"/> 
											<span style="margin-left: 5px; margin-right: 5px" class="pull-right">
												<a title='<qp:message code="sc.blogiccomponent.0180" />' href="javascript:" style="margin-top: 3px;" class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetLabelOfFormula" targetValue="findTargetValueOfFormula" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" href="javascript:void(0)"></a>
											</span>
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