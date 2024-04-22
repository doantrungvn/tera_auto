<!-- dialog option setting  -->
	<div class="modal fade" id="settingForeignKeys" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 55%">
		<div class="modal-content">
			<div class="modal-header"><button type="button" id="close-foreign-key-button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div id="dialog-component-list-setting-codelist" class="tab-pane" style="min-height: 200px">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.tabledesign.0056" /></span>
						</div>
						<div class="panel-body">
							<form:hidden path="storeForeignKeyTemp" value="${tableDesignForm.storeForeignKeyTemp}"/>
							<input type="hidden" id="fistClick" value = "0" />
							<input type="hidden" id="projectId" value="${tableDesignForm.projectId}" />
							<script id="tbl_fk_list-template" type="text/template">
								<tr>
									<td class="qp-output-fixlength tableIndex">1</td>
									<td><input id="tableDesignForeignKeys0.foreignKeyCode" name="tableDesignForeignKeys[0].foreignKeyCode" class="form-control qp-input-text" type="text" maxlength="30"></td>
									<td>
										<select id="tableDesignForeignKeys0.fromColumnCode" name="tableDesignForeignKeys[0].fromColumnCode" class="form-control qp-input-select" onchange="selectColumnName(event)"></select>
										<input id="tableDesignForeignKeys0.fromColumnName" name="tableDesignForeignKeys[0].fromColumnName" type="hidden"/>
										<input id="tableDesignForeignKeys0.fromColumnId" name="tableDesignForeignKeys[0].fromColumnId" type="hidden"/>
										<input id="tableDesignForeignKeys0.indexRow" name="tableDesignForeignKeys[0].indexRow" type="hidden"/>
										<input id="tableDesignForeignKeys0.foreignKeyType" name="tableDesignForeignKeys[0].foreignKeyType" type="hidden"/>
									</td>
									<td>
										<qp:autocomplete 
											selectSqlId="getAllTableDesignByProjectId" 
											emptyLabel="sc.sys.0030" maxlength="200" 
											name="tableDesignForeignKeys[].toTableId"
											onChangeEvent="changeTableAC"
											arg02="${tableDesignForm.projectId}"
											value="" 
											displayValue="" 
											mustMatch="true"
											optionValue="optionValue" optionLabel="optionLabel">
										</qp:autocomplete>
										<input type="hidden" id="flgCheckLoadForeignkey" />
										<input id="tableDesignForeignKeys0.toTableCode" name="tableDesignForeignKeys[0].toTableCode" type="hidden"/>
										<input id="tableDesignForeignKeys0.toTableName" name="tableDesignForeignKeys[0].toTableName" type="hidden"/>
									</td>
									<td>
										<qp:autocomplete 
											selectSqlId="getAllTableDesignColumnACFK"
											emptyLabel="sc.sys.0030" maxlength="200" 
											name="tableDesignForeignKeys[0].toColumnId"
											value="${tableDesignForeignKeys[0].toColumnCode}" 
											displayValue="${listTableDesignDetails[0].toColumnCodeAutocomplete}"
											onChangeEvent="changeColumnAC"
											mustMatch="true"
											optionValue="optionValue" optionLabel="optionLabel">
										</qp:autocomplete>
										<input id="tableDesignForeignKeys0.toColumnCode" name="tableDesignForeignKeys[0].toColumnCode" type="hidden"/>
										<input id="tableDesignForeignKeys0.toColumnName" name="tableDesignForeignKeys[0].toColumnName" type="hidden"/>
										<input id="tableDesignForeignKeys0.toForeignKeyType" name="tableDesignForeignKeys[0].toForeignKeyType" type="hidden"/>
									</td>
									<td>
										<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJSEx(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
									</td>
								</tr>
							</script>
							<fieldset>
								  <legend style="text-align: center;">
								  	<c:if test="${ not empty tableDesignForm.tableName }"><qp:message code="sc.tabledesign.0057" /> "${tableDesignForm.tableName}"</c:if>
								  	<c:if test="${ empty tableDesignForm.tableName}"><qp:message code="sc.tabledesign.0058" /></c:if>
								  </legend>
							</fieldset>
							<table class="table table-bordered qp-table-list" id="tbl_fk_list">
								<colgroup>
									<col>
									<col width="23%">
									<col width="20%">
									<col width="23%">
									<col width="20%">
									<col>
								</colgroup>
								<thead>
									<tr>
										<th><qp:message code="sc.sys.0004" /></th>
										<th><qp:message code="sc.tabledesign.0027" /><span class="qp-required-field"> (*) </span></th><!-- Foreign Key Name -->
										<th><qp:message code="sc.tabledesign.0028" /><span class="qp-required-field"> (*) </span></th><!-- From Column -->
										<th><qp:message code="sc.tabledesign.0029" /><span class="qp-required-field"> (*) </span></th><!-- To Table -->
										<th><qp:message code="sc.tabledesign.0030" /><span class="qp-required-field"> (*) </span></th><!-- To Column -->
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="key" items="${tableDesignForm.tableDesignForeignKeys}" varStatus="status">
										<tr>
											<td class="qp-output-fixlength tableIndex">${status.count}</td>
											<td><form:input path="tableDesignForeignKeys[${status.index}].foreignKeyCode" value="${key.foreignKeyCode}" cssClass="form-control qp-input-text" maxlength="30" /></td>
											<td>
												<form:select path="tableDesignForeignKeys[${status.index}].fromColumnCode" value="${key.fromColumnCode}" cssClass="form-control qp-input-select" onchange="selectColumnName(event)"/>
												<form:hidden path="tableDesignForeignKeys[${status.index}].fromColumnCode" value="${key.fromColumnCode}" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].fromColumnName" value="${key.fromColumnName}" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].fromColumnId" value="${key.fromColumnId}" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].indexRow" value="${key.indexRow}" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].foreignKeyType" value="${key.foreignKeyType}" />
											</td>
											<td style="font-size: 12px;">
												<qp:autocomplete 
													name="tableDesignForeignKeys[${status.index}].toTableId" 
													optionValue="optionValue" 
													optionLabel="optionLabel" 
													selectSqlId="getAllTableDesignByProjectId" 
													arg02="${tableDesignForm.projectId}"
													arg03="${key.toTableId}"
													value="${key.toTableId}" 
													emptyLabel="sc.sys.0030"
													displayValue="${key.toTableName}" 
													onChangeEvent="changeTableAC" 
													mustMatch="true" maxlength="200" >
												</qp:autocomplete>
												<form:hidden path="tableDesignForeignKeys[${status.index}].toTableCode" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].toTableName" />
												<input type="hidden" id="flgCheckLoadForeignkey" />
											</td>
											<td style="border-left: none;">
												<qp:autocomplete 
													name="tableDesignForeignKeys[${status.index}].toColumnId" 
													optionValue="optionValue" 
													optionLabel="optionLabel" 
													selectSqlId="getAllTableDesignColumnACFK" 
													value="${key.toColumnId}" 
													emptyLabel="sc.sys.0030"
													displayValue="${key.toColumnName}"  
													onChangeEvent="changeColumnAC"
													mustMatch="true" maxlength="200" >
												</qp:autocomplete>
												<form:hidden path="tableDesignForeignKeys[${status.index}].toColumnCode" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].toColumnName" />
												<form:hidden path="tableDesignForeignKeys[${status.index}].toForeignKeyType" />
											</td>
											<td>
												<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' onclick="$.qp.removeRowJSEx(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
											</td>
										</tr>
									</c:forEach>
								</tbody>										
							</table>
							<div>
								<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" id="plusOfForeignKey" onclick="$.qp.addRowJSByLink(this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn qp-button-client"  onclick="saveDialogSetting();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>