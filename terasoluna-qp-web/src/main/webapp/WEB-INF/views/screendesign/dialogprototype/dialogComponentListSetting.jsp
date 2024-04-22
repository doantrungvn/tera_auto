<div class="modal-dialog" style="min-height: 500px;width: 800px;">
		    	<div class="modal-content">	
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  	     
		      		<div class="modal-body">
		      		
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-component-list-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"></qp:message></a>
							</li>
							<li><a href="#dialog-component-list-setting-codelist" data-toggle="tab"><qp:message code="sc.screendesign.0148"></qp:message></a></li>
							<li><a href="#dialog-component-list-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>
						</ul>
						<div class="tab-content" >
							<div id="dialog-component-list-setting-properties" class="tab-pane active">
		      					<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0081"></qp:message></span>
									</div>
									<div class="panel-body">									
						       			<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>
											<tr>
												<th><qp:message code="sc.screendesign.0194"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>																							
												<td>
													<div style="float: left; width: 80%; margin-right: 4px;">							
														<qp:autocomplete optionLabel="optionLabel" cssInput="qp-convention-name" optionValue="optionValue" mustMatch="false" name="labelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"></qp:autocomplete>
														<input type="hidden" name="isBundle" value="true" />
													</div> 
													<div class="vertical-middle level-text"></div>							
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0093"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><input type="text" class="form-control qp-input-text qp-convention-code" name="columnname" maxlength="200"/></td>
											</tr>		
											<tr>
												<td colspan="2"></td>
											</tr>		
											<tr id="trRequire">
												<th><qp:message code="sc.screendesign.0100"/></th>
												<td><input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="mandatory" value="true"/></td>
											</tr>							
											<tr>
												<th><qp:message code="sc.screendesign.0086"></qp:message></th>
												<td baseType="dialog-component-list-setting-base-types">
													
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0085"></qp:message></th>
												<td>													
													<div style="float: left; width: 100px; margin-right: 4px;"><input name="dialog-component-list-setting-width" type="text" class="qp-numeric-up-down form-control" /></div>
													<div style="float: left;margin-left: 4px;">
														<select class="form-control qp-input-select" style="width: 60px; padding-top: 0.1em; padding-bottom: 0.4em; margin-bottom: 4px;" name="dialog-component-list-setting-width-unit">
															<option value="%"><qp:message code="sc.screendesign.0146"></qp:message></option>
															<option value="px"><qp:message code="sc.screendesign.0147"></qp:message></option>
														</select>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0074"></qp:message></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input name="colspan" type="text" class="qp-numeric-up-down form-control" /></div>												
													<div class="vertical-middle"><qp:message code="sc.screendesign.0075"></qp:message></div>	
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0076"></qp:message></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input type="text" name="rowspan" class="qp-numeric-up-down form-control" /></div>												
													<div class="vertical-middle"><qp:message code="sc.screendesign.0077"></qp:message></div> 
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0191"></qp:message></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input type="text" name="tabindex" class="qp-numeric-up-down form-control" /></div>												
												</td>
											</tr>
										</table>
									</div>
								</div>
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0205"/></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>											
											<tr>
												<th><qp:message code="sc.screendesign.0206"/></th>
												<td>
													<label><input name="elementType" value="6" class="qp-input-radio-margin qp-input-radio" type='radio'><qp:message code="sc.screendesign.0047"/></label>
													<label><input name="elementType" value="5" class="qp-input-radio-margin qp-input-radio" type='radio'><qp:message code="sc.screendesign.0049"/></label>
													<label><input name="elementType" value="7" class="qp-input-radio-margin qp-input-radio" type='radio'><qp:message code="sc.screendesign.0048"/></label>
													<label><input name="elementType" value="" class="qp-input-radio-margin qp-input-radio" type='radio'><qp:message code="sc.screendesign.0207"/></label>
												</td>
											</tr>
										</table>									
									</div>
								</div>
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0078"></qp:message></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>											
											<tr style="display: none;"><th><qp:message code="sc.screendesign.0079"/></th><td><input type="checkbox" checked="checked" name="enableGroup" /></td></tr>
											<tr>
												<th><qp:message code="sc.screendesign.0080"></qp:message></th>
												<td>
													<select name='dialog-component-list-setting-group-display-type' class="form-control">
														<option value="1"><qp:message code="sc.screendesign.0144"></qp:message></option>
														<option value="2"><qp:message code="sc.screendesign.0145"></qp:message></option>
													</select>
												</td>
											</tr>											
										</table>									
									</div>
								</div>
							</div>
							<div id="dialog-component-list-setting-codelist" class="tab-pane" style="min-height: 200px">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0154"></qp:message></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%">
												<col width="60%">
											</colgroup>
											<tr>
												<th>
													<qp:message code="sc.screendesign.0155"></qp:message>
												</th>
												<td>
													<div class="radio">
														<label class="radio-inline"><input type="radio" name="localCodelist" value="1" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0213"></qp:message></label>
														<label class="radio-inline"><input type="radio" name="localCodelist" value="2" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0156"></qp:message></label>
														<label class="radio-inline"><input type="radio" name="localCodelist" value="3" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0157"></qp:message></label>
													</div>
												</td>
											</tr>
											<tr id="showBlankItemLayout">
												<th><qp:message code="sc.screendesign.0451" /></th>
												<td>
													<label><input onchange="changeValue(this)" class="qp-input-radio-margin qp-input-checkbox" name="showBlankItem" value="1"  type="checkbox" /></label>	
												</td>
											</tr>
											<tr class="codelist-system">
												<th>
													<qp:message code="sc.screendesign.0095"></qp:message>
												</th>
												<td>
													<qp:autocomplete  optionLabel="optionLabel" selectSqlId="autocompleteGetCodeList" arg03="${screenDesignForm.moduleId}" arg01="${screenDesignForm.projectId}" arg02="20" emptyLabel="sc.sys.0030" optionValue="optionValue" name="codelistCode" onChangeEvent="selectCodeList"></qp:autocomplete>
												</td>
											</tr>
											<tr class="codelist-system">
												<td colspan="2">
													<table id="dialog-component-list-setting-tbl-system-options" class="table table-bordered qp-table-list">
														<colgroup>
															<col/>
															<col width="50%"/>
															<col width="50%"/>
														</colgroup>
														<thead>
															<tr>
																<th><qp:message code="sc.sys.0004"></qp:message> </th>
																<th class="colOptionName">
																	<qp:message code="sc.screendesign.0096"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																</th>
																<th>
																	<qp:message code="sc.screendesign.0097"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																</th>
															</tr>
														</thead>
														<tbody>
														
														</tbody>
													</table>
												</td>
											</tr>
											<tr class="codelist-table">
												<td colspan="2">
													<table id="dialog-component-list-setting-tbl-table-options" class="table table-bordered qp-table-list">
														<colgroup>
															<col/>
															<col width="50%"/>
															<col width="50%"/>
														</colgroup>
														<thead>
															<tr>
																<th><qp:message code="sc.sys.0004"></qp:message> </th>
																<th class="colOptionName">
																	<qp:message code="sc.screendesign.0096"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																</th>
																<th>
																	<qp:message code="sc.screendesign.0097"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																</th>
															</tr>
														</thead>
														<tbody>
														
														</tbody>
													</table>
												</td>
											</tr>
											<tr class="codelist-screen">
												<td colspan="2">
													<div class="checkbox">
														 <label for="supportOptionValue">
															<input type="checkbox" name="supportOptionValue" id="supportOptionValue" onclick="changeSupportOptionValue(this)" style="margin-top: 1px"> <b><b><qp:message code="sc.tabledesign.0053"/></b></b>
														</label>
													</div>
												</td>
											</tr>
											<tr class="codelist-screen">
												<td colspan="2">
													<table id="dialog-component-list-setting-tbl-options" class="table table-bordered qp-table-list">
														<thead>
															<tr>
																<th><qp:message code="sc.sys.0004"></qp:message> </th>
																<th class="colOptionName">
																	<qp:message code="sc.screendesign.0096"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																</th>
																<th>
																	<qp:message code="sc.screendesign.0097"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																</th>
																<th></th>
															</tr>
														</thead>
														<tbody>
														
														</tbody>
													</table>
													<div class="qp-add-left">
														<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="addRowCodelist(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<div id="dialog-component-list-setting-style" class="tab-pane">
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
											</table>
											
											</div>
										</div>
								</div>
						</div>						
			      		<div class="modal-footer">
			        		<button type="button" class="btn qp-button-client " onclick="deleteDialogComponentListSetting(this)"><qp:message code="sc.sys.0014"></qp:message></button>
			        		<button type="button" class="btn qp-button-client " onclick="saveDialogComponentListSetting(this)"><qp:message code="sc.sys.0054"></qp:message></button>
			      		</div>
		   	 		</div>
		  		</div>
			</div>