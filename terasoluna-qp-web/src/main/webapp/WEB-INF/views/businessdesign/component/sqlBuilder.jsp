
<!-- Dialog sql builder setting -->
<div id="dialog-sqlbuilder-setting" class="modal fade" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body" style="height: 100%; max-height: 100%">
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0089" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0021" /> :</th>
									<td><select class="form-control qp-input-select" onchange="bdSelectSQLType(this)">
											<option value='0' selected="selected"><qp:message code="sc.businesslogicdesign.0119" /></option>
											<option value='1'><qp:message code="sc.businesslogicdesign.0215" /></option>
											<option value='1'><qp:message code="sc.businesslogicdesign.0216" /></option>
											<option value='1'><qp:message code="sc.businesslogicdesign.0217" /></option>
									</select></td>
									<th><qp:message code="sc.blogiccomponent.0090" /> :</th>
									<td><input type="text" class="form-control qp-input-text" value="order.sql.001" /></td>
								</tr>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0091" /> :</th>
									<td><input type="text" class="form-control qp-input-text" value="Get total order today" /></td>
									<th><qp:message code="sc.sys.0028" /> :</th>
									<td><textarea class="form-control qp-input-text"> </textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text"><qp:message code="sc.blogiccomponent.0092" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-borderless" id="tbl_tables">
							<colgroup>
								<col width="30%">
								<col width="30%">
								<col width="35%">
								<col width="5%">
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.blogiccomponent.0077" /></th>
									<th><qp:message code="sc.blogiccomponent.0093" /></th>
									<th><qp:message code="sc.blogiccomponent.0094" /></th>
									<th></th>
								</tr>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="UCASE"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order No."></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_tables', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="COUNT"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order Id"></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_tables', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</tbody>
						</table>
						<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJS('tbl_tables');" href="javascript:void(0)"></a>
						<br>
					</div>
				</div>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">FROM</span>
					</div>
					<div class="panel-body">
						<table class="table table-borderless" id="tbl_from">
							<colgroup>
								<col width="25%">
								<col width="10%">
								<col width="20%">
								<col width="20%">
								<col width="20%">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<td colspan="5">
										<div class="qp-div-highlight-border">
											<table class="table table-borderless">
												<colgroup>
													<col width="35%">
													<col width="65%">
												</colgroup>
												<tbody>
													<tr>
														<td style="width: 259px; vertical-align: top">
															<table class="table table-borderless">
																<tbody>
																	<tr>
																		<td><p class="text-info">
																				<b>JOIN TYPE</b>
																			</p></td>
																	</tr>
																	<tr>
																		<td><select class="form-control qp-input-select-detail">
																				<option selected="selected">INNER JOIN</option>
																				<option>LEFT JOIN</option>
																				<option>RIGHT JOIN</option>
																				<option>LEFT OUTER JOIN</option>
																				<option>RIGHT OUTER JOIN</option>
																		</select></td>
																		<td rowspan="2"><b>ON</b></td>
																	</tr>
																	<tr>
																		<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Customer"></td>
																		<td></td>
																	</tr>
																</tbody>
															</table>
														</td>
														<td style="width: 259px; vertical-align: top">
															<table class="table table-borderless" id="tbl_from1">
																<tbody>
																	<tr>
																		<td><p class="text-info">
																				<b>Order</b>
																			</p></td>
																		<td></td>
																		<td><p class="text-info">
																				<b>Customer</b>
																			</p></td>
																		<td></td>
																	</tr>
																	<tr>
																		<td><input class="form-control form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><select class="form-control">
																				<option>=</option>
																				<option>&lt;</option>
																				<option>&lt;=</option>
																				<option>&gt;</option>
																				<option>&gt;=</option>
																				<option>&lt;&gt;</option>
																				<option>Like</option>
																		</select></td>
																		<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_from1', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																	</tr>
																	<tr>
																		<td><input class="form-control form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><select class="form-control">
																				<option>=</option>
																				<option>&lt;</option>
																				<option>&lt;=</option>
																				<option>&gt;</option>
																				<option>&gt;=</option>
																				<option>&lt;&gt;</option>
																				<option>Like</option>
																		</select></td>
																		<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_from1', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																	</tr>
																</tbody>
															</table>
															<div class="qp-add-left">
																<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_from', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
								</tr>
							</tbody>
						</table>
						<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJS('tbl_from');" href="javascript:void(0)"></a>
						<br>
						<table class="table table-borderless" id="tbl_from3">
							<colgroup>
								<col width="25%">
								<col width="10%">
								<col width="20%">
								<col width="20%">
								<col width="20%">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<td colspan="5">
										<div class="qp-div-highlight-border">
											<table class="table table-borderless">
												<colgroup>
													<col width="35%">
													<col width="65%">
												</colgroup>
												<tbody>
													<tr>
														<td style="width: 259px; vertical-align: top">
															<table class="table table-borderless">
																<tbody>
																	<tr>
																		<td><p class="text-info">
																				<b>JOIN TYPE</b>
																			</p></td>
																	</tr>
																	<tr>
																		<td><select class="form-control qp-input-select-detail">
																				<option selected="selected">INNER JOIN</option>
																				<option>LEFT JOIN</option>
																				<option>RIGHT JOIN</option>
																				<option>LEFT OUTER JOIN</option>
																				<option>RIGHT OUTER JOIN</option>
																		</select></td>
																		<td rowspan="2"><b>ON</b></td>
																	</tr>
																	<tr>
																		<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Customer"></td>
																		<td></td>
																	</tr>
																</tbody>
															</table>
														</td>
														<td style="width: 259px; vertical-align: top">
															<table class="table table-borderless" id="tbl_from2">
																<tbody>
																	<tr>
																		<td><p class="text-info">
																				<b>Order</b>
																			</p></td>
																		<td></td>
																		<td><p class="text-info">
																				<b>Customer</b>
																			</p></td>
																		<td></td>
																	</tr>
																	<tr>
																		<td><input class="form-control form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><select class="form-control">
																				<option>=</option>
																				<option>&lt;</option>
																				<option>&lt;=</option>
																				<option>&gt;</option>
																				<option>&gt;=</option>
																				<option>&lt;&gt;</option>
																				<option>Like</option>
																		</select></td>
																		<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_from2', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																	</tr>
																	<tr>
																		<td><input class="form-control form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><select class="form-control">
																				<option>=</option>
																				<option>&lt;</option>
																				<option>&lt;=</option>
																				<option>&gt;</option>
																				<option>&gt;=</option>
																				<option>&lt;&gt;</option>
																				<option>Like</option>
																		</select></td>
																		<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Customer identify"></td>
																		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_from2', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																	</tr>
																</tbody>
															</table>
															<div class="qp-add-left">
																<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_from', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
								</tr>
							</tbody>
						</table>
						<div class="qp-add-left">
							<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
						</div>
						<br>
					</div>
				</div>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">GROUP BY</span>
					</div>
					<div class="panel-body">
						<table class="table table-borderless qp-table-form" id="tbl_group">
							<colgroup>
								<col width="30%">
								<col width="30%">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order Id"></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_group', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order No"></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_group', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Start order date"></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_group', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</tbody>
						</table>
						<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJS('tbl_group');" href="javascript:void(0)"></a>
					</div>
				</div>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">HAVING</span>
					</div>
					<div class="panel-body">
						<table class="table table-borderless" id="tbl_having">
							<colgroup>
								<col width="100%">
								<col width="44px">
							</colgroup>
							<tbody>
								<tr>
									<td colspan="1">
										<div class="qp-div-highlight-border">
											<table class="table table-borderless qp-table-form">
												<colgroup>
													<col width="10%">
													<col width="40%">
													<col width="12%">
													<col width="38%">
												</colgroup>
												<tbody>
													<tr>
														<td>&nbsp;</td>
														<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
														<td><select class="form-control qp-input-select-detail">
																<option selected="selected">Value</option>
																<option>Entity</option>
																<option>Function</option>
														</select></td>
														<td></td>
													</tr>
													<tr>
														<td>&nbsp;</td>
														<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order No."></td>
														<td><select class="form-control qp-input-select-detail">
																<option>=</option>
																<option>&lt;</option>
																<option>&lt;=</option>
																<option>&gt;</option>
																<option>&gt;=</option>
																<option>&lt;&gt;</option>
																<option selected="selected">Like</option>
																<option>not like</option>
																<option>is null</option>
																<option>is not null</option>
														</select></td>
														<td><input class="form-control qp-input-text" style="width: 140px" name="inputName" value="KH091"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_having', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
									<td colspan="1">
										<div class="qp-div-highlight-border">
											<table class="table table-borderless qp-table-form">
												<colgroup>
													<col width="10%">
													<col width="40%">
													<col width="12%">
													<col width="38%">
												</colgroup>
												<tbody>
													<tr>
														<td rowspan="2"><select class="form-control qp-input-select-detail">
																<option>AND</option>
																<option>OR</option>
														</select></td>
														<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
														<td><select class="form-control qp-input-select-detail">
																<option>Value</option>
																<option>Entity</option>
																<option selected="selected">Function</option>
														</select></td>
														<td></td>
													</tr>
													<tr>
														<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order date"></td>
														<td><select class="form-control qp-input-select-detail">
																<option>=</option>
																<option>&lt;</option>
																<option>&lt;=</option>
																<option>&gt;</option>
																<option selected="selected">&gt;=</option>
																<option>&lt;&gt;</option>
																<option>Like</option>
																<option>not like</option>
																<option>is null</option>
																<option>is not null</option>
														</select></td>
														<td><select class="form-control qp-input-select-detail" style="width: 120px">
																<option>Current Date</option>
														</select></td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_having', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
								<tr>
								</tr>
							</tbody>
						</table>
						<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJS('tbl_having');" href="javascript:void(0)"></a>
					</div>
				</div>
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="qp-heading-text">ORDER BY</span>
					</div>
					<div class="panel-body">
						<table class="table table-borderless qp-table-form" id="tbl_order">
							<colgroup>
								<col>
								<col>
								<col>
								<col width="5%">
							</colgroup>
							<tbody>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Modified time"></td>
									<td><select class="form-control qp-input-select-detail">
											<option>Ascending</option>
											<option selected="selected">Descending</option>
									</select></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.blogiccomponent.0095" /> ORDER BY' href="javascript:void(0)"></a></td>
								</tr>
								<tr>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order"></td>
									<td><input class="form-control qp-input-autocomplete-detail" name="inputName" value="Order No."></td>
									<td><select class="form-control qp-input-select-detail">
											<option>Ascending</option>
											<option>Descending</option>
									</select></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_order', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</tbody>
						</table>
						<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJS('tbl_order');" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn btn-md btn-warning qp-dialog-confirm"><qp:message code="sc.sys.0008" /></button>
					<button type="button" class="btn btn-md btn-success qp-link-button" data-dismiss="modal"><qp:message code="sc.sys.0031" /></button>
				</div>
			</div>
		</div>
	</div>
</div>