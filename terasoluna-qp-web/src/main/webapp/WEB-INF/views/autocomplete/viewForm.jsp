<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.autocomplete.0066" />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
		</script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/autocompleteDesign/css/autocompleteDesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=autocomplete_sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init(true);
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="designForm"
			action="${pageContext.request.contextPath}/autocomplete/deleteConfirm">
			<c:set var="designForm" value="${autocompleteDesignForm }" scope="request"></c:set>
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
                <div class="form-inline form-group">
                <c:if test="${designForm.openOwner eq '1' }">
                    <div class="qp-div-action">
                        <form:hidden path="autocompleteForm.autocompleteId" />
                        <form:hidden path="autocompleteForm.updatedDate" />
                        <input type="hidden" name="designStatus" value="${designForm.autocompleteForm.designStatus}"/>
                        <form:hidden path="actionDelete" value="false"/>
                        <qp:authorization permission="changeDesignStatus">
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
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0058"></qp:message></th>
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
										<c:choose>
											<c:when test="${status.index eq 0}">
												<td>
													<qp:message code="sc.sqldesign.0056"></qp:message>
												</td>
											</c:when>
											<c:otherwise>
												<td>
													<qp:message code="sc.sqldesign.0057"></qp:message>
												</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
								<c:if test="${empty designForm.inputForm}">
									<tr>
										<td colspan="7">
											<qp:message code="inf.sys.0013"></qp:message>
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<div id="tab-output" class="tab-pane">
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
											${f:h(designForm.outputForm.output1ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output2ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output3ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output4ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output5ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output6ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output7ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output8ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output9ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output10ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output11ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output12ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output13ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output14ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output15ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output16ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output17ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output18ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output19ColumnAutocomplete)}
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
											${f:h(designForm.outputForm.output20ColumnAutocomplete)}
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
						<div class="panel panel-default qp-div-search-result">
							<div class="panel-heading">
								<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="qp-heading-text">
									<qp:message code="sc.autocomplete.0041"></qp:message>
								</span>
							</div>
							<div class="panel-body">
								<table class="table table-borderless qp-table-form" id="fromForm">
									<colgroup>
										<col width="182px"/>
										<col />
										<col width="35px" />
									</colgroup>
									<tr>
										<td>
											<span style="width: 78%; margin-left: 8px;" class="pull-left form-control qp-form-control-label${designForm.fromForm[0].tableMissingFlag eq '1'?' qp-missing':'' }">
												${f:h(designForm.fromForm[0].tableIdAutocomplete)}
											</span>
										</td>
									</tr>
									<c:if test="${not empty designForm.fromForm[0].joinTableIdAutocomplete }">
										<c:forEach items="${designForm.fromForm}" varStatus="status">
											<tr data-ar-rindex="${status.index }">
												<td colspan="2">
													<div class="qp-div-highlight-border">
														<table class="table table-borderless join-conditions-table" >
															<colgroup>
																<col width="18%" />
																<col width="30px" />
																<col />
															</colgroup>
															<tr>
																<td style="vertical-align: top;">
																	<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }" style="width:88%;margin-top:15px">
																		${f:h(designForm.fromForm[status.index].joinTableIdAutocomplete)}
																	</span>
																</td>
																<td style="vertical-align:top;padding-top:20px" rowspan="2" class="text-center">
																	<b class="joinColumnsFormToggle qp-link-toggle glyphicon glyphicon-eye-open"></b>
																</td>
																<td rowspan="2" colspan="3">
																	<div style="display:none">
																		<div>
																			<label>
																				<c:if test="${designForm.fromForm[status.index ].joinType eq '1' }">
																					<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_INNER_JOIN_48.png" >
																				</c:if>
																				<c:if test="${designForm.fromForm[status.index ].joinType eq '2' }">
																					<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_LEFT_JOIN_48.png">
																				</c:if>
																				<c:if test="${designForm.fromForm[status.index ].joinType eq '3' }">
																					<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_RIGHT_JOIN_48.png">
																				</c:if>
																				<c:if test="${designForm.fromForm[status.index ].joinType eq '4' }">
																					<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_FULL_JOIN_48.png">
																				</c:if>
																				<c:if test="${designForm.fromForm[status.index ].joinType eq '5' }">
																					<img src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_CROSS_JOIN_48.png">
																				</c:if>
																			</label>
																		</div>
																		<table class="table table-borderless" id="joinPairTable">
																			<colgroup>
																				<col width="20%" />
																				<col width="20%" />
																				<col width="20%" />
																				<col width="20%" />
																				<col width="20%" />
																			</colgroup>
																			<c:forEach items="${designForm.fromForm[status.index].joinColumnsForm}" varStatus="nestedStatus">
																				<tr data-ar-rindex="${nestedStatus.index }" style="border-bottom: solid 1.5px rgba(94, 92, 113, 0.15);">
																					<td>
																						<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableMissingFlag eq '1'?' qp-missing':'' }">
																							${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].tableIdAutocomplete)}
																						</span> 
																					</td>
																					<td>
																						<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnMissingFlag eq '1'?' qp-missing':'' }">
																							${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].columnIdAutocomplete) }
																						</span>
																					</td>
																					<td>
																						<span class="form-control qp-form-control-label text-center" >
																							<qp:message code="${CL_SQL_OPERATOR.get(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].operatorCode) }"></qp:message>
																						</span>
																					</td>
																					<td>
																						<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinTableMissingFlag eq '1'?' qp-missing':'' }">
																							${f:h(designForm.fromForm[status.index].joinTableIdAutocomplete)}
																						</span>
																					</td>
																					<td>
																						<span class="form-control qp-form-control-label${designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnMissingFlag eq '1'?' qp-missing':'' }">
																							${f:h(designForm.fromForm[status.index].joinColumnsForm[nestedStatus.index].joinColumnIdAutocomplete) }
																						</span>
																					</td>
																				</tr>
																			</c:forEach>
																		</table>
																	</div>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</table>
							</div>
						</div>
						<div class="panel panel-default qp-div-search-result">
							<div class="panel-heading">
								<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="qp-heading-text"><qp:message code="sc.autocomplete.0045" /></span>
							</div>
							<div class="panel-body">
								<label><qp:message code="sc.autocomplete.0051"></qp:message><qp:message code="sc.sys.0037"></qp:message>&nbsp;<qp:booleanFormatYesNo value="${designForm.sqlDesignForm.omitOverlap}"></qp:booleanFormatYesNo></label>
								<table class="table table-bordered qp-table" id="selectForm" style="margin-top:10px">
									<colgroup>
										<col />
										<col width="30%"/>
										<col width="30%"/>
									</colgroup>
									<thead>
										<tr>
											<th>
												<qp:message code="sc.autocomplete.0016" />
											</th>
											<th>
												<qp:message code="sc.autocomplete.0017" />
											</th>
											<th>
												<qp:message code="sc.autocomplete.0015" />
											</th>
										</tr>
									</thead>
									<c:forEach items="${designForm.selectForm}" varStatus="status">
										<c:if test="${designForm.selectForm[status.index].isSelected}">
											<tr>
												<td>
													<span class="${designForm.selectForm[status.index].tableMissingFlag eq '1'?'form-control qp-form-control-label qp-missing':'' }">
														${f:h(designForm.selectForm[status.index].tableIdAutocomplete) }
													</span>
												</td>
												<td>
													<span class="${designForm.selectForm[status.index].columnMissingFlag eq '1'?'form-control qp-form-control-label qp-missing':'' }">
														${f:h(designForm.selectForm[status.index].columnIdAutocomplete) }
													</span>
												</td>
												<td>
													${CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.selectForm[status.index].functionCode)}
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</div>
						<c:if test="${not empty designForm.whereForm}">
							<div class="panel panel-default qp-div-search-result">
								<div class="panel-heading">
									<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="qp-heading-text"><qp:message code="sc.autocomplete.0046"></qp:message></span>
								</div>
								<div class="panel-body">
									<table class="table table-borderless" id="whereForm" data-ar-callback="setArgumentForAllTableAC">
										<colgroup>
											<col>
											<col width="20px">
										</colgroup>
										<c:forEach items="${designForm.whereForm}" varStatus="status">
											<tr>
												<td>
													<div class="qp-div-highlight-border">
														<table class="table table-borderless qp-table-form">
															<colgroup>
																<col width="15%" />
																<col width="15px" />
																<col width="35%" />
																<col width="15%" />
																<col />
																<col width="15px" />
															</colgroup>
															<tr>
																	<td rowspan="3">
																		<c:if test="${not empty designForm.whereForm[status.index].logicCode.toString()}">
																			<span class="form-control qp-form-control-label text-center">
																				${f:h(CL_SQL_COMBINING_OPERATOR.get(designForm.whereForm[status.index].logicCode.toString())) }
																			</span>
																		</c:if>
																	</td>
																	<td rowspan="3">
																		<label class="qp-open-parenthesis"></label>
																	</td>
																	<td>
																		<span class="form-control qp-form-control-label${designForm.whereForm[status.index].leftTableMissingFlag eq '1'?' qp-missing':'' }">
																			${f:h(designForm.whereForm[status.index].leftTableIdAutocomplete)}
																		</span>
																	</td>
																	<td>
																		<span class="form-control qp-form-control-label text-center">
																			${f:h(CL_SQL_CONDITION_TYPE.get(designForm.whereForm[status.index].conditionType.toString())) }
																		</span>
																	</td>
																	<td>
																		<c:choose>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='1' }">
																				<span class="form-control qp-form-control-label${designForm.whereForm[status.index].rightTableMissingFlag eq '1'?' qp-missing':'' }">
																					${f:h(designForm.whereForm[status.index].rightTableIdAutocomplete)}
																				</span>
																			</c:when>
																		</c:choose>
																	</td>
																	<td rowspan="3">
																		<label class="qp-close-parenthesis"></label>
																	</td>
																</tr>
																<tr>
																	<td>
																		<span class="form-control qp-form-control-label${designForm.whereForm[status.index].leftColumnMissingFlag eq '1'?' qp-missing':'' }">
																			${f:h(designForm.whereForm[status.index].leftColumnIdAutocomplete)}
																		</span>
																	</td>
																	<td>
																		<span class="form-control qp-form-control-label  text-center">
																			<qp:message code="${CL_SQL_OPERATOR.get(designForm.whereForm[status.index].operatorCode.toString())}"></qp:message>
																		</span>
																	</td>
																	<td>
																		<c:choose>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='1' }">
																				<span class="form-control qp-form-control-label${designForm.whereForm[status.index].rightColumnMissingFlag eq '1'?' qp-missing':'' }">
																					${f:h(designForm.whereForm[status.index].rightColumnIdAutocomplete)}
																				</span>
																			</c:when>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='0' }">
																				<c:choose>
																					<c:when test="${designForm.whereForm[status.index].operatorCode=='7' }">
																						<span class="form-control qp-form-control-label pull-left text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].value) }
																						</span>
																						<div class="qp-separator-from-to">~</div>
																						<span class="form-control qp-form-control-label pull-right text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].value2) }
																						</span>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																								<span class="form-control qp-form-control-label  text-center">
																									${f:h(designForm.whereForm[status.index ].value) }
																								</span>
																							</c:when>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:when test="${designForm.whereForm[status.index].conditionType=='2' }">
																				<c:choose>
																					<c:when test="${designForm.whereForm[status.index].operatorCode=='7' }">
																						<span class="form-control qp-form-control-label pull-left text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].displayArg) }
																						</span>
																						<div class="qp-separator-from-to">~</div>
																						<span class="form-control qp-form-control-label pull-right text-center" style="width: 47%">
																							${f:h(designForm.whereForm[status.index ].displayArg2) }
																						</span>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${designForm.whereForm[status.index].operatorCode!='8' || designForm.whereForm[status.index].operatorCode!='9' }">
																								<span class="form-control qp-form-control-label">
																									${f:h(designForm.whereForm[status.index ].displayArg) }
																								</span>
																							</c:when>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																		</c:choose>
																	</td>
																</tr>
															<c:if test="${not empty designForm.whereForm[status.index ].functionCode }">
																<tr>
																	<td>
																		<span class="form-control qp-form-control-label">
																			${f:h(CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.whereForm[status.index ].functionCode)) }
																		</span>
																	</td>
																</tr>
															</c:if>
														</table>
													</div>
												</td>
												<td>
													<form:checkbox cssStyle="display:none" path="whereForm[${status.index }].groupType" />
												</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</c:if>
						<c:if test="${false}">
							<div class="panel panel-default qp-div-search-result">
								<div class="panel-heading">
									<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="qp-heading-text">
										<qp:message code="sc.autocomplete.0047"></qp:message>
									</span>
								</div>
								<div class="panel-body">
									<table class="table table-borderless qp-table-form"
										id="groupByForm" data-ar-callback="setArgumentForAllTableAC">
										<colgroup>
											<col width="30%" />
											<col width="30%" />
											<col />
										</colgroup>
										<c:forEach items="${designForm.groupByForm}" varStatus="status">
											<tr>
												<td>
													<qp:formatText value="${designForm.groupByForm[status.index].tableIdAutocomplete}"></qp:formatText>
												</td>
												<td>
													<qp:formatText value="${designForm.groupByForm[status.index].columnIdAutocomplete}"></qp:formatText>
												</td>
												<td></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</c:if>
						<c:if test="${false }">
							<div class="panel panel-default qp-div-search-result">
								<div class="panel-heading">
									<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="qp-heading-text">
										<qp:message code="sc.autocomplete.0048"></qp:message>
									</span>
								</div>
								<div class="panel-body">
									<c:if test="${empty designForm.havingForm}">
										<qp:message code="inf.sys.0013" />
									</c:if>
									<table class="table table-borderless" id="havingForm"
										data-ar-callback="setArgumentForAllTableAC">
										<colgroup>
											<col width="90%" />
											<col />
										</colgroup>
										<c:forEach items="${designForm.havingForm}"
											varStatus="status">
											<tr>
												<td colspan="4">
													<div class="qp-div-highlight-border">
														<table class="table table-borderless qp-table-form">
															<colgroup>
																<col width="15%" />
																<col width="40%" />
																<col width="12%" />
																<col width="" />
															</colgroup>
															<tr>
																<td rowspan="3">
																	<qp:formatText value="${CL_SQL_COMBINING_OPERATOR.get(designForm.havingForm[status.index].logicCode) }"></qp:formatText>
																</td>
																<td>
																	<qp:formatText value="${designForm.havingForm[status.index].tableIdAutocomplete}"></qp:formatText>
																</td>
															</tr>
															<tr>
																<td><qp:formatText value="${designForm.havingForm[status.index].columnIdAutocomplete}"></qp:formatText>
																</td>
																<td>
																	<qp:message code="${CL_SQL_OPERATOR.get(designForm.havingForm[status.index].operatorCode) }"></qp:message>
																</td>
																<td>
																	<c:choose>
																		<c:when
																			test="${designForm.havingForm[status.index].operatorCode=='7' }">
																			<span class="qp-input-text pull-left"
																				style="width: 47%"> <qp:formatText
																					value="${designForm.havingForm[status.index ].value }" />
																			</span>
																			<div class="qp-separator-from-to">~</div>
																			<span class="qp-input-text pull-right"
																				style="width: 47%"> <qp:formatText
																					value="${designForm.havingForm[status.index ].value2 }" />
																			</span>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${designForm.havingForm[status.index].operatorCode!='8' || designForm.havingForm[status.index].operatorCode!='9' }">
																					<qp:formatText value="${designForm.havingForm[status.index ].value }" />
																				</c:when>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
															<tr>
																<td>
																	<qp:formatText value="${CL_SQL_AGGREGATE_FUNCTIONS.get(designForm.havingForm[status.index].functionCode)}"></qp:formatText>
																</td>
															</tr>
														</table>
													</div>
												</td>
												<td></td>
											</tr>
										</c:forEach>
									</table>
									<div class="qp-add-left"></div>
								</div>
							</div>
						</c:if>
						<c:if test="${not empty designForm.orderByForm}">
							<div class="panel panel-default qp-div-search-result">
								<div class="panel-heading">
									<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
									<span class="qp-heading-text"><qp:message
											code="sc.autocomplete.0044"></qp:message></span>
								</div>
								<div class="panel-body">
									<table class="table table-bordered qp-table" id="orderByForm" data-ar-callback="setArgumentForAllTableAC">
										<colgroup>
											<col width="50%" />
											<col width="50%" />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.sqldesign.0048"></qp:message></th>
												<th><qp:message code="sc.sqldesign.0049"></qp:message></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${designForm.orderByForm}"
												varStatus="status">
												<tr>
													<td><qp:formatText value="${designForm.orderByForm[status.index].tableColumnAutocomplete}"></qp:formatText>
													</td>
													<td><qp:formatText value="${CL_SQL_ORDER_DIRECTION.get(designForm.orderByForm[status.index].orderType) }"></qp:formatText>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="qp-add-left"></div>
								</div>
							</div>
						</c:if>
					</div>
					
				</div>
			</c:if>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>