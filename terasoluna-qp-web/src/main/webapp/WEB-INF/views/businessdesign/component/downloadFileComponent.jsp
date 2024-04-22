<!-- Dialog Download File setting -->
<div id="dialog-downloadfile-setting" class="modal fade" style="display: none;">
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
						<span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0162"/></span>
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
									<th><qp:message code="sc.blogiccomponent.0163"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0192" /><br/><qp:message code="sc.blogiccomponent.0195" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
									<td colspan="3">
										<div class="input-group" style="width:100%">
							                <qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" cssStyle="width:100%"
							                	arg01="ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeTargetOfDownloadSet"></qp:autocomplete>
							            </div>
									</td>
								</tr>
								<tr id="fileName">
									<th><qp:message code="sc.blogiccomponent.0164"/><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0186" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
									<td colspan="3">
										<div class="vertical-midle-div">
											<input type="text" class="form-control qp-input-text" name="content" style="width: 100%" maxlength="200" /> 
	                                        <input type="hidden" name="type" value="0"/>
	                                        <input type="hidden" name="formulaDefinitionDetails" /> 
	                                        <a class="margin-btn-setting btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForDownload" targetValue="findTargetValueOfPathForDownload"  onaftersave="onAfterSaveFormulaOfPathForDownload" onafterdelete="onAfterDeleteFormulaOfPathForDownload" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
										</div>
                                    </td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"/></th>
									<td colspan="3"><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalDownloadFile(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>