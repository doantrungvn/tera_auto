<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<!--  attribute -->
<%@ attribute name="name" required="false"%>
<%@ attribute name="optionValue" required="true"%>
<%@ attribute name="optionLabel" required="true"%>
<%@ attribute name="selectSqlId" required="false"%>
<%@ attribute name="emptyLabel" required="false"%>
<%@ attribute name="onSelectEvent" required="false"%>
<%@ attribute name="onChangeEvent" required="false"%>
<%@ attribute name="onRemoveEvent" required="false"%>
<%@ attribute name="mustMatch" required="false"%>
<%@ attribute name="maxlength" required="false"%>
<%@ attribute name="minlength" required="false"%>
<%@ attribute name="value" required="false"%>
<%@ attribute name="displayValue" required="false"%>
<%@ attribute name="cssStyle" required="false"%>
<%@ attribute name="cssClass" required="false"%>
<%@ attribute name="cssInput" required="false"%>
<%@ attribute name="placeHolder" required="false"%>
<%@ attribute name="arg01" required="false"%>
<%@ attribute name="arg02" required="false"%>
<%@ attribute name="arg03" required="false"%>
<%@ attribute name="arg04" required="false"%>
<%@ attribute name="arg05" required="false"%>
<%@ attribute name="arg06" required="false"%>
<%@ attribute name="arg07" required="false"%>
<%@ attribute name="arg08" required="false"%>
<%@ attribute name="arg09" required="false"%>
<%@ attribute name="arg10" required="false"%>
<%@ attribute name="arg11" required="false"%>
<%@ attribute name="arg12" required="false"%>
<%@ attribute name="arg13" required="false"%>
<%@ attribute name="arg14" required="false"%>
<%@ attribute name="arg15" required="false"%>
<%@ attribute name="arg16" required="false"%>
<%@ attribute name="arg17" required="false"%>
<%@ attribute name="arg18" required="false"%>
<%@ attribute name="arg19" required="false"%>
<%@ attribute name="arg20" required="false"%>
<%@ attribute name="sourceType" required="false"%>
<%@ attribute name="sourceCallback" required="false"%>
<c:choose>
 	<c:when test="${placeHolder != null }">
 		<c:set var="placeHolder">
 			<qp:message code='${placeHolder}'></qp:message>
 		</c:set>
 	</c:when>
 	<c:otherwise>
 		<c:set var="placeHolder">
 			<qp:message code='sc.sys.0034'></qp:message>
 		</c:set>
 	</c:otherwise>
</c:choose>
<div class="input-group ${cssClass}" style="${cssStyle}">
	<input type="text" name="${name}Autocomplete" id="${name}AutocompleteId" class="form-control qp-input-autocomplete ${cssInput }" value="${displayValue}"
	 optionValue="${optionValue}" optionLabel="${optionLabel}" selectsqlid="${selectSqlId}" emptyLabel="${emptyLabel}" onSelectEvent ="${onSelectEvent}" onChangeEvent ="${onChangeEvent}" 
	 onRemoveEvent="${onRemoveEvent}" mustMatch="${mustMatch==null ? true : mustMatch}" maxlength="${maxlength==null ? '' : maxlength}" minlength="${minlength==null ? '' : minlength}" arg01="${arg01}" 
	 arg02="${arg02}" arg03="${arg03}" arg04="${arg04}" arg05="${arg05}" arg06="${arg06}" arg07="${arg07}" arg08="${arg08}" arg09="${arg09}" arg10="${arg10}" arg11="${arg11}" arg12="${arg12}" arg13="${arg13}" 
	 arg14="${arg14}" arg15="${arg15}" arg16="${arg16}" arg17="${arg17}" arg18="${arg18}" arg19="${arg19}" arg20="${arg20}"
	 sourceType= "${sourceType }" sourceCallback= "${sourceCallback }"
	 placeholder='<qp:message code="${placeHolder}"></qp:message>' />
	<input type="hidden" class="${cssInput }" name="${name}" value="${value}" />
</div>