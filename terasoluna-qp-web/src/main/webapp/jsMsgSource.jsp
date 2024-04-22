<%@ page contentType="text/javascript; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

var fcomSysDateFormat = '';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.dateFormat}">
	fcomSysDateFormat = '${clDateFormat.get(systemSetting.dateFormat.toString())}';
</c:if>
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.dateFormat}">
	fcomSysDateFormat = '${clDateFormat.get(sessionScope.ACCOUNT_PROFILE.dateFormat.toString())}';
</c:if>

var javaDateTimeFormat = '${javaDateTimeFormat}';
var javaTimeFormat = '${javaTimeFormat}';
var javaDateFormat = '${javaDateFormat}';

var fcomSysTimeFormat = '';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.timeFormat}">
	fcomSysTimeFormat = '${clTimeFormat.get(systemSetting.timeFormat.toString())}';
</c:if>
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.timeFormat}">
	fcomSysTimeFormat = '${clTimeFormat.get(sessionScope.ACCOUNT_PROFILE.timeFormat.toString())}';
</c:if>

var fcomSysDatetimeFormat = fcomSysDateFormat + ' ' + fcomSysTimeFormat;
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.dateTimeFormat}">
	fcomSysDatetimeFormat = '${clDateTimeFormat.get(systemSetting.dateTimeFormat.toString())}'; 
</c:if>
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.dateTimeFormat}">
	fcomSysDatetimeFormat = '${clDateTimeFormat.get(sessionScope.ACCOUNT_PROFILE.dateTimeFormat.toString())}';
</c:if>
var mapDateFormat = {"1": "${clDateFormat.get('1')}",
					"2": "${clDateFormat.get('2')}"};
var mapDateTimeFormat = {"1":"${clDateTimeFormat.get('1')}",
						"2":"${clDateTimeFormat.get('2')}",
						"3":"${clDateTimeFormat.get('3')}",
						"4":"${clDateTimeFormat.get('4')}"};
var mapTimeFormat = {"1":"${clTimeFormat.get('1')}",
					"2":"${clTimeFormat.get('2')}"};

var keyDateFormat = "${sessionScope.ACCOUNT_PROFILE.dateFormat}";
var keyDateTimeFormat = "${sessionScope.ACCOUNT_PROFILE.dateTimeFormat}";
var keyTimeFormat = "${sessionScope.ACCOUNT_PROFILE.timeFormat}";			 

var fcomSysCurrencyCode	= '';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.currencyCode}">fcomSysCurrencyCode = '${systemSetting.currencyCode}';</c:if>	
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.currencyCode}">fcomSysCurrencyCode = '${sessionScope.ACCOUNT_PROFILE.currencyCode}';</c:if>	

var fcomSysCurrencyFormat = '';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.currencyFormat}">fcomSysCurrencyFormat = '${systemSetting.currencyFormat}';</c:if>	
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.currencyFormat}">fcomSysCurrencyFormat = '${sessionScope.ACCOUNT_PROFILE.currencyFormat}';</c:if>

var fcomSysCurrencyPosition = 's';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.currencyCodePosition}">fcomSysCurrencyPosition = '${systemSetting.currencyCodePosition}';</c:if>	
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.currencyCodePosition}">fcomSysCurrencyPosition = '${sessionScope.ACCOUNT_PROFILE.currencyCodePosition}';</c:if>

var fcomSysIntegerFormat = '';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.integerFormat}">fcomSysIntegerFormat = '${systemSetting.integerFormat}';</c:if>	
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.integerFormat}">fcomSysIntegerFormat = '${sessionScope.ACCOUNT_PROFILE.integerFormat}';</c:if>


var fcomSysDecimalFormat = '';
<c:if test="${empty sessionScope.ACCOUNT_PROFILE.floatFormat}">fcomSysDecimalFormat = '${systemSetting.floatFormat}';</c:if>	
<c:if test="${not empty sessionScope.ACCOUNT_PROFILE.floatFormat}">fcomSysDecimalFormat = '${sessionScope.ACCOUNT_PROFILE.floatFormat}';</c:if>

<c:set value="en_US" var="localeName"/><c:if test="${not empty sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}"><c:set value="${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}" var="localeName"/></c:if>
<c:set value="${localeName}_jsMesssageSource" var="jsMessage"/><c:set value="${fn:length(applicationScope[jsMessage]) - 1}" var="jsLength"/>
var fcomMsgSource = {
	<c:forEach var="msg" begin="0" end="${jsLength < 0 ? 0 : jsLength}" varStatus="status"><c:if test="${status.index < jsLength}">"${applicationScope[jsMessage][msg].key}":"${applicationScope[jsMessage][msg].value}",</c:if><c:if test="${status.index == jsLength}">"${applicationScope[jsMessage][msg].key}":"${applicationScope[jsMessage][msg].value}"</c:if></c:forEach>
}