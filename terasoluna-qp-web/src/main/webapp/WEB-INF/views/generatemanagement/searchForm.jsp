<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			$( document ).ready(function() {
				//init default
				var $radios = $("#generateManagementForm").find("input:radio[name='generateMode']");
				if($radios.is(':checked') === false) {
					$radios.filter('[value=0]').prop('checked', true);
				}
	
				$radios.click(function () {
					displayLanguage($(this).val());
				});
	
				displayLanguage($("#generateManagementForm").find("input[type=radio][name='generateMode']:checked").val());
			});
	
			function displayLanguage(generateMode) {
				//document mode
				if(generateMode == 0) {
					$("#language-required").show();
					$(".language-div").show();
				}
				else {
					// source code mode
					if(generateMode == 1) {
						$("#language-required").hide();
						$(".language-div").hide();
					}
					else {
						$("#language-required").hide();
						$(".language-div").hide();
					}
				}
			}
		</script>
	</tiles:putAttribute>
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.generation"></qp:message></span></li>
         <li><span><qp:message code="sc.generatemanagement.0000"/></span></li>
    </tiles:putAttribute>
        
	<tiles:putAttribute name="header-link">
		<span class="qp-link-header-icon glyphicon glyphicon-refresh"></span>
		<a href="javascript:window.location.reload()"><qp:message code="sc.generatemanagement.0023"/></a>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="generateManagementForm"
			action="${pageContext.request.contextPath}/generatemanagement/generate">
			<div class="panel panel-default qp-div-information">
				<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.generatemanagement.0001"/></span>
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
							<th><qp:message code="sc.generatemanagement.0003"/></th>
							<td><qp:formatText value="${project.projectName}"/></td>
							<th><qp:message code="sc.generatemanagement.0004"/></th>
							<td><qp:formatText value="${project.projectCode}"/></td>
						</tr>
						<tr>
							<th><qp:message code="sc.generatemanagement.0005"/></th>
							<td><qp:message code="${CL_DESIGN_STATUS.get(project.status.toString())}" /></td>
							<th><qp:message code="sc.generatemanagement.0006"/></th>
							<td><qp:message code="${CL_DATABASE_TYPE.get(project.dbType.toString())}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.generatemanagement.0007"/></th>
							<td>
								<form:radiobuttons path="generateMode" items="${CL_GENERATE_MODE}" class="qp-input-radio qp-input-radio-margin"/>
							</td>
							<th>
								<div class="language-div">
									<qp:message code="sc.generatemanagement.0020"/>&nbsp;<label id="language-required" class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								</div>
							</th>
							<td>
								<qp:autocomplete name="languageId" optionValue="output02" optionLabel="optionLabel" cssClass="language-div"
									value="${generateManagementForm.languageId}" selectSqlId="getLanguageDesignForAutocomplete" arg02="${generateManagementForm.projectId}"
									displayValue="${generateManagementForm.languageIdAutocomplete}" mustMatch="true" maxlength="200" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<input type="button" messageid="inf.sys.0025" value="<qp:message code="sc.generatemanagement.0014"/>" class="btn qp-button qp-dialog-confirm" />
			</div>			
		</form:form>
		
		<form:form method="post" modelAttribute="generateManagementResultForm"
			action="${pageContext.request.contextPath}/generatemanagement/generatemanagement">
			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">					
					<qp:itemPerPage form="generateManagementResultForm" action="/generatemanagement/generatemanagement" overwriteMessage="sc.generatemanagement.0002"/>
				</div>				
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${page != null && page.totalPages > 0 }">
							<table class="table table-bordered qp-table-list" id="businessTypesTable">
								<colgroup>
									<col width="5%"/>
									<col width="15%"/>
									<col width="15%"/>
									<col width="15%"/>
									<col width="15%"/>
									<col width="25%"/>
									<col width="15%"/>
									<col width="10%"/>
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004"></qp:message></th>
										<th><qp:columnSort colName="generate_mode" colCode="sc.generatemanagement.0007" form="generateManagementResultForm" /></th>
										<th><qp:columnSort colName="generate_date" colCode="sc.generatemanagement.0008" form="generateManagementResultForm" /></th>
										<th><qp:columnSort colName="username" colCode="sc.generatemanagement.0009" form="generateManagementResultForm" /></th>
										<th><qp:columnSort colName="generate_status" colCode="sc.generatemanagement.0010" form="generateManagementResultForm" /></th>
										<th><qp:columnSort colName="file_name" colCode="sc.generatemanagement.0011" form="generateManagementResultForm" /></th>
										<th><qp:columnSort colName="updated_date" colCode="sc.generatemanagement.0021" form="generateManagementResultForm" /></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:set var="rowStyle" scope="page" value=""/>
									<c:forEach var="item" items="${page.content}" varStatus="status">
										<c:if test="${item.generateStatus eq 2 || item.generateStatus eq 3}">
											<c:set var="rowStyle" scope="page" value="danger"/>
										</c:if>
										<c:if test="${item.generateStatus eq 1}">
											<c:set var="rowStyle" scope="page" value="success"/>
										</c:if>
										<c:if test="${item.generateStatus eq 0}">
											<c:set var="rowStyle" scope="page" value=""/>
										</c:if>
										<tr class="${rowStyle}">
											<td class="qp-output-fixlength"><qp:formatInteger value="${(page.number * page.size) + status.count}" /></td> 
											<td class="qp-output-text">${CL_GENERATE_MODE.get(item.generateMode.toString())}</td>
											<td class="qp-output-datetime"><qp:formatDateTime value="${item.generateDate}"/></td>
											<td class="qp-output-text">${item.generateByName}</td>
											<td class="qp-output-text">${CL_GENERATE_STATUS.get(item.generateStatus.toString())}</td>
											<td class="qp-output-text">${item.fileName}</td>
											<td class="qp-output-text"><qp:formatDateTime value="${item.updatedDate}"/></td>
											<td class="qp-table-list-action-field">
												<c:if test="${item.isDownload==1 && not empty item.fileName}"> <!-- if isDownload =0 then display download button -->
													<a class="btn btn-success glyphicon glyphicon-save qp-link-button qp-link-action" title="Download"
														href="${pageContext.request.contextPath}/downloadFile?fileName=${item.fileName}&redirectPath=generatemanagement/generatemanagement">
													</a>
												</c:if>

												<c:if test="${item.generateStatus eq 2 || item.generateStatus eq 3}"> <!-- if generate status is error then display regenerate button -->
													<a type="submit" class="btn btn-primary qp-link-button glyphicon glyphicon-repeat" title="<qp:message code="sc.generatemanagement.0022"/>"
														href="${pageContext.request.contextPath}/generatemanagement/regenerate?generateId=${item.generateId}">
													</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<div class="qp-div-action">
								<c:choose>
									<c:when test="${page.sort != null }">
										<t:pagination outerElementClass="pagination pagination-md"
											page="${page}"
											queryTmpl="page={page}&size={size}&sort={sortOrderProperty},{sortOrderDirection}"
											maxDisplayCount="5" />
									</c:when>
									<c:otherwise>
										<t:pagination outerElementClass="pagination pagination-md"
											page="${page}"
											maxDisplayCount="5" />
									</c:otherwise>
								</c:choose>	
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
