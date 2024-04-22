<div id="dialog-function-detail-setting" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
<script id="tbl-function-detail-input-template" type="text/template">
    <tr>
        <td class="com-output-fixlength tableIndex">\${index}</td>
        <td><input type="hidden" name="methodInputId" value="\${methodInputId}" /><label class="qp-output-text" name="methodInputCode">\${methodInputCode}</label></td>
        <td><label class="qp-output-text" name="dataType">\${dataTypeStr}</label></td>
        <td>
            <label><input type="radio" name="parameterScope\${index}" checked onchange="$.qp.formulabuilder.onChangeParameterScopeOfFunction(this)" value="0">Value</label>
            <label><input type="radio" name="parameterScope\${index}" onchange="$.qp.formulabuilder.onChangeParameterScopeOfFunction(this)" value="1">Parameter</label>
        </td>
        <td type="value"><input type="text" class="form-control qp-input-text" name="parameterValue"/></td>
        <td type="parameter" style="display:none">
            <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue"
                sourceType="local" sourceCallback="$.qp.formulabuilder.getDataSourceAutocompleteFormula" onChangeEvent="$.qp.formulabuilder.setParameterTypeOfAutocompleteFormula">
            </qp:autocomplete>
        </td>
    </tr>
</script>
<script id="tbl-function-detail-output-template" type="text/template">
    <tr>
        <td class="com-output-fixlength tableIndex">\${index}</td>
        <td><input type="hidden" name="methodOutputId" value="\${methodOutputId}" /><label class="qp-output-text" name="methodOutputCode">\${methodOutputCode}</label></td>
        <td><label class="qp-output-text" name="dataType">\${dataTypeStr}</label></td>
        <td>
            <input type="radio" name="outputSelect">
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
