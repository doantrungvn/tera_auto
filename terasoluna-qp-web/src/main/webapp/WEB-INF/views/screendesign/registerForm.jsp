<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/module/change-entity-type.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				if($("#copyFromScreen").prop("checked")){
					$("#copyFromScreenSelect .input-group").prop("disabled", "false");
					$("#copyFromScreenSelect .input-group").removeClass("qp-disable");
					$("#tableSelection").hide();
					$("#copyScreenIdAutocompleteId").removeAttr("disabled");
					$("#requireCopy").show();
				} else {
					$("#copyFromScreenSelect .input-group").prop("disabled", "true");
					$("#copyFromScreenSelect .input-group").addClass("qp-disable");
					$("#copyFromScreenSelect .input-group").val("");
					$("#tableSelection").show();
					$("#copyScreenIdAutocompleteId").attr("disabled", "disabled");
					$("input[name=copyScreenId]").val("");
					$("#copyScreenIdAutocompleteId").val("");
					$("#requireCopy").hide();
					
					var screenPatternType = $("#screenPatternType").val();
					if(screenPatternType == 1){
// 						$("#tableElementType option[value=1]").hide();
						$("#screenRegisterForm").find("select[name$='.areaPatternType']").each(function(){
							$(this).hide();
						});
// 						$("#screenRegisterForm").find("select[name$='.tableMappingType']").each(function(){
// 							$(this).find("option[value=1]").hide();
// 						});
					} else {
// 						$("#tableElementType option[value=1]").show();
						$("#screenRegisterForm").find("select[name$='.areaPatternType']").each(function(){
							$(this).show();
						});
// 						$("#screenRegisterForm").find("select[name$='.tableMappingType']").each(function(){
// 							$(this).find("option[value=1]").show();
// 					});
				}
			}
			$("#screenRegisterForm").find("a.glyphicon-plus").click(function(){
				var screenPatternType = $("#screenPatternType").val();
				if(screenPatternType == 1){
// 					$("#tableElementType option[value=1]").hide();
					$("#screenRegisterForm").find("select[name$='.areaPatternType']").each(function(){
						$(this).hide();
					});
// 					$("#screenRegisterForm").find("select[name$='.tableMappingType']").each(function(){
// 						$(this).find("option[value=1]").hide();
// 					});
				} else {
// 					$("#tableElementType option[value=1]").show();
					$("#screenRegisterForm").find("select[name$='.areaPatternType']").each(function(){
						$(this).show();
					});
// 					$("#screenRegisterForm").find("select[name$='.tableMappingType']").each(function(){
// 						$(this).find("option[value=1]").show();
// 					});
					}
				});
			changeEntityTypeForDropDown();
			});
		</script>
		<script type="text/javascript">
			function enableCopyFromScreen(){
				if($("#copyFromScreen").prop("checked")){
					$("#copyFromScreenSelect .input-group").prop("disabled", "false");
					$("#copyFromScreenSelect .input-group").removeClass("qp-disable");
					$("#tableSelection").find("tr:gt(0)").remove();
					$("#tableSelection").hide();
					$("#copyScreenIdAutocompleteId").removeAttr("disabled");
					$("#requireCopy").show();
				} else {
					$("#copyFromScreenSelect .input-group").prop("disabled", "true");
					$("#copyFromScreenSelect .input-group").addClass("qp-disable");
					$("#copyFromScreenSelect .input-group").val("");
					$("#tableSelection").show();
					$("#copyScreenIdAutocompleteId").attr("disabled", "disabled");
					$("input[name=copyScreenId]").val("");
					$("#copyScreenIdAutocompleteId").val("");
					$("#copyScreenIdAutocompleteId").data("ui-autocomplete")._trigger("change");
					$("#requireCopy").hide();
				}
				changeEntityTypeForDropDown();
			};
			function changeScreenPattern(obj){
				//clear
				$("#copyScreenIdAutocompleteId").val('');
				$("#copyScreenId").val('');

				if($(obj).val() == 1){
					$("#trNotification").hide();
					$("#copyScreenIdAutocompleteId").attr("arg03", $(obj).val());
					$("#screenPatternHelp").attr("href", "javascript:");
					$("#screenRegisterForm").find("select[name$='.areaPatternType']").each(function(){
						$(this).hide();
					});
// 					$("#screenRegisterForm").find("select[name$='.tableMappingType']").each(function(){
// 						$(this).find("option[value=1]").hide();
// 					});
			        //$.fc.reCalIndex("tmp_list_table"); // refresh row index (No.)
			        //$.fc.alternateRowColorInTable("tmp_list_table"); // alternate color
				} else {
					$("#copyScreenIdAutocompleteId").attr("arg03", $(obj).val());
					$("#screenRegisterForm").find("select[name$='.areaPatternType']").each(function(){
						$(this).show();
					});
// 					$("#screenRegisterForm").find("select[name$='.tableMappingType']").each(function(){
// 						$(this).find("option[value=1]").show();
// 					});
				}
				if($(obj).val() == 3){
					$("#trNotification").hide();
					$("#screenPatternHelp").attr("href", "javascript:");
				}
				if($(obj).val() == 2 || $(obj).val() == 4){
					$("#trNotification").show();
					changeConfirmationSetting();
				}
			}
			function changeModule(event) {
				var value = $(event.target).next("input[type=hidden]").val();
				var nextInput = $(event.target).closest("tr").find("input[name$='functionDesignIdAutocomplete']");
				var nextHidden = nextInput.next("input[type=hidden]");
				nextInput.val("");
				nextInput.attr("arg01",value);
				nextHidden.val("");
				nextInput.data("ui-autocomplete")._trigger("change");

				var moduleId = $("input[name=moduleId]").val();
				$("#copyScreenIdAutocompleteId").val("");
				$("#copyScreenIdAutocompleteId").attr("arg04", moduleId);
				$("#copyScreenId").val('');
				if (event.item != undefined) {
					$("#copyScreenIdAutocompleteId").data("ui-autocomplete")._trigger("change");
					$("#screenRegisterForm").find("input[name$='confirmationType']").each(function(){
						if($(this).val() == event.item.output02){
							$(this).prop("checked", true);
						}
					});
					$("#screenRegisterForm").find("input[name$='completionType']").each(function(){
						if($(this).val() == event.item.output03){
							$(this).prop("checked", true);
						}
					});
				}
			};
			function changeConfirmationSetting(){
				if($("input:radio[name=completionType]:checked").val() == 2 && $("input:radio[name=confirmationType]:checked").val() == 2){
					$("#screenPatternHelp").attr("href", CONTEXT_PATH + "/media/images/screenpattern/complete.png");
				}
				if($("input:radio[name=completionType]:checked").val() == 2 && $("input:radio[name=confirmationType]:checked").val() != 2){
					$("#screenPatternHelp").attr("href", CONTEXT_PATH + "/media/images/screenpattern/complete_half.png");
				}
				if($("input:radio[name=completionType]:checked").val() == 1 && $("input:radio[name=confirmationType]:checked").val() == 2){
					$("#screenPatternHelp").attr("href", CONTEXT_PATH + "/media/images/screenpattern/confirmation.png");
				} 
				if($("input:radio[name=completionType]:checked").val() == 1 && $("input:radio[name=confirmationType]:checked").val() != 2){
					$("#screenPatternHelp").attr("href", CONTEXT_PATH + "/media/images/screenpattern/default.png");
				}
			}
			function changeTable(event) {
				var nextInput = $(event.target).closest("tr").find("input[name$='tblDesignCode']");
				nextInput.val(event.item.output01);
			}
		</script>
		<script type="text/template" id="tblSelectionTable-template">
			<tr>
				<td class="tableIndex">
					1
				</td>
				<td>
					<div class="input-group" style="width:100%;">
						<input autocomplete="off" name="moduleTableMappings[0].tblDesignIdAutocomplete" id="moduleTableMappings[0].tblDesignIdAutocompleteId" class="form-control qp-input-autocomplete ui-autocomplete-input" value="" optionvalue="optionValue" optionlabel="optionLabel" selectsqlid="getAllDomainTableMappingForAutocomplete" emptylabel="" onselectevent="changeTable" onchangeevent="" onremoveevent="" mustmatch="true" minlength="0"  arg01="${screenRegisterForm.projectId}" arg02="" arg03="" arg4="" arg05="" arg06="" arg07="" arg08="" arg09="" arg10="" arg11="" arg12="" arg13="" arg14="" arg15="" arg16="" arg17="" arg18="" arg19="" arg20="" placeholder="Search…" type="text">
						<input name="moduleTableMappings[0].tblDesignId" value="" type="hidden"><span class="input-group-addon dropdown-toggle" data-dropdown="dropdown" style="cursor: pointer;"> <span class="caret"></span></span>
						<input name="moduleTableMappings[0].tblDesignCode" value="" type="hidden">
					</div>
				</td>
				<td>
					<select id="tableElementType" name="moduleTableMappings[0].tableMappingType" class="form-control qp-input-select" onchange="changeEntityTypeForDropDown();"><option value="">-- Select --</option><option value="0">Single</option><option value="1">List</option></select>
				</td>
					<td><select id="tableDataType" name="moduleTableMappings[0].areaPatternType" class="form-control qp-input-select"><option value="">-- Select --</option><option value="1">Register</option><option value="2">Modify</option><option value="3">View</option></select></td>
				<td>
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tblSelectionTable', this); changeEntityTypeForDropDown();" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.screendesign"></qp:message></span></li>
         <li><span><qp:message code="sc.screendesign.0021"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="moduleSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/module/search"><qp:message code="sc.screendesign.0020"/></a>
		</qp:authorization>
		<qp:authorization permission="screendesignSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/screendesign/search"> <qp:message code="sc.screendesign.0019"/></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<form:form method="post" action="${pageContext.request.contextPath}/screendesign/register" modelAttribute="screenRegisterForm">
         <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
		<div class="panel panel-default qp-div-information">
			<div class="panel-heading">
				<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
				<span class="qp-heading-text"><qp:message code="sc.screendesign.0004"></qp:message></span>
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
						<th><form:label path="screenName"><qp:message code="sc.screendesign.0005"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
						<td><form:input path="screenName" value="${screenRegisterForm.screenName}" cssClass="form-control qp-input-text qp-convention-name" maxlength="200" /></td>
						<th><form:label path="screenCode"><qp:message code="sc.screendesign.0007"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
						<td><form:input path="screenCode" value="${screenRegisterForm.screenCode}" cssClass="form-control qp-input-text qp-convention-code" maxlength="200" /></td>
					</tr>
					<tr>
						<th>
							<form:label path="moduleId"><qp:message code="sc.screendesign.0006" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
						</th>
						<td>
							<qp:autocomplete optionValue="optionValue"
									selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
									name="moduleId" value="${screenRegisterForm.moduleId }"
									displayValue="${screenRegisterForm.moduleIdAutocomplete }"
									arg01="${screenRegisterForm.projectId}"
									optionLabel="optionLabel" onChangeEvent="changeModule"
									maxlength="200"
									arg03="1"
									arg04="0">
								</qp:autocomplete>
						</td>
						<th><form:label path="functionDesignId"><qp:message code="sc.functiondesign.0002" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label></th>
                       	<td>
                       		<qp:autocomplete optionValue="optionValue"
								selectSqlId="getAllFunctionDesignForAutocomplete"
								name="functionDesignId" value="${screenRegisterForm.functionDesignId }"
								displayValue="${screenRegisterForm.functionDesignIdAutocomplete }"
								arg01="${screenRegisterForm.moduleId}" arg02="20" arg03="0"
								optionLabel="optionLabel"
								maxlength="200">
							</qp:autocomplete>
                       	</td>
					</tr>
					<tr>
						<th>
							<form:label path="screenPatternType"><qp:message code="sc.screendesign.0009"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
						</th>
                        <%-- <td>
                        	<table style="width: 100%;">
								<colgroup>
									<col width="90%" />
									<col width="10%" />
								</colgroup>
								<tr>
									<td>
										<form:select path="screenPatternType" cssClass="form-control qp-input-select" onchange="changeScreenPattern(this)">
											<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
											<form:options items="${CL_SCREEN_PARTTERN_TYPES}" />
										</form:select>
									</td>
									<td style="text-align: center;">
										<span style="float: right; padding-right: 5px;"><a id="screenPatternHelp" class="glyphicon glyphicon-info-sign" onclick="return false;" href="${pageContext.request.contextPath}/resources/media/images/screenpattern/full.png"></a></span>
									</td>
								</tr>
							</table>
                        </td> --%>
                        <td>
							<form:select path="screenPatternType" cssClass="form-control qp-input-select" onchange="changeScreenPattern(this)">
								<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
								<form:option value="1"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('1')}"/></form:option>
								<form:option value="2"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('2')}"/></form:option>
								<form:option value="3"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('3')}"/></form:option>
								<form:option value="4"><qp:message code="${CL_SCREEN_PARTTERN_TYPES.get('4')}"/></form:option>
							</form:select>
						</td>
                        <th>
                        	<form:label path="templateType"><qp:message code="sc.screendesign.0008"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></form:label>
                        </th>
						<td>
							<form:select path="templateType" cssClass="form-control qp-input-select">
							 <form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
								<form:options items="${CL_TEMPLATE_TYPE}" />
							</form:select>
						</td>
					</tr>
					<tr id="trNotification">
                        <th><form:label path="confirmationType"><qp:message code="sc.screendesign.0010"></qp:message></form:label></th>
                        <td>
                          	<c:forEach var="item" items="${CL_MODULE_CONFIRM_TYPE}">
								<label><form:radiobutton path="confirmationType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/>&nbsp;<qp:message code="${CL_MODULE_CONFIRM_TYPE.get(item.key)}" /></label>
							</c:forEach>
						</td>
                        <th><form:label path="completionType"><qp:message code="sc.screendesign.0011"></qp:message></form:label></th>
                    	<td>
							<c:forEach var="item" items="${CL_MODULE_COMPLETE_TYPE}">
								<label><form:radiobutton path="completionType" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/>&nbsp;<qp:message code="${CL_MODULE_COMPLETE_TYPE.get(item.key)}" /></label>
							</c:forEach>
						</td>
                    </tr>
                    <tr>
                    	<th><form:label path="designMode"><qp:message code="sc.screendesign.0250" /></form:label></th>
                       	<td>
                       		<c:forEach var="item" items="${CL_SCREEN_DESIGN_MODE}">
								<label>
									<form:radiobutton path="designMode" value="${item.key}" class="qp-input-radio qp-input-radio-margin"/>&nbsp;
									<qp:message code="${CL_SCREEN_DESIGN_MODE.get(item.key)}" />
								</label>
							</c:forEach>
                       	</td>
						<th><form:label path="remark"><qp:message code="sc.sys.0028" /></form:label></th>
                       	<td><form:textarea path="remark" type="text" rows="3" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
					</tr>
                    <tr>
						<th><qp:message code="sc.screendesign.0012"/>&nbsp;<label class="qp-required-field" id="requireCopy"><qp:message code="sc.sys.0029" /></label></th>
						<td>
							<table style="width: 100%;">
								<colgroup>
									<col width="90%" />
									<col width="10%" />
								</colgroup>
								<tr>
									<td id="copyFromScreenSelect"><qp:autocomplete
											optionValue="optionValue" 
											arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}"
											arg02="20"
											arg03="${screenRegisterForm.screenPatternType}"
											arg04="${screenRegisterForm.moduleId}"
											selectSqlId="getScreenInfoByModuleId"
											emptyLabel="<qp:message code='sc.sys.0030'/>"
											optionLabel="optionLabel"
											name="copyScreenId"
											value="${screenRegisterForm.copyScreenId}"
											displayValue="${screenRegisterForm.copyScreenIdAutocomplete}"></qp:autocomplete>
									<td style="text-align: center;"> <form:checkbox id="copyFromScreen" path="isCopy" onclick="enableCopyFromScreen()"/>
									</td>
								</tr>
							</table>
						</td>
						<th></th>
						<td></td>
					</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-search-result" id="tableSelection">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span><span class="qp-heading-text"><qp:message code="sc.screendesign.0013"></qp:message></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-list" id="tblSelectionTable">
						<colgroup>
							<col width="5%" />
							<col width="30%" />
							<col width="30%" />
							<col width="30%" />
							<col width="5%" />
						</colgroup>
						<thead>
							<tr>
								<th><qp:message code="sc.sys.0004"></qp:message></th>
								<th><qp:message code="sc.screendesign.0014"></qp:message></th>
								<th><qp:message code="sc.screendesign.0015"></qp:message></th>
								<th><qp:message code="sc.screendesign.0016"></qp:message></th>
								<th></th>
							</tr>
						</thead>
						<c:forEach var="item" items="${screenRegisterForm.moduleTableMappings}" varStatus="status">
                        	<tr data-ar-rindex="${status.index }">
                            	<td class="qp-output-fixlength tableIndex">
                                	<qp:formatInteger value="${status.count}" />
                                </td>
                                <td>
                                    <qp:autocomplete arg01="${screenRegisterForm.projectId}" optionValue="optionValue" selectSqlId="getAllDomainTableMappingForAutocomplete" emptyLabel="" name="moduleTableMappings[${status.index}].tblDesignId" value="${screenRegisterForm.moduleTableMappings[status.index].tblDesignId}" displayValue="${screenRegisterForm.moduleTableMappings[status.index].tblDesignIdAutocomplete}" optionLabel="optionLabel"  mustMatch="true"  onChangeEvent="changeTable"></qp:autocomplete>
                                    <input name="moduleTableMappings[0].tblDesignCode" value="" type="hidden">
                                </td>
                                <td>
                               		<form:select id="tableElementType" name="tableElementType" cssClass="form-control qp-input-select" path="moduleTableMappings[${status.index}].tableMappingType" onchange="changeEntityTypeForDropDown();">
                                		<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
                             			<form:options items="${CL_ENTITY_TYPE}" />
                                	</form:select>
                                </td>
                                <td>
	                                <form:select id="tableDataType" name="tableDataType" class="form-control qp-input-select" path="moduleTableMappings[${status.index}].areaPatternType">
	                                	<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
	                            		<form:options items="${CL_QP_AREAPATTERNTYPE}" />
	                                </form:select>
                                </td>
                                <td>
                                    <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('tblSelectionTable', this); changeEntityTypeForDropDown();" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                </td>
                            </tr>
                        </c:forEach>
                                <%-- <c:if test="${empty screenRegisterForm.moduleTableMappings && !screenRegisterForm.isCopy}"> 
                                    <td class="qp-output-fixlength tableIndex">1</td>
                                    <td><qp:autocomplete arg01="${screenRegisterForm.projectId}" optionLabel="optionLabel" selectSqlId="getAllDomainTableMappingForAutocomplete" name="moduleTableMappings[0].tblDesignId" value="${screenRegisterForm.moduleTableMappings[0].tblDesignId}" displayValue="${screenRegisterForm.moduleTableMappings[0].tblDesignIdAutocomplete}" optionValue="optionValue"  mustMatch="true"></qp:autocomplete></td>
                                    <td>
                                    <form:select id="tableElementType" name="moduleTableMappings[0].tableMappingType" class="form-control qp-input-select" path="moduleTableMappings[0].tableMappingType" >
                                    
                                     <form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
                                     <form:options items="${CL_ENTITY_TYPE}" />
                                     </form:select></td>
                                   <td><form:select id="tableDataType" name="moduleTableMappings[0].areaPatternType" class="form-control qp-input-select" path="moduleTableMappings[0].areaPatternType" >
                                    <form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
                                    	<form:options items="${CL_QP_AREAPATTERNTYPE}" />
                                    </form:select></td>
                                    <td>
                                        <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                    </td>
                                </c:if> --%>
						
					</table>
					<div class="qp-add-left">
						<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" href="javascript:void(0)"></a>
					</div>
					
				</div>
			</div>
			
			<div class="qp-div-action">
				<qp:authorization permission="screendesignRegister">
					<button type="button" class="btn qp-button qp-dialog-confirm"><qp:message code="sc.sys.0031" /></button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
