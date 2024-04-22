<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">
			<qp:message code="sc.screendesign.0249"/>
		</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.screendesign"></qp:message></span></li>
         <li><span><qp:message code="sc.screendesign.0021"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<div style="float: left;">
			<qp:authorization permission="screendesignDesign">
				<a href="javascript:" onclick="return previewDialog(this)" ><qp:message code="sc.screendesign.0244"/></a>
			</qp:authorization>
		</div>
<%-- 		<qp:authorization permission="screendesignTransition">
			<a href="${pageContext.request.contextPath}/screendesign/transition"><qp:message code="tqp.screentransition" /></a>
		</qp:authorization> --%>
		&nbsp;&nbsp;&nbsp;
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/screendesign/search"><qp:message code="sc.screendesign.0019"></qp:message> </a>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">	
		<script type="text/javascript">
			var CL_QP_VALIDATION = {}//CL_QP_Validation
			<c:forEach items="${CL_QP_VALIDATION}" var="item">
				CL_QP_VALIDATION['${item.validationRuleCode}'] = '<qp:message code="${item.validationRuleName}" />';
			</c:forEach>
			
			var CL_TABLE_SETTING = {}//CL_QP_Validation
			<c:forEach items="${CL_TABLE_SETTING}" var="item">
				CL_TABLE_SETTING['${item.key}'] = '${item.value}';
			</c:forEach>
			
			var CL_DATATYPE_PARAMETERS = {};
			<c:forEach items="${CL_BD_DATATYPE_PARAMETER}" var="item">
				CL_DATATYPE_PARAMETERS['${item.key}'] = '${item.value}';
			</c:forEach>
			
			var CL_BD_DATATYPE = {};
			<c:forEach items="${CL_BD_DATATYPE}" var="item">
			CL_BD_DATATYPE['${item.key}'] = '${item.value}';
			</c:forEach>
			
			var CL_PRIMITIVE_DATATYPE = {};
			<c:forEach items="${CL_PRIMITIVE_DATATYPE}" var="item" varStatus="loopCounter">
				CL_PRIMITIVE_DATATYPE['${item.key}'] = '${item.value}';
				<c:if test="${loopCounter.last}">CL_PRIMITIVE_DATATYPE['total'] = '${loopCounter.count}'</c:if>
			</c:forEach>
			
			function changeScreenId(obj) {
				var screenId = $(obj).prev("input").val();
			}
			
		</script>
		<jsp:useBean id="random" class="java.util.Random" scope="application" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=screendesign_blogiccomponent_databasedesign_messagedesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/color.js?r=12e31"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/jquery.onscreen.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/prototype/common.js?r=131e21"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/project/javascript/holder.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/jquery-scrolltofixed-min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/prototype/init.js?r=31"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/prototype/display.js?r=13e21"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/prototype/display-section.js?r=1e231"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/screendesign/javascript/prototype/process.js?r=13e31"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/validation.js"></script>
		<link href="${pageContext.request.contextPath}/resources/app/screendesign/css/style.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/app/screendesign/css/icon.css" type="text/css" rel="stylesheet" />		
		<link href="${pageContext.request.contextPath}/resources/app/screendesign/css/bootstrap.vertical-tabs.min.css" type="text/css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/colorpicker/js/bootstrap-colorpicker.min.js"></script>
		<style>
			.form-area-content {
				/* ${projectStyle['screenStyle']} */
			}
			
			.form-area-content .panel-default>.panel-heading{
				${projectStyle['panelHeader']}
			}
			
			.form-area-content .panel-body{
				${projectStyle['panelBody']}
			}
			
			.form-area-content .qp-table-list{
				${projectStyle['panelListTable']}
			}
			
			.form-area-content .qp-table-list thead tr th{
				${projectStyle['panelListTh']}
			}
			
			.form-area-content .qp-table-list-none-action{
				${projectStyle['panelListTable']}
			}
			
			.form-area-content .qp-table-list-none-action thead tr th{
				${projectStyle['panelListTh']}
			}
			
			.form-area-content .result-text{
				${projectStyle['panelListTdText']}
			}
			
			.form-area-content .result-numeric{
				${projectStyle['panelListTdNumeric']}
			}
			
			.form-area-content .result-date{
				${projectStyle['panelListTdDate']}
			}
			
			.form-area-content .result-date-time{
				${projectStyle['panelListTdDateTime']}
			}
			
			.form-area-content .result-no-number{
				${projectStyle['panelListTdNoNumber']}
			}
			
			.form-area-content .result-action-column{
				${projectStyle['panelListTdActionColumn']}
			}
			
			.form-area-content .qp-table-form{
				${projectStyle['panelTableForm']}
			}
			
			.form-area-content .qp-table-form tr th label{
				${projectStyle['panelTableFormTh']}
			}
			.form-area-content .qp-table-form tr th{
				${projectStyle['panelTableFormTh']}
			}
			
			.form-area-content .qp-table-form tr td{
				${projectStyle['panelTableFormTd']}
			}
			
			.form-area-content .qp-button-type-design {
				${projectStyle['commonButtonBgColor']}
				${projectStyle['commonButtonTextColor']}
			}
			
			.form-area-content .qp-button-type-design:ACTIVE {
				${projectStyle['commonButtonBgActiveColor']}
			}
			
			.form-area-content .qp-button-type-warning-design {
				${projectStyle['commonButtonDeleteBgColor']}
				${projectStyle['commonButtonDeleteTextColor']}
			}
			
			.form-area-content .qp-button-type-warning-design:ACTIVE {
				${projectStyle['commonButtonDeleteBgActiveColor']}
			}
			
			.form-area-content .qp-button-type-client-design {
				${projectStyle['clientButtonDeleteBgColor']}
				${projectStyle['clientButtonDeleteTextColor']}
			}
			
			.form-area-content .qp-button-type-client-design:ACTIVE {
				${projectStyle['clientButtonDeleteBgActiveColor']}
			}
			
			/* CSS group Elements */
		    .strike {
		        display: block;
		        text-align: center;
		        overflow: hidden;
		        white-space: nowrap; 
		        padding-top: 5px;
		    }
		
		    .strike > span {
		        position: relative;
		        display: inline-block;
		        font-weight: bold;
		    }
			
		    .strike > span:before,
		    .strike > span:after {
		        content: "";
		        position: absolute;
		        top: 50%;
		        width: 9999px;
		        /* Here is the modification */
		        height: 5px; /* space between lines */
		        margin-top: -2px; /* adjust vertical align */
		        border-top: 1px solid red;
		        border-bottom: 1px solid red;
		       
		    }
		
		    .strike > span:before {
		        right: 100%;
		        margin-right: 15px;
		    }
		
		    .strike > span:after {
		        left: 100%;
		        margin-left: 15px;
		    }
		    
		    .qp-item-custom {
		    	border-style:dotted;
		    	border-color: #ff9999
		    }
		    .qp-root .qp-table-form tr td div{ 
				border-spacing : 0px;
			}
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<script type="text/javascript">
			var TABLE_SETTING = {}//CL_QP_Validation
			<c:forEach items="${CL_TABLE_SETTING}" var="item">
				TABLE_SETTING['${item.key}'] = '${item.value}';
			</c:forEach>
			
		</script>
				
		<form:form method="post" name="screenDesignForm" action="${pageContext.request.contextPath}/screendesign/prototype" modelAttribute="screenDesignForm">	
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<input type="hidden" name="moduleId" value="${screenDesignForm.moduleId}" />
			<input type="hidden" name="projectId" value="${screenDesignForm.projectId}" />
			<input type="hidden" name="updatedDate" value="${screenDesignForm.updatedDate}" />
			<input type="hidden" name="designMode" value="${screenDesignForm.designMode }" />
			<input type="hidden" name="designStatus" value="${screenDesignForm.designStatus }" />
			<form:hidden path="messageDesign.messageCode"/>
			<input type="hidden" name="screenPatternType" value="${screenDesignForm.screenPatternType}" />
			<form:hidden path="screenId"/>
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading" ><span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.screendesign.0252"/></span></div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form"
						id="tableDialogFormAutocomplete">
						<colgroup>
							<col width="20%" /><col width="30%" /><col width="20%" /><col width="30%" />
						</colgroup>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0005"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input maxlength="255" cssClass="form-control qp-input-text qp-convention-name" path="screenName"/><form:hidden path="messageDesign.messageDesignId"/></td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0007"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
							<td><form:input maxlength="200" cssClass="form-control qp-input-text qp-convention-code" path="screenCode"/></td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0006"></qp:message> </th>
							<td>${screenDesignForm.moduleName}<form:hidden path="moduleName"/> </td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.functiondesign.0002"></qp:message> </th>
							<td>
								<qp:autocomplete optionValue="optionValue"
									selectSqlId="getAllFunctionDesignByTypeForAutocomplete"
									name="functionDesignId" value="${screenDesignForm.functionDesignId }"
									displayValue="${screenDesignForm.functionDesignName }"
									arg02="${screenDesignForm.moduleId}" 
									arg03="${screenDesignForm.functionDesignType}"
									optionLabel="optionLabel"
									maxlength="200">
							</qp:autocomplete>
							<form:hidden path="functionDesignName" value="${screenDesignForm.functionDesignIdAutocomplete}"/>
							</td>
							
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0009"></qp:message></th>
							<td>
								<c:choose>
									<c:when test="${screenDesignForm.screenPatternType == 1 }"><qp:message code="sc.screendesign.0019"></qp:message></c:when>
									<c:when test="${screenDesignForm.screenPatternType == 2 }"><qp:message code="sc.screendesign.0021"></qp:message></c:when>
									<c:when test="${screenDesignForm.screenPatternType == 3 }"><qp:message code="sc.screendesign.0030"></qp:message></c:when>
									<c:otherwise><qp:message code="sc.screendesign.0029"></qp:message></c:otherwise>
								</c:choose>
							</td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0031"></qp:message></th>
							<td><form:select path="templateType" class="form-control">
									<form:option value="1"><qp:message code="sc.screendesign.0142"></qp:message> </form:option>
									<form:option value="2"><qp:message code="sc.screendesign.0143"></qp:message></form:option>
								</form:select></td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0250"/></th>
							<td>
								<c:choose>
									<c:when test="${screenDesignForm.designMode == 1 }">
										<qp:formatText value="Prototype mode"></qp:formatText>
									</c:when>
									<c:when test="${screenDesignForm.designMode == 2 }">
										<qp:formatText value="Business mode"></qp:formatText>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0055"/></th>
							<td>
								<c:choose>
									<c:when test="${screenDesignForm.designStatus == 1 }">
										<qp:formatText value="Under design"></qp:formatText>
									</c:when>
									<c:when test="${screenDesignForm.designStatus == 2 }">
										<qp:formatText value="Fixed design"></qp:formatText>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.screendesign.0057"></qp:message></th>
							<td colspan="3">
								<form:textarea class="form-control qp-input-textarea" path="remark"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="showHideControlDesign" style="text-align: right;width: 100%" class="" >
				<span id="hideComponentTool" class="glyphicon glyphicon-resize-full fullScreenDesign" onclick="hideComponentTool()" title="Hide tools" style="cursor: pointer;"></span>
				<span id="showComponentTool" class="glyphicon glyphicon-resize-full fullScreenDesign" onclick="showComponentTool()" title="Show tools" style="cursor: pointer;display: none"></span>
			</div>
			<table id="allDragDropContent" style="width: 100%;">
				<tbody>
					<tr>
						<td valign="top" id="srcgenScreenAndAction" style="min-height: 27px;">
							<div id="loadingScreen"><qp:message code="sc.screendesign.0374"></qp:message> </div>
							<div id="dragDropContent" style="visibility: hidden;">
								<div class="com-sd-cover" id="srcgenHeaderLinkPanel" style="text-align: right; border: 2px solid #337AB7;">
									<div class="qp-div-action ui-droppable" id="srcgenHeaderLinkArea" style="line-height: 25px;min-height: 27px;">
										<c:forEach var="screenArea" items="${screenDesignForm.areaNonGroup }" varStatus="areaStatus">
											<c:forEach var="headerArea" items="${screenDesignForm.headerLinkItems}" varStatus="status">
												<c:if test="${screenArea.screenAreaId == headerArea.screenAreaId &&  screenArea.areaType == -1}">
													<script type="text/javascript">
														var obj = convertToJson('${headerArea.value }');
														var html = returnLinkItem(obj);
													</script>
												</c:if>
											</c:forEach>
											<c:if test="${screenArea.areaType == -1}">
												<input type="hidden" name="formAreaCaptionText" value=""><input type="hidden" name="formAreaCaptionId" value="${screenArea.messageDesign.messageCode }"><input type="hidden" name="formAreaCode" value="${screenArea.areaCode }"><input type="hidden" name="formTableColumnSize" value="${screenArea.colWidthUnit }"><input type="hidden" name="formTableWidth" value="${screenArea.tblWidthUnit }"><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value="${screenArea.alignPositionType }"><input type="hidden" name="formElementHidden" value="${screenArea.formElementHidden}"><input type="hidden" name="formDirection" value=""><input type="hidden" name="formDisplayType" value=""><input type="hidden" name="formElementTable" value="${screenArea.totalCol },${totalElement}"><input type="hidden" value="-1" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value="${screenArea.tblHeaderRow }"><input type="hidden" name="formHeaderComponentRow" value="${screenArea.tblComponentRow }"><input type="hidden" name="formIndex" value="0"><input type="hidden" name="formTotalGroup" value="0"><input type="hidden" value="" name="formAreaPatternType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/>
												<input type="hidden" name="panelStyle" value="${screenArea.panelStyle}" /><input type="hidden" name="headerStyle" value="${screenArea.headerStyle}" /><input type="hidden" name="rowStyle" value="${screenArea.rowStyle}" /><input type="hidden" name="inputStyle" value="${screenArea.inputStyle}" /><input type="hidden" name="alternateRowStyle" value="${screenArea.alternateRowStyle}" /><input type="hidden" name="areaCustomType" value="${screenArea.areaCustomType}" /><input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" name="formAreaTypeAction" value="${screenArea.areaTypeAction}"><input type="hidden" value="${screenArea.screenAreaId }" name="formAreaIdStore"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/>
												<input type="hidden" value="${screenArea.objectMappingId }" name="formObjectMappingId"/><input type="hidden" value="${screenArea.objectMappingType }" name="formObjectMappingType"/><input type="hidden" value='' name="formScreenAreaSortValue"/>
											</c:if>
										</c:forEach>
										<c:if test="${empty screenDesignForm.areaNonGroup || fn:length(screenDesignForm.areaNonGroup) == 0 }"><input type="hidden" name="formAreaCaptionText" value=""><input type="hidden" name="formAreaCaptionId" value=""><input type="hidden" name="formAreaCode" value=""><input type="hidden" name="formTableColumnSize" value=""><input type="hidden" name="formTableWidth" value=""><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value=""><input type="hidden" name="formElementHidden" value=""><input type="hidden" name="formDirection" value=""><input type="hidden" name="formDisplayType" value=""><input type="hidden" name="formElementTable" value=""><input type="hidden" value="-1" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value=""><input type="hidden" name="formHeaderComponentRow" value=""><input type="hidden" name="formIndex" value="0"><input type="hidden" name="formTotalGroup" value="0"><input type="hidden" name="formAreaPatternType" value=""/><input type="hidden" value='' name="formAreaEvent"/>
											<input type="hidden" name="panelStyle" value="" /><input type="hidden" name="headerStyle" value="" /><input type="hidden" name="rowStyle" value="" /><input type="hidden" name="inputStyle" value="" /><input type="hidden" name="alternateRowStyle" value="" /><input type="hidden" name="areaCustomType" value="" /><input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/><input type="hidden" name="formAreaTypeAction" value=""><input type="hidden" value="" name="formAreaIdStore"/><input type="hidden" value="" name="formObjectMappingId"/><input type="hidden" value="" name="formObjectMappingType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" value='' name="formScreenAreaSortValue"/>
										</c:if>
									</div>
								</div>
								<!-- Screen design area -->
								
								<!--Start load data  -->
								<c:forEach var="screenForm" items="${screenDesignForm.screenForms }" varStatus="formStatus">
								<div class="form-layout">
									<span class="form-setting">
										<br>
										<a href="javascript:" onclick="openNewTabSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-folder-close" title="Tab setting"></a>&nbsp;
										<a href="javascript:" onclick="deleteForm(this)" class="ui-state-default glyphicon glyphicon-remove" title="Delete this table"></a>
										<a style="cursor: move;" href="javascript:" class="ui-state-default glyphicon glyphicon-sort form-sort" title="Sort form"></a>
									</span>
									<div class="com-sd-cover ui-sortable form-area-content" id="srcgenScreen">
									<c:forEach var="screenArea" items="${screenDesignForm.screenAreas }" varStatus="areaStatus">
										<c:if test="${screenForm.screenFormId ==  screenArea.screenFormId}">
											${screenArea.startHtml }
											<c:set var="withAreaContent" value="100%" /><c:if test="${not empty screenArea.tblWidthUnit}"><c:set var="withAreaContent" value="${screenArea.tblWidthUnit}" /></c:if><c:set var="alignAreaContent" value="left" />
											<c:if test="${3 == screenArea.alignPositionType}"><c:set var="alignAreaContent" value="right" /></c:if>
											<c:choose>
											<c:when test="${screenArea.areaType == 0 }">
												<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
													<div class="panel panel-default">
														<div class="panel-heading" style="${screenArea.panelStyle}">
															<span class="glyphicon" aria-hidden="true"></span>
															<span class="qp-heading-text">
																<a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;" class="srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort" title="Move"></a><a href="javascript:" style="float: left; margin-top: 3px; margin-right: 5px;" onclick="openTableSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog" title="Table properties setting"></a>
																|&nbsp;<i style="margin-top: 1px;" name="icon-display" class="${screenArea.areaIcon }">&nbsp;</i><span class="qp-heading-text">${screenArea.messageDesign.messageString}</span>
																<a href="javascript:" style="float: right; margin-top: 3px;" onclick="deleteTable(this)" class="ui-state-default glyphicon glyphicon-remove" title="Delete this table"></a>
																<c:if test="${screenArea.areaCustomType == 1}">
																	<span style='float: right;' name='addRemoveTable'>(add remove table)&nbsp;&nbsp;</span>
																</c:if>
																<input type="hidden" name="formAreaCaptionText" value="${screenArea.messageDesign.messageString}"><input type="hidden" name="formAreaCaptionId" value="${screenArea.messageDesign.messageCode }"><input type="hidden" name="formAreaCode" value="${screenArea.areaCode }"><input type="hidden" name="formTableColumnSize" value="${screenArea.colWidthUnit }"><input type="hidden" name="formTableWidth" value="${screenArea.tblWidthUnit }"><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value="${screenArea.alignPositionType }"><input type="hidden" name="formElementHidden" value="${screenArea.formElementHidden}"><input type="hidden" name="formDirection" value=""><input type="hidden" name="formDisplayType" value=""><input type="hidden" name="formElementTable" value="${screenArea.totalCol },${totalElement}"><input type="hidden" value="0" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value="${screenArea.tblHeaderRow }"><input type="hidden" name="formHeaderComponentRow" value="${screenArea.tblComponentRow }"><input type="hidden" name="formIndex" value="${formStatus.index }"><input type="hidden" name="formTotalGroup" value=""><input type="hidden" value="${screenArea.areaPatternType }" name="formAreaPatternType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" name="areaCustomType" value="${screenArea.areaCustomType}" /><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/><input type="hidden" value="${screenArea.screenAreaId }" name="formAreaIdStore"/>
																<input type="hidden" name="panelStyle" value="${screenArea.panelStyle}" /><input type="hidden" name="headerStyle" value="${screenArea.headerStyle}" /><input type="hidden" name="rowStyle" value="${screenArea.rowStyle}" /><input type="hidden" name="inputStyle" value="${screenArea.inputStyle}" /><input type="hidden" name="alternateRowStyle" value="${screenArea.alternateRowStyle}" /><input type="hidden" value="${screenArea.objectMappingId }" name="formObjectMappingId"/><input type="hidden" value="${screenArea.objectMappingType }" name="formObjectMappingType"/>
																<input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" name="formAreaTypeAction" value="${screenArea.areaTypeAction}"><input type="hidden" value='' name="formScreenAreaSortValue"/>
															</span>
														</div>
														<div class="panel-body">
															<c:set var="searchType" value="" /><c:if test="${screenDesignForm.screenPatternType == 1 }"><c:set var="searchType" value="type='search'" /></c:if>
															<c:set var="rowIndex" value="0"></c:set><c:set var="colIndex" value="0"></c:set><c:set var="col" value="${screenArea.totalCol }" /><c:set var="row" value="${screenArea.totalCol/col }" />
															<c:if test="${screenArea.totalCol%col != 0 }"><c:set var="row" value="${row + 1 }" /></c:if>
															<c:set var="colRowSpanItem" value=""></c:set><c:set var="currentIndex" value=""></c:set><c:set var="arrColRow" /><c:set var="arrCurrentIndex" />
															<c:set var="elementGroup" value="0" />
															<table ${searchType } class="table table-bordered qp-table-form" id="srcgenTableId${areaStatus.index}">
																<colgroup>
																	<c:set var="columnIndexColGroup" value="0"></c:set>
																	<c:forTokens var="itemWidth" items="${screenArea.colWidthUnit }" delims="," ><col width="${itemWidth }"></col><c:set var="columnIndexColGroup" value="${columnIndexColGroup + 1 }"></c:set></c:forTokens>	
																	<c:if test="${columnIndexColGroup < screenArea.totalCol }"><c:forEach begin="1" end="${screenArea.totalCol - columnIndexColGroup }"><col width="${100/screenArea.totalCol }%"></col></c:forEach></c:if>			
																	<col width="20"></col>
																	<col width="20"></col>												
																</colgroup>
																<tbody class="ui-sortable">
																	<tr>
																		<c:forEach begin="1" end="${col/2 }" var="i">
																			<td class="srcgenControl" colspan="2" index="${i - 1 }">
																				<span style="float: left; cursor: move;" class="qp-glyphicon glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																				<a href="javascript:" style="float: right; margin-left: 5px;" onclick="removeColumnJS(this);" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-minus com-button-action" title="Remove column"></a>
																				<a href="javascript:" style="float: right; margin-left: 5px;" onclick="addColumnJS(this);" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-plus com-button-action" title="Add new column"></a>
																			</td>
																		</c:forEach>
																		<td class="srcgenControl" width="20" index="2"> </td>
																		<td class="srcgenControl" width="20" index="3">&nbsp;</td>
																	</tr>
																	<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">
																			<c:if test="${colIndex == 0 }"><tr index="${rowIndex }"></c:if><c:set var="colSpanProperties" value="" /><c:set var="rowSpanProperties" value="" /><c:set var="hasRowColSpan" value="false" />
																			<c:if test="${not empty screenItem.colSpan &&  screenItem.colSpan > 1}"><c:set var="colSpanProperties" value="colspan='${screenItem.colSpan }'" /><c:set var="hasRowColSpan" value="true" /></c:if><c:if test="${not empty screenItem.rowSpan &&  screenItem.rowSpan > 1}"><c:set var="rowSpanProperties" value="rowspan='${screenItem.rowSpan }'" /><c:set var="hasRowColSpan" value="true" /></c:if>
																			<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}"><c:set var="isDisable" value=""></c:set>
																			<c:if test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId)}"><c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set><c:set var="isDisable" value="${screenGroup.styleColRowSpan }"></c:set></c:if>
																				<c:choose>
																					<c:when test="${screenItem.logicalDataType == 20 && screenItem.itemType != 5}"><th index="${colIndex }"  style="${isDisable } ${screenArea.headerStyle }" ${colSpanProperties} ${rowSpanProperties}><c:choose>
																							<c:when test="${empty screenItem.value || empty screenItem.messageDesign}">
																								<input type="hidden" name="groupDisplayType"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement">
																								<input type="hidden" name="formElement" value='"label":"","datatype":"20","physicaldatatype":"","columnname":"label","rowspan":"","colspan":"","datasource":"","minvalue":"","maxvalue":"","formatcode":"","tablename":"","tablecode":"","tablecolumnname":"","tablecolumncode":"","connectionmsg":"","transitiontype":"","actiontype":"","parameters":"","screenactionid":"","linklabel":"","toscreenid":"","maxlength":"200","physicalmaxlength":"","labelText":"","msglabel":"","msgvalue":"","require":"2","elementtype":"0"' />
																								<span style="float: left; cursor: move;" class="qp-glyphicon glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span><div class="dropLabel ui-droppable">&nbsp;</div>
																							</c:when>
																							<c:otherwise>
																								<script type="text/javascript">
																									var obj = convertToJson('${screenItem.value }');
																									var html = returnElementTHAddEntity(obj, '${hasRowColSpan}');
																									document.write(html);
																								</script>
																							</c:otherwise>
																							</c:choose>
																						</th>
																						<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																					</c:when>
																					<c:otherwise>
																						<c:if test="${not empty screenItem.groupItemId }">	
																							<c:choose><c:when test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId) && not empty screenDesignForm.screenGroups[screenItem.groupItemId] }">
																									<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																									<c:if test="${screenItem.itemSeqNo == screenGroup.elementStart }"><c:set var="styleGroup" ></c:set>
																									<c:if test="${screenItem.itemGroupType == 1 }"><c:set var="styleGroup" value="class='enableGroupTd'" ></c:set></c:if>
																										<td ${styleGroup } index="${colIndex }" style="${isDisable } ${screenArea.inputStyle }" ${colSpanProperties} ${rowSpanProperties} >
																											<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }">
																											<c:choose>
																												<c:when test="${screenItem.itemGroupType == 1 }"><input type="hidden" name="enableGroup" value="true"></c:when>
																												<c:otherwise><input type="hidden" name="enableGroup"></c:otherwise>
																											</c:choose>
																											<input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }">
																									</c:if>
																									<c:choose>
																										<c:when test="${empty screenItem.value || empty screenItem.logicalDataType || screenItem.logicalDataType < 0  }">
																											<c:if test="${screenItem.itemGroupType == 0 || empty screenItem.logicalDataType || screenItem.logicalDataType < 0}">
																												<div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																											</c:if>
																										</c:when>
																										<c:otherwise>
																												<c:choose>
																													
																													<c:when test="${screenDesignForm.screenPatternType == 1 }">
																														<script type="text/javascript">
																															var obj = convertToJson('${screenItem.value }');
																															var html = returnElementTDSearchEntity(obj, '${screenGroup.groupType }');
																															document.write(html);
																														</script>
																													</c:when>
																													<c:otherwise>
																														<script type="text/javascript">
																															var obj = convertToJson('${screenItem.value }');
																															var html = returnElementTDLoad(obj, '${screenGroup.groupType }');
																															document.write(html);
																														</script>
																													</c:otherwise>
																												</c:choose>
																											
																										</c:otherwise>																											
																									</c:choose>
																									<c:if test="${screenItem.itemSeqNo == screenGroup.elementEnd }">																											
																										</td>
																										<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<td index="${colIndex }" style="${isDisable }" ${colSpanProperties} ${rowSpanProperties} >
																										<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }">
																										<div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																									</td>
																								</c:otherwise>
																							</c:choose>																							
																						</c:if>																																														
																					</c:otherwise>
																				</c:choose>	
																				<c:if test="${ colIndex == col}">																	
																					<td style="width: 50px;" class="sortable srcgenControl ui-sortable-handle">
																						<a href="javascript:" style="margin-top: 3px; cursor: move;" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-sort" title="Move"></a>
																					</td>
																					<td class="srcgenControl">
																						<a href="javascript:" style="margin-top: 3px;" onclick="removeRowTBJS(this);" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-minus com-button-action" title="Remove row"></a>
																					</td>
																					</tr>			
																					<c:set var="colIndex" value="0"></c:set><c:set var="rowIndex" value="${rowIndex + 1 }"></c:set>
																				</c:if>
																			</c:if>	
																	</c:forEach>
																</tbody>
															</table>
															<a style="margin-top: 2px; margin-left:6px;" href="javascript:" onclick="addRowTBJS(this);" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-plus com-button-action" title="Add new row"></a>
														</div>
													</div>
												</div>
											</c:when>
											<c:when test="${screenArea.areaType == 1 }">
												
												<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
													<div class="panel panel-default">
														<div class="panel-heading"  style="${screenArea.panelStyle}">
															<span class="glyphicon" aria-hidden="true"></span>
															<span class="qp-heading-text">
																<a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;" class="srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort" title="Move"></a>
																<a href="javascript:" style="float: left; margin-top: 3px; margin-right: 5px;" onclick="openTableListSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog" title="Table properties setting"></a>
																|&nbsp;<i name="icon-display" style="margin-top: 1px;" class="${screenArea.areaIcon }">&nbsp;</i><span>${screenArea.messageDesign.messageString}</span><a href="javascript:" style="float: right; margin-top: 3px;" onclick="deleteTable(this)" class="ui-state-default glyphicon glyphicon-remove" title="Delete this table"></a>
																<input type="hidden" name="formAreaCaptionText" value="${screenArea.messageDesign.messageString}"><input type="hidden" name="formAreaCaptionId" value="${screenArea.messageDesign.messageCode }"><input type="hidden" name="formAreaCode" value="${screenArea.areaCode }"><input type="hidden" name="formTableColumnSize" value="${screenArea.colWidthUnit }"><input type="hidden" name="formTableWidth" value="${screenArea.tblWidthUnit }"><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value="${screenArea.alignPositionType}"><input type="hidden" name="formElementHidden" value="${screenArea.formElementHidden}"><input type="hidden" name="formDirection" value=""><input type="hidden" name="formDisplayType" value=""><input type="hidden" name="formElementTable" value="${screenArea.totalCol },${totalElement}"><input type="hidden" value="1" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value="${screenArea.tblHeaderRow }"><input type="hidden" name="formHeaderComponentRow" value="${screenArea.tblComponentRow }"><input type="hidden" name="formIndex" value="${formStatus.index }"><input type="hidden" name="formTotalGroup" value=""><input type="hidden" value="${screenArea.areaPatternType }" name="formAreaPatternType"/><input type="hidden" name="panelStyle" value="${screenArea.panelStyle}" /><input type="hidden" name="headerStyle" value="${screenArea.headerStyle}" /><input type="hidden" name="rowStyle" value="${screenArea.rowStyle}" /><input type="hidden" name="inputStyle" value="${screenArea.inputStyle}" /><input type="hidden" name="alternateRowStyle" value="${screenArea.alternateRowStyle}" /><input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="areaCustomType" value="${screenArea.areaCustomType}" /><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" name="formAreaTypeAction" value="${screenArea.areaTypeAction}"><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/><input type="hidden" value="${screenArea.screenAreaId }" name="formAreaIdStore"/><input type="hidden" value="${screenArea.objectMappingId }" name="formObjectMappingId"/><input type="hidden" value="${screenArea.objectMappingType }" name="formObjectMappingType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" value='' name="formScreenAreaSortValue"/>
															</span>
														</div>
														<div class="panel-body">
															<c:set var="rowIndex" value="0"></c:set>
															<c:set var="colIndex" value="0"></c:set>
															<c:set var="col" value="${screenArea.totalCol }" />
															<table class="table table-bordered qp-table-list-none-action" id="srcgenTableId${areaStatus.index}">																
																<colgroup>
																	<c:set var="columnIndexColGroup" value="0"></c:set>
																	<c:forTokens var="itemWidth" items="${screenArea.colWidthUnit }" delims="," >
																		<col width="${itemWidth }"></col>
																		<c:set var="columnIndexColGroup" value="${columnIndexColGroup + 1 }"></c:set>
																	</c:forTokens>	
																	<c:if test="${columnIndexColGroup < screenArea.totalCol }">
																		<c:forEach begin="1" end="${screenArea.totalCol - columnIndexColGroup }">
																			<col width="${100/screenArea.totalCol }%"></col>
																		</c:forEach>
																	</c:if>															
																</colgroup>
																<thead>
																	<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">
																			<c:if test="${screenItem.logicalDataType == 20 && screenItem.itemType != 5}">
																				<c:set var="colSpanProperties" value="" /><c:set var="rowSpanProperties" value="" /><c:set var="hasRowColSpan" value="false" />
																				<c:if test="${not empty screenItem.colSpan &&  screenItem.colSpan > 1}"><c:set var="colSpanProperties" value="colspan='${screenItem.colSpan }'" /><c:set var="hasRowColSpan" value="true" /></c:if>
																				<c:if test="${not empty screenItem.rowSpan &&  screenItem.rowSpan > 1}"><c:set var="rowSpanProperties" value="rowspan='${screenItem.rowSpan }'" /><c:set var="hasRowColSpan" value="true" /></c:if>
																				<c:set var="isDisable" value=""></c:set>
																			<c:if test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId)}">
																				<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set><c:set var="isDisable" value="${screenGroup.styleColRowSpan }"></c:set>
																			</c:if>
																				<c:if test="${ colIndex == 0}"><tr index="${rowIndex }"></c:if>
																				<th style="text-align: left;${isDisable}  ${screenArea.headerStyle}" class="align-left" index="${colIndex }" ${colSpanProperties} ${rowSpanProperties} >
																					<c:choose>
																						<c:when test="${empty screenItem.value || empty screenItem.messageDesign}">
																							<input type="hidden" name="groupDisplayType"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement">
																							<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																							<div class="dropLabel ui-droppable"><input type="hidden" name="formElement" value='"label":"","datatype":"20","physicaldatatype":"","columnname":"label","rowspan":"","colspan":"","datasource":"","minvalue":"","maxvalue":"","formatcode":"","tablename":"","tablecode":"","tablecolumnname":"","tablecolumncode":"","connectionmsg":"","transitiontype":"","actiontype":"","parameters":"","screenactionid":"","linklabel":"","toscreenid":"","maxlength":"200","physicalmaxlength":"","labelText":"","msglabel":"","msgvalue":"","require":"2","elementtype":"0"' />&nbsp;</div>
																						</c:when>
																						<c:otherwise>	
																							<script type="text/javascript">
																								var obj = convertToJson('${screenItem.value }');
																								var html = returnElementTHAddEntity(obj, '${hasRowColSpan}');
																								document.write(html);
																							</script>
																						</c:otherwise>
																					</c:choose>																								
																				</th>
																				<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																				<c:if test="${ colIndex == col }">																					
																					</tr>
																					<c:set var="colIndex" value="0"></c:set><c:set var="rowIndex" value="${rowIndex + 1 }"></c:set>	
																				</c:if>
																			</c:if>	
																	</c:forEach>
																</thead>
																<tbody>					
																					
																	<c:set var="colIndex" value="0"></c:set>
																	<c:set var="isGroup" value="false" />																
																	<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">	
																			<c:if test="${empty screenItem.logicalDataType || (screenItem.logicalDataType != 20) || screenItem.itemType == 5}">
																				<c:set var="isDisable" value=""></c:set><c:set var="colSpanProperties" value="" /><c:set var="rowSpanProperties" value="" /><c:set var="hasRowColSpan" value="false" />
																				<c:if test="${not empty screenItem.colSpan &&  screenItem.colSpan > 1}"><c:set var="colSpanProperties" value="colspan='${screenItem.colSpan }'" /><c:set var="hasRowColSpan" value="true" /></c:if><c:if test="${not empty screenItem.rowSpan &&  screenItem.rowSpan > 1}"><c:set var="rowSpanProperties" value="rowspan='${screenItem.rowSpan }'" /><c:set var="hasRowColSpan" value="true" /></c:if>
																		<c:if test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId)}">
																			<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set><c:set var="isDisable" value="${screenGroup.styleColRowSpan }"></c:set>
																		</c:if>
																		<c:set var="styleFirstCol" value="" ></c:set>
																		<c:if test="${ colIndex == 0 && !isGroup}"><c:set var="styleFirstCol" value="align-left" ></c:set><tr style="${screenArea.rowStyle}" index="${rowIndex }"></c:if>
																				<c:if test="${not empty screenItem.groupItemId }">	<c:set var="isGroup" value="true" />
																						<c:choose><c:when test="${screenDesignForm.screenGroups.containsKey(screenItem.groupItemId) && not empty screenDesignForm.screenGroups[screenItem.groupItemId] }">
																								<c:set var="screenGroup" value="${screenDesignForm.screenGroups[screenItem.groupItemId] }"></c:set>
																								<c:if test="${screenItem.itemSeqNo == screenGroup.elementStart }">
																								
																								<c:set var="styleGroup" value="0" ></c:set>
																									<c:if test="${screenItem.itemGroupType == 1 }"><c:set var="styleGroup" value=" enableGroupTd" ></c:set></c:if>
																									<c:set var="fieldFormat" value=" ${screenItem.fieldStyle}"></c:set>
																									<c:if test="${screenGroup.elementStart != screenGroup.elementEnd }"><c:set var="fieldFormat" value=" result-text"></c:set></c:if>
																									<td class='${styleFirstCol } ${styleGroup } ${fieldFormat}'  index="${colIndex }" style="${isDisable } ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																										<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																										<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }">
																										<c:choose>
																											<c:when test="${screenItem.itemGroupType == 1 }"><input type="hidden" name="enableGroup" value="true"></c:when><c:otherwise><input type="hidden" name="enableGroup"></c:otherwise>
																										</c:choose>
																										<input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }">
																								</c:if>
																								
																								<c:choose>
																									<c:when test="${empty screenItem.value || empty screenItem.logicalDataType || screenItem.logicalDataType < 0  }">
																										<c:if test="${screenItem.itemGroupType == 0 || empty screenItem.logicalDataType || screenItem.logicalDataType < 0 }">
																											<div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																										</c:if>
																									</c:when>
																									<c:otherwise>	
																										<script type="text/javascript">
																											var obj = convertToJson('${screenItem.value }');
																											var html = returnElementTDLoad(obj, '${screenGroup.groupType }');
																											document.write(html);
																										</script>
																									</c:otherwise>																											
																								</c:choose>
																								<c:if test="${screenItem.itemSeqNo == screenGroup.elementEnd }">																											
																									</td>
																									<c:set var="colIndex" value="${colIndex + 1 }"></c:set>
																									<c:set var="isGroup" value="false" />
																								</c:if>
																							</c:when>
																							<c:otherwise>
																								<td index="${colIndex }" style="${isDisable }  ${screenArea.inputStyle}" ${colSpanProperties} ${rowSpanProperties} >
																									<span style="float: left; cursor: move;" class="qp-glyphicon  glyphicon glyphicon-screenshot ui-draggable ui-draggable-handle ui-droppable" title="Move"></span>
																									<input type="hidden" name="groupDisplayType" value="${screenGroup.groupType }"><input type="hidden" name="enableGroup"><input type="hidden" name="groupTotalElement" value="${screenGroup.totalElement }"><div class="dropComponent ui-droppable"><input type="hidden" name="formElement" value='${screenItem.value }'>&nbsp;</div>
																								</td>
																							</c:otherwise>
																						</c:choose>																							
																					</c:if>
																				<c:if test="${ colIndex == col}">
																					</tr>			
																					<c:set var="colIndex" value="0"></c:set>																					
																					<c:set var="rowIndex" value="${rowIndex + 1 }"></c:set>																					
																				</c:if>
																			</c:if>																																																													
																	</c:forEach>
																</tbody>
																<tfoot>
																	<tr>
																		<c:forEach begin="1" end="${screenArea.totalCol }">
																			<td class="srcgenControl" index="0">
																				<a href="javascript:" style="float: right; margin-left: 5px;" onclick="removeColumnTBJS(this);" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-minus com-button-action" title="Remove column"></a>
																				<a href="javascript:" style="float: right;" onclick="addColumnTBJS(this);" class=".ui-state-dark qp-glyphicon glyphicon glyphicon-plus com-button-action" title="Add new column"></a>
																			</td>
																		</c:forEach>																		
																	</tr>
																</tfoot>
															</table>
														</div>
													</div>
												</div>
											</c:when>
											<c:when test="${screenArea.areaType == 3 }">
												<div class="areaContent" style="width: ${withAreaContent}; float:${alignAreaContent};">
													<div class="panel panel-default">
														<div class="panel-heading" style="${screenArea.panelStyle}">
															<span class="glyphicon" aria-hidden="true"></span>
															<span class="qp-heading-text">
																<a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;" class="srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort" title="Move"></a>
																<a href="javascript:" style="float: left; margin-top: 3px; margin-right: 5px;" onclick="openSectionSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog" title="Table properties setting"></a>
																|&nbsp;<i name="icon-display" style="margin-top: 1px;" class="${screenArea.areaIcon }">&nbsp;</i><span>${screenArea.messageDesign.messageString}</span>
																<a href="javascript:" style="float: right; margin-top: 3px;" onclick="deleteTable(this)" class="ui-state-default glyphicon glyphicon-remove" title="Delete this table"></a>
																<input type="hidden" name="formAreaCaptionText" value="${screenArea.messageDesign.messageString}"><input type="hidden" name="formAreaCaptionId" value="${screenArea.messageDesign.messageCode }"><input type="hidden" name="formAreaCode" value="${screenArea.areaCode }"><input type="hidden" name="formTableColumnSize" value="${screenArea.colWidthUnit }"><input type="hidden" name="formTableWidth" value="${screenArea.tblWidthUnit }"><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value="${screenArea.alignPositionType }"><input type="hidden" name="formElementHidden" value="${screenArea.formElementHidden}"><input type="hidden" name="formDirection" value="${screenArea.elementPositionType }"><input type="hidden" name="formDisplayType" value="${screenArea.elementDipslayType }"><input type="hidden" name="formElementTable" value="${screenArea.totalCol },${totalElement}"><input type="hidden" value="3" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value="${screenArea.tblHeaderRow }"><input type="hidden" name="formHeaderComponentRow" value="${screenArea.tblComponentRow }"><input type="hidden" name="formIndex" value="${formStatus.index }"><input type="hidden" name="formTotalGroup" value=""><input type="hidden" value="" name="formAreaPatternType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/>
																<input type="hidden" name="panelStyle" value="${screenArea.panelStyle}" /><input type="hidden" name="headerStyle" value="${screenArea.headerStyle}" /><input type="hidden" name="rowStyle" value="${screenArea.rowStyle}" /><input type="hidden" name="inputStyle" value="${screenArea.inputStyle}" /><input type="hidden" name="alternateRowStyle" value="${screenArea.alternateRowStyle}" /><input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" name="formAreaTypeAction" value="${screenArea.areaTypeAction}"><input type="hidden" value="${screenArea.screenAreaId }" name="formAreaIdStore"/><input type="hidden" value="${screenArea.objectMappingId }" name="formObjectMappingId"/><input type="hidden" value="${screenArea.objectMappingType }" name="formObjectMappingType"/><input type="hidden" name="areaCustomType" value="${screenArea.areaCustomType}" /><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" value='' name="formScreenAreaSortValue"/>
															</span>
														</div>
														<div class="panel-body">
															<div class="section-area ui-droppable ui-sortable">
																<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">																	
																		<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<script type="text/javascript">
																			var style = 'padding: 2px;';
																			var align = '${screenArea.elementPositionType }';//0:left, 1 right
																			var displayType = '${screenArea.elementDipslayType }';//0: horial, 1: vertical
																			
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
																				style += '';
																			}
																			var html = returnElemenActionSection(obj, style);
																			document.write(html);
																			</script>
																		</c:if>																																																								
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
											</c:when>
											<c:when test="${screenArea.areaType == 2 }">
												<div class="areaContent " style="width: ${withAreaContent}; float:${alignAreaContent};">
													<div class="panel panel-default">
														<div class="panel-heading">
															<span class="glyphicon" aria-hidden="true"></span>
															<span class="qp-heading-text"><a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;color: #337AB7;" class="srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort" title="Move"></a>
																<a href="javascript:" style="float: left; margin-top: 3px; margin-right: 5px;color: #337AB7;" onclick="openActionAreaSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog" title="Table properties setting"></a>
																<a href="javascript:" style="float: right; margin-top: 3px;color: #337AB7;" onclick="deleteTable(this)" class="ui-state-default glyphicon glyphicon-remove" title="Delete this table"></a>
																<input type="hidden" name="formAreaCaptionText" value="${screenArea.messageDesign.messageString}"><input type="hidden" name="formAreaCaptionId" value="${screenArea.messageDesign.messageCode }"><input type="hidden" name="formAreaCode" value="${screenArea.areaCode }"><input type="hidden" name="formTableColumnSize" value="${screenArea.colWidthUnit }"><input type="hidden" name="formTableWidth" value="${screenArea.tblWidthUnit }"><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value="${screenArea.alignPositionType }"><input type="hidden" name="formElementHidden" value="${screenArea.formElementHidden}"><input type="hidden" name="formDirection" value="${screenArea.elementPositionType}"><input type="hidden" name="formDisplayType" value="${screenArea.elementDipslayType }"><input type="hidden" name="formElementTable" value="${screenArea.totalCol },${totalElement}"><input type="hidden" value="2" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value="${screenArea.tblHeaderRow }"><input type="hidden" name="formHeaderComponentRow" value="${screenArea.tblComponentRow }"><input type="hidden" name="formIndex" value="${formStatus.index }"><input type="hidden" name="formTotalGroup" value=""><input type="hidden" value="" name="formAreaPatternType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" name="panelStyle" value="${screenArea.panelStyle}" /><input type="hidden" name="headerStyle" value="${screenArea.headerStyle}" /><input type="hidden" name="rowStyle" value="${screenArea.rowStyle}" /><input type="hidden" name="inputStyle" value="${screenArea.inputStyle}" /><input type="hidden" name="alternateRowStyle" value="${screenArea.alternateRowStyle}" /><input type="hidden" name="areaCustomType" value="${screenArea.areaCustomType}" /><input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" name="formAreaTypeAction" value="${screenArea.areaTypeAction}"><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/><input type="hidden" value="${screenArea.screenAreaId }" name="formAreaIdStore"/><input type="hidden" value="${screenArea.objectMappingId }" name="formObjectMappingId"/><input type="hidden" value="${screenArea.objectMappingType }" name="formObjectMappingType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" value='' name="formScreenAreaSortValue"/>
															</span>
														</div>
														<div class="panel-body-action">
															<div class="action-area ui-droppable">
																<c:forEach var="screenItem" items="${screenArea.items }" varStatus="status">																	
																		<c:if test="${screenItem.screenAreaId ==  screenArea.screenAreaId}">
																			<c:set var="displayType" value=""></c:set><c:set var="elementDisplayType" value=""></c:set>
																			<c:if test="${screenArea.elementDipslayType == 1 }" ><c:set var="displayType" value="clear: both;"></c:set></c:if>
																			<c:choose>
																				<c:when test="${screenArea.elementPositionType == 0 }"><c:set var="elementDisplayType" value="float: left; padding: 2px"></c:set></c:when>
																				<c:when test="${screenArea.elementPositionType == 1 }"><c:set var="elementDisplayType" value="float: right;padding: 2px"></c:set></c:when>
																				<c:otherwise><c:set var="elementDisplayType" value="float: right;padding: 2px"></c:set></c:otherwise>
																			</c:choose>
																				<script type="text/javascript">
																				
																				var style = 'padding: 2px; ';
																				var align = '${screenArea.elementPositionType }';//0:left, 1 right
																				var displayType = '${screenArea.elementDipslayType }';//0: horial, 1: vertical
																				
																				if (align == "0") {
																					style += "float: left;";
																				} else if (align == "1") {
																					style += "float: right;";
																				} else {
																					style += "float: right;";
																				}
																				
																				if (displayType == "1") {
																					style += "clear: both;";
																				}
																				
																				/* var width = '${screenItem.itemWidthUnit}';
																				if (width.length > 0) {
																					style += 'width: ' + width + ';';
																				} */
																				
																				var obj = convertToJson('${screenItem.value }');
																				
																				if (obj.datatype != 10) {
																					style += '';
																				}
																				var html = returnElemenActionSection(obj, style);
																				document.write(html);
																				</script>
																		</c:if>																																																								
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
											</c:when>
											
											<c:when test="${screenArea.areaType == 4 }">
													<div class="areaContent " style="width: ${withAreaContent}; float:${alignAreaContent};">
														<div class="panel panel-default">
															<div class="panel-heading" style="background-color:#ebebe1;${screenArea.panelStyle}">
																<span class="glyphicon" aria-hidden="true"></span>
																<c:set var="style" value="${screenArea.panelStyle}" />
																<span class="qp-heading-text"><a href="javascript:" style="float:left; margin-top: 3px; cursor: move; margin-right: 5px;color: #337AB7;" class="srcgenTableSort ui-state-default qp-glyphicon-sort glyphicon glyphicon-sort" title="Move"></a>
																	<a href="javascript:" style="float: left; margin-top: 3px; margin-right: 5px;color: #337AB7;" onclick="openDialogCustomSectionSetting(this)" class="ui-state-default qp-glyphicon-cog glyphicon glyphicon-cog" title="Table properties setting"></a>
																	<c:if test="${screenArea.panelStyle eq null or screenArea.panelStyle eq '' }">
																		<c:set var="style" value="color: #337AB7" />
																	</c:if>
																	<span style="${style}"><qp:message code="sc.screendesign.0434" /></span>
																	<a href="javascript:" style="float: right; margin-top: 3px;color: #337AB7;" onclick="deleteTable(this)" class="ui-state-default glyphicon glyphicon-remove" title="Delete this table"></a>
																	<input type="hidden" name="formAreaCaptionText" value="${screenArea.messageDesign.messageString}"><input type="hidden" name="formAreaCaptionId" value="${screenArea.messageDesign.messageCode }"><input type="hidden" name="formAreaCode" value="${screenArea.areaCode }"><input type="hidden" name="formTableColumnSize" value="${screenArea.colWidthUnit }"><input type="hidden" name="formTableWidth" value="${screenArea.tblWidthUnit }"><input type="hidden" name="formTableRow" value=""><input type="hidden" name="formTablePosition" value="${screenArea.alignPositionType }"><input type="hidden" name="formElementHidden" value="${screenArea.formElementHidden}"><input type="hidden" name="formDirection" value="${screenArea.elementPositionType}"><input type="hidden" name="formDisplayType" value="${screenArea.elementDipslayType }"><input type="hidden" name="formElementTable" value="${screenArea.totalCol },${totalElement}"><input type="hidden" value="4" name="formAreaType"><input type="hidden" name="formHeaderLabelRow" value="${screenArea.tblHeaderRow }"><input type="hidden" name="formHeaderComponentRow" value="${screenArea.tblComponentRow }"><input type="hidden" name="formIndex" value="${formStatus.index }"><input type="hidden" name="formTotalGroup" value=""><input type="hidden" value="" name="formAreaPatternType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/><input type="hidden" name="panelStyle" value="${screenArea.panelStyle}" /><input type="hidden" name="headerStyle" value="${screenArea.headerStyle}" /><input type="hidden" name="rowStyle" value="${screenArea.rowStyle}" /><input type="hidden" name="inputStyle" value="${screenArea.inputStyle}" /><input type="hidden" name="alternateRowStyle" value="${screenArea.alternateRowStyle}" /><input type="hidden" name="areaCustomType" value="${screenArea.areaCustomType}" /><input type="hidden" name="formFixedRow" value="${screenArea.fixedRow}"><input type="hidden" name="formCustomSectionContent" value="${screenArea.customSectionContent}"><input type="hidden" name="formAreaTypeAction" value="${screenArea.areaTypeAction}"><input type="hidden" value='${screenArea.areaIcon }' name="formAreaIcon"/><input type="hidden" value="${screenArea.screenAreaId }" name="formAreaIdStore"/><input type="hidden" value="${screenArea.objectMappingId }" name="formObjectMappingId"/><input type="hidden" value="${screenArea.objectMappingType }" name="formObjectMappingType"/><input type="hidden" value='${screenArea.areaEventValue }' name="formAreaEvent"/>
																</span>
															</div>
														</div>
													</div>
												</c:when>
											
										</c:choose>
										${screenArea.endHtml }
										</c:if>										
									</c:forEach>
									<div class="form-content">
										<input type="hidden" name="screenFormFormActionCode" value="${screenForm.formCode }" ><input type="hidden" name="screenFormEncryptType" value="${screenForm.enctypeType }"><input type="hidden" name="screenFormMethodType" value="${screenForm.methodType }"><input type="hidden" name="screenFormFormSeqNo" value="${formStatus.index }"><input type="hidden" name="screenFormFormActionName" value="${screenForm.formActionName }"><input type="hidden" name="screenFormScreenFormId" value="${screenForm.screenFormId }" /><input type="hidden" name="screenFormTab" value='${ screenForm.tabValue}' />
									</div>
									<div id="srcgenAreaTemplate" class="drap-drop-area" style="float:left ;line-height: 24px; height: 30px; width: 100%; margin: 2px;" class="ui-droppable">&nbsp;</div>
								</div>
								</div>
								</c:forEach>
								<div id="srcgenFormTemplate" style="line-height: 24px; height: 30px; width: 100%; margin: 2px;" class="ui-droppable">&nbsp;</div>
								
								<!-- Display Action Area -->
							</div>
							<%-- <div class="qp-div-action" style="float: left;">
								<qp:authorization permission="screendesignDesign">
									<qp:message code="sc.screendesign.0306"/> <input type="button" value='Generate blogic' message-string='<qp:message code="sc.screendesign.0275"></qp:message>' onclick="return clickSaveButton(this, 'prototypeGen')" class="btn qp-button">
									<input type="button" value='Generate database' class="btn qp-button">
								</qp:authorization>
							</div> --%>
							<div class="qp-div-action" style="float: right;">
								<qp:authorization permission="screendesignDesign">
									<%-- <input type="button" onclick="clickDeleteButton(this, ${screenDesignForm.screenId });" messageId="inf.sys.0014" value="<qp:message code="sc.sys.0008"></qp:message>" class="btn btn-md btn-warning"> --%>
									<input id="previewScreenBtn" type="button" value='<qp:message code="sc.screendesign.0244"></qp:message>' onclick="return previewDialog(this)" class="btn qp-button">
									<input type="button" value='<qp:message code="sc.screendesign.0179"></qp:message>' onclick="return clickSaveButton(this, 'prototypeSave')" class="btn qp-button">
								</qp:authorization>
							</div>
						</td>
						<td valign="top" width="252px;"style="padding-left: 12px;">
							<div id="srcgenControlElement">
								<div style="width: 240px; padding-top: 0px;" id="srcgenControl">
									<div class="com-sd-tools" id="srcgenControlDiv">
										<%-- <table width="100%">
											<tbody>
												<tr class="srcgenElementsTable">
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 109px;" elementtype="0"><qp:message code="sc.screendesign.0058"></qp:message></div>
													</td>
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 109px;" elementtype="1"><qp:message code="sc.screendesign.0059"></qp:message></div>
													</td>
												</tr>
												<tr class="srcgenElementsTable">
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 109px;" elementtype="2"><qp:message code="sc.screendesign.0060"></qp:message></div>
													</td>
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 51px; float: left;" elementtype="3"><qp:message code="sc.screendesign.0061"></qp:message></div>
														<div id="form-element" class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative;  width: 51px; float: left;" elementtype="4"><qp:message code="sc.screendesign.0116"></qp:message></div>
													</td>													
												</tr>
												<tr id="srcgenElements">
													<td style="vertical-align: top;">
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="0" columnname="<qp:formatCode code="sc.screendesign.0041"></qp:formatCode>" label="<qp:message code="sc.screendesign.0041"></qp:message>" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0041"></qp:message></div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="8" columnname="<qp:formatCode code="sc.screendesign.0042"></qp:formatCode>" label="<qp:message code="sc.screendesign.0042"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0042"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="12" columnname="<qp:formatCode code="sc.screendesign.0043" />" label="<qp:message code="sc.screendesign.0043" />" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0043" /></div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="3" columnname="<qp:formatCode code="sc.screendesign.0044"></qp:formatCode>" label="<qp:message code="sc.screendesign.0044"></qp:message>" maxlength="200" require="2" elementtype="0"> <qp:message code="sc.screendesign.0044"></qp:message></div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="14" columnname="<qp:formatCode code="sc.screendesign.0045"></qp:formatCode>" label="<qp:message code="sc.screendesign.0045"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0045"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="9" columnname="<qp:formatCode code="sc.screendesign.0046"></qp:formatCode>" label="<qp:message code="sc.screendesign.0046"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0046"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="0�1" msglabel="<qp:formatCode code="sc.screendesign.0317"/>�<qp:formatCode code="sc.screendesign.0318"/>" datatype="6" columnname="<qp:formatCode code="sc.screendesign.0047"></qp:formatCode>" label="<qp:message code="sc.screendesign.0047"></qp:message>" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0047"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="0�1" msglabel="<qp:formatCode code="sc.screendesign.0317"/>�<qp:formatCode code="sc.screendesign.0318"/>" datatype="7" columnname="<qp:formatCode code="sc.screendesign.0048"></qp:formatCode>" label="<qp:message code="sc.screendesign.0048"></qp:message>" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0048"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="0�1" msglabel="<qp:formatCode code="sc.screendesign.0317"/>�<qp:formatCode code="sc.screendesign.0318"/>" datatype="5" columnname="<qp:formatCode code="sc.screendesign.0049"></qp:formatCode>" label="<qp:message code="sc.screendesign.0049"></qp:message>" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0049"></qp:message> </div>
													</td>
													<td style="vertical-align: top;">
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="20" columnname="<qp:formatCode code="sc.screendesign.0050"></qp:formatCode>" label="<qp:message code="sc.screendesign.0050"></qp:message>" maxlength="200" require="2" elementtype="0" id="divLabel"><qp:message code="sc.screendesign.0050"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="21" columnname="<qp:formatCode code="sc.screendesign.0181"></qp:formatCode>" label="<qp:message code="sc.screendesign.0181"></qp:message>" maxlength="200"  require="2" elementtype="0"><qp:message code="sc.screendesign.0181"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="1" columnname="<qp:formatCode code="sc.screendesign.0182"></qp:formatCode>" label="<qp:message code="sc.screendesign.0051"></qp:message>" maxlength="200" physicalmaxlength="200" formatcode="Name"  require="2" elementtype="0"><qp:message code="sc.screendesign.0182"></qp:message> </div>														
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="2" columnname="<qp:formatCode code="sc.screendesign.0052"></qp:formatCode>" label="<qp:message code="sc.screendesign.0052"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0052"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="4" columnname="<qp:formatCode code="sc.screendesign.0053"></qp:formatCode>" label="<qp:message code="sc.screendesign.0053"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0053"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="18" columnname="<qp:formatCode code="sc.screendesign.0054"></qp:formatCode>" label="<qp:message code="sc.screendesign.0054"></qp:message>" maxlength="50" physicalmaxlength="50" formatcode="Alphanumeric" require="2" elementtype="0"><qp:message code="sc.screendesign.0054"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="15" columnname="<qp:formatCode code="sc.screendesign.0055"></qp:formatCode>" label="<qp:message code="sc.screendesign.0055"></qp:message>" maxlength="50" physicalmaxlength="50" formatcode="Email" require="2" elementtype="0"><qp:message code="sc.screendesign.0055"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="16" columnname="<qp:formatCode code="sc.screendesign.0056"></qp:formatCode>" label="<qp:message code="sc.screendesign.0056"></qp:message>" maxlength="20" physicalmaxlength="20" formatcode="Phone" require="2" elementtype="0"><qp:message code="sc.screendesign.0056"></qp:message> </div>
														<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 109px;" msgvalue="" datatype="10" columnname="<qp:formatCode code="sc.screendesign.0057"></qp:formatCode>" label="<qp:message code="sc.screendesign.0057"></qp:message>" maxlength="1000" physicalmaxlength="1000" formatcode="Remark" require="2" elementtype="0"><qp:message code="sc.screendesign.0057"></qp:message> </div>
													</td>
												</tr>
												<tr id="srcgenAction">
													<td>
														<div id="divHtmlLink" class="div-html-element ui-draggable" style="cursor: move; position: relative;width: 109px;" datatype="11" columnname="link" label="Link" maxlength="" labelText="<qp:message code="sc.screendesign.0039"></qp:message>" require="2" elementtype="0"><qp:message code="sc.screendesign.0039"></qp:message></div>
													</td>
													<td>
														<div id="divHtmlButton" class="div-html-element ui-draggable" style="cursor: move; position: relative;width: 109px;" datatype="13" columnname="button" label="Button" maxlength="" labelText="<qp:message code="sc.screendesign.0128"></qp:message>" require="2" elementtype="0"><qp:message code="sc.screendesign.0128"></qp:message></div>
													</td>
												</tr>
											</tbody>
										</table> --%>
										<div class="strike">
											<span><qp:message code="sc.screendesign.0427"></qp:message></span>
										</div>
										<table width="100%">
											<tbody>
												<tr class="srcgenElementsTable">											
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: ;" elementtype="0"><qp:message code="sc.screendesign.0058"></qp:message></div>
													</td>
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 51px; float: left;" islist elementtype="0"><qp:message code="sc.screendesign.0467" /></div>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 51px; float: left;" elementtype="1"><qp:message code="sc.screendesign.0468" /></div>
													</td>
												</tr>
												<tr class="srcgenElementsTable">
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: ;" elementtype="2"><qp:message code="sc.screendesign.0060"></qp:message></div>
													</td>
													<td>
														<div class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative; width: 51px; float: left;" elementtype="3"><qp:message code="sc.screendesign.0061"></qp:message></div>
														<div id="form-element" class="div-html-element ui-draggable" searchtype="${screenDesignForm.screenPatternType}" style="cursor: move; border: 1px solid blue; color: blue; position: relative;  width: 51px; float: left;" elementtype="4"><qp:message code="sc.screendesign.0116"></qp:message></div>
													</td>													
												</tr>
											</tbody>
										</table>
										<div id="srcgenElements">
											<div class="strike">
												<span><qp:message code="sc.screendesign.0428"></qp:message></span>
											</div>											
											<table width="100%">
												<tbody>
													<tr>												
														<td style="vertical-align: top;">
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 110px;" msgvalue="" datatype="0" columnname="<qp:formatCode code="sc.screendesign.0041"></qp:formatCode>" label="<qp:message code="sc.screendesign.0041"></qp:message>" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0041"></qp:message>
																<span class="glyphicon glyphicon-collapse-down"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="8" columnname="<qp:formatCode code="sc.screendesign.0042"></qp:formatCode>" label="<qp:message code="sc.screendesign.0042"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0042"></qp:message> 
																<span class="glyphicon glyphicon-usd"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="12" columnname="<qp:formatCode code="sc.screendesign.0043" />" label="<qp:message code="sc.screendesign.0043" />" maxlength="200" require="2" elementtype="0"><qp:message code="sc.screendesign.0043" />
																<span class="glyphicon glyphicon-file"   style="float: right; top: 3px;"></span>
															</div>														
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="14" columnname="<qp:formatCode code="sc.screendesign.0045"></qp:formatCode>" label="<qp:message code="sc.screendesign.0045"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0045"></qp:message> 
																<span class="glyphicon glyphicon-calendar"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="9" columnname="<qp:formatCode code="sc.screendesign.0046"></qp:formatCode>" label="<qp:message code="sc.screendesign.0046"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0046"></qp:message> 
																<span class="glyphicon glyphicon-time"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="0�1" msglabel="<qp:formatCode code="sc.screendesign.0317"/>�<qp:formatCode code="sc.screendesign.0318"/>" datatype="6" columnname="<qp:formatCode code="sc.screendesign.0047"></qp:formatCode>" label="<qp:message code="sc.screendesign.0047"></qp:message>" require="2" elementtype="0"><qp:message code="sc.screendesign.0047"></qp:message> 
																<span class="glyphicon glyphicon-check"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="0�1" msglabel="<qp:formatCode code="sc.screendesign.0317"/>�<qp:formatCode code="sc.screendesign.0318"/>" datatype="7" columnname="<qp:formatCode code="sc.screendesign.0048"></qp:formatCode>" label="<qp:message code="sc.screendesign.0048"></qp:message>" require="2" elementtype="0"><qp:message code="sc.screendesign.0048"></qp:message> 
																<span class="glyphicon glyphicon-collapse-down"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="0�1" msglabel="<qp:formatCode code="sc.screendesign.0317"/>�<qp:formatCode code="sc.screendesign.0318"/>" datatype="5" columnname="<qp:formatCode code="sc.screendesign.0049"></qp:formatCode>" label="<qp:message code="sc.screendesign.0049"></qp:message>" require="2" elementtype="0"><qp:message code="sc.screendesign.0049"></qp:message> 
																<span class="glyphicon glyphicon-record"   style="float: right; top: 3px;"></span>
															</div>
														</td>
														<td style="vertical-align: top;">														
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="1" columnname="<qp:formatCode code="sc.screendesign.0182"></qp:formatCode>" label="<qp:message code="sc.screendesign.0051"></qp:message>" maxlength="200" physicalmaxlength="200" formatcode="Name"  require="2" elementtype="0"><qp:message code="sc.screendesign.0182"></qp:message>
															 	<span class="glyphicon glyphicon-edit"   style="float: right; top: 3px;"></span>
															 </div>														
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="2" physicaldatatype="5" columnname="<qp:formatCode code="sc.screendesign.0052"></qp:formatCode>" label="<qp:message code="sc.screendesign.0052"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0052"></qp:message> 
																<span class=""   style="float: right; top: 3px;">123</span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="3" physicaldatatype="16" columnname="<qp:formatCode code="sc.screendesign.0044"></qp:formatCode>" label="<qp:message code="sc.screendesign.0044"></qp:message>" maxlength="200" require="2" elementtype="0"> <qp:message code="sc.screendesign.0044"></qp:message>
																<span class=""   style="float: right; top: 3px;">12.3</span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="4" columnname="<qp:formatCode code="sc.screendesign.0053"></qp:formatCode>" label="<qp:message code="sc.screendesign.0053"></qp:message>" maxlength="10" require="2" elementtype="0"><qp:message code="sc.screendesign.0053"></qp:message> 
																<span class="glyphicon glyphicon-calendar"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="18" columnname="<qp:formatCode code="sc.screendesign.0054"></qp:formatCode>" label="<qp:message code="sc.screendesign.0054"></qp:message>" maxlength="50" physicalmaxlength="50" formatcode="Alphanumeric" require="2" elementtype="0"><qp:message code="sc.screendesign.0054"></qp:message>
																<span class="glyphicon glyphicon-barcode"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="15" columnname="<qp:formatCode code="sc.screendesign.0055"></qp:formatCode>" label="<qp:message code="sc.screendesign.0055"></qp:message>" maxlength="50" physicalmaxlength="50" formatcode="Email" require="2" elementtype="0"><qp:message code="sc.screendesign.0055"></qp:message> 
																<span class="glyphicon glyphicon-envelope"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="16" columnname="<qp:formatCode code="sc.screendesign.0056"></qp:formatCode>" label="<qp:message code="sc.screendesign.0056"></qp:message>" maxlength="20" physicalmaxlength="20" formatcode="Phone" require="2" elementtype="0"><qp:message code="sc.screendesign.0056"></qp:message> 
																<span class="glyphicon glyphicon-phone"   style="float: right; top: 3px;"></span>
															</div>
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" msgvalue="" datatype="10" columnname="<qp:formatCode code="sc.screendesign.0057"></qp:formatCode>" label="<qp:message code="sc.screendesign.0057"></qp:message>" maxlength="1000" physicalmaxlength="1000" formatcode="Remark" require="2" elementtype="0"><qp:message code="sc.screendesign.0057"></qp:message> 
																<span class="glyphicon glyphicon-comment"   style="float: right; top: 3px;"></span>
															</div>
														</td>												
													</tr>													
												</tbody>
											</table>
											
											<div class="strike">
												<span><qp:message code="sc.screendesign.0429"></qp:message></span>
											</div>
											<table width="100%">
												<tbody>
													<tr>
														<td style="vertical-align: top;">
															<div class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 110px;" msgvalue="" datatype="20" columnname="<qp:formatCode code="sc.screendesign.0050"></qp:formatCode>" label="<qp:message code="sc.screendesign.0050"></qp:message>" maxlength="200" require="2" elementtype="0" id="divLabel"><qp:message code="sc.screendesign.0050"></qp:message> 
																<span class="glyphicon glyphicon-font"   style="float: right; top: 3px;"></span>	
															</div>														
														</td>
														<td style="vertical-align: top;">														
															<div id="divDynamicLabel" class="div-html-element ui-draggable" style="cursor: move; position: relative; width: 110px;" msgvalue="" datatype="21" columnname="<qp:formatCode code="sc.screendesign.0181" ></qp:formatCode>" label="<qp:message code="sc.screendesign.0181"></qp:message>" maxlength="200"  require="2" elementtype="0"><qp:message code="sc.screendesign.0181"></qp:message> 
																<span class="glyphicon glyphicon-text-background"   style="float: right; top: 3px;"></span>
															</div>														
														</td>
													</tr>	
												</tbody>
											</table>
										</div>
										<div class="strike">
											<span><qp:message code="sc.screendesign.0430"></qp:message></span>
										</div>
										<table width="100%">
											<tbody>
												<tr id="srcgenAction">													
													<td style="vertical-align: top;">
														<div id="divHtmlLink" class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" datatype="11" columnname="link" label="Link" maxlength="" labelText="<qp:message code="sc.screendesign.0039" />" require="2" elementtype="0"><qp:message code="sc.screendesign.0432" />
															<span class="glyphicon glyphicon-link" style="float: right; top: 3px;"></span>	
														</div>
														<div id="divHtmlButton" class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" datatype="13" columnname="button" label="Button" maxlength="" labelText="<qp:message code="sc.screendesign.0128" />" require="2" elementtype="0"><qp:message code="sc.screendesign.0128" />
															<span class="glyphicon glyphicon-unchecked" style="float: right; top: 3px;"></span>		
														</div>
													</td>
													<td style="vertical-align: top;">
														<div id="divHtmlLinkDynamic" class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" datatype="22" columnname="linkDynamic" label="linkDynamic" maxlength="" labelText="<qp:message code="sc.screendesign.0431" />"  require="2" elementtype="0"><qp:message code="sc.screendesign.0431" />
															<span class="glyphicon glyphicon-link" style="float: right; top: 3px;"></span>		
														</div>
													</td>
												</tr>	
											</tbody>
										</table>
										
										<div class="strike">
											<span><qp:message code="sc.screendesign.0466" /></span>
										</div>
										<table width="100%">
											<tbody>
												<tr id="srcgenCustom">
													<td style="vertical-align: top;">
														<div id="divCustomItem" class="div-html-element ui-draggable" style="cursor: move; position: relative; width:  110px;" datatype="23" columnname="Item custom" label="Item custom" maxlength="" labelText="Item custom" require="2" elementtype="0"><qp:message code="sc.screendesign.0302" />
															<span class="glyphicon glyphicon-paperclip" style="float: right; top: 3px;"></span>
														</div>
													</td>
													<td style="vertical-align: top;">
														<div id="sectionCustomArea" class="div-html-element" style="cursor: move; position: relative; width:  110px;"  elementtype="4"><qp:message code="sc.screendesign.0060" />
															<span class="glyphicon glyphicon-paperclip" style="float: right; top: 3px;"></span>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
										
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<!-- End body panel -->

			<!-- End root panel -->
			<!-- dialog -->
		<script id="tbl-condition-template" type="text/template">
			<tr>
				<td style="border: none;" width="70%"><qp:autocomplete optionLabel="" selectSqlId="" optionValue="" name=""></qp:autocomplete></td>
				<td style="border: none;" width="28%"><qp:message code="sc.screendesign.0297"/></td>
				<td style="border: none;" class="com-output-fixlength" width="2%">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('tbl-condition', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
		<script id="tbl-action-template" type="text/template">
			<tr>
				<td style="border: none;" width="70%"><qp:autocomplete optionLabel="" selectSqlId="" optionValue="" name=""></qp:autocomplete></td>
				<td style="border: none;" width="28%"><qp:message code="sc.screendesign.0297"/></td>
				<td style="border: none;" class="com-output-fixlength" width="2%">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('tbl-action', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
		<script id="dialog-component-list-setting-tbl-options-template" type="text/template">
				<tr>
					<td class="com-output-fixlength tableIndex"></td>
					<td class="colOptionName"><input type="text" class="form-control qp-input-text" name="parameterOptionName"/></td>
					<td><input type="text" class="form-control qp-input-text" name="parameterOptionValue"/></td>
					<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dialog-component-list-setting-tbl-options', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
				</tr>
		</script>
		<script id="dialog-component-list-setting-tbl-system-options-template" type="text/template">
				<tr>
					<td class="com-output-fixlength tableIndex"></td>
					<td class="colOptionName"><span name="parameterOptionName"/></td>
					<td><span name="parameterOptionValue"/></td>
				</tr>
		</script>
		<script id="dialog-component-list-setting-tbl-table-options-template" type="text/template">
				<tr>
					<td class="com-output-fixlength tableIndex"></td>
					<td class="colOptionName"><span name="parameterOptionName"/></td>
					<td><span name="parameterOptionValue"/></td>
				</tr>
		</script>
		<script id="dialog-button-area-setting-tbl-parameter-template" type="text/template">
			<tr>
				<td class="com-output-fixlength tableIndex">1</td>
				<td>
					<input type="text" class="form-control" name="parameterAttribute" maxlength="100"/>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTable" emptyLabel="sc.sys.0030" optionValue="optionValue" name="tableCode" arg01="${screenDesignForm.projectId}" arg02="20" onChangeEvent="dialogLinkAreaSettingTableCodeOnChange"></qp:autocomplete>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTableItem" emptyLabel="sc.sys.0030" optionValue="optionValue" name="columnCode" arg01="20"></qp:autocomplete>
				</td>
				<td class="com-output-fixlength">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dialog-button-area-setting-tbl-parameter', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
		<script id="model-section-setting-tbl-parameter-template" type="text/template">
			<tr>
				<td class="com-output-fixlength tableIndex">1</td>
				<td>
					<input type="text" class="form-control" name="parameterAttribute" maxlength="100"/>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTable" emptyLabel="sc.sys.0030" optionValue="optionValue" name="tableCode" arg01="${screenDesignForm.projectId}" arg02="20" onChangeEvent="dialogLinkAreaSettingTableCodeOnChange"></qp:autocomplete>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTableItem" emptyLabel="sc.sys.0030" optionValue="optionValue" name="columnCode" arg01="20"></qp:autocomplete>
				</td>
				<td class="com-output-fixlength">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('model-section-setting-tbl-parameter', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
		<script id="tbl-hiddenParameter-template" type="text/template">
			<tr>
				<td class="com-output-fixlength tableIndex">1</td>
				<td><input type="text" class="form-control qp-input-text-detail" name="parameterAttribute" maxlength="100"></td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTable" emptyLabel="sc.sys.0030" optionValue="optionValue" name="tableCode" arg01="${screenDesignForm.projectId}" arg02="20" onChangeEvent="dialogLinkAreaSettingTableCodeOnChange"></qp:autocomplete>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTableItem" emptyLabel="sc.sys.0030" optionValue="optionValue" name="columnCode" arg01="20"></qp:autocomplete>
				</td>
				<td class="com-output-fixlength">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('tbl-hiddenParameter', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
		<script id="dialog-form-parameter-tbl-parameter-template" type="text/template">
			<tr>
				<td class="com-output-fixlength tableIndex">1</td>
				<td>
					<input type="text" class="form-control" name="parameterAttribute" maxlength="100"/>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTable" emptyLabel="sc.sys.0030" optionValue="optionValue" name="tableCode" arg01="${screenDesignForm.projectId}" arg02="20" onChangeEvent="dialogLinkAreaSettingTableCodeOnChange"></qp:autocomplete>
				</td>
				<td>
					<qp:autocomplete optionLabel="optionLabel" selectSqlId="autocompleteGetTableItem" emptyLabel="sc.sys.0030" optionValue="optionValue" name="columnCode" arg01="20"></qp:autocomplete>
				</td>
				<td class="com-output-fixlength">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dialog-form-parameter-tbl-parameter', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
		<script id="physical-data-template" type="text/template">
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<th><qp:message code="sc.screendesign.0098"></qp:message></th>
				<td>
					<select class="combobox input-large form-control" name="datatype">
						<option><qp:message code="sc.screendesign.0149"></qp:message></option>
						<option><qp:message code="sc.screendesign.0038"></qp:message></option>
						<option><qp:message code="sc.screendesign.0150"></qp:message></option>
						<option><qp:message code="sc.screendesign.0151"></qp:message></option>
						<option><qp:message code="sc.screendesign.0052"></qp:message></option>
						<option><qp:message code="sc.screendesign.0152"></qp:message></option>
						<option><qp:message code="sc.screendesign.0153"></qp:message></option>
					</select>
				</td>
			</tr>
			<tr>
				<th><qp:message code="sc.screendesign.0072"></qp:message></th>
				<td>
					<span name="tablename"></span>
				</td>
			</tr>
			<tr>
				<th><qp:message code="sc.screendesign.0073"></qp:message></th>
				<td>
					<span name="tablecolumnname"></span>
				</td>
			</tr>
		</script>
		
		<script id="area-event-template" type="text/template">
				<br>
		<table class='table table-bordered qp-table-form' name='require-constraint'
			style='background-color: #FFFFFF'>
			<colgroup>
				<col width='20%' />
				<col width='30%' />
				<col width='20%' />
				<col width='30%' />
			</colgroup>
			<tr >
				<th style='text-align: left; vertical-align: middle;background-color: #ebebe1;' colspan='4'>
					<qp:message code="sc.screendesign.0242"/><span id='id-block-require-constraint${sequen}' onclick='removeRequireConstraint(this);' class='glyphicon glyphicon-remove-circle pull-right'
					style='font-size: 20px'></span>
				</th>
			</tr>
			<tr>
				<td colspan='4'>
					<table class='table table-bordered qp-table-form'>
						<colgroup>
							<col width='30%' />
							<col width='65%' />
						</colgroup>
						<tr name="if">
							<th><qp:message code="sc.screendesign.0299"/></th>
							<td>
								<table class='table table-bordered qp-table-form' id='if-constraint'>
									<colgroup>
										<col width='45%' />
										<col width='45%' />
										<col width='10%' />
									</colgroup>
									<tr>
										<td>
											<qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" sourceType="local" name="screenItemCode" sourceCallback="loadScreenItemCodeByArea" ></qp:autocomplete>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
										</td>
										<td><qp:message code="sc.screendesign.0297"/></td>
										<td>
											<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick="$.qp.removeRowJS('if-constraint', this);" style='margin-top: 3px;' href='javascript:void(0)'></a>
										</td>
									</tr>
								</table>
								<div class='qp-add-left'>
									<a class='btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action' onclick='$.qp.addRowJSByLink(this);initCatAutocomplete(this);' style='margin-top: 3px;' href='javascript:void(0)'></a>
								</div>
							</td>
						</tr>
						<tr name="then">
							<th><qp:message code="sc.screendesign.0300"/></th>
							<td>
								<table class='table table-bordered qp-table-form' id='then-constraint'>
									<colgroup>
										<col width='45%' />
										<col width='45%' />
										<col width='10%' />
									</colgroup>
									<tr>
										<td>
											<qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" sourceType="local" name="screenItemCode" sourceCallback="loadScreenItemCodeByArea" ></qp:autocomplete>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
										</td>
										<td><qp:message code="sc.screendesign.0298"/></td>
										<td>
											<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick="$.qp.removeRowJS('the-constraint', this);" style='margin-top: 3px;' href='javascript:void(0)'></a>
										</td>
									</tr>
								</table>
								<div class='qp-add-left'>
									<a class='btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action' onclick='$.qp.addRowJSByLink(this);initCatAutocomplete(this);' style='margin-top: 3px;' href='javascript:void(0)'></a>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</script>
		<script id="tbl-hiddenAttibutes-template" type="text/template">
			<tr>
				<td class="com-output-fixlength tableIndex">1</td>
				<td>
					<input type="hidden" name="screenItemStoreId" />
					<qp:autocomplete optionLabel="optionLabel" cssInput="qp-convention-name-row"  optionValue="optionValue" mustMatch="false" name="labelName"  onSelectEvent='dialogLabelSettinglableOnSelect' arg01="${screenDesignForm.moduleId }" selectSqlId="findSystemMessage" arg05="${screenDesignForm.projectId}"></qp:autocomplete>
				</td>
				<!-- assign project_id = 1 -->
				<td><input type="text" class="form-control qp-input-text-detail qp-convention-code-row" name="parameterAttribute" maxlength="100"></td>
				<td>
					<form:select path="" name="parameterDatatype" class="form-control">
						<form:options items="${CL_PRIMITIVE_DATATYPE }"/>
					</form:select>
				</td>
				<td class="com-output-fixlength"><a href="javascript:" style="margin-top: 3px;" onclick="$.qp.removeRowJS('tbl-hiddenAttibutes', this);" class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove row"></a></td>
			</tr>
		</script>
		<script id="if-constraint-template" type="text/template">
				<tr>
					<td>
						<qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" sourceType="local" name="screenItemCode" sourceCallback="loadScreenItemCodeByArea" ></qp:autocomplete>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
					</td>
					<td><qp:message code="sc.screendesign.0380"></qp:message></td>
					<td>
						<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick='$.qp.removeRowJS("if-constraint", this);' style='margin-top: 3px;' href='javascript:void(0)'></a>
					</td>
				</tr>
		</script>
		<script id="then-constraint-template" type="text/template">
									<tr>
										<td>
											<qp:autocomplete optionLabel="optionLabel" optionValue="optionValue" sourceType="local" name="screenItemCode" sourceCallback="loadScreenItemCodeByArea" ></qp:autocomplete>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
										</td>
										<td><qp:message code="sc.screendesign.0381"></qp:message></td>
										<td>
											<a class='btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action' title='' onclick='$.qp.removeRowJS("then-constraint", this);' style='margin-top: 3px;' href='javascript:void(0)'></a>
										</td>
									</tr>
		</script>	
		
		<div class="modal fade" id="dialog-custom-section-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			 <jsp:include page="dialog/dialogCustomSectionSetting.jsp"></jsp:include>
		</div>	
		<div class="modal fade" id="dialog-custom-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			 <jsp:include page="dialog/dialogCustomSetting.jsp"></jsp:include>
		</div>	
		
		<div class="modal fade" id="dialog-link-area-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			 <jsp:include page="dialogprototype/dialogLinkAreaSetting.jsp"></jsp:include>
		</div>		
		
		<div class="modal fade" id="dialog-button-area-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			  <jsp:include page="dialogprototype/dialogButtonAreaSetting.jsp"></jsp:include>
		</div>	
			
		<div class="modal fade" id="dialog-button-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			 <jsp:include page="dialogprototype/dialogButtonSetting.jsp"></jsp:include>
		</div>		
		
		<div class="modal fade" id="dialog-link-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			 <jsp:include page="dialogprototype/dialogLinkSetting.jsp"></jsp:include>
		</div>	
		
		<div class="modal fade" id="dialog-component-autocomplete-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			<jsp:include page="dialogprototype/dialogComponentAutocompleteSetting.jsp"></jsp:include>
		</div>
				
		<div class="modal fade" id="dialog-component-list-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			 <jsp:include page="dialogprototype/dialogComponentListSetting.jsp"></jsp:include>
		</div>
		
		<div class="modal fade" id="dialog-component-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			 <jsp:include page="dialogprototype/dialogComponentSetting.jsp"></jsp:include>
		</div>
		
		<div class="modal fade" id="dialog-label-setting" tabindex="-1" role="dialog" aria-labelledby="labelSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/dialogLabelSetting.jsp"></jsp:include>
		</div>
			
		<div class="modal fade" id="modal-section-setting" tabindex="-1" role="dialog" aria-labelledby="modalTableSetting" aria-hidden="false">
				<jsp:include page="dialogprototype/modalSectionSetting.jsp"></jsp:include>
				<!-- /.modal-dialog -->
			</div>	
		
		<div class="modal fade" id="modal-action-setting" tabindex="-1" role="dialog" aria-labelledby="modalTableSetting" aria-hidden="false">
				 <jsp:include page="dialogprototype/modalActionSetting.jsp"></jsp:include>
				<!-- /.modal-dialog -->
		</div>						
	
		<div class="modal fade" id="modal-table-setting" tabindex="-1" role="dialog" aria-labelledby="modalTableSetting" aria-hidden="false">
				 <jsp:include page="dialogprototype/modalTableSetting.jsp"></jsp:include>
				<!-- /.modal-dialog -->
			</div>
	
		<div class="modal fade" id="modal-table-list-setting" tabindex="-1" role="dialog" aria-labelledby="modalTableSetting" aria-hidden="false">
				<jsp:include page="dialogprototype/modalTableListSetting.jsp"></jsp:include>
				<!-- /.modal-dialog -->
			</div>	
			<!-- begin section free dialog -->
		<div class="modal fade" id="dialog-link-area-setting-section" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			<jsp:include page="dialogprototype/dialogLinkAreaSettingSection.jsp"></jsp:include>
		</div>		
		
		<div class="modal fade" id="dialog-button-area-setting-section" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			<jsp:include page="dialogprototype/dialogButtonAreaSettingSection.jsp"></jsp:include>
		</div>
	
		<div class="modal fade" id="dialog-component-autocomplete-setting-section" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			<jsp:include page="dialogprototype/dialogComponentAutocompleteSettingSection.jsp"></jsp:include>
		</div>
		
		<div class="modal fade" id="dialog-component-setting-section" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<jsp:include page="dialogprototype/dialogComponentSettingSection.jsp"></jsp:include>
		</div>
		
		<div class="modal fade" id="dialog-component-list-setting-section" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">			
			<jsp:include page="dialogprototype/dialogComponentListSettingSection.jsp"></jsp:include>
		</div>		
		
		<div class="modal fade" id="dialog-label-setting-section" tabindex="-1" role="dialog" aria-labelledby="labelSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/dialogLabelSettingSection.jsp"></jsp:include>
		</div>							
			<!-- end section -->
			
			<!-- begin screen setting -->
		<div class="modal fade" id="dialog-form-parameter" tabindex="-1" role="dialog" aria-labelledby="labelSetting" aria-hidden="true">
				 <jsp:include page="dialogprototype/dialogFormParameter.jsp"></jsp:include>	 
		</div>
		
		<div class="modal fade" id="modal-form-setting" tabindex="-1" role="dialog" aria-labelledby="modalFormSetting" aria-hidden="false">
			<jsp:include page="dialogprototype/modalFormSetting.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modal-event-assign-result" tabindex="-1" role="dialog" aria-labelledby="modalEventParamSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/dialogEventAssignResult.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modal-event-param-setting" tabindex="-1" role="dialog" aria-labelledby="modalEventParamSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/dialogEventSettingParam.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modal-display-param-setting" tabindex="-1" role="dialog" aria-labelledby="modalEventParamSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/modalDisplayParamSetting.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modal-display-param-argument-setting" tabindex="-1" role="dialog" aria-labelledby="modalEventParamSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/modalDisplayParamArgumentSetting.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modal-model-attribute-mapping" tabindex="-1" role="dialog" aria-labelledby="modalModelAttributeMapping" aria-hidden="true">
			<jsp:include page="dialogprototype/modalModelAttributeMapping.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modalTabSetting" tabindex="-1" role="dialog" aria-labelledby="modalTabSetting" aria-hidden="true">
			<jsp:include page="dialogprototype/modalTabSetting.jsp"></jsp:include>		
		</div>
		<div class="modal fade" id="modal-setting-icon" tabindex="-1" role="dialog" aria-labelledby="modalModelAttributeMapping" aria-hidden="true">
			<jsp:include page="dialog/settingIcon.jsp"></jsp:include>		
		</div>
			<!-- end screen setting -->	
			<!-- /.modal -->
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>