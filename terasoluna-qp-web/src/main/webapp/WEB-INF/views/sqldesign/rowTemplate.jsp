<script type="text/javascript">
	var CL_SQL_DATATYPE = {}
	<c:forEach items="${CL_SQL_DATATYPE}" var="item">
		CL_SQL_DATATYPE['${item.key}'] = '${item.value}';
	</c:forEach>
</script>
<script id="fromForm-template" type="text/template">
	<tr class="ar-dataRow">
		<td colspan="2">
			<div class="qp-div-highlight-border">
				<table class="table table-borderless join-conditions-table" data-ar-tlevel="0" data-ar-callback="$.qp.sqlbuilder.fromFormCallback">
					<colgroup>
						<col width="18%"/>
						<col width="30px"/>
						<col width=""/>
					</colgroup>
					<tr>
						<td style="vertical-align:top;">
							<input type="hidden" name="fromForm[].itemSeqNo" class="ar-groupIndex"/>
							<input type="hidden" name="fromForm[].sqlDesignTableId"/>
							<div class="input-group pull-left" style="width:88%;margin-top:15px">
							<input type="text" name="fromForm[].joinTableIdAutocomplete" id="fromForm[].joinTableIdAutocompleteId" class="form-control qp-input-autocomplete"
												optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignByProjectIdWithColumns" minLength = "0"
												onChangeEvent="$.qp.sqlbuilder.passJoinTableNameLabel"
												placeholder='<qp:message code="sc.autocomplete.0016"></qp:message>' />
							<input type="hidden" name="fromForm[].joinTableId" />
							<input type="hidden" name="fromForm[].joinTableType" />
							</div>
							<label class="qp-required-field" style="margin-top:15px">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</div>
						</td>
						<td style="vertical-align:top;padding-top:20px" rowspan="2" class="text-center">
							<b class="glyphicon glyphicon-eye-open joinColumnsFormToggle qp-link-toggle"></b>
							<input type="hidden" name="fromForm[0].isShow"/>	
						</td>
						<td rowspan="2" colspan="3">
							<div style="display:none">
									<div>
										<c:forEach items="${CL_SQL_JOIN_TYPE }" var="clItem" varStatus="clStatus">
											&nbsp;&nbsp;&nbsp;&nbsp;											
											<input type="radio" id="fromForm[0].joinType${clStatus.index }" name="fromForm[0].joinType" value="${clItem.key }" style="vertical-align: middle" onclick="$.qp.sqlbuilder.joinTypeClick(this);">
											<label for="fromForm[0].joinType${clStatus.index }">
											<c:if test="${clItem.key eq '1' }">
												<img title="INNER JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_INNER_JOIN_48.png" >
											</c:if>
											<c:if test="${clItem.key eq '2' }">
												<img title="LEFT JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_LEFT_JOIN_48.png">
											</c:if>
											<c:if test="${clItem.key eq '3' }">
												<img title="RIGHT JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_RIGHT_JOIN_48.png">
											</c:if>
											<c:if test="${clItem.key eq '4' }">
												<img title="FULL OUTER JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_FULL_JOIN_48.png">
											</c:if>
											<c:if test="${clItem.key eq '5' }">
												<img title="CROSS JOIN" src="${pageContext.request.contextPath}/resources/app/autocompleteDesign/img/IMG_CROSS_JOIN_48.png">
											</c:if>
											</label>
										</c:forEach>
									</div>
									<table class="table table-borderless" id="joinPairTable" data-ar-tlevel="1" data-ar-callback="$.qp.sqlbuilder.joinPairCallback" data-ar-precheck="$.qp.sqlbuilder.joinPairPrecheck">
									<colgroup>
										<col width="20%" />
										<col width="20%" />
										<col width="15%" />
										<col width="20%" />
										<col width="20%" />
										<col width="5%" />
									</colgroup>
									<tr style="border-bottom:solid 1.5px rgba(94, 92, 113, 0.15);">
										<td>
											<input type="hidden" name="fromForm[].joinColumnsForm[0].itemSeqNo" class="ar-groupIndex"/>
											<input type="hidden" name="fromForm[].joinColumnsForm[0].sqlDesignTableItemId"/>
											<div class="input-group pull-left" style="width:85%;">
											<input type="text" name="fromForm[].joinColumnsForm[0].tableIdAutocomplete" id="fromForm[].joinColumnsForm[0].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
													optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignByProjectId" minLength = "0"
													onChangeEvent="$.qp.sqlbuilder.passTableId"
													placeholder='<qp:message code="sc.autocomplete.0016"></qp:message>' />
											<input type="hidden" name="fromForm[].joinColumnsForm[0].tableId" />
											<input type="hidden" name="fromForm[].joinColumnsForm[0].tableType" />
											</div>
											<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
										</td>
										<td>
											<div class="input-group pull-left" style="width:85%">
												<input type="text" name="fromForm[].joinColumnsForm[0].columnIdAutocomplete" id="fromForm[].joinColumnsForm[0].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
														optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0" onChangeEvent="$.qp.sqlbuilder.setDisplayOperatorCode" 
														placeholder='<qp:message code="sc.autocomplete.0017"></qp:message>' />
												<input type="hidden" name="fromForm[].joinColumnsForm[0].columnId" />
											</div>
											<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
										</td>
										<td>
											<select name="fromForm[].joinColumnsForm[0].operatorCode" class="form-control qp-input-select pull-left" style="width:80%;white-space: nowrap;overflow: hidden;" onfocus="$.qp.sqlbuilder.initOperatorOptions(this,'1');">
												<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
												<c:forEach var="item" items="${CL_SQL_OPERATOR }">
													<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
												</c:forEach>
												</select>
											<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
										</td>
										<td><b name="fromForm[].rightTableName" class="form-control" style="padding-top: 2px;"></b></td>
										<td>
											<div class="input-group pull-left" style="width:85%">
											<input type="text" name="fromForm[].joinColumnsForm[0].joinColumnIdAutocomplete" id="fromForm[].joinColumnsForm[0].joinColumnIdAutocompleteId" class="form-control qp-input-autocomplete"
													optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
													placeholder='<qp:message code="sc.autocomplete.0017"></qp:message>' />
											<input type="hidden" name="fromForm[].joinColumnsForm[0].joinColumnId" />
											</div>
											<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
								
										</td>
										<td>
											<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
										</td>
									</tr>
								</table>
								<div class="qp-add-left">
									<a class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action" onclick="$.qp.ar.addRow({link:this});" style="margin-top: 3px;" href="javascript:void(0)"></a>
								</div>
							</div>
					</tr>
				</table> 
			</div>
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
		</td>
	</tr>
