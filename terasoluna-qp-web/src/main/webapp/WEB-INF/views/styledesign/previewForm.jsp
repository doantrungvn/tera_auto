
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
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
	var CURRENT_PROJECT_ID = "155";
	var INTERVAL_RELOAD = "123";
	if (INTERVAL_RELOAD == 0 || INTERVAL_RELOAD == 'null') {
		INTERVAL_RELOAD = 60000;
	} else {
		INTERVAL_RELOAD = INTERVAL_RELOAD * 1000;
	}
</script>
<title>terasoluna-qp</title>

<link rel="icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/media/images/tool.ico" type="image/x-icon" />

<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.theme.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/media/js/combobox/bootstrap-combobox.css">
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/common.css" rel="stylesheet" media="screen,print" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/elements.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/spinedit/bootstrap-spinedit.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/media/js/customnavbar/bootstrap-submenu.css" rel="stylesheet">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/fancybox/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/autoNumeric.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.tmpl.min.js"></script>
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.watermark.js"></script>-->
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.inputdate.js"></script>-->
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.dateFormat-1.0.js"></script>-->
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery.suggest.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/bootstrap-combobox.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/bootstrap-tooltip.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/bootstrap-confirmation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/customnavbar/bootstrap-submenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap.file-input.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/bootstrap-spinedit.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/terasoluna-qp-1.0.0.0.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/listProblem.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/terasoluna-qp-init.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/project/javascript/design.js?r=555"></script>

<jsp:include page="include/style.jsp" />

