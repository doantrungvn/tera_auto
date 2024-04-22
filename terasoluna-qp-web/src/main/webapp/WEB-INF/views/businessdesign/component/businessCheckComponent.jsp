<!-- Dialog Business Check setting -->
<div id="dialog-businesscheck-setting" class="modal fade in" tabindex="-1" role="dialog" style="display: none;">
<script id="tbl-bcheck-function-template" type="text/template">
<div class="qp-bdesign-div-content" businessCheckType = "">
	<table class="table table-bordered qp-table-form" id="tbl_list_result" style="background-color: #FFFFFF">
		<colgroup>
			<col width="12%" />
			<col width="78%" />
		</colgroup>
		<tr>
			<th colspan="2">
				<div class="pull-left">
					<qp:message code="sc.businesslogicdesign.0069"/>
				</div>
				<div class="pull-right">					
					<span class="glyphicon glyphicon-remove-circle pull-right qp-bdesign-btn-setting qp-cursor" title='<qp:message code="sc.sys.0014" />' onclick="removeBusinessCheckOfBCheckSet(this)"></span>
					<a href="javascript:" style="font-size: 12px; cursor: move; margin-top: 3px;" class="glyphicon glyphicon-sort pull-right sortable" title='<qp:message code="sc.sys.0015" />'></a>
                </div>								
			</th>
		</tr>
		<tr>
			<td colspan="2" style="height: 100px;">
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
			<th><qp:message code="sc.businesslogicdesign.0072"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
			<td>
				<div style="width: 95%" class="pull-left">
					<qp:autocomplete name = "messageCode" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet" mustMatch="false" ></qp:autocomplete>
				</div>
				<div style="width: 5%" class="pull-right">
					<span title='<qp:message code="sc.blogiccomponent.0181" />' name="btnChooseParameter" class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action pull-right qp-cursor" style="float: right; margin-right: 5px; display:none" onclick="openParameterOfFeeback(this)" callback="onClickChooseParamMsg"></span>
					<input type="hidden" name="messageParameter" value=""/>
				</div>
			</td>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0073"/></th>
			<td><input type="checkbox" class="checkbox" name="abortFlg"/></td>
		</tr>
	</table>
</div>
</script>
<script id="tbl-bcheck-existence-template" type="text/template">
<div class="qp-bdesign-div-content">
	<table class="table table-bordered qp-table-form" id="tbl_list_result" style="background-color: #FFFFFF">
		<colgroup>
			<col width="12%" />
			<col width="78%" />
		</colgroup>
		<tr>
			<th colspan="2">
				<div class="pull-left">
					<qp:message code="sc.businesslogicdesign.0074"/>
				</div>
				<div class="pull-right">					
					<span class="glyphicon glyphicon-remove-circle pull-right qp-bdesign-btn-setting qp-cursor" title='<qp:message code="sc.sys.0014" />' onclick="removeBusinessCheckOfBCheckSet(this)"></span>
					<a href="javascript:" style="font-size: 12px; cursor: move; margin-top: 3px;" class="glyphicon glyphicon-sort pull-right sortable" title='<qp:message code="sc.sys.0015" />'></a>
				</div>
			</th>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0021"/></th>
			<td>
				<table class="table table-bordered qp-table-list-none-action" id="tbl-content">
					<colgroup>
						<col />
						<col width="20%" />
						<col width="80%" />
					</colgroup>
					<thead>
						<tr>
							<th><qp:message code="sc.businesslogicdesign.0036"/></th>
							<th><qp:message code="sc.businesslogicdesign.0075"/></th>
							<th><qp:message code="sc.businesslogicdesign.0076"/></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="qp-output-fixlength tableIndex">1</td>
							<td valign="top">
								<qp:autocomplete name = "tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" 
									arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20"  onChangeEvent="onChangeTableConditionOfBCheckSet"></qp:autocomplete>
							</td>
							<td valign="top">
								<table class="table table-bordered qp-table-list" style="border: none;" id="tbl-content-conditions"  data-ar-callback="onAddNewConditionOfBCheckSet">
									<colgroup>
										<col width="30%">
										<col width="28%">
										<col width="42%">
										<col width="40px">
									</colgroup>
									<tbody></tbody>
								</table>
								<div class="qp-div-action-table" style="margin-top:0px;margin-left:5px">
									<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this,'','tbl-content-conditions-template');"></a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0072"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
			<td>
				<div style="width: 95%" class="pull-left">
					<qp:autocomplete name = "messageCode" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet" mustMatch="false" ></qp:autocomplete>
				</div>
				<div style="width: 5%" class="pull-right">
					<span name="btnChooseParameter" title='<qp:message code="sc.blogiccomponent.0181" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action pull-right qp-cursor" style="float: right; margin-right: 5px; display:none" onclick="openParameterOfFeeback(this)" callback="onClickChooseParamMsg"></span>
					<input type="hidden" name="messageParameter" value=""/>
				</div>
			</td>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0073"/></th>
			<td><input type="checkbox" class="checkbox" name="abortFlg"/></td>
		</tr>
	</table>
