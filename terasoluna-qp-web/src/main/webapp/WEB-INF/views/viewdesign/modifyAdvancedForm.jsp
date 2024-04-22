<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.viewdesign"></qp:message></span></li>
         <li><span><qp:message code="sc.viewdesign.0008"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="viewdesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/viewdesign/search"><qp:message code='sc.viewdesign.0003' /></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/advancedsql.js"></script>
		<!-- Adding sql editor -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/codemirror.css" />
        <script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/codemirror.js"></script>
        <script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/mybatis.js"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/show-hint.css" />
        <script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/show-hint.js"></script>
        <script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/mybatis-hint.js"></script>
        <script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/dialog.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/searchcursor.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/search.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/dialog.css"/>
        <script type="text/javascript">
			$.qp.advancedsql.init();
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<form:form method="post" action="${pageContext.request.contextPath}/viewdesign/modifyAdvanced" modelAttribute="viewDesignAdvancedDesignForm">
		<jsp:include page="advancedEditTemplate.jsp"></jsp:include>
		<div class="qp-div-action">
			<qp:authorization permission="sqldesignModify">
				<button type="submit" 
						class="btn qp-button qp-dialog-confirm" 
						data-confirm-bcallback="$.qp.common.formSubmitCallback"
						data-confirm-pcallback="$.qp.advancedsql.validateBeforeSubmit">
						<c:if test="${designForm.sqlDesignForm.isConversion }"><qp:message code="sc.sqldesign.0044"></qp:message></c:if>
						<c:if test="${not designForm.sqlDesignForm.isConversion }"><qp:message code="sc.sys.0031"></qp:message></c:if>
				</button>
			</qp:authorization>
		</div>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>