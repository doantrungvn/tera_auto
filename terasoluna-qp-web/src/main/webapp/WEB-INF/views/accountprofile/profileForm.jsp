<tiles:insertDefinition name="layouts-popup">

	<tiles:putAttribute name="header-name"> 
		<qp:message code="sc.tqp.0009" />
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			$( document ).ready(function() {
				// connect setting
				var $radioConn = $("#settingForm").find("input:radio[name='connectionFlg']");
				if($radioConn.is(':checked') === false) {
					$radioConn.filter('[value=0]').prop('checked', true);
				}

				$radioConn.click(function () {
					displayManualSetting($(this).val());
				});
				displayManualSetting($("#settingForm").find("input[type=radio][name='connectionFlg']:checked").val());
				
			});
			function displayManualSetting(connSettingType){
				if(connSettingType == 0) {
					$("#connSettingTb").hide();
				} else if (connSettingType == 1) {
					$("#connSettingTb").show();
				}
			}

			function testConnection() {
				var url = CONTEXT_PATH + "/accountprofile/userTestConnection";
				$("#settingForm").attr('action', url);
				$.qp.undoFormatNumericForm("#settingForm");
				$("#settingForm").submit(); 
			}
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="settingForm" action="${pageContext.request.contextPath}/accountprofile/modifyUserSetting">
			<form:errors path="*" cssClass="alert qp-error" element="div" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.account.0001"/></span>
				</div>
				
				<div class="panel-body">
					<sec:authentication var="user" property="principal" />
					
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col />
						</colgroup>
						<tr>
							<th><qp:message code="sc.account.0002"/></th>
							<td><qp:formatText value="${user.username}"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.account.0003"/></th>
							<td><b><a href="${pageContext.request.contextPath}/accountprofile/modifyPassword"><qp:message code="sc.accountprofile.0010"></qp:message></a></b></td>
						</tr>
						<tr>
							<th><qp:message code="sc.account.0006"/></th>
							<td><qp:formatText value="${user.accountNonLocked}"/></td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountprofile.0012" /></span>
				</div>
				<div class="panel-body">
					
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="30%" />
							<col />
						</colgroup>
						<tr>
							<th><form:label path="defaultLanguage"><qp:message code="sc.accountprofile.0013" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="defaultLanguage" items="${CL_LANGUAGE_LIST}"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountprofile.0034" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col />
							<col width="15%" />
							<col width="15%"/>
							<col />
							<col />
							<col width="15%"/>
						</colgroup>
						<tr>
							<th><form:label path="integerFormat"><qp:message code="sc.accountprofile.0014" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="integerFormat">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="#,###">#,###</form:option>
								</form:select>
							</td>
							<th><form:label path="floatFormat"><qp:message code="sc.accountprofile.0015" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="floatFormat">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="#,###.###">#,###.###</form:option>
								</form:select>
							</td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
						<tr>
							<th><form:label path="dateFormat"><qp:message code="sc.accountprofile.0016" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="dateFormat">
									<form:options items="${CL_DATE_FORMAT_SETTING}"/>
								</form:select>
							</td>
							<th><form:label path="dateTimeFormat"><qp:message code="sc.accountprofile.0017" /></form:label></th>
							<td>
								<form:select path="dateTimeFormat" cssClass="form-control qp-input-select">
									<form:options items="${CL_DATETIME_FORMAT_SETTING}"/>
								</form:select>
							</td>
							<th><form:label path="timeFormat"><qp:message code="sc.accountprofile.0018" /></form:label></th>
							<td>
								<form:select path="timeFormat" cssClass="form-control qp-input-select">
									<form:options items="${CL_TIME_FORMAT_SETTING}"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
						<tr>
							<th><form:label path="currencyFormat"><qp:message code="sc.accountprofile.0019" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="currencyFormat">
									<%-- <form:option value=""></form:option> --%>
									<form:option value="#,###.###">#,###.###</form:option>
								</form:select>
							</td>
							<th><form:label path="currencyCode"><qp:message code="sc.accountprofile.0020" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="currencyCode">
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
							<th><form:label path="currencyCodePosition"><qp:message code="sc.accountprofile.0021" /></form:label></th>
							<td>
								<form:select cssClass="form-control qp-input-select" path="currencyCodePosition">
									<form:option value="p"><qp:message code="cl.accountprofile.0041" /></form:option>
									<form:option value="s"><qp:message code="cl.accountprofile.0042" /></form:option>
								</form:select>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountprofile.0035" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="30%" />
							<col />
						</colgroup>
						<tr>
							<th></th>
							<td>
								<label class="radio-inline">
									<form:radiobutton path="connectionFlg" value="0" cssClass="qp-input-checkbox qp-input-checkbox-margin"/> <qp:message code="sc.tqp.0010" />
								</label>
								<label class="radio-inline">
									<form:radiobutton path="connectionFlg" value="1" cssClass="qp-input-checkbox qp-input-checkbox-margin"/> <qp:message code="sc.tqp.0009" />
								</label>
							</td>
						</tr>
						<tr>
						<td colspan="2" id="connSettingTb">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="30%" />
								<col />
							</colgroup>
							<tr>
								<th><form:label path="sessionTimeOut"><qp:message code="sc.accountprofile.0022" /></form:label>
									<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.accountprofile.0040"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
								</th>
								<td>
									<form:input path="sessionTimeOut" cssClass="form-control qp-input-smallint"/> 
								</td>
							</tr>
							<tr>
								<th><form:label path="intervalReload"><qp:message code="sc.accountprofile.0037" /></form:label></th>
								<td>
									<form:input path="intervalReload" cssClass="form-control qp-input-smallint"/>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td colspan="2">&nbsp;</td> -->
