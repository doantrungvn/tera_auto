<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/css/designDatabase.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=databasedesign_tabledesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/initData.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/oz.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/config.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generate/javascript/genDbAndBLogic/sqldesigner.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generate/javascript/genDbAndBLogic/process.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/process.js"></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.generation"></qp:message></span></li>
		<li><span><qp:message code="sc.generation.0003" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-name">	
		<qp:message code="sc.generation.0003" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<jsp:include page="../graphicdatabasedesign/dialogRemark.jsp"></jsp:include>
		<form:form method="post" id="formSave" action="${pageContext.request.contextPath}/generation/generateConfirm" modelAttribute="graphicDbDesignForm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.databasedesign.0044" /></span>
				</div>
				<div class="panel-body">
				<%-- <c:if test="${not empty graphicDbDesignForm.projectId}"> --%>
					<div class="com-body-panel">
						<div class="designArea" >
							<div id="databaseDesign" class="databaseDesignArea input-hidden">
								<form:hidden path="xml" />
								<form:hidden path="nameMask"/>
								<form:hidden path="subjectAreaId" />
								<form:hidden path="moduleId"/>
								<div id="area"></div>

								<div id="controls" class="input-hidden">
									<div id="bar">
										<div id="toggle"></div>

										<input type="button" class="btn qp-button-client input-hidden" id="saveload" />
										<!-- <hr /> -->

										<input type="button" class="btn qp-button-client input-hidden" id="addtable" /> 
										<input type="button" class="btn qp-button-client" id="edittable" /> 
										<input type="button" class="btn qp-button-client" id="tablekeys" /> 
										<input type="button" class="btn qp-button-client input-hidden" id="removetable" /> 
										<input type="button" class="btn qp-button-client" id="aligntables" /> 
										<input type="button" class="btn qp-button-client input-hidden" id="cleartables" />
										<hr />
										<input type="button" class="btn qp-button-client input-hidden" id="addrow" /> 
										<input type="button" class="btn qp-button-client" id="editrow" />
										<input type="button" class="btn qp-button-client" id="uprow" style="width: 45%;" />
										<input type="button" class="btn qp-button-client" id="downrow" style="width: 45%;" />
										<input type="button" class="btn qp-button-client" id="foreigncreate" />
										<input type="button" class="btn qp-button-client" id="foreignconnect" />
										<input type="button" class="btn qp-button-client" id="foreigndisconnect" />
										<input type="button" class="btn qp-button-client input-hidden" id="removerow" />
										<input type="button" id="options" hidden="true" />  
											<a href="javascript:"  hidden="true"><input type="button" class="com-button" id="docs" value="" /></a>
									</div>
									<div id="rubberband"></div>
									<div id="minimap"></div>
									<div id="background"></div>
									<div class="modal-dialog" id="window">
										<div class="modal-content">
											<button id="windowcancel" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<div class="modal-body close-margin">
												<div class="panel panel-default qp-div-information">
													<div class="panel-heading">
														<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
														<span class="pq-heading-text" id="windowtitle" >
														<img id="throbber" />
														</span>
													</div>
													<div class="panel-body" id="windowcontent">
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn qp-button-client" id="windowok"> <qp:message code="sc.sys.0054" /></button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- #controls -->
								<div id="opts" class="input-hidden">
									<table class="table table-bordered qp-table-form" >
										<tbody>
											<tr>
												<td>*<label id="language" for="optionlocale"></label></td>
												<td><select id="optionlocale"><option></option></select></td>
											</tr>
											<tr>
												<td>*<label id="db" for="optiondb"></label></td>
												<td><select id="optiondb"><option></option></select></td>
											</tr>
											<tr>
												<td><label id="snap" for="optionsnap"></label></td>
												<td><input type="text" size="4" id="optionsnap" /> <span class="small" id="optionsnapnotice"></span></td>
											</tr>
											<tr>
												<td><label id="pattern" for="optionpattern"></label></td>
												<td><input type="text" size="6" id="optionpattern" /> <span class="small" id="optionpatternnotice"></span></td>
											</tr>
											<tr>
												<td><label id="hide" for="optionhide"></label></td>
												<td><input type="checkbox" id="optionhide" /></td>
											</tr>
											<tr>
												<td>* <label id="vector" for="optionvector"></label></td>
												<td><input type="checkbox" id="optionvector" /></td>
											</tr>
											<tr>
												<td>* <label id="showsize" for="optionshowsize"></label></td>
												<td><input type="checkbox" id="optionshowsize" /></td>
											</tr>
											<tr>
												<td>* <label id="showtype" for="optionshowtype"></label></td>
												<td><input type="checkbox" id="optionshowtype" /></td>
											</tr>
										</tbody>
									</table>
		
									<hr />
									* <span class="small" id="optionsnotice"></span>
								</div>
		
								<div id="io" style="width: 620px;" class="input-hidden">
									<table class="table table-bordered qp-table-form">
										<tbody>
											<tr>
												<td>
													<fieldset>
														<legend id="client"></legend>
														<input type="button" id="clientsave" class="btn qp-button-client" style="font-size: 12px;" /> 
														<input type="button" id="clientload" class="btn qp-button-client" style="font-size: 12px;" /> 
														<br/>
														<input type="button" id="clientlocalsave" class="btn qp-button-client" style="font-size: 12px; margin-top: 5px;" />
														<input type="button" id="clientlocalload" class="btn qp-button-client" style="font-size: 12px; margin-top: 5px;" />
														<hr />
														<input type="button" id="clientsql" class="btn qp-button-client" style="font-size: 12px;" />
													</fieldset>
												</td>
												<td>
													<fieldset>
														<legend id="server"></legend>
														<label for="backend" id="backendlabel"></label> 
														<select id="backend"><option></option></select>
														<hr />
														<input type="button" id="serversave" class="btn qp-button-client" style="font-size: 12px;"/> 
														<input type="button" id="quicksave" class="btn qp-button-client" style="font-size: 12px;" /> 
														<input type="button" id="serverload" class="btn qp-button-client" style="font-size: 12px;" /> 
														<br/>
														<input type="button" id="serverlist" class="btn qp-button-client" style="font-size: 12px; margin-top: 5px;" /> 
														<input type="button" id="serverimport" class="btn qp-button-client" style="font-size: 12px; margin-top: 5px;" />
													</fieldset>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<fieldset>
														<legend id="output"></legend>
														<textarea id="textarea" rows="1" cols="1"></textarea>
														<!--modified by javascript later-->
													</fieldset>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
				
								<div id="keys" class="input-hidden">
									<fieldset>
										<legend id="keyslistlabel"></legend>
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="50%" />
												<col width="50%" />
											</colgroup>
											<tbody>
												<tr>
													<td><select id="keyslist" class="form-control qp-input-select"><option></option></select></td>
													<td>
														<input type="button" id="keyadd" class="btn qp-button-client" style="font-size: 12px;" /> 
														<input type="button" id="keyremove" class="btn qp-button-client" style="font-size: 12px;" />
													</td>
												</tr>
											</tbody>
										</table>
									</fieldset>
		
									<fieldset class="input-hidden" id = "areaKey">
										<legend id="keyedit"></legend>
										<table class="table table-bordered qp-table-form ">
											<colgroup>
												<col width="40%" />
												<col width="20%" />
												<col width="40%" />
											</colgroup>
											<tbody>
												<tr>
													<td>
														<label for="keytype" id="keytypelabel" ></label>
														<select id="keytype" class="form-control qp-input-select"><option></option></select>
													</td>
													<td></td>
													<td>
														<label for="keyname" id="keynamelabel"></label>&nbsp;<span class="qp-required-field" >(*)</span>
														<input type="text" id="keyname" size="10" class="form-control qp-input-text out-focus-lower" />
													</td>
												</tr>
												<tr>
													<td colspan="3"><hr />
													</td>
												</tr>
												<tr>
													<td>
														<label for="keyfields" id="keyfieldslabel"></label><br />
														<select id="keyfields" size="5" multiple="multiple" class="form-control qp-input-select"><option></option></select>
													</td>
													<td>
														<input type="button" id="keyleft" value="&lt;&lt;" class="btn qp-button-client" style="font-size: 12px;" /><br />
														<input type="button" id="keyright" value="&gt;&gt;" class="btn qp-button-client" style="font-size: 12px; margin-top: 5px;" /><br />
													</td>
													<td>
														<label for="keyavail" id="keyavaillabel"></label><br />
														<select id="keyavail" size="5" multiple="multiple" class="form-control qp-input-select"><option></option></select>
													</td>
												</tr>
											</tbody>
										</table>
									</fieldset>
								</div>
								<div id="table" class="panel-body input-hidden">
									<table class="table table-bordered qp-table-form">
										<colgroup>
											<col width="30%" />
											<col width="70%" />
										</colgroup>
										<tbody>
											<tr hidden="true">
												<td colspan="2">
													<input id="tableid" type="text" class="form-control qp-input-text" />
													<input id="updateddate" type="text" class="form-control qp-input-text" />
													<input id="createddate" type="text" class="form-control qp-input-text" />
													<input id="updatedby" type="text" class="form-control qp-input-text" />
													<input id="createdby" type="text" class="form-control qp-input-text" />
												</td>
											</tr>
											<tr>
												<th><label id="tablenamelabel" for="tablename"></label>&nbsp;<span class="qp-required-field">(*)</span></th>
												<td><input id="tablename" type="text" class="form-control qp-input-text qp-convention-db-name" data-index="1"/></td>
											</tr>
											<tr>
												<th><label id="tablecodelabel" for="tablecode"></label>&nbsp;<span class="qp-required-field">(*)</span></th>
												<td><input id="tablecode" type="text" class="form-control qp-input-text qp-convention-db-code out-focus-lower" data-index="2"/></td>
											</tr>
											<tr>
												<th><qp:message code="sc.sys.0055" /></th>
												<td><span id="designStatus"><qp:message code="${CL_DESIGN_STATUS.get('1')}"  /></span></td>
											</tr>
											<tr>
												<th><qp:message code="sc.sys.0059" /></th>
												<td>
													<c:forEach var="item" items="${CL_TABLE_TYPE}">
														<label>
															<input type="radio" name="tableType" id="tableType" value="${item.key}" class="qp-input-radio qp-input-radio-margin" ><qp:message code="${CL_TABLE_TYPE.get(item.key)}" />
														</label><br>
													</c:forEach>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.tabledesign.0080" /></th>
												<td>
													<input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" id="usedCommonColumn" name="usedCommonColumn" value="1"/>
												</td>
											</tr>
											<tr>
												<th><label id="tablecommentlabel" for="tablecomment"></label></th>
												<td><textarea rows="5" cols="40" id="tablecomment" class="form-control qp-input-textarea" data-index="3"></textarea></td>
											</tr>
										</tbody>
									</table>
								</div>
								<script type="text/javascript">
									var d = new SQL.Designer($("#xml").val());
									
									showData();
									
									function submitForm() {
										$("#xml").val(d.toXML());
										if ($("#xml").val() == "") {
											return false;
										}
									}
									
									var CL_DESIGN_STATUS = {}//CL_QP_Validation
									<c:forEach items="${CL_DESIGN_STATUS}" var="item">
										CL_DESIGN_STATUS['${item.key}'] = '<qp:message code="${item.value}" />';
									</c:forEach>
									
									var NAME_MASK = "${graphicDbDesignForm.nameMask}"
									
									var listReservedWords = [];
									<c:forEach items="${reservedWords}" var="item"> 
									listReservedWords.push("${item}");
									</c:forEach>
									
								</script>
							</div>
						</div>
				</div>
				<%-- </c:if> --%>
			</div>
		</div>

		<div class="panel panel-default qp-div-search-result">
			<div class="panel-heading">
				<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
				<span class="qp-heading-text"><qp:message code="sc.businesslogicdesign.0008" /></span>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered qp-table-list-none-action">
					<colgroup>
						<col />
						<col />
						<col width="20%" />
						<col width="10%" />
						<col width="25%" />
						<col width="10%" />
					</colgroup>
					<thead>
						<tr>
							<th><qp:message code="sc.sys.0004" /></th>
							<th><qp:columnSort colName="business_logic_name" colCode="sc.businesslogicdesign.0005" form="businessDesignSearchForm"></qp:columnSort></th>
							<th><qp:columnSort colName="business_logic_code" colCode="sc.businesslogicdesign.0006" form="businessDesignSearchForm"></qp:columnSort></th>
							<th><qp:columnSort colName="return_type" colCode="sc.businesslogicdesign.0023" form="businessDesignSearchForm"></qp:columnSort></th>
							<th><qp:columnSort colName="screen_id" colCode="sc.businesslogicdesign.0047" form="businessDesignSearchForm"></qp:columnSort></th>
							<th><qp:columnSort colName="design_status" colCode="sc.businesslogicdesign.0020" form="businessDesignSearchForm"></qp:columnSort></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="blogic" items="${graphicDbDesignForm.lstBlogic}" varStatus="status">
								<tr>
									<td>
										<qp:formatInteger value="${status.count}" />
									</td>
									<td>
										<qp:formatText value="${blogic.businessLogicName}" />
										<input type="hidden" name="lstBlogic[${status.index}].businessLogicName" value="${blogic.businessLogicName}">
									</td>
									<td>
										<qp:formatText value="${blogic.businessLogicCode}"/> 
										<input type="hidden" name="lstBlogic[${status.index}].businessLogicCode" value="${blogic.businessLogicCode}">
									</td>
									<td>
										<qp:message code="${CL_RETURN_TYPE.get(blogic.returnType.toString())}"/>
										<input type="hidden" name="lstBlogic[${status.index}].returnType" value="${blogic.returnType}">
									</td>
									<td>
										<qp:formatText value="${blogic.screenIdAutocomplete}"/>
										<input type="hidden" name="lstBlogic[${status.index}].screenIdAutocomplete" value="${blogic.screenIdAutocomplete}">
									</td>
									<td>
										<qp:message code="${CL_DESIGN_STATUS.get(blogic.designStatus.toString())}"></qp:message>
										<input type="hidden" name="lstBlogic[${status.index}].designStatus" value="${blogic.designStatus}">
									</td>
								</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<%--<input type="hidden" name="formJson" value="${f:h(formJson)}" />  --%>
			<form:hidden path="jSonString"/>
		</div>

		<div class="qp-div-action">
			<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
				<qp:authorization permission="graphicdatabasedesignModify">
					<button type="submit" class="btn qp-button" name="jsonBack"><qp:message code="sc.sys.0049" /></button>
					<button type="submit" class="btn qp-button qp-dialog-confirm" onclick="submitForm"><qp:message code="sc.generation.0008" /></button>
				</qp:authorization>
			</c:if>
		</div>
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>