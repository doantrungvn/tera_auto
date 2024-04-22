<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.domaindesign.0032" />
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	</tiles:putAttribute>

<c:if test="${ not empty domain}">
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/domaindesign/delete" modelAttribute="domainDesignForm">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.domaindesign.0015" /></span>
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
							<th><qp:message code="sc.domaindesign.0001" /></th>
							<td><qp:formatText value="${domain.domainName }" /></td>
							<th><qp:message code="sc.domaindesign.0002" /></th>
							<td><qp:formatText value="${domain.domainCode }" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.domaindesign.0003" /></th>
							<td><qp:formatText value="${domain.baseTypeAutocomplete}" /></td>
							<th><qp:message code="sc.domaindesign.0009" /></th>
							<td><qp:formatText value="${domain.fmtCode}"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.domaindesign.0006" /></th>
							<td><qp:formatInteger value="${domain.maxLength }" /></td>
							<th><qp:message code="sc.domaindesign.0007" /></th>
							<td><qp:formatInteger value="${domain.precision }" /></td>
						</tr>
						<%-- <tr>
							<th><qp:message code="sc.domaindesign.0005" /></th>
							<td>
								<c:if test="${domain.mandatoryFlg == 1}"><qp:message code="sc.sys.0011" /></c:if>
								<c:if test="${domain.mandatoryFlg == 0}"><qp:message code="sc.sys.0012" /></c:if>
							</td>
							<th><qp:message code="sc.domaindesign.0009" /></th>
							<td><qp:formatText value="${domain.fmtCode}"/></td>
						</tr> --%>
						<tr>
							<th><qp:message code="sc.domaindesign.0004" /></th>
							<td>
								<c:if test="${domain.baseType eq 8 }"><!-- Boolean base type -->
									<c:set var="defaultValueMessageCode" value="${CL_BOOLEAN_DEFAULT_VALUE.get(domain.defaultValue.toString())}" />
									<c:if test="${empty defaultValueMessageCode}">
										<c:set var="defaultValueMessageCode" value="sc.sys.0095" /> <!-- None -->
									</c:if>
									<qp:message code="${defaultValueMessageCode}"  />
								</c:if>
								<c:if test="${domain.baseType ne 8 }">
									<qp:formatText value="${domain.defaultValue}" />
								</c:if>
							</td>
							<th><qp:message code="sc.domaindesign.0008" /></th>
							<td>
								<qp:formatText value="${domain.minVal }" />
								<c:if test="${ not empty domain.maxVal }">~</c:if>
								<qp:formatText value="${domain.maxVal }" />
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0028" /></th>
							<td><qp:formatRemark value="${domain.remark }" /></td>
							<th><qp:message code="sc.domaindesign.0042" /></th>
							<td><qp:formatText value="${domain.majorClassification }" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.domaindesign.0043" /></th>
							<td><qp:formatText value="${domain.subClassification }" /></td>
							<th><qp:message code="sc.domaindesign.0044" /></th>
							<td><qp:formatText value="${domain.minorClassification }" /></td>
						</tr>
					</table>
			
				</div>
			</div>

			<div class="qp-div-action">
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
					<qp:authorization permission="domaindesignDelete">
						<c:if test="${empty listOfTableDesign}">
							<form:hidden path="domainId" />
							<input type="hidden" name="updatedDate"
								value="${domain.updatedDate}" />
							<button type="submit"
								class="btn btn-md btn-warning qp-dialog-confirm">
								<qp:message code="sc.sys.0008" />
							</button>
						</c:if>
					</qp:authorization>
					<qp:authorization permission="domaindesignModify">
						<a type="submit"
							class="btn btn-md btn-success qp-link-button qp-link-popup-navigate"
							href="${pageContext.request.contextPath}/domaindesign/modify?domainId=${domain.domainId}&mode=1">
							<qp:message code="sc.sys.0006" />
						</a>
					</qp:authorization>
				</c:if>
			</div>

			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.domaindesign.0016" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action">
						<colgroup>
							<col width="8%"/>
							<col width="23%"/>
							<col width="23%"/>
							<col width="23%"/>
							<col width="23%"/>
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.domaindesign.0017" /></th>
								<th><qp:message code="sc.domaindesign.0049" /></th>
								<th><qp:message code="sc.domaindesign.0018" /></th>
								<th><qp:message code="sc.domaindesign.0050" /></th>
							</tr>
						</thead>
						<c:forEach items="${listOfTableDesign }" var="item" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td><qp:formatText value="${item.output01 }" /></td>
								<td><qp:formatText value="${item.output05 }" /></td>
								<td><qp:formatText value="${item.output02 }" /></td>
								<td><qp:formatText value="${item.output06 }" /></td>
							</tr>
						</c:forEach>
						<c:if test="${empty listOfTableDesign}">
							<tr>
								<td colspan="5"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>

		</form:form>
	</tiles:putAttribute>
</c:if>
</tiles:insertDefinition>