</script>
<script id="joinPairTable-template" type="text/template">
	<tr class="ar-dataRow" style="border-bottom:solid 1.5px rgba(94, 92, 113, 0.15);">
		<td>
			<div class="input-group pull-left" style="width:85%;">
				<input type="text" name="fromForm[].joinColumnsForm[0].tableIdAutocomplete" id="fromForm[].joinColumnsForm[0].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
					optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignByProjectId" minLength = "0"
					onChangeEvent="$.qp.sqlbuilder.passTableId"
					placeholder='<qp:message code="sc.autocomplete.0016"></qp:message>' value="\${tableName}" />
				<input type="hidden" name="fromForm[].joinColumnsForm[0].tableId" value="\${tableId}"/>
				<input type="hidden" name="fromForm[].joinColumnsForm[0].tableType" value="\${tableType}"/>
			</div>
			<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<div class="input-group pull-left" style="width:85%;">
				<input type="hidden" name="fromForm[].joinColumnsForm[].itemSeqNo" class="ar-groupIndex"/>
				<input type="hidden" name="fromForm[].joinColumnsForm[].sqlDesignTableItemId"/>
				<input type="text" name="fromForm[].joinColumnsForm[0].columnIdAutocomplete" id="fromForm[].joinColumnsForm[0].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0" onChangeEvent="$.qp.sqlbuilder.setDisplayOperatorCode" 
						placeholder='<qp:message code="sc.autocomplete.0017"></qp:message>' value="\${columnName}"/>
				<input type="hidden" name="fromForm[].joinColumnsForm[0].columnId" value="\${columnId}"/>
			</div>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<select name="fromForm[].joinColumnsForm[].operatorCode" class="form-control qp-input-select pull-left" style="width:80%;white-space: nowrap;overflow: hidden;" onfocus="$.qp.sqlbuilder.initOperatorOptions(this,'1');">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
				<c:forEach var="item" items="${CL_SQL_OPERATOR }">
					<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
				</c:forEach>
			</select>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td><b name="fromForm[].rightTableName" class="form-control qp-form-control-label" style="padding-top: 2px;">\${joinTableName}</b></td>
		<td>
			<div class="input-group pull-left" style="width:85%;">
				<input type="text" name="fromForm[].joinColumnsForm[0].joinColumnIdAutocomplete" id="fromForm[].joinColumnsForm[0].joinColumnIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.autocomplete.0017"></qp:message>' value="\${joinColumnName}"/>
				<input type="hidden" name="fromForm[].joinColumnsForm[0].joinColumnId" value="\${joinColumnId}"/>
			</div>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
		</td>
	</tr>					
</script>
<script id="joinPairTable-v1-template" type="text/template">
	<tr  class="ar-dataRow" style="border-bottom:solid 1.5px rgba(94, 92, 113, 0.15);">
		<td>
			<select name="fromForm[].joinColumnsForm[].logicCode" class="form-control qp-input-select pull-left" style="width:80%">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
				<c:forEach var="item" items="${CL_SQL_COMBINING_OPERATOR }">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td style="vertical-align:bottom">
			<div class="input-group pull-left" style="width:90%;margin-bottom:10px">
				<input type="text" name="fromForm[].joinColumnsForm[0].tableIdAutocomplete" id="fromForm[].joinColumnsForm[0].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
					optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignByProjectId" minLength = "0"
					onChangeEvent="$.qp.sqlbuilder.passTableId"
					placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
				<input type="hidden" name="fromForm[].joinColumnsForm[0].tableId" />
			</div>
			<label class="qp-required-field" style="margin-bottom:15px">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
			<div class="input-group pull-left" style="width:90%;">
				<input type="hidden" name="fromForm[].joinColumnsForm[].itemSeqNo" class="ar-groupIndex"/>
				<input type="hidden" name="fromForm[].joinColumnsForm[].sqlDesignTableItemId"/>
				<input type="text" name="fromForm[].joinColumnsForm[0].columnIdAutocomplete" id="fromForm[].joinColumnsForm[0].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0" onChangeEvent="$.qp.sqlbuilder.setDisplayOperatorCode" 
						placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
				<input type="hidden" name="fromForm[].joinColumnsForm[0].columnId" />
			</div>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td style="vertical-align:bottom">
			<select name="fromForm[].joinColumnsForm[].operatorCode" class="form-control qp-input-select pull-left" style="width:85%" onfocus="$.qp.sqlbuilder.initOperatorOptions(this,'1');">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
				<c:forEach var="item" items="${CL_SQL_OPERATOR }">
					<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
				</c:forEach>
			</select>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td style="vertical-align:bottom">
			<div class="input-group pull-left" style="width:90%;">
				<input type="text" name="fromForm[].joinColumnsForm[0].joinColumnIdAutocomplete" id="fromForm[].joinColumnsForm[0].joinColumnIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"  
						placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
				<input type="hidden" name="fromForm[].joinColumnsForm[0].joinColumnId" />
			</div>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
		</td>
	</tr>						
