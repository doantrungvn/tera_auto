<div class="modal-dialog" style="min-height: 500px;width: 800px;">
		    	<div class="modal-content">		   
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active"><a href="#dialog-link-area-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/> </a></li>
							<li><a href="#dialog-link-area-setting-codelist" data-toggle="tab"><qp:message code="sc.screendesign.0198"/></a></li>
							<li><a href="#dialog-link-area-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339" /></a></li>
						</ul>
						<div class="tab-content" style="min-height: 330px;">
							<div id="dialog-link-area-setting-properties" class="tab-pane active">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0066"/> </span>
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
														<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="labelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														<input type="hidden" name="isBundle" value="true" /> 	
													</div>												
													<div class="vertical-middle level-text"></div>				
												</td>
											</tr>
											<tr name="linkDynamic">
												<th><qp:message code="sc.screendesign.0199"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>	
													<div style="float: left; width: 80%; margin-right: 4px;">
														<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="linkDynamicLabelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														<input type="hidden" name="isBundle" value="true" /> 	
													</div>												
													<div class="vertical-middle level-text"></div>														
												</td>
											</tr>
											<tr name="linkDynamic">
												<th><qp:message code="sc.screendesign.0093"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><input type="text" class="form-control qp-input-text qp-convention-code" name="columnname" maxlength="200" datatype=""/></td>
											</tr>	
											<tr style="display: none">
												<th><qp:message code="sc.screendesign.0338"/></th>
												<td>
													<label><input type="radio" value="0" name="actiontype" checked="checked" onclick="changeNavigateType(this)" > <qp:message code="sc.screendesign.0340"/></label>&nbsp;&nbsp;
													<label><input type="radio" value="1" name="actiontype" onclick="changeNavigateType(this)"> <qp:message code="sc.screendesign.0341"/></label>
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
											<tr><td colspan="2"></td></tr>
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
											<tr>
												<th>Transition node&nbsp;</th>
												<td><qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" selectSqlId="getTransitionAutocomplete" arg01="${screenDesignForm.screenId}" arg03="" emptyLabel="sc.sys.0030"  name="screenTransition" arg02="20"/></td>
											</tr>
										<%-- <tr>
											<th><qp:message code="sc.screendesign.0069"/></th>
											<td><input type="text" class="form-control" name="actionName" maxlength="100"/></td>
										</tr> --%>
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
										<tr><td colspan="2"></td></tr>
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
										<tr id="dialogActionParamSetting">
												<td colspan="2">
													<b><qp:message code="sc.screendesign.0070"/></b>
													<script id="autocomplete-template" type="text/template">
														<div style="width: 100%;">
															<qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" sourceType="local" name="screenItemCode" sourceCallback="loadScreenItemCode" ></qp:autocomplete>
														</div>
															
													</script>
													
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
												<th><qp:message code="sc.screendesign.0080"/></th>
												<td>
													<select class="form-control" name='dialog-link-area-setting-group-display-type'>
														<option value="1"><qp:message code="sc.screendesign.0144"/></option>
														<option value="2"><qp:message code="sc.screendesign.0145"/></option>
													</select>
												</td>
											</tr>											
										</table>									
									</div>
								</div>						
							</div>
							<div id="dialog-link-area-setting-codelist" class="tab-pane" style="min-height: 200px">
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
												<th><qp:message code="sc.screendesign.0208"/></th>
												<td>
													<label><input class="qp-input-checkbox-margin qp-input-radio" name="dataSourceType" type="radio" value="1" onclick="changeSettingDataSource(this)" /><qp:message code="sc.screendesign.0450"/></label>&nbsp;&nbsp;<label><input class="qp-input-checkbox-margin qp-input-radio" value="2" name="dataSourceType" onclick="changeSettingDataSource(this)" type="radio" /><qp:message code="sc.screendesign.0148"/></label>	
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
															<th><qp:message code="sc.screendesign.0210"/></th>
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
																		<th><qp:message code="sc.screendesign.0201"/></th>
																		<td><div style="width: 100%;"><qp:autocomplete arg02="20" name="optionlabel" optionLabel="optionLabel" optionValue="optionValue" selectSqlId="findColumnByDatasource"/></div></td>
																	</tr>
																	<tr>
																		<th><qp:message code="sc.screendesign.0202"/></th>
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
																	<label><input type="radio" name="localCodelist" value="1" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0213"/></label>
																	<label><input type="radio" disabled name="localCodelist" value="2" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0156"/></label>
																	&nbsp;&nbsp;<label><input type="radio" name="localCodelist" value="3" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0157"/></label>
																</div>
															</td>
														</tr>
														<tr class="codelist-system">
															<th>
																Codelist
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
																				<qp:message code="sc.screendesign.0201"/>
																			</th>
																			<th>
																				<qp:message code="sc.screendesign.0097"/>
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
																				<qp:message code="sc.screendesign.0096"/>
																			</th>
																			<th>
																				<qp:message code="sc.screendesign.0097"/>
																			</th>
																		</tr>
																	</thead>
																	<tbody>
																	
																	</tbody>
																</table>
															</td>
														</tr>
														<tr class="codelist-screen">
															<th>Screen name</th>
															<td><qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" onChangeEvent="changeScreenId" mustMatch="true" name="screenNameCodeList"  selectSqlId="getAllScreenOfModule" arg01="${screenDesignForm.moduleId}"/></td>
														</tr>
														<tr class="codelist-screen">
															<th>Screen item name</th>
															<td><qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="true" onChangeEvent="changeScreenItemId" name="screenItemCodeList" selectSqlId="getAllItemCodeListScreenOfScreen" /></td>
														</tr>
														<tr class="codelist-screen">
															<td colspan="2">
																<div class="checkbox">
																	 <label for="supportOptionValue">
																		<input disabled="disabled" type="checkbox" name="supportOptionValue" id="supportOptionValue" onclick="changeSupportOptionValue(this)" style="margin-top: 1px"> <b><qp:message code="sc.tabledesign.0053"/></b>
																	</label>
																</div>
															</td>
														</tr>
														<tr class="codelist-screen">
															<td colspan="2">
																<table id="dialog-component-list-setting-tbl-options" class="table table-bordered qp-table-list">
																	<thead>
																		<tr>
																			<th style="width: 5%"><qp:message code="sc.sys.0004"/> </th>
																			<th style="width: 45%" class="colOptionName"><qp:message code="sc.screendesign.0096"/></th>
																			<th style="width: 45%"><qp:message code="sc.screendesign.0097"/></th>
																		</tr>
																	</thead>
																	<tbody>
																	
																	</tbody>
																</table>
															</td>
														</tr>
													</table>
										  </div>
								          <div >
								          </div>
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
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogLinkAreaSetting(this)"><qp:message code="sc.sys.0014"/> </button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogLinkAreaSetting(this)"><qp:message code="sc.sys.0054"/> </button>
		      		</div>					
				</div>
			</div>