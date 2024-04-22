<table class="table table-bordered qp-table-list" max-rows="20" id="inputForm" data-ar-dataClass="ar-dataRow" data-ar-callback="$.qp.sqlbuilder.inputFormCallback">
	<colgroup>
		<col />
		<col width="40px"/>
		<col width="40px"/>
		<col width="32%"/>
		<col width="20%"/>
		<col width="20%"/>
		<col width="10%"/>
		<col />
	</colgroup>
	<thead>
		<tr>
			<th><qp:message code="sc.sys.0004"></qp:message></th>
			<th colspan="3"><qp:message code="sc.sqldesign.0023"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0024"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0058"></qp:message></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designForm.inputForm }" var="item" varStatus="status">
			<c:if test="${status.index lt 0}">
				<tr data-ar-rgroup="${item.groupId }" data-ar-rgroupindex="${f:h(item.groupIndex) }"  class="ar-dataRow">
					<td colspan="4" class="qp-none-padding">
						<div style="height:100%">
							<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
							<div class="pull-right" style="height:100%;vertical-align: middle;">	
								<form:input class="form-control qp-input-text" path="inputForm[${status.index}].sqlDesignInputName" />
							</div>
						</div>
					</td>
					<td>
						<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
						<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
						<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
						<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
						<form:input class="form-control qp-input-text" path="inputForm[${status.index}].sqlDesignInputCode" />
					</td>
					<td>
						<div class="input-group" style="width:100%">
							<form:select path="inputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
								<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
								<form:options items="${CL_SQL_DATATYPE }" />
							</form:select>
						</div>
					</td>
					
					<c:choose>
						<c:when test="${status.index eq 0}">
							<td>
								<qp:message code="sc.sqldesign.0056"></qp:message>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<qp:message code="sc.sqldesign.0057"></qp:message>
							</td>
						</c:otherwise>
					</c:choose>
				
					<td>
						<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
					</td>
				</tr>
			</c:if>
			<c:if test="${status.index ge 0}">
				<tr data-ar-rgroup="${item.groupId }" data-ar-rgroupindex="${f:h(item.groupIndex) }"  class="ar-dataRow">
					<td colspan="4" class="qp-none-padding">
						<div style="height:100%">
							<div><span class="ar-groupIndex">${item.groupIndex }</span></div>
							<div class="pull-right" style="height:100%;vertical-align: middle;">	
								<form:input class="form-control qp-input-text" path="inputForm[${status.index}].sqlDesignInputName" />
							</div>
						</div>
					</td>
					<td>
						<form:hidden path="inputForm[${status.index}].groupId" class="ar-groupId"/>
						<form:hidden path="inputForm[${status.index}].groupIndex" class="ar-groupIndex"/>
						<form:hidden path="inputForm[${status.index}].itemSeqNo" class="ar-rIndex"/>
						<form:hidden path="inputForm[${status.index}].sqlDesignInputId"/>
						<form:input class="form-control qp-input-text" path="inputForm[${status.index}].sqlDesignInputCode" />
					</td>
					<td>
						<div class="input-group" style="width:100%">
							<form:select path="inputForm[${status.index }].dataType" cssClass="form-control qp-input-select pull-left"  onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
								<form:option value=""><qp:message code="sc.sys.0030"></qp:message></form:option>
								<form:options items="${CL_SQL_DATATYPE }" />
							</form:select>
						</div>
					</td>
					<c:choose>
						<c:when test="${status.index eq 0}">
							<td class="autocomleteType">
								<qp:message code="sc.sqldesign.0056"></qp:message>
							</td>
						</c:when>
						<c:otherwise>
							<td class="autocomleteType">
								<qp:message code="sc.sqldesign.0057"></qp:message>
							</td>
						</c:otherwise>
					</c:choose>
					<td>
						<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
<div class="qp-div-action-table">
	<a type="button" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this,tableId:'inputForm',templateId:'inputForm-l0-template',templateData:{groupId:''}});"></a>
</div>