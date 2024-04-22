<!-- Dialog email setting -->
<div id="dialog-email-setting" class="modal fade" style="display: none;">
    <script id="tlbTo-template" type="text/x-jquery-tmpl">
    <tr>
        <input type="hidden"/>
        <td colspan="3">
            <div class="vertical-midle-div">
                <input type="text" class="form-control qp-input-text" name="content" style="width: 100%;" maxlength="200"/> 
                <input type="hidden" name="type" value="0"/>
                <input type="hidden" name="formulaDefinitionDetails" /> 
                <a class="margin-btn-setting btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForEmail" targetValue="findTargetValueOfPathForEmail"  onaftersave="onAfterSaveFormulaOfPathForEmail" onafterdelete="onAfterDeleteFormulaOfPathForEmail" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
            </div>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.removeRowJS('tlbTo', this);" href="javascript:void(0)" title='<qp:message code="sc.sys.0014" />'></a></td>
    </tr>
    </script>
    <script id="tlbCC-template" type="text/x-jquery-tmpl">
    <tr>
        <input type="hidden"/>
        <td colspan="3">
            <div class="vertical-midle-div">
                <input type="text" class="form-control qp-input-text" name="content" style="width: 100%;" maxlength="200"/> 
                <input type="hidden" name="type" value="0"/>
                <input type="hidden" name="formulaDefinitionDetails" /> 
                <a class="margin-btn-setting btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForEmail" targetValue="findTargetValueOfPathForEmail"  onaftersave="onAfterSaveFormulaOfPathForEmail" onafterdelete="onAfterDeleteFormulaOfPathForEmail" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
            </div>
        </td>
        <td><a title='<qp:message code="sc.sys.0014" />' class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.removeRowJS('tlbCC', this);" href="javascript:void(0)"></a></td>
    </tr>
    </script>
    <script id="tlbBCC-template" type="text/x-jquery-tmpl">
    <tr>
        <input type="hidden"/>
        <td colspan="3">
            <div class="vertical-midle-div">
                <input type="text" class="form-control qp-input-text" name="content" style="width: 100%;" maxlength="200"/> 
                <input type="hidden" name="type" value="0"/>
                <input type="hidden" name="formulaDefinitionDetails" /> 
                <a class="margin-btn-setting btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForEmail" targetValue="findTargetValueOfPathForEmail"  onaftersave="onAfterSaveFormulaOfPathForEmail" onafterdelete="onAfterDeleteFormulaOfPathForEmail" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
            </div>
        </td>
        <td><a title='<qp:message code="sc.sys.0014" />' class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.removeRowJS('tlbBCC', this);" href="javascript:void(0)"></a></td>
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
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0143" /></span>
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
									<th><qp:message code="sc.businesslogicdesign.0063" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" name="label" maxlength="200"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0150" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0191" />' data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
									<td colspan="3">
                                        <table class="table table-bordered qp-table-form">
                                            <colgroup>
                                                <col width="10%" />
                                                <col width="80%" />
                                                <col width="10%" />
                                            </colgroup>
                                            <tbody>
                                                <tr>
                                                    <th><qp:message code="sc.blogiccomponent.0151" /></th>
                                                    <td colspan="3" class="form-inline">
                                                        <table class="table table-bordered" id="tlbTo">
                                                            <colgroup>
                                                                <col width="85%" />
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                            	<tr class="hidden"></tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>
                                                        <div class="qp-add-left">
                                                            <a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th><qp:message code="sc.blogiccomponent.0152" /></th>
                                                    <td colspan="3" class="form-inline">
                                                        <table class="table table-bordered" id="tlbCC">
                                                            <colgroup>
                                                                <col width="85%" />
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                            	<tr class="hidden"></tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>
                                                        <div class="qp-add-left">
                                                            <a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th><qp:message code="sc.blogiccomponent.0153" /></th>
                                                    <td colspan="3" class="form-inline">
                                                        <table class="table table-bordered" id="tlbBCC">
                                                            <colgroup>
                                                                <col width="85%" />
                                                                <col />
                                                            </colgroup>
                                                            <thead>
                                                            	<tr class="hidden"></tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>
                                                        <div class="qp-add-left">
                                                            <a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
								</tr>
                                <tr>
                                    <th><qp:message code="sc.blogiccomponent.0154" /></th>
                                    <td colspan="3">
                                        <select class="form-control qp-input-select" onchange="" name="priorityType">
												<option value='0' selected="selected"><qp:message code="sc.blogiccomponent.0155" /></option>
												<option value='1'><qp:message code="sc.blogiccomponent.0156" /></option>
												<option value='2'><qp:message code="sc.blogiccomponent.0157" /></option>
                                                <option value='3'><qp:message code="sc.blogiccomponent.0158" /></option>
                                                <option value='4'><qp:message code="sc.blogiccomponent.0159" /></option>
										</select>
                                    </td>
                                </tr>
                                <tr id="subject">
                                    <th><qp:message code="sc.blogiccomponent.0160" /></th>
                                    <td colspan="3">
                                    	<div class="vertical-midle-div">
                                    		<input type="text" class="form-control qp-input-text" name="content" maxlength="200"/> 
                                             <input type="hidden" name="type" value="0"/>
                                             <input type="hidden" name="formulaDefinitionDetails" /> 
                                    		 <a title='<qp:message code="sc.blogiccomponent.0180" />' class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action margin-btn-setting" targetLabel="findTargetInputOfPathForEmail" targetValue="findTargetValueOfPathForEmail"  onaftersave="onAfterSaveFormulaOfPathForEmail" onafterdelete="onAfterDeleteFormulaOfPathForEmail" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);"></a>
                                    	</div>
                                    </td>
                                </tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064" /></th>
									<td colspan="3"><textarea rows="2" cols="1" class="form-control qp-input-text" name="remark" maxlength="2000"></textarea></td>
								</tr>
							</tbody>
						</table>
						<br />
                        
                        <strong><qp:message code="sc.blogiccomponent.0161" /></strong>
                        <br />
						<textarea id="editor-content" style="width: 100%; height: 100px;" maxlength="2000"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)">
						<qp:message code="sc.sys.0008"></qp:message>
					</button>
					<button type="button" class="btn qp-button-client " onclick="saveModalEmailSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>