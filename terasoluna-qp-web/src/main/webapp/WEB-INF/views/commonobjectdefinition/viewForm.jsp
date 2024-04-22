<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
			<qp:message code="sc.commonobjectdefinition.0005"></qp:message>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/constants.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/initialCommonTable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/commonobjectdefinition/javascript/commonobjectdefinition.js"></script>
		
		<script type="text/javascript">
			var returnType = 1;
			var CL_EX_DATATYPE = {}//CL_QP_Datatype
			<c:forEach items="${CL_EX_DATATYPE}" var="item">
				CL_EX_DATATYPE['${item.key}'] = '${item.value}';
			</c:forEach>
			$(document).ready(function() {
				reIndexAllRowOfMenu();
				$('#tbl_attribute_list_define').find("tbody tr").each(function() {
					 if($(this).find('td:eq(0)').attr("colspan") == '100%'){
						 $(this).remove();
					 }
					$(this).find("select[name*='dataType']").prop( "disabled", true );
				});
			});
			var MAX_NESTED_OBJECT = '${CL_SYSTEM_SETTING["maxNestedObject"]}';
		</script>
		
		
		<!-- Begin include business check component -->
			<jsp:include page="rowTemplate.jsp" />
		<!-- End include business check component -->
	</tiles:putAttribute>
	<c:if test="${not commonObjectDefinitionForm.hasErrors}">
		<tiles:putAttribute name="body">
			<c:if test="${notExistFlg ne 1}">
				<form:form method="post"
					action="${pageContext.request.contextPath}/commonobjectdefinition/delete"
					modelAttribute="commonObjectDefinitionForm">
					<form:hidden path="commonObjectDefinitionId" />
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.commonobjectdefinition.0005"></qp:message></span>
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
									<th><form:label path="commonObjectDefinitionName">
											<qp:message code="sc.commonobjectdefinition.0002"></qp:message>
										</form:label></th>
									<td>${commonObjectDefinitionForm.commonObjectDefinitionName}</td>
									<th><form:label path="commonObjectDefinitionCode">
											<qp:message code="sc.commonobjectdefinition.0003"></qp:message>
										</form:label></th>
									<td>${commonObjectDefinitionForm.commonObjectDefinitionCode}</td>
								</tr>
								<tr>
									<th><form:label path="moduleIdAutocomplete">
									<qp:message code="sc.module.0007" />
								</form:label></th>
									<td>${commonObjectDefinitionForm.moduleIdAutocomplete}</td>
									<th><form:label path="remark">
											<qp:message code="sc.sys.0028" />
										</form:label></th>
									<td><qp:formatRemark value="${commonObjectDefinitionForm.remark}" /></td>
								</tr>
							</table>
						</div>
					</div>
                    
                    <div class="qp-div-action">
                    <qp:authorization permission="commonobjectdefinitionDelete">
                        <a type="button" class="btn qp-button-warning qp-link-button qp-link-button qp-dialog-confirm"> <qp:message code="sc.sys.0008" /> </a>
                    </qp:authorization>
                    <qp:authorization permission="commonobjectdefinitionModify">
                        <a type="submit" class="btn btn-md btn-success qp-link-button qp-link-popup-navigate" href="${pageContext.request.contextPath}/commonobjectdefinition/modify?commonObjectDefinitionId=${f:u(commonObjectDefinitionForm.commonObjectDefinitionId)}&mode=1"><qp:message code="sc.sys.0006" /></a> 
                    </qp:authorization>
                    </div>

					<div class="panel panel-default qp-div-select">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.commonobjectdefinition.0008"></qp:message></span>
						</div>
						<div class="panel-body">
							<table class="table table-bordered qp-table-list" id="tbl_attribute_list_define" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.businessdesign.inputFormCallback">
							<colgroup>
								<col width="40px"/>
								<col width="450px" />
								<col width="50%" />
								<col width="50%" />
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.sys.0004"></qp:message></th>
									<th><qp:message code="sc.commonobjectdefinition.0009"></qp:message></th>
									<th><qp:message code="sc.commonobjectdefinition.0010"></qp:message></th>
									<th><qp:message code="sc.commonobjectdefinition.0011"></qp:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${commonObjectDefinitionForm.commonObjectAttributes }" var="item" varStatus="status">
									<qp:commonViewRow itemValue="${item}"/>
								</c:forEach>
							</tbody>		
						</table>
						</div>
					</div>
				</form:form>
			</c:if>
		</tiles:putAttribute>
	</c:if>
</tiles:insertDefinition>