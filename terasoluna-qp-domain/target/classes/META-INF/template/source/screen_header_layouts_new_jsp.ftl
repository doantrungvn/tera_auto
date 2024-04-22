<style type="text/css">
.label-menu-corner {
	font-size: 10px;
	line-height: 12px;
	position: absolute;
	left: 24px;
	top: 8px;
}

.dropdown-alerts {
	min-width: 250px;
}
</style>
<!-- Start header info -->
<div class="com-header-info-panel">

	<!-- Start logo panel -->
	<div class="com-header-logo-panel" style="padding-top : 10px;">
		<a href="${pageContext}"><img src="${requestScope}/resources/media/images/logo_header_01.png" style="border: 0px; height: 25px;" /> </a>
	</div>
	<!-- End logo panel -->
	
	<!-- Start header button -->
	<div class="com-header-button-panel" style="text-align: right;">
		
		<!-- start user information -->
		<#if currentProject?has_content>
			<qp:message code="sc.tqp.0017" />:
				<a class="qp-link-popup" href="${pageContext}/project/view?projectId=${dollar}{f:h(${currentProject.projectId})}"><qp:formatText value="${dollar}{${currentProject.projectName} }" /></a>
			<a class="qp-link-popup" href="${pageContext}/project/view?projectId=${dollar}{f:h(${currentProject.projectId})}"><qp:formatText value="${dollar}{${currentProject.projectName} }" /></a>
			
			(<a href="${dollar}${pageContext}/home"><qp:message code="sc.tqp.0018" /></a>)
		</#if>
		<#if !currentProject?has_content && accountProfile.currentProjectId?has_content>
			<a href="${pageContext}/home"><qp:message code="sc.homepage.0005" /></a>
		</#if>
		<!-- end user information -->
		
	</div>
	<!-- End header button -->
</div>
<!-- End header info -->

<!-- Start Menu panel -->
${menuContentOfHome}