</script>
<script id="whereForm-template" type="text/template">
	<tr  class="ar-dataRow">
		<td>
			<div class="qp-div-highlight-border">
				<table class="table table-borderless qp-table-form">
						<colgroup>
							<col width="15%" />
								<col width="15px" />
								<col width="35%" />
								<col width="15%" />
								<col />
							<col width="15px" />
						</colgroup>
						<tr>
							<td rowspan="3">
								<input type="hidden" name="whereForm[].itemSeqNo" class="ar-groupIndex"/>
								<input type="hidden" name="whereForm[].conditionsId"/>
								<select name="whereForm[0].logicCode" class="form-control qp-input-select pull-left" style="width:85%">
									<c:forEach var="item" items="${CL_SQL_COMBINING_OPERATOR }">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</td>
							<td rowspan="3">
								<label class="qp-open-parenthesis"></label>
							</td>
							<td>
								<div class="input-group pull-left" style="width:90%">
									<input type="text" name="whereForm[0].leftTableIdAutocomplete" 
											id="whereForm[0].leftTableIdAutocompleteId"
											class="form-control qp-input-autocomplete"
											optionValue="optionValue" optionLabel="optionLabel" 
											selectsqlid="getAllTableDesignAC" 
											minLength = "0"
											onChangeEvent="$.qp.sqlbuilder.whereLeftTableChange"
											placeholder='<qp:message code="sc.autocomplete.0016"></qp:message>' />
									<input type="hidden" name="whereForm[0].leftTableId" />
								</div>
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td>
								<select name="whereForm[0].operatorCode" class="form-control qp-input-select pull-left" style="width:85%" onchange="$.qp.sqlbuilder.operatorTypeChange(this);">
									<c:forEach var="item" items="${CL_SQL_OPERATOR }">
										<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
									</c:forEach>
								</select>
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td>
								<div class="input-group pull-left" style="width:90%;display:none">
									<input type="text" name="whereForm[0].rightTableIdAutocomplete" 
											id="whereForm[0].rightTableIdAutocompleteId"
											class="form-control qp-input-autocomplete"
											optionValue="optionValue" optionLabel="optionLabel" 
											selectsqlid="getAllTableDesignAC" 
											minLength = "0"
											onChangeEvent="$.qp.sqlbuilder.whereRightTableChange"
											placeholder='<qp:message code="sc.autocomplete.0016"></qp:message>' />
									<input type="hidden" name="whereForm[0].rightTableId" />
								</div>
								<label class="qp-required-field" style="vertical-align:top;display:none">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td rowspan="3">
								<label class="qp-close-parenthesis"></label>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group pull-left" style="width:90%">
									<input type="text" name="whereForm[0].leftColumnIdAutocomplete" 
											id="whereForm[0].leftColumnIdAutocompleteId"
											class="form-control qp-input-autocomplete"
											optionValue="optionValue" optionLabel="optionLabel" 
											selectsqlid="getAllTableDesignColumnAC" 
											minLength = "0" onChangeEvent="$.qp.sqlbuilder.callSetDisplayFunctionCode"
											placeholder='<qp:message code="sc.autocomplete.0017"></qp:message>' />
									<input type="hidden" name="whereForm[0].leftColumnId" />
								</div>
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
								<input type="hidden" name="whereForm[0].dataType" value=""/>
								<input type="hidden" name="whereForm[0].patternFormat" value=""/>
							</td>
							<td>
								<select name="whereForm[0].conditionType" class="form-control qp-input-select pull-left" style="width:85%" onchange="$.qp.sqlbuilder.conditionTypeChange(this);">
									<c:forEach var="item" items="${CL_SQL_CONDITION_TYPE }">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td>
								<div class="input-group pull-left" style="width:90%;display:none">
									<input type="text" name="whereForm[0].rightColumnIdAutocomplete" 
											id="whereForm[0].rightColumnIdAutocompleteId"
											class="form-control qp-input-autocomplete"
											optionValue="optionValue" optionLabel="optionLabel" 
											selectsqlid="getAllTableDesignColumnAC" 
											minLength = "0"
											placeholder='<qp:message code="sc.autocomplete.0017"></qp:message>' />
									<input type="hidden" name="whereForm[0].rightColumnId" />
								</div>
								<select name="whereForm[0].arg" class="form-control qp-input-select pull-left" onmouseover="$.qp.common.buildInputList('table#inputForm',this)" style="display:none;" onchange="$.qp.sqlbuilder.whereParameterChange(this);">
									<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
									<c:forEach var="item" items="${CL_AUTOCOMPLETE_ARGUMENTS }">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
								<input type="hidden"/>
								<input type="text" name="whereForm[0].value" class="form-control qp-input-text pull-left" style="display:none;"/>
								<label class="qp-required-field pull-left" style="display:none">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
								<div class="qp-separator-from-to" style="display:none">&nbsp;~</div>
								<label class="qp-required-field pull-right" style="display:none">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
								<select name="whereForm[0].arg2" class="form-control qp-input-select pull-right" style="display:none" onmouseover="$.qp.common.buildInputList('table#inputForm',this)"  onchange="$.qp.sqlbuilder.whereParameterChange(this);">
									<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
									<c:forEach var="item" items="${CL_AUTOCOMPLETE_ARGUMENTS }">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
								<input type="hidden"/>
								<input type="text" name="whereForm[0].value2" class="form-control qp-input-text pull-right" style="display:none;"/>
							</td>
						</tr>
						<tr>
							<td>
								<select name="whereForm[0].functionCode" class="form-control qp-input-select pull-left" style="width:90%" onchange="$.qp.sqlbuilder.whereFunctionCodeChange(this)">
									<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
									<c:forEach var="item" items="${CL_SQL_AGGREGATE_FUNCTIONS }">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
			</div>
		</td>
		<td><input type="checkbox" class="qp-input-checkbox" name="whereForm[0].groupType" onchange="$.qp.sqlbuilder.groupTypeChange(this);"/></td>
		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove conditions" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a></td>
	</tr>
