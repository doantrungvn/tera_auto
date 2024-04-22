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
	
	<title>${projectName}</title>
	
	<link rel="icon" href="media/images/tool.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="media/images/tool.ico" type="image/x-icon" />
	
	<link type="text/css" href="media/css/bootstrap.css" rel="stylesheet" />
	<link type="text/css" href="media/css/bootstrap.theme.css" rel="stylesheet" />
	<link rel="stylesheet" href="media/js/combobox/bootstrap-combobox.css">
	<link type="text/css" href="media/css/common.css" rel="stylesheet" media="screen,print" />
	<link type="text/css" href="media/css/elements.css" rel="stylesheet" />
	<link type="text/css" href="media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
	<link type="text/css" href="media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
	<link type="text/css" href="media/js/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" />
	<link type="text/css" href="media/js/spinedit/bootstrap-spinedit.css" rel="stylesheet" />	

	<script type="text/javascript" src="media/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="media/js/bootstrap.min.js"></script>
	
	<link href="media/smartmenus-1.0.0/css/sm-core-css.css" type="text/css" rel="stylesheet" />
	<link href="media/smartmenus-1.0.0/css/sm-blue/sm-blue.css" type="text/css" rel="stylesheet" />
	<link href="media/smartmenus-1.0.0/css/style-vertical.css" type="text/css" rel="stylesheet" />
	
	<script type="text/javascript" src="media/smartmenus-1.0.0/jquery.smartmenus.min.js" ></script>
	<style id="main-menu-desktop-width-css">@media (min-width: 768px) { #main-menu { float:left; width:12em; } }</style>
	<script type="text/javascript">
		$(function() {
			$('#main-menu').smartmenus({
				subMenusSubOffsetX: 1,
				subMenusSubOffsetY: -8
			});
			$('#main-menu').smartmenus();
		});
	</script>
	
	
	
	</head>
	<body class="qp-header-img">
	<div class="qp-root">
		<!-- Start header info -->
		<div class="com-header-info-panel">
			<!-- Start logo panel -->
			<div class="com-header-logo-panel" style="margin-top: 10px;">
				<a href="#"><img src="media/images/logo_header_01.png" style="border: 0px; height: 25px;" /></a>
				<div class="fl" style="padding-top: 22px;"></div>
			</div>
		</div>
		<!-- End logo panel -->
		<!-- End header info -->
		
		<nav role="navigation">
			<!-- Sample menu definition -->
			<ul id="main-menu" class="sm sm-vertical sm-blue">
				<li><a href="#">${projectName}</a></li>
			</ul>
		</nav>

<!-- End Menu panel --><!-- Start body container -->
		<div class="qp-body-container">
			<!-- Start function name -->
<!-- 			<div class="ui-widget-header com-header-function-panel"> -->
<!-- 		   	</div> -->
			<!-- End function name -->
						
			<!-- Start toolbar --->
<!-- 			<div class="style-header-toolbar com-header-toolbar-panel"> -->
<!-- 				<div class="com-header-toolbar-text"> -->
<!-- 	   			</div> -->
<!-- 			</div> -->
			<!-- End toolbar-->
			
		  	<!-- Start message -->
		  	<div class="qp-message">
				
			</div>
		  	<!-- End message -->
				
			<!-- Start content -->	 
			<div class="qp-body">
				<div class="qp-panel-form-login">
			<div class="panel panel-default login-panel ">
				<div class="panel-heading">
					<b>Login</b>
				</div>
				<div class="panel-body qp-panel-body-login">
					<form id="command" action="home.html" method="post"><fieldset>
							<div class="form-group input-group">
								<input class="form-control qp-form-control" id="username" name="j_username" placeholder="Username" autofocus>
								<span class="input-group-addon"><i class="glyphicon glyphicon-user" style=""></i></span>
							</div>
							<div class="form-group input-group">
								<input class="form-control qp-form-control" placeholder="Password" type="password" id="password" name="j_password" value="">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							</div>
							<div>
								<p class="help-block">User ID and Password are case sensitive</p>
							</div>
							<!-- Change this to a button or input when using this as a form -->
							<div class="qp-div-action">
								<input id="loginBtn" type="submit" value="Login" class="btn btn-success ">
								<input type="hidden" name="redirectTo" value="" />
							</div>
						</fieldset>
					<div>

</div></form></div>
			</div>
		</div>
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