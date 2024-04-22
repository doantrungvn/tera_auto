<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ attribute name="form" required="true"%>
<%@ attribute name="colName" required="true"%>
<%@ attribute name="colCode" required="true"%>
<c:set var="up" value="${colName}: ASC" />
<c:set var="down" value="${colName}: DESC" />
<div class="form-inline" onclick="$.qp.sortDataByColumn('${form}','${colName}')" style="cursor: pointer;">
	<label style="margin-bottom: 0px;cursor: pointer;"><qp:message code="${colCode}"></qp:message></label>
	<c:if test="${not empty page.sort}">
		<c:set var="sortBy" value="${page.sort}" />
		<c:if test="${sortBy eq up}">
			<span class="glyphicon glyphicon-sort-by-order qp-icon-sort"></span>
			<input type="hidden" value="${colName},DESC" id="fcomHeaderSort${colName}" />
		</c:if>
		<c:if test="${sortBy eq down}">
			<span class="glyphicon glyphicon-sort-by-order-alt qp-icon-sort"></span>
			<input type="hidden" value="${colName},ASC" id="fcomHeaderSort${colName}" />
		</c:if>
		<c:if test="${sortBy ne up and sortBy ne down}">
			<input type="hidden" value="${colName},DESC" id="fcomHeaderSort${colName}" />
		</c:if>
	</c:if>
	<c:if test="${empty page.sort}">
		<input type="hidden" value="${colName},DESC" id="fcomHeaderSort${colName}" />
	</c:if>
</div>