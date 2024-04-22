<script id="tblMethod-template" type="text/template">
<tr data-ar-rgroup="\${groupId}" class="ar-dataRow" data-ar-rindex="" data-ar-rgroupindex="">
<td>	
<div id="methodInfor" class="methodInfor">
		<table class="table table-bordered qp-table-form" name="methodInfo" id="tblMethod" data-ar-tlevel="0" data-ar-dataClass="ar-dataRow">
			<colgroup>
			<col width="20%">
			<col width="30%">
			<col width="20%">
			<col width="30%">
			</colgroup>
			<tbody>
				<tr>
					<th style="text-align: left; vertical-align: middle;" colspan="4"><qp:message code="sc.functionmaster.0032" />
						<span id="id-block-reload-data0" onclick="removeEvent(this);" title='<qp:message code="sc.functionmaster.0071" />' class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px;cursor: pointer;"></span>
						<span class="pull-right">
							<span class="sortable"><a href="javascript:" style="margin-top: 3px; cursor: move; margin-right: 10px" class="glyphicon glyphicon-sort sort-method" title='<qp:message code="sc.functionmaster.0070" />'></a></span>
						</span>
					</th>
				</tr>
				<tr>
					<th><qp:message code="sc.functionmaster.0027" />&nbsp;<label class="qp-required-field">(*)</label></th>
					<td>
						<input id="functionMethodName" name="functionMethod[].functionMethodName" class="form-control qp-input-text qp-convention-name-row" type="text" value="" maxlength="200">
						<input type="hidden" name="functionMethod[].itemSeqNo" id="itemSeqNo" value="" class="ar-rIndex" />
						<input type="hidden" name="functionMethod[].groupId" id="groupId" value="" class="ar-groupId" />
						<input type="hidden" name="functionMethod[].tableIndex" id="tableIndex" value="" class="ar-groupIndex" />
						<input type="hidden" id="currentMethod" name="currentMethod" class="ar-rIndex"/>
					</td>
					<th><qp:message code="sc.functionmaster.0028" />&nbsp;<label class="qp-required-field">(*)</label></th>
					<td>
						<input id="functionMethodCode" name="functionMethod[].functionMethodCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
					</td>
				</tr>
				<tr>
					<th><qp:message code="sc.sys.0028" /></th>
					<td colspan="3">
						<textarea id="remark" name="functionMethod[].remark" class="form-control qp-input-textarea" maxlength="2000" type="text" rows="2"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<ul class="nav nav-tabs" id="com-menu-sidebar">
							<li class="active" name="liInputLogic"><a href="#tabsDecision-[].1" data-toggle="tab" style="font: bold;"><qp:message code="sc.functionmaster.0033" /></a></li>
							<li name="liOutputLogic"><a href="#tabsDecision-[].2" data-toggle="tab" style="font: bold;"><qp:message code="sc.functionmaster.0034" /></a></li>
						</ul>
						<div class="tab-content">
							<div id="tabsDecision-1" name="divInputLogic" class="tab-pane active" style="height: auto;">
								<div class="panel panel-default">
									<div class="panel-body">
										<table class="table table-bordered qp-table-list" id="tblInput" name="tblInput" data-ar-callback="$.qp.functionmaster.formCallback" data-ar-tlevel="1" data-ar-dataClass="ar-dataRow">
											<colgroup>
												<col width="40px">
												<col width="360px">
												<col width="30%">
												<col>
												<col width="40px">
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.sys.0004" /></th>
													<th><qp:message code="sc.functionmaster.0035" /></th>
													<th><qp:message code="sc.functionmaster.0036" /></th>
													<th><qp:message code="sc.functionmaster.0037" /></th>
													<th>&nbsp;</th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
										<div class="qp-add-left">
											<a  name="btnAdd" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,templateId:'tblInput-attribute-template',templateData:{groupId:''}});" style="margin-top: 3px;" href="javascript:void(0)"></a>
										</div>
									</div>
								</div>
							</div>
							<div id="tabsDecision-2" name="divOutputLogic" class="tab-pane" style="height: auto;">
								<div class="panel panel-default">
									<div class="panel-body">
										<table class="table table-bordered qp-table-list" id="tblOutput" name="tblOutput" data-ar-callback="$.qp.functionmaster.formCallback" data-ar-tlevel="1" data-ar-dataClass="ar-dataRow">
											<colgroup>
												<col width="40px">
												<col width="360px">
												<col width="30%">
												<col>
												<col width="40px">
											</colgroup>
											<thead>
												<tr>
													<th><qp:message code="sc.sys.0004" /></th>
													<th><qp:message code="sc.functionmaster.0035" /></th>
													<th><qp:message code="sc.functionmaster.0036" /></th>
													<th><qp:message code="sc.functionmaster.0037" /></th>
													<th>&nbsp;</th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
										<div class="qp-add-left">
											<a name="btnAdd" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,templateId:'tblOutput-attribute-template',templateData:{groupId:''}});" style="margin-top: 3px;" href="javascript:void(0)"></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</td>
</tr>
</script>

