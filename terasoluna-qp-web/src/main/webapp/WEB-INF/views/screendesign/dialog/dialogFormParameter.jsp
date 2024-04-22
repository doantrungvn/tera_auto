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
											<col width="35%"/>
											<col width="35%"/>
											<col width="30%"/>
											<col />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.sys.0004"/></th>
												<th><qp:message code="sc.screendesign.0336"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<th><qp:message code="sc.screendesign.0071"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<th><qp:message code="sc.decisiontable.0017"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td><input type="text" class="form-control qp-convention-name-row" name="parameterName" maxlength="100"/>
													<input type="hidden" name="parameterId"/>
												</td>
												<td><input type="text" class="form-control qp-convention-code-row" name="parameterAttribute" maxlength="100"/></td>
												<td>
													<form:select path="" name="parameterDatatype" class="form-control">
														<form:option value=""/>
														<form:options items="${CL_BD_DATATYPE_PARAMETER}"/>
													</form:select>
												</td>
												<td class="com-output-fixlength">
													<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
												</td>
											</tr>
										</tbody>
									</table>
									<div class="qp-add-left">
										<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);$.qp.initialConventionNameCode();" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</div>
					      		</div>
			      			</div>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn qp-button-client " onclick="saveScreenSetting(this)"><qp:message code="sc.sys.0054"/></button>
			      		</div>
			   	 	</div>
			  	</div>