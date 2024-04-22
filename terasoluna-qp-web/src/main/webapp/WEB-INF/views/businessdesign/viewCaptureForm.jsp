<tiles:insertDefinition name="layouts-capture">
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=blogiccomponent_businesslogicdesign_decisiontable_sys"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jsPlumb/jsPlumb-2.1.1.js"></script>

		<!-- view -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/common.js"></script>
		<!-- formula builder -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/formulaBuilder.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialBlogicDesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialViewBlogic.js"></script>
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
	<tiles:putAttribute name="header-link">
		<script type="text/javascript">
			function setFlag(obj) {
				$("#flagStatus").val(false);
			}
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<c:if test="${notExistFlg ne 1}">
			<jsp:include page="componentView.jsp" />
			<c:choose>
				<c:when test="${businessDesignForm.blogicType eq 1}">
					<jsp:include page="template/rowCommonTemplate.jsp" />
				</c:when>
				<c:when test="${businessDesignForm.blogicType eq 2}">
					<jsp:include page="template/rowWSTemplate.jsp" />
				</c:when>
				<c:when test="${businessDesignForm.blogicType eq 3}">
					<jsp:include page="template/rowBatchTemplate.jsp" />
				</c:when>
				<c:otherwise>
					<jsp:include page="template/rowTemplate.jsp" />
				</c:otherwise>
			</c:choose>
			<form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/businessdesign/deleteConfirm" modelAttribute="businessDesignForm">
                <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<form:hidden path="jsonInputBean" />
				<form:hidden path="jsonOutputBean" />
				<form:hidden path="jsonObjectDefinition" />
				<form:hidden path="jsonConnector" />
				<form:hidden path="jsonComponent" />
				<form:hidden path="returnType" />
				<form:hidden path="businessLogicName" />
				<form:hidden path="businessLogicCode" />
				<form:hidden path="remark" />
				<form:hidden path="designStatus" />
				<form:hidden path="moduleId" />
				<form:hidden path="moduleIdAutocomplete" />
				<form:hidden path="moduleType" />
				<form:hidden path="blogicType"/>
				<form:hidden path="projectId" />
                <form:hidden path="moduleCode"/>
			
				<!-- End define dialog for setting -->
	            <div class="design-area" id="designArea">
	            	<div class="clearSelection" style="display: none">
	            		<a class="com-link-popup clear-all" href="#"><qp:message code="sc.businesslogicdesign.0031" /></a>&nbsp;&nbsp;
	            		<a class="com-link-popup select-all" href="#"><qp:message code="sc.businesslogicdesign.0032" /></a>
	            		<label class="qp-output-text" style="cursor: pointer;"> <input type="radio" name="controlMultiSelect" checked="checked"  class="qp-input-radio qp-input-radio-margin" value="off" add="off" onchange="onChangeDisableMultiSelect(this)"/><qp:message code="sc.businesslogicdesign.0033" />&nbsp;&nbsp;</label>
	            		<label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" class="qp-input-radio qp-input-radio-margin" value="on"/><qp:message code="sc.businesslogicdesign.0034" /></label>
	            	</div>
	            	<c:forEach var="scrComponent" items="${businessDesignForm.lstSequenceLogics}" varStatus="rowStatus">
                               <c:if test="${scrComponent.componentType == 14}">
            				<div class="execution-class ${f:h(scrComponent.cssClass)}" id="${f:h(scrComponent.sequenceLogicId)}" componenttype = "${scrComponent.componentType}" ondblclick="${scrComponent.actionPath}" style="left: ${scrComponent.xCoordinates}px; top: ${scrComponent.yCoordinates}px;" add="off"
                               	 data-toggle="tooltip" data-placement="right" title="${f:h(scrComponent.remark)}">
                               	 <a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/designBlogic" style="display: none;"></a>
								<div style="border: 2px solid orange; padding: 3px; border-radius: 8px;">
									<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${scrComponent.imagePath}" class="qp-bdesign-node-image"/>
				            		${f:h(scrComponent.prefixLabel)}<span class="component-name">${f:h(scrComponent.sequenceLogicName)}</span>
								</div>
				            	<div class="ep"></div>
				            	<input type="hidden" name="componentElement" value="${f:h(scrComponent.strData)}">
				            </div>
            			</c:if>
            			<c:if test="${scrComponent.componentType != 14}">
                               <div class="execution-class ${f:h(scrComponent.cssClass)}" id="${f:h(scrComponent.sequenceLogicId)}" componenttype = "${scrComponent.componentType}" ondblclick="${scrComponent.actionPath}" style="left: ${scrComponent.xCoordinates}px; top: ${scrComponent.yCoordinates}px;" add="off"
                               	 data-toggle="tooltip" data-placement="right" title="${f:h(scrComponent.remark)}">
				            	<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${scrComponent.imagePath}" class="qp-bdesign-node-image"/>
				            	${f:h(scrComponent.prefixLabel)}<span class="component-name">${f:h(scrComponent.sequenceLogicName)}</span>
				            	<div class="ep"></div>
				            	<input type="hidden" name="componentElement" value="${f:h(scrComponent.strData)}">
				            </div>
			            </c:if>
                      	</c:forEach>
	            </div>
			</form:form>
		</c:if>
		
		<!-- Begin include message parameter component -->
		<jsp:include page="component/messageParameterBuilder.jsp" />
		<jsp:include page="component/messageValidateParameterBuilder.jsp" />
		<!-- End include message parameter component -->
	</tiles:putAttribute>
</tiles:insertDefinition>