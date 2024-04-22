<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.codelist"></qp:message></span></li>
         <li><span><qp:message code="sc.codelist.0017"/></span></li>
    </tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<qp:authorization permission="codelistView">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/codelist/search"><qp:message code="sc.codelist.0001"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/codelist/javascript/codelist.js?r=1321"></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<script id="tbl_list_result-template" type="text/template">
				<tr>
				<td class="qp-output-fixlength tableIndex">1</td>
				<td class="colName"><input type="text" class="form-control" name="codeListDetails[0].name" maxlength="200"></td>
				<td ><input type="text" class="form-control" name="codeListDetails[0].value" maxlength="100"></td>
				<td class="multiVal"><input type="text" class="form-control" name="codeListDetails[0].value1" maxlength="100"></td>
				<td class="multiVal"><input type="text" class="form-control" name="codeListDetails[0].value2" maxlength="100"></td>
				<td class="multiVal"><input type="text" class="form-control" name="codeListDetails[0].value3" maxlength="100"></td>
				<td class="multiVal"><input type="text" class="form-control" name="codeListDetails[0].value4" maxlength="100"></td>
				<td class="multiVal"><input type="text" class="form-control" name="codeListDetails[0].value5" maxlength="100"></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tbl_list_result', this);" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
				</tr>
		</script>

		<form:form method="post" action="${pageContext.request.contextPath}/codelist/modify" modelAttribute="codeListForm">
		<c:if test="${notExistFlg ne 1}">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="codeListId" />
			<form:hidden path="updatedDate" />
			<form:hidden path="multivalueFlg"/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.codelist.0006" /></span>
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
							<th><form:label path="codeListName"><qp:message code="sc.codelist.0003" /></form:label>&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029" /></span></th>
							<td><form:input path="codeListName"	cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200"/></td>
							<th><form:label path="codeListCode"><qp:message code="sc.codelist.0002" /></form:label>&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029" /></span></th>
							<td><form:input path="codeListCode" cssClass="form-control qp-input-text qp-convention-code-row" maxlength="50"/></td>
						</tr>
						<tr>
							<th>
								<form:label path="isOptionValude"><qp:message code="sc.tabledesign.0053" /></form:label>
							<td>
								<form:checkbox path="isOptionValude" cssClass="qp-input-checkbox qp-input-checkbox-margin" value="1" onclick="supportOnyValue();"/>
							</td>
							<th><qp:message code="sc.module.0007"/></th>
							<td>
								<qp:formatText value="${codeListForm.moduleIdAutocomplete}"/>
								<form:hidden path="moduleIdAutocomplete"/>
								<form:hidden path="moduleId" />
							</td>
						</tr>
						<tr>
							<th>
								<form:label path="remark"><qp:message code="sc.sys.0028" /></form:label>
							</th>
							<td>
								<form:textarea path="remark" cssClass="form-control qp-input-textarea" maxlength="2000"/>
							</td>							
							<th></th>
							<td></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.codelist.0007" /></span>
				</div>
				<div class="panel-body">
						<table class="table table-bordered qp-table-list" id="tbl_list_result" data-ar-mrows="200">
<!-- 							<colgroup> -->
<!-- 								<col> -->
<!-- 								<col> -->
<!-- 								<col width="10%"> -->
<!-- 								<col width="10%"> -->
<!-- 								<col width="10%"> -->
<!-- 								<col width="10%"> -->
<!-- 								<col width="10%"> -->
<!-- 								<col width="10%"> -->
<!-- 								<col> -->
<!-- 							</colgroup> -->
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th class="colName"><qp:message code="sc.codelist.0008" /><span class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043" /></span></th>
									<th ><qp:message code="sc.codelist.0009" /><span class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043" /></span></th>
									<th width="10%" class="multiVal"><qp:message code="sc.codelist.0011" /></th>
									<th width="10%" class="multiVal"><qp:message code="sc.codelist.0012" /></th>
									<th width="10%" class="multiVal"><qp:message code="sc.codelist.0013" /></th>
									<th width="10%" class="multiVal"><qp:message code="sc.codelist.0014" /></th>
									<th width="10%" class="multiVal"><qp:message code="sc.codelist.0015" /></th>
									<th><a id="multiValue" class="glyphicon glyphicon-eye-open" href="javascript:void(0)" onclick="processSupportMultiVal(this);"></a></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${codeListForm.codeListDetails}" varStatus="status">
									<tr>
										<td class="qp-output-fixlength tableIndex">
											${status.count}
											<form:hidden path="codeListDetails[${status.index}].clDeatailId" />
										</td>
										<td class="colName">
											<form:input type="text" class="form-control" path="codeListDetails[${status.index}].name" maxlength="200"/></td>
										<td>
											<form:input type="text" class="form-control" path="codeListDetails[${status.index}].value" maxlength="100"/></td>
											<td class="multiVal"><form:input type="text" class="form-control" path="codeListDetails[${status.index}].value1" maxlength="100"/></td>
											<td class="multiVal"><form:input type="text" class="form-control" path="codeListDetails[${status.index}].value2" maxlength="100"/></td>
											<td class="multiVal"><form:input type="text" class="form-control" path="codeListDetails[${status.index}].value3" maxlength="100"/></td>
											<td class="multiVal"><form:input type="text" class="form-control" path="codeListDetails[${status.index}].value4" maxlength="100"/></td>
											<td class="multiVal"><form:input type="text" class="form-control" path="codeListDetails[${status.index}].value5" maxlength="100"/></td>
										<td>
											<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('tbl_list_result', this);"
												style="margin-top: 3px;" href="javascript:void(0)"></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="qp-add-left">
							<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this);supportOnyValue();supportMultiVal();" style="margin-top: 3px;" href="javascript:void(0)"></a>
						</div>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="codelistModify">
					<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</c:if>
		</form:form>

	</tiles:putAttribute>

</tiles:insertDefinition>