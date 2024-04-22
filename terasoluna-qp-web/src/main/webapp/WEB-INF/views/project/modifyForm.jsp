
<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.project"></qp:message></span></li>
		<li><span><qp:message code="sc.project.0014"/></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/project/search"><qp:message code="sc.project.0008"/></a>
	</tiles:putAttribute>

    <jsp:include page="initData.jsp"></jsp:include>

	<tiles:putAttribute name="body">
		<script type="text/javascript">
			 $(document).ready(function() {
				 var input = $("#inputText");
				 var radio = $("#extraSetting").find("tr[type='webservice']").find("input[type='radio']").each(function(){
					if($(this).attr("value") == "true" && $(this).attr("checked") == "checked") {
						input.attr("readOnly",false);
					} else if($(this).attr("value") == "false" && $(this).attr("checked") == "checked") {
						input.attr("readOnly",true);
					}
				 });
			 });
			
			function setReadOnly(obj) {
				var varRadio = $(obj).val();
				var input = $(obj).closest("tr").next("tr").find("input");
				if(varRadio == "false") {
					$(input).attr("readOnly",true);
				} else{
					$(input).attr("readOnly",false);
				}
			}
		</script>
	
	
		<form:form method="post" modelAttribute="projectForm" action="${pageContext.request.contextPath}/project/modifyConfirm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
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
							<th><form:label path="projectName"><qp:message code="sc.project.0005" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input path="projectName" type="text" cssClass="form-control qp-input-text qp-convention-name" maxlength="200" /></td>
							<th><form:label path="projectCode"><qp:message code="sc.project.0006" /></form:label>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input path="projectCode" type="text" cssClass="form-control qp-input-text qp-convention-code" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="status"><qp:message code="sc.sys.0055" /></form:label></th>
							<td><form:hidden path="status"/><qp:message code="${CL_DESIGN_STATUS.get(projectForm.status.toString())}" /></td>
							<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
							<td><form:textarea path="remark" type="text" rows="5" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
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
							<th><form:label path="dbName"><qp:message code="sc.project.0017" /></form:label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text" path="dbName" maxlength="50" /></td>
								<th></th>
								<td></td>
						</tr>
						<tr>
							<th><form:label path="dbType"><qp:message code="sc.project.0016" /></form:label></th>
							<td>
								<form:hidden path="dbTypeOld"/>
								<form:select path="dbType" cssClass="form-control qp-input-select">
									<form:options items="${CL_DATABASE_TYPE}"/>
								</form:select>
							</td>
							<th><form:label path="dbDriver"><qp:message code="sc.project.0022" /></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="dbDriver" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="dbHostName"><qp:message code="sc.project.0018" /></form:label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text" path="dbHostName" maxlength="50" /></td>
							<th><form:label path="dbPort"><qp:message code="sc.project.0019" /></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-serial" path="dbPort" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="dbUser"><qp:message code="sc.project.0020" /></form:label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text" path="dbUser" maxlength="50" /></td>
							<th><form:label path="dbPassword"><qp:message code="sc.project.0021" /></form:label></th>
							<td><form:input type="password" cssClass="form-control qp-input-text" path="dbPassword" maxlength="50" showPassword="true" autocomplete="new-password"/></td>
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
					<table class="table table-bordered qp-table-form" id="extraSetting">
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
							<th><qp:message code="sc.project.0024" /></th>
							<td><label>
									<form:checkbox path="caseSensitivity" value="caseSensitivity" cssClass="qp-input-checkbox-margin qp-input-checkbox form-inline"/>
									<qp:message code="sc.project.0025" />
								</label>
							</td>
							<th>
								<form:label path="packageName"><qp:message code="sc.project.0027" />&nbsp;
									<span class="qp-required-field "><qp:message code="sc.sys.0029" /></span>
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.project.0028"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</form:label>
							</th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="packageName" maxlength="200" /></td>
						</tr>
						<%-- <tr>
							<th><form:label path="defaultLanguageId"><qp:message code="sc.project.0034" /></form:label></th>
							<td>
								<qp:autocomplete name="defaultLanguageId" optionValue="output02" optionLabel="optionLabel" value="${projectForm.defaultLanguageId}"  selectSqlId="getLanguageDesignForAutocomplete" displayValue="${projectForm.defaultLanguageIdAutocomplete}" onChangeEvent="changeToLanguageAC" arg02="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" mustMatch="true" />
							</td>
							<th></th>
							<td></td>
						</tr> --%>
						
						<!--  QuyND Add format data settings -->
						<tr class="success form-inline" tag="tqp.account">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.project.0037" /></td>
						</tr>
						<tr>
							<th><form:label path="dataFormat.integerFormat"><qp:message code="sc.accountprofile.0014" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dataFormat.integerFormat">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="#,###">#,###</form:option>
								</form:select>
							</td>
							<th><form:label path="dataFormat.decimalFormat"><qp:message code="sc.accountprofile.0015" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dataFormat.decimalFormat">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="#,###.###">#,###.###</form:option>
								</form:select>
							</td>
						</tr>
						<tr>
							<th><form:label path="dataFormat.dateFormat"><qp:message code="sc.accountprofile.0016" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dataFormat.dateFormat">
									<form:options items="${CL_DATE_FORMAT_SETTING}"/>
								</form:select>
							</td>
							<th><form:label path="dataFormat.dateTimeFormat"><qp:message code="sc.accountprofile.0017" /></form:label></th>
							<td>
								<form:select path="dataFormat.dateTimeFormat" cssClass="form-control qp-input-select">
									<form:options items="${CL_DATETIME_FORMAT_SETTING}"/>
								</form:select>
							</td>
							
						</tr>
						<tr>
							<th><form:label path="dataFormat.timeFormat"><qp:message code="sc.accountprofile.0018" /></form:label></th>
							<td>
								<form:select path="dataFormat.timeFormat" cssClass="form-control qp-input-select">
									<form:options items="${CL_TIME_FORMAT_SETTING}"/>
								</form:select>
							</td>
							<th><form:label path="dataFormat.currencyCode"><qp:message code="sc.accountprofile.0020" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dataFormat.currencyCode">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="¥">¥-CNY</form:option>
									<form:option value="₩">₩-KPW</form:option>
									<form:option value="Rp">Rp-IDR</form:option>
									<form:option value="¥">¥-JPY</form:option>
									<form:option value="RM">RM-MYR</form:option>
									<form:option value="$">$-SGD</form:option>
									<form:option value="฿">฿-THB</form:option>
									<form:option value="£">£-GBP</form:option>
									<form:option value="$">$-USD</form:option>
									<form:option value="₫">₫-VND</form:option>
								</form:select>
							</td>
						</tr>
						<tr>
							<th><form:label path="dataFormat.currencyFormat"><qp:message code="sc.accountprofile.0019" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dataFormat.currencyFormat">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="#,###.###">#,###.###</form:option>
								</form:select>
							</td>
							<th><form:label path="dataFormat.currencyCodePosition"><qp:message code="sc.accountprofile.0021" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dataFormat.currencyCodePosition">
									<!-- <form:option value=""></form:option> -->
									<form:option value="p"><qp:message code="cl.accountprofile.0041" /></form:option>
									<form:option value="s"><qp:message code="cl.accountprofile.0042" /></form:option>
								</form:select>
							</td>
						</tr>
						
						<!-- End of format data settings -->
						
						<tr class="success form-inline">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.project.0036" /></td>
						</tr>
						<tr type="webservice">
							<th><form:label path="webserviceFlg"><qp:message code="sc.project.0029"/></form:label></th>
							<td colspan="3">
								<c:forEach var="item" items="${CL_ENABLE_DISABLE}">
									<label><form:radiobutton id="radiobutton" path="webserviceFlg" value="${item.key}" class="qp-input-radio-margin  qp-input-radio" onclick="setReadOnly(this)"/><qp:message code="${item.value}"/></label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th>
								<form:label path="webservicePattern"><qp:message code="sc.project.0030" />&nbsp;
									<span class="qp-required-field "><qp:message code="sc.sys.0029" /></span>
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.project.0033"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</form:label>
							</th>
							<td colspan="3">
								<div class="form-inline">
									<span><qp:message code="sc.project.0031"/></span>
									<form:input id="inputText" type="text" cssClass="form-control qp-input-text" path="webservicePattern" maxlength="50" readonly="false"/>
									<span><qp:message code="sc.project.0032"/></span>
								</div>
							</td>
						</tr>
						<tr class="success form-inline">
							<td style="text-align: left;" colspan="4"><qp:message code="sc.project.0038" /></td>
						</tr>
						<tr>
							<th><form:label path="emailAddress"><qp:message code="sc.project.0039"/></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="emailAddress" maxlength="200" /></td>
							<th><form:label path="emailName"><qp:message code="sc.project.0040"/></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="emailName" maxlength="200" /></td>
						</tr>
						<tr>
							<th><form:label path="smtpHost"><qp:message code="sc.project.0041"/></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="smtpHost" maxlength="200" /></td>
							<th/>
						</tr>
						<tr>
							<th><form:label path="smtpEncryption"><qp:message code="sc.project.0042"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_ENCRYPTION_TYPE}">
									<label><form:radiobutton path="smtpEncryption" value="${item.key}" class="qp-input-radio-margin  qp-input-radio" onclick="setReadOnly(this)"/><qp:message code="${item.value}"/></label>
								</c:forEach>
							</td>
							<th><form:label path="smtpPort"><qp:message code="sc.project.0045"/></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-serial" path="smtpPort" maxlength="200" /></td>
						</tr>
						<tr>
							<th><form:label path="smtpUserName"><qp:message code="sc.project.0046"/></form:label></th>
							<td><form:input type="text" cssClass="form-control qp-input-text" path="smtpUserName" maxlength="200" /></td>
							<th><form:label path="smtpPassword"><qp:message code="sc.project.0047"/></form:label></th>
							<td><form:input type="password" cssClass="form-control qp-input-password" path="smtpPassword" maxlength="200" autocomplete="new-password"/></td>
						</tr>
					</table>
					
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="projectModify">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031"></qp:message></button>
					<form:hidden path="updatedDate" />
					<form:hidden path="updatedBy" />
					<form:hidden path="projectId" />
				</qp:authorization>
			</div>
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
											<input type="hidden" name="modules[${status.index}].moduleName" value="${module.moduleName}">
											<qp:authorization permission="moduleView" isDisplay="true" displayValue="${module.moduleName}">
												<a class="qp-link-popup" href="${pageContext.request.contextPath}/module/view?moduleId=${f:h(module.moduleId)}"><qp:formatText value="${module.moduleName}" /></a>
											</qp:authorization>
										</td>
										<td><qp:formatText value="${module.moduleCode}" /><input type="hidden" name="modules[${status.index}].moduleCode" value="${module.moduleCode}"></td>
										<td><qp:formatText value="${module.businessTypeName}" /><input type="hidden" name="modules[${status.index}].businessTypeName" value="${module.businessTypeName}"></td>
									</tr>
								</c:forEach>
								<c:if test="${empty projectForm.modules}">
									<tr><td colspan="4"><qp:message code="inf.sys.0013"/></td></tr>
								</c:if>
							</table>
						</div>
					</div>
				</div>
			</c:if>
		</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>