<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">	
	    Modify Form
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a class="com-link-popup" href="${pageContext.request.contextPath}/sample/search?init">Search sample form</a>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<script id="dynamic-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex">1</td>
				<td><input type="text" name="sampleChild[].columnText" class="form-control qp-input-text"></td>
				<td><input type="text" id="sampleChild[].columnAutocomplete.id" class="form-control qp-input-autocomplete-test" data-selectsqlid="getAllType" data-optionlabel="optionLabel" data-optionvalue="optionValue" data-onselect="" data-onchange="" data-onremove="" data-emptylabel="" data-arg01="" data-arg02="" data-arg03="" data-arg04="" data-arg05="" data-arg06="" data-arg07="" data-arg08="" data-arg09="" data-arg10="" data-arg11="" data-arg12="" data-arg13="" data-arg14="" data-arg15="" data-arg16="" data-arg17="" data-arg18="" data-arg19="" data-arg20="" autocomplete="off">
					<input type="hidden" name="sampleChild[].columnAutocomplete.id">				
				</td>
				<td>
					<div class='input-group date qp-input-datetimepicker-detail'>
						<input type="text" name="sampleChild[].columnDatetime" class="form-control">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</td>
				<td><input type="text" name="sampleChild[].columnInteger" class="form-control qp-input-integer" /></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
			</tr>
		</script>
		<form:form method="post" modelAttribute="sampleForm" enctype="multipart/form-data" action="${pageContext.request.contextPath}/sample/modify">
			<form:errors path="*" cssClass="qp-error" element="div" />
			<form:hidden path="columnId"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text">Sample information</span>
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
							<th>Column integer <label class="qp-required-field">(*)</label></th>
							<td><form:input path="columnInteger" cssClass="form-control qp-input-integer" value="${sampleForm.columnInteger}"/></td>
							<th>Column float</th>
							<td><form:input path="ColumnFloat" cssClass="form-control qp-input-float" /></td>
						</tr>
						<tr>
							<th>Column text</th>
							<td><form:input path="columnText" cssClass="form-control qp-input-text" /></td>
							<th>Column currency</th>
							<td><form:input path="columnCurrency" cssClass="form-control qp-input-currency" /></td>
						</tr>
						<tr>
							<th>Column percentage decimal</th>
							<td><form:input path="columnPercentageDecimal" cssClass="form-control qp-input-percentage-decimal" /></td>
							<th>Column percentage</th>
							<td><form:input path="columnPercentage" cssClass="form-control qp-input-percentage" /></td>
						</tr>
						<tr>
							<th>Column datepicker</th>
							<td>
								<div id="test" class='input-group date qp-input-datepicker'>
									<form:input path="columnDate" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</td>
							<th>Column datepicker-detail</th>
							<td>
								<div id="test" class='input-group date qp-input-datetimepicker-detail'>
									<form:input path="columnDatetime" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th>Column select<label class="qp-required-field">(*)</label></th>
							<td>
								<form:select path="columnSelect" cssClass="form-control qp-input-select">
									<form:option value="">--- Select ---</form:option>
									<form:options items="${SampleTypes }" itemLabel="nameType" itemValue="id" />
								</form:select>
							</td>
							<th>Column autocomplete<label class="qp-required-field">(*)</label></th>
							<td>
								<qp:autocomplete optionValue="optionValue" selectSqlId="getAllType" value="${sampleForm.columnAutocomplete }" emptyLabel="sc.sys.0030" name="columnAutocomplete" optionLabel="optionLabel"></qp:autocomplete>
							</td>
						</tr>
						<tr>
							<th>Column timepicker</th>
							<td><div class='input-group date qp-input-timepicker'>
									<form:input path="columnTime" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
							</td>
							<th>Column checkbox</th>
							<td><form:checkboxes path="columnCheckbox.id" items="${CL_SAMPLE}" cssClass="qp-input-checkbox qp-input-checkbox-margin"/> </td>
						</tr>
						<tr>
							<th>Column radio</th>
							<td><form:radiobuttons path="columnRadio.id" cssClass="qp-input-radio-margin qp-input-radio" items="${CL_SAMPLE}"/></td>
							<th>Image</th>
							<td><form:input path="columnImage" cssClass="qp-input-file-detail" type="file" /></td>
						</tr>
						<tr>
							<td colspan="4">
								
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">List sample child</span>
				</div>
				<div class="panel-body">
					<table id="dynamic" class="table table-bordered qp-table-list">
						<colgroup>
							<col width="5%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="5%" />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th>Text format</th>
								<th>Autocomplete</th>
								<th>datetime</th>
								<th>Integer<label class="qp-required-field">(*)</label></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${sampleForm.sampleChild }" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">
										${status.index + 1}
										<form:hidden path="sampleChild[${status.index }].columnId" />
										<form:hidden path="sampleChild[${status.index }].sample.columnId" /></td>
									<td><input type="text" name="sampleChild[${status.index }].columnText" class="form-control qp-input-text" value="${item.columnText }"></td>
									<td>
										<qp:autocomplete optionLabel="optionLabel" selectSqlId="getAllType" mustMatch="false" optionValue="optionValue"  name="sampleChild[${status.index }].columnAutocomplete.id"></qp:autocomplete>													
<%-- 										<qp:autocomplete optionValue="optionValue" selectSqlId="getAllType" name="sampleChild[${status.index }].columnAutocomplete.id" value="${item.columnAutocomplete.id }" optionLabel="optionLabel" mustMatch="false"></qp:autocomplete>													 --%>
									</td>
									<td>
										<div  class='input-group date qp-input-datetimepicker-detail'>
											<input type="text" name="sampleChild[${status.index }].columnDatetime" value="${item.columnDatetime }" class="form-control">
											<span class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</td>
									<td><input type="text" name="sampleChild[${status.index }].columnInteger" value="${item.columnInteger }" class="form-control qp-input-integer" /></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</c:forEach>
							<c:if test="${empty sampleForm.sampleChild}">
								<tr>
									<td class="qp-output-fixlength tableIndex">1</td>
									<td><input type="text" name="sampleChild[0].columnText" class="form-control qp-input-text"></td>
									<td>
										<qp:autocomplete optionLabel="optionLabel" selectSqlId="getAllType" mustMatch="false" optionValue="optionValue" name="sampleChild[0].columnAutocomplete.id"></qp:autocomplete>
<%-- 										<qp:autocomplete optionLabel="optionLabel" selectSqlId="getAllType" name="sampleChild[0].columnAutocomplete.id" optionValue="optionValue" mustMatch="false"></qp:autocomplete> --%>
									</td>
									<td>
										<div id="test" class='input-group date qp-input-datetimepicker-detail'>
											<input type="text" name="sampleChild[0].columnDatetime" class="form-control">
											<span class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</td>
									<td><input type="text" name="sampleChild[0].columnInteger" class="form-control qp-input-integer" /></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</c:if>
						</tbody>
					</table> 
					<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJS('dynamic');" style="margin-top: 3px;" href="javascript:void(0)"></a>				
				</div>
			</div>			
			<div class="qp-div-action">
				<input type="button" value="Save" class="btn qp-button qp-dialog-confirm">
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
