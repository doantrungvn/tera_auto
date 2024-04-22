<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
<jsp:include page="../sqldesign/rowTemplate.jsp"></jsp:include>
<form:hidden path="activeTab"/>
<c:set var="designForm" value="${autocompleteAdvancedDesignForm }" scope="request"></c:set>
<script type="text/javascript">
	var projectId = ${sessionScope.CURRENT_PROJECT.projectId};
</script>
<div id="qp-autocomplete">
<div class="panel panel-default qp-div-information">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text"><qp:message code="sc.autocomplete.0012"></qp:message></span>
	</div>
	<div class="panel-body">
		<form:hidden path="autocompleteForm.autocompleteId" />
		<form:hidden path="sqlDesignForm.sqlDesignId" />
		<form:hidden path="sqlDesignForm.isConversion" />
		<c:if test="${not empty designForm.autocompleteForm.updatedDate }">
			<form:hidden path="autocompleteForm.updatedDate" />
		</c:if>
		<table class="table table-bordered qp-table-form" id="autocompleteForm1">
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup> 
			<tr>
				<th><label for="autocompleteForm.autocompleteName"><qp:message code="sc.autocomplete.0005"></qp:message>&nbsp;<span class="qp-required-field"><qp:message code="sc.autocomplete.0043"></qp:message></span></label></th>
				<td><form:input path="autocompleteForm.autocompleteName" cssClass="form-control qp-input-text qp-convention-name" maxlength="200"/></td>
				<th><label for="autocompleteForm.autocompleteCode"><qp:message code="sc.autocomplete.0006"></qp:message>&nbsp;<span class="qp-required-field"><qp:message code="sc.autocomplete.0043"></qp:message></span></label></th>
				<td><form:input path="autocompleteForm.autocompleteCode" class="form-control qp-input-text qp-convention-code"  maxlength="50"/></td>
			</tr>
			<tr>
				<th><label for="autocompleteForm.moduleIdAutocomplete"><qp:message code="sc.autocomplete.0007"></qp:message></label></th>
				<td>
					<c:choose>
						<c:when test="${not empty designForm.autocompleteForm.autocompleteId && not empty designForm.autocompleteForm.moduleId}">
							<qp:formatText value="${designForm.autocompleteForm.moduleIdAutocomplete}" />
							<form:hidden path="autocompleteForm.moduleId"/>
						</c:when>
						<c:otherwise>
							<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete"
								name="autocompleteForm.moduleId"
								value="${designForm.autocompleteForm.moduleId }"
								displayValue="${designForm.autocompleteForm.moduleIdAutocomplete }"
								optionLabel="optionLabel"
								arg01="${sessionScope.CURRENT_PROJECT.projectId }"
								arg02="20" arg03="1">
							</qp:autocomplete>
						</c:otherwise>
					</c:choose>
				</td>
				<th>
					<label for="autocompleteForm.minLength">
						<qp:message code="sc.autocomplete.0062"></qp:message>
						<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title="<qp:message code="sc.autocomplete.0068"></qp:message>" data-html="true" data-toggle="tooltip" data-placement="top"></a>
					</label>
				</th>
				<td><form:input path="autocompleteForm.minLength" class="form-control qp-input-serial"  /></td>
			</tr>
			<tr>
				<th><label for="autocompleteForm.matchingTypes"><qp:message code="sc.autocomplete.0009"></qp:message></label>
					<a style="margin-left:5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.autocomplete.0069"></qp:message>' data-html="true" data-toggle="tooltip" data-placement="top"></a>
				</th>
				<td>
					<c:forEach var="item" items="${CL_MATCHING_TYPE}">
						<label>
							<form:radiobutton cssClass="qp-input-radio-margin qp-input-radio" path="autocompleteForm.matchingType" value="${item.key}" />
							<qp:message code="${CL_MATCHING_TYPE.get(item.key)}" />
						</label>
					</c:forEach>
				</td>
				<th><qp:message code="sc.sys.0055"></qp:message></th>
				<td><qp:message code="${CL_DESIGN_STATUS.get(designForm.sqlDesignForm.designStatus) }"></qp:message></td>
			</tr>
			<tr>
				<th><qp:message code='sc.sys.0028'></qp:message></th>
				<td colspan="3">
					<form:textarea path="autocompleteForm.remark" type="text" rows="3" cssClass="form-control qp-input-textarea" maxlength="2000" />
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
	<ul class="nav nav-tabs">
		<li><a href="#tab-input" data-toggle="tab" style="font: bold;"><qp:message code='sc.sqldesign.0003'></qp:message></a></li>
		<li><a href="#tab-output" data-toggle="tab" style="font: bold;"><qp:message code='sc.sqldesign.0004'></qp:message></a></li>
		<li class="active"><a href="#tab-sql-design" data-toggle="tab" style="font: bold;">SQL Design</a></li>
	</ul>
	<div class="tab-content">
		<div id="tab-input" class="tab-pane" style="height: auto;">
			<jsp:include page="../autocomplete/inputForm.jsp"></jsp:include>
		</div>
		<div id="tab-output" class="tab-pane" style="height: auto;">
			<jsp:include page="../autocomplete/advancedOutputForm.jsp"></jsp:include>
		</div>
		<div id="tab-sql-design" class="tab-pane active" style="height: auto;">
			<jsp:include page="../sqldesign/advancedSqlForm.jsp"></jsp:include>
		</div>
	</div>
</div>
</div>