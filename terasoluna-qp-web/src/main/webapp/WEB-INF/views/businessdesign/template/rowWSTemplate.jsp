<!-- input bean -->
<script id="tbl_inputbean_list_define-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="text" name="lstInputBean[].inputBeanName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
						<input type="hidden" name="lstInputBean[].inputBeanId" />
						<input type="hidden" name="lstInputBean[].screenItemId" />
						<input type="hidden" name="lstInputBean[].inputBeanType" value="1"/>
						<input type="hidden" name="lstInputBean[].parentInputBeanId" />
						<input type="hidden" name="lstInputBean[].itemSequenceNo" />
						<input type="hidden" name="lstInputBean[].objectFlg" value="true"/>
					</div>
				</div>
			</div>
		</td>
		<td>
			<input type="text" name="lstInputBean[].inputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" />
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstInputBean[].groupBaseTypeId" value=""/>
				<select name="lstInputBean[].dataType" class="form-control qp-input-select" onchange="$.qp.businessdesign.objectTypeChange(this);">
					<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
					<c:forEach var="item" items="${CL_BD_DATATYPE}">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
				</select> 
				<span class="input-group-addon">
					<label><input type="checkbox" aria-label="Array" name="lstInputBean[].arrayFlg">Array</label>
				</span>
			</div>
		</td>
		<td class="bd-in-getscope" align="center">
			<div class="dropdown">
				<button class="btn btn-info glyphicon glyphicon-menu-hamburger qp-link-button qp-link-action" type="button" data-toggle="dropdown"></button>
				<ul class="dropdown-menu dropdown-nav-left">
					<li>
						<input type="hidden" name="lstInputBean[].jsonValidationInputs" />
						<a class="qp-link qp-cursor" onclick="openModalValidationCheckDetail(this)"><qp:message code="sc.businesslogicdesign.0263"/></a>
					</li>
				</ul>
			</div>
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
		</td>
	</tr>
</script>
<script id="tbl_inputbean_list_define-action-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" rowbtn ="addchildrenbtn">
		<td colspan="100%">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'tbl_inputbean_list_define',templateId:'tbl_inputbean_list_define-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>
