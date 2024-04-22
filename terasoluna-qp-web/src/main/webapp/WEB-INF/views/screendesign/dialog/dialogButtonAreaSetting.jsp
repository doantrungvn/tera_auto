<div class="modal-dialog" style="min-height: 500px;width: 800px;">
		    	<div class="modal-content">		
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>       
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-button-area-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a>
							</li>
							<li><a href="#dialog-button-area-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>
						</ul>
						<div class="tab-content" style="min-height: 450px;">
							<div id="dialog-button-area-setting-properties" class="tab-pane active">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0084"/></span>
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
											<tr>
												<th><qp:message code="sc.screendesign.0083"/></th>
												<td><input type="checkbox" name="isSubmit" class="qp-input-checkbox-margin qp-input-checkbox" checked="checked" onclick="changeSubmit(this)"/></td>
											</tr>
											<tr style="display: none;">
												<th><qp:message code="sc.screendesign.0338"/></th>
												<td>
													<label><input type="radio" value="0" name="actiontype" checked="checked" onclick="changeNavigateType(this)" ><qp:message code="sc.screendesign.0340"/></label>&nbsp;&nbsp;
													<label><input type="radio" value="1" name="actiontype" onclick="changeNavigateType(this)"> <qp:message code="sc.screendesign.0341"/></label>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0018"/></th>
												<td>
													<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}" displayValue="${screenDesignForm.moduleName}"  onChangeEvent="changeModule" value="${screenDesignForm.moduleId}" />
												</td>
											</tr>
											<tr id="navigateToScreen">
												<th><qp:message code="sc.screendesign.0068"></qp:message>&nbsp;</th>
												<td>
													<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetScreenList" onChangeEvent="changeNavigationTo" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateTo" arg03="${f:h(sessionScope.CURRENT_LANGUAGE.languageId)}" arg01="${screenDesignForm.moduleId}" arg02="20"/>
												</td>
											</tr>
											<tr id="navigateToScreenBlogic">
												<th><qp:message code="sc.screendesign.0341" />&nbsp;<label class="qp-required-field">(*)</label></th>
												<td>
													<qp:autocomplete optionLabel="optionLabel" onChangeEvent="changeBlogic" arg05="${screenDesignForm.screenId}" arg04="0" selectSqlId="getBlogicNavigateByScreenIdAutocomplete" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateToBlogic" arg03="${f:h(sessionScope.CURRENT_LANGUAGE.languageId)}" arg01="" arg02="20"/>
												</td>
											</tr>
											<%-- <tr id="navigateToblogic">
												<th><qp:message code="sc.screendesign.0068"></qp:message>&nbsp;</th>
												<td>
													<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetBlogicList" onChangeEvent="changeNavigationTo" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateTo" arg01="${screenDesignForm.moduleId}" arg02="20"/>
												</td>
											</tr> --%>
											<tr>
												<th>Transition node&nbsp;</th>
												<td><qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" selectSqlId="getTransitionAutocomplete" arg01="${screenDesignForm.screenId}" arg03="" emptyLabel="sc.sys.0030"  name="screenTransition" arg02="20"/></td>
											</tr>
											<%-- <tr>
												<th><qp:message code="sc.screendesign.0069"/></th>
												<td><input type="text" class="form-control" name="actionName" maxlength="100"/></td>
											</tr> --%>
											<tr id="dialogActionParamSetting">
												<td colspan="2">
													<b><qp:message code="sc.screendesign.0070"/></b>
													<table class="table table-bordered qp-table-list-none-action" id="dialog-form-parameter-tbl-parameter">
														<colgroup>
															<col />
															<col width="35%"/>
															<col width="30%"/>
															<col width="30%"/>
														</colgroup>
														<thead>
															<tr>
																<th><qp:message code="sc.sys.0004"/></th>
																<th><qp:message code="sc.screendesign.0071"/></th>
																<th><qp:message code="sc.decisiontable.0017"/></th>
																<th><qp:message code="sc.screendesign.0197"/></th>
															</tr>
														</thead>
														<tbody>
															<!-- <tr>
																<td>1</td>
																<td><input type="text" class="form-control" name="parameterAttribute" maxlength="100"/></td>
																<td><select class="form-control">
																	<option>Long</option>
																</select></td>
															</tr> -->
														</tbody>
													</table>
													<table id="blogic-param" class="table table-bordered qp-table-list mapping-bean">
														<colgroup>
															<col width="55px"/>
															<col width="35%" />
															<col width="20%" />
															<col width="15%" />
															<col width="25%" />
														</colgroup>
														<thead>
															<tr>
																<th colspan="4"><qp:message code="sc.screendesign.0342"/></th>
																<th rowspan="2" style="color: blue;"><qp:message code="sc.screendesign.0197"/>&nbsp;<label
																	class="com-required-field">(*)</label></th>
															</tr>
															<tr>
																<th><qp:message code="sc.sys.0004"/></th>
																<th><qp:message code="sc.decisiontable.0015"/></th>
																<th><qp:message code="sc.decisiontable.0016"/></th>
																<th><qp:message code="sc.decisiontable.0017"/></th>
																<th></th>
															</tr>
														</thead>
														<tbody>
															
														</tbody>
													</table>
												</td>
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
											
										<tr><td colspan="2">&nbsp;</td></tr>
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
											<th><qp:message code="sc.screendesign.0080"></qp:message></th>
											<td>
												<select class="form-control" name='dialog-button-area-setting-group-display-type'>
													<option value="1"><qp:message code="sc.screendesign.0144"/></option>
													<option value="2"><qp:message code="sc.screendesign.0145"/></option>
												</select>
											</td>
										</tr>											
									</table>									
								</div>
							</div>			
						</div>
						<div id="dialog-button-area-setting-style" class="tab-pane">
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
													<span class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action" style="float: right; cursor: pointer;" onclick="settingIcon(this)"></span></td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0345"/></th>
													<td><input type="checkbox" name="showLabel" value="1" onclick='handleClickLabel(this)' /></td>
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
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogButtonAreaSetting(this)"><qp:message code="sc.sys.0014"/></button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogButtonAreaSetting(this)"><qp:message code="sc.sys.0054"/></button>
		      		</div>					
				</div>
			</div>