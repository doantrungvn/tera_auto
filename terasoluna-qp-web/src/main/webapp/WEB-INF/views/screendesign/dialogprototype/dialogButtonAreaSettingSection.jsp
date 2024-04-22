<div class="modal-dialog">
		    	<div class="modal-content">
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  		     
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-button-area-setting-section-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a>
							</li>
							<li><a href="#dialog-button-area-setting-section-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>
						</ul>
						<div class="tab-content" style="min-height: 450px;">
							<div id="dialog-button-area-setting-section-properties" class="tab-pane active">
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
												<th><qp:message code="sc.screendesign.0082"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>
													<div style="float: left; width: 80%; margin-right: 4px;">
														<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="buttonLabelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														<input type="hidden" name="isBundle" value="true" /> 	
													</div>												
													<div class="vertical-middle level-text"></div>	
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0439" /></th>
												<td>
													<label><input type="radio" class="qp-input-radio-margin qp-input-radio" value="0" name="buttonType"><qp:message code="sc.screendesign.0436" /></label>
													<label><input type="radio" class="qp-input-radio-margin qp-input-radio" value="1" name="buttonType"><qp:message code="sc.screendesign.0437" /></label>
													<label><input type="radio" class="qp-input-radio-margin qp-input-radio" value="2" name="buttonType"><qp:message code="sc.screendesign.0268" /></label>
													<label><input type="radio" class="qp-input-radio-margin qp-input-radio" value="3" name="buttonType"><qp:message code="sc.screendesign.0438" /></label>
												</td>
											</tr>
											<tr>
												<td colspan="2"></td>
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
												<td colspan="2"></td>
											</tr>
											<%-- <tr>
												<th><qp:message code="sc.screendesign.0083"/></th>
												<td><input type="checkbox" name="isSubmit" class="qp-input-checkbox-margin qp-input-checkbox" checked="checked" onclick="changeSubmitDialogAction(this)"/></td>
											</tr> --%>
											<tr>
												<th><qp:message code="sc.screendesign.0018"/></th>
												<td>
													<qp:autocomplete 
					                                        optionValue="optionValue" optionLabel="optionLabel"
					                                        selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
					                                        name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}"
					                                        displayValue="${screenDesignForm.moduleName}" 
					                                        onChangeEvent="changeModule"
					                                        value="${screenDesignForm.moduleId}" >
					                                </qp:autocomplete>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0068"></qp:message></th>
												<td>
													<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetScreenList" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateTo" arg03="${f:h(sessionScope.CURRENT_LANGUAGE.languageId)}" arg01="${screenDesignForm.moduleId}" arg02="20"/>
												</td>
											</tr>
											<tr>
												<th>Transition node&nbsp;</th>
												<td><qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" selectSqlId="getTransitionAutocomplete" arg01="${screenDesignForm.screenId}" arg03="" emptyLabel="sc.sys.0030"  name="screenTransition" arg02="20"/></td>
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
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0191"/></th>
												<td>
													<div style="float: left; width: 100px; margin-right: 4px;"><input type="text" name="tabindex" class="qp-numeric-up-down form-control" /></div>												
												</td>
											</tr>
										<tr><td colspan="2">&nbsp;</td></tr>										
									</table>	
								</div>
							</div>						
						</div>
						<div id="dialog-button-area-setting-section-style" class="tab-pane">
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
													<th><qp:message code="sc.screendesign.0344"/></th>
													<td><span name="iconPreview"><qp:message code="sc.screendesign.0442" /></span>
													<input type="hidden" name="iconButton" />
													<span class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action" style="float: right; cursor: pointer;" onclick="settingIcon(this, 'dialog-button-area-setting-section')"></span></td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0345"/></th>
													<td><input type="checkbox" name="showLabel" value="1" onclick='handleClickLabelSection(this)'/></td>
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
												<th><qp:message code="sc.screendesign.0443" /></th>
												<td><div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="background-color" class="form-control" type="text" value="">
													</div></td>
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
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogButtonAreaSetting(this, 'dialog-button-area-setting-section')"><qp:message code="sc.sys.0014"/></button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogButtonAreaSetting(this, 'dialog-button-area-setting-section')"><qp:message code="sc.sys.0054"/></button>
		      		</div>					
				</div>
			</div>