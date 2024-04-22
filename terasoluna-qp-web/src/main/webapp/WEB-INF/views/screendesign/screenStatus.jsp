<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.screendesign"></qp:message></span></li>
         <li><span>Screen item status</span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/screendesign/search"><qp:message code="sc.screendesign.0019"></qp:message> </a>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/display.js?r=1321"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/process.js?r=1331"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/constants.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/processComponent.js"></script>
		
		<script type="text/javascript">
		    var CL_FORMULA_BUILDER_DATATYPE = [];
		    <c:forEach items="${CL_BD_DATATYPE}" var="item">
		       CL_FORMULA_BUILDER_DATATYPE['${item.key}'] = '${item.value}';
		    </c:forEach>
		</script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/functions.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/formulaBuilder.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/screenStatus.js"></script>
		
		<script type="text/javascript">
			function setValue(obj) {
				var value = $(obj).find('option:selected').val();
				var val = $(obj).closest("td").find("input[name$='status']");
				val.attr("value",value);
			}
			function validateFormulaContent(obj) {
				var blean = false;
				var arrFormulaContent = $(obj).closest('div').prev('div').find("table[name='tbl_list_items']").find("input[name$='formulaDefinitionContent']");
				arrFormulaContent.each(function(){
					if(($(this).attr("value") == null || $(this).attr("value") == '') && $(this).attr("value") != 'undefined') {
						blean = true;
						$(this).closest("th").css("background-color","#ff8f8f");
					}
				});
				if(blean) {
					alert("Formula content is not allow empty");
					return false;
				}
			}
			
			/* function settingValueJSON(){
				var table = $("#tbl_list_domain_items");
				
			}
			
			
			$document.ready(settingValueJSON) */
			
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<form:form action="${pageContext.request.contextPath}/screendesign/screenStatus" method="POST" modelAttribute="screenDesignStatusForm">
					<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
					<form:hidden path="screenId" id="screenId"/>
					
		</form:form>
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
		
		<form:form action="${pageContext.request.contextPath}/screendesign/screenStatus" method="POST" modelAttribute="screenDesignStatusForm">
			<%-- <%@include file="settingDefaultValue.jsp" %>
			<form:hidden path="screenParameters" value =""/> --%>
				<form:hidden path="screenId" id="screenId"/>
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.screendesign.0272"/></span>
					</div>
					<div class="panel-body">
					<c:forEach items="${screenDesignStatusForm.listOfScreenItemStatusForm }" varStatus="formStatus">
						<c:if test="${not empty screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars }">
							<c:set var="size">${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars.size() }</c:set>
						</c:if>
						<c:if test="${empty screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars }">
							<c:set var="size">0</c:set>
						</c:if>
						
						<fieldset>
							<legend>
								${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].itemCode }
								<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].itemName" />
								<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].itemCode"/>
								<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].itemType"/>
								<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].screenFormId" />
								<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].itemId" />
							</legend>
							<div class="table-responsive">
								<c:set var="formCode" value="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].itemCode }"></c:set>
								<table id="tbl_list_domain_items" tableCode="${formCode}tbl_list_domain_items" name="tbl_list_items" class="table table-bordered qp-table-list" style="width: 980px" index="${formStatus.index}" screenId="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenFormId}">
									<colgroup>
										<col width="50px" />
										<col width="230px" />
										<col width="230px" />
										<col width="100px" />
										<c:if test="${size != 0}">
											<c:forEach begin="1" end="${size}" step="1">
												<col width="225px" />
											</c:forEach>
										</c:if>
										<c:if test="${size == 0}">
											<col width="225px" />
										</c:if>
									</colgroup>
									<thead>
										<tr>
											<th style="text-align:center"><qp:message code="sc.sys.0004"/></th>
											<th style="text-align:center"><qp:message code="sc.screendesign.0194" /></th>
											<th style="text-align:center"><qp:message code="sc.screendesign.0093" /></th>
											<th style="text-align:center"><qp:message code="sc.screendesign.0271" />
											<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].jsonOutputBean"/></th>
											<th style="text-align:center" index="0">Initial display
												<c:if test="${empty screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars}">
													<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" href="javascript:void(0)" title="Add" onclick="$.qp.addColumnJSByLink(this, 'tbl_list_domain_items');" ></a>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].formulaDefinitionContent" value="undefined"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].formulaName"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].screenFormId" value="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenFormId}"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].formularDefinitionDetails" />
												</c:if>
												<c:if test="${not empty screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars}">
														<c:if test="${size eq 1}">
															<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" href="javascript:void(0)" title="Add" onclick="$.qp.addColumnJSByLink(this, 'tbl_list_domain_items');" value="undefined"></a>
														</c:if>
														<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].formulaDefinitionContent" value="undefined"/>
														<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].formulaName"/>
														<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].screenFormId"/>
														<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].formulars[0].formularDefinitionDetails" />
												</c:if>
											</th>
											<c:if test="${not empty screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars}">
												<c:forEach var="formula" items="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].formulars}" varStatus="i" begin="1" step="1">
													<th style="text-align:right" index='${size}'><span class="forPrototype"></span>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[${i.index}].formulaDefinitionContent" value="${formula.formulaDefinitionContent}"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[${i.index}].formulaName" value="${formula.formulaName}"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[${i.index}].screenFormId" value="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenFormId}" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].formulars[${i.index}].formularDefinitionDetails" value='${formula.formularDefinitionDetails}' />
														<label style="float: left;margin-left: 5px; margin-top : 4px; display : none" class="qp-output-text">${formula.formulaDefinitionContent}</label>
														<a href="javascript:void(0)" onclick="openFomulaSetting(${screenDesignStatusForm.screenId}, this);" targetlabel="findTargetLabelOfFormula" targetvalue="findTargetValueOfFormula" sourceCallback="$.qp.formulabuilder.buildDataSourceForBusinessLogic">
														<span class="btn btn-default btn-xs glyphicon glyphicon-list-alt qp-button-action" title="Setting"></span></a>
														
														<c:if test="${i.index eq size-1}">
															<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" href="javascript:void(0)" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeColumnJSByLink(this,'${formCode}tbl_list_domain_items','${formCode}');"></a>
															<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" href="javascript:void(0)" title="Add" onclick="$.qp.addColumnJSByLink(this, 'tbl_list_domain_items');" value="undefined"></a>
														</c:if>
														<c:if test="${i.index lt size-1}">
															<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" href="javascript:void(0)" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeColumnJSByLink(this, '${formCode}tbl_list_domain_items','${formCode}');"></a>
														</c:if>
													</th>
												</c:forEach>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenItemStatusForms}" varStatus="status">
											<c:if test="${item.itemType eq 1 }">
												<tr type="${item.itemType }">
													<td>${status.count}
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemName" value="${item.itemName }" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemCode" value="${item.itemCode }" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemType" value="${item.itemType }"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenFormId" value="${item.screenFormId}"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemId" value="${item.itemId }" />
													</td>
													<td>${item.itemName}</td>
													<td>${item.itemCode}</td>
													<td><qp:message code="sc.screendesign.0471" /></td>
													<c:if test="${not empty item.formulars}">
														<c:forEach var="formula" items="${item.formulars}" varStatus="i" begin="0" end="${size - 1}" step="1">
															<c:if test="${i.index == 0}">
																<td style="text-align:right">
																</td>
															</c:if>
															<c:if test="${i.index != 0}">
																<td style="text-align:right">
																</td>
															</c:if>
														</c:forEach>
													</c:if>
													<c:if test="${empty item.formulars}">
														<td>
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].formulars[0].formulaDefinitionContent" value="undefined">
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].formulars[0].formularDefinitionDetails" value="">
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].formulars[0].screenFormId" value="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenFormId}"/>
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenFormId" value="${item.screenFormId}"/>
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].jsonOutputBean" value='${item.jsonOutputBean}'/>
														</td>
													</c:if>
												</tr>
											</c:if>
											<c:if test="${item.itemType eq 2 }">
												<tr type="${item.itemType }">
													<td>${status.count}</td>
													<td>${item.itemName}</td>
													<td>${item.itemCode}</td>
													<td><qp:message code="sc.screendesign.0472" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemName" value="${item.itemName }" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemCode" value="${item.itemCode }" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemType" id="firstTypeArea" value="${item.itemType }"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenFormId" value="${item.screenFormId}"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemId" value="${item.itemId }" />
													</td>
													<c:if test="${not empty item.screenItemStatuses}">
														<c:forEach var="screenItemStatus" items="${item.screenItemStatuses }" varStatus="i" begin="0" end="${size -1}" step="1">
															<td type="status">
																<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].itemId" />
																<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].screenFormId" />
																<form:hidden path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].formulaDefinitionId" />
																
																<div class="input-group" style="width: 100%;">
																	<c:set var="enable" value="display: none;"/>
																	<c:if test="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenItemStatusForms[status.index].screenItemStatuses[i.index].enabled }">
																		<c:set var="enable" value=""/>
																	</c:if>
																	<form:select style="float: left; width: 150px; ${enable }" class="form-control qp-input-select pull-left" onchange="setValue(this);areaStatusChange(this);" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].status">
																		<c:forEach var="init" items="${CL_INITIAL_DISPLAY}">
																			<form:option value="${init.key}"><qp:message code="${CL_INITIAL_DISPLAY.get(init.key)}" /></form:option>
																		</c:forEach>
																	</form:select>
																	<div class="checkbox-default  input-group-addon checkbox-slider--a checkbox-slider--a-rounded" style="line-height: 150%;  float: right; width: 60px;">
																		<label><form:checkbox onclick="enableCheck(this)" value="true" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].enabled"/><span></span></label>
																	</div>
																</div>
															</td>
														</c:forEach>
													</c:if>
													<c:if test="${empty item.screenItemStatuses}">
														<td type="status">
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[0].screenFormId" value="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenFormId}"/>
															<div class="input-group" style="width: 100%;">
																<c:set var="enable" value="display: none;"/>
																<c:if test="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenItemStatusForms[status.index].screenItemStatuses[i.index].enabled }">
																	<c:set var="enable" value=""/>
																</c:if>
																<form:select style="float: left; width: 150px; ${enable }" class="form-control qp-input-select pull-left" onchange="setValue(this);areaStatusChange(this);" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[0].status">
																	<c:forEach var="init" items="${CL_INITIAL_DISPLAY}">
																		<c:if test="${init.key eq 1}">
																			<form:option value="${init.key}" selected="true"><qp:message code="${CL_INITIAL_DISPLAY.get(init.key)}"  /></form:option>
																		</c:if>
																		<c:if test="${init.key ne 1}">
																			<form:option value="${init.key}"><qp:message code="${CL_INITIAL_DISPLAY.get(init.key)}"  /></form:option>
																		</c:if>
																	</c:forEach>
																</form:select>
																<div class="checkbox-default  input-group-addon checkbox-slider--a checkbox-slider--a-rounded" style="line-height: 150%; float: right;  width: 60px;">
																		<label><form:checkbox onclick="enableCheck(this)" value="true" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[0].enabled"/><span></span></label>
																</div>
															</div>
														</td>
													</c:if>
												</tr>
											</c:if>
											<c:if test="${item.itemType eq 3 }">
												<tr type="${item.itemType }" area-id="${item.screenAreaId }">
													<td>${status.count}</td>
													<td>${item.itemName}</td>
													<td>${item.itemCode}</td>
													<td><qp:message code="sc.screendesign.0473" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemName" value="${item.itemName }" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemCode" value="${item.itemCode }" />
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemType" id="firstTypeItem" value="${item.itemType }"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenFormId" value="${item.screenFormId}"/>
														<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].itemId" value="${item.itemId }" />
													</td>
													<c:if test="${not empty item.screenItemStatuses }">
														<c:forEach var="screenItemStatus" items="${item.screenItemStatuses}" varStatus="i" begin="0" step="1">
															<td type="status">
																<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].itemId" value="${screenItemStatus.itemId}"/>
																<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].screenFormId" value="${screenItemStatus.screenFormId}"/>
																<div class="input-group" style="width:100%;">
																	<c:set var="enable" value="display: none;"/>
																	<c:if test="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenItemStatusForms[status.index].screenItemStatuses[i.index].enabled }">
																		<c:set var="enable" value=""/>
																	</c:if>
																	<form:select style="float: left; width: 150px; ${enable }" class="form-control qp-input-select pull-left" onchange="setValue(this)" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].status">
																		<c:forEach var="init" items="${CL_INITIAL_DISPLAY_ITEM}">
																			<form:option value="${init.key}"><qp:message code="${CL_INITIAL_DISPLAY_ITEM.get(init.key)}" /></form:option>
																		</c:forEach>
																	</form:select>
																	<div class="checkbox-default  input-group-addon checkbox-slider--a checkbox-slider--a-rounded" style="line-height: 150%; float: right; width: 60px;">
																		<label><form:checkbox onclick="enableCheck(this)" value="true" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[${i.index}].enabled" /><span></span></label>
																	</div>
																</div>
															</td>
														</c:forEach>
													</c:if>
													<c:if test="${empty item.screenItemStatuses}">
														<td type="status">
															<input type="hidden" name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[0].screenFormId" value="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenFormId}"/>
															<div class="input-group" style="width: 100%;">
																<c:set var="enable" value="display: none;"/>
																<c:if test="${screenDesignStatusForm.listOfScreenItemStatusForm[formStatus.index].screenItemStatusForms[status.index].screenItemStatuses[i.index].enabled }">
																	<c:set var="enable" value=""/>
																</c:if>
																<form:select style="float: left; width: 150px; ${enable }" class="form-control qp-input-select pull-left" onchange="setValue(this)" path="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[0].status">
																	<c:forEach var="init" items="${CL_INITIAL_DISPLAY_ITEM}">
																		<c:if test="${init.key eq 1}">
																			<form:option value="${init.key}" selected="true"><qp:message code="${CL_INITIAL_DISPLAY_ITEM.get(init.key)}"  /></form:option>
																		</c:if>
																		<c:if test="${init.key ne 1}">
																			<form:option value="${init.key}"><qp:message code="${CL_INITIAL_DISPLAY_ITEM.get(init.key)}"  /></form:option>
																		</c:if>
																	</c:forEach>
																</form:select>
																<div
																	class="checkbox-default  input-group-addon checkbox-slider--a checkbox-slider--a-rounded"
																	style="line-height: 150%;  float: right; width: 60px;">
																	<label>
																		<input id="screenItemStatusForms${status.index}.screenItemStatuses0.enabled0"
																			name="listOfScreenItemStatusForm[${formStatus.index}].screenItemStatusForms[${status.index}].screenItemStatuses[0].enabled"
																			type="checkbox" onclick="enableCheck(this)" value="true">
																		<input
																			type="hidden"
																			name="listOfScreenItemStatusForm[${formStatus.index}]._screenItemStatusForms[${status.index}].screenItemStatuses[0].enabled"
																			value="off">
																			<span></span>
																	</label>
																</div>
															</div>
														</td>
													</c:if>
												</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</fieldset>
					</c:forEach>
				</div>
			</div>
		<div class="qp-div-action">
			<qp:authorization permission="moduleRegister">
				<button type="submit" class="btn qp-button qp-dialog-confirm" onClick="validateFormulaContent"><qp:message code="sc.sys.0031" /></button>
			</qp:authorization>
		</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>


<jsp:include page="components/functionlist.jsp"></jsp:include>
<jsp:include page="components/formulaBuilder.jsp"></jsp:include>
<jsp:include page="components/functionSetting.jsp"></jsp:include>