</script>
<script id="groupByForm-template" type="text/template">
	<tr class="ar-dataRow">
		<td>
			<input type="hidden" name="groupByForm[].itemSeqNo" class="ar-groupIndex"/>
			<input type="hidden" name="groupByForm[].groupById"/>
			<div class="input-group pull-left" style="width:90%;">
				<input type="text" name="groupByForm[].tableIdAutocomplete" id="groupByForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
					optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignAC" minLength = "0"
					onChangeEvent="$.qp.sqlbuilder.tableChangeToNextCell" 
					placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
				<input type="hidden" name="groupByForm[].tableId" />
			</div>	
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<div class="input-group pull-left" style="width:90%;">
				<input type="text" name="groupByForm[].columnIdAutocomplete" id="groupByForm[].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
					optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
					placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
				<input type="hidden" name="groupByForm[].columnId" />
			</div>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<a style="cursor: move;" class="btn btn-default btn-xs glyphicon glyphicon-move qp-link-action sortable"></a>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove GROUP BY" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
		</td>
	</tr>
</script>
<script id="orderByForm-template" type="text/template">
	<tr class="ar-dataRow">
		<td class="sortable" style="cursor: move;">
			<span style="margin-top: 3px;" class="qp-glyphicon glyphicon glyphicon-sort"></span>
		</td>
		<td>
			<input type="hidden" name="orderByForm[].itemSeqNo" class="ar-groupIndex"/>
			<input type="hidden" name="orderByForm[].orderId"/>
			<div class="input-group pull-left" style="width:95%">
				<input type="text" name="orderByForm[].tableColumnAutocomplete" id="orderByForm[].tableColumnAutocompleteId" class="form-control qp-input-autocomplete"
					optionValue="optionValue" optionLabel="optionLabel" sourceType="local" onchangeevent="$.qp.sqlbuilder.orderByChange" sourceCallback="$.qp.sqlbuilder.resultColumnSourceCallback" minLength = "0"
					placeholder='<qp:message code="sc.sqldesign.0048"></qp:message>' />
				<input type="hidden" name="orderByForm[].tableColumn" />
			</div>	
			<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
		</td>
		<td>
		 	<select name="orderByForm[].orderType" class="form-control qp-input-select pull-left" style="width:95%">
				<c:forEach var="item" items="${CL_SQL_ORDER_DIRECTION }">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
			<label class="qp-required-field">&nbsp;<qp:message code="sc.autocomplete.0043"></qp:message></label>
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
		</td>
	</tr>	
</script>
<script id="orderByForm-v1-template" type="text/template">
	<tr class="ar-dataRow">
		<td>
			<select name="orderByForm[].functionCode" class="form-control qp-input-select pull-left">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
				<c:forEach var="item" items="${CL_SQL_AGGREGATE_FUNCTIONS}">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<input type="hidden" name="orderByForm[].itemSeqNo" class="ar-groupIndex"/>
			<input type="hidden" name="orderByForm[].orderId"/>
			<div class="input-group pull-left" style="width:90%;">
						<input type="text" name="orderByForm[].tableIdAutocomplete" id="orderByForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
							optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignAC" minLength = "0"
							placeholder='<qp:message code="sc.sys.0034"></qp:message>' onChangeEvent="$.qp.sqlbuilder.tableChangeToNextCell" />
						<input type="hidden" name="orderByForm[].tableId" />
			</div>	
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<div class="input-group pull-left" style="width:90%;">
						<input type="text" name="orderByForm[].columnIdAutocomplete" id="orderByForm[].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
							optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
							placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
						<input type="hidden" name="orderByForm[].columnId" />
			</div>		
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<select name="orderByForm[].orderType" class="form-control qp-input-select pull-left" style="width:90%">
				<c:forEach var="item" items="${CL_SQL_ORDER_DIRECTION }">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove ORDER BY" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a>
		</td>
	</tr>
