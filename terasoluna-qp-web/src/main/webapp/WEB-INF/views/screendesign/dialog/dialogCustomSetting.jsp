
<div class="modal-dialog">
		    	<div class="modal-content">	
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
		      		<div class="modal-body" >
		      			<ul class="nav nav-tabs">
							<li class="active"><a href="#dialog-custom-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0452" /></a></li>	
							<%-- <li><a href="#dialog-custom-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li> --%>
						</ul>
						<div class="tab-content">
							<div id="dialog-custom-setting-properties" class="tab-pane active">
		       					<table class="table table-bordered qp-table-form">
									<tr>
										<td>
											<textarea name="customItemContent" style="width: 100%; text-align: left; height: 200px" rows="" cols="" ></textarea>
											<input type="hidden" name="ajaxContent" value="" />
											<input type="hidden" name="f" value="" />
											<p class="text-warning" style="margin-top:10px"><qp:message code="sc.sqldesign.0050"></qp:message></p>
											<input type="hidden" name="dialogCustomSettingHidden" />
										</td>
									</tr>
								</table>
		      				</div>
		      				<div id="dialog-custom-setting-style" class="tab-pane">
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
		        		<button type="button" class="btn qp-button-client " onclick="deleteDialogCustomSetting()"><qp:message code="sc.sys.0014"/></button>
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogCustomSetting()"><qp:message code="sc.sys.0054"/></button>
		      		</div>
		   	 	</div>
		  	</div>