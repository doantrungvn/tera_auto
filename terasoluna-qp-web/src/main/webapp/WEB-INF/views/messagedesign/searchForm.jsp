
<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/messagedesign/messagedesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=messagedesign"></script>
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.messagedesign"></qp:message></span></li>
		 <li><span><qp:message code="sc.messagedesign.0020"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
			<qp:authorization permission="languagedesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/languagedesign/register"><qp:message code="sc.languagedesign.0021"/></a>
			</qp:authorization>
			<qp:authorization permission="messagedesignRegister">
				<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
				<a href="${pageContext.request.contextPath}/messagedesign/register"><qp:message code="sc.messagedesign.0021"/></a>
			</qp:authorization>
		</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<script type="text/javascript">
			function validate(arrBefore) {
				var flag = true;
				// get value of message string after modify
					var arrAfter = [];
					$("#tableResult").children('tbody').find("input[name$='MessageString']").each(function (index){
						arrAfter[index] = $(this).val();
					});
				for(var i = 0; i<arrBefore.length; i++) {
					if(arrBefore[i].fromMessageString != arrAfter[i]) {
						flag = false;
					}
				}
				if(!flag){
					 $(".pagination").find("li").click(function (){
					 			return confirm(fcomMsgSource["inf.sys.0028"]);
					 		}); 
					 $(".qp-table-list").find(".qp-link-action").click(function (){
									return confirm(fcomMsgSource["inf.sys.0028"]);
							});
				}
			}
			
			function validatePlaceHolder() {
				// get value of message string before modify
				var result = true;
				var mess = "";
				var arrBefore = [];
				var message;
				var isBoolean = "true";
				// Iterator
				<c:forEach items="${messageDesignSearchForm.modifyMessageDesignForm}" var="item">
					message = {
						messageContentFrom : '${item.fromMessageString}',
						messageContentTo : '${item.toMessageString}'
					};
					// Adding array
					arrBefore.push(message);
				</c:forEach>
		
				// get value of message string after modify
					var arrAfterFrom = [];
					var arrAfterTo = [];
					
					$("#tableResult").children('tbody').find("input[name$='fromMessageString']").each(function (index){
						arrAfterFrom[index] = $(this).val();
					});
					
					$("#tableResult").children('tbody').find("input[name$='toMessageString']").each(function (index){
						arrAfterTo[index] = $(this).val();
					});
					
				// get value of message level
					// count and define "{i}" before and after => check
					for (j = 0; j < arrBefore.length; j++) {
						var tempBfFrom = arrBefore[j].messageContentFrom;
						var temAtFrom  = arrAfterFrom[j];
						var sBeforeFrom = getRule(tempBfFrom);
						var sAfterFrom = getRule(temAtFrom);
						if(sBeforeFrom != sAfterFrom){
							result = false;
						}
						
						var tempBfTo = arrBefore[j].messageContentTo;
						var temAtTo  = arrAfterTo[j];
						if(tempBfTo != null && temAtTo != null) {
							var sBeforeTo = getRule(tempBfTo);
							var sAfterTo = getRule(temAtTo);
							if(sBeforeTo != sAfterTo){
								result = false;
							}
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
		</script>
		<form:form method="post" action="${pageContext.request.contextPath}/messagedesign/search" modelAttribute="messageDesignSearchForm">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
		<qp:ColumnSortHidden/>
		<qp:ColumnSortHidden />
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.sys.0002"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col width="35%" />
						</colgroup>
						<tr>
							<th><form:label path="messageString"><qp:message code="sc.message.0002"/></form:label></th>
							<td><form:input type="text" path="messageString" cssClass="form-control qp-input-text" maxlength="200" /></td>
							<th><form:label path="messageCode"><qp:message code="sc.message.0003"/></form:label></th>
							<td><form:input type="text" path="messageCode" cssClass="form-control qp-input-text" maxlength="50" /></td>
						</tr>
						<tr>
							<th><form:label path="moduleId"><qp:message code="sc.module.0007"/></form:label></th>
							<td><qp:autocomplete name="moduleId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" value="${messageDesignSearchForm.moduleId}" displayValue="${messageDesignSearchForm.moduleIdAutocomplete}" onChangeEvent="changeModuleAC" arg01="${messageDesignSearchForm.projectId}" mustMatch="true" maxlength="200" /></td>
							<th><form:label path="screenId" ><qp:message code="sc.messagedesign.0004"/></form:label></th>
							<td>
								<qp:autocomplete name="screenId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllScreenByScreenNameAndModuleIdForAutocomplete" 
									value="${messageDesignSearchForm.screenId}" displayValue="${messageDesignSearchForm.screenIdAutocomplete}"
									arg01="${messageDesignSearchForm.moduleId}" arg02="20" arg03="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" mustMatch="true" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th><qp:message code="sc.messagedesign.0002"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td>
								<qp:autocomplete name="fromLanguageId" optionValue="output02" optionLabel="optionLabel" value="${messageDesignSearchForm.fromLanguageId}"  selectSqlId="getLanguageDesignForAutocomplete" displayValue="${messageDesignSearchForm.fromLanguageIdAutocomplete}" onChangeEvent="changeFromLanguageAC" arg01="${messageDesignSearchForm.toLanguageId}" arg02="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" mustMatch="true" maxlength="200" />
								<input type="hidden" name="fromLanguageCode" value="${messageDesignSearchForm.fromLanguageCode}"/>
							</td>
							<th><form:label path="toLanguageId"><qp:message code="sc.messagedesign.0003"/></form:label></th>
							<td>
								<qp:autocomplete name="toLanguageId" optionValue="output02" optionLabel="optionLabel" value="${messageDesignSearchForm.toLanguageId}"  selectSqlId="getLanguageDesignForAutocomplete" displayValue="${messageDesignSearchForm.toLanguageIdAutocomplete}" onChangeEvent="changeToLanguageAC" arg01="${messageDesignSearchForm.fromLanguageId}" arg02="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" mustMatch="true" maxlength="200" />
								<input type="hidden" name="toLanguageCode" value="${messageDesignSearchForm.toLanguageCode}"/>
							</td>
						</tr>
						<tr>
							<th><form:label path="businessLogicName"><qp:message code="sc.messagedesign.0005"/></form:label></th>
							<td><form:input type="text" path="businessLogicName" cssClass="form-control qp-input-text" maxlength="200" /></td>
							<th><form:label path="generatedStatus"><qp:message code="sc.messagedesign.0030"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_GENERATED_STATUS}">
									<label><form:checkbox path="generatedStatus" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_GENERATED_STATUS.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
							
						</tr>
						<tr>
							<th><form:label path="messageLevels"><qp:message code="sc.messagedesign.0013"/></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_MESSAGE_LEVEL}">
									<label><form:checkbox path="messageLevels" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_MESSAGE_LEVEL.get(item.key)}"></qp:message></label>
								</c:forEach>
							</td>
							
							<th><form:label path="messageTypes"><qp:message code="sc.message.0006"/></form:label></th>
							<td >
								<c:forEach var="item" items="${CL_MESSAGE_TYPE}">
									<c:if test="${item.key ne 'cl' }">
										<label><form:checkbox path="messageTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_MESSAGE_TYPE.get(item.key)}" /></label>
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="messagedesignSearch">
					<form:hidden path="flagAction" value="true"/>
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		<form:form method="post" action="${pageContext.request.contextPath}/messagedesign/modify" modelAttribute="messageDesignSearchForm">
				<div class="panel panel-default qp-div-search-result">
					<div class="panel-heading">
						<qp:itemPerPage form="messageDesignSearchForm" action="/messagedesign/search"/>
					</div>
					<c:if test="${page != null && page.totalPages != 0}">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered qp-table-list"  id="tableResult">
									<colgroup>
										<col />
										<col width="30%" />
										
										<col width="30%" />
										
										<col width="11%" />
										<col width="11%" />
										<col width="20%" />
										<col width=""/>
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"/></th>
											<th>
												<div>
													<div style="float: left;">
														<qp:columnSort colName="from_message_string" colCode="sc.messagedesign.0017" form="messageDesignSearchForm" />
													</div>
													<div style="float: left;">
														&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
													</div>
												</div>
												<br>
												<div>
													<div style="float: left;">
														<qp:authorization permission="languagedesignView" isDisplay="true" displayValue="${messageDesignSearchForm.fromLanguageCode}">
															<a href="${pageContext.request.contextPath}/languagedesign/view?languageId=${messageDesignSearchForm.fromLanguageId}" class="qp-link-popup"><qp:formatText value="${messageDesignSearchForm.fromLanguageIdAutocomplete}"/></a>
														</qp:authorization>
													</div>
													<div style="float: right;">
														<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="inf.messagedesign.0001"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
													</div>
												</div>
											</th>
											<th>
												<c:choose>
													<c:when test="${not empty messageDesignSearchForm.toLanguageId}">
														<div>
															<div style="float: left;">
																<qp:columnSort colName="to_message_string" colCode="sc.messagedesign.0014" form="messageDesignSearchForm" />
															</div>
															<div style="float: left;">
																&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
															</div>
														</div>
														<br>
														<div>
															<div style="float: left;">
																<qp:authorization permission="languagedesignView" isDisplay="true" displayValue="${messageDesignSearchForm.toLanguageCode}">
																	<a href="${pageContext.request.contextPath}/languagedesign/view?languageId=${messageDesignSearchForm.toLanguageId}" class="qp-link-popup"><qp:formatText value="${messageDesignSearchForm.toLanguageIdAutocomplete}"/> </a>
																</qp:authorization>
															</div>
															<div style="float: right;">
																<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="inf.messagedesign.0001"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<qp:message code="sc.messagedesign.0014" />
													</c:otherwise>
												</c:choose>
											</th>
											<th><qp:columnSort colName="module_name" colCode="sc.module.0007" form="messageDesignSearchForm" /></th>
											<th><qp:message code="sc.messagedesign.0013" /></th>
											<th><qp:columnSort colName="message_code" colCode="sc.message.0003" form="messageDesignSearchForm" /></th>
											<th></th>
										</tr>
									</thead>
									<c:forEach var="item" items="${page.content}" varStatus="status">
										<tr>
											<td><qp:formatInteger value="${(page.number * page.size) + status.count}" /></td>
											<td >
												<input type="hidden" name="modifyMessageDesignForm[${status.index}].fromMessageDesignId" value="${item.fromMessageDesignId}">
												<form:input type="text" onchange="hasModifyValue(this);" path="modifyMessageDesignForm[${status.index}].fromMessageString" value="${item.fromMessageString}" maxlength="255" style="width: 90%; display: inline-block;" class="form-control qp-input-text"/>
												<input type="hidden" name="modifyMessageDesignForm[${status.index}].fromUpdatedDate" value="${item.fromUpdatedDate}">
												<input type="hidden" name="modifyMessageDesignForm[${status.index}].fromLanguageCode" value="${item.fromLanguageCode}">
												<input type="hidden" name="modifyMessageDesignForm[${status.index}].fromGeneratedStatus" value="${item.fromGeneratedStatus}">
												
												<c:choose>
													<c:when test="${item.fromGeneratedStatus == 2}">
														<input type="checkbox" id="tick" class="qp-input-checkbox-margin qp-input-checkbox" name="modifyMessageDesignForm[${status.index}].fromSelected" checked >
													</c:when>
													<c:when test="${item.fromGeneratedStatus != 2}">
														<input type="checkbox" id="tick" class="qp-input-checkbox-margin qp-input-checkbox" name="modifyMessageDesignForm[${status.index}].fromSelected" title="<qp:message code="sc.messagedesign.0025" />">
													</c:when>
												</c:choose>
											</td>
											<td >
											<c:choose>
												<c:when test="${not empty messageDesignSearchForm.toLanguageId}">
													<input type="hidden" name="modifyMessageDesignForm[${status.index}].toMessageDesignId" value="${item.toMessageDesignId}">
													<form:input type="text" onchange="hasModifyValue(this);" path="modifyMessageDesignForm[${status.index}].toMessageString" value="${item.toMessageString}" maxlength="255" style="width: 90%; display: inline-block;" class="form-control qp-input-text"/>
													<input type="hidden" name="modifyMessageDesignForm[${status.index}].toUpdatedDate" value="${item.toUpdatedDate}">
													<input type="hidden" name="modifyMessageDesignForm[${status.index}].toGeneratedStatus" value="${item.toGeneratedStatus}">
													<input type="hidden" name="modifyMessageDesignForm[${status.index}].toLanguageCode" value="${item.toLanguageCode}">
													<c:choose>
														<c:when test="${item.toGeneratedStatus == 2}">
															<input type="checkbox" id="tick" class="qp-input-checkbox-margin qp-input-checkbox" name="modifyMessageDesignForm[${status.index}].toSelected" checked >
														</c:when>
														<c:when test="${item.toGeneratedStatus != 2}">
															<input type="checkbox" id="tick" class="qp-input-checkbox-margin qp-input-checkbox" name="modifyMessageDesignForm[${status.index}].toSelected" title="<qp:message code="sc.messagedesign.0025" />">
														</c:when>
												</c:choose>
												</c:when>
											</c:choose>
											</td>
											<td><qp:formatText value="${item.moduleName}"/></td>
											<td style="text-align: left">
												<qp:message code="${CL_MESSAGE_LEVEL.get(item.messageLevel.toString())}" />
											</td>
											<td>
												<qp:authorization permission="messagedesignView" isDisplay="true" displayValue="${item.messageCode}">
													<a class="qp-link-popup" href="${pageContext.request.contextPath}/messagedesign/view?messageCode=${item.messageCode}"><qp:formatText value="${item.messageCode}"/></a>
												</qp:authorization>
											</td>
											<td>
												<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 && item.moduleStatus eq 1 }">
													<qp:authorization permission="messagedesignModify">
														<a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action" href="${pageContext.request.contextPath}/messagedesign/modifyByCode?messageCode=${item.messageCode}&mode=0" style="margin: auto" data-toggle="tooltip" title="<qp:message code="sc.messagedesign.0022"/>" ></a>
													</qp:authorization>
												</c:if>
											</td>
										</tr>
										<input type="hidden" name="modifyMessageDesignForm[${status.index}].messageCode" value="${item.messageCode}">
									</c:forEach>
								</table>
								<div class="qp-div-action">
									<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
										<qp:authorization permission="messagedesignModify">
											<form:hidden path="flagAction" value="false"/>
											<button type="button" onclick="validatePlaceHolder" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
										</qp:authorization>
									</c:if>
								</div>	
								<div class="qp-div-action">
									<qp:authorization permission="messagedesignSearch">
										<c:url var="customQueryTmpl" value="?">
											<c:param name="messageString" value="${messageDesignSearchForm.messageString}" />
											<c:param name="messageCode" value="${messageDesignSearchForm.messageCode}" />
											<c:param name="projectId" value="${messageDesignSearchForm.projectId}" />
											<%-- <c:param name="moduleId" value="${messageDesignSearchForm.moduleId}" /> --%>
											<c:param name="screenId" value="${messageDesignSearchForm.screenId}" />
											<c:param name="businessLogicName" value="${messageDesignSearchForm.businessLogicName}" />
											<c:param name="fromLanguageId" value="${messageDesignSearchForm.fromLanguageId}" />
											<c:param name="toLanguageId" value="${messageDesignSearchForm.toLanguageId}" />
											<c:param name="fromLanguageCode" value="${messageDesignSearchForm.fromLanguageCode}" />
											<c:param name="toLanguageCode" value="${messageDesignSearchForm.toLanguageCode}" />
											<c:param name="fromLanguageIdAutocomplete" value="${messageDesignSearchForm.fromLanguageIdAutocomplete}" />
											<c:param name="toLanguageIdAutocomplete" value="${messageDesignSearchForm.toLanguageIdAutocomplete}" />
											<c:forEach items="${messageDesignSearchForm.messageLevels}" var="messageLevel">
												<c:param name="messageLevels" value="${messageLevel}" />
											</c:forEach>
											<c:forEach items="${messageDesignSearchForm.messageTypes}" var="messageType">
												<c:param name="messageTypes" value="${messageType}" />
											</c:forEach>
										</c:url>
										<t:pagination page="${page}" outerElementClass="pagination pagination-md"  queryTmpl="page={page}&size={size}" criteriaQuery="${customQueryTmpl}"  maxDisplayCount="5" />
									</qp:authorization>
								</div>
								<input type="hidden" name="checkChanged" id ="checkChanged" value="">
							</div>
						</div>
					</c:if>
				</div>
			</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>