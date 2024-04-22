<tiles:insertDefinition name="layouts-popup">

	<tiles:putAttribute name="header-name">
		Modify screen permission
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<div class="panel panel-default  qp-div-information">
			<div class="panel-heading">
				<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span
					class="qp-heading-text">Screen Information</span>
			</div>
			<div class="panel-body">
				<table class="table table-bordered qp-table-form">
					<colgroup>
						<col width="20%" />
						<col width="30%" />
						<col width="20%" />
						<col width="30%" />
					</colgroup>
					<tr>
						<th>Username</th>
						<td>admin</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>Screen</th>
						<td>Register Order screen</td>
						<th>Screen pattern</th>
						<td>Register</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
				<span class="qp-heading-text">
					Screen Item information
				</span>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-bordered qp-table-list">
						<colgroup>
							<col width="40px"/>
							<col width="50%" />
							<col width="50%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="3" style="text-align: left;">Order Information</th>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th>Screen Item name</th>
								<th>Edit permission</th>
							</tr>
						</thead>
						<tr class="form-inline">
							<td> 1</td>
							<td class="com-output-text">Order no <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox"  disabled/>
							</td>
						</tr>
						<tr class="form-inline">
							<td> 2</td>
							<td class="com-output-text">Status <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" disabled />
							</td>
						</tr>
						<tr class="form-inline">
							<td> 3</td>
							<td class="com-output-text">Order date</td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" />
							</td>
						</tr>
						<tr class="form-inline">
							<td> 4</td>
							<td class="com-output-text">Payment type <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" disabled/>
							</td>
						</tr>
						<tr class="form-inline">
							<td> 5</td>
							<td class="com-output-text">Shipping address</td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" />
							</td>
						</tr>
						<tr class="form-inline">
							<td> 6</td>
							<td class="com-output-text">Remark</td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" />
							</td>
						</tr>
					</table>
					<br/>
					<table class="table table-bordered qp-table-list">
						<colgroup>
							<col width="40px"/>
							<col width="50%" />
							<col width="50%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="3" style="text-align: left;">List OrderDetail</th>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th>Screen Item name</th>
								<th>Edit permission</th>
							</tr>
						</thead>
						<tr class="form-inline">
							<td> 1</td>
							<td class="com-output-text">Product <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox"  disabled/>
							</td>
						</tr>
						<tr class="form-inline">
							<td> 2</td>
							<td class="com-output-text">Unit price <label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" disabled />
							</td>
						</tr>
						<tr class="form-inline">
							<td> 3</td>
							<td class="com-output-text">Quantity</td>
							<td>
								<input type="checkbox" checked class="checkbox qp-input-checkbox" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="qp-div-action">
				<button type="button" class="btn qp-button" onclick="parent.jQuery.fancybox.close();">Save</button>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>