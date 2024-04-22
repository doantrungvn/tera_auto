<!-- Dialog for advance setting -->
<div class="modal fade" id="settingDefaultValue" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog" style="width: 800px;">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0254"/></span>
					</div>
					<div class="panel-body" >
						<table class="table table-bordered qp-table-form" id="tableDialogFormAutocomplete">
							<colgroup>
								<col width="40%" /><col width="60%" />
							</colgroup>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0255"/></th>
								<td>
									<label style="font-weight: normal; width: 100%">
										<input type="radio" value="1" class="qp-input-radio qp-input-radio-margin" name="defaultValue" />
										<qp:message code="sc.screendesign.0256"/>
									</label>
									<label style="font-weight: normal; width: 100%">
										<input type="radio" value="2" class="qp-input-radio qp-input-radio-margin" name="defaultValue" />
										<qp:message code="sc.screendesign.0257"/>
									</label>
									<label style="font-weight: normal; width: 100%">
										<input type="radio" value="3" class="qp-input-radio qp-input-radio-margin" name="defaultValue" />
										<qp:message code="sc.screendesign.0258"/>
									</label>
									<label style="font-weight: normal; width: 100%">
										<input type="radio" value="4" class="qp-input-radio qp-input-radio-margin" name="defaultValue" />
										<qp:message code="sc.screendesign.0259"/>
									</label>
									<label style="font-weight: normal; width: 100%">
										<input type="radio" value="5" class="qp-input-radio qp-input-radio-margin" name="defaultValue" />
										<qp:message code="sc.screendesign.0207"/>
									</label>
								</td>
							</tr>
						</table>							
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveAdvanceSetting" class="btn qp-button-client" onclick="saveDialogSettingDefault();"><qp:message code="sc.sys.0054"/></button>
				</div>
			</div>
		</div>
	</div>
</div>