</script>
<script id="havingForm-template" type="text/template">
	<tr class="ar-dataRow">
		<td colspan="4">	
			<input type="hidden" name="havingForm[].itemSeqNo" class="ar-groupIndex"/>
			<input type="hidden" name="havingForm[].havingId"/>
			<div class="qp-div-highlight-border">
				<table class="table table-borderless qp-table-form">
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col width="" />
						</colgroup>
						<tr>
							<td rowspan="3">
								<select name="havingForm[].logicCode" class="form-control qp-input-select pull-left" style="width:85%">
									<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
									<c:forEach var="item" items="${CL_SQL_COMBINING_OPERATOR}">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td>
								<div class="input-group pull-left" style="width:90%;">
									<input type="text" name="havingForm[].tableIdAutocomplete" id="havingForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
										optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignAC" minLength = "0"
										placeholder='<qp:message code="sc.sys.0034"></qp:message>' onChangeEvent="$.qp.sqlbuilder.havingTableChange"
								 />
									<input type="hidden" name="havingForm[].tableId" />
								</div>	
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
						</tr>
						<tr>
							<td>
								<div class="input-group pull-left" style="width:90%;">
									<input type="text" name="havingForm[].columnIdAutocomplete" id="havingForm[].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
										optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
										placeholder='<qp:message code="sc.sys.0034"></qp:message>' onChangeEvent="$.qp.sqlbuilder.formatHavingValue"
									 />
									<input type="hidden" name="havingForm[].columnId" />
								</div>		
								<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td>
								<select name="havingForm[].operatorCode" class="form-control qp-input-select pull-left" style="width:85%" onchange="$.qp.sqlbuilder.operatorTypeChange(this);">
									<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
									<c:forEach var="item" items="${CL_SQL_OPERATOR}">
										<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
									</c:forEach>
								</select>
								<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
							<td>
								<input type="text" class="form-control qp-input-text pull-left" style="width:93.58%;display:inline-block;" name="havingForm[].value" />
								<label class="qp-required-field pull-left" style="vertical-align:top;display:inline-block;">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
								<div class="qp-separator-from-to" style="display:none">&nbsp;~</div>
								<label class="qp-required-field pull-right" style="vertical-align:top;display:none;">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>													
								<input type="text" name="havingForm[].value2" class="form-control qp-input-text pull-right" style="display:none;"/>	
							</td>
						</tr>
						<tr>
							<td>
								<select name="havingForm[].functionCode" class="form-control qp-input-select pull-left" style="width:90%">
									<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
									<c:forEach var="item" items="${CL_SQL_AGGREGATE_FUNCTIONS}">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
								<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
							</td>
						</tr>
					</table>
			</div>
		</td>
		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="Click here to remove conditions" onclick="$.qp.ar.removeRow({link:this});" href="javascript:void(0)"></a></td>
	</tr>
</script>
<script id="valueForm-template-input" type="text/template">
	<input name="\${name}" class="form-control pull-left" style="width:90%">
	</input>
	<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
</script>
<script id="valueForm-template-input-checkbox" type="text/template">
	<div style="width:90%; display:inline-block; vertical-align: middle; text-align: center;">
		<input  type="checkbox" onchange="$.qp.sqlbuilder.setValueForparam(this)"/>
		<input  type="hidden" name="\${name}" value="false"/>
	</div>
	<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
</script>
<script id="valueForm-template-input-datepicker" type="text/template">
	<div style="width:90%; display:inline-block; vertical-align: middle;">
		<div class="input-group date">
			<input name="\${name}" class="form-control"/>
			<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		</div>
	</div>
	<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
</script>
<script id="valueForm-template-select" type="text/template">
	<select name="\${name}" class="form-control qp-input-select pull-left" onmouseover="$.qp.common.buildInputList('table#inputForm',this)" style="width:90%">
		<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
	</select>
	<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
</script>
<script id="valueForm-template-autoComplete" type="text/template">
	<div class="input-group pull-left" style="width:90%;">
		<input type="text" class="form-control qp-input-autocomplete"
			name="\${name}Autocomplete"
			optionValue="optionValue" optionLabel="optionLabel" 
			selectsqlid="getAllTableDesignColumnAC"
			arg01="\${tableId}"
			onchangeevent="$.qp.sqlbuilder.clearSqlScirpt"
			minLength = "0"
			placeholder='<qp:message code="sc.sys.0034"></qp:message>' 
		/>
		<input type="hidden" name="\${name}" />
	</div>
	<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
</script>
<script id="valueForm-template" type="text/template">
	<tr class="ar-dataRow">
		<td>
			<input type="hidden" name="valueForm[].itemSeqNo" cssClass="ar-groupIndex"/>
			<input type="hidden" name="valueForm[].sqlDesignValueId"/>
			<div class="input-group pull-left" style="width:90%;">
				<input type="text" name="valueForm[].columnIdAutocomplete" 
						id="valueForm[].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" 
						selectsqlid="getAllTableDesignColumnAC"
						minLength = "0" onChangeEvent="$.qp.sqlbuilder.changeDataTypeColumnValueItem" 
						sourceCallback="$.qp.sqlbuilder.changeSourceValueItem"
						sourceType="local"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>' 
				/>
			<input type="hidden" name="valueForm[].columnId" />
			</div>
			<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
			<input type="hidden" name="valueForm[].dataType" value=""/>
			<input type="hidden" name="valueForm[].patternFormat" value=""/>
		</td>
		<td align="center">
			=
		</td>
		<td>
			<select class="form-control qp-input-select pull-left" name="valueForm[].valueType" style="width:90%" onchange="$.qp.sqlbuilder.changeTypeInsertValue(this)">
				<c:forEach items="${CL_QP_VALUE_TYPE}" var="item">
					<option value="${item.key}"><qp:message code="${item.value}"></qp:message></option>
				</c:forEach>
			</select>
			<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td>
			<select name="valueForm[].parameter" onmouseover="$.qp.common.buildInputList('table#inputForm',this)" class="form-control qp-input-select pull-left" style="width:90%">
				<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
			</select>
			<label class="qp-required-field">&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
		</td>
		<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});"></a></td>
	</tr>
