<script id="tbl_attribute_list_define-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span  class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">	
					<input type="hidden" name="externalObjectAttributes[].groupId"/>
					<input type="hidden" name="externalObjectAttributes[].tableIndex"/>
					<input type="hidden" name="externalObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="externalObjectAttributes[].externalObjectAttributeId"/>
					<input type="hidden" name="externalObjectAttributes[].parentExternalObjectAttributeId"/>
					<input type="hidden" name="externalObjectAttributes[].saveFlg" value="true"/>
					<input type="text" name="externalObjectAttributes[].externalObjectAttributeName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="externalObjectAttributes[].externalObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="externalObjectAttributes[].dataType" value=""/>
            	<select name="externalObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                        <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_COMMON_OBJECT}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="externalObjectAttributes[].arrayFlg">Array</label></span>
			</div>
         </td>
         <td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
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
					<input type="hidden" name="externalObjectAttributes[].groupId"/>
					<input type="hidden" name="externalObjectAttributes[].tableIndex"/>
					<input type="hidden" name="externalObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="externalObjectAttributes[].externalObjectAttributeId"/>
					<input type="hidden" name="externalObjectAttributes[].externalObjectAttributeName"/>
					<input type="hidden" name="externalObjectAttributes[].saveFlg" value="true"/>
					<input type="hidden" name="externalObjectAttributes[].parentExternalObjectAttributeId"/>
					<qp:autocomplete name = "externalObjectAttributes[].objectDefinitionId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForEO" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${externalObjectDefinitionForm.externalObjectDefinitionId}" arg04="${externalObjectDefinitionForm.moduleId}" mustMatch="true" onChangeEvent="onChangeExternalObject"></qp:autocomplete>
					<input type="hidden" name="externalObjectAttributes[].moduleId"/>
				</div>
			</div>
		</td>
        <td>
			<input type="text" name="externalObjectAttributes[].externalObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50"/>
		</td>
		<td>
			<div class="input-group">
				<input type="hidden" name="externalObjectAttributes[].dataType" value="17"/>
				<select name="externalObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);"  oldvalue="17">
                	<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_COMMON_OBJECT}">
						<c:if test='${item.key == "17"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "17"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
                </select>
				<span class="input-group-addon"><label><input type="checkbox" aria-label="Array" name="externalObjectAttributes[].arrayFlg">Array</label></span>
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
					<input type="hidden" name="externalObjectAttributes[].groupId"/>
					<input type="hidden" name="externalObjectAttributes[].tableIndex"/>
					<input type="hidden" name="externalObjectAttributes[].itemSeqNo" />
					<input type="hidden" name="externalObjectAttributes[].externalObjectAttributeName"/>
					<input type="hidden" name="externalObjectAttributes[].externalObjectDefinitionId"/>
					<input type="hidden" name="externalObjectAttributes[].externalObjectAttributeId"/>
					<input type="hidden" name="externalObjectAttributes[].parentExternalObjectAttributeId"/>
					<input type="hidden" name="externalObjectAttributes[].saveFlg" value="false"/>
					<label name="externalObjectAttributes[].externalObjectAttributeName" class="qp-output-text"></label>
				</div>
			</div>
		</td>
        <td>
			<input type="hidden" name="externalObjectAttributes[].externalObjectAttributeCode"/>
			<label name="externalObjectAttributes[].externalObjectAttributeCode" class="qp-output-text"></label>
		</td>
        <td>
			<div class="input-group">
            	<input type="hidden" name="externalObjectAttributes[].dataType" />
				<label name="externalObjectAttributes[].dataType" class="qp-output-text"></label>
				<input type="hidden" name="externalObjectAttributes[].arrayFlg" />
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