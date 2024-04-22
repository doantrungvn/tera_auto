<!-- Dialog advance setting -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/media/js/codemirror/css/codemirror.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/media/js/codemirror/css/show-hint.css" />
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/codemirror.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/show-hint.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/matchbrackets.js"></script>
<script src="${pageContext.request.contextPath}/resources/media/js/codemirror/js/clike.js"></script>
<div id="dialog-advance-setting" class="modal fade" style="display: none;">
<script id="tbl-advance-inputbean-template" type="text/x-jquery-tmpl">
<tr>
	<td class="qp-output-fixlength tableIndex">\${index}</td>
	<td>
		<input type="hidden" name="advanceInputValueId" value=''/>
		<input type="text" class="form-control qp-input-text qp-convention-name-row" name="inputBeanName" value='' maxlength='200'/>
	</td>
	<td><input type="text" class="form-control qp-input-text qp-convention-code-row" name="inputBeanCode" value='' maxlength='50'/></td>
	<td>
		<div class="input-group">
			<select class="form-control qp-input-select" name="dataType">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
				<c:forEach var="item" items="${CL_BD_DATATYPE_NOT_OBJECT_ENTITY}">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
			<span class="input-group-addon">
				<label><input type="checkbox" aria-label="Array" name="arrayFlg">Array</label>
			</span>
		</div>
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
<script id="tbl-advance-outputbean-template" type="text/x-jquery-tmpl">
<tr>
	<td class="qp-output-fixlength tableIndex">\${index}</td>
	<td>
		<input type="hidden" name="advanceOutputValueId" value=''/>
		<input type="text" class="form-control qp-input-text qp-convention-name-row" name="outputBeanName" value='' maxlength='200'/>
	</td>
	<td><input type="text" class="form-control qp-input-text qp-convention-code-row" name="outputBeanCode" value='' maxlength='50'/></td>
	<td>
		<div class="input-group">
			<select class="form-control qp-input-select" name="dataType">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
				<c:forEach var="item" items="${CL_BD_DATATYPE_NOT_OBJECT_ENTITY}">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
			<span class="input-group-addon">
				<label><input type="checkbox" aria-label="Array" name="arrayFlg">Array</label>
			</span>
		</div>
	</td>
	<td>
		<div class="bd-content" style="width:100%" pattern="0" name="target">
			<qp:autocomplete name = "targetId" optionLabel="optionLabel" selectSqlId="" optionValue="optionValue" 
				arg01="in,ob,ou" arg02="true" sourceType="local" sourceCallback="getDataSourceAutocompleteBD" onChangeEvent="onchangeParameterOfBD"></qp:autocomplete>			</div>
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
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.blogiccomponent.0023" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tbody>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0063" /></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" name="label" maxlength="200"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0165" />&nbsp;<label class="qp-required-field"><qp:message code="sc.sys.0029" /></label><a style="margin-left: 5px" class="glyphicon glyphicon-info-sign qp-link-toggle" title='<qp:message code="sc.businesslogicdesign.0264"/>' data-toggle="tooltip" data-placement="top"></a></th>
									<td colspan="3"><input type="text" class="form-control qp-input-text" name="methodName" onchange="buildSampleContentOfAdvanceComp(this)" maxlength="50"/></td>
								</tr>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0064" /></th>
									<td colspan="3"><textarea rows="2" cols="1" class="form-control qp-input-text" name="remark" maxlength="2000"></textarea></td>
								</tr>
							</tbody>
						</table>
						<br />

						<div id="tabsAdvance">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tabsAdvance-editor" data-toggle="tab"><qp:message code="sc.blogiccomponent.0024" /></a></li>
								<li><a href="#tabsAdvance-inputbean" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0089" /></a></li>
								<li><a href="#tabsAdvance-outputbean" data-toggle="tab"><qp:message code="sc.businesslogicdesign.0090" /></a></li>
							</ul>
							<div class="tab-content" style="min-height: 400px;">
								<div id="tabsAdvance-editor" class="tab-pane active" style="height: auto;">
									<label id="firstLine"></label>
                                    <textarea id="editor-java-code" maxlength="4000">
									</textarea>
                                    <label id="lastLine">}</label>
									<p class="text-warning" style="margin-top: 10px"><qp:message code="sc.blogiccomponent.0025" /></p>
								</div>

								<div id="tabsAdvance-inputbean" class="tab-pane">
									<table class="table table-bordered qp-table-list" id="tbl-advance-inputbean" data-ar-dataClass="ar-dataRow" >
										<colgroup>
											<col />
											<col width="25%" />
											<col width="12%" />
											<col />
											<col width="35%" />
											<col />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.businesslogicdesign.0036" /></th>
												<th><qp:message code="sc.businesslogicdesign.0037" /></th>
												<th><qp:message code="sc.businesslogicdesign.0038" /></th>
												<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												<th class="bd-pass-parameter"><qp:message code="sc.businesslogicdesign.0082" /></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
									<div class="qp-add-left">
										<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</div>
								</div>
								<div id="tabsAdvance-outputbean" class="tab-pane">
									<table class="table table-bordered qp-table-list" id="tbl-advance-outputbean" data-ar-dataClass="ar-dataRow">
										<colgroup>
											<col />
											<col width="25%" />
											<col width="12%" />
											<col />
											<col width="35%" />
											<col />
										</colgroup>
										<thead>
											<tr>
												<th><qp:message code="sc.businesslogicdesign.0036" /></th>
												<th><qp:message code="sc.businesslogicdesign.0037" /></th>
												<th><qp:message code="sc.businesslogicdesign.0038" /></th>
												<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												<th class="bd-assign"><qp:message code="sc.blogiccomponent.0008" /></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
									<div class="qp-add-left">
										<a title='<qp:message code="sc.businesslogicdesign.0200" />' class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" ></a>
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
					<button type="button" class="btn qp-button-client " onclick="saveModalAdvanceSetting(this)">
						<qp:message code="sc.sys.0031"></qp:message>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var javaEditor = CodeMirror.fromTextArea(document.getElementById("editor-java-code"), {
		lineNumbers: true,
	    matchBrackets: true,
	    mode: "text/x-java",
		extraKeys: {
			"Ctrl-Space": "autocomplete"
				},
	});
</script>