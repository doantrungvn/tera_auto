<!-- Dialog decision setting -->
<div id="dialog-decision-setting" class="modal fade" style="display: none;">
    <!-- Adding HungHX -->
	<script type="text/javascript">
     var CL_QP_OPERATORTYPE = {};
     <c:forEach items="${CL_QP_OPERATORTYPE}" var="item">
         CL_QP_OPERATORTYPE['${item.key}'] = "<qp:message code='${item.value}'/>";
     </c:forEach>
     var CL_SUPPORT_OPTION_VALUE_FLAG = {};
     <c:forEach items="${CL_SUPPORT_OPTION_VALUE_FLAG}" var="item">
         CL_SUPPORT_OPTION_VALUE_FLAG['${item.key}'] = "<qp:message code='${item.value}'/>";
     </c:forEach>
    </script>
	<script id="tbl-decision-inputbean-template" type="text/x-jquery-tmpl">
    	<tr>
			<td class="com-output-fixlength tableIndexNotAuto">\${tableIndex}</td>
			<td>
				<input type="hidden" name="decisionInputBeanId" value='\${decisionInputBeanId}'></input>
				<input type="hidden" name="decisionInputBeanName" value='\${decisionInputBeanName}'></input>
				<label name="decisionInputBeanName" class="qp-output-text">\${decisionInputBeanName}</label>
			</td>
			<td><label name="decisionInputBeanCode" class="qp-output-text">\${decisionInputBeanCode}</label></td>
			<td>
				<input type="hidden" name="dataType" value='\${dataType}'>
				<label name="dataType" class="qp-output-text">\${dataTypeStr}</label>
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
	<script id="tbl-decision-outputbean-template" type="text/x-jquery-tmpl">
		<tr>
			<td class="com-output-fixlength tableIndexNotAuto">\${tableIndex}</td>
			<td>
				<input type="hidden" name="decisionOutputBeanId" value='\${decisionOutputBeanId}'></input>
				<input type="hidden" name="decisionOutputBeanName" value='\${decisionOutputBeanName}'></input>
				<label name="decisionOutputBeanName" class="qp-output-text">\${decisionOutputBeanName}</label>
			</td>
			<td><label name="decisionOutputBeanCode" class="qp-output-text">\${decisionOutputBeanCode}</label></td>
			<td>
				<input type="hidden" name="dataType" value='\${dataType}'>
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
			selectSqlId="" optionValue="optionValue" arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
	</script>
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header qp-model-header" style="border-bottom: 0px;">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<br />
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0085"/></span>
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
								<th><qp:message code="sc.businesslogicdesign.0063"/></th>
								<td><input type="text" class="form-control qp-input-text" name="label" maxlength="200"></td>
								<th><qp:message code="sc.businesslogicdesign.0024"/></th>
								<td>
									<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" emptyLabel='sc.sys.0030' name="moduleId" 
										arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02 = "20" arg03 = "1" onChangeEvent="onchangeModuleOfDecision" >
									</qp:autocomplete>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0086"/> &nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
									<qp:autocomplete name = "decisionTableId" optionLabel="optionLabel" selectSqlId="getAutocompleteDecisionForDecisionCom" optionValue="optionValue" arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02="20" onChangeEvent="onchangeDecisionOfDecision"></qp:autocomplete>
								</td>
								<th><qp:message code="sc.businesslogicdesign.0087"/></th>
								<td>
									<a title='<qp:message code="sc.blogiccomponent.0182" />' href="${pageContext.request.contextPath}/decisiontable/modify?decisionTbId=" target="_blank" class="qp-cursor"><label name="decisionTableCode" class="qp-output-text qp-cursor"></label></a>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0064"/></th>
								<td colspan="3">
									<textarea rows="2"style="width: 100%" name="remark" maxlength="2000"></textarea>
								</td>
							</tr>
						</table>
						<br />
						<div id="tabsDecision">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tabsDecision-1" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0088"/></a></li>
								<li><a href="#tabsDecision-2" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0089"/></a></li>
								<li><a href="#tabsDecision-3" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0090"/></a></li>
							</ul>
							<div class="tab-content" style="min-height: 400px;">
								                        
		                        <div id="tabsDecision-1" class="tab-pane active" style="height: auto;">
	                            <div class="panel-body">
	                                <table class="table table-bordered qp-table-list" id="tbl_logic_design"></table>
	                                <div style="text-align: center;"><qp:message code="sc.blogiccomponent.0183"/></div>
	                            </div>
			                    </div>
								
								<div id="tabsDecision-2" class="tab-pane">
									<table class="table table-bordered qp-table-list" id="tbl-decision-inputbean">
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
                                                <th class="bd-pass-parameter"><qp:message code="sc.businesslogicdesign.0082"/></th>
                                                <th></th>
											</tr>
										</thead>
                                        <tbody>
                                        </tbody>
									</table>
									<div class="qp-add-left" style="display: none;">
                                        <a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                    </div>
								</div>
								<div id="tabsDecision-3" class="tab-pane">
									<table class="table table-bordered qp-table-list" id="tbl-decision-outputbean">
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
									<div class="qp-add-left" style="display: none;">
                                        <a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
                                    </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)">
						<qp:message code="sc.sys.0008"></qp:message>
					</button>
					<button type="button" class="btn qp-button-client " onclick="saveModalDecisionSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>