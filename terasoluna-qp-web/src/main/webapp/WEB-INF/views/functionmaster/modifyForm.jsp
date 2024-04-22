<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.functionmaster"></qp:message></span></li>
         <li><span><qp:message code="sc.functionmaster.0016"/></span></li>
    </tiles:putAttribute>

    <tiles:putAttribute name="header-link">
        <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
            <qp:authorization permission="functionmasterSearch">
                <span class="qp-link-header-icon glyphicon glyphicon-search"></span>
                <a class="com-link-popup" href="${pageContext.request.contextPath}/functionmaster/search"><qp:message code="sc.functionmaster.0008" /></a>
            </qp:authorization>
        </c:if>
    </tiles:putAttribute>

    <tiles:putAttribute name="additionalHeading">
    	<script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=functionmaster"></script>
		<link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />s
        <link href="${pageContext.request.contextPath}/resources/app/domaindatatype/css/style.css" type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/init.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/validation.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/functionmaster/javascript/functionmaster.js"></script>
        <script type="text/javascript">
			$( document ).ready(function() {
				$("#file").bind("change", checkChangeFileName);
				// add warning message : User choose file again.
				var isShowWarningFile = ${functionMasterForm.isShowWarningFile};
				if(eval(isShowWarningFile)){
					alert(fcomMsgSource['inf.sys.0041']);
				}
				
	        	$.qp.ar.recalculateRowIndex($("#wapper>table>tbody").closest("table"),"");
	        	$.qp.ar.renameAttributes($("#wapper>table>tbody").closest("table"));

				reIndexTable();
				initAddButton();
			});
			
			function checkChangeFileName(e){
				$(".flagChangeFile").val(true);
		}
		</script>
        <script type="text/javascript">
            var returnType = 1;
            var CL_QP_DATATYPE = {}//CL_QP_Datatype
            <c:forEach items="${CL_QP_DATATYPE}" var="item">
                CL_QP_DATATYPE['${item.key}'] = '${item.value}';
            </c:forEach>

            var MAX_NESTED_OBJECT = '${CL_SYSTEM_SETTING["maxNestedObject"]}';
        </script>
        <jsp:include page="rowTemplate.jsp" />
    </tiles:putAttribute>

    <tiles:putAttribute name="body">
        <form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/functionmaster/modifyConfirm" modelAttribute="functionMasterForm">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
             <form:hidden path="maxSize" />
             <c:if test="${notExistFlg ne 1}">
                <div class="panel panel-default qp-div-information">
                    <div class="panel-heading">
                        <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.functionmaster.0010" /></span>
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
                                <th><qp:message code="sc.functionmaster.0005" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                                <td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name-row" path="functionMasterName" maxlength="200" /></td>
                                <th><qp:message code="sc.functionmaster.0006" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                                <td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code-row" path="functionMasterCode" maxlength="50" /></td>
                            </tr>
                            <tr>
                                <th><qp:message code="sc.functionmaster.0029" /></th>
                                <td style="height: 23px;"><form:input path="file" cssClass="qp-input-file pull-right" type="file" accept=".java" /> <span class="file-input-name">${functionMasterForm.fileName}</span> <form:hidden class="flagChangeFile" path="flagChangeFile" /> <form:hidden path="fileName" /></td>
                                <th><qp:message code="sc.functionmaster.0030" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                                <td class="word-wrap"><form:input type="text" cssClass="form-control qp-input-text" path="packageName" maxlength="50" readonly="true" value="${functionMasterForm.packageName}" /></td>
                            </tr>
                            <tr>
                                <th><qp:message code="sc.sys.0028" /></th>
                                <td colspan="3"><form:textarea path="remark" type="text" rows="2" cssClass="form-control qp-input-textarea" maxlength="2000" /></td>
                            </tr>
                        </table>
                    </div>
                    <div class="qp-div-action">
                        <input type="button" value="<qp:message code="sc.functionmaster.0031" />" class="btn qp-button-client" onclick="addMethodInformation();">
                    </div>
                </div>
                <div id="wapper">
                    <table id='tblLstMethod' data-ar-dataClass="ar-dataRow">
                        <tbody>
                        <c:forEach var="functionMethod" items="${functionMasterForm.functionMethod}" varStatus="status">
                            <tr data-ar-rgroup="${functionMethod.groupId}" class="ar-dataRow" data-ar-rindex="${functionMethod.itemSeqNo }" data-ar-rgroupindex="${functionMethod.tableIndex }">
                                <td>
                                    <div id="methodInfor${status.index}" style="border-color: red; border-style: dashed; border-width: 2px; margin-bottom: 20px" class="methodInfor">
                                        <table class="table table-bordered qp-table-form" name="methodInfo" id="tblMethod" data-ar-tlevel="0"  data-ar-dataClass="ar-dataRow">
                                            <form:hidden path="functionMethod[${status.index}].itemSeqNo" class="ar-rIndex" />
                                            <form:hidden path="functionMethod[${status.index}].functionMethodId" />
                                            <form:hidden path="functionMethod[${status.index}].functionMasterId" />
                                            <form:hidden path="functionMethod[${status.index}].groupId" class="ar-groupId"/>
                                            <form:hidden path="functionMethod[${status.index}].tableIndex" class="ar-groupIndex" />
                                            <input type="hidden" id="currentMethod" name="currentMethod" class="ar-rIndex"/>  
                                            <colgroup>
                                                <col width="20%">
                                                <col width="30%">
                                                <col width="20%">
                                                <col width="30%">
                                            </colgroup>
                                            <tbody>
                                                <tr>
                                                    <th style="text-align: left; vertical-align: middle;" colspan="4">
                                                    	<qp:message code="sc.functionmaster.0032" /> 
                                                    	<span id="id-block-reload-data0" onclick="removeEvent(this);" class="glyphicon glyphicon-remove-circle pull-right" style="font-size: 20px;cursor: pointer;" title='<qp:message code="sc.functionmaster.0071"/>'></span> 
                                                    	<span class="pull-right"> 
                                                    		<span class="sortable"><a href="javascript:" style="margin-top: 3px; cursor: move; margin-right: 10px" class="glyphicon glyphicon-sort sort-method" title='<qp:message code="sc.functionmaster.0070"/>'></a></span>
                                                    	</span>
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th><qp:message code="sc.functionmaster.0027" />&nbsp;<label class="qp-required-field">(*)</label></th>
                                                    <td><form:input path="functionMethod[${status.index}].functionMethodName" cssClass="form-control qp-input-text qp-convention-name-row" maxlength="200" /></td>
                                                    <th><qp:message code="sc.functionmaster.0028" />&nbsp;<label class="qp-required-field">(*)</label></th>
                                                    <td><form:input path="functionMethod[${status.index}].functionMethodCode" cssClass="form-control qp-input-text out-focus-lower qp-convention-code-row" maxlength="25" /></td>
                                                </tr>
                                                <tr>
                                                    <th><qp:message code="sc.sys.0028" /></th>
                                                    <td colspan="3"><form:textarea path="functionMethod[${status.index}].remark" cssClass="form-control qp-input-textarea" /></td>
                                                </tr>
                                                <tr data-ar-rgroup="${functionMethod.groupId}" class="ar-dataRow" data-ar-rindex="${functionMethod.itemSeqNo }" data-ar-rgroupindex="${functionMethod.tableIndex }">
                                                    <td colspan="4">
                                                        <ul class="nav nav-tabs" id="com-menu-sidebar">
                                                            <li class="active" name="liInputLogic"><a href="#tabsDecision-${functionMethod.itemSeqNo }1" data-toggle="tab" style="font: bold;"><qp:message code="sc.functionmaster.0033" /></a></li>
                                                            <li name="liOutputLogic"><a href="#tabsDecision-${functionMethod.itemSeqNo }2" data-toggle="tab" style="font: bold;"><qp:message code="sc.functionmaster.0034" /></a></li>
                                                        </ul>
                                                        <div class="tab-content">
                                                            <div id="tabsDecision-${functionMethod.itemSeqNo }1" name="divInputLogic" class="tab-pane active" style="height: auto;">
                                                                <div class="panel panel-default">
                                                                    <div class="panel-body">
                                                                        <table class="table table-bordered qp-table-list" id="tblInput" name="tblInput" data-ar-callback="$.qp.functionmaster.formCallback" data-ar-tlevel="1"  data-ar-dataClass="ar-dataRow">
                                                                            <colgroup>
                                                                                <col width="40px">
                                                                                <col width="360px">
                                                                                <col width="30%">
                                                                                <col width="">
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
                                                                                <c:forEach var="functionMethodInput" items="${functionMethod.functionMethodInput}" varStatus="statusInput">
                                                                                    <c:choose>
                                                                                        <c:when test="${functionMethodInput.dataType == '16' && functionMethodInput.objectFlg}">
                                                                                            <tr data-ar-rgroup="${functionMethodInput.groupId}" class="ar-dataRow" data-ar-templateid="tblInput-common-object-template" data-ar-rindex="${functionMethodInput.itemSeqNo }" data-ar-rgroupindex="${functionMethodInput.tableIndex }">
                                                                                                <td colspan="2">
                                                                                                    <div style="height: 100%">
                                                                                                        <div>
                                                                                                            <span class="ar-groupIndex">${functionMethodInput.tableIndex }</span>
                                                                                                        </div>
                                                                                                        <div class="pull-right" style="height: 100%;">
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputName" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].functionMethodId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].parentFunctionMethodInputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].itemSeqNo" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectType" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectFlg" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].groupId" class="ar-groupId"/>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                            <qp:autocomplete name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForFM" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeCommonObjectOfIn" value="${functionMethodInput.methodInputId}" displayValue="${functionMethodInput.methodInputName}"></qp:autocomplete>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><input id="methodInputCode" value="${functionMethodInput.methodInputCode}" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50"></td>
                                                                                                <td>
                                                                                                    <div class="input-group" style="width: 100%">
                                                                                                        <form:select name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" id="dataType" path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this)">
                                                                                                            <form:option value="">
                                                                                                                <qp:message code="sc.sys.0030" />
                                                                                                            </form:option>
                                                                                                            <c:forEach var="item" items="${CL_QP_DATATYPE }">
                                                                                                                <form:option value="${item.key}">${item.value}</form:option>
                                                                                                            </c:forEach>
                                                                                                        </form:select>
                                                                                                        <c:if test="${functionMethodInput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" checked="checked" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                        <c:if test="${!functionMethodInput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><a  name="btnAdd" class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
                                                                                            </tr>
                                                                                        </c:when>
                                                                                        <c:when test="${functionMethodInput.dataType == '17' && functionMethodInput.objectFlg}">
                                                                                            <tr data-ar-rgroup="${functionMethodInput.groupId}" class="ar-dataRow" data-ar-templateid="tblInput-external-object-template" data-ar-rindex="${functionMethodInput.itemSeqNo }" data-ar-rgroupindex="${functionMethodInput.tableIndex }">
                                                                                                <td colspan="2">
                                                                                                    <div style="height: 100%">
                                                                                                        <div>
                                                                                                            <span class="ar-groupIndex">${functionMethodInput.tableIndex }</span>
                                                                                                        </div>
                                                                                                        <div class="pull-right" style="height: 100%;">
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputName" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].functionMethodId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].parentFunctionMethodInputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].itemSeqNo" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectType" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectFlg" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].groupId" class="ar-groupId"/>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                            <qp:autocomplete name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForFM" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeExternalObjectOfIn" value="${functionMethodInput.methodInputId}" displayValue="${functionMethodInput.methodInputName}"></qp:autocomplete>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><input id="methodInputCode" value="${functionMethodInput.methodInputCode}" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50"></td>
                                                                                                <td>
                                                                                                    <div class="input-group" style="width: 100%">
                                                                                                        <form:select name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" id="dataType" path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this)">
                                                                                                            <form:option value="">
                                                                                                                <qp:message code="sc.sys.0030" />
                                                                                                            </form:option>
                                                                                                            <c:forEach var="item" items="${CL_QP_DATATYPE }">
                                                                                                                <form:option value="${item.key}">${item.value}</form:option>
                                                                                                            </c:forEach>
                                                                                                        </form:select>
                                                                                                        <c:if test="${functionMethodInput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" checked="checked" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                        <c:if test="${!functionMethodInput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><a  name="btnAdd" class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
                                                                                            </tr>
                                                                                        </c:when>
                                                                                        <c:when test="${!functionMethodInput.objectFlg}">
                                                                                            <tr data-ar-rgroup="${functionMethodInput.groupId}" class="ar-dataRow" data-ar-templateid="tblInput-object-attribute-template" data-ar-rindex="${functionMethodInput.itemSeqNo }" data-ar-rgroupindex="${functionMethodInput.tableIndex }">
                                                                                                <td colspan="2">
                                                                                                    <div style="height: 100%">
                                                                                                        <div>
                                                                                                            <span class="ar-groupIndex">${functionMethodInput.tableIndex }</span>
                                                                                                        </div>
                                                                                                        <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                                                            <label name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputName" class="qp-output-text">${functionMethodInput.methodInputName }</label>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputName" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].functionMethodId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].parentFunctionMethodInputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].itemSeqNo" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectType" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectFlg" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].groupId" class="ar-groupId"/>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <label name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputCode" class="qp-input-text">${functionMethodInput.methodInputCode }</label>
                                                                                                    <input type="hidden" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputCode" value="${functionMethodInput.methodInputCode }"/>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <div class="input-group" style="width: 100%">
                                                                                                        <input type="hidden" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" addChildFlg="false"  value="${functionMethodInput.dataType }" />
                                                                                                        <label name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" class="qp-output-text">${CL_QP_DATATYPE.get(functionMethodInput.dataType.toString())}</label>
                                                                                                        <input type="hidden" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" value="false" />
                                                                                                        <c:if test="${functionMethodInput.arrayFlg}">
                                                                                                                []
                                                                                                            </c:if>
                                                                                                        <span class="input-group-addon" style="display: none"> <label><form:checkbox path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
                                                                                                        </span>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td></td>
                                                                                            </tr>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <c:if test="${!functionMethodInput.dataType != null}">
                                                                                                <tr data-ar-rgroup="${functionMethodInput.groupId}" class="ar-dataRow" data-ar-templateid="tblInput-attribute-template" data-ar-rindex="${functionMethodInput.itemSeqNo }" data-ar-rgroupindex="${functionMethodInput.tableIndex }" data-ar-dataClass="ar-dataRow">
                                                                                                    <td colspan="2">
                                                                                                        <div style="height: 100%">
                                                                                                            <div>
                                                                                                                <span class="ar-groupIndex">${functionMethodInput.tableIndex }</span>
                                                                                                            </div>
                                                                                                            <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                                                                <input type="text" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" value="${functionMethodInput.methodInputName }"/>
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].functionMethodId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].parentFunctionMethodInputId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].itemSeqNo" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectType" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].objectFlg" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].groupId" class="ar-groupId"/>
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                    <td><input name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].methodInputCode" value="${functionMethodInput.methodInputCode }" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50"></td>
                                                                                                    <td>
                                                                                                        <div class="input-group" style="width: 100%">
                                                                                                            <form:select name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" path="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this);">
                                                                                                                <form:option value=""><qp:message code="sc.sys.0030" /></form:option>
                                                                                                                <c:forEach var="item" items="${CL_QP_DATATYPE }">
                                                                                                                    <form:option value="${item.key}">${item.value}</form:option>
                                                                                                                </c:forEach>
                                                                                                            </form:select>
                                                                                                            
                                                                                                            <c:if test="${functionMethodInput.arrayFlg}">
                                                                                                                <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" checked="checked" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                            </c:if>
                                                                                                            <c:if test="${!functionMethodInput.arrayFlg}">
                                                                                                                <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" name="functionMethod[${status.index}].functionMethodInput[${statusInput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                            </c:if>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                    <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.ar.removeRow({link:this})"></a></td>
                                                                                                </tr>
                                                                                            </c:if>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:forEach>
                                                                            </tbody>
                                                                        </table>
                                                                        <div class="qp-add-left">
                                                                            <a name="btnAdd" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,templateId:'tblInput-attribute-template',templateData:{groupId:''}});" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div id="tabsDecision-${functionMethod.itemSeqNo }2" name="divOutputLogic" class="tab-pane" style="height: auto;">
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
                                                                                <c:forEach var="functionMethodOutput" items="${functionMethod.functionMethodOutput}" varStatus="statusOutput">
                                                                                    <c:choose>
                                                                                        <c:when test="${functionMethodOutput.dataType == '16' && functionMethodOutput.objectFlg}">
                                                                                            <tr data-ar-rgroup="${functionMethodOutput.groupId}" class="ar-dataRow" data-ar-templateid="tblOutput-common-object-template" data-ar-rindex="${functionMethodOutput.itemSeqNo }" data-ar-rgroupindex="${functionMethodOutput.tableIndex }">
                                                                                                <td colspan="2" style="width: 400px" >
                                                                                                    <div style="height: 100%">
                                                                                                        <div>
                                                                                                            <span class="ar-groupIndex">${functionMethodOutput.tableIndex }</span>
                                                                                                        </div>
                                                                                                        <div class="pull-right" style="height: 100%;">
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputName" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].functionMethodId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].parentFunctionMethodOutputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].itemSeqNo" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectType" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectFlg" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].groupId" class="ar-groupId"/>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                            <qp:autocomplete name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForFM" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeCommonObjectOfIn" value="${functionMethodOutput.methodOutputId}" displayValue="${functionMethodOutput.methodOutputName}"></qp:autocomplete>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><input id="methodOutputCode" value="${functionMethodOutput.methodOutputCode}" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50"></td>
                                                                                                <td>
                                                                                                    <div class="input-group" style="width: 100%">
                                                                                                        <form:select name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" id="dataType" path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this)">
                                                                                                            <form:option value=""><qp:message code="sc.sys.0030" /></form:option>
                                                                                                            <c:forEach var="item" items="${CL_QP_DATATYPE }">
                                                                                                                <form:option value="${item.key}">${item.value}</form:option>
                                                                                                            </c:forEach>
                                                                                                        </form:select>
                                                                                                        <c:if test="${functionMethodOutput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" checked="checked" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                        <c:if test="${!functionMethodOutput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
                                                                                            </tr>
                                                                                        </c:when>
                                                                                        <c:when test="${functionMethodOutput.dataType == '17' && functionMethodOutput.objectFlg}">
                                                                                            <tr data-ar-rgroup="${functionMethodOutput.groupId}" class="ar-dataRow" data-ar-templateid="tblOutput-external-object-template" data-ar-rindex="${functionMethodOutput.itemSeqNo }" data-ar-rgroupindex="${functionMethodOutput.tableIndex }">
                                                                                                <td colspan="2" style="width: 400px" >
                                                                                                    <div style="height: 100%">
                                                                                                        <div>
                                                                                                            <span class="ar-groupIndex">${functionMethodOutput.tableIndex }</span>
                                                                                                        </div>
                                                                                                        <div class="pull-right" style="height: 100%;">
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputName" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].functionMethodId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].parentFunctionMethodOutputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].itemSeqNo" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectType" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectFlg" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].groupId" class="ar-groupId"/>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                            <qp:autocomplete name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForFM" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" mustMatch="true" onChangeEvent="fmOnChangeExternalObjectOfOu" value="${functionMethodOutput.methodOutputId}" displayValue="${functionMethodOutput.methodOutputName}"></qp:autocomplete>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><input id="methodOutputCode" value="${functionMethodOutput.methodOutputCode}" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputCode" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50"></td>
                                                                                                <td>
                                                                                                    <div class="input-group" style="width: 100%">
                                                                                                        <input type="hidden" id="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" value="${functionMethodOutput.dataType}"> 
                                                                                                        <form:select name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" id="dataType" path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this)">
                                                                                                            <form:option value=""><qp:message code="sc.sys.0030" /></form:option>
                                                                                                            <c:forEach var="item" items="${CL_QP_DATATYPE }">
                                                                                                                <form:option value="${item.key}">${item.value}</form:option>
                                                                                                            </c:forEach>
                                                                                                        </form:select>
                                                                                                        <c:if test="${functionMethodOutput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" checked="checked" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                        <c:if test="${!functionMethodOutput.arrayFlg}">
                                                                                                            <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" />Array</label></span>
                                                                                                        </c:if>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.ar.removeRow({link:this})" style="margin-top: 3px;" href="javascript:void(0)"></a></td>
                                                                                            </tr>
                                                                                        </c:when>
                                                                                        <c:when test="${!functionMethodOutput.objectFlg}">
                                                                                            <tr data-ar-rgroup="${functionMethodOutput.groupId}" class="ar-dataRow" data-ar-templateid="tblOutput-object-attribute-template" data-ar-rindex="${functionMethodOutput.itemSeqNo }" data-ar-rgroupindex="${functionMethodOutput.tableIndex }">
                                                                                                <td colspan="2" style="width: 400px" >
                                                                                                    <div style="height: 100%">
                                                                                                        <div>
                                                                                                            <span class="ar-groupIndex">${functionMethodOutput.tableIndex }</span>
                                                                                                        </div>
                                                                                                        <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                                                            <label name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputName" class="qp-output-text">${functionMethodOutput.methodOutputName }</label>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputName" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].functionMethodId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].parentFunctionMethodOutputId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].itemSeqNo" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectType" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectId" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectFlg" />
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].groupId" class="ar-groupId"/>
                                                                                                            <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <label name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputCode" class="qp-input-text">${functionMethodOutput.methodOutputCode }</label>
                                                                                                    <input type="hidden" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputCode" value="${functionMethodOutput.methodOutputCode }"/>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <div class="input-group" style="width: 100%">
                                                                                                        <input type="hidden" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" addChildFlg="false"  value="${functionMethodOutput.dataType }"/>
                                                                                                        <label name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" class="qp-output-text">${CL_QP_DATATYPE.get(functionMethodOutput.dataType.toString())}</label>
                                                                                                        <input type="hidden" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" value="false" />
                                                                                                        <c:if test="${functionMethodOutput.arrayFlg}">
                                                                                                                []
                                                                                                            </c:if>
                                                                                                        <span class="input-group-addon" style="display: none"> <label><form:checkbox path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" /> <qp:message code="sc.businesslogicdesign.0040" /></label>
                                                                                                        </span>
                                                                                                    </div>
                                                                                                </td>
                                                                                                <td></td>
                                                                                            </tr>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <c:if test="${!functionMethodOutput.dataType != null}">
                                                                                                <tr data-ar-rgroup="${functionMethodOutput.groupId}" class="ar-dataRow" data-ar-templateid="tblOutput-attribute-template" data-ar-rindex="${functionMethodOutput.itemSeqNo }" data-ar-rgroupindex="${functionMethodOutput.tableIndex }">
                                                                                                    <td colspan="2" style="width: 400px" >
                                                                                                        <div style="height: 100%">
                                                                                                            <div>
                                                                                                                <span class="ar-groupIndex">${functionMethodOutput.tableIndex }</span>
                                                                                                            </div>
                                                                                                            <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                                                                <input type="text" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" value="${functionMethodOutput.methodOutputName }" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].functionMethodId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].parentFunctionMethodOutputId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].itemSeqNo" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectType" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectId" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].objectFlg" />
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].groupId" class="ar-groupId"/>
                                                                                                                <form:hidden path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].tableIndex" class="ar-groupIndex" />
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                    <td><input name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].methodOutputCode" value="${functionMethodOutput.methodOutputCode }" class="form-control qp-input-text qp-convention-code-row" type="text" value="" maxlength="50"></td>
                                                                                                    <td>
                                                                                                        <div class="input-group" style="width: 100%">
                                                                                                            <form:select name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" path="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].dataType" class="form-control qp-input-select" onchange="onChangeDataType(this);">
                                                                                                                <form:option value=""><qp:message code="sc.sys.0030" /></form:option>
                                                                                                                <c:forEach var="item" items="${CL_QP_DATATYPE }">
                                                                                                                    <form:option value="${item.key}">${item.value}</form:option>
                                                                                                                </c:forEach>
                                                                                                            </form:select>
                                                                                                            
                                                                                                            <c:if test="${functionMethodOutput.arrayFlg}">
                                                                                                                <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" checked="checked" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" /><qp:message code="sc.businesslogicdesign.0040" /></label></span>
                                                                                                            </c:if>
                                                                                                            <c:if test="${!functionMethodOutput.arrayFlg}">
                                                                                                                <span class="input-group-addon"><label><input id="arrayFlg" type="checkbox" name="functionMethod[${status.index}].functionMethodOutput[${statusOutput.index}].arrayFlg" aria-label="Array" /><qp:message code="sc.businesslogicdesign.0040" /></label></span>
                                                                                                            </c:if>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                    <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.ar.removeRow({link:this})"></a></td>
                                                                                                </tr>
                                                                                            </c:if>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:forEach>
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
                        </c:forEach>
                        </div>
                        </tbody>
                    </table>
                </div>
                <div class="qp-div-action form-inline form-group">
                    <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
                        <qp:authorization permission="functionmasterModify">
                        	<div class="checkbox">
								<label>	
									<form:checkbox path="showImpactFlag"/><qp:message code="sc.sys.0097" />
								</label>
							</div>
                            <button type="submit" class="btn qp-button qp-dialog-confirm" id="submitFunctionMaster" data-confirm-pcallback="checkFileSize" onclick="saveFunctionMaster">
                                <qp:message code="sc.sys.0031" />
                            </button>
                            <form:hidden path="updatedDate" />
                            <form:hidden path="updatedBy" />
                            <form:hidden path="functionMasterId" value="${functionMasterForm.functionMasterId}" />
                            <form:hidden path="functionMasterType" />
                            <form:hidden path="projectId" />
                            <form:hidden path="uploadFileId" />
                        </qp:authorization>
                    </c:if>
                </div>
            </c:if>
       </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>