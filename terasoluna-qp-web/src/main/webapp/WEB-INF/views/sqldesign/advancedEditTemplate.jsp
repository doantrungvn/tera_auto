<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
<jsp:include page="../sqldesign/rowTemplate.jsp"></jsp:include>
<c:set var="designForm" value="${sqlDesignAdvancedDesignForm }" scope="request"></c:set>
<form:hidden path="activeTab"/>
<script type="text/javascript">
	var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
	var DB_TYPE_PROJECT =  ${sessionScope.CURRENT_PROJECT.dbType};
</script>
<div id="qp-sqldesign">
	<div class="panel panel-default qp-div-information">
		<div class="panel-heading">
			<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
			<span class="qp-heading-text"><qp:message code="sc.sqldesign.0021"></qp:message></span>
		</div>
		<div class="panel-body">
			<form:hidden path="sqlDesignForm.sqlDesignId" />
			<form:hidden path="sqlDesignForm.isConversion" />
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
						${CL_SQL_TYPE_SQLDESIGN.get('5') }
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
									onChangeEvent="$.qp.common.changeModuleEvent"
									displayValue="${designForm.sqlDesignForm.moduleIdAutocomplete }"
									optionLabel="optionLabel"
									arg01="${sessionScope.CURRENT_PROJECT.projectId }"
									arg03="1">
								</qp:autocomplete>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th><qp:message code="sc.sys.0055"></qp:message></th>
					<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus) }"></qp:message></td>
					<th><qp:message code='sc.sys.0028'></qp:message></th>
					<td><form:textarea path="sqlDesignForm.remark" cssClass="form-control qp-input-textarea"></form:textarea></td>
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
				<jsp:include page="../sqldesign/advancedOutputForm.jsp"></jsp:include>
			</div>
			<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
				<jsp:include page="../sqldesign/advancedSqlForm.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="fcomConfirmDialogSynchronize">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header qp-model-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title qp-model-header-title"><qp:message code="sc.sys.0038"/></h3>
			</div>
			<div class="modal-body">
			</div>
			<div class="modal-footer qp-model-footer">
				<button type="button" class="btn btn-primary" onclick="$.qp.advancedsql.synchronizeAdvanceSql(false)" tabindex="1"><qp:message code="sc.sys.0011"/></button>
				<button type="button" class="btn btn-default" data-dismiss="modal" tabindex="2"><qp:message code="sc.sys.0012"/></button>
			</div>
		</div>
	</div>
</div>