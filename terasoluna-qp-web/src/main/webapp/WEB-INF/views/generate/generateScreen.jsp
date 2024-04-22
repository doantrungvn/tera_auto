<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script>
			$(document).ready(function(){
				$("#generateScreenForm").find("a.glyphicon-plus").click(function(){
					var projectId = $("#generateScreenForm").find("input[name$='projectId']").val();
					if (projectId) {
						$("#generateScreenForm").find("input[name$='.tblDesignIdAutocomplete']").each(function(){
							$(this).attr("arg01", projectId);
						});
					}
				});
				changeEntityType();
			});
			
			function changeType(event) {
				var moduleId = $("input[name=moduleId]").val();
				if(moduleId == ""){
					$("#generateScreenForm").find("input[name$='confirmationType']").removeAttr("checked");
					$("#generateScreenForm").find("input[name$='completionType']").removeAttr("checked");
				}else{
					$("#generateScreenForm").find("input[name$='confirmationType']").each(function(){
						if($(this).val() == event.item.output02){
							$(this).prop("checked", true);
						}
					});
					$("#generateScreenForm").find("input[name$='completionType']").each(function(){
						if($(this).val() == event.item.output03){
							$(this).prop("checked", true);
						}
					});
					$("#generateScreenForm").find("input[name$='moduleName']").each(function(){
						$(this).val(event.item.optionLabel);
					});
					$("#generateScreenForm").find("input[name$='moduleCode']").each(function(){
						$(this).val(event.item.output01);
					});
				}
			};
			
			function changeTableAC(obj){
				var value = $(obj.target).next("input[type=hidden]").val();
				if(obj.item != undefined){
					$(obj.target).closest("tr").find("input[name$='.tblDesignCode']").val(obj.item.output01);
				}
			};
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/module/change-entity-type.js"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-name">
	</tiles:putAttribute>
	
	<tiles:putAttribute name="breadcrumb">
		 <li><span><qp:message code="tqp.generation"></qp:message></span></li>
		 <li><span><qp:message code="tqp.generatescreen"/></span></li>
	</tiles:putAttribute>
	
	 <tiles:putAttribute name="header-link">
		<qp:authorization permission="moduleSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/module/search"><qp:message code="sc.module.0019" /></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="generateScreenForm" action="${pageContext.request.contextPath}/generation/generatescreen">
		<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
		<form:hidden path="projectId"/>
		<script id="tmp_list_table-template" type="text/template">
			<tr>
				<td class="qp-output-fixlength tableIndex">1</td>
				<td>
					<div class="input-group" style="width:100%">
						<input id="moduleTableMappings[0].tblDesignId" class="form-control qp-input-autocomplete ui-autocomplete-input" type="text" 
							placeholder="<qp:message code='sc.sys.0034' />" 
							arg20="" arg19="" arg18="" arg17="" arg16="" arg15="" arg14="" arg13="" arg12="" arg11="" arg10="" arg09="" arg08="" arg07="" arg06="" arg05="" arg4="" arg03="" arg02="" arg01="" 
							minlength="0"
							mustmatch="true" 
							onremoveevent="" 
							onchangeevent="changeTableAC" 
							onselectevent="" 
							emptylabel="" 
							selectsqlid="getAllDomainTableMappingForAutocomplete" 
							optionlabel="optionLabel" 
							optionvalue="optionValue" 
							name="moduleTableMappings[0].tblDesignIdAutocomplete" 
							displayValue="${generateScreenForm.moduleTableMappings[0].tblDesignIdAutocomplete}" 
							autocomplete="on" 
							maxlength="200" 
						/>
						<input type="hidden" name="moduleTableMappings[0].tblDesignId" value="" />
					</div>
						<input type="hidden" name="moduleTableMappings[0].tblDesignCode" value="" />
				</td>
				</td>
				<td><select name="moduleTableMappings[0].tableMappingType" class="form-control qp-input-select" onchange="changeEntityType()"><option value=""><qp:message code="sc.sys.0030" /></option><c:forEach var="item" items="${CL_ENTITY_TYPE }"><option value="${item.key}">${item.value}</option></c:forEach></select></td>
				<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.removeRowJS('dynamic', this); changeEntityType();" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
			</tr>
			</script>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.module.0030" /></span>
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
							<th><qp:message code="sc.module.0007" /></th>
							<td><qp:formatText value="${generateScreenForm.moduleName}" /></td>
							<th><qp:message code="sc.module.0008" /></th>
							<td><qp:formatText value="${generateScreenForm.moduleCode}" /></td>
						</tr>
						<tr>
							<th><qp:message code="sc.sys.0055" /></th>
							<td><qp:message code="${CL_DESIGN_STATUS.get(generateScreenForm.status.toString())}" /></td>
							<th><qp:message code="sc.businesstype.0001" /></th>
							<td><qp:formatText value="${generateScreenForm.businessTypeName}" /></td>
						</tr>
						 <tr>
							<th>
								<form:label path="businessGenerateName"><qp:message code="sc.generation.0010" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
							</th>
							<td><form:input path="businessGenerateName" type="text" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200" /></td>
							<th>
								<form:label path="businessGenerateCode"><qp:message code="sc.generation.0011" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
							</th>
							<td><form:input path="businessGenerateCode" type="text" cssClass="form-control qp-input-text qp-convention-code-row" maxlength="50" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-select">
				<div class="panel-heading">
					<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.module.0020" /></span>
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
								<th><form:label path="confirmationType"><qp:message code="sc.module.0009" /></form:label></th>
								<td>
									<c:forEach var="item" items="${CL_MODULE_CONFIRM_TYPE}">
										<label><form:radiobutton path="confirmationType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/><qp:message code="${CL_MODULE_CONFIRM_TYPE.get(item.key)}" /></label>
									</c:forEach>
								</td>
								<th><form:label path="completionType"><qp:message code="sc.module.0010" /></form:label>
								</th>
								<td>
									<c:forEach var="item" items="${CL_MODULE_COMPLETE_TYPE}">
										<label><form:radiobutton path="completionType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/><qp:message code="${CL_MODULE_COMPLETE_TYPE.get(item.key)}" /></label>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th><form:label path="screenPatternTypes"><qp:message code="sc.module.0017" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
								<td colspan="3">
									<c:forEach var="item" items="${CL_SCREEN_PARTTERN_TYPES}">
										<label><form:checkbox path="screenPatternTypes" value="${item.key}" cssClass="qp-input-checkbox-margin qp-input-checkbox"/><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get(item.key)}" /></label>
									</c:forEach>
								</td>
							</tr>
					 </table>
				</div>
				<div class="panel-body">
				   <table id="tmp_list_table" class="table table-bordered qp-table-list">
					   <colgroup>
						   <col />
						   <col width="50%" />
						   <col width="50%" />
						   <col />
					   </colgroup>
					<thead>
					<tr>
						<th><qp:message code="sc.sys.0004" /></th>
						<th><qp:message code="sc.tabledesign.0019" /> <label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029" /></label></th>
						<th><qp:message code="sc.module.0015" /> <label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029" /></label></th>
						<th></th>
					</tr>
					</thead>
						<tbody>
							<c:forEach var="item" items="${generateScreenForm.moduleTableMappings}" varStatus="status">
								<tr>
									<td class="qp-output-fixlength tableIndex">
										<qp:formatInteger value="${status.count}" />
									</td>
									<td>
										<qp:autocomplete 
											name="moduleTableMappings[${status.index}].tblDesignId" 
											optionLabel="optionLabel" 
											optionValue="optionValue" 
											selectSqlId="getAllDomainTableMappingForAutocomplete" 
											value="${generateScreenForm.moduleTableMappings[status.index].tblDesignId}" 
											displayValue="${generateScreenForm.moduleTableMappings[status.index].tblDesignIdAutocomplete}" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" 
											mustMatch="true" 
											maxlength="200"
											onChangeEvent="changeTableAC"
											>
										</qp:autocomplete>
										<form:hidden path="moduleTableMappings[${status.index}].tblDesignCode" />
									</td>
									<td>
										<form:select cssClass="form-control qp-input-select" path="moduleTableMappings[${status.index}].tableMappingType" onchange="changeEntityType()">
											<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
											<form:options items="${CL_ENTITY_TYPE}" />
										</form:select>
									</td>
									<td>
										<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Remove module table" onclick="$.qp.removeRowJS('tmp_list_table', this); changeEntityType();" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="qp-add-left">
						<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
					</div>
				</div>
			</div>
			<div class="qp-div-action">
				<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
					<qp:authorization permission="generationGeneratescreen">
						<form:hidden path="moduleId"/>
						<form:hidden path="moduleCode"/>
						<form:hidden path="moduleName"/>
						<form:hidden path="businessTypeName"/>
						<form:hidden path="status"/>
						<button type="submit" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.generation.0008"/></button>
					</qp:authorization>
				</c:if>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>