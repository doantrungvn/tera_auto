<#macro compress_single_line>
	<#local captured><#nested></#local>
	${ captured?replace("^\\s*[\\n\\r]+", "", "rm") }
</#macro>
<@compress_single_line>
	<#if checkHTML?has_content && checkHTML == 1>
		<div class="qp-header-main">
			<h4><span class="qp-header-main-text"><qp:messageCode="${screenCode}"></span></h4>
		</div>
	</#if>
		
		<#assign modelAttribute = "">
		<#assign headerRow = 0>
		<#assign componentRowRemove = 0>
		<#assign tdComponentRowIndex = 0>
		<#assign i = 0 >
		<#assign indexForEach = 0 >
		<#assign enctypion = "" />
		<#if enctype?has_content>
			<#assign enctypion = enctype>
		</#if>
			
			<#if screenDesignForm.screenPatternType == 3>
				<style>
					.qp-button-type {
						${projectStyle['commonButtonBgColor']}
						${projectStyle['commonButtonTextColor']}
					}
					
					.qp-button-type:ACTIVE {
						${projectStyle['commonButtonBgActiveColor']}
					}
					
					.qp-button-type-warning {
						${projectStyle['commonButtonDeleteBgColor']}
						${projectStyle['commonButtonDeleteTextColor']}
					}
					
					.qp-button-type-warning:ACTIVE {
						${projectStyle['commonButtonDeleteBgActiveColor']}
					}
					
					.qp-button-type-client {
						${projectStyle['clientButtonDeleteBgColor']}
						${projectStyle['clientButtonDeleteTextColor']}
					}
					
					.qp-button-type-client:ACTIVE {
						${projectStyle['clientButtonDeleteBgActiveColor']}
					}
					.qp-root .qp-table-form tr td div{ 
						border-spacing : 0px;
					}
				</style>
			</#if>
				<style>
					.qp-root .qp-table-form tr td div{ 
						border-spacing : 0px;
					}
				</style>
			
			<!--Start load data  -->
			<#list screenDesignForm.screenForms as screenForm>
					<#if businessDesigns?has_content && businessDesigns?size gt 0>
						<#list businessDesigns as businessDesign>
							<#if screenForm.screenFormId == businessDesign.screenFormId>
								<#assign formerror = "" />
								<#if screenDesignForm.screenPatternType == 2 || screenDesignForm.screenPatternType == 4>
									<#assign formerror = error>
								</#if>
								<#assign modelAttribute = businessDesign.businessLogicCode + "InputForm">
								<#if blogicCode?has_content>
									<form:form method="POST" name="${screenForm.formCode}" ${enctypion} modelAttribute="${businessDesign.businessLogicCode}${'InputForm'}" action="${pageContext}/${moduleCode}/${blogicCode}" >
									${formerror}
								<#else>
									<form:form method="POST" name="${screenForm.formCode}" ${enctypion} modelAttribute="${businessDesign.businessLogicCode}${'InputForm'}" action="${pageContext}/${moduleCode}" >
									${formerror}
								</#if>
							<#else>
								<#assign mafordisplay = "" />
								<#if maForDisplay?has_content>
									<#assign mafordisplay = maForDisplay >
								</#if>
								<#if screenDesignForm.screenPatternType == 2 || screenDesignForm.screenPatternType == 4>
									<#assign maforregister = "" />
									<#if maForRegister?has_content>
										<#assign maforregister = maForRegister >
									</#if>
									<#assign modelAttribute = maforregister>
									<form:form method="POST" name="${screenForm.formCode}" ${enctypion} modelAttribute="${maforregister}" action="${pageContext}/${moduleCode}">
								<#else>
									<form:form method="POST" name="${screenForm.formCode}" ${enctypion} modelAttribute="${mafordisplay}" action="${pageContext}/${moduleCode}">
								</#if>
							</#if>
							
							<#break>
						</#list>
					</#if>
					
					<#if !businessDesigns?has_content>
						<#assign mafordisplay = "" />
						<#if maForDisplay?has_content>
							<#assign mafordisplay = maForDisplay >
						</#if>
						<#if screenDesignForm.screenPatternType == 2 || screenDesignForm.screenPatternType == 4>
							<#assign maforregister = "" />
							<#if maForRegister?has_content>
								<#assign maforregister = maForRegister >
							</#if>
							<#assign modelAttribute = maforregister>
							<form:form method="POST" name="${screenForm.formCode}" ${enctypion} modelAttribute="${maforregister}" action="${pageContext}/${moduleCode}">
						<#else>
							<form:form method="POST" name="${screenForm.formCode}" ${enctypion} modelAttribute="${mafordisplay}" action="${pageContext}/${moduleCode}">
						</#if>
					</#if>
						
				<#list screenDesignForm.screenAreas as screenArea>
					<#assign srcAreaId = (screenArea.screenAreaId)?c>
					<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1 && screenArea.fixedRow?has_content && screenArea.fixedRow == 0) && screenArea.tblComponentRow?has_content && screenArea.tblComponentRow gt 1 >
						<#assign addRow = "$" + ".qp.addMultiRowJSByLink(this) " >
						<#assign removeRow = "$" + ".qp.removeMultiRowJS('tbl-${srcAreaId}', this) " >
					<#else>
						<#assign addRow = "$" + ".qp.addRowJSByLink(this) " >
						<#assign removeRow = "$" + ".qp.removeRowJS('tbl-${srcAreaId}', this) " >
					</#if>
					
					<#assign tblComponentRowTemp = 1>
					<#if screenArea.tblComponentRow?has_content>
						<#assign tblComponentRowTemp = screenArea.tblComponentRow>
					</#if>
					<#assign componentRowRemoveTemp = 0>
					<#assign componentRowRemoveTempTd = 0>
					<#assign componentRowRemoveTd = 0>
					<#assign srcAreaId = (screenArea.screenAreaId)?c>
						<#if screenDesignForm.screenPatternType == 1>
							<script>
									$(document).ready(function() {
										if (${'$'}('#tbl-${srcAreaId} tbody').children().length == 0) {
											${'$'}('#tbl-${srcAreaId}').next().find(".glyphicon-plus").trigger( "click" );
										}
									});
							</script>
						</#if>
						
						<#assign areaTypeAction></#assign>
						<#if screenArea.areaTypeAction?has_content >
							<#assign areaTypeAction = screenArea.areaTypeAction >
						<#else>
							<#assign areaTypeAction = 1 >
						</#if>
						
						<#if screenArea.areaType == 0 && screenArea.areaCustomType?has_content && screenArea.areaCustomType == 1>
								<script id="tbl-${srcAreaId}-template" type="text/template">
									<#if screenForm.screenFormId ==  screenArea.screenFormId>
											<div class="qp-add-right" style="margin-bottom: 5px; margin-top:3px">
												<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.removeTableJS(this);"  href="javascript:void(0)" title="Remove"></a>
											</div>
												<table class="table table-bordered qp-table-form" id="tbl-${srcAreaId}" >
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
																		${screenItem.elementTemplate}
																	</th>
																<#else>
																	<th index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																		${screenItem.elementTemplate}
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
																					${screenItem.elementTemplate}
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
																		${screenItem.elementTemplate}
																	</th>
																<#else>
																	<th index="${colIndex }" style="${isDisable}" ${colSpanProperties} ${rowSpanProperties} >
																		${screenItem.elementTemplate}
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
																					${screenItem.elementTemplate}
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
											</#if>
										</script>
									</#if>
							
							<#if screenArea.areaType == 1 && (areaTypeAction == 1 && screenArea.fixedRow?has_content && screenArea.fixedRow == 0)>
								<script id="tbl-${srcAreaId}-template" type="text/template">
										<#if screenForm.screenFormId ==  screenArea.screenFormId>
											<#assign col = screenArea.totalCol>
											<#assign rowIndex = 0>
											<#assign colRowSpanItem ="">
											<#assign colIndex = 0>
											<#assign currentIndex ="">
											<#assign arrColRow></#assign>
											<#assign arrCurrentIndex></#assign>
											<#assign isGroup = false>
											<#list screenArea.items as screenItem>
												<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
													<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
													<#assign itemType = (screenItem.itemType)!"-1">
													<#if (logicalDataType?string == "-1" || logicalDataType != 20) || itemType?string == "5">
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
																<#assign styleFirstCol = "align-left">
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
																	
																	<#assign styleFirstColumn = "" />
																	<#if colIndex == 0>
																		<#assign styleFirstColumn = "text-align: left;">
																	</#if>
																	 
																	<#assign fieldFormat = (screenItem.fieldStyle)!"-1">
																	<#if fieldFormat?string != "-1">
																		<#assign fieldFormat = (screenItem.fieldStyle)>
																	</#if>
																	
																	<#assign inputStyle = (screenArea.inputStyle)!"-1">
																	<#if inputStyle?string != "-1">
																		<#assign inputStyle = (screenArea.inputStyle)>
																	</#if>
																	
																	<#assign elementStart = (screenGroup.elementStart)!"-1">
																	<#assign elementEnd = (screenGroup.elementEnd)!"-1">
																	<#if elementStart?string != "-1" && elementEnd?string != "-1" && elementStart != elementEnd>
																		<#assign fieldFormat = "result-text">
																	</#if>
																	
																	<#if screenDesignForm.screenPatternType == 1 || screenDesignForm.screenPatternType == 3>
																			<td>
																	<#else>
																		<td class='${styleFirstCol } ${styleGroup } ${inputStyle} ${fieldFormat}' index="${colIndex }" style="${styleFirstColumn} ${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																	</#if>
																</#if>
																	
																${(screenItem.elementTemplate)?replace("[${'$'}{status.index}]","[0]")}
																			
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
															<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
																<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1) && (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0 && componentRowRemoveTemp == 0>
																	<td style='text-align: center;' rowspan="${tblComponentRowTemp}">
																		<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																		<#assign componentRowRemoveTemp = 1>
																	</td>
																</#if>
															</#if>
															<#if parentOutputBean?has_content || parentInputBean?has_content || screenDesignForm.screenPatternType == 1>
																</tr>
															</#if> 
															<#assign colIndex = 0 >
															<#assign rowIndex = rowIndex + 1>
														</#if>
													</#if>
												</#if>
											</#list>
										</#if>
								</script>
							</#if>
							
					<#assign areaIcon = "" />
					<#if screenArea.areaIcon?has_content>
						<#assign areaIcon = screenArea.areaIcon>
					<#else>
						<#assign areaIcon = "glyphicon qp-heading-icon">
					</#if>
					
					<#assign withAreaContent = "100%">
					<#assign alignAreaContent = "left">
					<#assign tblWidthUnit = (screenArea.tblWidthUnit)!"-1">
					<#if tblWidthUnit?string != "-1">
						<#assign withAreaContent = tblWidthUnit >
					</#if>
					<#if withAreaContent == "" >
						<#assign withAreaContent = "100%" >
					</#if>
					<#assign alignPositionType = (screenArea.alignPositionType)!"-1">
					<#if alignPositionType?string != "-1">
						<#if alignPositionType == 3>
							<#assign alignAreaContent = "right" >
						</#if>
					</#if>
					
					<#if screenForm.screenFormId ==  screenArea.screenFormId>
						
						<#if ((screenDesignForm.screenPatternType == 3 && !screenDesignForm.completionType?has_content) || 
						(screenDesignForm.screenPatternType == 3 && screenDesignForm.templateType == 2))>
							<#if screenItemHiddensSingle?has_content>
								<#list screenItemHiddensSingle as screenItem>
									<#if screenItem.screenArea.screenAreaId == screenArea.screenAreaId>
										<!-- Hidden -->
										<#assign parentOutputBeanJSTL = "" />
										<#if screenItem.parentOutputBeanCode?has_content>
											<#assign parentOutputBeanJSTL = "${screenItem.parentOutputBeanCode}${'.'}" >
										</#if>
										
										<#assign outputBeanCodeJSTL = "" />
										<#if screenItem.outputBeanCode?has_content>
											<#assign outputBeanCodeJSTL = "${screenItem.outputBeanCode}" >
										</#if>
										
										<#if parentOutputBeanJSTL?has_content && !outputBeanCodeJSTL?has_content>
											<#assign outputBeanCodeJSTL = screenItem.outputBeanCode>
										</#if>
										
										<#assign value = "">
										<#if !outputBeanCodeJSTL?has_content>
											<#assign value = "">
										<#else>
											<#assign value = "${'$'}{${maForDisplay}.${parentOutputBeanJSTL}${outputBeanCodeJSTL}}">
										</#if>
										
										<#assign name = "" >
										<#if screenItem.parentInputBeanCode?has_content && screenItem.inputBeanCode?has_content>
											<#assign name = "${screenItem.parentInputBeanCode}${'.'}" + "${screenItem.inputBeanCode}" >
										</#if> 
										<#if !screenItem.parentInputBeanCode?has_content && screenItem.inputBeanCode?has_content>
											<#assign name = "${screenItem.inputBeanCode}" >
										</#if>
										
										<#assign valueHidden = "" />
										<#if screenItem.physicalDataType?has_content>
											<#if screenItem.physicalDataType == 10>
												<#assign valueHidden ="<qp:formatDate value=\"${value}\" />">
											<#elseif (screenItem.physicalDataType == 11)>
												<#assign valueHidden ="<qp:formatTime value=\"${value}\" />">
											<#elseif screenItem.physicalDataType == 12>
												<#assign valueHidden ="<qp:formatDateTime value=\"${value}\" />">
											<#else>
												<#assign valueHidden ="${value}">
											</#if>
										</#if>
										<input type="hidden" name="${name}" value='${valueHidden}'>
										
									</#if>
								</#list>
							</#if>
						</#if>
						
						<#if screenDesignForm.screenPatternType != 3 && !screenDesignForm.completionType?has_content>
							<#if screenItemHiddensSingle?has_content>
								<#list screenItemHiddensSingle as screenItem>
									<#if screenItem.screenArea.screenAreaId == screenArea.screenAreaId>
										<!-- Hidden -->
										<#assign parentOutputBeanJSTL = "" />
										<#if screenItem.parentOutputBeanCode?has_content>
											<#assign parentOutputBeanJSTL = "${screenItem.parentOutputBeanCode}${'.'}" >
										</#if>
										
										<#assign outputBeanCodeJSTL = "" />
										<#if screenItem.outputBeanCode?has_content>
											<#assign outputBeanCodeJSTL = "${screenItem.outputBeanCode}" >
										</#if>
										
										<#if parentOutputBeanJSTL?has_content && !outputBeanCodeJSTL?has_content>
											<#assign outputBeanCodeJSTL = screenItem.outputBeanCode>
										</#if>
										
										<#assign value = "">
										<#if !outputBeanCodeJSTL?has_content>
											<#assign value = "">
										<#else>
											<#assign value = "${'$'}{${maForDisplay}.${parentOutputBeanJSTL}${outputBeanCodeJSTL}}">
										</#if>
										
										<#assign name = "" >
										<#if screenItem.parentInputBeanCode?has_content && screenItem.inputBeanCode?has_content>
											<#assign name = "${screenItem.parentInputBeanCode}${'.'}" + "${screenItem.inputBeanCode}" >
										</#if> 
										<#if !screenItem.parentInputBeanCode?has_content && screenItem.inputBeanCode?has_content>
											<#assign name = "${screenItem.inputBeanCode}" >
										</#if>
										
										<#if !screenItem.inputBeanCode?has_content>
											<#assign name = "" >
										</#if>
										
										<#assign valueHidden = "" />
										<#if screenItem.physicalDataType?has_content>
											<#if screenItem.physicalDataType == 10>
												<#assign valueHidden ="<qp:formatDate value=\"${value}\" />">
											<#elseif (screenItem.physicalDataType == 11)>
												<#assign valueHidden ="<qp:formatTime value=\"${value}\" />">
											<#elseif screenItem.physicalDataType == 12>
												<#assign valueHidden ="<qp:formatDateTime value=\"${value}\" />">
											<#else>
												<#assign valueHidden ="${value}">
											</#if>
										</#if>
										<input type="hidden" name="${name}" value='${valueHidden}'>
										
									</#if>
								</#list>
							</#if>
						</#if>
						<#assign startHtml = (screenArea.startHtml)!"-1">
						<#list screenArea.screenStatusConditions as condition>
							<c:if test="${'$'}{${condition}}">					
						</#list>
						<#if startHtml?string != "-1">
							${startHtml}
						</#if>
							<#switch screenArea.areaType>
								<#case 0 >
									<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};" name="${screenArea.areaCode}">
									<div class="panel panel-default qp-div-information">
										<#assign panelStyle = (screenArea.panelStyle)!"-1">
										<#if panelStyle != "-1">
											<#assign panelStyle = (screenArea.panelStyle)>
										</#if>
										<div class="panel-heading" style="${panelStyle}">
											<span aria-hidden="true" class="${areaIcon}">&nbsp;</span>
											<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
											<#if messageString?string != "-1">
												<span class="qp-heading-text"><qp:message code="${screenArea.messageDesign.messageCode}"/></span>
											</#if>
										</div>
										<div class="panel-body">
												<#assign scrAreaId = (screenArea.screenAreaId)?c>
												<table class="table table-bordered qp-table-form" id="tbl-${scrAreaId}" >
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
															<#assign parentOutputBeanJSTL = "" />
															<#if screenItem.parentOutputBeanCode?has_content>
																<#assign parentOutputBeanJSTL = "${screenItem.parentOutputBeanCode}${'.'}" >
															</#if>
															<#assign outputBeanCodeJSTL  = "" />
															<#if screenItem.outputBeanCode?has_content>
																<#assign outputBeanCodeJSTL = "${screenItem.outputBeanCode}" >
															</#if>
															
															<#assign elementName = "${maForDisplay}${'.'}${parentOutputBeanJSTL}${outputBeanCodeJSTL}">
														
															<#if screenDesignForm.screenPatternType == 3>
																<#if screenItem.parentInputBeanCode?has_content>
																	<#assign parentInputBeanCode = "${screenItem.parentInputBeanCode}${'.'}" >
																<#else>
																	<#assign parentInputBeanCode = "" />
																</#if>
																<#if screenItem.inputBeanCode?has_content>
																	<#assign inputBeanCode = "${screenItem.inputBeanCode}" >
																<#else>
																	<#assign inputBeanCode  = "" />
																</#if>
																<#assign elementNameInput = "${parentInputBeanCode}${inputBeanCode}">
																
																<#comment>
																<#if elementNameInput?has_content>
																	<input type="hidden" name="${elementNameInput}" value ="${'$'}{${elementName}}" />	
																</#if>
																</#comment>
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
																	<#if (logicalDataType?string != "-1" && logicalDataType?string == "20") && (itemType?string != "-1" && itemType?string != "5")>
																		<th index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																			${screenItem.element}
																		</th>
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
																					<#if elementStart?string != "-1" && screenItem.itemSeqNo?string == elementStart?string>
																						<td index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																					</#if>
		
																					<#if screenItem.value == "" || logicalDataType?string == "-1">
																					<#else>
																						<span style="width: 100%; float: left; padding-right: 3px;">
																							<#if screenDesignForm.screenPatternType == 1 || ((screenItem.logicalDataType == 21 || screenItem.logicalDataType == 20) && screenDesignForm.screenPatternType != 3 && screenDesignForm.screenPatternType != 4 && screenDesignForm.screenPatternType != 2 )>
																								<#if screenArea.areaType == 1>
																									<#if screenItem.physicalDataType?has_content>
																										<#if screenItem.itemCode?has_content || (screenItem.logicalDataType == 13 || screenItem.logicalDataType == 11)>
																											<#if (screenItem.physicalDataType == 1 || screenItem.physicalDataType == 3 || screenItem.physicalDataType == 2 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatText value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 5 || screenItem.physicalDataType == 6 || screenItem.physicalDataType == 7) || (screenItem.physicalDataType == 13) || (screenItem.physicalDataType == 14) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatInteger value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 4 || screenItem.physicalDataType == 16 || screenItem.physicalDataType == 17) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatFloat value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 10) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																												<qp:formatDate value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 15)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatCurrency value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 8)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:booleanFormatYesNo value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 11 || screenItem.physicalDataType == 10 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatTime value="${dollar}{${elementName}}" />
																											<#elseif screenItem.physicalDataType == 12 && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatDateTime value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 9)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<#comment>
																													<qp:image width="" heigth="" src="${dollar}{${elementName}}" />
																												</#comment>
																												<label></label>
																											<#elseif screenItem.logicalDataType == 11>
																												${screenItem.element}
																											<#elseif screenItem.logicalDataType == 13>
																												${screenItem.element}
																											<#elseif screenItem.logicalDataType == 0 || screenItem.logicalDataType == 2 || screenItem.logicalDataType == 5 || screenItem.logicalDataType == 6 || screenItem.logicalDataType == 7 >
																												<qp:formatText value="${dollar}{${elementName}}" />
																											</#if>
																										</#if>
																									<#else>
																										${screenItem.element}
																									</#if>
																								<#else>
																									<#if screenItem.physicalDataType?has_content>
																										<#if screenItem.itemCode?has_content && screenItem.logicalDataType == 20>
																											<#if (screenItem.physicalDataType == 10) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																												<qp:formatDate value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 11 || screenItem.physicalDataType == 10 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatTime value="${dollar}{${elementName}}" />
																											<#elseif screenItem.physicalDataType == 12 && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatDateTime value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 15) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																												<qp:formatCurrency value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 16 || screenItem.physicalDataType == 17 || screenItem.physicalDataType == 4) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																												<qp:formatFloat value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 1 || screenItem.physicalDataType == 3 || screenItem.physicalDataType == 2 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatText value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 5 || screenItem.physicalDataType == 6 || screenItem.physicalDataType == 7) || (screenItem.physicalDataType == 13) || (screenItem.physicalDataType == 14) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatInteger value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 8)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:booleanFormatYesNo value="${dollar}{${elementName}}" />
																											<#else>
																												${screenItem.element}
																											</#if>
																										<#else>
																											${screenItem.element}
																										</#if>												
																									<#else>
																										${screenItem.element}
																									</#if>
																								</#if>
																							</#if>
																									
																							<#assign index = 0>
																							<#if screenDesignForm.screenPatternType == 3>
																								<#if (screenItem.logicalDataType == 21 && screenItem.dataSourceType?has_content && screenItem.dataSourceType == 2) || (screenItem.physicalDataType?has_content && screenItem.physicalDataType != 10 && screenItem.physicalDataType != 11 && screenItem.physicalDataType != 12 && screenItem.physicalDataType != 15 && screenItem.physicalDataType != 16 && screenItem.physicalDataType != 17 && screenItem.physicalDataType != 4 && screenItem.physicalDataType != 5 && screenItem.physicalDataType != 7 && screenItem.physicalDataType != 13 && screenItem.physicalDataType != 14 && screenItem.physicalDataType != 6 && screenItem.physicalDataType != 1 && screenItem.physicalDataType != 2 && screenItem.physicalDataType != 3)>
																									${screenItem.element}
																									<#assign index = index + 1>
																								</#if>
																								
																								<#if index < 1>
																									<#if screenItem.physicalDataType?has_content || (screenItem.logicalDataType == 13 || screenItem.logicalDataType == 11)>
																										<#if screenItem.itemCode?has_content>
																											<#if (screenItem.physicalDataType == 1 || screenItem.physicalDataType == 3 || screenItem.physicalDataType == 2 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatText value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 5 || screenItem.physicalDataType == 6 || screenItem.physicalDataType == 7) || (screenItem.physicalDataType == 13) || (screenItem.physicalDataType == 14) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatInteger value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 4 || screenItem.physicalDataType == 16 || screenItem.physicalDataType == 17) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatFloat value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 10) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																												<qp:formatDate value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 15)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatCurrency value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 8)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:booleanFormatYesNo value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 11 || screenItem.physicalDataType == 10 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatTime value="${dollar}{${elementName}}" />
																											<#elseif screenItem.physicalDataType == 12 && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<qp:formatDateTime value="${dollar}{${elementName}}" />
																											<#elseif (screenItem.physicalDataType == 9)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																												<#comment>
																													<qp:image width="" heigth="" src="${dollar}{${elementName}}" />
																												</#comment>
																												<label></label>
																											<#else>
																												${screenItem.element}
																											</#if>
																										<#else>
																											${screenItem.element}
																										</#if>
																									</#if>
																								</#if>
																							</#if>
																							
																							<#if (screenDesignForm.screenPatternType != 1 && screenDesignForm.screenPatternType != 3) >
																								<#if screenItem.logicalDataType?has_content && screenItem.logicalDataType == 20>
																									<#if screenItem.outputBeanCode?has_content>
																										<#if (screenItem.physicalDataType == 1 || screenItem.physicalDataType == 3 || screenItem.physicalDataType == 2 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:formatText value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 5 || screenItem.physicalDataType == 6 || screenItem.physicalDataType == 7) || (screenItem.physicalDataType == 13) || (screenItem.physicalDataType == 14) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:formatInteger value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 4 || screenItem.physicalDataType == 16 || screenItem.physicalDataType == 17) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:formatFloat value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 10) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																											<qp:formatDate value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 15)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:formatCurrency value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 8)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:booleanFormatYesNo value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 11 || screenItem.physicalDataType == 10 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:formatTime value="${dollar}{${elementName}}" />
																										<#elseif screenItem.physicalDataType == 12 && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<qp:formatDateTime value="${dollar}{${elementName}}" />
																										<#elseif (screenItem.physicalDataType == 9)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																											<#comment>
																												<qp:image width="" heigth="" src="${dollar}{${elementName}}" />
																											</#comment>
																											<label></label>
																										</#if>
																									</#if>
																								<#else>
																									<#comment>
																										User for dynamicLabel
																									</#comment>
																									${screenItem.element}
																								</#if>
																							</#if>
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
									<#assign areaTypeAction = "" >
									<#if screenArea.areaTypeAction?has_content>
										<#assign areaTypeAction = screenArea.areaTypeAction >
									</#if>
									<#assign fixedRow = "" >
									<#if screenArea.fixedRow?has_content>
										<#assign fixedRow = screenArea.fixedRow >
									</#if>
									<#assign isTrTemp = "false" >
									<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};" name="${screenArea.areaCode}">
									<div class="panel panel-default qp-div-select">
											<div class="panel-heading">
												<#if screenDesignForm.screenPatternType == 1>
													<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 2>
														<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
														<#assign tableCaption = "" />
														<#if messageString?string != "-1">
															<#assign tableCaption = screenArea.messageDesign.messageCode >
														</#if>
														<#if moduleCode?has_content>
															<qp:itemPerPage form="${modelAttribute}" action="/${moduleCode}/${screenDesignForm.screenCode}" pagearea="${dollar}{page${screenArea.areaCode}}" overwriteMessage="${tableCaption}" />
														<#else>	
															<qp:itemPerPage form="${modelAttribute}" action="" pagearea="${dollar}{page${screenArea.areaCode}}" overwriteMessage="${tableCaption}" />
														</#if>
													<#else>
														<span aria-hidden="true" class="${areaIcon}">&nbsp;</span>
														<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
														<#if messageString?string != "-1">
															<span class="qp-heading-text"><qp:message code="${screenArea.messageDesign.messageCode}"/></span>
														</#if>
													</#if>
												<#else>
													<span aria-hidden="true" class="${areaIcon}">&nbsp;</span>
													<#assign messageString = (screenArea.messageDesign.messageString)!"-1">
													<#if messageString?string != "-1">
														<span class="qp-heading-text"><qp:message code="${screenArea.messageDesign.messageCode}"/></span>
													</#if>
												</#if>
											</div>
											<div class="panel-body">
												<#if screenDesignForm.screenPatternType == 1 && ((screenArea.fixedRow)?has_content && screenArea.fixedRow == 0) && ((screenArea.areaPatternType)?has_content && screenArea.areaPatternType == 3) && (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction != 1)>
													<c:if test="${'$'}{page${screenArea.areaCode} != null && page${screenArea.areaCode}.totalPages > 0 }">
												</#if>
													<#assign rowIndex = 0>
													<#assign colIndex = 0>
													<#assign shortIndex = 0>
													<#assign col = screenArea.totalCol>
													
													<table class="table table-bordered qp-table-list" id="tbl-${srcAreaId}">
														<colgroup>
															<#assign columnIndexColGroup = 0>
															<col width="35px">
															<#list screenArea.colWidthUnit?split(",") as itemWidth>
																<#if itemWidth?has_content>
																	<col width="${itemWidth }"></col>
																	<#assign columnIndexColGroup = columnIndexColGroup + 1>
																</#if>
															</#list>
															<#if columnIndexColGroup < screenArea.totalCol>
																<#if screenDesignForm.screenPatternType == 1>
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
															<#if screenArea.areaPatternType == 1 || screenArea.areaPatternType == 2>
																<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1) && (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0>
																	<col width="50px">
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
																	<#assign itemType = (screenItem.itemType)!"-1">
																	<#if ((logicalDataType?string != "-1" && logicalDataType?string == "20") && (itemType?string != "-1" && itemType?string != "5")) || !logicalDataType?has_content >
																		
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
																		
																		<#if colIndex?number == 0>
																			<tr index="${rowIndex}">
																				<#if screenArea.tblHeaderRow?has_content>
																					<#if screenArea.tblHeaderRow == 1>
																						<th style="text-align: left;${isDisable} ${headerStyle}"><qp:message code="sc.sys.0004" /></th>
																					<#elseif screenArea.tblHeaderRow != 1 && headerRow == 0>
																						<th rowspan="${screenArea.tblHeaderRow}" style="text-align: left;${isDisable} ${headerStyle}"><qp:message code="sc.sys.0004" /></th>
																						<#assign headerRow = headerRow + 1>
																					</#if>
																				<#else>
																					<th style="text-align: left;${isDisable} ${headerStyle}"><qp:message code="sc.sys.0004" /></th>
																				</#if>
																			
																		</#if>
																		
																		<#if screenDesignForm.screenPatternType == 1>
																			<#assign messagedesign = (screenItem.messageDesign)!"-1">
																			<#if messagedesign != "-1">
																				<th style="text-align: left;${isDisable} ${headerStyle}">
																					<#if messagedesign.messageString?has_content>
																						<#assign columnCode = "" />
																						<#if screenItem.columnCode?has_content>
																							<#assign columnCode = screenItem.columnCode >
																						</#if>
																						<#if screenArea.screenAreaSortMappings?has_content>
																							<#assign sqlColumnCode = "" >
																							<#if screenArea.screenAreaSortMappings[shortIndex].sqlColumnCode?has_content>
																								<#assign sqlColumnCode = screenArea.screenAreaSortMappings[shortIndex].sqlColumnCode >
																							</#if>
																							<qp:columnSort colName="${sqlColumnCode}" colCode="${messagedesign.messageCode}" form="${modelAttribute}"></qp:columnSort>
																						<#else>
																							<qp:message code="${messagedesign.messageCode}" />
																						</#if>
																					</#if>
																				</th>
																			<#else>
																				<th style="text-align: left;${isDisable} ${headerStyle}"></th>
																			</#if>
																		<#else>
																			<th index="${colIndex }" style="${isDisable } ${headerStyle}" ${colSpanProperties} ${rowSpanProperties} >
																				${screenItem.element}
																			</th>
																		</#if>
																		
																		<#assign colIndex = colIndex?number + 1>
																		<#assign shortIndex = shortIndex?number + 1>
																		<#if colIndex?number == col>		
																			<#if screenArea.areaPatternType == 1 || screenArea.areaPatternType == 2>
																				<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1) && (screenArea.fixedRow)?has_content && screenArea.fixedRow == 0>
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
															<#assign indexForForEachInput = 0 >
															<#assign indexForForEachOutput = 0 >
															
															<#assign maregister = "" />
															<#if maForRegister?has_content>
																<#assign maregister = maForRegister >
															</#if>
															
															<#if screenDesignForm.screenPatternType == 1 >
																<#if (areaTypeAction?string != "1" && fixedRow?string != "1") || (areaTypeAction?string == "1" && fixedRow?string == "0") || areaTypeAction?string == "0" || areaTypeAction?string == "2" >
																	<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content>
																		 <c:forEach var="item" items="${dollar}{${maForDisplay}.${parentOutputForForEach}}" varStatus="status">
																	<#elseif areaTypeAction?string == "2" >
																		<c:forEach var="item" items="${dollar}{page${screenArea.areaCode}.content}" varStatus="status">
																	</#if>
																</#if>
															<#elseif screenDesignForm.screenPatternType == 3>
																	<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																		<c:forEach var="item" items="${dollar}{${maForDisplay}.${parentOutputForForEach}}" varStatus="status">
																		<#assign checkListEmpty = "${maForDisplay}${'.'}${parentOutputForForEach}" >
																	<#elseif parentOutputForForEach?has_content>
																		<c:forEach var="item" items="${dollar}{${maForDisplay}.${parentOutputForForEach}}" varStatus="status">
																	</#if>
																	
															<#elseif screenDesignForm.screenPatternType == 4>
																	<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																		<c:forEach var="item" items="${dollar}{${maForDisplay}.${parentOutputForForEach}}" varStatus="status">
																		<#assign checkListEmpty = "${maForDisplay}${'.'}${parentOutputForForEach}" >
																	<#elseif parentInputForForEach?has_content>
																		<c:forEach var="item" items="${dollar}{${maregister}.${parentInputForForEach?replace("[${'$'}{status.index}]","")}}" varStatus="status">
																	</#if>
															<#elseif screenDesignForm.screenPatternType == 2>
																<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																	<c:forEach var="item" items="${dollar}{${maForDisplay}.${parentOutputForForEach}}" varStatus="status">
																	<#assign checkListEmpty = "${maForDisplay}${'.'}${parentOutputForForEach}" >
																<#elseif parentInputForForEach?has_content>
																	<c:forEach var="item" items="${dollar}{${maregister}.${parentInputForForEach?replace("[${'$'}{status.index}]","")}}" varStatus="status">
																</#if>
															</#if>
															
															<#list screenArea.items as screenItem>
																<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																	
																	<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																	<#assign itemType = (screenItem.itemType)!"-1">
																	<#if (logicalDataType?string == "-1" || logicalDataType != 20) || itemType?string == "5">
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
																			<#if screenItem.parentInputBeanCode?has_content>
																				<#assign parentInput = "${screenItem.parentInputBeanCode}" >
																			<#else>
																				<#assign parentInput = "" />
																			</#if>
																			
																			<#if screenItem.parentOutputBeanCode?has_content>
																				<#assign parentOutput = "${screenItem.parentOutputBeanCode}" >
																			<#else>
																				<#assign parentOutput = "" />
																			</#if>
																			
																			<#if screenItemEvents?has_content>
																				<#if i == 0>
																					<#comment>
																						--------------------------------------------------------------------------------
																						Gen tr temp
																					</#comment>
																						<#assign col = screenArea.totalCol>
																						<#assign rowIndex = 0>
																						<#assign colRowSpanItem ="">
																						<#assign colIndex = 0>
																						<#assign currentIndex ="">
																						<#assign arrColRow></#assign>
																						<#assign arrCurrentIndex></#assign>
																						<#assign isGroup = false>
																						<#list screenArea.items as screenItem>
																							<#if screenItem.screenAreaId ==  screenArea.screenAreaId>
																								<#assign logicalDataType = (screenItem.logicalDataType)!"-1">
																								<#assign itemType = (screenItem.itemType)!"-1">
																								<#if (logicalDataType?string == "-1" || logicalDataType != 20) || itemType?string == "5">
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
																											<tr style="display:none" index="${rowIndex }">
																											<#if componentRowRemoveTd == 0>
																												<td rowspan="${tblComponentRowTemp}" class="qp-output-fixlength tableIndex">1</td>
																												<#assign componentRowRemoveTd = 1>
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
																												
																												<#assign styleFirstColumn = "" />
																												<#if colIndex == 0>
																													<#assign styleFirstColumn = "text-align: left;">
																												</#if>
																												 
																												<#assign fieldFormat = (screenItem.fieldStyle)!"-1">
																												<#if fieldFormat?string != "-1">
																													<#assign fieldFormat = (screenItem.fieldStyle)>
																												</#if>
																												
																												<#assign inputStyle = (screenArea.inputStyle)!"-1">
																												<#if inputStyle?string != "-1">
																													<#assign inputStyle = (screenArea.inputStyle)>
																												</#if>
																												
																												<#assign elementStart = (screenGroup.elementStart)!"-1">
																												<#assign elementEnd = (screenGroup.elementEnd)!"-1">
																												<#if elementStart?string != "-1" && elementEnd?string != "-1" && elementStart != elementEnd>
																													<#assign fieldFormat = "result-text">
																												</#if>
																												
																												<#assign itemCode = "" >
																												<#if screenItem.itemCode?has_content>
																													<#assign itemCode = screenItem.itemCode >
																												</#if>
																												
																												<#if screenDesignForm.screenPatternType == 1 || screenDesignForm.screenPatternType == 3>
																														<td itemCode="${itemCode}">
																												<#else>
																													<td class='${styleFirstCol } ${styleGroup } ${inputStyle} ${fieldFormat}' index="${colIndex }" style="${styleFirstColumn} ${isDisable }" ${colSpanProperties} ${rowSpanProperties} itemCode= "${itemCode}">
																												</#if>
																											</#if>
																												
																											${(screenItem.elementTemplate)?replace("[${'$'}{status.index}]","[0]")}
																														
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
																													<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																												</td>
																											</#if>
																										</#if>
																											<#if parentOutputBean?has_content || parentInputBean?has_content || screenDesignForm.screenPatternType == 1>
																												</tr>
																											</#if> 
																										<#assign colIndex = 0 >
																										<#assign rowIndex = rowIndex + 1>
																									</#if>
																								</#if>
																							</#if>
																						</#list>
																					<#comment>
																						--------------------------------------------------------------------------------
																					</#comment>
																					<#assign i = i + 1>
																				</#if>
																			</#if>
																			
																			<#assign styleFirstCol = "align-left" >
																			
																			<#assign td = "" >
																			<#assign tdForScreenSeach = "" >
																			<#assign tdScreenSearch = "" >
																			
																			<#if screenArea.tblComponentRow?has_content>
																				<#if screenArea.tblComponentRow == 1>
																					<#assign td = tdNotComponentRow>
																					<#assign tdScreenSearch = tdNotComponentRowSearch >
																				<#elseif screenArea.tblComponentRow != 1 && tdComponentRowIndex == 0>
																					<#assign td = tdComponentRow>
																					<#assign tdScreenSearch = tdComponentRowSearch >
																					<#assign tdComponentRowIndex = tdComponentRowIndex + 1>
																				</#if>
																			</#if>
																			
																			<#if !screenArea.tblComponentRow?has_content>
																				<#assign td = tdNotComponentRow>
																			</#if>
																			
																			<#assign maregister = "" />
																			<#if maForRegister?has_content>
																				<#assign maregister = maForRegister >
																			</#if>
																			
																			<#assign checkListEmpty = "">
																			
																			<#if screenDesignForm.screenPatternType == 1 >
																				<#if (areaTypeAction?string != "1" && fixedRow?string != "1") || (areaTypeAction?string == "1" && fixedRow?string == "0") || areaTypeAction?string == "0" || areaTypeAction?string == "2" >
																					<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content>
																							<tr>
																								${td}
																					<#elseif areaTypeAction?string == "2" >
																							<tr>
																								<td><qp:formatInteger value="${dollar}{(page${screenArea.areaCode}.number * page${screenArea.areaCode}.size) + status.count}" /></td>
																					<#else>
																						<tr>
																							${td}
																					</#if>
																					
																				</#if>
																			<#elseif screenDesignForm.screenPatternType == 3>
																					<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																							<tr>
																								${td}
																						<#assign checkListEmpty = "${maForDisplay}${'.'}${parentOutputForForEach}" >
																					<#elseif parentOutputForForEach?has_content>
																							<tr>
																								${td}
																					<#else>
																						<tr>
																							${td}
																					</#if>
																					
																			<#elseif screenDesignForm.screenPatternType == 4>
																					<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																							<tr>
																								${td}
																						<#assign checkListEmpty = "${maForDisplay}${'.'}${parentOutputForForEach}" >
																					<#elseif parentInputForForEach?has_content>
																							<tr>
																								${td}
																					<#else>
																						<tr>
																							${td}
																					</#if>
																			<#elseif screenDesignForm.screenPatternType == 2>
																					<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																							<tr>
																								${td}
																						<#assign checkListEmpty = "${maForDisplay}${'.'}${parentOutputForForEach}" >
																					<#elseif parentInputForForEach?has_content>
																							<tr>
																								${td}
																					<#else>
																						<tr>
																							${td}
																					</#if>
																					
																			<#else>
																				<tr index="${rowIndex }"><td>
																					1
																				</td>
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
																					
																					<#assign styleFirstColumn = "" />
																					<#if colIndex == 0>
																						<#assign styleFirstColumn = "text-align: left;">
																					</#if>
																					 
																					<#assign fieldFormat = (screenItem.fieldStyle)!"-1">
																					<#if fieldFormat?string != "-1">
																						<#assign fieldFormat = (screenItem.fieldStyle)>
																					</#if>
																					
																					<#assign inputStyle = (screenArea.inputStyle)!"-1">
																					<#if inputStyle?string != "-1">
																						<#assign inputStyle = (screenArea.inputStyle)>
																					</#if>
																					
																					<#assign elementStart = (screenGroup.elementStart)!"-1">
																					<#assign elementEnd = (screenGroup.elementEnd)!"-1">
																					<#if elementStart?string != "-1" && elementEnd?string != "-1" && elementStart != elementEnd>
																						<#assign fieldFormat = "result-text">
																					</#if>
																					
																					<#assign itemCode = "" >
																					<#if screenItem.itemCode?has_content>
																						<#assign itemCode = screenItem.itemCode >
																					</#if>
																					
																					<#if screenDesignForm.screenPatternType == 1 || screenDesignForm.screenPatternType == 3>
																							<td itemCode="${itemCode}">
																					<#else>
																						<td class='${styleFirstCol } ${styleGroup } ${inputStyle} ${fieldFormat}' index="${colIndex }" style="${styleFirstColumn} ${isDisable }" ${colSpanProperties} ${rowSpanProperties} itemCode= "${itemCode}">
																					</#if>
																				</#if>
																				
																				<#assign indexList = 0>
																				<#if screenDesignForm.screenPatternType == 1>
																					<#if screenArea.areaType == 1>
																						<#if screenItem.logicalDataType?has_content>
																							<#if (screenItem.logicalDataType == 21 && screenItem.dataSourceType?has_content && screenItem.dataSourceType == 2) || (screenItem.physicalDataType?has_content && screenItem.physicalDataType != 10 && screenItem.physicalDataType != 11 && screenItem.physicalDataType != 12 && screenItem.physicalDataType != 15 && screenItem.physicalDataType != 16 && screenItem.physicalDataType != 17 && screenItem.physicalDataType != 4 && screenItem.physicalDataType != 5 && screenItem.physicalDataType != 7 && screenItem.physicalDataType != 13 && screenItem.physicalDataType != 14 && screenItem.physicalDataType != 6 && screenItem.physicalDataType != 1 && screenItem.physicalDataType != 2 && screenItem.physicalDataType != 3)>
																								${screenItem.element}
																								<#assign indexList = indexList + 1>
																							</#if>
																							
																						</#if>
																						<#if indexList < 1>
																							<#if screenItem.physicalDataType?has_content>
																								<#if screenItem.outputBeanCode?has_content || (screenItem.logicalDataType == 13 || screenItem.logicalDataType == 11)>
																									<#if (screenItem.physicalDataType == 1 || screenItem.physicalDataType == 3 || screenItem.physicalDataType == 2 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:formatText value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif ((screenItem.physicalDataType == 5 || screenItem.physicalDataType == 6 || screenItem.physicalDataType == 7) || (screenItem.physicalDataType == 13) || (screenItem.physicalDataType == 14)) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:formatInteger value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif (screenItem.physicalDataType == 4 || screenItem.physicalDataType == 16 || screenItem.physicalDataType == 17) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:formatFloat value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif (screenItem.physicalDataType == 10) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>	
																										<qp:formatDate value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif (screenItem.physicalDataType == 15)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:formatCurrency value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif (screenItem.physicalDataType == 8)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:booleanFormatYesNo value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif (screenItem.physicalDataType == 11 || screenItem.physicalDataType == 10 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:formatTime value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif screenItem.physicalDataType == 12 && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<qp:formatDateTime value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									<#elseif (screenItem.physicalDataType == 9)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																										<#comment>
																											<qp:image width="" heigth="" src="${dollar}{item.${screenItem.outputBeanCode}}" />
																										</#comment>
																										<label></label>
																									<#elseif screenItem.logicalDataType == 11 || screenItem.logicalDataType == 22>
																										${screenItem.element}
																									<#elseif screenItem.logicalDataType == 13>
																										${screenItem.element}
																									<#elseif screenItem.logicalDataType == 0 || screenItem.logicalDataType == 2 || screenItem.logicalDataType == 5 || screenItem.logicalDataType == 6 || screenItem.logicalDataType == 7 >
																										<qp:formatText value="${dollar}{item.${screenItem.outputBeanCode}}" />
																									</#if>
																								</#if>
																							<#else>
																								${screenItem.element}
																							</#if>
																						</#if>
																					<#else>
																						${screenItem.element}
																					</#if>
																				</#if>
																				
																				<#if i == 0>
																					<#if screenItemHiddensList?has_content>
																						<#list screenItemHiddensList as screenItem>
																							<#assign parentOutputBeanJSTL = "" />
																							<#if screenItem.parentOutputBeanCode?has_content>
																								<#assign parentOutputBeanJSTL = "${screenItem.parentOutputBeanCode}${'.'}" >
																								<#assign parentOutputBeanJSTLNot = "${screenItem.parentOutputBeanCode}" >
																							</#if>
																							
																							<#assign outputBeanCodeJSTL = "" />
																							<#if screenItem.outputBeanCode?has_content>
																								<#assign outputBeanCodeJSTL = "${screenItem.outputBeanCode}" >
																							</#if>
																							<#if parentOutputBeanJSTL?has_content && !outputBeanCodeJSTL?has_content>
																								<#assign outputBeanCodeJSTL = screenItem.itemCode>
																							</#if>
																							
																							
																							<#assign parentInputBeanJSTL = "" />
																							<#if screenItem.parentInputBeanCodeForTemp?has_content>
																								<#assign parentInputBeanJSTL = "${screenItem.parentInputBeanCodeForTemp}${'.'}" >
																								<#assign parentInputBeanJSTLNot = "${screenItem.parentInputBeanCodeForTemp}" >
																							</#if>
																							
																							<#assign inputBeanCodeJSTL = "" />
																							<#if screenItem.inputBeanCode?has_content>
																								<#assign inputBeanCodeJSTL = "${screenItem.inputBeanCode}" >
																							</#if>
																							<#if parentInputBeanJSTL?has_content && !inputBeanCodeJSTL?has_content>
																								<#assign inputBeanCodeJSTL = screenItem.itemCode>
																							</#if>
																							
																							<#assign name = "" >
																							<#if parentInputBeanJSTL?has_content>
																								<#assign status = status >
																								<#assign name = parentInputBeanJSTLNot + status + screenItem.itemCode>
																							</#if> 
																							<#if !parentInputBeanJSTL?has_content>
																								<#assign name = screenItem.itemCode >
																							</#if>
																							<#comment>
																								<input type="hidden" name="${name}" value="${'$'}{item.${parentOutputBeanJSTL}${outputBeanCodeJSTL}}">
																								<input type="hidden" name="${name}" value="${'$'}{item.${outputBeanCodeJSTL}}">
																							</#comment>
																							
																							<#assign valueHidden = "" />
																							<#if screenItem.physicalDataType?has_content>
																								<#if screenItem.physicalDataType == 10>
																									<#assign valueHidden ="<qp:formatDate value=\"${'$'}{item.${outputBeanCodeJSTL}}\" />">
																								<#elseif (screenItem.physicalDataType == 11)>
																									<#assign valueHidden ="<qp:formatTime value=\"${'$'}{item.${outputBeanCodeJSTL}}\" />">
																								<#elseif screenItem.physicalDataType == 12>
																									<#assign valueHidden ="<qp:formatDateTime value=\"${'$'}{item.${outputBeanCodeJSTL}}\" />">
																								<#else>
																									<#assign valueHidden ="${'$'}{item.${outputBeanCodeJSTL}}" >
																								</#if>
																							</#if>
																							<input type="hidden" name="${name}" value='${valueHidden}'>
																							
																						</#list>
																					</#if>
																					<#assign i = i + 1>
																				</#if>
																				
																				<#if screenDesignForm.screenPatternType == 3>
																					<#if screenItem.physicalDataType?has_content>
																						<#if screenItem.outputBeanCode?has_content || (screenItem.logicalDataType == 13 || screenItem.logicalDataType == 11)>
																							<#if (screenItem.physicalDataType == 1 || screenItem.physicalDataType == 3 || screenItem.physicalDataType == 2 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<qp:formatText value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif ((screenItem.physicalDataType == 5 || screenItem.physicalDataType == 6 || screenItem.physicalDataType == 7) || (screenItem.physicalDataType == 13) || (screenItem.physicalDataType == 14)) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11 && screenItem.logicalDataType != 22)>
																								<qp:formatInteger value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif (screenItem.physicalDataType == 4 || screenItem.physicalDataType == 16 || screenItem.physicalDataType == 17) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<qp:formatFloat value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif (screenItem.physicalDataType == 10) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>	
																								<qp:formatDate value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif (screenItem.physicalDataType == 15)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<qp:formatCurrency value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif (screenItem.physicalDataType == 8)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<qp:booleanFormatYesNo value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif (screenItem.physicalDataType == 11 || screenItem.physicalDataType == 10 ) && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<qp:formatTime value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif screenItem.physicalDataType == 12 && (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<qp:formatDateTime value="${dollar}{item.${screenItem.outputBeanCode}}" />
																							<#elseif (screenItem.physicalDataType == 9)&& (screenItem.logicalDataType != 13 && screenItem.logicalDataType != 11)>
																								<#comment>
																									<qp:image width="" heigth="" src="${dollar}{item.${screenItem.outputBeanCode}}" />
																								</#comment>
																								<label></label>
																							</#if>
																						</#if>
																					</#if>
																				</#if>
																				
																				<#if screenDesignForm.screenPatternType == 2 || screenDesignForm.screenPatternType == 4>
																					${screenItem.element}
																				</#if>
																				
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
																				<#if screenArea.areaPatternType == 1 ||  screenArea.areaPatternType == 2>
																					<#if screenArea.tblComponentRow?has_content>
																						<#if screenArea.tblComponentRow == 1>
																							<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1) && ((screenArea.fixedRow)?has_content && screenArea.fixedRow == 0)>
																								<td style='text-align: center;'>
																									<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																								</td>
																							</#if>
																						<#elseif screenArea.tblComponentRow != 1 && componentRowRemove == 0>
																							<td rowspan="${screenArea.tblComponentRow}" style='text-align: center;'><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
																							<#assign componentRowRemove = componentRowRemove + 1>
																						</#if>
																					<#else>
																						<#if (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 1) && ((screenArea.fixedRow)?has_content && screenArea.fixedRow == 0)>
																							<td style='text-align: center;'>
																								<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="${removeRow?string};" style="margin-top: 3px;" href="javascript:void(0)"></a>
																							</td>																									
																						</#if>																						
																					</#if>
																				</#if>
																			</tr>
																			<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 0>
																				<#if checkListEmpty?has_content>
																					<c:if test="${"$"}{empty ${checkListEmpty}}">
																						<tr>
																							<td colspan="${col?number + 1}" ><qp:message code="inf.sys.0013"/></td>
																						</tr>
																					</c:if>
																				</#if>
																			</#if>
																			<#assign colIndex = 0 >
																			<#assign rowIndex = rowIndex + 1>
																		</#if>
																	</#if>
																</#if>
															</#list>
															<#if screenDesignForm.screenPatternType == 1 >
																<#if (areaTypeAction?string != "1" && fixedRow?string != "1") || (areaTypeAction?string == "1" && fixedRow?string == "0") || areaTypeAction?string == "0" || areaTypeAction?string == "2" >
																	<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content>
																		 </c:forEach>
																	<#elseif areaTypeAction?string == "2" >
																		</c:forEach>
																	</#if>
																</#if>
															<#elseif screenDesignForm.screenPatternType == 3>
																	<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																		</c:forEach>
																	<#elseif parentOutputForForEach?has_content>
																		</c:forEach>
																	</#if>
																	
															<#elseif screenDesignForm.screenPatternType == 4>
																	<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																		</c:forEach>
																	<#elseif parentInputForForEach?has_content>
																		</c:forEach>
																	</#if>
															<#elseif screenDesignForm.screenPatternType == 2>
																<#if areaTypeAction?string == "0" && parentOutputForForEach?has_content> 
																	</c:forEach>
																<#elseif parentInputForForEach?has_content>
																	</c:forEach>
																</#if>
															</#if>
														</tbody>
													</table>
												<#if screenDesignForm.screenPatternType == 1 && ((screenArea.fixedRow)?has_content && screenArea.fixedRow == 0) && ((screenArea.areaPatternType)?has_content && screenArea.areaPatternType == 3) && (screenArea.areaTypeAction?has_content && screenArea.areaTypeAction != 1)>
													</c:if>
												</#if>
												
												<#if screenDesignForm.screenPatternType == 1>
													<#if screenArea.areaTypeAction?has_content && screenArea.areaTypeAction == 2>
														<div class="qp-div-action">
															<#assign maforregister = "" />
															<#if maForRegister?has_content>
																<#assign maforregister = maForRegister >
															</#if>
															<#assign modelAttribute = maforregister>
															<c:choose>
																<c:when test="${dollar}{page${screenArea.areaCode}.sort != null }">
																	<qp:pagination outerElementClass="pagination pagination-md" page="${dollar}{page${screenArea.areaCode}}" queryTmpl="page={${screenArea.areaCode}}&size={size}&sort={sortOrderProperty},{sortOrderDirection}" criteriaQuery="${'$'}{f:query(${modelAttribute})}" maxDisplayCount="5" />
																</c:when>
																<c:otherwise>
																	<qp:pagination outerElementClass="pagination pagination-md" page="${dollar}{page${screenArea.areaCode}}" criteriaQuery="${'$'}{f:query(${modelAttribute})}" maxDisplayCount="5" />
																</c:otherwise>
															</c:choose>
														</div>
													</#if>
												</#if>
												<#if (areaTypeAction?string == "1") && (fixedRow?string == "0") >
													<div class="qp-add-left">
														<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="${addRow?string}; $.qp.initialAllPicker($('#${srcAreaId}'));" style="margin-top: 3px;" href="javascript:void(0)"></a>
													</div>
												</#if>
											</div>
										</div>
										</div>
										<#break>
								<#case 2>
									<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};" name="${screenArea.areaCode}">
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
														
														<#assign itemWidthUnit = "" />
														<#if screenItem.itemWidthUnit?has_content>
															<#assign itemWidthUnit = screenItem.itemWidthUnit >
														<#else>
															<#assign itemWidthUnit = "" >
														</#if>
														<#assign alignPositionType = "" />
														<#if screenArea.alignPositionType?has_content>
															<#assign alignPositionType = screenArea.alignPositionType >
														<#else>
															<#assign alignPositionType = "right" >
														</#if> 
														<span style="width: ${itemWidthUnit} ; padding: 2px; float: ${alignPositionType};" >
															${screenItem.element}
														</span>
														
													</#if>
												</#list>
											</div>
										</div>
									</div>
									<#break>
								<#case 3>
									<div class="" style="width: ${withAreaContent}; float:${alignAreaContent};" name="${screenArea.areaCode}">
										<div class="panel panel-default qp-div-information">
											<div class="panel-heading">
												<span class="${areaIcon}" aria-hidden="true"></span>
												<span class="qp-heading-text">
													<span>${screenArea.messageDesign.messageString}</span>
												</span>
											</div>
											<div class="panel-body">
													<div class="section-area ui-droppable ui-sortable">
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
																<#assign itemWidthUnit = "" />
																<#if screenItem.itemWidthUnit?has_content>
																	<#assign itemWidthUnit = screenItem.itemWidthUnit >
																<#else>
																	<#assign itemWidthUnit = "25%" >
																</#if>
																<#assign alignPositionType = "" />
																<#if screenArea.alignPositionType?has_content>
																	<#assign alignPositionType = screenArea.alignPositionType >
																<#else>
																	<#assign alignPositionType = "left" >
																</#if> 
																
																<span style="width: ${itemWidthUnit} ; padding: 2px; float: ${alignPositionType};" >
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
									<#if screenArea.customSectionContent?has_content>
										<qp:custom>${screenArea.customSectionContent}</qp:custom>
									</#if>
								<#break>
							</#switch>
							<#assign endHtml = (screenArea.endHtml)!"-1">
							<#if endHtml?string != "-1">
								${endHtml}
							</#if>
						<#list screenArea.screenStatusConditions as condition>
							</c:if>					
						</#list>
					</#if>
				</#list>
				</form:form>
			</#list>
	
</@compress_single_line>