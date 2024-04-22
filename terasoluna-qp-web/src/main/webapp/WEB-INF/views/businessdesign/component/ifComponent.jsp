<script id="tbl-ifcondition-template" type="text/template">
	<tr>
		<td class="qp-output-fixlength tableIndex">1</td>
		<td><input type="text" class="form-control" name="caption" maxlength="50"/></td>
		<td>
            <div style="width: 100%">
                <label class="qp-output-text" name="formulaDefinitionContent"></label> 
                <input type="hidden" name="formulaDefinitionDetails"/> 
                <span style="margin-left: 5px; margin-right: 5px" class="pull-right">
                    <a title='<qp:message code="sc.blogiccomponent.0180" />' href="javascript:" style="margin-top: -2px;" class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetLabelOfFormula" targetValue="findTargetValueOfFormula" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" href="javascript:void(0)"></a>
                </span>
            </div>
        </td>
		<td><input type="text" class="form-control" name="conditionRemark" maxlength="2000" /></td>
		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJSEx(this,false);" style="margin-top: -2px;" href="javascript:void(0)"></a></td>
	</tr>
</script>
<!-- Dialog If setting -->
<div id="dialog-if-setting" class="modal fade" style="display: none;">
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
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0120"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
					    		<col width="80%" />
							</colgroup>
							<tbody>
						    	<tr>
							        <th><qp:message code="sc.businesslogicdesign.0063"/></th>
							        <td class="form-inline">
							            <input type="text" style="width: 100%" class="form-control qp-input-text" name="label" maxlength="200"/>
							        </td>
							    </tr>
							    <tr>
							        <th><qp:message code="sc.businesslogicdesign.0064"/></th>
							        <td class="form-inline">
							            <textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea>
							        </td>
							    </tr>
							</tbody>
                        </table>
					    <br/>
						<table class="table table-bordered qp-table-list" id="tbl-ifcondition" data-ar-callback="updateIndexOfCondtionDetail">
							<colgroup>
								<col />
								<col width="20%" />
								<col width="50%" />
								<col width="30%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0036"/></th>
									<th><qp:message code="sc.screendesign.0105"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<th><qp:message code="sc.businesslogicdesign.0076"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<th><qp:message code="sc.databasedesign.0013"/></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<table class="table table-bordered qp-table-list" id="tbl-elsecondition" style="border: 0px;">
							<colgroup>
								<col />
								<col width="20%" />
								<col width="50%" />
								<col width="30%" />
								<col />
							</colgroup>
							<tbody>
								<tr>
									<td class="qp-output-fixlength tableIndex" style="border-top: 0px;" name="indexOfElse">1</td>
									<td style="border-top: 0px;"><label name="caption"><qp:message code="sc.blogiccomponent.0078" /></label></td>
									<td style="border-top: 0px;">
							            <div style="width: 100%">
							                <label class="qp-output-text" name="formulaDefinitionContent"><qp:message code="sc.blogiccomponent.0079" /></label> 
							                <input type="hidden" name="formulaDefinitionDetails"/>
							            </div>
							        </td>
									<td style="border-top: 0px;"><input type="text" class="form-control" name="conditionRemark" /></td>
									<td style="border-top: 0px;">
										<label><input name="usedConditionFlg" class="qp-input-checkbox-margin qp-input-checkbox" type="checkbox" checked="checked" onchange="onchangeStatusOfElseCondition(this)" ></label>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="qp-add-left">
	                    	<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this,'tbl-ifcondition','tbl-ifcondition-template');" style="margin-top: 3px;" href="javascript:void(0)"></a>
	                   	</div>
						<br />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalIfSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>