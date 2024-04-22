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
	<link type="text/css" href="media/js/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" />
	<link type="text/css" href="media/css/bootstrap.theme.css" rel="stylesheet" />
	<link type="text/css" href="media/js/customnavbar/bootstrap-submenu.css" rel="stylesheet">
	<link rel="stylesheet" href="media/js/combobox/bootstrap-combobox.css">
	<link type="text/css" href="media/css/common.css" rel="stylesheet" media="screen,print" />
	<link type="text/css" href="media/css/elements.css" rel="stylesheet" />
	<link type="text/css" href="media/css/custom-theme/blue-theme/style.css" rel="stylesheet" id="themeStyle" />
	<link type="text/css" href="media/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
	<link type="text/css" href="media/js/spinedit/bootstrap-spinedit.css" rel="stylesheet" />

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
	<script type="text/javascript" src="media/js/customnavbar/bootstrap-submenu.js" ></script>
	<script type="text/javascript" src="media/js/terasoluna-qp-1.0.0.0.js"></script>
	<script type="text/javascript" src="media/js/terasoluna-qp-init.js"></script>

	

	<link href="media/screendesign/css/style.css" type="text/css" rel="stylesheet" />
	<link href="media/screendesign/css/icon.css" type="text/css" rel="stylesheet" />
	<link href="media/screendesign/css/bootstrap.vertical-tabs.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="media/screendesign/javascript/search.js" ></script>
	<script type="text/javascript" src="media/screendesign/javascript/common.js?r=21"></script>
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
	
		background-image: linear-gradient(to bottom, ${projectStyle['menuBgColor']} 0%, ${projectStyle['menuBgColor']}  100%);   
	
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
@font-face {
  font-family: 'Glyphicons Halflings';

  src: url('./media/fonts/glyphicons-halflings-regular.eot');
  src: url('./media/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'), url('./media/fonts/glyphicons-halflings-regular.woff2') format('woff2'), url('./media/fonts/glyphicons-halflings-regular.woff') format('woff'), url('./media/fonts/glyphicons-halflings-regular.ttf') format('truetype'), url('./media/fonts/glyphicons-halflings-regular.svg#glyphicons_halflingsregular') format('svg');
}

		/* menu brand */
			.navbar-default .navbar-brand  {
			    ${projectStyle['menuBrandColor']}${projectStyle['menuBrandSize']}
			}
			
			/*selected menu*/
			.navbar-default .navbar-nav > .open > a, .navbar-default .navbar-nav > .open > a:hover, .navbar-default .navbar-nav > .open > a:focus {
			    ${projectStyle ['menuSelectedStyle']}
			    <#assign menuSelectedBgColor = projectStyle['menuSelectedBgColor']>
			    <#if menuSelectedBgColor?has_content>
			    	background-image: linear-gradient(to bottom, ${projectStyle ['menuSelectedBgColor']} 0%, ${projectStyle ['menuSelectedBgColor']} 100%);
			    </#if>
			}
			
			/* menu font color*/
			.navbar-default .navbar-nav > li > a {
			    ${projectStyle['menuStyle']}
			}
			.navbar-default .navbar-nav > li > a {
			    ${projectStyle['menuItemStyle']}
			}
			
			.dropdown-menu > li > a:hover, .dropdown-menu > li > a:focus {
			    ${projectStyle ['menuItemHoverStyle']}
			    background-image: linear-gradient(to bottom, ${projectStyle ['menuItemBgHoverStyle']} 0%, ${projectStyle ['menuItemBgHoverStyle']} 100%);
			    background-repeat: repeat-x;
			}
			
			.panel-default>.panel-heading{
				${projectStyle['panelHeader']}
			}
			
			.panel-body{
				${projectStyle['panelBody']}
			}
			
			.qp-table-list{
				${projectStyle['panelListTable']}
			}
			
			.qp-table-list thead tr th{
				${projectStyle['panelListTh']}
			}
			
			.result-text{
				${projectStyle['panelListTdText']}
			}
			
			.result-numeric{
				${projectStyle['panelListTdNumeric']}
			}
			
			.result-date{
				${projectStyle['panelListTdDate']}
			}
			
			.result-date-time{
				${projectStyle['panelListTdDateTime']}
			}
			
			.result-no-number{
				${projectStyle['panelListTdNoNumber']}
			}
			
			.result-action-column{
				${projectStyle['panelListTdActionColumn']}
			}
			
			.qp-table-form{
				${projectStyle['panelTableForm']}
			}
			
			.qp-table-form tr th{
				${projectStyle['panelTableFormTh']}
			}
			
			.qp-table-form tr td{
				${projectStyle['panelTableFormTd']}
			}
			
			.qp-root .qp-footer {
				${projectStyle ['footerStyle']}
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
				$("a." + className).each(function(){
						$(this).fancybox({
					'href' : $(this).attr('href'),
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
				});
				
			}
		};
	$(document).ready(function(){
		$.qp.initalFancybox("qp-link-popup", 'test');
		$.qp.initalFancybox("qp-link-popup-text", 'test');
		$.qp.initalFancybox("qp-button-popup", 'test');
		$('[data-submenu]').submenupicker();
		
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
	
	
	function showConfirmForGenerateHTML(url,obj) {
		var messageConfirm = fcomMsgSource["inf.sys.0015"];
		var onselectConfirm = undefined;
		
		if($(obj).attr("message-string") != undefined){
			messageConfirm =$(obj).attr("message-string");
		} else {
			if($(obj).attr("messageId") != undefined){
				messageConfirm = fcomMsgSource[$(obj).attr("messageId")];
			}
		}
		
		if($(obj).attr("onClick") != undefined){
			onselectConfirm = $(obj).attr("onClick");
		}
		$.qp.initConfirmModels('#fcomConfirmDialog');
		
		var warning = "";
		
		if ($(obj).attr("warning") != undefined) {
			warning = $(obj).attr("warning");
		}
		
		$("#fcomConfirmDialog .modal-body").html(warning + messageConfirm);
		$("#fcomConfirmDialog").modal('show');
		
		$("#fcomConfirmDialog").on("hidden.bs.modal", function() {    // remove the event listeners when the dialog is dismissed
			$(this).parent().find('input[type!=hidden]:first').focus();
		});
		$('#fcomConfirmDialog').on('shown.bs.modal', function() {
			$("#fcomConfirmDialog #fcomConfirmBtnYes").focus();
	    });
		$("#fcomConfirmBtnYes").on("click", function(e) {
			if(onselectConfirm != undefined && typeof(onselectConfirm) == "function"){
				var result = window[onselectConfirm](event);
				if(result == false)
				{
					$("#fcomConfirmDialog").modal('hide');
					return true;
				}
			}
            
			parent.location.href =url;
			
	        });
		$("#fcomConfirmBtnYes").next().on("click", function(e) {
			$("#fcomConfirmBtnYes").unbind();
			
	        });
		$(obj).parent().on('keydown', function(evt) {
			 if (evt.keyCode == $.ui.keyCode.ESCAPE) {
				 $("#fcomConfirmDialog").modal('hide');
				 $('input[type!=hidden]:first').focus();
			 }
	    });
	};
	
	function checkEnableConfirm(url, enableConfirm, messageConfirmString, object){
		if(enableConfirm=="0"){
			parent.location.href =url;
		}
		if(enableConfirm=="1"){
			showConfirmForGenerateHTML(url,object);
			if(showConfirmForGenerateHTML(url,object)) {
				return true;
			} else {
				return false;
			}
		}
	};
	
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
	</style>
<!-- Start header info -->
<div class="com-header-info-panel" style="min-height: 47px;">
	<div class="com-header-logo-panel" style="width: 17%; float: left; padding-top: 12px; height: 100%;">
		<div style="float: left; ">
			<#if !(projectStyle['logo'])?has_content>
				<a href="${urlHomePage}.html">
					<img src="media/images/logo_header_01.png" style="border: 0px; height: 25px; ${projectStyle['logoHeight']} ${projectStyle['logoWidth']}" />
				</a>
			<#else>
				<#if (projectStyle['logoHeight']?has_content && !projectStyle['logoWidth']?has_content) || (!projectStyle['logoHeight']?has_content && projectStyle['logoWidth']?has_content) 
						|| (projectStyle['logoHeight']?has_content && projectStyle['logoWidth']?has_content)>
					<#assign height = projectStyle['logoHeight'] >
					<#assign width = projectStyle['logoWidth'] >
				<#else>
					<#assign height = "height : 25px;" >
					<#assign width = "width : 162px" >
				</#if>
				
				<a href="${urlHomePage}.html"><img src="data:image/jpeg;base64,${projectStyle['logo'] }" style="border: 0px; ${height} ${width}" /> </a>
			</#if>
		</div>
	</div>
	<div class="com-header-logo-panel" style="width: 83%; float: left; padding-top: 12px; height: 50%;">
		<div type="0" style="float: left;  width: 38%;">&nbsp;
			<#if listLogoLeft?has_content>
				<#list listLogoLeft as logoLeft>
					<#if logoLeft.itemType == 0>
						<#if (logoLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${logoLeft.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${logoLeft.messageString}</span>
						</#if>
					</#if>
					<#if logoLeft.itemType == 1>
						<#if (logoLeft.style)?has_content && (logoLeft.hoverStyle)?has_content>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}; ${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}; ${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							</#if>
							
						<#elseif (logoLeft.style)?has_content && !(logoLeft.hoverStyle)?has_content>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
							</#if>
							
						<#elseif !(logoLeft.style)?has_content && (logoLeft.hoverStyle)?has_content>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							</#if>
						<#else>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if logoLeft.itemType == 2>
						<#if logoLeft.componentType == 1 || logoLeft.componentType == 0>
							<#if (logoLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (logoLeft.style)?has_content>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								</#if>
							<#else>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
						
					</#if>
					<#if logoLeft.itemType == 3>
						<#if logoLeft.componentType == 1 || logoLeft.componentType == 0>
							<#if (logoLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (logoLeft.style)?has_content>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								</#if>
							<#else>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if logoLeft.itemType == 4>
						<#if (logoLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
			</#if>
		</div>
		<div type="1" style="float: right;  width: 38%; text-align: right;">&nbsp;
			<#if listLogoRight?has_content>
				<#list listLogoRight as logoRight>
					<#if logoRight.itemType == 0>
						<#if (logoRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${logoRight.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${logoRight.messageString}</span>
						</#if>
					</#if>
					<#if logoRight.itemType == 1>
						<#if (logoRight.style)?has_content && (logoRight.hoverStyle)?has_content>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}; ${logoRight.hoverStyle}">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}; ${logoRight.hoverStyle}">${logoRight.messageString}</a>
							</#if>
							
						<#elseif (logoRight.style)?has_content && !(logoRight.hoverStyle)?has_content>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
							</#if>
							
						<#elseif !(logoRight.style)?has_content && (logoRight.hoverStyle)?has_content>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.hoverStyle}">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.hoverStyle}">${logoRight.messageString}</a>
							</#if>
							
						<#else>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if logoRight.itemType == 2>
						<#if logoRight.componentType == 1 || logoRight.componentType == 0>
							<#if (logoRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (logoRight.style)?has_content>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								</#if>
							<#else>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if logoRight.itemType == 3>
						<#if logoRight.componentType == 1 || logoRight.componentType == 0>
							<#if (logoRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (logoRight.style)?has_content>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								</#if>
							<#else>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								</#if>
							</#if>
						</#if>
					</#if>
					<#if logoRight.itemType == 4>
						<#if (logoRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
			</#if>
		</div>
	</div>
	<div class="com-header-button-panel" style="width: 83%; float: left; height: 50%;">
		<div type="2" style="width: 48%; float: left;">&nbsp;
			<#if listHeaderLeft?has_content>
				<#list listHeaderLeft as headerLeft>
					<#if headerLeft.itemType == 0>
						<#if (headerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${headerLeft.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${headerLeft.messageString}</span>
						</#if>
					</#if>
					<#if headerLeft.itemType == 1>
						<#if (headerLeft.style)?has_content && (headerLeft.hoverStyle)?has_content>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}; ${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}; ${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							</#if>
							
						<#elseif (headerLeft.style)?has_content && !(headerLeft.hoverStyle)?has_content>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
							</#if>
							
						<#elseif !(headerLeft.style)?has_content && (headerLeft.hoverStyle)?has_content>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							</#if>
							
						<#else>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
							</#if>
						</#if>
					</#if>
					<#if headerLeft.itemType == 2>
						<#if headerLeft.componentType == 1 || headerLeft.componentType == 0>
							<#if (headerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (headerLeft.style)?has_content>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								</#if>
								
							<#else>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if headerLeft.itemType == 3>
						<#if headerLeft.componentType == 1 || headerLeft.componentType == 0>
							<#if (headerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (headerLeft.style)?has_content>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								</#if>
							<#else>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								</#if>
							</#if>
						</#if>
					</#if>
					<#if headerLeft.itemType == 4>
						<#if (headerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
			</#if>
		</div>
		<div type="3" style="width: 48%; float: right; text-align: right;">
			<#if listHeaderRight?has_content>
					<#list listHeaderRight as headerRight>
						<#if headerRight.itemType == 0>
							<#if (headerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${headerRight.messageString}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${headerRight.messageString}</span>
							</#if>
						</#if>
						<#if headerRight.itemType == 1>
							<#if (headerRight.style)?has_content && (headerRight.hoverStyle)?has_content>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}; ${headerRight.hoverStyle}">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}; ${headerRight.hoverStyle}">${headerRight.messageString}</a>
								</#if>
								
							<#elseif (headerRight.style)?has_content && !(headerRight.hoverStyle)?has_content>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
								</#if>
								
							<#elseif !(headerRight.style)?has_content && (headerRight.hoverStyle)?has_content>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.hoverStyle}">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.hoverStyle}">${headerRight.messageString}</a>
								</#if>
								
							<#else>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
								</#if>
								
							</#if>
						</#if>
						<#if headerRight.itemType == 2>
							<#if headerRight.componentType == 1 || headerRight.componentType == 0>
								<#if (headerRight.style)?has_content>
									<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${userName}</span>
								<#else>
									<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
								</#if>
							<#else>
								<#if (headerRight.style)?has_content>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									</#if>
									
								<#else>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									</#if>
									
								</#if>
							</#if>
						</#if>
						<#if headerRight.itemType == 3>
							<#if headerRight.componentType == 1 || headerRight.componentType == 0>
								<#if (headerRight.style)?has_content>
									<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${dateTime}</span>
								<#else>
									<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
								</#if>
							<#else>
								<#if (headerRight.style)?has_content>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									</#if>
									
								<#else>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									</#if>
									
								</#if>
							</#if>
						</#if>
						<#if headerRight.itemType == 4>
							<#if (headerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
							
						</#if>
					</#list>
				</#if>
		</div>
	</div>
</div>
<!-- End header info -->

<!-- Start Menu panel -->
${menuContent}
<!-- Start body container -->
<div class="qp-body-container">
	<div class="qp-breadcrumb">
		<ul class="breadcrumb">
			<li><a href="${urlHomePage}.html"><i class="qp-link-header-icon glyphicon glyphicon-home"></i></a></li>
		</ul>
	</div>
	<div class="qp-header-main">
		
		<em style="text-align: right;"></em>
		<p style="float:right;">The time on the server is&nbsp;October 7, 2015 11:42:16 AM ICT.</p>
		<p style="float:right; padding-right: 15px; font-weight: bold;">User name : </p></div>
		<!-- Start content -->
		<div class="qp-body">
			<div id="wrapper" style="text-align: center; height: 50px;">
			<h1>Welcome to ${projectName}</h1>
		</div>
		<div class="qp-footer pull-left" style="width:100%">
		<div type="4" class="pull-left">
			<#if listFooterLeft?has_content>
				<#list listFooterLeft as footerLeft>
					<#if footerLeft.itemType == 0>
						<#if (footerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${footerLeft.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${footerLeft.messageString}</span>
						</#if>
					</#if>
					<#if footerLeft.itemType == 1>
						<#if (footerLeft.style)?has_content && (footerLeft.hoverStyle)?has_content>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}; ${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}; ${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							</#if>
							
						<#elseif (footerLeft.style)?has_content && !(footerLeft.hoverStyle)?has_content>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
							</#if>
							
						<#elseif !(footerLeft.style)?has_content && (footerLeft.hoverStyle)?has_content>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							</#if>
							
						<#else>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if footerLeft.itemType == 2>
						<#if footerLeft.componentType == 1 || footerLeft.componentType == 0>
							<#if (footerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (footerLeft.style)?has_content>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if footerLeft.itemType == 3>
						<#if footerLeft.componentType == 1 || footerLeft.componentType == 0>
							<#if (footerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (footerLeft.style)?has_content>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if footerLeft.itemType == 4>
						<#if (footerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
				
			<#else>
				<ul class="pull-left">
					<li><a href="">About this Site</a></li>
					<li><a href="">Contact Us</a></li>
				</ul>
			</#if>
		</div>
			
		<div type="5" class="pull-right">
			<#if listFooterRight?has_content>
				<#list listFooterRight as footerRight>
					<#if footerRight.itemType == 0>
						<#if (footerRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${footerRight.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${footerRight.messageString}</span>
						</#if>
					</#if>
					<#if footerRight.itemType == 1>
						<#if (footerRight.style)?has_content && (footerRight.hoverStyle)?has_content>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}; ${footerRight.hoverStyle}">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}; ${footerRight.hoverStyle}">${footerRight.messageString}</a>
							</#if>
							
						<#elseif (footerRight.style)?has_content && !(footerRight.hoverStyle)?has_content>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
							</#if>
							
						<#elseif !(footerRight.style)?has_content && (footerRight.hoverStyle)?has_content>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.hoverStyle}">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.hoverStyle}">${footerRight.messageString}</a>
							</#if>
							
						<#else>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if footerRight.itemType == 2>
						<#if footerRight.componentType == 1 || footerRight.componentType == 0>
							<#if (footerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (footerRight.style)?has_content>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								</#if>
							</#if>
						</#if>
					</#if>
					<#if footerRight.itemType == 3>
						<#if footerRight.componentType == 1 || footerRight.componentType == 0>
							<#if (footerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (footerRight.style)?has_content>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if footerRight.itemType == 4>
						<#if (footerRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
				
			<#else>
				<address class="pull-right">Copyright © 2015 NTT DATA Corporation</address>
			</#if>
		</div>
	</div>
		<!-- End body panel -->
</div>
		<!-- End body panel -->
	</div>
</body>
</html>