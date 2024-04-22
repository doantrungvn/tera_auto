<!-- Dialog assign setting -->
<div id="dialog-assign-setting" class="modal fade in" tabindex="-1" role="dialog" style="display: none;">
<script id="tbl-assign-parameter-template" type="text/x-jquery-tmpl">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2" style="width: 340px">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right bd-content" style="height:100%;vertical-align: middle;" pattern="0" name="target">
					<qp:autocomplete name = "targetId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
						arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeTargetOfAssignSet"></qp:autocomplete>
					<input type="hidden" name="dataType">
				</div>
			</div>
		</td>
		
		<td style="text-align: center;" class="btn-getobject">
			<a class="btn btn-info btn-xs glyphicon glyphicon-circle-arrow-down qp-button-action" onclick="ongetPropetyOfObjectAssignSet(this)" title="Get object" ></a>
		</td>
		<td>
			<div class="input-group bd-content" style="width:100%" pattern="0" name="parameter">
				<qp:autocomplete name = "parameterId" mustMatch="false" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
					arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfAssignSet"></qp:autocomplete>
            </div>
		</td>
		
		<td style="text-align: center;" class="btn-setting">
            <input type="hidden" name="assignType" value="0"/> 
            <input type="hidden" name="formulaDefinitionDetails"/> 
			<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetLabelOfFormulaForAssign" formulaType="0" targetValue="findTargetValueOfFormulaForAssign" onbeforeopen="onBeforeOpenFormulaForAssign" onaftersave="onAfterSaveFormulaForAssign" onafterdelete="onAfterDeleteFormulaForAssign" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />' ></a>
		</td>
		<td class="btn-remove">
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
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
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0062"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl-assign-infor">
							<colgroup>
								<col width="20%" />
								<col width="80%" />
							</colgroup>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0063"/></th>
								<td><input type="text" class="form-control qp-input-text" name="label" maxlength="200"/></td>
							</tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0064"/></th>
								<td><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
							</tr>
						</table>
						<br />
						<table class="table table-bordered qp-table-list" id="tbl-assign-parameter"  data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputParameterOfAssignCallback">
							<colgroup>
								<col />
								<col width="300px"/>
								<col width="40px"/>
								<col width="" />
								<col width="40px"/>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0036"/></th>
									<th><qp:message code="sc.businesslogicdesign.0038"/></th>
									<th></th>
									<th><qp:message code="sc.businesslogicdesign.0065"/></th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
						<div class="qp-div-action-table">
							<a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'tbl-assign-parameter',templateId:'tbl-assign-parameter-template',templateData:{groupId:''}});" title='<qp:message code="sc.businesslogicdesign.0200" />'></a>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalAssignSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>
