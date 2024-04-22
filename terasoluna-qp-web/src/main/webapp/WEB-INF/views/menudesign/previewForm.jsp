<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.menudesign.0007" />
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
	<jsp:include page="../styledesign/include/style.jsp" />

	</tiles:putAttribute>
	<tiles:putAttribute name="body">
				${menuContent}
	</tiles:putAttribute>
</tiles:insertDefinition>