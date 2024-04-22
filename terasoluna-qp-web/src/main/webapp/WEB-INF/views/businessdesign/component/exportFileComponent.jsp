<script id="tbl-exportfile-assign-parameter-template" type="text/x-jquery-tmpl">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2" style="width: 390px">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<label name="parameterIdAutocomplete"/>
					<input type="hidden" name="parameterId"/>
					<input type="hidden" name="dataType">
				</div>
			</div>
		</td>
		<td class="columnNo">
			<input type="text" class="form-control qp-input-integer" name="columnNo" value="">
		</td>
		<td>
			<div>
				<span name="btnopenExportFileColumnFormat" class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action pull-right" style="float: right; margin-right: 5px; cursor: pointer;" onclick="openExportFileColumnFormat(this)"></span> 
				<input type="hidden" name="columnFileFormat" value="" />
				<input type="hidden" name="isOnlyView" value="" />
			</div>
		</td>
	</tr>
</script>
<div id="dialog-exportfile-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0026" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl-exportfile">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label" maxlength="200"></td>
								</tr>
								<tr name="destinationPath">
									<th><qp:message code="sc.blogiccomponent.0027" />&nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label> <a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0187" />' data-html="true" data-toggle="tooltip" data-placement="top"></a>
									</th>
									<td colspan="3" class="form-inline">
										<div class="form-group">
											<span class="text-primary"><qp:message code="sc.blogiccomponent.0028" /></span> <input type="text" class="form-control qp-input-text" name="content" maxlength="200" style="width:500px"/> <input type="hidden" name="pathType" value="0" /> <input type="hidden" name="formulaDefinitionDetails" /> <a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForExportFile" targetValue="findTargetValueOfPathForExportFile" onaftersave="onAfterSaveFormulaOfPathForExportFile" onafterdelete="onAfterDeleteFormulaOfPathForExportFile" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0029" /></th>
									<td colspan="3">
										<div style="width: 5%" class="pull-left">
											<span title='<qp:message code="sc.blogiccomponent.0185" />' name="btnopenFileExportFormat" class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action pull-right qp-cursor" style="float: right; margin-right: 5px;" onclick="openExportFileFormat(this)"></span> 
											<input type="hidden" name="fileFormat" value="" /> <input type="hidden" name="isOnlyView" value="" />
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0082" />&nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td colspan="3">
										<table class="table table-bordered qp-table-list" id="tbl-exportfile-assign-parameter" data-ar-dataclass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputParameterOfAssignCallback">
											<colgroup>
												<col>
												<col width="350px">
												<col width="100%">
												<col width="40px">
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.businesslogicdesign.0036" /></th>
													<th><qp:message code="sc.businesslogicdesign.0038" /></th>
													<th><qp:message code="sc.blogiccomponent.0030" /></th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<tr data-ar-rgroup="" class="ar-dataRow" data-ar-templateid="tbl-exportfile-assign-parameter-template" data-ar-rindex="0" data-ar-rgroupindex="1">
													<td colspan="2" style="width: 390px; padding: 0px;">
														<div style="height: 100%">
															<div style="width: 40px; height: 100%; border-right-style: solid; border-right-width: 1px; border-right-color: rgb(221, 221, 221); text-align: center; padding-top: 5px; float: left;">
																<span class="ar-groupIndex">1</span>
															</div>
															<div class="pull-right" style="height: 100%; vertical-align: middle; padding: 3px 5px; text-align: left; width: 348px;">
																<!--  attribute -->
																<div class="input-group " style="width:100%">
																	<qp:autocomplete name="parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" arg01="ob" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD"  onSelectEvent="onchangeParameterOfPassObject" onChangeEvent="onchangeParameterOfPassObject"></qp:autocomplete>
																	<input type="hidden" name="dataType">
																</div>
															</div>
														</div>
													</td>
													<td></td>
													<td></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064" /></th>
									<td colspan="3"><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
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
					<button type="button" class="btn qp-button-client " onclick="saveModalExportFileSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Dialog file format setting -->
