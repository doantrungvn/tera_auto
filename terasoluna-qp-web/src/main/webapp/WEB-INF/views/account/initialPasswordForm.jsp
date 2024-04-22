<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.account"></qp:message></span></li>
		<li><span><qp:message code="sc.account.0020"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/account/search"><qp:message code="sc.account.0008"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<div class="panel panel-default qp-div-search-condition">
			<div class="panel-heading">
				<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
				<span class="qp-heading-text"><qp:message code="sc.account.0001"></qp:message></span>
			</div>
			<div class="panel-body">
				<p align="center"><qp:message code="sc.account.0020"/><qp:message code="sc.sys.0037"/><span style="font-weight: bold;color: red;font-size: 20px;"> ${initialPassword}</span></p>
				<p align="center" style="font-weight: bold;color: blue"><qp:message code="sc.account.0021"/></p>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>