</div>
</script>
<script id="tbl-bcheck-duplicated-template" type="text/template">
<div class="qp-bdesign-div-content">
	<table class="table table-bordered qp-table-form" id="tbl_list_result" style="background-color: #FFFFFF">
		<colgroup>
			<col width="12%" />
			<col width="78%" />
		</colgroup>
		<tr>
			<th colspan="2">
				<div class="pull-left">
					<qp:message code="sc.businesslogicdesign.0071"/>
				</div>
				<div class="pull-right">
					<span class="glyphicon glyphicon-remove-circle pull-right qp-bdesign-btn-setting qp-cursor" title='<qp:message code="sc.sys.0014" />' onclick="removeBusinessCheckOfBCheckSet(this)"></span>
					<a href="javascript:" style="font-size: 12px; cursor: move; margin-top: 3px;" class="glyphicon glyphicon-sort pull-right sortable" title='<qp:message code="sc.sys.0015" />'></a>
				</div>
			</th>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0021"/></th>
			<td>
				<table class="table table-bordered qp-table-list-none-action" id="tbl-content">
					<colgroup>
						<col />
						<col width="20%" />
						<col width="80%" />
					</colgroup>
					<thead>
						<tr>
							<th><qp:message code="sc.businesslogicdesign.0036"/></th>
							<th><qp:message code="sc.businesslogicdesign.0075"/></th>
							<th><qp:message code="sc.businesslogicdesign.0076"/></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="qp-output-fixlength tableIndex">1</td>
							<td valign="top">
								<qp:autocomplete name = "tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" 
									arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20"  onChangeEvent="onChangeTableConditionOfBCheckSet"></qp:autocomplete>
							</td>
							<td valign="top">
								<table class="table table-bordered qp-table-list" style="border: none;" id="tbl-content-conditions"  data-ar-callback="onAddNewConditionOfBCheckSet">
									<colgroup>
										<col width="30%">
										<col width="28%">
										<col width="42%">
										<col width="40px">
									</colgroup>
									<tbody></tbody>
								</table>
								<div class="qp-div-action-table" style="margin-top:0px;margin-left:5px">
									<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this,'','tbl-content-conditions-template');"></a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0072"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
			<td>
				<div style="width: 95%" class="pull-left">
					<qp:autocomplete name = "messageCode" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet" mustMatch="false" ></qp:autocomplete>
				</div>
				<div style="width: 5%" class="pull-right">
					<span name="btnChooseParameter" title='<qp:message code="sc.blogiccomponent.0181" />' class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action pull-right qp-cursor" style="float: right; margin-right: 5px; display:none" onclick="openParameterOfFeeback(this)" callback="onClickChooseParamMsg"></span>
					<input type="hidden" name="messageParameter" value=""/>
				</div>
			</td>
		</tr>
		<tr>
			<th><qp:message code="sc.businesslogicdesign.0073"/></th>
			<td><input type="checkbox" class="checkbox" name="abortFlg"/></td>
		</tr>
	</table>
