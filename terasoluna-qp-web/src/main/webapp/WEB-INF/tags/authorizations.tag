<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ attribute name="permissions" required="true"%>
<%@ attribute name="isDisplay" required="false"%>
<%@ attribute name="displayValue" required="false"%>

<jsp:doBody var="body" />
<c:set var="hasPermission">false</c:set>
<c:if test="${not empty permissions}">
	<c:forTokens items="${permissions}" delims="," var="item">   	
		<sec:authorize access="hasRole('${fn:trim(item)}')">
			<c:set var="hasPermission">true</c:set>	
		</sec:authorize>
	</c:forTokens>
	<c:if test="${hasPermission == true}">
		<jsp:doBody></jsp:doBody>
	</c:if>	
</c:if>
<%-- <sec:authorize access="hasRole('${permission}')">
	<c:out value="${body }" escapeXml="false"></c:out>
</sec:authorize>

<sec:authorize access="!hasRole('${permission}')">
	<c:if test="${isDisplay== null ? false : isDisplay}">
		<qp:formatText value="${displayValue}"></qp:formatText>
	</c:if>
</sec:authorize> --%>