<div class="modal-dialog">
		    	<div class="modal-content">		
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>       
		      		<div class="modal-body">
		      			<ul class="nav nav-tabs">
							<li class="active">
								<a href="#dialog-button-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a>
							</li>
							<li><a href="#dialog-button-setting-event" data-toggle="tab"><qp:message code="sc.screendesign.0065"/></a></li>
						</ul>
						<div class="tab-content" style="min-height: 450px;">
							<div id="dialog-button-setting-properties" class="tab-pane active">
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
													<td><input type="text" class="form-control" name="actionLabel" maxlength="250"/></td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0068"/></th>
													<td>
														<qp:autocomplete optionLabel="" selectSqlId="" optionValue="" name=""/>
													</td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0083"/></th>
													<td><input type="checkbox" name="isSubmit" checked="checked" onclick="changeSubmitDialogAction(this)"/></td>
												</tr>
												<tr>
													<th><qp:message code="sc.screendesign.0069"/></th>
													<td><input type="text" class="form-control" name="actionName" maxlength="100"/></td>
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
							<div id="dialog-button-setting-event" class="tab-pane"></div>
						</div>
					</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogButtonSetting(this)"><qp:message code="sc.sys.0014"/></button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogButtonSetting(this)"><qp:message code="sc.sys.0054"/></button>
		      		</div>					
				</div>
			</div>