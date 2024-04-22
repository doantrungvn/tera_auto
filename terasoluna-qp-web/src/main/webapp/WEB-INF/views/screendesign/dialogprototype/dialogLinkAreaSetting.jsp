<div class="modal-dialog">
		    	<div class="modal-content">		   
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-link-area-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"></qp:message> </a>
							</li>
							<li><a href="#dialog-link-area-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339" /></a></li>
						</ul>
						<div class="tab-content" style="min-height: 450px;">
							<div id="dialog-link-area-setting-properties" class="tab-pane active">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0066"></qp:message> </span>
									</div>
									<div class="panel-body">
									<table class="table table-bordered qp-table-form">
										<colgroup>
											<col width="40%"/>
											<col width="60%"/>
										</colgroup>
										<tr id="linkStatic">
											<th><qp:message code="sc.screendesign.0067"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
											<td>
												<div style="float: left; width: 80%; margin-right: 4px;">
													<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="linkLabelName" arg01="${screenDesignForm.moduleId }" onChangeEvent="showMessageLevel" onSelectEvent='dialogLinkAreaSettinglableOnSelect' arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
													<input type="hidden" name="isBundle" value="true" />
												</div>
												<div class="vertical-middle level-text"></div>		
											</td>
										</tr>
										<tr name="linkDynamic">
											<th><qp:message code="sc.screendesign.0199"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
											<td>
												<div style="float: left; width: 80%; margin-right: 4px;">
													<qp:autocomplete cssInput="qp-convention-name" optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="linkDynamicLabelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
													<input type="hidden" name="isBundle" value="true" /> 	
												</div>
												<div class="vertical-middle level-text"></div>														
											</td>
										</tr>
										<tr name="linkDynamic">
											<th><qp:message code="sc.screendesign.0093"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
											<td><input type="text" class="form-control qp-input-text qp-convention-code" name="columnname" maxlength="200" datatype=""/></td>
										</tr>
										<tr>
											<th><qp:message code="sc.screendesign.0068"></qp:message></th>
											<td>
												<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetScreenList" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateTo" arg03="${f:h(sessionScope.CURRENT_LANGUAGE.languageId)}" arg01="${screenDesignForm.moduleId}" arg02="20"/>
											</td>
										</tr>
										<tr>
											<th><qp:message code="sc.screendesign.0440" /></th>
											<td>
												<label><input onclick="enableConfirmEvent(this)" type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" value="1" name="enableConfirm"></label>
											</td>
										</tr>
										<tr id="enableConfirmGroup">
											<th><qp:message code="sc.screendesign.0441" /></th>
											<td>
												<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="messageConfirm" arg01="${screenDesignForm.moduleId }" selectSqlId="findInfoMessage"/>
											</td>
										</tr>
										<tr>
											<th>Transition node&nbsp;</th>
											<td><qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" selectSqlId="getTransitionAutocomplete" arg01="${screenDesignForm.screenId}" arg03="" emptyLabel="sc.sys.0030"  name="screenTransition" arg02="20"/></td>
										</tr>
										<tr name="baseType">
											<td colspan="2"></td>
										</tr>
										<tr name="baseType">
											<th><qp:message code="sc.screendesign.0086"/></th>
											<td baseType="dialog-component-setting-base-types" id="baseTypeText">
												<qp:message code="sc.screendesign.0453" />
											</td>
										</tr>
										<tr id="baseStypeDynamicLink">
											<th><qp:message code="sc.screendesign.0503"/></th>
											<td>
												<select class="form-control qp-input-select" name="baseType">
												
												</select>
											</td>
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
										<tr><td colspan="2">&nbsp;</td></tr>
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
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0078"></qp:message></span>
									</div>
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>											
											<tr style="display: none;"><th><qp:message code="sc.screendesign.0079"></qp:message></th><td><input type="checkbox" checked="checked" name="enableGroup" /></td></tr>
											<tr>
												<th><qp:message code="sc.screendesign.0080"></qp:message></th>
												<td>
													<select class="form-control" name='dialog-link-area-setting-group-display-type'>
														<option value="1"><qp:message code="sc.screendesign.0144"></qp:message></option>
														<option value="2"><qp:message code="sc.screendesign.0145"></qp:message></option>
													</select>
												</td>
											</tr>											
										</table>									
									</div>
								</div>						
							</div>
							<div id="dialog-link-area-setting-style" class="tab-pane">
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
													<th colspan="4" style="background-color: #FAF5EE; text-align: left;"><qp:message code="sc.screendesign.0454" /></th>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0356"/></th>
													<td>
														<select name="text-decoration" class="form-control">
															<option><qp:message code="sc.screendesign.0207" /></option>
															<option><qp:message code="sc.screendesign.0357" /></option>
															<option><qp:message code="sc.screendesign.0358" /></option>
															<option><qp:message code="sc.screendesign.0359" /></option>
														</select>
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
													<th colspan="4" style="background-color: #FAF5EE; text-align: left;"><qp:message code="sc.screendesign.0455" /></th>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0356"/></th>
													<td>
														<select name="hoverStyle=text-decoration" class="form-control">
															<option><qp:message code="sc.screendesign.0207" /></option>
															<option><qp:message code="sc.screendesign.0357" /></option>
															<option><qp:message code="sc.screendesign.0358" /></option>
															<option><qp:message code="sc.screendesign.0359" /></option>
														</select>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0346"/></th>
													<td>
														<div class="input-group qp-input-pickcolor colorpicker-element">
															<span class="input-group-addon"> <i></i></span>
															<input name="hoverStyle=color" class="form-control" type="text" >
														</div>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0347"/></th>
													<td>
														<div class="input-group">
															<input class="form-control qp-input-integer" name="hoverStyle=font-size" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
														</div>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0348"/></th>
													<td>
														<select class="form-control" name="hoverStyle=font-style">
															<option value="Normal"><qp:message code="sc.screendesign.0142"/></option>
															<option value="italic"><qp:message code="sc.screendesign.0350"/></option>
														</select>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0352"/></th>
													<td><qp:autocomplete name="hoverStyle=font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="false" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0443" /></th>
													<td>
														<div class="input-group qp-input-pickcolor colorpicker-element">
															<span class="input-group-addon"> <i></i></span>
															<input class="form-control" name="hoverStyle=background-color" type="text" >
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
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogLinkAreaSetting(this)"><qp:message code="sc.sys.0014"></qp:message> </button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogLinkAreaSetting(this)"><qp:message code="sc.sys.0054"></qp:message> </button>
		      		</div>					
				</div>
			</div>