<tiles:insertDefinition name="layouts-popup">
	
	<%--
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.licensemanagement"></qp:message></span></li>
		 <li><span><qp:message code="sc.permission.licensemanagementRegister.remark"/></span></li>
	</tiles:putAttribute>
	
	 <tiles:putAttribute name="header-link">
	<qp:authorization permission="licensemanagementSearch">
		<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
		<a href="${pageContext.request.contextPath}/licensemanagement/search"><qp:message code="sc.permission.licensemanagementSearch.remark"/></a>
	</qp:authorization>
	</tiles:putAttribute> --%>
	
	<tiles:putAttribute name="header-name">	
		<qp:message code="sc.permission.licensemanagementRegister.remark"/>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
    	<script>
    		function checkFileSize(){
    		    var fileUpload = $("span.file-input-name").text();
    		    
    		    if (fileUpload != undefined || fileUpload != "") {
    		        $("#licenseFileName").val(fileUpload);
    		    }
    		    
    			var uploadFile = $("[name='fileName']")[0].files[0];
    			var maxSize = $("[name='maxSize']").val();
    			
    			if(uploadFile != undefined && uploadFile.size > maxSize*1024*1024){
    				$.qp.showAlertModal(fcomMsgSource['err.sys.0208'].replace("{0}",maxSize));
    				return false;
    			}
    			return true;
        	}
    	</script>
	
		<form:form method="post" enctype="multipart/form-data" modelAttribute="licenseManagementForm" action="${pageContext.request.contextPath}/licensemanagement/import">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="maxSize" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.licensemanagement.0011"/></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tblForm">
						<colgroup>
							<col />
							<col />
						</colgroup>
						<tr>
							<th>
								<qp:message code="sc.licensemanagement.0015" />&nbsp;
								<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
							</th>
							<td class="com-table-td-text">
									<form:input path="fileName" cssClass="qp-input-file pull-right" type="file" accept=".lic" />
									<form:hidden path="status" />
                                <form:hidden path="licenseFileName" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
			<qp:authorization permission="licensemanagementRegister">
				<button type="submit" class="btn qp-button"  onclick="checkFileSize()" id="btnImport" messageId="inf.sys.0023"><qp:message code="sc.sys.0050" /></button>
			</qp:authorization>
			</div>
			<%-- <c:if test="${licenseManagementForm.resultFileName != null && licenseManagementForm.resultFileName != ''}">
				<div class="panel panel-default qp-div-information" id="importResult">				
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.importmanagement.0009" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="80%" />
								
							</colgroup>
							<tr>
								<th><qp:message code="sc.importmanagement.0010" /></th>						
								<form:hidden path="resultFileName"/>
								<td>
									${licenseManagementForm.resultFileName} &nbsp;&nbsp;&nbsp;&nbsp; 
									<c:if test="${licenseManagementForm.resultFileName != null && licenseManagementForm.resultFileName != ''}">
									<qp:authorization permission="licensedesignRegister">
										<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
										<form:hidden path="accountId" value="${account.accountId}" />
									</qp:authorization>
									</c:if>
								</td>
							</tr>
						</table>
					</div>				
				</div>
			</c:if> --%>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>