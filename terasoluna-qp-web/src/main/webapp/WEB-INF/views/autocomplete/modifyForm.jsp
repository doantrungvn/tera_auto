<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.autocomplete"></qp:message></span></li>
         <li><span><qp:message code='sc.autocomplete.0065'></qp:message></span></li>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/autocompleteDesign/css/autocompleteDesign.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=autocomplete_sqldesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/generaSql.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/javascript/autocompleteDesign.js"></script>
		<script type="text/javascript">
			$.qp.sqlbuilder.init();
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="autocompleteSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/autocomplete/search"><qp:message code="sc.autocomplete.0063"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" role="form" action="${pageContext.request.contextPath}/autocomplete/modifyConfirm" modelAttribute="autocompleteDesignForm">
			<jsp:include page="editTemplate.jsp"></jsp:include>
			<div class="qp-div-action">
				<qp:authorization permission="autocompleteModify">
					<div class="form-inline form-group">
						<button type="submit" 
								class="btn qp-button qp-dialog-confirm" 
								data-confirm-bcallback="$.qp.sqlbuilder.beforeSubmitConversion"
								data-confirm-pcallback="$.qp.sqlbuilder.validateBeforeSubmit"
								name="sqlDesignForm.isConversion"
								value="true"
								messageId="inf.sys.0039"
								><qp:message code="sc.autocomplete.0070"></qp:message>
						</button>
						<div class="checkbox">
								<label> <form:checkbox path="showImpactFlag" />
									<qp:message code="sc.sys.0097" /></label>
						</div>
						<button type="submit" 
								class="btn qp-button qp-dialog-confirm" 
								data-confirm-bcallback="$.qp.common.formSubmitCallback"
								data-confirm-pcallback="$.qp.sqlbuilder.validateBeforeSubmit">
								<qp:message code="sc.sys.0031"></qp:message>
						</button>
					</div>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
