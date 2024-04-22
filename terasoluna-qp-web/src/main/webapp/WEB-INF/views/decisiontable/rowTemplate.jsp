<script type="text/javascript">
     var projectId = '${sessionScope.CURRENT_PROJECT.projectId }';
     var moduleId = '${decisionTableForm.moduleId }';
     var decisionTbId = '${decisionTableForm.decisionTbId }';
     
     var CL_QP_DATATYPE = {};//CL_QP_Datatype
     <c:forEach items="${CL_QP_DATATYPE}" var="item">
         CL_QP_DATATYPE['${item.key}'] = "<qp:message code='${item.value}'/>";
     </c:forEach>

     var CL_QP_OPERATORTYPE = {};
     <c:forEach items="${CL_QP_OPERATORTYPE}" var="item">
         CL_QP_OPERATORTYPE['${item.key}'] = "<qp:message code='${item.value}'/>";
     </c:forEach>
     
     var CL_SUPPORT_OPTION_VALUE_FLAG = {};
     <c:forEach items="${CL_SUPPORT_OPTION_VALUE_FLAG}" var="item">
         CL_SUPPORT_OPTION_VALUE_FLAG['${item.key}'] = "<qp:message code='${item.value}'/>";
     </c:forEach>
     
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/common.js"></script>

<script id="tbl_input_list_result-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span  class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
                    <input type="text" name="inputLst[].decisionInputBeanName" class="form-control qp-input-text qp-convention-name-row" onfocus="markValue(this)" onchange="setBeanName(this);" maxlength="200" value=""/>
            		<input type="hidden" name="inputLst[].decisionInputBeanId" value="">
            		<input type="hidden" name="inputLst[].parentDecisionInputBeanId" value="">
            		<input type="hidden" name="inputLst[].decisionTbId" value="">
                    <input type="hidden" name="inputLst[].itemSequenceNo" class="ar-rIndex" />
					<input type="hidden" name="inputLst[].objectType" value=""/>
					<input type="hidden" name="inputLst[].objectId" value=""/>
					<input type="hidden" name="inputLst[].objectFlg" value="true"/>
					<input type="hidden" name="inputLst[].groupId" class="ar-groupId"/>
					<input type="hidden" name="inputLst[].tableIndex" class="ar-groupIndex" />
                </div>
            </div>
        </td>
        <td>
            <input type="text" name="inputLst[].decisionInputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" onfocus="markValue(this)" value="" onchange="setBeanCode(this);"/>
        </td>
        <td>
            <select name="inputLst[].dataType" class="form-control qp-input-select" onfocus="markValue(this)" onchange="changeDataType(this);"  >
                <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                <c:forEach var="item" items="${CL_QP_DATATYPE }">
                   <option value="${item.key}">${item.value}</option>
                </c:forEach>
            </select>
        </td>
        <td>
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_input_list_result-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
            		<input type="hidden" name="inputLst[].decisionInputBeanName" value="">
            		<input type="hidden" name="inputLst[].decisionInputBeanId" value="">
            		<input type="hidden" name="inputLst[].parentDecisionInputBeanId" value="">
            		<input type="hidden" name="inputLst[].decisionTbId" value="">
                    <input type="hidden" name="inputLst[].itemSequenceNo" class="ar-rIndex" />
					<input type="hidden" name="inputLst[].objectType" value=""/>
					<input type="hidden" name="inputLst[].objectId" value=""/>
					<input type="hidden" name="inputLst[].objectFlg" value="true"/>
					<input type="hidden" name="inputLst[].groupId" class="ar-groupId"/>
					<input type="hidden" name="inputLst[].tableIndex" class="ar-groupIndex" />
					<qp:autocomplete name="inputLst[].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForDT" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeCommonObjectOfIn"></qp:autocomplete>
                </div>
            </div>
        </td>
        <td>
            <input type="text" name="inputLst[].decisionInputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" onfocus="markValue(this)" value="" onchange="setBeanCode(this);"/>
        </td>
        <td>
			<select name="externalObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onfocus="markValue(this)" onchange="changeDataType(this);"  oldvalue="16">
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
        </td>
        <td>
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_input_list_result-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
            		<input type="hidden" name="inputLst[].decisionInputBeanName" value="">
                    <input type="hidden" name="inputLst[].decisionInputBeanId" value="">
                    <input type="hidden" name="inputLst[].parentDecisionInputBeanId" value="">
                    <input type="hidden" name="inputLst[].decisionTbId" value="">
                    <input type="hidden" name="inputLst[].itemSequenceNo" class="ar-rIndex" />
                    <input type="hidden" name="inputLst[].objectType" value=""/>
                    <input type="hidden" name="inputLst[].objectId" value=""/>
                    <input type="hidden" name="inputLst[].objectFlg" value="true"/>
                    <input type="hidden" name="inputLst[].groupId" class="ar-groupId"/>
                    <input type="hidden" name="inputLst[].tableIndex" class="ar-groupIndex" />
                    <qp:autocomplete name="inputLst[].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForDT" optionValue="optionValue" 
                        arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeExternalObjectOfIn"></qp:autocomplete>
                </div>
            </div>
        </td>
        <td>
            <input type="text" name="inputLst[].decisionInputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" onfocus="markValue(this)" value="" onchange="setBeanCode(this);"/>
        </td>
        <td>
            <select name="externalObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onfocus="markValue(this)" onchange="changeDataType(this);"  oldvalue="17">
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
        </td>
        <td>
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_input_list_result-object-attribute-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span  class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;text-align: left;vertical-align: middle;">
                    <input type="hidden" name="inputLst[].decisionInputBeanId" value="">
                    <input type="hidden" name="inputLst[].parentDecisionInputBeanId" value="">
                    <input type="hidden" name="inputLst[].decisionTbId" value="">
                    <input type="hidden" name="inputLst[].itemSequenceNo" class="ar-rIndex" />
                    <input type="hidden" name="inputLst[].decisionInputBeanName"/>
                    <input type="hidden" name="inputLst[].objectType" value=""/>
                    <input type="hidden" name="inputLst[].objectId" value=""/>
                    <input type="hidden" name="inputLst[].objectFlg" value="false"/>
                    <input type="hidden" name="inputLst[].groupId" class="ar-groupId"/>
                    <input type="hidden" name="inputLst[].tableIndex" class="ar-groupIndex" />
                    <label name="inputLst[].decisionInputBeanName" class="qp-output-text" />
                </div>
            </div>
        </td>
        <td>
            <label name="inputLst[].decisionInputBeanCode" class="qp-input-text" />
            <input type="hidden" name="inputLst[].decisionInputBeanCode"/>
        </td>
        <td>
			<div class="input-group" style="width: 100%">
                <input type="hidden" name="inputLst[].dataType" addChildFlg="false"/>
                <label name="inputLst[].dataType" class="qp-output-text"></label>
                <input type="hidden" name="inputLst[].arrayFlg" value="false"/>
            </div>
        </td>
        <td>
        </td>
    </tr>
