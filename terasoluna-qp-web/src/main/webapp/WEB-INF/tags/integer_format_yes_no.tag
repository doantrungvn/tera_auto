<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="value" rtexprvalue="true" required="true"%>
<%@ attribute name="yesValue" rtexprvalue="true" required="true"%>
<%@ attribute name="noValue" rtexprvalue="true" required="false"%>
<%@ attribute name="type" rtexprvalue="false" required="false"%>

<c:choose>
	<c:when test="${empty type}">
		<c:if test="${value eq yesValue}">
			<div align="center">
				<span class="glyphicon glyphicon-ok"></span>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${value eq yesValue}">
				<c:out value="Yes"></c:out>
			</c:when>
			<c:when test="${value eq noValue}">
				<c:out value="No"></c:out>
			</c:when>
			<c:otherwise>
				<c:out value="No"></c:out>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
