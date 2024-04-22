<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.domaindesign"></qp:message></span></li>
         <li><span><qp:message code="sc.domaindesign.0031"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
			<qp:authorization permission="domaindesignSearch">
				<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
				<a href="${pageContext.request.contextPath}/domaindesign/search"><qp:message code="sc.domaindesign.0000" /></a>
		</qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/initData.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/advanceSetting.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/classification.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=domaindesign_tabledesign_databasedesign_screendesign"></script>
		<script src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/domaindesign.js" type="text/javascript"></script>
		<script type="text/javascript">
		
		var includeLength = "";
		var listOptionKey = [];
		var listObj = [];
		var fmtCode = "${domainDesignForm.fmtCode}";
			<c:forEach items="${domainDesignForm.fmtCodelist}" var="item"> 
				var object = {code:"${item.validationRuleCode}",name:"<qp:message code="${item.validationRuleName}" />",include:"${item.include}",group:"${item.baseTypeGroup}"};
				listObj.push(object);
			</c:forEach>
			
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<%@include file="advanceSetting.jsp" %>
		<form:form method="post" action="${pageContext.request.contextPath}/domaindesign/viewListAffectedChangeDesignForm" modelAttribute="domainDesignForm">
		<%@include file="classification.jsp" %>
			<input type="hidden" name="ftmCodeTemp" value="${domainDesignForm.fmtCode}">
			<%-- <form:hidden path="projectId" value="${sessionScope.CURRENT_PROJECT.projectId}"/> --%>
			<c:if test="${notExistFlg ne 1}">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<script id="tblFmtCode-template" type="text/template">
				<tr>
					<td>
						<form:select path="fmtCode" class="form-control qp-input-select" onchange="changeChecked(this)">
							<form:option value=""><qp:message code="sc.domaindesign.0052" /></form:option>
							<c:forEach var="item" items="${domainDesignForm.fmtCodelist}" varStatus="status">
								<form:option value="${item.validationRuleCode}" include ="${item.include}"><qp:message code="${item.validationRuleName}" /> </form:option>
							</c:forEach>
						</form:select>
					</td>
					<td>
						<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.removeRowJS('tblFmtCode', this);checkAction(this);" href="javascript:void(0)"></a>
					</td>
				</tr>
			</script>
			<form:hidden path="domainId" />
			<form:hidden path="projectId" />
			<form:hidden path="updatedDate"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.domaindesign.0031" /></span>
				</div>

				<div class="panel-body">
					<%-- <div class="table table-bordered qp-table-form"> --%>

						<table class="table table-bordered qp-table-form" id="domainDesignTbl">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tr>
								<th>
									<form:label path="domainName">
										<qp:message code="sc.domaindesign.0001" />&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029" /></span>
									</form:label>
								</th>
								<td>
									<form:input path="domainName" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200"/>
								</td>
								<th>
									<form:label path="domainCode">
										<qp:message code="sc.domaindesign.0002" />&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029" /></span>
									</form:label>
								</th>
								<td>
									<form:input path="domainCode" cssClass="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
								</td>
							</tr>
							<tr>
								<%-- <th><qp:message code="sc.domaindesign.0026" /></th>
								<td>
									<qp:formatText value="${domainDesignForm.projectIdAutocomplete}"/>
									<form:hidden path="projectIdAutocomplete"/>
								</td> --%>
								<th>
									<qp:message code="sc.domaindesign.0003" />&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029" /></span>
								</th>
								<td>
									<div class="td-with-setting">
											<qp:autocomplete selectSqlId="getAllBasetypeAC"
											maxlength="200" name="baseType" 
											value="${domainDesignForm.baseType}" displayValue="${domainDesignForm.baseTypeAutocomplete}" 
											onChangeEvent="changeDataType"
											optionValue="optionValue" optionLabel="optionLabel">
										</qp:autocomplete> 
									</div>
									<span class="glyphicon glyphicon-cog icon-open-dialog-show" title="Advance setting" id="2" data-target="#dialogAutocomplete" onclick="openDialogAutocompleteSetting(this);" ></span>
									<form:hidden path="groupBasetypeId" />
									<form:hidden path="constrainsType"/>
									<form:hidden path="datasourceId"/>
									<form:hidden path="datasourceType"/>
									<form:hidden path="defaultValue"/>
									<form:hidden path="operatorCode"/>
									<form:hidden path="defaultType" />
									<form:hidden path="minVal"/>
									<form:hidden path="maxVal"/>
									<form:hidden path="codelistCodeAutocomplete"/>
									<form:hidden path="codelistDefaultAutocomplete"/>
									<form:hidden path="sqlCodeAutocomplete"/>
									<form:hidden path="userDefineValue"/>
									<form:hidden path="supportOptionFlg"/>
									<form:hidden path="optionLabel"/>
									<form:hidden path="optionValue"/>
									<form:hidden path="optionLabelAutocomplete"/>
									<form:hidden path="optionValueAutocomplete"/>
								</td>
								<th>
									<form:label path="maxLength">
										<qp:message code="sc.domaindesign.0006" />&nbsp;<span id="maxLengthRequired" class="qp-required-field"><qp:message code="sc.sys.0029" /></span>
										<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.domaindesign.0047"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
									</form:label>
								</th>
								<td>
									<form:input path="maxLength" cssClass="form-control qp-input-serial"/>
								</td>
							</tr>
							<tr>
								<th>
									<%-- <form:label path="mandatoryFlg"><qp:message code="sc.domaindesign.0005" /></form:label> --%>
									<%-- <form:label path="minorClassification"><qp:message code="sc.domaindesign.0046" /></form:label> --%>
									<form:label path="minorClassification"><qp:message code="sc.domaindesign.0009" />&nbsp;<span class="qp-required-field" id="fmtCodeRequired"><qp:message code="sc.sys.0029" /></span></form:label>
									
								</th>
								<td>
									<%-- <form:checkbox path="mandatoryFlg" cssClass="qp-input-checkbox qp-input-checkbox-margin" value="1"/> --%>
									<form:hidden path="mandatoryFlg" value="0"/>
									<input type="hidden" id="validationRulesValid" value="">
									<!-- <a class="btn btn-default btn-xs glyphicon glyphicon-plus-sign qp-button-action" data-target="#dialogAutocomplete" onclick="openDialogClassification(this, 0);"></a> -->
									<table class="table table-bordered qp-table-list-none-action" id="tblFmtCode">
										<colgroup>
											<col />
											<col width="35px" />
										</colgroup>
										<tbody>
											<%-- <c:forEach var="itemA" items="${domainDesignForm.fmtCodeByString}" varStatus="statussaaaa">
												<tr>
													<td>
														<form:select id ="selectValidationRule" path="fmtCode" class="form-control qp-input-select" onchange="changeChecked(this)" >
															<form:option value="">
																<qp:message code="sc.domaindesign.0052" />
															</form:option>
															<c:forEach var="item" items="${domainDesignForm.fmtCodelist}" varStatus="status">
																<form:option value="${item.validationRuleCode}" include ="${item.include}"><qp:message code="${item.validationRuleName}" /> </form:option>
															</c:forEach>
														</form:select>
													</td>
													<td>
														<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" id = "actionRemove" onclick="$.qp.removeRowJS('tblFmtCode', this);checkAction(this);" href="javascript:void(0)"></a>
													</td>
												</tr>
											</c:forEach>
											<c:if test="${ empty domainDesignForm.fmtCode}"> --%>
												<tr>
													<td>
														<form:select id = "selectValidationRule" path="fmtCode" class="form-control qp-input-select" onchange="changeChecked(this)" >
															<form:option value="">
																<qp:message code="sc.domaindesign.0052" />
															</form:option>
															<c:forEach var="item" items="${domainDesignForm.fmtCodelist}" varStatus="status">
																<form:option value="${item.validationRuleCode}" include ="${item.include}"><qp:message code="${item.validationRuleName}" /> </form:option>
															</c:forEach>
														</form:select>
													</td>
													<td>
														<!-- <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" id = "actionRemove" onclick="$.qp.removeRowJS('tblFmtCode', this);checkAction(this);" href="javascript:void(0)"></a> -->
													</td>
												</tr>
											<%-- </c:if> --%>
										</tbody>
									</table>
									<div class="qp-add-left">
										<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" id = "actionButton" onclick="$.qp.addRowJSByLink(this);changeOption();" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</div>
								</td>
								<th>
									<form:label path="precision"><qp:message code="sc.domaindesign.0007" /></form:label>
									
								</th>
								<td>
									<form:input path="precision" cssClass="form-control qp-input-serial" />
								</td>
							</tr>
							<tr>
								<th>
									 <form:label path="minorClassification"><qp:message code="sc.domaindesign.0046" /></form:label>
								</th>
								<td>
									<a class="btn btn-default btn-xs glyphicon glyphicon-plus-sign qp-button-action" data-target="#dialogAutocomplete" onclick="openDialogClassification(this, 0);"></a>
									<%-- <table class="table table-bordered qp-table-list-none-action" id="tblFmtCode">
										<colgroup>
											<col />
											<col width="35px" />
										</colgroup>
										<tbody>
											<c:forEach var="itemA" items="${domainDesignForm.fmtCodeByString}" varStatus="statussaaaa">
												<tr>
													<td>
														<form:select id ="selectValidationRule" path="fmtCode" class="form-control qp-input-select" onclick="calculatorFtmCode(this)" onchange="changeChecked(this)" >
															<form:option value="">
																<qp:message code="sc.domaindesign.0052" />
															</form:option>
															<c:forEach var="item" items="${domainDesignForm.fmtCodelist}" varStatus="status">
																<form:option value="${item.validationRuleCode}" include ="${item.include}"><qp:message code="${item.validationRuleName}" /> </form:option>
															</c:forEach>
														</form:select>
													</td>
													<td>
														<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" id = "actionRemove" onclick="$.qp.removeRowJS('tblFmtCode', this);checkAction(this);" href="javascript:void(0)"></a>
													</td>
												</tr>
											</c:forEach>
											<c:if test="${ empty domainDesignForm.fmtCode}">
												<tr>
													<td>
														<form:select id = "selectValidationRule" path="fmtCode" class="form-control qp-input-select" onchange="changeChecked(this)" >
															<form:option value="">
																<qp:message code="sc.domaindesign.0052" />
															</form:option>
															<c:forEach var="item" items="${domainDesignForm.fmtCodelist}" varStatus="status">
																<form:option value="${item.validationRuleCode}" include ="${item.include}"><qp:message code="${item.validationRuleName}" /> </form:option>
															</c:forEach>
														</form:select>
													</td>
													<td>
														<!-- <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" id = "actionRemove" onclick="$.qp.removeRowJS('tblFmtCode', this);checkAction(this);" href="javascript:void(0)"></a> -->
													</td>
												</tr>
											</c:if>
										</tbody>
									</table>
									<div class="qp-add-left">
										<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" id = "actionButton" onclick="$.qp.addRowJSByLink(this);changeOption();" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</div> --%>
								</td>
								<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
								<td><form:textarea Class="form-control qp-input-textarea" path="remark" rows="2"/></td>
							</tr>
							<%-- <tr>
								<th><form:label path="minorClassification"><qp:message code="sc.domaindesign.0046" /></form:label></th>
								<td><a class="btn btn-default btn-xs glyphicon glyphicon-plus-sign qp-button-action" data-target="#dialogAutocomplete" onclick="openDialogClassification(this, 0);"></a></td>
								<th></th>
								<td></td>
							</tr> --%>
						</table>
					<%-- </div> --%>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="domaindesignModify">
					<div class="form-inline form-group">
						<c:if test="${not empty listOfTableDesign}">
							<div class="checkbox"><label><form:checkbox path="showImpactFlag" /><qp:message code="sc.sys.0097" /></label></div>
						</c:if>
						<button type="submit" id="submitModifyDomain" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
					</div>
				</qp:authorization>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.domaindesign.0016" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list-none-action">
						<colgroup>
							<col width="5%"/>
							<col width="50%"/>
							<col width="45%"/>
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.domaindesign.0017" /></th>
								<th><qp:message code="sc.domaindesign.0018" /></th>
							</tr>
						</thead>
						<c:forEach items="${listOfTableDesign }" var="item" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td><qp:formatText value="${item.output01 }" /></td>
								<td><qp:formatText value="${item.output02 }" /></td>
							</tr>
						</c:forEach>
						<c:if test="${empty listOfTableDesign}">
							<tr>
								<td colspan="3"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
			</c:if>
		</form:form>

	</tiles:putAttribute>

</tiles:insertDefinition>