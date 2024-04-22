<script id="tbl_attribute_list_define-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="commonObjectAttributes[].groupId"/>
					<input type="hidden" name="commonObjectAttributes[].tableIndex"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="commonObjectAttributes[].parentCommonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].saveFlg" value="true"/>
					<input type="text" name="commonObjectAttributes[].commonObjectAttributeName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="commonObjectAttributes[].commonObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="commonObjectAttributes[].dataType" value=""/>
            	<select name="commonObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                        <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_OBJECT}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="commonObjectAttributes[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
		</td>
    </tr>
</script>
<script id="tbl_attribute_list_define-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">	
					<input type="hidden" name="commonObjectAttributes[].groupId"/>
					<input type="hidden" name="commonObjectAttributes[].tableIndex"/>
					<input type="hidden" name="commonObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeName"/>
					<input type="hidden" name="commonObjectAttributes[].saveFlg" value="true"/>
					<input type="hidden" name="commonObjectAttributes[].parentCommonObjectAttributeId"/>
					<qp:autocomplete name = "commonObjectAttributes[].objectDefinitionId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObject" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${commonObjectDefinitionForm.commonObjectDefinitionId}" arg04="" mustMatch="true" onChangeEvent="onChangeCommonObject"></qp:autocomplete>
					<input type="hidden" name="commonObjectAttributes[].moduleId"/>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="commonObjectAttributes[].commonObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="commonObjectAttributes[].dataType" value="16"/>
				<select name="commonObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);"  oldvalue="16">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_OBJECT}">
						<c:if test='${item.key == "16"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "16"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="commonObjectAttributes[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
		</td>
    </tr>
</script>
<script id="tbl_attribute_list_define-common-object-column-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="commonObjectAttributes[].groupId"/>
					<input type="hidden" name="commonObjectAttributes[].tableIndex"/>
					<input type="hidden" name="commonObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeName"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectDefinitionId"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].parentCommonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].saveFlg" value="false"/>
					<input type="hidden" name="commonObjectAttributes[].objectDefinitionId"/>
					<label name="commonObjectAttributes[].commonObjectAttributeName" class="qp-output-text"></label>
				</div>
			</div>
		</td>
        <td>
			<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeCode"/>
			<label name="commonObjectAttributes[].commonObjectAttributeCode" class="qp-output-text"></label>
		</td>
        <td>
			<div class="input-group">
            	<input type="hidden" name="commonObjectAttributes[].dataType" />
				<label name="commonObjectAttributes[].dataType" class="qp-output-text"></label>
				<input type="hidden" name="commonObjectAttributes[].arrayFlg"/>
			</div>
        </td>
		<td>
		</td>
    </tr>
</script>
<script id="tbl_attribute_list_define-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="commonObjectAttributes[].groupId"/>
					<input type="hidden" name="commonObjectAttributes[].tableIndex"/>
					<input type="hidden" name="commonObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeName"/>
					<input type="hidden" name="commonObjectAttributes[].saveFlg" value="true"/>
					<input type="hidden" name="commonObjectAttributes[].parentCommonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeId"/>
					<qp:autocomplete name = "commonObjectAttributes[].objectDefinitionId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObject" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" arg04="" mustMatch="true" onChangeEvent="onChangeExternalObject"></qp:autocomplete>
					<input type="hidden" name="commonObjectAttributes[].moduleId"/>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="commonObjectAttributes[].commonObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="commonObjectAttributes[].dataType" value="17"/>
				<select name="commonObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);"  oldvalue="17">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_OBJECT}">
						<c:if test='${item.key == "17"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "17"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="commonObjectAttributes[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
		</td>
    </tr>
</script>
<script id="tbl_attribute_list_define-external-object-column-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="hidden" name="commonObjectAttributes[].groupId"/>
					<input type="hidden" name="commonObjectAttributes[].tableIndex"/>	
					<input type="hidden" name="commonObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeName"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectDefinitionId"/>
					<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].objectDefinitionId"/>
					<input type="hidden" name="commonObjectAttributes[].parentCommonObjectAttributeId"/>
					<input type="hidden" name="commonObjectAttributes[].saveFlg" value="false"/>
					<label name="commonObjectAttributes[].commonObjectAttributeName" class="qp-output-text"></label>
				</div>
			</div>
		</td>
        <td>
			<input type="hidden" name="commonObjectAttributes[].commonObjectAttributeCode"/>
			<label name="commonObjectAttributes[].commonObjectAttributeCode" class="qp-output-text"></label>
		</td>
        <td>
			<div class="input-group">
            	<input type="hidden" name="commonObjectAttributes[].dataType" />
				<label name="commonObjectAttributes[].dataType" class="qp-output-text"></label>
				<input type="hidden" name="commonObjectAttributes[].arrayFlg"/>
			</div>
        </td>
		<td>
		</td>
    </tr>
</script>
<script id="tbl_attribute_list_define-action-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" rowbtn ="addchildrenbtn">
		<td colspan="100%">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'tbl_attribute_list_define',templateId:'tbl_attribute_list_define-template',templateData:{groupId: $(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>