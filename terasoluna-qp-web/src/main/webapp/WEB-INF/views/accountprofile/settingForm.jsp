<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name"> 
		<qp:message code="sc.accountprofile.0011" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			$( document ).ready(function() {
				//init default
				var $radios = $("#settingForm").find("input:radio[name='proxyLevel']");
				if($radios.is(':checked') === false) {
					$radios.filter('[value=0]').prop('checked', true);
				}

				$radios.click(function () {
					displayProxySetting($(this).val());
				});

				displayProxySetting($("#settingForm").find("input[type=radio][name='proxyLevel']:checked").val());
				
				triggerDirectoryType();
			});

			function displayProxySetting(proxyLevel) {
				//none proxy
				if(proxyLevel==0) {
					$("#trHostPort").hide();
					$("#proxySetting").attr("rowspan", 1);
				} else if (proxyLevel == 2) {//manual proxy
					$("#trHostPort").show();
					$("#proxySetting").attr("rowspan", 2);
				}
			}

			function testConnection(option) {

				var url = CONTEXT_PATH + "/accountprofile/systemTestConnection";
				$("#settingForm").attr('action', url);
				$.qp.undoFormatNumericForm("#settingForm");
				if (option == 1) { // Test internet connection
					$('[name="testBingFlag"]').val('false');
					$('[name="testInternetFlag"]').val('true');
				} else {
					$('[name="testBingFlag"]').val('true');
					$('[name="testInternetFlag"]').val('false');
				}
				/* $("#settingForm").submit();  */
			}
			
			function triggerDirectoryType(){
				$("input:radio[name ='batchDirectoryType']").each(function(){
					$(this).click(function(){
						var type = $(this).val();
						$("#batchJobPath").css("display", "none");
						$("#absolutelyLabelDisplay").css("display", "none");
						if(type == "1"){
							$("#batchJobPath").css("display", "block");
							$("#absolutelyLabelDisplay").css("display", "block");
						}
					});
					if($(this).is(':checked')){
				   		$(this).trigger("click");
			   	  	}
				})
			}
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="settingForm" action="${pageContext.request.contextPath}/accountprofile/modifySystemSetting">
			<form:errors path="*" cssClass="alert qp-error" element="div" />
			<form:hidden path="testBingFlag"/>
			<form:hidden path="testInternetFlag"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountprofile.0035" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="30%" />
							<col width="70%" />
						</colgroup>
						<tr>
							<th><form:label path="sessionTimeOut"><qp:message code="sc.accountprofile.0022" /></form:label>
							<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.accountprofile.0040"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a></th>
							<td>
								<form:input path="sessionTimeOut" cssClass="form-control qp-input-smallint" />
							</td>
						</tr>
						<tr>
							<th><form:label path="intervalReload"><qp:message code="sc.accountprofile.0037" /></form:label></th>
							<td>
								<form:input path="intervalReload" cssClass="form-control qp-input-smallint"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr class="qp-box-top qp-box-left qp-box-right ">
							<th rowspan="2" id="proxySetting"><form:label path="proxyLevel"><qp:message code="sc.accountprofile.0023" /></form:label></th>
							<td>
								<label class="radio-inline">
									<form:radiobutton path="proxyLevel" value="0" cssClass="qp-input-checkbox qp-input-checkbox-margin"/>
									<qp:message code="sc.accountprofile.0031"/>
								</label>
								<%-- <label class="radio-inline">
									<form:radiobutton id="proxyLevel2" path="proxyLevel" value="1" cssClass="qp-input-checkbox qp-input-checkbox-margin"/>
									<qp:message code="sc.accountprofile.0032"/>
								</label> --%>
								<label class="radio-inline">
									<form:radiobutton path="proxyLevel" value="2" cssClass="qp-input-checkbox qp-input-checkbox-margin"/>
									<qp:message code="sc.accountprofile.0033"/>
								</label>
							</td>
						</tr>
						<tr id="trHostPort" class="qp-box-left qp-box-right input-hidden">
							<!-- <th></th> -->
							<td>
								<table class="table table-bordered qp-table-form">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th>
											<form:label path="proxyHost"><qp:message code="sc.accountprofile.0024" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
										</th>
										<td>
											<form:input cssClass="form-control qp-input-text" path="proxyHost" maxlength="50"/>
										</td>
										<th>
											<form:label path="proxyPort"><qp:message code="sc.accountprofile.0025" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
										</th>
										<td><form:input cssClass="form-control qp-input-serial" path="proxyPort"/></td>
									</tr>
									<tr>
										<th>
											<form:label path="proxyUser"><qp:message code="sc.accountprofile.0026" /></form:label>
										</th>
										<td>
											<form:input cssClass="form-control qp-input-text" path="proxyUser" maxlength="50"/>
										</td>
										<th>
											<form:label path="proxyPass"><qp:message code="sc.accountprofile.0027" /></form:label>
										</th>
										<td>
											<form:input type="password" cssClass="form-control qp-input-text" path="proxyPass"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr class="qp-box-left qp-box-right ">
							<th><form:label path="urlTestProxy"><qp:message code="sc.accountprofile.0044" /></form:label></th>
							<td><form:input path="urlTestProxy" id="urlTestProxy" class="form-control qp-input-text" /></td>
						</tr>
						
						<tr class="qp-box-left qp-box-right">
							<td colspan="2">
								<div class="qp-div-action">
									<button type="button" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0045" onclick="testConnection(1);"><qp:message code="sc.accountprofile.0053" /></button>
								</div>
							</td>
						</tr>
						
						<tr class="qp-box-left qp-box-right">
							<th><qp:message code="sc.accountprofile.0036" /></th>
							<td>
								<table class="table table-bordered qp-table-form">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th>
											<form:label path="bingClientId"><qp:message code="sc.accountprofile.0029"/></form:label>
										</th>
										<td>
											<form:input cssClass="form-control qp-input-text" path="bingClientId" maxlength="50"/>
										</td>
										<th>
											<form:label path="bingClientSecret"><qp:message code="sc.accountprofile.0030" /></form:label>
										</th>
										<td>
											<form:input type="password" cssClass="form-control qp-input-text" path="bingClientSecret"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr class="qp-box-bottom qp-box-left qp-box-right">
							<td colspan="2">
								<div class="qp-div-action">
									<button type="button" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0045" onclick="testConnection(0);"><qp:message code="sc.accountprofile.0054" /></button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<!-- Panel for batch job settings -->
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountprofile.0048" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
							<col />
						</colgroup>
						<tr>
							<th><form:label path="urlScreenCapture"><qp:message code="sc.accountprofile.0044" /></form:label></th>
							<td>
								<form:input path="urlScreenCapture" id="urlScreenCapture" class="form-control qp-input-text" />
							</td>
							<th><form:label path="maxJobNumber"><qp:message code="sc.accountprofile.0045" /></form:label></th>
							<td>
								<form:input path="maxJobNumber" id="maxJobNumber" class="form-control qp-input-serial" />
							</td>
						</tr>
						<tr>
							<th><form:label path="batchDirectoryType"><qp:message code="sc.accountprofile.0046" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_BATCH_DIRECTORY_TYPE}">
									<label><form:radiobutton path="batchDirectoryType" value="${item.key}" cssClass="qp-input-radio qp-input-radio-margin"/><qp:message code="${CL_BATCH_DIRECTORY_TYPE.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
							<th><form:label path="batchJobPath" id="absolutelyLabelDisplay"><qp:message code="sc.accountprofile.0047" /></form:label></th>
							<td><form:input path="batchJobPath" id="batchJobPath" cssClass="form-control qp-input-text" /></td>
						</tr>
						</table>
				</div>
			</div>
			<!-- End of batch job settings -->
			
			<!-- Panel for other settings -->
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.accountprofile.0051" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="30%" />
							<col width="70%" />
							<col />
						</colgroup>
						<tr>
							<th><form:label path="maxSizeUpload"><qp:message code="sc.accountprofile.0052" /></form:label></th>
							<td>
								<form:input path="maxSizeUpload" cssClass="form-control qp-input-smallint"/>
							</td>
						</tr>
						</table>
				</div>
			</div>
			<!-- End of other settings -->
			<qp:authorization permission="accountprofileModifysystemsetting">
				<div class="qp-div-action">
					<form:hidden path="accountId"/>
					<button type="button" class="btn qp-button qp-dialog-confirm" name="validateProxy" value="false"><qp:message code="sc.sys.0031" /></button>
				</div>
			</qp:authorization>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>