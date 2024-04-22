<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=blogiccomponent_businesslogicdesign_decisiontable_sys"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jsPlumb/jsPlumb-2.1.1.js"></script>

		<!-- modify -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/common.js"></script>
		<!-- formula builder -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/formulaBuilder.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialBlogicDesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialModifyBlogic.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processOverview.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/displayComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/validationComponent.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/displayDialog.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/processDialog.js"></script>
		<!-- from decision -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/constant.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/init.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/displayView.js"></script>
		<!-- function -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/functions.js"></script>
		<!-- Define variable in js -->
		<jsp:include page="commonVariable.jsp" />
	</tiles:putAttribute>

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.businesslogicdesign"></qp:message></span></li>
		<li><span><qp:message code="sc.businesslogicdesign.0271" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<qp:authorization permission="businesslogicSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a class="com-link-popup" href="${pageContext.request.contextPath}/businessdesign/search"><qp:message code="sc.businesslogicdesign.0017" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1 }">
			<jsp:include page="componentView.jsp" />
			<jsp:include page="template/rowBatchTemplate.jsp" />
			<form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/businessdesign/modifyConfirm" modelAttribute="businessDesignForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="jsonInputBean" />
				<form:hidden path="jsonOutputBean" />
				<form:hidden path="jsonObjectDefinition" />
				<form:hidden path="jsonConnector" />
				<form:hidden path="jsonComponent" />
				<!-- Block for display information maybe edit data -->
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0018" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tbl-blogicinformation">
							<form:hidden path="blogicType"/>
							<form:hidden path="moduleId" />
							<form:hidden path="moduleIdAutocomplete" />
							<form:hidden path="functionDesignId" />
							<form:hidden path="functionDesignIdAutocomplete" />
							<form:hidden path="moduleType" />
							<form:hidden path="designStatus" />
							<form:hidden path="patternType" />
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tr>
								<th><form:label path="businessLogicName"><qp:message code="sc.businesslogicdesign.0005" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name " path="businessLogicName" maxlength="200" /></td>
								<th><form:label path="businessLogicName"><qp:message code="sc.businesslogicdesign.0006" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
								<td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code " path="businessLogicCode" maxlength="50" onChange="setURLOfWS()"/></td>
							</tr>
							<tr>
							    <form:hidden path="batchType" />
							    <th><qp:message code="sc.businesslogicdesign.0169" /></th>
                                   <td colspan="3"><qp:message code="${CL_BATCH_TYPE.get(businessDesignForm.batchType.toString())}" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0024" /></th>
								<td><qp:formatText value="${businessDesignForm.moduleIdAutocomplete}" /></td>
								<th><qp:message code="sc.functiondesign.0002" /></th>
								<td><qp:formatText value="${businessDesignForm.functionDesignIdAutocomplete}" /></td>
							</tr>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0266" /></th>
								<td><qp:message code="${CL_BD_DESIGN_MODE.get(businessDesignForm.designMode.toString())}" /></td>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0020" /></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(businessDesignForm.designStatus.toString())}" /></td>
							</tr>
							<tr>
								<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
								<td colspan="3"><form:textarea path="remark" type="text" rows="2" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
							</tr>
						</table>
					</div>
				</div>


				<!-- Block for display tab -->

				<div id="tabs">
					<ul class="nav nav-tabs" id="com-menu-sidebar">
						<li id="tabInput"><a id="tabInputBean" href="#tabBusiness-inputbean" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0025" /></a></li>
						<li id="tabObj"><a id="tabObjectDefinition" href="#tabBusiness-objectdefinition" id="tabObjectDefinition" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0027" /></a></li>
						<li id="tabBlogic" class="active"><a id="tabBlogicSetting" href="#tabBusiness-blogicsetting" id="tabBlogicSetting" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0028" /></a></li>
					</ul>

					<div class="tab-content">
						<div id="tabBusiness-blogicsetting" class="tab-pane ${(businessDesignForm.blogicType == 1 && businessDesignForm.customizeFlg) ? '' : 'active'}" style="height: auto;">
							<table class="table table-bordered qp-table-list" id="allDragDropContent">
								<colgroup>
									<col width="130px" />
									<col width="100%" />
								</colgroup>
								<tbody>
									<tr>
										<td valign="top" id="srcgenElements" style="vertical-align: top; padding: 0px 0px;" level="1">
											<!-- Begin include componentList --> 
											<jsp:include page="componentList.jsp" /> 
											<!-- End include componentList -->
										</td>
										<td style="vertical-align: top; height: 600px; width: 100%; overflow: auto;">
											<div class="design-area" id="designArea" style="min-width: 849px">
												<div class="clearSelection">
													<a class="com-link-popup clear-all" href="#"><qp:message code="sc.businesslogicdesign.0031" /></a>&nbsp;&nbsp; <a class="com-link-popup select-all" href="#"><qp:message code="sc.businesslogicdesign.0032" /></a> <label class="qp-output-text" style="cursor: pointer;"> <input type="radio" name="controlMultiSelect" checked="checked" class="qp-input-radio qp-input-radio-margin" value="off" add="off" /> <qp:message code="sc.businesslogicdesign.0033" />&nbsp;&nbsp;
													</label> <label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" class="qp-input-radio qp-input-radio-margin" value="on" /> <qp:message code="sc.businesslogicdesign.0034" /></label> <a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.businesslogicdesign.0170" />' data-html="true" data-toggle="tooltip" data-placement="bottom"></a>
												</div>
												<c:if test="${empty businessDesignForm.lstSequenceLogics}">
													<div class="execution-class terminal bdesign-node-one" id="start" componenttype="1" style="left: 34px; top: 13px;" add="off">
														<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/start.png" class="qp-bdesign-node-image" /> &nbsp;<span><qp:message code="sc.blogiccomponent.0001" /><span class='component-name'></span>
															<div class="ep"></div>
													</div>
												</c:if>
												<c:if test="${not empty businessDesignForm.lstSequenceLogics}">
													<c:forEach var="scrComponent" items="${businessDesignForm.lstSequenceLogics}" varStatus="rowStatus">
														<c:if test="${scrComponent.componentType == 14}">
															<div class="execution-class ${f:h(scrComponent.cssClass)}" id="${f:h(scrComponent.sequenceLogicId)}" componenttype="${scrComponent.componentType}" ondblclick="${scrComponent.actionPath}" style="left: ${scrComponent.xCoordinates}px; top: ${scrComponent.yCoordinates}px;" add="off" data-toggle="tooltip" data-placement="right" title="${f:h(scrComponent.remark)}">
																<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/designBlogic" style="display: none;"></a>
																<div style="border: 2px solid orange; padding: 3px; border-radius: 8px;">
																	<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${scrComponent.imagePath}" class="qp-bdesign-node-image" /> ${f:h(scrComponent.prefixLabel)}<span class="component-name">${f:h(scrComponent.sequenceLogicName)}</span>
																</div>
																<div class="ep"></div>
																<input type="hidden" name="componentElement" value="${f:h(scrComponent.strData)}">
															</div>
														</c:if>
														<c:if test="${scrComponent.componentType != 14}">
															<div class="execution-class ${f:h(scrComponent.cssClass)}" id="${f:h(scrComponent.sequenceLogicId)}" componenttype="${scrComponent.componentType}" ondblclick="${scrComponent.actionPath}" style="left: ${scrComponent.xCoordinates}px; top: ${scrComponent.yCoordinates}px;" add="off" data-toggle="tooltip" data-placement="right" title="${f:h(scrComponent.remark)}" id-endif="${scrComponent.relatedSequenceLogicId != null ? scrComponent.relatedSequenceLogicId : ''}">
																<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${scrComponent.imagePath}" class="qp-bdesign-node-image" /> ${f:h(scrComponent.prefixLabel)}<span class="component-name">${f:h(scrComponent.sequenceLogicName)}</span>
																<div class="ep"></div>
																<input type="hidden" name="componentElement" value="${f:h(scrComponent.strData)}">
															</div>
														</c:if>
													</c:forEach>
												</c:if>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- Begin Input Bean(in.) Tab  -->
						<jsp:include page="inputbean/inputBatchTab.jsp" />
						<!-- End Input Bean(in.) Tab  -->
	
						<!-- Begin object definition(ob.) Tab  -->
						<jsp:include page="objectdefinitionEditTab.jsp" />
						<!-- End object definition(ob.) Tab  -->
					</div>
					<br />
					<div class="qp-div-action">
						<form:hidden path="businessLogicId" />
						<form:hidden path="updatedDate" />
						<form:hidden path="showImpactFlag" />
						<qp:authorization permission="businesslogicModify">
							<div class="form-inline form-group">
								<button type="submit" class="btn qp-button qp-dialog-confirm" name="mode" value="2" onclick="saveData">
									<qp:message code="sc.businesslogicdesign.0044" />
								</button>
							</div>
						</qp:authorization>
					</div>
				</div>
			</form:form>
		</c:if>

		<!-- Begin include message parameter component -->
		<jsp:include page="component/messageParameterBuilder.jsp" />
		<jsp:include page="component/messageValidateParameterBuilder.jsp" />
		<!-- End include message parameter component -->
		<jsp:include page="component/assignScreenItem.jsp" />
	</tiles:putAttribute>
</tiles:insertDefinition>