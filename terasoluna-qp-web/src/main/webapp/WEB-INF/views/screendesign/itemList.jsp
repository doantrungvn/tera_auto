<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.screendesign"></qp:message></span></li>
         <li><span><qp:message code="sc.screendesign.0248"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/screendesign/search"><qp:message code="sc.screendesign.0019"></qp:message> </a>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/display.js?r=1321"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/process.js?r=1331"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=tabledesign_screendesign"></script>
		<script>
			$(document).ready(function() {
				$('#tbl_list_domain_items').find("tbody tr").each(function() {
					var messageLevel = $(this).find("input[name=messageLevel]").val();
					switch (messageLevel) {
					case "0":
						$(this).find("#messageLevelSpan").text("(P)");
						break;
					case "1":
						$(this).find("#messageLevelSpan").text("(M)");
						break;
					case "2":
						$(this).find("#messageLevelSpan").text("(S)");
						break;
					case "3":
						$(this).find("#messageLevelSpan").text("(SA)");
						break;
					case "4":
						$(this).find("#messageLevelSpan").text("(SI)");
						break;
					case "5":
						$(this).find("#messageLevelSpan").text("(B)");
						break;
					case "6":
						$(this).find("#messageLevelSpan").text("(MD)");
						break;
					case "7":
						$(this).find("#messageLevelSpan").text("(DI)");
						break;
					}
				});
			});
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/screendesign/modifysettingitemlist" method="POST" modelAttribute="screenDesignItemForm">
					<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
		</form:form>
		<%@include file="dialog/itemDefaultSetting.jsp" %>
		<%@include file="dialog/itemDefaultSettingDate.jsp" %>
		<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.screendesign.0273" /></span>
				</div>
				
					<div class="panel-body">
						<table class="table table-bordered qp-table-form" id="tableDialogFormAutocomplete">
							<colgroup>
								<col width="20%" /><col width="30%" /><col width="20%" /><col width="30%" />
							</colgroup>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0005"></qp:message></th>
								<td>${screenDesign.messageDesign.messageString }</td>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0007"></qp:message></th>
								<td>${screenDesign.screenCode }</td>
							</tr>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0006"></qp:message> </th>
								<td>${screenDesign.moduleName}</td>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0031"></qp:message> </th>
								<td>
									<c:choose>
										<c:when test="${screenDesign.templateType eq '1' }"><qp:message code="sc.screendesign.0142"></qp:message></c:when>
										<c:when test="${screenDesign.templateType eq '2' }"><qp:message code="sc.screendesign.0143"></qp:message></c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0009"></qp:message></th>
								<td>
									<c:choose>
										<c:when test="${screenDesign.screenPatternType == 1 }"><qp:message code="sc.screendesign.0019"></qp:message></c:when>
										<c:when test="${screenDesign.screenPatternType == 2 }"><qp:message code="sc.screendesign.0021"></qp:message></c:when>
										<c:when test="${screenDesign.screenPatternType == 3 }"><qp:message code="sc.screendesign.0030"></qp:message></c:when>
										<c:otherwise><qp:message code="sc.screendesign.0029"></qp:message></c:otherwise>
									</c:choose>
								</td>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0057"></qp:message></th>
								<td>${screenDesign.remark }</td>
							</tr>
													<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0250"/></th>
							<td>
								<c:choose>
									<c:when test="${screenDesign.designMode == 1 }">
										<qp:message code="sc.screendesign.0306"/>
									</c:when>
									<c:when test="${screenDesign.designMode == 2 }">
										<qp:message code="sc.screendesign.0316"/>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0055"/></th>
							<td>
								<c:choose>
									<c:when test="${screenDesign.designStatus == 1 }">
										<qp:formatText value="Under design"></qp:formatText>
									</c:when>
									<c:when test="${screenDesign.designStatus == 2 }">
										<qp:formatText value="Fixed design"></qp:formatText>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</td>
						</tr>
						</table>
					</div>
			</div>
			<form:form action="${pageContext.request.contextPath}/screendesign/modifysettingitemlist" method="POST" modelAttribute="screenDesignItemForm">
			<%@include file="settingDefaultValue.jsp" %>
			<form:hidden path="screenParameters" value =""/>
				<script>
					function showSetting(type) {
						
					
						if (type == 'settingSearch') {
							$('table[name="settingSearch"]').show();
							$('table[name="settingItem"]').hide();
						} else {
							$('table[name="settingSearch"]').hide();
							$('table[name="settingItem"]').show();
						}

					} 
					$(document).ready(function() {
						$('table[name="settingSearch"]').hide();
						$('table[name="settingItem"]').show();
					});
				</script>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0272"/></span>
						<!-- &nbsp;&nbsp;<a href="javascript:;" class="pull-right" style="color: white;" onclick="showSetting('settingSearch')">Setting search pattem</a>
						<div class="pull-right">&nbsp;&nbsp;</div>
						<a href="javascript:;" class="pull-right" onclick="showSetting('settingItem')" style="color: white;">Setting item</a> -->			
					</div>
					<div class="panel-body">
					<table id="tbl_list_domain_items" name="settingItem" class="table table-bordered qp-table-list">
						<colgroup>
							<col/>
							<col width="17%" />
							<col width="13%" />
							<col width="11%" />
							<col  width="12%" />
							<col width="19%" />
							<col name="settingItem" width="8%" />
							<col name="settingItem" width="8%" />
							<col name="settingItem" width="8%" />
							<col name="settingItem" width="6%"/>
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"/></th>
								<th><qp:message code="sc.screendesign.0194" /></th>
								<th><qp:message code="sc.screendesign.0093" /></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0271"/></th>
								<th  name="settingItem"><qp:message code="sc.tabledesign.0023"/></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0270"/></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0269"/></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0100"/></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0191"/></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0268"/></th>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${not empty screenDesignItemForm.screenItemForms }">
								<c:forEach items="${screenDesignItemForm.screenItemForms }" var="items" varStatus="rowStatus">
									<tr>
									<form:hidden path="screenItemForms[${rowStatus.index}].screenItemId" />
									<form:hidden path="screenItemForms[${rowStatus.index}].screenId" />
									<form:hidden path="moduleId" value ="${screenDesign.moduleId}" />
									<form:hidden path="screenId" value ="${screenDesign.screenId}" />
									<form:hidden path="screenItemForms[${rowStatus.index}].updatedDate"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].screenAreaId"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].nameLogicalDataType" value="${screenDesignItemForm.screenItemForms[rowStatus.index].nameLogicalDataType}"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].screenArea.messageDesign.messageString" value="${screenDesignItemForm.screenItemForms[rowStatus.index].screenArea.messageDesign.messageString}"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].logicalDataType" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType}"/>
									<c:set var="datatype" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType }"></c:set>	
									<td align="center">
										${rowStatus.count}
										<input type="hidden" name="rowCount" value="${rowStatus.count}">
									</td>
										
										<td>
											<div class="td-with-setting">
												<qp:autocomplete optionLabel="optionLabel" onChangeEvent="changeItemName"  optionValue="optionValue" mustMatch="false"  name="screenItemForms[${rowStatus.index}].messageCode" 
														displayValue="${screenDesignItemForm.screenItemForms[rowStatus.index].messageCodeAutocomplete }" value="${screenDesignItemForm.screenItemForms[rowStatus.index].messageCode }"
															arg01="${screenDesign.moduleId}" selectSqlId="findSystemMessage"></qp:autocomplete>
												
												<form:hidden path="screenItemForms[${rowStatus.index}].hasSelectItemName" value ="true"/>
											</div>
											<span id="messageLevelSpan"></span>
											<input type="hidden" value="${screenDesignItemForm.screenItemForms[rowStatus.index].messageDesign.messageLevel }" name="messageLevel">
										</td>
										<td>
											<c:if test="${datatype != null && datatype != 20}">
												<form:input path="screenItemForms[${rowStatus.index}].itemCode"  class="form-control qp-input-text"/>
											</c:if>
											<form:hidden path="screenItemForms[${rowStatus.index}].itemCodeH" value="${screenDesignItemForm.screenItemForms[rowStatus.index].itemCode }"/>
										</td>
									
										
										<td name="settingItem">
											<qp:message code="${CL_QP_ITEMTYPE.get(screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType.toString()) }"></qp:message>
										</td>
										
										<td name="settingItem" class="qp-output-text word-wrap">
	 	                                	<form:label path="screenItemForms[${rowStatus.index}].screenArea.messageDesign.messageString" cssStyle="font:12px Arial, Helvetica, sans-serif;" title="${screenDesignItemForm.screenItemForms[rowStatus.index].screenForms.formCode }">
	 	                                		${screenDesignItemForm.screenItemForms[rowStatus.index].screenArea.messageDesign.messageString }
	 	                                	</form:label>
		                           		</td>
		                           		
										<td name="settingItem">
											<c:set var="logical" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType}"></c:set>
											<c:set var="physical" value="${screenDesignItemForm.screenItemForms[rowStatus.index].physicalDataType}"></c:set>
											<c:set var="dataSourceType" value="${screenDesignItemForm.screenItemForms[rowStatus.index].dataSourceType}"></c:set>
											<c:if test="${datatype != null && datatype != 20 && datatype != 21 }">
												<c:choose>
													<c:when test="${logical eq '4'}">
														<c:if test="${screenDesign.screenPatternType == 1 }">
															<div class="td-with-setting">
																<c:if test="${items.defaultValue eq '1'}">
																	<qp:message code="sc.screendesign.0256"/>
																</c:if>
																<c:if test="${items.defaultValue eq '2'}">
																	<qp:message code="sc.screendesign.0257"/>
																</c:if>
																<c:if test="${items.defaultValue eq '3'}">
																	<qp:message code="sc.screendesign.0258"/>
																</c:if>
															</div>
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openSettingDefaultValue(this, '${logical}');" title="Setting" style="float:right;"> </a>
															<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
														</c:if>
														<c:if test="${screenDesign.screenPatternType != 1 }">
															<div class="td-with-setting" >
																<c:if test="${items.defaultValue eq '5' }">
																	<qp:formatText value="now()" />
																</c:if>
																<c:if test="${items.defaultValue ne '5' }">
																	<qp:formatText value="${items.defaultValue }" />
																</c:if>
															</div>
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openDialogItemDefaultSettingDate(this, '${logical}');" title="Setting" style="float:right;"> </a>
															<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
														</c:if>
													</c:when>
													<c:when test="${logical eq '14'}">
														<c:if test="${screenDesign.screenPatternType == 1 }">
															<div class="td-with-setting">
																<c:if test="${items.defaultValue eq '1'}">
																	<qp:message code="sc.screendesign.0256"/>
																</c:if>
																<c:if test="${items.defaultValue eq '2'}">
																	<qp:message code="sc.screendesign.0257"/>
																</c:if>
																<c:if test="${items.defaultValue eq '3'}">
																	<qp:message code="sc.screendesign.0258"/>
																</c:if>
															</div>
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openSettingDefaultValue(this, '${logical}');" title="Setting" style="float:right;"> </a>
															<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
														</c:if>
														<c:if test="${screenDesign.screenPatternType != 1 }">
															<div class="td-with-setting" >
																<c:if test="${items.defaultValue eq '5' }">
																	<qp:formatText value="now()" />
																</c:if>
																<c:if test="${items.defaultValue ne '5' }">
																	<qp:formatText value="${items.defaultValue }" />
																</c:if>
															</div>
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openDialogItemDefaultSettingDate(this, '${logical}');" title="Setting" style="float:right;"> </a>
															<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
														</c:if>
													</c:when>												
													<c:when test="${logical eq '9'}">
														<c:if test="${screenDesign.screenPatternType == 1 }">
															<div class="td-with-setting">
																<c:if test="${items.defaultValue eq '4'}">
																	<qp:message code="sc.screendesign.0259"/>
																</c:if>
															</div>
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openSettingDefaultValue(this, '${logical}');" title="Setting" style="float:right;"> </a>
															<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
														</c:if>
														<c:if test="${screenDesign.screenPatternType != 1 }">
															<div class="td-with-setting" >
																<c:if test="${items.defaultValue eq '5' }">
																	<qp:formatText value="now()" />
																</c:if>
																<c:if test="${items.defaultValue ne '5' }">
																	<qp:formatText value="${items.defaultValue }" />
																</c:if>
															</div>
															<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openDialogItemDefaultSettingDate(this, '${logical}');" title="Setting" style="float:right;"> </a>
															<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
														</c:if>
													</c:when>
													<c:when test="${logical eq '8' || logical eq '3'}">
														<c:if test="${screenDesign.screenPatternType == 1 }">
															<form:input path="screenItemForms[${rowStatus.index}].defaultValue" cssClass="form-control qp-input-from-float pull-left" />
															<div class="qp-separator-from-to">~</div>
															<form:input path="screenItemForms[${rowStatus.index}].defaultValueTo" cssClass="form-control qp-input-to-float pull-right" />
														</c:if>
														<c:if test="${screenDesign.screenPatternType != 1 }">
															<form:input path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-float"/>
														</c:if>
													</c:when>
													<c:when test="${logical eq '2'}">
														<c:if test="${screenDesign.screenPatternType == 1 }">
															<form:input path="screenItemForms[${rowStatus.index}].defaultValue" cssClass="form-control qp-input-from-integer pull-left" />
															<div class="qp-separator-from-to">~</div>
															<form:input path="screenItemForms[${rowStatus.index}].defaultValueTo" cssClass="form-control qp-input-to-integer pull-right" />
														</c:if>
														<c:if test="${screenDesign.screenPatternType != 1 }">
															<form:input path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-integer"/>
														</c:if>
													</c:when>
													<c:when test="${logical eq '1' || logical eq '10' || logical eq '15'   
														|| logical eq '16' || logical eq '17' || logical eq '18' || logical eq '21'}">   
														<form:input path="screenItemForms[${rowStatus.index}].defaultValue" value ="${items.defaultValue }" class="form-control qp-input-text"/>
													</c:when>
												</c:choose>
											</c:if>
											<c:if test="${logical eq '6'}">
												<c:if test="${physical ne '8'}">
													<div class="td-with-setting" >
														<qp:formatText value="${screenDesignItemForm.screenItemForms[rowStatus.index].defaultLabel}" />
													</div>
													<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openDialogItemDefaultSetting(this);" title="Setting" style="float:right;"> </a>
													<form:hidden path="screenItemForms[${rowStatus.index}].codelistItemInfor" value="${screenDesignItemForm.screenItemForms[rowStatus.index].codelistItemInfor}"/>
													<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value="${screenDesignItemForm.screenItemForms[rowStatus.index].defaultValue}"/>
													<form:hidden path="screenItemForms[${rowStatus.index}].defaultLabel" value="${screenDesignItemForm.screenItemForms[rowStatus.index].defaultLabel}"/>
													<c:set var="codelistItemInfor" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType}"></c:set>
												</c:if>
											</c:if>
											<c:if test="${logical eq '5' || logical eq '7'}">
												<c:if test="${dataSourceType eq '2'}">
													<div class="td-with-setting" >
														<qp:formatText value="${screenDesignItemForm.screenItemForms[rowStatus.index].defaultLabel}" />
													</div>
													<a class="btn btn-default btn-xs glyphicon glyphicon-cog qp-button-action" onclick="openDialogItemDefaultSetting(this);" title="Setting" style="float:right;"> </a>
													<form:hidden path="screenItemForms[${rowStatus.index}].codelistItemInfor" value="${screenDesignItemForm.screenItemForms[rowStatus.index].codelistItemInfor}"/>
													<form:hidden path="screenItemForms[${rowStatus.index}].defaultValue" value="${screenDesignItemForm.screenItemForms[rowStatus.index].defaultValue}"/>
													<form:hidden path="screenItemForms[${rowStatus.index}].defaultLabel" value="${screenDesignItemForm.screenItemForms[rowStatus.index].defaultLabel}"/>
													<c:set var="codelistItemInfor" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType}"></c:set>
												</c:if>
											</c:if>
		                           		</td>
										<td name="settingItem">
											<c:if test="${datatype != null && (datatype == 1 || datatype == 18 || datatype == 15 || datatype == 16 || datatype == 10) }">
	 	                                		<form:input path="screenItemForms[${rowStatus.index}].screenItemValidation.maxlength" class="form-control qp-input-integer" />
	 	                                	</c:if>
		                           		</td>
		                           		<td name="settingItem" align="center">
			                           		<c:if test="${datatype != null && datatype != 20 && datatype != 21 }">
			                           			<c:set var="mandatoryFlg" value="${screenDesignItemForm.screenItemForms[rowStatus.index].screenItemValidation.mandatoryFlg}"></c:set>
	<%-- 			                           		<c:if test="${mandatoryFlg != null }"> --%>
					                           		<c:choose>
														<c:when test="${mandatoryFlg eq '1'}">
															<input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="screenItemForms[${rowStatus.index}].hasMandatoryFlgCheck" checked />
														</c:when>
														<c:when test="${mandatoryFlg != '1'}">
															<input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="screenItemForms[${rowStatus.index}].hasMandatoryFlgCheck" />
														</c:when>
													</c:choose>
	<%-- 											</c:if> --%>
											</c:if>
										</td>
										<td name="settingItem">
											<c:if test="${datatype != null && datatype != 20 && datatype != 21 }">
												<form:input path="screenItemForms[${rowStatus.index}].tabIndex" class="form-control qp-input-integer" />
											</c:if>
		                           		</td>
		                           		<td name="settingItem" align="center">
											<form:checkbox id="idRemove${rowStatus.count}" path="screenItemForms[${rowStatus.index}].delete" class="qp-input-checkbox qp-input-checkbox-margin" onclick="highLightRow(this);" value="1" />
										</td>
									</tr>
								</c:forEach>							
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="10"><qp:message code="sc.screendesign.0325"></qp:message> </td>
								</tr>
							</c:otherwise>
						</c:choose>
							
							</table>	
						<%-- <table name="settingSearch" style="width: 100%;"  class="table table-bordered qp-table-list">
						<colgroup>
							<col/>
							<col width="13%" />
							<col width="11%" />
							<col width="11%" />
							<col  width="12%" />
							<col width="10%" />
							<col width="45%" />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"/></th>
								<th><qp:message code="sc.screendesign.0194" /></th>
								<th><qp:message code="sc.screendesign.0093" /></th>
								<th  name="settingItem"><qp:message code="sc.screendesign.0271"/></th>
								<th  name="settingItem"><qp:message code="sc.tabledesign.0023"/></th>
								<th name="settingSearch">Case sensitivity</th>
								<th name="settingSearch">Condition</th>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${not empty screenDesignItemForm.screenItemForms }">
								<c:forEach items="${screenDesignItemForm.screenItemForms }" var="items" varStatus="rowStatus">
									<tr>
									<form:hidden path="screenItemForms[${rowStatus.index}].screenItemId" />
									<form:hidden path="screenItemForms[${rowStatus.index}].screenId" />
									<form:hidden path="moduleId" value ="${screenDesign.moduleId}" />
									<form:hidden path="screenId" value ="${screenDesign.screenId}" />
									<form:hidden path="screenItemForms[${rowStatus.index}].updatedDate"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].screenAreaId"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].nameLogicalDataType" value="${screenDesignItemForm.screenItemForms[rowStatus.index].nameLogicalDataType}"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].screenArea.messageDesign.messageString" value="${screenDesignItemForm.screenItemForms[rowStatus.index].screenArea.messageDesign.messageString}"/>
									<form:hidden path="screenItemForms[${rowStatus.index}].logicalDataType" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType}"/>
										
									<td align="center">${rowStatus.count}</td>
										<td>
											<qp:autocomplete optionLabel="optionLabel" onChangeEvent="changeItemName"  optionValue="optionValue" mustMatch="false"  name="screenItemForms[${rowStatus.index}].messageCode" 
													displayValue="${screenDesignItemForm.screenItemForms[rowStatus.index].messageCodeAutocomplete }" value="${screenDesignItemForm.screenItemForms[rowStatus.index].messageCode }"
														arg01="${screenDesign.moduleId}" selectSqlId="findSystemMessage"></qp:autocomplete>
											
											<form:hidden path="screenItemForms[${rowStatus.index}].hasSelectItemName" value ="true"/>
										</td>
										<td>
											<form:input path="screenItemForms[${rowStatus.index}].itemCode"  class="form-control qp-input-text"/>
										</td>
										<td name="settingItem">
											<qp:message code="${CL_QP_ITEMTYPE.get(screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType.toString()) }"></qp:message>
										</td>
										
										<td name="settingItem" class="qp-output-text word-wrap">
	 	                                	<form:label path="screenItemForms[${rowStatus.index}].screenArea.messageDesign.messageString" cssStyle="font:12px Arial, Helvetica, sans-serif;" title="${screenDesignItemForm.screenItemForms[rowStatus.index].screenForms.formCode }">
	 	                                		${screenDesignItemForm.screenItemForms[rowStatus.index].screenArea.messageDesign.messageString }
	 	                                	</form:label>
		                           		</td>
										<td name="settingSearch" align="center">
											<c:set var="logical" value="${screenDesignItemForm.screenItemForms[rowStatus.index].logicalDataType}"></c:set>
											<c:if test="${logical ==  10 || logical == 1 || logical == 18 || logical == 15 || logical == 20}">
												<input type="checkbox" />
											</c:if>
										</td>
										<td name="settingSearch" valign="top" style="vertical-align: top;">
										<c:if test="${logical ==  10 || logical == 1 || logical == 18 || logical == 15 || logical == 20}">
											<select class="form-control">
												<option>Like condition</option>
												<option>Starting with condition</option>
												<option>Ending with condition</option>
												<option>Containing condition</option>
											</select>
											</c:if>
										</td>
									</tr>
								</c:forEach>							
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="10"><qp:message code="sc.screendesign.0325"></qp:message> </td>
								</tr>
							</c:otherwise>
						</c:choose>
							
							</table> --%>
						</div>
					</div>
					<c:if test="${screenDesign.designStatus == 1 && not empty screenDesignItemForm.screenItemForms }">
						<div class="qp-div-action">
			                <qp:authorization permission="screendesignRegister">
			                     <button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
			                </qp:authorization>
			            </div>
		            </c:if>
		</form:form>
	
	</tiles:putAttribute>

</tiles:insertDefinition>