</script>
<script id="selectForm-v1-template" type="text/template">
		<tr class="ar-dataRow">
			<td>
				<select name="selectForm[].functionCode" class="form-control qp-input-select pull-left">
					<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
					<c:forEach var="item" items="${CL_SQL_AGGREGATE_FUNCTIONS}">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="hidden" name="selectForm[].itemSeqNo" class="ar-groupIndex"/>
				<input type="hidden" name="selectForm[].groupById"/>
				<div class="input-group pull-left" style="width:90%;">
					<input type="text" name="selectForm[].tableIdAutocomplete" id="selectForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignAC" minLength = "0"
						onChangeEvent="$.qp.sqlbuilder.tableChangeToNextCell" 
						placeholder='<qp:message code="sc.sys.0034"></qp:message>' />
					<input type="hidden" name="selectForm[].tableId" />
				</div>	
				<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
			</td>
			<td>
				<div class="input-group pull-left" style="width:90%;">
					<input type="text" name="selectForm[].columnIdAutocomplete" id="selectForm[].columnIdAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>' onChangeEvent="$.qp.sqlbuilder.columnAutocompleteChange"/>
					<input type="hidden" name="selectForm[].columnId" />
				</div>
				<label class="qp-required-field" >&nbsp;<qp:message code="sc.sys.0029"></qp:message></label>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this});"></a>
			</td>
		</tr>
</script>
<script id="selectForm-template" type="text/template">
		<tr data-tableId="\${tableId}" style="\${style}" class="ar-dataRow">
			<td>
				<input type="checkbox" name="selectForm[].isSelected" value="true" onchange="$.qp.sqlbuilder.selectFunctionCodeChange(this);" checked="checked"/>
			</td>
			<td>
				<input type="hidden" name="selectForm[].itemSeqNo" class="ar-groupIndex"/>
				<input type="hidden" name="selectForm[].groupById"/>
				<input type="hidden" name="selectForm[].tableId" value="\${tableId}"/>
				<input type="hidden" name="selectForm[].tableType" value="\${tableType}"/>
				<input type="hidden" name="selectForm[].tableIdAutocomplete" value="\${tableName}"/>
				\${tableName}
			</td>
			<td>
				<input type="hidden" name="selectForm[].columnId" value="\${columnId}"/>
				<input type="hidden" name="selectForm[].columnIdAutocomplete" value="\${columnName}"/>
				<input type="hidden" name="selectForm[].columnCode" value="\${columnCode}"/>
				\${columnName}
				<input type="hidden" name="selectForm[].dataType" value="\${dataType}"/>
				<label name="dataTypeBackup" value="\${dataType}" style="display: none;"></label>
			</td>
			<td>
				<select name="selectForm[].functionCode" class="form-control qp-input-select pull-left" onchange="$.qp.sqlbuilder.selectFunctionCodeChange(this);">
					<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
					<c:forEach var="item" items="${CL_SQL_AGGREGATE_FUNCTIONS}">
						<option value="${item.key}">${item.value}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
</script>
<script id="inputForm-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<input type="text" name="inputForm[].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="inputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="inputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="inputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="inputForm[].sqlDesignInputId" />
				<input type="hidden" name="inputForm[].designType" value="0" />
				<input type="text" name="inputForm[].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="inputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td class="autocomleteType"></td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="inputForm-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<input type="text" name="inputForm[].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="inputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="inputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="inputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="inputForm[].sqlDesignInputId" />
				<input type="hidden" name="inputForm[].designType" value="0" />
				<input type="text" name="inputForm[].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="inputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
					<span class="input-group-addon">
						<input type="checkbox" name="inputForm[].isArray" id="inputForm[].isArray" aria-label="Array" class="qp-input-checkbox">
						<label for="inputForm[].isArray"><qp:message code="sc.sqldesign.0008"></qp:message></label>
					</span>
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="inputForm-entity-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="inputForm[].tableIdAutocomplete" id="inputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteGetTableForBD" onChangeEvent="$.qp.common.entityAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="inputForm[].tableId" />
						</div>						
						<input type="hidden" name="inputForm[].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row""/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="inputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="inputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="inputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="inputForm[].sqlDesignInputId" />
				<input type="hidden" name="inputForm[].designType" value="0" />
				<input type="hidden" name="inputForm[].objectType" value="4"/>
				<input type="text" name="inputForm[].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"   value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="inputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
					<span class="input-group-addon">
						<input type="checkbox" name="inputForm[].isArray" id="inputForm[].isArray" aria-label="Array" class="qp-input-checkbox">
						<label for="inputForm[].isArray">Array</label>
					</span>
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="inputForm-child-entity-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;text-align:left">	
						<input type="hidden" name="inputForm[].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row""/>
						<label name="inputForm[].sqlDesignInputName"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="inputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="inputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="inputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="inputForm[].sqlDesignInputId" />
				<input type="hidden" name="inputForm[].tableId" />
				<input type="hidden" name="inputForm[].columnId" />
				<input type="hidden" name="inputForm[].sqlDesignInputCode" >
				<input type="hidden" name="inputForm[].designType" value="1" />
				<input type="hidden" name="inputForm[].objectType" />
				<label name="inputForm[].sqlDesignInputCode"/>
			</td>
			<td>
				<input type="hidden" name="inputForm[].dataType" />
				<input type="hidden" name="inputForm[].isArray" />
				<label name="inputForm[].dataType" />
			</td>
			<td>
			</td>
	</tr>
