<tiles:insertDefinition name="layouts">
	
	<tiles:putAttribute name="breadcrumb">
		<li><span><qp:message code="tqp.generation"></qp:message></span></li>
		<li><span><qp:message code="tqp.generateddl"/></span></li>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="additionalHeading">
		<!-- Adding sql editor -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/app/sqldesign/css/codemirror.css" />
		<script src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/codemirror.js"></script>
		<script src="${pageContext.request.contextPath}/resources/app/generate/javascript/generateDDL.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=sys"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				if ($("#sqlScripts").val().trim() == '') {
					$("#link-download-sql-scripts").hide();
				} else {
					createDownloadLink("#link-download-sql-scripts", $("#sqlScripts").val(), "script.sql");
					$("#link-download-sql-scripts").show();
				}
				
				var editor = CodeMirror.fromTextArea(document.getElementById('sqlScripts'), {
					indentWithTabs : true,
					smartIndent : true,
					lineNumbers : true,
					styleActiveLine : true,
					matchBrackets : true,
					autofocus : true
				});
				if($("#generateMode").val() == "1"){
					$('input:radio[name=generateModeR]').filter('[value=customTable]').prop('checked', true);
				} else if($("#generateMode").val() == "2"){
					$('input:radio[name=generateModeR]').filter('[value=allForLog]').prop('checked', true);
				} else {
					$('input:radio[name=generateModeR]').filter('[value=all]').prop('checked', true);
					$("#generateMode").val(0);
				}
			});
			
			function createDownloadLink(anchorSelector, str, fileName) {
				if (window.navigator.msSaveOrOpenBlob) {
					var fileData = [ str ];
					blobObject = new Blob(fileData);
					$(anchorSelector).click(
							function() { 
								window.navigator.msSaveOrOpenBlob(blobObject, fileName);
							});
				} else {
					var url = "data:text/plain;charset=utf-8," + encodeURIComponent(str);
					$(anchorSelector).attr("download", fileName);
					$(anchorSelector).attr("href", url);
				}
			}
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="generateForm" action="${pageContext.request.contextPath}/generation/generateddl">
			<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.project.0023" /></span>
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
								<th><qp:message code="sc.project.0017" /></th>
								<td ><qp:formatText value="${projectForm.dbName}" /></td>
								<th><a href="${pageContext.request.contextPath}/generation/guideline" class="qp-link-popup"><qp:message code="Guideline" /></a></th>
								<td></td>
							</tr>
							<tr>
								<th><qp:message code="sc.project.0016" /></th>
								<td><qp:formatText value="${CL_DATABASE_TYPE.get(projectForm.dbType.toString())}" /></td>
								<th><qp:message code="sc.project.0022" /></th>
								<td><qp:formatText value="${projectForm.dbDriver}" /></td>
							</tr>
							
							<tr>
								<th><qp:message code="sc.project.0018" /></th>
								<td><qp:formatText value="${projectForm.dbHostName}" /></td>
								<th><qp:message code="sc.project.0019" /></th>
								<td><qp:formatText value="${projectForm.dbPort}" /></td>
							</tr>
							<tr>
								<th><qp:message code="sc.project.0020" /></th>
								<td><qp:formatText value="${projectForm.dbUser}" /></td>
								<th><qp:message code="sc.generation.0005" /></th>
								<td>
									<label class='radio-inline'><input type='radio' name='generateModeR' value='all' onchange ="changeMode(this)"/><qp:message code="sc.databasedesign.0098"/></label><br />
									<label class='radio-inline'><input type='radio' name='generateModeR' value='customTable' onchange ="changeMode(this)"/><qp:message code="sc.databasedesign.0120"/></label>
									<c:if test="${generateForm.databaseLog == true}">
										<br /><label class='radio-inline'><input type='radio' name='generateModeR' value='allForLog' onchange ="changeMode(this)"/><qp:message code="sc.databasedesign.0121"/></label>
									</c:if>
							</td>
							</tr>
							<tr>
								<th><qp:message code="sc.generation.0022" /></th>
								<td><form:checkbox path="genDrop" cssClass="qp-input-checkbox-margin qp-input-checkbox" ></form:checkbox></td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.generation.0001" /></span>
				</div>
				
				<div class="panel-body">
					<table style="width: 100%; height: 100%;">
						<tr>
							<td>
							<%-- <form:textarea path="sqlScripts" style="width: 100%; text-align: left; height: 400px" rows="6" /> --%>
							<textarea rows="6" id="sqlScripts" style="width: 100%; text-align: left; height: 300px"><c:out value="${generateForm.sqlScripts}" escapeXml="true" /></textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="generationGenerateddl">
					<a id="link-download-sql-scripts" href="#" class="btn qp-button"><qp:message code="sc.generation.0009" /></a>
					<form:hidden path="generateMode" value="${generateForm.generateMode}"/>
					<form:hidden path="generateFrom" value="0"/>
					<form:hidden path="databaseLog" value="${generateForm.databaseLog}"/>
					<button type="submit" class="btn qp-button qp-dialog-confirm" messageId="inf.sys.0025"><qp:message code="sc.generation.0008" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>