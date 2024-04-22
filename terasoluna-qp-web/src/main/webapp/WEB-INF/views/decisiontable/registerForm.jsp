<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.decisiontable"></qp:message></span></li>
         <li><span><qp:message code="sc.decisiontable.0009"/></span></li>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="header-link">
        <qp:authorization permission="decisiontableSearch">
        	<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
            <a class="com-link-popup" href="${pageContext.request.contextPath}/decisiontable/search"><qp:message code="sc.decisiontable.0008"/></a>
        </qp:authorization>
    </tiles:putAttribute>
    
    <tiles:putAttribute name="additionalHeading">
        <script type="text/javascript" src="${pageContext.request.contextPath}/dbMsgSource.js?module=decisiontable_sqldesign_sys"></script>
        <link type="text/css" href="${pageContext.request.contextPath}/resources/app/businessdesign/css/businessBasicDesign.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resources/app/decisionTable/css/style.css" type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/constant.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/common.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/process.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/init.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/drawlogic.js" ></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/decisionTable/javascript/validation.js"></script>

        <!-- formula builder -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/formulaBuilder.js"></script>
        <!-- method -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businessdesign/javascript/functions.js"></script>
        
                
        <script type="text/javascript">
            $( document ).ready(function() {
            	$.qp.decisiontable.initAddButtonRow('tbl_input_list_result', 'tbl_input_list_result-action-template');
            	$.qp.decisiontable.initAddButtonRow('tbl_output_list_result', 'tbl_output_list_result-action-template');
    		});
            
    		var MAX_NESTED_OBJECT = '${CL_SYSTEM_SETTING["maxNestedObject"]}';
    		MAX_NESTED_OBJECT = 1;
        </script>
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <!-- Include row template -->
        <jsp:include page="rowTemplate.jsp"/>

        <form:form method="post" modelAttribute="decisionTableForm"
            action="${pageContext.request.contextPath}/decisiontable/register">
            <form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />

            <!-- Begin include business check component -->
            <jsp:include page="../businessdesign/component/formulaBuilder.jsp" />
            <!-- End include business check component -->

            <!-- Begin include business check component -->
            <jsp:include page="../businessdesign/component/functionlist.jsp" />
            <!-- End include business check component -->

            <!-- Hidden define -->
            <input type="hidden" name="listInput">
            <input type="hidden" name="listOutput">
            <input type="hidden" name="listItemCondition">
            <input type="hidden" name="listItemAction">
            <input type="hidden" name="listConditionGroup">
            <input type="hidden" name="listConditionItem">

            <!-- Mark select -->
            <input type="hidden" name="currentDT">
    
    
            <div class="panel panel-default qp-div-information">
                <div class="panel-heading">
                    <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
                    <span class="pq-heading-text"><qp:message code="sc.decisiontable.0007"/></span>
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
                            <th>
                                <form:label path="decisionTbName">
                                        <qp:message code="sc.decisiontable.0005"/>&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029"/></span>
                                </form:label>
                            </th>
                            <td><form:input type="text" cssClass="form-control qp-input-text qp-convention-name" path="decisionTbName" maxlength="200" autofocus="true"/></td>
                            <th>
                                <form:label path="decisionTbCode">
                                    <qp:message code="sc.decisiontable.0006"/>&nbsp;<span class="qp-required-field"><qp:message code="sc.sys.0029"/></span>
                                </form:label>
                            </th>
                            <td><form:input type="text" cssClass="form-control qp-input-text qp-convention-code" path="decisionTbCode" maxlength="50" /></td>
                        </tr>
                        <tr>
                            <th>
                                <form:label path="moduleName">
                                    <qp:message code="sc.module.0007"/>
                                </form:label>
                            </th>
                            <td><qp:autocomplete 
                                        optionValue="optionValue" optionLabel="optionLabel" emptyLabel="Enter module here" 
                                        selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" 
                                        name="moduleId" arg01="${sessionScope.CURRENT_PROJECT.projectId }" arg02 = "20" arg03 = "1" 
                                        displayValue="${decisionTableForm.moduleIdAutocomplete}" 
                                        value="${decisionTableForm.moduleId}"  onChangeEvent="onchangeModuleAffectObjExt">
                                </qp:autocomplete></td>
                            <th><form:label path="designStatus"><qp:message code="sc.sys.0055" /></form:label></th>
							<td><input type="hidden" name="designStatus" value="1"/><qp:message code="${CL_DESIGN_STATUS.get('1')}"/></td>
                        </tr>
                        <tr>
							<th>
                                <form:label path="remark">
                                    <qp:message code="sc.sys.0028"/>
                                </form:label>
                            </th>
                            <td colspan="3"><form:textarea path="remark" cssClass="form-control qp-input-textarea" rows="3" maxlength="2000"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            
            <!-- Block for display tab -->
            <div id="tabsDecision">
                <ul class="nav nav-tabs" id="com-menu-sidebar">
                    <li><a href="#tabsDecision-2" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0011"/></a></li>
                    <li><a href="#tabsDecision-3" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0012"/></a></li>
                    <li><a href="#tabsDecision-4" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0013"/></a></li>
                    <li class="active"><a href="#tabsDecision-1" data-toggle="tab" style="font: bold;"><qp:message code="sc.decisiontable.0014"/></a></li>
                </ul>
                
                <div class="tab-content">
                    <div id="tabsDecision-1" class="tab-pane active" style="height: auto;">
                    </div>
                    
                    <div id="tabsDecision-2" class="tab-pane" style="height: auto;">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div>
                                    <table class="table table-bordered qp-table-list" id="tbl_input_list_result" data-ar-dataClass="ar-dataRow" 
                                            data-ar-callback="$.qp.decisiontable.formCallback">
                                        <colgroup>
                                            <col />
	                                        <col width="450px"/>
	                                        <col width="25%"/>
	                                        <col width="25%"/>
	                                        <col />
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th><qp:message code="sc.sys.0004"/></th>
	                                            <th><qp:message code="sc.decisiontable.0015"/></th>
	                                            <th><qp:message code="sc.decisiontable.0016"/></th>
	                                            <th><qp:message code="sc.decisiontable.0017"/></th>
	                                            <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${decisionTableForm.inputLst }" var="inputBean" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${inputBean.dataType == '16' && inputBean.objectFlg}">
                                                        <tr data-ar-rgroup="${inputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-common-object-template" data-ar-rindex="${inputBean.itemSequenceNo }" data-ar-rgroupindex="${inputBean.tableIndex }">
                                                            <td colspan="2" style="width: 491px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${inputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanName" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].parentDecisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="inputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="inputLst[${status.index}].objectType" />
                                                                        <form:hidden path="inputLst[${status.index}].objectId" />
                                                                        <form:hidden path="inputLst[${status.index}].objectFlg" value="true" />
                                                                        <form:hidden path="inputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="inputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <qp:autocomplete name="inputLst[${status.index}].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForDT" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeCommonObjectOfIn" value="${inputBean.decisionInputBeanId}" displayValue="${inputBean.decisionInputBeanName}"></qp:autocomplete>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:input path="inputLst[${status.index}].decisionInputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
                                                            <td>
                                                                <div>
                                                                    <form:hidden path="inputLst[${status.index}].dataType" />
                                                                    <form:select cssClass="form-control qp-input-select" path="inputLst[${status.index}].dataType" oldvalue="${inputBean.dataType}" onchange="changeDataType(this);">
                                                                        <form:option value="">
                                                                            <qp:message code="sc.sys.0030"></qp:message>
                                                                        </form:option>
                                                                        <form:options items="${CL_QP_DATATYPE}" />
                                                                    </form:select>
                                                                </div>
                                                            </td>
                                                            <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when test="${inputBean.dataType == '17' && inputBean.objectFlg}">
                                                        <tr data-ar-rgroup="${inputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-external-object-template" data-ar-rindex="${inputBean.itemSequenceNo }" data-ar-rgroupindex="${inputBean.tableIndex }">
                                                            <td colspan="2" style="width: 491px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${inputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanName" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].parentDecisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="inputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="inputLst[${status.index}].objectType" />
                                                                        <form:hidden path="inputLst[${status.index}].objectId" />
                                                                        <form:hidden path="inputLst[${status.index}].objectFlg" value="true" />
                                                                        <form:hidden path="inputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="inputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <qp:autocomplete name="inputLst[${status.index}].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForDT" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeExternalObjectOfIn" value="${inputBean.decisionInputBeanId}" displayValue="${inputBean.decisionInputBeanName}"></qp:autocomplete>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:input path="inputLst[${status.index}].decisionInputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
                                                            <td>
                                                                <div>
                                                                    <form:hidden path="inputLst[${status.index}].dataType" />
                                                                    <form:select cssClass="form-control qp-input-select" path="inputLst[${status.index}].dataType"  oldvalue="${inputBean.dataType}" onchange="changeDataType(this);">
                                                                        <form:option value="">
                                                                            <qp:message code="sc.sys.0030"></qp:message>
                                                                        </form:option>
                                                                        <form:options items="${CL_QP_DATATYPE}" />
                                                                    </form:select>
                                                                </div>
                                                            </td>
                                                            <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when test="${!inputBean.objectFlg}">
                                                        <tr data-ar-rgroup="${inputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-column-template" data-ar-rindex="${inputBean.itemSequenceNo }" data-ar-rgroupindex="${inputBean.tableIndex }">
                                                            <td colspan="2" style="width: 491px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${inputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; text-align: left; vertical-align: middle;">
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanName" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].parentDecisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="inputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="inputLst[${status.index}].objectType" />
                                                                        <form:hidden path="inputLst[${status.index}].objectId" />
                                                                        <form:hidden path="inputLst[${status.index}].objectFlg" value="false" />
                                                                        <form:hidden path="inputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="inputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <label name="inputLst[${status.index}].decisionInputBeanName" class="qp-output-text">${inputBean.decisionInputBeanName}</label>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:hidden path="inputLst[${status.index}].decisionInputBeanCode" /> <label name="inputLst[${status.index}].decisionInputBeanCode" class="qp-output-text">${inputBean.decisionInputBeanCode}</label></td>
                                                            <td>
                                                                <div>
                                                                    <form:hidden path="inputLst[${status.index}].dataType" addChildFlg="false" />
                                                                    <label name="inputLst[${status.index}].dataType" class="qp-output-text">${CL_QP_DATATYPE.get(inputBean.dataType.toString())}</label>
                                                                </div>
                                                            </td>
                                                            <td><span></span></td>

                                                            <td></td>
                                                        </tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr data-ar-rgroup="${inputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_inputbean_list_define-template" data-ar-rindex="${inputBean.itemSequenceNo }" data-ar-rgroupindex="${inputBean.tableIndex }">
                                                            <td colspan="2" style="width: 492px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${inputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                        <form:hidden path="inputLst[${status.index}].decisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].parentDecisionInputBeanId" />
                                                                        <form:hidden path="inputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="inputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="inputLst[${status.index}].objectType" />
                                                                        <form:hidden path="inputLst[${status.index}].objectId" />
                                                                        <form:hidden path="inputLst[${status.index}].objectFlg" value="true" />
                                                                        <form:hidden path="inputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="inputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <form:input path="inputLst[${status.index}].decisionInputBeanName" class="form-control qp-input-text" maxlength="50" />
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:input path="inputLst[${status.index}].decisionInputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
                                                            <td>
                                                                <div>
                                                                    <form:select cssClass="form-control qp-input-select" path="inputLst[${status.index}].dataType" onchange="changeDataType(this);">
                                                                        <form:option value="">
                                                                            <qp:message code="sc.sys.0030"></qp:message>
                                                                        </form:option>
                                                                        <form:options items="${CL_QP_DATATYPE}" />
                                                                    </form:select>
                                                                </div>
                                                            </td>
                                                            <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="qp-div-action-table">
                                         <a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" 
                                              onclick="$.qp.ar.addRow({link:this,tableId:'tbl_input_list_result',templateId:'tbl_input_list_result-template',templateDate:{groupId:''}});"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div id="tabsDecision-3" class="tab-pane" style="height: auto;">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div>
                                    <table class="table table-bordered qp-table-list" id="tbl_output_list_result" data-ar-dataClass="ar-dataRow" 
                                            data-ar-callback="$.qp.decisiontable.formCallback">
                                        <colgroup>
                                            <col />
                                            <col width="450px"/>
                                            <col width="25%"/>
                                            <col width="25%"/>
                                            <col />
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th><qp:message code="sc.sys.0004"/></th>
                                                <th><qp:message code="sc.decisiontable.0015"/></th>
                                                <th><qp:message code="sc.decisiontable.0016"/></th>
                                                <th><qp:message code="sc.decisiontable.0017"/></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${decisionTableForm.outputLst }" var="outputBean" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${outputBean.dataType == '16' && outputBean.objectFlg}">
                                                        <tr data-ar-rgroup="${outputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-common-object-template" data-ar-rindex="${outputBean.itemSequenceNo }" data-ar-rgroupindex="${outputBean.tableIndex }">
                                                            <td colspan="2" style="width: 491px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${outputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanName" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].parentDecisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="outputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="outputLst[${status.index}].objectType" />
                                                                        <form:hidden path="outputLst[${status.index}].objectId" />
                                                                        <form:hidden path="outputLst[${status.index}].objectFlg" value="true" />
                                                                        <form:hidden path="outputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="outputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <qp:autocomplete name="outputLst[${status.index}].commonObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteCommonObjectForDT" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeCommonObjectOfIn" value="${outputBean.decisionOutputBeanId}" displayValue="${outputBean.decisionOutputBeanName}"></qp:autocomplete>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:input path="outputLst[${status.index}].decisionOutputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
                                                            <td>
                                                                <div>
                                                                    <form:hidden path="outputLst[${status.index}].dataType" />
                                                                    <form:select cssClass="form-control qp-input-select" path="outputLst[${status.index}].dataType" oldvalue="${outputBean.dataType}" onchange="changeDataType(this);">
                                                                        <form:option value="">
                                                                            <qp:message code="sc.sys.0030"></qp:message>
                                                                        </form:option>
                                                                        <form:options items="${CL_QP_DATATYPE}" />
                                                                    </form:select>
                                                                </div>
                                                            </td>
                                                            <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when test="${outputBean.dataType == '17' && outputBean.objectFlg}">
                                                        <tr data-ar-rgroup="${outputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-external-object-template" data-ar-rindex="${outputBean.itemSequenceNo }" data-ar-rgroupindex="${outputBean.tableIndex }">
                                                            <td colspan="2" style="width: 491px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${outputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanName" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].parentDecisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="outputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="outputLst[${status.index}].objectType" />
                                                                        <form:hidden path="outputLst[${status.index}].objectId" />
                                                                        <form:hidden path="outputLst[${status.index}].objectFlg" value="true" />
                                                                        <form:hidden path="outputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="outputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <qp:autocomplete name="outputLst[${status.index}].externalObjectId" optionLabel="optionLabel" selectSqlId="getAutocompleteExternalObjectForDT" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${decisionTableForm.moduleId}" mustMatch="true" onChangeEvent="dtOnChangeExternalObjectOfOu" value="${outputBean.decisionOutputBeanId}" displayValue="${outputBean.decisionOutputBeanName}"></qp:autocomplete>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:input path="outputLst[${status.index}].decisionOutputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
                                                            <td>
                                                                <div>
                                                                    <form:hidden path="outputLst[${status.index}].dataType" />
                                                                    <form:select cssClass="form-control qp-input-select" path="outputLst[${status.index}].dataType" oldvalue="${outputBean.dataType}" onchange="changeDataType(this);">
                                                                        <form:option value="">
                                                                            <qp:message code="sc.sys.0030"></qp:message>
                                                                        </form:option>
                                                                        <form:options items="${CL_QP_DATATYPE}" />
                                                                    </form:select>
                                                                </div>
                                                            </td>
                                                            <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when test="${!outputBean.objectFlg}">
                                                        <tr data-ar-rgroup="${outputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-column-template" data-ar-rindex="${outputBean.itemSequenceNo }" data-ar-rgroupindex="${outputBean.tableIndex }">
                                                            <td colspan="2" style="width: 491px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${outputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; text-align: left; vertical-align: middle;">
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanName" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].parentDecisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="outputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="outputLst[${status.index}].objectType" />
                                                                        <form:hidden path="outputLst[${status.index}].objectId" />
                                                                        <form:hidden path="outputLst[${status.index}].objectFlg" value="false" />
                                                                        <form:hidden path="outputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="outputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <label name="outputLst[${status.index}].decisionOutputBeanName" class="qp-output-text">${outputBean.decisionOutputBeanName}</label>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:hidden path="outputLst[${status.index}].decisionOutputBeanCode" /> <label name="outputLst[${status.index}].decisionOutputBeanCode" class="qp-output-text">${outputBean.decisionOutputBeanCode}</label></td>
                                                            <td>
                                                                <div>
                                                                    <form:hidden path="outputLst[${status.index}].dataType" addChildFlg="false" />
                                                                    <label name="outputLst[${status.index}].dataType" class="qp-output-text">${CL_QP_DATATYPE.get(outputBean.dataType.toString())}</label>
                                                                </div>
                                                            </td>
                                                            <td><span></span></td>

                                                            <td></td>
                                                        </tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr data-ar-rgroup="${outputBean.groupId}" class="ar-dataRow" data-ar-templateid="tbl_outputbean_list_define-template" data-ar-rindex="${outputBean.itemSequenceNo }" data-ar-rgroupindex="${outputBean.tableIndex }">
                                                            <td colspan="2" style="width: 492px">
                                                                <div style="height: 100%">
                                                                    <div>
                                                                        <span class="ar-groupIndex">${outputBean.tableIndex }</span>
                                                                    </div>
                                                                    <div class="pull-right" style="height: 100%; vertical-align: middle;">
                                                                        <form:hidden path="outputLst[${status.index}].decisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].parentDecisionOutputBeanId" />
                                                                        <form:hidden path="outputLst[${status.index}].decisionTbId" />
                                                                        <form:hidden path="outputLst[${status.index}].itemSequenceNo" class="ar-rIndex" />
                                                                        <form:hidden path="outputLst[${status.index}].objectType" />
                                                                        <form:hidden path="outputLst[${status.index}].objectId" />
                                                                        <form:hidden path="outputLst[${status.index}].objectFlg" value="true" />
                                                                        <form:hidden path="outputLst[${status.index}].groupId" class="ar-groupId"/>
                                                                        <form:hidden path="outputLst[${status.index}].tableIndex" class="ar-groupIndex" />
                                                                        <form:input path="outputLst[${status.index}].decisionOutputBeanName" class="form-control qp-input-text" maxlength="50" />
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td><form:input path="outputLst[${status.index}].decisionOutputBeanCode" class="form-control qp-input-text" maxlength="50" /></td>
                                                            <td>
                                                                <div>
                                                                    <form:select cssClass="form-control qp-input-select" path="outputLst[${status.index}].dataType" onchange="changeDataType(this);">
                                                                        <form:option value="">
                                                                            <qp:message code="sc.sys.0030"></qp:message>
                                                                        </form:option>
                                                                        <form:options items="${CL_QP_DATATYPE}" />
                                                                    </form:select>
                                                                </div>
                                                            </td>
                                                            <td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />'></a></td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="qp-div-action-table">
                                         <a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" 
                                              onclick="$.qp.ar.addRow({link:this,tableId:'tbl_output_list_result',templateId:'tbl_output_list_result-template',templateDate:{groupId:''}});"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Item condition tabs -->
                    <div id="tabsDecision-4" class="tab-pane" style="height: auto;">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div>
                                    <table class="table table-bordered qp-table-list" id="tbl_item_condition">
                                        <colgroup>
                                            <col width="4%"/>
                                            <col width="30%"/>
                                            <col width="25%"/>
                                            <col width="18%"/>
                                            <col width="15%"/>
                                            <col width="4%"/>
                                            <col width="4%"/>
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th colspan="7">
                                                    <qp:message code="sc.decisiontable.0018"/>
                                                </th>
                                            </tr>
                                            <tr>
                                                <th><qp:message code="sc.sys.0004"/></th>
                                                <th><qp:message code="sc.decisiontable.0042"/></th>
                                                <th><qp:message code="sc.decisiontable.0036"/></th>
                                                <th><qp:message code="sc.decisiontable.0037"/></th>
                                                <th><qp:message code="sc.decisiontable.0038"/></th>
                                                <th>&nbsp;</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <div class="qp-add-left qp-div-action-table">
                                        <a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" 
                                            onclick="$.qp.addRowJSByLink(this);" href="javascript:void(0)"></a>
                                    </div>
                                    <script type="text/javascript">
                                         var obj = $.qp.decisiontable.convertToJson('${decisionTableForm.listItemCondition }');
                                         var htmlOutput = returnBeanHTML(obj, BEAN_TYPE.CONDITION);
                                         $('#tbl_item_condition tbody').prepend(htmlOutput);
                                    </script>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div >
                                    <table class="table table-bordered qp-table-list" id="tbl_item_action">
                                        <colgroup>
                                            <col width="4%"/>
                                            <col width="30%"/>
                                            <col width="25%"/>
                                            <col width="18%"/>
                                            <col width="15%"/>
                                            <col width="4%"/>
                                            <col width="4%"/>
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th colspan="7">
                                                    <qp:message code="sc.decisiontable.0019"/>
                                                </th>
                                            </tr>
                                            <tr>
                                                <th><qp:message code="sc.sys.0004"/></th>
                                                <th><qp:message code="sc.decisiontable.0043"/></th>
                                                <th><qp:message code="sc.decisiontable.0039"/></th>
                                                <th><qp:message code="sc.decisiontable.0040"/></th>
                                                <th><qp:message code="sc.decisiontable.0041"/></th>
                                                <th>&nbsp;</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <div class="qp-add-left qp-div-action-table">
                                        <a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" 
                                            onclick="$.qp.addRowJSByLink(this);" href="javascript:void(0)"></a>
                                    </div>
                                    <script type="text/javascript">
                                         var obj = $.qp.decisiontable.convertToJson('${decisionTableForm.listItemAction }');
                                         var htmlOutput = returnBeanHTML(obj, BEAN_TYPE.ACTION);
                                         $("#tbl_item_action").find('tr[class=result]').remove();
                                         $('#tbl_item_action tbody').prepend(htmlOutput);
                                    </script>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="qp-div-action">
            	<c:if test="${sessionScope.CURRENT_PROJECT.status eq 1}">
	                <qp:authorization permission="decisiontableRegister">
	                    <button type="submit" class="btn qp-button qp-dialog-confirm" name="mode" value="2" onclick="validateSubmit"><qp:message code="sc.sys.0031"/></button>
	                </qp:authorization>
                </c:if>
            </div>
            <script type="text/javascript">
	             var listConditionGroup = $.qp.decisiontable.convertToJson('${decisionTableForm.listConditionGroup }');
	             if(listConditionGroup.length != undefined) {
	                 var stringJson = $.qp.decisiontable.convertToString(listConditionGroup);
	                 $('form').find("input:hidden[name$='listConditionGroup']").val(stringJson);
	                 var listConditionItem = $.qp.decisiontable.convertToJson('${decisionTableForm.listConditionItem }');
	                 if(listConditionItem.length != undefined){
	                     stringJson = $.qp.decisiontable.convertToString(listConditionItem);
	                     $('form').find("input:hidden[name$='listConditionItem']").val(stringJson);
	                 }
	             }
	        </script>
            <br />
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>