<div id="dialog-exportfileformat-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0031" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list" id="fileFormat" data-ar-callback='onAddNewMessageOfMessageParameterSet'>
								<colgroup>
									<col width="40%" />
									<col width="60%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.blogiccomponent.0032" /></th>
										<th><qp:message code="sc.blogiccomponent.0033" /> <a style="margin-left: 5px" class="glyphicon glyphicon-refresh qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0085" />' data-html="true" data-toggle="tooltip" data-placement="top" onclick="resetFileFormatExportFile(this)"></a>
                                        </th>
									</tr>
								</thead>
								<tbody>
									<tr style="text-align: left">
										<td><qp:message code="sc.blogiccomponent.0034" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0034" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td><select class="form-control qp-input-select" onchange="" name="fileEncoding">
												<option value='0' selected="selected">UTF-8</option>
												<option value='1'>UTF-16</option>
												<option value='2'>MS932</option>
										</select></td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0035" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0035" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td style="text-align: left;">
											<label><input type="radio" name="lineFeedCharType" value="0" class="qp-input-radio qp-input-radio-margin" checked="checked" onchange="onChangeLineFeedChar(this)"/><qp:message code="sc.blogiccomponent.0049" /></label></br>
											<label><input type="radio" name="lineFeedCharType" value="1" class="qp-input-radio qp-input-radio-margin" onchange="onChangeLineFeedChar(this)"/>\n</label></br>
											<div class="form-inline"><label><input type="radio" name="lineFeedCharType" value="-1" class="qp-input-radio qp-input-radio-margin" onchange="onChangeLineFeedChar(this)"/><qp:message code="sc.blogiccomponent.0050" />&nbsp;<input type="text" maxlength="50" class="form-control qp-input-text" name="lineFeedChar" value="" readonly="readonly"></label></div>
										</td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0037" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0037" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td style="text-align: left;">
											<label><input type="radio" name="encloseCharType" value="0" class="qp-input-radio qp-input-radio-margin" checked="checked" onchange="onChangeEncloseChar(this)"/>\u0000</label></br>
											<div class="form-inline"><label><input type="radio" name="encloseCharType" value="-1" class="qp-input-radio qp-input-radio-margin" onchange="onChangeEncloseChar(this)"/><qp:message code="sc.blogiccomponent.0050" /></label>&nbsp;<input type="text" maxlength="50" class="form-control qp-input-text" name="encloseChar" value="" readonly="readonly"></div>
										</td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0040" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0040" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td><input type="checkbox" name="overwriteFlg" class="pull-left"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<br />
			</div>
			<div class="modal-footer">
				<br />
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="saveModalExportFileFormatSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Dialog column format setting -->
<div id="dialog-exportfilecolumnformat-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0041" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list" id="fileFormat" data-ar-callback='onAddNewMessageOfMessageParameterSet'>
								<colgroup>
									<col width="40%" />
									<col width="60%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.blogiccomponent.0032" /></th>
										<th><qp:message code="sc.blogiccomponent.0033" />
                                        </th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0043" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0043" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td style="text-align: left">
										<label><input type="radio" name="paddingType" value="0" class="qp-input-radio qp-input-radio-margin" onchange="" checked="checked"/><qp:message code="sc.blogiccomponent.0046" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="paddingType" value="1" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0047" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="paddingType" value="2" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0048" /></label>
										</td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0044" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0044" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td style="text-align: left;">
											<label><input type="radio" name="paddingCharType" value="0" class="qp-input-radio qp-input-radio-margin" checked="checked" onchange="onChangePaddingChar(this)"/><qp:message code="sc.blogiccomponent.0049" /></label></br>
											<div class="form-inline"><label><input type="radio" name="paddingCharType" value="-1" class="qp-input-radio qp-input-radio-margin" onchange="onChangePaddingChar(this)"/><qp:message code="sc.blogiccomponent.0050" /></label><input type="text" class="form-control qp-input-text" name="paddingChar" value="" readonly="readonly"></div>
										</td>
									</tr>
									<tr>
										<td><qp:message code="sc.sys.0062" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.sys.0062" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td><input type="text" class="form-control qp-input-text" style="width: 100%" name="specifyByte" value="-1"></td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0045" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** Column format' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td><select class="form-control qp-input-select" onchange="" name="columnFormat">
												<option value='0' selected="selected">###,###,###</option>
												<option value='1'>yyyy/MM/dd</option>
										</select></td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0051" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0051" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td style="text-align: left">
										<label><input type="radio" name="trimType" value="0" class="qp-input-radio qp-input-radio-margin" onchange="" checked="checked"/><qp:message code="sc.blogiccomponent.0046" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="trimType" value="1" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0047" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="trimType" value="2" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0048" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="trimType" value="3" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0052" /></label>
										</td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0053" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0053" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td><input type="text" class="form-control qp-input-text" style="width: 100%" name="trimChar" value=""></td>
									</tr>
									<tr>
										<td><qp:message code="sc.blogiccomponent.0054" /><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='*** <qp:message code="sc.blogiccomponent.0054" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></td>
										<td style="text-align: left">
										<label><input type="radio" name="converter" value="0" class="qp-input-radio qp-input-radio-margin" onchange="" checked="checked"/><qp:message code="sc.blogiccomponent.0046" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="converter" value="1" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0055" />&nbsp;&nbsp;</label>
										<label><input type="radio" name="converter" value="2" class="qp-input-radio qp-input-radio-margin" onchange=""/><qp:message code="sc.blogiccomponent.0056" /></label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<br />
			</div>
			<div class="modal-footer">
				<br />
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="saveModalExportFileColumnFormatSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>