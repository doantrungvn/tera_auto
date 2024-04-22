<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />
<script type="text/javascript">
    
</script>
<c:set var="titleKey">
  <tiles:insertAttribute name="title" ignore="true" />
</c:set>
<title><spring:message code="tqp.tqp" text="terasoluna-qp" /></title>
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.theme.css" rel="stylesheet" />
	<c:if test="${empty sessionScope.scomSessionUserDTO.themeCd}">
		<!-- <link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/blue-theme/jquery.ui.theme.css" rel="stylesheet" id="themeUI" /> -->
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
	</c:if>
	<c:if test="${not empty sessionScope.scomSessionUserDTO.themeCd}">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/${sessionScope.scomSessionUserDTO.themeCd}-theme/jquery.ui.theme.css" rel="stylesheet" id="themeUI" />
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/${sessionScope.scomSessionUserDTO.themeCd}-theme/style.css" rel="stylesheet" id="themeStyle" />
	</c:if>
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/common.css" rel="stylesheet" media="screen,print" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap.min.js"></script>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-ui-1.10.3.custom.min.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/autoNumeric.js"></script>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-combobox-patch.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.watermark.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.inputdate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.dateFormat-1.0.js"></script>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-timepicker.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/superfish.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.suggest.js"></script>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jsMsgSource.js"></script>  -->
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/javascript/FCOM/FCOM001.js"></script> --%>
	<!--combobox  -->
	<!-- 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/media/js/combobox/base/jquery.ui.base.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/media/js/combobox/base/autocomplate.css">	
	 -->
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.core.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.button.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.position.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.menu.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.autocomplete.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/combobox/jquery.ui.tooltip.js"></script>
	
	
	<script type="text/javascript">						
		$(document).ready(function(){
			$("#nav-wrapper ul.menu").superfish({ speed: 0 });
			$("ul.topnav li").click(function() { 
				$(this).parent().find("ul.subnav").toggle();		
			});
		});
	</script>
	
	<tiles:insertAttribute name="additionalHeading" ignore="true" />
	
	<link rel="icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
</head>
<body class="com-header-bgimg">
	<div class="container com-root-panel">
		<tiles:insertAttribute name="header-bootstrap" />
		<div class="row com-header-panel">
			<h4>
				<tiles:insertAttribute name="header-name" ignore="true" />
			</h4>
			<em><tiles:insertAttribute name="header-link" ignore="true" />&nbsp;</em>
		</div>

		<!-- Start message -->
		<div class="row com-message-panel"></div>
		<!-- End message -->

		<!-- Start content -->
		<div class="row com-body-panel">
			<tiles:insertAttribute name="body" />
		</div>
		<!-- End content -->
		
		<!-- Start footer -->
		<div class="row com-footer-panel">
			<tiles:insertAttribute name="footer-bootstrap" />
		</div>
		<!-- End footer -->
	</div>
	
</body>
</html>