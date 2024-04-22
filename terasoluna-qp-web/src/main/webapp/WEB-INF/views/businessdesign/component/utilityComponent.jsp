<div id="dialog-utility-setting" class="modal fade" style="display: none;">
<script id="div-parameter-template" type="text/x-jquery-tmpl">
    <tr name="trContent">
        <th type="0" ><qp:message code="sc.businesslogicdesign.0021" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0190" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
            <td type="0" colspan="3">
				<div class="input-group bd-content" style="width:100%" pattern="0" name="parameter" id="parameter">
					<qp:autocomplete name= "parameterId" optionLabel="optionLabel" selectSqlId="" cssStyle="width:100%" optionValue="optionValue" 
						arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
				</div>
            </td>
        <th type="1" ><qp:message code="sc.blogiccomponent.0080" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
			<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0189" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
             <td type="1" colspan="3">
                 <div class="input-group bd-content" style="width:100%" pattern="0" name="index" id="index">
                     <qp:autocomplete name = "indexId" cssStyle="width:100%" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                         arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
                 </div>
             </td>
     </tr>
</script>
<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
    <qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
        selectSqlId="" optionValue="optionValue" arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
</script>
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0148" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl-utility">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" name="label" maxlength="200"></td>
								</tr>
								<tr>
									<th><qp:message code="sc.sys.0061" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td colspan="3">
                        				<div class="bd-content" style="height:100%; width:100%; vertical-align: middle;" pattern="0" name="target">
                        					<qp:autocomplete name = "targetId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                        						arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBDFilterArray" onChangeEvent="onchangeTargetOfUtilitySet"></qp:autocomplete>
                        				</div>
                                    </td>
								</tr>
                                <tr>
                                    <th><qp:message code="sc.blogiccomponent.0057" /></th>
                                    <td colspan="3">
                                    <select class="form-control qp-input-select" onchange="onchangeTypeOfUtility(this)" name="type">
                                             <c:forEach var="itemValue" items="${CL_UL_TYPE}">
                                                 <option value="${itemValue.key}"><qp:message code="${itemValue.value}"></qp:message></option>
                                            </c:forEach>
                                    </select></td>
                                </tr>
                                <tr name="trContent">
                                    <th type="0" ><qp:message code="sc.businesslogicdesign.0021" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0190" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
                                    <td type="0" colspan="3">
										<div class="input-group bd-content" style="width:100%" pattern="0" name="parameter" id="parameter">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" cssStyle="width:100%"
							                	arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
							            </div>
                                    </td>
                                    <th type="1" ><qp:message code="sc.blogiccomponent.0080" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
                                    <a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0189" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
                                    <td type="1" colspan="3">
                                        <div class="input-group bd-content" style="width:100%" pattern="0" name="index" id="index">
                            				<qp:autocomplete name = "indexId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                            					arg01="in,ob,ou" arg02="true" mustMatch="false" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
                                        </div>
                                    </td>
                                </tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064" /></th>
									<td colspan="3"><textarea rows="2" cols="1" name="remark" class="form-control" maxlength="2000"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)">
						<qp:message code="sc.sys.0008"></qp:message>
					</button>
					<button type="button" class="btn qp-button-client " onclick="saveModalUtilitySetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>