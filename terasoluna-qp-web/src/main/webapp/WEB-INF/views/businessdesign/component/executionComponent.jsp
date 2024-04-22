<!-- Dialog sql setting -->
<div id="dialog-execution-setting" class="modal fade" style="display: none;">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/sqldesign/javascript/sqlbuilder.js"></script>
<script type="text/javascript">
	$.qp.sqlbuilder.initFromForm();
</script>
<script type="text/javascript">
	var CL_SQL_SQLPATTERN = new Array();
	<c:forEach items="${CL_SQL_SQLPATTERN}" var="item">
		CL_SQL_SQLPATTERN['${item.key}'] = '${item.value}';
	</c:forEach>
	
	var CL_SQL_RETURNTYPE = new Array();
	<c:forEach items="${CL_SQL_RETURNTYPE}" var="item">
	CL_SQL_RETURNTYPE['${item.key}'] = '${item.value}';
</c:forEach>
var DB_TYPE_PROJECT =  ${sessionScope.CURRENT_PROJECT.dbType};
</script>
<script id="tbl-execution-inputbean-template" type="text/x-jquery-tmpl">
    <tr>
        <td class='tableIndexNotAuto qp-bdesign-text-left'>\${groupIndex}</td>
        <td>
            <input type='hidden' name='sqlDesignInputId' value='\${sqlDesignInputId}'></input>
            <label name='sqlDesignInputName' class='qp-output-text'>\${sqlDesignInputName}</label>
        </td>
        <td><label name='sqlDesignInputCode' class='qp-output-text'>\${sqlDesignInputCode}</label></td>
        <td>
            <input type='hidden' name='dataType' value='\${dataType}'>
            <input type='hidden' name='arrayFlg' value='\${arrayFlag}'>
            <label name='dataType' class='qp-output-text'>\${dataTypeStr}</label>
        </td>
        <td>
			<div class="bd-content" style="width:100%" pattern="0" name="parameter">
				<qp:autocomplete name = "parameterId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
					arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
			</div>
		</td>
        <td class="btn-remove">
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>
<script id="tbl-execution-outputbean-template" type="text/x-jquery-tmpl">
    <tr>
        <td class="tableIndexNotAuto qp-bdesign-text-left">\${groupIndex}</td>
        <td>
            <input type="hidden" name="sqlDesignOutputId" value='\${sqlDesignOutputId}'></input>
            <label name="sqlDesignOutputName" class="qp-output-text">\${sqlDesignOutputName}</label>
        </td>
        <td><label name="sqlDesignOutputCode" class="qp-output-text">\${sqlDesignOutputCode}</label></td>
        <td>
            <input type="hidden" name="dataType" value='\${dataType}'>
            <input type="hidden" name="arrayFlg" value='\${arrayFlag}'>
            <label name="dataType" class="qp-output-text">\${dataTypeStr}</label>
        </td>
        <td>
			<div class="bd-content" style="width:100%" pattern="0" name="target">
				<qp:autocomplete name = "targetId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
					arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
			</div>
		</td>
        <td class="btn-remove">
            <a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
        </td>
    </tr>
</script>
<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
	<qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
		selectSqlId="" optionValue="optionValue" arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD">
	</qp:autocomplete>
</script>
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0091"/></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063"/></th>
									<td><input type="text" class="form-control qp-input-text" name="label"></td>
									<th><qp:message code="sc.businesslogicdesign.0024"/></th>
									<td>
										<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" emptyLabel='sc.sys.0030' name="moduleId" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02 = "20" arg03 = "1" onChangeEvent="onchangeModuleOfExecution" >
										</qp:autocomplete>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0092"/> &nbsp<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
									<td>
										<qp:autocomplete name ="sqlDesignId" optionLabel="optionLabel" 
											selectSqlId="getAutocompleteExecutionForExecutionCom" optionValue="optionValue" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" onChangeEvent="onchangeSQLOfExecution"/>
									</td>
									<th><qp:message code="sc.businesslogicdesign.0093"/></th>
									<td>
										<a href="${pageContext.request.contextPath}/sqldesign/modify?sqlDesignForm.sqlDesignId=" target="_blank" class="qp-cursor" title='<qp:message code="sc.blogiccomponent.0179" />'>
										<label name="sqlDesignCode" class="qp-output-text" style="cursor: pointer;"></label></a>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0163"/></th>
									<td colspan="3"><label name="sqlPattern" class="qp-output-text"></label></td>
