<div class="modal-dialog" style="min-height: 500px;width: 800px;">
					<div class="modal-content">
						<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  
						<div class="modal-body">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#modal-table-setting-properties"
									data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a></li>
								<li><a href="#modal-table-setting-event" data-toggle="tab"><qp:message code="sc.screendesign.0065"/></a></li>
								<li><a href="#modal-table-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339" /></a></li>
							</ul>
							<div class="tab-content" style="min-height: 350px;">
								<div id="modal-table-setting-properties" class="tab-pane active">
									<div class="panel panel-default qp-div-information">
										<input type="hidden" name="formcode" />
										<div class="panel-heading">
											<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0117"/> </span>
										</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table-form">
												<colgroup>
													<col width="40%">
													<col width="60%">
												</colgroup>
												<tbody>
													<tr>
														<th><qp:message code="sc.screendesign.0118"/></th>
														<td>
															<qp:autocomplete optionLabel="optionLabel" cssInput="qp-convention-name" optionValue="optionValue" mustMatch="false" name="tableCaption"  onSelectEvent='dialogButtonAreaSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														</td>
													</tr>
													<tr>
														<th><qp:message code="sc.screendesign.0184"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
														<td>
															<input type="text" class="form-control qp-input-text qp-convention-code" name="areaCode" />
														</td>
													</tr>
													<tr>
														<th><qp:message code="sc.screendesign.0456" /></th>
														<td>
															<label><input class="qp-input-radio-margin qp-input-radio" type="radio" name="areaCustomTypeField" value="0"><qp:message code="sc.screendesign.0142" /></label>
															<label><input class="qp-input-radio-margin qp-input-radio" type="radio" name="areaCustomTypeField" value="1" ><qp:message code="sc.screendesign.0459" /></label>
														</td>
													</tr>
													<tr id="tableWidthUnitPosition">
														<th><qp:message code="sc.screendesign.0119"/></th>
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
													<tr id="uniqueList" style="display: none;">
														<th><qp:message code="sc.screendesign.0122"/></th>
														<td>
															<table class="table">
																<tbody>
																	<tr>
																		<td style="border: none;" width="47%">
																			<input class="com-input-autocomplete-detail" value="Product No."></td>
																		<td width="3%" style="border: none;"><a class="ui-icon ui-icon-minus" title='<qp:message code="sc.sys.0014" />' style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																	</tr>
																</tbody>
															</table>
															<a class="ui-icon ui-icon-plus" title="Add new row" style="margin-top: 3px;" href="javascript:void(0)"></a>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="panel panel-default qp-div-select">
										<div class="panel-heading">
											<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0240"/></span>
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
													
												</tbody>
											</table>
											<div class="qp-add-left">
												<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);$.qp.initialConventionNameCode();" style="margin-top: 3px;" href="javascript:void(0)"></a>
											</div>
										</div>
									</div>
									</div>
									
								</div>
								<div id="modal-table-setting-event" class="tab-pane">
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
								<div id="modal-table-setting-style" class="tab-pane">
									<div class="panel panel-default  qp-div-information">
										<div class="panel-heading">
											<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
											<span class="pq-heading-text"><qp:message code="sc.screendesign.0365"/></span>
										</div>
										<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%">
												<col width="60%">
											</colgroup>
											<tr>
												<th colspan="2" style="background-color: #FAF5EE; text-align: left;"><qp:message code="sc.screendesign.0366"/></th>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0344"/></th>
												<td><span name="iconPreview"><qp:message code="sc.screendesign.0442" /></span>
												<input type="hidden" name="iconButton" />
												<span class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action" style="float: right; cursor: pointer;" onclick="settingIcon(this)"></span></td>
											</tr>
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
											<tr>
												<th colspan="2" style="background-color: #FAF5EE; text-align: left;"><qp:message code="sc.screendesign.0371"/></th>
											</tr>
											<tr>
												<th valign="bottom"><qp:message code="sc.screendesign.0372"/></th>
												<td>
													<select name="headerStyle=text-align" class="form-control">
														<option><qp:message code="sc.screendesign.0158"/></option>
														<option><qp:message code="sc.screendesign.0373"/></option>
														<option><qp:message code="sc.screendesign.0159"/></option>
													</select>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0346"/></th>
												<td>
													<div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="headerStyle=color" class="form-control" type="text" value="">
													</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0347"/></th>
												<td>
													<div class="input-group">
														<input class="form-control qp-input-integer" name="headerStyle=font-size" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
													</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0348"/></th>
												<td>
													<select name="headerStyle=font-style" class="form-control">
														<option><qp:message code="sc.screendesign.0142"/></option>
														<option><qp:message code="sc.screendesign.0350"/></option>
													</select>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0352"/></th>
												<td><qp:autocomplete name="headerStyle=font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0362"/></th>
												<td>
												<div class="input-group">
													<input name="headerStyle=border-width" class="form-control qp-input-integer" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
												</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0363"/></th>
												<td>
													<qp:autocomplete name="headerStyle=border-style" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="borderStyle"></qp:autocomplete>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0443" /></th>
												<td>
													<div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="headerStyle=background-color" class="form-control" type="text" value="">
													</div>
												</td>
											</tr>
											<tr>
													<th><qp:message code="sc.screendesign.0444" /></th>
													<td>
														<div class="input-group">
															<input class="form-control qp-input-integer" name="headerStyle=height" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
														</div>
													</td>
												</tr>
											<tr>
												<th colspan="2" style="background-color: #FAF5EE; text-align: left;"><qp:message code="sc.screendesign.0370"/></th>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0346"/></th>
												<td>
													<div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="inputStyle=color" class="form-control" type="text" value="">
													</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0347"/></th>
												<td>
													<div class="input-group">
														<input name="inputStyle=font-size" class="form-control qp-input-integer" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
													</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0348"/></th>
												<td>
													<select name="inputStyle=font-style" class="form-control">
														<option><qp:message code="sc.screendesign.0142"/></option>
														<option><qp:message code="sc.screendesign.0350"/></option>
													</select>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0352"/></th>
												<td><qp:autocomplete name="inputStyle=font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0362"/></th>
												<td>
												<div class="input-group">
													<input name="inputStyle=border-width" class="form-control qp-input-integer" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
												</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0363"/></th>
												<td>
													<qp:autocomplete name="inputStyle=border-style" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="borderStyle"></qp:autocomplete>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0443" /></th>
												<td><div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="inputStyle=background-color" class="form-control" type="text" value="">
													</div></td>
										</tr>
										</table>
											
											</div>
										</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" onclick="saveDialogTableSetting()" class="btn qp-button-client" value='<qp:message code="sc.sys.0054"></qp:message>'/>																		
						</div>
					</div>
					<!-- /.modal-content -->
				</div>