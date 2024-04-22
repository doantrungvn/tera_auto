<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>	<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>	<html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width" />
	<title>DungNN QP</title>

	<link rel="icon" href="media/images/tool.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="media/images/tool.ico" type="image/x-icon" />
	
	<link type="text/css" href="media/css/bootstrap.css" rel="stylesheet" />
	<link type="text/css" href="media/js/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" />
	<link type="text/css" href="media/css/bootstrap.theme.css" rel="stylesheet" />
	<link rel="stylesheet" href="media/js/combobox/bootstrap-combobox.css">
	<link type="text/css" href="media/css/common.css" rel="stylesheet" media="screen,print" />
	<link type="text/css" href="media/css/elements.css" rel="stylesheet" />
	<link type="text/css" href="media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
	<link type="text/css" href="media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
	<link type="text/css" href="media/js/spinedit/bootstrap-spinedit.css" rel="stylesheet" />

	<link type="text/css" href="media/js/customnavbar/bootstrap-submenu.css" rel="stylesheet">

	
	<script type="text/javascript" src="media/js/jquery-1.11.2.min.js"></script>	
	<script type="text/javascript" src="media/js/jquery-ui.min.js"></script>		
	<script type="text/javascript" src="media/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="media/js/fancybox/jquery.fancybox.pack.js"></script>
	<script type="text/javascript" src="media/js/autoNumeric.js"></script>
	<script type="text/javascript" src="media/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="media/js/moment.js"></script>
	<script type="text/javascript" src="media/js/bootstrap-datetimepicker.min.js"></script>
	<script src="media/js/bootstrap-combobox.js"></script>
	<script src="media/js/bootstrap-tooltip.js"></script>	
	<script src="media/js/bootstrap-confirmation.js"></script>	
	<script type="text/javascript" src="media/js/bootstrap.file-input.js"></script>
	<script type="text/javascript" src="media/js/bootstrap-spinedit.js"></script>
	<script type="text/javascript" src="media/js/jsMsgSource.js"></script>
	<script type="text/javascript" src="media/js/terasoluna-qp-1.0.0.0.js"></script>
	
	<script type="text/javascript" src="media/js/terasoluna-qp-init.js"></script>
	
	<script type="text/javascript" src="media/js/customnavbar/bootstrap-submenu.js" ></script>
	<script type="text/javascript">
			$(function() {
				$('[data-submenu]').submenupicker();
			});
	</script>
	

	<!-- Start load theme for user -->
	<style>
/*************************************************************
	change Theme
*************************************************************/

