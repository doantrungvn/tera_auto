<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%@ attribute name="format" required="true"%>
<jsp:doBody var="body" />

<x:parse xml="${fn:trim(body)}" var="output" />
<c:set var="oldValue">
	<x:out select="$output/input/@value" />
</c:set>
<c:set var="newValue" value="222"></c:set>


<c:set var="body"
	value="${fn:replace(fn:trim(body), oldValue, newValue) }"></c:set>
<c:out value="${body }" escapeXml="false"></c:out>



