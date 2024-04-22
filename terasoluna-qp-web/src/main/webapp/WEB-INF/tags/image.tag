<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="src" rtexprvalue="true" required="true"%>
<%@ attribute name="alt" rtexprvalue="true" required="false"%>
<%@ attribute name="heigth" rtexprvalue="true" required="false"%>
<%@ attribute name="width" rtexprvalue="true" required="false"%>

<c:set var="heigthImage" value="${heigth }px"></c:set>
<c:set var="widthImage" value="${width }px" />

<c:if test="${empty heigthImage }">
	<c:set var="heigthImage" value="100%"></c:set>
</c:if>
<c:if test="${empty widthImage }">
	<c:set var="widthImage" value="100%"></c:set>
</c:if>
<img src='data:image/jpeg;charset=utf-8;base64,${src }' alt="${alt }" width="${widthImage }" height="${heigthImage }" />
