<!-- dialog option setting  -->
	<div class="modal fade" id="settingKey" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header"><button id="close-primary-key-button" type="button" onclick="close()" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div id="dialog-component-list-setting-codelist" class="tab-pane" style="min-height: 200px">
					<div class="panel panel-default qp-div-information">
						<div class="panel-heading">
							<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.tabledesign.0059" /></span>
						</div>
						<input type="hidden" id="storeKeyTemp">
						<input type="hidden" id="flgRemovekey">
						<input type="hidden" id="currentKey" value="0">  
						<div class="panel-body">
							<fieldset>
								  <legend style="text-align: center;">
								  	<c:if test="${ not empty tableDesignForm.tableName }"><qp:message code="sc.tabledesign.0060" /> "${tableDesignForm.tableName}"</c:if>
								  	<c:if test="${ empty tableDesignForm.tableName}"><qp:message code="sc.tabledesign.0061" /></c:if>
								  </legend>
							</fieldset>
							<table class="table table-bordered qp-table-form">
								<colgroup>
									<col width="50%">
									<col width="50%">
								</colgroup>
								<tr>
									<td>
										<select id="keyList" class="form-control qp-input-select" onkeydown="changeKeyList(event)" onchange="changeStoreKey(event)"></select>
									</td>
									<td style="font-size: 12px;">
										<input type="button" id="keyAdd" onclick="addKey()" class="btn qp-button-client" value="<qp:message code="sc.tabledesign.0062" />">
										<input type="button" id="keyRemove" onclick="removeKey()" class="btn qp-button-client" value="<qp:message code="sc.tabledesign.0063" />">
									</td>
								</tr>										
							</table>
							<fieldset>
								  <legend style="text-align: center;"><qp:message code="sc.tabledesign.0064" /></legend>
							</fieldset>
							<table class="table table-bordered qp-table-form">
								<colgroup>
									<col width="40%">
									<col width="20%">
									<col width="40%">
								</colgroup>
								<tr>
									<td>
										<label for="keytype" id="keytypelabel"><qp:message code="sc.tabledesign.0065" />:</label>
										<select id="keytype" class="form-control qp-input-select">
											<option value="1">PRIMARY</option>
											<option value="8">INDEX</option>
											<option value="4">UNIQUE</option>
											<!-- <option value="4">FULLTEXT</option> -->
										</select>
									</td>
									<td id="storeKeySave">
										<c:forEach var="tableDesignKey" items="${tableDesignForm.listTableDesignKey}" varStatus="status">
											<input type="hidden" name="" value="${tableDesignKey.strKeyItems}" />
										</c:forEach>
									</td>
									
									<td>
										<label for="keyname" id="keynamelabel" style="text-align: center;"><qp:message code="sc.tabledesign.0066" />:</label><span class="qp-required-field">(*)</span>
										<input type="text" id="keyname" size="10" class="form-control qp-input-text">
									</td>
								</tr>
								<tr>
									<td colspan="3"><hr>
										<input type="hidden" id="listColumnCodeBeforeModify"/>
										<input type="hidden" id="listValueKeyObjectType"/>
										<input type="hidden" id="maxTableDetailsRow"/>
									</td>
								</tr>
								<tr>
									<td>
										<label for="keyfields" id="keyfieldslabel" style="text-align: center;"><qp:message code="sc.tabledesign.0071" /></label><br>
										<select id="keyfields" size="5" multiple="multiple" class="form-control qp-input-select"></select>
									</td>
									<td align="center">
										<input type="button" id="keyleft" onclick="toLeftClick()" value="&lt;&lt;" class="btn qp-button-client" style="font-size: 12px;"><br>
										<input type="button" id="keyright" onclick="toRightClick()" value="&gt;&gt;" class="btn qp-button-client" style="font-size: 12px; margin-top: 5px;"><br>
									</td>
									<td>
										<label for="keyavail" id="keyavaillabel" style="text-align: center;"><qp:message code="sc.tabledesign.0072" /></label><br>
										<select id="keyavail" size="5" multiple="multiple" class="form-control qp-input-select"></select>
									</td>
								</tr>									
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn qp-button-client"  onclick="setKeyType();"><qp:message code="sc.sys.0054" /></button>
				</div>
			</div>
		</div>
	</div>
</div>