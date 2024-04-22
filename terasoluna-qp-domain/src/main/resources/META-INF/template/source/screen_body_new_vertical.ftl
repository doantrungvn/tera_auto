	<#assign withAreaContentVertical = "100%">
	<#assign alignAreaContentVertical = "right">
	<div class="qp-header-main">
		<script type="text/javascript" src="../media/screendesign/javascript/search.js" ></script>
		<script type="text/javascript" src="../media/screendesign/javascript/common.js?r="></script>
		<link href="../media/screendesign/css/style.css" type="text/css" rel="stylesheet" />
		<div style="width: ${withAreaContentVertical};">
			<h4>
				<span class="qp-header-main-text">${screenDesignForm.screenName}</span>
				<#if screenDesignForm.headerLinkItems?size gt 0>
					<div class="qp-link-header pull-right">
						<#if glyphicon?has_content>
							<span class="qp-link-header-icon glyphicon ${glyphicon}"></span>
						</#if>
						<#list screenDesignForm.areaNonGroup as screenArea>
							<#list screenDesignForm.headerLinkItems as headerArea>
								<#if screenArea.screenAreaId == headerArea.screenAreaId && screenArea.areaType == -1>
									${headerArea.element}&nbsp;
								</#if>
							</#list>
						</#list>
					</div>
				</#if>
			</h4>
		</div>
		<input type="hidden" name="screenId"/>
	</div>
	
	<div class="qp-body">
	
		<form method="POST" modelAttribute="screenDesignForm">
			
			<#list screenDesignForm.screenForms as screenForm>
						<#list screenDesignForm.screenAreas as screenArea>
							<#assign screenAreaId = (screenArea.screenAreaId)?c>
							<script id="${screenAreaId}-template" type="text/template">
								<#if screenForm.screenFormId ==  screenArea.screenFormId>
									<#switch screenArea.areaType>
										<#case 0 >
														<#assign rowIndex = 0>
														<#assign colIndex = 0>
														<#assign col = screenArea.totalCol>
														<#assign row = screenArea.totalCol/col>
														<#if screenArea.totalCol%col != 0 >
															<#assign row = row + 1>
														</#if>
														
														<#assign colRowSpanItem ="">
														<#assign currentIndex ="">
														<#assign arrColRow></#assign>
														<#assign arrCurrentIndex></#assign>
														<#assign elementGroup = 0>
														
														<#list screenArea.items as screenItem>
															<#if screenItem.screenAreaId == screenArea.screenAreaId>
																<#if colIndex == 0>
																	<tr index="${rowIndex }">
																</#if>
																<#assign colSpanProperties ="" >
																<#assign rowSpanProperties ="" >
																<#assign hasRowColSpan = false>
																
																<#assign colSpanTemp = (screenItem.colSpan)!"-1">
																<#if colSpanTemp?string != "-1">
																	<#assign colSpanProperties = "colspan='${screenItem.colSpan}'" >
																	<#assign hasRowColSpan = true>
																</#if>
																
																<#assign rowSpanTemp = (screenItem.rowSpan)!"-1">
																<#if rowSpanTemp?string != "-1">
																	<#assign rowSpanProperties = "rowspan='${screenItem.rowSpan}'" >
																	<#assign hasRowColSpan = true>
																</#if>
																
																<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																	<#assign isDisable = "">
																	<#assign groupItemId = (screenItem.groupItemId)!"-1">
																	<#if groupItemId?string!= "-1">
																		<#assign values = screenDesignForm.screenGroups?values>
																		<#list values as value>
																			<#if value.groupItemId == groupItemId>
																				<#assign screenGroup = value>
																				<#if (screenGroup.styleColRowSpan)?has_content>
																					<#assign isDisable = screenGroup.styleColRowSpan>
																				</#if>
																				<#break>
																			</#if>
																		</#list>
																	</#if>
																	
																	<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																	<#if logicalDataType?string != "-1" && logicalDataType?string == "20">
																		<#if (screenArea.headerStyle)?has_content>
																			<th index="${colIndex }" style="${isDisable } ${screenArea.headerStyle}" ${colSpanProperties} ${rowSpanProperties} >
																				${screenItem.element}
																			</th>
																		<#else>
																			<th index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																				${screenItem.element}
																			</th>
																		</#if>
																		<#assign colIndex = colIndex + 1>
																	<#else>
																			<#assign groupItemId = (screenItem.groupItemId)!"-1">
																			<#if groupItemId?string!= "-1">
																				<#assign values = screenDesignForm.screenGroups?values>
																				<#assign isExists=false >
																				<#list values as value>
																					<#if value.groupItemId == groupItemId>
																						<#assign screenGroup = value>
																						<#assign isExists= true >
																						<#break>
																					</#if>
																				</#list>
																				
																				<#if isExists>
																					<#assign elementStart = (screenGroup.elementStart)!"-1">
																					
																					<#if  elementStart?string != "-1" && screenItem.itemSeqNo?string == elementStart?string>
																						<#if (screenArea.inputStyle)?has_content>
																							<td index="${colIndex }" style="${isDisable} ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																						<#else>
																							<td index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																						</#if>
																					</#if>
		
																					<#if screenItem.value == "" || logicalDataType?string == "-1">
																					<#else>
																						<span style="width: 100%; float: left; padding-right: 3px;">
																							${screenItem.element}
																						</span>
																					</#if>
		
																					<#assign elementEnd = (screenGroup.elementEnd)!"-1">
																					<#if elementEnd?string !="-1" && screenItem.itemSeqNo?string == elementEnd?string>
																						</td>
																						<#assign colIndex = colIndex + 1>
																					</#if>
																				<#else>
																					<#if (screenArea.inputStyle)?has_content>
																						<td index="${colIndex }" style="${isDisable} ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																					<#else>
																						<td index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																					</#if>
																				</#if>
																			</#if>
																		<#if colIndex == col?number>
																			</tr>
																			<#assign colIndex = 0>
																			<#assign rowIndex = rowIndex + 1>
																		</#if>
																	</#if>	
																</#if>
															</#if>
														</#list>
												<#break>
											<#case 1>
															<#assign rowIndex = 0>
															<#assign colIndex = 0>
															<#assign col = screenArea.totalCol>
															<#assign colRowSpanItem ="">
															<#assign colIndex = 0>	
															<#assign currentIndex ="">
															<#assign arrColRow></#assign>
															<#assign arrCurrentIndex></#assign>		
															<#assign isGroup = false>
															<#list screenArea.items as screenItem>	
																<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																	<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																	<#if logicalDataType?string == "-1" || logicalDataType != 20>
																		
																		<#assign isDisable = "">
																		<#assign groupItemId = (screenItem.groupItemId)!"-1">
																		<#if groupItemId?string!= "-1">
																			<#assign values = screenDesignForm.screenGroups?values>
																			<#list values as value>
																				<#if value.groupItemId == groupItemId>
																					<#assign screenGroup = value>
																					<#if (screenGroup.styleColRowSpan)?has_content>
																						<#assign isDisable = screenGroup.styleColRowSpan>
																					</#if>
																					<#break>
																				</#if>
																			</#list>
																		</#if>
																		
																		
																		<#assign colSpanProperties ="">
																		<#assign rowSpanProperties ="">
																		<#assign hasRowColSpan = false >
																		
																		<#assign colSpanTemp = (screenItem.colSpan)!"-1">
																		<#if colSpanTemp?string != "-1" && colSpanTemp gt 1>
																			<#assign colSpanProperties = "colspan='${screenItem.colSpan }'">
																			<#assign hasRowColSpan = true >
																		</#if>
																		
																		<#assign rowSpanTemp = (screenItem.rowSpan)!"-1">
																		<#if rowSpanTemp?string != "-1" && rowSpanTemp gt 1>
																			<#assign rowSpanProperties ="rowspan='${screenItem.rowSpan }'">
																			<#assign hasRowColSpan = true>
																		</#if>
																		<#assign styleFirstCol ="" >
																		<#if colIndex == 0 && !isGroup>
																				<#assign styleFirstCol = "align-left" >
																				<tr index="${rowIndex }"><td class="qp-output-fixlength tableIndex">1</td>
																		</#if>
																		
																		<#assign groupItemId = (screenItem.groupItemId)!"-1">
																		
																		<#if groupItemId?string != "-1">
																				<#assign isGroup = true>
																				<#assign groupItemIdTemp = (screenItem.groupItemId)!"-1">
																				
																				<#assign values = (screenDesignForm.screenGroups)?values>
																				<#assign isExists = false>
																				<#list values as value>
																					<#if value.groupItemId == groupItemIdTemp>
																						<#assign screenGroup = value>
																						<#assign isExists = true>
																					</#if>
																				</#list>
																				<#if isExists>
																					<#if screenItem.itemSeqNo == screenGroup.elementStart>
																						<#assign styleGroup = 0 >
																						
																						<#if screenItem.itemGroupType == 1>
																							<#assign styleGroup = "enableGroupTd">
																						</#if>
																						<#if (screenArea.inputStyle)?has_content>
																							<td index="${colIndex }" style="${isDisable} ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																						<#else>
																							<td index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																						</#if>
																					</#if>
																					${screenItem.element}
																					<#if screenItem.itemSeqNo == screenGroup.elementEnd>
																						</td>
																						<#assign colIndex = colIndex + 1 >
																						<#assign isGroup = false >
																					</#if>
																				<#else>
																					<#if (screenArea.inputStyle)?has_content>
																						<td index="${colIndex }" style="${isDisable} ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																					<#else>
																						<td index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																					</#if>
																						<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																						<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }"><div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																					</td>
																				</#if>
																		</#if>
																		<#if colIndex == col?number>
																			<#if screenDesignForm.screenPatternType == 2 ||  screenDesignForm.screenPatternType == 4>
																				<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
																					<td style='text-align: center;'>
																						<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
																					</td>
																				</#if>
																			</#if>
																			</tr>			
																			<#assign colIndex = 0 >
																			<#assign rowIndex = rowIndex + 1>
																		</#if>
																	</#if>
																</#if>
															</#list>
													<#if screenDesignForm.screenPatternType == 1>
														<#if screenArea.areaPatternType == 3>
															<div class="qp-div-action">
																<ul class="pagination pagination-md"><li class="disabled"><a href="javascript:void(0)">&lt;&lt;</a></li>
																<li class="disabled"><a href="javascript:void(0)">&lt;</a></li><li class="active"><a href="javascript:void(0)">1</a></li>
																<li><a href="">2</a></li>
																<li><a href="">3</a></li>
																<li><a href="">4</a></li>
																<li><a href="">5</a></li>
																<li><a href="">&gt;</a></li>
																<li><a href="">&gt;&gt;</a></li></ul>
															</div>
														</#if>
													</#if>
													<#if screenDesignForm.screenPatternType == 2 ||  screenDesignForm.screenPatternType == 4>
														<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
															<div class="qp-add-left">
																<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this); $.qp.initialAllPicker($('#${screenAreaId}'));"  style="margin-top: 3px;" href="javascript:void(0)"></a>
															</div>
														</#if>
													</#if>
											<#break>
										</#switch>
									</#if>
								</script>
							</#list>
			</#list>
			
			
			<!-- Screen design area -->
			<div class="ui-sortable" id="srcgenScreen">
			<!--Start load data  -->
			<#list screenDesignForm.screenForms as screenForm>
				<#list screenDesignForm.screenAreas as screenArea>	
					<#if screenForm.screenFormId ==  screenArea.screenFormId>
						<#assign startHtml = (screenArea.startHtml)!"-1">
						<#if startHtml?string != "-1">
							${startHtml}
						</#if>
							
						<#assign withAreaContent = "100%">
						<#assign alignAreaContent = "left">
						
						<#assign tblWidthUnit = (screenArea.tblWidthUnit)!"-1">
						<#if tblWidthUnit?string != "-1">
							<#assign withAreaContent = tblWidthUnit >
						</#if>
						
						<#assign alignPositionType = (screenArea.alignPositionType)!"-1">
						<#if alignPositionType?string != "-1">
							<#if alignPositionType == 3>
								<#assign alignAreaContent = "right" >
							</#if>
						</#if>
							
							<#switch screenArea.areaType>
								<#case 0 >
								<div class="areaContent" style="width: ${withAreaContentVertical}; float:${alignAreaContentVertical};">
									<div class="panel panel-default qp-div-information">
											<#if (screenArea.panelStyle)?has_content>
												<div class="panel-heading" style="${screenArea.panelStyle}">
											<#else>
												<div class="panel-heading">
											</#if>
											<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
											<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
											<#if messageString?string != "-1">
												<span class="pq-heading-text">${screenArea.messageDesign.messageString}</span>
											</#if>
										</div>
										<div class="panel-body">
											<table class="table table-bordered qp-table-form">
												<colgroup>
													<#assign columnIndexColGroup = 0>
													<#list  screenArea.colWidthUnit?split(",") as itemWidth>
														<col width="${itemWidth }"></col>
														<#assign columnIndexColGroup = columnIndexColGroup + 1>
													</#list>	
													<#if screenArea.totalCol gt columnIndexColGroup>
														<#list 1..screenArea.totalCol - columnIndexColGroup as x>
															<col width="${100/screenArea.totalCol }%"></col>
														</#list>
													</#if>	
												</colgroup>
												<tbody class="ui-sortable">
													<#assign rowIndex = 0>
													<#assign colIndex = 0>
													<#assign col = screenArea.totalCol>
													<#assign row = screenArea.totalCol/col>
													<#if screenArea.totalCol%col != 0 >
														<#assign row = row + 1>
													</#if>
													
													<#assign colRowSpanItem ="">
													<#assign currentIndex ="">
													<#assign arrColRow></#assign>
													<#assign arrCurrentIndex></#assign>
													<#assign elementGroup = 0>
													
													<#list screenArea.items as screenItem>
														<#if screenItem.screenAreaId == screenArea.screenAreaId>
															<#if colIndex == 0>
																<tr index="${rowIndex }">
															</#if>
															<#assign colSpanProperties ="" >
															<#assign rowSpanProperties ="" >
															<#assign hasRowColSpan = false>
															
															<#assign colSpanTemp = (screenItem.colSpan)!"-1">
															<#if colSpanTemp?string != "-1">
																<#assign colSpanProperties = "colspan='${screenItem.colSpan}'" >
																<#assign hasRowColSpan = true>
															</#if>
															
															<#assign rowSpanTemp = (screenItem.rowSpan)!"-1">
															<#if rowSpanTemp?string != "-1">
																<#assign rowSpanProperties = "rowspan='${screenItem.rowSpan}'" >
																<#assign hasRowColSpan = true>
															</#if>
															
															<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																<#assign isDisable = "">
																<#assign groupItemId = (screenItem.groupItemId)!"-1">
																<#if groupItemId?string!= "-1">
																	<#assign values = screenDesignForm.screenGroups?values>
																	<#list values as value>
																		<#if value.groupItemId == groupItemId>
																			<#assign screenGroup = value>
																			<#if (screenGroup.styleColRowSpan)?has_content>
																				<#assign isDisable = screenGroup.styleColRowSpan>
																			</#if>
																			<#break>
																		</#if>
																	</#list>
																</#if>
																
																<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																<#if logicalDataType?string != "-1" && logicalDataType?string == "20">
																	<#if (screenArea.headerStyle)?has_content>
																		<th index="${colIndex }" style="${isDisable} ${screenArea.headerStyle}" ${colSpanProperties} ${rowSpanProperties} >
																			${screenItem.element}
																		</th>
																	<#else>
																		<th index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																			${screenItem.element}
																		</th>
																	</#if>
																	<#assign colIndex = colIndex + 1>
																<#else>
																		<#assign groupItemId = (screenItem.groupItemId)!"-1">
																		<#if groupItemId?string!= "-1">
																			<#assign values = screenDesignForm.screenGroups?values>
																			<#assign isExists=false >
																			<#list values as value>
																				<#if value.groupItemId == groupItemId>
																					<#assign screenGroup = value>
																					<#assign isExists= true >
																					<#break>
																				</#if>
																			</#list>
																			
																			<#if isExists>
																				<#assign elementStart = (screenGroup.elementStart)!"-1">
																				
																				<#if  elementStart?string != "-1" && screenItem.itemSeqNo?string == elementStart?string>
																					<td index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																				</#if>
	
																				<#if screenItem.value == "" || logicalDataType?string == "-1">
																				<#else>
																					<span style="width: 100%; float: left; padding-right: 3px;">
																						${screenItem.element}
																					</span>
																				</#if>
	
																				<#assign elementEnd = (screenGroup.elementEnd)!"-1">
																				<#if elementEnd?string !="-1" && screenItem.itemSeqNo?string == elementEnd?string>
																					</td>
																					<#assign colIndex = colIndex + 1>
																				</#if>
																			<#else>
																				<td index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} ></td>
																			</#if>
																		</#if>
																	<#if colIndex == col?number>
																		</tr>
																		<#assign colIndex = 0>
																		<#assign rowIndex = rowIndex + 1>
																	</#if>
																</#if>	
															</#if>
														</#if>
													</#list>
												</tbody>
											</table>
										</div>
									</div>
									</div>
									<#break>
								<#case 1>
									<div class="areaContent" style="width: ${withAreaContentVertical}; float:${alignAreaContentVertical};">
									<div class="panel panel-default qp-div-select">
												<#if (screenArea.panelStyle)?has_content>
													<div class="panel-heading" style="${screenArea.panelStyle}">
												<#else>
													<div class="panel-heading">
												</#if>
												<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
												<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
												<#if messageString?string != "-1">
													<span class="pq-heading-text">${screenArea.messageDesign.messageString}</span>
												</#if>
											</div>
											<div class="panel-body">
												<#assign rowIndex = 0>
												<#assign colIndex = 0>
												<#assign col = screenArea.totalCol>
												<table class="table table-bordered qp-table-list">
													<colgroup>
														<#assign columnIndexColGroup = 0>
														<#list screenArea.colWidthUnit?split(",") as itemWidth>
															<col width="${itemWidth }"></col>
															<#assign columnIndexColGroup = columnIndexColGroup + 1>
														</#list>	
														<#if columnIndexColGroup < screenArea.totalCol>
															<#list 1..(screenArea.totalCol - columnIndexColGroup) as x>
																<col width="${100/screenArea.totalCol }%"></col>
															</#list>
														</#if>	
														<#if screenDesignForm.screenPatternType == 2 || screenDesignForm.screenPatternType == 4>
															<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
																<col width="35px">
															</#if>
														</#if>
													</colgroup>
													<thead>
														<#assign colRowSpanItem ="">
														<#assign currentIndex ="">
														<#assign arrColRow></#assign>
														<#assign arrCurrentIndex></#assign>
														<#list screenArea.items as screenItem>
															<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																<#if logicalDataType?string != "-1" && logicalDataType == 20>
																	<#assign colSpanProperties ="">
																	<#assign rowSpanProperties ="">
																	<#assign hasRowColSpan = false>
																	
																	<#assign colSpanTemp = (screenItem.colSpan)!"-1">
																	<#if colSpanTemp?string != "-1" && colSpanTemp gt 1>
																		<#assign colSpanProperties = "colspan='${screenItem.colSpan}'" >
																		<#assign hasRowColSpan = true>
																	</#if>
																	
																	<#assign rowSpanTemp = (screenItem.rowSpan)!"-1">
																	<#if rowSpanTemp?string != "-1" && rowSpanTemp gt 1>
																		<#assign rowSpanProperties = "rowspan='${screenItem.rowSpan }'" >
																		<#assign hasRowColSpan = true>
																	</#if>
															
																	<#if colIndex?number == 0>
																		<tr index="${rowIndex }">
																	</#if>
																	
																	<#assign isDisable = "">
																	<#assign groupItemId = (screenItem.groupItemId)!"-1">
																	<#if groupItemId?string!= "-1">
																		<#assign values = screenDesignForm.screenGroups?values>
																		<#list values as value>
																			<#if value.groupItemId == groupItemId>
																				<#assign screenGroup = value>
																				<#if (screenGroup.styleColRowSpan)?has_content>
																					<#assign isDisable = screenGroup.styleColRowSpan>
																				</#if>
																				<#break>
																			</#if>
																		</#list>
																	</#if>
																	
																	<#if (screenArea.headerStyle)?has_content>
																		<th style="text-align: left;${isDisable} ${screenArea.headerStyle}" class="align-left" index="${colIndex }" ${colSpanProperties} ${rowSpanProperties} >
																				${screenItem.element}
																		</th>
																	<#else>
																		<th style="text-align: left;${isDisable}" class="align-left" index="${colIndex }" ${colSpanProperties} ${rowSpanProperties} >
																			${screenItem.element}
																		</th>
																	</#if>
																	<#assign colIndex = colIndex + 1>
																	<#if colIndex?number == col>		
																		<#if screenDesignForm.screenPatternType == 2 || screenDesignForm.screenPatternType == 4>
																			<#if screenArea.areaPatternType == 1 || screenArea.areaPatternType == 2>
																				<th width="35px"></th>
																			</#if>
																		</#if>
																		</tr>
																		<#assign colIndex ="0">
																		<#assign rowIndex = rowIndex + 1>	
																	</#if>
																</#if>	
															</#if>
														</#list>
													</thead>
													<tbody>
														<#assign colRowSpanItem ="">
														<#assign colIndex = 0>	
														<#assign currentIndex ="">
														<#assign arrColRow></#assign>
														<#assign arrCurrentIndex></#assign>		
														<#assign isGroup = false>
														<#list screenArea.items as screenItem>	
															<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																<#if logicalDataType?string == "-1" || logicalDataType != 20>
																	
																	<#assign isDisable = "">
																	<#assign groupItemId = (screenItem.groupItemId)!"-1">
																	<#if groupItemId?string!= "-1">
																		<#assign values = screenDesignForm.screenGroups?values>
																		<#list values as value>
																			<#if value.groupItemId == groupItemId>
																				<#assign screenGroup = value>
																				<#if (screenGroup.styleColRowSpan)?has_content>
																					<#assign isDisable = screenGroup.styleColRowSpan>
																				</#if>
																				<#break>
																			</#if>
																		</#list>
																	</#if>
																	
																	
																	<#assign colSpanProperties ="">
																	<#assign rowSpanProperties ="">
																	<#assign hasRowColSpan = false >
																	
																	<#assign colSpanTemp = (screenItem.colSpan)!"-1">
																	<#if colSpanTemp?string != "-1" && colSpanTemp gt 1>
																		<#assign colSpanProperties = "colspan='${screenItem.colSpan }'">
																		<#assign hasRowColSpan = true >
																	</#if>
																	
																	<#assign rowSpanTemp = (screenItem.rowSpan)!"-1">
																	<#if rowSpanTemp?string != "-1" && rowSpanTemp gt 1>
																		<#assign rowSpanProperties ="rowspan='${screenItem.rowSpan }'">
																		<#assign hasRowColSpan = true>
																	</#if>
																	<#assign styleFirstCol ="" >
																	<#if colIndex == 0 && !isGroup>
																			<#assign styleFirstCol = "align-left" >
																			<tr index="${rowIndex }">
																	</#if>
																	
																	<#assign groupItemId = (screenItem.groupItemId)!"-1">
																	
																	<#if groupItemId?string != "-1">
																			<#assign isGroup = true>
																			<#assign groupItemIdTemp = (screenItem.groupItemId)!"-1">
																			
																			<#assign values = (screenDesignForm.screenGroups)?values>
																			<#assign isExists = false>
																			<#list values as value>
																				<#if value.groupItemId == groupItemIdTemp>
																					<#assign screenGroup = value>
																					<#assign isExists = true>
																				</#if>
																			</#list>
																			<#if isExists>
																				<#if screenItem.itemSeqNo == screenGroup.elementStart>
																					<#assign styleGroup = 0 >
																					
																					<#if screenItem.itemGroupType == 1>
																						<#assign styleGroup = "enableGroupTd">
																					</#if>
																					<td class='${styleFirstCol } ${styleGroup }' index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																				</#if>
																				${screenItem.element}
																				<#if screenItem.itemSeqNo == screenGroup.elementEnd>
																					</td>
																					<#assign colIndex = colIndex + 1 >
																					<#assign isGroup = false >
																				</#if>
																			<#else>
																				<td index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																					<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																					<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }"><div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																				</td>
																			</#if>
																	</#if>
																	<#if colIndex == col?number>
																		<#if screenDesignForm.screenPatternType == 2 ||  screenDesignForm.screenPatternType == 4>
																			<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
																				<td style='text-align: center;'>
																					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
																				</td>
																			</#if>
																		</#if>
																		</tr>			
																		<#assign colIndex = 0 >
																		<#assign rowIndex = rowIndex + 1>
																	</#if>
																</#if>
															</#if>
														</#list>
													</tbody>
												</table>
												<#if screenDesignForm.screenPatternType == 2 ||  screenDesignForm.screenPatternType == 4>
													<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
														<div class="qp-add-left">
															<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
														</div>
													</#if>
												</#if>
											</div>
										</div>
										</div>
										<#break>
								<#case 2>
									<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
									<div class="qp-div-action">
										<div class="action-area ui-droppable">
											<#list screenArea.items as screenItem>
												<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
													<#assign displayType ="">
													<#assign elementDisplayType ="">
													
													<#assign elementDipslayType = (screenArea.elementDipslayType)!"-1">
													<#if elementDipslayType?string != "-1" && elementDipslayType == 1>
														<#assign displayType = "clear: both;">
													</#if>
													
													<#switch elementDipslayType?number>
														<#case 0>
															<#assign elementDisplayType ="float: left; padding-right: 4px">	
														<#case 1>
															<#assign elementDisplayType ="float: right; padding-left: 4px">
														<#default>
															<#assign elementDisplayType ="float: right; padding-left: 4px">
													</#switch>
													
													${screenItem.element}
													
												</#if>
											</#list>
										</div>
									</div>
									</div>
									<#break>
								<#case 3>
									<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
										<div class="panel panel-default">
												<#if (screenArea.panelStyle)?has_content>
													<div class="panel-heading" style="${screenArea.panelStyle}">
												<#else>
													<div class="panel-heading">
												</#if>
												<span class="glyphicon" aria-hidden="true">&nbsp;</span>
												<span class="qp-heading-text"></span>
											</div>
											<div class="panel-body">
												<div class="section-area ui-droppable ui-sortable">
													<#list screenArea.items as screenItem>			
														<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
															<#assign elementPositionType = (screenArea.elementPositionType)!"-1">
															<#assign elementDipslayType = (screenArea.elementDipslayType)!"-1">
															<script type="text/javascript">
																var style = 'padding: 2px; ';
																
																if(elementPositionType?string != "-1")
																	var align = 'elementPositionType';//0:left, 1 right
															
																if(elementDipslayType?string != "-1")
																	var displayType = 'elementDipslayType';//0: horial, 1: vertical
																
																if (align == "0") {
																	style += "float: left;";
																} else if (align == "1") {
																	style += "float: right;";
																} else {
																	style += "float: left;";
																}
																
																if (displayType == "1") {
																	style += "clear: both;";
																}
																
																var width = '${screenItem.itemWidthUnit}';
																if (width.length > 0) {
																	style += 'width: ' + width + ';';
																}
																
																var obj = convertToJson('${screenItem.value }');
																
																if (obj.datatype != 10) {
																	style += ' height: 30px;';
																}
																var html = returnElemenActionSection(obj, style);
																document.write(html);
															</script>
														</#if>
													</#list>
												</div>
											</div>
										</div>
									</div>
									<#break>
							</#switch>
							<#assign endHtml = (screenArea.endHtml)!"-1">
							<#if endHtml?string != "-1">
								${endHtml}
							</#if>
					</#if>
				</#list>
			</#list>
		</div>
	</form>
	</div>