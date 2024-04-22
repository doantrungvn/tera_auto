<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.message"></qp:message></span></li>
		<li><span><qp:message code="sc.message.0001"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">	
	<qp:authorization permission="languageRegister">
		<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
		<a href="${pageContext.request.contextPath}/language/register"><qp:message code="sc.message.0008" /></a>
	</qp:authorization>
	<qp:authorization permission="messageReload"> 
		<span class="qp-link-header-icon glyphicon glyphicon-refresh"></span>
		<a class="com-link-popup" href="${pageContext.request.contextPath}/message/reload"><qp:message code="sc.message.0009" /></a>
	</qp:authorization>	
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/message/search" modelAttribute="messageForm">
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span
						class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
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
							<th><qp:message code="sc.message.0002" /></th>
							<td><form:input path="messageString" cssClass="form-control qp-input-text" /></td>
							<th><qp:message code="sc.message.0003" /></th>
							<td><form:input path="messageCode" class="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.message.0004" /></th>
							<td>
								<form:select path="moduleResource" cssClass="form-control qp-input-select">
									<form:option value=""><qp:message code="sc.sys.0030" /></form:option>
									<c:forEach var="resource" items="${moduleResources}">
										<form:option value="${resource.messageCode}"><qp:message code="${resource.messageCode}" /></form:option>
									</c:forEach>
								</form:select>
							</td>
							<th><qp:message code="sc.message.0005" /></th>
							<td>
								<form:select path="languageCode" cssClass="form-control qp-input-select">
									<form:option value=""><qp:message code="sc.sys.0030" /></form:option>
									<form:options items="${CL_LANGUAGE_LIST}"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.message.0006" /></th>
							<td colspan="3">
								<c:forEach var="item" items="${CL_MESSAGE_TYPE}">
									<label><form:checkbox path="messageTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_MESSAGE_TYPE.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<div class="fr">
					<qp:authorization permission="messageSearch"><button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button></qp:authorization>
				</div>
			</div>
		</form:form>

		<c:if test="${page != null}">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<qp:itemPerPage form="messageForm" action="/message/search" />
				</div>
				<form:form method="post" action="${pageContext.request.contextPath}/message/modify" modelAttribute="messageForm">
					<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="margin: 5px 5px 0" />
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col width="5%" />
									<col width="45%" />
									<col width="20%" />
									<col width="20%" />
									<col width="10%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:columnSort colName="message_string" colCode="sc.message.0002" form="messageForm" /></th>
										<th><qp:columnSort colName="message_code" colCode="sc.message.0003" form="messageForm" /></th>
										<th><qp:columnSort colName="module_resource" colCode="sc.message.0004" form="messageForm" /></th>
										<th><qp:columnSort colName="language_name" colCode="sc.message.0005" form="messageForm" /></th>
									</tr>
								</thead>
								<c:forEach var="item" items="${page.content}" varStatus="status">
									<tr>
										<td><qp:formatInteger value="${(page.number * page.size) + status.count}" />
                                            <form:hidden path="listMessage[${status.index}].messageId" value="${item.messageId}" />
										</td>
										<td>
											<form:input path="listMessage[${status.index}].messageString" value="${item.messageString}" cssClass="form-control qp-input-text" size="400"/>
										</td>
										<td><qp:formatText value="${item.messageCode}" /></td>
										<td><qp:message code="${item.moduleResource}" /></td>
										<td>
										<qp:authorization permission="languageView">
											<a class="qp-link-popup" href="${pageContext.request.contextPath}/language/view?languageCode=${item.language.languageCode}&countryCode=${item.language.countryCode}"> <qp:formatText value="${item.language.languageName}" /></a>  
											</qp:authorization>
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="qp-div-action">
								<div class="fr">
									<qp:authorization permission="messageModify"><button class="btn qp-button qp-dialog-confirm" type="button"><qp:message code="sc.sys.0031" /></button></qp:authorization>
								</div>
							</div>
							<qp:authorization permission="messageSearch">
								<div class="qp-div-action">
									<c:url var="customQueryTmpl" value="?">
										<%-- <c:param name="messageString" value="${messageForm.messageString}" />
										<c:param name="messageCode" value="${messageForm.messageCode}" />
										<c:param name="moduleResource" value="${messageForm.moduleResource}" />
										<c:param name="languageCode" value="${messageForm.languageCode}" />
										<c:forEach items="${messageForm.messageTypes}" var="messageType">
											<c:param name="messageTypes" value="${messageType}" />
										</c:forEach> --%>
									</c:url>
									<t:pagination page="${page}" outerElementClass="pagination pagination-md" queryTmpl="page={page}&size={size}" maxDisplayCount="5" />
								</div>
							</qp:authorization>
							<input type="hidden" name="pageNumber" value='${page.number}' />
							<input type="hidden" name="pageSize" value='${page.size}' />
						</div>
					</div>
				</form:form>
			</div>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>