<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/common/javascript/ar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sessionmanagement/javascript/sessionmanagement.js"></script>

	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.sessionmanagement"></qp:message></span></li>
         <li><span><qp:message code="sc.sessionmanagement.0006"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="sessionmanagementSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/sessionmanagement/search"><qp:message code="sc.sessionmanagement.0004"></qp:message></a>
		</qp:authorization>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<form:form method="post" action="${pageContext.request.contextPath}/sessionmanagement/register" modelAttribute="sessionManagementForm">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="qp-heading-text"><qp:message code="sc.sessionmanagement.0006"></qp:message></span>
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
                            <th><qp:message code="sc.sessionmanagement.0002" /><label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                            <td><form:input path="sessionManagementName" cssClass="form-control qp-input-text qp-convention-name" /></td>
                            <th><qp:message code="sc.sessionmanagement.0003" /><label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                            <td><form:input path="sessionManagementCode" cssClass="form-control qp-input-text qp-convention-code" /></td>
                        </tr>
                        <tr>
                            <th><qp:message code="sc.sessionmanagement.0008" /><label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
                            <td>
                            	<div class="input-group">
	                                <select name="dataType" class="form-control qp-input-select pull-left" onchange="dataTypeChange(this);">
	                                    <option value=""><qp:message code="sc.sys.0030"></qp:message></option>
	                                    <c:forEach var="itemValue" items="${CL_SM_DATATYPE}">
	                                        <c:if test='${itemValue.key == sessionManagementForm.dataType}'>
	                                            <option value="${itemValue.key}" selected="selected" >${itemValue.value}</option>
	                                        </c:if>
	                                        <c:if test='${itemValue.key != sessionManagementForm.dataType}'>
	                                            <option value="${itemValue.key}">${itemValue.value}</option>
	                                        </c:if>
	                                    </c:forEach>
	                                </select>
	                                
				                    <span class="input-group-addon">
				                        <label>
				                        	<form:checkbox aria-label="Array" path="arrayFlg"/>Array
				                        </label>
				                    </span>
			                    </div>
                            </td>
                            <th>
                                <div name="autocomplete" class="hidden">
                                <qp:message code="sc.sessionmanagement.0009" /><label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
                                </div>
                            </th>
                            <td>
                                <div name="autocomplete" class="hidden">
                                <qp:autocomplete name="objectId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="" value="${sessionManagementForm.objectId}" displayValue="${sessionManagementForm.objectIdAutocomplete}" arg01="${sessionScope.CURRENT_PROJECT.projectId}" mustMatch="true" maxlength="200"></qp:autocomplete>
                                </div>
                            </td>
                        </tr>
					</table>
				</div>
			</div>
			<div class="qp-div-action">
				<qp:authorization permission="sessionmanagementRegister">
					<button type="button" onclick="" class="btn qp-button qp-dialog-confirm">
						<qp:message code="sc.sys.0031" />
					</button>
				</qp:authorization>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>