</script>
<script id="inputForm-common-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="inputForm[].tableIdAutocomplete" id="inputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteCommonObjectForSQLDesign" onChangeEvent="$.qp.common.commonObjectAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="inputForm[].tableId" />
							<input type="hidden" name="inputForm[].moduleId" />
						</div>						
						<input type="hidden" name="inputForm[].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row"/>
						<input type="hidden" name="inputForm[].moduleId"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="inputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="inputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="inputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="inputForm[].sqlDesignInputId" />
				<input type="hidden" name="inputForm[].designType" value="0" />
				<input type="hidden" name="inputForm[].objectType" value="0"/>
				<input type="text" name="inputForm[].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"   value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="inputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
					<span class="input-group-addon">
						<input type="checkbox" name="inputForm[].isArray" id="inputForm[].isArray" aria-label="Array" class="qp-input-checkbox">
						<label for="inputForm[].isArray">Array</label>
					</span>
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="inputForm-external-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="inputForm[].tableIdAutocomplete" id="inputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteExternalObjectForSQLDesign" onChangeEvent="$.qp.common.externalObjectAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="inputForm[].tableId" />
						</div>						
						<input type="hidden" name="inputForm[].sqlDesignInputName" class="form-control qp-input-text qp-convention-name-row"/>
						<input type="hidden" name="inputForm[].moduleId"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="inputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="inputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="inputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="inputForm[].sqlDesignInputId" />
				<input type="hidden" name="inputForm[].designType" value="0" />
				<input type="hidden" name="inputForm[].objectType" value="1"/>
				<input type="text" name="inputForm[].sqlDesignInputCode" class="form-control qp-input-text qp-convention-code-row"  value="\${objectCode}" >
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="inputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'inputForm','inputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
					<span class="input-group-addon">
						<input type="checkbox" name="inputForm[].isArray" id="inputForm[].isArray" aria-label="Array" class="qp-input-checkbox">
						<label for="inputForm[].isArray">Array</label>
					</span>
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>

<script id="inputForm-action-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}">
		<td colspan="7">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'inputForm',templateId:'inputForm-l1-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>
<script id="outputForm-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<input type="text" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].columnId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="1"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"   value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l0-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
					<span class="input-group-addon" style="display:none">
						<input type="checkbox" disabled name="outputForm[].isArray" aria-label="Array" id="outputForm[].isArray">
						<label for="outputForm[0].isArray">Array</label>      
					</span>
				</div>
			</td>
			<td>
				<div class="input-group pull-left">
					<input type="text" name="outputForm[].mappingColumnAutocomplete" onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange" id="outputForm[].mappingColumnAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
					<input type="hidden" name="outputForm[].mappingColumn" />
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-l0-insert-template" type="text/template">
	<tr  data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right vertical-midle-div" style="height:100%;vertical-align: middle; justify-content: flex-start">	
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row" value="result"/>
						<span>result</span>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].columnId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="1"/>
				<input type="hidden" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"   value="result">
				<span>result</span>
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<input type="hidden" name="outputForm[].dataType" value="3" />
					<span>${CL_SQL_DATATYPE.get('3')}</span>
				</div>
			</td>
			<td>
			</td>
			<td>
			</td>
	</tr>
</script>
<script id="outputForm-entity-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="outputForm[].tableIdAutocomplete" id="outputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteGetTableForBD" onChangeEvent="$.qp.common.entityAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="outputForm[].tableId" />
						</div>						
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="4"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row" value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l0-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<div class="input-group pull-left">
					<input type="text" name="outputForm[].mappingColumnAutocomplete" onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange" id="outputForm[].mappingColumnAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
					<input type="hidden" name="outputForm[].mappingColumn" />
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-child-entity-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;text-align:left">	
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
						<label name="outputForm[].sqlDesignOutputName" />
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].tableId" />
				<input type="hidden" name="outputForm[].columnId" />
				<input type="hidden" name="outputForm[].designType" value="1" />
				<input type="hidden" name="outputForm[].objectType" value=""/>
				<input type="hidden" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row">
				<label name="outputForm[].sqlDesignOutputCode"/>
			</td>
			<td>
				<input type="hidden" name="outputForm[].dataType" />
				<label name="outputForm[].dataType" />
			</td>
			<td>
				<div class="input-group pull-left">
					<input type="text" name="outputForm[].mappingColumnAutocomplete" onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange" id="outputForm[].mappingColumnAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
					<input type="hidden" name="outputForm[].mappingColumn" />
				</div>
			</td>
			<td>
			</td>
	</tr>
