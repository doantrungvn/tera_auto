<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ attribute name="itemValue" required="true" type="org.terasoluna.qp.app.commonobjectdefinition.CommonObjectAttributeForm"%>
<%@ attribute name="viewFlg" required="false" %>
<c:choose>
	<c:when test="${itemValue.dataType == 16 && ((!viewFlg && itemValue.saveFlg == null) || (itemValue.saveFlg))}">
		<tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-common-object-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
			<td colspan="2">
				<div style="height:100%">
					<div>
						<span class="ar-groupIndex">${itemValue.tableIndex }</span>
					</div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">
						<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
						<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
						<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeId" value="${itemValue.commonObjectAttributeId}"/>
						<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeName" value="${itemValue.commonObjectAttributeName}"/>
						<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].parentCommonObjectAttributeId" value="${itemValue.parentCommonObjectAttributeId}"/>
						<qp:autocomplete name = "commonObjectAttributes[${itemValue.itemSeqNo}].objectDefinitionId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetCommonObject" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${commonObjectDefinitionForm.commonObjectDefinitionId}" arg04="${commonObjectDefinitionForm.moduleId}" mustMatch="true" onChangeEvent="onChangeCommonObject" value="${itemValue.objectDefinitionId }" displayValue="${itemValue.commonObjectAttributeName}"></qp:autocomplete>
						<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].moduleId" value="${itemValue.moduleId}"/>
					</div>
				</div>
			</td>
	        <td>
				<input type="text" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" value="${itemValue.commonObjectAttributeCode}"/>
			</td>
			<td>
				<div class="input-group">
					<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}"/>
					<select name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);"  oldvalue="${itemValue.dataType}">
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
					<span class="input-group-addon">
                        <label>
                            <c:if test="${itemValue.arrayFlg}">
                                <input type="checkbox" aria-label="Array" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" checked="checked" />Array
                            </c:if>
                            <c:if test="${!itemValue.arrayFlg}">
                                <input type="checkbox" aria-label="Array" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" />Array
                            </c:if>
                        </label>
                    </span>
				</div>
	         </td>
	         <td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
				<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="true"/>
			</td>
		</tr>
		<c:if test="${itemValue.commonObjectDefinition != null && itemValue.commonObjectDefinition.commonObjectAttributes != null}">
			<c:forEach items="${itemValue.commonObjectDefinition.commonObjectAttributes}" var="itemDetail" varStatus="status">
				<qp:commonModifyRow itemValue="${itemDetail}" viewFlg="true"/>
			</c:forEach>
		</c:if>
	</c:when>
	<c:when test="${itemValue.dataType == 17 && ((!viewFlg && itemValue.saveFlg == null) || (itemValue.saveFlg))}">
		<tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-common-object-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
            <td colspan="2">
                <div style="height:100%">
                    <div>
                        <span class="ar-groupIndex">${itemValue.tableIndex }</span>
                    </div>
                    <div class="pull-right" style="height:100%;vertical-align: middle;">
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeId" value="${itemValue.commonObjectAttributeId}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeName" value="${itemValue.commonObjectAttributeName}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].parentCommonObjectAttributeId" value="${itemValue.parentCommonObjectAttributeId}"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="true"/> 
                        <qp:autocomplete name = "commonObjectAttributes[${itemValue.itemSeqNo}].objectDefinitionId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObject" optionValue="optionValue"
                            arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="" arg04="${commonObjectDefinitionForm.moduleId}" mustMatch="true" onChangeEvent="onChangeExternalObject" value="${itemValue.objectDefinitionId }" displayValue="${itemValue.commonObjectAttributeName}"></qp:autocomplete>
                    	<input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].moduleId" value="${itemValue.moduleId}"/>
                    </div>
                </div>
            </td>
            <td>
                <input type="text" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" value="${itemValue.commonObjectAttributeCode}"/>
            </td>
            <td>
                <div class="input-group">
                    <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}"/>
                    <select name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);"  oldvalue="${itemValue.dataType}">
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
                    <span class="input-group-addon">
                        <label>
                            <c:if test="${itemValue.arrayFlg}">
                                <input type="checkbox" aria-label="Array" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" checked="checked" />Array
                            </c:if>
                            <c:if test="${!itemValue.arrayFlg}">
                                <input type="checkbox" aria-label="Array" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" />Array
                            </c:if>
                        </label>
                    </span>
                </div>
             </td>
             <td>
                <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
            </td>
        </tr>
        <c:if test="${itemValue.externalObjectDefinition != null && itemValue.externalObjectDefinition.externalObjectAttributes != null}">
            <c:forEach items="${itemValue.externalObjectDefinition.externalObjectAttributes}" var="itemDetail" varStatus="status">
                <qp:commonModifyExternalRow itemValue="${itemDetail}" viewFlg="true"/>
            </c:forEach>
        </c:if>
	</c:when>
	<c:otherwise>
        <c:choose>
            <c:when test="${(!viewFlg && itemValue.saveFlg == null) || (itemValue.saveFlg)}">
                <tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
                <td colspan="2">
                    <div style="height:100%">
                        <div>
                            <span class="ar-groupIndex">${itemValue.tableIndex }</span>
                        </div>
                        <div class="pull-right" style="height:100%;vertical-align: middle;">
                            <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
                            <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                            <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeId" value="${itemValue.commonObjectAttributeId}"/>
                            <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
                            <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].parentCommonObjectAttributeId" value="${itemValue.parentCommonObjectAttributeId}"/>
                            <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="true"/>
                            <input type="text" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" value="${itemValue.commonObjectAttributeName}"/>
                        </div>
                    </div>
                </td>
                <td>
                    <input type="text" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" value="${itemValue.commonObjectAttributeCode}"/>
                </td>
                <td>
                    <div class="input-group">
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}"/>
                        <select name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);" value="${itemValue.dataType}">
                            <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                            <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_OBJECT}">
                                <c:if test='${item.key == itemValue.dataType}'>
                                    <option value="${item.key}" selected="selected" >${item.value}</option>
                                </c:if>
                                <c:if test='${item.key != itemValue.dataType}'>
                                    <option value="${item.key}">${item.value}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <span class="input-group-addon">
                            <label>
                                <c:if test="${itemValue.arrayFlg}">
                                    <input type="checkbox" aria-label="Array" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" checked="checked" />Array
                                </c:if>
                                <c:if test="${!itemValue.arrayFlg}">
                                    <input type="checkbox" aria-label="Array" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" />Array
                                </c:if>
                            </label>
                        </span>
                    </div>
                 </td>
                 <td>
                    <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
                </td>
            </tr>
            </c:when>
            <c:otherwise>
                <tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-common-object-columm-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
                    <td colspan="2" style="width: 490px">
                        <div style="height:100%">
                            <div>
                                <span class="ar-groupIndex">${itemValue.tableIndex }</span>
                            </div>
                            <div class="pull-right" style="height:100%;vertical-align: middle;">
                                <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
                                <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                                <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
                                <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="false"/>
                                <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeName" value="${itemValue.commonObjectAttributeName}"/>
                                <label class="qp-output-text">${itemValue.commonObjectAttributeName}</label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].commonObjectAttributeCode" value="${itemValue.commonObjectAttributeCode}"/>
                        <label class="qp-output-text">${itemValue.commonObjectAttributeCode}</label>
                    </td>
                    <td style="text-align: left;">${CL_EX_DATATYPE.get(itemValue.dataType.toString())}
                        <c:if test="${itemValue.arrayFlg }">
                        []
                        </c:if>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}" addChildFlg="false"/>
                        <input type="hidden" name="commonObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" value="${itemValue.arrayFlg}"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <c:if test="${itemValue.dataType == 16 && itemValue.commonObjectDefinition != null && itemValue.commonObjectDefinition.commonObjectAttributes != null}">
                    <c:forEach items="${itemValue.commonObjectDefinition.commonObjectAttributes}" var="itemDetail" varStatus="status">
                        <qp:commonModifyRow itemValue="${itemDetail}" viewFlg="true"/>
                    </c:forEach>
                </c:if>
                <c:if test="${itemValue.dataType == 17 && itemValue.externalObjectDefinition != null && itemValue.externalObjectDefinition.externalObjectAttributes != null}">
                    <c:forEach items="${itemValue.externalObjectDefinition.externalObjectAttributes}" var="itemDetail" varStatus="status">
                        <qp:commonModifyExternalRow itemValue="${itemDetail}" viewFlg="true"/>
                    </c:forEach>
                </c:if>
            </c:otherwise>
        </c:choose>
	</c:otherwise>
</c:choose>