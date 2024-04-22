<!-- Dialog common setting -->
<div id="dialog-common-setting" class="modal fade" style="display: none;">
<script id="tbl-common-inputbean-template" type="text/x-jquery-tmpl">
	<tr>
		<td class="com-output-fixlength tableIndexNotAuto qp-bdesign-text-left">\${tableIndex}</td>
		<td>
			<input type="hidden" name="inputBeanId" value='\${inputBeanId}'></input>
			<input type="hidden" name="inputBeanName" value='\${inputBeanName}'></input>
			<label name="inputBeanName" class="qp-output-text">\${messageStringAutocomplete}</label>
		</td>
		<td><label name="inputBeanCode" class="qp-output-text">\${inputBeanCode}</label></td>
		<td>
			<input type="hidden" name="dataType" value='\${dataType}'>
			<input type="hidden" name="arrayFlg" value='\${arrayFlg}'>
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
<script id="tbl-common-outputbean-template" type="text/x-jquery-tmpl">
	<tr>
		<td class="com-output-fixlength tableIndexNotAuto qp-bdesign-text-left">\${tableIndex}</td>
		<td>
			<input type="hidden" name="outputBeanId"  value='\${outputBeanId}'></input>
			<label name="outputBeanName" class="qp-output-text">\${outputBeanName}</label>
		</td>
		<td><label name="outputBeanCode" class="qp-output-text">\${outputBeanCode}</label></td>
		<td>
			<input type="hidden" name="dataType" value='\${dataType}'>
			<input type="hidden" name="arrayFlg" value='\${arrayFlg}'>
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
				<br/>
				<div class="panel panel-default  qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0077"/></span>
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
								<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label" maxlength="200"></td>
							</tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0078"/><label class="qp-required-field"><qp:message code="sc.sys.0029" /></label></th>
								<td>
									<qp:autocomplete name = "businessLogicId" optionLabel="optionLabel" selectSqlId="getAutocompleteBlogicForCommonCom" optionValue="optionValue" arg02="20" arg01="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeBusinessLogicOfCommonSetting"></qp:autocomplete>
								</td>
								<th><qp:message code="sc.businesslogicdesign.0079"/></th>
								<td>
									<a title='<qp:message code="sc.blogiccomponent.0184"/>' href="${pageContext.request.contextPath}/businessdesign/modify?businessLogicId=" target="_blank" ><label name="businessLogicCode" class="qp-output-text qp-cursor"></label></a>
								</td>
							</tr>
							<tr>
								<th><qp:message code="sc.businesslogicdesign.0064"/></th>
								<td colspan="3"><textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea></td>
							</tr>
						</table>
						<br />
						<div id="tabsCommon">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tabsCommon-inputbean"data-toggle="tab"><qp:message code="sc.businesslogicdesign.0080"/></a></li>
								<li><a href="#tabsCommon-outputbean" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0081"/></a></li>
							</ul>

							<div class="tab-content">
								<div id="tabsCommon-inputbean" style="min-height: 200px;"
									class="tab-pane active">
									<table class="table table-bordered qp-table-list" id="tbl-common-inputbean">
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
								<div id="tabsCommon-outputbean" style="min-height: 200px;" class="tab-pane">
									<table class="table table-bordered qp-table-list" id="tbl-common-outputbean">
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
												<th class="bd-assign"><qp:message code="sc.blogiccomponent.0008"></qp:message></th>
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
			<br />
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalCommonSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
			</div>
		</div>
	</div>
</div>