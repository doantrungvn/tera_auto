<div class="modal-dialog">
		    	<div class="modal-content">		   
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#linkSetting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"></qp:message> </a>
							</li>
							<li><a href="#linkSetting-style" data-toggle="tab">Style</a></li>
						</ul>
						<div class="tab-content" style="min-height: 450px;">
							<div id="linkSetting-properties" class="tab-pane active">
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
										<tr>
											<th><qp:message code="sc.screendesign.0067"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
											<td>
												<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="labelName" arg01="${screenDesignForm.moduleId }" onSelectEvent='dialogLinkAreaSettinglableOnSelect' selectSqlId="findSystemMessage"></qp:autocomplete>
												<input type="hidden" name="isBundle" value="true" /> 
											</td>
										</tr>
										<tr>
												<th><qp:message code="sc.screendesign.0018"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>
													<qp:autocomplete 
					                                        optionValue="optionValue" optionLabel="optionLabel"
					                                        selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
					                                        name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId}"
					                                        displayValue="${screenDesignForm.moduleName}" 
					                                        onChangeEvent="changeModule" onSelectEvent="selectModuleNameLinkSetting"
					                                        value="${screenDesignForm.moduleId}" >
					                                </qp:autocomplete>
												</td>
											</tr>
										<tr>
											<th><qp:message code="sc.screendesign.0068"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
											<td>
												<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetScreenList" onChangeEvent="changeNavigationTo" emptyLabel="sc.sys.0030" optionValue="optionValue" name="navigateTo" arg03="${f:h(sessionScope.CURRENT_LANGUAGE.languageId)}" arg01="${screenDesignForm.moduleId}" arg02="20"/>
											</td>
										</tr>
									</table>	
								</div>
							</div>	
							</div>
							<div id="linkSetting-style" class="tab-pane">
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
													<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Common style</th>
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
															<option value="oblique"><qp:message code="sc.screendesign.0351"/></option>
														</select>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0352"/></th>
													<td><qp:autocomplete name="font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="false" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
												</tr>
												<tr>
													<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Hover style</th>
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
															<option value="oblique"><qp:message code="sc.screendesign.0351"/></option>
														</select>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0352"/></th>
													<td><qp:autocomplete name="hoverStyle=font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="false" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
												</tr>
												<tr>
													<th>Background color</th>
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