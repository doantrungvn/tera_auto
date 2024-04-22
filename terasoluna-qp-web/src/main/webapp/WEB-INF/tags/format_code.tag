<%@ tag language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%@ attribute name="code" rtexprvalue="true" required="true" %><c:catch var="exception"><spring:message var="message" code="${code}"></spring:message><c:out value="${fn:replace(fn:toLowerCase(message), ' ', '')}"></c:out></c:catch><c:if test = "${exception != null}"><c:out value="${code}"></c:out></c:if>