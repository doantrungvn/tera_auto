<%@ tag language="java" pageEncoding="utf-8" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--  attribute -->
<%@ attribute name="value" required="true"%>
<%@ attribute name="suffix" required="false"%>

<c:if test="${not empty value}">

	<c:if test="${not empty sessionScope.accountProfile.integerFormat}">
		<fmt:formatNumber type="number" pattern="${sessionScope.accountProfile.integerFormat}" value="${value}" />
	</c:if>
	
	<c:if test="${empty sessionScope.accountProfile.integerFormat}">
		<c:if test="${not empty CL_SYSTEM_SETTING['integerFormat']}">
			<fmt:formatNumber type="number" pattern="${CL_SYSTEM_SETTING['integerFormat']}" value="${value}" />
		</c:if>
		<c:if test="${empty CL_SYSTEM_SETTING['integerFormat']}">
			<fmt:formatNumber type="number" pattern="#,###" value="${value}" />
		</c:if>
	</c:if>
	${suffix}
</c:if>