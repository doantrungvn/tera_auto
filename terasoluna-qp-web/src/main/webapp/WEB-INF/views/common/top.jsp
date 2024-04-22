<!-- 
<sec:authorize ifNotGranted="ROLE_USER">
    <div class="com-message-panel">
		<div class="com-error-message-text">
       		<li><spring:message code="label.tr.common.notLoginMessage" /></li>
       	</div>
    </div>
</sec:authorize>
 -->

<sec:authorize ifAnyGranted="ROLE_USER">
    <form:form action="${pageContext.request.contextPath}/logout" method="POST"
        cssClass="inline">
        <input id="logoutBtn" type="submit" name="logout"
            value="<spring:message code="label.tr.common.logout"/>">
    </form:form>
</sec:authorize>