</script>
<script id="outputForm-common-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="outputForm[].tableIdAutocomplete" id="outputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteCommonObjectForSQLDesign" onChangeEvent="$.qp.common.commonObjectAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="outputForm[].tableId" />
							<input type="hidden" name="outputForm[].moduleId" />
						</div>						
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row"/>
						<input type="hidden" name="outputForm[].moduleId"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="0"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row" value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l0-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<div class="input-group pull-left">
					<input type="text" name="outputForm[].mappingColumnAutocomplete" onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange" id="outputForm[].mappingColumnAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
					<input type="hidden" name="outputForm[].mappingColumn" />
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-external-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="outputForm[].tableIdAutocomplete" id="outputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteExternalObjectForSQLDesign" onChangeEvent="$.qp.common.externalObjectAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="outputForm[].tableId" />
						</div>						
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
						<input type="hidden" name="outputForm[].moduleId"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="1"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row" value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-sql-builder','outputForm-action-l0-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<div class="input-group pull-left">
					<input type="text" name="outputForm[].mappingColumnAutocomplete" onChangeEvent="$.qp.sqlbuilder.outputFormMappingColumnChange" id="outputForm[].mappingColumnAutocompleteId" class="form-control qp-input-autocomplete"
						optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAllTableDesignColumnAC" minLength = "0"
						placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
					<input type="hidden" name="outputForm[].mappingColumn" />
				</div>
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-entity-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="outputForm[].tableIdAutocomplete" id="outputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteGetTableForBD" onChangeEvent="$.qp.common.entityAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="outputForm[].tableId" />
						</div>						
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="4"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"" >
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-advanced-sql','outputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<input type="text" name="outputForm[].mappingColumn" class="form-control qp-input-text">
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-child-entity-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;text-align:left">	
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
						<label name="outputForm[].sqlDesignOutputName"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].tableId" />
				<input type="hidden" name="outputForm[].columnId" />
				<input type="hidden" name="outputForm[].sqlDesignOutputCode" >
				<input type="hidden" name="outputForm[].designType" value="1" />
				<input type="hidden" name="outputForm[].objectType" value=""/>
				<label name="outputForm[].sqlDesignOutputCode"/>
			</td>
			<td>
				<input type="hidden" name="outputForm[].isArray" />
				<input type="hidden" name="outputForm[].dataType" />
				<label name="outputForm[].dataType" />
			</td>
			<td>
				<input type="text" name="outputForm[].mappingColumn" class="form-control qp-input-text">
			</td>
			<td>
			</td>
	</tr>
</script>
<script id="outputForm-common-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="outputForm[].tableIdAutocomplete" id="outputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteCommonObjectForSQLDesign" onChangeEvent="$.qp.common.commonObjectAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="outputForm[].tableId" />
							<input type="hidden" name="outputForm[].moduleId" />
						</div>						
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row"/>
						<input type="hidden" name="outputForm[].moduleId"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="0"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row"" >
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-advanced-sql','outputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<input type="text" name="outputForm[].mappingColumn" class="form-control qp-input-text">
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-external-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<div class="input-group pull-left">
							<input type="text" name="outputForm[].tableIdAutocomplete" id="outputForm[].tableIdAutocompleteId" class="form-control qp-input-autocomplete"
								optionValue="optionValue" optionLabel="optionLabel" selectsqlid="getAutocompleteExternalObjectForSQLDesign" onChangeEvent="$.qp.common.externalObjectAutocompleteChange" arg01="${sessionScope.CURRENT_PROJECT.projectId}" minLength = "0"
								placeholder='<qp:message code="sc.sys.0034"></qp:message>'/>
							<input type="hidden" name="outputForm[].tableId" />
						</div>						
						<input type="hidden" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row"/>
						<input type="hidden" name="outputForm[].moduleId"/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="hidden" name="outputForm[].objectType" value="1"/>
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row" value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-advanced-sql','outputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<input type="text" name="outputForm[].mappingColumn" class="form-control qp-input-text">
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}" class="ar-dataRow">
			<td colspan="4">
				<div style="height:100%">
					<div><span class="ar-groupIndex"></span></div>
					<div class="pull-right" style="height:100%;vertical-align: middle;">	
						<input type="text" name="outputForm[].sqlDesignOutputName" class="form-control qp-input-text qp-convention-name-row""/>
					</div>
				</div>
			</td>
			<td>
				<input type="hidden" name="outputForm[].groupId" class="ar-groupId" />
				<input type="hidden" name="outputForm[].groupIndex" class="ar-groupIndex" />
				<input type="hidden" name="outputForm[].itemSeqNo" class="ar-rIndex" />
				<input type="hidden" name="outputForm[].sqlDesignOutputId" />
				<input type="hidden" name="outputForm[].designType" value="0" />
				<input type="text" name="outputForm[].sqlDesignOutputCode" class="form-control qp-input-text qp-convention-code-row" value="\${objectCode}">
			</td>
			<td>
				<div class="input-group" style="width:100%">
					<select name="outputForm[].dataType" class="form-control qp-input-select pull-left" onchange="$.qp.common.objectTypeChange(this,'outputForm-advanced-sql','outputForm-action-l1-template');">
						<c:forEach var="item" items="${CL_SQL_DATATYPE}">
							<option value="${item.key}">${item.value}</option>
						</c:forEach>
					</select>
					<span class="input-group-addon" style="display:none">
						<input type="checkbox" disabled name="outputForm[].isArray" aria-label="Array" id="outputForm[].isArray">
						<label for="outputForm[0].isArray">Array</label>      
					</span>
				</div>
			</td>
			<td>
				<input type="text" name="outputForm[].mappingColumn" class="form-control qp-input-text">
			</td>
			<td>
				<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this})"></a>
			</td>
	</tr>
</script>
<script id="outputForm-action-l1-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}">
		<td colspan="8">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'outputForm-advanced-sql',templateId:'outputForm-l1-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>
<script id="outputForm-action-l0-template" type="text/template">
	<tr data-ar-rgroup="\${groupId}">
		<td colspan="8">
			<div style="height:100%">
				<div class="pull-left" style="height:100%;vertical-align: middle;">
					<a title="Add new row" class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action pull-left" onclick="$.qp.ar.addRow({link: this,tableId:'outputForm-sql-builder',templateId:'outputForm-l0-template',templateData:{groupId:$(this).closest('tr').attr('data-ar-rgroup') },position:{anchor:$(this).closest('tr'),string:'before'}})"></a>
				</div>
			</div>
		</td>
	</tr>
</script>