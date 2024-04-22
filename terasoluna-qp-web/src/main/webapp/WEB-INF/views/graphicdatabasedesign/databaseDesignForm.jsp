<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/css/designDatabase.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=databasedesign_tabledesign"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/domaindesign/javascript/initData.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/oz.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/config.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/sqldesigner.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/bootstrap-tabdrop.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/graphicdatabasedesign/javascript/process.js"></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.graphicdatabasedeisgn"></qp:message></span></li>
		<li><span><qp:message code="sc.databasedesign.0090"/></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<qp:authorization permission="tabledesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/tabledesign/search?init"><qp:message code="tqp.tabledesign" /></a>
		</qp:authorization>
		<qp:authorization permission="viewdesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/viewdesign/search?init"><qp:message code="tqp.viewdesign" /></a>
		</qp:authorization>
		<qp:authorization permission="subjectareaSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/subjectarea/search?init"><qp:message code="tqp.subareadesign" /></a>
		</qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<jsp:include page="dialogRemark.jsp"></jsp:include>
		<form:form method="post" id="formSave" action="${pageContext.request.contextPath}/graphicdatabasedesign/modify" modelAttribute="graphicDbDesignForm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.databasedesign.0044" /></span>
				</div>
				<div class="panel-body">
				<c:if test="${not empty graphicDbDesignForm.projectId}">
					<div class="com-body-panel">
						<div class="tabbable nav-pills input-hidden" style="width:100%;">
							<div class="pull-right form-inline form-group" style="position: relative; z-index: 1;">
								<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
									<qp:authorization permission="graphicdatabasedesignModify">
										<div class="checkbox">
											<label><input type="checkbox" class="showImpactFlag" />
											<qp:message code="sc.sys.0097" /></label>
										</div>
										<button type="submit" class="btn qp-button qp-dialog-confirm" onclick="submitForm">
											<qp:message code="sc.sys.0031" />
										</button>
									</qp:authorization>
								</c:if>
								<form:hidden path="xml" />
								<form:hidden path="subjectAreaId" />
								<form:hidden path="nameMask" />
								<form:hidden path="showImpactFlag" />
								<form:hidden path="updatedDate" />
							</div>
							<ul class="nav nav-tabs">
								<qp:authorization permission="subjectareaRegister">
									<li>
										<a href="javascript:void(0)" class="glyphicon glyphicon-plus" id="0" url="${pageContext.request.contextPath}/subjectarea/register"></a>
									</li>
								</qp:authorization>
								<li ${graphicDbDesignForm.subjectAreaId eq -1 ? 'class="active"':''}>
									<a href="javascript:void(0)" id="-1">
										<qp:message code="sc.databasedesign.0098"/>
									</a>
								</li>
								<c:forEach var="item" items="${areas}" varStatus="look">
									<li ${graphicDbDesignForm.subjectAreaId eq item.areaId? 'class="active"':''}>
										<a href="javascript:void(0)" id="${item.areaId}">
											<qp:formatText value="${item.areaName}"/>
										</a>
									</li>
								</c:forEach>
								<li ${graphicDbDesignForm.subjectAreaId eq -2 ? 'class="active"':''}>
									<a href="javascript:void(0)" id="-2">
										<qp:message code="sc.databasedesign.0118"/>
									</a>
								</li>
							</ul>
						</div>
		
						<div class="designArea" >
							<div id="databaseDesign" class="databaseDesignArea input-hidden">
								<div id="area"></div>
		
								<div id="controls" class="input-hidden">
									<div id="bar">
										<div id="toggle"></div>
		<!-- 									This is blocked by Sonpt - 05/04/15 - Purpose: QP doesn't need those menu items -->
		<!-- 									<input type="button" class="com-button" id="saveload" /> -->
		<!-- 									<hr /> -->
										<input type="button" class="btn qp-button-client input-hidden" id="saveload" />
										<!-- <hr /> -->
		<!-- 							<input type="button" class="com-button" id="saveload" hidden="true"/> -->
										<input type="button" class="btn qp-button-client" id="addtable" /> 
										<input type="button" class="btn qp-button-client" id="edittable" /> 
										<input type="button" class="btn qp-button-client" id="tablekeys" /> 
										<input type="button" class="btn qp-button-client" id="removetable" /> 
										<input type="button" class="btn qp-button-client" id="aligntables" /> 
										<input type="button" class="btn qp-button-client" id="cleartables" />
										<hr />
										<input type="button" class="btn qp-button-client" id="addrow" /> 
										<input type="button" class="btn qp-button-client" id="editrow" />
										<input type="button" class="btn qp-button-client" id="uprow" style="width: 45%;" />
										<input type="button" class="btn qp-button-client" id="downrow" style="width: 45%;" />
										<input type="button" class="btn qp-button-client" id="foreigncreate" />
										<input type="button" class="btn qp-button-client" id="foreignconnect" />
										<input type="button" class="btn qp-button-client" id="foreigndisconnect" />
										<input type="button" class="btn qp-button-client" id="removerow" />
		<!-- 									This is blocked by Sonpt - 05/04/15 - Purpose: QP doesn't need those menu items -->	
		<!-- 									<hr /> -->
		<!-- 									<input type="button" class="com-button" id="options" />  -->
		<!-- 									<a href="javascript:"><input type="button" class="com-button" id="docs" value="" /></a> -->
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
												<td><span id="designStatus"><qp:message code="${CL_DESIGN_STATUS.get('1')}" /></span></td>
											</tr>
											<tr>
												<th><qp:message code="sc.sys.0059" /></th>
												<td>
													<c:forEach var="item" items="${CL_TABLE_TYPE_ALL}">
														<label>
															<input type="radio" name="tableType" id="tableType" value="${item.key}" class="qp-input-radio qp-input-radio-margin" ><qp:message code="${CL_TABLE_TYPE_ALL.get(item.key)}" />
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

										if ($("#isChangeFlg").val() == 0 ) {
											return false;
										}

										$("#xml").val(d.toXML());
										if ($("#xml").val() == "") {
											return false;
										}

										$("#showImpactFlag").val($('.showImpactFlag').prop('checked'));

										/* var url = CONTEXT_PATH + "/graphicdatabasedesign/modify";
										$("#formSave").attr('action', url); */
									}
									
									var CL_DESIGN_STATUS = {}//CL_QP_Validation
									<c:forEach items="${CL_DESIGN_STATUS}" var="item">
										CL_DESIGN_STATUS['${item.key}'] = '<qp:message code="${item.value}" />';
									</c:forEach>
									
									var NAME_MASK = "${graphicDbDesignForm.nameMask}";

									var listReservedWords = [];
									<c:forEach items="${reservedWords}" var="item"> 
									listReservedWords.push("${item}");
									</c:forEach>
									
								</script>
							</div>
						</div>
						<div class="qp-div-action">
							<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
								<qp:authorization permission="graphicdatabasedesignModify">
									<div class="form-inline form-group">
										<div class="checkbox"><label>
											<input type="checkbox" class="showImpactFlag" <c:if test="${graphicDbDesignForm.showImpactFlag}"> checked="checked"</c:if>>
											<qp:message code="sc.sys.0097" /></label></div>
										<button type="submit" class="btn qp-button qp-dialog-confirm" onclick="submitForm"><qp:message code="sc.sys.0031" /></button>
									</div>
								</qp:authorization>
							</c:if>
						</div>
					</div>
				</c:if>
			</div>
		</div>
		<input type="hidden" value="0" id="isChangeFlg" />
		<form:hidden path="mode"/>
		<form:hidden path="projectId"/>
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>