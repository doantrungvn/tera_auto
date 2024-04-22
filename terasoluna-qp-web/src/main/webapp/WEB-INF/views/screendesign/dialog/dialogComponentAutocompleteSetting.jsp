<div class="modal-dialog" style="min-height: 500px;width: 800px;">
		    	<div class="modal-content">
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  	     
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-component-autocomplete-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a>
							</li>
							<li><a href="#dialog-component-autocomplete-setting-autocomplete-setting" data-toggle="tab"><qp:message code="sc.screendesign.0198"/></a></li>
							<li><a href="#dialog-component-autocomplete-setting-autocomplete-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>
						</ul>
						<div class="tab-content" >
							<div id="dialog-component-autocomplete-setting-properties" class="tab-pane active">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0081"></qp:message></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form" id="tableDialogFormAutocomplete">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>
											<tbody>
											<tr>
												<th><qp:message code="sc.screendesign.0199"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>																							
												<td>													
													<div style="float: left; width: 80%; margin-right: 4px;">
														<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="labelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														<input type="hidden" name="isBundle" value="true" /> 	
													</div>												
													<div class="vertical-middle level-text"></div>												
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0093"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><input type="text" class="form-control qp-input-text qp-validate-required qp-convention-code" name="columnname" maxlength="200"/></td>
											</tr>
											<tr>
												<td colspan="2"></td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0085"/></th>
												<td>													
													<div style="float: left; width: 100px; margin-right: 4px;"><input name="dialog-component-setting-element-width" type="text" class="qp-numeric-up-down form-control" /></div>
													<div style="float: left;margin-left: 4px;">
														<select class="form-control qp-input-select" style="width: 60px; padding-top: 0.1em; padding-bottom: 0.4em; margin-bottom: 4px;" name="dialog-component-setting-element-width-unit">
															<option value="%"><qp:message code="sc.screendesign.0146"/></option>
															<option value="px"><qp:message code="sc.screendesign.0147"/></option>
														</select>
													</div>
												</td>
											</tr>	
											<tr id="trRequire">
												<th><qp:message code="sc.screendesign.0100"/></th>
												<td><input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="mandatory" value="true"/></td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0086"/></th>
												<td baseType="dialog-autocomplete-setting-base-types">
													
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0074"/></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input name="colspan" type="text" class="qp-numeric-up-down form-control" /></div>												
													<div class="vertical-middle"><qp:message code="sc.screendesign.0075"/></div>	
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0076"/></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input type="text" name="rowspan" class="qp-numeric-up-down form-control" /></div>												
													<div class="vertical-middle"><qp:message code="sc.screendesign.0077"/></div> 
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0191"/></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input type="text" name="tabindex" class="qp-numeric-up-down form-control" /></div>												
												</td>
											</tr>
											</tbody>
										</table>	
									</div>
								</div>
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0078"/></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>											
											<tr style="display: none;"><th><qp:message code="sc.screendesign.0079"/></th><td><input type="checkbox" checked="checked" name="enableGroup" /></td></tr>
											<tr>
												<th><qp:message code="sc.screendesign.0080"/></th>
												<td>
													<select name='dialog-component-setting-group-display-type' class="form-control">
														<option value="1"><qp:message code="sc.screendesign.0144"/></option>
														<option value="2"><qp:message code="sc.screendesign.0145"/></option>
													</select>
												</td>
											</tr>
										</table>									
									</div>
								</div>						
							</div>
							<div id="dialog-component-autocomplete-setting-autocomplete-setting" class="tab-pane">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0200"/></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form" id="sql-setting">
											<colgroup>
												<col width="40%">
												<col width="60%">
											</colgroup>
											<tr>
												<th><qp:message code="sc.autocomplete.0005"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>
													<qp:autocomplete mustMatch="true" arg03="${screenDesignForm.moduleId}" name="sqldesignid" arg02="20" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" optionLabel="optionLabel" optionValue="optionValue" onChangeEvent="findColumnByDatasource" selectSqlId="getSqlBuilderAutocomplete"/>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0201"/></th>
												<td optionLabel="optionLabel"></td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0202"/></th>
												<td optionValue="optionValue"></td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0419"></qp:message></th>
												<td>
													<label><input type="radio" class="qp-input-radio-margin qp-input-radio" value="0" name="allowAnyInput" /><qp:message code="sc.screendesign.0420"></qp:message></label>
													<label><input type="radio" class="qp-input-radio-margin qp-input-radio" value="1" name="allowAnyInput" /><qp:message code="sc.screendesign.0421"></qp:message></label>
												</td>
											</tr>	
											<tr id="autocompleteTitle">
												<td colspan="2"><b><qp:message code="sc.screendesign.0445" /></b></td>
											</tr>
											<tr  id="autocompleteInputSetting">
												<td colspan="2">
													<table id="settingInput" class="table table-bordered qp-table-list-none-action" style="background-color: #FFFFFF">
														<colgroup>
															<col width="6%">
															<col width="25%">
															<col width="25%">
															<col width="20%">
															<col width="25%">
														</colgroup>
														<thead>
															<tr>
																<th colspan="4"><qp:message code="sc.screendesign.0446" /></th>
																<th rowspan="2" valign="middle" style="vertical-align: middle;"><qp:message code="sc.screendesign.0447" /></th>
															</tr>
															<tr>
																<th><qp:message code="sc.screendesign.0448" /></th>
																<th><qp:message code="sc.screendesign.0051" /></th>
																<th><qp:message code="sc.screendesign.0054" /></th>
																<th><qp:message code="sc.screendesign.0196" /></th>
															</tr>
														</thead>
														<tbody>
															
														</tbody>
														
													</table>
												</td>
											</tr>	
										</table>
									</div>
							</div>
							</div>
							<div id="dialog-component-autocomplete-setting-dependencies" class="tab-pane">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0449" /></span>
									</div>
									<div class="panel-body" style="background-color: #EEEEEE">
										<table class="table table-bordered qp-table-form" id="tbl_list_result" style="background-color: #FFFFFF">
											<colgroup>
												<col width="40%">
												<col width="60%">
											</colgroup>
											<tbody><tr>
												<th><qp:message code="sc.screendesign.0447" /></th>
												<td>
													
												</td>
											</tr>
										</tbody></table>
										<div class="qp-div-action">
											<input type="button" value="Add" class="screen-design-btn qp-button-client" onclick="AddActionListComponent(this);" style="width: 70px">
										</div>
										<div id="dependenciesItem">
											<table class="table table-bordered qp-table-list-none-action" style="background-color: #FFFFFF">
												<colgroup>
													<col width="5%">
													<col width="95%">
												</colgroup>
												<thead>
													<tr>
														<th></th>
														<th><qp:message code="sc.screendesign.0447" /></th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td></td>
														<td></td>
													</tr>
												</tbody>
												
											</table>
										</div>
									</div>
							</div>
							</div>
							<div id="dialog-component-autocomplete-setting-autocomplete-style" class="tab-pane">
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
													<th><qp:message code="sc.screendesign.0353"/></th>
													<td>
														<table class="table table-bordered qp-table-form">
															<col width="25%">
															<col width="25%">
															<col width="25%">
															<col width="25%">
															<tr>
																<th style="text-align: center;"><qp:message code="sc.screendesign.0158"/></th>
																<th style="text-align: center;"><qp:message code="sc.screendesign.0354"/></th>
																<th style="text-align: center;"><qp:message code="sc.screendesign.0159"/></th>
																<th style="text-align: center;"><qp:message code="sc.screendesign.0355"/></th>
															</tr>
															<tr>
																<td>
																	<div class="input-group">
																		<input class="form-control qp-input-integer" name="margin-left" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
																	</div>
																</td>
																<td>
																	<div class="input-group">
																		<input class="form-control qp-input-integer" name="margin-top" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
																	</div>
																</td>
																<td>
																	<div class="input-group">
																		<input class="form-control qp-input-integer" name="margin-right" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
																	</div>
																</td>
																<td>
																	<div class="input-group">
																		<input class="form-control qp-input-integer" name="margin-bottom" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
																	</div>
																</td>
															</tr>	
														</table>		
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0346"/></th>
													<td>
														<div class="input-group qp-input-pickcolor colorpicker-element">
															<span class="input-group-addon"> <i></i></span>
															<input name="color" class="form-control" type="text" >
														</div>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0347"/></th>
													<td>
														<div class="input-group">
															<input class="form-control qp-input-integer" name="font-size" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
														</div>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0348"/></th>
													<td>
														<select class="form-control" name="font-style">
															<option value="Normal"><qp:message code="sc.screendesign.0142"/></option>
															<option value="italic"><qp:message code="sc.screendesign.0350"/></option>
														</select>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0352"/></th>
													<td><qp:autocomplete name="font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="false" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0444" /></th>
													<td>
														<div class="input-group">
															<input class="form-control qp-input-integer" name="height" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
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
			        		<button type="button" class="btn qp-button-client " onclick="deleteDialogAutocompleteSetting(this)"><qp:message code="sc.sys.0014"/></button>
			        		<button type="button" class="btn qp-button-client " onclick="saveDialogAutocompleteSetting(this)"><qp:message code="sc.sys.0054"/></button>
			      </div>					
				</div>
							
			</div>
			