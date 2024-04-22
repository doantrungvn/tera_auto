<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.project.0015" />
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
	<script type="text/javascript">
			function setFlag() {
				$("#actionDelete").val(true); 
			}
		</script>
	</tiles:putAttribute>
	<c:if test="${notExistFlg ne 1}">
		<tiles:putAttribute name="body">
			<form:form method="post" modelAttribute="projectForm" action="${pageContext.request.contextPath}/project/delete">
                <form:errors path="*" cssClass="error" element="div" />
	            <!-- begin project information -->
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.project.0010" /></span>
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
								<th><qp:message code="sc.project.0005" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.projectName}" /></td>
								<th><qp:message code="sc.project.0006" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.projectCode}" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0055" /></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(projectForm.status)}" /></td>
								<th><qp:message code="sc.sys.0028" /></th>
								<td class="word-wrap"><qp:formatRemark value="${projectForm.remark}" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.project.0023" /></span>
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
								<th><qp:message code="sc.project.0017" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.dbName}" /></td>
								<th></th>
								<td></td>
							</tr>
							<tr>
								<th><qp:message code="sc.project.0016" /></th>
								<td class="word-wrap"><qp:formatText value="${CL_DATABASE_TYPE.get(projectForm.dbType.toString())}" /></td>
								<th><qp:message code="sc.project.0022" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.dbDriver}" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.project.0018" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.dbHostName}" /></td>
								<th><qp:message code="sc.project.0019" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.dbPort}" /></td>
								
							</tr>
							<tr>
								<th><qp:message code="sc.project.0020" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.dbUser}" /></td>
								<th><qp:message code="sc.project.0021" /></th>
								<td class="word-wrap"><qp:formatText value="${projectForm.dbPassword}" /></td>
							</tr>
						</table>
					</div>
				</div>
				
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.project.0026" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr class="success form-inline">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.sys.0058" /></td>
						</tr>
						
						<tr>
							<th><form:label path="caseSensitivity"><qp:message code="sc.project.0024" /></form:label></th>
							<td><qp:message code="sc.project.0025" /> : <qp:booleanFormatYesNo value="${projectForm.caseSensitivity}" type="1"/></td>
							<th><qp:message code="sc.project.0027" /></th>
							<td class="word-wrap"><qp:formatText value="${projectForm.packageName}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0034" /></th>
							<td>
								${projectForm.defaultLanguageIdAutocomplete}
							</td>
							<th></th>
							<td></td>
						</tr>
						<!--  QuyND Add format data settings -->
						<tr class="success form-inline" tag="tqp.account">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.project.0037" /></td>
						</tr>
						<tr>
							<th><form:label path="dataFormat.integerFormat"><qp:message code="sc.accountprofile.0014" /></form:label></th>
							<td>
								<qp:formatText value="${projectForm.dataFormat.integerFormat}"/>
							</td>
							<th><form:label path="dataFormat.decimalFormat"><qp:message code="sc.accountprofile.0015" /></form:label></th>
							<td>
								<qp:formatText value="${projectForm.dataFormat.decimalFormat}"/>
							</td>
						</tr>
						<tr>
							<th><form:label path="dataFormat.dateFormat"><qp:message code="sc.accountprofile.0016" /></form:label></th>
							<td>
								<qp:formatText value="${CL_DATETIME_FORMAT_SETTING.get(projectForm.dataFormat.dateFormat)}"/>
							</td>
							<th><form:label path="dataFormat.dateTimeFormat"><qp:message code="sc.accountprofile.0017" /></form:label></th>
							<td>
								<qp:formatText value="${CL_DATETIME_FORMAT_SETTING.get(projectForm.dataFormat.dateTimeFormat)}"/>
							</td>
							
						</tr>
						<tr>
							<th><form:label path="dataFormat.timeFormat"><qp:message code="sc.accountprofile.0018" /></form:label></th>
							<td>
								<qp:formatText value="${CL_TIME_FORMAT_SETTING.get(projectForm.dataFormat.timeFormat)}"/>
							</td>
							<th><form:label path="dataFormat.currencyCode"><qp:message code="sc.accountprofile.0020" /></form:label></th>
							<td>
								<qp:message code="${CL_CURRENCY_CODE.get(projectForm.dataFormat.currencyCode)}"/>
							</td>
						</tr>
						<tr>
							<th><form:label path="dataFormat.currencyFormat"><qp:message code="sc.accountprofile.0019" /></form:label></th>
							<td>
								<qp:formatText value="${projectForm.dataFormat.currencyFormat}"/>
							</td>
							<th><form:label path="dataFormat.currencyCodePosition"><qp:message code="sc.accountprofile.0021" /></form:label></th>
							<td>
								<qp:message code="${CL_CURRENCY_CODE_POSITION.get(projectForm.dataFormat.currencyCodePosition)}"/>
							</td>
						</tr>
						<!-- End of format data settings -->
						<tr class="success form-inline">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.project.0036" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0029"/></th>
							<td><qp:message code="${CL_ENABLE_DISABLE.get(projectForm.webserviceFlg.toString())}"/></td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0030" /></th>
							<td colspan="3">
								<div class="form-inline">
									<span><qp:message code="sc.project.0031"/></span>
									<qp:formatText value="${projectForm.webservicePattern}"></qp:formatText>
									<span><qp:message code="sc.project.0032"/></span>
								</div>
							</td>
						</tr>
						<tr class="success form-inline">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.project.0038" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0039"/></th>
							<td><qp:formatText value="${projectForm.emailAddress}" /></td>
							<th><qp:message code="sc.project.0040"/></th>
							<td><qp:formatText value="${projectForm.emailName}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0041"/></th>
							<td><qp:formatText value="${projectForm.smtpHost}" /></td>
							<th/>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0042"/></th>
							<td>
								<label ><qp:message code="${CL_ENCRYPTION_TYPE.get(projectForm.smtpEncryption.toString())}"/></label>
							</td>
							<th><qp:message code="sc.project.0045"/></th>
							<td><qp:formatText value="${projectForm.smtpPort}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.project.0046"/></th>
							<td><qp:formatText value="${projectForm.smtpUserName}" /></td>
							<th><qp:message code="sc.project.0047"/></th>
							<td><qp:formatText value="${projectForm.smtpPassword}" /></td>
						</tr>
					</table>
				</div>
			</div>
				
				<!-- end project information -->
				<!-- begin operation button panel -->
				<div class="qp-div-action">
					<form:hidden path="projectId" />
					<form:hidden path="actionDelete" value="false"/>
					<form:hidden path="updatedDate"/>
					<c:if test="${projectForm.openOwner eq 1}">
						<c:if test = "${projectForm.status eq 1 }">
							<c:if test="${checkDesignStatus eq 0}">
								<qp:authorization permission="moduleModify">
									<form:hidden path="status" value="2"/>
									<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('2')}"/></button>
								</qp:authorization>
							</c:if>
			                <sec:authorize access="hasRole('projectSpecialdelete')">
				                    <button type="button" class="btn btn-md btn-danger qp-dialog-confirm" messageId="inf.sys.0042" onclick="setFlag()"><qp:message code="sc.sys.0052" /></button>
							</sec:authorize>
			                <sec:authorize access="!hasRole('projectSpecialdelete')">
			                	<c:if test="${ empty projectForm.modules}">
				                	<qp:authorization permission="projectDelete">
				                    	<button type="button" class="btn btn-md btn-warning qp-dialog-confirm" messageId="inf.sys.0014" onclick="setFlag()"><qp:message code="sc.sys.0008" /></button>
				                    </qp:authorization>
			                	</c:if>
							</sec:authorize>
							<qp:authorization permission="projectModify">
							    <a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/project/modify?projectId=${projectForm.projectId}&mode=1"><qp:message code="sc.sys.0006" /></a>
							</qp:authorization>
						</c:if>
						<c:if test="${projectForm.status eq 2}">
							<qp:authorization permission="changeDesignStatus">
								<form:hidden path="status" value="1"/>
								<button type="button" style="background-color: #419641" class="btn qp-button-warning qp-dialog-confirm" messageId="inf.sys.0036"><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></button>
							</qp:authorization>
						</c:if>
					</c:if>
				</div>
				<!-- <input id="isChange" type="hidden" value="false"/> -->
				<!-- end operation button panel -->
				<!-- begin list of related module -->
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.project.0012" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col />
									<col width="40%" />
									<col width="30%" />
									<col width="30%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.module.0007" /></th>
										<th><qp:message code="sc.module.0008" /></th>
										<th><qp:message code="sc.businesstype.0001" /></th>
									</tr>
								</thead>
								<c:forEach var="module" items="${projectForm.modules}" varStatus="status">
									<tr>
										<td><qp:formatInteger value="${status.count}" /></td>
										<td>
										   <qp:authorization permission="moduleView" isDisplay="true" displayValue="${module.moduleName}">
										       <a class="qp-link-popup" href="${pageContext.request.contextPath}/module/view?moduleId=${f:h(module.moduleId)}"><qp:formatText value="${module.moduleName}" /></a>
										   </qp:authorization>
										</td>
										<td><qp:formatText value="${module.moduleCode}" /></td>
										<td><qp:formatText value="${module.businessTypeName}" /></td>
									</tr>
								</c:forEach>
								<c:if test="${empty projectForm.modules}">
	                                <tr><td colspan="4"><qp:message code="inf.sys.0013"/></td></tr>
	                            </c:if>
							</table>
						</div>
					</div>
				</div>
				<!-- end list of related module -->
			</form:form>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>