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
		CONTEXT_PATH = "<%=request.getContextPath()%>";		
		DATE_FORMAT = "DD/MM/YYYY";
		DATE_TIME_FORMAT = "DD/MM/YYYY HH:mm:ss";
		TIME_FORMAT = "HH:mm:ss";
	</script>
	<c:set var="titleKey">
		<tiles:insertAttribute name="title" ignore="true" />
	</c:set>
	<title><spring:message code="tqp.tqp" text="terasoluna-qp" /></title>
	
	<link rel="icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
	
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.css" rel="stylesheet" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.theme.css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/media/js/combobox/bootstrap-combobox.css">
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/common.css" rel="stylesheet" media="screen,print" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/elements.css" rel="stylesheet" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" />
	<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/spinedit/bootstrap-spinedit.css" rel="stylesheet" />	

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-1.11.2.min.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/autoNumeric.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.watermark.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.inputdate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.dateFormat-1.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.suggest.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/moment.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/bootstrap-combobox.js"></script>
	<script src="${pageContext.request.contextPath}/resources/media/js/bootstrap-tooltip.js"></script>	
	<script src="${pageContext.request.contextPath}/resources/media/js/bootstrap-confirmation.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap.file-input.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap-spinedit.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/terasoluna-qp-1.0.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/terasoluna-qp-init.js"></script>
	
	<tiles:insertAttribute name="additionalHeading" ignore="true" />

</head>
<body class="qp-header-img">
  <div class="qp-root">
  
    <tiles:insertAttribute name="header" />
    
    	<!-- Start body container -->
		<div class="qp-body-container">
			
			<!-- Start function name -->
<!-- 			<div class="ui-widget-header com-header-function-panel"> -->
<%-- 	        	<div class="com-header-function-text"><tiles:getAsString name="header-name" ignore="true" /></div> --%>
<!-- 	       	</div> -->
	    	<!-- End function name -->
	                    
	        <!-- Start toolbar --->
<!-- 	        <div class="style-header-toolbar com-header-toolbar-panel"> -->
<!-- 	            <div class="com-header-toolbar-text"> -->
<%-- 	       			<tiles:insertAttribute name="header-link" ignore="true" /> --%>
<!-- 	   			</div> -->
<!-- 	        </div> -->
	        <!-- End toolbar-->
	        
	      	<!-- Start message -->
	      	<div class="qp-message">
		        
	        </div>
	      	<!-- End message -->
	        	
	        <!-- Start content -->     
	        <div class="qp-body">
	            <tiles:insertAttribute name="body" />
	        </div>
	        
	        <div class="qp-footer">
                <tiles:insertAttribute name="footer" />
            </div>
		</div>
		<!-- End body panel -->
    
  </div>
</body>
</html>