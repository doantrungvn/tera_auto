<tiles:insertDefinition name="layouts">

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.loggingmanagement"></qp:message></span></li>

	</tiles:putAttribute>

	<tiles:putAttribute name="header-name">
		<qp:message code="tqp.loggingmanagement	" />
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">	
			var CL_LOG_APPENDER = {};
			<c:forEach items="${CL_LOG_APPENDER}" var="item">
				CL_LOG_APPENDER['${item.key}'] = '${item.value}';
			</c:forEach>
			
			var CL_POSTGRESQL_CONFIG = {};
			<c:forEach items="${CL_POSTGRESQL_CONFIG}" var="item"> 
				CL_POSTGRESQL_CONFIG['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_ORACLE_CONFIG = {};
			<c:forEach items="${CL_ORACLE_CONFIG}" var="item"> 
				CL_ORACLE_CONFIG['${item.key}'] = '${item.value}';
			</c:forEach>
			
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=loggingmanagement"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/loggingmanagement/javascript/init.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/loggingmanagement/javascript/loggingmanagement.js"></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<script id="tmp_filelog" type="text/template">
			<div id="fileLogInfor" class="fileLogInfor" style="border-color:red; border-style:dashed;border-width:2px; margin-bottom: 20px;">
				<table class="table table-bordered qp-table-form tblFileLog" name="fileLogTable" id="tblFileLog">
					<colgroup>
						<col width="20%" />
						<col width="30%" />
						<col width="20%" />
						<col width="30%" />
					</colgroup>
					<tr>
						<th colspan="4" style="text-align: left; vertical-align: middle;"><qp:message code="sc.loggingmanagement.0014" /> 
							<span id="id-block-reload-data0" onclick="removeFileLog(this);" title="<qp:message code="sc.loggingmanagement.0034" />" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px"></span>
						</th>
					</tr>
					<tr>
						<th><label ><qp:message code="sc.loggingmanagement.0056" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td colspan="3">
							<c:forEach items="${CL_LOG_CONFIG_MODE}" var="item" varStatus="status">
								<label><input name="fileLog.lstLogDetail[].configMode" type="radio" value="${item.key}" <c:if test = "${status.index eq 0}"> checked="true" </c:if> >${item.value}</label>
								<c:if test="${item.key  eq 1}">
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0057"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</c:if>
								<c:if test="${item.key  eq 2}">
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0058"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</c:if>
								<c:if test="${item.key  eq 3}">
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0059"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th><label><qp:message code="sc.loggingmanagement.0015" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td colspan="3">
						<label><input id="normal" name="fileLog.lstLogDetail[].logFileType" onchange="changeLogFileType(this)" type="radio"
							value="1">FileAppender</label>
						<label><input id="rollingFile" name="fileLog.lstLogDetail[].logFileType" onchange="changeLogFileType(this)"
							type="radio" value="2">RollingFileAppender</label>
						</td>
					</tr>
					<tr>
						<th><label ><qp:message code="sc.loggingmanagement.0007" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td colspan="3">
						<c:forEach items="${CL_LOG_LEVEL}" var="item" varStatus="status">
							<label><input id="fileLevel${status.index}" name="fileLog.lstLogDetail[].logLevel"
								type="radio" value="${item.key}" <c:if test = "${status.index eq 0}"> checked="true" </c:if> >${item.value}</label>
							<c:if test="${item.key  eq 0}">
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0051"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</c:if>
							<c:if test="${item.key  eq 1}">
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0052"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</c:if>
							<c:if test="${item.key  eq 2}">
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0053"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</c:if>
							<c:if test="${item.key  eq 3}">
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0054"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</c:if>
							<c:if test="${item.key  eq 4}">
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0055"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</c:if>
						</c:forEach>
						</td>
					</tr>
					<tr>
						<th><label><qp:message code="sc.loggingmanagement.0017" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td><input id="filePath" name="fileLog.lstLogDetail[0].filePath"
								class="form-control qp-input-text" maxlength="100" /></td>
						<th><label><qp:message code="sc.loggingmanagement.0042" /></label></th>
						<td><input id="charset" name="fileLog.lstLogDetail[0].charset"
								class="form-control qp-input-text" value="UTF-8" maxlength="100"/></td>
					</tr>
					<tr>
						<th><label><qp:message code="sc.loggingmanagement.0019" /></label></th>
						<td><select id="immediateFlush"
							name="fileLog.lstLogDetail[0].immediateFlush" class="form-control qp-input-select">
								<option value="1">True</option>
								<option value="0">False</option>
						</select></td>
						<th><label><qp:message code="sc.loggingmanagement.0020" /></label></th>
						<td><select id="appending" name="fileLog.lstLogDetail[0].appendType"
							class="form-control qp-input-select">
								<option value="1">True</option>
								<option value="0">False</option>
						</select></td>
					</tr>
					<!-- Div for Rolling file appender -->
					<tr name="fileLog[0].policyFile" class="rollingFile">
						<th><label><qp:message code="sc.loggingmanagement.0043" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td>
							<select id="rollingPolicy" name="fileLog.lstLogDetail[0].rollingPolicy" class="form-control qp-input-select">
								<option value="" selected="selected">-- Select --</option>
								<c:forEach var="rollingPolicy" items="${CL_LOG_ROLLING_POLICY}">
									<option value="${rollingPolicy.key}">${rollingPolicy.value}</option>
								</c:forEach>
							</select>
						</td>
						<th><label><qp:message code="sc.loggingmanagement.0016" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td><input id="fileNamePattern" name="fileLog.lstLogDetail[0].patternFileName" type="text"
								class="form-control qp-input-text" maxlength="100"/></td>
					</tr>
					<tr name="fileLog[0].rollingPatternFileName" class="rollingFile">
						<th><label><qp:message code="sc.loggingmanagement.0044" /></label></th>
						<td>
							<select id="triggeringPolicy" name="fileLog.lstLogDetail[0].triggeringPolicy" class="form-control qp-input-select">
								<option value="" selected="selected">-- Select --</option>
								<c:forEach var="triggeringPolicy" items="${CL_LOG_TRIGGERING_POLICY}">
									<option value="${triggeringPolicy.key}">${triggeringPolicy.value}</option>
								</c:forEach>
							</select>
						</td>
						<th><label class="maxFileSize"><qp:message code="sc.loggingmanagement.0024" /></label>&nbsp;<label class="qp-required-field maxFileSize"><qp:message code="sc.sys.0029" /></label></th>
						<td><input id="maxFileSize" name="fileLog.lstLogDetail[0].maxFileSize" maxlength="100" type="text"
								class="form-control qp-input-text maxFileSize" value = "0" /></td>
					</tr>
					<!-- Div for TimeBasedRollingPolicy and SizeAndTimeBasedRollingPolicy -->
					<tr class="timeBasedRollingPolicy rollingFile">
						<th><label><qp:message code="sc.loggingmanagement.0045" /></label></th>
						<td><input id="maxHistory" name="fileLog.lstLogDetail[0].maxHistory" type="text"
								class="form-control qp-input-integer" value = "0" /></td>
						<th><label style="display: none;"><qp:message code="sc.loggingmanagement.0046" /></label></th>
						<td><input id="totalSizeCap" name="fileLog.lstLogDetail[0].totalSizeCap" type="text" style="display: none;"
								class="form-control qp-input-integer" value = "0" /></td>
					</tr>
					<!-- End of div for TimeBasedRollingPolicy and SizeAndTimeBasedRollingPolicy -->

					<!-- Div for FixedWindowRollingPolicy -->
					<tr class="fixedWindowRollingPolicy rollingFile">
						<th><label><qp:message code="sc.loggingmanagement.0047" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></label></th>
						<td><input id="minIndex" name="fileLog.lstLogDetail[0].minIndex" maxlength="100" type="text"
								class="form-control qp-input-integer" value = "0" /></td>
						<th><label><qp:message code="sc.loggingmanagement.0048" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></label></th>
						<td><input id="maxIndex" name="fileLog.lstLogDetail[0].maxIndex" maxlength="100" type="text"
								class="form-control qp-input-integer" value = "0" /></td>
					</tr>
					<!-- End of div for FixedWindowRollingPolicy -->

					<!-- End of div for Rolling file appender -->
				
					<tr>
						<th><label><qp:message code="sc.loggingmanagement.0010" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
						<td><select id="logLayout"
							name="fileLog.lstLogDetail[0].layout" class="form-control qp-input-select">
								<option value="" selected="selected">-- Select --</option>
								<c:forEach var="layout" items="${CL_LOG_LAYOUT}">
									<option value="${layout.key}">${layout.value}</option>
								</c:forEach>
								</select></td>
						<th colspan="2"><th>
					</tr>
					<tr>
						<th><label><qp:message code="sc.loggingmanagement.0011" /></label></th>
						<td name="conversionPattern" colspan="3">
							<div class="input-group set-width" style="width: 100%; float: left;">
							<textarea name ="fileLog.lstLogDetail[0].conversionPattern" class="form-control qp-input-text" style="width: 100%; height: 70px" maxlength="400" />
							<span class="input-group-addon" style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px;">
							<a class="btn qp-button glyphicon glyphicon-cog qp-button-action" style="top: auto;" onclick="openDialogConversionPattern('fileLog.lstLogDetail[0].', 2, 0);"></a>
							</span>
							<div id="patternHidden_2_0">
							</div>
						</div>
						</td>
					</tr>
				</table>
			</div>
		</script>

		<form:form method="post" action="${pageContext.request.contextPath}/loggingmanagement/modify" modelAttribute="loggingManagementForm" role="form">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<qp:ColumnSortHidden />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.project.0010" /></span>
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
							<th><label><qp:message code="sc.project.0005" /></label></th>
							<td><qp:formatText value="${loggingManagementForm.currentProject.projectName}" /></td>
							<th><label><qp:message code="sc.project.0006" /></label></th>
							<td><qp:formatText value="${loggingManagementForm.currentProject.projectCode}" /></td>
						</tr>
						<tr>
							<th><label><qp:message code="sc.sys.0055" /></label></th>
							<td><qp:message code="${CL_DESIGN_STATUS.get(loggingManagementForm.currentProject.status.toString())}" /></td>
							<th><label><qp:message code="sc.sys.0028" /></label></th>
							<td><qp:formatText value="${loggingManagementForm.currentProject.remark}" /></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.loggingmanagement.0002" /></span>
				</div>
				<div class="panel-body">
					<ul class="nav nav-tabs" id="com-menu-sidebar">
						<li class="active"><a href="#tabsLog-01" data-toggle="tab" style="font: bold;" aria-expanded="false"><qp:message code="sc.loggingmanagement.0003" /></a></li>
						<li><a href="#tabsLog-02" data-toggle="tab" style="font: bold;" aria-expanded="true"><qp:message code="sc.loggingmanagement.0004" /></a></li>
						<li><a href="#tabsLog-03" data-toggle="tab" style="font: bold;" aria-expanded="true"><qp:message code="sc.loggingmanagement.0005" /></a></li>
					</ul>
					<div class="tab-content">
						<div id="tabsLog-01" class="tab-pane active" style="height: auto;">
							<form:hidden path="consoleLog.logId" value="${loggingManagementForm.consoleLog.logId}" />
							<form:hidden path="consoleLog.projectId" value="${loggingManagementForm.consoleLog.projectId}" />
							<form:hidden path="consoleLog.logType" value="${loggingManagementForm.consoleLog.logType}" />
							<form:hidden path="consoleLog.updatedDate" value="${loggingManagementForm.consoleLog.updatedDate}" />
							<form:hidden path="consoleLog.lstLogDetail[0].logDetailId" />
							<label> <form:checkbox id="tabsCheckbox-01" path="consoleLogStatus" onclick="tabCheckbox(1)" cssClass="qp-input-checkbox qp-input-checkbox-margin" /> <qp:message code="sc.loggingmanagement.0006" /></label>
							<div id="tabsLogContent-01" style="display: none" class="fileLogInfor">
								<table class="table table-bordered qp-table-form tblFileLog" id="tblFileLog">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th><label><qp:message code="sc.loggingmanagement.0056" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
										<td colspan="3"><c:forEach var="item" items="${CL_LOG_CONFIG_MODE}">
												<c:if test="${consoleLog.lstLogDetail[0].configMode == item.key}">
													<label><form:radiobutton path="consoleLog.lstLogDetail[0].configMode" checked="true" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
														<qp:message code="${CL_LOG_CONFIG_MODE.get(item.key)}"></qp:message></label>
												</c:if>
												<c:if test="${consoleLog.lstLogDetail[0].configMode != item.key}">
													<label><form:radiobutton path="consoleLog.lstLogDetail[0].configMode" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
														<qp:message code="${CL_LOG_CONFIG_MODE.get(item.key)}"></qp:message></label>
												</c:if>
												<c:if test="${item.key  eq 1}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0057"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
												<c:if test="${item.key  eq 2}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0058"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
												<c:if test="${item.key  eq 3}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0059"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
											</c:forEach></td>
									</tr>
									<tr>
										<th><label><qp:message code="sc.loggingmanagement.0007" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
										<td colspan="3">
											<c:forEach var="item" items="${CL_LOG_LEVEL}">
												<label><form:radiobutton path="consoleLog.lstLogDetail[0].logLevel" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
													<qp:message code="${CL_LOG_LEVEL.get(item.key)}"></qp:message></label>
												<c:if test="${item.key  eq 0}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0051"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
												<c:if test="${item.key  eq 1}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0052"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
												<c:if test="${item.key  eq 2}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0053"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
												<c:if test="${item.key  eq 3}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0054"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
												<c:if test="${item.key  eq 4}">
													<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0055"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</c:if>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<th><label><qp:message code="sc.loggingmanagement.0042" /></label></th>
										<td><form:input path="consoleLog.lstLogDetail[0].charset" cssClass="form-control qp-input-text" maxlength="100" /></td>
										<th><label><qp:message code="sc.loggingmanagement.0009" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
										<td>
											<form:select path="consoleLog.lstLogDetail[0].logTarget" cssClass="qp-input-select" style="width: 100%">
												<c:forEach var="logTarget" items="${CL_LOG_TARGET}">
													<option value="${logTarget.key}" <c:if test = "${loggingManagementForm.consoleLog.lstLogDetail[0].logTarget eq logTarget.key}"> selected="selected" </c:if>>${logTarget.value}</option>
												</c:forEach>
											</form:select>
										</td>
									</tr>
									<tr>
										<th><label><qp:message code="sc.loggingmanagement.0011" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
										<td colspan="3" name="conversionPattern">
											<div class="input-group set-width" style="width: 100%; float: left;">
												<form:textarea path="consoleLog.lstLogDetail[0].conversionPattern" cssClass="form-control qp-input-text" style="width: 100%; height: 70px" maxlength="400" />
												<span class="input-group-addon" style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px;"> <a class="btn qp-button glyphicon glyphicon-cog qp-button-action" style="top: auto;" onclick="openDialogConversionPattern('consoleLog.lstLogDetail[0].', 1, 0);"></a>
												</span>
												<div id="patternHidden_1_0">
													<c:forEach var="pattern" items="${loggingManagementForm.consoleLog.lstLogDetail[0].lstConversionPattern}" varStatus="status">
														<div id="pattern${status.index}">
															<form:hidden path="consoleLog.lstLogDetail[0].lstConversionPattern[${status.index}].patternId" />
															<form:hidden path="consoleLog.lstLogDetail[0].lstConversionPattern[${status.index}].itemSequence" />
														</div>
													</c:forEach>
												</div>
											</div>
										</td>
									</tr>
								</table>
								<br />
							</div>
						</div>
						<div id="tabsLog-02" class="tab-pane" style="height: auto;">
							<form:hidden path="fileLog.logId" value="${loggingManagementForm.fileLog.logId}" />
							<form:hidden path="fileLog.projectId" value="${loggingManagementForm.fileLog.projectId}" />
							<form:hidden path="fileLog.logType" value="${loggingManagementForm.fileLog.logType}" />
							<form:hidden path="fileLog.updatedDate" value="${loggingManagementForm.fileLog.updatedDate}" />
							<label><form:checkbox id="tabsCheckbox-02" path="fileLogStatus" class="qp-input-checkbox-margin qp-input-checkbox" onclick="tabCheckbox(2)" />
								<qp:message code="sc.loggingmanagement.0012" /></label>
							<div id="tabsLogContent-02" style="display: none">
								<div class="qp-div-action">
									<input type="button" value="<qp:message code="sc.loggingmanagement.0013" />" class="btn qp-button-client" onclick="addFileLog();">
								</div>
								<div id="fileWrapper">
									<input type="hidden" id="numberFileLog" value="${(loggingManagementForm.fileLog.lstLogDetail != null) ? loggingManagementForm.fileLog.lstLogDetail.size() : 0}">
									<c:forEach var="fileLog" items="${loggingManagementForm.fileLog.lstLogDetail}" varStatus="status">
										<div id="fileLogInfor${status.index }" class="fileLogInfor" style="border-color: red; border-style: dashed; border-width: 2px; margin-bottom: 20px;">
											<form:hidden path="fileLog.lstLogDetail[${status.index }].logDetailId" />
											<table class="table table-bordered qp-table-form tblFileLog" id="tblFileLog">
												<colgroup>
													<col width="20%" />
													<col width="30%" />
													<col width="20%" />
													<col width="30%" />
												</colgroup>
												<tr>
													<th colspan="4" style="text-align: left; vertical-align: middle;" colspan="4"><qp:message code="sc.loggingmanagement.0014" /> <span id="id-block-reload-data0" onclick="removeFileLog(this);" title="<qp:message code="sc.loggingmanagement.0034" />" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px"></span></th>
												</tr>
												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0056" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td colspan="3"><c:forEach var="item" items="${CL_LOG_CONFIG_MODE}">
															<c:if test="${fileLog.configMode == item.key}">
																<label><form:radiobutton path="fileLog.lstLogDetail[${status.index }].configMode" checked="true" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
																	<qp:message code="${CL_LOG_CONFIG_MODE.get(item.key)}"></qp:message></label>
															</c:if>
															<c:if test="${fileLog.configMode != item.key}">
																<label><form:radiobutton path="fileLog.lstLogDetail[${status.index }].configMode" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
																	<qp:message code="${CL_LOG_CONFIG_MODE.get(item.key)}"></qp:message></label>
															</c:if>
															<c:if test="${item.key  eq 1}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0057"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
															<c:if test="${item.key  eq 2}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0058"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
															<c:if test="${item.key  eq 3}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0059"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
														</c:forEach></td>
												</tr>
												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0015" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td colspan="3"><c:forEach var="item" items="${CL_LOG_FILE_TYPE}">
															<label><form:radiobutton path="fileLog.lstLogDetail[${status.index }].logFileType" onchange="changeLogFileType(this)" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
																<qp:message code="${CL_LOG_FILE_TYPE.get(item.key)}"></qp:message></label>
														</c:forEach></td>
												</tr>
												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0007" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td colspan="3">
														<%-- <form:radiobuttons class="qp-input-radio qp-input-radio-margin" path="fileLog.lstLogDetail[${status.index }].logLevel"  items="${CL_LOG_LEVEL}" /> --%> 
														<c:forEach var="item" items="${CL_LOG_LEVEL}">
															<label><form:radiobutton path="fileLog.lstLogDetail[${status.index }].logLevel" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
																<qp:message code="${CL_LOG_LEVEL.get(item.key)}"></qp:message></label>
															<c:if test="${item.key  eq 0}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0051"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
															<c:if test="${item.key  eq 1}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0052"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
															<c:if test="${item.key  eq 2}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0053"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
															<c:if test="${item.key  eq 3}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0054"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
															<c:if test="${item.key  eq 4}">
																<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0055"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</c:if>
														</c:forEach>
													</td>
												</tr>
												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0017" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].filePath" cssClass="form-control qp-input-text" maxlength="200" /></td>
													<th><label><qp:message code="sc.loggingmanagement.0042" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].charset" cssClass="form-control qp-input-text" maxlength="100" /></td>
												</tr>
												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0019" /></label></th>
													<td><form:select path="fileLog.lstLogDetail[${status.index }].immediateFlush" cssClass="form-control qp-input-select pull-left">
															<form:options items="${CL_IMMEDIATE_FLUSH }" />
														</form:select></td>
													<th><label><qp:message code="sc.loggingmanagement.0020" /></label></th>
													<td><form:select path="fileLog.lstLogDetail[${status.index }].appendType" cssClass="form-control qp-input-select pull-left">
															<form:options items="${CL_IMMEDIATE_FLUSH }" />
														</form:select></td>
												</tr>
												<!-- Div for Rolling file appender -->
												<tr name="fileLog[0].policyFile" class="rollingFile">
													<th><label><qp:message code="sc.loggingmanagement.0043" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:select path="fileLog.lstLogDetail[${status.index }].rollingPolicy" cssClass="form-control qp-input-select" style="width: 100%">
															<form:option value="" label="-- Select --" />
															<c:forEach var="rollingPolicy" items="${CL_LOG_ROLLING_POLICY}">
																<option value="${rollingPolicy.key}" <c:if test = "${loggingManagementForm.fileLog.lstLogDetail[status.index].rollingPolicy eq rollingPolicy.key}"> selected="selected" </c:if>>${rollingPolicy.value}</option>
															</c:forEach>
														</form:select></td>
													<th><label><qp:message code="sc.loggingmanagement.0016" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].patternFileName" cssClass="form-control qp-input-text" maxlength="100" /></td>

												</tr>
												<tr name="fileLog[0].rollingPatternFileName" class="rollingFile">
													<th><label><qp:message code="sc.loggingmanagement.0044" /></label></th>
													<td><form:select path="fileLog.lstLogDetail[${status.index }].triggeringPolicy" cssClass="form-control qp-input-select" style="width: 100%">
															<form:option value="" label="-- Select --" />
															<c:forEach var="triggeringPolicy" items="${CL_LOG_TRIGGERING_POLICY}">
																<option value="${triggeringPolicy.key}" <c:if test = "${loggingManagementForm.fileLog.lstLogDetail[status.index].triggeringPolicy eq triggeringPolicy.key}"> selected="selected" </c:if>>${triggeringPolicy.value}</option>
															</c:forEach>
														</form:select></td>
													<th><label class="maxFileSize"><qp:message code="sc.loggingmanagement.0024" /></label>&nbsp;<label class="qp-required-field maxFileSize"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].maxFileSize" maxlength="100" cssClass="form-control qp-input-text maxFileSize" /></td>
												</tr>
												<!-- Div for TimeBasedRollingPolicy and SizeAndTimeBasedRollingPolicy -->
												<tr class="timeBasedRollingPolicy rollingFile">
													<th><label><qp:message code="sc.loggingmanagement.0045" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].maxHistory" cssClass="form-control qp-input-integer" /></td>
													<th><label style="display: none;"><qp:message code="sc.loggingmanagement.0046" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].totalSizeCap" style="display: none;" cssClass="form-control qp-input-integer" /></td>
												</tr>
												<!-- End of div for TimeBasedRollingPolicy and SizeAndTimeBasedRollingPolicy -->
												<!-- Div for FixedWindowRollingPolicy -->
												<tr class="fixedWindowRollingPolicy rollingFile">
													<th><label><qp:message code="sc.loggingmanagement.0047" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].minIndex" maxlength="100" cssClass="form-control qp-input-integer" /></td>
													<th><label><qp:message code="sc.loggingmanagement.0048" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:input path="fileLog.lstLogDetail[${status.index }].maxIndex" maxlength="100" cssClass="form-control qp-input-integer" /></td>
												</tr>
												<!-- End of div for FixedWindowRollingPolicy -->

												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0010" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td><form:select path="fileLog.lstLogDetail[${status.index }].layout" cssClass="qp-input-select" style="width: 100%">
															<form:option value="" label="-- Select --" />
															<c:forEach var="layout" items="${CL_LOG_LAYOUT}">
																<option value="${layout.key}" <c:if test = "${loggingManagementForm.fileLog.lstLogDetail[status.index].layout eq layout.key}"> selected="selected" </c:if>>${layout.value}</option>
															</c:forEach>
														</form:select></td>
													<th colspan="2"></th>
												</tr>
												<tr>
													<th><label><qp:message code="sc.loggingmanagement.0011" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
													<td name="conversionPattern" colspan="3">
														<div class="input-group set-width" style="width: 100%; float: left;">
															<form:textarea path="fileLog.lstLogDetail[${status.index }].conversionPattern" cssClass="form-control qp-input-text" style="width: 100%; height: 70px" maxlength="400" />
															<span class="input-group-addon" style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px;"> <a class="btn qp-button glyphicon glyphicon-cog qp-button-action" style="top: auto;" onclick="openDialogConversionPattern('fileLog.lstLogDetail[${status.index }].', 2, ${status.index });"></a>
															</span>
															<div id="patternHidden_2_${status.index}">
																<c:forEach var="pattern" items="${loggingManagementForm.fileLog.lstLogDetail[status.index].lstConversionPattern}" varStatus="status1">
																	<div id="pattern${status1.index}">
																		<form:hidden path="fileLog.lstLogDetail[${status.index }].lstConversionPattern[${status1.index}].patternId" />
																		<form:hidden path="fileLog.lstLogDetail[${status.index }].lstConversionPattern[${status1.index}].itemSequence" />
																	</div>
																</c:forEach>
															</div>
														</div>
													</td>
												</tr>
											</table>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<div id="tabsLog-03" class="tab-pane" style="height: auto;">
							<form:hidden path="databaseLog.logId" value="${loggingManagementForm.databaseLog.logId}" />
							<form:hidden path="databaseLog.projectId" value="${loggingManagementForm.databaseLog.projectId}" />
							<form:hidden path="databaseLog.logType" value="${loggingManagementForm.databaseLog.logType}" />
							<form:hidden path="databaseLog.updatedDate" value="${loggingManagementForm.databaseLog.updatedDate}" />
							<label><form:checkbox id="tabsCheckbox-03" path="databaseLogStatus" class="qp-input-checkbox-margin qp-input-checkbox" onclick="tabCheckbox(3)" />
								<qp:message code="sc.loggingmanagement.0027" /></label>
							<div id="tabsLogContent-03" style="display: none">
								<div id="databaseWrapper">
									<input type="hidden" id="numberDatabaseLog" value="${(loggingManagementForm.databaseLog.lstLogDetail != null) ? loggingManagementForm.databaseLog.lstLogDetail.size() : 0}">
									<%-- <c:forEach var="databaseLog" items="${loggingManagementForm.databaseLog.lstLogDetail}" varStatus="status"> --%>
									<div id="databaseLogInfor" class="databaseLogInfor fileLogInfor" style="border-color: red; margin-bottom: 20px;">
										<table class="table table-bordered qp-table-form tblFileLog" id="tblDatabaseLog">
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tr>
												<th><label><qp:message code="sc.loggingmanagement.0056" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td colspan="3"><c:forEach var="item" items="${CL_LOG_CONFIG_MODE}">
														<c:if test="${databaseLog.lstLogDetail[0].configMode == item.key}">
															<label><form:radiobutton path="databaseLog.lstLogDetail[0].configMode" checked="true" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
																<qp:message code="${CL_LOG_CONFIG_MODE.get(item.key)}"></qp:message></label>
														</c:if>
														<c:if test="${databaseLog.lstLogDetail[0].configMode != item.key}">
															<label><form:radiobutton path="databaseLog.lstLogDetail[0].configMode" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
																<qp:message code="${CL_LOG_CONFIG_MODE.get(item.key)}"></qp:message></label>
														</c:if>
														<c:if test="${item.key  eq 1}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0057"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
														<c:if test="${item.key  eq 2}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0058"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
														<c:if test="${item.key  eq 3}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0059"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
													</c:forEach></td>
											</tr>
											<tr>
												<th><label><qp:message code="sc.loggingmanagement.0007" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td colspan="3">
													<%-- <form:radiobuttons class="qp-input-radio qp-input-radio-margin" path="databaseLog.lstLogDetail[0].logLevel"  items="${CL_LOG_LEVEL}" /> --%> 
													<c:forEach var="item" items="${CL_LOG_LEVEL}">
														<label><form:radiobutton path="databaseLog.lstLogDetail[0].logLevel" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin" />
															<qp:message code="${CL_LOG_LEVEL.get(item.key)}"></qp:message></label>
														<c:if test="${item.key  eq 0}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0051"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
														<c:if test="${item.key  eq 1}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0052"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
														<c:if test="${item.key  eq 2}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0053"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
														<c:if test="${item.key  eq 3}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0054"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
														<c:if test="${item.key  eq 4}">
															<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.loggingmanagement.0055"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
														</c:if>
													</c:forEach>
												</td>
											</tr>
											<tr>
												<th><label><qp:message code="sc.loggingmanagement.0049" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>
													<form:select path="databaseLog.lstLogDetail[0].dbType" cssClass="form-control qp-input-select" style="width: 100%" onchange="onchangeDBType(this)">
														<c:forEach var="dbType" items="${CL_DATABASE_TYPE}">
															<option value="${dbType.key}" <c:if test = "${loggingManagementForm.databaseLog.lstLogDetail[0].dbType eq dbType.key}"> selected="selected" </c:if>>${dbType.value}</option>
														</c:forEach>
													</form:select>
												</td>
												<th><label><qp:message code="sc.loggingmanagement.0029" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><form:input id="dbDriver" path="databaseLog.lstLogDetail[0].dbDriver" cssClass="form-control qp-input-text" maxlength="100" /></td>
											</tr>
											<tr>
												<th><label><qp:message code="sc.loggingmanagement.0030" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><form:input id="dbConnection" path="databaseLog.lstLogDetail[0].dbUrl" cssClass="form-control qp-input-text" maxlength="100" /></td>
												<th><label><qp:message code="sc.loggingmanagement.0050" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td>
													<form:select path="databaseLog.lstLogDetail[0].insertHeaders" cssClass="form-control qp-input-select pull-left">
														<form:options items="${CL_INSERT_HEADERS}" />
													</form:select>
												</td>
											</tr>
											<tr>
												<th><label><qp:message code="sc.loggingmanagement.0031" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><form:input id="dbUsername" path="databaseLog.lstLogDetail[0].dbUserName" cssClass="form-control qp-input-text" maxlength="100" /></td>
												<th><label><qp:message code="sc.loggingmanagement.0032" /></label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
												<td><form:input type="password" id="dbPassword" path="databaseLog.lstLogDetail[0].dbPassword" cssClass="form-control qp-input-text" maxlength="100" /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%@include file="conversionPattern.jsp"%>
			<div class="qp-div-action">
				<qp:authorization permission="loggingmanagementModify">
					<button type="submit" class="btn qp-button qp-dialog-confirm">
						<qp:message code="sc.sys.0031" />
					</button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>

</tiles:insertDefinition>