<script id="tbl_inputbean_list_define-entity-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstInputBean[].inputBeanId"/>
						<input type="hidden" name="lstInputBean[].parentInputBeanId" />
						<input type="hidden" name="lstInputBean[].itemSequenceNo" />
						<input type="hidden" name="lstInputBean[].inputBeanName"/>
						<input type="hidden" name="lstInputBean[].objectFlg" value="true"/>
						<qp:autocomplete name = "lstInputBean[].tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="bdOnChangeTableDesignOfIn"></qp:autocomplete>
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstInputBean[].inputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstInputBean[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstInputBean[].dataType" value="14"/>
				<select name="lstInputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);" oldvalue="14">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "14"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "14"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstInputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
		<td class="bd-in-getscope" align="center">
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
		</td>
    </tr>
</script>
<script id="tbl_inputbean_list_define-column-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;"> 
                    	<input type="hidden" name="lstInputBean[].inputBeanId"/>
						<input type="hidden" name="lstInputBean[].screenItemId" />
						<input type="hidden" name="lstInputBean[].inputBeanType" value="1"/>
                    	<input type="hidden" name="lstInputBean[].parentInputBeanId" />
                    	<input type="hidden" name="lstInputBean[].itemSequenceNo" />
                    	<input type="hidden" name="lstInputBean[].inputBeanName"/>
                    	<input type="hidden" name="lstInputBean[].tblDesignId"/>
                    	<input type="hidden" name="lstInputBean[].columnId"/>
                    	<input type="hidden" name="lstInputBean[].objectFlg" value="false"/>
						<input type="hidden" name="lstInputBean[].objectType"/>
						<input type="hidden" name="lstInputBean[].objectId"/>
						<input type="hidden" name="lstInputBean[].createdMessageFlg" value="false"/>
						<input type="hidden" name="lstInputBean[].messageStringAutocomplete"/>
						<input type="hidden" name="lstInputBean[].mappingScreenItemFlag" value="false"/>
                    	<label name="lstInputBean[].inputBeanName" class="qp-output-text"></label>
					</div>
                </div>
            </div>
        </td>
        <td>
            <input type="hidden" name="lstInputBean[].inputBeanCode"/>
            <label name="lstInputBean[].inputBeanCode" class="qp-output-text"></label>
        </td>
        <td>
            <div class="input-group">
                <input type="hidden" name="lstInputBean[].groupBaseTypeId" value=""/>
                <input type="hidden" name="lstInputBean[].dataType"/>
                <label name="lstInputBean[].dataType" class="qp-output-text"></label>
                <input type="hidden" name="lstInputBean[].arrayFlg" value="false"/>
            </div>
        </td>
        <td class="bd-in-getscope" align="center">
		</td>
        <td>
			<a style='display:none' class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_inputbean_list_define-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstInputBean[].groupId"/>
						<input type="hidden" name="lstInputBean[].tableIndex"/>
						<input type="hidden" name="lstInputBean[].itemSequenceNo" />
						<input type="hidden" name="lstInputBean[].inputBeanId"/>
						<input type="hidden" name="lstInputBean[].screenItemId" />
						<input type="hidden" name="lstInputBean[].inputBeanType" value="1"/>
						<input type="hidden" name="lstInputBean[].parentInputBeanId" />
						<input type="hidden" name="lstInputBean[].inputBeanName"/>
						<input type="hidden" name="lstInputBean[].objectType"/>
						<input type="hidden" name="lstInputBean[].objectFlg" value="true"/>
						<qp:autocomplete name = "lstInputBean[].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObjectForBD" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeCommonObjectOfIn"></qp:autocomplete>
						<input type="hidden" name="lstInputBean[].moduleId"/>
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstInputBean[].inputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstInputBean[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstInputBean[].dataType" value="16"/>
				<select name="lstInputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);"  oldvalue="16">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE}">
						<c:if test='${item.key == "16"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "16"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstInputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
		<td class="bd-in-getscope" align="center">
		</td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
		</td>
    </tr>
</script>
<script id="tbl_inputbean_list_define-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstInputBean[].groupId"/>
						<input type="hidden" name="lstInputBean[].tableIndex"/>
						<input type="hidden" name="lstInputBean[].itemSequenceNo" />
						<input type="hidden" name="lstInputBean[].inputBeanId"/>
						<input type="hidden" name="lstInputBean[].screenItemId" />
						<input type="hidden" name="lstInputBean[].inputBeanType" value="1"/>
						<input type="hidden" name="lstInputBean[].parentInputBeanId" />
						<input type="hidden" name="lstInputBean[].inputBeanName"/>
						<input type="hidden" name="lstInputBean[].objectType"/>
						<input type="hidden" name="lstInputBean[].objectFlg" value="true"/>
						<qp:autocomplete name = "lstInputBean[].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForBD" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeExternalObjectOfIn"></qp:autocomplete>
						<input type="hidden" name="lstInputBean[].moduleId"/>
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstInputBean[].inputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstInputBean[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstInputBean[].dataType" value="17"/>
				<select name="lstInputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);"  oldvalue="17">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE}">
						<c:if test='${item.key == "17"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "17"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstInputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
		<td class="bd-in-getscope" align="center">
		</td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
		</td>
    </tr>
</script>

 <!-- output bean -->
<script id="tbl_outputbean_list_define-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex" style ="vertical-align: middle"></span>
				</div>
				
				<div class="pull-right" style="height:100%">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstOutputBean[].outputBeanId"/>
						<input type="hidden" name="lstOutputBean[].outputBeanType" value="1"/>
						<input type="hidden" name="lstOutputBean[].parentOutputBeanId" />
						<input type="hidden" name="lstOutputBean[].itemSequenceNo" />
						<input type="hidden" name="lstOutputBean[].objectFlg" value="true"/>
						<input  type="text" name="lstOutputBean[].outputBeanName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstOutputBean[].outputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstOutputBean[].groupBaseTypeId" value=""/>
            	<select name="lstOutputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                        <c:forEach var="item" items="${CL_BD_DATATYPE }">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstOutputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>
<script id="tbl_outputbean_list_define-action-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" rowbtn ="addchildrenbtn">
        <td colspan="100%">
            <div style="height:100%">
                <div class="pull-left" style="height:100%;vertical-align: middle;">
                    <a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'tbl_outputbean_list_define',templateId:'tbl_outputbean_list_define-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
                </div>
            </div>
        </td>
    </tr>
</script>
<script id="tbl_outputbean_list_define-column-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div class="vertical-midle-div">
                    <span  class="ar-groupIndex"  style ="vertical-align: middle"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
                    	<input type="hidden" name="lstOutputBean[].outputBeanId"/>
						<input type="hidden" name="lstOutputBean[].outputBeanType" value="1"/>
                    	<input type="hidden" name="lstOutputBean[].parentOutputBeanId" />
                    	<input type="hidden" name="lstOutputBean[].itemSequenceNo" />
                   	 	<input type="hidden" name="lstOutputBean[].outputBeanName"/>
                    	<input type="hidden" name="lstOutputBean[].tblDesignId"/>
                    	<input type="hidden" name="lstOutputBean[].columnId"/>
                    	<input type="hidden" name="lstOutputBean[].objectFlg" value="false"/>
						<input type="hidden" name="lstOutputBean[].objectType"/>
						<input type="hidden" name="lstOutputBean[].objectId"/>
                    	<label name="lstOutputBean[].outputBeanName" class="qp-output-text"></label>
					</div>
                </div>
            </div>
        </td>
        <td>
            <input type="hidden" name="lstOutputBean[].outputBeanCode"/>
            <label name="lstOutputBean[].outputBeanCode" class="qp-output-text"></label>
        </td>
        <td>
            <div class="input-group">
                <input type="hidden" name="lstOutputBean[].groupBaseTypeId" value=""/>
                <input type="hidden" name="lstOutputBean[].dataType" addChildFlg="false"/>
                <label name="lstOutputBean[].dataType" class="qp-output-text"></label>
                <input type="hidden" name="lstOutputBean[].arrayFlg" value="false"/>
            </div>
        </td>
        <td>
        </td>
    </tr>
</script>
<script id="tbl_outputbean_list_define-entity-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex" style ="vertical-align: middle"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstOutputBean[].outputBeanId"/>
						<input type="hidden" name="lstOutputBean[].outputBeanType" value="1"/>
						<input type="hidden" name="lstOutputBean[].parentOutputBeanId" />
						<input type="hidden" name="lstOutputBean[].itemSequenceNo" />
						<input type="hidden" name="lstOutputBean[].outputBeanName"/>
						<input type="hidden" name="lstOutputBean[].objectFlg" value="true"/>
						<qp:autocomplete name = "lstOutputBean[].tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="bdOnChangeTableDesignOfOu"></qp:autocomplete>
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstOutputBean[].outputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstOutputBean[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstOutputBean[].dataType" value="14"/>
				<select name="lstOutputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);" oldvalue="14">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "14"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "14"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstOutputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>
<script id="tbl_outputbean_list_define-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"  style ="vertical-align: middle"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>	
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstOutputBean[].groupId"/>
						<input type="hidden" name="lstOutputBean[].outputBeanType" value="1"/>
						<input type="hidden" name="lstOutputBean[].tableIndex"/>
						<input type="hidden" name="lstOutputBean[].itemSequenceNo" />
						<input type="hidden" name="lstOutputBean[].outputBeanId"/>
						<input type="hidden" name="lstOutputBean[].parentOutputBeanId" />
						<input type="hidden" name="lstOutputBean[].outputBeanName"/>
						<input type="hidden" name="lstOutputBean[].objectType"/>
						<input type="hidden" name="lstOutputBean[].objectFlg" value="true"/>
						<qp:autocomplete name = "lstOutputBean[].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObjectForBD" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeCommonObjectOfOu"></qp:autocomplete>
						<input type="hidden" name="lstOutputBean[].moduleId"/>
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstOutputBean[].outputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstOutputBean[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstOutputBean[].dataType" value="16"/>
				<select name="lstOutputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);"  oldvalue="16">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "16"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "16"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstOutputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>
<script id="tbl_outputbean_list_define-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex" style ="vertical-align: middle"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">	
					<span style="vertical-align: middle; display: inline-block;  height: 100%;"></span>
					<div style="width:98%; display: inline-block; vertical-align: middle;">
						<input type="hidden" name="lstOutputBean[].outputBeanType" value="1"/>
						<input type="hidden" name="lstOutputBean[].groupId"/>
						<input type="hidden" name="lstOutputBean[].tableIndex"/>
						<input type="hidden" name="lstOutputBean[].itemSequenceNo" />
						<input type="hidden" name="lstOutputBean[].outputBeanId"/>
						<input type="hidden" name="lstOutputBean[].parentOutputBeanId" />
						<input type="hidden" name="lstOutputBean[].outputBeanName"/>
						<input type="hidden" name="lstOutputBean[].objectType"/>
						<input type="hidden" name="lstOutputBean[].objectFlg" value="true"/>
						<qp:autocomplete name = "lstOutputBean[].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForBD" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeExternalObjectOfOu"></qp:autocomplete>
						<input type="hidden" name="lstOutputBean[].moduleId"/>
					</div>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstOutputBean[].outputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="lstOutputBean[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstOutputBean[].dataType" value="17"/>
				<select name="lstOutputBean[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);"  oldvalue="17">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "17"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "17"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstOutputBean[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>

<!-- object definition -->
<script id="tbl_objectdefinition_list_define-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionId"/>
					<input type="hidden" name="lstObjectDefinition[].parentObjectDefinitionId" />
					<input type="hidden" name="lstObjectDefinition[].itemSequenceNo" />
					<input type="hidden" name="lstObjectDefinition[].objectFlg" value="true"/>
					<input type="text" name="lstObjectDefinition[].objectDefinitionName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstObjectDefinition[].objectDefinitionCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
        <td>
			<div class="input-group">
				<input type="hidden" name="lstObjectDefinition[].groupBaseTypeId" value=""/>
            	<select name="lstObjectDefinition[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstObjectDefinition[].arrayFlg">Array</label></span>
			</div>
        </td>
        <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>
<script id="tbl_objectdefinition_list_define-action-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" action-type="objectdefinition">
        <td colspan="5">
            <div style="height:100%">
                <div class="pull-left" style="height:100%;vertical-align: middle;">
                    <a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'tbl_objectdefinition_list_define',templateId:'tbl_objectdefinition_list_define-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
                </div>
            </div>
        </td>
    </tr>
</script>
<script id="tbl_objectdefinition_list_define-column-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
                    <input type="hidden" name="lstObjectDefinition[].objectDefinitionId"/>
                    <input type="hidden" name="lstObjectDefinition[].parentObjectDefinitionId" />
                    <input type="hidden" name="lstObjectDefinition[].itemSequenceNo" />
                    <input type="hidden" name="lstObjectDefinition[].objectDefinitionName"/>
                    <input type="hidden" name="lstObjectDefinition[].tblDesignId"/>
                    <input type="hidden" name="lstObjectDefinition[].columnId"/>
                    <input type="hidden" name="lstObjectDefinition[].objectFlg" value="false"/>
					<input type="hidden" name="lstObjectDefinition[].objectType"/>
					<input type="hidden" name="lstObjectDefinition[].objectId"/>
                    <label name="lstObjectDefinition[].objectDefinitionName" class="qp-output-text"></label>
                </div>
            </div>
        </td>
        <td>
            <input type="hidden" name="lstObjectDefinition[].objectDefinitionCode"/>
            <label name="lstObjectDefinition[].objectDefinitionCode" class="qp-output-text"></label>
        </td>
        <td>
            <div class="input-group">
                <input type="hidden" name="lstObjectDefinition[].groupBaseTypeId" value=""/>
                <input type="hidden" name="lstObjectDefinition[].dataType" addChildFlg="false"/>
                <label name="lstObjectDefinition[].dataType" class="qp-output-text"></label>
                <input type="hidden" name="lstObjectDefinition[].arrayFlg" value="false"/>
            </div>
        </td>
        <td>
        </td>
    </tr>
</script>
<script id="tbl_objectdefinition_list_define-entity-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionId"/>
					<input type="hidden" name="lstObjectDefinition[].parentObjectDefinitionId" />
					<input type="hidden" name="lstObjectDefinition[].itemSequenceNo" />
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionName"/>
					<input type="hidden" name="lstObjectDefinition[].objectFlg" value="true"/>
					<qp:autocomplete name = "lstObjectDefinition[].tblDesignId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetTableForBD" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="bdOnChangeTableDesignOfOb"></qp:autocomplete>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstObjectDefinition[].objectDefinitionCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
        <td>
			<div class="input-group">
				<input type="hidden" name="lstObjectDefinition[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstObjectDefinition[].dataType" value="14"/>
				<select name="lstObjectDefinition[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);" oldvalue="14">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "14"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "14"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstObjectDefinition[].arrayFlg">Array</label></span>
			</div>
        </td>
        <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>
<script id="tbl_objectdefinition_list_define-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="lstObjectDefinition[].groupId"/>
					<input type="hidden" name="lstObjectDefinition[].tableIndex"/>
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionId"/>
					<input type="hidden" name="lstObjectDefinition[].parentObjectDefinitionId" />
					<input type="hidden" name="lstObjectDefinition[].itemSequenceNo" />
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionName"/>
					<input type="hidden" name="lstObjectDefinition[].objectType"/>
					<input type="hidden" name="lstObjectDefinition[].objectFlg" value="true"/>
					<qp:autocomplete name = "lstObjectDefinition[].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObjectForBD" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeCommonObjectOfOb"></qp:autocomplete>
					<input type="hidden" name="lstObjectDefinition[].moduleId"/>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstObjectDefinition[].objectDefinitionCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
        <td>
			<div class="input-group">
				<input type="hidden" name="lstObjectDefinition[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstObjectDefinition[].dataType" value="16"/>
				<select name="lstObjectDefinition[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);" oldvalue="16">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "16"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "16"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstObjectDefinition[].arrayFlg">Array</label></span>
			</div>
        </td>
        <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>
<script id="tbl_objectdefinition_list_define-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div class="vertical-midle-div">
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">	
					<input type="hidden" name="lstObjectDefinition[].groupId"/>
					<input type="hidden" name="lstObjectDefinition[].tableIndex"/>
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionId"/>
					<input type="hidden" name="lstObjectDefinition[].parentObjectDefinitionId" />
					<input type="hidden" name="lstObjectDefinition[].itemSequenceNo" />
					<input type="hidden" name="lstObjectDefinition[].objectDefinitionName"/>
					<input type="hidden" name="lstObjectDefinition[].objectType"/>
					<input type="hidden" name="lstObjectDefinition[].objectFlg" value="true"/>
					<qp:autocomplete name = "lstObjectDefinition[].objectId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForBD" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${businessDesignForm.moduleId}" mustMatch="true" onChangeEvent="bdOnChangeExternalObjectOfOb"></qp:autocomplete>
					<input type="hidden" name="lstObjectDefinition[].moduleId"/>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="lstObjectDefinition[].objectDefinitionCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
        <td>
			<div class="input-group">
				<input type="hidden" name="lstObjectDefinition[].groupBaseTypeId" value=""/>
				<input type="hidden" name="lstObjectDefinition[].dataType" value="17"/>
				<select name="lstObjectDefinition[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.businessdesign.objectTypeChange(this);" oldvalue="17">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_BD_DATATYPE }">
						<c:if test='${item.key == "17"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "17"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="lstObjectDefinition[].arrayFlg">Array</label></span>
			</div>
        </td>
        <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a>
		</td>
    </tr>
</script>

<!-- common template -->
<script id="tbl-component-nestedlogicnode-template" type="text/x-jquery-tmpl">
	<div class="execution-class \${cssClass}" id="\${sequenceLogicId}" componenttype = "\${componentType}" ondblclick="\${actionPath}" style="left: \${xCoordinates}px; top: \${yCoordinates}px;" add="off"
		data-toggle="tooltip" data-placement="right" title="\${remark}">
		<a class="qp-link-popup" href="${pageContext.request.contextPath}/businessdesign/designBlogic" style="display: none;"></a>
		<div style="border: 2px solid orange; padding: 3px; border-radius: 8px;">
			<img src="\${imagePath}" class="qp-bdesign-node-image"/>
			\${prefixLabel}<span class="component-name">\${sequenceLogicName}</span>
		</div>
		<div class="ep"></div>
		<input type="hidden" name="componentElement" value="\${strData}">
	</div>
</script>