/* body background color */
.qp-header-img {
	
		background-color: #ffffff;
	
}
/* menu bg*/
.navbar-default {
	
		background-image: linear-gradient(to bottom, #7895cf 0%, #7895cf 100%);   
	
}
/* menu brand */
.navbar-default .navbar-brand  {
	
		color: #ffffff ;
	
		font-size: 18px;
	
}
/* menu text color*/
.navbar-default .navbar-nav > li > a {
	
		color: #ffffff ;
	
}

/* header */
.qp-header-main-text{
	
		font-size: 18px;
	
		color: #333333 ;
	
}
.qp-link-header{
	
		text-align: right ;
	
		color:  #0033cc ;
	
}
/* panel */
.panel-default>.panel-heading {
	
		color: #ffffff ;
	
		background-color:  #7895cf ;
	
		border-color: 1px #CBCFED ;
	
		font-size: 12px;
	
		height: 28px;
	
}
.panel-body {
	
		padding: 10px;
	
		background:  #ffffff ;
	
}
/* panel table form */
.qp-table-form tr th{
	
		border: 1px solid #CBCFED ;
	
		background-color: #eff5f9 ;
	
} 
.qp-table-form tr th {
	
		text-align: right ;
	
}
/* panel table list */
.qp-table-list thead tr th {
	
		border: 1px solid #CBCFED ;
	
		background-color: #eff5f9 ;
	
}
.qp-table-list tbody tr td:first-child{
	
		text-align: center ;
	
		width: 40px;
	
}
.qp-table-list tbody tr td:last-child {
	
		text-align: center ;
	
		width: 40px;
	
}
.qp-table-list thead tr th {
	
		text-align: left ;
	
}

/* badge */
.panel-default > .panel-heading .badge {
	
		color: #333 ;
	
		background-color: #ffffff ;
	
}
/* footer */
.qp-footer {
	
		border-top: solid 1.5px #7895CF ;
	
}
/* common */

.qp-button-warning {
	
		background-color: #eb9316 ;
	
		color: #ffffff ;
	
}
.qp-button-warning:active ,.qp-button-warning:hover, .qp-button-warning:focus {
	
		background-color: #eb9316 ;
	
		border-color: #eb9316 ;
	
}
.qp-button {
	
		color: #ffffff ;
	
		background-color: #5cb85c ;
	
}

.qp-button:active, .qp-button:hover, .qp-button:focus{
	
		color: #ffffff ;
	
		background-color: #419641 ;
		border-color: #419641 ;
	
}
.qp-link-button:hover, .qp-link-button:visited, .qp-link-button:active, .qp-link-button:focus {
	
		color: #ffffff;
	
}
.pagination > .active > a {
	
		background-color: #5cb85c ;
		border-color: #5cb85c ;
	
		color: #ffffff ;
	
}

.qp-link-popup{
	
		color: #0033cc ;
	
}
.qp-root {
	
		width: 1004px ;
	
}

@media (max-width: 767px) {
	.navbar-default .navbar-nav > li.open {
		background-color:white;
	}
}
</style><!-- End load theme -->
	
	
	<!-- Start load theme for user -->
	<style>
/*************************************************************
	change Theme
*************************************************************/

/* body background color */
.qp-header-img {
	
		background-color: #ffffff;
	
}
/* menu bg*/
.navbar-default {
	
		background-image: linear-gradient(to bottom, #7895cf 0%, #7895cf 100%);   
	
}
/* menu brand */
.navbar-default .navbar-brand  {
	
		color: #ffffff ;
	
		font-size: 18px;
	
}
/* menu text color*/
.navbar-default .navbar-nav > li > a {
	
		color: #ffffff ;
	
}

/* header */
.qp-header-main-text{
	
		font-size: 18px;
	
		color: #333333 ;
	
}
.qp-link-header{
	
		text-align: right ;
	
		color:  #0033cc ;
	
}
/* panel */
.panel-default>.panel-heading {
	
		color: #ffffff ;
	
		background-color:  #7895cf ;
	
		border-color: 1px #CBCFED ;
	
		font-size: 12px;
	
		height: 28px;
	
}
.panel-body {
	
		padding: 10px;
	
		background:  #ffffff ;
	
}
/* panel table form */
.qp-table-form tr th{
	
		border: 1px solid #CBCFED ;
	
		background-color: #eff5f9 ;
	
} 
.qp-table-form tr th {
	
		text-align: right ;
	
}
/* panel table list */
.qp-table-list thead tr th {
	
		border: 1px solid #CBCFED ;
	
		background-color: #eff5f9 ;
	
}
.qp-table-list tbody tr td:first-child{
	
		text-align: center ;
	
		width: 40px;
	
}
.qp-table-list tbody tr td:last-child {
	
		text-align: center ;
	
		width: 40px;
	
}
.qp-table-list thead tr th {
	
		text-align: left ;
	
}

/* badge */
.panel-default > .panel-heading .badge {
	
		color: #333 ;
	
		background-color: #ffffff ;
	
}
/* footer */
.qp-footer {
	
		border-top: solid 1.5px #7895CF ;
	
}
/* common */

.qp-button-warning {
	
		background-color: #eb9316 ;
	
		color: #ffffff ;
	
}
.qp-button-warning:active ,.qp-button-warning:hover, .qp-button-warning:focus {
	
		background-color: #eb9316 ;
	
		border-color: #eb9316 ;
	
}
.qp-button {
	
		color: #ffffff ;
	
		background-color: #5cb85c ;
	
}

.qp-button:active, .qp-button:hover, .qp-button:focus{
	
		color: #ffffff ;
	
		background-color: #419641 ;
		border-color: #419641 ;
	
}
.qp-link-button:hover, .qp-link-button:visited, .qp-link-button:active, .qp-link-button:focus {
	
		color: #ffffff;
	
}
.pagination > .active > a {
	
		background-color: #5cb85c ;
		border-color: #5cb85c ;
	
		color: #ffffff ;
	
}

.qp-link-popup{
	
		color: #0033cc ;
	
}
.qp-root {
	
		width: 1004px ;
	
}

@media (max-width: 767px) {
	.navbar-default .navbar-nav > li.open {
		background-color:white;
	}
}
</style><!-- End load theme -->
	<!-- Start load screen size for user -->
	<script type="text/javascript">
		$.qp.initalFancybox = function(className, href) {
			if (href == null) {
				$("a." + className).fancybox({
					'centerOnScroll' : true,
					'enableEscapeButton' : false,
					onStart : function() {
						$("body").css("overflow", "hidden");
					},
					onClosed : function() {
						$("body").css("overflow", "auto");
					},
					afterClose : function(){
						fcomFancyBoxClose();
					},
					'width' : 1004,
					'height' : '100%',
					'autoSize' : false,
					'autoScale' : true,
					'autoDimensions' : true,
					'hideOnOverlayClick' : false,
					'transitionIn' : 'none',
					'transitionOut' : 'none',
					'type' : 'iframe',
					'beforeLoad': function(){
						var state = {"html":window.top.document.documentElement.innerHTML};
						window.history.replaceState(state,"",window.top.location.href);
					},
					'afterLoad':function(event,arg01,arg02){
						if($.qp.isHasPageErrors($(this.content).contents()[0].documentElement)) {
							var iframeHtml = $($("iframe").contents()[0].documentElement).html();
							$("iframe").parents(".fancybox-overlay:first").remove();
							var state = {"html":iframeHtml};
							window.history.pushState(state,"",window.location.href);
							$.qp.updateDocument(window.top.document,{"html":state.html})
							return false;
						}
					}
				});
			}
			if (href != null) {
				$("a." + className).fancybox({
					'href' : $("a." + className).attr('href'),
					'centerOnScroll' : true,
					'enableEscapeButton' : false,
					onStart : function() {
						$("body").css("overflow", "hidden");
					},
					onClosed : function() {
						$("body").css("overflow", "auto");
					},
					afterClose : function(){
						fcomFancyBoxClose();
					},
					'width' : 1004,
					'height' : '100%',
					'autoSize' : false,
					'autoScale' : true,
					'autoDimensions' : true,
					'hideOnOverlayClick' : false,
					'transitionIn' : 'none',
					'transitionOut' : 'none',
					'type' : 'iframe'
				});
			}
		};
	$(document).ready(function(){
		$.qp.initalFancybox("qp-link-popup", 'test');
		$.qp.initalFancybox("qp-button-popup", 'test');
		
		$(".qp-link-popup-navigate").click(function(){
			var parentWindow = window.parent.document; 
			var href = $(this).attr('href');
			
			$.ajax({
					method : "GET",
					url : href,
				}).done(function(data,status) {
					var $document;
					var state;
					if($.qp.isHasPageErrors(data)) {
						$document = window.document;
						state = {"html":data};
					} else {
						$document = window.parent.document;
						$("iframe").parents(".fancybox-overlay")
						state = {"html":data};
						window.parent.history.pushState(state,"",href);
					}
					$.qp.updateDocument($document,state);
				});
				return false;
	    });
	});
	function fcomFancyBoxClose()
	{
		var isReload = $('#fcomReloadFancyBox').val();
		if(isReload == 'true')
		{
			var parentWindow = window.parent.document;
			parentWindow.location.reload();
		}
	}
</script><!-- End load theme screen size -->
</head>
<body class="qp-header-img">
	<div class="modal fade" id="fcomConfirmDialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header qp-model-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title qp-model-header-title">Confirm message</h3>
				</div>
				<div class="modal-body qp-model-body">
				</div>
				<div class="modal-footer qp-model-footer">
					<button type="button" class="btn btn-primary" id="fcomConfirmBtnYes" tabindex="1">Yes</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" tabindex="2">No</button>
				</div>
			</div>
		</div>
	</div>
	<div class="qp-root">
		<style type="text/css">
			.label-menu-corner {
				font-size: 10px;
				line-height: 12px;
				position: absolute;
				left: 24px;
				top: 8px;
			}

			.dropdown-alerts {
				min-width: 250px;
			}
			
			.qp-header-img {
				background: none;
			}
		</style>
		<!-- Start header info -->
		<div class="com-header-info-panel">
			<!-- Start logo panel -->
			<div class="com-header-logo-panel" style="padding-top : 10px;">
				<a href="#"><img src="media/images/logo_header_01.png" style="border: 0px; height: 25px;" /> </a>
			</div>
			<!-- End logo panel -->

			<!-- Start header button -->
			<div class="com-header-button-panel">
				<!-- start user information -->
				<div class="fl" style="padding-top: 15px;">
				</div>
				<!-- end user information -->
			</div>
			<!-- End header button -->
		</div>
		<!-- End header info -->

<!-- Start Menu panel -->
	<nav class="navbar navbar-default qp-menu-primary" style="width:150px; float: left;">
			${menuContent}
	</nav>
		<#assign withAreaContentVertical = "84%">
		<#assign alignAreaContentVertical = "right">
<!-- Start body container -->
<div class="qp-body-container" style="width: ${withAreaContentVertical}; float:${alignAreaContentVertical};">
	<div class="qp-header-main">
		<h4><span class="qp-header-main-text">Home page</span></h4>
		<em style="text-align: right;"></em>
		<p style="float:right;">The time on the server is&nbsp;October 7, 2015 11:42:16 AM ICT.</p>
		<p style="float:right; padding-right: 15px; font-weight: bold;">User name : </p></div>
		<!-- Start content -->
		<div class="qp-body">
			<div id="wrapper" style="text-align: center; height: 50px;">
			<h1>Welcome to ${projectName}</h1>
		</div>
		<div class="qp-footer">
			<ul class="pull-left">
				<li><a href="">About this Site</a></li>
				<li><a href="">Contact Us</a></li>
			</ul>
			<address class="pull-right">Copyright &copy; 2015 NTT DATA Corporation</address></div>
		</div>
		<!-- End body panel -->
	</div>
</body>
</html>