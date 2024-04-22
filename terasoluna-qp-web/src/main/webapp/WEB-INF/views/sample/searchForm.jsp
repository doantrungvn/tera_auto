<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">	
	    Sample search
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">	
	<qp:authorization permission="sample_search"> 
		<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
		<a class="com-link-popup" href="${pageContext.request.contextPath}/sample/register">Register sample form</a>
	</qp:authorization>	
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="get" modelAttribute="sampleSearchForm" action="${pageContext.request.contextPath}/sample/search">
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"></qp:message> </span>
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
							<th>Column text</th>
							<td><form:input path="columnText" cssClass="form-control qp-input-text" /></td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th>Column integer from</th>
							<td>
								<form:input path="columnIntegerFrom" cssClass="form-control qp-input-from-integer pull-left" />
								<div class="qp-separator-from-to">~</div>
								<form:input path="columnIntegerTo" cssClass="form-control qp-input-to-integer pull-right" />
							</td>
							<th>Column currency from</th>
							<td>
								<form:input path="columnCurrencyFrom" cssClass="form-control qp-input-from-currency pull-left" />
								<div class="qp-separator-from-to">~</div>
								<form:input path="columnCurrencyTo" cssClass="form-control qp-input-to-currency pull-right" />
							</td>
						</tr>
						<tr>
							<th>Column float from</th>
							<td>
								<form:input path="columnFloatFrom" cssClass="form-control qp-input-from-float pull-left" />
								<div class="qp-separator-from-to">~</div>
								<form:input path="columnFloatTo" cssClass="form-control qp-input-to-float pull-right" />
							</td>
							<th>Column percentage</th>
							<td>
								<form:input path="columnPercentageFrom" cssClass="form-control qp-input-from-percentage pull-left" />
								<div class="qp-separator-from-to">~</div>
								<form:input path="columnPercentageTo" cssClass="form-control qp-input-to-percentage pull-right" />
							</td>
						</tr>
						<tr>
							<th>Column percentage decimal</th>
							<td>
								<form:input path="columnPercentageDecimalFrom" cssClass="form-control qp-input-from-percentage-decimal pull-left" />
								<div class="qp-separator-from-to">~</div>
								<form:input path="columnPercentageDecimalTo" cssClass="form-control qp-input-to-percentage-decimal pull-right" />
							</td>
							<th>Column datetime</th>
							<td>
								<div id="datetimeFrom" class='input-group date qp-input-from-datetimepicker-detail pull-left'>
									<form:input path="columnDatetimeFrom" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div id="datetimeTo"  class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
									<form:input path="columnDatetimeTo" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th>Column select</th>
							<td>
								<form:select path="columnSelect" cssClass="form-control qp-input-select">
									<form:option value=""><qp:message code="sc.sys.0030"></qp:message> </form:option>
									<form:options items="${SampleTypes }" itemLabel="nameType" itemValue="id" />
								</form:select>
							</td>
							<th>Column autocomplete</th>
							<td>								
								<qp:autocomplete optionValue="optionValue" selectSqlId="getAllType" emptyLabel="sc.sys.0030" value="${sampleSearchForm.columnAutocomplete}" name="columnAutocomplete" optionLabel="optionLabel"></qp:autocomplete>
							</td>
						</tr>
						<tr>
							<th>Column time</th>
							<td>
								<div class='input-group date qp-input-from-timepicker pull-left'>
									<form:input path="columnTimeFrom" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-timepicker pull-rigth'>
									<form:input path="columnTimeTo" cssClass="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
							</td>
							<th>Column date</th>
							<td>
								<div class='input-group date qp-input-from-datepicker pull-left'>
									<form:input path="columnDateFrom" cssClass="form-control" />
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
								<div class="qp-separator-from-to">~</div>
								<div class='input-group date qp-input-to-datepicker pull-rigth'>
									<form:input path="columnDateTo" cssClass="form-control" />
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th>Column radio</th>
							<td><form:radiobuttons cssClass="qp-input-radio qp-input-radio-margin" path="columnRadio" items="${CL_SAMPLE}"/></td>
							<th>Column checkbox</th>
							<td><form:checkboxes cssClass="qp-input-checkbox-margin qp-input-checkbox" items="${CL_SAMPLE}" path="columnCheckboxs"/></td>
						</tr>
						<tr>
							<th>Autocomplete test</th>
							<td><qp:autocomplete optionLabel="optionLabel" selectSqlId="getAllType" optionValue="optionValue" name="autocompleteTest"></qp:autocomplete></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<input type="button" value="Search" class="btn qp-button qp-button-search" />
			</div>		
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<!-- <div class="bs-callout bs-callout-info">  -->
					<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0003"></qp:message> &nbsp;
						<c:choose>
							<c:when test="${page != null }">
								<span class="badge">&nbsp;${page.totalElements}&nbsp;</span>		
							</c:when>
							<c:otherwise>
								<span class="badge">&nbsp;0&nbsp;</span>
							</c:otherwise>
						</c:choose>						
					</span>
						<qp:itemPerPage form="sampleSearchForm" action="/sample/search"></qp:itemPerPage>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-bordered qp-table-list" id="businessTypesTable">
							<colgroup>
								<col />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
								<col width="15%" />
								<col width="10%" />							
								<col width="10%" />
								<col width="15%" />
								<col />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message> </th>
									<th>Text</th>
									<th>Integer</th>
									<th>Float</th>
									<th>Percentage</th>
									<th>Date</th>
									<th>Datetime</th>
									<th>Currency</th>
									<th>Radio</th>
									<th>Checkbox</th>									
									<th></th>
								</tr>
							</thead>
							<c:if test="${page != null && page.totalPages > 0 }">
								<c:forEach var="item" items="${page.content }" varStatus="look">
									<tr>
										<td>${(page.number * page.size) + look.count}</td>
										<td>
											<a href="${pageContext.request.contextPath}/sample/view?columnId=${item.columnId }" class="qp-link-popup">
												<qp:formatText value="${item.columnText}"></qp:formatText>
											</a>
										</td>
										<td><qp:formatInteger value="${item.columnInteger }"></qp:formatInteger></td>
										<td><qp:formatFloat value="${item.columnFloat }"></qp:formatFloat></td>
										<td><qp:formatPercentage value="${item.columnPercentage }"></qp:formatPercentage></td>
										<td><qp:formatDate value="${item.columnDate }"></qp:formatDate></td>
										<td><qp:formatDateTime value="${item.columnDatetime }"></qp:formatDateTime></td>
										<td><qp:formatCurrency value="${item.columnCurrency }"></qp:formatCurrency></td>
										<td><qp:formatText value="${item.columnRadio.nameType}"></qp:formatText> </td>			
										<td><qp:formatText value="${item.columnCheckbox.nameType}"></qp:formatText></td>																															
										<td><a href="${pageContext.request.contextPath}/sample/modify?id=${item.columnId}" class="btn qp-button glyphicon glyphicon-pencil qp-link-button"></a></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						<div class="qp-div-action">
							<t:pagination page="${page}" outerElementClass="pagination pagination-md" criteriaQuery="${f:query(sampleSearchForm)}" />
						</div>
					</div>
				</div>
			</div>	
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