</script>

<script id="tbl_input_list_result-action-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}">
        <td colspan="5">
            <div style="height:100%">
                <div class="pull-left" style="height:100%;vertical-align: middle;">
                    <a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" 
                        onclick="$.qp.ar.addRow({link: this,tableId:'tbl_input_list_result',templateId:'tbl_input_list_result-template',templateData:{groupId:'\${groupId }' },position:{anchor:$(this).closest('tr'),string:'before'}});"></a>
                </div>
            </div>
        </td>
    </tr>
</script>

<script id="tbl_output_list_result-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span  class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
                    <input type="text" name="outputLst[].decisionOutputBeanName" class="form-control qp-input-text qp-convention-name-row" onfocus="markValue(this)" onchange="setBeanName(this);" maxlength="200" value=""/>
            		<input type="hidden" name="outputLst[].decisionOutputBeanId" value="">
            		<input type="hidden" name="outputLst[].parentDecisionOutputBeanId" value="">
            		<input type="hidden" name="outputLst[].decisionTbId" value="">
                    <input type="hidden" name="outputLst[].itemSequenceNo" class="ar-rIndex" />
					<input type="hidden" name="outputLst[].objectType" value=""/>
					<input type="hidden" name="outputLst[].objectId" value=""/>
					<input type="hidden" name="outputLst[].objectFlg" value="true"/>
					<input type="hidden" name="outputLst[].groupId" class="ar-groupId"/>
					<input type="hidden" name="outputLst[].tableIndex" class="ar-groupIndex" />
                </div>
            </div>
        </td>
        <td>
            <input type="text" name="outputLst[].decisionOutputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" onfocus="markValue(this)" value="" onchange="setBeanCode(this);"/>
        </td>
        <td>
            <select name="outputLst[].dataType" class="form-control qp-input-select" onfocus="markValue(this)" onchange="changeDataType(this);"  >
                <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                <c:forEach var="item" items="${CL_QP_DATATYPE }">
                   <option value="${item.key}">${item.value}</option>
                </c:forEach>
            </select>
        </td>
        <td>
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_output_list_result-common-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
            		<input type="hidden" name="outputLst[].decisionOutputBeanName" value="">
            		<input type="hidden" name="outputLst[].decisionOutputBeanId" value="">
            		<input type="hidden" name="outputLst[].parentDecisionOutputBeanId" value="">
            		<input type="hidden" name="outputLst[].decisionTbId" value="">
                    <input type="hidden" name="outputLst[].itemSequenceNo" class="ar-rIndex" />
					<input type="hidden" name="outputLst[].objectType" value=""/>
					<input type="hidden" name="outputLst[].objectId" value=""/>
					<input type="hidden" name="outputLst[].objectFlg" value="true"/>
					<input type="hidden" name="outputLst[].groupId" class="ar-groupId"/>
					<input type="hidden" name="outputLst[].tableIndex" class="ar-groupIndex" />
					<qp:autocomplete name="outputLst[].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForDT" optionValue="optionValue" 
						arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeCommonObjectOfOu"></qp:autocomplete>
                </div>
            </div>
        </td>
        <td>
            <input type="text" name="outputLst[].decisionOutputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" onfocus="markValue(this)" value="" onchange="setBeanCode(this);"/>
        </td>
        <td>
			<select name="externalObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onfocus="markValue(this)" onchange="changeDataType(this);"  oldvalue="16">
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
        </td>
        <td>
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_output_list_result-external-object-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;vertical-align: middle;">
            		<input type="hidden" name="outputLst[].decisionOutputBeanName" value="">
                    <input type="hidden" name="outputLst[].decisionOutputBeanId" value="">
                    <input type="hidden" name="outputLst[].parentDecisionOutputBeanId" value="">
                    <input type="hidden" name="outputLst[].decisionTbId" value="">
                    <input type="hidden" name="outputLst[].itemSequenceNo" class="ar-rIndex" />
                    <input type="hidden" name="outputLst[].objectType" value=""/>
                    <input type="hidden" name="outputLst[].objectId" value=""/>
                    <input type="hidden" name="outputLst[].objectFlg" value="true"/>
                    <input type="hidden" name="outputLst[].groupId" class="ar-groupId"/>
                    <input type="hidden" name="outputLst[].tableIndex" class="ar-groupIndex" />
                    <qp:autocomplete name="outputLst[].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForDT" optionValue="optionValue" 
                        arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeExternalObjectOfOu"></qp:autocomplete>
                </div>
            </div>
        </td>
        <td>
            <input type="text" name="outputLst[].decisionOutputBeanCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" onfocus="markValue(this)" value="" onchange="setBeanCode(this);"/>
        </td>
        <td>
            <select name="externalObjectAttributes[].dataType" class="form-control qp-input-select pull-left" onfocus="markValue(this)" onchange="changeDataType(this);"  oldvalue="17">
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
        </td>
        <td>
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>

