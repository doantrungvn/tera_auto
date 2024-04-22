<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=messagedesign"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/messagedesign/messagedesign.js"></script>
	<script type="text/javascript">
		function validate() {
			// get value of message string before modify
			var result = true;
			var mess = "";
			var arrBefore = [];
			var message;
			var isBoolean = "true";
			// Iterator
			<c:forEach items="${messageDesignForm.multipleMessageDesignForm}" var="item">
				message = {
					messageContent : '${item.messageString}'
				};
				// Adding array
				arrBefore.push(message);
			</c:forEach>

			// get value of message string after modify
				var arrAfter = [];
				$("#tableResult").children('tbody').find("input[name$='messageString']").each(function (index){
					arrAfter[index] = $(this).val();
				});
				
			// get value of message level
			var temp = $("#messageLevel").val();	
			if(temp == 0){
				// count and define "{i}" before and after => check
				for (j = 0; j < arrBefore.length; j++) {
					var tempBf = arrBefore[j].messageContent;
					var temAt  = arrAfter[j];
					var sBefore = getRule(tempBf);
					var sAfter = getRule(temAt);
					if(sBefore != undefined && sBefore != "" && sAfter != undefined && sAfter != "" && (sBefore != sAfter)){
						result = false;
					}
				}
				if(result){
					return true;
				}else{
					//display message
					alert(dbMsgSource['err.messagedesign.0003']);
					return false;
				}
			}
		}
	</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.messagedesign"></qp:message></span></li>
		 <li><span><qp:message code="sc.messagedesign.0022"/></span></li>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="messagedesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/messagedesign/search"><qp:message code="sc.messagedesign.0020" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="messageDesignForm" action="${pageContext.request.contextPath}/messagedesign/modifyByCode">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${notExistFlg ne 1 }">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.messagedesign.0007" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tblCommon">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tr>
								<th><qp:message code="sc.message.0003" /></th>
								<td><qp:formatText value="${messageDesignForm.messageCode}"/>
								<form:hidden path="messageCode" />
								</td>
								<th><qp:message code="sc.message.0004" /></th>
								<td >
									<form:hidden path="moduleName"/><qp:formatText value="${messageDesignForm.moduleName}" />
									<form:hidden path="moduleId" value="${messageDesignForm.moduleId }"/>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.messagedesign.0013" /></th>
								<td>
									<c:choose>
										<c:when test = "${CL_MESSAGE_LEVEL.get(messageDesignForm.messageLevel.toString()) != 'sc.databasedesign.0092'}">
											<form:select cssClass="form-control qp-input-select" path="messageLevel">
												<form:option value="0"><qp:message code="sc.tqp.0011" /></form:option>
												<form:option value="1"><qp:message code="sc.screendesign.0322" /></form:option>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:hidden path="messageLevel"/><qp:message code="${CL_MESSAGE_LEVEL.get(messageDesignForm.messageLevel.toString())}" /> 
										</c:otherwise>
									</c:choose>
								</td>
								<th><qp:message code="sc.message.0006" /></th>
								<td>
									<c:choose>
										<c:when test = "${CL_MESSAGE_TYPE.get(messageDesignForm.messageType.toString()) == 'cl.sys.0020' }">
											<form:hidden path="messageType"/><qp:message code="${CL_MESSAGE_TYPE.get(messageDesignForm.messageType)}" /> 
										</c:when>
										<c:when test = "${CL_MESSAGE_TYPE.get(messageDesignForm.messageType.toString()) == 'cl.sys.0019' }">
											<form:hidden path="messageType"/><qp:message code="${CL_MESSAGE_TYPE.get(messageDesignForm.messageType)}" /> 
										</c:when>
										<c:when test = "${CL_MESSAGE_TYPE.get(messageDesignForm.messageType.toString()) == 'cl.sys.0016' }">
											<form:hidden path="messageType"/><qp:message code="${CL_MESSAGE_TYPE.get(messageDesignForm.messageType)}" /> 
										</c:when>
										<c:otherwise>
											<form:select cssClass="form-control qp-input-select" path="messageType">
												<form:option value="wrn"><qp:message code="${CL_MESSAGE_TYPE.get('wrn')}"/></form:option>
												<form:option value="inf"><qp:message code="${CL_MESSAGE_TYPE.get('inf')}"/></form:option>
											</form:select>
										</c:otherwise>
									</c:choose>
								 </td>
							</tr>
							<tr>
								<th><qp:message code="sc.sys.0028" /></th>
								<td ><form:textarea path="remark" value="${messageDesignForm.remark}" cssClass="form-control qp-input-text"/></td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.message.0007" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-list" id="tableResult">
							<colgroup>
								<col/>
								<col width="75%" />
								<col width="20%" />
							</colgroup>
							<thead>
								<tr>
									<th style="text-align: center"><qp:message code="sc.sys.0004" /></th>
									<th style="text-align: center"><qp:message code="sc.messagedesign.0009" /></th>
									<th style="text-align: center"><qp:message code="sc.languagedesign.0002" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${messageDesignForm.multipleMessageDesignForm}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex"><qp:formatInteger value="${status.count}" />
										</td>
										<td  style="text-align: left">
											<form:hidden path="multipleMessageDesignForm[${status.index}].messageCode"/>
											<form:input path="multipleMessageDesignForm[${status.index}].messageString" cssClass="form-control qp-input-text" maxlength="255" />
											<form:hidden path="multipleMessageDesignForm[${status.index}].updatedDate" />
										</td>
										<td  style="text-align: left">
											<form:hidden path="multipleMessageDesignForm[${status.index}].languageCode" />
											<form:hidden path="multipleMessageDesignForm[${status.index}].languageName" />
											<form:hidden path="multipleMessageDesignForm[${status.index}].messageDesignId" />
											<form:hidden path="multipleMessageDesignForm[${status.index}].languageId" />
											<qp:formatText  value="${item.languageName}" />
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="messagedesignModify">
						<button type="button" onclick="validate" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
						<form:hidden path="updatedDate" />
						<form:hidden path="updatedBy" />
						<form:hidden path="projectId"/>
					</qp:authorization>
				</div>
			</c:if>
			<form:hidden path="screenType" value="1" />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>