<!-- Dialog message parameter list setting -->
<div id="dialog-messageparameter-setting" class="modal fade" style="display: none;">
	<script id="messageParameter-template" type="text/x-jquery-tmpl">
    <tr name='trContent'>
        <td class="com-output-fixlength tableIndex">1</td>
        <td><label name="orderIndex"></label></td>
        <td>
            <c:forEach var="item" items="${CL_MESSAGE_PARAMETER_TYPE}">
                <label><input type="radio" name="parameterType" value="${item.key}" class="qp-input-radio qp-input-radio-margin" onchange="onChangeParameterTypeOfMessageParameter(this)"><qp:message code="${item.value}" /></label>
                <br/>
            </c:forEach>
        </td>
        <td type="0" style="display:none">
			<qp:autocomplete name="parameterCode" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackLabel" optionValue="optionValue" 
				arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeParameterOfMessageParSet" mustMatch="false"/>
		</td>
        <td type="1" style="display:none"><input type="text" class="form-control qp-input-text" name="parameterValue"></td>
        <td type="2" style="display:none">
             <div class="input-group bd-content" style="width:100%" pattern="0" name="parameter" id="parameter">
                 <qp:autocomplete name = "parameterCode" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                     arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
             </div>
        </td>
        <td><input type="hidden" name="messageLevel"/><span name="messageLevel"></span></td>
    </tr>
</script>
	<script id="parameter-content-message-design" type="text/x-jquery-tmpl">
    <td type="0" style="display:none">
		<qp:autocomplete name="parameterCode" optionLabel="optionLabel" selectSqlId="getAutocompleteForFeedbackLabel" optionValue="optionValue" 
			arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" onChangeEvent="onchangeParameterOfMessageParSet" mustMatch="false"/>
	</td>
</script>
	<script id="parameter-content-value" type="text/x-jquery-tmpl">
    <td type="1" style="display:none">
		<input type="text" class="form-control qp-input-text" name="parameterValue" maxlength="200">
	</td>
</script>
	<script id="parameter-content-variable" type="text/x-jquery-tmpl">
    <td type="2" style="display:none">
         <div class="input-group bd-content" style="width:100%" pattern="0" name="parameter" id="parameter">
             <qp:autocomplete name = "parameterCode" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
                 arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>
         </div>
    </td>
</script>
	<script id="div-parameter-index-template" type="text/x-jquery-tmpl">
    <qp:autocomplete name="parameterIndexId" cssInput="qp-formula-component-index" mustMatch="false" optionLabel="optionLabel" 
        selectSqlId="" optionValue="optionValue" arg01="in,ob" arg02="true" sourceType="local" sourceCallback="getDataSourceOfIndexBD" onChangeEvent="setTypeOfAssignIndexBD"></qp:autocomplete>
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
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0127" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered qp-table-form">
								<colgroup>
									<col width="25%" />
									<col width="75%" />
								</colgroup>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0072" /></th>
									<td><label name="messageString"></label></td>
								</tr>
							</table>
						</div>
						<br />
						<div class="table-responsive">
							<table class="table table-bordered qp-table-list" id="messageParameter" data-ar-callback="onAddNewMessageOfMessageParameterSet">
								<colgroup>
									<col />
									<col width="100px" />
									<col width="20%" />
									<col width="35%" />
									<col width="30%" />
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.businesslogicdesign.0036" /></th>
										<th><qp:message code="sc.blogiccomponent.0169" /></th>
										<th><qp:message code="sc.businesslogicdesign.0116" /></th>
										<th><qp:message code="sc.businesslogicdesign.0128" /></th>
										<th><qp:message code="sc.businesslogicdesign.0129" /></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							<div class="qp-add-left" style="display: none;">
								<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLinkEx(this,'messageParameter','messageParameter-template');" style="margin-top: 3px;" href="javascript:void(0)"></a>
							</div>
						</div>
					</div>
				</div>
				<br />
			</div>
			<div class="modal-footer">
				<br />
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="saveModalMessageParameterSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>