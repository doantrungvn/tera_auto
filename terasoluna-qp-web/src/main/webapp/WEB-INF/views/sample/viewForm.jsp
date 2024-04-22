<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">	
	    View form
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
		<a class="qp-link-popup" href="${pageContext.request.contextPath}/sample/createForm"></a>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="sampleForm" action="${pageContext.request.contextPath}/sample/delete">
			<form:hidden path="columnId"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">Search Sample</span>
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
							<th>Column integer</th>
							<td><qp:formatInteger value="${sample.columnInteger }"></qp:formatInteger></td>
							<th>Column float</th>
							<td><qp:formatFloat value="${sample.columnFloat }"></qp:formatFloat></td>
						</tr>
						<tr>
							<th>Column text</th>
							<td><qp:formatText value="${sample.columnText }"></qp:formatText></td>
							<th>Column currency</th>
							<td><qp:formatCurrency value="${sample.columnCurrency }"></qp:formatCurrency></td>
						</tr>
						<tr>
							<th>Column timepicker</th>
							<td><qp:formatDate value="${sample.columnTime }"></qp:formatDate></td>
							<th>Column percentage</th>
							<td><qp:formatInteger value="${sample.columnPercentage }" suffix="%"></qp:formatInteger></td>
						</tr>
						<tr>
							<th>Column date</th>
							<td><qp:formatDate value="${sample.columnDate }"></qp:formatDate></td>
							<th>Column datetime</th>
							<td><qp:formatDate value="${sample.columnDatetime }"></qp:formatDate></td>
						</tr>
						<tr>
							<th>Column radio</th>
							<td><qp:formatText value="${sample.columnRadio.nameType }"></qp:formatText></td>
							<th>Column checkbox</th>
							<td><qp:formatText value="${sample.columnRadio.nameType }"></qp:formatText> </td>
						</tr>
						<tr>
							<th>image</th>
							<td><qp:image width="50" heigth="40" src="${sample.base64Image}"></qp:image></td>
							<th>Column percentage decimal 	</th>
							<td><qp:formatFloat value="${sample.columnPercentageDecimal }" suffix="%"></qp:formatFloat></td>
						</tr>
					
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">List sample child</span>
				</div>
				<div class="panel-body">
					<table id="dynamic" class="table table-bordered qp-table-list-none-action">
						<colgroup>
							<col width="5%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20" />
							<col width="20%" />							
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th>Text format</th>
								<th>Autocomplete</th>
								<th>datetime</th>
								<th>Integer</th>								
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${sample.sampleChild }"
								varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">${status.index + 1}</td>
									<td><qp:formatText value="${item.columnText }"></qp:formatText> </td>
									<td><qp:formatText value="${item.columnAutocomplete.nameType} "></qp:formatText> </td>
									<td>										
										<qp:formatDateTime value="${item.columnDatetime }"></qp:formatDateTime>
									</td>
									<td><qp:formatInteger value="${item.columnInteger }"></qp:formatInteger> </td>									
								</tr>
							</c:forEach>							
						</tbody>
					</table>					
				</div>
			</div>			
			<div class="qp-div-action">
				<button type="button" class="btn qp-button-warning qp-dialog-confirm" name="mode" value="3">Delete</button>
				<a href="${pageContext.request.contextPath}/sample/modify?id=${sample.columnId}" class="btn qp-button qp-link-button qp-link-popup-navigate" type="submit">Modify</a>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>