<script id="tblInput-action-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" rowbtn ="addchildrenbtn">
		<td colspan="5">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,container:$(this).closest('table'),templateId:'tblInput-attribute-template',templateData:{groupId: $(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>
<script id="tblInput-attribute-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="text" name="functionMethod[].functionMethodInput[].methodInputName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].methodInputId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].parentFunctionMethodInputId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectType" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectFlg" value="true"/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].tableIndex" class="ar-groupIndex" />
				</div>
			</div>
		</td>
		<td>
			<input name="functionMethod[].functionMethodInput[].methodInputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
    	</td>
    	<td>
			<div class="input-group" style="width: 100%">
				<select name="functionMethod[].functionMethodInput[].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this);">
					<option value=""><qp:message code="sc.sys.0030" /></option>
					<c:forEach var="item" items="${CL_QP_DATATYPE }">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
				</select>
				<span class="input-group-addon">
					<label><input type="checkbox" aria-label="Array" name="functionMethod[].functionMethodInput[].arrayFlg" />Array</label>
				</span>
			</div>
		</td>
    	<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.ar.removeRow({link:this})"></a></td>
    </tr>
</script>
<script id="tblInput-object-attribute-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span  class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
					<label name="functionMethod[].functionMethodInput[].methodInputName" class="qp-output-text" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].methodInputId" value=""/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].methodInputName"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].parentFunctionMethodInputId" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectType" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectFlg" value="false"/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].tableIndex" class="ar-groupIndex" />
                </div>
            </div>
        </td>
        <td>
            <label name="functionMethod[].functionMethodInput[].methodInputCode" class="qp-input-text" />
            <input type="hidden" name="functionMethod[].functionMethodInput[].methodInputCode"/>
        </td>
        <td>
            <div class="input-group" style="width: 100%">
				<input type="hidden" name="functionMethod[].functionMethodInput[].dataType" addChildFlg="false"/>
    			<label name="functionMethod[].functionMethodInput[].dataType" class="qp-output-text"></label>
    			<input type="hidden" name="functionMethod[].functionMethodInput[].arrayFlg" value="false"/>
            </div>
        </td>
        <td></td>
    </tr>
</script>
<script id="tblInput-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
			<div style="height:100%">
				<div>
					<span class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
                    <input type="hidden" name="functionMethod[].functionMethodInput[].methodInputName"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].methodInputId" value=""/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].tableIndex" class="ar-groupIndex" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].parentFunctionMethodInputId" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectType"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectId"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].objectFlg" value="true"/>
					<qp:autocomplete name="functionMethod[].functionMethodInput[].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForFM" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeCommonObjectOfIn"></qp:autocomplete>
				</div>
			</div>
        </td>
        <td>
            <input name="functionMethod[].functionMethodInput[].methodInputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
        </td>
        <td>
            <div class="input-group" style="width: 100%">
				<select name="functionMethod[].functionMethodInput[].dataType" class="form-control qp-input-select pull-left" onchange="onChangeDataType(this);"  oldvalue="16">
    				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
        			<c:forEach var="item" items="${CL_QP_DATATYPE}">
						<c:if test='${item.key == "16"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "16"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
    			</select>
                <span class="input-group-addon">
                    <label><input type="checkbox" aria-label="Array" name="functionMethod[].functionMethodInput[].arrayFlg" />Array</label>
                </span>
            </div>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
    </tr>
</script>
<script id="tblInput-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">    
                    <input type="hidden" name="functionMethod[].functionMethodInput[].methodInputName"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].methodInputId" value=""/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodInput[].tableIndex" class="ar-groupIndex" />
					<input type="hidden" name="functionMethod[].functionMethodInput[].parentFunctionMethodInputId" />
                    <input type="hidden" name="functionMethod[].functionMethodInput[].objectType"/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].objectId"/>
                    <input type="hidden" name="functionMethod[].functionMethodInput[].objectFlg" value="true"/>
                    <qp:autocomplete name="functionMethod[].functionMethodInput[].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForFM" optionValue="optionValue" 
                        arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeExternalObjectOfIn"></qp:autocomplete>
                </div>
            </div>
        </td>
        <td>
            <input name="functionMethod[].functionMethodInput[].methodInputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
        </td>
        <td>
            <div class="input-group" style="width: 100%">
                <select name="functionMethod[].functionMethodInput[].dataType" class="form-control qp-input-select pull-left" onchange="onChangeDataType(this);"  oldvalue="17">
                    <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_QP_DATATYPE}">
                        <c:if test='${item.key == "17"}'>
                            <option value="${item.key}" selected="selected" >${item.value}</option>
                        </c:if>
                        <c:if test='${item.key != "17"}'>
                            <option value="${item.key}">${item.value}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <span class="input-group-addon">
                    <label><input type="checkbox" aria-label="Array" name="functionMethod[].functionMethodInput[].arrayFlg" />Array</label>
                </span>
            </div>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
    </tr>
</script>

