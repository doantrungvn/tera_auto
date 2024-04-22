<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.messagedesign.0024"/>
	</tiles:putAttribute>
	
	<c:if test="${notExistFlg ne '1'}">
		<tiles:putAttribute name="body">
			<form:form method="post" modelAttribute="messageDesignForm" action="${pageContext.request.contextPath}/messagedesign/delete">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.messagedesign.0007" /></span>
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
								<th><qp:message code="sc.message.0003" /></th>
								<td><qp:formatText value="${messageDesign.messageCode}" /></td>
								<th><qp:message code="sc.message.0004" /></th>
								<td class="word-wrap"><qp:formatText value="${messageDesign.moduleName}" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.messagedesign.0013" /></th>
								<td><qp:message code="${CL_MESSAGE_LEVEL.get(messageDesign.messageLevel.toString())}" /></td>
								<th><qp:message code="sc.message.0006" /></th>
								<td><qp:message code="${CL_MESSAGE_TYPE.get(messageDesign.messageType)}" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0028" /></th>
								<td><qp:formatRemark value="${messageDesign.remark}" /></td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.message.0007" /></span>
					</div>
					<div class="panel-body">
						<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
						<table class="table table-bordered qp-table-list-none-action">
							<colgroup>
								<col/>
								<col width="75%" />
								<col width="25%" />
							</colgroup>
							<thead>
								<tr>
									<th style="text-align: center"><qp:message code="sc.sys.0004" /></th>
									<th style="text-align: center"><qp:message code="sc.messagedesign.0009" /></th>
									<th style="text-align: center"><qp:message code="sc.languagedesign.0002" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${messageDesign.messageDesigns}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex"><qp:formatInteger value="${status.count}" /></td>
										<td ><qp:formatRemark value="${item.messageString}" /></td>
										<td ><qp:formatText value="${item.languageName}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="qp-div-action">
					<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && messageDesign.moduleStatus eq 1}">
						<c:if test="${empty messageDesign.screenDesigns && empty messageDesign.listOfBusinessDesign && empty messageDesign.menuDesign}">
							<c:if test="${messageDesign.classFlg eq 1}">
								<qp:authorization permission="messagedesignDelete">
									<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014"><qp:message code="sc.sys.0008" /></button>
								</qp:authorization>
							</c:if>
						</c:if>
						<qp:authorization permission="messagedesignModify">
							<a type="button" class="btn qp-button qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/messagedesign/modifyByCode?messageCode=${messageDesign.messageCode}&mode=1"><qp:message code="sc.sys.0006" /></a>
						</qp:authorization>
					
						<form:hidden path="messageDesignId" value="${messageDesign.messageDesignId}" />
						<form:hidden path="messageCode" value="${messageDesign.messageCode}" />
						<form:hidden path="messageString" value="${messageDesign.messageString}" />
						<form:hidden path="updatedDate" value="${messageDesign.updatedDate}" />
					</c:if>
				</div>

				<!-- //List of screen design -->
				<c:if test="${not empty messageDesign.screenDesigns}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.messagedesign.0016" /></span>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="30%" />
										<col width="10%" />
										<col width="15%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:message code="sc.screendesign.0005" /></th>
											<th><qp:message code="sc.screendesign.0007" /></th>
											<th><qp:message code="sc.screendesign.0183" /></th>
											<th><qp:message code="sc.screendesign.0009" /></th>
										</tr>
									</thead>
									<c:forEach var="map" items="${messageDesign.screenDesigns}" varStatus="rowStatus">
										<tr>
											<td class="qp-output-fixlength tableIndex"><qp:formatInteger value="${rowStatus.count}" /></td>
											<td>
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/screendesign/view?screenId=${f:h(map['screen_id'])}&openOwner=0" ><qp:formatText value="${map['screen_name']}" /></a>
											</td>
											<td class="qp-output-text"><qp:formatText value="${map['screen_code']}"/></td>
											<td class="qp-output-text"><qp:formatText value="${CL_TEMPLATE_TYPE.get(map['template_type'].toString())}"/></td>
											<td class="qp-output-text"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(map['screen_pattern_type'].toString())}"/></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>
				<!-- //List of blogic -->
				<c:if test="${not empty messageDesign.listOfBusinessDesign}">
					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.module.0029" /></span>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered qp-table-list-none-action">
									<colgroup>
										<col />
										<col />
										<col width="30%" />
										<col width="10%" />
										<col width="15%" />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:message code="sc.businesslogicdesign.0005"/></th>
											<th><qp:message code="sc.businesslogicdesign.0006" /></th>
											<th><qp:message code="sc.sqldesign.0022"/></th>
											<th><qp:message code="sc.module.0034" /></th>
										</tr>
									</thead>
									<c:forEach var="blogic" items="${messageDesign.listOfBusinessDesign}" varStatus="rowStatus"> 
										<tr>
											<td><qp:formatInteger value="${rowStatus.count}" /></td>
											<td>
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/view?businessLogicId=${f:h(blogic.businessLogicId)}"><qp:formatText value="${blogic.businessLogicName}" /></a>
											</td>
											<td><qp:formatText value="${blogic.businessLogicCode}"/></td>
											<td><qp:message code="${CL_RETURN_TYPE.get(blogic.returnType.toString())}"/></td>
											<td><qp:message code="${CL_DEFAULT_GENERATION_SETTING.get(blogic.moduleType.toString())}"/></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>
				<!-- //List of menu design -->
				<c:if test="${not empty messageDesign.menuDesign}">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.menudesign.0012" /></span>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered qp-table-form">
									<colgroup>
										<col />
										<col width="30%"/>
										<col width="20%"/>
										<col width="30%"/>
									</colgroup>
									<tr>
										<th><qp:message code="sc.menudesign.0011" /></th>
										<td><qp:formatText value="${messageDesign.menuDesign.headerMenuName}"/></td>
										<th><qp:message code="sc.menudesign.0004" /></th>
										<td><qp:message code="${CL_MENU_TYPE.get(messageDesign.menuDesign.menuType.toString())}"/></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</c:if>
				
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>