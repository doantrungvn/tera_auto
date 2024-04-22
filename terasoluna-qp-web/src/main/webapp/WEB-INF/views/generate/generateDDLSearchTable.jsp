<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.generation"></qp:message></span></li>
		<li><span><qp:message code="tqp.generateddl"/></span></li>
		 <li><span><qp:message code="sc.tabledesign.0069"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="generationGenerateddl">
			<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
			<a href="${pageContext.request.contextPath}/generation/generateddl"><qp:message code="tqp.generateddl" /></a>
		</qp:authorization>
		<script src="${pageContext.request.contextPath}/resources/app/generate/javascript/generateDDL.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				
			});
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/generation/searchTable" modelAttribute="tableDesignSearchForm">
		<input type="hidden" name="backJson"/>
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"> <qp:message code="sc.sys.0002"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tblSearch">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th><form:label path="tableName"><qp:message code="sc.tabledesign.0019"/></form:label></th>
							<td><form:input path="tableName" cssClass="form-control qp-input-text" maxlength="200" />
							<th><form:label path="tableCode"><qp:message code="sc.tabledesign.0020"/></form:label></th>
							<td><form:input path="tableCode" cssClass="form-control qp-input-text" maxlength="25" />
						</tr>
						<tr>
							<th><form:label path="status"><qp:message code="sc.sys.0055"></qp:message></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_DESIGN_STATUS}">
									<label><form:checkbox path="designStatus" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_DESIGN_STATUS.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th><form:label path="subjectAreaId"><qp:message code="sc.subareadesign.0004" /></form:label></th>
							<td>
								<qp:autocomplete 
									name="subjectAreaId" 
									optionValue="optionValue" 
									optionLabel="optionLabel" 
									selectSqlId="getAllSubjectAreaByProjectIdForAutocomplete" 
									value="${tableDesignSearchForm.subjectAreaId}" 
									displayValue="${tableDesignSearchForm.subjectAreaIdAutocomplete}" 
									arg01="${tableDesignSearchForm.projectId}" 
									mustMatch="true" maxlength="200">
								</qp:autocomplete>
							</td>
						</tr>
						<tr>
							<th><form:label path="typeTable"><qp:message code="sc.sys.0059"></qp:message></form:label></th>
							<td>
								<c:forEach var="item" items="${CL_TABLE_TYPE_ALL}">
									<label><form:checkbox path="types" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_TABLE_TYPE_ALL.get(item.key)}" /></label>
								</c:forEach>
							</td>
							<th><qp:message code="sc.generation.0022" /></th>
							<td><form:checkbox path="genDrop" cssClass="qp-input-checkbox-margin qp-input-checkbox" ></form:checkbox></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="tabledesignSearch">
					<button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
				</qp:authorization>
			</div>
		</form:form>
		
		<form:form method="post" modelAttribute="generateForm" action="${pageContext.request.contextPath}/generation/generateddl">
			<%-- <form:hidden path="genDrop"/> --%>
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text">
						<qp:message code="sc.sys.0003"/>&nbsp;
						<span class="badge">&nbsp;${page.totalElements}&nbsp;</span>
					</span>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list" id="tblAllTable">
								<colgroup>
									<col />
									<col />
									<col width="25%" />
									<col width="16%" />
									<col width="15%" />
									<col width="5%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:columnSort colName="tbl_design_name" colCode="sc.tabledesign.0019" form="tableDesignSearchForm" /><input type="hidden" name="designStatus" value="${item.designStatus}" /></th>
										<th><qp:columnSort colName="tbl_design_code" colCode="sc.tabledesign.0020" form="tableDesignSearchForm" /></th>
										<th><qp:columnSort colName="type" colCode="sc.sys.0059" form="tableDesignSearchForm" /></th>
										<th><qp:columnSort colName="design_status" colCode="sc.sys.0055" form="tableDesignSearchForm" /></th>
										<th><input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" onclick="selectAll(this)" name="checkboxAll"></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${page.content }" varStatus="look">
										<tr>
											<td>
												${(page.number * page.size) + look.count}
											</td>
											
											<td>
												<qp:authorization permission="tabledesignView" isDisplay="true" displayValue="${item.tableName}">
													<a href="${pageContext.request.contextPath}/tabledesign/view?tableDesignId=${item.tableDesignId}&mode=1" class="qp-link-popup"><qp:formatText value="${item.tableName}" /></a>
												</qp:authorization>
											</td>
											<td>
												<qp:formatText value="${item.tableCode}" />
												<form:hidden path="listTableId[${((page.number * page.size) + look.count) - 1}]"/>
												<input type="hidden" value="${item.tableDesignId}" name="tableDesignId">
											</td>
											<td><qp:message code="${CL_TABLE_TYPE_ALL.get(item.type.toString())}"/></td>
											<td><qp:message code="${CL_DESIGN_STATUS.get(item.designStatus.toString())}"/></td>
											<td>
												<input type="checkbox" class="qp-input-checkbox-margin qp-input-checkbox" name="checkboxSingle">
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>			
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="generationGenerateddl">
					<form:hidden path="generateMode" value="${generateForm.generateMode}"/>
					<form:hidden path="generateFrom" value="1"/>
					<form:hidden path="genDrop" value=""/>
					<form:hidden path="databaseLog" value="${generateForm.databaseLog}"/>
					<button type="submit" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0025" onclick="selectedTableGen"><qp:message code="sc.generation.0008" /></button>
					<input type="hidden" id="message_table" value='<qp:message code="sc.tabledesign.0000"/>'>
				</qp:authorization>
			</div>

		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