<!-- End load theme -->
<!-- Start load screen size for user -->
<script type="text/javascript">
	$.qp.initalFancybox = function(className, href) {
		if (href == null) {
			$("a." + className)
					.fancybox(
							{
								'centerOnScroll' : true,
								'enableEscapeButton' : false,
								onStart : function() {
									$("body").css("overflow", "hidden");
								},
								onClosed : function() {
									$("body").css("overflow", "auto");
								},
								afterClose : function() {
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
								'beforeLoad' : function() {
									var state = {
										"html" : window.top.document.documentElement.innerHTML
									};
									window.history.replaceState(state, "",
											window.top.location.href);
								},
								'afterLoad' : function(event, arg01, arg02) {
									if ($.qp.isHasPageErrors($(this.content)
											.contents()[0].documentElement)) {
										var iframeHtml = $(
												$("iframe").contents()[0].documentElement)
												.html();
										$("iframe").parents(
												".fancybox-overlay:first")
												.remove();
										var state = {
											"html" : iframeHtml
										};
										window.history.pushState(state, "",
												window.location.href);
										$.qp.updateDocument(
												window.top.document, {
													"html" : state.html
												})
										return false;
									}
								},
								helpers : {
									overlay : {
										closeClick : false
									}
								}
							});
		}
		if (href != null) {
			$("a." + className).fancybox({
				'href' : href,
				'centerOnScroll' : true,
				'enableEscapeButton' : false,
				onStart : function() {
					$("body").css("overflow", "hidden");
				},
				onClosed : function() {
					$("body").css("overflow", "auto");
				},
				afterClose : function() {
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
				helpers : {
					overlay : {
						closeClick : false
					}
				}
			});
		}
	};
	$(document).ready(function() {
		$.qp.initalFancybox("qp-link-popup", null);
		$.qp.initalFancybox("qp-button-popup", null);
	});
	function fcomFancyBoxClose() {
		var isReload = $('#fcomReloadFancyBox').val();
		if (isReload == 'true') {
			var parentWindow = window.parent.document;
			parentWindow.location.reload();
		}
	}
</script>

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
</style>
</head>
<script>
	$(document).ready(function() {
		var jsonString = '${data}';
		var json = {};
		
		if (jsonString.length > 0) {
			json = JSON.parse(jsonString);
			
			for (var i = 0; i < json.length; i++) {
				var tempLayout = getLayoutByPosition(json[i].position);
				
				if (json[i].type == 4) continue;
				var temp = getTemplateByType(json[i].type);
				var content = $("#" + temp.template).tmpl();
				$(content).attr('style', json[i].style);
				
				if (json[i].type == 1) {
					$(content).attr('onmouseover', "this.setAttribute('style', '"+json[i].hoverStyle + "')");
					$(content).attr('onmouseout', "this.setAttribute('style', '" + json[i].style + "')");
				}
				if (json[i].type == 0 || json[i].type == 1) {
					$(content).html(json[i].labelNameAutocomplete);	
				}
				
				if ((json[i].type == "2" || json[i].type == "3") && json[i].convertTo == "2") {
					$(content).find("span").replaceWith(function() {
						return '<a class="item" href="javascript:;">' + json[i].labelNameAutocomplete + '</a>';
					});
				}
				
				$(".qp-root").find('div[type='+json[i].position+']').append(content).append("&nbsp;");
			}
		}
	});
</script>
<script id="text" type="text/template">
	<span class="item"></span>
</script>
<script id="link" type="text/template">
	<a class="item" href="javascript:;"></a>
</script>
<script id="currentUser" type="text/template">
	<span class="item">${account.username}</span>
</script>
<script id="currentDatetime" type="text/template">
	<span class="item">${serverTime}</span>
</script>
<body class="qp-header-img">
	<div class="modal fade" id="fcomConfirmDialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header qp-model-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h3 class="modal-title qp-model-header-title">Confirm message</h3>
			</div>
				<div class="modal-body qp-model-body"></div>
				<div class="modal-footer qp-model-footer">
					<button type="button" class="btn btn-primary" id="fcomConfirmBtnYes" tabindex="1">Yes</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" tabindex="2">No</button>
			</div>
		</div>
	</div>
</div>
	<div class="qp-root">
		<div class="com-header-info-panel" style="min-height: 52px;">
			<div class="com-header-logo-panel" style="width: 100%; float: left; padding-top: 10px">
				<div style="float: ${projectStyle ['panel-table-list-td-anotherChild-position']}; width: 20%;">
					<c:if test="${empty projectStyle['logo'] or projectStyle['logo'] == 'null'}">
						<a class="btn-disable" href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/resources/media/images/logo_header_01.png" style="border: 0px; ${projectStyle['logoHeight']} ${projectStyle['logoWidth']}" /> </a>
					</c:if>
					<c:if test="${not empty projectStyle['logo'] and projectStyle['logo'] ne 'null'}">
						<a class="btn-disable" href="${pageContext.request.contextPath}"><img src="data:image/jpeg;base64,${projectStyle['logo'] }" style="border: 0px; ${projectStyle['logoHeight']} ${projectStyle['logoWidth']}" /> </a>
					</c:if>
				</div>
				<div type="0" style="float: left;  width: 38%;"></div>
				<div type="1" style="float: right;  width: 38%; text-align: right;"></div>
			</div>
			<div class="com-header-button-panel" style="width: 100%; float: left;">
				<div type="2" style="width: 48%; float: left;"></div>
				<div type="3" style="width: 48%; float: right; text-align: right;"></div>
			</div>
		</div> 
		<nav class="navbar navbar-default qp-menu-primary">
			<div class="container-fluid" style="position: relative; z-index: 6">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button aria-controls="navbar" aria-expanded="false" data-target="#navbar-menu" data-toggle="collapse" class="navbar-toggle collapsed navbar-default" type="button"><span class="sr-only">Toggle navigation</span> <span class="icon-bar" style="color: white; background-color: white;"></span> <span class="icon-bar" style="color: white; background-color: white;"></span> <span class="icon-bar" style="color: white; background-color: white;"></span></button> <a class="navbar-brand btn-disable" href="${pageContext.request.contextPath}/home">TERASOLUNA QP</a>
			</div> <!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="navbar-menu">
					<ul class="nav navbar-nav">

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Project<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">

								<li><a class="btn-disable_menu_item">Project management</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Business type</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Module management</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Function design management</a></li>

							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Upper design<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">

								<li><a class="btn-disable_menu_item">Message design</a></li>
								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Domain design</a></li>
								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Subject area design</a></li>
								<li><a class="btn-disable_menu_item">Table design</a></li>
								<li><a class="btn-disable_menu_item">Graphic database design</a></li>
								<li><a class="btn-disable_menu_item">View design</a></li>
								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Autocomplete design</a></li>
								<li><a class="btn-disable_menu_item">SQL design</a></li>
								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Screen transition diagram</a></li>
								<li><a class="btn-disable_menu_item">Screen list</a></li>
								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Decision table</a></li>
								<li><a class="btn-disable_menu_item">Business logic design</a></li>
								<li><a class="btn-disable_menu_item">Common object definition</a></li>
								<li><a class="btn-disable_menu_item">External object definition</a></li>
							</ul></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Other design<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">

								<li><a class="btn-disable_menu_item">Codelist design</a></li>
								<li class="divider"></li>

								<li><a class="btn-disable_menu_item">Function master</a></li>
								<li class="divider"></li>

								<li><a class="btn-disable_menu_item">Menu design</a></li>
								<li class="divider"></li>

								<li><a class="btn-disable_menu_item">Design information</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Library management</a></li>

							</ul></li>


						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Generation<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">

								<li><a class="btn-disable_menu_item">Generate Script</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Generate HTML</a></li>


								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Generate document</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Generate source code</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable_menu_item">Generate Management</a></li>

							</ul></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Administrator<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">

								<li><a class="btn-disable_menu_item">Account rule definition</a></li>
								<li class="divider"></li>

								<li><a class="btn-disable_menu_item">Account management</a></li>
								<li class="divider"></li>


								<li><a class="btn-disable_menu_item">Role management</a></li>
								<li class="divider"></li>

								<li><a class="btn-disable_menu_item">Message management</a></li>

							</ul></li>

					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown" id="navTopProblem"><a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="min-width: 17px; font-size: 16px; padding-left: 16px; padding-right: 16px;"> <i class="glyphicon glyphicon-bell"></i> <span class="label label-danger label-menu-corner" style="display: none;"></span>
						</a></li>
						<li class="dropdown"><a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="font-size: 16px;"> <span class="glyphicon glyphicon-globe"></span> <span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a class="btn-disable" onclick="$.qp.changeSystemLanguage('en_US')" href="javascript:"><img src="${pageContext.request.contextPath}/resources/media/images/en_US.png" style="border: 0px;" />&nbsp;&nbsp;English</a></li>
								<li><a class="btn-disable" onclick="$.qp.changeSystemLanguage('ja_JP')" href="javascript:"><img src="${pageContext.request.contextPath}/resources/media/images/ja_JP.png" style="border: 0px;" />&nbsp;&nbsp;Japanese</a></li>
							</ul></li>
						<li><a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="font-size: 16px;"> <span class="glyphicon glyphicon-user"></span><span class="caret"></span>
						</a>
							<ul class="dropdown-menu dropdown-user">
								<li><a class="btn-disable" class="qp-link-popup" href="${pageContext.request.contextPath}/accountprofile/modifyUserSetting"> <span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;User setting
								</a></li>

								<li><a class="btn-disable" class="qp-link-popup" href="${pageContext.request.contextPath}/accountprofile/modifySystemSetting"> <span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;System setting
								</a></li>

								<li><a class="btn-disable" class="qp-link-popup" href="${pageContext.request.contextPath}/accountprofile/modifyTheme"> <span class="glyphicon glyphicon-text-color"></span>&nbsp;&nbsp;Theme setting
								</a></li>

								<li class="divider"></li>
								<li><a class="btn-disable" href="javascript:document.getElementById('logOutForm').submit();"> <span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;Logout
								</a>
									<form id="logOutForm" action="${pageContext.request.contextPath}/logout" method="POST"><div><input type="hidden" name="_csrf" value="6377b988-a401-434c-9802-84dd2557d4df" /></div></form></li>
							</ul></li>
					</ul>
			</div> <!-- /.navbar-collapse -->
		</div>
	</nav>
	<!-- Start body container -->
		<div class="qp-body-container">
			<div class="qp-link-header pull-right"></div>
			<div class="qp-header-main">
				<h4><span class="qp-header-main-text qp-header-title"> Header title</span></h4>
			</div>
			<div class="qp-link-header">
				<a class="qp-link-popup-text-color" href="javascript:">Header link</a>
			</div>
			<div style="min-height: 16px;"><span class="qp-breadcrumb pull-left"> </span> <!-- End toolbar--></div> <!-- End toolbar--> <!-- Start message -->
			<div class="qp-message"></div> <!-- End message --> <!-- Start content -->
			<div class="qp-body">
				<!-- Screen design area -->
					<div class="ui-sortable" id="srcgenScreen">
						<!--Start load data  -->
						<div class="" style="width:; float: left;">
							<div class="panel panel-default qp-div-information">
								<div class="panel-heading"><span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span> <span class="pq-heading-text">Single Entity</span></div>
								<div class="panel-body">
									<table class="table table-bordered qp-table-form" id="17601">
										<colgroup>
											<col width=""></col>
											<col width=""></col>
											<col width=""></col>
											<col width=""></col>
										</colgroup>
										<tbody class="ui-sortable">
											<tr index="0">
												<th index="0" style="" ><label style="cursor: pointer;">FullElement Id</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" type="text" value="" class="form-control qp-input-text" name="fullelement_id" maxlength="" />
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Autocomplete</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;">
														<div class="input-group" style="width: 100%"><input type="text" class="form-control" name="autocomplete" maxlength="10 optionvalue=" optionValue" optionlabel="optionLabel" selectsqlid="" emptylabel="" onselectevent="" onchangeevent="" onremoveevent="" mustmatch="true" minlength="0" arg01="" arg02="" arg03="" arg4="" arg05="" arg06="" arg07="" arg08="" arg09="" arg10="" arg11="" arg12="" arg13="" arg14="" arg15="" arg16="" arg17="" arg18="" arg19="" arg20="" placeholder="" autocomplete="off"> <input type="hidden" value=""> <span class="input-group-addon dropdown-toggle" data-dropdown="dropdown"> <span class="caret"></span>
														</span></div>
												</span></td>
											</tr>
											<tr index="1">
												<th index="0" style=""><label style="cursor: pointer;">Currency</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input type="text" value="" class="form-control qp-input-text" name="currency" maxlength="" />
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Text label</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="text_label" maxlength="200" />
												</span></td>
											</tr>
											<tr index="2">
												<th index="0" style=""><label style="cursor: pointer;">File upload</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input class="qp-input-file" type="file" name="file_upload" />
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Text input</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="text_input" maxlength="200" />
												</span></td>
											</tr>
											<tr index="3">
												<th index="0" style=""><label style="cursor: pointer;">Decimal</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" type="text" value="" class="form-control qp-input-text" name="decimal" maxlength="" />
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Integer</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" type="text" value="" class="form-control qp-input-text" name="integer" maxlength="" />
												</span></td>
											</tr>
											<tr index="4">
												<th index="0" style=""><label style="cursor: pointer;">Datetime</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="datetime" maxlength="19" />
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Date</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;">
														<div class="input-group date qp-input-datetimepicker-detail"><span><input type="text" class="form-control" name="date" maxlength="" /></span><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div>
												</span></td>
											</tr>
											<tr index="5">
												<th index="0" style=""><label style="cursor: pointer;">Time</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;">
														<div class="input-group date qp-input-timepicker"><span><input type="text" class="form-control" name="time" maxlength="" /></span><span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span></div>
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Code</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="code" maxlength="200" />
												</span></td>
											</tr>
											<tr index="6">
												<th index="0" style=""><label style="cursor: pointer;">Checkbox</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <select name="checkbox" class="form-control qp-input-select"><option></option>
															<option>Option1</option>
															<option>Option2</option></select>
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Email</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="email" maxlength="200" />
												</span></td>
											</tr>
											<tr index="7">
												<th index="0" style=""><label style="cursor: pointer;">Select</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <select name="select" class="form-control qp-input-select"><option>value 1</option>
															<option>value 2</option>
															<option>A 3</option></select>
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Phone</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="phone" maxlength="20" />
												</span></td>
											</tr>
											<tr index="8">
												<th index="0" style=""><label style="cursor: pointer;">Radio</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input type="radio" class="qp-input-radio qp-input-radio-margin" name="radio" /><label for="radio">Laptop</label><input type="radio" class="qp-input-radio qp-input-radio-margin" name="radio" /><label for="radio">Tivi</label>
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Remark</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <textarea value="" class="form-control qp-input-textarea" class="form-control qp-input-textarea" name="remark1" maxlength="4000"></textarea>
												</span></td>
											</tr>
											<tr index="9">
												<th index="0" style=""><label style="cursor: pointer;">only value</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <select name="only_value" class="form-control qp-input-select"><option>null</option>
															<option>null</option></select>
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">both value</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <select name="both_value" class="form-control qp-input-select"><option></option>
															<option>Option1</option>
															<option>Option2</option></select>
												</span></td>
											</tr>
											<tr index="10">
												<th index="0" style=""><label style="cursor: pointer;">numeric</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <input style="width: 100%;" type="text" value="" class="form-control qp-input-text" name="numeric" maxlength="" />
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;">Code list</label></th>
												<td index="3" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <select name="code_list" class="form-control qp-input-select"><option>value 1</option>
															<option>value 2</option>
															<option>A 3</option></select>
												</span></td>
											</tr>
											<tr index="11">
												<th index="0" style=""><label style="cursor: pointer;">Autocomplete full</label></th>
												<td index="1" style=""><span style="width: 100%; float: left; padding-right: 3px;"> <select name="autocomplete_full" class="form-control qp-input-select"><option>value 1</option>
															<option>value 2</option>
															<option>A 3</option></select>
												</span></td>
												<th index="2" style=""><label style="cursor: pointer;"></label></th>
												<td index="3" style=""></td>
											</tr>
										</tbody>
									</table>
							</div>
						</div>
					</div>
						<div class="" style="width:; float: left;">
							<div class="panel panel-default qp-div-select">
								<div class="panel-heading"><span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text">List Entities</span></div>
								<div class="panel-body">
									<table class="table table-bordered qp-table-list" id="17602">
										<colgroup>
											<col width="35px">
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
										</colgroup>
										<thead>
											<tr index="0">
												<th>No.</th>
												<th style="text-align: left;" class="align-left" index="0"><label style="cursor: pointer;">Currency</label></th>
												<th style="text-align: left;" class="align-left" index="1"><label style="cursor: pointer;">Text label</label></th>
												<th style="text-align: left;" class="align-left" index="2"><label style="cursor: pointer;">Text input</label></th>
												<th style="text-align: left;" class="align-left" index="3"><label style="cursor: pointer;">Integer</label></th>
												<th style="text-align: left;" class="align-left" index="4"><label style="cursor: pointer;">Datetime</label></th>
												<th style="text-align: left;" class="align-left" index="5"><label style="cursor: pointer;">Date</label></th>
												<th style="text-align: left;" class="align-left" index="6"><label style="cursor: pointer;">Time</label></th>
												<th style="text-align: left;" class="align-left" index="7"><label style="cursor: pointer;">Email</label></th>
												<th style="text-align: left;" class="align-left" index="8"><label style="cursor: pointer;">Remark</label></th>
												<th style="text-align: left;" class="align-left" index="9"><label style="cursor: pointer;">numeric</label></th>
											</tr>
										</thead>
										<tbody>
											<tr index="1">
												<td class="result-no-number">1</td>
												<td class='align-left enableGroupTd' index="0" style=""><input type="text" value="" class="form-control qp-input-text" name="currency" maxlength="" /></td>
												<td class=' enableGroupTd' index="1" style=""><input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="text_label" maxlength="200" /></td>
												<td class=' enableGroupTd' index="2" style=""><input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="text_input" maxlength="200" /></td>
												<td class=' enableGroupTd' index="3" style=""><input style="width: 100%;" type="text" value="" class="form-control qp-input-text" name="integer" maxlength="" /></td>
												<td class=' enableGroupTd' index="4" style=""><input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="datetime" maxlength="19" /></td>
												<td class=' enableGroupTd' index="5" style="">
													<div class="input-group date qp-input-datetimepicker-detail"><span><input type="text" class="form-control" name="date" maxlength="" /></span><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div>
												</td>
												<td class=' enableGroupTd' index="6" style="">
													<div class="input-group date qp-input-timepicker"><span><input type="text" class="form-control" name="time" maxlength="" /></span><span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span></div>
												</td>
												<td class=' enableGroupTd' index="7" style=""><input style="width: 100%;" value="" type="text" class="form-control qp-input-text" name="email" maxlength="200" /></td>
												<td class=' enableGroupTd' index="8" style=""><textarea value="" class="form-control qp-input-textarea" class="form-control qp-input-textarea" name="remark1" maxlength="4000"></textarea></td>
												<td class=' enableGroupTd' index="9" style=""><input style="width: 100%;" type="text" value="" class="form-control qp-input-text" name="numeric" maxlength="" /></td>
											</tr>
										</tbody>
									</table>
							</div>
						</div>
						<div class="qp-div-action">
							<button type="button" class="btn qp-button">Default</button>
							<button type="button" class="btn qp-button-save">Save</button>
							<button type="button" class="btn qp-button-delete" messageId="inf.sys.0014">Delete</button>
							<button type="button" class="btn qp-button-client">Client</button>
							<a class="qp-link-popup-text-color " >Link</a>
						</div>
					</div>
						<div class="" style="width:; float: left;">
							<div class="panel panel-default qp-div-select">
								<div class="panel-heading"><span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text">List Entities</span></div>
								<div class="panel-body">

									<table class="table table-bordered qp-table-list" id="17603">
										<colgroup>
											<col width="35px">
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
											<col width="10%"></col>
										</colgroup>
										<thead>
											<tr index="0">
												<th>No.</th>
												<th style="text-align: left;" class="align-left" index="1"><label style="cursor: pointer;">Text label</label></th>
												<th style="text-align: left;" class="align-left" index="2"><label style="cursor: pointer;">Text input</label></th>
												<th style="text-align: left;" class="align-left" index="3"><label style="cursor: pointer;">Integer</label></th>
												<th style="text-align: left;" class="align-left" index="4"><label style="cursor: pointer;">Datetime</label></th>
												<th style="text-align: left;" class="align-left" index="5"><label style="cursor: pointer;">Date</label></th>
												<th style="text-align: left;" class="align-left" index="7"><label style="cursor: pointer;">Email</label></th>
												<th style="text-align: left;" class="align-left" index="8"><label style="cursor: pointer;">Remark</label></th>
												<th style="text-align: left;" class="align-left" index="9"><label style="cursor: pointer;">numeric</label></th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<tr index="1">
												<td class="result-no-number" style="${projectStyle ['panelListTdNoNumber']}">1</td>
												<td class=' enableGroupTd result-text' index="1" style=""><Label style="" name="text_label" maxlength="200">text_label</label></td>
												<td class=' enableGroupTd result-text' index="2" style=""><Label style="" name="text_input" maxlength="200">text_input</label></td>
												<td class=' enableGroupTd result-numeric' index="3" style=""><Label style="" name="integer" maxlength="10">integer</label></td>
												<td class=' enableGroupTd result-date-time' index="4" style=""><Label style="" name="datetime" maxlength="19">datetime</label></td>
												<td class=' enableGroupTd result-date' index="5" style=""><Label style="" name="date" maxlength="10">date</label></td>
												<td class=' enableGroupTd result-text' index="7" style=""><Label style="" name="email" maxlength="200">email</label></td>
												<td class=' enableGroupTd result-text' index="8" style=""><Label style="" name="remark" maxlength="4000">remark</label></td>
												<td class=' enableGroupTd result-numeric' index="9" style=""><Label style="" name="numeric" maxlength="16">numeric</label></td>
												<td class="result-action-column" style="${projectStyle['panelListTdActionColumn']}"><a href="javascript:" class="btn qp-button glyphicon glyphicon-pencil  qp-link-button qp-link-action"></a></td>
											</tr>
										</tbody>
									</table>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="qp-footer">
				<div type="4" class="pull-left"></div>
				<div type="5" class="pull-right"></div>
			</div>

	</div> <!-- End body panel -->
</div>
</body>
</html>