<!-- Dialog Navigator setting -->
<div id="dialog-navigator-setting" class="modal fade" style="display: none;">
	<script id="tbl-navigator-inputbean-template" type="text/x-jquery-tmpl">
		<tr>
			<td class="tableIndexNotAuto qp-bdesign-text-left">\${tableIndex}</td>
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
						arg01="ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
				</div>
			</td>
			<td class="btn-remove">
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})" title='<qp:message code="sc.sys.0014" />' ></a>
			</td>
		</tr>
	</script>
	<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
		<qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
			selectSqlId="" optionValue="optionValue" arg01="ou" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
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
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0132"/></span>
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
									<td colspan="3"><input type="text" class="form-control qp-input-text" style="width: 100%" name="label" maxlength="200"></td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0133"/></th>
									<td colspan="1" name="navigatorType">
										<label><input type="radio" name="navigatorToType" value="0" class="qp-input-radio qp-input-radio-margin" onchange="onChangeNavigationType(this)"/><qp:message code="sc.businesslogicdesign.0134"/>&nbsp;&nbsp;</label>
										<label><input type="radio" name="navigatorToType" value="1" class="qp-input-radio qp-input-radio-margin" onchange="onChangeNavigationType(this)"/><qp:message code="sc.businesslogicdesign.0135"/></label>
										<label><input type="radio" name="navigatorToType" value="2" class="qp-input-radio qp-input-radio-margin" onchange="onChangeNavigationType(this)"/><qp:message code="sc.sys.0058"/></label>
									</td>
									<th name="tranType"><qp:message code="sc.businesslogicdesign.0137"/></th>
									<td name="tranType">
										<label><input type="radio" name="transitionType" value="0" class="qp-input-radio qp-input-radio-margin" /><qp:message code="sc.businesslogicdesign.0138"/>&nbsp;&nbsp;</label>
										<label><input type="radio" name="transitionType" value="1" class="qp-input-radio qp-input-radio-margin"><qp:message code="sc.businesslogicdesign.0139"/></label>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0024"/></th>
									<td>
										<qp:autocomplete optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAllModuleByModuleNameAndProjectIdForAutocomplete" emptyLabel='sc.sys.0030' name="moduleId" 
											arg01="${sessionScope.CURRENT_PROJECT.projectId}" arg02 = "20" arg03 = "1" onChangeEvent="onchangeModuleOfNavigator" >
										</qp:autocomplete>
									</td>
									<th>
										<label><qp:message code="sc.businesslogicdesign.0136"/></label> &nbsp;
										<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label>
										<a name="registerNavigatorLink" style="cursor: pointer;">(<qp:message code="sc.sys.0005"/>)</a></th>
									<td>
										<qp:autocomplete name="navigatorToId" optionValue="optionValue" optionLabel="optionLabel" selectSqlId="getAutocompleteBusinessLogicForNavigatorBD" arg01="${f:h(sessionScope.CURRENT_PROJECT.projectId)}" arg02="20" arg03="${businessDesignForm.moduleId}" onChangeEvent="onchangeNavigatorOfNavigator"></qp:autocomplete>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064"/></th>
									<td colspan="3">
										<textarea rows="2" cols="1" style="width: 100%" name="remark" maxlength="2000"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
						<br />
						<table class="table table-bordered qp-table-list" id="tbl-navigator-inputbean">
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
									<th><qp:message code="sc.businesslogicdesign.0140"/></th>
									<th><qp:message code="sc.businesslogicdesign.0131"/></th>
									<th><qp:message code="sc.businesslogicdesign.0039"/></th>
									<th style="color: blue;">
										<qp:message code="sc.businesslogicdesign.0082"/> 
										<a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.blogiccomponent.0195" />' data-html="true" data-toggle="tooltip" data-placement="bottom"></a></th>
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
			<div class="modal-footer">	
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteDialog(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalNavigationSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
			</div>
			</div>
		</div>
	</div>
</div>