<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="value" rtexprvalue="true" required="true"%>
<span style="white-space:pre-wrap"><c:out value="${value}" escapeXml="true"></c:out></span>