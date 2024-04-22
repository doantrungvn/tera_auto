<input type="hidden" name="dialogCustomSettingHidden" />
<div class="modal-dialog">
		    	<div class="modal-content">	
		    		<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
		      		<div class="modal-body" >
		      			<ul class="nav nav-tabs">
							<li class="active"><a href="#dialog-custom-section-setting-properties" data-toggle="tab"><qp:message code="sc.screendesign.0452" /></a></li>	
							<%-- <li><a href="#dialog-custom-section-setting-style" data-toggle="tab"><qp:message code="sc.screendesign.0339"/></a></li> --%>
						</ul>
						<div class="tab-content">
							<div id="dialog-custom-section-setting-properties" class="tab-pane active">
		       					<table class="table table-bordered qp-table-form">
									<tr>
										<td>
											<textarea name="customSectionContent" style="width: 100%; text-align: left; height: 200px" rows="" cols="" ></textarea>
											<p class="text-warning" style="margin-top:10px"><qp:message code="sc.sqldesign.0050"></qp:message></p>
											<input type="hidden" name="isBundle" value="true" />
										</td>
									</tr>
								</table>
		      				</div>
		      				<div id="dialog-custom-section-setting-style" class="tab-pane">
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
												<th><qp:message code="sc.screendesign.0346"/></th>
												<td>
													<div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="panelStyle=color" class="form-control" type="text" value="">
													</div>
												</td>
											</tr>
											<tr style="font-size: ">
												<th><qp:message code="sc.screendesign.0347"/></th>
												<td>
													<div class="input-group">
														<input class="form-control qp-input-integer" name="panelStyle=font-size" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
													</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0348"/></th>
												<td>
													<select name="panelStyle=font-style" class="form-control">
														<option><qp:message code="sc.screendesign.0142"/></option>
														<option><qp:message code="sc.screendesign.0350"/></option>
													</select>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0352"/></th>
												<td><qp:autocomplete name="panelStyle=font-weight" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0362"/></th>
												<td>
												<div class="input-group">
													<input name="panelStyle=border-width" class="form-control qp-input-integer" type="text" value=""> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
												</div>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0363"/></th>
												<td>
													<qp:autocomplete name="panelStyle=border-style" optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="borderStyle"></qp:autocomplete>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.screendesign.0443" /></th>
												<td><div class="input-group qp-input-pickcolor colorpicker-element">
														<span class="input-group-addon"> <i></i></span>
														<input name="panelStyle=background-color" class="form-control" type="text" value="">
													</div></td>
											</tr>
											</table>
											
											</div>
										</div>
								</div>
		      			</div>
		      		</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn qp-button-client " onclick="saveDialogCustomSectionSetting()"><qp:message code="sc.sys.0054"/></button>
		      		</div>
		   	 	</div>
		  	</div>