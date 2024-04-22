<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.sqldesign.0030" />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
		</script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/sqldesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=sqldesign"></script>
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
			var DB_TYPE_PROJECT =  ${sessionScope.CURRENT_PROJECT.dbType};
		</script>
		
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="designForm"
			action="${pageContext.request.contextPath}/sqldesign/deleteConfirm">
			<c:set var="designForm" value="${sqlDesignAdvancedDesignForm }" scope="request"></c:set>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty designForm.sqlDesignForm.sqlDesignId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.sqldesign.0021" /></span>
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
								<th><label><qp:message code="sc.sqldesign.0010"></qp:message></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignName }"></qp:formatText></td>
								<th><label><qp:message code="sc.sqldesign.0011"></qp:message></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.sqlDesignCode }"></qp:formatText></td>
							</tr>
							<tr>
								<th><label for="sqlDesignForm.designTypes"><qp:message code="sc.sqldesign.0031"></qp:message></label></th>
								<td><qp:formatText value="${CL_SQL_TYPE_SQLDESIGN.get(designForm.sqlDesignForm.designType.toString())}"></qp:formatText>
								</td>
								<th><label for="sqlDesignForm.moduleIdAutocomplete"><qp:message code="sc.autocomplete.0007"></qp:message></label></th>
								<td><qp:formatText value="${designForm.sqlDesignForm.moduleIdAutocomplete }"></qp:formatText>
								</td>
							</tr>
							<tr>
								<th><label><qp:message code="sc.sys.0055"></qp:message></label></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus.toString())}" /></td>
								<th><label for="sqlDesignForm.sqlPattern"><qp:message code="sc.sqldesign.0020"></qp:message></label></th>
								<td><qp:formatText value="${CL_SQL_SQLPATTERN.get(designForm.sqlDesignForm.sqlPattern)}" /></td>
							</tr>
							<tr>
								<th><label for="remark"><qp:message code="sc.sys.0028"></qp:message></label></th>
								<td><qp:formatRemark value="${designForm.sqlDesignForm.remark}" /></td>
								<th></th>
								<td></td>
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
						<table class="table table-bordered qp-table-list" id="inputForm">
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
												<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
												<div class="pull-right" style="height:100%;vertical-align: middle;text-align: left;">	
													<span>${f:h(item.sqlDesignInputName)}</span>
												</div>
											</div>
										</td>
										<td>
											${f:h(item.sqlDesignInputCode)}
										</td>
										<td>
											${f:h(CL_SQL_DATATYPE.get(item.dataType))}
											<c:if test="${designForm.inputForm[status.index].isArray }">
												[]
											</c:if>
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
						<table style="width:30%" class="table table-borderless">
							<colgroup>
								<col width="30%" />
								<col  />
							</colgroup>
							<tbody>
								<tr>
									<td><qp:message code='sc.sqldesign.0022'></qp:message></td>
									<td>
										<span class="form-control qp-form-control-label">${f:h(CL_SQL_RETURNTYPE.get(designForm.sqlDesignForm.returnType.toString())) }</span>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="table table-bordered qp-table-list-none-action" id="outputForm">
							<colgroup>
								<col />
								<col width="40px"/>
								<col />
								<col width="20%"/>
								<col width="20%"/>
								<col width="20%"/>
								<col width="20%"/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
									<th><qp:message code="sc.sqldesign.0029"></qp:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${designForm.outputForm }" var="item" varStatus="status">
									<tr data-ar-rgroup="${item.groupId }" data-ar-rgroupindex="${f:h(item.itemSeqNo) }"  class="ar-dataRow">
										<td colspan="4" class="qp-none-padding">
											<div style="height:100%">
												<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
												<div class="pull-right" style="height:100%;vertical-align: middle;text-align: left;">	
													<span>${f:h(item.sqlDesignOutputName)}</span>
												</div>
											</div>
										</td>
										<td>
											${f:h(item.sqlDesignOutputCode)}
										</td>
										<td>
											${f:h(CL_SQL_DATATYPE.get(item.dataType))}</span>
											<c:if test="${designForm.outputForm[status.index].isArray }">
												[]
											</c:if>
										</td>
										<td>
											${f:h(designForm.outputForm[status.index].mappingColumn) }
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty designForm.outputForm}">
									<tr>
										<td colspan="7">
											<qp:message code="inf.sys.0013"></qp:message>
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
						<span id="lblPageableCodeOracle" hidden="true"></span>
						<form:textarea path="sqlDesignForm.sqlText" style="width: 100%; text-align: left; height: 400px" rows="6" readonly="true"/>
						<span id="lblPageableCodeOracle2" hidden="true"></span>
						<div id="generatePageable" hidden="true">
							<textarea rows="12" cols="20" id="txtPageableCode" style="width: 100%" readonly="readonly"></textarea>
							<form:hidden path="sqlDesignForm.pageable"/>
						</div>
					</div>
				</div>
				 <div class="form-inline form-group">
				<c:if test="${designForm.openOwner eq '1' }">
					<div class="qp-div-action">
						<form:hidden path="sqlDesignForm.sqlDesignId" />
						<form:hidden path="sqlDesignForm.updatedDate" />
						<input type="hidden" name="designStatus" value="${designForm.sqlDesignForm.designStatus}"/>
						<form:hidden path="actionDelete" value="false"/>
						<qp:authorization permission="sqldesignModify">
							<c:choose>
								<c:when test="${designForm.sqlDesignForm.designStatus eq '1'}">
									<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"></qp:message></button>
								</c:when>
								<c:when test="${designForm.sqlDesignForm.designStatus eq '2'}">
									<qp:authorization permission="changeDesignStatus">
										<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"></qp:message></button>
									</qp:authorization>
								</c:when>
							</c:choose>
						</qp:authorization>
						<c:if test="${designForm.sqlDesignForm.designStatus eq '1'}">
							<qp:authorization permission="sqldesignDelete">
								<div class="checkbox">
									<label> <form:checkbox path="showImpactFlag" />
										<qp:message code="sc.sys.0097" /></label>
								</div>
								<button type="button" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="$.qp.common.setFlag()" name="openOwner" value="1">
									<qp:message code="sc.sys.0008" />
								</button>
							</qp:authorization>
							<qp:authorization permission="sqldesignModify">
								<a type="submit"
									class="btn btn-success qp-link-button qp-link-popup-navigate"
									href="${pageContext.request.contextPath}/sqldesign/modify?sqlDesignForm.sqlDesignId=${f:u(designForm.sqlDesignForm.sqlDesignId)}&mode=1">
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