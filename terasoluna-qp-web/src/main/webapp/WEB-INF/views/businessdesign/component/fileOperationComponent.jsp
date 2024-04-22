<!-- Dialog transaction setting -->
<div id="dialog-fileoperation-setting" class="modal fade" style="display: none;">

	<script id="tlbMergedFile-template" type="text/x-jquery-tmpl">
	<tr>
		<input type="hidden"/>
		<td colspan="3" class="form-inline">
			<div class="form-group">
				<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
				<input type="text" class="form-control qp-input-text" name="content" /> 
				<input type="hidden" name="pathType" value="0"/>
				<input type="hidden" name="formulaDefinitionDetails" /> 
				<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
			</div>
		</td>
		<td><a
			class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action"
			onclick="$.qp.removeRowJS('tlbMergedFile', this);"
			href="javascript:void(0)"></a></td>
	</tr>
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
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0125" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl-fileoperation">
							<colgroup>
								<col width="20%" />
								<col width="80%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063" /></th>
									<td><input type="text" class="form-control qp-input-text" name="label" maxlength="200"></td>
								</tr>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0057" /></th>
									<td><select class="form-control qp-input-select" onchange="onchangeTypeOfFileOperation(this)" name="type">
											<option value='0' selected="selected"><qp:message code="sc.sys.0017" /></option>
											<option value='1'><qp:message code="sc.sys.0008" /></option>
											<option value='2'><qp:message code="sc.blogiccomponent.0058" /></option>
											<option value='3'><qp:message code="sc.blogiccomponent.0059" /></option>
									</select></td>
								</tr>
								<tr type="0" name="sourcePath">
									<th><qp:message code="sc.blogiccomponent.0060" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
										</div>
									</td>
								</tr>
								<tr type="0" name="destinationPath">
									<th><qp:message code="sc.blogiccomponent.0061" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
										</div>
									</td>
								</tr>
								<tr type="1" name="sourcePath">
									<th><qp:message code="sc.blogiccomponent.0060" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
										</div>
									</td>
								</tr>
								<tr type="2" name="sourcePath">
									<th><qp:message code="sc.blogiccomponent.0060" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
										</div>
									</td>
								</tr>
								<tr type="2" name="newFilename">
									<th><qp:message code="sc.blogiccomponent.0062" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
										</div>
									</td>
								</tr>
								<tr type="3" name="sourcePath">
									<th><qp:message code="sc.blogiccomponent.0060" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
										</div>
									</td>
								</tr>
								<tr type="3" name="destinationPath">
									<th><qp:message code="sc.blogiccomponent.0061" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
											<input type="text" class="form-control qp-input-text" name="content" maxlength="200"> 
											<input type="hidden" name="pathType" value="0"/>
											<input type="hidden" name="formulaDefinitionDetails" /> 
											<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
										</div>
									</td>
								</tr>
								<tr type="3">
									<th><qp:message code="sc.blogiccomponent.0063" /> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<table class="table table-bordered qp-table-list-none-action" id="tlbMergedFile">
											<colgroup>
												<col width="85%" />
												<col />
											</colgroup>
											<tbody>
												<tr>
													<input type="hidden"/>
													<td colspan="3" class="form-inline">
														<div class="form-group">
															<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> 
															<input type="text" class="form-control qp-input-text" name="content" maxlength="200"/> 
															<input type="hidden" name="pathType" value="0"/>
															<input type="hidden" name="formulaDefinitionDetails" /> 
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForFileOperation" targetValue="findTargetValueOfPathForFileOperation"  onaftersave="onAfterSaveFormulaOfPathForFileOperation" onafterdelete="onAfterDeleteFormulaOfPathForFileOperation" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title="Setting"></a>
														</div>
													</td>
													<td></td>
												</tr>
											</tbody>
										</table>
										<div class="qp-add-left">
											<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064" /></th>
									<td><textarea class="form-control" rows="2" cols="1" name="remark" maxlength="2000"></textarea></td>
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
					<button type="button" class="btn qp-button-client " onclick="saveModalFileOperationSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>