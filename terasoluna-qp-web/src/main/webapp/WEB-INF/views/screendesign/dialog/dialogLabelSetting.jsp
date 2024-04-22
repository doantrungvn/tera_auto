<input type="hidden" name="dialogLabelSettingHidden" />
<div class="modal-dialog">
		    	<div class="modal-content">	
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  	     
		      		<div class="modal-body" >
		      			<ul class="nav nav-tabs">
							<li class="active"><a href="#dialog-label-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0064"/></a></li>
							<!-- <li><a href="#dialog-label-setting-sort-column" data-toggle="tab">Header sort</a></li> -->	
							<li><a href="#dialog-label-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li>						
						</ul>
						<div class="tab-content">
							<div id="dialog-label-setting-properties" class="tab-pane active">
				      			<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text"><qp:message code="sc.screendesign.0102"/></span>
									</div>
									<div class="panel-body" style="height: 250px;">
				       					<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>
											<tr>
												<th><qp:message code="sc.screendesign.0103"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>	
												<td>
													<div style="float: left; width: 80%; margin-right: 4px;">
														<qp:autocomplete optionLabel="optionLabel"  optionValue="optionValue" mustMatch="false" name="labelName" onChangeEvent="showMessageLevel" onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" arg03="${screenDesignForm.screenId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"/>
														<input type="hidden" name="isBundle" value="true" /> 	
													</div>												
													<div class="vertical-middle level-text"></div>	
												</td>
											</tr>
											
											<tr id="trRequire">
													<th><qp:message code="sc.screendesign.0100"/></th>
													<td><input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" value="true" name="mandatory"/></td>
											</tr>
											
											<tr id="widthLabelDisplay" style="display: none">
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
											<!-- 
											<tr>
												<th><qp:message code="sc.screendesign.0101"/></th>
												<td><input name="isBlank" onclick="dialogLableSettingChecked()" type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox"></td>
											</tr>
											 -->	
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
													<div style="float: left; width: 100px; margin-right: 4px;"><input name="rowspan" type="text" class="qp-numeric-up-down form-control" /></div>												
													<div class="vertical-middle"><qp:message code="sc.screendesign.0077"/></div> 
												</td>
											</tr>
											<%-- <tr>
												<th>Enable sort</th>
												<td><input type="checkbox" /></td>
											</tr>
											 <tr>
												<th>Sort column</th>
												<td>
													<table class="table table-bordered qp-table-form">
														<tr>
															<th align="center" style="text-align: center;">Sql</th>
															<th align="center" style="text-align: center;">Sql output</th>
														</tr>
														<tr>
															<td><qp:autocomplete optionLabel="optionLabel" displayValue="Customer"  optionValue="optionValue" mustMatch="false" name="te" selectSqlId=""/></td>
															<td><qp:autocomplete optionLabel="optionLabel" displayValue="Customer name"  optionValue="optionValue" mustMatch="false" name="te" selectSqlId=""/></td>
														</tr>
													</table>
												</td>
											</tr> --%>	 
										</table>
				      				</div>
				      			</div>
		      				</div>
		      				<div id="dialog-label-setting-sort-column" class="tab-pane active">
				      			<div class="panel panel-default qp-div-information">
									<div class="panel-heading">
										<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
										<span class="pq-heading-text">Sort column setting</span>
									</div>
									<div class="panel-body" style="height: 250px;">
				       					<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="40%"/>
												<col width="60%"/>
											</colgroup>
											<tr>
												<th>Enable sort</th>
												<td><input type="checkbox" /></td>
											</tr>
											 <tr>
												<th>Sort column</th>
												<td>
													<table class="table table-bordered qp-table-form">
														<tr>
															<th align="center" style="text-align: center;">Sql</th>
															<th align="center" style="text-align: center;">Sql output</th>
														</tr>
														<tr>
															<td><qp:autocomplete optionLabel="optionLabel" displayValue="Customer"  optionValue="optionValue" mustMatch="false" name="te" selectSqlId=""/></td>
															<td><qp:autocomplete optionLabel="optionLabel" displayValue="Customer name"  optionValue="optionValue" mustMatch="false" name="te" selectSqlId=""/></td>
														</tr>
													</table>
												</td>
											</tr>	 
										</table>
				      				</div>
				      			</div>
		      				</div>
		      				<div id="dialog-label-setting-style" class="tab-pane">
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
		      		</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogLableSetting()"><qp:message code="sc.sys.0014"/></button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogLableSetting()"><qp:message code="sc.sys.0054"/></button>
		      		</div>
		   	 	</div>
		  	</div>