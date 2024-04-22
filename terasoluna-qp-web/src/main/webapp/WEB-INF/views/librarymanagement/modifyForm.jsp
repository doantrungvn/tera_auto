<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">	
			var CL_LIBRARY_SCOPE = {};
			<c:forEach items="${CL_LIBRARY_SCOPE}" var="item">
				CL_LIBRARY_SCOPE['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_LIBRARY_TYPE= {};
			<c:forEach items="${CL_LIBRARY_TYPE}" var="item">
				CL_LIBRARY_TYPE['${item.key}'] = '${item.value}';
			</c:forEach>
			
			$(function(){
				$("[name='uploadFileContent']").on("change",function(){
					$("[name='uploadFileContentChange']").val(true);
				});
			});
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsMsgSource.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/librarymanagement/javascript/common.js"></script>
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.librarymanagement"></qp:message></span></li>
         <li><span><qp:message code="sc.librarymanagement.0014"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="librarymanagementSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/librarymanagement/search"><qp:message code="sc.librarymanagement.0002"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		
		<form:form method="post" enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/librarymanagement/modify"
			modelAttribute="libraryManagementForm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="maxSize" />
			<c:if test="${notExistFlg ne 1}">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="qp-heading-text"><qp:message code="sc.librarymanagement.0004"/></span>
				</div>
				<div class="panel-body">
						
					<form:hidden path="libraryId" value="${libraryManagementForm.libraryId}"/>
					<form:hidden path="updatedDate" value="${libraryManagementForm.updatedDate}" />	
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th>
								<form:label path="groupId"><qp:message code="sc.librarymanagement.0005"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
								<label for="groupId" title="<qp:message code="sc.librarymanagement.0016"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0016"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td><form:input path="groupId" cssClass="form-control qp-input-text qp-convention-name" maxlength="150" /></td>
							<th>
								<form:label path="artifactId"><qp:message code="sc.librarymanagement.0006"/>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
								<label for="artifactId" title="<qp:message code="sc.librarymanagement.0017"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0017"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td><form:input path="artifactId" cssClass="form-control qp-input-text qp-convention-name" maxlength="150" /></td>
						</tr>
						<tr>
							<th>
								<form:label path=""><qp:message code="sc.librarymanagement.0007"/></form:label>
								<label for="version" title="<qp:message code="sc.librarymanagement.0018"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0018"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td>
								<form:input path="version" cssClass="form-control qp-input-text" maxlength="50" />
							</td>
							<th>
								<form:label path=""><qp:message code="sc.librarymanagement.0008"/></form:label>
								<label for="version" title="<qp:message code="sc.librarymanagement.0019"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0019"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td>
								<form:input path="classifier" cssClass="form-control qp-input-text" maxlength="50" />
							</td>
						</tr>
						<tr>
							<th>
								<form:label path="optionalFlg"><qp:message code="sc.librarymanagement.0012"/></form:label>
								<label for="version" title="<qp:message code="sc.librarymanagement.0023"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0023"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td>
								<form:checkbox id="optionalFlg" path="optionalFlg" cssClass="checkbox qp-input-checkbox" value ="1"/>
							</td>
							<th>
								<form:label path=""><qp:message code="sc.librarymanagement.0010"/></form:label>
								<label for="version" title="<qp:message code="sc.librarymanagement.0021"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0021"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td>
								<qp:autocomplete mustMatch="false" name="type" displayValue="${libraryManagementForm.type}" value="${libraryManagementForm.type}"
									optionValue="optionValue" optionLabel="optionLabel" sourceCallback="loadLibraryTypeDefault" sourceType="local">
								</qp:autocomplete>
							</td>
						</tr>
						<tr>
							<th>
								<form:label path="scope"><qp:message code="sc.librarymanagement.0009"/></form:label>
								<label for="version" title="<qp:message code="sc.librarymanagement.0020"/>"></label>
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0020"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td>
								<qp:autocomplete mustMatch="false" name="scope" displayValue="${libraryManagementForm.scope}" value="${libraryManagementForm.scope}"
									optionValue="optionValue" optionLabel="optionLabel" sourceCallback="loadLibraryScopeDefault" sourceType="local" onChangeEvent="changeScope">
								</qp:autocomplete>
							</td>
							<td colspan="2">
								<c:forEach items="${CL_SYSTEM_PATH_FLAG}" var="item" varStatus="status">
									<form:radiobutton path="systemFlag" value="${item.key }" cssClass="qp-input-radio-margin qp-input-radio" onchange="systemFlagChange(this);"/>
									<label for="systemFlag${status.index+1 }"><qp:message code="${item.value}" /></label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th>
								<span class="upload"><qp:message code="sc.librarymanagement.0011"/></span>
								<span class="path"><qp:message code="sc.librarymanagement.0024"/></span>
								&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
								<a style="margin-left:5px" class="path glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.librarymanagement.0022"/>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td colspan="3">
								<form:input path="systemPath" cssClass="path form-control"/>
								<form:input path="uploadFileContent" cssClass="qp-input-file pull-right" type="file" accept=".jar"/>
								<span class="file-input-name">${libraryManagementForm.uploadFileName}</span>
								<form:hidden path="uploadFileContentChange" />
                                <form:hidden path="uploadFileName" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="librarymanagementModify">
					<button type="button" class="btn qp-button qp-dialog-confirm" data-confirm-pcallback="checkBeforeSubmit">
						<qp:message code="sc.sys.0031" />
					</button>
				</qp:authorization>
			</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
