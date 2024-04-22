<%@ page contentType="text/javascript; charset=UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set value="en_US" var="localeName"/><c:if test="${not empty sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}"><c:set value="${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}" var="localeName"/></c:if>
<c:set value="${moduleName}_${localeName}_dbMesssageSource" var="dbMessage" />
<c:set value="${fn:length(applicationScope[dbMessage]) - 1}" var="dbLength" />
$.namespace("dbMsgSource");
dbMsgSource =$.extend(dbMsgSource || {},{
<c:forEach var="msg" begin="0" end="${dbLength < 0 ? 0 : dbLength}" varStatus="status">
	<c:if test="${status.index < dbLength}">"${applicationScope[dbMessage][msg].key}":"${applicationScope[dbMessage][msg].value}",</c:if>
	<c:if test="${status.index == dbLength}">"${applicationScope[dbMessage][msg].key}":"${applicationScope[dbMessage][msg].value}"</c:if>
</c:forEach>
});
