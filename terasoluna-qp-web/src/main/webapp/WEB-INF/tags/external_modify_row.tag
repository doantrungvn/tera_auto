<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ attribute name="itemValue" required="true" type="org.terasoluna.qp.app.externalobjectdefinition.ExternalObjectAttributeForm"%>
<%@ attribute name="viewFlg" required="false" %>

<c:choose>
	<c:when test="${itemValue.dataType == 17 && ((!viewFlg && itemValue.saveFlg == null) || (itemValue.saveFlg))}">
		<tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
			<td colspan="2">
				<div style="height:100%">
					<div>
						<span class="ar-groupIndex">${itemValue.tableIndex}</span>
					</div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
						<input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeId" value="${itemValue.externalObjectAttributeId}"/>
						<input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeName" value="${itemValue.externalObjectAttributeName}"/>
						<input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].parentExternalObjectAttributeId" value="${itemValue.parentExternalObjectAttributeId}"/>
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="true"/>
						<qp:autocomplete name = "externalObjectAttributes[${itemValue.itemSeqNo}].objectDefinitionId" optionLabel="optionLabel" selectSqlId="getAutocompleteGetExternalObjectForEO" optionValue="optionValue" 
							arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" arg03="${externalObjectDefinitionForm.externalObjectDefinitionId}" arg04="${externalObjectDefinitionForm.moduleId}" mustMatch="true" onChangeEvent="onChangeExternalObject" value="${itemValue.objectDefinitionId }" displayValue="${itemValue.externalObjectAttributeName}"></qp:autocomplete>
						<input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].moduleId" value="${itemValue.moduleId}"/>
					</div>
				</div>
			</td>
	        <td>
				<input type="text" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" value="${itemValue.externalObjectAttributeCode}"/>
			</td>
			<td>
				<div class="input-group">
					<input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}"/>
					<select name="externalObjectAttributes[${itemValue.itemSeqNo}].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);"  oldvalue="${itemValue.dataType}">
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
			      	<span class="input-group-addon">
                        <label>
                            <c:if test="${itemValue.arrayFlg}">
                                <input type="checkbox" aria-label="Array" name="externalObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" checked="checked" />Array
                            </c:if>
                            <c:if test="${!itemValue.arrayFlg}">
                                <input type="checkbox" aria-label="Array" name="externalObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" />Array
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
				<qp:externalModifyRow itemValue="${itemDetail}" viewFlg="true"/>
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
                                <span  class="ar-groupIndex">${itemValue.tableIndex}</span>
                            </div>
                            <div class="pull-right" style="height:100%;vertical-align: middle;">    
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeId" value="${itemValue.externalObjectAttributeId}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].parentExternalObjectAttributeId" value="${itemValue.parentExternalObjectAttributeId}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="true"/>
                                <input type="text" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeName" class="form-control qp-input-text qp-convention-name-row" maxlength="200" value="${itemValue.externalObjectAttributeName}"/>
                            </div>
                        </div>
                    </td>
                    <td>
                        <input type="text" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeCode" class="form-control qp-input-text qp-convention-code-row" maxlength="50" value="${itemValue.externalObjectAttributeCode}"/>
                    </td>
                    <td>
                        <div class="input-group">
                            <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}"/>
                            <select name="externalObjectAttributes[${itemValue.itemSeqNo}].dataType" class="form-control qp-input-select pull-left" onchange="objectTypeChange(this);" value="${itemValue.dataType}">
                                <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
                                <c:forEach var="item" items="${CL_EX_DATATYPE_NOT_COMMON_OBJECT}">
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
                                        <input type="checkbox" aria-label="Array" name="externalObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" checked="checked" />Array
                                    </c:if>
                                    <c:if test="${!itemValue.arrayFlg}">
                                        <input type="checkbox" aria-label="Array" name="externalObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" />Array
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
                <tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-external-object-columm-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
                    <td colspan="2" style="width: 490px">
                        <div style="height:100%">
                            <div>
                                <span class="ar-groupIndex">${itemValue.tableIndex}</span>
                            </div>
                            <div class="pull-right" style="height:100%;vertical-align: middle;">
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].groupId" value="${itemValue.groupId}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].tableIndex" value="${itemValue.tableIndex}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].itemSeqNo" value="${itemValue.itemSeqNo}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeName" value="${itemValue.externalObjectAttributeName}"/>
                                <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].saveFlg" value="false"/>
                                <label class="qp-output-text">${itemValue.externalObjectAttributeName}</label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].externalObjectAttributeCode" value="${itemValue.externalObjectAttributeCode}"/>
                        <label class="qp-output-text">${itemValue.externalObjectAttributeCode}</label>
                    </td>
                    <td style="text-align: left;">${CL_EX_DATATYPE_NOT_COMMON_OBJECT.get(itemValue.dataType.toString())}
                        <c:if test="${itemValue.arrayFlg }">
                        []
                        </c:if>
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].dataType" value="${itemValue.dataType}" addChildFlg="false"/>
                        <input type="hidden" name="externalObjectAttributes[${itemValue.itemSeqNo}].arrayFlg" value="${itemValue.arrayFlg}"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <c:if test="${itemValue.dataType == 17 && itemValue.externalObjectDefinition != null && itemValue.externalObjectDefinition.externalObjectAttributes != null}">
                    <c:forEach items="${itemValue.externalObjectDefinition.externalObjectAttributes}" var="itemDetail" varStatus="status">
                        <qp:externalModifyRow itemValue="${itemDetail}" viewFlg="true"/>
                    </c:forEach>
                </c:if>
            </c:otherwise>
        </c:choose>
	</c:otherwise>
</c:choose>