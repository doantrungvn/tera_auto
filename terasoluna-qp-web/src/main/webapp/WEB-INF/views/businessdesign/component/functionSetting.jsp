<div id="dialog-function-detail-setting" class="modal fade in" tabindex="-1" role="dialog" style="display: none;">
<script id="tbl-function-detail-input-template" type="text/template">
    <tr>
        <td class="com-output-fixlength tableIndex">\${tableIndex}</td>
        <td><input type="hidden" name="methodInputId" value="\${methodInputId}" /><label class="qp-output-text" name="methodInputCode">\${methodInputCode}</label></td>
        <td>
			<input type="hidden" name="dataType" value="\${dataType}" />
			<input type="hidden" name="arrayFlg" value="\${arrayFlg}" />
			<label class="qp-output-text" name="dataType">\${dataTypeStr}</label></td>
        <td>
            <label><input type="radio" name="parameterScope\${index}" checked onchange="$.qp.formulabuilder.onChangeParameterScopeOfFunction(this)" value="1">Value</label>
            <label><input type="radio" name="parameterScope\${index}" onchange="$.qp.formulabuilder.onChangeParameterScopeOfFunction(this)" value="2">Parameter</label>
        </td>
        <td type="value"><input type="text" class="form-control qp-input-text" name="parameterValue"/></td>
		<td type="parameter" style="display:none" class="form-inline">
             <div class="input-group bd-content" style="width:100%" pattern="0" name="parameter" id="parameter">
                 <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                     arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
             </div>
        </td>
    </tr>
</script>
<script id="tbl-function-detail-output-template" type="text/template">
    <tr>
        <td class="com-output-fixlength tableIndex">\${tableIndex}</td>
        <td>
			<input type="hidden" name="methodOutputCode" value="\${methodOutputCode}" />
			<input type="hidden" name="dataType" value="\${dataType}" />
			<input type="hidden" name="arrayFlg" value="\${arrayFlg}" />
			<input type="hidden" name="methodOutputId" value="\${methodOutputId}" />
			<label class="qp-output-text" name="methodOutputCode">\${methodOutputCode}</label></td>
        <td><label class="qp-output-text" name="dataType">\${dataTypeStr}</label></td>
        <td>
            <input type="radio" name="outputSelect">
        </td>
    </tr>
</script>
<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
    <qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
        selectSqlId="" optionValue="optionValue" arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
</script>
<script type="text/javascript">
    var CL_FORMULA_BUILDER_DATATYPE = [];
    <c:forEach items="${CL_BD_DATATYPE}" var="item">
       CL_FORMULA_BUILDER_DATATYPE['${item.key}'] = '${item.value}';
    </c:forEach>
</script>
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header qp-model-header" style="border-bottom: 0px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <br/>
                <table class="table table-bordered qp-table-form" name="methodInfo" id="tblMethod-infor" data-ar-tlevel="0" >
                	<colgroup>
						<col width="20%">
						<col width="30%">
						<col width="20%">
						<col width="30%">
					</colgroup>
					<tbody>
						<tr><th style="text-align: left; vertical-align: middle;" colspan="4"><qp:message code="sc.functionmaster.0032" /></th></tr><tr>
						<tr>
							<th><qp:message code="sc.functionmaster.0027" /></th>
							<td class="word-wrap"><span id="lblFunctionMethodName"></span></td>
							<th><qp:message code="sc.functionmaster.0028" /></th>
							<td class="word-wrap"><span id="lblFunctionMethodCode"></span></td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0028" /></th>
							<td colspan="3"><span id="lblFunctionMethodRemark"></span></td>
						</tr>
						<tr>
							<td colspan="4">
								<br/>
								 <div class="panel panel-default  qp-div-information">
				                    <div class="panel-heading">
				                        <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
				                        <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0115"/></span>
				                    </div>
				                    <div class="panel-body">
				                        <table class="table table-bordered qp-table-list-none-action" id="tbl-function-detail-input"  data-ar-dataClass="ar-dataRow">
				                            <colgroup>
				                                <col />
				                                <col width="25%"/>
				                                <col width="25%"/>
				                                <col width="25%"/>
				                                <col width="25%"/>
				                            </colgroup>
				                            <thead>
				                                <tr>
				                                    <th><qp:message code="sc.businesslogicdesign.0036"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0038"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0039"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0116"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0117"/></th>
				                                </tr>
				                            </thead>
				                            <tbody>
				                            </tbody>
				                        </table>
				                    </div>
				                </div>
				                <br/>
				                <div class="panel panel-default  qp-div-information">
				                    <div class="panel-heading">
				                        <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
				                        <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0118"/></span>
				                    </div>
				                    <div class="panel-body">
				                        <table class="table table-bordered qp-table-list" id="tbl-function-detail-output"  data-ar-dataClass="ar-dataRow">
				                            <colgroup>
				                                <col />
				                                <col width="50%"/>
				                                <col width="50%"/>
				                                <col width="100px"/>
				                                <col/>
				                            </colgroup>
				                            <thead>
				                                <tr>
				                                    <th><qp:message code="sc.businesslogicdesign.0036"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0038"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0039"/></th>
				                                    <th><qp:message code="sc.businesslogicdesign.0119"/></th>
				                                </tr>
				                            </thead>
				                            <tbody>
				                            </tbody>
				                        </table>
				                    </div>
				                </div>
								
							</td>
						</tr>
					</tbody>
                </table>
            </div>
            <div class="modal-footer">
                <div class="qp-div-action">
                    <button type="button" class="btn qp-button-client " onclick="$.qp.formulabuilder.deleteFunctionFormulaSetting(this)"><qp:message code="sc.sys.0008"></qp:message></button>
                    <button type="button" class="btn qp-button-client " onclick="$.qp.formulabuilder.onSaveFunctionFormulaSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
                </div>
            </div>
        </div>
    </div>
</div>
