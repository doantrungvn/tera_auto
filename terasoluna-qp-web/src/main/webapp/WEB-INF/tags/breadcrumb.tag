<%@ tag language="java" pageEncoding="utf-8" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--  attribute -->
<%@ attribute name="path" required="false"%>

<ul class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/home"><i class="qp-link-header-icon glyphicon glyphicon-home"></i></a></li>
	<c:forTokens items="${path}" delims="%%" var="item" >
		
	</c:forTokens>
</ul>