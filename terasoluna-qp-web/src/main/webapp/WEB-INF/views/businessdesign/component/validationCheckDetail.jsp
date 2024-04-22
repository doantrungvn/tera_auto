<!-- Dialog Validation Check setting -->
<div id="dialog-validationcheck-detail-setting" class="modal fade" style="display: none;">
<script id="item-validationcheck-parameter-template" type="text/template">
<div class="input-group pull-left" style="width:200px" parameterType="\${parameterType}" itemSequenceNo="\${itemSequenceNo}" name="divMessageParameter">
	<input type="text" name="parameterCodeAutocomplete" class="form-control qp-input-autocomplete " 
		value="" optionvalue="optionValue" optionlabel="optionLabel" selectsqlid="getAutocompleteForFeedbackLabel" emptylabel="" onselectevent="" onremoveevent="" mustmatch="true" maxlength="" minlength="" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" 
		 disabled = "true" onchangeevent="onchangeMessageOfValidationCheck">
	<input type="hidden" name="parameterCode" value="">
</div>
</script>
<script id="item-validationcheck-parameter-level-template" type="text/template">
	<span class="qp-bdesign-div-text pull-left bg-primary">()</span>
</script>

<script id="item-validationcheck-parameter-timepicker-template" type="text/template">
<div class="input-group pull-left" style="width:200px" parameterType="\${parameterType}" itemSequenceNo="\${itemSequenceNo}" name="divMessageParameter">
	<input type="text" name="parameterCodeAutocomplete" class="form-control qp-input-autocomplete " 
		value="" optionvalue="optionValue" optionlabel="optionLabel" selectsqlid="getAutocompleteForFeedbackLabel" emptylabel="" onselectevent="" onremoveevent="" mustmatch="true" maxlength="" minlength="" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" 
		 disabled = "true">
	<input type="hidden" name="parameterCode" value="">
	<input type="hidden" class="qp-input-timepicker" name="patternFormat" value="">
</div>
</script>
<script id="item-validationcheck-parameter-datepicker-template" type="text/template">
<div class="input-group pull-left" style="width:200px" parameterType="\${parameterType}" itemSequenceNo="\${itemSequenceNo}" name="divMessageParameter">
	<input type="text" name="parameterCodeAutocomplete" class="form-control qp-input-autocomplete " 
		value="" optionvalue="optionValue" optionlabel="optionLabel" selectsqlid="getAutocompleteForFeedbackLabel" emptylabel="" onselectevent="" onremoveevent="" mustmatch="true" maxlength="" minlength="" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" 
		 disabled = "true">
	<input type="hidden" name="parameterCode" value="">
	<input type="hidden" class="qp-input-datepicker" name="patternFormat" value="">
</div>
</script>
<script id="item-validationcheck-parameter-datetimepicker-template" type="text/template">
<div class="input-group pull-left" style="width:200px" parameterType="\${parameterType}" itemSequenceNo="\${itemSequenceNo}" name="divMessageParameter">
	<input type="text" name="parameterCodeAutocomplete" class="form-control qp-input-autocomplete " 
		value="" optionvalue="optionValue" optionlabel="optionLabel" selectsqlid="getAutocompleteForFeedbackLabel" emptylabel="" onselectevent="" onremoveevent="" mustmatch="true" maxlength="" minlength="" arg01="20" arg02="${sessionScope.CURRENT_PROJECT.projectId}" 
		 disabled = "true">
	<input type="hidden" name="parameterCode" value="">
	<input type="hidden" class="qp-input-datetimepicker" name="patternFormat" value="">
</div>
</script>
<script id="item-validationcheck-parameter-textbox-template" type="text/template">
<div class="input-group pull-left" style="width:100px" parameterType="\${parameterType}" itemSequenceNo="\${itemSequenceNo}"  name="divMessageParameter" >
	<input type="text" name="parameterCode" class="form-control qp-input-text" disabled = "true"/>
</div>
</script>
<script id="item-validationcheck-parameter-datetime-template" type="text/template">
<div class="input-group pull-left" style="width:100px" parameterType="\${parameterType}" itemSequenceNo="\${itemSequenceNo}"  name="divMessageParameter" >
	<input type="text" name="parameterCode" class="form-control qp-input-text" disabled = "true" style="width: 130px;"/>
	<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
</div>
</script>
<script id="tbl-validationcheck-detail-template" type="text/template">
	<tr validationType="">
		<td class="qp-output-fixlength tableIndex">1</td>
		<td class="validateName"></td>
		<td class="validateType"></td>
		<td class="level"></td>
		<td  class=""><div name="checkContent"></div></td>
		<td style="text-align: center;"><input type="checkbox" class="checkbox" style="width: 100%;" name="validateFlg" onchange="onchangeValidFlagOfValidationDetailSet(this)"/></td>
	</tr>
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
						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0149"/></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive" id="validationList">
			                <table class="table table-bordered qp-table-list" id="tbl-validationcheck-detail">
			                    <colgroup>
			                        <col/>
			                        <col width = "110px"/>
			                        <col width = "90px"/>
			                        <col width = "90px"/>
			                        <col width = "60%" />
			                        <col width = "80px" />
			                    </colgroup>
			                    <thead>
			                        <tr>
			                            <th><qp:message code="sc.businesslogicdesign.0036"/></th>
			                            <th><qp:message code="sc.businesslogicdesign.0242"/></th>
			                            <th><qp:message code="sc.businesslogicdesign.0243"/></th>
			                            <th><qp:message code="sc.businesslogicdesign.0129" /></th>
			                            <th><qp:message code="sc.businesslogicdesign.0150"/></th>
			                            <th><qp:message code="sc.businesslogicdesign.0151"/></th>
			                        </tr>
			                    </thead>
			                    <tbody>
			                    </tbody>
			                </table>
			                <div class="qp-add-left" style="display: none">
		                    	<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
		                   	</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="qp-div-action">
					<button type="button" class="btn qp-button-client " onclick="deleteModalValidationCheckDetailSetting(this)"><qp:message code="sc.sys.0008"></qp:message></button>
        			<button type="button" class="btn qp-button-client " onclick="saveModalValidationCheckDetailSetting(this)"><qp:message code="sc.sys.0031"></qp:message></button>
				</div>
            </div>  
		</div>
	</div>
</div>
<!-- Dialog Validation list setting -->