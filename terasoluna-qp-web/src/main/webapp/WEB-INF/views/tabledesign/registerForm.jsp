<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.tabledesign"></qp:message></span></li>
         <li><span><qp:message code="sc.tabledesign.0068"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/app/tabledesign/css/advanceSetting.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/tabledesign.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/initData.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/processPK.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/processFK.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/advanceSetting.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/tabledesign/javascript/settingRemarkColumn.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=domaindesign_tabledesign_databasedesign"></script>
		<script>
			$(document).ready(function() {
				var $radios = $('input:radio[name=type]');
				if ($radios.is(':checked') === false) {
					$radios.filter('[value=1]').prop('checked', true);
				}
				var $row = $("#tmp_list_table").find(' tbody tr:first');
				if ($("input[name=flagRegister]").val() != 1) {
					setDefaultDatatype($row);
				}
				<c:forEach items="${reservedWords}" var="item"> 
					listReservedWords.push("${item}");
				</c:forEach>
				//$("#listReservedWords").val(listReservedWords);
			});
			
			
			
		</script>
		<qp:authorization permission="tabledesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/tabledesign/search"><qp:message code="sc.tabledesign.0069" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<script id="tmp_list_table-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex" id="spanItemSeqNo"></td>
				<td>	
					<input id="listTableDesignDetails0.name" name="listTableDesignDetails[0].name" class="form-control qp-input-text qp-convention-db-name-row" type="text" value="" maxlength="200">
					<input id="itemSeqNo" name="listTableDesignDetails[0].itemSeqNo" type="hidden" value="0">
					<input type="hidden" name="listTableDesignDetails[0].indexRow" value="0">
					<input type="hidden" name="listTableDesignDetails[0].keyType" value="00000">
					<input type="hidden" name="listTableDesignDetails[0].binKeyType" value="0">
					<input type="hidden" name="listTableDesignDetails[0].commonColumn" value="0">
				</td>
				<td>
					<input id="listTableDesignDetails0.code" name="listTableDesignDetails[0].code" class="form-control qp-input-text qp-convention-db-code-row out-focus-lower" type="text" value="" maxlength="150">
				</td>
				<td>
					<div class="td-with-setting">
						<select class="form-control qp-input-select" name="listTableDesignDetails[0].dataType" onchange="changeDataType(event)">
							<c:forEach var="item" items="${tableDesignForm.listGroupOfDatatype}" varStatus="status">
								<optgroup label="${item.label}">
									<c:forEach var="dataType" items="${item.listOfDataType}">
										<option primitiveId = "${dataType.primitiveId}" value="${dataType.basetypeId}" baseTypeGroup="${dataType.groupBaseTypeId}" datatypeFlg="${item.datatypeFlg}" codelistCodeAutocomplete="${dataType.codelistCodeAutocomplete}" codelistDefaultAutocomplete="${dataType.codelistDefaultAutocomplete}" sqlCodeAutocomplete="${dataType.sqlCodeAutocomplete}"
												   					maxValue="${dataType.maxValue}" minValue="${dataType.minValue}" validationRule="${dataType.validationRule}" length="${dataType.length}" precision="${dataType.precision}" mandatory="${dataType.mandatory}" 
										remark="${dataType.remark}"	defaultValue="${dataType.defaultValue}"	supportOptionFlg="${dataType.supportOptionFlg}" userDefineValue="${dataType.userDefineValue}" operatorCode="${dataType.operatorCode}" datasourceType="${dataType.datasourceType}" datasourceId="${dataType.datasourceId}" constrainsType="${dataType.constrainsType}" style = "background-color: ${dataType.color}">
												   					${dataType.label}
										<option>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</div>
					<span class="glyphicon glyphicon-cog icon-open-dialog-show icon-advance-setting" title="Advance setting" id="2" data-target="#dialogAutocomplete" onclick="openDialogAutocompleteSetting(this);" ></span>
					<input type="hidden" name="listTableDesignDetails[0].groupBaseTypeId" value=""/>
					<input type="hidden" name="listTableDesignDetails[0].autoIncrementFlag" value="0"/>
					<input type="hidden" name="listTableDesignDetails[0].dataTypeFlg"/>
					<input type="hidden" name="listTableDesignDetails[0].constrainsType" value="0"/>
					<input type="hidden" name="listTableDesignDetails[0].datasourceId"/>
					<input type="hidden" name="listTableDesignDetails[0].datasourceType"/>
					<input type="hidden" name="listTableDesignDetails[0].fmtCode"/>	
					<input type="hidden" name="listTableDesignDetails[0].defaultValue"/>
					<input type="hidden" name="listTableDesignDetails[0].operatorCode"/>
					<input type="hidden" name="listTableDesignDetails[0].minVal"/>
					<input type="hidden" name="listTableDesignDetails[0].maxVal"/>
					<input type="hidden" name="listTableDesignDetails[0].codelistCodeAutocomplete"/>
					<input type="hidden" name="listTableDesignDetails[0].codelistDefaultAutocomplete"/>
					<input type="hidden" name="listTableDesignDetails[0].sqlCodeAutocomplete"/>
					<input type="hidden" name="listTableDesignDetails[0].userDefineValue"/>
					<input type="hidden" name="listTableDesignDetails[0].supportOptionFlg"/>
					<input type="hidden" name="listTableDesignDetails[0].defaultType"/>
					<input type="hidden" name="listTableDesignDetails[0].optionLabel"/>
					<input type="hidden" name="listTableDesignDetails[0].optionValue"/>
					<input type="hidden" name="listTableDesignDetails[0].optionLabelAutocomplete"/>
					<input type="hidden" name="listTableDesignDetails[0].optionValueAutocomplete"/>
				</td>
				<td><input id="listTableDesignDetails0.maxlength" name="listTableDesignDetails[0].maxlength" readonly class="form-control qp-input-integer" type="text" value="" maxlength="200"></td>
				<td><input id="listTableDesignDetails0.decimalPart" name="listTableDesignDetails[0].decimalPart" class="form-control qp-input-integer" type="text" value="" maxlength="200" readonly></td>
				<td align="center"><input id="listTableDesignDetails0.isMandatory" name="listTableDesignDetails[0].isMandatory" class="qp-input-checkbox-margin qp-input-checkbox" type="checkbox" value="1">
				
				</td>
				<td align="center">
					<span class="glyphicon glyphicon-comment icon-open-dialog-show" title="Remark setting" id="2" data-target="#dialogAutocomplete" onclick="openDialogSettingRemarkColumn(this);" ></span>
					<input type="hidden" name="listTableDesignDetails[0].remark">
				</td>
				<td class="sortable"><a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort icon-sort" title="Move"></a></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove table design details" id="deleteRow" onclick="removeRowEx(this);$.qp.removeRowJS('tmp_list_table', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
			</tr>
		</script>
		<script id="tbl_list_Subject-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex">1</td>
				<td>
					<qp:autocomplete 
						name="subjectAreas[].areaId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllSubjectAreaBySubAreaIdForAutocomplete" onChangeEvent="changeTableCodeLabel" arg01="${tableDesignForm.projectId}" value="${subArea.areaId}" mustMatch="true" maxlength="200">
					</qp:autocomplete>
				</td>
				<td><span></span></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJSEx(this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
			</tr>
		</script>
		<form:form action="${pageContext.request.contextPath}/tabledesign/register" method="post" modelAttribute="tableDesignForm">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<%@include file="advanceSetting.jsp" %>
			<%@include file="advanceSettingForDomain.jsp" %>
			<%@include file="settingRemarkColumn.jsp" %>
			<%@include file="settingKeys.jsp" %>
			<%@include file="settingForeignKeys.jsp" %>
			
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.tabledesign.0005" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl-list-Table">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path="tableName"><qp:message code="sc.tabledesign.0019" /><span class="qp-required-field"> (*) </span></form:label></th>
							<td><form:input path="tableName" value="${tableDesignForm.tableName}" cssClass="form-control qp-input-text qp-convention-db-name-row" maxlength="200" autofocus="true"/></td>
							<th><form:label path="tableCode"><qp:message code="sc.tabledesign.0020" /><span class="qp-required-field"> (*) </span></form:label></th>
							<td><form:input path="tableCode" value="${tableDesignForm.tableCode}" cssClass="form-control qp-input-text qp-convention-db-code-row out-focus-lower" maxlength="150" /></td>
						</tr>
						<tr>
							<th><form:label path="type"><qp:message code="sc.sys.0059" /></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_TABLE_TYPE}">
									<label><form:radiobutton path="type" value="${item.key}" cssClass="qp-input-radio-margin qp-input-radio"/>&nbsp;<qp:message code="${CL_TABLE_TYPE.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th><form:label path="designStatus"><qp:message code="sc.sys.0055" /></form:label></th>
							<td><input type="hidden" name="designStatus" value="1"/><qp:message code="${CL_DESIGN_STATUS.get('1')}"  /></td>
							
						</tr>
						<tr>
							<th><qp:message code="sc.tabledesign.0080" /></th>
							<td>
								<input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" id="commonColumnTable" name="commonColumnTable" onclick="processCommonColumn(this)">
								<form:hidden path="commonColumn" value="${tableDesignForm.commonColumn}"/>
							</td>
							<th><form:label path="remark"><qp:message code="sc.tabledesign.0013"/></form:label></th>
							<td><form:textarea path="remark" value="${tableDesignForm.remark}" cssClass="form-control qp-input-textarea" /></td>
						</tr>	
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.subareadesign.0017"></qp:message></span>
				</div>
				<div class="panel-body">
					<table id="tbl_list_Subject"
						class="table table-bordered qp-table-list" data-ar-callback="callback">
						<colgroup>
							<col width="3%">
							<col width="45%">
							<col width="45%">
							<col width="3%">
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.subareadesign.0004" /></th>
								<th><qp:message code="sc.subareadesign.0005" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody class="ui-sortable">
							<c:forEach var="subArea" items="${tableDesignForm.subjectAreas}" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">${status.count}</td>
									<td><qp:autocomplete name="subjectAreas[${status.index}].areaId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllSubjectAreaBySubAreaIdForAutocomplete" value="${subArea.areaId}" displayValue="${subArea.areaIdAutocomplete}" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" onChangeEvent="changeTableCodeLabel" mustMatch="true" maxlength="200"> </qp:autocomplete></td>
									<td><span><qp:formatText value="${subArea.areaCode}"/></span></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_list_Subject', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
					<div>
						<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this);" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.tabledesign.0014" /></span>
				</div>
				<div class="panel-body">
					<table id="tmp_list_table" class="table table-bordered qp-table-list" data-ar-callback="setIndexRow">
						<colgroup>
								<col>
								<col>
								<col width="15%">
								<col width="19%">
								<col width="8%">
								<col width="8%">
								<col width="8%">
								<col width="6%">
								<col width="3%">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004" /></th>
									<th><qp:message code="sc.tabledesign.0021" /><span class="qp-required-field"> (*) </span></th><!-- Column Name -->
									<th><qp:message code="sc.tabledesign.0022" /><span class="qp-required-field"> (*) </span></th><!-- Column Code -->
									<th><qp:message code="sc.tabledesign.0007" /><span class="qp-required-field"> (*) </span></th><!-- Data Type -->
									<th><qp:message code="sc.tabledesign.0008" /><span class="qp-required-field"> (*) </span></th><!-- Max length -->
									<th><qp:message code="sc.tabledesign.0016" /></th><!-- Precision -->
									<th><qp:message code="sc.tabledesign.0017" /></th><!-- Madatory -->
									<th><qp:message code="sc.tabledesign.0013" /></th><!-- Remark -->
									<th></th>
									<th></th>
								</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${tableDesignForm.listTableDesignDetails}" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex" id="spanItemSeqNo">${status.count}</td>
									<td>
										<form:input path="listTableDesignDetails[${status.index}].name" value="${item.name}" cssClass="form-control qp-input-text qp-convention-db-name-row" maxlength="200" />
										<form:hidden path="listTableDesignDetails[${status.index}].itemSeqNo" id="itemSeqNo" value="${item.indexRow}"/>
										<form:hidden path="listTableDesignDetails[${status.index}].keyType" value="${item.keyType}"/>
										<form:hidden path="listTableDesignDetails[${status.index}].binKeyType" value="${item.binKeyType}"/>
										<form:hidden path="listTableDesignDetails[${status.index}].indexRow" value="${item.indexRow }"/>
										<form:hidden path="listTableDesignDetails[${status.index}].commonColumn" value="${item.commonColumn }"/>
									</td>
									<td>
										<form:input path="listTableDesignDetails[${status.index}].code" value="${item.code}" cssClass="form-control qp-input-text qp-convention-db-code-row out-focus-lower" maxlength="150" />
									</td>
									<td>
										<div class="td-with-setting">
									   		<form:select path="listTableDesignDetails[${status.index}].dataType" onchange="changeDataType(event)" class="form-control qp-input-select">
										   		<c:forEach var="typeDisplay" items="${tableDesignForm.listGroupOfDatatype}" >
												   		<optgroup label="${typeDisplay.label}">
												   			<c:forEach var="dataType" items="${typeDisplay.listOfDataType}" >
												   				<form:option primitiveId = "${dataType.primitiveId}" value="${dataType.basetypeId}" baseTypeGroup="${dataType.groupBaseTypeId}" datatypeFlg="${typeDisplay.datatypeFlg}" codelistCodeAutocomplete="${dataType.codelistCodeAutocomplete}" codelistDefaultAutocomplete="${dataType.codelistDefaultAutocomplete}" sqlCodeAutocomplete="${dataType.sqlCodeAutocomplete}"
												   					maxValue="${dataType.maxValue}" minValue="${dataType.minValue}" validationRule="${dataType.validationRule}" length="${dataType.length}" precision="${dataType.precision}" mandatory="${dataType.mandatory}" 
												   					remark="${dataType.remark}"	defaultValue="${dataType.defaultValue}" supportOptionFlg="${dataType.supportOptionFlg}" userDefineValue="${dataType.userDefineValue}" operatorCode="${dataType.operatorCode}" datasourceType="${dataType.datasourceType}" datasourceId="${dataType.datasourceId}" constrainsType="${dataType.constrainsType}" style = "background-color: ${dataType.color}">
												   					${dataType.label}
												   				</form:option>
												   			</c:forEach>
												   		</optgroup>
										   		</c:forEach>
									   		</form:select>
										</div>
									   	<span class="glyphicon glyphicon-cog icon-open-dialog-show icon-advance-setting" title="Advance setting" id="2" data-target="#dialogAutocomplete" onclick="openDialogAutocompleteSetting(this);" ></span>
									   	<form:hidden path="listTableDesignDetails[${status.index}].groupBaseTypeId"/>
									   	<form:hidden path="listTableDesignDetails[${status.index}].autoIncrementFlag"/>
										<form:hidden path="listTableDesignDetails[${status.index}].dataTypeFlg"/>
										<form:hidden path="listTableDesignDetails[${status.index}].constrainsType"/>
										<form:hidden path="listTableDesignDetails[${status.index}].datasourceId"/>
										<form:hidden path="listTableDesignDetails[${status.index}].datasourceType"/>
										<form:hidden path="listTableDesignDetails[${status.index}].fmtCode"/>
										<form:hidden path="listTableDesignDetails[${status.index}].defaultValue"/>
										<form:hidden path="listTableDesignDetails[${status.index}].operatorCode"/>
										<form:hidden path="listTableDesignDetails[${status.index}].minVal"/>
										<form:hidden path="listTableDesignDetails[${status.index}].maxVal"/>
										<form:hidden path="listTableDesignDetails[${status.index}].codelistCodeAutocomplete"/>
										<form:hidden path="listTableDesignDetails[${status.index}].codelistDefaultAutocomplete"/>
										<form:hidden path="listTableDesignDetails[${status.index}].sqlCodeAutocomplete"/>
										<form:hidden path="listTableDesignDetails[${status.index}].userDefineValue"/>
										<form:hidden path="listTableDesignDetails[${status.index}].supportOptionFlg"/>	
										<form:hidden path="listTableDesignDetails[${status.index}].defaultType"/>
										<form:hidden path="listTableDesignDetails[${status.index}].optionLabel"/>
										<form:hidden path="listTableDesignDetails[${status.index}].optionValue"/>
										<form:hidden path="listTableDesignDetails[${status.index}].optionLabelAutocomplete"/>
										<form:hidden path="listTableDesignDetails[${status.index}].optionValueAutocomplete"/>
									</td>
									<td><form:input path="listTableDesignDetails[${status.index}].maxlength" value="${item.maxlength}" cssClass="form-control qp-input-integer" maxlength="5" /></td>
									<td><form:input path="listTableDesignDetails[${status.index}].decimalPart" value="${item.decimalPart}" cssClass="form-control qp-input-integer" maxlength="50" /></td>
									<td align="center"><form:checkbox path="listTableDesignDetails[${status.index}].isMandatory" value="${item.isMandatory}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/>
										<input type="hidden" name="mandatoryFolowKey" value="${item.isMandatory}">
									</td>
									<td align="center">
										<span class="glyphicon glyphicon-comment icon-open-dialog-show" title="Remark setting" id="2" data-target="#dialogAutocomplete" onclick="openDialogSettingRemarkColumn(this);" ></span>
										<form:hidden path="listTableDesignDetails[${status.index}].remark"/>
									</td>
									<td class="sortable"><a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort icon-sort" title="Move"></a></td>
									<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove table design details" id="deleteRow" onclick="removeRowEx(this);$.qp.removeRowJS('tmp_list_table', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="qp-add-left">
						<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" id="addRowTable" onclick="$.qp.addRowJSByLinkEx(this);" href="javascript:void(0)"></a>
						<qp:authorization permission="tabledesignRegister">
							<a title="<qp:message code="sc.tabledesign.0054" />" style="float: right; padding-left:1em; margin-top: 10px; text-decoration:none;" href="javascript:void(0)" data-target="#dialogOption" onclick="showDialogSettingKey();"><span class="glyphicon glyphicon-cog icon-open-dialog-show" data-target="#dialogOption" onclick="showDialog();"></span><qp:message code="sc.tabledesign.0054" /></a>
							<a title="<qp:message code="sc.tabledesign.0055" />" style="float: right; margin-top: 10px; text-decoration:none;" href="javascript:void(0)" data-target="#dialogOption" onclick="showSettingForeignKeys();"><span class="glyphicon glyphicon-cog icon-open-dialog-show" data-target="#dialogOption" onclick="showDialog();"></span><qp:message code="sc.tabledesign.0055" /></a>
						</qp:authorization>
					</div>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="tabledesignRegister">
					<form:hidden path="tableDesignId" />
					<input type="hidden" id="groupBaseTypeTemp">
					<input type="hidden" id="datatypeflgTemp">
					
					<form:hidden path="flagRegister" value="${tableDesignForm.flagRegister}"/>
					<form:hidden path="projectId" value="${tableDesignForm.projectId}"/>
					<button type="submit" id="prepareData" onclick="submitForm" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>

		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>