<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
		<!-- load css for color picker -->
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/colorpicker/js/bootstrap-colorpicker.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$('.qp-input-pickcolor').colorpicker();
			});
			var parentWindow = window.parent.document;
			function fcomCloseDeleteComplete() {
				$(parentWindow).find('.fancybox-overlay').hide();
				parentWindow.location.reload();
			}
			function onSelectBlock(thiz, blockname) {
				var blockid = "#theme-setting-" + blockname;
				$("#tbl_theme_setting").find("table").each(function() {
					$(this).hide();
				});
				$("#tbl_theme_setting").find(blockid).show();
				$(thiz).parent().find("li").each(function() {
					$(this).find("label").addClass("styledesign-label");
				});
				$(thiz).find("label").removeClass("styledesign-label");
			}
			$(document).ready(function() {
				$("#tbl_theme_setting").find("table").each(function() {
					$(this).hide();
				});
				$("#tbl_theme_setting").find("#theme-setting-General").show();
			});
		</script>
		<style>
		.styledesign-label-active {
			font-weight: bold;
		}

		.styledesign-label {
			font-weight: normal;
			cursor: pointer;
		}
</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-name"> 
            <qp:message code="sc.accounttheme.0001"></qp:message>
    </tiles:putAttribute>
    <tiles:putAttribute name="header-link">
    	<qp:authorization permission="accountprofileSetdefaulttheme">
			<a class="" href="${pageContext.request.contextPath}/accountprofile/setDefaultTheme"><qp:message code="sc.accounttheme.0008"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="accountThemeForm" action="${pageContext.request.contextPath}/accountprofile/modifyTheme">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text">Theme information</span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl_theme_setting">
						<colgroup>
							<col width="20%" />
							<col width="80%" />
						</colgroup>
						<tr>
							<th style="vertical-align: top;">
								<div style="width: 100%; text-align: left">
									<div class="well well-lg" style="background-color: #FFFFFF; background-image: none; height: 100%; margin-bottom: 0px; padding: 10px;">
										<ul class="nav ">
											<li onclick="onSelectBlock(this,'General')"><label class="tree-toggler nav-header ">General</label></li>
											<li class="nav-divider"></li>
											<!-- <li onclick="onSelectBlock(this,'header-footer')"><label class="tree-toggler nav-header styledesign-label">Header & footer</label></li>
											<li class="nav-divider"></li> -->
											<li onclick="onSelectBlock(this,'Menu')"><label class="tree-toggler nav-header styledesign-label">Menu</label></li>
											<li class="nav-divider"></li>
											<li onclick="onSelectBlock(this,'Content-Header')"><label class="tree-toggler nav-header styledesign-label">Content: Header</label></li>
											<li class="nav-divider"></li>
											<li onclick="onSelectBlock(this,'Content-Panel')"><label class="tree-toggler nav-header styledesign-label">Content: Panel</label></li>
											<li class="nav-divider"></li>
											<li onclick="onSelectBlock(this,'Content-List')"><label class="tree-toggler nav-header styledesign-label">Content: List</label></li>
											<li class="nav-divider"></li>
											<li onclick="onSelectBlock(this,'Content-Table')"><label class="tree-toggler nav-header styledesign-label">Content: Table</label></li>
											<li class="nav-divider"></li>
											
										</ul>
									</div>
								</div>
							</th>
							<td style="vertical-align: top;">
								<table class="table table-bordered qp-table-form" id="theme-setting-General">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Common</th>
									</tr>
									<tr>
										<th>screen-size</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['common-screen-size']">
												<form:option value=""></form:option>
												<form:option value="1900">1920x1080</form:option>
												<form:option value="1346">1366x768</form:option>
												<form:option value="1260">1280x1024</form:option>
												<form:option value="1004">1024x768</form:option>
											</form:select>
										</td>
										<th colspan="2"></th>
									</tr>