<script id="tbl_output_list_result-object-attribute-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
        <td colspan="2">
            <div style="height:100%">
                <div>
                    <span  class="ar-groupIndex"></span>
                </div>
                <div class="pull-right" style="height:100%;text-align: left;vertical-align: middle;">
                    <input type="hidden" name="outputLst[].decisionOutputBeanId" value="">
                    <input type="hidden" name="outputLst[].parentDecisionOutputBeanId" value="">
                    <input type="hidden" name="outputLst[].decisionTbId" value="">
                    <input type="hidden" name="outputLst[].itemSequenceNo" class="ar-rIndex" />
                    <input type="hidden" name="outputLst[].decisionOutputBeanName"/>
                    <input type="hidden" name="outputLst[].objectType" value=""/>
                    <input type="hidden" name="outputLst[].objectId" value=""/>
                    <input type="hidden" name="outputLst[].objectFlg" value="false"/>
                    <input type="hidden" name="outputLst[].groupId" class="ar-groupId"/>
                    <input type="hidden" name="outputLst[].tableIndex" class="ar-groupIndex" />
                    <label name="outputLst[].decisionOutputBeanName" class="qp-output-text" />
                </div>
            </div>
        </td>
        <td>
            <label name="outputLst[].decisionOutputBeanCode" class="qp-input-text" />
            <input type="hidden" name="outputLst[].decisionOutputBeanCode"/>
        </td>
        <td>
			<div class="input-group" style="width: 100%">
                <input type="hidden" name="outputLst[].dataType" addChildFlg="false"/>
                <label name="outputLst[].dataType" class="qp-output-text"></label>
                <input type="hidden" name="outputLst[].arrayFlg" value="false"/>
            </div>
        </td>
        <td>
        </td>
    </tr>
