<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.sqldesign"></qp:message></span></li>
         <li><span><qp:message code='sc.sqldesign.0012'></qp:message></span></li>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/sqldesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=autocomplete_sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/generaSql.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript">
			var isSqlPage = true;
			$.qp.sqlbuilder.init();
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="sqldesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/sqldesign/search"><qp:message code='sc.sqldesign.0014'></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/sqldesign/register" modelAttribute="sqlDesignDesignForm">
			<jsp:include page="editTemplate.jsp"></jsp:include>
			<div class="qp-div-action">
				<qp:authorization permission="sqldesignRegister">
					<button type="submit" 
							class="btn qp-button qp-dialog-confirm" 
							data-confirm-bcallback="$.qp.common.formSubmitCallback"
							data-confirm-pcallback="$.qp.sqlbuilder.validateBeforeSubmit">
							<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>