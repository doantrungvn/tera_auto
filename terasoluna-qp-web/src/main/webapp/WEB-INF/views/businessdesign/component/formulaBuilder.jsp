<!-- Dialog Formula builder -->
<div id="dialog-formula-setting" class="modal fade" style="display: none;" tabindex="0">
	<script id="tbl-formula-select-function-template" type="text/template">
    <option style="background-color: rgb(255, 200, 200)" value="\${functionMethodId}" 
        method='\${functionMethod}'>\${functionMethodCode}
    </option>
</script>
	<script id="tbl-formula-content-parameter-template" type="text/template">
<div class= 'form-inline qp-formula-component' contenteditable='false' type="\${type}">
    <div class='qp-formula-component-content' >
        <label>\${label}</label>
    </div>
    <input type="hidden" name="formulaitem" value="\${hiddenValue}" />
    <div class='qp-formula-component-remove' onclick='$.qp.formulabuilder.onRemoveComponent(this)'>-</div>
</div>
</script>
	<script id="tbl-formula-content-parameter-index-template" type="text/template">
<div class= 'form-inline qp-formula-component' contenteditable='false' type="\${type}">
    <div class='qp-formula-component-content' >
    </div>
    <input type="hidden" name="formulaitem" value="\${hiddenValue}" />
    <div class='qp-formula-component-remove' onclick='$.qp.formulabuilder.onRemoveComponent(this)'>-</div>
</div>
</script>
	<script id="tbl-formula-content-function-template" type="text/template">
<div class= 'form-inline qp-formula-component' contenteditable='false' type="17">
    <div class='qp-formula-component-content'  onclick ="$.qp.formulabuilder.onOpenFunctionFormulaSetting(this)">
    </div>
    <input type="hidden" name="formulaitem" value="" />
    <div class='qp-formula-component-remove' onclick='$.qp.formulabuilder.onRemoveComponent(this)'>-</div>
</div>
</script>
	<script id="tbl-formula-content-value-template" type="text/template">
<div class= 'form-inline qp-formula-component' type="\${type}">
    <div class='qp-formula-component-content' >
        <input type='text' maxlength='200' class='form-control qp-input-text' style='width: 100px;margin: 2px;' value="\${value}">
    </div>
    <input type="hidden" name="formulaitem" value="\${hiddenValue}" />
    <div class='qp-formula-component-remove' onclick='$.qp.formulabuilder.onRemoveComponent(this)'>-</div>
</div>
</script>
	<script id="tbl-scopeparameter-parent-template" type="text/template">
<li>
	<label class="tree-toggler nav-header" style="margin-bottom: 5px;margin-top: 5px;">\${label}</label>
	<ul class="nav  tree" style="display: none;" id="\${id}">
	</ul>
</li>
</script>
	<script id="tbl-scopeparameter-children-template" type="text/template">
<li>
    <a href="javascript:void(0)" onclick="$.qp.formulabuilder.onSelectComponentBean(this)" style="padding-left: \${paddingLeft};" >\${code}</a>
    <input type="hidden" name="detailparameter" value />
</li>
</script>
	<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
	<qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
		selectSqlId="" optionValue="optionValue" arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
