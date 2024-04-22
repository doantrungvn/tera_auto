<div class="modal-dialog" style="min-height: 500px;width: 800px;">
		    	<div class="modal-content">	
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  	     
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active"><a href="#dialog-component-list-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a></li>
							<li><a href="#dialog-component-list-setting-codelist" data-toggle="tab"><qp:message code="sc.screendesign.0198"/></a></li>
							<li><a href="#dialog-component-list-setting-event" data-toggle="tab"><qp:message code="sc.screendesign.0065"/></a></li>
							<li><a href="#dialog-component-list-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>
						</ul>
						<div class="tab-content" >
							<div id="dialog-component-list-setting-properties" class="tab-pane active">
		      					<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0081"/></span>
									</div>
									<div class="panel-body">									
						       			<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>
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
												<th><qp:message code="sc.screendesign.0086"/></th>
												<td baseType="dialog-component-list-setting-base-types">
													
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0085"/></th>
												<td>													
													<div style="float: left; width: 100px; margin-right: 4px;"><input name="dialog-component-list-setting-width" type="text" class="qp-numeric-up-down form-control" /></div>
													<div style="float: left;margin-left: 4px;">
														<select class="form-control qp-input-select" style="width: 60px; padding-top: 0.1em; padding-bottom: 0.4em; margin-bottom: 4px;" name="dialog-component-list-setting-width-unit">
															<option value="%"><qp:message code="sc.screendesign.0146"/></option>
															<option value="px"><qp:message code="sc.screendesign.0147"/></option>
														</select>
													</div>
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
													<select name='dialog-component-list-setting-group-display-type' class="form-control">
														<option value="1"><qp:message code="sc.screendesign.0144"/></option>
														<option value="2"><qp:message code="sc.screendesign.0145"/></option>
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
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0200"/></span>
									</div>
									<div class="panel-body">
										
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%">
												<col width="60%">
											</colgroup>
											<tr>
												<th><qp:message code="sc.screendesign.0208"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>
													<label><input class="qp-input-radio-margin qp-input-radio" name="dataSourceType" type="radio" value="1" onclick="changeSettingDataSource(this)" /><qp:message code="sc.screendesign.0450" /></label>&nbsp;&nbsp;<label><input class="qp-input-radio-margin qp-input-radio" value="2" name="dataSourceType" onclick="changeSettingDataSource(this)" type="radio" /><qp:message code="sc.screendesign.0148"/></label>	
												</td>
											</tr>
											<tr id="showBlankItemLayout">
												<th><qp:message code="sc.screendesign.0451" /></th>
												<td>
													<label><input onchange="changeValue(this)" class="qp-input-radio-margin qp-input-checkbox" name="showBlankItem" value="1"  type="checkbox" /></label>	
												</td>
											</tr>
										</table>
										<br />
										<div class="panel-group" id="accordion">
										        	<table class="table table-bordered qp-table-form" id="sql-setting">
														<colgroup>
															<col width="40%">
															<col width="60%">
														</colgroup>
														<tr>
															<th><qp:message code="sc.screendesign.0210"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
															<td>
																<qp:autocomplete name="sqldesignid" arg02="20" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" optionLabel="optionLabel" optionValue="optionValue" onChangeEvent="findColumnByDatasource" selectSqlId="getAllSqlBuilderAutocomplete"/>
															</td>
														</tr>
														<tr>
															<td colspan="2">
																<table class="table table-bordered qp-table-form">
																	<colgroup>
																		<col width="50%">
																		<col width="50%">
																	</colgroup>
																	<tr>
																		<th style="text-align: center;"><qp:message code="sc.screendesign.0211"/></th>
																		<th style="text-align: center;"><qp:message code="sc.screendesign.0212"/></th>
																	</tr>
																	<tr>
																		<th><qp:message code="sc.screendesign.0201"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
																		<td><div style="width: 100%;"><qp:autocomplete arg02="20" name="optionlabel" optionLabel="optionLabel" optionValue="optionValue" selectSqlId="findColumnByDatasource"/></div></td>
																	</tr>
																	<tr>
																		<th><qp:message code="sc.screendesign.0202"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
																		<td><div style="width: 100%;"><qp:autocomplete arg02="20" name="optionvalue" optionLabel="optionLabel" optionValue="optionValue" selectSqlId="findColumnByDatasource"/></div></td>
																	</tr>
																</table>		
															</td>
														</tr>
													</table>
										   
											        <table class="table table-bordered qp-table-form" id="codelist-setting">
														<colgroup>
															<col width="40%">
															<col width="60%">
														</colgroup>
														<tr>
															<th>
																<qp:message code="sc.screendesign.0155"/>
															</th>
															<td>
																<div>
																	<label><input class="qp-input-radio-margin qp-input-radio" type="radio" name="localCodelist" value="1" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0213"/></label>
																	<label><input class="qp-input-radio-margin qp-input-radio" type="radio" name="localCodelist" value="2" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0156"/></label>
																	&nbsp;<label><input class="qp-input-radio-margin qp-input-radio" type="radio" name="localCodelist" value="3" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0157"/></label>
																</div>
															</td>
														</tr>
														<tr class="codelist-system">
															<th>
																<qp:message code="sc.screendesign.0095"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
															</th>
															<td>
																<qp:autocomplete  optionLabel="optionLabel" selectSqlId="autocompleteGetCodeList" arg03="${screenDesignForm.moduleId}" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" arg02="20" emptyLabel="sc.sys.0030" optionValue="optionValue" name="codelistCode" onChangeEvent="selectCodeList"/>
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
																			<th><qp:message code="sc.sys.0004"/> </th>
																			<th class="colOptionName">
																				<qp:message code="sc.codelist.0008"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																			</th>
																			<th>
																				<qp:message code="sc.screendesign.0097"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
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
																			<th><qp:message code="sc.sys.0004"/> </th>
																			<th class="colOptionName">
																				<qp:message code="sc.screendesign.0096"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																			</th>
																			<th>
																				<qp:message code="sc.screendesign.0097"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
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
																		<input type="checkbox" name="supportOptionValue" id="supportOptionValue" onclick="changeSupportOptionValue(this)" style="margin-top: 1px"> <b><qp:message code="sc.tabledesign.0053"/></b>
																	</label>
																</div>
															</td>
														</tr>
														<tr class="codelist-screen">
															<td colspan="2">
																<table id="dialog-component-list-setting-tbl-options" class="table table-bordered qp-table-list">
																	<thead>
																		<tr>
																			<th><qp:message code="sc.sys.0004"/> </th>
																			<th class="colOptionName">
																				<qp:message code="sc.screendesign.0096"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
																			</th>
																			<th>
																				<qp:message code="sc.screendesign.0097"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
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
								          <div >
								          </div>
									</div>
								</div>
							</div>
							<div id="dialog-component-list-setting-event" class="tab-pane">
								<div class="panel panel-default  qp-div-information">
									<div class="panel-heading">
										<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0219"/></span>
									</div>
									<div class="panel-body" style="background-color: #EEEEEE">
										<table class="table table-bordered qp-table-form" id="tbl_list_result" style="background-color: #FFFFFF">
											<colgroup>
												<col width="20%">
												<col width="20%">
												<col width="20%">
												<col width="30%">
											</colgroup>
											<tr>
												<th><qp:message code="sc.screendesign.0215"/></th>
												<td><qp:message code="sc.screendesign.0216"/></td>
												<th><qp:message code="sc.screendesign.0061"/></th>
												<td><select id="actionType" class="form-control qp-input-select">
														<option value="0"><qp:message code="sc.screendesign.0217"/></option>
														<option value="1"><qp:message code="sc.screendesign.0218"/></option>
														<option value="2"><qp:message code="sc.autocomplete.0020"/></option>
												</select></td>
											</tr>
										</table>
										<div class="qp-div-action">
											<input type="button" value="Add" class="screen-design-btn qp-button-client" onclick="AddActionListComponent(this);" style="width: 70px">
										</div>
									</div>
								</div>
								<div id="listEvent">
								
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
			      		<div class="modal-footer">
			        		<button type="button" class="btn qp-button-client " onclick="deleteDialogComponentListSetting(this)"><qp:message code="sc.sys.0014"/></button>
			        		<button type="button" class="btn qp-button-client " onclick="saveDialogComponentListSetting(this)"><qp:message code="sc.sys.0054"/></button>
			      		</div>
		   	 		</div>
		  		</div>
			</div>