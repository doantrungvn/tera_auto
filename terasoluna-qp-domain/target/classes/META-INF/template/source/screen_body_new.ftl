	<#assign moduleCode = "" />
	<#if screenDesignForm.moduleCode?has_content>
		<#assign moduleCode = screenDesignForm.moduleCode >
	</#if>
	
	<div class="qp-breadcrumb">
		<ul class="breadcrumb">
			<li><a href="${urlHomePage}.html"><i class="qp-link-header-icon glyphicon glyphicon-home"></i></a></li>
			<#if moduleCode?has_content>
				<li><span>${moduleCode}</span></li>
			</#if>
			<li><span>${screenDesignForm.screenName}</span></li>
 		</ul>
	</div>
	
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
	
	<#assign no1 = 0>
	<#assign indexForEach = 0>
	<#assign colIndexList = 0>
	
	<div class="qp-body">
		<form method="POST" modelAttribute="screenDesignForm">
			
					<#list screenDesignForm.screenForms as screenForm>
						<#list screenDesignForm.screenAreas as screenArea>
							<#assign addRow = "" />
							<#assign removeRow = "" />
							
							<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow?has_content && screenArea.fixedRow == 0) && screenArea.tblComponentRow?has_content && screenArea.tblComponentRow gt 1 >
								<#assign addRow = "$" + ".qp.addMultiRowJSByLink(this) " >
								<#assign removeRow = "$" + ".qp.removeMultiRowJS('dynamic',this) " >
							<#else>
								<#assign addRow = "$" + ".qp.addRowJSByLink(this) " >
								<#assign removeRow = "$" + ".qp.removeRowJS('dynamic',this) " >
							</#if>
							
							<#assign tblComponentRowTemp = 1>
							<#if screenArea.tblComponentRow?has_content>
								<#assign tblComponentRowTemp = screenArea.tblComponentRow>
							</#if>
							
							<#assign componentRowRemoveTemp = 0>
							<#assign componentRowRemoveTempTd = 0>
							<#assign screenAreaId = (screenArea.screenAreaId)?c>
							<#assign withAreaContent = "100%">
							
							<#assign tblWidthUnit = (screenArea.tblWidthUnit)!"-1">
							<#if tblWidthUnit?has_content && tblWidthUnit != "" && tblWidthUnit?string != "-1">
								<#assign withAreaContent = tblWidthUnit >
							</#if>
							
							<#assign alignAreaContent = "left">
							<#assign alignPositionType = (screenArea.alignPositionType)!"-1">
							<#if alignPositionType?string != "-1">
								<#if alignPositionType == 3>
									<#assign alignAreaContent = "right" >
								</#if>
							</#if>
							
							<#assign elementPositionTypeContent = "" />
							<#assign elementPositionType = (screenArea.elementPositionType)!"-1">
							
							<#if elementPositionType?string == "-1" && screenArea.areaType == 3 >
								<#assign elementPositionTypeContent = "left">
							<#elseif elementPositionType?string == "-1" && screenArea.areaType != 3 >
								<#assign elementPositionTypeContent = "right">
							<#elseif elementPositionType?string != "-1" && elementPositionType == 0>
								<#assign elementPositionTypeContent = "left">
							<#elseif elementPositionType?string != "-1" && elementPositionType == 1>
								<#assign elementPositionTypeContent = "right">
							</#if>
							
							<#if (screenArea.areaCustomType?has_content && screenArea.areaCustomType == 1) || 
										(screenArea.areaTypeAction?has_content && screenArea.fixedRow?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow == 0)>
								<script id="${screenAreaId}-template" type="text/template">
									<#if (screenForm.screenFormId ==  screenArea.screenFormId) || (screenArea.areaCustomType?has_content && screenArea.areaCustomType == 1)>
										<#switch screenArea.areaType>
											<#case 0 >
												<#if screenArea.areaCustomType?has_content>
													<#if screenArea.areaCustomType == 1 >
															<div class="qp-add-right" style="margin-bottom: 5px; margin-top:3px">
																<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.removeTableJS(this);"  href="javascript:void(0)" title="Remove"></a>
															</div>
																<table class="table table-bordered qp-table-form" id="${screenAreaId}" >
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
													</#if>
												</#if>
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
														
														<#comment>
															// area custom type is "add remove table"
														</#comment>
														
														<#if screenArea.areaCustomType?has_content && screenArea.areaCustomType == 1 >
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
																							<span style="width: 100%; float: left; padding-right: 3px; padding-bottom: 3px">
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
																					</tbody>
																				</table>
																			</div>
																		</div>
																	</div>
																</td>
															</tr>	
														</#if>
														
														<#comment>
															// area custom type is "normal"
														</#comment>
														<#if screenArea.areaCustomType?has_content && screenArea.areaCustomType != 1 >
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
																							<span style="width: 100%; float: left; padding-right: 3px;padding-bottom: 3px">
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
														</#if>
															
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
																					<tr index="${rowIndex }">
																					<#if componentRowRemoveTempTd == 0>
																						<td rowspan="${tblComponentRowTemp}" class="qp-output-fixlength tableIndex">1</td>
																						<#assign componentRowRemoveTempTd = 1>
																					</#if>
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
																				<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1>
																					<#if (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0 && componentRowRemoveTemp == 0>
																						<td style='text-align: center;' rowspan="${tblComponentRowTemp}" >
																							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																						</td>
																						<#assign componentRowRemoveTemp = 1>
																					</#if>
																				</#if>
																				</tr>			
																				<#assign colIndex = 0 >
																				<#assign rowIndex = rowIndex + 1>
																			</#if>
																		</#if>
																	</#if>
																</#list>
														<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1>
															<#if (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0>
																<div class="qp-add-left">
																	<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="${addRow?string}; $.qp.initialAllPicker($('#${screenAreaId}'));"  style="margin-top: 3px;" href="javascript:void(0)"></a>
																</div>
															</#if>
														</#if>
														<#if screenDesignForm.screenPatternType == 1>
															<#if screenArea.areaPatternType?has_content && screenArea.areaPatternType == 3>
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
														
												<#break>
										</#switch>
								</#if>
							</script>
						</#if>
					</#list>
				</#list>
				<#if screenDesignForm.templateType == 2>
					<style>
						.qp-button-type {
							${projectStyle['commonButtonBgColor']}
							${projectStyle['commonButtonTextColor']}
						}
						.qp-button-type:ACTIVE,.qp-button-type:hover,.qp-button-type:focus {
							${projectStyle['commonButtonBgActiveColor']}
						}
						
						.qp-button-type-warning {
							${projectStyle['commonButtonDeleteBgColor']}
							${projectStyle['commonButtonDeleteTextColor']}
						}
						
						.qp-button-type-warning:ACTIVE,.qp-button-type-warning:hover,.qp-button-type-warning:focus  {
							${projectStyle['commonButtonDeleteBgActiveColor']}
						}
						
						.qp-button-type-client {
							${projectStyle['clientButtonDeleteBgColor']}
							${projectStyle['clientButtonDeleteTextColor']}
						}
						.qp-button-type-client:ACTIVE,.qp-button-type-client:hover,.qp-button-type-client:focus {
							${projectStyle['clientButtonDeleteBgActiveColor']}
						}
						.qp-table-list > tbody > tr > td:last-child .btn,
						.qp-table-list-none-no > tbody > tr > td:last-child .btn {
								<#comment>
									countButton == null => only one button on one screen area type is list
								</#comment>
								<#if !countButton?has_content>
									font-size: 12px;
									padding: 2px;
								</#if>
						}
						
					</style>
				</#if>
			<!-- Screen design area -->
			<div class="ui-sortable" id="srcgenScreen">
			<!--Start load data  -->
			<#list screenDesignForm.screenForms as screenForm>
				<#list screenDesignForm.screenAreas as screenArea>
				
					<#assign addRow = "" />
					<#assign removeRow = "" />
					<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow?has_content && screenArea.fixedRow == 0) && screenArea.tblComponentRow?has_content && screenArea.tblComponentRow gt 1 >
						<#assign addRow = "$" + ".qp.addMultiRowJSByLink(this) " >
						<#assign removeRow = "$" + ".qp.removeMultiRowJS('dynamic',this) " >
					<#else>
						<#assign addRow = "$" + ".qp.addRowJSByLink(this) " >
						<#assign removeRow = "$" + ".qp.removeRowJS('dynamic',this) " >
					</#if>
					
					<#assign withAreaContent = "100%">
					
					<#assign tblWidthUnit = (screenArea.tblWidthUnit)!"-1">
					<#if tblWidthUnit?has_content && tblWidthUnit != "" && tblWidthUnit?string != "-1">
						<#assign withAreaContent = tblWidthUnit >
					</#if>
					
					<#assign alignAreaContent = "left">
					<#assign alignPositionType = (screenArea.alignPositionType)!"-1">
					<#if alignPositionType?string != "-1">
						<#if alignPositionType == 3>
							<#assign alignAreaContent = "right" >
						</#if>
					</#if>
					
					<#assign elementPositionTypeContent = "" />
					<#assign elementPositionType = (screenArea.elementPositionType)!"-1">
					
					<#if elementPositionType?string == "-1" && screenArea.areaType == 3 >
						<#assign elementPositionTypeContent = "left">
					<#elseif elementPositionType?string == "-1" && screenArea.areaType != 3 >
						<#assign elementPositionTypeContent = "right">
					<#elseif elementPositionType?string != "-1" && elementPositionType == 0>
						<#assign elementPositionTypeContent = "left">
					<#elseif elementPositionType?string != "-1" && elementPositionType == 1>
						<#assign elementPositionTypeContent = "right">
					</#if>
					
					<#if screenForm.screenFormId ==  screenArea.screenFormId>
						<#assign startHtml = (screenArea.startHtml)!"-1">
						<#if startHtml?string != "-1">
							${startHtml}
						</#if>
							
							<#switch screenArea.areaType>
								<#case 0 >
								<#assign srcAreaId = (screenArea.screenAreaId)?c>
								<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};">
									<div class="panel panel-default qp-div-information">
										<#if (screenArea.panelStyle)?has_content>
											<div class="panel-heading" style="${screenArea.panelStyle}">
										<#else>
											<div class="panel-heading">
										</#if>
											<#if (screenArea.areaIcon)?has_content>
												<span aria-hidden="true" class="${screenArea.areaIcon}">&nbsp;</span>
											<#else>
												<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
											</#if>
											<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
											<#if messageString?string != "-1">
												<span class="pq-heading-text">${screenArea.messageDesign.messageString}</span>
											</#if>
										</div>
										<div class="panel-body">
											<#assign scrAreaId = (screenArea.screenAreaId)?c>
											<table class="table table-bordered qp-table-form" id="${scrAreaId}" >
												<colgroup>
													<#assign columnIndexColGroup = 0>
													<#list  screenArea.colWidthUnit?split(",") as itemWidth>
														<#if itemWidth?has_content>
															<col width="${itemWidth }"></col>
														</#if>
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
														<#assign width = "">
														<#assign widthUnit = "">
														<#if screenItem.width?has_content>
															<#assign width = screenItem.width>
														</#if>
														<#if screenItem.widthUnit?has_content>
															<#assign widthUnit = screenItem.widthUnit>
														</#if>
														
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
																<#assign itemType = (screenItem.itemType)!"-1">
																<#if logicalDataType?string != "-1" && logicalDataType?string == "20" && itemType?string != "-1" && itemType?string != "5" >
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
																					<#if (screenArea.inputStyle)?has_content>
																						<td index="${colIndex }" style="${isDisable } ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																					<#else>
																						<td index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																					</#if>
																				</#if>
	
																				<#if screenItem.value == "" || logicalDataType?string == "-1">
																				<#else>
																					<span style="width: ${width}${widthUnit}; float: left; padding-right: 3px; padding-bottom: 3px">
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
																	</#if>	
																	<#if colIndex?number == col?number || colIndex?string == col?string>
																		</tr>
																		<#assign colIndex = 0>
																		<#assign rowIndex = rowIndex + 1>
																	</#if>
																</#if>	
														</#if>
													</#list>
												</tbody>
											</table>
											<#if screenArea.areaCustomType?has_content && screenArea.areaCustomType == 1>
												<div class="qp-add-left">
													<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addTableJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
												</div>
											</#if>
										</div>
									</div>
									</div>
									
									<#break>
								<#case 1>
									<style>
										.qp-table-form tbody tr th label{
											${projectStyle['panelTableFormTh']}
										}
									</style>
									<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};">
									<#assign srcAreaId = (screenArea.screenAreaId)?c>
									<#assign columnIndexColGroup = 0>
									<div class="panel panel-default qp-div-select">
											<#if (screenArea.panelStyle)?has_content>
												<div class="panel-heading" style="${screenArea.panelStyle}">
											<#else>
												<div class="panel-heading">
											</#if>
												<#if (screenArea.areaIcon)?has_content>
													<span class="${screenArea.areaIcon}" aria-hidden="true">&nbsp;</span>
												<#else>
													<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
												</#if>												
												<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
												<#if messageString?string != "-1">
													<span class="pq-heading-text">${screenArea.messageDesign.messageString}&nbsp;
														<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 2>
															<span class="badge">&nbsp;1&nbsp;</span>
														</#if>
													</span>
												</#if>
												<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 2>
													<div class="form-inline qp-item-per-page">
														<label>
															<select id="fcomPageSizeSelect" style="margin-top: -3px;" name="size" aria-controls="dataTables-example" class="form-control qp-input-select" onchange="calSavePageSize()">
													    				<option value="10" selected="selected">10</option>
													    				<option value="20">20</option>
													    				<option value="30">30</option>
													    				<option value="50">50</option>
													    				<option value="100">100</option>
															</select>
														</label>
													</div>
												</#if>
											</div>
											<div class="panel-body">
												<#assign componentRow = 0>
												<#assign componentRowRemove = 0>
												<#assign rowIndex = 0>
												<#assign headerRow = 0>
												
												<#assign col = screenArea.totalCol>
												<table class="table table-bordered qp-table-list" id="${srcAreaId}">
													<colgroup>
															<#if (((screenDesignForm.screenPatternType == 1 || screenDesignForm.screenPatternType == 3) && (screenArea.areaTypeAction?has_content && (screenArea.areaTypeAction == 0 || screenArea.areaTypeAction == 2))) || 
																	(screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 0) || (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 2) ||
																	(screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow?has_content && screenArea.fixedRow == 0) ||
																	(screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow?has_content && screenArea.fixedRow == 1))>
																<col width="35px">
															</#if>
															<#assign size = (screenArea.colWidthUnit?split(","))?size >
															<#list screenArea.colWidthUnit?split(",") as itemWidth>
																<#if itemWidth?has_content>
																	<col width="${itemWidth }"></col>
																	<#assign columnIndexColGroup = columnIndexColGroup + 1>
																</#if>
															</#list>
															<#if columnIndexColGroup < screenArea.totalCol>
																<#if screenDesignForm.screenPatternType == 1 && screenArea.tblWidthUnit?has_content>
																	<#list 1..(screenArea.totalCol - columnIndexColGroup) as x>
																		<#if x = (screenArea.totalCol - columnIndexColGroup)>
																			<col width="50px">
																		<#else>
																			<col width="${100/(screenArea.totalCol - 1)}%"></col>
																		</#if>
																	</#list>
																<#else>	
																	<#list 1..(screenArea.totalCol - columnIndexColGroup) as x>
																		<col width="${100/screenArea.totalCol }%"></col>
																	</#list>
																</#if>
															</#if>
															<#if (screenArea.areaTypeAction?has_content && screenArea.fixedRow?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow == 0)>
																	<col width="50px">
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
																<#if logicalDataType?string != "-1" && logicalDataType == 20 && screenItem.itemType?has_content && screenItem.itemType != 5>
														
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
																	
																	<#assign headerStyle = "">
																	<#if (screenArea.headerStyle)?has_content>
																		<#assign headerStyle = screenArea.headerStyle>	
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
																	
																	<#if colIndexList?number == 0>
																		<tr index="${rowIndex }">
																			<#assign tblHeaderRow = (screenArea.tblHeaderRow)!"-1">
																			<#if tblHeaderRow?string != "-1" && tblHeaderRow == 1>
																				<th style="text-align: left;${isDisable} ${headerStyle}">No.</th>
																			<#elseif tblHeaderRow?string != "-1" && headerRow == 0 && tblHeaderRow gt 1>
																				<th rowspan="${screenArea.tblHeaderRow}" style="text-align: left;${isDisable} ${headerStyle}">No.</th>
																				<#assign headerRow = headerRow + 1>
																			<#elseif tblHeaderRow?string == "-1">
																				<th style="text-align: left;${isDisable} ${headerStyle}">No.</th>
																			</#if>
																	</#if>
																	<#if (screenArea.headerStyle)?has_content>
																		<th style="text-align: left;${isDisable} ${screenArea.headerStyle}" class="align-left" index="${colIndexList }" ${colSpanProperties} ${rowSpanProperties} >
																				${screenItem.element}
																		</th>
																	<#else>
																		<th style="text-align: left;${isDisable}" class="align-left" index="${colIndexList }" ${colSpanProperties} ${rowSpanProperties} >
																			${screenItem.element}
																		</th>
																	</#if>
																	<#assign colIndexList = colIndexList?number + 1>
																	<#if colIndexList?number == col>
																		<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1>
																			<#if (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0>
																				<th width="35px"></th>
																			</#if>
																		</#if>
																		</tr>
																		<#assign colIndexList = "0">
																		<#assign rowIndex = rowIndex + 1>	
																	</#if>
																</#if>	
															</#if>
														</#list>
													</thead>
													<tbody>
														<#assign colRowSpanItem ="">
														<#assign colIndexTdList = 0>	
														<#assign currentIndex ="">
														<#assign arrColRow></#assign>
														<#assign arrCurrentIndex></#assign>		
														<#assign isGroup = false>
														<#assign inputStyle = "" />
														<#if (screenArea.inputStyle)?has_content>
															<#assign inputStyle = screenArea.inputStyle >
														</#if>
														<#list screenArea.items as screenItem>
															<#assign width = "">
															<#assign widthUnit = "">
															<#if screenItem.width?has_content>
																<#assign width = screenItem.width>
															</#if>
															<#if screenItem.widthUnit?has_content>
																<#assign widthUnit = screenItem.widthUnit>
															</#if>
															<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																<#assign itemType = (screenItem.itemType)!"-1">
																<#if logicalDataType?string == "-1" || logicalDataType != 20 || (itemType?string != "-1" && itemType?string == "5") >
																
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
																	<#if colIndexTdList == 0 && !isGroup>
																		<#assign styleFirstCol = "align-left" >
																		<#if (screenArea.rowStyle)?has_content>
																			<tr index="${rowIndex }" style="${screenArea.rowStyle}">
																				<#if screenArea.tblComponentRow?has_content>
																					<#if screenArea.tblComponentRow == 1>
																						<td style="${inputStyle};">1</td>
																					<#elseif screenArea.tblComponentRow != 1 && componentRow == 0>
																						<td rowspan="${screenArea.tblComponentRow}" style="${inputStyle};">1</td>
																						<#assign componentRow = componentRow + 1>
																					</#if>
																				<#else>
																					<td style="${inputStyle};">1</td>
																				</#if>
																		<#else>
																			<tr index="${rowIndex }">
																				<#if screenArea.tblComponentRow?has_content>
																					<#if screenArea.tblComponentRow == 1>
																						<td style="${inputStyle};">1</td>
																					<#elseif screenArea.tblComponentRow != 1 && componentRow == 0>
																						<td rowspan="${screenArea.tblComponentRow}" style="${inputStyle};">1</td>
																						<#assign componentRow = componentRow + 1>
																					</#if>
																				<#else>
																					<td style="${inputStyle};">1</td>
																				</#if>
																		</#if>
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
																				<#assign logicaldatatype = (screenItem.logicalDataType)!"-1">
																				<#assign floatButton = "" />
																				<#assign alignTdActionColumn = "" />
																				<#if logicaldatatype?string != "-1" && logicaldatatype?string == "13">
																					<#assign alignTdActionColumn = projectStyle['panelListTdActionColumn'] >
																					<#assign floatButton = "float:left" >
																				</#if>
																				
																				<#if screenItem.itemSeqNo == screenGroup.elementStart>
																					<#assign styleGroup = 0 >
																					<#if screenItem.itemGroupType == 1>
																						<#assign styleGroup = "enableGroupTd">
																					</#if>
																					<td class='${styleFirstCol } ${styleGroup }' index="${colIndexTdList }" style="${isDisable }; ${inputStyle}; ${alignTdActionColumn}" ${colSpanProperties} ${rowSpanProperties} >
																				</#if>
																				
																				<span style=" ${floatButton} ;width: ${width}${widthUnit};padding-right: 3px; padding-bottom: 3px; float: left;">
																					${screenItem.element}
																				</span>
																				<#if screenItem.itemSeqNo == screenGroup.elementEnd>
																					</td>
																					<#assign colIndexTdList = colIndexTdList + 1 >
																					<#assign isGroup = false >
																				</#if>
																			<#else>
																				<td index="${colIndexTdList }" style="${isDisable }; ${inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																					<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																					<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }"><div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																				</td>
																			</#if>
																	</#if>
																	
																	<#if colIndexTdList == col?number>
																		<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1  && (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0>
																			<#if screenArea.tblComponentRow?has_content>
																				<#if screenArea.tblComponentRow == 1>
																						<td style='text-align: center;'>
																							<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																						</td>
																				<#elseif screenArea.tblComponentRow != 1 && componentRowRemove == 0>
																					<td rowspan="${screenArea.tblComponentRow}" style='text-align: center;'><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																					<#assign componentRowRemove = componentRowRemove + 1>
																				</#if>
																			<#else>
																				<td style='text-align: center;'>
																					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																				</td>
																			</#if>
																		</#if>
																		</tr>
																		<#assign colIndexTdList = 0 >
																		<#assign rowIndex = rowIndex + 1>
																	</#if>
																</#if>
															</#if>
														</#list>
													</tbody>
												</table>
												<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1>
													<#if (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0>
														<div class="qp-add-left">
															<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="${addRow?string}; $.qp.initialAllPicker($('#${srcAreaId}'));" style="margin-top: 3px;" href="javascript:void(0)"></a>
														</div>
													</#if>
												</#if>
												<#if screenDesignForm.screenPatternType == 1>
													<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 2>
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
											</div>
										</div>
										</div>
										<#break>
								<#case 2>
									<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};">
									<div class="qp-div-action">
											<#list screenArea.items as screenItem>
												<#assign width = "">
												<#assign widthUnit = "">
												<#if screenItem.width?has_content>
													<#assign width = screenItem.width>
												</#if>
												<#if screenItem.widthUnit?has_content>
													<#assign widthUnit = screenItem.widthUnit>
												</#if>
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
													<span style="width: ${width}${widthUnit}; float:${elementPositionTypeContent}; padding-right: 3px;padding-bottom: 3px">
														${screenItem.element}
													</span>
												</#if>
											</#list>
										
									</div>
									</div>
									<#break>
								<#case 3>
									<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
									
										<#assign panelStyle = "" />
										<#if screenArea.panelStyle?has_content>
											<#assign panelStyle = screenArea.panelStyle >
										</#if>
										<div class="panel panel-default">
											<div class="panel-heading" style="${panelStyle}">
												<span class="qp-heading-text">
													<span>${screenArea.messageDesign.messageString}</span>
												</span>
											</div>
											<div class="panel-body">
												<div class="section-area ui-droppable ui-sortable">
													<#list screenArea.items as screenItem>			
														<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
															<#assign elementPositionType = "" />
															<#if screenArea.elementPositionType?has_content>
																<#assign elementPositionType = screenArea.elementPositionType >
															</#if>
															
															<#assign elementDipslayType = "" />
															<#if screenArea.elementDipslayType?has_content>
																<#assign elementDipslayType = screenArea.elementDipslayType >
															</#if>
															<#assign width = "">
															<#assign widthUnit = "">
															<#if screenItem.width?has_content>
																<#assign width = screenItem.width>
															</#if>
															<#if screenItem.widthUnit?has_content>
																<#assign widthUnit = screenItem.widthUnit>
															</#if>
															<span style="width: ${width}${widthUnit}; float:${elementPositionTypeContent}; padding-right: 3px;padding-bottom: 3px">
																${screenItem.element}
															</span>
														</#if>
													</#list>
												</div>
											</div>
										</div>
									</div>
									<#break>
								<#case 4>
									<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
										<div class="panel panel-default">
											<div class="panel-heading" style="background-color:#ebebe1;${screenArea.panelStyle}">
												<span class="glyphicon" aria-hidden="true"></span>
												<#assign style = "" >
												<#if !screenArea.panelStyle?has_content>
													<#assign style = "color: #337AB7" >
												</#if>
												<#if screenArea.panelStyle?has_content>
													<#assign style = screenArea.panelStyle >
												</#if>
												<span class="qp-heading-text"><a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;color: #337AB7;" class="" title="Move"></a>
													<span style="${style}">Custom content</span>
												</span>
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