</script>

<script id="tbl_output_list_result-action-template" type="text/template">
    <tr data-ar-rgroup="\${groupId}">
        <td colspan="5">
            <div style="height:100%">
                <div class="pull-left" style="height:100%;vertical-align: middle;">
                    <a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" 
                        onclick="$.qp.ar.addRow({link: this,tableId:'tbl_output_list_result',templateId:'tbl_output_list_result-template',templateData:{groupId:'\${groupId }' },position:{anchor:$(this).closest('tr'),string:'before'}});"></a>
                </div>
            </div>
        </td>
    </tr>
</script>

<script id="tbl_item_condition-template" type="text/template">
    <tr>
        <td class="qp-output-fixlength tableIndex" id="sttIn">
             <input type="hidden" name="arrIndexIn" value="" />
        </td>
        <td ><input type="text" name="condition[].itemName" class="form-control qp-input-text" maxlength="200" ></td>
        <td>
            <div class="input-group" style="width:100%">
                <input type="text" name="condition[].itemValueAutocomplete" id="condition[].itemValueAutocompleteId" class="form-control qp-input-autocomplete"
                    optionValue="optionValue" optionLabel="optionLabel" sourceType="local" minLength = "0" sourceCallback="getDataSourceCondition"
                    onChangeEvent="displayDataRow" arg01="0" mustMatch="true" maxlength="200" value="" beanType="0"
                    placeholder='<qp:message code="sc.sys.0034"></qp:message>' previousvalue="" previouslabel="" selectedvalue="false"/>
                <input type="hidden" name="condition[].itemValue">
            </div>
        </td>  
        <td></td>
        <td></td>
        <td class="sortable" style="text-align: center;">
             <a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort" title="Move"></a>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="checkRemoveRow(this);" 
                title='<qp:message code="sc.sys.0014" />' style="margin-top: 3px;" href="javascript:void(0)"></a>
            <input type="hidden" name="condition[].objectCode" value="">
            <input type="hidden" name="condition[].objectName" value="">
            <input type="hidden" name="condition[].dataType" value="">
            <input type="hidden" name="condition[].decisionItemDesignId" value="">
            <input type="hidden" name="condition[].decisionTbId" value="">
			<input type="hidden" name="markColumIdCond" value="">
        </td>
    </tr>
</script>

<script id="tbl_item_action-template" type="text/template">
    <tr>
        <td class="qp-output-fixlength tableIndex" id="sttOut">
             <input type="hidden" name="arrIndexOut" value="" />
        </td>
        <td ><input type="text" name="action[].itemName" class="form-control qp-input-text" maxlength="100" ></td>
        <td>
            <div class="input-group" style="width:100%">
                <input type="text" name="action[].itemValueAutocomplete" id="action[].itemValueAutocompleteId" class="form-control qp-input-autocomplete"
                    optionValue="optionValue" optionLabel="optionLabel" sourceType="local" minLength = "0" sourceCallback="getDataSourceAction"
                    onChangeEvent="displayDataRow" arg01="1" mustMatch="true" maxlength="200" beanType="1"  minLength = "0" value=""
                    placeholder='<qp:message code="sc.sys.0034"></qp:message>' previousvalue="" previouslabel="" selectedvalue="false" />
                <input type="hidden" name="action[].itemValue">
            </div>
        </td>  
        <td></td>
        <td></td>
        <td class="sortable" style="text-align: center;">
             <a href="javascript:" style="margin-top: 3px; cursor: move;" class="glyphicon glyphicon-sort" title="Move"></a>
        </td>
        <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="checkRemoveRow(this);" 
                title='<qp:message code="sc.sys.0014" />' style="margin-top: 3px;" href="javascript:void(0)"></a>
            <input type="hidden" name="action[].objectCode" value="">
            <input type="hidden" name="action[].objectName" value="">
            <input type="hidden" name="action[].dataType" value="">
            <input type="hidden" name="action[].decisionItemDesignId" value="">
            <input type="hidden" name="action[].decisionTbId" value="">
			<input type="hidden" name="markColumIdAct" value="">
    </td>
    </tr>
</script>
