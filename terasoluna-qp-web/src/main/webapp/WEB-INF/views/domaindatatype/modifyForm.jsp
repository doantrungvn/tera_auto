<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">
		<qp:message code="sc.sys.0006"/>&nbsp;<qp:message code="sc.domaindatatype.0000"/>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<qp:authorization permission="domaindatatypeSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/domaindatatype/search"><qp:message code="sc.sys.0001"/>&nbsp;<qp:message code="sc.domaindatatype.0000"/></a>
		</qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=domaindatatype"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/validation.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindatatype/javascript/validator.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindatatype/javascript/process.js" ></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<script id="tableDialogFormOptions-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex">2</td>
				<td class="colOptionName"><input type="text" class="form-control" name="srcgenOptionName" value=""></td>
				<td><input type="text" class="form-control" name="srcgenOptionValue" value=""></td>
				<td>
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tableDialogFormOptions', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>

		<form:form method="post" action="${pageContext.request.contextPath}/domaindatatype/modify" modelAttribute="domainDatatypeForm" id="formDatatype">
			<c:if test="${notExistFlg ne 1}">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="domainDatatypeId"/>
			<form:hidden path="status"/>
			<form:hidden path="projectId"/>
			<form:hidden path="changeDesignFlg"/>
			<form:hidden path="tableDesignId"/>
			<form:hidden path="tableDesignName"/>
			<form:hidden path="projectName"/>
			<form:hidden path="updatedDate" />

			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.domaindatatype.0007"/></span>
				</div>

				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="35%" />
							<col width="15%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><label for="domainDatatypeName"><qp:message code="sc.domaindatatype.0002"/>&nbsp;<span class="qp-required-field">(*)</span></label></th>
							<td class="word-wrap"><form:input path="domainDatatypeName" cssClass="form-control qp-input-text" /></td>
							<th><label for="domainDatatypeCode"><qp:message code="sc.domaindatatype.0003"/>&nbsp;<span class="qp-required-field">(*)</span></label></th>
							<td class="word-wrap"><form:input path="domainDatatypeCode" cssClass="form-control qp-input-text" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.domaindatatype.0021"/></th>
							<td class="word-wrap"><qp:formatText value="${domainDatatypeForm.tableDesignName }"/></td>
							<th><qp:message code="sc.sys.0027"/></th>
							<td class="word-wrap">${CL_DESIGN_STATUS.get(domainDatatypeForm.status.toString())}</td>
						</tr>
						<tr>
							<th><qp:message code="sc.domaindatatype.0025"/></th>
							<td class="word-wrap"><label for="isGenerate">
								<input type="checkbox" name="isGenerate" id="isGenerate" value="1">&nbsp;<qp:message code="sc.domaindatatype.0026"/></label>
							</td>
							<th><label for="remark"><qp:message code="sc.sys.0028"/></label></th>
							<td class="word-wrap"><form:textarea path="remark" cssClass="form-control qp-input-textarea" /></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.domaindatatype.0034"/></span>
					<div style="float: right;"><input type="checkbox" />Screen item setting</div>
				</div>

				<div class="panel-body">
					<table id="tbl_list_domain_items" class="table table-bordered qp-table-list">
						<colgroup>
							<col/>
							<col width="18%" />
							<col width="13%" />
							<col width="16%" />
							<col width="12%" />
							<col width="12%" />
							<col width="17%" />
							<!-- <col width="12%" /> -->
							<col/>
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"/></th>
								<th><qp:message code="sc.domaindatatype.0010"/><span class="qp-required-field">&nbsp;(*)</span></th>
								<th><qp:message code="sc.domaindatatype.0011"/><span class="qp-required-field">&nbsp;(*)</span></th>
								<th><qp:message code="sc.domaindatatype.0012"/></th>
								<th><qp:message code="sc.domaindatatype.0013"/></th>
								<th><qp:message code="sc.domaindatatype.0014"/><span class="qp-required-field">&nbsp;(*)</span></th>
								<th><qp:message code="sc.domaindatatype.0022"/></th>
								<%-- <th><qp:message code="sc.domaindatatype.0023"/></th> --%>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${not empty domainDatatypeForm.domainDatatypeItems}">
							<c:forEach var="item" items="${domainDatatypeForm.domainDatatypeItems}" varStatus="status">
							<c:choose>
								<c:when test="${item.status eq '1'}">
									<tr class="trAppend">
								</c:when>
								<c:when test="${item.status eq '2'}">
									<tr class="trRemove">
								</c:when>
								<c:when test="${item.status eq '3'}">
									<tr class="trChange">
								</c:when>
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
									<td>
										<span id="spanItemSeqNo" data-target="#dialogAdvanceSetting" class="qp-link-popup icon-open-dialog-show" onclick="openDialogAdvanceSetting(this);">${status.count}</span>
										<form:hidden path="domainDatatypeItems[${status.index}].fmtCode" value="${item.fmtCode}"/>
										<form:hidden path="domainDatatypeItems[${status.index}].defaultValue" value="${item.defaultValue}"/>
										<form:hidden path="domainDatatypeItems[${status.index}].itemSeqNo" id="itemSeqNo"/>
										<form:hidden path="domainDatatypeItems[${status.index}].domainDatatypeItemId" />
										<form:hidden path="domainDatatypeItems[${status.index}].requiredFlg" />
										<form:hidden path="domainDatatypeItems[${status.index}].tblDesignDetailsId" />
										<form:hidden path="domainDatatypeItems[${status.index}].physicalDataType" />
										<form:hidden path="domainDatatypeItems[${status.index}].keyType" />
										<form:hidden path="domainDatatypeItems[${status.index}].status"/>
									</td>
									<!-- Name -->
									<td>
										<div class="td-with-primery-key">
											<form:input path="domainDatatypeItems[${status.index}].domainColumnName" cssClass="form-control qp-input-text"/>
										</div>
										<c:if test="${item.isPrimaryKey() eq '1'}">
											<span class="qp-required-field">(PK)</span>
										</c:if>
									</td>
									<!-- Code -->
									<td><form:input path="domainDatatypeItems[${status.index}].domainColumnCode" cssClass="form-control qp-input-text"/></td>
									<!-- Data Type -->
									<td>
										<div class="td-with-setting">
											<form:select path="domainDatatypeItems[${status.index}].domainDataType" cssClass="form-control qp-input-select" onchange="changeDataType(this);">
												<c:if test="${ not empty listOfDomainDesign.get(item.groupBasetypeId) }">
													<c:forEach var="domainItem" items="${listOfDomainDesign.get(item.groupBasetypeId)}">
														<c:if test="${domainItem.maxLength == 0 || item.maxlengthPhysical == 0 || ( domainItem.maxLength > 0 && domainItem.maxLength <= item.maxlengthPhysical)}">
															<form:option value="${domainItem.domainId}" defaultVal="${domainItem.defaultValue}" fmtCode="${domainItem.fmtCode}" minVal="${domainItem.minVal }" maxVal="${domainItem.maxVal }">
																<qp:formatText value="${domainItem.domainName}"/>
															</form:option>
														</c:if>
													</c:forEach>
												</c:if> 

												<!-- If Text or Interger -->
												<c:if test="${item.groupBasetypeId eq '1' || item.groupBasetypeId eq '2'}">
													<c:if test="${item.isPrimaryKey() ne '1'}">
														<form:option value="5"><qp:message code="sc.domaindatatype.0032"/></form:option>
														<form:option value="6"><qp:message code="sc.domaindatatype.0030"/></form:option>
														<form:option value="7"><qp:message code="sc.domaindatatype.0031"/></form:option>
													</c:if>
													<form:option value="0"><qp:message code="sc.domaindatatype.0033"/></form:option>
												</c:if>
												<!-- if Boolean or Char -->
												<c:if test="${item.groupBasetypeId eq '7' || item.groupBasetypeId eq '5'}">
													<form:option value="5"><qp:message code="sc.domaindatatype.0032"/></form:option>
													<form:option value="6"><qp:message code="sc.domaindatatype.0030"/></form:option>
													<form:option value="7"><qp:message code="sc.domaindatatype.0031"/></form:option>
												</c:if>
											</form:select>
										</div>
										<c:choose>
											<c:when test="${item.domainDataType eq '5' || item.domainDataType eq '7' || item.domainDataType eq '6'}">
												<c:if test="${item.groupBasetypeId ne '7'}">
													<span class="glyphicon glyphicon-cog icon-open-dialog-show" data-target="#dialogOption" onclick="openDialogSetting(this);"></span>
												</c:if>
												<c:if test="${item.groupBasetypeId eq '7'}">
													<span id="${item.groupBasetypeId}" class="glyphicon glyphicon-cog icon-open-dialog-show" data-target="#dialogBooleanOption" onclick="openDialogBooleanSetting(this);"></span>
												</c:if>
											</c:when>
											<c:when test="${item.domainDataType eq '0'}">
												<span class="glyphicon glyphicon-cog icon-open-dialog-show" data-target="#dialogAutocomplete" onclick="openDialogAutocompleteSetting(this);"></span>
											</c:when>
											<c:otherwise>
												<span class="glyphicon glyphicon-cog icon-open-dialog-hidden" id="${item.groupBasetypeId}" data-target="#dialogOption" onclick="openDialogSetting(this);"></span>
											</c:otherwise>
										</c:choose>
										<c:if test="${empty item.domainDataType}">
										<!--  -->
										</c:if>

										<form:hidden path="domainDatatypeItems[${status.index}].msgLabel" />
										<form:hidden path="domainDatatypeItems[${status.index}].msgValue"/>
										<form:hidden path="domainDatatypeItems[${status.index}].dataSource"/>
										<form:hidden path="domainDatatypeItems[${status.index}].supportOptionFlg"/>
										<form:hidden path="domainDatatypeItems[${status.index}].codelistType"/>
										<form:hidden path="domainDatatypeItems[${status.index}].onChangeMethod"/>
										<form:hidden path="domainDatatypeItems[${status.index}].onSelectMethod"/>
										<form:hidden path="domainDatatypeItems[${status.index}].autocompleteName"/>
										<form:hidden path="domainDatatypeItems[${status.index}].groupBasetypeId"/>
									</td>
									<!-- Display Type -->
									<td>
										<c:choose>
											<c:when test="${item.requiredFlg eq '1'}"><!-- if is mandatory -->
												<form:select path="domainDatatypeItems[${status.index }].displayType" cssClass="form-control qp-input-select">
													<form:option value="1"><qp:message code="sc.domaindatatype.0027"/></form:option>
													<c:if test="${item.isPrimaryKey() eq '1'}"><!-- if is primary key -->
														<form:option value="4"><qp:message code="sc.domaindatatype.0029"/></form:option>
													</c:if>
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select path="domainDatatypeItems[${status.index }].displayType" cssClass="form-control qp-input-select">
													<form:option value="1"><qp:message code="sc.domaindatatype.0027"/></form:option>
													<form:option value="2"><qp:message code="sc.domaindatatype.0028"/></form:option>
													<form:option value="4"><qp:message code="sc.domaindatatype.0029"/></form:option>
												</form:select>
											</c:otherwise>
										</c:choose>
									</td>
									<!-- Length  -->
									<td>
										<div class="td-with-label">
										<!-- Text OR Integer OR Decimal OR Char OR Currency-->
										<c:choose>
											<c:when test="${item.groupBasetypeId == '1' || item.groupBasetypeId == '2' || item.groupBasetypeId == '3' || item.groupBasetypeId == '5' || item.groupBasetypeId == '6'}">
												<form:input path="domainDatatypeItems[${status.index }].maxlength" cssClass="form-control qp-input-integer" maxlength="5"/>
											</c:when>										
											<c:otherwise>
												<form:input path="domainDatatypeItems[${status.index }].maxlength" cssClass="form-control qp-input-integer" readonly="true"/>
											</c:otherwise>
										</c:choose>
										</div>
										<span><b>(&lt;=${item.maxlengthPhysical })</b></span>
										<form:hidden path="domainDatatypeItems[${status.index}].maxlengthPhysical"/>
									</td>
									<!-- Max-Min  -->
									<td>
										<%-- <form:hidden path="domainDatatypeItems[${status.index}].groupBasetypeId" /> --%>
										<c:choose>
											<c:when test="${item.groupBasetypeId eq '1' || item.groupBasetypeId eq '5' || item.groupBasetypeId eq '7'}">
												<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control qp-input-text input-hidden" readonly="true"/>
												<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control qp-input-text input-hidden" readonly="true"/>
											</c:when>
											<c:when test="${item.groupBasetypeId eq '8'}">
												<div class='input-group date qp-input-from-timepicker pull-left'>
													<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
												</div>
												<div class="qp-separator-from-to">~</div>
												<div class='input-group date qp-input-to-timepicker pull-rigth'>
													<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
												</div>
											</c:when>
											<c:when test="${item.groupBasetypeId eq '2'}">
												<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control qp-input-from-integer pull-left" />
												<div class="qp-separator-from-to">~</div>
												<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control qp-input-to-integer pull-right" />
											</c:when>
											<c:when test="${item.groupBasetypeId eq '4'}">
												<div class='input-group date qp-input-from-datepicker pull-left'>
													<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
												</div>
												<div class="qp-separator-from-to">~</div>
												<div class='input-group date qp-input-to-datepicker pull-rigth'>
													<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
												</div>
											</c:when>
											
											<c:when test="${item.groupBasetypeId eq '6'}">
												<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control qp-input-from-currency pull-left" />
												<div class="qp-separator-from-to">~</div>
												<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control qp-input-to-currency pull-right" />
											</c:when>
											
											<c:when test="${item.groupBasetypeId eq '3'}">
												<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control qp-input-from-float pull-left" />
												<div class="qp-separator-from-to">~</div>
												<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control qp-input-to-float pull-right" />
											</c:when>
											<c:when test="${item.groupBasetypeId eq '9'}">
												<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
													<form:input path="domainDatatypeItems[${status.index }].minVal" cssClass="form-control" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
												</div>
												<div class="qp-separator-from-to">~</div>
												<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
													<form:input path="domainDatatypeItems[${status.index }].maxVal" cssClass="form-control" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
												</div>
											</c:when>
										</c:choose>
									</td>
									<!-- Physical column -->
									<td class="td-word-wrap">
										<qp:formatText value="${item.tblDesignDetailsCode }"/>
										<form:hidden path="domainDatatypeItems[${status.index}].tblDesignDetailsCode"/>
									</td>
									<!-- Sort -->
									<td class="sortable">
										<a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort" title="Move"></a>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty domainDatatypeForm.domainDatatypeItems}">
							<tr>
								<td colspan="9"><qp:message code="inf.sys.0013"/></td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</div>
			</div>

			<c:if test="${not empty domainDatatypeForm.domainDatatypeItems}">
			<div class="qp-div-action">
			<!-- if changeDesignFlg = 0 => Save to DB else must change design -->
				<c:choose>
					<c:when test="${domainDatatypeForm.changeDesignFlg eq '0'}">
						<qp:authorization permission="domaindatatypeModify">
							<button type="button" class="btn qp-button qp-dialog-confirm" name="mode" value="2" onclick="clickSaveButton"><qp:message code="sc.sys.0031" /></button>
						</qp:authorization>
				</c:when>
					<c:otherwise>
						<button type="button" class="btn qp-button" name="mode" value=""><qp:message code="sc.domaindatatype.0053" /></button>
					</c:otherwise>
				</c:choose>
			</div>
			</c:if>
		</c:if>
		</form:form>

		<!-- Dialog for autocomplate -->
		<div class="modal fade" id="dialogAutocomplete" tabindex="-1" role="dialog" aria-labelledby="dialogAutocompleteLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
					<div class="modal-body">
						<div class="panel panel-default qp-div-information">
							<div class="panel-heading">
								<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text">Extra setting</span>
							</div>

							<div class="panel-body">
								<div id="dialog-form-autocomplete-error" align="center"></div>
								
								<table class="table table-bordered qp-table-list" id="tableDialogBooleanOptions">
									<colgroup>
										<col width="20%">
										<col width="80%">
									</colgroup>
									<thead>
									<tr>
											<th>
												Domain type
											</th>
											<td colspan="2">
												<div class="radio">
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													Autocomplete</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													<qp:message code="sc.domaindatatype.0032"/>
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													<qp:message code="sc.domaindatatype.0031"/>
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													<qp:message code="sc.domaindatatype.0030"/>
													</label>
												</div>
											</td>
										</tr>
									</thead>
								</table>
								<br />
								
								
								<table class="table table-bordered qp-table-form" id="tableDialogFormAutocomplete">
									<colgroup>
										<col width="40%" />
										<col width="60%" />
									</colgroup>
									
									<tr>
										<th><qp:message code="sc.domaindatatype.0038"/>&nbsp;<span class="qp-required-field">(*)</span></th>
										<td>
											<qp:autocomplete optionValue="optionValue" selectSqlId="findAllAutocompleteForDomainDatatype" value="" 
												onChangeEvent="setDialogAutocompleteDetail" name="columnAutocomplete" optionLabel="optionLabel" arg02="${domainDatatypeForm.projectId}"/>
										</td>
									</tr>
									<tr>
										<th><qp:message code="sc.domaindatatype.0039"/></th>
										<td id="srcgenAtcTable">&nbsp;</td>
									</tr>
									<tr>
										<th><qp:message code="sc.domaindatatype.0040"/></th>
										<td id="srcgenAtcSearchColumn">&nbsp;</td>
									</tr>
									<tr>
										<th><qp:message code="sc.domaindatatype.0041"/></th>
										<td id="srcgenAtcDisplayColumn">&nbsp;</td>
									</tr>

									<tr>
										<th><qp:message code="sc.domaindatatype.0042"/></th>
										<td id="srcgenAtcSubmitColumn">&nbsp;</td>
									</tr>
									<tr>
										<th><label for="dialogOnChangeEvent"><qp:message code="sc.domaindatatype.0043"/></label></th>
										<td><input type="text" class="form-control qp-input-text" name="dialogOnChangeEvent" maxlength="200"/></td>
									</tr>
									<tr>
										<th><label for="dialogOnSelectEvent"><qp:message code="sc.domaindatatype.0044"/></label></th>
										<td><input type="text" class="form-control qp-input-text" name="dialogOnSelectEvent" maxlength="200"/></td>
									</tr>
								</table>
							</div>
						</div>

						<div class="modal-footer">
							<button type="button" class="btn qp-button-client" onclick="saveAutocomplete();" ><qp:message code="sc.sys.0031" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- dialog option setting  -->
		<div class="modal fade" id="dialogOption" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
					<div class="modal-body">
						<div id="dialog-component-list-setting-codelist" class="tab-pane" style="min-height: 200px">
							<div class="panel panel-default qp-div-information">
								<div class="panel-heading">
									<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
									<span class="pq-heading-text">Extra setting</span>
								</div>
								<div class="panel-body">
								
								<table class="table table-bordered qp-table-list" id="tableDialogBooleanOptions">
									<colgroup>
										<col width="20%">
										<col width="80%">
									</colgroup>
									<thead>
									<tr>
											<th>
												Domain type
											</th>
											<td colspan="2">
												<div class="radio">
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													Autocomplete</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													<qp:message code="sc.domaindatatype.0032"/>
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													<qp:message code="sc.domaindatatype.0031"/>
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													<qp:message code="sc.domaindatatype.0030"/>
													</label>
												</div>
											</td>
										</tr>
									</thead>
								</table>
								<br />
								
									<table class="table table-bordered qp-table-form">
										<colgroup>
											<col width="40%">
											<col width="60%">
										</colgroup>
										
										
										
										<tr>
											<th>
												<qp:message code="sc.screendesign.0155"></qp:message>
											</th>
											<td>
												<div class="radio">
													<label class="radio-inline"><input type="radio" name="localCodelist" value="1" onchange="changeTypeCodeList(this)"><qp:message code="sc.domaindatatype.0059" /></label>
													<label class="radio-inline"><input type="radio" name="localCodelist" value="3" onchange="changeTypeCodeList(this)"><qp:message code="sc.screendesign.0157" /></label>
												</div>
											</td>
										</tr>
										<tr class="codelist-system">
											<th>
												<qp:message code="sc.domaindatatype.0046"/>&nbsp;<span class="qp-required-field">(*)</span>
											</th>
											<td>
												<qp:autocomplete  optionLabel="optionLabel" selectSqlId="autocompleteGetCodeList" arg01="${domainDatatypeForm.projectId}" arg02="20" emptyLabel="sc.sys.0030" optionValue="optionValue" name="codelistSetting" onChangeEvent="selectCodeList"></qp:autocomplete>
											</td>
										</tr>
										<tr class="codelist-system">
											<td colspan="2">
													<table id="tableDialogFormOptionsSystemCodelist" class="table table-bordered qp-table-list">
														<colgroup>
															<col/>
															<col width="50%"/>
															<col width="50%"/>
														</colgroup>
														<thead>
															<tr>
																<th><qp:message code="sc.sys.0004"></qp:message> </th>
																<th>
																	<qp:message code="sc.screendesign.0096"></qp:message>
																	<label class="qp-required-field">(*)</label>
																</th>
																<th>
																	<qp:message code="sc.screendesign.0097"></qp:message>
																	<label class="qp-required-field">(*)</label>
																</th>
															</tr>
														</thead>
														<tbody></tbody>
												</table>
											</td>
										</tr>
										<tr class="codelist-screen">
											<td colspan="2">
												<div class="checkbox">
													<label for="supportOptionValue">
														<input type="checkbox" checked="checked" name="supportOptionValue" id="supportOptionValue" onclick="changeSupportOptionValue(this)" style="margin-top: 1px">
														<b><qp:message code="sc.domaindatatype.0049"/></b>
													</label>
												</div>
											</td>
										</tr>
										<tr class="codelist-screen">
											<td colspan="2">
												<table id="tableDialogFormOptions" class="table table-bordered qp-table-list">
													<thead>
														<tr>
															<th><qp:message code="sc.sys.0004" /> </th>
															<th class="colOptionName">
																<qp:message code="sc.screendesign.0096"/>
																<label class="qp-required-field">(*)</label>
															</th>
															<th>
																<qp:message code="sc.screendesign.0097" />
																<label class="qp-required-field">(*)</label>
															</th>
															<th></th>
														</tr>
													</thead>
													<tbody></tbody>
												</table>
												<div class="qp-add-left">
													<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);changeSupportOptionValue();" style="margin-top: 3px;" href="javascript:void(0)"></a>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<button type="button" class="btn qp-button-client"  onclick="saveDialogSetting();"><qp:message code="sc.sys.0031" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Dialog for boolean setting -->
		<div class="modal fade" id="dialogBooleanOption" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
					<div class="modal-body ">
						<div class="panel panel-default qp-div-information">
							<div class="panel-heading">
								<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text">Extra setting</span>
							</div>

							<div class="panel-body">
								<div id="dialog-boolean-options-error" align="center"></div>
								
								<table class="table table-bordered qp-table-list" id="tableDialogBooleanOptions">
									<colgroup>
										<col width="20%">
										<col width="80%">
									</colgroup>
									<thead>
									<tr>
											<th>
												Domain type
											</th>
											<td colspan="2">
												<div class="radio">
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													Autocomplete</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													<qp:message code="sc.domaindatatype.0032"/>
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													<qp:message code="sc.domaindatatype.0031"/>
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													<qp:message code="sc.domaindatatype.0030"/>
													</label>
												</div>
											</td>
										</tr>
									</thead>
								</table>
								<br />
								<table class="table table-bordered qp-table-list" id="tableDialogBooleanOptions">
									<colgroup>
										<col width="10%">
										<col width="45%">
										<col width="45%">
									</colgroup>
									<thead>
									
										<tr>
											<th><qp:message code="sc.sys.0004"/></th>
											<th class="colOptionName"><qp:message code="sc.domaindatatype.0050"/>&nbsp;<span class="qp-required-field">(*)</span></th>
											<th><qp:message code="sc.domaindatatype.0051"/>&nbsp;<span class="qp-required-field">(*)</span></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td><input type="text" class="form-control" name="srcgenBooleanName" maxlength="200"/></td>
											<td><input type="text" class="form-control com-disable" name="srcgenBooleanValue" maxlength="200" value="TRUE" readonly="readonly"/></td>
										</tr>
										<tr class="notForCheckbox">
											<td>2</td>
											<td><input type="text" class="form-control" name="srcgenBooleanName" maxlength="200"/></td>
											<td><input type="text" class="form-control com-disable" name="srcgenBooleanValue" maxlength="200" value="FALSE" readonly="readonly"/></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn qp-button-client" onclick="saveDialogBooleanSetting();"><qp:message code="sc.sys.0031" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Dialog for advance setting -->
		<div class="modal fade" id="dialogAdvanceSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
					<div class="modal-body">
						<div class="panel panel-default qp-div-information">
							<div class="panel-heading">
								<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.domaindatatype.0054"/></span>
							</div>
							<div class="panel-body" >
								<div id="dialog-boolean-options-error" align="center"></div>
								<table class="table table-bordered qp-table-form" id="tableDialogAdvanceSetting" >
									<colgroup>
										<col width="20%" />
										<col width="40%" />
										<col width="40%" />
									</colgroup>
									<tr>
										<th><qp:message code="sc.domaindesign.0009"/></th>
										<td id="fmtCode" colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<th>Remark</th>
										<td colspan="2">
 											<textarea id="remark" name="remark" class="form-control qp-input-textarea" rows="4">For physical table add new item</textarea>
 										</td>
									</tr>
									
										<tr>
										<th>Default</th>
										<td colspan="2" class="form-inline">
											<!-- <input type="text" class="form-control qp-input"> -->
											<div class='input-group date qp-input-datetimepicker'>
												<input type="text" class="form-control" />
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
											</div>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox">&nbsp;now()
											
										</td>
										<!-- <td></td> -->
									</tr> 
									
									<tr>
										<th>Constrains</th>
										<td colspan="2">
											<select class="form-control qp-input-select">
												<option>--Select--</option>
												<option>Range</option>
												<option selected="selected">Datasource</option>
											</select>
										</td>
									</tr>
									<tr>
										<th>Type of List</th>
										<td colspan="2">
												 <div class="radio">
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="1" onchange="">
													User define</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													Codelist
													</label>
													<label class="radio-inline"><input type="radio" name="localCodelist1" value="3" onchange="">
													SQL builder
													</label>
												</div>
										</td>
									</tr>
									
									<tr>
										<th>Value</th>
										<td >
											<select name="whereForm[0].operatorCode" class="form-control qp-input-select">
												<option value="">-- Select --</option>
												<option value="0">=</option>
												<option value="1">&lt;</option>
												<option value="2">&lt;=</option>
												<option value="3">&gt;</option>
												<option value="4">&gt;=</option>
												<option value="5">&lt;&gt;</option>
												<option value="6">LIKE</option>
												<option value="7">BETWEEN</option>
												
										</select> 
																				</td>
										<td>
											<!-- <input type="text" class="form-control qp-input-from-float pull-left" />
											<div class="qp-separator-from-to">~</div>
											<input type="text" class="form-control qp-input-to-float pull-right" /> -->
											
											<div class='input-group date qp-input-from-datetimepicker-detail pull-left'>
												<input type="text" class="form-control" />
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
											</div>
											<div class="qp-separator-from-to">~</div>
											<div class='input-group date qp-input-to-datetimepicker-detail pull-rigth'>
												<input type="text" class="form-control" />
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
											</div>
											
										</td>
									</tr>
									
									<%-- <tr class="codelist-screen">
									<th></th>
											<td colspan="2">
												<div class="checkbox">
													<label for="supportOptionValue">
														<input type="checkbox" checked="checked" name="supportOptionValue" id="supportOptionValue" onclick="changeSupportOptionValue(this)" style="margin-top: 1px">
														<b><qp:message code="sc.domaindatatype.0049"/></b>
													</label>
												</div>
											</td>
										</tr>
									
									<tr>
									<th></th>
									<td colspan="2">
									<table class="table table-bordered qp-table-list">
										<colgroup>
											<col width="15%" />
											<col />
											<!-- <col width="30%" /> -->
											<col width="10%" />
										</colgroup>
										<thead>
											<tr>
												<th>Default</th>
												<th class="colOptionName"><qp:message code="sc.screendesign.0096" /> <label
													class="qp-required-field">(*)</label></th>
												<th><qp:message code="sc.screendesign.0097" /> <label
													class="qp-required-field">(*)</label></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										<tr>
											<td align="right"><input type="radio"></td>
											<!-- <td class="colOptionName"><input type="text" value="Value 1" class="form-control"></td> -->
											<td><input type="text" value="Value 1" class="form-control"></td>
											<td align="center"><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove table design details" onclick="$.qp.removeRowJS('tmp_list_table', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
										</tr>
										<tr>
											<td align="right"><input type="radio"></td>
											<!-- <td class="colOptionName"><input type="text" value="Value 1" class="form-control"></td> -->
											<td><input type="text" value="Value 1" class="form-control"></td>
											 <td align="center"><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove table design details" onclick="$.qp.removeRowJS('tmp_list_table', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
										</tr>
										</tbody>
										
										</table>
										<div class="qp-add-left">
											<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);changeSupportOptionValue();" style="margin-top: 3px;" href="javascript:void(0)"></a>
										</div>
									</td>
									
									</tr>   --%>
									
									 <tr>
										<th>SQL code</th>
										<td><qp:autocomplete  optionLabel="optionLabel" selectSqlId="autocompleteGetCodeList" 
										mustMatch="false" displayValue="CountryCodelist"
										arg01="${domainDatatypeForm.projectId}" arg02="20" emptyLabel="sc.sys.0030" optionValue="optionValue" name="codelistSetting" onChangeEvent="selectCodeList"></qp:autocomplete>
										</td>
										<td class="form-inline"><a href="#">View SQL</a>&nbsp;&nbsp;&nbsp;<a href="#">Register SQL</a>
										</td>
									</tr>
									
										<%-- <tr>
										<th>Default</th>
										<td >
																						<qp:autocomplete  optionLabel="optionLabel" selectSqlId="autocompleteGetCodeList" 
											mustMatch="false" displayValue="Japan"
											arg01="${domainDatatypeForm.projectId}" arg02="20" emptyLabel="sc.sys.0030" optionValue="optionValue" name="codelistSetting" onChangeEvent="selectCodeList">
											</qp:autocomplete>
											
										</td>
										<td></td>
										
									</tr> --%>
									
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn qp-button-client" onclick="saveDialogAdvanceSetting();"><qp:message code="sc.sys.0031" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</tiles:putAttribute>

</tiles:insertDefinition>