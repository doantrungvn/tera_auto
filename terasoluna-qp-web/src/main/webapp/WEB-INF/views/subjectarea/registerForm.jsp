<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.subareadesign"></qp:message></span></li>
		 <li><span><qp:message code="sc.subareadesign.0018"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="subjectareaSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		  <a href="${pageContext.request.contextPath}/subjectarea/search"><qp:message code="sc.subareadesign.0001"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<link href="${pageContext.request.contextPath}/resources/app/subjectarea/css/style.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">
			var positionAreaId ="";
				<c:forEach items="${subjectAreaForm.positionLst }"  var="position" >
				if("${subjectAreaForm.itemSeqNo}" == "${position.itemSeqNo}"){
					positionAreaId = "${position.areaId}";
				}
				</c:forEach>
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/subjectarea/javascript/subjectarea.js" ></script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<script id="tmp_list_keyword-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex"></td>
				<td>
					<input type="hidden" name="keywordLst[].keywordId">
					<input type="text" name="keywordLst[].keyword" class="form-control qp-input-text" maxlength="200"/></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' 
						onclick="$.qp.removeRowJS('tmp_list_keyword', this);" href="javascript:void(0)"></a></td>
			</tr>
		</script>

		<script id="tmp_list_table-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex"></td>
				<td style="vertical-align:top">
					<div class="input-group" style="width:100%">
					<input type="text" name="tableLst[].tableDesignIdAutocomplete" id="tableLst[].tableDesignIdAutocompleteId" class="form-control qp-input-autocomplete"
					 optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignByProjectId" minLength = "0"
					 onChangeEvent="changeTableCodeLabel" arg02="${subjectAreaForm.projectId}" mustMatch="true" maxlength="200"
					 placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
					 <input type="hidden" name="tableLst[].tableDesignId">
					</div>
				</td>
				<td><span></span></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' 
						onclick="$.qp.removeRowJS('tmp_list_table', this);" href="javascript:void(0)"></a></td>
			 </tr>
		</script>

		<form:form method="post" modelAttribute="subjectAreaForm" action="${pageContext.request.contextPath}/subjectarea/register"
			style="width: 100%;">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="registerFlag" value="0"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.subareadesign.0011"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th>
								<form:label path="areaName">
									<qp:message code="sc.subareadesign.0004"/>&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029"/></span>
								</form:label>
							</th>
							<td>
								<form:input type="text" cssClass="form-control qp-input-text qp-convention-name-row" path="areaName" maxlength="200"/></td>
							<th>
								<form:label path="areaCode">
									<qp:message code="sc.subareadesign.0005"/>&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029"/></span>
								</form:label>
							</th>
							<td>
								<form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="areaCode" maxlength="200"/></td>
						</tr>
						<tr>
							<th><label title="<qp:message code="sc.subareadesign.0021" />"><qp:message code="sc.subareadesign.0009"/></label></th>
							<td>
								<c:forEach var="item" items="${CL_SUPPORT_OPTION_VALUE_FLAG}">
									<label><form:radiobutton path="defaultFlg" value="${item.key}" />&nbsp;<qp:message code="${CL_SUPPORT_OPTION_VALUE_FLAG.get(item.key)}" /></label><br>
								</c:forEach>
							 </td>
							 <th><form:label path="remark"><qp:message code="sc.sys.0028"/></form:label></th>
							<td><form:textarea path="remark" cssClass="form-control qp-input-textarea" rows="3" maxlength="2000"/></td>
							<td colspan="2"></td>
						</tr>
						<tr>
							<th><qp:message code="sc.subareadesign.0012"></qp:message></th>
							<td colspan="4">
								<table class="table table-bordered qp-table-list"  id="tbl_list_post">
									<colgroup>
										<col />
										<col />
										<col width="30%"/>
										<col width="10%"/>
										<col />
									</colgroup>
									<thead>
										<tr>
											<th><qp:message code="sc.sys.0004"></qp:message></th>
											<th><qp:message code="sc.subareadesign.0004"></qp:message></th>
											<th><qp:message code="sc.subareadesign.0005"></qp:message></th>
											<th><qp:message code="sc.subareadesign.0009"></qp:message></th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="position" items="${subjectAreaForm.positionLst }" varStatus="status">
											<c:if test="${subjectAreaForm.itemSeqNo == position.itemSeqNo}">
												<tr class="trChange">
													<td class="qp-output-fixlength tableIndex">
														<span id="stt">${status.index + 1}</span>
														<input type="hidden" name="arrIndex" value="${status.index + 1}">
														<input type="hidden" name="positionLst[${status.index}].areaId">
														<input type="hidden" name="positionLst[${status.index}].itemSeqNo" class="itemPos">
														<input type="hidden" name="positionLst[${status.index}].areaName">
														<input type="hidden" name="positionLst[${status.index}].areaCode">
														<input type="hidden" name="positionLst[${status.index}].defaultFlg">
													</td>
													<c:if test="${empty position.areaName}">
														<td><qp:message code="sc.subareadesign.0013"></qp:message></td>
													</c:if>
													<c:if test="${not empty position.areaName}">
														<td><qp:formatText value="${position.areaName}"/></td>
													</c:if>
													<c:if test="${empty position.areaCode}">
														<td><qp:message code="sc.subareadesign.0014"></qp:message></td>
													</c:if>
													<c:if test="${not empty position.areaCode}">
														<td><qp:formatText value="${position.areaCode}"/></td>
													</c:if>
													<td>
														<c:if test="${position.defaultFlg eq 1}"><span class="glyphicon glyphicon-ok"></span></c:if>
													</td>
													<td class="sortable">
														<a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort" title="Move"></a>
													</td>
												</tr>
											</c:if>
											<c:if test="${subjectAreaForm.itemSeqNo != position.itemSeqNo}">
												<tr>
													  <td class="qp-output-fixlength tableIndex">
														  <span id="stt">${status.index + 1}</span>
														  <input type="hidden" name="arrIndex" value="${status.index + 1}" />
														  <input type="hidden" name="positionLst[${status.index}].areaId" value="${position.areaId}">
														  <input type="hidden" name="positionLst[${status.index}].itemSeqNo" class="itemPos">
														  <input type="hidden" name="positionLst[${status.index}].areaName" value="${position.areaName}">
														  <input type="hidden" name="positionLst[${status.index}].areaCode" value="${position.areaCode}">
														  <input type="hidden" name="positionLst[${status.index}].defaultFlg" value="${position.defaultFlg}">
													  </td>
													  <td><qp:formatText value="${position.areaName}"/></td>
													  <td><qp:formatText value="${position.areaCode}"/></td>
													  <td id = "defaultflg">
														<c:if test="${position.defaultFlg eq 1}"><span class="glyphicon glyphicon-ok"></span></c:if>
													  </td>
													  <td class="sortable">
														  <a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort" title="Move"></a>
													  </td>
												 </tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.subareadesign.0015"></qp:message></span>
				</div>
				<div class="panel-body">
					<table id="tmp_list_table" class="table table-bordered qp-table-list">
						<colgroup>
							<col />
							<col />
							<col />
							<col />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th><qp:message code="sc.subareadesign.0006"></qp:message></th>
								<th><qp:message code="sc.subareadesign.0007"></qp:message></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="table" items="${subjectAreaForm.tableLst }" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">
										${status.index + 1}
									</td>
									<td>
										<qp:autocomplete optionValue="optionValue"
												 selectSqlId="getAllTableDesignByProjectId" 
												 name="tableLst[${status.index}].tableDesignId" 
												 value="${table.tableDesignId}" arg02="${subjectAreaForm.projectId}"
												 displayValue="${table.tableName}" maxlength="200"
												 optionLabel="optionLabel" mustMatch="true"
												 onChangeEvent="changeTableCodeLabel" >
										</qp:autocomplete>
									</td>
									<td><span><qp:formatText value="${table.tableCode}"/></span></td>
									<td>
										<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' 
											onclick="$.qp.removeRowJS('tmp_list_table', this);" href="javascript:void(0)"></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="qp-add-left">
						<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" 
						onclick="$.qp.addRowJSByLink(this);" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.subareadesign.0016"></qp:message></span>
				</div>
				<div class="panel-body">
					<table id="tmp_list_keyword" class="table table-bordered qp-table-list">
						<colgroup>
							<col />
							<col />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th><qp:message code="sc.subareadesign.0008"></qp:message></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="keyword" items="${subjectAreaForm.keywordLst }" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">${status.index + 1}</td>
									<td>
									<input type="hidden" name="keywordLst[${status.index}].keywordId" value="${keyword.keywordId }">
									<input type="text" name="keywordLst[${status.index}].keyword" value="${keyword.keyword }" class="form-control qp-input-text" maxlength="200"/></td>
									<td>
										<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' 
											onclick="$.qp.removeRowJS('tmp_list_keyword', this);" href="javascript:void(0)"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="qp-add-left">
						<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" 
							onclick="$.qp.addRowJSByLink(this);" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="subjectareaRegister">
					<form:hidden path="itemSeqNo"/>
					<form:hidden path="projectId"/>
					<button type="submit" class="btn btn-md qp-button qp-dialog-confirm" name="mode" value="0"><qp:message code="sc.sys.0031"></qp:message></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>