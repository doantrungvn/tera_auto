<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name"> 
			<qp:message code="sc.tqp.0006"></qp:message>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-name-homepage"> 
			<p style="float:right;"><qp:message code="sc.homepage.0002" />&nbsp;<qp:formatDateTime value="${serverTime}" /></p>
			<p style="float:right; padding-right: 15px; font-weight: bold;"><qp:message code="sc.homepage.0003" /> : ${account.username}</p>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=homepage"></script>
		<script type="text/javascript" >
			function validation() {
				if (!$("input[name='projectId']:checked").val()) {
					alert(dbMsgSource['err.homepage.0001']);
					return false;
				}
			}

			$(document).ready(
				function() {
					if (CURRENT_PROJECT_ID != undefined && CURRENT_PROJECT_ID != '') {
						$radios = $("#tblProjects tbody").find("input[type=radio]");
						$radios.filter('[value='+CURRENT_PROJECT_ID+']').prop('checked', true);
					}
				}
			);
			
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div id="wrapper" style="text-align: center; height: 50px;">
			<h1>${msgWelcome}</h1>
		</div>
		<div id="wrapper" style="text-align: right;">
		<qp:authorization permission="projectRegister">
			<a href="${pageContext.request.contextPath}/project/register"><qp:message code="sc.project.0013"/></a>
		</qp:authorization>
		</div>
		<c:if test="${not empty listProjectOfUser}">
			<form:form method="post" action="${pageContext.request.contextPath}/home" modelAttribute="homepageForm">
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
						<qp:message code="sc.homepage.0004" />&nbsp;<span class="badge">&nbsp;${listProjectOfUser.size()}&nbsp;</span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
								<table class="table table-bordered table-hover qp-table-list" id="tblProjects">
									<colgroup>
										<col />
										<col />
										<col width="30%" />
										<col width="15%" />
										<col width="8%"/>
										<col width="8%"/>
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004" /></th>
											<th><qp:message code="sc.project.0005" /></th>
											<th><qp:message code="sc.project.0006" /></th>
											<th><qp:message code="sc.sys.0055" /></th>
											<th></th>
											<th><qp:message code="sc.homepage.0006" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="project" items="${listProjectOfUser}" varStatus="rowStatus">
											<tr>
												<td><qp:formatInteger value="${rowStatus.count}" /></td>
												<td>
													<qp:authorization permission="projectView" isDisplay="true" displayValue="${project.projectName}">
														<a class="qp-link-popup" href="${pageContext.request.contextPath}/project/view?projectId=${f:h(project.projectId)}&openOwner=1"><qp:formatText value="${project.projectName}" /></a>
													</qp:authorization>
												</td>
												<td><qp:formatText value="${project.projectCode}"/></td>
												<td><qp:message code="${CL_DESIGN_STATUS.get(project.status.toString())}"/></td>
												<td align="center">
													<c:if test = "${project.status eq 1 }">
														<qp:authorization permission="projectSetting">
															<a href="${pageContext.request.contextPath}/styledesign/view?projectId=${f:h(project.projectId)}&openOwner=1" class="btn qp-button glyphicon glyphicon-cog qp-link-button qp-button-popup" data-toggle="tooltip" title="<qp:message code="sc.tqp.0015"/>"></a>
														</qp:authorization>
														<qp:authorization permission="projectModify">
															<a href="${pageContext.request.contextPath}/project/modify?projectId=${f:h(project.projectId)}&mode=0" class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" data-toggle="tooltip" title="<qp:message code="sc.project.0014"/>"></a>
														</qp:authorization>
													</c:if>
												</td>
												<td>
													<input type="radio" id="projectId" name="projectId" value="${project.projectId}" />
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
						</div>
					</div>
				</div>
				<div class="qp-div-action">
					<button type="button" class="btn qp-button qp-dialog-confirm" onclick="validation" messageId="inf.sys.0044" ><qp:message code="sc.homepage.0005" /></button>
				</div>
			</form:form>
		</c:if>
	</tiles:putAttribute>

</tiles:insertDefinition>