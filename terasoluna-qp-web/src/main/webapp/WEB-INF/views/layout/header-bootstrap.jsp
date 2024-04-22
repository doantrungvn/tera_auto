<div class="row">
	<div class="col-md-12 com-header-info-panel">
		<!-- Start logo panel -->
		<div class="com-header-logo-panel" style="margin-top: 10px;">
			<a href="#"><img
				src="${pageContext.request.contextPath}/resources/media/images/logo_header_01.png"
				style="border: 0px; height: 25px;" /> </a>
		</div>
		<!-- End logo panel -->

		<!-- Start header button -->
		<div class="com-header-button-panel">
			<%-- 
				<div class="fr">
					<a href='<c:url value="${pageContext.request.contextPath}/logout" />' ><img src="${pageContext.request.contextPath}/resources/media/images/logout.png" style="border: 0px; height: 12px;"/>&nbsp;&nbsp;Logout</a>
				</div>
				
				<!-- start multi language module -->	
				<div style="padding-left: 50px; padding-right: 50px;" class="fr">
					<ul class="topnav">
						<li>
					        <a href="javascript:"><img src="${pageContext.request.contextPath}/resources/media/images/en_US.png" style="border: 0px;"/>&nbsp;&nbsp;English</a>
					    	<ul class="subnav style-header-table">
			            		<li>
			            			<a onclick="$.fc.changeSystemLanguage('jp_JP')" href="javascript:"><img src="${pageContext.request.contextPath}/resources/media/images/ja_JP.png" style="border: 0px;"/>&nbsp;&nbsp;Japan</a>
			            		</li>
					        </ul>
					    </li>
					</ul> 
				</div>
				--%>
			<!-- end multi language module -->

			<!-- start theme module -->
			<c:if test="${not empty applicationScope['clmDefaultTheme']}">
				<script type="text/javascript">
					$(document)
							.ready(
									function() {
										$(".colorblue")
												.click(
														function() {
															var url = CONTEXT_PATH
																	+ '/SCOM/SCOM0910BL.do?themeCd=blue&r='
																	+ Math
																			.random();
															$
																	.ajax({
																		url : url,
																		data : null,
																		success : function(
																				page) {
																		}
																	});
															$("link#themeUI")
																	.attr(
																			"href",
																			"media/css/custom-theme/blue-theme/bootstrap.theme.css");
															$("link#themeStyle")
																	.attr(
																			"href",
																			"media/css/custom-theme/blue-theme/style.css");
															$(this)
																	.parents()
																	.find(
																			"ul.subnav")
																	.css(
																			"display",
																			"none");
															return false;
														});
									});
				</script>
			</c:if>
			<%--
				<div class="fr">
					<ul class="topnav">
					    <li>
					    	<a href="javascript:"><img src="${pageContext.request.contextPath}/resources/media/images/Theme-icon.png" style="border: 0px;" height="12px" width="20px"/><span>&nbsp;&nbsp;Theme</span></a>
					    	<ul class="subnav style-header-table">
					        	<li><a class="colorbox colorblue" href="?theme=blue"><span class="theme-blue">&nbsp;</span>&nbsp;&nbsp;Blue</a></li>
					        	<li><a class="colorbox colorsilver" href="?theme=silver"><span class="theme-silver">&nbsp;</span>&nbsp;&nbsp;Silver</a></li>
					        </ul>
					    </li>
					</ul> 
				</div>		
				 --%>
			<!-- end theme module -->

			<!-- start user information -->
			<div class="fl" style="padding-top: 22px;">
				<c:if test="${pageContext['request'].userPrincipal != null}">
					<sec:authentication var="user" property="principal" />
					Welcome <a class="com-link-popup" href="javascript:">${user.username}</a>
				</c:if>
			</div>
			<!-- end user information -->
		</div>
		<!-- End header button -->

	</div>
	<!-- End header info -->

	<!-- Start Menu panel -->

	<nav class="col-md-12 navbar navbar-default qp-menu-primary">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar-menu">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" style="font-size: 12px;">TERASOLUNA
					QP</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="navbar-menu">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Project
							<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Project management</a></li>
							<li class="divider"></li>
							<li><a href="#">Module management</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Upper design<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Domain design</a></li>
							<li class="divider"></li>
							<li><a href="#">Table design</a></li>
							<li><a href="#">View design</a></li>
							<li><a href="#">Graphic database design</a></li>
							<li class="divider"></li>
							<li><a href="#">Domain datatype mapping</a></li>
							<li class="divider"></li>
							<li><a href="#">Screen design</a></li>
							<li class="divider"></li>
							<li><a href="#">Business logic design</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Other Design<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a
								href="${pageContext.request.contextPath}/businesstype/search">Business
									type</a></li>
							<li class="divider"></li>
							<li><a href="#">Codelist</a></li>
							<li class="divider"></li>
							<li><a href="#">Autocomplete</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Administrator
							<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a
								href="${pageContext.request.contextPath}/account/search">Account
									management</a></li>
							<li class="divider"></li>
							<li><a href="#">Role management</a></li>
							<li class="divider"></li>
							<li><a
								href="${pageContext.request.contextPath}/messages/search">Language
									management</a></li>
						</ul></li>
					<!-- <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>  -->
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false"><span
							class="glyphicon glyphicon-text-size" style="font-size: 20px;"></span><span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a class="colorbox colorblue" href="?theme=blue"><span
									class="theme-blue">&nbsp;</span>&nbsp;&nbsp;Blue</a></li>
							<li><a class="colorbox colorsilver" href="?theme=silver"><span
									class="theme-silver">&nbsp;</span>&nbsp;&nbsp;Silver</a></li>
						</ul></li>
					<li class="dropdown"><a href="javascript:"
						class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-expanded="false"><span class="glyphicon glyphicon-cog"
							style="font-size: 20px;"></span> <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a onclick="$.fc.changeSystemLanguage('jp_JP')"
								href="javascript:"><img
									src="${pageContext.request.contextPath}/resources/media/images/en_US.png"
									style="border: 0px;" /></a></li>
							<li><a onclick="$.fc.changeSystemLanguage('jp_JP')"
								href="javascript:"><img
									src="${pageContext.request.contextPath}/resources/media/images/ja_JP.png"
									style="border: 0px;" /></a></li>
						</ul></li>
					<li><a href="#" class="glyphicon glyphicon-log-out"
						style="font-size: 20px;"></a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-->
	</nav>

	<!-- End Menu panel -->
</div>