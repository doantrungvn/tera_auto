<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.sqldesign"></qp:message></span></li>
         <li><span><qp:message code='sc.sqldesign.0013'></qp:message></span></li>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="sqldesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/sqldesign/search"><qp:message code='sc.sqldesign.0014'></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/sqldesign.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/dialog.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=autocomplete_sqldesign"></script>
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
		
		<script type="text/javascript">
			$.qp.advancedsql.init();
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<form:form method="post" action="${pageContext.request.contextPath}/sqldesign/registerAdvanced" modelAttribute="sqlDesignAdvancedDesignForm">
		<jsp:include page="advancedEditTemplate.jsp"></jsp:include>
		<div class="qp-div-action">
			<qp:authorization permission="sqldesignRegister">
				<button type="submit" 
						class="btn qp-button qp-dialog-confirm" 
						data-confirm-bcallback="$.qp.common.formSubmitCallback"
						data-confirm-pcallback="$.qp.advancedsql.validateBeforeSubmit">
						<qp:message code="sc.sys.0031"></qp:message>
				</button>
			</qp:authorization>
		</div>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>