</div>
</script>
<script id="tbl-content-conditions-template" type="text/template">
<tr>
	<td class="qp-bdesign-tr-same-div">
		<qp:autocomplete name = "columnId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetComlumnForBD" optionValue="optionValue" arg01="20" onChangeEvent="setDataTypeOfColumnBD"></qp:autocomplete>
	</td>
	<td class="qp-bdesign-tr-same-div">
		<select name="operatorCode" class="form-control qp-input-select pull-left" style="width:100%" onchange="">
			<c:forEach var="item" items="${CL_SQL_OPERATOR }">
				<c:if test="${item.key < 7}">
					<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
				</c:if>
			</c:forEach>
		</select>
	</td>
	<td class="form-inline qp-bdesign-tr-same-div">
		<div class="bd-content" style="width:100%" pattern="0" name="parameter">
			<qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
				arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
		</div>
	</td>
	<td class="qp-bdesign-tr-same-div"><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl-content-conditions',this);"></a></td>
</tr>
</script>
<script id="tbl-content-template" type="text/template">
<tr>
	<td class="qp-output-fixlength tableIndex">1</td>
	<td valign="top">
		<qp:autocomplete name = "tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet"></qp:autocomplete>
	</td>
	<td valign="top">
		<table class="table table-bordered qp-table-list" style="border: none;" id="tbl-content-conditions">
			<colgroup>
				<col width="30%">
				<col width="28%">
				<col width="42%">
				<col width="40px">
			</colgroup>
			<tr>
				<td class="qp-bdesign-tr-same-div">
					<qp:autocomplete name = "columnId" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet"></qp:autocomplete>
				</td>
				<td class="qp-bdesign-tr-same-div">=</td>
				<td class="qp-bdesign-tr-same-div">
					<qp:autocomplete name = "columnId" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackMessage" optionValue="optionValue" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeMessageOfFeebackSet"></qp:autocomplete>
				</td>
				<td class="qp-bdesign-tr-same-div"><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl-content-conditions',this);"></a></td>
			</tr>
		</table>
		<div class="qp-div-action-table">
			<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);"></a>
		</div>
	</td>
	<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl-content',this);"></a></td>
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
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0067"/></span>
					</div>
					<div class="panel-body" style="background-color: #EEEEEE">
						<table class="table table-bordered qp-table-form" id="tbl-bcheck-infor" style="background-color: #FFFFFF">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tr>
                               <th><qp:message code="sc.businesslogicdesign.0063"/></th>
								<td>
									<input type="text" class="form-control qp-input-text" name="label" maxlength="200"/>
								</td>
                                <th><qp:message code="sc.businesslogicdesign.0068"/></th>
								<td>
									<select class="form-control qp-input-select" name="businessCheckType">
										<option value='0' selected="selected"><qp:message code="sc.sys.0030"/></option>
										<option value='1'><qp:message code="sc.businesslogicdesign.0069"/></option>
										<option value='2'><qp:message code="sc.businesslogicdesign.0070"/></option>
										<option value='3'><qp:message code="sc.businesslogicdesign.0071"/></option>
									</select>
								</td>
                            </tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0064"/></th>
								<td colspan="3"><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
							</tr>
						</table>
						<br/>
						<div class="qp-div-action">
							<input type="button" value='<qp:message code="sc.sys.0013" />' class="screen-design-btn qp-button-client" style="width: 70px" onclick="addBusinessCheckOfBCheckSet(this)">
						</div>
						<div id="listBusinessCheck">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalBusinessCheckSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$('label.tree-toggler').click(function() {
		$(this).parent().children('ul.tree').toggle(300);
	});
</script>