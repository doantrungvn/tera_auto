<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="value" rtexprvalue="true" required="true"%>
<%@ attribute name="type" rtexprvalue="false" required="false"%>

<c:choose>
	<c:when test="${empty type}">
		<c:if test="${value}">
			<div align="center">
				<span class="glyphicon glyphicon-ok"></span>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${value}">
			<c:out value="True"></c:out>
		</c:if>
		<c:if test="${!value}">
			<c:out value="False"></c:out>
		</c:if>
	</c:otherwise>
</c:choose>