<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		Screen item setting
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<link
			href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css"
			type="text/css" rel="stylesheet" />
	</tiles:putAttribute>

	<c:if test="${notExistFlg ne 1}">
		<tiles:putAttribute name="body">
			<form:form method="post"
				action="${pageContext.request.contextPath}/domaindatatype/search"
				modelAttribute="domainDatatypeForm">
				<form:hidden path="domainDatatypeId" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text">Table design information</span>
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
								<th>Table name</th>
								<td>Table design For Prototy</td>
								<th>Table code</th>
								<td>TableForPrototyp</td>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0028" /></th>
								<td colspan="3">Table design For Prototy</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text">Table detail information</span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-list">
							<colgroup>
								<col width="4%" />
								<col />
								<col width="20%" />
								<col width="15%" />
								<col width="7%" />
								<col width="13%" />
								<col width="5%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.domaindatatype.0010" /></th>
									<th><qp:message code="sc.domaindatatype.0011" /></th>
									<th><qp:message code="sc.tabledesign.0007" /></th>
									<!-- Data Type -->
									<th><qp:message code="sc.tabledesign.0008" /></th>
									<!-- Max length -->
									<th>Item type</th>
									<th>Used</th>
								</tr>
							</thead>

							<tbody>

								<tr>
									<td>1</td>
									<td>order_id <span class="qp-required-field">(PK)</span></td>
									<td>orderId</td>
									<td>Width</td>
									<td>16</td>
									<td><select class="form-control qp-input-select">
											<option value="">--Select--</option>
											<option value="">Checkbox</option>
											<option value="">Select</option>
											<option value="">Radio</option>
											<option value="" selected="selected">Autocomplete</option>
									</select></td>
									<td><input type="checkbox" checked="checked"
										disabled="disabled"></td>
								</tr>

								<tr>
									<td>2</td>
									<td>order_name</td>
									<td>orderName</td>
									<td>Character varying</td>
									<td>50</td>
									<td><select class="form-control qp-input-select">
											<option value="">--Select--</option>
											<option value="">Checkbox</option>
											<option value="">Select</option>
											<option value="">Radio</option>
											<option value="">Autocomplete</option>
											<option value="" selected="selected">Text input</option>
									</select></td>
									<td><input type="checkbox" checked="checked"></td>
								</tr>
								
								<tr>
									<td>3</td>
									<td>order_type</td>
									<td>orderType</td>
									<td>Integer</td>
									<td>10</td>
									<td><select class="form-control qp-input-select">
											<option value="">--Select--</option>
											<option value="">Checkbox</option>
											<option value="" selected="selected">Select</option>
											<option value="">Radio</option>
											<option value="">Autocomplete</option>
									</select></td>
									<td><input type="checkbox" checked="checked"></td>
								</tr>	
								

								<tr class="trDisable">
									<td>4</td>
									<td>order_date</td>
									<td>orderDate</td>
									<td>Date</td>
									<td>0</td>
									<td><select class="form-control qp-input-select">
											<option value="">--Select--</option>
											<option value="">Checkbox</option>
											<option value="">Select</option>
											<option value="">Radio</option>
											<option value="">Autocomplete</option>
									</select></td>
									<td><input type="checkbox"></td>
								</tr>
							</tbody>

						</table>
					</div>
				</div>

				<!-- if table design was deleted -->
				<c:if test="${notExistFlg ne 2}">
					<div class="qp-div-action">
						<qp:authorization permission="domaindatatypeModify">
							<a
								href="${pageContext.request.contextPath}/domaindatatype/modify?domainDatatypeId=${modelObj.domainDatatypeId}"
								class="btn btn-md btn-success qp-link-button qp-link-popup-navigate"
								type="submit">Save</a>
						</qp:authorization>
					</div>
				</c:if>
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>