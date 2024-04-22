<tiles:insertDefinition name="layouts">
		<tiles:putAttribute name="breadcrumb">
				 <li><span><qp:message code="tqp.generation"></qp:message></span></li>
				 <li><span><qp:message code="sc.generatesourcecode.0000"/></span></li>
		</tiles:putAttribute>
				
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generateSourceCode/javascript/generateSourceCode.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/generateSourceCode/javascript/initial.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=generatesourcecode"></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/generatesourcecode/generatesourcecode" modelAttribute="generateSourceCodeForm">
			<input type="hidden" id="fileName" value="${fileName}">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generatesourcecode.0001" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl_list_result">
						<colgroup>
							<col width="25%" />
							<col width="75%" />
						</colgroup>
						<tr>
							<th style="text-align: left;">
								<qp:message code="sc.generatesourcecode.0002" />
							</th>
							<th style="text-align: left;">
<%-- 								<qp:message code="sc.project.0012" /> --%>
								<label style="margin-right: 30px">
									<input name="selectType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1" onclick="changeSelectType();">
									<qp:message code="sc.project.0010"/>
								</label>
								<label>
									<input name="selectType" class="qp-input-radio-margin qp-input-radio" type="radio" value="2" onclick="changeSelectType();">
									<qp:message code="sc.generation.0020"/>
								</label>
							</th>
						</tr>
						<tr>
						 	<td rowspan="2" style="vertical-align: top;">
								<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%" id="tblGenerateSetting">
									<colgroup>
										<col width="10%">
										<col width="90%">
									</colgroup>
									<thead>
									</thead>
									<tbody>
										<tr class="success form-inline">
											<td colspan="2" style="text-align: left;">
												<label>
													<input class="qp-input-radio-margin qp-input-radio input-hidden" id="generateType1" type="radio" name="generateType" value="1" checked="checked">
													<qp:message code="sc.generatesourcecode.0005" />
													<a id="generateTypeInfor" style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.generatesourcecode.0091"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
												</label>
											</td>
										</tr>
										<!-- Output source code type -->
										<c:forEach var="rd" items="${generateSourceCodeForm.generateSourceCodeItemLst}" varStatus="loop">
											<tr align="left" class="form-inline">
												<td></td>
												<td class="com-output-text">
													<c:choose>
														<c:when test="${rd.isChecked}">
															<label>
																<input name="generateTypeItem" class="qp-input-radio-margin qp-input-radio input-hidden" type="checkbox" checked="checked" value="${rd.sourceCodeItemKey}">&nbsp;
																${rd.sourceCodeItemTemplateName}
															</label>
														</c:when>
														<c:otherwise>
															<label>
																<input name="generateTypeItem" class="qp-input-radio-margin qp-input-radio input-hidden" type="checkbox" value="${rd.sourceCodeItemKey}">&nbsp;
																${rd.sourceCodeItemTemplateName}
															</label>
														</c:otherwise>
													</c:choose>
												</td>
												<form:hidden path="generateSourceCodeItemLst[${loop.index}].sourceCodeItemKey" value="${rd.sourceCodeItemKey}"/>
												<form:hidden path="generateSourceCodeItemLst[${loop.index}].sourceCodeItemTemplateName" value="${rd.sourceCodeItemTemplateName}"/>
												<form:hidden path="generateSourceCodeItemLst[${loop.index}].isChecked" value="${rd.isChecked}"/>
											</tr>
										</c:forEach>
									</tbody>
								</table>
						 	</td>
							<td style="vertical-align: top;">
								<c:choose>
									<c:when test="${generateSourceCodeForm.scopeGenerateSource eq 1}">
										<table class="table table-bordered qp-table-form" style="width: 100%; vertical-align: top;" id="tblProject">
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tr>
												<th><qp:message code="sc.project.0005" /></th>
												<td><form:label path="project.projectName"><qp:formatText value="${generateSourceCodeForm.project.projectName}"/></form:label>
													<form:hidden path="project.projectId" value="${generateSourceCodeForm.project.projectId}"/>
													<form:hidden path="project.projectName" value="${generateSourceCodeForm.project.projectName}"/>
													<form:hidden path="project.packageName" value="${generateSourceCodeForm.project.packageName}"/>
												</td>
												<th><qp:message code="sc.project.0006" /></th>
												<td><form:label path="project.projectCode"><qp:formatText value="${generateSourceCodeForm.project.projectCode}" /></form:label>
													<form:hidden path="project.projectCode" value="${generateSourceCodeForm.project.projectCode}"/>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.sys.0055"></qp:message></th>
												<td><form:hidden path="project.status" value="${generateSourceCodeForm.project.status}"/>
													<form:label path="project.status"><qp:message code="${CL_DESIGN_STATUS.get(generateSourceCodeForm.project.status.toString())}"/></form:label>
												</td>
												<th><qp:message code="sc.sys.0028"/></th>
												<td><form:label path="project.remark"><qp:formatText value="${generateSourceCodeForm.project.remark}" /></form:label>
													<form:hidden path="project.remark" value="${generateSourceCodeForm.project.remark}"/>
												</td>
											</tr>
										</table>
										<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%; vertical-align: top;display: none;" id="tblModule">
											<colgroup>
												<col width="45%">
												<col width="35%">
												<col width="15%">
												<col width="15%">
											</colgroup>
											<thead>
												<tr>
													<th style="text-align: left;"><qp:message code="sc.module.0007" /></th>
													<th><qp:message code="sc.module.0008" /></th>
													<th><qp:message code="sc.module.0034" /></th>
													<th><qp:message code="sc.sys.0055" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="item" items="${generateSourceCodeForm.modules }" varStatus="rowStatus" >
													<tr align="left" class="form-inline">
														<td style="text-align: left;" class="com-output-text">
															<c:choose>
																<c:when test="${item.selectedGenerate == 1}">
																	<label>
																		<input class="qp-input-radio-margin qp-input-radio" id="${item.moduleId }" name="module.moduleId" type="checkbox"  checked="checked" value="${item.moduleId }" scope="module_item">&nbsp;
																		<qp:formatText value="${item.moduleName}"/>
																	</label>
																</c:when>
																<c:otherwise>
																	<label>
																		<input class="qp-input-radio-margin qp-input-radio" id="${item.moduleId }" name="module.moduleId" type="checkbox" value="${item.moduleId }" scope="module_item">&nbsp;
																		<qp:formatText value="${item.moduleName}"/>
																	</label>
																</c:otherwise>
															</c:choose>
															<input type="hidden" name="modules[${rowStatus.index}].moduleId" value="${item.moduleId }"/>
															<input type="hidden" name="modules[${rowStatus.index}].moduleName" value="${item.moduleName }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].remark" value="${item.remark }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].selectedGenerate" value="${item.selectedGenerate }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].moduleCode" value="${item.moduleCode }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].status" value="${item.status }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].moduleType" value="${item.moduleType }"/>
                                                                                                <input type="hidden" name="modules[${rowStatus.index}].author" value="${item.author }"/>
														</td>
														<td><label><qp:formatText value="${item.moduleCode}"/></label></td>
														<td><label><qp:message code="${CL_FUNCTION_DESIGN_TYPE.get(item.moduleType.toString())}" /></label></td>
														<td align="left"><qp:message code="${CL_DESIGN_STATUS.get(item.status.toString())}"/></td>
													</tr>
												</c:forEach>
												<c:if test="${empty generateSourceCodeForm.modules}">
													<tr>
														<td colspan="4"><qp:message code="inf.sys.0013"/></td>
													</tr>
												</c:if>
											</tbody>
										</table>
									</c:when>
									<c:otherwise>
										<table class="table table-bordered qp-table-form hidden" style="width: 100%; vertical-align: top; display: none;" id="tblProject">
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tr>
												<th><qp:message code="sc.project.0005" /></th>
												<td><form:label path="project.projectName"><qp:formatText value="${generateSourceCodeForm.project.projectName}"/></form:label>
													<form:hidden path="project.projectId" value="${generateSourceCodeForm.project.projectId}"/>
													<form:hidden path="project.projectName" value="${generateSourceCodeForm.project.projectName}"/>
													<form:hidden path="project.packageName" value="${generateSourceCodeForm.project.packageName}"/>
												</td>
												<th><qp:message code="sc.project.0006" /></th>
												<td><form:label path="project.projectCode"><qp:formatText value="${generateSourceCodeForm.project.projectCode}" /></form:label>
													<form:hidden path="project.projectCode" value="${generateSourceCodeForm.project.projectCode}"/>
												</td>
											</tr>
											<tr>
												<th><qp:message code="sc.sys.0055"></qp:message></th>
												<td><form:hidden path="project.status" value="${generateSourceCodeForm.project.status}"/>
													<form:label path="project.status"><qp:message code="${CL_DESIGN_STATUS.get(generateSourceCodeForm.project.status.toString())}"/></form:label>
												</td>
												<th><qp:message code="sc.sys.0028"/></th>
												<td><form:label path="project.remark"><qp:formatText value="${generateSourceCodeForm.project.remark}" /></form:label>
													<form:hidden path="project.remark" value="${generateSourceCodeForm.project.remark}"/>
												</td>
											</tr>
										</table>
										<table class="table table-bordered qp-table-list-none-action pull-right" style="width: 100%; vertical-align: top;" id="tblModule">
											<colgroup>
												<col width="35%">
												<col width="35%">
												<col width="30%">
											</colgroup>
											<thead>
												<tr>
													<th style="text-align: left;"><qp:message code="sc.module.0007" /></th>
													<th><qp:message code="sc.module.0008" /></th>
													<th><qp:message code="sc.sys.0055" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="item" items="${generateSourceCodeForm.modules }" varStatus="rowStatus" >
													<tr align="left" class="form-inline">
														<td style="text-align: left;" class="com-output-text"><label>
															<input class="qp-input-radio-margin qp-input-radio" id="${item.moduleId }" name="module.moduleId" type="radio" value="${item.moduleId }" scope="module_item">&nbsp;<qp:formatText value="${item.moduleName}"/></label>
															<input type="hidden" name="modules[${rowStatus.index}].moduleId" value="${item.moduleId }"/>
															<input type="hidden" name="modules[${rowStatus.index}].moduleName" value="${item.moduleName }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].remark" value="${item.remark }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].selectedGenerate" value="${item.selectedGenerate }"/>
																								<input type="hidden" name="modules[${rowStatus.index}].moduleCode" value="${item.moduleCode }"/>
														</td>
														<td><qp:formatText value="${item.moduleCode}"/></td>
														<td align="left"><qp:message code="${CL_DESIGN_STATUS.get(item.status.toString())}"/></td>
														
													</tr>
												</c:forEach>
												<c:if test="${empty generateSourceCodeForm.modules}">
													<tr>
														<td colspan="4"><qp:message code="inf.sys.0013"/></td>
													</tr>
												</c:if>
											</tbody>
										</table>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="generationGeneratesourcecode">
					<button type="submit" class="btn qp-button qp-dialog-confirm" data-confirm-pcallback="$.qp.initial.validateBeforeSubmit" messageId="inf.sys.0025" name="genAll" value="false"><qp:message code="sc.sys.0024"/></button>
				<%-- <button type="submit" class="btn qp-button qp-dialog-confirm" data-confirm-pcallback="" messageid="inf.sys.0025" name="genAll" value="true"><qp:message code="sc.generatesourcecode.0089"/></button> --%>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>