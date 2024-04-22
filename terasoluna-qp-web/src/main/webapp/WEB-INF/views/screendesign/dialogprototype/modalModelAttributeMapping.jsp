<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body">

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text">Mapping model</span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="40%">
							<col width="60%">
						</colgroup>
						<tbody>
							<tr>
								<td colspan="4">
									<table class="table table-bordered qp-table-list">
										<colgroup>
											<col width="6%"/>
											<col width="35px" />
											<col width="35px" />
											<col width="25%" />
											<col width="22%" />
											<col width="15%" />
											<col width="10%" />
											<col width="5%" />
										</colgroup>
										<thead>
											<tr>
												<th colspan="7">Model attribute</th>
												<th></th>
											</tr>
											<tr>
												<th>No.</th>
												<th colspan="3">Object name</th>
												<th>Object code</th>
												<th>Data type</th>
												<th>Is Array</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="com-output-fixlength">1</td>
												<td colspan="3">Order form</td>
												<td>OrderForm</td>
												<td>Object</td>
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td>1.1</td>
												<td colspan="2">Order code</td>
												<td>orderCode</td>
												<td>Long</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td>1.2</td>
												<td colspan="2">Order date</td>
												<td>orderDate</td>
												<td>Date</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td>1.3</td>
												<td colspan="2">Remark</td>
												<td>remark</td>
												<td>String</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td>1.4</td>
												<td colspan="2">Status</td>
												<td>status</td>
												<td>Integer</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td>1.5</td>
												<td colspan="2">Customer</td>
												<td>customer</td>
												<td>Object</td>
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.5.1</td>
												<td >Customer id</td>
												<td>customerId</td>
												<td>Long</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.5.2</td>
												<td >Full name</td>
												<td>fullName</td>
												<td>String</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.5.3</td>
												<td >BirthDate</td>
												<td>birthDate</td>
												<td>Date</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.5.4</td>
												<td >Email</td>
												<td>email</td>
												<td>String</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td>1.6</td>
												<td colspan="2">Product</td>
												<td>product</td>
												<td>Object</td>
												<td><span class="glyphicon glyphicon-check" aria-hidden="true"></span></td>
												<td></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.6.1</td>
												<td >Product name</td>
												<td>productName</td>
												<td>String</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.6.2</td>
												<td >Quantity</td>
												<td>quantity</td>
												<td>Integer</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
											<tr>
												<td class="com-output-fixlength"></td>
												<td></td>
												<td >1.6.3</td>
												<td >Price</td>
												<td>price</td>
												<td>Float</td>
												<td></td>
												<td><input name="selectcode" type="radio" /></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<input type="button" onclick="saveModalFormSetting()"
				class="btn qp-button-client" value='<qp:message code="sc.sys.0054"></qp:message>' />
		</div>
	</div>
	<!-- /.modal-content -->
</div>