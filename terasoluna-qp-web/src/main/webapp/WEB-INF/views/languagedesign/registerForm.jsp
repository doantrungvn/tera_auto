<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="sc.languagedesign.0009"></qp:message></span></li>
         <li><span><qp:message code="sc.languagedesign.0027"/></span></li>
    </tiles:putAttribute>
	
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="languagedesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/languagedesign/search"><qp:message code="sc.languagedesign.0020"/></a>
		</qp:authorization>
		<style type="text/css">
			#languageDesignForm td .qp-input-radio {
				margin: auto;
				width: 100%;
				height: 60%;
				cursor: pointer;
			}
		</style>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/languagedesign/languagedesign.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#languageDesignForm").find("tbody>tr").each(function(){
					var row = this;
					$("#languageDesignForm").find("tbody>tr").not(row).each(function(){
						$(row).find("select[name$='.languageCode'] option[value='"+$(this).find("[name$='.languageCode']").closest("td").next().text()+"']").prop("disabled",true);
						$(row).find("select[name$='.languageCode'] option[value='"+$(this).find("[name$='.languageCode']").closest("td").next().text()+"']").hide();
					});
					
				});
			});
		</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<jsp:include page="rowTemplate.jsp"></jsp:include>
		<form:form method="post" action="${pageContext.request.contextPath}/languagedesign/register" modelAttribute="languageDesignDesignForm">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.languagedesign.0008"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list" id="languageDesignForm" data-ar-callback="removeExistingLanguage">
						<colgroup>
							<col />
							<col />
							<col />
							<col width="10%"/>
							<col />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004" /></th>
								<th><qp:message code="sc.languagedesign.0002"/></th>
								<th><qp:message code="sc.languagedesign.0001"/></th>
								<th><qp:message code="sc.subareadesign.0009"/></th>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${languageDesignDesignForm.languageDesignForms}" var="languageDesign" varStatus="status">
							<c:if test="${not empty languageDesign.languageId}">
								<tr class="ar-dataRow">
									<td>${status.index + 1}</td>
									<td>
										${languageDesign.languageName }
										<form:hidden path="languageDesignForms[${status.index}].languageCode"/>
										<form:hidden path="languageDesignForms[${status.index}].countryCode"/>
										<form:hidden path="languageDesignForms[${status.index}].languageName"/>
										<form:hidden path="languageDesignForms[${status.index}].languageId"/>
										<form:hidden path="languageDesignForms[${status.index}].itemSeqNo" cssClass="ar-groupIndex"/>
									</td>
									<td style="border-right: none; padding-top:8px;">${languageDesign.languageCode}_${languageDesign.countryCode }</td>
									<td>
										<form:radiobutton path="defaultLanguage" value="${languageDesignDesignForm.languageDesignForms[status.index].itemSeqNo }" cssClass="radio qp-input-radio ar-groupIndex"/>
									</td>
									<td>
										<c:if test="${languageDesign.languageCode ne 'en' and sessionScope.CURRENT_LANGUAGE.languageCode ne languageDesign.languageCode}">
										<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
										</c:if>
									</td>
								</tr>
							</c:if>
							<c:if test="${empty languageDesign.languageId}">
								<tr class="ar-dataRow">
									<td>${status.index + 1}</td>
									<td>
										<form:select path="languageDesignForms[${status.index}].languageCode" cssClass="form-control qp-input-select" onchange="typeChangeLanguage(this, 'register')">
											<form:option value=""><qp:message code="sc.sys.0030"/></form:option>
											<c:forEach var="item" items="${CL_LANGUAGE_CODE}">
												<form:option value="${item.key}"><qp:message code="${CL_LANGUAGE_CODE.get(item.key)}"/></form:option>
											</c:forEach>
										</form:select>
										<form:hidden path="languageDesignForms[${status.index}].languageName"/>
										<form:hidden path="languageDesignForms[${status.index}].languageId"/>
										<form:hidden path="languageDesignForms[${status.index}].itemSeqNo" cssClass="ar-groupIndex"/>
									</td>
									<td style="border-right: none; padding-top:8px;">${languageDesign.languageCode}</td>
									<td>
										<form:radiobutton path="defaultLanguage" value="${languageDesignDesignForm.languageDesignForms[status.index].itemSeqNo }" cssClass="radio qp-input-radio ar-groupIndex"/>
									</td>
									<td>
										<c:if test="${languageDesign.languageCode ne 'en'}">
										<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
										</c:if>
									</td>
								</tr>
							</c:if>
						</c:forEach>
						
					</table>
					<div class="qp-add-left">
						<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link : this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			 <div class="qp-div-action">
				<qp:authorization permission="languagedesignRegister">
					<form:hidden path="projectId"/>
					<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>