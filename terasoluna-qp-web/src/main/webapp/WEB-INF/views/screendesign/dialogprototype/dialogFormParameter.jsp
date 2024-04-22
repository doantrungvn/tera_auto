<div class="modal-dialog">
			    	<div class="modal-content">
		    			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>  	
     
			      		<div class="modal-body">
			      			<div id="dialog-form-options-error" align="center"></div>
							<div class="panel panel-default qp-div-information">
								<div class="panel-heading">
									<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
									<span class="pq-heading-text"><qp:message code="sc.screendesign.0226"/></span>
								</div>
								<div class="panel-body">
									<table class="table table-bordered qp-table-list" id="dialog-form-parameter-tbl-parameter">
										<colgroup>
											<col />
											<col width="50%"/>
											<col width="45%"/>
											<col />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.sys.0004"></qp:message></th>
												<th><qp:message code="sc.screendesign.0071"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<th><qp:message code="sc.decisiontable.0017"/></th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td><input type="text" class="form-control" name="parameterAttribute" maxlength="100"/></td>
												<td><select class="form-control">
													<option><qp:message code="sc.validationrule.0018"/></option>
												</select></td>
												<td class="com-output-fixlength">
													<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
												</td>
											</tr>
										</tbody>
									</table>
									<div class="qp-add-left">
										<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</div>
					      		</div>
			      			</div>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn qp-button-client " onclick="saveScreenSetting(this)"><qp:message code="sc.sys.0054"></qp:message></button>
			      		</div>
			   	 	</div>
			  	</div>