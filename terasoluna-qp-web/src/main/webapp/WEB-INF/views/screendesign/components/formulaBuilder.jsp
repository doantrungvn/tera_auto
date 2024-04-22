<div class="modal fade in" id="dialog-formula-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <input type="hidden" value="" name="parameterItems">
	<div class="modal-dialog modal-weight-lg">
		<div class="modal-content">
			<div style="border-bottom: 0px;" class="modal-header qp-model-header">
				<button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
			</div>
			<div class="modal-body">
				<br>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
						<span class="pq-heading-text">Formula setting</span>
					</div>
					<div class="panel-body">
						<table id="tbl_list_result" class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%">
								<col width="80%">
							</colgroup>
							<tbody>
							<tr>
								<th style="text-align: left;">
									Condition name</th>
								<td>
									<input class="form-control qp-input-text" type="text" id="formulaName">
								</td>
							</tr>
							<tr>
								<th style="text-align: left;">
									Item scope</th>
								<th style="text-align: left;">Content<div onclick="$.qp.formulabuilder.onValidationFormula()" style="float: right; cursor: pointer; color: red;">&nbsp;&nbsp; Validate formula</div>
									<div onclick="$.qp.formulabuilder.onRemoveAll('Formula')" style="float: right; cursor: pointer;">
										<a> Clear all component</a>
									</div>
								</th>
							</tr>
							<tr>
							 	<td style="vertical-align: top;" rowspan="2">
                                    <div style="width: 100%; text-align: left">
                                        <div style="background-color: rgb(255, 255, 255); background-image: none; height: 100%; margin-bottom: 0px; padding-top: 10px; padding-bottom: 10px; padding-left: 5px;" class="well well-lg">
                                            <ul id="scopeParameter" class="nav ">
                                            </ul>
                                        </div>
                                    </div>
							 	</td>
								<td>
									<div contenteditable="true" style="height:100%;min-height: 300px" class="form-control qp-input-textarea qp-formula-container" id="bdFormulaValueFormula"></div>
								</td>
							</tr>
							<tr>
								<td>
									<table id="tbl_list_result" class="table table-bordered qp-table-form">
			                            <colgroup>
			                                <col width="20%">
			                                <col width="20%">
			                                <col width="20%">
			                                <col width="20%">
			                            </colgroup>
			                            <tbody><tr>
			                                <th colspan="4" style="text-align: left;">Expressions</th>
			                            </tr>
			                            <tr>
			                                <td align="left" colspan="4">
			                                    <div role="group" class="btn-group">
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'0')" class="btn btn-default qp-formula-expression-component" style="font-size: 14px;" type="button">+</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'1')" class="btn btn-default qp-formula-expression-component" style="font-size: 14px;" type="button">-</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'2')" class="btn btn-default qp-formula-expression-component" style="font-size: 14px;" type="button">*</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'3')" class="btn btn-default qp-formula-expression-component" style="font-size: 14px;" type="button">/</button>
			                                    </div>
			                                    <div role="group" class="btn-group">
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'4')" class="btn btn-default qp-formula-expression-component" type="button">and</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'5')" class="btn btn-default qp-formula-expression-component" type="button">or</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'6')" class="btn btn-default qp-formula-expression-component" type="button">not</button>
			                                    </div>
			                                    <div role="group" class="btn-group">
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'7')" class="btn btn-default qp-formula-expression-component" type="button">=</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'8')" class="btn btn-default qp-formula-expression-component" type="button">&lt;</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'9')" class="btn btn-default qp-formula-expression-component" type="button">&lt;=</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'10')" class="btn btn-default qp-formula-expression-component" type="button">&gt;</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'11')" class="btn btn-default qp-formula-expression-component" type="button">&gt;=</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'12')" class="btn btn-default qp-formula-expression-component" type="button">&lt;&gt;</button>
			                                    </div>
			                                    <div role="group" class="btn-group">
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'13')" class="btn btn-default qp-formula-expression-component" type="button">(</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'14')" class="btn btn-default qp-formula-expression-component" type="button">)</button>
			                                    </div>
			                                    <div role="group" class="btn-group">
                                                    <button onclick="$.qp.formulabuilder.onSelectComponent(this,'15')" class="btn btn-default qp-formula-expression-component" type="button">Empty</button>
			                                        <button onclick="$.qp.formulabuilder.onSelectComponent(this,'16')" class="btn btn-default qp-formula-expression-component" type="button">Value</button>
			                                    </div>
			                                    
			                                    <!-- HungHX -->
			                                    <div role="group" class="btn-group">
                                                    <button onclick="$.qp.functions.openModal(this)" class="btn btn-default qp-formula-expression-component" type="button">Function</button>
			                                    </div> 
			                                    
		                                        <select  style="display:none; float: right; width: 140px;" class="form-control qp-input-select" onchange="$.qp.formulabuilder.onSelectFunctionOperators(event)" id="cbFunctionMaster"></select>
			                                </td>
			                            </tr>
			                        </tbody></table>
								</td>
							</tr>
						</tbody></table>
					</div>
				</div>
			</div>
			<div style="border-top: 0px;" class="modal-footer">
				<div class="qp-div-action">
                    <button onclick="$.qp.formulabuilder.deleteModalFormulaSetting(this)" class="btn qp-button-client " type="button">Delete</button>
                    <button onclick="$.qp.setConditionName()" class="btn qp-button-client " type="button">Save</button>
                </div>
			</div>
		</div>
	</div>
</div>
<script id="tbl-formula-content-parameter-template" type="text/template">
<div class= 'form-inline qp-formula-component' contenteditable='false' type="\${type}">
    <div class='qp-formula-component-content' >
        \${label}
    </div>
    <input type="hidden" name="formulaitem" value="\${hiddenValue}" />
    <div class='qp-formula-component-remove' onclick='$.qp.formulabuilder.onRemoveComponent(this)'>-</div>
</div>
</script>
<script id="tbl-formula-content-value-template" type="text/template">
<div class= 'form-inline qp-formula-component' type="\${type}">
    <div class='qp-formula-component-content' >
        <input type='text' class='form-control qp-input-text' style='width: 50px;margin: 2px;' value="\${value}">
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
<script id="tbl-formula-select-function-template" type="text/template">
    <option style="background-color: rgb(255, 200, 200)" value="\${functionMethodId}" 
        method='\${functionMethod}'>\${functionMethodCode}
    </option>
</script>