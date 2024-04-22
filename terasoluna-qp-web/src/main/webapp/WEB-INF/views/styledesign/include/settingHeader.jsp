<script id="text" type="text/template">
	<div name='element'>
		<span class="item" ondblclick="settingText(this)">Text</span>
		<input type="hidden" name="formElement" />
	</div>
</script>
<script id="link" type="text/template">
	<div name='element'>
		<a class="item" href="javascript:;" ondblclick="settingLink(this)">Link</a>
		<input type="hidden" name="formElement" />
	</div>
</script>
<script id="currentUser" type="text/template">
	<div name='element'>
		<span class="item" ondblclick="componentSetting(this)">{Current user}</span>
		<input type="hidden" name="formElement" />
	</div>
</script>
<script id="currentDatetime" type="text/template">
	<div name='element'>
		<span class="item" ondblclick="componentSetting(this)">{Current datetime}</span>
		<input type="hidden" name="formElement" />
	</div>
</script>
<div class="modal-dialog" style="min-height: 500px;width: 900px;">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body" style="min-height: 400px;">
			<table class="design" style="width: 100%;">
				<tr>
					<td>
						<div class="design-header" style="position: relative; border: 0px none #0000FF; margin-bottom: -5px;">
							<div style="margin-top: -4px;">
								<div class="com-header-logo-panel" style="padding-top : 3px;padding-bottom: 3px;">
									<img class="media-object" ondblclick="imageSetting(this)" style="float: left; margin: 2px; padding: 2px; cursor: pointer;" data-src="holder.js/126x36?text=Logo" alt="Logo">
									<input type="hidden" name="formElement" id="logoHidden" />
								</div>
								<div layout-name="logo-header-left" class="text-design" style="position:inherit; bottom: 0px; width: 34%; float: left; ">
									
								</div>
								<div layout-name="logo-header-right" class="text-design" style="bottom: 0px; width: 49%; float: right;">
									
								</div>
							</div>
														
							<div layout-name="header-left" class="text-design" style="position: absolute; bottom: 0px; width: 34%; margin-left:134px">
								
							</div>
							<div layout-name="header-right" class="text-design" style="position: absolute; bottom: 0px; width: 49%; right: 0px;">
								
							</div>
						</div>
						<div class="design-body" style="position: relative;">
							<div style="width: 100%; float: left;">
								<nav class="navbar navbar-default qp-menu-primary">
									<div class="container-fluid navbar-default" style=" position: relative; z-index: 6">
									
										<!-- Brand and toggle get grouped for better mobile display -->
										<div class="navbar-header">
											<button aria-controls="navbar" aria-expanded="false" data-target="#navbar-menu" data-toggle="collapse" class="navbar-toggle collapsed navbar-default" type="button">
									              <span class="sr-only">Toggle navigation</span>
									              <span class="icon-bar" style="color: white; background-color: white;"></span>
									              <span class="icon-bar" style="color: white; background-color: white;"></span>
									              <span class="icon-bar" style="color: white; background-color: white;"></span>
									            </button>
											<a class="navbar-brand" href="#">MENU</a>
										</div>
									
										<!-- Collect the nav links, forms, and other content for toggling -->
										<div class="collapse navbar-collapse" id="navbar-menu">
											
										</div>
									<!-- /.navbar-collapse -->
									</div>
								</nav>
							</div>
							<div style="float: left; width: 100%;">
								<div class="qp-header-main">
										<h4><span class="qp-header-main-text">Home page</span></h4>
								</div>
							</div>
							<div style="width: 400px; float: right;">
								<div item="currentUser" class="element">{Current user}</div>
								<div item="text" class="element">Text</div>
								<div item="currentDatetime" class="element">{Current datetime}</div>
								<div item="link" class="element">Link</div>
							</div>
						</div>
						<div class="design-footer" style="position: relative; border: 0px none #0000FF;">
							<div class="com-header-logo-panel" style="padding-top : 10px; border-top: solid 1.5px #7895CF; margin-top: 10px;"></div>
							<div layout-name="footer-left" class="text-design" style="position: absolute; bottom: 30px; width: 50%; margin: auto; ">
								
							</div>
							<div layout-name="footer-right" class="text-design" style="position: absolute; bottom: 30px; width: 49%; right: 0px; margin: auto;">
								
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="modal-footer">
			<input type="button" onclick="hideHeaderFooterSetting()"
				class="btn qp-button-client"
				value='<qp:message code="sc.sys.0054"></qp:message>' />
		</div>
	</div>
	<!-- /.modal-content -->
</div>