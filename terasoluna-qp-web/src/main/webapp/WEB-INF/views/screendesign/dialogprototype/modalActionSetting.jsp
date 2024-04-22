<div class="modal-dialog" style="min-height: 500px;width: 800px;">
					<div class="modal-content">
						<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  
						<div class="modal-body">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#modal-action-setting-properties"
									data-toggle="tab"><qp:message code="sc.screendesign.0064"></qp:message></a></li>
							</ul>
							<div class="tab-content" style="min-height: 350px;">
								<div id="modal-action-setting-properties" class="tab-pane active">
									<div class="panel panel-default qp-div-information">
										<div class="panel-heading">
											<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0104"></qp:message></span>
										</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table-form">
												<colgroup>
													<col width="40%">
													<col width="60%">
												</colgroup>
												<tbody>
													<tr style="display: none;">
														<th><qp:message code="sc.screendesign.0105"></qp:message></th>
														<td><qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="tableCaption"  onSelectEvent='dialogButtonAreaSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"></qp:autocomplete></td>
													</tr>
													<tr>
														<th>
															<qp:message code="sc.screendesign.0184"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
														</th>
														<td>
															<input type="text" class="form-control qp-input-text" name="areaCode" />
														</td>
													</tr>
													<tr id="tableWidthUnitPosition">
														<th><qp:message code="sc.screendesign.0106"></qp:message></th>
														<td style="vertical-align: top;">
															<div style="width: 100px;float: left; margin-left: 4px;">
																<input type="text" class="qp-numeric-up-down" name="tableWidth" />
															</div>
															<div style="float: left;margin-left: 4px;">															
																<select id="tableWidthUnit" class="form-control qp-input-select" style="width: 60px; padding-top: 0.1em; padding-bottom: 0.4em;" name="tableWidthUnit" onchange="changeUnit(this)">
																	<option value="%"><qp:message code="sc.screendesign.0146"></qp:message></option>
																	<option value="px"><qp:message code="sc.screendesign.0147"></qp:message></option>
																</select>
															</div>
															<div style="float: left;margin-left: 4px;">
																<select name="tablePosition" class="form-control qp-input-select" style="width: 85px; padding-top: 0.1em; padding-bottom: 0.4em;">
																	<option value=""></option>
																	<option value="1"><qp:message code="sc.screendesign.0158"></qp:message></option>
																	<option value="3"><qp:message code="sc.screendesign.0159"></qp:message></option>
																</select>								
															</div>							
														</td>
													</tr>
													<tr>
														<th><qp:message code="sc.screendesign.0107"></qp:message></th>
														<td>
															<select  class="form-control" name='modal-section-setting-display-type'>
																<option value="0" selected="selected"><qp:message code="sc.screendesign.0144"></qp:message></option>
																<option value="1"><qp:message code="sc.screendesign.0145"></qp:message></option>
															</select>
														</td>
													</tr>
													<tr>
														<th><qp:message code="sc.screendesign.0108"></qp:message></th>
														<td>
															<select  class="form-control" name='direction'>
																<option value="0"><qp:message code="sc.screendesign.0158"></qp:message></option>
																<option value="1" selected="selected"><qp:message code="sc.screendesign.0159"></qp:message></option>
															</select>
														</td>
													</tr>													
												</tbody>
											</table>
										</div>
									</div>
									<div class="panel panel-default qp-div-select">
										<div class="panel-heading">
											<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0109"></qp:message></span>
										</div>
										<div class="panel-body">
											<table id="tbl-hiddenAttibutes" class="table table-bordered qp-table-list">
												<colgroup>
													<col width="10%">
													<col width="30%">
													<col width="30%">
													<col width="20%">
													<col width="10%">
												</colgroup>
												<thead>
													<tr>
														<th><qp:message code="sc.sys.0004"/></th>
														<th><qp:message code="sc.screendesign.0194"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
														<th><qp:message code="sc.screendesign.0093"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
														<th><qp:message code="sc.decisiontable.0017"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
														<th>&nbsp;</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td class="com-output-fixlength tableIndex">1</td>
														<td>
															<input type="text" class="form-control qp-input-text" name="parameterAttribute" maxlength="100">
														</td>
														<td>
															<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTable" emptyLabel="sc.sys.0030" optionValue="optionValue" name="tableCode" arg01="${screenDesignForm.projectId}" arg02="20" onChangeEvent="dialogLinkAreaSettingTableCodeOnChange"></qp:autocomplete>
														</td>
														<td class="com-output-fixlength"><a href="javascript:" style="margin-top: 3px;" onclick="removeParameterJS(this);" class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove row"></a></td>
													</tr>
												</tbody>
											</table>
											<div class="qp-add-left">
												<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);$.qp.initialConventionNameCode();" style="margin-top: 3px;" href="javascript:void(0)"></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">							
							<button type="button" class="btn qp-button-client " onclick="saveDialogActionAreaSetting()"><qp:message code="sc.sys.0054"></qp:message></button>																				
						</div>
					</div>
					<!-- /.modal-content -->
				</div>