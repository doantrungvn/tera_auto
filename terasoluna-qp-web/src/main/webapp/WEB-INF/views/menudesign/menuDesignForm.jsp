<tiles:insertDefinition name="layouts">

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.menudesign"></qp:message></span></li>
		<li><span><qp:message code="sc.menudesign.0008" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<link
			href="${pageContext.request.contextPath}/resources/app/menudesign/css/style.css"
			type="text/css" rel="stylesheet" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/app/menudesign/javascript/menudesign.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script>
			$(document).ready(function() {
				reIndexAllRowOfMenu();
				initialSortableOfMenu();
				clearActionParentMenu();
				removeIconDeleteFistRow();
			});
			
			var MAX_NESTED_OBJECT = '${CL_SYSTEM_SETTING["maxNestedObject"]}';
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<script id="tbl_list_details-template" type="text/template">
				<tr id="rankOne" data-ar-rgroup="\${groupId}" class="ar-dataRow" data-ar-templateid="tbl_list_details-template">
					<td>
						<input id="itemSeqNo" name="listMenuDesignItem[0].itemSeqNo" type="hidden" value="">
						<input id="menuItemId" name="listMenuDesignItem[0].menuItemId" type="hidden" value="">
						<input id="parentMenuItemId" name="listMenuDesignItem[0].parentMenuItemId" type="hidden" value="">
						<input id="menuItemType" name="listMenuDesignItem[0].menuItemType" type="hidden" value="0">
						<input id="actionUrlCode" name="listMenuDesignItem[0].actionUrlCode" type="hidden">
						<div style="float: left; width: 100%" id="fistDiv">
							<div class="input-group set-width" style="width: 100%; float: left;">
								<span class="input-group-addon" style="padding-right: 0px;">
									<div class="dropdown">
											<a href="javascript:" style="margin-top: 3px; cursor: move;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort" title="Move"></a>
											<button class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" data-toggle="dropdown"></button>
											 <ul class="dropdown-menu doropdow-menu-desgin">
											 	<li><a onclick="$.qp.addRow({type:'menu',link:this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'after'}});"><qp:message code="sc.menudesign.0009" /></a></li>
											 	<li><a onclick="$.qp.addRow({type:'separator',link:this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'after'}});"><qp:message code="sc.menudesign.0010" /></a></li>
											 </ul>
										</div>
								</span>
								<div id="inputmenuName">
									<input type="text" class="form-control" value="" name="listMenuDesignItem[].menuName" style="width:  100%; height: 25.2px" maxlength="255">
								</div>
								<span class="input-group-addon" style="padding-right: 0px; padding-left: 0px;"><a class="btn btn-default btn-xs glyphicon glyphicon-circle-arrow-right qp-button-action" 
									onclick="$.qp.addRow({link: this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroupindex') },position:{anchor:$(this).closest('tr'),string:'after'}})" style="top: 0px;" href="javascript:void(0)"></a></span>
							</div>
						</div>
					</td>
					<td><qp:autocomplete name="listMenuDesignItem[].screenId" 
										optionValue="optionValue" 
										optionLabel="optionLabel" 
										selectSqlId="autocompleteGetScreenListForMenuDesign" 
										arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
										arg02="20"
										arg03="${menuDesignForm.languageId}"
										value="" 
										onChangeEvent="setActionUrlCode"
										displayValue="" 
										mustMatch="true"
										maxlength="200" /><input type="hidden" name="checkParent"></td>
					<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove message design" onclick="$.qp.removeRowJS({link:this})" style="top: 0px;" href="javascript:void(0)"></a></td>
				</tr>
			</script>

		<script id="action-template" type="text/template">
				<qp:autocomplete name="listMenuDesignItem[].screenId" 
										optionValue="optionValue" 
										optionLabel="optionLabel" 
										selectSqlId="autocompleteGetScreenListForMenuDesign" 
										arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
										arg02="20"
										arg03="${menuDesignForm.languageId}"
										value="" 
										onChangeEvent="setActionUrlCode"
										displayValue="" 
										mustMatch="true"
										maxlength="200" />
			</script>

		<form:form method="post"
			action="${pageContext.request.contextPath}/menudesign/modify"
			modelAttribute="menuDesignForm">
			<input type="hidden" name="jsonMenuDesignItem" />
			<form:hidden path="menuId" />
			<form:hidden path="updatedDate" />
			<form:hidden path="projectId"/>
			<form:hidden path="languageId"/>
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>"
				element="div" cssStyle="" />

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message
							code="sc.messagedesign.0007" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<%-- <tr>
							<th><qp:message code="sc.project.0005" /></th>
							<td><qp:formatText value="${projectForm.projectName}" /></td>
							<th><qp:message code="sc.project.0006" /></th>
							<td><qp:formatText value="${projectForm.projectCode}" /></td>
						</tr> --%>
						<%-- <tr>
							<th><qp:message code="sc.sys.0055" /></th>
							<td><qp:message code="${CL_DESIGN_STATUS.get(projectForm.status.toString())}" /></td>
							<th><qp:message code="sc.sys.0028" /></th>
							<td><qp:formatText value="${projectForm.remark}" /></td>
						</tr> --%>
						<%-- </table>
				</div>
			</div>

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.messagedesign.0007" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup> --%>
						<tr>
							<th><qp:message code="sc.menudesign.0004" /></th>
							<td style="border-right: none"><c:forEach var="item"
									items="${CL_MENU_TYPE}">
									<label><form:radiobutton path="menuType"
											value="${item.key}"
											cssClass="qp-input-radio-margin qp-input-radio" /> <qp:message
											code="${CL_MENU_TYPE.get(item.key)}" /></label>
								</c:forEach></td>
							<th><form:label path="screenId">
									<qp:message code="sc.menudesign.0013" />
								</form:label></th>
							<td><qp:autocomplete name="screenId"
									optionValue="optionValue" optionLabel="optionLabel"
									selectSqlId="autocompleteGetScreenListForMenuDesign"
									arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
									arg02="20" arg03="${menuDesignForm.languageId}"
									value="${menuDesignForm.screenId}"
									displayValue="${menuDesignForm.screenIdAutocomplete}"
									mustMatch="true" maxlength="200" /></td>
						</tr>
						<tr>
							<th><form:label path="headerMenuName">
									<qp:message code="sc.menudesign.0011" />
									<span class="qp-required-field"> (*) </span>
								</form:label></th>
							<td><form:input path="headerMenuName"
									cssClass="form-control qp-input-text" maxlength="200"
									autofocus="true" /></td>

							<th><form:label path="headerMenuAction">
									<qp:message code="sc.menudesign.0014" />
								</form:label></th>
							<td><qp:autocomplete name="headerMenuAction"
									optionValue="optionValue" optionLabel="optionLabel"
									selectSqlId="autocompleteGetScreenListForMenuDesign"
									arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
									arg02="20" arg03="${menuDesignForm.languageId}"
									value="${menuDesignForm.headerMenuAction}"
									displayValue="${menuDesignForm.headerMenuActionAutocomplete}"
									mustMatch="true" maxlength="200" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message
							code="sc.menudesign.0005" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list"
						id="tbl_list_details" data-ar-dataClass="ar-dataRow"
						data-ar-callback="menuTableCallback">
						<colgroup>
							<col width="43%" />
							<col width="42%" />
							<col width="4%" />
						</colgroup>
						<thead>
							<tr>
								<th style="text-align: left;"><qp:message
										code="sc.menudesign.0002" /></th>
								<th><qp:message code="sc.menudesign.0003" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody class="ui-sortable">
							<c:forEach var="item"
								items="${menuDesignForm.listMenuDesignItem}" varStatus="status">
								<!-- -- Line 2 -- -->
								<tr id="rankOne" data-ar-rgroup="${item.groupId}"
									class="ar-dataRow"
									data-ar-templateid="tbl_list_details-template"
									data-ar-rindex="${item.itemSeqNo }"
									data-ar-rgroupindex="${item.tableIndex }">
									<td><form:hidden
											path="listMenuDesignItem[${status.index}].itemSeqNo" /> <form:hidden
											path="listMenuDesignItem[${status.index}].menuItemId" /> <form:hidden
											path="listMenuDesignItem[${status.index}].parentMenuItemId" />
										<form:hidden
											path="listMenuDesignItem[${status.index}].itemSeqNo" /> <form:hidden
											path="listMenuDesignItem[${status.index}].menuItemType"
											value="${item.menuItemType }" /> <form:hidden
											path="listMenuDesignItem[${status.index}].actionUrlCode"
											value="${item.actionUrlCode }" />
										<div style="float: left; width: 100%" id="fistDiv">
											<div class="input-group set-width"
												style="width: 100%; float: left;">
												<span class="input-group-addon" style="padding-right: 0px;">
													<div class="dropdown">
														<a href="javascript:"
															style="margin-top: 3px; cursor: move;"
															class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort"
															title="Move"></a>
														<button
															class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"
															data-toggle="dropdown"></button>
														<ul class="dropdown-menu doropdow-menu-desgin">
															<li><a
																onclick="$.qp.addRow({type:'menu',link:this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'after'}});"><qp:message
																		code="sc.menudesign.0009" /></a></li>
															<li><a
																onclick="$.qp.addRow({type:'separator',link:this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'after'}});"><qp:message
																		code="sc.menudesign.0010" /></a></li>
														</ul>
													</div>
												</span>
												<div id="inputmenuName">
													<input type="text" class="form-control"
														value="${item.menuName}"
														name="listMenuDesignItem[${status.index}].menuName"
														style="width: 100%; height: 25.2px" maxlength="255">
												</div>
												<span class="input-group-addon"
													style="padding-right: 0px; padding-left: 0px;"><a
													class="btn btn-default btn-xs glyphicon glyphicon-circle-arrow-right qp-button-action"
													onclick="$.qp.addRow({link: this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroupindex') },position:{anchor:$(this).closest('tr'),string:'after'}})"
													style="top: 0px;" href="javascript:void(0)"></a></span>
											</div>
										</div></td>
									<td><qp:autocomplete
											name="listMenuDesignItem[${status.index}].screenId"
											optionValue="optionValue" optionLabel="optionLabel"
											selectSqlId="autocompleteGetScreenListForMenuDesign"
											arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
											arg02="20" arg03="${menuDesignForm.languageId}"
											onChangeEvent="setActionUrlCode" value="${item.screenId}"
											displayValue="${item.screenIdAutocomplete}" mustMatch="true"
											maxlength="200" /> <input type="hidden" name="checkParent">
									</td>
									<td><a
										class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action"
										title="Remove message design"
										onclick="$.qp.removeRowJS({link:this})" style="top: 0px;"
										href="javascript:void(0)"></a></td>
								</tr>

							</c:forEach>
							<c:if test="${empty menuDesignForm.listMenuDesignItem}">
								<tr id="rankOne" data-ar-rgroup="${item.groupId}"
									class="ar-dataRow"
									data-ar-templateid="tbl_list_details-template"
									data-ar-rindex="0" data-ar-rgroupindex="1">
									<td><form:hidden path="listMenuDesignItem[0].itemSeqNo" />
										<form:hidden path="listMenuDesignItem[0].menuItemId" /> <form:hidden
											path="listMenuDesignItem[0].parentMenuItemId" /> <form:hidden
											path="listMenuDesignItem[0].menuItemType" value="0" /> <form:hidden
											path="listMenuDesignItem[0].actionUrlCode" value="0" />
										<div style="float: left; width: 100%" id="fistDiv">
											<div class="input-group set-width"
												style="width: 100%; float: left;">
												<span class="input-group-addon" style="padding-right: 0px;">
													<div class="dropdown">
														<a href="javascript:"
															style="margin-top: 3px; cursor: move;"
															class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort"
															title="Move"></a>
														<button
															class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"
															data-toggle="dropdown"></button>
														<ul class="dropdown-menu doropdow-menu-desgin">
															<li><a
																onclick="$.qp.addRow({type:'menu',link:this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:''}});"><qp:message
																		code="sc.menudesign.0009" /></a></li>
															<li><a
																onclick="$.qp.addRow({type:'separator',link:this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:''}});"><qp:message
																		code="sc.menudesign.0010" /></a></li>
														</ul>
													</div>
												</span>
												<div id="inputmenuName">
													<input type="text" class="form-control"
														name="listMenuDesignItem[0].menuName"
														style="width: 100%; height: 25.2px" maxlength="255">
												</div>
												<span class="input-group-addon"
													style="padding-right: 0px; padding-left: 0px;"><a
													class="btn btn-default btn-xs glyphicon glyphicon-circle-arrow-right qp-button-action"
													onclick="$.qp.addRow({link: this,tableId:'tbl_list_details',templateId:'tbl_list_details-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroupindex') },position:{anchor:$(this).closest('tr'),string:'after'}})"
													style="top: 0px;" href="javascript:void(0)"></a></span>
											</div>
										</div></td>
									<td><qp:autocomplete name="listMenuDesignItem[0].screenId"
											optionValue="optionValue" optionLabel="optionLabel"
											selectSqlId="autocompleteGetScreenListForMenuDesign"
											arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
											arg02="20" arg03="${menuDesignForm.languageId}"
											onChangeEvent="setActionUrlCode" displayValue=""
											mustMatch="true" maxlength="200" /> <input type="hidden"
										name="checkParent"></td>
									<td><a
										class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action"
										title="Remove message design"
										onclick="$.qp.removeRowJS({link:this})" style="top: 0px;"
										href="javascript:void(0)"></a></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="menudesignModify">
					<%-- <a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/menudesign/modify?init">Delete</a> --%>
					<button type="button" class="btn qp-button "
						onclick="openDialogPreview(this)" id="btnPreview">
						<qp:message code="sc.menudesign.0006" />
					</button>
					<c:if test="${f:h(sessionScope.CURRENT_PROJECT.status) eq 1}">
						<button type="submit" class="btn qp-button qp-dialog-confirm"
							onclick="saveMenuSetting">
							<qp:message code="sc.sys.0031" />
						</button>
					</c:if>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>