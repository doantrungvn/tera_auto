<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.autocomplete.0066" />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/autocompleteDesign/css/autocompleteDesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=autocomplete_sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/advancedsql.js"></script>
		<!-- Adding sql editor -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/codemirror.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/codemirror.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sql.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/show-hint.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/show-hint.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sql-hint.js"></script>
		<script type="text/javascript">
			$.qp.advancedsql.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="designForm"
			action="${pageContext.request.contextPath}/autocomplete/deleteConfirm">
			<c:set var="designForm" value="${autocompleteAdvancedDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.autocompleteForm.autocompleteId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.autocomplete.0003" /></span>
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
								<th><label><qp:message code="sc.autocomplete.0005"></qp:message></label></th>
								<td><qp:formatText value="${designForm.autocompleteForm.autocompleteName }"></qp:formatText></td>
								<th><label><qp:message code="sc.autocomplete.0006"></qp:message></label></th>
								<td><qp:formatText value="${designForm.autocompleteForm.autocompleteCode }"></qp:formatText></td>
							</tr>
							<tr>
								<th><label for="autocompleteForm.matchingTypes"><qp:message code="sc.autocomplete.0009"></qp:message></label></th>
								<td><qp:message code="${CL_MATCHING_TYPE.get(designForm.autocompleteForm.matchingType.toString())}"></qp:message>
								</td>
								<th><label for="autocompleteForm.moduleIdAutocomplete"><qp:message code="sc.autocomplete.0007"></qp:message></label></th>
								<td><qp:formatText value="${designForm.autocompleteForm.moduleIdAutocomplete }"></qp:formatText>
								</td>
							</tr>
							<tr>
								<th><label for="autocompleteForm.minLength"><qp:message code="sc.autocomplete.0062"></qp:message></label></th>
								<td><qp:formatInteger value="${designForm.autocompleteForm.minLength }"></qp:formatInteger></td>
								<th><form:label path="designStatus"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.autocompleteForm.designStatus.toString())}" /></td>
							</tr>
							<tr>
								<th><label><qp:message code="sc.sys.0028"></qp:message></label></th>
								<td colspan="3"><qp:formatRemark value="${designForm.autocompleteForm.remark}" /></td>
							</tr>
						</table>
					</div>
				</div>
				<ul class="nav nav-tabs">
					<li><a href="#tab-input" data-toggle="tab" style="font: bold;"><qp:message code='sc.sqldesign.0003'></qp:message></a></li>
					<li><a href="#tab-output" data-toggle="tab" style="font: bold;"><qp:message code='sc.sqldesign.0004'></qp:message></a></li>
					<li class="active"><a href="#tab-sql-design" data-toggle="tab" style="font: bold;"><qp:message code='sc.sqldesign.0009'></qp:message></a></li>
				</ul>
				<div class="tab-content">
					<div id="tab-input" class="tab-pane">
						<table class="table table-bordered qp-table-list-none-action" id="inputForm">
							<colgroup>
								<col />
								<col width="40px"/>
								<col width="40px"/>
								<col />
								<col width="20%"/>
								<col width="20%"/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${designForm.inputForm }" var="item" varStatus="status">
									<tr data-ar-rgroup="${item.groupId }" data-ar-rgroupindex="${f:h(item.itemSeqNo) }"  class="ar-dataRow">
										<td colspan="4" class="qp-none-padding">
											<div style="height:100%">
												<div><span class="ar-groupIndex">${item.itemSeqNo }</span></div>
												<div class="pull-right" style="height:100%;vertical-align: middle;text-align: left;">	
													<span>${f:h(item.sqlDesignInputName)}</span>
												</div>
											</div>
										</td>
										<td>
											${f:h(item.sqlDesignInputCode)}
										</td>
										<td>
											<div class="input-group" style="width:100%">
												${f:h(CL_SQL_DATATYPE.get(item.dataType))}
											</div>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty designForm.inputForm}">
									<tr>
										<td colspan="6">
											<qp:message code="inf.sys.0013"></qp:message>
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<div id="tab-output" class="tab-pane">
						<c:set var="index" value="0" />
						<table class="table table-bordered qp-table-list-none-action" id="outputForm">
							<colgroup>
								<col />
								<col />
								<col width="15%"/>
								<col width="30%"/>
								<col width="60px"/> 
								<col width="60px"/> 
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0023"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0029"></qp:message></th>
									<th style="text-align: center"><qp:message code="sc.autocomplete.0019"></qp:message></th>
									<th style="text-align: center"><qp:message code="sc.autocomplete.0020"></qp:message></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty designForm.outputForm.output1Column or designForm.outputForm.output1Display or designForm.outputForm.submitColumn eq '1' }">
									<tr>
										<td>
											<c:set var="index" value="${index + 1}" scope="page"/>
											${index }
										</td>
										<td><qp:message code="sc.sqldesign.0004" /> 1</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output1Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output1Display}"></qp:booleanFormatYesNo>
										</td>
										<td>
											<qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="1"></qp:integerFormatYesNo>
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output2Column or designForm.outputForm.output2Display or designForm.outputForm.submitColumn eq '2' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 2</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output2Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output2Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="2"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output3Column or designForm.outputForm.output3Display or designForm.outputForm.submitColumn eq '3' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 3</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output3Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output3Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="3"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output4Column or designForm.outputForm.output4Display or designForm.outputForm.submitColumn eq '4' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 4</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output4Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output4Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="4"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output5Column or designForm.outputForm.output5Display or designForm.outputForm.submitColumn eq '5' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 5</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output5Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output5Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="5"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output6Column or designForm.outputForm.output6Display or designForm.outputForm.submitColumn eq '6' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 6</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output6Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output6Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="6"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output7Column or designForm.outputForm.output7Display or designForm.outputForm.submitColumn eq '7' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 7</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output7Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output7Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="7"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output8Column or designForm.outputForm.output8Display or designForm.outputForm.submitColumn eq '8' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 8</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output8Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output8Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="8"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output9Column or designForm.outputForm.output9Display or designForm.outputForm.submitColumn eq '9' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 9</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output9Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output9Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="9"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output10Column or designForm.outputForm.output10Display or designForm.outputForm.submitColumn eq '10' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 10</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output10Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output10Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="10"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output11Column or designForm.outputForm.output11Display or designForm.outputForm.submitColumn eq '11' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 11</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output11Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output11Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="11"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output12Column or designForm.outputForm.output12Display or designForm.outputForm.submitColumn eq '12' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 12</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output12Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output12Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="12"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output13Column or designForm.outputForm.output13Display or designForm.outputForm.submitColumn eq '13' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 13</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output13Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output13Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="13"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output14Column or designForm.outputForm.output14Display or designForm.outputForm.submitColumn eq '14' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 14</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output14Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output14Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="14"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output15Column or designForm.outputForm.output15Display or designForm.outputForm.submitColumn eq '15' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 15</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output15Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output15Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="15"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output16Column or designForm.outputForm.output16Display or designForm.outputForm.submitColumn eq '16' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 16</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output16Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output16Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="16"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output17Column or designForm.outputForm.output17Display or designForm.outputForm.submitColumn eq '17' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 17</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output17Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output17Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="17"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output18Column or designForm.outputForm.output18Display or designForm.outputForm.submitColumn eq '18' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 18</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output18Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output18Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="18"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output19Column or designForm.outputForm.output19Display or designForm.outputForm.submitColumn eq '19' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 19</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output19Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output19Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="19"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
								<c:if test="${not empty designForm.outputForm.output20Column or designForm.outputForm.output20Display or designForm.outputForm.submitColumn eq '20' }">
									<tr>
										<td><c:set var="index" value="${index + 1}" scope="page"/>${index }</td>
										<td><qp:message code="sc.sqldesign.0004" /> 20</td>
										<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
										<td>
											${f:h(designForm.outputForm.output20Column)}
										</td>
										<td>
											<qp:booleanFormatYesNo value="${designForm.outputForm.output20Display }"></qp:booleanFormatYesNo>
										</td>
										<td><qp:integerFormatYesNo value="${designForm.outputForm.submitColumn }" yesValue="20"></qp:integerFormatYesNo></td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
						<form:textarea path="sqlDesignForm.sqlText" style="width: 100%; text-align: left; height: 400px" rows="6" readonly="true"/>
					</div>
				</div>
				<div class="form-inline form-group">
				<c:if test="${designForm.openOwner eq '1' }">
					<div class="qp-div-action">
						<form:hidden path="autocompleteForm.autocompleteId" />
						<form:hidden path="autocompleteForm.updatedDate" />
						<input type="hidden" name="designStatus" value="${designForm.autocompleteForm.designStatus}"/>
						<form:hidden path="actionDelete" value="false"/>
						<qp:authorization permission="autocompleteModify">
							<c:choose>
								<c:when test="${designForm.autocompleteForm.designStatus eq '1'}">
									<qp:authorization permission="changeDesignStatus">
										<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"></qp:message></button>
									</qp:authorization>
								</c:when>
								<c:when test="${designForm.autocompleteForm.designStatus eq '2'}">
									<qp:authorization permission="changeDesignStatus">
										<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"></qp:message></button>
									</qp:authorization>
								</c:when>
							</c:choose>
						</qp:authorization>
						<c:if test="${designForm.autocompleteForm.designStatus eq '1'}">
							<qp:authorization permission="autocompleteDelete">
								<div class="checkbox">
						             <label> 
						              <form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" /></label>
					            </div>
								<button type="button" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="$.qp.common.setFlag()" name="openOwner" value="1">
									<qp:message code="sc.sys.0008" />
								</button>
							</qp:authorization>
							<qp:authorization permission="autocompleteModify">
								<a type="submit"
									class="btn btn-success qp-link-button qp-link-popup-navigate"
									href="${pageContext.request.contextPath}/autocomplete/modify?autocompleteForm.autocompleteId=${f:u(designForm.autocompleteForm.autocompleteId)}&mode=1">
									<qp:message code="sc.sys.0006"></qp:message>
								</a>
							</qp:authorization>
						</c:if>
					</div>
				</c:if>
				</div>
			</c:if>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>