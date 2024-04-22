<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ attribute name="itemValue" required="true" type="org.terasoluna.qp.app.commonobjectdefinition.CommonObjectAttributeForm"%>
<c:choose>
	<c:when test="${itemValue.dataType == 16}">
		<tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-common-object-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
			<td colspan="2" style="width: 490px">
				<div style="height:100%">
					<div>
						<span  class="ar-groupIndex">${itemValue.tableIndex }</span>
					</div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">
						<label class="qp-output-text">${itemValue.commonObjectAttributeName}</label>
					</div>
				</div>
			</td>
			<td>
				<label class="qp-output-text">${itemValue.commonObjectAttributeCode}</label>
			</td>
			<td style="text-align: left;">${CL_EX_DATATYPE.get(itemValue.dataType.toString())}
				<c:if test="${itemValue.arrayFlg }">
				[]
				</c:if>
			</td>
		</tr>
		<c:if test="${itemValue.commonObjectDefinition != null && itemValue.commonObjectDefinition.commonObjectAttributes != null}">
			<c:forEach items="${itemValue.commonObjectDefinition.commonObjectAttributes}" var="itemDetail" varStatus="status">
				<qp:commonViewRow itemValue="${itemDetail}"/>
			</c:forEach>
		</c:if>
	</c:when>
	<c:when test="${itemValue.dataType == 17}">
		<tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-common-object-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
            <td colspan="2" style="width: 490px">
                <div style="height:100%">
                    <div>
                        <span  class="ar-groupIndex">${itemValue.tableIndex }</span>
                    </div>
                    <div class="pull-right" style="height:100%;vertical-align: middle;">
                        <label class="qp-output-text">${itemValue.commonObjectAttributeName}</label>
                    </div>
                </div>
            </td>
            <td>
                <label class="qp-output-text">${itemValue.commonObjectAttributeCode}</label>
            </td>
            <td style="text-align: left;">${CL_EX_DATATYPE.get(itemValue.dataType.toString())}
                <c:if test="${itemValue.arrayFlg }">
                []
                </c:if>
            </td>
        </tr>
        <c:if test="${itemValue.externalObjectDefinition != null && itemValue.externalObjectDefinition.externalObjectAttributes != null}">
            <c:forEach items="${itemValue.externalObjectDefinition.externalObjectAttributes}" var="itemDetail" varStatus="status">
                <qp:externalViewRow itemValue="${itemDetail}"/>
            </c:forEach>
        </c:if>
	</c:when>
	<c:otherwise>
		<tr data-ar-rgroup="${itemValue.groupId}" class="ar-dataRow" data-ar-templateid="tbl_attribute_list_define-template" data-ar-rindex="${itemValue.itemSeqNo }" data-ar-rgroupindex="${itemValue.tableIndex }" >
			<td colspan="2" style="width: 490px">
				<div style="height:100%">
					<div>
						<span  class="ar-groupIndex">${itemValue.tableIndex }</span>
					</div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">
						<label class="qp-output-text">${itemValue.commonObjectAttributeName}</label>
					</div>
				</div>
			</td>
			<td>
				<label class="qp-output-text">${itemValue.commonObjectAttributeCode}</label>
			</td>
			<td style="text-align: left;">${CL_EX_DATATYPE.get(itemValue.dataType.toString())}
				<c:if test="${itemValue.arrayFlg }">
				[]
				</c:if>
			</td>
	    </tr>
	</c:otherwise>
</c:choose>