<%-- 									<th class="hide"><qp:message code="sc.businesslogicdesign.0094"/></th> --%>
<!-- 									<td class="hide"><input type="checkbox" name="concurrencyFlg"/></td> -->
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"/></th>
	                                <td colspan="3">
	                                    <textarea rows="2" name="remark" class="form-control"></textarea>
	                                </td>
								</tr>
							</tbody>
						</table>
						<br/>
						<div class="qp-div-action">
							<a onclick="openNewTab(0);" style="cursor: pointer;"><qp:message code="sc.businesslogicdesign.0095"/></a>
							&nbsp;
							<a onclick="openNewTab(1);" style="cursor: pointer;"><qp:message code="sc.businesslogicdesign.0096"/></a>
							<script type="text/javascript">
								function openNewTab(type){
									var url;
									if(type == 0){
										url = CONTEXT_PATH+"/sqldesign/register";
									} else {
										url = CONTEXT_PATH+"/sqldesign/registerAdvanced";
									}
									var win = window.open(url, '_blank');
								}
							</script>
						</div>
						<br />
						<div id="tabsCommon">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tabsExecution-1" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0097"/></a></li>
								<li><a href="#tabsExecution-2" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0098"/></a></li>
								<li><a href="#tabsExecution-3" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0099"/></a></li>
							</ul>
							<div class="tab-content">
								<div id="tabsExecution-1" style="min-height: 250px;text-align:left;" class="tab-pane active">
								</div>
								<div id="tabsExecution-2" style="min-height: 250px;" class="tab-pane">
									<table class="table table-bordered qp-table-list" id="tbl-execution-inputbean">
										<colgroup>
											<col />
											<col width="30%" />
											<col width="15%" />
											<col />
											<col width="35%" />
											<col />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.businesslogicdesign.0036"/></th>
												<th><qp:message code="sc.businesslogicdesign.0037"/></th>
												<th><qp:message code="sc.businesslogicdesign.0038"/></th>
												<th><qp:message code="sc.businesslogicdesign.0039"/></th>
												<th class="bd-pass-parameter"><qp:message code="sc.businesslogicdesign.0082"/>&nbsp;</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
								<div id="tabsExecution-3" style="min-height: 250px;" class="tab-pane">
									<table style="width:30%" class="table table-borderless">
										<colgroup>
											<col width="30%" />
											<col  />
										</colgroup>
										<tbody>
											<tr>
												<td><qp:message code='sc.sqldesign.0022'></qp:message></td>
												<td>
													<label name="returnType" class="form-control qp-form-control-label"></label>
												</td>
											</tr>
										</tbody>
									</table>
									<table class="table table-bordered qp-table-list" id="tbl-execution-outputbean">
										<colgroup>
											<col />
											<col width="30%" />
											<col width="15%" />
											<col />
											<col width="35%" />
											<col />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.businesslogicdesign.0036"/></th>
												<th><qp:message code="sc.businesslogicdesign.0037"/></th>
												<th><qp:message code="sc.businesslogicdesign.0038"/></th>
												<th><qp:message code="sc.businesslogicdesign.0039"/></th>
												<th class="bd-assign"><qp:message code="sc.blogiccomponent.0008"/></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<br/>
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalExecutionSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Begin include sql builder setting -->
<jsp:include page="sqlBuilder.jsp" />
<!-- End include sql builder setting -->