<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
<c:set var="designForm" value="${viewDesignDesignForm }" scope="request"></c:set>
<script type="text/javascript">
	var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
</script>
<jsp:include page="../sqldesign/rowTemplate.jsp"></jsp:include>
<div id="qp-viewdesign">
	<div class="panel panel-default qp-div-information">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
			<span class="qp-heading-text"><qp:message code="sc.sqldesign.0021"></qp:message></span>
		</div>
		<div class="panel-body">
			<form:hidden path="sqlDesignForm.sqlDesignId" />
			<c:if test="${not empty designForm.sqlDesignForm.updatedDate }">
				<form:hidden path="sqlDesignForm.updatedDate" />
				<form:hidden path="sqlDesignForm.sqlPattern"/>
			</c:if>
			<table class="table table-bordered qp-table-form">
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<tr>
					<th><qp:message code='sc.viewdesign.0006' />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
					<td><form:input path="sqlDesignForm.sqlDesignName" class="form-control qp-input-text qp-convention-name"/></td>
					<th><qp:message code='sc.viewdesign.0007' />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
					<td><form:input path="sqlDesignForm.sqlDesignCode" class="form-control qp-input-text qp-convention-code"/></td>
				</tr>
				<tr>
					<th>
						<qp:message code='sc.sqldesign.0031' />
					</th>
					<td>
						${CL_SQL_TYPE_VIEWDESIGN.get('0') }
					</td>
					<th><qp:message code="sc.module.0007"></qp:message></th>
					<td>
						<c:choose>
							<c:when test="${not empty designForm.sqlDesignForm.sqlDesignId && not empty designForm.sqlDesignForm.moduleId}">
								<qp:formatText value="${designForm.sqlDesignForm.moduleIdAutocomplete}" />
								<form:hidden path="sqlDesignForm.moduleId"/>
							</c:when>
							<c:otherwise>
								<qp:autocomplete optionValue="optionValue" 
									selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
									name="sqlDesignForm.moduleId"
									value="${designForm.sqlDesignForm.moduleId }"
									displayValue="${designForm.sqlDesignForm.moduleIdAutocomplete }"
									optionLabel="optionLabel"
									arg01="${sessionScope.CURRENT_PROJECT.projectId }">
								</qp:autocomplete>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th><qp:message code="sc.sys.0055"></qp:message></th>
					<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus) }"></qp:message></td>
					<th><qp:message code='sc.sys.0028'></qp:message></th>
					<td><form:textarea path="sqlDesignForm.remark" type="text" rows="3" cssClass="form-control qp-input-textarea" maxlength="2000"/></td>
				</tr>
			</table>
		</div>
	</div>
	<div>
		<div>
			<jsp:include page="../sqldesign/generatedSqlExplanation.jsp"></jsp:include>
		</div>
		<div style="text-align: right; margin-top: 10px"><a class="viewDesign" href="javascript:void(0)" id="synchronizeLink">Synchronize</a></div>
		<div id="tab-sql-design-area">
			<div id="tab-sql-design-select">
				<jsp:include page="../sqldesign/fromForm.jsp"></jsp:include>
				<jsp:include page="../sqldesign/selectForm.jsp"></jsp:include>
				<jsp:include page="../sqldesign/whereForm.jsp"></jsp:include>
<%-- 					<jsp:include page="../sqldesign/groupByForm.jsp"></jsp:include> --%>
<%-- 					<jsp:include page="../sqldesign/havingForm.jsp"></jsp:include> --%>
				<jsp:include page="../sqldesign/orderByForm.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include page="../sqldesign/generateSQLScript.jsp"></jsp:include>
	</div>
</div>
<jsp:include page="../sqldesign/modalSynchronize.jsp"></jsp:include>