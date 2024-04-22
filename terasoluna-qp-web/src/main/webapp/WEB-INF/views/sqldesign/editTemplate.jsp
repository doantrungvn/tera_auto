<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
<c:set var="designForm" value="${sqlDesignDesignForm }" scope="request"></c:set>
<form:hidden path="activeTab"/>
<script type="text/javascript">
	var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
</script>
<jsp:include page="rowTemplate.jsp"></jsp:include>
<div id="qp-sqldesign">
	<div class="panel panel-default qp-div-information">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
			<span class="qp-heading-text"><qp:message code="sc.sqldesign.0021"></qp:message></span>
		</div>
		<div class="panel-body">
			<form:hidden path="sqlDesignForm.sqlDesignId" />
			<c:if test="${not empty designForm.sqlDesignForm.updatedDate }">
				<form:hidden path="sqlDesignForm.updatedDate" />
			</c:if>
			<table class="table table-bordered qp-table-form">
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<tr>
					<th><qp:message code="sc.sqldesign.0010"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
					<td><form:input path="sqlDesignForm.sqlDesignName" class="form-control qp-input-text qp-convention-name"/></td>
					<th><qp:message code="sc.sqldesign.0011"></qp:message>&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
					<td><form:input path="sqlDesignForm.sqlDesignCode" class="form-control qp-input-text qp-convention-code"/></td>
				</tr>
				<tr>
					<th>
						<qp:message code="sc.sqldesign.0031"></qp:message>
					</th>
					<td>
						${CL_SQL_TYPE_SQLDESIGN.get('4') }
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
									value="${designForm.sqlDesignForm.moduleId}"
									displayValue="${designForm.sqlDesignForm.moduleIdAutocomplete }"
									optionLabel="optionLabel"
									arg01="${sessionScope.CURRENT_PROJECT.projectId }"
									arg03="1"
									onChangeEvent="$.qp.common.changeModuleEvent">
								</qp:autocomplete>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th><qp:message code="sc.sys.0055"></qp:message></th>
					<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus) }"></qp:message></td>
					<th><qp:message code="sc.sqldesign.0020"></qp:message></th>
					<td>
						<form:select path="sqlDesignForm.sqlPattern" cssClass="form-control qp-input-select pull-left">
							<form:options items="${CL_SQL_SQLPATTERN }"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<th><qp:message code='sc.sys.0028'></qp:message></th>
					<td><form:textarea path="sqlDesignForm.remark" cssClass="form-control qp-input-textarea"></form:textarea></td>
					<th></th>
					<td></td>
				</tr>
			</table>
		</div>
	</div>
	<div>
		<a href="javascript:void(0)" id="synchronizeLink" style="float: right;margin-top: 10px">Synchronize</a>
		<ul class="nav nav-tabs">
			<li><a href="#tab-input" data-toggle="tab" style="font: bold;"><spring:message code="sc.sqldesign.0003"></spring:message></a></li>
			<li><a href="#tab-output" data-toggle="tab" style="font: bold;"><spring:message code="sc.sqldesign.0004"></spring:message></a></li>
			<li class="active"><a href="#tab-sql-design" data-toggle="tab" style="font: bold;"><spring:message code="sc.sqldesign.0009"></spring:message></a></li>
		</ul>
		<div class="tab-content">
			<div id="tab-input" class="tab-pane" style="height: auto;">
				<jsp:include page="../sqldesign/inputForm.jsp"></jsp:include>
			</div>
			<div id="tab-output" class="tab-pane" style="height: auto;">
				<jsp:include page="../sqldesign/outputForm.jsp"></jsp:include>
			</div>
			<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
				<div>
					<jsp:include page="../sqldesign/generatedSqlExplanation.jsp"></jsp:include>
				</div>
				<div id="tab-sql-design-area">
					<div id="tab-sql-design-select">
						<jsp:include page="../sqldesign/fromForm.jsp"></jsp:include>
						<jsp:include page="../sqldesign/selectForm.jsp"></jsp:include>
						<jsp:include page="../sqldesign/whereForm.jsp"></jsp:include>
<%-- 					<jsp:include page="../sqldesign/groupByForm.jsp"></jsp:include> --%>
<%-- 					<jsp:include page="../sqldesign/havingForm.jsp"></jsp:include> --%>
						<jsp:include page="../sqldesign/orderByForm.jsp"></jsp:include>
					</div>
					<div id="tab-sql-design-insert-update">
						<jsp:include page="../sqldesign/intoForm.jsp"></jsp:include>
						<jsp:include page="../sqldesign/valueForm.jsp"></jsp:include>
						<jsp:include page="../sqldesign/whereForm.jsp"></jsp:include>
					</div>
					<div id="tab-sql-design-delete"> 
						<jsp:include page="../sqldesign/intoForm.jsp"></jsp:include>
						<jsp:include page="../sqldesign/whereForm.jsp"></jsp:include>
					</div>
				</div>
				
				<jsp:include page="../sqldesign/generateSQLScript.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../sqldesign/modalSynchronize.jsp"></jsp:include>