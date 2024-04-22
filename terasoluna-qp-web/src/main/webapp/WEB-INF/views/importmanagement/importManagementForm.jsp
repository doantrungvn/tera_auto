<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	<script type="text/javascript">
	</script>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.generation"></qp:message></span></li>
		<li><span><qp:message code="sc.importmanagement.0000" /></span></li>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<script>
			function changedocumentType(event,ui){	
				$("#importResult").attr("style","display:none");
			}
			function checkBeforeSubmit(){
			    var fileUpload = $("span.file-input-name").text();
			    
			    if (fileUpload != undefined || fileUpload != "") {
			        $("#fileName").val(fileUpload);
			    }
				
				var uploadFile = $("[name='file']")[0].files[0];
				var maxSize = $("[name='maxSize']").val();
				
				if(uploadFile.size > maxSize*1024*1024){
					//alert(fcomMsgSource['err.sys.0201']);
					$.qp.alert(fcomMsgSource['err.sys.0208'].replace("{0}",maxSize));
					return false;
				}
				
				return true;
			}
		</script>
		<form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/importmanagement/importmanagement" modelAttribute="importManagementForm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<form:hidden path="maxSize" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.importmanagement.0001" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form" id="tbl_list_result">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th>
								<qp:message code="sc.importmanagement.0002" />&nbsp;
								<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
							</th>
							<td class="com-table-td-text">
								<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getDocumentType" onChangeEvent="changedocumentType"
									value="${importManagementForm.documentType }" name="documentType" displayValue="${importManagementForm.documentTypeAutocomplete }" 
									arg02="20" mustMatch="true">
								</qp:autocomplete>
							</td>
							<th>
								<qp:message code="sc.importmanagement.0003" />&nbsp;
								<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
							</th>
							<td class="com-table-td-text">
								<form:input path="file" cssClass="qp-input-file pull-right" type="file" accept=".xlsx"/>
                                <form:hidden path="fileName" />
							</td>
						</tr>
						<tr>
							<th>
								<qp:message code="sc.importmanagement.0020" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
							</th>
							<td>
								<label ><form:radiobutton cssClass="qp-input-radio-margin  qp-input-radio" path="rollback" value="true" /><qp:message code="sc.importmanagement.0005" /></label>
								<label ><form:radiobutton cssClass="qp-input-radio-margin  qp-input-radio" path="rollback" value="false" /><qp:message code="sc.importmanagement.0006" /></label>
							</td>
							<th>
								<qp:message code="sc.importmanagement.0021" />&nbsp;
								<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>		
								<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="Data exist in database but not exist in document" data-html="true" data-toggle="tooltip" data-placement="top"></a>
							</th>
							<td>
								<label ><form:radiobutton cssClass="qp-input-radio-margin  qp-input-radio" path="delete" value="false" /><qp:message code="sc.importmanagement.0008" /></label>	
								<label ><form:radiobutton cssClass="qp-input-radio-margin  qp-input-radio" path="delete" value="true" /><qp:message code="sc.importmanagement.0007" /></label>								
							</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="qp-div-action">
				<qp:authorization permission="importmanagement">
					<button type="submit" class="btn qp-button qp-dialog-confirm"  data-confirm-pcallback="checkBeforeSubmit" id="btnImport" messageId="inf.sys.0023"><qp:message code="sc.sys.0018" /></button>
				</qp:authorization>
			</div>
			
			<c:if test="${not empty importManagementForm.resultFileName}">
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
									${importManagementForm.resultFileName} &nbsp;&nbsp;&nbsp;&nbsp; 
									<c:if test="${importManagementForm.resultFileName != null && importManagementForm.resultFileName != ''}">
										<a class="btn btn-success glyphicon glyphicon-save qp-link-button qp-link-action" title="<qp:message code="sc.sys.0020" />"
											href="${pageContext.request.contextPath}/downloadImportFile?fileName=${importManagementForm.resultFileName}&redirectPath=importmanagement/importmanagement">
										</a>
									</c:if>
	<!-- 								<button type="submit" class="btn qp-button qp-dialog-confirm" data-confirm-pcallback="$.qp.initial.validateBeforeSubmit" id="btnGenerate" messageId="inf.sys.0025">Download</button> -->
								</td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>