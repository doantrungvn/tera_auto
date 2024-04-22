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
</script>
<c:set var="titleKey">
	<tiles:insertAttribute name="title" ignore="true" />
</c:set>
<title><spring:message code="tqp.tqp" text="terasoluna-qp" /></title>
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/media/js/combobox/bootstrap-combobox.css">
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.theme.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/common.css" rel="stylesheet" media="screen,print" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/elements.css" rel="stylesheet" />
 <link href="${pageContext.request.contextPath}/resources/media/js/customnavbar/bootstrap-submenu.css" rel="stylesheet">
<!-- Start load theme for user -->
<jsp:include page="customize-theme.jsp" />
<!-- End load theme -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-ui.min.js"></script>		
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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap-spinedit.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/spinedit/bootstrap-spinedit.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/terasoluna-qp-1.0.0.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/terasoluna-qp-init.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/customnavbar/bootstrap-submenu.js" ></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap.file-input.js"></script>
<!-- Start load screen size for user -->
<jsp:include page="customize-popup.jsp" />
<!-- End load theme screen size -->
<tiles:insertAttribute name="additionalHeading" ignore="true" />
<link rel="icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
</head>
<body>
	<div class="modal fade" id="fcomConfirmDialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header qp-model-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title qp-model-header-title"><qp:message code="sc.sys.0038"/></h3>
				</div>
				<div class="modal-body qp-model-body">
				</div>
				<div class="modal-footer qp-model-footer">
					<button type="button" class="btn btn-primary" id="fcomConfirmBtnYes" tabindex="1"><qp:message code="sc.sys.0011"/></button>
					<button type="button" class="btn btn-default" data-dismiss="modal" tabindex="2"><qp:message code="sc.sys.0012"/></button>
				</div>
			</div>
		</div>
	</div>
	<div class="qp-root">
		<!-- Start body container -->
		<div class="qp-body-container">
			<!-- Start message -->
			<div class="qp-message">
				<t:messagesPanel messagesAttributeName="message"></t:messagesPanel>
			</div>
			<!-- End message -->
			<!-- Start content -->
			<div class="qp-body">
				<tiles:insertAttribute name="body" ignore="true" />
			</div>
		</div>
	</div>
</body>
</html>