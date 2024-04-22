<div class="modal-dialog" style="min-height: 500px;width: 800px;">
					<div class="modal-content">
						<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  
						<div class="modal-body">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#modal-section-setting-properties"
									data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a></li>
									<li><a href="#modal-section-setting-event" data-toggle="tab"><qp:message code="sc.screendesign.0065"/></a></li>
								<li><a href="#modal-section-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>
							</ul>
							<div class="tab-content" style="min-height: 350px;">
								<div id="modal-section-setting-properties" class="tab-pane active">
								<input type="hidden" name="formcode" />
									<div class="panel panel-default qp-div-information">
										<div class="panel-heading">
											<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0104"/></span>
										</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table-form">
												<colgroup>
													<col width="40%">
													<col width="60%">
												</colgroup>
												<tbody>
													<tr>
														<th><qp:message code="sc.screendesign.0105"/></th>
														<td>
															<qp:autocomplete optionLabel="optionLabel" cssInput="qp-convention-name" optionValue="optionValue" mustMatch="false" name="tableCaption"  onSelectEvent='dialogButtonAreaSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														</td>
													</tr>
													<tr>
														<th>
															<qp:message code="sc.screendesign.0184"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
														</th>
														<td>
															<input type="text" class="form-control qp-input-text qp-convention-code" name="areaCode" />
														</td>
													</tr>
													<tr id="tableWidthUnitPosition">
														<th><qp:message code="sc.screendesign.0106"/></th>
														<td style="vertical-align: top;">
															<div style="width: 100px;float: left; margin-left: 4px;">
																<input type="text" class="qp-numeric-up-down" name="tableWidth" />
															</div>
															<div style="float: left;margin-left: 4px;">															
																<select id="tableWidthUnit" class="form-control qp-input-select" style="width: 60px; padding-top: 0.1em; padding-bottom: 0.4em;" name="tableWidthUnit" onchange="changeUnit(this)">
																	<option value="%"><qp:message code="sc.screendesign.0146"/></option>
																	<option value="px"><qp:message code="sc.screendesign.0147"/></option>
																</select>
															</div>
															<div style="float: left;margin-left: 4px;">
																<select name="tablePosition" class="form-control qp-input-select" style="width: 85px; padding-top: 0.1em; padding-bottom: 0.4em;">
																	<option value=""></option>
																	<option value="1"><qp:message code="sc.screendesign.0158"/></option>
																	<option value="3"><qp:message code="sc.screendesign.0159"/></option>
																</select>								
															</div>							
														</td>
													</tr>
													<tr>
														<th><qp:message code="sc.screendesign.0107"/></th>
														<td>
															<select  class="form-control" name='modal-section-setting-display-type'>
																<option value="0"><qp:message code="sc.screendesign.0144"/></option>
																<option value="1"><qp:message code="sc.screendesign.0145"/></option>
															</select>
														</td>
													</tr>
													<tr>
														<th><qp:message code="sc.screendesign.0108"/></th>
														<td>
															<select  class="form-control" name='direction'>
																<option value="0"><qp:message code="sc.screendesign.0158"/></option>
																<option value="1"><qp:message code="sc.screendesign.0159"/></option>
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
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0109"/></span>
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
															<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTable" emptyLabel="sc.sys.0030" optionValue="optionValue" name="tableCode" arg01="${screenDesignForm.projectId}" arg02="20" onChangeEvent="dialogLinkAreaSettingTableCodeOnChange"/>
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
								<div id="modal-section-setting-event" class="tab-pane">
									<div class="panel panel-default  qp-div-information">
									<div class="panel-heading">
										<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0219"/></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form" id="tbl_list_result"
											style="background-color: #FFFFFF">
											<colgroup>
												<col width="40%">
												<col width="40%">
												<col width="20%">
											</colgroup>
											<tr>
												<th><qp:message code="sc.screendesign.0215"/></th>
												<td><qp:message code="sc.screendesign.0242"/></td>
												<td>
													<button type="button" class="btn qp-button-client" onclick="addRequireConstraint(this);"><qp:message code="sc.screendesign.0243"/></button>
												</td>
											</tr>
										</table>
										<br />
										<div id="events-setting"></div>
										<br />
										</div>
									</div>
								</div>	
								<div id="modal-section-setting-style" class="tab-pane">
									<div class="panel panel-default  qp-div-information">
										<div class="panel-heading">
											<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0343"/></span>
										</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table-form">
												<colgroup>
													<col width="30%">
													<col width="70%">
												</colgroup>
												<tr>
												<th><qp:message code="sc.screendesign.0346"/></th>
												<td>
													<div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="panelStyle=color" class="form-control" type="text" value="">
													</div>
												</td>
											</tr>
											<tr style="font-size: ">
												<th><qp:message code="sc.screendesign.0347"/></th>
												<td>
													<div class="input-group">
														<input class="form-control qp-input-integer" name="panelStyle=font-size" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
													</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0348"/></th>
												<td>
													<select name="panelStyle=font-style" class="form-control">
														<option><qp:message code="sc.screendesign.0142"/></option>
														<option><qp:message code="sc.screendesign.0350"/></option>
													</select>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0352"/></th>
												<td><qp:autocomplete name="panelStyle=font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0362"/></th>
												<td>
												<div class="input-group">
													<input name="panelStyle=border-width" class="form-control qp-input-integer" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
												</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0363"/></th>
												<td>
													<qp:autocomplete name="panelStyle=border-style" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="borderStyle"></qp:autocomplete>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0443" /></th>
												<td><div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="panelStyle=background-color" class="form-control" type="text" value="">
													</div></td>
											</tr>
											<tr>
													<th><qp:message code="sc.screendesign.0444" /></th>
													<td>
														<div class="input-group">
															<input class="form-control qp-input-integer" name="panelStyle=height" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
														</div>
													</td>
												</tr>
											</table>
											
											</div>
										</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">							
							<button type="button" class="btn qp-button-client " onclick="saveDialogSectionSetting()"><qp:message code="sc.sys.0054"/></button>																				
						</div>
					</div>
					<!-- /.modal-content -->
				</div>