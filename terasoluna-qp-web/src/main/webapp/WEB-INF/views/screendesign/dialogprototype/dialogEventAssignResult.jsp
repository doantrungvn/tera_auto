
<div class="modal-dialog modal-lg" style="min-height: 500px;width: 800px;">
	<div class="modal-content">
		<div class="modal-header qp-model-header" style="border-bottom: 0px;">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text">Mapping result field </span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list">
						<colgroup>
							<col width="5%"/>
							<col width="30px" />
							<col width="20%" />
							<col width="20%" />
							<col width="15%" />
							<col width="30%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="5">Business logic output</th>
								<th style="color: blue;">Effect area</th>
							</tr>
							<tr>
								<th>No.</th>
								<th colspan="2">Object name</th>
								<th>Object code</th>
								<th>Data type</th>
								<th>Area name: Product info</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="com-output-fixlength">1</td>
								<td colspan="2">Product</td>
								<td>product</td>
								<td>Object</td>
								<td>Area item</td>
							</tr>
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.1</td>
								<td>Product name</td>
								<td>productName</td>
								<td>String</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value=""> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.2</td>
								<td>Price</td>
								<td>price</td>
								<td>Currency</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value="price"> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>	
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.3</td>
								<td>Quantity</td>
								<td>quantity</td>
								<td>Integer</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value="quantity"> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<br />
			<div class="qp-div-action">
				<button type="button" class="btn btn-md btn-success qp-link-button"
					data-dismiss="modal">Save</button>
			</div>
		</div>
	</div>
</div>

<!-- <div class="modal-dialog modal-lg" style="min-height: 500px;width: 800px;">
	<div class="modal-content">
		<div class="modal-header qp-model-header" style="border-bottom: 0px;">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text">Mapping result field </span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list">
						<colgroup>
							<col width="5%"/>
							<col width="30px" />
							<col width="20%" />
							<col width="20%" />
							<col width="15%" />
							<col width="30%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="5">Business logic output</th>
								<th style="color: blue;">Effect area</label></th>
							</tr>
							<tr>
								<th>No.</th>
								<th colspan="2">Object name</th>
								<th>Object code</th>
								<th>Data type</th>
								<th>Area name: Order list</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="com-output-fixlength">1</td>
								<td colspan="2">Order</td>
								<td>Order</td>
								<td>Object</td>
								<td>Area item</td>
							</tr>
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.1</td>
								<td>Order code</td>
								<td>orderCode</td>
								<td>String</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value="orderCode"> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.2</td>
								<td>Customer</td>
								<td>customer</td>
								<td>String</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value="customer"> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>	
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.3</td>
								<td>Order date</td>
								<td>orderDate</td>
								<td>Datetime</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value="orderDate"> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="com-output-fixlength"></td>
								<td>1.3</td>
								<td>Order status</td>
								<td>status</td>
								<td>Integer</td>
								<td>
									<div class="input-group " style="">
										<input type="text"
											class="form-control qp-input-autocomplete ui-autocomplete-input"
											value="status"> <span
											class="input-group-addon dropdown-toggle"
											data-dropdown="dropdown" style="cursor: pointer;"><span
											class="caret"></span></span>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<br />
			<div class="qp-div-action">
				<button type="button" class="btn btn-md btn-success qp-link-button"
					data-dismiss="modal">Save</button>
			</div>
		</div>
	</div>
</div>  -->