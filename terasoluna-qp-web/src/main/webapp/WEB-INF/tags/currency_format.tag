<%@ tag language="java" pageEncoding="utf-8" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Attribute -->
<%@ attribute name="value" required="true"%>

<c:if test="${not empty value}">

	<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.currencyFormat}">
			<fmt:formatNumber type="number" pattern="${sessionScope.ACCOUNT_PROFILE.currencyFormat}" value="${value}"/>
		</c:if>
		
		<c:if test="${empty sessionScope.ACCOUNT_PROFILE.currencyFormat}">
			<c:if test="${not empty CL_SYSTEM_SETTING['currencyFormat']}">
				<fmt:formatNumber type="number" pattern="${CL_SYSTEM_SETTING['currencyFormat']}" value="${value}" />
			</c:if>
			<c:if test="${empty CL_SYSTEM_SETTING['currencyFormat']}">
				<fmt:formatNumber type="number" pattern="#,###.###" value="${value}" />
			</c:if>
		</c:if>
		<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.currencyCode}">
			${sessionScope.ACCOUNT_PROFILE.currencyCode}
		</c:if>
		<c:if test="${empty sessionScope.ACCOUNT_PROFILE.currencyCode}">
			<c:if test="${not empty CL_SYSTEM_SETTING['currencyCode']}">
				${CL_SYSTEM_SETTING['currencyCode']}
			</c:if>
			<c:if test="${empty CL_SYSTEM_SETTING['currencyCode']}">
				$
			</c:if>
		</c:if>
</c:if>