<!-- 							</tr> -->
<!-- 							<tr class="qp-box-top qp-box-left qp-box-right "> -->
								
<%-- 								<th><qp:message code="sc.accountprofile.0036" /></th> --%>
<!-- 								<td> -->
<!-- 									<table class="table table-bordered qp-table-form"> -->
<!-- 										<colgroup> -->
<!-- 											<col width="20%" /> -->
<!-- 											<col width="30%" /> -->
<!-- 											<col width="20%" /> -->
<!-- 											<col width="30%" /> -->
<!-- 										</colgroup> -->
<!-- 										<tr> -->
<!-- 											<th> -->
<%-- 												<form:label path="bingClientId"><qp:message code="sc.accountprofile.0029"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label> --%>
<!-- 											</th> -->
<!-- 											<td> -->
<%-- 												<form:input cssClass="form-control qp-input-text" path="bingClientId" maxlength="50"/> --%>
<!-- 											</td> -->
<!-- 											<th> -->
<%-- 												<form:label path="bingClientSecret"><qp:message code="sc.accountprofile.0030" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label> --%>
<!-- 											</th> -->
<!-- 											<td> -->
<%-- 												<form:input type="password" cssClass="form-control qp-input-text" path="bingClientSecret"/> --%>
<!-- 											</td> -->
<!-- 										</tr> -->
<!-- 									</table> -->
<!-- 								</td> -->
<!-- 							</tr> -->
<!-- 							<tr> -->
<!-- 								<td colspan="2"> -->
<!-- 									<div class="qp-div-action"> -->
<%-- 										<button type="button" class="btn qp-button" onclick="testConnection();"><qp:message code="sc.accountprofile.0028" /></button> --%>
<!-- 									</div> -->
<!-- 								</td> -->
<!-- 							</tr> -->
						</table>
					</td>
					</tr>
					</table>
				</div>
			</div>
			
			<qp:authorization permission="accountprofileModifysystemsetting">
				<div class="qp-div-action">
					<form:hidden path="accountId"/>
					<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</div>
			</qp:authorization>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>