<!-- Dialog for advance setting -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/loggingmanagement/javascript/conversionpatterninit.js" ></script>
<div class="modal fade" id="conversionPatternSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog modal-lg" id="modalDialog">
		<div class="modal-content">
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>
			<div class="modal-body">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.loggingmanagement.0037" /></span>
					</div>
					<script type="text/javascript">
						<!-- Declare LIST PATTERN get from DB  -->
						var LIST_PATTERN = [];
						<c:forEach items="${loggingManagementForm.lstConversionPattern}" var="item">
							LIST_PATTERN.push({
						        patternId: '${item.patternId}',
						        patternCode: '${item.patternCode}',
						        patternName: '${item.patternName}',
						        remark: '${item.remark}',
						        itemSequence: Number.MAX_VALUE
						    });
						</c:forEach>
					</script>
					<div class="panel-body" id="panelBody">
						<table  class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="80%" />
							</colgroup>
							<tr>
								<th ><label ><qp:message code="sc.loggingmanagement.0038" /></label></th>
								<td ><label name ="currentPatternText" id="currentPatternText" style="width: 100%;"> </label></td>
							</tr>
						</table>
						<br/>
						<div class="table-responsive">
							<input type="hidden" id="dialogLogType"/>
							<input type="hidden" id="dialogLogIndex" />
							<input type="hidden" id="dialogSelectedId" />
							<table id="patternTable" class="table table-bordered qp-table-form" style="width: 100%">
								<colgroup>
									<col width="40px" />
									<col width="30%" />
									<col width="60%" />
									<col width="35px" />
									<col width="40px" />
								</colgroup>
								<thead>
									<th style="text-align: left;"><qp:message code="sc.loggingmanagement.0039" /></th>
									<th style="text-align: left;"><qp:message code="sc.loggingmanagement.0040" /></th>
									<th style="text-align: left;"><qp:message code="sc.loggingmanagement.0041" /></th>
									<th style="text-align: left;"><input id="checkAll" name="checkAll" class="qp-input-checkbox-margin qp-input-checkbox" type="checkbox"></th>
									<th>&nbsp;</th>
								</thead>
								<tbody class="ui-sortable" id="conversionPatternTableContent">
								
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<qp:authorization permission="loggingmanagementModify">
						<button type="button" class="btn qp-button-client" onclick="saveConversionPattern(this);"><qp:message code="sc.sys.0031" /></button>
					</qp:authorization>
				</div>
			</div>
		</div>
	</div>
</div>