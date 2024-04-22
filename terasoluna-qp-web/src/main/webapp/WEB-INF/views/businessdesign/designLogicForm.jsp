<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=blogiccomponent_businesslogicdesign_decisiontable_sys"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jsPlumb/jsPlumb-2.1.1.js"></script>

		<!-- register -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/common.js"></script>
		<!-- formula builder -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/formulaBuilder.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialBlogicDesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialTable.js"></script>
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
		<script type="text/javascript">
			var CL_QP_DATATYPE_NOT_ENTITY = {};//CL_QP_Datatype
			<c:forEach items="${CL_BD_DATATYPE_NOT_ENTITY}" var="item">
			CL_QP_DATATYPE_NOT_ENTITY['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_QP_OPERATORTYPE = {};
			<c:forEach items="${CL_QP_OPERATORTYPE}" var="item">
			CL_QP_OPERATORTYPE['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_SUPPORT_OPTION_VALUE_FLAG = {};
			<c:forEach items="${CL_SUPPORT_OPTION_VALUE_FLAG}" var="item">
			CL_SUPPORT_OPTION_VALUE_FLAG['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_BD_DATATYPE = {}//CL_QP_Datatype
			<c:forEach items="${CL_BD_DATATYPE}" var="item">
			CL_BD_DATATYPE['${item.key}'] = '${item.value}';
			</c:forEach>

			var sequenceLogicId = '${sequenceLogicId == null || sequenceLogicId.length() == 0 ? "":sequenceLogicId}';
			var allInstanceConnect = [];
			var arrComponent = new Array();
			var isOnlyView = ${isOnlyView == null ? false : isOnlyView};
			var moduleIdOfBD = "";
			var moduleIdAutocompleteOfBD = "";
			$(document).ready(function() {
				arrConnectionComponent = new Array();
				$("input[name='sequenceLogicId']").val(sequenceLogicId);
				initialComponentForNestedLogic(sequenceLogicId,isOnlyView);
				var componentList = $("#srcgenElements");
				hideUnusedComponentOfNestedLogic(componentList);
				loadComponentByFunctiontype($(document).find("input[name='moduleType']").val());
				$("div.qp-header-main").hide();
				$("#designArea").attr("prefixId", sequenceLogicId);
				loadJsPlumb(isOnlyView);
				if(isOnlyView){
					$("#designArea").find("div.clearSelection").hide();
				}
				initGroupNode();
			});
		</script>

		<script id="tbl-component-node-template" type="text/x-jquery-tmpl">
			<div class="execution-class \${cssClass}" data-toggle="tooltip" data-placement="right" componenttype = "\${componentType}" ondblclick="\${actionPath}" style="position: absolute; left: \${xCoordinates}px; top: \${yCoordinates}px;" add="off"
				title="\${remark}" id="\${sequenceLogicId}" >
				<img src="\${imagePath}" class="qp-bdesign-node-image"/>
				\${prefixLabel}<span class="component-name">\${sequenceLogicName}</span>
				<div class="ep"></div>
				<input type="hidden" name="componentElement" value="\${strData}">
			</div>
		</script>
		<script id="tbl-component-if-node-template" type="text/x-jquery-tmpl">
			<div class="execution-class \${cssClass}" data-toggle="tooltip" data-placement="right" componenttype = "\${componentType}" ondblclick="\${actionPath}" style="position: absolute; left: \${xCoordinates}px; top: \${yCoordinates}px;" add="off"
				title="\${remark}" id="\${sequenceLogicId}" id-endif="\${relatedSequenceLogicId}">
				<img src="\${imagePath}" class="qp-bdesign-node-image"/>
				\${prefixLabel}<span class="component-name">\${sequenceLogicName}</span>
				<div class="ep"></div>
				<input type="hidden" name="componentElement" value="\${strData}">
			</div>
		</script>
		<script id="tbl-component-nestedlogicnode-template" type="text/x-jquery-tmpl">
			<div class="execution-class \${cssClass}" data-toggle="tooltip" data-placement="right" componenttype = "\${componentType}" ondblclick="\${actionPath}" style="position: absolute; left: \${xCoordinates}px; top: \${yCoordinates}px;" add="off"
				title="\${remark}" id="\${sequenceLogicId}" >
				<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/designBlogic" style="display: none;"></a>
				<div style="border: 2px solid orange; padding: 3px; border-radius: 8px;">
					<img src="\${imagePath}" class="qp-bdesign-node-image"/>
					\${prefixLabel}<span class="component-name">\${sequenceLogicName}</span>
				</div>
				<div class="ep"></div>
				<input type="hidden" name="componentElement" value="\${strData}">
			</div>
		</script>
		<script id="tbl-component-nested-node-template" type="text/x-jquery-tmpl">
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<!-- Begin include batch -->
		<jsp:include page="component/readFileComponent.jsp" />
		<jsp:include page="component/exportFileComponent.jsp" />
		<jsp:include page="component/fileOperationComponent.jsp" />
		<jsp:include page="component/advanceComponent.jsp" />
		<jsp:include page="component/transactionComponent.jsp" />
		<!-- End include batch -->

        <!-- Begin include download component -->
        <jsp:include page="component/downloadFileComponent.jsp" />
        <!-- End include download component -->

        <!-- Begin include email component -->
        <jsp:include page="component/emailComponent.jsp" />
        <!-- End include email component -->

        <!-- Begin include log component -->
        <jsp:include page="component/logComponent.jsp" />
        <!-- End include log component -->

        <!-- Begin include utility component -->
        <jsp:include page="component/utilityComponent.jsp" />
        <!-- End include utility component -->
		
		<!-- Begin include common component -->
		<jsp:include page="component/commonComponent.jsp" />
		<!-- End include common component -->
		
		<!-- Begin include assign component -->
		<jsp:include page="component/decisionComponent.jsp" />
		<!-- End include assign component -->
		
		<!-- Begin include assign component -->
		<jsp:include page="component/assignComponent.jsp" />
		<!-- End include assign component -->
		
		<!-- Begin include execution component -->
		<jsp:include page="component/executionComponent.jsp" />
		<!-- End include execution component -->
		
		<!-- Begin include feedback component -->
		<jsp:include page="component/feedbackComponent.jsp" />
		<!-- End include feedback component -->
		
		<!-- Begin include feedback component -->
		<jsp:include page="component/navigatorComponent.jsp" />
		<!-- End include feedback component -->

		<!-- Begin include loop component -->
		<jsp:include page="component/loopComponent.jsp" />
		<!-- End include loop component -->
		
		<!-- Begin include if component -->
		<jsp:include page="component/ifComponent.jsp" />
		<!-- End include if component -->
		
		<!-- Begin include validation check component -->
		<jsp:include page="component/validationCheckDetail.jsp" />
		<!-- End include if component -->
		
		<!-- Begin include business check component -->
		<jsp:include page="component/businessCheckComponent.jsp" />
		<!-- End include business check component -->
		
		<!-- Begin include business check component -->
		<jsp:include page="component/formulaBuilder.jsp" />
		<!-- End include business check component -->
		
		<!-- Begin include connection setting -->
		<jsp:include page="component/connectionSetting.jsp" />
		<!-- End include business check component -->
		
		<!-- Begin include function list -->
		<jsp:include page="component/functionlist.jsp" />
		<!-- End include include function list -->
        
        <!-- Begin include exception component -->
        <jsp:include page="component/exceptionComponent.jsp" />
        <!-- End include exception component -->
        
		<form:form method="post" enctype="multipart/form-data" modelAttribute="businessDesignForm">
			<form:hidden path="jsonInputBean"/>
			<form:hidden path="jsonOutputBean"/>
			<form:hidden path="jsonObjectDefinition"/>
			<form:hidden path="businessLogicId"/>
			<form:hidden path="businessLogicCode"/>
			<form:hidden path="blogicType"/>
			<form:hidden path="moduleType"/>
			<input type="hidden" name="sequenceLogicId" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"> <qp:message code="sc.businesslogicdesign.0173" /> </span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl-nestedlocic-infor">
						<colgroup>
							<col width="20%" />
							<col width="80%" />
						</colgroup>
						<tr>
							<th><qp:message code="sc.businesslogicdesign.0063" /></th>
							<td><input type="text" class="form-control qp-input-text" name="label" maxlength="200"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.businesslogicdesign.0064" /></th>
							<td><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
						</tr>
					</table>
					<br />
					<table class="table table-bordered qp-table-list" id="allDragDropContent">
						<c:if test="${isOnlyView}">
							<colgroup>
								<col width="100%" />
							</colgroup>
							<tr>
								<td style="vertical-align: top; height: 600px; width: 100%; overflow: auto;">
									<div class=" design-area" id="designArea">
										<div class="clearSelection">
											<a class="com-link-popup clear-all" href="#"><qp:message code="sc.businesslogicdesign.0031" /></a>&nbsp;&nbsp; <a class="com-link-popup select-all" href="#"><qp:message code="sc.businesslogicdesign.0032" /></a> <label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" checked="checked" class="qp-input-radio qp-input-radio-margin" value="off" add="off" onchange="onChangeDisableMultiSelect(this)" /> <qp:message code="sc.businesslogicdesign.0033" />&nbsp;&nbsp; </label> <label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" class="qp-input-radio qp-input-radio-margin" value="on" /> <qp:message code="sc.businesslogicdesign.0034" /></label>
										</div>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${!isOnlyView}">
							<colgroup>
								<col width="130px" />
								<col width="100%" />
							</colgroup>
							<tr>
								<td valign="top" id="srcgenElements" style="vertical-align: top; padding: 0px 0px;">
									<!-- Begin include componentList -->
									<jsp:include page="componentList.jsp" />
									<!-- End include componentList -->
								</td>
								<td style="vertical-align: top; height: 600px; width: 100%; overflow: auto;">
									<div class="design-area" id="designArea">
										<div class="clearSelection">
											<a class="com-link-popup clear-all" href="#"><qp:message code="sc.businesslogicdesign.0031" /></a>&nbsp;&nbsp; <a class="com-link-popup select-all" href="#"><qp:message code="sc.businesslogicdesign.0032" /></a> <label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" checked="checked" class="qp-input-radio qp-input-radio-margin" value="off" add="off"/> <qp:message code="sc.businesslogicdesign.0033" />&nbsp;&nbsp; </label> <label class="qp-output-text" style="cursor: pointer;"><input type="radio" name="controlMultiSelect" class="qp-input-radio qp-input-radio-margin" value="on" /> <qp:message code="sc.businesslogicdesign.0034" /></label>
										</div>
									</div>
								</td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				
				<button type="button" class="btn qp-button-client " onclick="deleteNestedLogicModal(this)">
					<qp:message code="sc.sys.0008"></qp:message>
				</button>
				<button type="button" class="btn qp-button-client " onclick="detachNestedLogicModal(this)">
					<qp:message code="sc.businesslogicdesign.0174" />
				</button>
				<button type="button" class="btn qp-button-client " onclick="saveModalNestedLogicSetting(this)">
					<qp:message code="sc.sys.0031"></qp:message>
				</button>
			</div>
		</form:form>
		<!-- Begin include message parameter component -->
		<jsp:include page="component/messageParameterBuilder.jsp" />
		<jsp:include page="component/messageValidateParameterBuilder.jsp" />
		<!-- End include message parameter component -->
	</tiles:putAttribute>
</tiles:insertDefinition>