</script>
	<input type="hidden" name="parameterItems" value="" />
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0102" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl_list_result">
							<colgroup>
								<col width="20%" />
								<col width="80%" />
							</colgroup>
							<tr>
								<th style="text-align: left"><qp:message code="sc.businesslogicdesign.0103" /></th>
								<th>
									<label style="float: left;"><qp:message code="sc.businesslogicdesign.0021" /></label>
									<div style="float: right; cursor: pointer; color: red;" onclick='$.qp.formulabuilder.onValidationFormula()'>
										&nbsp;&nbsp;
										<qp:message code="sc.businesslogicdesign.0104" />
									</div>
									<div style="float: right; cursor: pointer;" onclick="$.qp.formulabuilder.onRemoveAll('Formula')">
										<qp:message code="sc.businesslogicdesign.0105" />
									</div>
								</th>
							</tr>
							<tr>
								<td rowspan="2" style="vertical-align: top;">
									<div style="max-height: 392px; overflow-x: auto">
										<div style="width: 100%; text-align: left">
											<div class="well well-lg" style="background-color: #FFFFFF; background-image: none; height: 100%; margin-bottom: 0px; padding-top: 10px; padding-bottom: 10px; padding-left: 5px;">
												<ul class="nav " id="scopeParameter">
												</ul>
											</div>
										</div>
									</div>

								</td>
								<td>
									<div id="bdFormulaValueFormula" class="form-control qp-input-textarea qp-formula-container" contenteditable="true" style="height: 100%; min-height: 300px; overflow-x: auto;"></div>
								</td>
							</tr>
							<tr>
								<td>
									<table class="table table-bordered qp-table-form" id="tbl_list_result">
										<colgroup>
											<col width="20%" />
											<col width="20%" />
											<col width="20%" />
											<col width="20%" />
										</colgroup>
										<tr>
											<th style="text-align: left;" colspan="4"><qp:message code="sc.businesslogicdesign.0106" /></th>
										</tr>
										<tr>
											<td colspan="4" align="left">
												<div class="btn-group" role="group">
													<button type="button" style="font-size: 14px;" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'0')">+</button>
													<button type="button" style="font-size: 14px;" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'1')">-</button>
													<button type="button" style="font-size: 14px;" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'2')">*</button>
													<button type="button" style="font-size: 14px;" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'3')">/</button>
												</div>
												<div class="btn-group" role="group">
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'4')">
														<qp:message code="sc.businesslogicdesign.0107" />
													</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'5')">
														<qp:message code="sc.businesslogicdesign.0108" />
													</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'6')">
														<qp:message code="sc.businesslogicdesign.0109" />
													</button>
												</div>
												<div class="btn-group" role="group">
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'7')">=</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'8')">&lt;</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'9')">&lt;=</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'10')">&gt;</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'11')">&gt;=</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'12')">&lt;&gt;</button>
												</div>
												<div class="btn-group" role="group">
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'13')">(</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'14')">)</button>
												</div>
												<div class="btn-group" role="group">
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'15')">
														<qp:message code="sc.businesslogicdesign.0110" />
													</button>
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'16')">
														<qp:message code="sc.businesslogicdesign.0111" />
													</button>
<!-- 													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'23')"> -->
<!-- 														String -->
<!-- 													</button> -->
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'22')">Null</button>
												</div> <!-- HungHX -->
												<div class="btn-group" role="group">
													<button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.functions.openModal(this)">
														<qp:message code="sc.blogiccomponent.0077" />
													</button>
												</div>
                                                <div class="btn-group" role="group">
                                                    <button type="button" class="btn btn-default qp-formula-expression-component" onclick="$.qp.formulabuilder.onSelectComponent(this,'27')" component="27">
                                                        new Object()
                                                    </button>
                                                </div>
                                                 <select style="display: none; float: right; width: 140px;" class="form-control qp-input-select" onchange="$.qp.formulabuilder.onSelectFunctionOperators(event)" id="cbFunctionMaster">
													<option value='0' selected="selected"><qp:message code="sc.sys.0030" /></option>
													<optgroup label="Array">
														<option style="background-color: rgb(255, 200, 200)" value="1" method='{"functionMethodId":"1","functionMethodCode":"length","functionMasterCode":"Array","functionMethodInputs":[{"methodInputId":1,"methodInputCode":"index","dataType":4,"arrayFlg":false}],"functionMethodOutputs":[{"methodOutputId":1,"methodOutputCode":"length","dataType":2,"arrayFlg":false}]}'><qp:message code="sc.businesslogicdesign.0112" /></option>
														<option style="background-color: rgb(255, 200, 200)" value="2" method='{"functionMethodId":"2","functionMethodCode":"get","functionMasterCode":"Array","functionMethodInputs":[]}'><qp:message code="sc.businesslogicdesign.0113" /></option>
													</optgroup>
													<optgroup label="Date">
														<option style="background-color: rgb(255, 200, 200)" value="3" method='{"functionMethodId":"3","functionMethodCode":"getCurrentDate","functionMasterCode":"Date","functionMethodInputs":[]}'><qp:message code="sc.businesslogicdesign.0114" /></option>
													</optgroup>
											</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer" style="border-top: 0px;">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="$.qp.formulabuilder.deleteModalFormulaSetting(this)">
						<qp:message code="sc.sys.0008"></qp:message>
					</button>
					<button type="button" class="btn qp-button-client " onclick="$.qp.formulabuilder.saveModalFormulaSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Begin include function setting-->
<jsp:include page="functionSetting.jsp" />
<!-- End include function setting -->