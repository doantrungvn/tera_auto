<!-- Dialog log setting -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/media/js/codemirror/css/codemirror.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/media/js/codemirror/css/show-hint.css" />
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/codemirror.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/show-hint.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/matchbrackets.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/clike.js"></script>

<div id="dialog-log-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0144" /></span>
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
                                    <th><qp:message code="sc.businesslogicdesign.0233" /></th>
                                    <td colspan="3">
                                        <select class="form-control qp-input-select" onchange="" name="level">
											<option value='0' selected="selected"><qp:message code="sc.businesslogicdesign.0234" /></option>
											<option value='1'><qp:message code="sc.businesslogicdesign.0235" /></option>
											<option value='2'><qp:message code="sc.businesslogicdesign.0236" /></option>
                                            <option value='3'><qp:message code="sc.businesslogicdesign.0237" /></option>
                                            <option value='4'><qp:message code="sc.businesslogicdesign.0238" /></option>
										</select>
                                    </td>
                                </tr>
                                <tr>
                                    <th><qp:message code="sc.businesslogicdesign.0240" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                                    <td colspan="3">
                                    	<div class="vertical-midle-div">
                                    		<input type="text" class="form-control qp-input-text" style="width: 100%" name="content" maxlength="200"/> 
	                                        <input type="hidden" name="messageType" value="0"/>
	                                        <input type="hidden" name="formulaDefinitionDetails" /> 
	                                        <a class="margin-btn-setting btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" targetLabel="findTargetInputOfPathForLog" targetValue="findTargetValueOfPathForLog"  onaftersave="onAfterSaveFormulaOfPathForLog" onafterdelete="onAfterDeleteFormulaOfPathForLog" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic" onclick="$.qp.formulabuilder.initialDataForFormulaSetting(this);" title='<qp:message code="sc.blogiccomponent.0180" />'></a>
                                    	</div>
                                    </td>
                                </tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064" /></th>
									<td colspan="3"><textarea rows="2" cols="1" class="form-control qp-input-text" name="remark" maxlength="2000"></textarea></td>
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
					<button type="button" class="btn qp-button-client " onclick="saveModalLogSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>