<%-- 									<tr>
										<th>font-family</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['common-font-family']">
													<form:option value="">-- Select font --</form:option>
													<form:option value="serif">Serif</form:option>
													<form:option value="cursive">Cursive</form:option>
													<form:option value="fantasy">Fantasy</form:option>
													<form:option value="sans-serif" selected="selected">Sans-serif</form:option>
													<form:option value="monospace">Monospace</form:option>
											</form:select>
										</td>
										<th>font-size</th>
										<td style="vertical-align: top;">
											<div class="input-group">
												<form:input path="mapTheme['common-font-size']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr> --%>
									<tr>
										<th>background-image</th>
										<td><input type="file" name="fileUpload" class="qp-input-file"></td>
										<th>background-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"><i></i></span>
												<form:input path="mapTheme['common-background-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<c:if test='${not empty styleDesignForm.mapTheme["commom-background-image"]}'>
										<tr>
											<th>background-image preview</th>
											<td>
												
												<img width="100" height="100" src='data:image/png;base64,${styleDesignForm.mapTheme["commom-background-image"]}' />
											</td>
											<th></th>
											<td>
												
											</td>
										</tr>
									</c:if>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Save Button</th>
									</tr>
									<tr>
										<th>common-button-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"><i></i></span>
												<form:input path="mapTheme['common-button-bg-color']" class="form-control" />
											</div>
										</td>
										<th>common-button-bg-active-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['common-button-bg-active-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<th>common-button-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span> 
												<form:input path="mapTheme['common-button-text-color']" class="form-control" />
											</div>
										</td>
										<th colspan="2"></th>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Delete Button</th>
									</tr>
									<tr>
										<th>common-button-delete-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"><i ></i></span>
												<form:input path="mapTheme['common-button-delete-bg-color']" class="form-control" />
											</div>
										</td>
										<th>common-button-delete-bg-active-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i ></i></span>
												<form:input path="mapTheme['common-button-delete-bg-active-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<th>common-button-delete-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['common-button-delete-text-color']" class="form-control" />
											</div>
										</td>
										<th colspan="2"></th>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Client Button</th>
									</tr>
									<tr>
										<th>client-button-delete-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"><i ></i></span>
												<form:input path="mapTheme['client-button-delete-bg-color']" class="form-control" />
											</div>
										</td>
										<th>client-button-delete-bg-active-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i ></i></span>
												<form:input path="mapTheme['client-button-delete-bg-active-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<th>client-button-delete-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['client-button-delete-text-color']" class="form-control" />
											</div>
										</td>
										<th colspan="2"></th>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Link</th>
									</tr>
									<tr>
										<th>common-link-popup-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['common-link-popup-text-color']" class="form-control" />
											</div>
										</td>
										<th colspan="2"></th>
									</tr>
								</table>
								<table class="table table-bordered qp-table-form" id="theme-setting-Menu">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Menu</th>
									</tr>
									<tr>
										<th>menu-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['menu-bg-color']" class="form-control" />
											</div>
										</td>
										<th></th>
										<td>
											
										</td>
									</tr>
									<tr>
										<th>menu-brand-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['menu-brand-color']" class="form-control" />
											</div>
										</td>
										<th>menu-brand-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['menu-brand-size']" class="form-control qp-input-integer" />
												<span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>menu-font-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['menu-font-color']" class="form-control" />
											</div>
										</td>
										<th>menu-font-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['menu-font-size']" class="form-control qp-input-integer" />
												<span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>menu-selected-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['menu-selected-bg-color']" class="form-control" />
											</div>
										</td>
										<th>menu-selected-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['menu-selected-text-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Menu item</th>
									</tr>
									<tr>
										<th>item-menu-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['item-menu-bg-color']" class="form-control" />
											</div>
										</td>
										<th></th>
										<td>
											
										</td>
									</tr>
									<tr>
										<th>item-menu-font-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['item-menu-font-color']" class="form-control" />
											</div>
										</td>
										<th>item-menu-font-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['item-menu-font-size']" class="form-control qp-input-integer" />
												<span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>item-menu-hover-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['item-menu-hover-bg-color']" class="form-control" />
											</div>
										</td>
										<th>item-menu-hover-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['item-menu-hover-text-color']" class="form-control" />
											</div>
										</td>
									</tr>
								</table>
								<table class="table table-bordered qp-table-form" id="theme-setting-Content-Header">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Header title</th>
									</tr>
									<tr>
										<th>header-title-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['header-title-color']" class="form-control" />
											</div>
										</td>
										<th>header-title-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['header-title-size']" class="form-control qp-input-integer" />
												<span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Header link</th>
									</tr>
									<tr>
										<th>header-link-position</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['header-link-position']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
										<th>header-link-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['header-link-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<th>header-link-font-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['header-link-font-size']" class="form-control qp-input-integer" />
												<span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
										<th>header-title-position</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['header-title-position']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
											</form:select>
										</td>
									</tr>
								</table>
								<table class="table table-bordered qp-table-form" id="theme-setting-Content-Panel">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Header</th>
									</tr>
									<tr>
										<th>panel-header-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['panel-header-background-color']" class="form-control" />
											</div>
										</td>
										<th>panel-header-height</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-header-height']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>panel-header-text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['panel-header-color']" class="form-control" />
											</div>
										</td>
										<th>panel-header-text-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-header-font-size']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Body</th>
									<tr>
										<th>panel-body-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span> <form:input path="mapTheme['panel-body-background-color']" class="form-control" />
											</div>
										</td>
										<th>panel-body-padding</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-body-padding']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
								</table>
								<table class="table table-bordered qp-table-form" id="theme-setting-Content-List">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Table Style</th>
									</tr>
									<tr>
										<th>panel-table-list-cell-padding</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-list-table-padding']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
										<th>panel-table-list-cell-spacing</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-list-table-border-spacing']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>panel-table-list-border</th>
										<td><form:input cssClass="form-control qp-input-text" path="mapTheme['panel-list-table-border']" /></td>
										<th></th>
										<td></td>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">TH style</th>
									</tr>
									<tr>
										<th>panel-table-list-th-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input path="mapTheme['panel-list-th-background-color']" class="form-control" />
											</div>
										</td>
										<th>panel-table-list-th-font-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-list-th-font-size']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">TD style</th>
									</tr>
									<tr>
										<th>text</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-list-td-text']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
										<th>numeric</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-list-td-numeric']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
									</tr>
									<tr>
										<th>date</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-list-td-date']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
										<th>date time</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-list-td-date-time']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
									</tr>
									<tr>
										<th>no-number</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-list-td-no-number']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
										<th>action column</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-list-td-action-column']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
									</tr>																		
								</table>
								<table class="table table-bordered qp-table-form" id="theme-setting-Content-Table">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Table style</th>
									</tr>
									<tr>
										<th>panel-table-form-cell-padding</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-table-form-table-padding']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
										<th>panel-table-form-cell-spacing</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-table-form-table-border-spacing']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>panel-table-form-border</th>
										<td><form:input cssClass="form-control qp-input-text" path="mapTheme['panel-table-form-table-border']" /></td>
										<th colspan="2"></th>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">TH style</th>
									</tr>
									<tr>
										<th>panel-table-form-th-font-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-table-form-th-font-size']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
										<th>panel-table-form-th-bg-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i style="background-color: rgb(239, 245, 249);"></i></span>
												<form:input path="mapTheme['panel-table-form-th-background-color']" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<th>panel-table-form-th-text-position</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-table-form-th-text-align']">
												<form:option value=""></form:option>
												<form:option value="left">left</form:option>
												<form:option value="right">right</form:option>
												<form:option value="center">center</form:option>
												<form:option value="justify">justify</form:option>
												<form:option value="initial">initial</form:option>
											</form:select>
										</td>
										<th></th>
										<td></td>
									</tr>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">TD style</th>
									</tr>
									<tr>
										<th>panel-table-form-td-font-size</th>
										<td>
											<div class="input-group">
												<form:input path="mapTheme['panel-table-form-td-font-size']" class="form-control qp-input-integer" /> <span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
										<th></th>
										<td></td>
									</tr>
								</table>
								<table class="table table-bordered qp-table-form" id="theme-setting-header-footer">
									<colgroup>
										<col width="20%" />
										<col width="30%" />
										<col width="20%" />
										<col width="30%" />
									</colgroup>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Header style</th>
									</tr>
									<tr>
										<th>logo</th>
										<td><input type="file" name="logoUpload" class="qp-input-file"></td>
										<th>Position</th>
										<td>
											<form:select cssClass="form-control qp-input-select" path="mapTheme['panel-table-list-td-anotherChild-position']">
												<form:option value=""></form:option>
												<form:option selected="selected" value="left">left</form:option>
												<form:option value="right">right</form:option>											
											</form:select>											
										</td>
																				
									</tr>
									<tr>
										<th>Size option</th>
										<td>
											<label class="radio-inline">
												<input type="radio" value="0" class="qp-input-checkbox qp-input-checkbox-margin" name="sizeOption" id="fixSize" onclick="onClickSizeOption(this)">Fix size</label>
											<label class="radio-inline">
												<input type="radio" value="1" checked="checked" class="qp-input-checkbox qp-input-checkbox-margin" name="sizeOption" id="nonFixSize" onclick="onClickSizeOption(this)">Non fix</label>
										</td>										
										<th id="size-header">Size(Height - Width)</th>
										<td>											
											<div class="input-group" style="width: 45%;  float: left;" id="height-group">
												<form:input class="form-control qp-input-integer" path="mapTheme['panel-table-list-td-firstChild-height']" />																								
												<span class="hex-pound input-group-addon">px</span>
											</div>
											<div class="input-group" style="width: 10%;  float: left;  text-align: center;" id="span-group">
												<span>-</span>
											</div>
											<div class="input-group" style="width: 45%; float: right;" id="width-group" >
												<form:input class="form-control qp-input-integer" path="mapTheme['panel-table-list-td-firstChild-width']" />
												<span class="hex-pound input-group-addon">px</span>
											</div>																					
										</td>
									</tr>
									<c:if test='${not empty styleDesignForm.mapTheme["logo"]}'>
										<tr>
											<th>Logo preview</th>
											<td>
												
												<img width="100" height="100" src='data:image/png;base64,${styleDesignForm.mapTheme["logo"]}' />
											</td>
											<th></th>
											<td>
												
											</td>
										</tr>
									</c:if>
									<tr>
										<th colspan="4" style="background-color: #FAF5EE; text-align: left;">Footer style</th>
									</tr>
									<tr>
										<th>text-color</th>
										<td>
											<div class="input-group qp-input-pickcolor colorpicker-element">
												<span class="input-group-addon"> <i></i></span>
												<form:input class="form-control" path="mapTheme['footer-text-color']"/>
											</div>
										</td>
										<th>text-size</th>
										<td style="vertical-align: top;">
											<div class="input-group">
												<form:input path="mapTheme['footer-text-size']" class="form-control qp-input-integer"/>
												<span class="hex-pound input-group-addon">px</span>
											</div>
										</td>
									</tr>
									<tr>
										<th colspan="1" style="background-color: #FAF5EE; text-align: left;">Header & footer design</th>
										<td colspan="3" style="background-color: #FAF5EE; text-align: left;">
											<span class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action" style="float: left; margin-right: 5px; cursor: pointer;" onclick="settingHeader(this)"></span>
											<input type="hidden" id="form-element" name="formElement" value="${fn:escapeXml(projectItem)}" />
										</td>
									</tr>									
								</table>								
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="accountprofileModifytheme">
					<script>
							function addUnit(obj) {
								$(obj).closest('#styleDesignForm').find('.input-group-addon').each(function() {
									$(this).prev().val($(this).prev().val() + 'px');
								});
							}
						</script>
						<button type="button" class="btn qp-button qp-dialog-confirm" on-yes="addUnit"><qp:message code="sc.sys.0031"></qp:message></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>