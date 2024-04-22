<div class="modal-dialog">
		    	<div class="modal-content">		
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>       
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-button-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"></qp:message></a>
							</li>
							<li><a href="#dialog-button-setting-event" data-toggle="tab"><qp:message code="sc.screendesign.0065"></qp:message></a></li>
						</ul>
						<div class="tab-content" style="min-height: 450px;">
							<div id="dialog-button-setting-properties" class="tab-pane active">
								<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0084"></qp:message></span>
									</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table-form">
												<colgroup>
													<col width="40%"/>
													<col width="60%"/>
												</colgroup>
												<tr>
													<th><qp:message code="sc.screendesign.0082"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><input type="text" class="form-control" name="actionLabel" maxlength="250"/></td>
												</tr>
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
														<qp:autocomplete optionLabel="" selectSqlId="" optionValue="" name=""></qp:autocomplete>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0083"></qp:message></th>
													<td><input type="checkbox" name="isSubmit" checked="checked" onclick="changeSubmitDialogAction(this)"/></td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0069"></qp:message></th>
													<td><input type="text" class="form-control" name="actionName" maxlength="100"/></td>
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
													<select name='dialog-component-setting-group-display-type' class="form-control">
														<option value="1"><qp:message code="sc.screendesign.0144"></qp:message></option>
														<option value="2"><qp:message code="sc.screendesign.0145"></qp:message></option>
													</select>
												</td>
											</tr>											
										</table>									
									</div>
								</div>							
							</div>
							<div id="dialog-button-setting-event" class="tab-pane"></div>
						</div>
					</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogButtonSetting(this)"><qp:message code="sc.sys.0014"></qp:message></button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogButtonSetting(this)"><qp:message code="sc.sys.0054"></qp:message></button>
		      		</div>					
				</div>
			</div>