<script id="tblOutput-action-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" rowbtn ="addchildrenbtn">
		<td colspan="5">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,container:$(this).closest('table'),templateId:'tblOutput-attribute-template',templateId:'tblOutput-attribute-template',templateData:{groupId: $(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>
<script id="tblOutput-attribute-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
		<td colspan="2">
			<div style="height:100%">
				<div>
					<span class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
					<input type="text" name="functionMethod[].functionMethodOutput[].methodOutputName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].parentFunctionMethodOutputId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectType" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectFlg" value="true"/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].tableIndex" class="ar-groupIndex" />
				</div>
			</div>
		</td>
		<td>
			<input name="functionMethod[].functionMethodOutput[].methodOutputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
    	</td>
    	<td>
			<div class="input-group" style="width: 100%">
				<select name="functionMethod[].functionMethodOutput[].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this);">
					<option value=""><qp:message code="sc.sys.0030" /></option>
					<c:forEach var="item" items="${CL_QP_DATATYPE }">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
				</select>
				<span class="input-group-addon">
					<label><input type="checkbox" aria-label="Array" name="functionMethod[].functionMethodOutput[].arrayFlg" />Array</label>
				</span>
			</div>
		</td>
    	<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.ar.removeRow({link:this})"></a></td>
    </tr>
</script>
<script id="tblOutput-object-attribute-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span  class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
					<label name="functionMethod[].functionMethodOutput[].methodOutputName" class="qp-output-text" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputId" value=""/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputName"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].parentFunctionMethodOutputId" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectType" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectId" value=""/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectFlg" value="false"/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].tableIndex" class="ar-groupIndex" />
                </div>
            </div>
        </td>
        <td>
            <label name="functionMethod[].functionMethodOutput[].methodOutputCode" class="qp-input-text" />
            <input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputCode"/>
        </td>
        <td>
            <div class="input-group" style="width: 100%">
				<input type="hidden" name="functionMethod[].functionMethodOutput[].dataType" addChildFlg="false"/>
    			<label name="functionMethod[].functionMethodOutput[].dataType" class="qp-output-text"></label>
    			<input type="hidden" name="functionMethod[].functionMethodOutput[].arrayFlg" value="false"/>
            </div>
        </td>
        <td></td>
    </tr>
</script>
<script id="tblOutput-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
			<div style="height:100%">
				<div>
					<span class="ar-groupIndex"></span>
				</div>
				<div class="pull-right" style="height:100%;vertical-align: middle;">
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputName"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputId" value=""/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].tableIndex" class="ar-groupIndex" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].parentFunctionMethodOutputId" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectType"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectId"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].objectFlg" value="true"/>
					<qp:autocomplete name="functionMethod[].functionMethodOutput[].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForFM" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeCommonObjectOfOu"></qp:autocomplete>
				</div>
			</div>
        </td>
        <td>
            <input name="functionMethod[].functionMethodOutput[].methodOutputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
        </td>
        <td>
            <div class="input-group" style="width: 100%">
				<select name="functionMethod[].functionMethodOutput[].dataType" class="form-control qp-input-select pull-left" onchange="onChangeDataType(this);"  oldvalue="16">
    				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
        			<c:forEach var="item" items="${CL_QP_DATATYPE}">
						<c:if test='${item.key == "16"}'>
							<option value="${item.key}" selected="selected" >${item.value}</option>
						</c:if>
						<c:if test='${item.key != "16"}'>
							<option value="${item.key}">${item.value}</option>
						</c:if>
					</c:forEach>
    			</select>
                <span class="input-group-addon">
                    <label><input type="checkbox" aria-label="Array" name="functionMethod[].functionMethodOutput[].arrayFlg" />Array</label>
                </span>
            </div>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
    </tr>
</script>
<script id="tblOutput-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">    
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputName"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].methodOutputId" value=""/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].itemSeqNo" class="ar-rIndex" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].groupId" class="ar-groupId"/>
					<input type="hidden" name="functionMethod[].functionMethodOutput[].tableIndex" class="ar-groupIndex" />
					<input type="hidden" name="functionMethod[].functionMethodOutput[].parentFunctionMethodOutputId" />
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].objectType"/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].objectId"/>
                    <input type="hidden" name="functionMethod[].functionMethodOutput[].objectFlg" value="true"/>
                    <qp:autocomplete name="functionMethod[].functionMethodOutput[].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForFM" optionValue="optionValue" 
                        arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeExternalObjectOfOu"></qp:autocomplete>
                </div>
            </div>
        </td>
        <td>
            <input name="functionMethod[].functionMethodOutput[].methodOutputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50">
        </td>
        <td>
            <div class="input-group" style="width: 100%">
                <select name="functionMethod[].functionMethodOutput[].dataType" class="form-control qp-input-select pull-left" onchange="onChangeDataType(this);"  oldvalue="17">
                    <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                    <c:forEach var="item" items="${CL_QP_DATATYPE}">
                        <c:if test='${item.key == "17"}'>
                            <option value="${item.key}" selected="selected" >${item.value}</option>
                        </c:if>
                        <c:if test='${item.key != "17"}'>
                            <option value="${item.key}">${item.value}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <span class="input-group-addon">
                    <label><input type="checkbox" aria-label="Array" name="functionMethod[].functionMethodOutput[].arrayFlg" />Array</label>
                </span>
            </div>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />'  onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
    </tr>
</script>